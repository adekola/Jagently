/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jagently;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;

import jade.core.*;
import jade.gui.GuiEvent;
import java.util.Vector;

/**
 *
 * @author adekola
 */
public class ClientAgentGUI extends javax.swing.JFrame {

    String selectedAgentType;

    private ClientAgent agent;
    Vector agentList;

    /**
     * Creates new form AgentGUI
     */
    public ClientAgentGUI(ClientAgent ca) {
        this.agent = ca;
        setTitle("Client Agent - " + agent.getLocalName());
        initComponents();

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                shutDown();
            }
        });
    }

    void shutDown() {
// -----------------  Control the closing of this gui

        int rep = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?",
                agent.getLocalName(),
                JOptionPane.YES_NO_CANCEL_OPTION);
        if (rep == JOptionPane.YES_OPTION) {
            GuiEvent ge = new GuiEvent(this, ClientAgent.SHUTDOWN_AGENT);
            agent.postGuiEvent(ge);
        }
    }

    void updateAgentList(Vector agentList) {
        this.agentList = agentList;
        agentListBox.setListData(agentList);
        agentListBox.setSelectedIndex(agentList.size() - 1);
    }

    void updateReceivedMessage(String msg) {
        textAreaReceivedMessage.setText(msg);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        textAreaSentMsg = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        textAreaReceivedMessage = new javax.swing.JTextArea();
        txtTopic = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        agentListBox = new javax.swing.JList();
        buttonSendMsg = new javax.swing.JButton();
        cmbAgentType = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        txtNumberOfAgents = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        textAreaSentMsg.setEditable(false);
        textAreaSentMsg.setColumns(20);
        textAreaSentMsg.setRows(5);
        jScrollPane1.setViewportView(textAreaSentMsg);

        jLabel1.setText("Select Agent to Communicate with");

        jLabel2.setText("Tell me about");

        jLabel3.setText("Sent Message");

        jLabel4.setText("Received Message");

        textAreaReceivedMessage.setEditable(false);
        textAreaReceivedMessage.setColumns(20);
        textAreaReceivedMessage.setLineWrap(true);
        textAreaReceivedMessage.setRows(5);
        jScrollPane2.setViewportView(textAreaReceivedMessage);

        jScrollPane3.setViewportView(agentListBox);

        buttonSendMsg.setText("Send Message");
        buttonSendMsg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSendMsgActionPerformed(evt);
            }
        });

        cmbAgentType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "FactsAgent", "QuotesAgent" }));

        jLabel5.setText("Agents Creation");

        jButton1.setText("Create");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtNumberOfAgents.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumberOfAgentsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(27, 27, 27))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                            .addComponent(jScrollPane3)
                            .addComponent(txtTopic)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel5)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(buttonSendMsg, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(txtNumberOfAgents, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmbAgentType, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 178, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNumberOfAgents)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cmbAgentType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton1)))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTopic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonSendMsg)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonSendMsgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSendMsgActionPerformed
        // TODO add your handling code here:
        //get the selected agent, and post it to the CA to send a message to it

        //ensure a message is composed to be sent
        if (txtTopic.getText().isEmpty()) {
            int rep = JOptionPane.showConfirmDialog(this, "Please Enter a message to send",
                    agent.getLocalName(),
                    JOptionPane.OK_OPTION);
        } else {
            String selectedAgent = (String)agentList.get(agentListBox.getSelectedIndex());
            GuiEvent ev = new GuiEvent(this, ClientAgent.SEND_MESSAGE);
            ev.addParameter(selectedAgent);
            ev.addParameter(txtTopic.getText());
            agent.postGuiEvent(ev);
            textAreaSentMsg.setText("Tell me about " + txtTopic.getText());
        }

    }//GEN-LAST:event_buttonSendMsgActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        //fetch the type of agent to be created and send to the 
        selectedAgentType = cmbAgentType.getSelectedItem().toString();
        if (!txtNumberOfAgents.getText().isEmpty()) {
            int numberOfAgents = Integer.parseInt(txtNumberOfAgents.getText());
            //create the required number of agents of selected type
            GuiEvent ev = new GuiEvent(this, ClientAgent.CREATE_AGENT);
            ev.addParameter(selectedAgentType);
            ev.addParameter(numberOfAgents);
            agent.postGuiEvent(ev);
        } else {
            int rep = JOptionPane.showConfirmDialog(this, "Please enter a valid number of agents to create",
                    agent.getLocalName(),
                    JOptionPane.OK_OPTION);
        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtNumberOfAgentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumberOfAgentsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumberOfAgentsActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList agentListBox;
    private javax.swing.JButton buttonSendMsg;
    private javax.swing.JComboBox cmbAgentType;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea textAreaReceivedMessage;
    private javax.swing.JTextArea textAreaSentMsg;
    private javax.swing.JTextField txtNumberOfAgents;
    private javax.swing.JTextField txtTopic;
    // End of variables declaration//GEN-END:variables
}
