package VisitasITR.API_PTC.Estudiante.Controller;

import VisitasITR.API_PTC.Estudiante.DTO.EstudianteDTO;
import VisitasITR.API_PTC.Estudiante.Service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estudiante")
@CrossOrigin("*")
public class EstudianteController {
    @Autowired
    private EstudianteService service;
    @GetMapping
    public ResponseEntity<List<EstudianteDTO>> listar(){
        return ResponseEntity.ok(service.listar());
    }
    @GetMapping("/{id}")
    public ResponseEntity<EstudianteDTO> guardar(@RequestBody EstudianteDTO dto){
        return ReponseEntity.ok(service.guardar(dto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<EstudianteDTO> actualizar(@PathVariable Integer id,
                                                    @RequestBody EstudianteDTO dto){
        return ResponseEntity.ok(service.actualizar(id,dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {

        service.eliminar(id);

        return ResponseEntity.ok("Estudiante eliminado correctamente");

    }

}

