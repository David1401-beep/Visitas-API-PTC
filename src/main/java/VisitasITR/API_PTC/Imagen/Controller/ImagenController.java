package VisitasITR.API_PTC.Imagen.Controller;

import VisitasITR.API_PTC.Imagen.Entity.Imagen;
import VisitasITR.API_PTC.Imagen.Repository.ImagenRepository;
import VisitasITR.API_PTC.Imagen.Services.CloudinaryService;
import VisitasITR.API_PTC.Response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/imagenes")
@RequiredArgsConstructor
public class ImagenController {

    private final CloudinaryService cloudinaryService;
    private final ImagenRepository imagenRepository;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<ApiResponse<List<Imagen>>> listar() {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Imagenes obtenidas", imagenRepository.findAll()));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<Imagen>> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam(defaultValue = "visitas-itr") String folder) {

        try {
            //1. Subir el archivo a Cloudinary
            Map resultado = cloudinaryService.upload(file, folder);

            String url = (String) resultado.get("secure_url");
            String publicId = (String) resultado.get("public_id");

            //2. Guardar los datos en la base
            Imagen imagen = Imagen.builder()
                    .imgNombre(file.getOriginalFilename())
                    .imgUrl(url)
                    .imgCloudinaryId(publicId)
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ApiResponse<>(true, "Imagen subida", imagenRepository.save(imagen)));

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                    "No fue posible subir el archivo: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','DOCENTE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        Imagen imagen = imagenRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Imagen no encontrada: " + id));

        try {
            //1. Borrar el archivo de Cloudinary
            cloudinaryService.delete(imagen.getImgCloudinaryId());

            //2. Borrar el registro de la base
            imagenRepository.delete(imagen);

            return ResponseEntity.ok(new ApiResponse<>(true, "Imagen eliminada", null));

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                    "No fue posible eliminar el archivo en Cloudinary.");
        }
    }
}
