package com.tea.rest;


import com.mongodb.client.MongoDatabase;
import mongo.MongoProducer;
import mongo.UserCollection;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.Timer;

public class Mail {

//    public static void main(String[] args) throws InterruptedException {
//        try{
//            UserCollection collection = new UserCollection();
//            User a = new User();
//            a.setEmail("email");
//            a.setToken_id("email");
//            a.setNotify(true);
//            a.setAdmin(true);
//            collection.addUser(a);
////            System.out.println(database.getCollection("Users"));
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//
//
//
//    }
}
