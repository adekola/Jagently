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
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import java.io.IOException;
import java.net.InetAddress;
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
    Vector agentList;
    //InetAddress address;
    int numberofAgents, tickerInterval, portNumber;
    Location addressofPawn;

    public void setup() {
        agentList = new Vector();
        addBehaviour(new ReceiveControllerInstruction());
    }

    public class ReceiveControllerInstruction extends CyclicBehaviour {

        private String Message_Performative;
        private String Message_Content;
        private String SenderName;

        
        private HashMap<String, String> message;
        int agentCount = 0;

        @Override
        public void action() {
            ACLMessage msg = receive();
            if (msg != null) {
                Message_Performative = msg.getPerformative(msg.getPerformative());
                Message_Content = msg.getContent();
                try {
                    message = (HashMap) msg.getContentObject();
                    if (message.isEmpty()) {

                        System.err.print("Freak Send me what to do !!!");

                    } else {
                        urlAddr = message.get("urlname").toString();
                        portNumber = Integer.parseInt(message.get("portNumber"));
                        numberofAgents = Integer.parseInt(message.get("numberofAgents"));
                        tickerInterval = Integer.parseInt(message.get("tickerInterval"));
                        Object[] args = new Object[4];
                        //the Order of these parameters is extremely important
                        args[0] = this.getAgent().getAID();
                        args[1] = urlAddr;
                        args[2] = portNumber;
                        args[3] = tickerInterval;
                        jade.core.Runtime runtime1 = jade.core.Runtime.instance();
                        ProfileImpl p = new ProfileImpl(false);
                        jade.wrapper.AgentContainer home = runtime1.createAgentContainer(p);
                        // String agentType = (String) ev.getParameter(0);
                        //  int numAgents = (int) ev.getParameter(1);
                        String agentType = "pawn";
                        try {
                            for (int i = 0; i < numberofAgents; i++) {
                                AgentController t2 = home.createNewAgent(String.format("%s:%s", agentType, agentCount++), PawnAgent.class.getName(), args);
                                agentList.add(t2.getName());
                                String agentName = t2.getName();
                               // agentID = getAID(t2.getName());
                                //addressofPawn = ((PawnAgent) t2).here();

                                t2.start();
                            }
                            // updateAgentList();
                        } catch (StaleProxyException ex) {
                            Logger.getLogger(SupervisorAgent.class.getName()).log(Level.SEVERE, null, ex);

                        }
                    }

                    // SenderName = msg.getSender().getName();
                } catch (UnreadableException ex) {
                    Logger.getLogger(SupervisorAgent.class.getName()).log(Level.SEVERE, null, ex);
                }
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        }
    }
    
    //For Hack's sake, let us skip this bit of code for a minute...and just get the PawnAgents to start attacking as soon as they're created

    /*
    public class DispatchPawnInstruction extends CyclicBehaviour {

        private String Message_Performative;
        private String Message_Content;
        private String SenderName;
        private String MyPlan;
        private Object object;

        @Override
        public void action() {
            ACLMessage dispatchMessage = new ACLMessage(ACLMessage.REQUEST);

            try {
                ACLMessage sendMessage = new ACLMessage(ACLMessage.REQUEST);
                HashMap<String, String> hashmap = new HashMap<String, String>();
                hashmap.put("urlname", urlAddr);
                hashmap.put("portNumber", portNumber);
                // hashmap.put("", url);

                sendMessage.setContentObject(hashmap);
                sendMessage.setContent("Attack");
                AID receiverPawn = new AID(agentName, AID.ISGUID);

                receiverPawn.setName(agentName);
                receiverPawn.addAddresses(addressofPawn.getAddress());

                dispatchMessage.addReceiver(receiverPawn);
                send(sendMessage);

            } catch (IOException ex) {
                Logger.getLogger(ControllerAgent.class.getName()).log(Level.SEVERE, null, ex);
            }

            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    */
}
