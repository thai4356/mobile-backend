package mobibe.mobilebe.dto.response.upload_file;

import lombok.Getter;
import lombok.Setter;
import mobibe.mobilebe.entity.upload_file.constant.UploadFileType;

@Getter
@Setter
public class UploadFileRes {
    private Integer id;
    private String originUrl;
    private String thumbUrl;
    private UploadFileType type;
    private Integer width;
    private Integer height;
}
