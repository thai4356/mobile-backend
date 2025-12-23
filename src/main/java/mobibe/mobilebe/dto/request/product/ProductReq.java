package mobibe.mobilebe.dto.request.product;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import mobibe.mobilebe.dto.request.tag.TagReq;
import mobibe.mobilebe.dto.request.uploadFile.UploadFileReq;

@Getter
@Setter
public class ProductReq {
    private Integer id; // null = create, có = update
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private Integer categoryId;
    private Boolean active;
    private List<UploadFileReq> files;

     private List<TagReq> tags;
}
