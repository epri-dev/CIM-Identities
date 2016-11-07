### SQL for tables
You can run this directly in the query tool provided by PgAdmin, but will likely encounter an error for the line "entry integer NOT NULL DEFAULT nextval('"Identity_entry_seq"'::regclass)".  To fix this, a workaround giving you a similar result would be to change that to "entry serial NOT NULL," and then execute the query.  

### CreateTable
A common error encountered when running the executable is "suitable driver not found".  To fix this, launch [this](https://github.com/epri-dev/CIM-Identities/blob/master/SQL%20and%20Java%20code%20docs/CreateTable/dist/lib/postgresql-9.2-1002.jdbc4.jar) executable first to install the driver.  Upon success, you should have CIMIdentity installed as a DB if you check PgAdmin, along with the following 5 tables:
* IdentifiedObject
* Identity
* Name
* NameType
* NameTypeAuthority

### DataEntry
Launching the [executable](https://github.com/epri-dev/CIM-Identities/blob/master/SQL%20and%20Java%20code%20docs/DataEntry/dist/DataEntry.jar) will prompt you first for your password to your PostgreSQL DB, and upon success launch a GUI for inserting data into CIMIdentity DB.  The Combo Boxes on the right will automatically populate with previous entries.
![Swing App](https://github.com/epri-dev/CIM-Identities/blob/master/Pictures/Swing%20App.png)