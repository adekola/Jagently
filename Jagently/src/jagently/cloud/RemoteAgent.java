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
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
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
       
        RemoteAgent remoteAgentExecute =new RemoteAgent();

        }
            
             public void executeCommand(String command) {

		StringBuffer output = new StringBuffer();

		Process p;
		try {
			p = java.lang.Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = 
                           new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = "";			
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		//return output.toString();

	}
            
            
               //Profile pClient = new ProfileImpl(false);
            //pClient.setParameter(Profile.MAIN_HOST, "172.26.190.91");
            //pClient.setParameter(Profile.MAIN_PORT, "1099");
            /*try {

                Runtime rtClient = Runtime.instance();
                
                
                Profile pClient = new ProfileImpl(false);//("52.30.231.116", 1099, null, false);
                
                
                pClient.setParameter(ProfileImpl.PLATFORM_ID, "platform"+1); 
// ID range from 1 to 10 
                pClient.setParameter(Profile.MAIN_HOST, "52.30.231.116");
                pClient.setParameter(Profile.MAIN_PORT, "1099"); // available port 
                pClient.setParameter(Profile.LOCAL_HOST, "jicp://172.30.0.79");
                pClient.setParameter(Profile.LOCAL_PORT, "1099");
                rtClient.instance().setCloseVM(false);
                
                
//ContainerController mc = Runtime.instance().createMainContainer(pClient);

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
*/
    }

}