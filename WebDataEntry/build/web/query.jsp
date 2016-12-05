<%-- 
    Document   : query
    Created on : Nov 28, 2016, 9:05:33 AM
    Author     : pdlo003
--%>

<%@page import="java.util.ArrayList"%>
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
            <p>Which rows would you like to see?</p>
            <fieldset><legend>Select:</legend>
            <input type="checkbox" name="fields" value="uuid">UUID<br>
            <input type="checkbox" name="fields" value="name">Name<br>
            <input type="checkbox" name="fields" value="ntName">NameType Name<br>
            <input type="checkbox" name="fields" value="ntDes">NameType Description<br>
            <input type="checkbox" name="fields" value="ntaName">NameType Authority Name<br>
            <input type="checkbox" name="fields" value="ntaDes">NameType Authority Description<br>
            </fieldset><br>
            <fieldset><legend>Where: (feature currently unavailable)</legend>
                <div id="container">
                <select name="field">
                    <option value="UUID">UUID</option>
                    <option value="Name">Name</option>
                    <option value="ntname">NameType Name</option>
                    <option value="ntdes">NameType Description</option>
                    <option value="ntaname">NameType Authority Name</option>
                    <option value="ntades">NameType Authority Description</option>               
                </select>
                <select name="operator">
                    <option value="=">equals</option>
                    <option value="!=">does not equal</option>
                    <option value="like">like</option>
                    <option value="not like">not like</option>
                    <option value="between">between</option>
                    <option value="in">in</option>
                    <option value="<="><=</option>
                    <option value=">=">>=</option>
                    <option value="<"><</option>
                    <option value=">">></option>
                </select>
                    <input type="text" placeholder="">
                    <select name="condition" id="con">
                        <option value=""></option>
                        <option value="and">and</option>
                        <option value="or">or</option>
                    </select>
                    <button onclick="removeInput(this);">Remove</button>
                    <br><br>
                </div>
                <div id="add_more"></div>
                <a href="javascript:void(0)" onClick="addInput('container', 'add_more');" id="add_more_text">+ Add more</a>
            </fieldset>
            <fieldset>
                <br><br><input type="submit" value="Submit" name="retrieveData"><br><br>
            </fieldset>
            <fieldset>
        <%
            try {
                String host = "jdbc:postgresql://localhost:5432/CIMIdentity";
                String uName = "postgres";
                String password = "epri97!!";
           
                String query = "SELECT DISTINCT";
                String where = "WHERE";
                String orderBy = "ORDER BY";
                
                String[] field = request.getParameterValues("fields");
                ArrayList table = new ArrayList();
                int fieldsize = field.length;
                
                if (field != null) {
                    int lastEntry = field.length - 1;
                    for (int i = 0; i < field.length; i++) {
                    
            //Add table for value to an array to pull for FROM
            //Grab UUID if selected from public."Identity" table
                        if (field[i].equals("uuid")) {
                            table.add( " public.\"Identity\" as i");
                            if (field[i].equals(field[lastEntry])) {
                                query += " i.id_pkey ";
                            } else query += " i.id_pkey,";
                        }
                        
            //Grab "name" if selected from public."Name" as n 
                        if (field[i].equals("name")) {
                            table.add( " public.\"Name\" as n");
                            if (field[i].equals(field[lastEntry])) {
                                query += " n.n_name ";
                            } else query += " n.n_name,";
                            if (field[0].equals("uuid")) {
                            where += " i.id_pkey = n.n_pkey AND";
                            }
                            orderBy += " n.n_name ASC";
                        }
                       
            //Grab "nt_name" from public."NameType" as nt
                        if (field[i].equals("ntName")) {
                            table.add( " public.\"NameType\" as nt");
                            if (field[i].equals(field[lastEntry])) {
                                query += " nt.nt_name ";
                            } else query += " nt.nt_name,";
                            if (field[0].equals("uuid") && (i == 1)) {
                            where += " i.id_pkey = nt.nt_pkey AND";
                            } else if (i > 0) {
                                where += " n.n_pkey = nt.nt_pkey AND";
                                
                            }
                        }
            //Grab "nt_description" from public."NameType" as nt
                        if (field[i].equals("ntDes")) {
                        //check to see that public."NameType" isn't already in our list
                            if (table.contains(" public.\"NameType\" as nt")) {} 
                            else table.add( " public.\"NameType\" as nt");
                        
                            if (field[i].equals(field[lastEntry])) {
                                query += " nt.nt_description ";
                            } else query += " nt.nt_description,";
                            if (i > 0) {
                             if (field[i-1].equals("ntName") == false) { //do same links as ntName if ntName isn't present
                                if (field[0].equals("uuid") && (i == 1)) {
                                    where += " i.id_pkey = nt.nt_pkey AND";
                                } else if ((field[1].equals("name")) || (field[0].equals("name"))) {
                                    where += " n.n_pkey = nt.nt_pkey AND";
                                }
                             }
                            }
                        }
                        
            //Grab "nta_name" from public."NameTypeAuthority" as nta
                        if (field[i].equals("ntaName")) {
                            table.add(" public.\"NameTypeAuthority\" as nta");
                            if (field[i].equals(field[lastEntry])) {
                                query += " nta.nta_name ";
                            } else query += " nta.nta_name,";
                          if (i > 0) {
                            if (field[i-1].equals("ntDes") || field[i-1].equals("ntName")) {
                                where += " nt.nt_pkey = nta.nta_pkey AND";
                            } else if (field[0].equals("uuid")) {
                                where += " i.id_pkey = nta.nta_pkey AND";
                            } else if (field[0].equals("name")) {
                                where += " n.n_pkey = nta.nta_pkey AND";
                            }
                          }
                        }
            //Grab "nta_description" from public."NameTypeAuthority" as nta
                        if (field[i].equals("ntaDes")) {
                        //check to see that public."NameTypeAuthority" isn't already in our list
                            if (table.contains( " public.\"NameTypeAuthority\" as nta")) {
                                ;
                            } else table.add( " public.\"NameTypeAuthority\" as nta");
                            if (field[i].equals(field[lastEntry])) {
                                query += " nta.nta_description ";
                            } else query += " nta.nta_description,";
                       if (i > 0) {
                            if (field[i-1].equals("ntaName")) continue;
                            else if (field[i-1].equals("ntDes") || field[i-1].equals("ntName")) {
                                where += " nt.nt_pkey = nta.nta_pkey AND";
                            } else if (field[0].equals("uuid")) {
                                where += " i.id_pkey = nta.nta_pkey AND";
                            } else if (field[0].equals("name")) {
                                where += " n.n_pkey = nta.nta_pkey AND";
                            }
                        }
                      }   
                    }
                }
                
                //At this point, "query" string has all SELECT statements.  Now
                //we add FROM by looping through our "table" array
                query += " FROM";
                int tlength = table.size() - 1; //used for adding just a space, no comma
                for (int s = 0; s < table.size(); s++) {
                    query += table.get(s);
                    if (s == tlength) query += " ";
                    else query += ",";
                }
                //Now we have SELECT and FROM statements in our query, all that remains
                //is WHERE.  Initially, all tables will be linked together by uuid
               
                if (where.substring(where.length()-3).equals("AND")) {
                    where = where.substring(0, where.length()-3); //remove the last AND
                } else where = ""; //WHERE string is blank if none were created.
                query += where;
                
                if (orderBy.substring(orderBy.length()-2).equals("BY")) {
                   orderBy = ""; 
                } else query += orderBy;
                
                out.println(query);
                Connection conn = DriverManager.getConnection(host, uName, password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                %>
                <table border="6">
                 <tr> 
                <%
                //build the table
                for (int i = 0; i < field.length; i++) {
                    %><td><strong><%
                    if (field[i].equals("uuid")) out.println("UUID");
                    if (field[i].equals("name")) out.println("Name");
                    if (field[i].equals("ntName")) out.println("NameType Name");
                    if (field[i].equals("ntDes")) out.println("NameType Description");
                    if (field[i].equals("ntaName")) out.println("NameTypeAuthority Name");
                    if (field[i].equals("ntaDes")) out.println("NameTypeAuthority Description");
                    %></strong></td> <%
                }
                %> 
                 </tr>
                
        <%
            //create a cell entry for each field present
            
            while (rs.next()) {
        %>
           <tr>
        <%
            for (int i = 0; i < fieldsize; i++) { 
                if (field[i].equals("uuid")) { %>
                <td><%= rs.getString("id_pkey") %></td>
                <% }
                if (field[i].equals("name")) { %>
                <td><%= rs.getString("n_name") %></td>
                <% }
                if (field[i].equals("ntName")) { %>
                <td><%= rs.getString("nt_name") %></td>
                <% }
                if (field[i].equals("ntDes")) { %>
                <td><%= rs.getString("nt_description") %></td>
                <% }
                if (field[i].equals("ntaName")) { %>
                <td><%= rs.getString("nta_name") %></td>
                <% }
                if (field[i].equals("ntaDes")) { %>
                <td><%= rs.getString("nta_description") %></td>
                <% } }
            
        %>
           
           </tr> <%  } %>
           </table>
           <%
            
                rs.close();
                stmt.close();
                conn.close();
           
            }
                catch(Exception e) {
                e.printStackTrace();
           
                }
        %>
            </fieldset>
        </form>  
        <script>
            var counter = 1;

            function addInput(divName, template) {
                
                var newdiv = document.createElement('div');
                newdiv.innerHTML = document.getElementById(divName).innerHTML;
                newdiv.className = 'added';
                document.getElementById(template).appendChild(newdiv);
                counter++;
                

                var selectElements = document.querySelectorAll('select');
                for (var i = 0; i < selectElements.length; i++) {
                    selectElements[i].id = 'id-' + i;
                    selectElements[i].name = 'category' + i;
                }
            }

        function removeInput(obj) {
            if (obj.parentNode.className == 'added') { //so we don't remove parent node
                obj.parentNode.parentNode.removeChild(obj.parentNode);
                counter--;
            }
        }
        </script>
    </div>
  </div>
  </div>
    </body>
</html>
