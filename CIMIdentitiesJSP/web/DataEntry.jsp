<%-- 
    Document   : DataEntry
    Created on : Jun 27, 2017, 10:53:32 AM
    Author     : pdlo003
--%>

<%@page import="ch.iec.tc57._2016.cimidentitiesqueriesmessage.CIMIdentitiesQueriesResponseMessageType"%>
<%@page import="java.util.Set"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="CIMWebService.CIMWebService"%>
<%@page import="javax.servlet.http.HttpServlet"%>
<%@page import="javax.servlet.http.HttpServletRequest"%>
<%@page import="javax.servlet.http.HttpServletResponse"%>
<!DOCTYPE html>
<html>  
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Enter Data for CIM</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script>
            $(document).ready(function() {
                $('#save').click(function ()
                {
                    $.ajax({
                        type: "post",
                        url: "confirmation.jsp",
                        data: {
                            n_nameBox: $('#n_nameBox').val(),
                            nt_nameBox: $('#nt_nameBox').val(),
                            nt_desBox: $('#nt_desBox').val(),
                            nta_nameBox: $('#nta_nameBox').val(),
                            nta_desBox: $('#nta_desBox').val(), 
                            dbAction: $(".dbAction:checked").val(),
                            uuidEnt: $('.uuidEnt:checked').val(),
                            enterUUID: $('#enterUUID').val(),
                            uuidBox: $('#uuidBox').val()
                        },
                        success: function(msg){
                            $('#confirmation').empty();
                            $('#confirmation').append(msg);
                        }  
                    });
                }); 
            });  
        </script>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <style>
            select {
                width: 175px;
                
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
            <fieldset>
                <legend><strong>Name</strong></legend>
                Name <br>
                 <input type="text" name="n_nameBox" id="n_nameBox" placeholder="Name"/>
                 <select name="n_name" onchange="chooseName(this)">
                     <option value=""></option>
                    <%
                    CIMWebService name_id = new CIMWebService();
                    name_id.getResponse("");
                    
                    Set<String> names = name_id.fillNameCB();
                    for (String entry : names) {
                    %><option value="<%
                        out.println(entry); %>
                        "><% out.println(entry); %>
                    </option> <%
                        }
                    %> 
                    
                 </select><br><br></fieldset>
        
            <fieldset>
                <legend><strong>Name Type</strong></legend>
            Name<br>
            <input type="text" name="nt_nameBox" id="nt_nameBox" placeholder="Name"/>
            <select name="nt_name" onchange="chooseNameType(this)">
                <option value=""></option>
                <%  
                    CIMWebService nameType_id = new CIMWebService();
                    nameType_id.getResponse("");
                    Set<String> nameTypeNames = nameType_id.fillNTNameCB();
                    
                    for (String entry : nameTypeNames) {
                    %><option value="<%
                        out.println(entry); %>
                        "><% out.println(entry); %>
                    </option> <%
                        }
                    %> 
            </select><br><br>
            Description<br>
            <input type="text" name="nt_desBox" id="nt_desBox" placeholder="Description"/>
            <select name="nt_des" onchange="chooseNTDes(this)">
                <option value=""></option>
                 <%    
                    CIMWebService nameTypeD_id = new CIMWebService();
                    nameTypeD_id.getResponse("");
                    Set<String> nameTypeDes = nameTypeD_id.fillNTDesCB();
                    
                    for (String entry : nameTypeDes) {
                    %><option value="<%
                        out.println(entry); %>
                        "><% out.println(entry); %>
                    </option> <%
                        }
                    %> 
            </select><br><br></fieldset>
            <fieldset>
                <legend><strong>Name Type Authority</strong></legend>
                Name<br>
                <input type="text" name="nta_nameBox" id="nta_nameBox" placeholder="Name"/>
                <select name="nta_name" onchange="chooseNTA(this)">
                    <option value=""></option>
                    <%    
                    CIMWebService nameTypeAuth_id = new CIMWebService();
                    nameTypeAuth_id.getResponse("");
                    Set<String> nameTypeAuthNames = nameTypeAuth_id.fillNTANameCB();
                    
                    for (String entry : nameTypeAuthNames) {
                    %><option value="<%
                        out.println(entry); %>
                        "><% out.println(entry); %>
                    </option> <%
                        }
                    %> 
                </select><br><br>
                Description<br>
                <input type="text" name="nta_desBox" id="nta_desBox" placeholder="Description"/>
                <select name="nta_des" onchange="chooseNTADes(this)">
                    <option value=""></option>
                    <%    
                    CIMWebService nameTypeAuthD_id = new CIMWebService();
                    nameTypeAuthD_id.getResponse("");
                    Set<String> nameTypeAuthDes = nameTypeAuthD_id.fillNTADesCB();
                    
                    for (String entry : nameTypeAuthDes) {
                    %><option value="<%
                        out.println(entry); %>
                        "><% out.println(entry); %>
                    </option> <%
                        }
                    %> 
                </select><br><br>
            </fieldset>
        <fieldset id="uuidEnt">
                <input type="radio" class="uuidEnt" name ="uuidEnt" value ="random" checked="checked"> Randomly Generate UUID<br>
                <input type="radio" class="uuidEnt" id="enterUUID" name="uuidEnt" value="enter"> Enter UUID Here:
                <input type="text" id="uuidBox" name="enter_uuidBox" oninput="fillPage()"><br><br>
        </fieldset>
        
        <fieldset id="dbAction">
                <input type="radio" name="dbAction" class="dbAction" value="insert" checked="checked"> Insert<br>
                <input type="radio" name="dbAction" class="dbAction" value="modify" onchange="buttonCheck()"> Modify<br>
                <input type="radio" name="dbAction" class="dbAction" value="delete" onchange="buttonCheck()"> Delete<br><br>
        </fieldset>
                
        <fieldset>    
            <input type="button" value="save" name="save" id="save"/>   
        </fieldset>              
    </div>
    <div class="col-xs-6 col-lg-4 text-left" id="confirmation"></div>      
  </div>
              
  </div>
            <SCRIPT>
            <!--
            
            window.onload = function() {
            document.getElementById("n_nameBox").focus();
            };
            
            function buttonCheck() {
                document.getElementById("enterUUID").checked = true;
            }
            
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
            
            <% CIMWebService data = new CIMWebService();
               CIMIdentitiesQueriesResponseMessageType db = data.getCIM();
                
            %> 
            cim_dict = { <%
                for (int i = 0; i < db.getPayload().getCIMIdentities().getCIMIdentity().size(); i++) {
                    %>'<%
                    out.print(db.getPayload().getCIMIdentities().getCIMIdentity().get(i).getIdentifiedObject().getMRID().trim());
                    %>':['<%
                    out.print(db.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getName().trim());
                    %>', '<%
                    out.print(db.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getNameType().getName().trim());
                    %>', '<%
                    out.print(db.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getNameType().getDescription().trim());
                    %>', '<%
                    out.print(db.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getNameType().getNameTypeAuthority().getName().trim());
                    %>', '<%
                    out.print(db.getPayload().getCIMIdentities().getCIMIdentity().get(i).getNames().get(0).getNameType().getNameTypeAuthority().getDescription().trim());
                    %>']<%
                    if (i != db.getPayload().getCIMIdentities().getCIMIdentity().size() - 1) out.println(", ");
                }
            %>};
            
            function fillPage() {
            buttonCheck();
            var id = String(document.getElementById("uuidBox").value);
            id.trim();
            document.getElementById("n_nameBox").value = cim_dict[id][0];
            document.getElementById("nt_nameBox").value = cim_dict[id][1];
            document.getElementById("nt_desBox").value = cim_dict[id][2];
            document.getElementById("nta_nameBox").value = cim_dict[id][3];
            document.getElementById("nta_desBox").value = cim_dict[id][4];
            
            }
            // -->
            </SCRIPT>
            <br>  
    </body>
</html>
