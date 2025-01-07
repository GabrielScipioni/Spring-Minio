package gabri.dev.minio.services;

import io.minio.errors.*;
import org.apache.tomcat.jni.FileInfo;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Servicio para la gestión de archivos en MinIO.
 */
public interface MinioFilesService {

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
    void uploadFile(InputStream stream, String bucket, String objectName)
            throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

    /**
     * Lista los archivos dentro de un bucket específico.
     *
     * @param bucket nombre del bucket a listar.
     * @return una lista de información de archivos ({@link FileInfo}) contenidos en el bucket.
     */
    List<gabri.dev.minio.dtos.common.FileInfo> listFiles(String bucket);

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
    InputStream download(String bucket, String objectName)
            throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

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
    void deleteObject(String bucket, String objectName)
            throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;
}
