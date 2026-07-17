package VisitasITR.API_PTC.Especialidad.Controller;

import VisitasITR.API_PTC.Especialidad.DTO.EspecialidadDTO;
import VisitasITR.API_PTC.Estudiante.Service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/especialidad")
@CrossOrigin("*")

public class EspecialidadController {
    @Autowired
    private EstudianteService service;

    @GetMapping
    public ResponseEntity<List<EspecialidadDTO>> listar(){
        return ResponseEntity.ok(service.listar());
    }
    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadDTO> obtener(@PathVariable Integer id){
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<EspecialidadDTO> guardar(@RequestBody EspecialidadDTO dto){
        return ResponseEntity.ok(service.guardar(dto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<EspecialidadDTO> actualizar(@PathVariable Integer id,
                                                      @RequestBody EspecialidadDTO dto){
        return ResponseEntity.ok(service.actualizar(id, dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id){
        service.eliminar(id);
        return ResponseEntity.ok("especialidad eliminada correctamente");
    }


}
