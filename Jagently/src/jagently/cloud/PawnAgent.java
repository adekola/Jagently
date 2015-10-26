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
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

 /*
     * @author ameerah
     */
    
    public class PawnAgent extends Agent {

        private AID supervisor;

        String hostAddr;
        int hostPort, tickerInterval;

    //sorry, No constructors for agents :p
         
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

            public ClientAttack(Agent a, long interval) {
                super(a, interval);
            }
            

             
            @Override
            protected void onTick() {

                Socket socket = null;
                try {
                    socket = new Socket(hostAddr, hostPort);
                    if (socket != null) {
                        System.out.println("Hi I just connected ");
                        socket.close();
                    }

                } catch (IOException ex) {
                    Logger.getLogger(PawnAgent.class.getName()).log(Level.SEVERE, null, ex);

                }
            
        }
        }
    }
