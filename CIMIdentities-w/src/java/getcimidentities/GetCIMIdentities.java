/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getcimidentities;

import ch.iec.tc57._2011.schema.message.ReplyType;
import ch.iec.tc57._2016.querycimidentities.QueryCIMIdentitiesFaultMessage;
import com.epri._2016.cimidentities_.CIMIdentities;
import com.epri._2016.cimidentities_.CIMIdentity;
import com.epri._2016.cimidentities_.IdentifiedObject;
import com.epri._2016.cimidentities_.Name;
import com.epri._2016.cimidentities_.NameType;
import com.epri._2016.cimidentities_.NameTypeAuthority;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;

/**
 *
 * @author pdlo003
 */
@WebService(serviceName = "QueryCIMIdentities", 
        portName = "QueryCIMIdentities_Port",
        endpointInterface = "ch.iec.tc57._2016.querycimidentities.QueryCIMIdentitiesPort",
        targetNamespace = "http://iec.ch/TC57/2016/QueryCIMIdentities", 
        wsdlLocation = "WEB-INF/wsdl/GetCIMIdentities/CIMIdentities_Query_WSDL.wsdl")
public class GetCIMIdentities {
    String mRID;
    String NName;
    String NTName;
    String NTDes;
    String NTAName;
    String NTADes;
    String host = "jdbc:postgresql://localhost:5432/CIMIdentity";
    String uName = "postgres";
    String password = "epri97!!";

    public ch.iec.tc57._2016.cimidentitiesqueriesmessage.CIMIdentitiesQueriesResponseMessageType queryCIMIdentities
        (ch.iec.tc57._2016.cimidentitiesqueriesmessage.CIMIdentitiesQueriesRequestMessageType message) 
                throws QueryCIMIdentitiesFaultMessage {
        
        //create response message object
        ch.iec.tc57._2016.cimidentitiesqueriesmessage.CIMIdentitiesQueriesResponseMessageType response = new ch.iec.tc57._2016.cimidentitiesqueriesmessage.CIMIdentitiesQueriesResponseMessageType();   
        
        //create header object to assign to response.  Set to "get" by default
        ch.iec.tc57._2011.schema.message.HeaderType header = new ch.iec.tc57._2011.schema.message.HeaderType();
        header.setVerb("get");
        header.setNoun("CIM Identities");
        response.setHeader(header);
        
        //create reply object that if successfully is "OK", if not is "FAILED"
        ReplyType value = new ReplyType();
        value.setResult("OK");
        response.setReply(value);
        
        
        //create Payload object for response message
        ch.iec.tc57._2016.cimidentitiesqueriesmessage.CIMIdentitiesQueriesPayloadType payload = new ch.iec.tc57._2016.cimidentitiesqueriesmessage.CIMIdentitiesQueriesPayloadType();
        response.setPayload(payload);
        

        //extract individual values for insertion into database
        
        try {
            String query;
            String uuid = message.getRequest().getCIMIdentitiesQueries().getEndDeviceGroup().get(0).getMRID();
            if (uuid == null || uuid.equals("") || uuid.equals("?")) {
            
            query = "SELECT *" +
                           "FROM public.\"NameType\" as nt, public.\"Name\" as n, " +
                           "public.\"NameTypeAuthority\" as nta " +
                           "WHERE n.n_pkey = nt.nt_pkey AND " +
                           "nt.nt_pkey = nta.nta_pkey " +
                           "ORDER BY n.n_name ASC;";
            } else {
               query = "SELECT *" +
                       "FROM public.\"NameType\" as nt, public.\"Name\" as n, " +
                       "public.\"NameTypeAuthority\" as nta " +
                       "WHERE n.n_pkey = '" + uuid + "' AND " +
                       "n.n_pkey = nt.nt_pkey AND " +
                       "nt.nt_pkey = nta.nta_pkey";
                       
            }
           
           Connection conn = DriverManager.getConnection(host, uName, password);
           Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
           ResultSet rs = stmt.executeQuery(query);
           
          
           int size = 0;
           if (rs != null) {
               rs.beforeFirst();
               rs.last();
               size = rs.getRow();
           } 
           rs.beforeFirst();
           
           CIMIdentities cimIDs = new CIMIdentities();
           payload.setCIMIdentities(cimIDs);
           ArrayList<CIMIdentity> cim = (ArrayList<CIMIdentity>) payload.getCIMIdentities().getCIMIdentity();
           cim.ensureCapacity(size);  //sets size of the ArrayList to size of the Result Set
           
           
           int i = 0;
           
           while (rs.next()) {  
                //create new CIM Identity objects for each object that will be assigned later 
                //when querying the database
                
                CIMIdentity cimid = new CIMIdentity();
                IdentifiedObject idObj = new IdentifiedObject();
                Name name = new Name();
                cim.add(i, cimid);
                
                List<Name> names = cim.get(i).getNames();
                NameType nameType = new NameType();
                NameTypeAuthority nameTypeAuthority = new NameTypeAuthority();
               
               
                //set the mRID
                idObj.setMRID(rs.getString("n_pkey"));
                cim.get(i).setIdentifiedObject(idObj);
            
                names.add(0, name);
                name.setName(rs.getString("n_name"));
                names.get(0).setName(name.getName());
               
                nameType.setName(rs.getString("nt_name"));
                nameType.setDescription(rs.getString("nt_description"));
                names.get(0).setNameType(nameType);
                
                nameTypeAuthority.setName(rs.getString("nta_name"));
                nameTypeAuthority.setDescription(rs.getString("nta_description"));
                names.get(0).getNameType().setNameTypeAuthority(nameTypeAuthority);

                i++;
           }
           rs.close();
           stmt.close();
           conn.close();
           response.setPayload(payload);
           
        } catch(Exception err){
                value.setResult("FAILED");
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                err.printStackTrace(pw);
                value.getError().get(0).setDetails(sw.toString());
                response.setReply(value);
                return response;
        }
        response.setReply(value);
        
        return response;
        //throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}
