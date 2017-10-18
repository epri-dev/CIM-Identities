/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataentryform;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author pdlo003
 */
public class DataEntryForm extends javax.swing.JFrame {
    
    String host = "jdbc:postgresql://localhost:5432/CIMIdentity";
    String uName = "postgres";
    String uPass; 
    ArrayList<String> forCombo = new ArrayList<>();
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
     * Creates new form dataEntry
     */
    public DataEntryForm(String Pass) {
        uPass = Pass;
        
        initComponents();
        this.setLocationRelativeTo(null);
        
    }
        
    public void connect(String column, String table){
        try{
            Connection con = DriverManager.getConnection(host, uName, uPass);           
            Statement stmt = con.createStatement();

            String getData = "SELECT DISTINCT " + column + " FROM " + table +
                    " ORDER BY " + column;

            ResultSet dataSet = stmt.executeQuery(getData);
            forCombo.add("");   //makes sure all comboboxes start with blank line
            while(dataSet.next()) {
                forCombo.add(dataSet.getString(column).trim());
            }
        
        
        Set<String> toRetain = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        toRetain.addAll(forCombo);
        Set<String> set = new LinkedHashSet<String>(forCombo);
        set.retainAll(new LinkedHashSet<String>(toRetain));
        forCombo = new ArrayList<String>(set);
            
        } catch(SQLException err){
            System.out.println(err.getMessage());
        }
    }
    
    public void popComboBoxes(String column, String table, javax.swing.JComboBox<String> comboBox) {
        connect(column, table);
        
        for(int i = 0; i < forCombo.size(); i ++)
            comboBox.addItem(forCombo.get(i));
        forCombo.clear();
        
    }
    
    public void createTable() {
        
        
        try {
        
        curPage = Integer.parseInt(curPageBox.getText());
        numRows = Integer.parseInt(numRowsBox.getText());
        DefaultTableModel Model = new DefaultTableModel();
        Object[] colName = new Object[6];
        colName[0] = "mRID";
        colName[1] = "Name";
        colName[2] = "NT Name";
        colName[3] = "NT Des";
        colName[4] = "NTA Name";
        colName[5] = "NTA Des";
        Model.setColumnIdentifiers(colName);
        
        Connection con = DriverManager.getConnection(host, uName, uPass);           
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.FETCH_UNKNOWN);
        String query = "SELECT *" +
                          "FROM public.\"NameType\" as nt, public.\"Name\" as n, " +
                          "public.\"NameTypeAuthority\" as nta " +
                          "WHERE n.n_pkey = nt.nt_pkey AND " +
                          "nt.nt_pkey = nta.nta_pkey " +
                          "ORDER BY n.n_name ASC;";
        
        ResultSet rs = stmt.executeQuery(query);
        Object[] rowData = new Object[6];
           while (rs.next()) {
           rowData[0] = rs.getString("n_pkey");
           rowData[1] = rs.getString("n_name");
           rowData[2] = rs.getString("nt_name");
           rowData[3] = rs.getString("nt_description");
           rowData[4] = rs.getString("nta_name");
           rowData[5] = rs.getString("nta_description");
           Model.addRow(rowData);
           }
           //now we add obtain the total number of rows:
           int totalRows = Model.getRowCount();
           //using that total number of rows, we computer the number of pages
           totalPage = (totalRows/numRows) + 1;
           totalPageBox.setText(Integer.toString(totalPage));
           
           //move index to starting value in current page
           rs.absolute( ((curPage-1)*numRows)+1 );
           //now loop through the result set adding to new model
           DefaultTableModel newModel = new DefaultTableModel();
           newModel.setColumnIdentifiers(colName);
           if ((curPage != totalPage) && (curPage < totalPage) && (curPage >= 1)) {
                for (int i = numRows; i > 0; i--) {
                  rowData[0] = rs.getString("n_pkey");
                  rowData[1] = rs.getString("n_name");
                  rowData[2] = rs.getString("nt_name");
                  rowData[3] = rs.getString("nt_description");
                  rowData[4] = rs.getString("nta_name");
                  rowData[5] = rs.getString("nta_description");
                  newModel.addRow(rowData);
                  rs.next();
                }
           }
           else if (curPage == totalPage) { 
               for (int i = (totalRows % numRows); i > 0; i--) {
                  rowData[0] = rs.getString("n_pkey");
                  rowData[1] = rs.getString("n_name");
                  rowData[2] = rs.getString("nt_name");
                  rowData[3] = rs.getString("nt_description");
                  rowData[4] = rs.getString("nta_name");
                  rowData[5] = rs.getString("nta_description");
                  newModel.addRow(rowData);
                  rs.next();
               }
           }
           else {
               JOptionPane.showMessageDialog( null, "Invalid page number");
               return;
           }
           dataTable = new javax.swing.JTable();
           dataTable.setModel(newModel);
           jScrollPane1.setViewportView(dataTable);
        } catch(SQLException err){
            System.out.println(err.getMessage());
        }
    }

    public void dbDelete(String mRID) {
        try {
        Connection con = DriverManager.getConnection(host, uName, uPass);
        Statement stmt = con.createStatement();
        
        //to-do:  Add error checking if "Randomly Generate" is selected
        
        String delUUID = "DELETE FROM public.\"Identity\" WHERE id_pkey = '" +
                mRID + "'";
        
        stmt.executeUpdate(delUUID);
        
        stmt.close();
        con.close();
        
        JOptionPane.showMessageDialog( null, "Data deleted" );
        
        } catch(SQLException err){
            JOptionPane.showMessageDialog( null, err.getMessage());
        }
    }
    
    public void dbModify(String n_nameNew, String nt_nameNew, String nt_desNew, String nta_nameNew, String nta_desNew, String mRID) {
        try {
            Connection con = DriverManager.getConnection(host, uName, uPass);
            Statement stmt = con.createStatement();
            
            //to-do:  Add error checking if "Randomly Generate" is selected
            
            String updateName = "UPDATE public.\"Name\" SET n_name = '" +
                                n_nameNew +"' WHERE n_pkey = '" + mRID + "'";
            String updateNT =   "UPDATE public.\"NameType\" SET nt_name = '" +
                                nt_nameNew + "', nt_description = '" + nt_desNew +
                                "' WHERE nt_pkey = '" + mRID + "'";
            String updateNTA =  "UPDATE public.\"NameTypeAuthority\" SET nta_name = '" +
                                nta_nameNew + "', nta_description = '" + nta_desNew +
                                "' WHERE nta_pkey = '" + mRID + "'";
            
            stmt.executeUpdate(updateName);
            stmt.executeUpdate(updateNT);
            stmt.executeUpdate(updateNTA);
            
            stmt.close();
            con.close();
            
            JOptionPane.showMessageDialog( null, "Data modified" );
        }
        catch(SQLException err){
            JOptionPane.showMessageDialog( null, err.getMessage());
        }  
            
    }
    //Send text entered to SQL database
    public void dbInsert(String n_nameNew, String nt_nameNew, String nt_desNew, String nta_nameNew, String nta_desNew){
       
        try {
            Connection con = DriverManager.getConnection(host, uName, uPass);
            Statement stmt = con.createStatement();
        
            if(uuidEntered ==false){
            
                String genUUID = "INSERT INTO public.\"Identity\" (id_pkey, entry)"
                        + "VALUES (DEFAULT, DEFAULT)";
                stmt.executeUpdate(genUUID);
                String getUUID = "SELECT id.id_pkey FROM \"Identity\" id "
                        + "ORDER BY id.entry desc LIMIT 1";
                ResultSet rs = stmt.executeQuery(getUUID);
                rs.next();
                newUUID = rs.getString("id_pkey");
            
                System.out.println("Generate UUID selected: " + newUUID);
            }
        
            else if (uuidEntered == true){
                newUUID = enter_uuidBox.getText();
                String entUUID = "INSERT INTO public.\"Identity\"(id_pkey, entry)"
                        + "VALUES ('" + newUUID + "', DEFAULT)";
                stmt.executeUpdate(entUUID);
            }
        
            String insertIDObj = "INSERT INTO public.\"IdentifiedObject\"(io_pkey)"
                    + "VALUES('" + newUUID + "')";
            String insertName = "INSERT INTO public.\"Name\"(n_pkey, n_name)"
                    + "VALUES('" + newUUID + "', '" + n_nameNew + "')";
            String insertNT = "INSERT INTO public.\"NameType\"(nt_pkey, nt_description, nt_name)"
                    + "VALUES ('" + newUUID + "', '" + nt_desNew + "', '" + nt_nameNew + "')";
            String insertNTA = "INSERT INTO public.\"NameTypeAuthority\"(nta_pkey, nta_name, nta_description)"
                    + "VALUES ('" + newUUID + "', '" + nta_nameNew + "', '" + nta_desNew + "')";
        
            stmt.executeUpdate(insertIDObj);
            stmt.executeUpdate(insertName);
            stmt.executeUpdate(insertNT);
            stmt.executeUpdate(insertNTA);
            
            stmt.close();
            con.close();
            
            JOptionPane.showMessageDialog( null, "Data inserted" );
        }
        catch(SQLException err){
            JOptionPane.showMessageDialog( null, err.getMessage());
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
        jLabel3 = new javax.swing.JLabel();
        n_nameBox = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        n_name = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        nt_nameBox = new javax.swing.JTextField();
        nt_namecb = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        nt_desBox = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        nt_descb = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        nta_nameBox = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        nta_namecb = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        nta_desBox = new javax.swing.JTextArea();
        jLabel14 = new javax.swing.JLabel();
        nta_descb = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel15 = new javax.swing.JLabel();
        gen_uuidSel = new javax.swing.JRadioButton();
        enter_uuidSel = new javax.swing.JRadioButton();
        Insert = new javax.swing.JRadioButton();
        Modify = new javax.swing.JRadioButton();
        Delete = new javax.swing.JRadioButton();
        enterButton = new javax.swing.JButton();
        enter_uuidBox = new javax.swing.JFormattedTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        dataTable = new javax.swing.JTable();
        prev = new javax.swing.JButton();
        next = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        curPageBox = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        totalPageBox = new javax.swing.JTextField();
        showDataBox = new javax.swing.JCheckBox();
        jLabel16 = new javax.swing.JLabel();
        numRowsBox = new javax.swing.JTextField();
        csvExport = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel3.setText("Name");

        jLabel4.setText("Select Name");

        n_name.setSelectedItem(null);
        connect("n_name", "\"Name\"");
        for (int i = 0; i < forCombo.size(); i ++)
        n_name.addItem(forCombo.get(i));
        forCombo.clear();
        n_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                n_nameActionPerformed(evt);
            }
        });

        jLabel5.setText("NameType");

        jLabel6.setText("Name");

        jLabel7.setText("Select Name");

        connect("nt_name", "\"NameType\"");
        for(int i = 0; i < forCombo.size(); i ++)
        nt_namecb.addItem(forCombo.get(i));
        forCombo.clear();
        nt_namecb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nt_namecbActionPerformed(evt);
            }
        });

        jLabel8.setText("Description");

        nt_desBox.setColumns(20);
        nt_desBox.setRows(5);
        jScrollPane2.setViewportView(nt_desBox);

        jLabel9.setText("Select Description");

        connect("nt_description", "\"NameType\"");
        for(int i = 0; i < forCombo.size(); i ++)
        nt_descb.addItem(forCombo.get(i));
        forCombo.clear();
        nt_descb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nt_descbActionPerformed(evt);
            }
        });

        jLabel10.setText("NameTypeAuthority");

        jLabel11.setText("Name");

        jLabel12.setText("Select Name");

        connect("nta_name", "\"NameTypeAuthority\"");
        for(int i = 0; i < forCombo.size(); i ++)
        nta_namecb.addItem(forCombo.get(i));
        forCombo.clear();
        nta_namecb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nta_namecbActionPerformed(evt);
            }
        });

        jLabel13.setText("Description");

        nta_desBox.setColumns(20);
        nta_desBox.setRows(5);
        jScrollPane3.setViewportView(nta_desBox);

        jLabel14.setText("Select Description");

        connect("nta_description", "\"NameTypeAuthority\"");
        for(int i = 0; i < forCombo.size(); i ++)
        nta_descb.addItem(forCombo.get(i));
        forCombo.clear();
        nta_descb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nta_descbActionPerformed(evt);
            }
        });

        jLabel15.setText("UUID");

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

        try {
            enter_uuidBox.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("AAAAAAAA-AAAA-AAAA-AAAA-AAAAAAAAAAAA")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        enter_uuidBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enter_uuidBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(jSeparator2)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel10)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addComponent(nt_nameBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                                        .addComponent(n_nameBox, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(nta_nameBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel13))
                                .addGap(74, 74, 74)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel4)
                                    .addComponent(n_name, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(nt_namecb, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(nt_descb, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(nta_namecb, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(nta_descb, 0, 150, Short.MAX_VALUE)))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(enterButton, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel15)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(gen_uuidSel)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(enter_uuidSel)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(enter_uuidBox, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(0, 115, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Insert)
                    .addComponent(Modify)
                    .addComponent(Delete))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(n_nameBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(n_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabel5)
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nt_nameBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nt_namecb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nt_descb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jLabel10)
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nta_nameBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nta_namecb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nta_descb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(gen_uuidSel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(enter_uuidSel)
                    .addComponent(enter_uuidBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Insert)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Modify)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Delete))
                    .addComponent(enterButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Change Database", jPanel1);

        dataTable.setAutoCreateRowSorter(true);
        dataTable.setModel(model);
        jScrollPane1.setViewportView(dataTable);

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

        jLabel1.setText("Page:");

        curPageBox.setText(Integer.toString(curPage));
        curPageBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                curPageBoxActionPerformed(evt);
            }
        });

        jLabel2.setText("of");

        totalPageBox.setEditable(false);

        showDataBox.setText("Show Data (loading large data sets may slow down the application)");
        showDataBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showDataBoxActionPerformed(evt);
            }
        });

        jLabel16.setText("Row per page:");

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(showDataBox)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(numRowsBox, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(prev)
                        .addGap(155, 155, 155)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(curPageBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalPageBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(next))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(csvExport)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(prev)
                    .addComponent(jLabel1)
                    .addComponent(curPageBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(totalPageBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(next))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(numRowsBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(csvExport)
                .addGap(7, 7, 7)
                .addComponent(showDataBox))
        );

        jTabbedPane1.addTab("View Database", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void enter_uuidBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enter_uuidBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_enter_uuidBoxActionPerformed

    private void enterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enterButtonActionPerformed
        n_nameNew = n_nameBox.getText();
        nt_nameNew = nt_nameBox.getText();
        nt_desNew = nt_desBox.getText();
        nta_nameNew = nta_nameBox.getText();
        nta_desNew = nta_desBox.getText();
        String mRID = enter_uuidBox.getText();
      
        //call dbInsert() if Insert radio button is checked
        if (Insert.isSelected()) {
        dbInsert(n_nameNew, nt_nameNew, nt_desNew, nta_nameNew, nta_desNew);
        }
        //call dbModify() if Modify radio button is checked
        //Rewrites the record with all data on screen
        else if (Modify.isSelected() && enter_uuidSel.isSelected() ) {
            uuidEntered = true;
            dbModify(n_nameNew, nt_nameNew, nt_desNew, nta_nameNew, nta_desNew, mRID);
        }
        //call dbDelete() if Delete radio button is checked
        //only requires the mRID due to cascade deletes
        else if ( Delete.isSelected() && enter_uuidSel.isSelected() ) {
            uuidEntered = true;
            dbDelete(mRID);
        }  else {
            JOptionPane.showMessageDialog( null, "Invalid argument" );
            return;
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
        
        popComboBoxes("n_name", "\"Name\"", n_name);
        popComboBoxes("nt_name", "\"NameType\"", nt_namecb);
        popComboBoxes("nt_description", "\"NameType\"", nt_descb);
        popComboBoxes("nta_name", "\"NameTypeAuthority\"", nta_namecb);
        popComboBoxes("nta_description", "\"NameTypeAuthority\"", nta_descb); 
        
        if (showDataBox.isSelected()) {
            createTable();
        }
    }//GEN-LAST:event_enterButtonActionPerformed

    private void nt_descbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nt_descbActionPerformed
        String nameSel = (String)nt_descb.getSelectedItem();
        nt_desBox.setText(nameSel);
    }//GEN-LAST:event_nt_descbActionPerformed

    private void nta_namecbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nta_namecbActionPerformed
        String nameSel = (String)nta_namecb.getSelectedItem();
        nta_nameBox.setText(nameSel);
    }//GEN-LAST:event_nta_namecbActionPerformed

    private void nt_namecbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nt_namecbActionPerformed
        String nameSel = (String)nt_namecb.getSelectedItem();
        nt_nameBox.setText(nameSel);
    }//GEN-LAST:event_nt_namecbActionPerformed

    private void n_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_n_nameActionPerformed
        String nameSel = (String)n_name.getSelectedItem();
        n_nameBox.setText(nameSel);
    }//GEN-LAST:event_n_nameActionPerformed

    private void nta_descbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nta_descbActionPerformed
        String nameSel = (String)nta_descb.getSelectedItem();
        nta_desBox.setText(nameSel);
    }//GEN-LAST:event_nta_descbActionPerformed

    private void curPageBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_curPageBoxActionPerformed
        createTable();
    }//GEN-LAST:event_curPageBoxActionPerformed

    private void showDataBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showDataBoxActionPerformed
        createTable();
    }//GEN-LAST:event_showDataBoxActionPerformed

    private void numRowsBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numRowsBoxActionPerformed
        createTable();
    }//GEN-LAST:event_numRowsBoxActionPerformed

    private void prevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevActionPerformed
        
            if ( Integer.parseInt( curPageBox.getText() ) != 1 ) {
                curPage -= 1;
                curPageBox.setText(Integer.toString(curPage));
                createTable();
            }
        
    }//GEN-LAST:event_prevActionPerformed

    private void nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextActionPerformed

            if ( Integer.parseInt( curPageBox.getText() ) != Integer.parseInt( totalPageBox.getText()) ) {
                curPage += 1;
                curPageBox.setText(Integer.toString(curPage));
                createTable();
            }
        
    }//GEN-LAST:event_nextActionPerformed

    private void csvExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_csvExportActionPerformed

        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("/home/me/Documents"));
        int retrival = chooser.showSaveDialog(null);
        if (retrival == JFileChooser.APPROVE_OPTION) {
        try {
            
        FileWriter writer = new FileWriter(chooser.getSelectedFile()+".csv");
        Connection con = DriverManager.getConnection(host, uName, uPass);           
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.FETCH_UNKNOWN);
        String query = "SELECT *" +
                          "FROM public.\"NameType\" as nt, public.\"Name\" as n, " +
                          "public.\"NameTypeAuthority\" as nta " +
                          "WHERE n.n_pkey = nt.nt_pkey AND " +
                          "nt.nt_pkey = nta.nta_pkey " +
                          "ORDER BY n.n_name ASC;";
        writer.write("mRID, Name, NameType Name, NameType Description, NameTypeAuthority Name, NameTypeAuthority Description\n");
        
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            writer.write(rs.getString("n_pkey") + ", " +
                         rs.getString("n_name") + ", " +
                         rs.getString("nt_name") + ", " +
                         rs.getString("nt_description") + ", " +
                         rs.getString("nta_name") + ", " +
                         rs.getString("nta_description") + "\n");
        }
        
        writer.flush();
        writer.close();
        
        JOptionPane.showMessageDialog(null, "CSV File Created");
        
        } catch(IOException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } 
        }
    }//GEN-LAST:event_csvExportActionPerformed

    private void DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteActionPerformed
        //clear all text fields and set to uneditable
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

    private void enter_uuidSelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enter_uuidSelActionPerformed
        uuidEntered = true;
    }//GEN-LAST:event_enter_uuidSelActionPerformed

    private void gen_uuidSelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gen_uuidSelActionPerformed
        uuidEntered = false;
    }//GEN-LAST:event_gen_uuidSelActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DataEntryForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DataEntryForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DataEntryForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DataEntryForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //Create splash-screen window user can accept or deny.
        //if accept, continue, if deny, exit program
        new Splash();
        
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
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JCheckBox showDataBox;
    private javax.swing.JTextField totalPageBox;
    // End of variables declaration//GEN-END:variables
}
