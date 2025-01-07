package gabri.dev.minio.controllers;

import gabri.dev.minio.dtos.common.FileInfo;
import gabri.dev.minio.services.MinioBucketService;
import gabri.dev.minio.services.MinioFilesService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;


@RestController
@RequestMapping("/minio/file")
public class MinioFileController {

    /**
     * Servicio encargado de las operaciones sobre los buckets de MinIO.
     */
    @Autowired
    MinioBucketService bucketService;
    /**
     * Servicio encargado de las operaciones sobre los files de los buckets de MinIO.
     */
    @Autowired
    MinioFilesService fileService;

    @Operation(
            summary = "Subir un archivo",
            description = "Permite subir archivos como imágenes, audios, etc.",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "multipart/form-data",
                            schema = @Schema(type = "string", format = "binary")
                    )
            )
    )
    @PostMapping("/uploadfile")
    public ResponseEntity<String> uploadFile(
            @RequestPart("uploadfile") MultipartFile uploadfile,
            @RequestParam String bucket,
            @RequestParam(required = false) String objectName
    ) throws Exception {
        bucketService.createBucket(bucket);
        if (objectName != null) {
            fileService.uploadFile(
                    uploadfile.getInputStream(),
                    bucket,
                    objectName + "/" + uploadfile.getOriginalFilename()
            );
        } else {
            fileService.uploadFile(
                    uploadfile.getInputStream(),
                    bucket,
                    uploadfile.getOriginalFilename()
            );
        }
        return ResponseEntity.ok("Archivo subido exitosamente");
    }


    @GetMapping("/list")
    public ResponseEntity<List<FileInfo>> listFiles(
            @RequestParam String bucket
    )throws Exception{
        return ResponseEntity.ok(fileService.listFiles(bucket));
    }

    @GetMapping("/download")
    public void downloadFile(
            @RequestParam String bucket,
            @RequestParam String objectName,
            HttpServletResponse response
    )throws Exception{
        InputStream stream = fileService.download(bucket,objectName);
        ServletOutputStream output = response.getOutputStream();
        response.setHeader(
                "Disposición de contenido",
                "adjunto; Nombre del archivo=" + URLEncoder.encode(
                        objectName.substring(
                        objectName.lastIndexOf("/") + 1),
                        "UTF-8"
                ));
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("UTF-8");
        IOUtils.copy(stream, output);
    }

    @GetMapping("/delete")
    public ResponseEntity deleteFile(
            @RequestParam String bucket,
            @RequestParam String objectName
    ) throws Exception{
        fileService.deleteObject(bucket, objectName);
        return ResponseEntity.ok().build();
    }

}
