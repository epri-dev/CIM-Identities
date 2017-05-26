# CIM-Identities
CIM example database code
Documentation on creating an easily accessible database for CIM using PostgreSQL for the database and Java for the GUI interface.
Potential for web application.

## Table of Contents

### Pictures
Contains all photos used for documentation in the Wiki.

### SQL and Java Code docs
Contains the following subdirectories:
* CreateTable
  * Contains the source code and executable file to create a database called "CIMIdentity" and create tables IdentifiedObject, Identity, Name, NameType, and NameTypeAuthority.
* DataEntry
  * Contains source code and executable for launching a Java Swing Application (GUI) for inserting data into the CIM Database.
* SQL for Tables
  * Contains the source SQL for creating the tables.

### WebDataEntry
* Contains the source code for the JSP used to launch a sample web application for inserting data remotely into the database.  This is NOT a SOAP web service.

### WebApp
* Contains the source code for the basic SOAP web service.  DatabaseService.java is the skeleton upon which the WSDL was generated using JAX-WS.  Note that this is a code-first, contract-last approach, and as a result does not adhere to IEC 61968-100.  

### WebAppClient
* Using the WSDL URL from WebApp, WebAppClient contains the files necessary to launch the web service in the web browser in order to modify the CIM Database.  Similar to WebDataEntry but created as a SOAP web service.

### CIMIdentitiesWebApp
* This is a non-functional initial setup of handling the contract-first approach of creating a SOAP Web Service following the IEC 61968-100 standard.  No changes were made to the WSDL from its initial template, resulting in void methods that returned no information back to the client.

### CIMIdentities-w
* This is the functional version of the contract-first approach of creating a SOAP Web Service following the IEC 61968-100 standard.  This project consists of two separate web services:  one for handling create/change/delete requests, and another for get/query requests.  This can be used through a client application or SOAP UI.

### CIMIdentities-c
* This is the client application for CIMIdentities-w.  It contains a launchable executable behave similarly to the DataEntryFrom application.  By setting the appropriate URL in place of localhost and maintaining a deployed web service, this can be accessed from any Internet-connected device.
