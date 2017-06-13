<%-- 
    Document   : viewDB
    Created on : Jun 9, 2017, 9:56:07 AM
    Author     : pdlo003
--%>
<%@page import="ch.iec.tc57._2016.cimidentitiesqueriesmessage.CIMIdentitiesQueriesResponseMessageType"%>
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
        <style>
            td {
                padding-left: 6px;
                padding-right: 6px;
            }
        </style>
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
        <li class="active"><a href="viewDB.jsp">View Database</a></li>
        <li><a href="query.jsp">Query</a></li>
    </div>
  </div>
</nav>
  <div class="container-fluid text-center">
  <div class="row content">
    <div class="col-sm-2 sidenav">
      <p><a href="https://github.com/epri-dev/CIM-Identities">Github: Source Code</a></p>
    </div>
    <div class="col-sm-8 text-left">
        <h1>CIM Database Entries</h1>
        <form method="post">
    <table border="6">
    <tr>
        <td><strong>entry</strong></td>
        <td><strong>UUID</strong></td>
        <td><strong>Name</strong></td>
        <td><strong>NameType Name</strong></td>
        <td><strong>NameType Description</strong></td>
        <td><strong>NameTypeAuthority Name</strong></td>
        <td><strong>NameTypeAuthority Description</strong></td>
   </tr>
           <% 
           CIMWebService CIM = new CIMWebService();
           CIMIdentitiesQueriesResponseMessageType db = CIM.getCIM();
           
           for (int i = 0; i <  db.getPayload().getCIMIdentities().getCIMIdentity().size(); i++) {
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
           %>
    </table>
        </form> 
    </div>
  </div>
  </div>
    </body>
</html>
