<%-- 
    Document   : viewDB
    Created on : Nov 18, 2016, 10:01:42 AM
    Author     : pdlo003
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="WebDataEntry.pdlo.jsp.*"%>
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
        <td><strong>UUID</strong></td>
        <td><strong>Name</strong></td>
        <td><strong>NameType Name</strong></td>
        <td><strong>NameType Description</strong></td>
        <td><strong>NameTypeAuthority Name</strong></td>
        <td><strong>NameTypeAuthority Description</strong></td>
   </tr>
   
   <%
       try {
           String host = "jdbc:postgresql://localhost:5432/CIMIdentity";
           String uName = "postgres";
           String password = "epri97!!";
           
           String query = "SELECT *" +
                          "FROM public.\"NameType\" as nt, public.\"Name\" as n, " +
                          "public.\"NameTypeAuthority\" as nta " +
                          "WHERE n.n_pkey = nt.nt_pkey AND " +
                          "nt.nt_pkey = nta.nta_pkey " +
                          "ORDER BY n.n_name ASC;";
           
           Connection conn = DriverManager.getConnection(host, uName, password);
           Statement stmt = conn.createStatement();
           ResultSet rs = stmt.executeQuery(query);
           while (rs.next()) {
               %>
           <tr>
           
           <td><%= rs.getString(1) %></td>
           <td><%= rs.getString(5) %></td>
           <td><%= rs.getString(3) %></td>
           <td><%= rs.getString(2) %></td>
           <td><%= rs.getString(7) %></td>
           <td><%= rs.getString(8) %></td>
           
           </tr>
           <%
             } 
           %>
    </table>
        </form>
    <%
        rs.close();
        stmt.close();
        conn.close();
           
       }
       catch(Exception e) {
           e.printStackTrace();
           
       }
       
   %>   
    </div>
  </div>
  </div>
    </body>
</html>
