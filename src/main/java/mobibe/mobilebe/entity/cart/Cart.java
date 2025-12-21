package mobibe.mobilebe.entity.cart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mobibe.mobilebe.entity.BaseEntity;
import mobibe.mobilebe.entity.cartItem.CartItem;
import mobibe.mobilebe.entity.user.User;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "carts")
public class Cart extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<CartItem> items;

    @Column(nullable = false)
    private boolean active = true;
}
