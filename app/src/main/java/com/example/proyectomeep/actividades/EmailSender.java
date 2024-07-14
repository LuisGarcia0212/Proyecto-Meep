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

    private static final String TAG = "EmailSender";
    private static final String EMAIL = "beto_186_15@hotmail.com"; // Cambia esto por tu dirección de correo Hotmail/Outlook
    private static final String PASSWORD = "Tobe15975312345689@@"; // Cambia esto por tu contraseña

    public static void sendEmail(final String recipient, final String subject, final String body) {
        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    // Configurar propiedades para la sesión de correo
                    Properties properties = new Properties();
                    properties.put("mail.smtp.auth", "true");
                    properties.put("mail.smtp.starttls.enable", "true");
                    properties.put("mail.smtp.host", "smtp-mail.outlook.com"); // Servidor SMTP de Outlook (Hotmail)
                    properties.put("mail.smtp.port", "587"); // Puerto para TLS

                    // Iniciar sesión usando autenticación
                    Session session = Session.getInstance(properties, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(EMAIL, PASSWORD);
                        }
                    });

                    // Crear mensaje de correo
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(EMAIL));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
                    message.setSubject(subject);
                    message.setText(body);

                    // Enviar mensaje
                    Transport.send(message);

                    Log.d(TAG, "Correo enviado satisfactoriamente a " + recipient);
                    return true;

                } catch (MessagingException e) {
                    Log.e(TAG, "Error al enviar el correo", e);
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean success) {
                super.onPostExecute(success);
                if (success) {
                    // Manejar el éxito (opcional)
                } else {
                    // Manejar el fallo (opcional)
                }
            }
        }.execute();
    }
}
