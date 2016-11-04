/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package createtable;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;
/**
 *
 * @author pdlo003
 */
public class CreateTable {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       String URL = "jdbc:postgresql://localhost:5432/CIMIdentity";
       String LoginID = "postgres";
  
       JPasswordField pwd = new JPasswordField(10);
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
        int action = JOptionPane.showConfirmDialog(null, pwd,"Enter Password",JOptionPane.OK_CANCEL_OPTION);
        if (action < 0) JOptionPane.showMessageDialog(null,"Cancel, X or escape key selected");
       
       String passw = new String(pwd.getPassword());
       
       Connection c = null;
       Statement stmt = null;
       Connection cc = null;
       try {
           
         c = DriverManager
            .getConnection("jdbc:postgresql://localhost:5432/", LoginID, passw);

         /* Now that we are connected to PostgreSQL, let's create
            the database */
         stmt = c.createStatement();
         String sql = "CREATE DATABASE \"CIMIdentity\";";
         stmt.executeUpdate(sql);
         stmt.close();
         c.close();
         System.out.println("Opened database successfully");
         
         
         stmt = null;
         cc = DriverManager.getConnection(URL, LoginID, passw);
         stmt = cc.createStatement();
         
        String usql = "CREATE EXTENSION pgcrypto; " +
                      "CREATE TABLE public.\"Identity\" " +
                      "(id_pkey uuid NOT NULL DEFAULT gen_random_uuid(), " +
                      " entry serial NOT NULL, " +
                      "CONSTRAINT id_pkey PRIMARY KEY (id_pkey) )" +
                      " WITH ( OIDS = FALSE); " +
                      " ALTER TABLE public.\"Identity\"" +
                      " OWNER to postgres;" +
                  
                      "CREATE TABLE public.\"IdentifiedObject\"" +
                      "( io_pkey uuid NOT NULL, " +
                      "CONSTRAINT io_pkey PRIMARY KEY (io_pkey), " +
                      "CONSTRAINT io_idkey FOREIGN KEY (io_pkey) " +
                      "REFERENCES public.\"Identity\" (id_pkey) MATCH SIMPLE " +
                      "ON UPDATE NO ACTION ON DELETE NO ACTION )" +
                      "WITH ( OIDS = FALSE);" +
                      "ALTER TABLE public.\"IdentifiedObject\"" +
                      "OWNER TO postgres;" +
                  
                      "CREATE TABLE public.\"Name\"" +
                      "( n_pkey uuid NOT NULL, " +
                      "n_name character varying NOT NULL, " +
                      "CONSTRAINT n_pkey PRIMARY KEY (n_pkey), " +
                      "CONSTRAINT n_idkey FOREIGN KEY (n_pkey)" +
                      " REFERENCES public.\"Identity\" (id_pkey) MATCH SIMPLE" +
                      " ON UPDATE NO ACTION ON DELETE NO ACTION" +
                      ") WITH ( OIDS = FALSE );" +
                      " ALTER TABLE public.\"Name\" OWNER TO postgres;" +
                  
                      " CREATE TABLE public.\"NameType\" " +
                      " ( nt_pkey uuid NOT NULL, " +
                      " nt_description character varying NOT NULL, " +
                      " nt_name character varying NOT NULL, " +
                      " CONSTRAINT nt_pkey PRIMARY KEY (nt_pkey), " +
                      " CONSTRAINT nt_nkey FOREIGN KEY (nt_pkey) " +
                      " REFERENCES public.\"Name\" (n_pkey) MATCH SIMPLE " +
                      " ON UPDATE NO ACTION ON DELETE NO ACTION ) " +
                      " WITH ( OIDS = FALSE ); " +
                      " ALTER TABLE public.\"NameType\" OWNER TO postgres; " +
                  
                      " CREATE TABLE public.\"NameTypeAuthority\"" +
                      " ( nta_pkey uuid NOT NULL, " +
                      " nta_name character varying, " +
                      " nta_description character varying NOT NULL, " +
                      " CONSTRAINT nta_pkey PRIMARY KEY (nta_pkey), " +
                      " CONSTRAINT nta_ntkey FOREIGN KEY (nta_pkey) " +
                      " REFERENCES public.\"NameType\" (nt_pkey) MATCH SIMPLE " +
                      " ON UPDATE NO ACTION ON DELETE NO ACTION )" +
                      " WITH ( OIDS = FALSE ); " +
                      " ALTER TABLE public.\"NameTypeAuthority\" " +
                      " OWNER TO postgres;";
         stmt.executeUpdate(usql);
         stmt.close();
         c.close();
       } catch ( Exception e ) {
         JOptionPane.showMessageDialog(null, e.getClass().getName()+": "+ e.getMessage() );
         System.exit(0);
       }
       JOptionPane.showMessageDialog(null,"Table created successfully!");
     }
    }
    
    

