package VisitasITR.API_PTC.Imagen.Services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    private static final long TAMANO_MAXIMO = 10 * 1024 * 1024;

    private static final List<String> TIPOS_PERMITIDOS = List.of(
            "image/jpeg", "image/png", "image/webp", "application/pdf"
    );

    //Sube un archivo a la carpeta que le indique en Cloudinary
    public Map upload(MultipartFile multipartFile, String folder) throws IOException {
        validar(multipartFile);

        Map params = ObjectUtils.asMap(
                "folder", folder,
                "resource_type", "auto"
        );

        return cloudinary.uploader().upload(multipartFile.getBytes(), params);
    }

    //Elimina un archivo de Cloudinary usando su public_id
    public Map delete(String publicId) throws IOException {
        return cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }

    //Reviso el archivo antes de mandarlo, para no subir cualquier cosa
    private void validar(MultipartFile archivo) {
        if (archivo == null || archivo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Debe adjuntar un archivo.");
        }

        if (archivo.getSize() > TAMANO_MAXIMO) {
            throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE,
                    "El archivo supera el limite de 10 MB.");
        }

        if (!TIPOS_PERMITIDOS.contains(archivo.getContentType())) {
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                    "Solo se admiten imagenes JPG, PNG, WEBP o archivos PDF.");
        }
    }
}
