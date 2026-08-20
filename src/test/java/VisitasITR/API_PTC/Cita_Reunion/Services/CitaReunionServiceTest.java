package VisitasITR.API_PTC.Cita_Reunion.Services;

import VisitasITR.API_PTC.Cita_Reunion.DTO.CitaReunionDTO;
import VisitasITR.API_PTC.Cita_Reunion.DTO.RespuestaEncargadoDTO;
import VisitasITR.API_PTC.Cita_Reunion.Entity.CitaReunionEntity;
import VisitasITR.API_PTC.Cita_Reunion.Reposity.CitaReunionRepository;
import VisitasITR.API_PTC.Empleado.Entity.EmpleadoEntity;
import VisitasITR.API_PTC.Empleado.Repository.EmpleadoRepository;
import VisitasITR.API_PTC.Encargado.Entity.EncargadoEntity;
import VisitasITR.API_PTC.Estudiante.Entity.EstudianteEntity;
import VisitasITR.API_PTC.Estudiante_Encargado.Entity.EstudianteEncargadoEntity;
import VisitasITR.API_PTC.Estudiante_Encargado.Reposity.EstudianteEncargadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CitaReunionServiceTest {

    @Mock
    private CitaReunionRepository citaReunionRepository;

    @Mock
    private EmpleadoRepository empleadoRepository;

    @Mock
    private EstudianteEncargadoRepository estudianteEncargadoRepository;

    private CitaReunionService service;
    private CitaReunionEntity citaPendiente;

    @BeforeEach
    void preparar() {
        service = new CitaReunionService(
                citaReunionRepository,
                empleadoRepository,
                estudianteEncargadoRepository
        );

        EstudianteEntity estudiante = EstudianteEntity.builder()
                .idEstudiante(3L)
                .estNombre("David")
                .estApellido("Ramírez")
                .build();

        EncargadoEntity encargado = EncargadoEntity.builder()
                .idEncargado(8L)
                .nombre("María")
                .apellido("López")
                .build();

        EstudianteEncargadoEntity relacion = EstudianteEncargadoEntity.builder()
                .idEstudianteEncargado(4L)
                .estudiante(estudiante)
                .encargado(encargado)
                .build();

        EmpleadoEntity docente = EmpleadoEntity.builder()
                .idEmpleado(2L)
                .empNombre("Ricardo")
                .empApellido("De Paz")
                .build();

        citaPendiente = CitaReunionEntity.builder()
                .idCita(10L)
                .empleado(docente)
                .estudianteEncargado(relacion)
                .motivo("Revisión académica")
                .estado("PENDIENTE")
                .observaciones("Conversar sobre el avance del estudiante.")
                .fechaReunion(LocalDateTime.now().plusDays(2))
                .build();

        when(citaReunionRepository.findById(10L)).thenReturn(Optional.of(citaPendiente));
    }

    @Test
    void encargadoPuedeAceptarSuConvocatoriaPendiente() {
        prepararGuardado();
        RespuestaEncargadoDTO respuesta = RespuestaEncargadoDTO.builder()
                .idEstudianteEncargado(4L)
                .estado("ACEPTADA")
                .build();

        CitaReunionDTO actualizada = service.responderComoEncargado(10L, respuesta);

        assertEquals("ACEPTADA", actualizada.getEstado());
        assertEquals(4L, actualizada.getIdEstudianteEncargado());
    }

    @Test
    void encargadoPuedeProponerNuevaFechaYMotivo() {
        prepararGuardado();
        LocalDateTime nuevaFecha = LocalDateTime.now().plusDays(5);
        RespuestaEncargadoDTO respuesta = RespuestaEncargadoDTO.builder()
                .idEstudianteEncargado(4L)
                .estado("POSPUESTA")
                .nuevaFechaReunion(nuevaFecha)
                .motivoReprogramacion("Tengo una cita médica.")
                .build();

        CitaReunionDTO actualizada = service.responderComoEncargado(10L, respuesta);

        assertEquals("POSPUESTA", actualizada.getEstado());
        assertEquals(nuevaFecha, actualizada.getFechaReunion());
        assertTrue(actualizada.getObservaciones().contains("Tengo una cita médica."));
    }

    @Test
    void encargadoNoPuedeResponderUnaConvocatoriaAjena() {
        RespuestaEncargadoDTO respuesta = RespuestaEncargadoDTO.builder()
                .idEstudianteEncargado(999L)
                .estado("ACEPTADA")
                .build();

        ResponseStatusException excepcion = assertThrows(
                ResponseStatusException.class,
                () -> service.responderComoEncargado(10L, respuesta)
        );

        assertEquals(HttpStatus.FORBIDDEN, excepcion.getStatusCode());
        assertEquals("PENDIENTE", citaPendiente.getEstado());
    }

    private void prepararGuardado() {
        when(citaReunionRepository.save(any(CitaReunionEntity.class)))
                .thenAnswer(invocacion -> invocacion.getArgument(0));
    }
}
