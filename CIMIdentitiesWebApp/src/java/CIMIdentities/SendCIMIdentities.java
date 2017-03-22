/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CIMIdentities;

import javax.jws.WebService;

/**
 *
 * @author pdlo003
 */
@WebService(serviceName = "SendCIMIdentities", 
        portName = "CIMIdentities_Port", 
        endpointInterface = "ch.iec.tc57._2016.sendcimidentities.CIMIdentitiesPort", 
        targetNamespace = "http://iec.ch/TC57/2016/SendCIMIdentities", 
        wsdlLocation = "WEB-INF/wsdl/SendCIMIdentities/Send_Receive_Reply_CIMIdentities.wsdl")
public class SendCIMIdentities {

    public void createdCIMIdentities(javax.xml.ws.Holder<ch.iec.tc57._2016.schema.message.HeaderType> header, javax.xml.ws.Holder<ch.iec.tc57._2016.cimidentitiesmessage.CIMIdentitiesPayloadType> payload, javax.xml.ws.Holder<ch.iec.tc57._2016.schema.message.ReplyType> reply) throws ch.iec.tc57._2016.sendcimidentities.FaultMessage {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void changedCIMIdentities(javax.xml.ws.Holder<ch.iec.tc57._2016.schema.message.HeaderType> header, javax.xml.ws.Holder<ch.iec.tc57._2016.cimidentitiesmessage.CIMIdentitiesPayloadType> payload, javax.xml.ws.Holder<ch.iec.tc57._2016.schema.message.ReplyType> reply) throws ch.iec.tc57._2016.sendcimidentities.FaultMessage {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void canceledCIMIdentities(javax.xml.ws.Holder<ch.iec.tc57._2016.schema.message.HeaderType> header, javax.xml.ws.Holder<ch.iec.tc57._2016.cimidentitiesmessage.CIMIdentitiesPayloadType> payload, javax.xml.ws.Holder<ch.iec.tc57._2016.schema.message.ReplyType> reply) throws ch.iec.tc57._2016.sendcimidentities.FaultMessage {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void closedCIMIdentities(javax.xml.ws.Holder<ch.iec.tc57._2016.schema.message.HeaderType> header, javax.xml.ws.Holder<ch.iec.tc57._2016.cimidentitiesmessage.CIMIdentitiesPayloadType> payload, javax.xml.ws.Holder<ch.iec.tc57._2016.schema.message.ReplyType> reply) throws ch.iec.tc57._2016.sendcimidentities.FaultMessage {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void deletedCIMIdentities(javax.xml.ws.Holder<ch.iec.tc57._2016.schema.message.HeaderType> header, javax.xml.ws.Holder<ch.iec.tc57._2016.cimidentitiesmessage.CIMIdentitiesPayloadType> payload, javax.xml.ws.Holder<ch.iec.tc57._2016.schema.message.ReplyType> reply) throws ch.iec.tc57._2016.sendcimidentities.FaultMessage {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}
