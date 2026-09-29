package VisitasITR.API_PTC.Auth.DTO;

//Junta las dos cosas que devuelve el login: los datos para el frontend
//y el token, que el controlador mete en la cookie.
public record SesionCreada(AuthResponseDTO datos, String token) {
}
