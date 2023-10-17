package com.tea.rest;

import com.sun.mail.smtp.SMTPTransport;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Notification {
    private final  String USERNAME = "positivityservice";
    private final  String FROM ="positivityservice@gmail.com";
    private final  String PSW = "NXs6sL8yydtywAh";
    public Notification(String recipient,String subject, String msg){

        String host = "smtp.gmail.com";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
//        Will need further configuration upon May 20, 2022 Less trusted apps will no longer be trusted



        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PSW);
            }
        });
        session.setDebug(true);

        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM));
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(recipient));
            message.setSubject(subject);
            message.setText(msg);
            Transport.send(message);


            System.out.println("Sent message successfully....");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }



}
