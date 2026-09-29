package VisitasITR.API_PTC.Recuperacion.Controller;

import VisitasITR.API_PTC.Recuperacion.DTO.CambiarPasswordDTO;
import VisitasITR.API_PTC.Recuperacion.DTO.SolicitarCodigoDTO;
import VisitasITR.API_PTC.Recuperacion.DTO.VerificarCodigoDTO;
import VisitasITR.API_PTC.Recuperacion.Services.RecuperacionService;
import VisitasITR.API_PTC.Response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Recuperacion de contrasena. Son rutas publicas: quien las usa todavia
// no puede iniciar sesion.
@RestController
@RequestMapping("/api/v1/auth/recuperacion")
@RequiredArgsConstructor
public class RecuperacionController {

    private final RecuperacionService recuperacionService;

    // Siempre responde lo mismo, exista o no el correo.
    @PostMapping("/solicitar")
    public ResponseEntity<ApiResponse<Void>> solicitar(
            @Valid @RequestBody SolicitarCodigoDTO peticion
    ) {
        recuperacionService.solicitar(peticion);

        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Si el correo está registrado, enviamos un código para continuar.",
                null
        ));
    }

    @PostMapping("/verificar")
    public ResponseEntity<ApiResponse<Void>> verificar(
            @Valid @RequestBody VerificarCodigoDTO peticion
    ) {
        recuperacionService.verificar(peticion);

        return ResponseEntity.ok(new ApiResponse<>(
                true, "Código correcto.", null));
    }

    @PostMapping("/cambiar")
    public ResponseEntity<ApiResponse<Void>> cambiar(
            @Valid @RequestBody CambiarPasswordDTO peticion
    ) {
        recuperacionService.cambiar(peticion);

        return ResponseEntity.ok(new ApiResponse<>(
                true, "Su contraseña fue cambiada. Ya puede iniciar sesión.", null));
    }
}
