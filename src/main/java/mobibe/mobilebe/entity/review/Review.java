package mobibe.mobilebe.entity.review;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mobibe.mobilebe.entity.BaseEntity;
import mobibe.mobilebe.entity.product.Product;
import mobibe.mobilebe.entity.user.User;

@Getter
@Setter
@Entity
@Table(name = "reviews")
public class Review extends BaseEntity {

    @Column(nullable = false)
    private Integer rating; // 1–5

    @Column(length = 1000)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnore
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;
}
