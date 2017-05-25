/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cimidentities.c;

import ch.iec.tc57._2011.schema.message.HeaderType;
import ch.iec.tc57._2016.cimidentitiesmessage.CIMIdentitiesResponseMessageType;
import ch.iec.tc57._2016.cimidentitiesqueries.CIMIdentitiesQueries;
import ch.iec.tc57._2016.cimidentitiesqueries.EndDeviceGroup;
import ch.iec.tc57._2016.cimidentitiesqueriesmessage.CIMIdentitiesQueriesRequestType;
import ch.iec.tc57._2016.cimidentitiesqueriesmessage.CIMIdentitiesQueriesResponseMessageType;
import ch.iec.tc57._2016.querycimidentities.QueryCIMIdentitiesFaultMessage;
import ch.iec.tc57._2016.sendcimidentities.FaultMessage;

/**
 *
 * @author pdlo003
 */
public class CIMIdentitiesC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws QueryCIMIdentitiesFaultMessage, FaultMessage {
        // TODO code application logic here
        CIMIdentitiesQueriesResponseMessageType response = new CIMIdentitiesQueriesResponseMessageType();
        ch.iec.tc57._2016.cimidentitiesqueriesmessage.CIMIdentitiesQueriesRequestMessageType message = new ch.iec.tc57._2016.cimidentitiesqueriesmessage.CIMIdentitiesQueriesRequestMessageType();
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
        
        response = queryCIMIdentities(message);
        
        int i = response.getPayload().getCIMIdentities().getCIMIdentity().size();
        for (int j = 0; j < i; j++) {
        System.out.println(response.getPayload().getCIMIdentities().getCIMIdentity().get(j).getNames().get(0).getName());
        }
        
    }

    private static CIMIdentitiesQueriesResponseMessageType queryCIMIdentities
        (ch.iec.tc57._2016.cimidentitiesqueriesmessage.CIMIdentitiesQueriesRequestMessageType queryCIMIdentitiesRequestMessage) throws QueryCIMIdentitiesFaultMessage {
        ch.iec.tc57._2016.querycimidentities.QueryCIMIdentities service = new ch.iec.tc57._2016.querycimidentities.QueryCIMIdentities();
        ch.iec.tc57._2016.querycimidentities.QueryCIMIdentitiesPort port = service.getQueryCIMIdentitiesPort();
        return port.queryCIMIdentities(queryCIMIdentitiesRequestMessage);
    }

    private static CIMIdentitiesResponseMessageType createdCIMIdentitiesRequest(ch.iec.tc57._2016.cimidentitiesmessage.CIMIdentitiesEventMessageType createdCIMIdentitiesEventMessage) throws FaultMessage {
        ch.iec.tc57._2016.sendcimidentities.SendCIMIdentities service = new ch.iec.tc57._2016.sendcimidentities.SendCIMIdentities();
        ch.iec.tc57._2016.sendcimidentities.CIMIdentitiesPort port = service.getCIMIdentitiesPort();
        return port.createdCIMIdentitiesRequest(createdCIMIdentitiesEventMessage);
    }

    private static CIMIdentitiesResponseMessageType changedCIMIdentitiesRequest(ch.iec.tc57._2016.cimidentitiesmessage.CIMIdentitiesEventMessageType changedCIMIdentitiesEventMessage) throws FaultMessage {
        ch.iec.tc57._2016.sendcimidentities.SendCIMIdentities service = new ch.iec.tc57._2016.sendcimidentities.SendCIMIdentities();
        ch.iec.tc57._2016.sendcimidentities.CIMIdentitiesPort port = service.getCIMIdentitiesPort();
        return port.changedCIMIdentitiesRequest(changedCIMIdentitiesEventMessage);
    }

    private static CIMIdentitiesResponseMessageType deletedCIMIdentitiesRequest(ch.iec.tc57._2016.cimidentitiesmessage.CIMIdentitiesEventMessageType deletedCIMIdentitiesEventMessage) throws FaultMessage {
        ch.iec.tc57._2016.sendcimidentities.SendCIMIdentities service = new ch.iec.tc57._2016.sendcimidentities.SendCIMIdentities();
        ch.iec.tc57._2016.sendcimidentities.CIMIdentitiesPort port = service.getCIMIdentitiesPort();
        return port.deletedCIMIdentitiesRequest(deletedCIMIdentitiesEventMessage);
    }
    
}
