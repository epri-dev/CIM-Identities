/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cimidentities;

import ch.iec.tc57._2011.schema.message.ReplyType;
import ch.iec.tc57._2016.sendcimidentities.FaultMessage;
import com.epri._2016.cimidentities_.CIMIdentity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.jws.WebService;
import javax.swing.JOptionPane;

/**
 *
 * @author pdlo003
 */
@WebService(serviceName = "SendCIMIdentities", 
        portName = "CIMIdentities_Port", 
        endpointInterface = "ch.iec.tc57._2016.sendcimidentities.CIMIdentitiesPort", 
        targetNamespace = "http://iec.ch/TC57/2016/SendCIMIdentities",
        wsdlLocation = "WEB-INF/wsdl/CIMIdentities/Send_Receive_Reply_CIMIdentities.wsdl")
public class CIMIdentities {
    String mRID;
    String NName;
    String NTName;
    String NTDes;
    String NTAName;
    String NTADes;
    String host = "jdbc:postgresql://localhost:5432/CIMIdentity";
    String uName = "postgres";
    String password = "epri97!!";
 

    public ch.iec.tc57._2016.cimidentitiesmessage.CIMIdentitiesResponseMessageType createdCIMIdentitiesRequest
        (ch.iec.tc57._2016.cimidentitiesmessage.CIMIdentitiesEventMessageType message)  {
        ch.iec.tc57._2016.cimidentitiesmessage.CIMIdentitiesResponseMessageType response = new ch.iec.tc57._2016.cimidentitiesmessage.CIMIdentitiesResponseMessageType();
        //Event message type only contains the header/payload
        //Response message type contains header/payload/reply
        //therefore, response message type must be set for CIMIdentitiesResponseMessageType
        ReplyType value = new ReplyType();
        //String result = value.getResult();
        value.setResult("OK");
        
        //set response header/payload/reply
        response.setHeader(message.getHeader());
        response.setPayload(message.getPayload());
        response.setReply(value);
        
        //extract individual values for insertion into database
        ArrayList<CIMIdentity> cim = (ArrayList<CIMIdentity>) response.getPayload().getCIMIdentities().getCIMIdentity();
        mRID = cim.get(0).getIdentifiedObject().getMRID();
        NName = cim.get(0).getNames().get(0).getName();
        NTName = cim.get(0).getNames().get(0).getNameType().getName();
        NTDes = cim.get(0).getNames().get(0).getNameType().getDescription();
        NTAName = cim.get(0).getNames().get(0).getNameType().getNameTypeAuthority().getName();
        NTADes = cim.get(0).getNames().get(0).getNameType().getNameTypeAuthority().getDescription();
        
        try {
                
              
            Connection con = DriverManager.getConnection(host, uName, password);
            Statement stmt = con.createStatement();
            
            //first check the mRID.  If it's an empty string, treat it the same
            //as uuidEntered == false
            if (mRID.equals("") || mRID.equals("?")) {
            
                String genUUID = "INSERT INTO public.\"Identity\" (id_pkey, entry)"
                        + "VALUES (DEFAULT, DEFAULT)";
                stmt.executeUpdate(genUUID);
                String getUUID = "SELECT id.id_pkey FROM \"Identity\" id "
                        + "ORDER BY id.entry desc LIMIT 1";
                ResultSet rs = stmt.executeQuery(getUUID);
                rs.next();
                mRID = rs.getString("id_pkey");
                
                response.getPayload().getCIMIdentities().getCIMIdentity().get(0).getIdentifiedObject().setMRID(mRID);
                } else {
                    String entUUID = "INSERT INTO public.\"Identity\"(id_pkey, entry)"
                        + "VALUES ('" + mRID + "', DEFAULT)";
                    stmt.executeUpdate(entUUID);
                }
        
                String insertIDObj = "INSERT INTO public.\"IdentifiedObject\"(io_pkey)"
                    + "VALUES('" + mRID + "')";
                String insertName = "INSERT INTO public.\"Name\"(n_pkey, n_name)"
                    + "VALUES('" + mRID + "', '" + NName + "')";
                String insertNT = "INSERT INTO public.\"NameType\"(nt_pkey, nt_description, nt_name)"
                    + "VALUES ('" + mRID + "', '" + NTDes + "', '" + NTName + "')";
                String insertNTA = "INSERT INTO public.\"NameTypeAuthority\"(nta_pkey, nta_name, nta_description)"
                    + "VALUES ('" + mRID + "', '" + NTAName + "', '" + NTADes + "')";
        
                stmt.executeUpdate(insertIDObj);
                stmt.executeUpdate(insertName);
                stmt.executeUpdate(insertNT);
                stmt.executeUpdate(insertNTA);
            
                stmt.close();
                con.close();
                
            } catch(SQLException err){
                value.setResult("FAILED");
                System.out.println(err.getMessage());
            }
        
        return response;

        //TODO implement this method
        //throw new UnsupportedOperationException("Not implemented yet.");
    }

    public ch.iec.tc57._2016.cimidentitiesmessage.CIMIdentitiesResponseMessageType changedCIMIdentitiesRequest
        (ch.iec.tc57._2016.cimidentitiesmessage.CIMIdentitiesEventMessageType message) throws FaultMessage {
        //Event message type only contains the header/payload
        //Response message type contains header/payload/reply
        //therefore, response message type must be set for CIMIdentitiesResponseMessageType
        ch.iec.tc57._2016.cimidentitiesmessage.CIMIdentitiesResponseMessageType response = new ch.iec.tc57._2016.cimidentitiesmessage.CIMIdentitiesResponseMessageType();
        ReplyType value = new ReplyType();
        //String result = value.getResult();
        value.setResult("OK");
        
        //set response header/payload/reply
        response.setHeader(message.getHeader());
        response.setPayload(message.getPayload());
        response.setReply(value);
        
         try {
             
            ArrayList<CIMIdentity> cim = (ArrayList<CIMIdentity>) response.getPayload().getCIMIdentities().getCIMIdentity();
            mRID = cim.get(0).getIdentifiedObject().getMRID();
            NName = cim.get(0).getNames().get(0).getName();
            NTName = cim.get(0).getNames().get(0).getNameType().getName();
            NTDes = cim.get(0).getNames().get(0).getNameType().getDescription();
            NTAName = cim.get(0).getNames().get(0).getNameType().getNameTypeAuthority().getName();
            NTADes = cim.get(0).getNames().get(0).getNameType().getNameTypeAuthority().getDescription();
             
            Connection con = DriverManager.getConnection(host, uName, password);
            Statement stmt = con.createStatement();
            
            //to-do:  Add error checking if "Randomly Generate" is selected
            
            String updateName = "UPDATE public.\"Name\" SET n_name = '" +
                                NName +"' WHERE n_pkey = '" + mRID + "'";
            String updateNT =   "UPDATE public.\"NameType\" SET nt_name = '" +
                                NTName + "', nt_description = '" + NTDes +
                                "' WHERE nt_pkey = '" + mRID + "'";
            String updateNTA =  "UPDATE public.\"NameTypeAuthority\" SET nta_name = '" +
                                NTAName + "', nta_description = '" + NTADes +
                                "' WHERE nta_pkey = '" + mRID + "'";
            
            stmt.executeUpdate(updateName);
            stmt.executeUpdate(updateNT);
            stmt.executeUpdate(updateNTA);
            
            stmt.close();
            con.close();
            
            JOptionPane.showMessageDialog( null, "Data modified" );
            
            } catch(SQLException err){
                value.setResult("FAILED");
                JOptionPane.showMessageDialog( null, err.getMessage());
            }    
        return response;
    }

    public ch.iec.tc57._2016.cimidentitiesmessage.CIMIdentitiesResponseMessageType canceledCIMIdentitiesRequest
        (ch.iec.tc57._2016.cimidentitiesmessage.CIMIdentitiesEventMessageType canceledCIMIdentitiesEventMessage) throws FaultMessage {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public ch.iec.tc57._2016.cimidentitiesmessage.CIMIdentitiesResponseMessageType closedCIMIdentitiesRequest
        (ch.iec.tc57._2016.cimidentitiesmessage.CIMIdentitiesEventMessageType closedCIMIdentitiesEventMessage) throws FaultMessage {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public ch.iec.tc57._2016.cimidentitiesmessage.CIMIdentitiesResponseMessageType deletedCIMIdentitiesRequest
        (ch.iec.tc57._2016.cimidentitiesmessage.CIMIdentitiesEventMessageType message) throws FaultMessage {
        ch.iec.tc57._2016.cimidentitiesmessage.CIMIdentitiesResponseMessageType response = new ch.iec.tc57._2016.cimidentitiesmessage.CIMIdentitiesResponseMessageType();
        ReplyType value = new ReplyType();
        //String result = value.getResult();
        value.setResult("OK");
        
        //set response header/payload/reply
        response.setHeader(message.getHeader());
        response.setPayload(message.getPayload());
        response.setReply(value);
        
        try {

            ArrayList<CIMIdentity> cim = (ArrayList<CIMIdentity>) response.getPayload().getCIMIdentities().getCIMIdentity();
            mRID = cim.get(0).getIdentifiedObject().getMRID();
            NName = cim.get(0).getNames().get(0).getName();
            NTName = cim.get(0).getNames().get(0).getNameType().getName();
            NTDes = cim.get(0).getNames().get(0).getNameType().getDescription();
            NTAName = cim.get(0).getNames().get(0).getNameType().getNameTypeAuthority().getName();
            NTADes = cim.get(0).getNames().get(0).getNameType().getNameTypeAuthority().getDescription();
            Connection con = DriverManager.getConnection(host, uName, password);
            Statement stmt = con.createStatement();
        
            //to-do:  Add error checking if "Randomly Generate" is selected
        
            String delUUID = "DELETE FROM public.\"Identity\" WHERE id_pkey = '" +
                            mRID + "'";
        
            stmt.executeUpdate(delUUID);
        
            stmt.close();
            con.close();
        
            JOptionPane.showMessageDialog( null, "Data deleted" );
            } catch(SQLException err){
                value.setResult("FAILED");
                JOptionPane.showMessageDialog( null, err.getMessage());
            }
        return response;
    }
    
}
