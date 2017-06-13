<%-- 
    Document   : query
    Created on : Jun 9, 2017, 1:46:06 PM
    Author     : pdlo003
--%>

<%@page import="ch.iec.tc57._2016.cimidentitiesqueriesmessage.CIMIdentitiesQueriesResponseMessageType"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="CIMWebService.CIMWebService"%>
<%@page import="javax.servlet.http.HttpServlet"%>
<%@page import="javax.servlet.http.HttpServletRequest"%>
<%@page import="javax.servlet.http.HttpServletResponse"%>
<% Class.forName("org.postgresql.Driver"); %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Database</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </head>
    <body>
        <nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="http://www.epri.com/Pages/Default.aspx">EPRI</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <li><a href="index.jsp">Enter Data</a></li>
        <li><a href="viewDB.jsp">View Database</a></li>
        <li class="active"><a href="query.jsp">Query</a></li>
    </div>
  </div>
</nav>
  <div class="container-fluid text-center">
  <div class="row content">
    <div class="col-sm-2 sidenav">
      <p><a href="https://github.com/epri-dev/CIM-Identities">Github: Source Code</a></p>
    </div>
    <div class="col-sm-8 text-left">
        <h1>Query the DB</h1>
        <form name="myForm" action="query.jsp" method="POST">  
            <fieldset><legend>Select:</legend>
                <input type="radio" name="q" value="mRID" checked="checked">UUID: 
                <input type="text" name="uuidBox"><br><br>
                <input type="radio" name="q" value="Nname">Name : 
                <input type="text" name="nameBox"><br><br>
                <input type="radio" name="q" value="NTname">NameType Name : 
                <input type="text" name="NTnameBox"><br><br>
                <input type="radio" name="q" value="NTdes">NameType Description : 
                <input type="text" name="NTdesBox"><br><br>
                <input type="radio" name="q" value="NTAname">NameTypeAuthority Name : 
                <input type="text" name="NTAnameBox"><br><br>
                <input type="radio" name="q" value="NTAdes">NameTypeAuthority Description : 
                <input type="text" name="NTAdesBox"><br><br>
            </fieldset>
            <fieldset>
                <input type="submit" value="Submit" name="retrieveData"><br><br>
            </fieldset>
            <fieldset>   
        <% //print the information retrieved here 
        try {        
        
            
        out.println("<table border=\"6\">");
        out.println("<tr>");
        out.println("<td><strong>entry</strong></td>");
        out.println("<td><strong>UUID</strong></td>");
        out.println("<td><strong>Name</strong></td>");
        out.println("<td><strong>NameType Name</strong></td>");
        out.println("<td><strong>NameType Description</strong></td>");
        out.println("<td><strong>NameTypeAuthority Name</strong></td>");
        out.println("<td><strong>NameTypeAuthority Description</strong></td></tr>");
        
        CIMWebService CIM = new CIMWebService();
        CIMIdentitiesQueriesResponseMessageType db = CIM.getCIM();
        
        if (request.getParameter("q").equals("Nname")) {
            for (int i = 0; i <  db.getPayload().getCIMIdentities().getCIMIdentity().size(); i++) {
                if (db.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getName().trim().equals(request.getParameter("nameBox").trim())) 
                {
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
                }  else continue;
            }
        } else if (request.getParameter("q").equals("mRID")) {
            for (int i = 0; i <  db.getPayload().getCIMIdentities().getCIMIdentity().size(); i++) {
                if (db.getPayload().getCIMIdentities().getCIMIdentity().get(i).getIdentifiedObject().getMRID().equals(request.getParameter("uuidBox").trim())) 
                {
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
                }  else continue;
            }
        } else if (request.getParameter("q").equals("NTname")) {
            for (int i = 0; i <  db.getPayload().getCIMIdentities().getCIMIdentity().size(); i++) {
                if (db.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getNameType().getName().equals(request.getParameter("NTnameBox").trim())) 
                {
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
                }  else continue;
            }
        } else if (request.getParameter("q").equals("NTdes")) {
            for (int i = 0; i <  db.getPayload().getCIMIdentities().getCIMIdentity().size(); i++) {
                if (db.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getNameType().getDescription().equals(request.getParameter("NTdesBox").trim())) 
                {
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
                }  else continue;
            }
        } else if (request.getParameter("q").equals("NTAname")) {
            for (int i = 0; i <  db.getPayload().getCIMIdentities().getCIMIdentity().size(); i++) {
                if (db.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getNameType().getNameTypeAuthority().getName().equals(request.getParameter("NTAnameBox").trim())) 
                {
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
                }  else continue;
            }
        } else if (request.getParameter("q").equals("NTAdes")) {
            for (int i = 0; i <  db.getPayload().getCIMIdentities().getCIMIdentity().size(); i++) {
                if (db.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getNameType().getNameTypeAuthority().getDescription().equals(request.getParameter("NTAdesBox").trim())) 
                {
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
                }  else continue;
            }
        }
        
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }  
                
        out.println("</table>");       
        %>      
        </fieldset>
        </form>  
        
    </div>
  </div>
  </div>
    </body>
</html>