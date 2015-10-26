/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jagently.cloud;

import jade.core.AID;
import jade.core.Agent;
import jade.core.Location;
import jade.core.ProfileImpl;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ameerah
 */
public class SupervisorAgent extends Agent {

    private String urlAddr, agentName;
    private AID agentID;
    Vector containerList;
    //InetAddress address;
    int numberofAgents, tickerInterval, portNumber;
    Location addressofPawn;
     int globalAgentCount = 0;

    public void setup() {
        
        
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("SupervisorAgent");
        sd.setName(getName());
        sd.setOwnership("Jagently");
        sd.addOntologies("SupervisorAgent");
        dfd.setName(getAID());
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException e) {
            System.err.println(getLocalName() + " registration with DF unsucceeded. Reason: " + e.getMessage());
            doDelete();
        }
        containerList = new Vector();
        addBehaviour(new ReceiveControllerInstruction());
    }

    public class ReceiveControllerInstruction extends CyclicBehaviour {

        private int Message_Performative;
        private String Message_Content;
        private String SenderName;

        private HashMap<String, String> message;
        private HashMap<String, Vector> response;
       

        @Override
        public void action() {
            ACLMessage msg = receive();
            if (msg != null) {
                Message_Performative = msg.getPerformative();
                Message_Content = msg.getContent();
                SenderName = msg.getSender().getName();
                try {

                    switch (Message_Performative) {
                        case ACLMessage.REQUEST:
                            message = (HashMap<String, String>) msg.getContentObject();
                            urlAddr = message.get("urlname");
                            portNumber = Integer.parseInt(message.get("portNumber"));
                            numberofAgents = Integer.parseInt(message.get("numberofAgents"));
                            tickerInterval = Integer.parseInt(message.get("tickerInterval"));
                            globalAgentCount = Integer.parseInt(message.get("globalAgentCount"));
                            Object[] args = new Object[4];
                            //the Order of these parameters is extremely important
                            args[0] = this.getAgent().getAID();
                            args[1] = urlAddr;
                            args[2] = portNumber;
                            args[3] = tickerInterval;
                            
                            jade.core.Runtime runtime1 = jade.core.Runtime.instance();
                            ProfileImpl p = new ProfileImpl(false);
                            jade.wrapper.AgentContainer home = runtime1.createAgentContainer(p);

                            String agentType = "pawn";
                            try {
                                for (int i = 0; i < numberofAgents; i++) {
                                    AgentController t2 = home.createNewAgent(String.format("%s:%s", agentType, ++globalAgentCount + i), PawnAgent.class.getCanonicalName(), args);
                                    //String agentName = t2.getName();
                                    // agentID = getAID(t2.getName());
                                    // addressofPawn = ((PawnAgent) t2).here();
                                    t2.start();
                                }
                                
                               // globalAgentCount+= numberofAgents;
                                //containerList.add(home.getContainerName() + "@" + home.getName());

                                // now, send a response to the controller with your list of containers
                                response = new HashMap<>();
                                //Vector containers = getAgentsOfType("Container");
                                response.put("containers", containerList);
                                ACLMessage out_msg = new ACLMessage(ACLMessage.CONFIRM);
                                out_msg.addReceiver(new AID(SenderName));
                                out_msg.setContentObject(response);
                                send(out_msg);

                            } catch (IOException ex) {
                                Logger.getLogger(SupervisorAgent.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            break;
                        case ACLMessage.QUERY_IF:
                            //get the number of Pawn Agents in the current environment
                            Integer agentsPopulation = globalAgentCount;
                            ACLMessage out_msg = new ACLMessage(ACLMessage.INFORM);
                            out_msg.addReceiver(new AID(SenderName));
                            out_msg.setContent(agentsPopulation.toString());
                            send(out_msg);
                            break;
                        case ACLMessage.PROPOSE:
                            //here's a command to kill a container
                            // jade.core.Runtime runtime1 = jade.core.Runtime.instance();
                            //ProfileImpl p = new ProfileImpl(false);

                            String containerToKill = (String) (message.get("containerID"));
                            AID id = new AID(containerToKill);
                            Agent ag = new Agent();
                            break;
                    }
                } catch (Exception ex) {
                    Logger.getLogger(SupervisorAgent.class.getName()).log(Level.SEVERE, null, ex);
                }
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        }

        Integer getAgentsOfType(String type) {
            int count = 0;
            DFAgentDescription template = new DFAgentDescription();
            ServiceDescription sd = new ServiceDescription();
            sd.setType(type);
            template.addServices(sd);
            try {
                DFAgentDescription[] result = DFService.search(myAgent, template);
                count = result.length;
            } catch (FIPAException fe) {
                fe.printStackTrace();
            }
            return count;
        }
    }

    public class ClientAttack extends TickerBehaviour {

        private String Message_Performative;
        private String Message_Content;
        private String SenderName;
        private String MyPlan;
        private HashMap<String, String> hashmap;
        String hostAddr;
        int hostPort;

        int number, temp;

        public ClientAttack(Agent a, long interval, String _targetHost, int _targetPort) {
            super(a, interval);
            this.hostAddr = _targetHost;
            this.hostPort = _targetPort;
        }
        /*
         public ClientAttack(Agent a, int port, String host) {
         super(a, period);
         this.url = url;
         this.port = port;
         }

         */

        @Override
        protected void onTick() {

            Socket socket = null;
            try {
                socket = new Socket(hostAddr, hostPort);
                if (socket != null) {
                    System.out.println("Hi I just connected ");
                    socket.close();
                }

            } catch (IOException ex) {
                Logger.getLogger(SupervisorAgent.class.getName()).log(Level.SEVERE, null, ex);

            }
           
        }
    }
    
     protected void takeDown() {
        //clear away the GUI and clean up after yourself :p
        try {
            DFService.deregister(this);
            doDelete();
        } catch (FIPAException ex) {
            Logger.getLogger(ControllerAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

    
   
