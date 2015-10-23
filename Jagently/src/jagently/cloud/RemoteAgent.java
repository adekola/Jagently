/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jagently.cloud;

import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.behaviours.CyclicBehaviour;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.core.Runtime;
import jade.core.behaviours.OneShotBehaviour;
import jade.wrapper.StaleProxyException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ameerah
 */
public class RemoteAgent extends Agent {

    AgentController controller;
    int agentCount = 0;

    public void setup() {

        addBehaviour(new CreateRemoteAgent());

    }

    public class CreateRemoteAgent extends OneShotBehaviour {

        public void action() {
               //Profile pClient = new ProfileImpl(false);
            //pClient.setParameter(Profile.MAIN_HOST, "172.26.190.91");
            //pClient.setParameter(Profile.MAIN_PORT, "1099");
            try {

                Runtime rtClient = Runtime.instance();

                Profile pClient = new ProfileImpl("52.30.231.116", 1099, null, false);

                pClient.setParameter(Profile.CONTAINER_NAME, "newContainer");

                AgentContainer ccAg = (AgentContainer) rtClient.createAgentContainer(pClient);

                AgentController t2 = ccAg.createNewAgent(String.format("%s:%s", "SupervisorAgent", agentCount++), SupervisorAgent.class.getName(), null);

                t2.start();

//controller=ccAg.createNewAgent("PawnAgent",PawnAgent.class.getName(),Object[] args);
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            } catch (StaleProxyException ex) {
                Logger.getLogger(RemoteAgent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
