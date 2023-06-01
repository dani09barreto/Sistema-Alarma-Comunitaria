package com.edu.alarmsystem.models;

import android.os.AsyncTask;
import android.util.Log;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmailTask extends AsyncTask<Void, Void, Boolean> {

    private static final String TAG = "SendEmailTask";

    private final String remitente;
    private final String password;
    private final String destinatario;
    private final String asunto;
    private final String cuerpoMensaje;

    public SendEmailTask(String remitente, String password, String destinatario, String asunto, String cuerpoMensaje) {
        this.remitente = remitente;
        this.password = password;
        this.destinatario = destinatario;
        this.asunto = asunto;
        this.cuerpoMensaje = cuerpoMensaje;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            // Configurar las propiedades del servidor de correo
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            // Crear una sesión con autenticación
            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(remitente, password);
                }
            });

            // Crear un mensaje de correo
            Message mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress(remitente));
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            mensaje.setSubject(asunto);
            mensaje.setText(cuerpoMensaje);

            // Enviar el mensaje
            Transport.send(mensaje);

            return true;
        } catch (AddressException e) {
            Log.e(TAG, "Error de dirección de correo electrónico", e);
            return false;
        } catch (MessagingException e) {
            Log.e(TAG, "Error al enviar el correo electrónico", e);
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        // Aquí puedes manejar el resultado del envío del correo electrónico
        if (result) {
            Log.i(TAG, "El correo electrónico ha sido enviado con éxito.");
        } else {
            Log.e(TAG, "Error al enviar el correo electrónico.");
        }
    }
}