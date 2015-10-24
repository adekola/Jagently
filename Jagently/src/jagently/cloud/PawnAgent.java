/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jagently.cloud;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ameerah
 */
public class PawnAgent extends Agent {

    private AID supervisor;

    String hostAddr;
    int hostPort, tickerInterval;

    //sorry, No constructors for agents :p
    /*
    public PawnAgent(String host, int port, int _tickerInterval) {
        host = hostAddr;
        hostPort = port;
        tickerInterval = _tickerInterval;
    }
    */

    public void setup() {
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("PawnAgent");
        sd.setName(getName());
        sd.setOwnership("Jagently");
        sd.addOntologies("PawnAgent");
        dfd.setName(getAID());
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException e) {
            System.err.println(getLocalName() + " registration with DF unsucceeded. Reason: " + e.getMessage());
            doDelete();
        }
        Object[] args = getArguments();
        supervisor = (AID) args[0];
        hostAddr = (String) args[1];
        hostPort = (int) args[2];
        tickerInterval = (int) args[3];
        addBehaviour(new ClientAttack(this, tickerInterval));

    }

    public class ClientAttack extends TickerBehaviour {

        private String Message_Performative;
        private String Message_Content;
        private String SenderName;
        private String MyPlan;
        private HashMap<String, String> hashmap;

        int number, temp;

        public ClientAttack(Agent a, long interval){
            super(a, interval);
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
                    System.out.println("Hi there I am attacking ");
                }

            } catch (IOException ex) {
                Logger.getLogger(PawnAgent.class.getName()).log(Level.SEVERE, null, ex);

            }
            //For the purposes of hacking these pieces together, let's just have the attack happen once the PawnAgent is created
            /*
             ACLMessage msg = receive();
             if (msg != null) {

             Message_Performative = msg.getPerformative(msg.getPerformative());
             Message_Content = msg.getContent();
             try {
             hashmap = (HashMap) msg.getContentObject();

             if (hashmap.isEmpty()) {

             System.err.print("Freak Send me what to do !!!");

             } else {
             String url = hashmap.get("urlname").toString();
             String portNumber = hashmap.get("portNumber").toString();
             port = Integer.parseInt(portNumber);
             }
             } catch (UnreadableException ex) {
             Logger.getLogger(PawnAgent.class.getName()).log(Level.SEVERE, null, ex);
             }
             SenderName = msg.getSender().getName();

             System.out.println("***I Received a Message***" + "\n"
             + "The Sender Name is:" + SenderName + "\n"
             + "The Content of the Message is::> " + Message_Content + "\n"
             + "::: And Performative is:: " + Message_Performative);
             //Reply to the Message
             if (Message_Performative.equals("REQUEST") && Message_Content.equals("Attack")) {
             */

        }
    }
}
