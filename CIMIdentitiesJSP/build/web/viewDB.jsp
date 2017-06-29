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
        <script src="addons/jquery-latest.js"></script>
        <script src="addons/jquery.tablesorter.js"></script>
        <script type="text/javascript" src="addons/jquery.tablesorter.pager.js"></script>
        <script type="text/javascript">
            $(document).ready(function() { 
            $("table") 
            .tablesorter({widthFixed: true, widgets: ['zebra']}) 
            .tablesorterPager({container: $("#pager")}); 
            }); 
        </script>
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
        <li><a href="DataEntry.jsp">Enter Data</a></li>
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
    <table border="6" class="tablesorter">
        <thead>
    <tr>
        <th><strong>entry</strong></th>
        <th><strong>UUID</strong></th>
        <th><strong>Name</strong></th>
        <th><strong>NameType Name</strong></th>
        <th><strong>NameType Description</strong></th>
        <th><strong>NameTypeAuthority Name</strong></th>
        <th><strong>NameTypeAuthority Description</strong></th>
   </tr>
        </thead>
        <tbody>
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
        </tbody>
    </table>
        </form> 
        <div id="pager" class="pager">
	<form>
            <input type="button" value="First Page" class="first"/>
		<input type="button" value="Previous" class="prev"/>
		<input type="text" class="pagedisplay"/>
		<input type="button" value="Next" class="next"/>
		<input type="button" value="Last Page" class="last"/>
		<select class="pagesize">
			<option selected="selected"  value="10">10</option>
			<option value="20">20</option>
			<option value="30">30</option>
			<option value="40">40</option>
                        <option value="50">50</option>
			<option value="60">60</option>
			<option value="70">70</option>
                        <option value="80">80</option>
			<option value="90">90</option>
			<option value="100">100</option>
                        <option value="250">250</option>
                        <option value="500">500</option>
                        <option value="1000">1000</option>
		</select>
	</form>
        </div>
    </div>
  </div>
  </div>
    </body>
</html>
