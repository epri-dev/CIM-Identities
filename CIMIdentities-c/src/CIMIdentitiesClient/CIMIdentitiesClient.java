/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CIMIdentitiesClient;

import ch.iec.tc57._2011.schema.message.HeaderType;
import ch.iec.tc57._2016.cimidentitiesmessage.CIMIdentitiesPayloadType;
import ch.iec.tc57._2016.cimidentitiesmessage.CIMIdentitiesResponseMessageType;
import ch.iec.tc57._2016.cimidentitiesqueries.CIMIdentitiesQueries;
import ch.iec.tc57._2016.cimidentitiesqueries.EndDeviceGroup;
import ch.iec.tc57._2016.cimidentitiesqueriesmessage.CIMIdentitiesQueriesRequestType;
import ch.iec.tc57._2016.cimidentitiesqueriesmessage.CIMIdentitiesQueriesResponseMessageType;
import ch.iec.tc57._2016.querycimidentities.QueryCIMIdentitiesFaultMessage;
import ch.iec.tc57._2016.sendcimidentities.FaultMessage;
import com.epri._2016.cimidentities.CIMIdentities;
import com.epri._2016.cimidentities.CIMIdentity;
import com.epri._2016.cimidentities.IdentifiedObject;
import com.epri._2016.cimidentities.Name;
import com.epri._2016.cimidentities.NameType;
import com.epri._2016.cimidentities.NameTypeAuthority;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author pdlo003
 */
public class CIMIdentitiesClient extends javax.swing.JFrame {
    CIMIdentitiesQueriesResponseMessageType response = new CIMIdentitiesQueriesResponseMessageType();
    ch.iec.tc57._2016.cimidentitiesqueriesmessage.CIMIdentitiesQueriesRequestMessageType message = new ch.iec.tc57._2016.cimidentitiesqueriesmessage.CIMIdentitiesQueriesRequestMessageType();
    int responseSize = 0;
    boolean uuidEntered = false;
    boolean enterPressed = false;
    int totalPage = 0;
    int curPage = 1;
    int numRows = 30;
    public DefaultTableModel model = new DefaultTableModel();
    public boolean ShowData = false; 
    String n_nameNew = "";
    String nt_nameNew = "";
    String nt_desNew = "";
    String nta_nameNew = "";
    String nta_desNew = "";
    String newUUID = "";
    /**
     * Creates new form CIMIdentitiesClient
     */
    public CIMIdentitiesClient() {
        createResponse();
        initComponents();
        setLocationRelativeTo(null);  //centers the application on screen
        createTable();
    }
    
    public void createResponse() {
        HeaderType header = new HeaderType();
        header.setNoun("CIMIdentities");
        header.setVerb("get");
        
        CIMIdentitiesQueriesRequestType request = new CIMIdentitiesQueriesRequestType();
        CIMIdentitiesQueries var = new CIMIdentitiesQueries();
        EndDeviceGroup edg = new EndDeviceGroup();
        edg.setMRID(null);  //can be null, '?', or '""' to receive all data, else set mRID
        
        
        message.setRequest(request);
        message.getRequest().setCIMIdentitiesQueries(var);
        message.getRequest().getCIMIdentitiesQueries().getEndDeviceGroup().add(0, edg);
        message.getRequest().getCIMIdentitiesQueries().getEndDeviceGroup();
      
        message.setHeader(header);
        
        try {
            response = queryCIMIdentities(message);
        } catch (QueryCIMIdentitiesFaultMessage ex) {
            JOptionPane.showMessageDialog( null, ex.getMessage() );
            Logger.getLogger(CIMIdentitiesClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (response.getPayload().getCIMIdentities().getCIMIdentity() != null)
        responseSize = response.getPayload().getCIMIdentities().getCIMIdentity().size();
    }
    
    public void createTable() {
        curPage = Integer.parseInt(curPageBox.getText());
        if (numRowsBox.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Number of rows field is empty\nPlease input desired value");
            return;
        }
        numRows = Integer.parseInt(numRowsBox.getText());
        if (!totalPageBox.getText().equals("")) {
            totalPage = Integer.parseInt(totalPageBox.getText());
            if (curPage > totalPage) {
                JOptionPane.showMessageDialog(null, "Invalid page number\n");
                curPage = totalPage;
                curPageBox.setText(Integer.toString(curPage));
                createTable();
            }
        }
        int index = 0;
        DefaultTableModel Model = new DefaultTableModel();
        Object[] colName = new Object[6];
        colName[0] = "mRID";
        colName[1] = "Name";
        colName[2] = "NT Name";
        colName[3] = "NT Des";
        colName[4] = "NTA Name";
        colName[5] = "NTA Des";
        Model.setColumnIdentifiers(colName);
        
        createResponse();
        
        Object[] rowData = new Object[6];
        for (int i = 0; i < responseSize; i++) {
            rowData[0] = response.getPayload().getCIMIdentities().getCIMIdentity().get(i).getIdentifiedObject().getMRID();
            rowData[1] = response.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getName();
            rowData[2] = response.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getNameType().getName();
            rowData[3] = response.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getNameType().getDescription();
            rowData[4] = response.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getNameType().getNameTypeAuthority().getName();
            rowData[5] = response.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getNameType().getNameTypeAuthority().getDescription();
            Model.addRow(rowData);
        }
        //now we add obtain the total number of rows:
           int totalRows = Model.getRowCount();
           //using that total number of rows, we computer the number of pages
           totalPage = (totalRows/numRows) + 1;
           totalPageBox.setText(Integer.toString(totalPage));
           
           //move index to starting value in current page
           index = ((curPage-1) * numRows);
           //now loop through the result set adding to new model
           DefaultTableModel newModel = new DefaultTableModel();
           newModel.setColumnIdentifiers(colName);
           if ((curPage != totalPage) && (curPage < totalPage) && (curPage >= 1)) {
                for (int i = numRows; i > 0; i--) {
                  rowData[0] = response.getPayload().getCIMIdentities().getCIMIdentity().get(index).getIdentifiedObject().getMRID();
                  rowData[1] = response.getPayload().getCIMIdentities().getCIMIdentity().get(index).getNames().get(0).getName();
                  rowData[2] = response.getPayload().getCIMIdentities().getCIMIdentity().get(index).getNames().get(0).getNameType().getName();
                  rowData[3] = response.getPayload().getCIMIdentities().getCIMIdentity().get(index).getNames().get(0).getNameType().getDescription();
                  rowData[4] = response.getPayload().getCIMIdentities().getCIMIdentity().get(index).getNames().get(0).getNameType().getNameTypeAuthority().getName();
                  rowData[5] = response.getPayload().getCIMIdentities().getCIMIdentity().get(index).getNames().get(0).getNameType().getNameTypeAuthority().getDescription();
                  newModel.addRow(rowData);
                  index++;
           }
           } else if (curPage == totalPage) { 
               for (int i = (totalRows % numRows); i > 0; i--) {
                  rowData[0] = response.getPayload().getCIMIdentities().getCIMIdentity().get(index).getIdentifiedObject().getMRID();
                  rowData[1] = response.getPayload().getCIMIdentities().getCIMIdentity().get(index).getNames().get(0).getName();
                  rowData[2] = response.getPayload().getCIMIdentities().getCIMIdentity().get(index).getNames().get(0).getNameType().getName();
                  rowData[3] = response.getPayload().getCIMIdentities().getCIMIdentity().get(index).getNames().get(0).getNameType().getDescription();
                  rowData[4] = response.getPayload().getCIMIdentities().getCIMIdentity().get(index).getNames().get(0).getNameType().getNameTypeAuthority().getName();
                  rowData[5] = response.getPayload().getCIMIdentities().getCIMIdentity().get(index).getNames().get(0).getNameType().getNameTypeAuthority().getDescription();
                  newModel.addRow(rowData);
                  index++;
               }
           }
           else {
               JOptionPane.showMessageDialog( null, "Invalid page number");
               return;
           }
           dataTable = new javax.swing.JTable();
           dataTable.setModel(newModel);
           jScrollPane3.setViewportView(dataTable);
    }
    
    public void populateComboBoxes() {
        
        createResponse();
        /* Populate Names box */
        LinkedHashSet<String> nameSet = new LinkedHashSet<String>();
        for (int i = 0; i < responseSize; i++) {
            nameSet.add(response.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getName());
        }
        for (String entry : nameSet) {
            n_name.addItem(entry);
        }
        /* Populate NameType names box */
        Set<String> nameTypeTSet = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < responseSize; i++) {
            nameTypeTSet.add(response.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getNameType().getName().trim());
        }
        for (String entry : nameTypeTSet) {
            nt_namecb.addItem(entry);
        }
        /* Populate NameType description box */
        Set<String> nameTypeDesTSet = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < responseSize; i++) {
            nameTypeDesTSet.add(response.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getNameType().getDescription().trim());
        }
        for (String entry : nameTypeDesTSet) {
            nt_descb.addItem(entry);
        }
        /* Populate NameTypeAuthority names box */
        Set<String> nameTypeAuthTSet = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < responseSize; i++) {
            nameTypeAuthTSet.add(response.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getNameType().getNameTypeAuthority().getName().trim());
        }
        for (String entry : nameTypeAuthTSet) {
            nta_namecb.addItem(entry);
        }
        /* Populate NameTypeAuthority descriptions box */
        Set<String> nameTypeAuthDesTSet = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < responseSize; i++) {
            nameTypeAuthDesTSet.add(response.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getNameType().getNameTypeAuthority().getDescription().trim());
        }
        for (String entry : nameTypeAuthDesTSet) {
            nta_descb.addItem(entry);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        n_nameBox = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        n_name = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        nt_nameBox = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        nt_namecb = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        nt_desBox = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        nt_descb = new javax.swing.JComboBox<>();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        nta_nameBox = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        nta_namecb = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        nta_desBox = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        nta_descb = new javax.swing.JComboBox<>();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        gen_uuidSel = new javax.swing.JRadioButton();
        enter_uuidSel = new javax.swing.JRadioButton();
        enter_uuidBox = new javax.swing.JFormattedTextField();
        Insert = new javax.swing.JRadioButton();
        Modify = new javax.swing.JRadioButton();
        Delete = new javax.swing.JRadioButton();
        enterButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        dataTable = new javax.swing.JTable();
        prev = new javax.swing.JButton();
        next = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        curPageBox = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        totalPageBox = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        numRowsBox = new javax.swing.JTextField();
        csvExport = new javax.swing.JButton();
        refreshButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTabbedPane1.setAutoscrolls(true);
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(588, 600));
        jTabbedPane1.setOpaque(true);

        jLabel1.setText("Name");

        jLabel2.setText("Select Name");

        LinkedHashSet<String> nameSet = new LinkedHashSet<String>();
        for (int i = 0; i < responseSize; i++) {
            nameSet.add(response.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getName());
        }
        for (String entry : nameSet) {
            n_name.addItem(entry);
        }
        n_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                n_nameActionPerformed(evt);
            }
        });

        jLabel3.setText("NameType Name");

        jLabel4.setText("Select Name");

        Set<String> nameTypeTSet = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < responseSize; i++) {
            nameTypeTSet.add(response.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getNameType().getName().trim());
        }
        for (String entry : nameTypeTSet) {
            nt_namecb.addItem(entry);
        }
        nt_namecb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nt_namecbActionPerformed(evt);
            }
        });

        jLabel5.setText("NameType Description");

        nt_desBox.setColumns(20);
        nt_desBox.setRows(5);
        jScrollPane1.setViewportView(nt_desBox);

        jLabel6.setText("Select Description");

        Set<String> nameTypeDesTSet = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < responseSize; i++) {
            nameTypeDesTSet.add(response.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getNameType().getDescription().trim());
        }
        for (String entry : nameTypeDesTSet) {
            nt_descb.addItem(entry);
        }
        nt_descb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nt_descbActionPerformed(evt);
            }
        });

        jLabel7.setText("NameTypeAuthority Name");

        nta_nameBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nta_nameBoxActionPerformed(evt);
            }
        });

        jLabel8.setText("Select Name");

        Set<String> nameTypeAuthTSet = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < responseSize; i++) {
            nameTypeAuthTSet.add(response.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getNameType().getNameTypeAuthority().getName().trim());
        }
        for (String entry : nameTypeAuthTSet) {
            nta_namecb.addItem(entry);
        }
        nta_namecb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nta_namecbActionPerformed(evt);
            }
        });

        jLabel9.setText("NameTypeAuthority Description");

        nta_desBox.setColumns(20);
        nta_desBox.setRows(5);
        jScrollPane2.setViewportView(nta_desBox);

        jLabel10.setText("Select Description");

        Set<String> nameTypeAuthDesTSet = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < responseSize; i++) {
            nameTypeAuthDesTSet.add(response.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getNameType().getNameTypeAuthority().getDescription().trim());
        }
        for (String entry : nameTypeAuthDesTSet) {
            nta_descb.addItem(entry);
        }
        nta_descb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nta_descbActionPerformed(evt);
            }
        });

        jLabel11.setText("UUID");

        buttonGroup1.add(gen_uuidSel);
        gen_uuidSel.setSelected(true);
        gen_uuidSel.setText("Randomly Generate");
        gen_uuidSel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gen_uuidSelActionPerformed(evt);
            }
        });

        buttonGroup1.add(enter_uuidSel);
        enter_uuidSel.setText("Enter Here:");
        enter_uuidSel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enter_uuidSelActionPerformed(evt);
            }
        });

        try {
            enter_uuidBox.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("AAAAAAAA-AAAA-AAAA-AAAA-AAAAAAAAAAAA")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        buttonGroup2.add(Insert);
        Insert.setSelected(true);
        Insert.setText("Insert");
        Insert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InsertActionPerformed(evt);
            }
        });

        buttonGroup2.add(Modify);
        Modify.setText("Modify");
        Modify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModifyActionPerformed(evt);
            }
        });

        buttonGroup2.add(Delete);
        Delete.setText("Delete");
        Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteActionPerformed(evt);
            }
        });

        enterButton.setText("Submit");
        enterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enterButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(enter_uuidSel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(enter_uuidBox, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(gen_uuidSel))
                .addContainerGap(165, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1)
                            .addComponent(jSeparator2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1)
                                            .addComponent(n_nameBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(75, 75, 75)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(n_name, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(nt_nameBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel5)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(35, 35, 35)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel4)
                                            .addComponent(nt_namecb, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(nt_descb, 0, 175, Short.MAX_VALUE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(nta_nameBox, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addComponent(jLabel9)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(39, 39, 39)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel10)
                                            .addComponent(jLabel8)
                                            .addComponent(nta_namecb, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(nta_descb, 0, 175, Short.MAX_VALUE))))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addComponent(jSeparator3)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Insert)
                            .addComponent(Modify)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(Delete)
                                .addGap(106, 106, 106)
                                .addComponent(enterButton, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(n_nameBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(n_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nt_nameBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nt_namecb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nt_descb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nta_nameBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nta_namecb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nta_descb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(gen_uuidSel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(enter_uuidSel)
                    .addComponent(enter_uuidBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Insert)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Modify)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Delete))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(enterButton)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Data Entry", jPanel1);

        jPanel2.setMinimumSize(new java.awt.Dimension(550, 600));

        dataTable.setModel(model);
        jScrollPane3.setViewportView(dataTable);

        prev.setText("Previous");
        prev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prevActionPerformed(evt);
            }
        });

        next.setText("Next");
        next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextActionPerformed(evt);
            }
        });

        jLabel12.setText("Page:");

        curPageBox.setText(Integer.toString(curPage));
        curPageBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                curPageBoxActionPerformed(evt);
            }
        });

        jLabel13.setText("of");

        totalPageBox.setEditable(false);
        totalPageBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalPageBoxActionPerformed(evt);
            }
        });

        jLabel14.setText("Rows per page:");

        numRowsBox.setText(Integer.toString(numRows));
        numRowsBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numRowsBoxActionPerformed(evt);
            }
        });

        csvExport.setText("Export to .csv");
        csvExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                csvExportActionPerformed(evt);
            }
        });

        refreshButton.setText("Refresh");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(prev)
                                        .addGap(69, 69, 69)
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(curPageBox, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(totalPageBox, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(refreshButton))
                                .addGap(69, 69, 69)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(csvExport)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(next)
                                        .addGap(0, 0, Short.MAX_VALUE))))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numRowsBox, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(prev)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(curPageBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13)
                        .addComponent(totalPageBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(next)))
                .addGap(42, 42, 42)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(numRowsBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(refreshButton)
                    .addComponent(csvExport))
                .addGap(41, 41, 41))
        );

        jTabbedPane1.addTab("View Database", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 704, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteActionPerformed
        n_nameBox.setText("");
        n_nameBox.setEditable(false);
        n_nameBox.setVisible(false);
        nt_nameBox.setText("");
        nt_nameBox.setEditable(false);
        nt_nameBox.setVisible(false);
        nt_desBox.setText("");
        nt_desBox.setEditable(false);
        nt_desBox.setVisible(false);
        nta_nameBox.setText("");
        nta_nameBox.setEditable(false);
        nta_nameBox.setVisible(false);
        nta_desBox.setText("");
        nta_desBox.setEditable(false);
        nta_desBox.setVisible(false);

        n_name.setSelectedItem("");
        nt_namecb.setSelectedItem("");
        nt_descb.setSelectedItem("");
        nta_namecb.setSelectedItem("");
        nta_descb.setSelectedItem("");

        enter_uuidSel.setSelected(true);
        gen_uuidSel.setEnabled(false);
        uuidEntered = true;
    }//GEN-LAST:event_DeleteActionPerformed

    private void ModifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModifyActionPerformed
        n_nameBox.setEditable(true);
        nt_nameBox.setEditable(true);
        nt_desBox.setEditable(true);
        nta_nameBox.setEditable(true);
        nta_desBox.setEditable(true);

        n_nameBox.setVisible(true);
        nt_nameBox.setVisible(true);
        nt_desBox.setVisible(true);
        nta_nameBox.setVisible(true);
        nta_desBox.setVisible(true);

        enter_uuidSel.setSelected(true);
        gen_uuidSel.setEnabled(false);
        uuidEntered = true;
    }//GEN-LAST:event_ModifyActionPerformed

    private void InsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InsertActionPerformed
        n_nameBox.setVisible(true);
        nt_nameBox.setVisible(true);
        nt_desBox.setVisible(true);
        nta_nameBox.setVisible(true);
        nta_desBox.setVisible(true);

        n_nameBox.setEditable(true);
        nt_nameBox.setEditable(true);
        nt_desBox.setEditable(true);
        nta_nameBox.setEditable(true);
        nta_desBox.setEditable(true);

        gen_uuidSel.setEnabled(true);
        gen_uuidSel.setSelected(true);
        uuidEntered = false;
    }//GEN-LAST:event_InsertActionPerformed

    private void enter_uuidSelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enter_uuidSelActionPerformed
        uuidEntered = true;
    }//GEN-LAST:event_enter_uuidSelActionPerformed

    private void gen_uuidSelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gen_uuidSelActionPerformed
        uuidEntered = false;
    }//GEN-LAST:event_gen_uuidSelActionPerformed

    private void nta_descbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nta_descbActionPerformed
        String nameSel = (String)nta_descb.getSelectedItem();
        nta_desBox.setText(nameSel);
    }//GEN-LAST:event_nta_descbActionPerformed

    private void nta_namecbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nta_namecbActionPerformed
        String nameSel = (String)nta_namecb.getSelectedItem();
        nta_nameBox.setText(nameSel);
    }//GEN-LAST:event_nta_namecbActionPerformed

    private void nta_nameBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nta_nameBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nta_nameBoxActionPerformed

    private void nt_descbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nt_descbActionPerformed
        String nameSel = (String)nt_descb.getSelectedItem();
        nt_desBox.setText(nameSel);
    }//GEN-LAST:event_nt_descbActionPerformed

    private void nt_namecbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nt_namecbActionPerformed
        String nameSel = (String)nt_namecb.getSelectedItem();
        nt_nameBox.setText(nameSel);
    }//GEN-LAST:event_nt_namecbActionPerformed

    private void n_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_n_nameActionPerformed
        String nameSel = (String)n_name.getSelectedItem();
        n_nameBox.setText(nameSel);
    }//GEN-LAST:event_n_nameActionPerformed

    private void curPageBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_curPageBoxActionPerformed
        try {
            
        Integer.parseInt(curPageBox.getText());
        
        if (Integer.parseInt(curPageBox.getText()) < 1) {
            throw new Exception("Value must be 1 or greater");
        }
        
        if (Integer.parseInt(curPageBox.getText()) > Integer.parseInt(totalPageBox.getText())) {
            throw new Exception("Current page cannot be greater than max page number\n");
        }
        
        createTable();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid value\n" + ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_curPageBoxActionPerformed

    private void csvExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_csvExportActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("/home/me/Documents"));
        int retrival = chooser.showSaveDialog(null);
        if (retrival == JFileChooser.APPROVE_OPTION) {
            
            try {
                FileWriter writer = new FileWriter(chooser.getSelectedFile()+".csv");
                createResponse();
                
                writer.write("mRID, Name, NameType Name, NameType Description, NameTypeAuthority Name, NameTypeAuthority Description\n");
                for (int i = 0; i < responseSize; i++) {
                    writer.write(response.getPayload().getCIMIdentities().getCIMIdentity().get(i).getIdentifiedObject().getMRID() + ", " +
                    response.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getName() + ", " +
                    response.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getNameType().getName() + ", " +
                    response.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getNameType().getDescription() + ", " +
                    response.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getNameType().getNameTypeAuthority().getName() + ", " +
                    response.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getNameType().getNameTypeAuthority().getDescription() + "\n");
                    
                }
                
                writer.flush();
                writer.close();
                
                
            } catch (IOException ex) {
                JOptionPane.showMessageDialog( null, ex.getMessage() );
                Logger.getLogger(CIMIdentitiesClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_csvExportActionPerformed

    private void prevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevActionPerformed
        if (numRowsBox.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Number of rows field is empty\nPlease input desired value");
            return;
        }
        
        if (Integer.parseInt(curPageBox.getText()) <= 1) {
            return;
        }
        
        if ( Integer.parseInt( curPageBox.getText() ) != 1 ) {
                curPage -= 1;
                curPageBox.setText(Integer.toString(curPage));
                createTable();
            }
    }//GEN-LAST:event_prevActionPerformed

    private void nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextActionPerformed
        if (numRowsBox.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Number of rows field is empty\nPlease input desired value");
            return;
        }
        if ( Integer.parseInt( curPageBox.getText() ) != Integer.parseInt( totalPageBox.getText()) ) {
                curPage += 1;
                curPageBox.setText(Integer.toString(curPage));
                createTable();
            }
    }//GEN-LAST:event_nextActionPerformed

    private void numRowsBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numRowsBoxActionPerformed

        try {
            Integer.parseInt(numRowsBox.getText());
            //curPageBox.setText("1");
            //curPage = 1;
            if (Integer.parseInt(numRowsBox.getText()) < 1) {
                throw new Exception("Cannot have less than 1 row per page\n");
            }
            createTable();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid format\n" + ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
    }//GEN-LAST:event_numRowsBoxActionPerformed

    private void totalPageBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalPageBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalPageBoxActionPerformed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        createTable();
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void enterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enterButtonActionPerformed
        n_nameNew = n_nameBox.getText();
        nt_nameNew = nt_nameBox.getText();
        nt_desNew = nt_desBox.getText();
        nta_nameNew = nta_nameBox.getText();
        nta_desNew = nta_desBox.getText();
        String mRID = enter_uuidBox.getText();
        ch.iec.tc57._2016.cimidentitiesmessage.CIMIdentitiesEventMessageType msg = new ch.iec.tc57._2016.cimidentitiesmessage.CIMIdentitiesEventMessageType();
 
        /* create payload object out of parsed data */
        CIMIdentitiesPayloadType payload = new CIMIdentitiesPayloadType();
        
        //create new CIMIdentities object
        CIMIdentities cimids = new CIMIdentities();
        CIMIdentity cimid = new CIMIdentity();
        payload.setCIMIdentities(cimids);
        
        //creates new ArrayList of CIMIdentities
        ArrayList<CIMIdentity> cim = (ArrayList<CIMIdentity>) payload.getCIMIdentities().getCIMIdentity();
        
        //set mRID
        IdentifiedObject value = new IdentifiedObject();
        value.setMRID(mRID);
        cimid.setIdentifiedObject(value);
        
        //set name
        Name name = new Name();
        name.setName(n_nameNew);
        if (Delete.isSelected()) name.setName("");
        if (name.getName() == null) name.setName("");
        cimid.getNames().add(name);
        
        //set NameType name/description
        NameType nameType = new NameType();
        nameType.setDescription(nt_desNew);
        if (Delete.isSelected()) nameType.setDescription("");
        if (nameType.getDescription() == null) nameType.setDescription("");
        nameType.setName(nt_nameNew);
        if (Delete.isSelected()) nameType.setName("");
        if (nameType.getName() == null) nameType.setName("");
        cimid.getNames().get(0).setNameType(nameType);
        
        //set NameTypeAuthority name/description
        NameTypeAuthority nameTypeAuth = new NameTypeAuthority();
        nameTypeAuth.setDescription(nta_desNew);
        if (Delete.isSelected()) nameTypeAuth.setDescription("");
        if (nameTypeAuth.getDescription() == null) nameTypeAuth.setDescription("");
        nameTypeAuth.setName(nta_nameNew);
        if (Delete.isSelected()) nameTypeAuth.setName("");
        if (nameTypeAuth.getName() == null) nameTypeAuth.setDescription("");
        cimid.getNames().get(0).getNameType().setNameTypeAuthority(nameTypeAuth);
        
        cim.add(cimid);
        
        msg.setPayload(payload);
        
        /* Insertion handling */
        if (Insert.isSelected()) {
            if (gen_uuidSel.isSelected()) {
                value.setMRID("");
                payload.getCIMIdentities().getCIMIdentity().get(0).setIdentifiedObject(value);
                msg.setPayload(payload);
            }
     
            HeaderType header = new HeaderType();
            header.setNoun("CIMIdentities");
            header.setVerb("create");
            msg.setHeader(header);
            try {
                createdCIMIdentitiesRequest(msg);
                JOptionPane.showMessageDialog( null, "Data inserted:\n\n"
                 + "\nName: " +  msg.getPayload().getCIMIdentities().getCIMIdentity().get(0).getNames().get(0).getName()
                 + "\nNameType Name: " +  msg.getPayload().getCIMIdentities().getCIMIdentity().get(0).getNames().get(0).getNameType().getName() 
                 + "\nNameType Description: " + msg.getPayload().getCIMIdentities().getCIMIdentity().get(0).getNames().get(0).getNameType().getDescription() 
                 + "\nNameTypeAuthority Name: " +  msg.getPayload().getCIMIdentities().getCIMIdentity().get(0).getNames().get(0).getNameType().getNameTypeAuthority().getName() 
                 + "\nNameTypeAuthority Description: " + msg.getPayload().getCIMIdentities().getCIMIdentity().get(0).getNames().get(0).getNameType().getNameTypeAuthority().getDescription());
            } catch (FaultMessage ex) {
                JOptionPane.showMessageDialog( null, ex.getMessage() );
                Logger.getLogger(CIMIdentitiesClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        /* deletion handling */
        if (Delete.isSelected() && enter_uuidSel.isSelected()) {
            uuidEntered = true;
            HeaderType header = new HeaderType();
            header.setNoun("CIMIdentities");
            header.setVerb("delete");
            msg.setHeader(header);
            
            /* query the server for the mRID being deleted */
            CIMIdentitiesQueriesRequestType request = new CIMIdentitiesQueriesRequestType();
            CIMIdentitiesQueries var = new CIMIdentitiesQueries();
            EndDeviceGroup edg = new EndDeviceGroup();
            edg.setMRID(mRID);  //can be null, '?', or '""' to receive all data, else set mRID
        
        
            message.setRequest(request);
            message.getRequest().setCIMIdentitiesQueries(var);
            message.getRequest().getCIMIdentitiesQueries().getEndDeviceGroup().add(0, edg);
            message.getRequest().getCIMIdentitiesQueries().getEndDeviceGroup();
            
            try {
                response = queryCIMIdentities(message);
            } catch (QueryCIMIdentitiesFaultMessage ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
            
            /* set outgoing deletion message to contain ALL data about the mRID */
            msg.getPayload().setCIMIdentities(response.getPayload().getCIMIdentities());
            
            try {
                
                if (response.getPayload().getCIMIdentities().getCIMIdentity().get(0).getIdentifiedObject().getMRID() == null) throw new IndexOutOfBoundsException("mRID doesn't exist");
                deletedCIMIdentitiesRequest(msg);
                JOptionPane.showMessageDialog( null,
                "Data deleted:\n\n"
                 + "mRID: " + response.getPayload().getCIMIdentities().getCIMIdentity().get(0).getIdentifiedObject().getMRID()
                 + "\nName: " +  response.getPayload().getCIMIdentities().getCIMIdentity().get(0).getNames().get(0).getName()
                 + "\nNameType Name: " +  response.getPayload().getCIMIdentities().getCIMIdentity().get(0).getNames().get(0).getNameType().getName() 
                 + "\nNameType Description: " + response.getPayload().getCIMIdentities().getCIMIdentity().get(0).getNames().get(0).getNameType().getDescription() 
                 + "\nNameTypeAuthority Name: " +  response.getPayload().getCIMIdentities().getCIMIdentity().get(0).getNames().get(0).getNameType().getNameTypeAuthority().getName() 
                 + "\nNameTypeAuthority Description: " + response.getPayload().getCIMIdentities().getCIMIdentity().get(0).getNames().get(0).getNameType().getNameTypeAuthority().getDescription());
                        
            } catch (FaultMessage ex) {
                JOptionPane.showMessageDialog( null, ex.getMessage() );
                Logger.getLogger(CIMIdentitiesClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IndexOutOfBoundsException err) {
                JOptionPane.showMessageDialog(null, "No deletion made.\nUUID does not currently exist in the database.\n");
            }
        }
        
        /* modification handling */
        if (Modify.isSelected()) {
            HeaderType header = new HeaderType();
            header.setNoun("CIMIdentities");
            header.setVerb("change");
            msg.setHeader(header);
            
            /* query the server for the mRID being modified */
            CIMIdentitiesQueriesRequestType request = new CIMIdentitiesQueriesRequestType();
            CIMIdentitiesQueries var = new CIMIdentitiesQueries();
            EndDeviceGroup edg = new EndDeviceGroup();
            edg.setMRID(mRID);  //can be null, '?', or '""' to receive all data, else set mRID
        
        
            message.setRequest(request);
            message.getRequest().setCIMIdentitiesQueries(var);
            message.getRequest().getCIMIdentitiesQueries().getEndDeviceGroup().add(0, edg);
            message.getRequest().getCIMIdentitiesQueries().getEndDeviceGroup();
            
            try {
                response = queryCIMIdentities(message);
            } catch (QueryCIMIdentitiesFaultMessage ex) {
                JOptionPane.showMessageDialog( null, ex.getMessage() );
                Logger.getLogger(CIMIdentitiesClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                if (response.getPayload().getCIMIdentities().getCIMIdentity().get(0).getIdentifiedObject().getMRID() == null) throw new IndexOutOfBoundsException("mRID doesn't exist");
                changedCIMIdentitiesRequest(msg);
                JOptionPane.showMessageDialog( null,
                "Data modified:\n\n"
                 + "mRID: " + msg.getPayload().getCIMIdentities().getCIMIdentity().get(0).getIdentifiedObject().getMRID()
                 + "\nPrevious Name: " + response.getPayload().getCIMIdentities().getCIMIdentity().get(0).getNames().get(0).getName()  
                 + "\nPrevious NameType Name: " + response.getPayload().getCIMIdentities().getCIMIdentity().get(0).getNames().get(0).getNameType().getName()   
                 + "\nPrevious NameType Description: " + response.getPayload().getCIMIdentities().getCIMIdentity().get(0).getNames().get(0).getNameType().getDescription()  
                 + "\nPrevoius NameTypeAuthority Name: " + response.getPayload().getCIMIdentities().getCIMIdentity().get(0).getNames().get(0).getNameType().getNameTypeAuthority().getName()    
                 + "\nPrevious NameTypeAuthority Description: " + response.getPayload().getCIMIdentities().getCIMIdentity().get(0).getNames().get(0).getNameType().getNameTypeAuthority().getDescription()
                        
                 + "\n\n\nNew Name: " +  msg.getPayload().getCIMIdentities().getCIMIdentity().get(0).getNames().get(0).getName()                 
                 + "\nNew NameType Name: " +  msg.getPayload().getCIMIdentities().getCIMIdentity().get(0).getNames().get(0).getNameType().getName()                       
                 + "\nNew NameType Description: " + msg.getPayload().getCIMIdentities().getCIMIdentity().get(0).getNames().get(0).getNameType().getDescription()                  
                 + "\nNew NameTypeAuthority Name: " +  msg.getPayload().getCIMIdentities().getCIMIdentity().get(0).getNames().get(0).getNameType().getNameTypeAuthority().getName()                  
                 + "\nNew NameTypeAuthority Description: " + msg.getPayload().getCIMIdentities().getCIMIdentity().get(0).getNames().get(0).getNameType().getNameTypeAuthority().getDescription());
                        
            } catch (FaultMessage ex) {
                JOptionPane.showMessageDialog( null, ex.getMessage() );
                Logger.getLogger(CIMIdentitiesClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IndexOutOfBoundsException err) {
                JOptionPane.showMessageDialog(null, "No modification made.\nUUID does not currently exist in the database.\n");
            }
        }


        n_nameBox.setText("");
        nt_nameBox.setText("");
        nt_desBox.setText("");
        nta_nameBox.setText("");
        nta_desBox.setText("");
        enter_uuidBox.setText("");
        
        n_name.removeAllItems();
        nt_namecb.removeAllItems();
        nt_descb.removeAllItems();
        nta_namecb.removeAllItems();
        nta_descb.removeAllItems();
        
        createTable();
        populateComboBoxes();
    }//GEN-LAST:event_enterButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        new Splash();
       
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog( null, ex.getMessage() );
            java.util.logging.Logger.getLogger(CIMIdentitiesClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            JOptionPane.showMessageDialog( null, ex.getMessage() );
            java.util.logging.Logger.getLogger(CIMIdentitiesClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            JOptionPane.showMessageDialog( null, ex.getMessage() );
            java.util.logging.Logger.getLogger(CIMIdentitiesClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            JOptionPane.showMessageDialog( null, ex.getMessage() );
            java.util.logging.Logger.getLogger(CIMIdentitiesClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CIMIdentitiesClient().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton Delete;
    private javax.swing.JRadioButton Insert;
    private javax.swing.JRadioButton Modify;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton csvExport;
    private javax.swing.JTextField curPageBox;
    private javax.swing.JTable dataTable;
    private javax.swing.JButton enterButton;
    private javax.swing.JFormattedTextField enter_uuidBox;
    private javax.swing.JRadioButton enter_uuidSel;
    private javax.swing.JRadioButton gen_uuidSel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JComboBox<String> n_name;
    private javax.swing.JTextField n_nameBox;
    private javax.swing.JButton next;
    private javax.swing.JTextArea nt_desBox;
    private javax.swing.JComboBox<String> nt_descb;
    private javax.swing.JTextField nt_nameBox;
    private javax.swing.JComboBox<String> nt_namecb;
    private javax.swing.JTextArea nta_desBox;
    private javax.swing.JComboBox<String> nta_descb;
    private javax.swing.JTextField nta_nameBox;
    private javax.swing.JComboBox<String> nta_namecb;
    private javax.swing.JTextField numRowsBox;
    private javax.swing.JButton prev;
    private javax.swing.JButton refreshButton;
    private javax.swing.JTextField totalPageBox;
    // End of variables declaration//GEN-END:variables

    private static CIMIdentitiesResponseMessageType changedCIMIdentitiesRequest(ch.iec.tc57._2016.cimidentitiesmessage.CIMIdentitiesEventMessageType changedCIMIdentitiesEventMessage) throws FaultMessage {
        ch.iec.tc57._2016.sendcimidentities.SendCIMIdentities service = new ch.iec.tc57._2016.sendcimidentities.SendCIMIdentities();
        ch.iec.tc57._2016.sendcimidentities.CIMIdentitiesPort port = service.getCIMIdentitiesPort();
        return port.changedCIMIdentitiesRequest(changedCIMIdentitiesEventMessage);
    }

    private static CIMIdentitiesResponseMessageType createdCIMIdentitiesRequest(ch.iec.tc57._2016.cimidentitiesmessage.CIMIdentitiesEventMessageType createdCIMIdentitiesEventMessage) throws FaultMessage {
        ch.iec.tc57._2016.sendcimidentities.SendCIMIdentities service = new ch.iec.tc57._2016.sendcimidentities.SendCIMIdentities();
        ch.iec.tc57._2016.sendcimidentities.CIMIdentitiesPort port = service.getCIMIdentitiesPort();
        return port.createdCIMIdentitiesRequest(createdCIMIdentitiesEventMessage);
    }

    private static CIMIdentitiesResponseMessageType deletedCIMIdentitiesRequest(ch.iec.tc57._2016.cimidentitiesmessage.CIMIdentitiesEventMessageType deletedCIMIdentitiesEventMessage) throws FaultMessage {
        ch.iec.tc57._2016.sendcimidentities.SendCIMIdentities service = new ch.iec.tc57._2016.sendcimidentities.SendCIMIdentities();
        ch.iec.tc57._2016.sendcimidentities.CIMIdentitiesPort port = service.getCIMIdentitiesPort();
        return port.deletedCIMIdentitiesRequest(deletedCIMIdentitiesEventMessage);
    }

    private static CIMIdentitiesQueriesResponseMessageType queryCIMIdentities(ch.iec.tc57._2016.cimidentitiesqueriesmessage.CIMIdentitiesQueriesRequestMessageType queryCIMIdentitiesRequestMessage) throws QueryCIMIdentitiesFaultMessage {
        ch.iec.tc57._2016.querycimidentities.QueryCIMIdentities service = new ch.iec.tc57._2016.querycimidentities.QueryCIMIdentities();
        ch.iec.tc57._2016.querycimidentities.QueryCIMIdentitiesPort port = service.getQueryCIMIdentitiesPort();
        return port.queryCIMIdentities(queryCIMIdentitiesRequestMessage);
    }
    
}
