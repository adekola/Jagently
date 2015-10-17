/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jagently;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jagently.RandomQuotes.QuoteTypes;

/**
 *
 * @author ameerah
 */
public class QuotesAgent extends Agent {

    public void setup() {
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("QuotesAgent");
        sd.setOwnership("Jagently");
        sd.addOntologies("ReceiverAgent");
        sd.setName(getLocalName() + "Life-Advice");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
            doDelete();
        }


        /* try {
         DFService.deregister(this);
         }
         catch (FIPAException fe) {
         fe.printStackTrace();
         }*/
        ReceiveMessage receiveMessage = new ReceiveMessage();
        addBehaviour(receiveMessage);

    }

    public class ReceiveMessage extends CyclicBehaviour {

        private String Message_Performative;
        private String Message_Content;
        private String SenderName;

        @Override
        public void action() {

            ACLMessage msg = myAgent.receive();
            if (msg != null) {

                Message_Performative = msg.getPerformative(msg.getPerformative());
                Message_Content = msg.getContent();
                SenderName = msg.getSender().getName();

                System.out.println("***I Received a Message***" + "\n"
                        + "The Sender Name is:" + SenderName + "\n"
                        + "The Content of the Message is::> " + Message_Content + "\n"
                        + "::: And Performative is:: " + Message_Performative);

                /*Object[] args = getArguments();
                 if (args != null) {
                 for (int i = 0; i < args.length; ++i) {
                 System.out.println("- "+args[i]);
                 }
                 }
                 String s = args[0].toString();
                 int x =1;
                 if(s.equals("Life Advice"))
                 //Message_Content=s;*/
                if (Message_Performative.equals("REQUEST")) {

                    ACLMessage out_msg = new ACLMessage(ACLMessage.INFORM);
                    out_msg.addReceiver(new AID(SenderName));
                    out_msg.setLanguage("English");
                    QuoteTypes t = mapQuoteRequest(Message_Content);
                    String replyMessage = RandomQuotes.getRandomQuotes(t);
                    out_msg.setContent(replyMessage);
                    send(out_msg);
                    System.out.println("****I Replied to::> " + SenderName + "***");
                    System.out.println("The Content of My Reply is:" + out_msg.getContent());

                }

            }
        }

        QuoteTypes mapQuoteRequest(String msg) {
            QuoteTypes type = null;
            switch (msg.toLowerCase()) {
                case "life":
                    type = QuoteTypes.LIFE;
                    break;
                case "love":
                    type = QuoteTypes.LOVE;
                    break;
                case "marriage":
                    type = QuoteTypes.MARRIAGE;
                    break;
                default:
                    type = QuoteTypes.LIFE;
                    break;
            }

            return type;
        }

    }
}
