# CIM-Identities
CIM example database code
Documentation on creating an easily accessible database for CIM using PostgreSQL for the database and Java for the GUI interface.
Potential for web application.

--Table of Contents (of sorts)--

CIM_Database_AccesJar---
    executable version of the Java code. Probably won't work without a matching database.

DatabaseService---
    a Java project that can be built into a SOAP webservice. I can't promise it contains the right dependencies to actually run, but it is certainly a demonstration of some of the architecture required for using web methods, servlets, and JSPs together.

SQL and Java code docs---
    SQL code to generate a database in PostgreSQL and Java code for connecting to the database and creating a Swing form. The "Java SOAP WebService Code" folder contains the code for key files to create a Java webservice on a Glassfish server using SOAP protocol, Java servlets and web methods, and JSP/JSTL webpage markup.
