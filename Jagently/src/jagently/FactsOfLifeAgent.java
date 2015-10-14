/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jagently;

import jade.core.*;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adekola
 */
public class FactsOfLifeAgent extends Agent {

    protected void setup() {
        System.out.println("FactsOfLifeAgent " + getAID().getName() + " is ready");

        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("FactsOfLifeAgent");
        sd.setName(getName());
        sd.setOwnership("Jagently");
        sd.addOntologies("FactsOfLifeAgent");
        dfd.setName(getAID());
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException e) {
            System.err.println(getLocalName() + " registration with DF unsucceeded. Reason: " + e.getMessage());
            doDelete();
        }

        System.out.println("Hello World! My name is " + getAID().getLocalName());
        ReceiveMessage rm = new ReceiveMessage();
        addBehaviour(rm);

    }

    public class ReceiveMessage extends CyclicBehaviour {

        // Variable to Hold the content of the received Message
        private String Message_Performative;
        private String Message_Content;
        private String SenderName;
        private String MyPlan;


        public void action() {
            //Receive a Message
            ACLMessage msg = receive();
            if(msg != null) {

                Message_Performative = msg.getPerformative(msg.getPerformative());
                Message_Content = msg.getContent();
                SenderName = msg.getSender().getName();


                System.out.println("***I Received a Message***" +"\n"+
                            "The Sender Name is:"+ SenderName+"\n"+
                            "The Content of the Message is::> " + Message_Content+"\n"+
                            "::: And Performative is:: " + Message_Performative);
                //Reply to the Message
                if (Message_Performative.equals("REQUEST")&& Message_Content.equals("Tell me some interesting fact")) {

                    ACLMessage out_msg = new ACLMessage(ACLMessage.INFORM);
                    out_msg.addReceiver(new AID(SenderName));
                    out_msg.setContent((new FactsOfLife()).getFact());
                    send(out_msg);
                    System.out.println("****I Replied to::> " + SenderName+"***");
                    System.out.println("The Content of My Reply is:" + out_msg.getContent());
                    System.out.println("--------------------------------------");

                }

            }

        }
    }
    
    protected void takeDown() {
        doDelete();
    }
}
