<%-- 
    Document   : index
    Created on : Aug 15, 2016, 3:49:21 PM
    Author     : peba007
--%>

<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>
            Enter Data for CIM
        </title>
    </head>
    <body>
        <!--form name="Table" method="post" action="DatabaseServlet">
            <input type="submit" value="See Tables" name="seeTables">
        </form-->
        <form name ="Test" method ="post" action ="DatabaseServlet">
            <fieldset>
                Name <br>
                <input type="text" name="n_nameBox">
                <!--jsp:useBean id="forCombo" class="cimdatabase.CIMdatabase" scope="page"-->
                    <!--select>
                        <c:forEach var="item" items="${obj.items}">
                            <option>${item}</option>
                        </c:forEach>
                    </select-->
                <!--/jsp:useBean-->
            </fieldset>
            <fieldset>
                Name Type<br><br>
                Name<br>
                <input type="text" name="nt_nameBox"><br><br>
                Description<br>
                <input type="text" name="nt_desBox"><br>
            </fieldset>
            <fieldset>
                Name Type Authority<br><br>
                Name<br>
                <input type="text" name="nta_nameBox"><br><br>
                Description<br>
                <input type="text" name="nta_desBox"><br>
            </fieldset>
            <fieldset>
                <input type ="radio" name ="uuidEnt" value ="false" checked> Randomly Generate UUID<br>
                <input type="radio" name="uuidEnt" value="true"> Enter UUID Here:
                <input type="text" name="enter_uuidBox"><br><br>
            </fieldset>
            <input type="submit" value="Enter" name="enterData">
        </form>
    </body>
</html>
