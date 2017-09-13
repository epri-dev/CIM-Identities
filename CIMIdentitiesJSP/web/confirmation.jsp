<%-- 
    Document   : DataEntry
    Created on : Jun 27, 2017, 10:53:32 AM
    Author     : pdlo003
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="CIMWebService.CIMWebService"%>
<%
                String result = new String();                
                String name = request.getParameter("n_nameBox");
                String ntName = request.getParameter("nt_nameBox");
                String ntDesBox = request.getParameter("nt_desBox");
                String ntaName = request.getParameter("nta_nameBox");
                String ntaDesBox = request.getParameter("nta_desBox");
                String uuidBox = new String();
                String dbAct = request.getParameter("dbAction");
                boolean entUUID = false;
                
                String pick = request.getParameter("uuidEnt");
                if (pick.equals("random")) {
                    uuidBox = "";
                    entUUID = false;
                }
                else if (pick.equals("enter")) {
                    entUUID = true;
                    uuidBox = request.getParameter("uuidBox");
                }
                /* instantiate the connection and move over the data */
                CIMWebService cim = new CIMWebService();
                result = cim.submitData(name, ntName, ntDesBox, ntaName, ntaDesBox, uuidBox, dbAct, entUUID);
            
            
    %>
    <p><% out.println(result); %>
    </p>