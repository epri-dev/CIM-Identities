<%-- 
    Document   : index
    Created on : Jan 9, 2017, 2:21:27 PM
    Author     : pdlo003
--%>

<%@page import="java.util.Iterator"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="WebAppClient.pdlo.*"%>
<%@page import="javax.servlet.http.HttpServlet"%>
<%@page import="javax.servlet.http.HttpServletRequest"%>
<%@page import="javax.servlet.http.HttpServletResponse"%>
<% Class.forName("org.postgresql.Driver"); %>
<!DOCTYPE html>
<html>
    
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Enter Data for CIM</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <style>
            select {
                width: 175px;
                
            }
        </style>
    </head>
    <body onLoad="displayResults()">
        
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
        <li class="active"><a href="index.jsp">Enter Data</a></li>
        <li><a href="viewDB.jsp">View Database</a></li>
        <li><a href="query.jsp">Query</a></li>
    </div>
  </div>
</nav>
  <div class="container-fluid text-center">
  <div class="row content">
    <div class="col-sm-2 sidenav">
      <p><a href="https://github.com/epri-dev/CIM-Identities">Github: Source Code</a></p>
    </div>
    <div class="col-sm-4 text-left">
        <h1>Enter Data for CIM Database </h1>
        
        <form name="myForm" action="DatabaseServlet" method="POST">   
            <fieldset>
                <legend><strong>Name:</strong></legend>
                Name <br>
                 <input class="des" type="text" name="n_nameBox" id="n_nameBox" placeholder="Name">
                 <select name="n_name" onchange="chooseName(this)">
                     <option value=""></option>
                    <%
                    DBConn getData = new DBConn();
                        
                    getData.connect("n_name", "\"Name\"");
                    
                    Iterator it = getData.forCombo.iterator();
                    while (it.hasNext()) {
                        Object object = it.next();
                    %><option value="<%
                        out.println(object); %>
                        "><% out.println(object); %>
                    </option> <%
                        }
                        getData.forCombo.clear();
                    %> 
                    
                 </select><br><br></fieldset>
        
            <fieldset>
                <legend><strong>Name Type:</strong></legend>
            Name<br>
            <input type="text" name="nt_nameBox" id="nt_nameBox" placeholder="Name">
            <select name="nt_name" onchange="chooseNameType(this)">
                <option value=""></option>
                <%    
                    getData.connect("nt_name", "\"NameType\"");
                    Iterator itn = getData.forCombo.iterator();
                    while (itn.hasNext()) {
                        Object object = itn.next();
                    %><option value="<%
                        out.println(object); %>
                        "><% out.println(object); %>
                    </option> <%
                        }
                        getData.forCombo.clear();
                    %> 
            </select><br><br>
            Description<br>
            <input type="text" name="nt_desBox" id="nt_desBox" placeholder="Description">
            <select name="nt_des" onchange="chooseNTDes(this)">
                <option value=""></option>
                 <%    
                    getData.connect("nt_description", "\"NameType\"");
                    Iterator itnd = getData.forCombo.iterator();
                    while (itnd.hasNext()) {
                        Object object = itnd.next();
                    %><option value="<%
                        out.println(object); %>
                        "><% out.println(object); %>
                    </option> <%
                        }
                        getData.forCombo.clear();
                    %> 
            </select><br><br></fieldset>
            <fieldset>
                <legend><strong>Name Type Authority:</strong></legend>
                Name<br>
                <input type="text" name="nta_nameBox" id="nta_nameBox" placeholder="Name">
                <select name="nta_name" onchange="chooseNTA(this)">
                    <option value=""></option>
                    <%    
                    getData.connect("nta_name", "\"NameTypeAuthority\"");
                    Iterator itnt = getData.forCombo.iterator();
                    while (itnt.hasNext()) {
                        Object object = itnt.next();
                    %><option value="<%
                        out.println(object); %>
                        "><% out.println(object); %>
                    </option> <%
                        }
                        getData.forCombo.clear();
                    %> 
                </select><br><br>
                Description<br>
                <input type="text" name="nta_desBox" id="nta_desBox" placeholder="Description">
                <select name="nta_des" onchange="chooseNTADes(this)">
                    <option value=""></option>
                    <%    
                    getData.connect("nta_description", "\"NameTypeAuthority\"");
                    Iterator itntd = getData.forCombo.iterator();
                    while (itntd.hasNext()) {
                        Object object = itntd.next();
                    %><option value="<%
                        out.println(object); %>
                        "><% out.println(object); %>
                    </option> <%
                        }
                        getData.forCombo.clear();
                    %> 
                </select><br><br>
            </fieldset>
        <fieldset>
                <input type="radio" name ="uuidEnt" value ="random" checked="checked"> Randomly Generate UUID<br>
                <input type="radio" name="uuidEnt" value="enter"> Enter UUID Here:
                <input type="text" name="enter_uuidBox" placeholder="0c2cdd47-8695-43f4-8e9a-1712d26da12f"><br><br>
            <input type="submit" value="Submit" name="enterData"> 
        </fieldset>
        </form>
            
    </div>
            
  </div>
  </div>
            <SCRIPT>
            <!--
            function displayResults()
            {
                if (document.myForm.hidden.value === 1) {
                    alert("Data inserted!");
                }
            }
            window.onload = function() {
            document.getElementById("n_nameBox").focus();
            };
            function chooseName(input) {
                document.getElementById("n_nameBox").value = input.value;
            }
            function chooseNameType(input) {
                document.getElementById("nt_nameBox").value = input.value;
            }
            function chooseNTDes(input) {
                document.getElementById("nt_desBox").value = input.value;
            }
            function chooseNTA(input) {
                document.getElementById("nta_nameBox").value = input.value;
            }
            function chooseNTADes(input) {
                document.getElementById("nta_desBox").value = input.value;
            }
            // -->
            </SCRIPT>
            <br>
            
    </body>
</html>
