package VisitasITR.API_PTC.Recuperacion.Services;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

// Manda el codigo por correo. Si el envio falla lo dejo anotado en el log
// pero no se lo digo al usuario, para no revelar si el correo existe.
@Service
@RequiredArgsConstructor
public class CorreoRecuperacionService {

    private static final Logger log = LoggerFactory.getLogger(CorreoRecuperacionService.class);

    private final JavaMailSender mailSender;

    @Value("${app.mail.remitente:}")
    private String remitente;

    @Value("${app.recuperacion.minutos:10}")
    private int minutos;

    public void enviarCodigo(String correo, String codigo) {
        SimpleMailMessage mensaje = new SimpleMailMessage();

        if (!remitente.isBlank()) {
            mensaje.setFrom(remitente);
        }

        mensaje.setTo(correo);
        mensaje.setSubject("Código para recuperar tu contraseña - Visitas ITR");
        mensaje.setText(
                "Recibimos una solicitud para cambiar la contraseña de esta cuenta.\n\n"
                        + "Tu código es: " + codigo + "\n\n"
                        + "Vence en " + minutos + " minutos y solo se puede usar una vez.\n\n"
                        + "Si no fuiste vos, ignora este mensaje: la contraseña no cambia "
                        + "mientras nadie use el código."
        );

        try {
            mailSender.send(mensaje);
        } catch (Exception excepcion) {
            log.error("No se pudo enviar el codigo de recuperacion a {}", correo, excepcion);
        }
    }
}
