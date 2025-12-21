package mobibe.mobilebe.service.product;

import mobibe.mobilebe.entity.product.Product;

import java.util.List;

import mobibe.mobilebe.dto.response.product.ProductRes;

public interface ProductService {

    List<Product> getAll();

    List<Product> getByCategory(int categoryId);

    List<Product> search(String keyword);

    ProductRes getProductDetail(int productId);
}
