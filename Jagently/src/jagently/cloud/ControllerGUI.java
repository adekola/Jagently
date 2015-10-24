/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jagently.cloud;

import jade.gui.GuiEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author adekola
 */
public class ControllerGUI extends javax.swing.JFrame {

    private ControllerAgent agent;

    
    HashMap<String, Vector> agentList;

    /**
     * Creates new form ControllerGUI
     */
    public ControllerGUI(ControllerAgent ca) {
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
            GuiEvent ge = new GuiEvent(this, ControllerAgent.SHUTDOWN_AGENT);
            agent.postGuiEvent(ge);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtTargetHost = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtNoOfAgents = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtTargetPort = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTickerInterval = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        lblTargetHostDetails = new javax.swing.JLabel();
        lblAgentPopulation = new javax.swing.JLabel();
        lblTargetHostDetails2 = new javax.swing.JLabel();
        lblTargetHostDetails3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listboxSupervisorList = new javax.swing.JList();
        jLabel5 = new javax.swing.JLabel();
        btnCreateSupervisor = new javax.swing.JButton();
        txtSupervisorAddress = new javax.swing.JTextField();
        lblSelectedSupervisor = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listboxContainerList = new javax.swing.JList();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Create");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Target Host ");

        txtNoOfAgents.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoOfAgentsActionPerformed(evt);
            }
        });

        jLabel2.setText("No of Agents");

        jLabel6.setText("Port");

        jLabel3.setText("Ticker Interval");

        txtTickerInterval.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTickerIntervalActionPerformed(evt);
            }
        });

        jButton2.setText("Save");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel4.setText("System Stats");

        lblTargetHostDetails.setText("{}");

        lblAgentPopulation.setText("{}");

        lblTargetHostDetails2.setText("Target:");

        lblTargetHostDetails3.setText("Agent Population");

        listboxSupervisorList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listboxSupervisorListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(listboxSupervisorList);

        jLabel5.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel5.setText("Supervisor Agents");

        btnCreateSupervisor.setText("Create Supervisor at:");
        btnCreateSupervisor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateSupervisorActionPerformed(evt);
            }
        });

        lblSelectedSupervisor.setText("{}");

        jScrollPane2.setViewportView(listboxContainerList);

        jButton3.setText("Kill Container");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
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
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(txtNoOfAgents, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtTickerInterval)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblTargetHostDetails2)
                                .addGap(18, 18, 18)
                                .addComponent(lblTargetHostDetails)
                                .addGap(188, 188, 188)
                                .addComponent(lblTargetHostDetails3, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblAgentPopulation)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblSelectedSupervisor)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton3))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(txtTargetHost))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtTargetPort, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel6)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnCreateSupervisor, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtSupervisorAddress)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTargetHost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTargetPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCreateSupervisor)
                    .addComponent(txtSupervisorAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblSelectedSupervisor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNoOfAgents, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTickerInterval, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTargetHostDetails)
                    .addComponent(lblAgentPopulation)
                    .addComponent(lblTargetHostDetails2)
                    .addComponent(lblTargetHostDetails3))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNoOfAgentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoOfAgentsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNoOfAgentsActionPerformed

    private void txtTickerIntervalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTickerIntervalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTickerIntervalActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        int port;
        String host;
        if (!txtTargetPort.getText().isEmpty() & !txtTargetHost.getText().isEmpty()) {
            if (tryParseInt(txtTargetPort.getText())) {
                port = Integer.parseInt(txtTargetPort.getText());
                host = txtTargetHost.getText();
                GuiEvent ev = new GuiEvent(this, ControllerAgent.SAVE_TARGET_DETAILS);
                ev.addParameter(host);
                ev.addParameter(port);
                agent.postGuiEvent(ev);
                lblTargetHostDetails.setText(txtTargetHost.getText());
            }
        } else {
            JOptionPane.showConfirmDialog(this, "Enter valid values for Target Host & Port ",
                    agent.getLocalName(),
                    JOptionPane.OK_OPTION);

        }
    }//GEN-LAST:event_jButton2ActionPerformed

    public void updateGlobalAgentCount(Integer count){
        lblAgentPopulation.setText(count.toString());
    }
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int noOfAgents;
        long tickerInterval;

        String selectedAgent = (String)listboxSupervisorList.getSelectedValue();
        //String selectedAgent = agentList.get(agentIndex);
        if (!txtNoOfAgents.getText().isEmpty() | !txtTickerInterval.getText().isEmpty()) {
            if (tryParseInt(txtNoOfAgents.getText()) | tryParseInt(txtTickerInterval.getText())) {
                noOfAgents = Integer.parseInt(txtNoOfAgents.getText());
                tickerInterval = Long.parseLong(txtTickerInterval.getText());
                GuiEvent ev = new GuiEvent(this, ControllerAgent.CREATE_PAWNS_ON_SUPERVISOR);
                ev.addParameter(noOfAgents);
                ev.addParameter(tickerInterval);
                ev.addParameter(selectedAgent);
                agent.postGuiEvent(ev);
            }
        } else {
            JOptionPane.showConfirmDialog(this, "Enter valid values for Target Host & Port ",
                    agent.getLocalName(),
                    JOptionPane.OK_OPTION);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnCreateSupervisorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateSupervisorActionPerformed
        // TODO add your handling code here:
        //let's first create supervisor at a specific address.
        // if(!txtSupervisorAddress.getText().isEmpty()){
        String supervisorAddy = txtSupervisorAddress.getText();
        GuiEvent ev = new GuiEvent(this, ControllerAgent.CREATE_SUPERVISOR);
        ev.addParameter(supervisorAddy);
        agent.postGuiEvent(ev);
        //}
    }//GEN-LAST:event_btnCreateSupervisorActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void listboxSupervisorListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listboxSupervisorListValueChanged
        // TODO add your handling code here:
        Vector empty = new Vector();
        String selectedAgent = (String)listboxSupervisorList.getSelectedValue();
        lblSelectedSupervisor.setText(selectedAgent);
        Vector v = agentList.get(selectedAgent);
        if(v != null)
            listboxContainerList.setListData(v);
        else
            listboxContainerList.setListData(empty);
    }//GEN-LAST:event_listboxSupervisorListValueChanged

    private boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    void updateAgentList(HashMap<String, Vector> agentList) {
        this.agentList = agentList;
        listboxSupervisorList.setListData(agentList.keySet().toArray());
        listboxSupervisorList.setSelectedIndex(agentList.size() - 1);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCreateSupervisor;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAgentPopulation;
    private javax.swing.JLabel lblSelectedSupervisor;
    private javax.swing.JLabel lblTargetHostDetails;
    private javax.swing.JLabel lblTargetHostDetails2;
    private javax.swing.JLabel lblTargetHostDetails3;
    private javax.swing.JList listboxContainerList;
    private javax.swing.JList listboxSupervisorList;
    private javax.swing.JTextField txtNoOfAgents;
    private javax.swing.JTextField txtSupervisorAddress;
    private javax.swing.JTextField txtTargetHost;
    private javax.swing.JTextField txtTargetPort;
    private javax.swing.JTextField txtTickerInterval;
    // End of variables declaration//GEN-END:variables
}
