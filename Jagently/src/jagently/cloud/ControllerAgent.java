/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jagently.cloud;

import jade.core.AID;
import jade.core.Agent;
import jade.core.ProfileImpl;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adekola
 */
public class ControllerAgent extends GuiAgent {

    HashMap<String, Vector> supervisorsList;

    String targetHost;
    Integer portNumber;
    Integer agentCount = 0;

    public static final int SHUTDOWN_AGENT = 0;
    public static final int SAVE_TARGET_DETAILS = 1;
    public static final int CREATE_SUPERVISOR = 2;
    public static final int CREATE_PAWNS_ON_SUPERVISOR = 3;
    public static final int KILL_CONTAINER = 4;

    HashMap<String, Integer> globalAgentsCount;
    private int command = 0;
    //why transient and protected stuff?
    transient protected ControllerGUI myGui;  // The gui

    protected void setup() {
        globalAgentsCount = new HashMap<>();
        supervisorsList = new HashMap<>();
        myGui = new ControllerGUI(this);
        myGui.setVisible(true);

        //TODO
        // Attempt the creation of the target agent and then update list in GUI
        /**
         * Registration with the DF
         */
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("ControllerAgent");
        sd.setName(getName());
        sd.setOwnership("Jagently");
        sd.addOntologies("ControllerAgent");
        dfd.setName(getAID());
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException e) {
            System.err.println(getLocalName() + " registration with DF unsucceeded. Reason: " + e.getMessage());
            doDelete();
        }

        ReceiveSupervisorMessages rm = new ReceiveSupervisorMessages();
        addBehaviour(rm);
        addBehaviour(new FetchSupervisorsBehavior(this, 5000));
        //addBehaviour(new QuerySupervisorBehavior(this, 5000));
        System.out.println("Hey! I'm " + getAID().getLocalName());

    }

    @Override
    protected void onGuiEvent(GuiEvent ev) {
        //check for event types and respond accordingly
        command = ev.getType();
        switch (command) {
            case SHUTDOWN_AGENT:
                //do stuff to make the agent shut down properly
                break;
            case SAVE_TARGET_DETAILS:
                targetHost = (String) ev.getParameter(0);
                portNumber = (int) ev.getParameter(1);
                break;
            case CREATE_PAWNS_ON_SUPERVISOR:
                //get the selected supervisor and the rest of what's needed to 
                int numOfAgents = (int) ev.getParameter(0);
                long tickInterval = (long) ev.getParameter(1);
                AID supervisorAgentID = new AID((String) ev.getParameter(2));
                SendInstructionSupervisor instructSuperVisor = new SendInstructionSupervisor(supervisorAgentID, numOfAgents, tickInterval);
                addBehaviour(instructSuperVisor);
                //addBehaviour(new QuerySupervisorBehavior());
                break;
            case KILL_CONTAINER:
                //addBehaviour(new QuerySupervisorBehavior());
                break;
        }

    }

    String buildSupervisorID(String address, String agentName, String port) {
        return agentName + "@" + address + ":" + port + "/" + "JADE";
    }

    void updateSupervisorsList() {
        myGui.updateAgentList(supervisorsList);
    }

    public class SendInstructionSupervisor extends OneShotBehaviour {

        AID supervisorAgent;
        Integer agentsToCreate;
        Long tickInterval;

        public SendInstructionSupervisor(AID _supervisorAgent, int _agentsToCreate, long _tickInterval) {
            supervisorAgent = _supervisorAgent;
            agentsToCreate = _agentsToCreate;
            tickInterval = _tickInterval;
        }

        @Override
        public void action() {

            ACLMessage sendMessage = new ACLMessage(ACLMessage.REQUEST);
            //HashMap hashmap=new HashMap();

            //AID receiverSupervisor =new AID();
            //receiverSupervisor.setName("R@Platform2");
            //receiverSupervisor.addAddresses("http://arash-pc2:7778/acc");
            //receiverSupervisor.setName();
            //receiverSupervisor.setName(urlname);
            //receiverSupervisor.addAddresses(urlname);
            sendMessage.addReceiver(supervisorAgent);
            sendMessage.setLanguage("English");

            HashMap<String, String> hashmap = new HashMap<String, String>();
            hashmap.put("urlname", targetHost);
            hashmap.put("portNumber", portNumber.toString());
            hashmap.put("numberofAgents", agentsToCreate.toString());
            hashmap.put("tickerInterval", tickInterval.toString());
            agentCount+=agentsToCreate;
            updateAgentCount();
            try {
                sendMessage.setContentObject(hashmap);
            } catch (IOException ex) {
                Logger.getLogger(ControllerAgent.class.getName()).log(Level.SEVERE, null, ex);
            }
            send(sendMessage);
        }
    }

    public class FetchSupervisorsBehavior extends TickerBehaviour{

        public FetchSupervisorsBehavior(Agent a, long period) {
            super(a, period);
        }

        @Override
        protected void onTick() {
            
            DFAgentDescription template = new DFAgentDescription();
            ServiceDescription sd = new ServiceDescription();
            sd.setType("SupervisorAgent");
            template.addServices(sd);
            try {
                DFAgentDescription[] result = DFService.search(myAgent, template);
                for(int i=0;i< result.length; i++){
                    supervisorsList.put(result[i].getName().getName(), null);
                }
                updateSupervisorsList();
            } catch (FIPAException fe) {
                fe.printStackTrace();
            }
        }
        
    }
    public class QuerySupervisorBehavior extends TickerBehaviour {

        public QuerySupervisorBehavior(Agent a, long period) {
            super(a, period);
        }

        @Override
        protected void onTick() {
            for (String s : supervisorsList.keySet()) {
                ACLMessage msg = new ACLMessage(ACLMessage.QUERY_IF);
                msg.setContent("How many agents have you got in your domain?");
                msg.addReceiver(new AID(s));
                send(msg);
            }
        }
    }

    public class KillContainerBehavior extends OneShotBehaviour {

        AID supervisorAgent;
        String containerToKill;
        Integer agentsToCreate;

        public KillContainerBehavior(AID _supervisorAgent, String _containerToKill) {
            supervisorAgent = _supervisorAgent;
            containerToKill = _containerToKill;
        }

        @Override
        public void action() {

            ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);
            //HashMap hashmap=new HashMap();

            //AID receiverSupervisor =new AID();
            //receiverSupervisor.setName("R@Platform2");
            //receiverSupervisor.addAddresses("http://arash-pc2:7778/acc");
            //receiverSupervisor.setName();
            //receiverSupervisor.setName(urlname);
            //receiverSupervisor.addAddresses(urlname);
            message.addReceiver(supervisorAgent);
            message.setLanguage("English");

            HashMap<String, String> hashmap = new HashMap<String, String>();
            hashmap.put("containerID", containerToKill);
            try {
                message.setContentObject(hashmap);
            } catch (IOException ex) {
                Logger.getLogger(ControllerAgent.class.getName()).log(Level.SEVERE, null, ex);
            }
            send(message);
        }
    }

    public class ReceiveSupervisorMessages extends CyclicBehaviour {

        // Variable to Hold the content of the received Message
        private String Message_Performative;
        private String Message_Content;
        private String SenderName;
        private String MyPlan;

        private HashMap<String, Vector> message;

        public void action() {
            ACLMessage msg = receive();
            if (msg != null) {

                Message_Performative = msg.getPerformative(msg.getPerformative());
                Message_Content = msg.getContent();
                SenderName = msg.getSender().getName();

                switch (Message_Performative) {
                    case "CONFIRM": {
                        try {
                            //get the content of created containers  and do the needful
                            message = (HashMap) msg.getContentObject();
                            Vector v = (Vector) message.get("containers");
                            supervisorsList.put(SenderName, v);
                            updateSupervisorsList();
                        } catch (UnreadableException ex) {
                            Logger.getLogger(ControllerAgent.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }

                    break;
                    case "INFORM":
                        //get the content of the status update - no of agents, list of containers etc. and do the needful
                        int agentCount = Integer.parseInt(Message_Content);
                        globalAgentsCount = new HashMap<>();
                        globalAgentsCount.put(SenderName, agentCount);
                        updateAgentCount();
                        break;
                    case "AGREE":
                        //confirm death of said container, plus maybe the new number of agents from concerned supervisor

                        break;
                }
                //update the received message
                //TODO: refactor this to a more re-usable form
                //updateMessageReceived(Message_Content);
            } else {
                //what exactly does this block thingy do?
                block();
            }

        }
    }

    void updateAgentCount() {
        myGui.updateGlobalAgentCount(agentCount);
    }

    //we could make use of a Cyclic Behavior to get the feedback from the Supervisors regarding the success or not of Pawns creation
    protected void takeDown() {
        //clear away the GUI and clean up after yourself :p
        if (myGui != null) {
            myGui.setVisible(false);
            myGui.dispose();
        }
        try {
            DFService.deregister(this);
            doDelete();
        } catch (FIPAException ex) {
            Logger.getLogger(ControllerAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
