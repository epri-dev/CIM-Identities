### CreateTable

This should be the first program run after installing PostgreSQL.  However, before running it, you will need to run the
driver file located at dist/lib/postgresql-9.2-1002.jdbc4.jar

After successfully running the driver file, run the CreateTable jar file located at CreateTable/dist/CreateTable.jar
This will prompt you for the password you set up for PostgreSql and upon successful password entry will create a
database with the following 5 tables:
 *  IdentifiedObject
 *  Identity
 *  Name
 *  NameType
 *  NameTypeAuthority

### CIMIdentities-w

* This is the functional version of the contract-first approach of creating a SOAP Web Service following the IEC 61968-100 standard.  
This project consists of two separate web services:  one for handling create/change/delete requests, and another for get/query requests.  
This can be used through a client application or SOAP UI.



### CIMIdentities-c

* This is the client application for CIMIdentities-w.  
It contains a launchable executable. 
By setting the appropriate URL in place of localhost and maintaining a deployed web service, 
this can be accessed from any Internet-connected device, as long as the application has been downloaded onto the machine that needs to access the database.



### CIMIdentitiesJSP

* Meant to be viewed via web browser, this SOAP client can be hosted ona  single machine and access via web browser instead of downloaded onto each client.

For additional testing, SOAPUI was used.

### Check the Wiki

If you're interested in some light reading about how this SOAP web service was greated, head over to the wiki for some explanations and other documentation on getting things running.
