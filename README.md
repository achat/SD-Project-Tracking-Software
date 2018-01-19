# SD-Project-Tracking-Software

**PLEASE READ**     :heavy_exclamation_mark:

Before you run this application you should have a **locahost web server** and [JAVA](https://www.java.com/en/) installed (min 1.4.0).

If you don't know how, read [this](https://github.com/tpliakas/SD-Project-Tracking-Software/wiki/Create-your-localhost-web-server.) guide in our wiki.

In the project folder you can see the database file, which must be imported to your local database.

If you don't know how, read [this](https://github.com/tpliakas/SD-Project-Tracking-Software/wiki/Importing-a-database-with-phpmyadmin) guide in our wiki.

- - - -

If you didn't set any port or set any other than 8888 when you configured your localhost, then you **should change** the **src/JavaConnector.java** file. Find the line #6 where you see this code: 
```
String jdbcUrl = "jdbc:mysql://localhost:8888/sdptsdb?useSSL=false";
```
If you didn't set any port, change to:
```
String jdbcUrl = "jdbc:mysql://localhost/sdptsdb?useSSL=false";
```
If you set any other port, replace 8888 to your port.

On line #6 & #7, change
```
String user = "root";
String pass = "";
```
to match your database username & password.

After these steps you should be able to run this application withount any problem.

  - - - -

If you still having issues,  do not hesitate to contact any of the contributors or to open a new [Issue.](https://github.com/tpliakas/SD-Project-Tracking-Software/issues)

  - - - -


 :heavy_plus_sign:    **UPCOMING CHANGES for v 1.10**
- Convert passwords to hash for increased security.
- When user clicks on task, details would show beneath it.
- After adding a task, database will not not refresh and the object will be returned directly to main window.
- Explanation text will be be added when exception exists.
