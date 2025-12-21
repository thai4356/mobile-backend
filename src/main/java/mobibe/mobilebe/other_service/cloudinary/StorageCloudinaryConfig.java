package mobibe.mobilebe.other_service.cloudinary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mobibe.mobilebe.other_service.StorageConfig;


@AllArgsConstructor
@Getter
public class StorageCloudinaryConfig implements StorageConfig {
    private String cloudName;
    private String apiKey;
    private String apiSecret;
}
