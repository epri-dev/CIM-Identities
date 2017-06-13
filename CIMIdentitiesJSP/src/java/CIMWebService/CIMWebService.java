/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CIMWebService;

import ch.iec.tc57._2011.schema.message.HeaderType;
import ch.iec.tc57._2016.cimidentitiesmessage.CIMIdentitiesPayloadType;
import ch.iec.tc57._2016.cimidentitiesmessage.CIMIdentitiesResponseMessageType;
import ch.iec.tc57._2016.cimidentitiesqueries_.CIMIdentitiesQueries;
import ch.iec.tc57._2016.cimidentitiesqueries_.EndDeviceGroup;
import ch.iec.tc57._2016.cimidentitiesqueriesmessage.CIMIdentitiesQueriesRequestType;
import ch.iec.tc57._2016.cimidentitiesqueriesmessage.CIMIdentitiesQueriesResponseMessageType;
import ch.iec.tc57._2016.querycimidentities.QueryCIMIdentitiesFaultMessage;
import ch.iec.tc57._2016.sendcimidentities.FaultMessage;
import com.epri._2016.cimidentities_.CIMIdentities;
import com.epri._2016.cimidentities_.CIMIdentity;
import com.epri._2016.cimidentities_.IdentifiedObject;
import com.epri._2016.cimidentities_.Name;
import com.epri._2016.cimidentities_.NameType;
import com.epri._2016.cimidentities_.NameTypeAuthority;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JOptionPane;

/**
 *
 * @author pdlo003
 */
public class CIMWebService {
    CIMIdentitiesQueriesResponseMessageType response = new CIMIdentitiesQueriesResponseMessageType();
    ch.iec.tc57._2016.cimidentitiesqueriesmessage.CIMIdentitiesQueriesRequestMessageType message = new ch.iec.tc57._2016.cimidentitiesqueriesmessage.CIMIdentitiesQueriesRequestMessageType();
    int responseSize = 0;
    boolean uuidEntered = false;
    boolean enterPressed = false;
    int totalPage = 0;
    int curPage = 1;
    int numRows = 30;
    public boolean ShowData = false; 
    String n_nameNew = "";
    String nt_nameNew = "";
    String nt_desNew = "";
    String nta_nameNew = "";
    String nta_desNew = "";
    String newUUID = "";
    
    public void printData(int i, CIMIdentitiesQueriesResponseMessageType db) {
        out.println("<tr><td>");
        out.println(i + 1);
        out.println("</td><td>");
        out.println(db.getPayload().getCIMIdentities().getCIMIdentity().get(i).getIdentifiedObject().getMRID());
        out.println("</td><td>");
        out.println(db.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getName());
        out.println("</td><td>");
        out.println(db.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getNameType().getName());
        out.println("</td><td>");
        out.println(db.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getNameType().getDescription());
        out.println("</td><td>");
        out.println(db.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getNameType().getNameTypeAuthority().getName());
        out.println("</td><td>");
        out.println(db.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getNameType().getNameTypeAuthority().getDescription());
        out.println("</td></tr>");
    }
    
    //Method name:  createResponse()
    //Parameters:  None
    //Purpose:  Takes an input string for UUID to provide a Query result to user.
    //If UUID is empty, it returns all data.  If not, it returns just that UUID's data.
    public void getResponse(String UUID) {
        HeaderType header = new HeaderType();
        header.setNoun("CIMIdentities");
        header.setVerb("get");
        
        CIMIdentitiesQueriesRequestType request = new CIMIdentitiesQueriesRequestType();
        CIMIdentitiesQueries var = new CIMIdentitiesQueries();
        EndDeviceGroup edg = new EndDeviceGroup();
        edg.setMRID(UUID);  //can be null, '?', or '""' to receive all data, else set mRID
        
        
        message.setRequest(request);
        message.getRequest().setCIMIdentitiesQueries(var);
        message.getRequest().getCIMIdentitiesQueries().getEndDeviceGroup().add(0, edg);
        message.getRequest().getCIMIdentitiesQueries().getEndDeviceGroup();
      
        message.setHeader(header);
        
        try {
            response = queryCIMIdentities(message);
        } catch (QueryCIMIdentitiesFaultMessage ex) {
            JOptionPane.showMessageDialog( null, ex.getMessage() );
        }
        if (response.getPayload().getCIMIdentities().getCIMIdentity() != null)
        responseSize = response.getPayload().getCIMIdentities().getCIMIdentity().size();
        
        
    }
    
     public CIMIdentitiesQueriesResponseMessageType getCIM() {
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
        }
        
        return response;
     }
    
    //Method: fillNameCB
    //Parameters:  None
    //Purpose: Creates an alphabetized list of all entries in the database for the Names Combo Box
    public Set<String> fillNameCB() {
        Set<String> nameSet = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < responseSize; i++) {
            if (!response.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getName().trim().equals(""))
            nameSet.add(response.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getName());
        }
        return nameSet;
    }
    
    public Set<String> fillNTNameCB() {
        Set<String> nameTypeTSet = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < responseSize; i++) {
            if (!response.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getNameType().getName().trim().equals("")) 
            nameTypeTSet.add(response.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getNameType().getName().trim());
        }
        return nameTypeTSet;
    }
    
    public Set<String> fillNTDesCB() {
        Set<String> nameTypeDesTSet = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < responseSize; i++) {
            if (!response.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getNameType().getDescription().trim().equals(""))
            nameTypeDesTSet.add(response.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getNameType().getDescription().trim());
        }
        return nameTypeDesTSet;
    }
    
    public Set<String> fillNTANameCB() {
        Set<String> nameTypeAuthTSet = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < responseSize; i++) {
            if (!response.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getNameType().getNameTypeAuthority().getName().trim().equals(""))
            nameTypeAuthTSet.add(response.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getNameType().getNameTypeAuthority().getName().trim());
        }
        return nameTypeAuthTSet;
    }
    
    public Set<String> fillNTADesCB() {
        Set<String> nameTypeAuthDesTSet = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < responseSize; i++) {
            if (!response.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getNameType().getNameTypeAuthority().getDescription().trim().equals(""))
            nameTypeAuthDesTSet.add(response.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getNameType().getNameTypeAuthority().getDescription().trim());
        }
        return nameTypeAuthDesTSet;
    }

    //Method name:  submitData
    //Parameters:  n_nameNew, nt_nameNew, nt_desNew, nta_nameNew, nta_desNew, mRID, action, gen_uuidSel
    //Purpose:  Submits data for either insertion, deletion, or modification
    public int submitData(String n_nameNew, String nt_nameNew, String nt_desNew, String nta_nameNew, String nta_desNew, String mRID, String action, boolean gen_uuidSel) {
        ch.iec.tc57._2016.cimidentitiesmessage.CIMIdentitiesEventMessageType msg = new ch.iec.tc57._2016.cimidentitiesmessage.CIMIdentitiesEventMessageType();
 
        CIMIdentitiesPayloadType payload = new CIMIdentitiesPayloadType();
        CIMIdentities cimids = new CIMIdentities();
        CIMIdentity cimid = new CIMIdentity();
        payload.setCIMIdentities(cimids);
        
        ArrayList<CIMIdentity> cim = (ArrayList<CIMIdentity>) payload.getCIMIdentities().getCIMIdentity();
        
        IdentifiedObject value = new IdentifiedObject();
        value.setMRID(mRID);
        cimid.setIdentifiedObject(value);
        
        Name name = new Name();
        name.setName(n_nameNew);
        if (action.equals("delete")) name.setName("");
        if (name.getName() == null) name.setName("");
        cimid.getNames().add(name);
        
        //set NameType name/description
        NameType nameType = new NameType();
        nameType.setDescription(nt_desNew);
        if (action.equals("delete")) nameType.setDescription("");
        if (nameType.getDescription() == null) nameType.setDescription("");
        nameType.setName(nt_nameNew);
        if (action.equals("delete")) nameType.setName("");
        if (nameType.getName() == null) nameType.setName("");
        cimid.getNames().get(0).setNameType(nameType);
        
        //set NameTypeAuthority name/description
        NameTypeAuthority nameTypeAuth = new NameTypeAuthority();
        nameTypeAuth.setDescription(nta_desNew);
        if (action.equals("delete")) nameTypeAuth.setDescription("");
        if (nameTypeAuth.getDescription() == null) nameTypeAuth.setDescription("");
        nameTypeAuth.setName(nta_nameNew);
        if (action.equals("delete")) nameTypeAuth.setName("");
        if (nameTypeAuth.getName() == null) nameTypeAuth.setDescription("");
        cimid.getNames().get(0).getNameType().setNameTypeAuthority(nameTypeAuth);
        
        cim.add(cimid);
        
        msg.setPayload(payload);
        
        /* Insertion handling */
        if (action.equals("insert")) {
            if (gen_uuidSel == true) {
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
                out.println("Data inserted:\n\n"
                 + "\nName: " +  msg.getPayload().getCIMIdentities().getCIMIdentity().get(0).getNames().get(0).getName()
                 + "\nNameType Name: " +  msg.getPayload().getCIMIdentities().getCIMIdentity().get(0).getNames().get(0).getNameType().getName() 
                 + "\nNameType Description: " + msg.getPayload().getCIMIdentities().getCIMIdentity().get(0).getNames().get(0).getNameType().getDescription() 
                 + "\nNameTypeAuthority Name: " +  msg.getPayload().getCIMIdentities().getCIMIdentity().get(0).getNames().get(0).getNameType().getNameTypeAuthority().getName() 
                 + "\nNameTypeAuthority Description: " + msg.getPayload().getCIMIdentities().getCIMIdentity().get(0).getNames().get(0).getNameType().getNameTypeAuthority().getDescription());
            } catch (FaultMessage ex) {
                out.println(ex.getMessage());
            }
        }
        
        /* deletion handling */
        if (action.equals("delete") && gen_uuidSel == true) {
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
                out.println(ex.getMessage());
            }
            
            /* set outgoing deletion message to contain ALL data about the mRID */
            msg.getPayload().setCIMIdentities(response.getPayload().getCIMIdentities());
            
            try {
                deletedCIMIdentitiesRequest(msg);
                out.println(
                "Data deleted:\n\n"
                 + "mRID: " + response.getPayload().getCIMIdentities().getCIMIdentity().get(0).getIdentifiedObject().getMRID()
                 + "\nName: " +  response.getPayload().getCIMIdentities().getCIMIdentity().get(0).getNames().get(0).getName()
                 + "\nNameType Name: " +  response.getPayload().getCIMIdentities().getCIMIdentity().get(0).getNames().get(0).getNameType().getName() 
                 + "\nNameType Description: " + response.getPayload().getCIMIdentities().getCIMIdentity().get(0).getNames().get(0).getNameType().getDescription() 
                 + "\nNameTypeAuthority Name: " +  response.getPayload().getCIMIdentities().getCIMIdentity().get(0).getNames().get(0).getNameType().getNameTypeAuthority().getName() 
                 + "\nNameTypeAuthority Description: " + response.getPayload().getCIMIdentities().getCIMIdentity().get(0).getNames().get(0).getNameType().getNameTypeAuthority().getDescription());
                        
            } catch (FaultMessage ex) {
                out.println(ex.getMessage());
            }
        }
        
         /* modification handling */
        if (action.equals("modify")) {
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
                out.println(ex.getMessage());
            }
            try {
                changedCIMIdentitiesRequest(msg);
                out.println(
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
                out.println(ex.getMessage());
            }
        }
        return 1;
    }
    
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
