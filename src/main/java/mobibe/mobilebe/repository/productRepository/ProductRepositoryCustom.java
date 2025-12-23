package mobibe.mobilebe.repository.productRepository;

import mobibe.mobilebe.entity.product.Product;

import java.util.List;

public interface ProductRepositoryCustom {

    List<Product> findAllActive();

    List<Product> findByCategory(int categoryId);

    List<Product> search(String keyword);

    Product findOneActiveById(int id);

    void insert(Product product);

    void update(Product product);

    void deleteById(int id);

    void updateActiveByCategoryId(Integer categoryId, boolean active);

    List<Product> findByCategoryIdAndDeletedFalse(Integer categoryId);
}
