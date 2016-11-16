<%-- 
    Document   : index
    Created on : Oct 21, 2016, 9:59:56 AM
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
        <title>Enter Data for CIM</title>
        <link rel="stylesheet" type="text/css" href="dEntry.css">
        
         <%
            int result = 0;
            if (request.getParameter("enterData") != null) {
                                 
                String name = new String();
                String ntName = new String();
                String ntDesBox = new String();
                String ntaName = new String();
                String ntaDesBox = new String();
                String uuidBox = new String();
                boolean entUUID = false;
                
                if (request.getParameter("uuidEnt") != null) {
                    if (request.getParameter("uuidEnt") == "false") {
                        entUUID = false;
                    }
                    else if (request.getParameter("uuidEnt") == "true") {
                        entUUID = true;
                        if (request.getParameter("enter_uuidBox") != null) {
                            uuidBox = request.getParameter("enter_uuidBox");
                        }
                    }
                }
                
                if (request.getParameter("n_nameBox") != null) {
                    name = request.getParameter("n_nameBox");
                }
                if (request.getParameter("nt_nameBox") != null) {
                    ntName = request.getParameter("nt_nameBox");
                }
                if (request.getParameter("nt_desBox") != null) {
                    ntDesBox = request.getParameter("nt_desBox");
                }
                if (request.getParameter("nta_nameBox") != null) {
                    ntaName = request.getParameter("nta_nameBox");
                }
                if (request.getParameter("nta_desBox") != null) {
                    ntaDesBox = request.getParameter("nta_desBox");
                }
                /* instantiate the connection and move over the data */
                DBConn insData = new DBConn();
                
               result = insData.sendToDB(name, ntName, ntDesBox, ntaName, ntaDesBox, uuidBox, entUUID);
            }
            

                %>
    </head>
    <body onLoad="displayResults()">
        <fieldset style="background-color: #90EE90">
        <h1>Enter Data for CIM Database </h1>
        </fieldset>
        <form name="myForm" action="index.jsp" method="POST">
         <fieldset>
             <legend><strong>Name:</strong></legend>
             Name <br>
                 <input class="des" type="text" name="n_nameBox" id="n_nameBox" placeholder="Name">
                 <select name="n_name" onchange="chooseName(this)">
                     <option value=""></option>
                    <%
                    DBConn getData = new DBConn();
                        
                    getData.connect("n_name", "\"Name\"");
                    for (int i = 0; i < getData.forCombo.size(); i ++) {
                        if (getData.forCombo.get(i).trim().length() == 0) {
                            continue;
                        }   
                        %><option value="<%
                        out.println(getData.forCombo.get(i)); %>
                        "><%
                        out.println(getData.forCombo.get(i)); %>
                        </option> <% 
                    }
                    getData.forCombo.clear();

                    %>
                    
                </select>
        </fieldset>
        <fieldset>
            <legend><strong>Name Type:</strong></legend>
            Name<br>
            <input type="text" name="nt_nameBox" id="nt_nameBox" placeholder="Name">
            <select name="nt_name" onchange="chooseNameType(this)">
                <option value=""></option>
                <%    
                    getData.connect("nt_name", "\"NameType\"");
                    for (int i = 0; i < getData.forCombo.size(); i ++) {
                        if (getData.forCombo.get(i).trim().length() == 0) {
                            continue;
                        }
                        %><option value="<%
                        out.println(getData.forCombo.get(i)); %>
                        "><%
                        out.println(getData.forCombo.get(i)); %>
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
                    for (int i = 0; i < getData.forCombo.size(); i ++) {
                        if (getData.forCombo.get(i).trim().length() == 0) {
                            continue;
                        }
                        %><option value="<%
                        out.println(getData.forCombo.get(i)); %>
                        "><%
                        out.println(getData.forCombo.get(i)); %>
                        </option> <% 
                    }
                    getData.forCombo.clear();

                    %>
            </select><br>
        </fieldset>
        <fieldset>
            <legend><strong>Name Type Authority:</strong></legend>
                Name<br>
                <input type="text" name="nta_nameBox" id="nta_nameBox" placeholder="Name">
                <select name="nta_name" onchange="chooseNTA(this)">
                    <option value=""></option>
                    <%    
                    getData.connect("nta_name", "\"NameTypeAuthority\"");
                    for (int i = 0; i < getData.forCombo.size(); i ++) {
                        if (getData.forCombo.get(i).trim().length() == 0) {
                            continue;
                        }
                        %><option value="<%
                        out.println(getData.forCombo.get(i)); %>
                        "><%
                        out.println(getData.forCombo.get(i)); %>
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
                    for (int i = 0; i < getData.forCombo.size(); i ++) {
                        if (getData.forCombo.get(i).trim().length() == 0) {
                            continue;
                        }
                        %><option value="<%
                        out.println(getData.forCombo.get(i)); %>
                        "><%
                        out.println(getData.forCombo.get(i)); %>
                        </option> <% 
                    }
                    getData.forCombo.clear();

                    %>
                </select><br>
            </fieldset>
            <fieldset>
                <input type ="radio" name ="uuidEnt" value ="false" checked> Randomly Generate UUID<br>
                <input type="radio" name="uuidEnt" value="true"> Enter UUID Here:
                <input type="text" name="enter_uuidBox" placeholder="AAAAAAAA-AAAA-AAAA-AAAA-AAAAAAAAAAAA"><br><br>
            </fieldset>
            <input type="hidden" name="hidden" value="<%= result %>" />
            <input type="submit" value="Submit" name="enterData"> 

        </form>
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
            // -->
            </SCRIPT>
            <br>
            
    </body>
</html>
