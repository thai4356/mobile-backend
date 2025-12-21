package mobibe.mobilebe.service.file;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import mobibe.mobilebe.entity.upload_file.UploadFile;

public interface FileStorageService {

    UploadFile storeImage(final MultipartFile file);
    void deleteFile(int fileId);
    InputStream getInputStream(final String fileName);
}