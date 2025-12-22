package mobibe.mobilebe.entity.productTag;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import mobibe.mobilebe.entity.BaseEntity;

@Entity
@Table(name = "product_tags")
@Getter @Setter
public class ProductTag extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type; // SPORT, GOAL, LEVEL
}
