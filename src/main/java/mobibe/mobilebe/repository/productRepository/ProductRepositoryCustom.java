package mobibe.mobilebe.repository.productRepository;

import mobibe.mobilebe.entity.product.Product;

import java.util.List;

public interface ProductRepositoryCustom {

    List<Product> findAllActive();

    List<Product> findByCategory(int categoryId);

    List<Product> search(String keyword);

    Product findOneActiveById(int id);
    
}
