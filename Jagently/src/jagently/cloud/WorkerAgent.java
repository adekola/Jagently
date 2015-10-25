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

public class WorkerAgent extends Agent {

        private AID supervisor;

        String hostAddr;
        int hostPort, tickerInterval;

         
        public void setup() {
            DFAgentDescription dfd = new DFAgentDescription();
            ServiceDescription sd = new ServiceDescription();
            sd.setType("WorkerAgent");
            sd.setName(getName());
            sd.setOwnership("Jagently");
            sd.addOntologies("WorkerAgent");
            dfd.setName(getAID());
            dfd.addServices(sd);
            try {
                DFService.register(this, dfd);
            } catch (FIPAException e) {
                System.err.println(getLocalName() + " registration with DF unsucceeded. Reason: " + e.getMessage());
                doDelete();
            }
            Object[] args = getArguments();
            hostAddr = (String) args[0];
            hostPort =  Integer.parseInt((String)args[1]);
            tickerInterval = Integer.parseInt((String)args[2]);
            addBehaviour(new ClientAttack(this, tickerInterval));

        }

        public class ClientAttack extends TickerBehaviour {

            private String Message_Performative;
            private String Message_Content;
            private String SenderName;
            private String MyPlan;
            private HashMap<String, String> hashmap;

            int number, temp;

            public ClientAttack(Agent a, int interval) {
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
                    Logger.getLogger(WorkerAgent.class.getName()).log(Level.SEVERE, null, ex);
                }
            
        }
        }
}