/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cimidentities;

import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.annotation.Resource;
import javax.annotation.sql.DataSourceDefinition;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.sql.DataSource;

/**
 *
 * @author pdlo003
 */


@WebService(serviceName = "DatabaseService")
public class DatabaseService {

    @Resource(name = "myDb")
    private DataSource myDb;
    
         
    
    /**
     * Web service operation
     * @param n_name
     * @param nt_name
     * @param nt_des
     * @param nta_name
     * @param nta_des
     * @param uuid
     * @param uuidEntered
     */
    @WebMethod(operationName = "SendToDB")
    public void SendToDB(@WebParam(name = "n_name") String n_name, @WebParam(name = "nt_name") String nt_name, @WebParam(name = "nt_des") String nt_des, @WebParam(name = "nta_name") String nta_name, @WebParam(name = "nta_des") String nta_des, @WebParam(name = "uuid") String uuid, @WebParam(name = "uuidEntered") boolean uuidEntered) {
        
        try {
            String newUUID = "";
            String host = "jdbc:postgresql://localhost:5432/CIMIdentity";
            String uName = "postgres";
            String password = "epri97!!";
            Connection con = DriverManager.getConnection(host, uName, password);  
            //Connection con = myDb.getConnection();
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
                newUUID = uuid;
                String entUUID = "INSERT INTO public.\"Identity\"(id_pkey, entry)"
                        + "VALUES ('" + newUUID + "', DEFAULT)";
                stmt.executeUpdate(entUUID);
            }
        
            String insertIDObj = "INSERT INTO public.\"IdentifiedObject\"(io_pkey)"
                    + "VALUES('" + newUUID + "')";
            String insertName = "INSERT INTO public.\"Name\"(n_pkey, n_name)"
                    + "VALUES('" + newUUID + "', '" + n_name + "')";
            String insertNT = "INSERT INTO public.\"NameType\"(nt_pkey, nt_description, nt_name)"
                    + "VALUES ('" + newUUID + "', '" + nt_des + "', '" + nt_name + "')";
            String insertNTA = "INSERT INTO public.\"NameTypeAuthority\"(nta_pkey, nta_name, nta_description)"
                    + "VALUES ('" + newUUID + "', '" + nta_name + "', '" + nta_des + "')";
        
            stmt.executeUpdate(insertIDObj);
            stmt.executeUpdate(insertName);
            stmt.executeUpdate(insertNT);
            stmt.executeUpdate(insertNTA);
            
            stmt.close();
            con.close();
            
            
        } catch (SQLException ex) {
            out.println(ex.getMessage());
        }
        
    }
}
