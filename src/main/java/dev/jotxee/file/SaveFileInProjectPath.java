package dev.jotxee.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static org.springframework.util.StringUtils.cleanPath;

public class SaveFileInProjectPath {
    private static final Logger log = LoggerFactory.getLogger(SaveFileInProjectPath.class);

    public void save(MultipartFile file) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss_");
        try {
            // Verificar si el archivo no está vacío
            if (!file.isEmpty()) {
                // Obtener el nombre original del archivo
                String nombreArchivo = cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

                // Construir la ruta completa de destino
                String rutaCompletaDestino = Paths.get("uploads") + File.separator + dtf.format(LocalDateTime.now()) +nombreArchivo;

                // Guardar el archivo en la ruta de destino
                file.transferTo(new File(rutaCompletaDestino));

                log.info("Archivo guardado correctamente.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar la excepción según tus necesidades
        }
    }
}
