package VisitasITR.API_PTC.Auth.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

//Datos que le devuelvo al frontend. Aqui no mando el token,
//porque ese va aparte en la cookie.
@Getter
@Builder
@AllArgsConstructor
public class AuthResponseDTO {

    private Long idUsuario;
    private String email;
    private String rol;
    private long expiraEnSegundos;
}
