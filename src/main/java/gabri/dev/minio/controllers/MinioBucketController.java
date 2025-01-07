package gabri.dev.minio.controllers;

import gabri.dev.minio.services.MinioBucketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador para manejar las operaciones relacionadas con los buckets de MinIO.
 * Proporciona endpoints para listar y eliminar buckets en el servicio de MinIO.
 *
 * @author Gabriel Scipioni
 */
@RestController
@RequestMapping("/minio/bucket")
public class MinioBucketController {

    /**
     * Servicio encargado de las operaciones sobre los buckets de MinIO.
     */
    @Autowired
    MinioBucketService service;

    /**
     * Endpoint para obtener la lista de los buckets disponibles en MinIO.
     *
     * @return Una respuesta con el código HTTP 200 (OK) que contiene una lista de nombres de los buckets.
     * @throws Exception Si ocurre un error al listar los buckets.
     */
    @Operation(
            summary = "Listar buckets",
            description = "Obtiene una lista con los nombres de todos los buckets disponibles en MinIO."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de buckets obtenida correctamente."
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno al intentar obtener los buckets."
            )
    })
    @GetMapping("/list")
    public ResponseEntity<List<String>> listBuckets() throws Exception {
        return ResponseEntity.ok(service.listBuckets());
    }

    /**
     * Endpoint para eliminar un bucket de MinIO.
     *
     * @param bucket El nombre del bucket que se desea eliminar.
     * @return Una respuesta con el código HTTP 200 (OK) si el bucket fue eliminado correctamente.
     * @throws Exception Si ocurre un error al eliminar el bucket.
     */
    @Operation(
            summary = "Eliminar bucket",
            description = "Elimina un bucket específico de MinIO."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Bucket eliminado correctamente."
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error al proporcionar el nombre del bucket."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "El bucket no fue encontrado."
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno al intentar eliminar el bucket."
            )
    })
    @GetMapping("/delete")
    public ResponseEntity deleteBucket(@RequestParam String bucket) throws Exception {
        service.deleteBucket(bucket);
        return ResponseEntity.ok().build();
    }
}
