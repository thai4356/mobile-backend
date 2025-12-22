package mobibe.mobilebe.entity.productTagMapping;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import mobibe.mobilebe.entity.BaseEntity;
import mobibe.mobilebe.entity.product.Product;
import mobibe.mobilebe.entity.productTag.ProductTag;

@Entity
@Table(name = "product_tag_mapping")
@Getter
@Setter
public class ProductTagMapping extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false)
    private ProductTag tag;
}
