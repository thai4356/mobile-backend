package mobibe.mobilebe.repository.productRepository;

import mobibe.mobilebe.entity.product.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer>, ProductRepositoryCustom {
    List<Product> findByCategoryId(Integer categoryId);

    Product findById(int id);
}
