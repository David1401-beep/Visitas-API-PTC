package VisitasITR.API_PTC.Config;

// Patrones de validacion que comparten varios DTO. Estan aqui para que la
// regla sea una sola: si cambia, se cambia en este archivo y no en cada DTO.
//
// Es la misma regla que usa js/validaciones.js en la web y en la app. La del
// navegador solo avisa antes de enviar; esta es la que manda, porque a la API
// se le puede llamar directo sin pasar por ningun formulario.
public final class Patrones {

    private Patrones() {
    }

    // Letras con acentos y ñ, espacios, apostrofe y guion (D'Angelo,
    // Pérez-Luna). Sin numeros ni simbolos.
    public static final String SOLO_LETRAS = "^[A-Za-zÁÉÍÓÚÜÑáéíóúüñ '’-]+$";
}
