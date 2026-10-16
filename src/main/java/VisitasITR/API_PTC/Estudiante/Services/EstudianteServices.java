package VisitasITR.API_PTC.Estudiante.Services;

import VisitasITR.API_PTC.Academica.Entity.AcademicaEntity;
import VisitasITR.API_PTC.Academica.Repository.AcademicaRepository;
import VisitasITR.API_PTC.Estudiante.DTO.EstudianteDTO;
import VisitasITR.API_PTC.Estudiante.Entity.EstudianteEntity;
import VisitasITR.API_PTC.Estudiante.Repository.EstudianteRepository;
import VisitasITR.API_PTC.Grado.Entity.GradoEntity;
import VisitasITR.API_PTC.Grado.Repository.GradoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EstudianteServices {

    private final EstudianteRepository repository;
    private final AcademicaRepository academicaRepository;
    private final GradoRepository gradoRepository;
    private final PasswordEncoder passwordEncoder;



    // Obtener estudiantes
    public List<EstudianteDTO> obtenerTodos() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }



    // Obtener por ID estudiante
    public EstudianteDTO obtenerPorId(Long id) {

        return toDTO(repository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estudiante no encontrado: " + id))
        );
    }


    // crear Estudiante
    @Transactional
    public EstudianteDTO crear(EstudianteDTO dto) {

        // Verificar correo repetido
        if (repository.existsByEstCorreo(dto.getEstCorreo())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El correo ya existe."
            );
        }

        if (repository.existsByEstCodigo(dto.getEstCodigo())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El código de estudiante ya existe."
            );
        }

        // La contraseña debe venir al crear al estudiante
        if (dto.getEstPassword() == null ||
                dto.getEstPassword().isBlank()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La contraseña del estudiante es obligatoria."
            );
        }

        AcademicaEntity academica = buscarAcademica(dto.getIdAcademica());
        GradoEntity grado = buscarGrado(dto.getIdGrado());
        validarSeccionDelGrado(grado, academica);

        EstudianteEntity entity = EstudianteEntity.builder()

                .estNombre(dto.getEstNombre())
                .estApellido(dto.getEstApellido())
                .estCorreo(dto.getEstCorreo())

                // BCrypt automático
                .estPassword(
                        passwordEncoder.encode(dto.getEstPassword())
                )

                .estGrado(dto.getEstGrado())
                .estSeccion(dto.getEstSeccion())
                .estEspecialidad(dto.getEstEspecialidad())
                .estCodigo(dto.getEstCodigo())

                .estRol(
                        dto.getEstRol() != null &&
                                !dto.getEstRol().isBlank()
                                ? dto.getEstRol()
                                : "ESTUDIANTE"
                )

                .academica(academica)
                .grado(grado)
                .build();

        return toDTO(repository.save(entity));
    }


    // Actualizar
    @Transactional
    public EstudianteDTO actualizar(Long id, EstudianteDTO dto) {

        EstudianteEntity entity = repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Estudiante no encontrado: " + id
                        )
                );

        entity.setEstNombre(dto.getEstNombre());
        entity.setEstApellido(dto.getEstApellido());
        entity.setEstCorreo(dto.getEstCorreo());
        entity.setEstGrado(dto.getEstGrado());
        entity.setEstSeccion(dto.getEstSeccion());
        entity.setEstEspecialidad(dto.getEstEspecialidad());
        entity.setEstCodigo(dto.getEstCodigo());


        if (dto.getEstPassword() != null &&
                !dto.getEstPassword().isBlank()) {

            entity.setEstPassword(
                    passwordEncoder.encode(dto.getEstPassword())
            );
        }


        if (dto.getEstRol() != null &&
                !dto.getEstRol().isBlank()) {

            entity.setEstRol(dto.getEstRol());
        }


        AcademicaEntity academica = buscarAcademica(dto.getIdAcademica());
        GradoEntity grado = buscarGrado(dto.getIdGrado());
        validarSeccionDelGrado(grado, academica);

        entity.setAcademica(academica);
        entity.setGrado(grado);


        return toDTO(repository.save(entity));
    }

    private AcademicaEntity buscarAcademica(Long id) {
        return academicaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Académica no encontrada"));
    }

    private GradoEntity buscarGrado(Long id) {
        return gradoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Grado no encontrado"));
    }

    // En tercer ciclo las secciones son solo una letra (A, B, C...) y en
    // bachillerato llevan letra y numero (A1, B2...). La pagina ya filtra la
    // lista, pero aqui se revisa por si alguien manda otra combinacion.
    private void validarSeccionDelGrado(GradoEntity grado, AcademicaEntity academica) {
        String nivel = grado.getNivel().getNivel().toUpperCase();
        String seccion = academica.getAcademica().trim().toUpperCase();

        boolean valida;

        if (nivel.contains("TERCER CICLO")) {
            valida = seccion.matches("[A-Z]");
        } else if (nivel.contains("BACHILLERATO")) {
            valida = seccion.matches("[A-Z]\\d");
        } else {
            return;
        }

        if (!valida) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La sección " + seccion + " no corresponde a " + grado.getNivel().getNivel().toLowerCase() + "."
            );
        }
    }



    // Eliminar
    @Transactional
    public void eliminar(Long id) {

        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Estudiante no encontrado: " + id
            );
        }

        repository.deleteById(id);
    }

    private EstudianteDTO toDTO(EstudianteEntity entity) {

        return EstudianteDTO.builder()
                .idEstudiante(entity.getIdEstudiante())
                .estNombre(entity.getEstNombre())
                .estApellido(entity.getEstApellido())
                .estCorreo(entity.getEstCorreo())
                .estGrado(entity.getEstGrado())
                .estSeccion(entity.getEstSeccion())
                .estEspecialidad(entity.getEstEspecialidad())
                .estCodigo(entity.getEstCodigo())
                .estRol(entity.getEstRol())
                .idAcademica(entity.getAcademica().getIdAcademica())
                .nombreAcademica(entity.getAcademica().getAcademica())
                .idGrado(entity.getGrado().getIdGrado())
                .nombreGrado(entity.getGrado().getGrado())
                .build();
    }

    //Listar paginado
    public Page<EstudianteDTO> obtenerPaginado(Pageable pageable) {
        return repository.findAll(pageable).map(this::toDTO);
    }
}
