package gabri.dev.minio.dtos.common;

import lombok.Data;

@Data
public class FileInfo {
    String filename;
    Boolean directory;
}
