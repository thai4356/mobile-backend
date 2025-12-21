package mobibe.mobilebe.other_service.ftp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mobibe.mobilebe.other_service.StorageConfig;

@Getter
@AllArgsConstructor
public class StorageFtpConfig implements StorageConfig {
    private String server;
    private int port;
    private String username;
    private String password;

    @Override
    public String toString() {
        return "ECFtpStorageConfig{" +
                "server='" + server + '\'' +
                ", port=" + port +
                ", username='" + username + '\'' +
                '}';
    }
}
