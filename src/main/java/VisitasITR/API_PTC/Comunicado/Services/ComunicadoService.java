package VisitasITR.API_PTC.Comunicado.Services;

import VisitasITR.API_PTC.Comunicado.DTO.ComunicadoDTO;
import VisitasITR.API_PTC.Comunicado.Entity.ComunicadoEntity;
import VisitasITR.API_PTC.Comunicado.Repository.ComunicadoRepository;
import VisitasITR.API_PTC.Docente.Entity.DocenteEntity;
import VisitasITR.API_PTC.Docente.Repository.DocenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ComunicadoService {

    private final ComunicadoRepository repository;
    private final DocenteRepository docenteRepository;

    // Marca de un comunicado visible. 'N' seria uno retirado.
    private static final String ACTIVO = "S";
    private static final String RETIRADO = "N";

    public List<ComunicadoDTO> obtenerActivos() {
        return repository.findByComActivoOrderByComFechaDesc(ACTIVO).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ComunicadoDTO> obtenerPorDocente(Long idDocente) {
        if (!docenteRepository.existsById(idDocente)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Docente no encontrado: " + idDocente);
        }

        return repository.findByDocente_IdDocenteOrderByComFechaDesc(idDocente).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ComunicadoDTO obtenerPorId(Long id) {
        return toDTO(repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Comunicado no encontrado: " + id)));
    }

    public List<ComunicadoDTO> buscar(String texto) {
        if (texto == null || texto.isBlank()) {
            return obtenerActivos();
        }

        String busqueda = texto.trim();

        List<ComunicadoDTO> porMensaje = repository
                .findByComActivoAndComMensajeContainingIgnoreCaseOrderByComFechaDesc(ACTIVO, busqueda)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

        List<ComunicadoDTO> porDocente = obtenerActivos().stream()
                .filter(dto -> dto.getNombreDocente() != null &&
                        dto.getNombreDocente().toLowerCase().contains(busqueda.toLowerCase()))
                // Evita repetir los que ya salieron por el mensaje.
                .filter(dto -> porMensaje.stream()
                        .noneMatch(previo -> previo.getIdComunicado().equals(dto.getIdComunicado())))
                .collect(Collectors.toList());

        porMensaje.addAll(porDocente);
        return porMensaje;
    }

    @Transactional
    public ComunicadoDTO crear(ComunicadoDTO dto) {
        ComunicadoEntity entity = ComunicadoEntity.builder()
                .docente(buscarDocente(dto.getIdDocente()))
                .comMensaje(dto.getComMensaje().trim())
                .comActivo(ACTIVO)
                .build();

        return toDTO(repository.save(entity));
    }

    @Transactional
    public ComunicadoDTO actualizar(Long id, ComunicadoDTO dto) {
        ComunicadoEntity entity = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Comunicado no encontrado: " + id));

        // Solo quien publico el comunicado puede modificarlo: si otro
        // docente envia su ID, la operacion se rechaza.
        if (!entity.getDocente().getIdDocente().equals(dto.getIdDocente())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Solo el docente que publico el comunicado puede modificarlo.");
        }

        entity.setComMensaje(dto.getComMensaje().trim());

        if (dto.getComActivo() != null) {
            validarActivo(dto.getComActivo());
            entity.setComActivo(dto.getComActivo());
        }

        return toDTO(repository.save(entity));
    }

    @Transactional
    public ComunicadoDTO retirar(Long id) {
        ComunicadoEntity entity = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Comunicado no encontrado: " + id));

        entity.setComActivo(RETIRADO);
        return toDTO(repository.save(entity));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Comunicado no encontrado: " + id);
        }

        repository.deleteById(id);
    }

    // Apoyo
    private DocenteEntity buscarDocente(Long idDocente) {
        return docenteRepository.findById(idDocente).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Docente no encontrado: " + idDocente));
    }

    private void validarActivo(String valor) {
        if (!List.of(ACTIVO, RETIRADO).contains(valor)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El campo comActivo solo acepta 'S' o 'N'.");
        }
    }

    private ComunicadoDTO toDTO(ComunicadoEntity entity) {
        DocenteEntity docente = entity.getDocente();

        return ComunicadoDTO.builder()
                .idComunicado(entity.getIdComunicado())
                .idDocente(docente.getIdDocente())
                .nombreDocente(docente.getDocNombre() + " " + docente.getDocApellido())
                .comMensaje(entity.getComMensaje())
                .comFecha(entity.getComFecha())
                .comActivo(entity.getComActivo())
                .build();
    }
}