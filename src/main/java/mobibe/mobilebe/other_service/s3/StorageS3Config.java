package mobibe.mobilebe.other_service.s3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mobibe.mobilebe.other_service.StorageConfig;


@Getter
@AllArgsConstructor
public class StorageS3Config implements StorageConfig {
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String region;

    @Override
    public String toString() {
        return "ECS3StorageConfig{" +
                "accessKey='" + accessKey + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", bucket='" + bucket + '\'' +
                ", region='" + region + '\'' +
                '}';
    }
}
