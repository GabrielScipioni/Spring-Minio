package gabri.dev.minio.services.impl;

import gabri.dev.minio.dtos.common.FileInfo;
import gabri.dev.minio.services.MinioBucketService;
import gabri.dev.minio.services.MinioFilesService;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service("minioFilesService")
public class MinioFilesServiceImpl implements MinioFilesService {

    @Autowired
    MinioClient minio;
    @Autowired
    MinioBucketService BucketService;

    /**
     * Sube un archivo a un bucket específico en MinIO.
     *
     * @param stream     flujo de entrada del archivo a subir.
     * @param bucket     nombre del bucket donde se almacenará el archivo.
     * @param objectName nombre del archivo dentro del bucket.
     * @throws ServerException           si ocurre un error del lado del servidor de MinIO.
     * @throws InsufficientDataException si los datos recibidos son insuficientes.
     * @throws ErrorResponseException    si MinIO devuelve una respuesta de error.
     * @throws IOException               si ocurre un error de entrada/salida.
     * @throws NoSuchAlgorithmException  si el algoritmo criptográfico requerido no está disponible.
     * @throws InvalidKeyException       si la clave proporcionada es inválida.
     * @throws InvalidResponseException  si la respuesta de MinIO es inválida.
     * @throws XmlParserException        si ocurre un error al analizar XML.
     * @throws InternalException         si ocurre un error interno en la librería de MinIO.
     */
    @Override
    public void uploadFile(InputStream stream, String bucket, String objectName)
            throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException{
        minio.putObject(
                PutObjectArgs.builder()
                        .bucket(bucket)
                        .object(objectName)
                        //-1 para tamaño desconocido
                        //10485760(10MB) tamaño del multipart
                        .stream(stream, -1, 10485760)
                        .build());

    }

    /**
     * Lista los archivos dentro de un bucket específico.
     *
     * @param bucket nombre del bucket a listar.
     * @return una lista de información de archivos ({@link FileInfo}) contenidos en el bucket.
     */
    @Override
    public List<FileInfo> listFiles(String bucket) {
        Iterable<Result<Item>> results = minio.listObjects(
                ListObjectsArgs.builder().bucket(bucket).recursive(true).build());

        List<FileInfo> infos = new ArrayList<>();
        results.forEach(r -> {
            FileInfo info = new FileInfo();
            try {
                Item item = r.get();
                info.setFilename(item.objectName());
                info.setDirectory(item.isDir());
                infos.add(info);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return infos;
    }
    /**
     * Descarga un archivo desde un bucket específico.
     *
     * @param bucket     nombre del bucket de donde se descargará el archivo.
     * @param objectName nombre del archivo a descargar.
     * @return un flujo de entrada con el contenido del archivo.
     * @throws ServerException           si ocurre un error del lado del servidor de MinIO.
     * @throws InsufficientDataException si los datos recibidos son insuficientes.
     * @throws ErrorResponseException    si MinIO devuelve una respuesta de error.
     * @throws IOException               si ocurre un error de entrada/salida.
     * @throws NoSuchAlgorithmException  si el algoritmo criptográfico requerido no está disponible.
     * @throws InvalidKeyException       si la clave proporcionada es inválida.
     * @throws InvalidResponseException  si la respuesta de MinIO es inválida.
     * @throws XmlParserException        si ocurre un error al analizar XML.
     * @throws InternalException         si ocurre un error interno en la librería de MinIO.
     */
    @Override
    public InputStream download(String bucket, String objectName)
            throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        InputStream stream = minio.getObject(
                GetObjectArgs.builder().bucket(bucket).object(objectName).build());
        return stream;
    }

    /**
     * Elimina un archivo de un bucket específico en MinIO.
     *
     * @param bucket     nombre del bucket de donde se eliminará el archivo.
     * @param objectName nombre del archivo a eliminar.
     * @throws ServerException           si ocurre un error del lado del servidor de MinIO.
     * @throws InsufficientDataException si los datos recibidos son insuficientes.
     * @throws ErrorResponseException    si MinIO devuelve una respuesta de error.
     * @throws IOException               si ocurre un error de entrada/salida.
     * @throws NoSuchAlgorithmException  si el algoritmo criptográfico requerido no está disponible.
     * @throws InvalidKeyException       si la clave proporcionada es inválida.
     * @throws InvalidResponseException  si la respuesta de MinIO es inválida.
     * @throws XmlParserException        si ocurre un error al analizar XML.
     * @throws InternalException         si ocurre un error interno en la librería de MinIO.
     */
    @Override
    public void deleteObject(String bucket, String objectName)
            throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minio.removeObject(RemoveObjectArgs.builder().bucket(bucket).object(objectName).build());
    }
}

