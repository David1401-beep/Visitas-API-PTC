package VisitasITR.API_PTC.Recuperacion.Services;

import VisitasITR.API_PTC.Recuperacion.Repository.RecuperacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

// Lleva la cuenta de los intentos fallidos.
//
// Va aparte y con una transaccion propia a proposito: cuando el codigo esta
// equivocado se lanza una excepcion, y eso deshace la transaccion en curso.
// Si el contador se guardara ahi, el rollback lo borraria y nunca se
// llegaria a bloquear.
@Component
@RequiredArgsConstructor
public class RegistroIntentos {

    private final RecuperacionRepository recuperacionRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sumarFallo(Long idRecuperacion) {
        recuperacionRepository.sumarIntento(idRecuperacion);
    }
}
