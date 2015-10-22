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
import jagently.ClientAgent;
import jagently.ClientAgentGUI;
import jagently.FactsOfLifeAgent;
import jagently.QuotesAgent;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adekola
 */
public class ControllerAgent extends GuiAgent {
    
    int agentCount = 0;

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
