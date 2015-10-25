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

    String targetHost;
    Integer portNumber;
    Integer agentCount = 0;

    public static final int SHUTDOWN_AGENT = 0;
    public static final int SAVE_TARGET_DETAILS = 1;
    public static final int CREATE_AGENTS = 3;

    private int command = 0;
    //why transient and protected stuff?
    transient protected ControllerGUI myGui;  // The gui

    protected void setup() {
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
            case CREATE_AGENTS:
                //do all the supervisor creation thingy, with params
                //... and now, do your thingy
                try {
                    Object[] args = new Object[3];
                    //we'll fix in here the part of creating agents remotely which you're working to fix...
                    String hostToCreateOn = (String) ev.getParameter(0);
                    int numberOfAgents = (int) ev.getParameter(1);
                    String interval = (String) ev.getParameter(2);
                    String targetHost = (String) ev.getParameter(3);
                    String targetPort = (String) ev.getParameter(4);

                    /*
                     if (hostToCreateOn.equals("localhost") | hostToCreateOn.equals("127.0.0.1")) {

                     jade.core.Runtime runtime1 = jade.core.Runtime.instance();
                     ProfileImpl p = new ProfileImpl(false);
                     jade.wrapper.AgentContainer home = runtime1.createAgentContainer(p);
                     AgentController t2 = null;
                     args[0] = targetHost;
                     args[1] = targetPort;
                     args[2] = interval;
                     try {
                     for (int i=0; i<=numberOfAgents; i++){
                     t2 = home.createNewAgent(String.format("%s:%s", "WorkerAgent", agentCount++), WorkerAgent.class.getName(), args);
                     t2.start();
                     }
                     } catch (StaleProxyException ex) {
                     Logger.getLogger(ControllerAgent.class.getName()).log(Level.SEVERE, null, ex);
                     }

                     }
                     else {
                            
                     */
                    String port = "1099";
                    String nameOfAgent = "Worker";

                    RemoteAgentCreator remoteAgentCall = new RemoteAgentCreator();

                    StringBuilder agentList = new StringBuilder();
                    String agentCreationOption = String.format("jagently.cloud.WorkerAgent\"(%s, %s ,%s)\"", targetHost, targetPort, interval);
                    
                    for (int i = 0; i <= numberOfAgents; i++) {
                        //String buildCommand = "java" + " " + "jade.Boot" + " " + "-container" + " " + "-container-name" + " " + "Container" + " " + "-host" + " " + hostToCreateOn + " " + "-port" + " " + port + " " + nameOfAgent + ":" + String.format("jagently.cloud.WorkerAgent(%s, %s, %s)", targetHost, targetPort, interval);
                        String agentid = String.format("%s-%s", nameOfAgent, i); //Worker-0,1,2...
                        String agentOption = String.format("%s:%s ", agentid, agentCreationOption); //Worker-0:jagently.cloud.WorkerAgent(x, y, z)

                        agentList.append(agentOption);
                    }

                    String buildCommand = "java" + " " + "jade.Boot" + " " + "-container" + " " + "-container-name" + " " + "Container" + " " + "-host" + " " + hostToCreateOn + " " + "-port" + " " + port + " " + agentList.toString();

                    
                    remoteAgentCall.executeCommand(buildCommand);
                        //building the command in command line;

                        //put a check to see if the command was successful
                    // System.out.println("Before socket connection");
                    //do some kind of magic to compose what the AID of the freshly minted supervisor will be
                } catch (Exception ex) {
                    System.out.println("Problem creating new agent");
                    ex.printStackTrace();
                }
                break;
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
