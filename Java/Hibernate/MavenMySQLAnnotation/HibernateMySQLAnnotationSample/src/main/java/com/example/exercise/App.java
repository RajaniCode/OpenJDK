package com.example.exercise;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.UUID;

public class App 
{
    public static void main( String[] args ) throws ParseException {
        SessionFactory sessionFactory;
        sessionFactory = new Configuration()
                .configure() // configures settings from hibernate.cfg.xml
                .buildSessionFactory();
 
        Session session = sessionFactory.openSession();
   
        Transaction tx = session.beginTransaction();
        Users user = new Users();
        String uid = UUID.randomUUID().toString();
        user.setUsername("Foo " + uid); // Unique 

        String strDate = new java.util.Date().toString(); // Sun Nov 06 10:31:54 IST 2016 // Default       
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        java.util.Date parsed = format.parse(strDate);

        // SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // java.util.Date parsed = format.parse("2016-12-31 23:59:59");

        java.sql.Date sqlDate = new java.sql.Date(parsed.getTime());
        user.setLoginDate(sqlDate); 
        java.sql.Time sqlTime = new java.sql.Time(parsed.getTime());
        user.setLoginTime(sqlTime);  
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(parsed.getTime());
        user.setCreatedAt(sqlTimestamp);
        user.setUpdatedAt(sqlTimestamp);

        session.save(user);
        tx.commit();
        session.close();
    }
}