package VisitasITR.API_PTC.Security;

//Datos del usuario que saco del token. Los guardo aqui para que /auth/me
//los pueda devolver sin volver a consultar la base de datos.
public record UsuarioAutenticado(Long id, String email, String rol) {
}
