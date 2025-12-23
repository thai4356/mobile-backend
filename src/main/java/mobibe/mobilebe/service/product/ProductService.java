package mobibe.mobilebe.service.product;

import mobibe.mobilebe.entity.product.Product;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import mobibe.mobilebe.dto.request.product.ProductReq;
import mobibe.mobilebe.dto.response.product.ProductRes;

public interface ProductService {

    List<ProductRes> getAll();

    List<Product> getByCategory(int categoryId);

    List<Product> search(String keyword);

    ProductRes getProductDetail(int productId);

    ProductRes create(ProductReq req, List<MultipartFile> files);

    ProductRes update(ProductReq req, List<MultipartFile> files);

    ProductRes delete(int id);
}
