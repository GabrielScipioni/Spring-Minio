package gabri.dev.minio.services.impl;

import gabri.dev.minio.services.MinioBucketService;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.RemoveBucketArgs;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service("minioBucketService")
public class MinioBucketServiceImpl implements MinioBucketService {

    @Autowired
    MinioClient minio;

    /**
     * Crea un bucket en el servidor MinIO con el nombre especificado.
     *
     * @param bucket el nombre del bucket a crear.
     *
     * @throws ServerException si ocurre un error en el servidor durante la operación.
     * @throws InsufficientDataException si los datos proporcionados son insuficientes para completar la operación.
     * @throws ErrorResponseException si el servidor devuelve una respuesta de error.
     * @throws IOException si ocurre un error de entrada/salida durante la comunicación con el servidor.
     * @throws NoSuchAlgorithmException si el algoritmo especificado no es compatible o no está disponible.
     * @throws InvalidKeyException si la clave proporcionada es inválida para la operación.
     * @throws InvalidResponseException si la respuesta del servidor es inválida o inesperada.
     * @throws XmlParserException si ocurre un error al analizar la respuesta en formato XML del servidor.
     * @throws InternalException si ocurre un error interno en el cliente durante la ejecución de la operación.
     */
    @Override
    public void createBucket(String bucket)
            throws ServerException, InsufficientDataException, ErrorResponseException, IOException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException,
            XmlParserException, InternalException{
        boolean found = minio.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
        if (!found) {
            minio.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
        }
    }

    /**
     * Obtiene una lista con los nombres de todos los buckets existentes en el servidor MinIO.
     *
     * @return una lista de nombres de buckets disponibles en el servidor.
     *
     * @throws ServerException si ocurre un error en el servidor durante la operación.
     * @throws InsufficientDataException si los datos proporcionados son insuficientes para completar la operación.
     * @throws ErrorResponseException si el servidor devuelve una respuesta de error.
     * @throws IOException si ocurre un error de entrada/salida durante la comunicación con el servidor.
     * @throws NoSuchAlgorithmException si el algoritmo especificado no es compatible o no está disponible.
     * @throws InvalidKeyException si la clave proporcionada es inválida para la operación.
     * @throws InvalidResponseException si la respuesta del servidor es inválida o inesperada.
     * @throws XmlParserException si ocurre un error al analizar la respuesta en formato XML del servidor.
     * @throws InternalException si ocurre un error interno en el cliente durante la ejecución de la operación.
     */
    @Override
    public List<String> listBuckets()
            throws ServerException, InsufficientDataException, ErrorResponseException, IOException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException,
            XmlParserException, InternalException{
        List<Bucket> buckets = minio.listBuckets();
        List<String> names = new ArrayList<>();
        buckets.forEach(b->{
            names.add(b.name());
        });
        return names;
    }

    /**
     * Elimina un bucket existente en el servidor MinIO con el nombre especificado.
     *
     * @param bucket el nombre del bucket a eliminar.
     *
     * @throws ServerException si ocurre un error en el servidor durante la operación.
     * @throws InsufficientDataException si los datos proporcionados son insuficientes para completar la operación.
     * @throws ErrorResponseException si el servidor devuelve una respuesta de error.
     * @throws IOException si ocurre un error de entrada/salida durante la comunicación con el servidor.
     * @throws NoSuchAlgorithmException si el algoritmo especificado no es compatible o no está disponible.
     * @throws InvalidKeyException si la clave proporcionada es inválida para la operación.
     * @throws InvalidResponseException si la respuesta del servidor es inválida o inesperada.
     * @throws XmlParserException si ocurre un error al analizar la respuesta en formato XML del servidor.
     * @throws InternalException si ocurre un error interno en el cliente durante la ejecución de la operación.
     */
    @Override
    public void deleteBucket(String bucket)
            throws ServerException, InsufficientDataException, ErrorResponseException, IOException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException,
            XmlParserException, InternalException{
        minio.removeBucket(RemoveBucketArgs.builder().bucket(bucket).build());
    }

}
