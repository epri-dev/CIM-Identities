/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebDataEntry.pdlo.jsp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 *
 * @author pdlo003
 */
public class DBConn {
    String host = "jdbc:postgresql://localhost:5432/CIMIdentity";
    String uName = "postgres";
    String password = "epri97!!";
    boolean uuidEntered = false;
    boolean enterPressed = false;
    public ArrayList<String> forCombo = new ArrayList<>();
    String n_nameNew = "";
    String nt_nameNew = "";
    String nt_desNew = "";
    String nta_nameNew = "";
    String nta_desNew = "";
    String newUUID = "";
            
    PreparedStatement insertData = null;
    Connection con = null;
    
    public DBConn() {        
        try {
            con = DriverManager.getConnection(host, uName, password);

        } catch (SQLException err){
            err.printStackTrace();
        }
    
    }
    public void connect(String column, String table){
        try{
           
            Connection con = DriverManager.getConnection(host, uName, password);           
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
            
        } catch(SQLException err){
            System.out.println(err.getMessage());
        }
    }
    
    public int sendToDB(String n_nameNew, String nt_nameNew, String nt_desNew, String nta_nameNew, String nta_desNew, String uuidBox, boolean uuidEntered){
        
        try{
            Connection con = DriverManager.getConnection(host, uName, password);
            Statement stmt = con.createStatement();
        
            if(uuidEntered == false){
            
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
                newUUID = uuidBox;
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
        }
        catch(SQLException err){
            System.out.println(err.getMessage());
        }
        return 1;
    }
}
