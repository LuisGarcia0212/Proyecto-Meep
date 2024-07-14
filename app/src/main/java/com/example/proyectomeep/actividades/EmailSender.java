package com.example.proyectomeep.actividades;

import android.os.AsyncTask;
import android.util.Log;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

    private static final String EMAIL = "beto_186_15@hotmail.com"; // Cambia esto por tu dirección de correo Hotmail/Outlook
    private static final String PASSWORD = "Tobe15975312345689@@"; // Cambia esto por tu contraseña

    public static void sendEmail(final String recipient, final String subject, final String body) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Properties properties = new Properties();
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.starttls.enable", "true");
                properties.put("mail.smtp.host", "smtp-mail.outlook.com"); // Servidor SMTP de Outlook (Hotmail)
                properties.put("mail.smtp.port", "587"); // Puerto para TLS

                Session session = Session.getInstance(properties, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(EMAIL, PASSWORD);
                    }
                });

                try {
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(EMAIL));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
                    message.setSubject(subject);
                    message.setText(body);

                    Transport.send(message);
                    Log.d("EmailSender", "Correo enviado satisfactoriamente a " + recipient);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }
}
