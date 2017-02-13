package dataEntry;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import java.awt.event.HierarchyEvent;
import java.awt.Component;
import java.awt.Window;
import java.awt.event.HierarchyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.SwingUtilities;
/**
 *
 * @author pdlo003
 */
public class DataEntry extends javax.swing.JFrame {
    String host = "jdbc:postgresql://localhost:5432/CIMIdentity";
    String uName = "postgres";
    String uPass; 
    ArrayList<String> forCombo = new ArrayList<>();
    boolean uuidEntered = false;
    boolean enterPressed = false;
    
    String n_nameNew = "";
    String nt_nameNew = "";
    String nt_desNew = "";
    String nta_nameNew = "";
    String nta_desNew = "";
    String newUUID = "";
    /**
     * Creates new form DataEntry
     */
    public DataEntry(String Pass) {
        uPass = Pass;
        initComponents();
        this.setLocationRelativeTo(null);
    }
    
        //Populate Combobox with previous entries
    public void connect(String column, String table){
        try{
            
           // Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(host, uName, uPass);           
            Statement stmt = con.createStatement();

            String getData = "SELECT DISTINCT " + column + " FROM " + table +
                    " ORDER BY " + column;

            ResultSet dataSet = stmt.executeQuery(getData);
            while(dataSet.next()){
                forCombo.add(dataSet.getString(column).trim());
            }
            
            Set<String> toRetain = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
            toRetain.addAll(forCombo);
            Set<String> set = new LinkedHashSet<String>(forCombo);
            set.retainAll(new LinkedHashSet<String>(toRetain));
            forCombo = new ArrayList<String>(set);

        }
            
        catch(SQLException err){
            System.out.println(err.getMessage());
        }
    }

    public void dbDelete(String mRID) {
        try {
        Connection con = DriverManager.getConnection(host, uName, uPass);
        Statement stmt = con.createStatement();
        
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
       
        try{
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
        jLabel1 = new javax.swing.JLabel();
        n_nameBox = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        n_name = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        nt_nameBox = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        nt_desBox = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        nt_namecb = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        nt_descb = new javax.swing.JComboBox<>();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        nta_nameBox = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        nta_desBox = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        nta_namecb = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        nta_descb = new javax.swing.JComboBox<>();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();
        gen_uuidSel = new javax.swing.JRadioButton();
        enter_uuidSel = new javax.swing.JRadioButton();
        enter_uuidBox = new javax.swing.JFormattedTextField();
        enterButton = new javax.swing.JButton();
        Insert = new javax.swing.JRadioButton();
        Modify = new javax.swing.JRadioButton();
        Delete = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Name");

        n_nameBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                n_nameBoxActionPerformed(evt);
            }
        });

        jLabel2.setText("Select Name");

        n_name.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        connect("n_name", "\"Name\"");
        for (int i = 0; i < forCombo.size(); i ++)
        n_name.addItem(forCombo.get(i));
        forCombo.clear();
        n_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                n_nameActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("NameType");

        jLabel4.setText("Name");

        jLabel5.setText("Description");

        jScrollPane1.setMinimumSize(new java.awt.Dimension(20, 5));
        jScrollPane1.setName(""); // NOI18N

        nt_desBox.setColumns(20);
        nt_desBox.setRows(5);
        jScrollPane1.setViewportView(nt_desBox);

        jLabel6.setText("Select Name");

        connect("nt_name", "\"NameType\"");
        for(int i = 0; i < forCombo.size(); i ++)
        nt_namecb.addItem(forCombo.get(i));
        forCombo.clear();
        nt_namecb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nt_namecbActionPerformed(evt);
            }
        });

        jLabel7.setText("Select Description");

        connect("nt_description", "\"NameType\"");
        for(int i = 0; i < forCombo.size(); i ++)
        nt_descb.addItem(forCombo.get(i));
        forCombo.clear();
        nt_descb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nt_descbActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("NameTypeAuthority");

        jLabel9.setText("Name");

        jLabel10.setText("Description");

        nta_desBox.setColumns(20);
        nta_desBox.setRows(5);
        jScrollPane2.setViewportView(nta_desBox);

        jLabel11.setText("Select Name");

        connect("nta_name", "\"NameTypeAuthority\"");
        for(int i = 0; i < forCombo.size(); i ++)
        nta_namecb.addItem(forCombo.get(i));
        forCombo.clear();
        nta_namecb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nta_namecbActionPerformed(evt);
            }
        });

        jLabel12.setText("Select Description");

        connect("nta_description", "\"NameTypeAuthority\"");
        for(int i = 0; i < forCombo.size(); i ++)
        nta_descb.addItem(forCombo.get(i));
        forCombo.clear();
        nta_descb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nta_descbActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setText("UUID");

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

        try {
            enter_uuidBox.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("AAAAAAAA-AAAA-AAAA-AAAA-AAAAAAAAAAAA")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        enter_uuidBox.setToolTipText("");
        enter_uuidBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enter_uuidBoxActionPerformed(evt);
            }
        });

        enterButton.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        enterButton.setText("Enter");
        enterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enterButtonActionPerformed(evt);
            }
        });

        buttonGroup2.add(Insert);
        Insert.setSelected(true);
        Insert.setText("Insert");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(jSeparator2)
            .addComponent(jSeparator3)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(Delete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(enterButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(n_nameBox, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(n_name, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4)
                                    .addComponent(nt_nameBox)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(nt_namecb, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(nt_descb, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel10)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(nta_descb, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(enter_uuidSel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(enter_uuidBox, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(gen_uuidSel)))
                            .addComponent(Insert)
                            .addComponent(Modify))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(nta_nameBox, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(nta_namecb, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(137, 137, 137))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(n_nameBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(n_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nt_nameBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nt_namecb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nt_descb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nta_nameBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nta_namecb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nta_descb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(gen_uuidSel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(enter_uuidSel)
                    .addComponent(enter_uuidBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(Insert)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(enterButton)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Modify)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Delete)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void n_nameBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_n_nameBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_n_nameBoxActionPerformed

    private void enterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enterButtonActionPerformed
        // TODO add your handling code here:
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
        else if (Modify.isSelected()) {
            dbModify(n_nameNew, nt_nameNew, nt_desNew, nta_nameNew, nta_desNew, mRID);
        }
        //call dbDelete() if Delete radio button is checked
        //only requires the mRID due to cascade deletes
        else if (Delete.isSelected()) {
            dbDelete(mRID);
        }
        n_nameBox.setText("");
        nt_nameBox.setText("");
        nt_desBox.setText("");
        nta_nameBox.setText("");
        nta_desBox.setText("");
        enter_uuidBox.setText("");
    }//GEN-LAST:event_enterButtonActionPerformed

    private void gen_uuidSelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gen_uuidSelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_gen_uuidSelActionPerformed

    private void enter_uuidBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enter_uuidBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_enter_uuidBoxActionPerformed

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

    private void DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DeleteActionPerformed

    private void ModifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModifyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ModifyActionPerformed

    
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
            java.util.logging.Logger.getLogger(DataEntry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DataEntry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DataEntry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DataEntry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Acquire password to PostgreSQL */
        JPasswordField pwd = new JPasswordField(20);
        pwd.addHierarchyListener(new HierarchyListener() 
        {
            public void hierarchyChanged(HierarchyEvent e) 
                {
                     final Component c = e.getComponent();
                        if (c.isShowing() && (e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0) 
                        {
                                Window toplevel = SwingUtilities.getWindowAncestor(c);
                                toplevel.addWindowFocusListener(new WindowAdapter() 
                                {
                                         public void windowGainedFocus(WindowEvent e) 
                                        {
                                            c.requestFocus();
                                        }
                                });
                        }
                }
        });
        int action = JOptionPane.showConfirmDialog(null, pwd,"Enter PostgreSQL Password",JOptionPane.OK_CANCEL_OPTION);
        if (action < 0) {
            JOptionPane.showMessageDialog(null,"Cancel, X or Escape key selected");
            System.exit(0);
        }
        
       String passw = new String(pwd.getPassword());
       
       Connection s = null;
       
       try {

         s = DriverManager.getConnection("jdbc:postgresql://localhost:5432/CIMIdentity", "postgres", passw);
       
         } catch ( Exception e ) {
         JOptionPane.showMessageDialog( null, e.getClass().getName()+": "+ e.getMessage() );
         System.exit(0);
         }
       
       /* End password acquisition */
       
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DataEntry(passw).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton Delete;
    private javax.swing.JRadioButton Insert;
    private javax.swing.JRadioButton Modify;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton enterButton;
    private javax.swing.JFormattedTextField enter_uuidBox;
    private javax.swing.JRadioButton enter_uuidSel;
    private javax.swing.JRadioButton gen_uuidSel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JComboBox<String> n_name;
    private javax.swing.JTextField n_nameBox;
    private javax.swing.JTextArea nt_desBox;
    private javax.swing.JComboBox<String> nt_descb;
    private javax.swing.JTextField nt_nameBox;
    private javax.swing.JComboBox<String> nt_namecb;
    private javax.swing.JTextArea nta_desBox;
    private javax.swing.JComboBox<String> nta_descb;
    private javax.swing.JTextField nta_nameBox;
    private javax.swing.JComboBox<String> nta_namecb;
    // End of variables declaration//GEN-END:variables
}
