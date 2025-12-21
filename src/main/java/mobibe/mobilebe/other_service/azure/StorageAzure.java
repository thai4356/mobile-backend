package mobibe.mobilebe.other_service.azure;

import java.io.InputStream;

import mobibe.mobilebe.other_service.StorageResource;


public class StorageAzure implements StorageResource {
    @Override
    public InputStream readResource(String path) {
        return null;
    }

    @Override
    public String writeResource(InputStream inputStream, String path) {
        return null;
    }

    @Override
    public boolean deleteFile(String file) {
        return false;
    }

    @Override
    public String getUrl(String file) {
        return null;
    }
}
