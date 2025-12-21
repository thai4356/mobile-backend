package mobibe.mobilebe.dto.request.uploadFile;

import lombok.Getter;
import lombok.Setter;
import mobibe.mobilebe.entity.upload_file.constant.UploadFileType;

@Getter
@Setter
public class UploadFileReq {
    private String originFilePath;
    private String thumbFilePath;
    private String originUrl;
    private String thumbUrl;
    private UploadFileType type;
    private Integer width;
    private Integer height;
    private Integer duration;
    private Integer size; // ép Integer, KHÔNG dùng Long
}
