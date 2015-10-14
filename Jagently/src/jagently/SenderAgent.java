/*
 * To change this license header+ choose License Headers in Project Properties.
 * To change this template file+ choose Tools | Templates
 * and open the template in the editor.
 */
package jagently;

import jade.core.*;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adekola
 */
public class SenderAgent extends Agent {

    protected void setup() {

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
        SendMessage sm = new SendMessage();
        ReceiveMessage rm = new ReceiveMessage();
        addBehaviour(sm);
        addBehaviour(rm);
    }

    public class SendMessage extends OneShotBehaviour {

        private MessageTemplate mt; // The template to receive replies

        public void action() {
            //get the receivers that are of a certain type

            DFAgentDescription template = new DFAgentDescription();
            ServiceDescription sd = new ServiceDescription();
            sd.setType("FactsOfLifeAgent");
            template.addServices(sd);
            try {
                DFAgentDescription[] result = DFService.search(myAgent, template);
                //check that result yielded a matching agent
                if (result.length > 0) {
                    System.out.println("Found the following FactsOfLife agents:");
                    for (int i = 0; i < result.length; ++i) {
                        System.out.println(result[i].getName());
                    }
                    //send message to the discovered agent
                    ACLMessage inform = new ACLMessage(ACLMessage.REQUEST);
                    
                    inform.addReceiver(result[0].getName());
                    
                    inform.setContent("Tell me some interesting fact");
                    
                    inform.setConversationId("fol-request");
                    inform.setReplyWith("inform" + System.currentTimeMillis()); // Unique value
                    myAgent.send(inform);
                    // Prepare the template to get proposals
                    mt = MessageTemplate.and(MessageTemplate.MatchConversationId("fol-request"),
                            MessageTemplate.MatchInReplyTo(inform.getReplyWith()));

                }

            } catch (FIPAException fe) {
                fe.printStackTrace();
            }
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
                System.out.println("message Received ***" +"\n"+
                        "The Sender Name is:"+ SenderName+"\n"+
                        "The Content of the Message is::> " + Message_Content + "\n");
                System.out.println("--------------------------------------------");
            }

        }
    }

    protected void takeDown() {
        doDelete();
    }
}
