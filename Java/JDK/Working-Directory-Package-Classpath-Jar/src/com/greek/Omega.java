package com.greek;

// [export CLASSPATH="E:\Working\Java\Working-Directory-Package-Classpath-Jar\mysql-connector-java\mysql-connector-java-5.1.39-bin.jar;.;"]
// java -cp "src;E:\Working\Java\Working-Directory-Package-Classpath-Jar\mysql-connector-java\mysql-connector-java-5.1.39-bin.jar;.;" com.greek.Omega

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
// import java.sql.Statement;
// import java.sql.ResultSet;
import java.sql.DatabaseMetaData;

class Omega {
    // WARN: Establishing SSL connection without server's identity verification is not recommended. 
    // According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. 
    // For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. 
    // You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification. 
    // useSSL=false
    // private final static String dbURL = "jdbc:mysql://localhost:3306/sys?user=root&password=My$ql@Server#5.7&useSSL=false";
    private final static String url = "jdbc:mysql://localhost:3306/sys?useSSL=false";
    private final static String user = "root";
    private final static String password = "My$ql@Server#5.7";    

    public static void main(String[] args){
        try {      
	    // Connection conn = DriverManager.getConnection(dbURL);
            Connection conn = DriverManager.getConnection(url, user, password);
	    if (conn != null) {
	        System.out.println("Connected to MySQL Server successfully.");	
	        DatabaseMetaData mtdt = conn.getMetaData();
	        System.out.println("URL in use: " + mtdt.getURL());
	        System.out.println("User name: " + mtdt.getUserName());
	        System.out.println("DBMS name: " + mtdt.getDatabaseProductName());
	        System.out.println("DBMS version: " + mtdt.getDatabaseProductVersion());
	        System.out.println("Driver name: " + mtdt.getDriverName());
	        System.out.println("Driver version: " + mtdt.getDriverVersion());
	        System.out.println("supp. SQL Keywords: " + mtdt.getSQLKeywords());    
	    }
            conn.close();
        }
        catch(SQLException e) { 
  	    e.printStackTrace();
        }
        System.out.println("Omega");
    }
}