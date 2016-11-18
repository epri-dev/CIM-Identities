### SQL for tables
You can run this directly in the query tool provided by PgAdmin, but will likely encounter an error for the line "entry integer NOT NULL DEFAULT nextval('"Identity_entry_seq"'::regclass)".  To fix this, a workaround giving you a similar result would be to change that to "entry serial NOT NULL," and then execute the query.  

### CreateTable
A common error encountered when running the executable is "suitable driver not found".  To fix this, place [this](https://github.com/epri-dev/CIM-Identities/blob/master/SQL%20and%20Java%20code%20docs/CreateTable/dist/lib/postgresql-9.2-1002.jdbc4.jar) executable inside a sub-folder titled 'lib' in the main project folder.  Upon successful run of the [launch executable](https://github.com/epri-dev/CIM-Identities/blob/master/SQL%20and%20Java%20code%20docs/CreateTable/src/createtable/CreateTable.java), you should have CIMIdentity installed as a DB if you check PgAdmin, along with the following 5 tables:
* IdentifiedObject
* Identity
* Name
* NameType
* NameTypeAuthority

<b>Project main folder</b>
![CreateTable1](https://github.com/epri-dev/CIM-Identities/blob/12d5d2725a790fcb5e407b5cc70175cab39c2206/Pictures/CreateTable1.PNG)
<b>Project sub-folder 'lib'</b>
![CreateTable2](https://github.com/epri-dev/CIM-Identities/blob/12d5d2725a790fcb5e407b5cc70175cab39c2206/Pictures/CreateTable2.PNG)

### DataEntry
Launching the [executable](https://github.com/epri-dev/CIM-Identities/blob/master/SQL%20and%20Java%20code%20docs/DataEntry/dist/DataEntry.jar) will prompt you first for your password to your PostgreSQL DB, and upon success launch a GUI for inserting data into CIMIdentity DB.  The Combo Boxes on the right will automatically populate with previous entries. 
![Swing App](https://github.com/epri-dev/CIM-Identities/blob/master/Pictures/Swing%20App.png)

The executable must be placed in the 'CreateTable' main project folder or be placed in a separate project folder containing the sub-folder 'lib,' in which the driver executable will be placed.

<b>With executable in the same project folder</b>
![DataEntry1](https://github.com/epri-dev/CIM-Identities/blob/master/Pictures/DataEntry1.PNG)
<b>With executable in a separate project folder</b>
![DataEntry2](https://github.com/epri-dev/CIM-Identities/blob/master/Pictures/DataEntry2.PNG)
<b>Project sub-folder 'lib'</b>
![DataEntry3](https://github.com/epri-dev/CIM-Identities/blob/master/Pictures/DataEntry3.PNG)
