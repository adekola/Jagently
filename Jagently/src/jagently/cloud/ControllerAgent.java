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
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
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

    Vector supervisorsList;

    String targetHost;
    Integer portNumber;
    Integer agentCount = 0;
    
    public static final int SHUTDOWN_AGENT = 0;
    public static final int SAVE_TARGET_DETAILS = 1;
    public static final int CREATE_SUPERVISOR = 2;
    public static final int CREATE_PAWNS_ON_SUPERVISOR = 3;

    private int command = 0;
    //why transient and protected stuff?
    transient protected ControllerGUI myGui;  // The gui

    protected void setup() {
        supervisorsList = new Vector();
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

        System.out.println("Hey! I'm " + getAID().getLocalName());

    }

    @Override
    protected void onGuiEvent(GuiEvent ev) {
        //check for event types and respond accordingly
        command = ev.getType();
        switch (command) {
            case SAVE_TARGET_DETAILS:
                targetHost = (String) ev.getParameter(0);
                portNumber = (int) ev.getParameter(1);
                break;
            case CREATE_SUPERVISOR:
                //do all the supervisor creation thingy, with params
                //... and now, do your thingy
                try {
                    Object[] args = new Object[2];
                    //we'll fix in here the part of creating agents remotely which you're working to fix...
                    String supervisorAddy = (String) ev.getParameter(0);
                    jade.core.Runtime runtime1 = jade.core.Runtime.instance();
                    ProfileImpl p = new ProfileImpl(false);
                    jade.wrapper.AgentContainer home = runtime1.createAgentContainer(p);
                    AgentController t2 = null;
                    try {
                        t2 = home.createNewAgent(String.format("%s:%s", "SupervisorAgent", agentCount++), SupervisorAgent.class.getName(), args);
                        supervisorsList.add(t2.getName());
                        t2.start();
                        updateSupervisorsList();
                    } catch (StaleProxyException ex) {
                        Logger.getLogger(ControllerAgent.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (Exception ex) {
                    System.out.println("Problem creating new agent");
                    ex.printStackTrace();
                }
                break;
            case CREATE_PAWNS_ON_SUPERVISOR:
                //get the selected supervisor and the rest of what's needed to 
                int numOfAgents = (int) ev.getParameter(0);
                long tickInterval = (long) ev.getParameter(1);
                AID supervisorAgentID = new AID ((String)ev.getParameter(2));
                SendInstructionSupervisor instructSuperVisor = new SendInstructionSupervisor(supervisorAgentID, numOfAgents, tickInterval);
                addBehaviour(instructSuperVisor);
        }

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
            try {
                sendMessage.setContentObject(hashmap);
            } catch (IOException ex) {
                Logger.getLogger(ControllerAgent.class.getName()).log(Level.SEVERE, null, ex);
            }
            send(sendMessage);
        }
    }

    //we could make use of a Cyclic Behavior to get the feedback from the Supervisors regarding the success or not of Pawns creation
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
            Logger.getLogger(ControllerAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
