/*
 * To change this license header+ choose License Headers in Project Properties.
 * To change this template file+ choose Tools | Templates
 * and open the template in the editor.
 */
package jagently;

import jade.core.*;
import jade.gui.*;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adekola
 */
public class ClientAgent extends GuiAgent {

    public static final int TARGET_AGENT_SELECTED = 1;
    public static final int SHUTDOWN_AGENT = 2;
    public static final int SEND_MESSAGE = 3;
    public static final int CREATE_AGENT = 4;
    int agentCount = 0;

    Vector agentList;
    private int command = 0;
    //why transient and protected stuff?
    transient protected ClientAgentGUI myGui;  // The gui

    
    protected void setup() {

        agentList = new Vector();
        myGui = new ClientAgentGUI(this);
        myGui.setVisible(true);

        //TODO
        // Attempt the creation of the target agent and then update list in GUI
        /**
         * Registration with the DF
         */
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("MasterAgent");
        sd.setName(getName());
        sd.setOwnership("Jagently");
        sd.addOntologies("MasterAgent");
        dfd.setName(getAID());
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException e) {
            System.err.println(getLocalName() + " registration with DF unsucceeded. Reason: " + e.getMessage());
            doDelete();
        }

        System.out.println("My name is " + getAID().getLocalName());
        //periodically check for fol-agents

        /*
         addBehaviour(new TickerBehaviour(this, 10000) {
         protected void onTick() {
         // Update the list of target agents
         DFAgentDescription template = new DFAgentDescription();
         ServiceDescription sd = new ServiceDescription();
         sd.setType("FactsOfLifeAgent");

         //TODO
         //check for agents from different platforms and machines
         template.addServices(sd);
         try {
         DFAgentDescription[] result = DFService.search(myAgent, template);
         System.out.println("Found the following matching agents:");
         agentList = new AID[result.length];
         for (int i = 0; i < result.length; ++i) {
         agentList[i] = result[i].getName();
         }
         updateAgentList();
         } catch (FIPAException fe) {
         fe.printStackTrace();
         }

         // Perform the request
         }
         });
         */
    }

    @Override
    protected void onGuiEvent(GuiEvent ev) {
        //check for event types and respond accordingly
        command = ev.getType();
        switch (command) {
            case SHUTDOWN_AGENT:
                takeDown();
                break;
            case SEND_MESSAGE:
                //get message parameters from gui 
                AID agent = new AID((String)ev.getParameter(0));
                String msg = (String) ev.getParameter(1);
                //set up the message to be sent
                SendMessage sm = new SendMessage(msg, agent);
                ReceiveMessage rm = new ReceiveMessage();
                addBehaviour(sm);
                addBehaviour(rm);
                //do your message sending ish
                break;
            case CREATE_AGENT:
                try {
                    Object[] args = new Object[2];
                    jade.core.Runtime runtime1 = jade.core.Runtime.instance();
                    ProfileImpl p = new ProfileImpl(false);
                    jade.wrapper.AgentContainer home = runtime1.createAgentContainer(p);
                    String agentType = (String) ev.getParameter(0);
                    int numAgents = (int) ev.getParameter(1);
                    String className = mapAgentClassName(agentType);
                    try {
                        Random r = new Random();
                        for (int i = 0; i < numAgents; i++) {
                            AgentController t2 = home.createNewAgent(String.format("%s:%s", agentType, agentCount++), className, args);
                            agentList.add(t2.getName());
                            t2.start();
                        }
                        updateAgentList();
                    } catch (StaleProxyException ex) {
                        Logger.getLogger(ClientAgent.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (Exception ex) {
                    System.out.println("Problem creating new agent");
                    ex.printStackTrace();
                }
        }
    }

    String mapAgentClassName(String name) {
        String clsName = "";
        switch (name.toLowerCase()) {
            case "factsagent":
                clsName = FactsOfLifeAgent.class.getName();
                break;
            case "quotesagent":
                clsName = QuotesAgent.class.getName();
        }
        return clsName;
    }

    void updateAgentList() {
        myGui.updateAgentList(agentList);
    }

    void updateMessageReceived(String msg) {
        myGui.updateReceivedMessage(msg);

    }

    public class SendMessage extends OneShotBehaviour {

        private MessageTemplate mt; // The template to receive replies

        private String content;
        private AID agent;

        public SendMessage(String msgContent, AID agent) {
            this.agent = agent;
            this.content = msgContent;
        }

        public void action() {
            //send message to the designated agent
            ACLMessage inform = new ACLMessage(ACLMessage.REQUEST);
            inform.addReceiver(agent);
            inform.setContent(content);
            inform.setConversationId("fol-request");
            inform.setReplyWith("inform" + System.currentTimeMillis()); // Unique value
            myAgent.send(inform);
            // Prepare the template to get proposals
            mt = MessageTemplate.and(MessageTemplate.MatchConversationId("fol-request"),
                    MessageTemplate.MatchInReplyTo(inform.getReplyWith()));

        }
    }

    public class ReceiveMessage extends CyclicBehaviour {

        // Variable to Hold the content of the received Message
        private String Message_Performative;
        private String Message_Content;
        private String SenderName;
        private String MyPlan;

        public void action() {
            ACLMessage msg = receive();
            if (msg != null) {

                Message_Performative = msg.getPerformative(msg.getPerformative());
                Message_Content = msg.getContent();
                SenderName = msg.getSender().getLocalName();
                //update the received message
                //TODO: refactor this to a more re-usable form
                updateMessageReceived(Message_Content);
                System.out.println("message Received ***" + "\n"
                        + "The Sender Name is:" + SenderName + "\n"
                        + "The Content of the Message is::> " + Message_Content + "\n");
            } else {
                //what exactly does this block thingy do?
                block();
            }

        }
    }

    protected void takeDown() {
        //clear away the GUI and clean up after yourself :p
        if (myGui != null) {
            myGui.setVisible(false);
            myGui.dispose();
        }
        doDelete();
        try {
            DFService.deregister(this);
        } catch (FIPAException ex) {
            Logger.getLogger(ClientAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
