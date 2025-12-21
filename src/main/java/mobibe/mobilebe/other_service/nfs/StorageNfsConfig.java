package mobibe.mobilebe.other_service.nfs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mobibe.mobilebe.other_service.StorageConfig;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StorageNfsConfig implements StorageConfig {
    private String directory;
    private String serverUrl;
}
