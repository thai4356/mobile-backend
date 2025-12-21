package mobibe.mobilebe.service.product;

import lombok.RequiredArgsConstructor;
import mobibe.mobilebe.dto.response.product.ProductRes;
import mobibe.mobilebe.entity.product.Product;
import mobibe.mobilebe.exceptions.BusinessException;
import mobibe.mobilebe.repository.productRepository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

import java.util.List;

import mobibe.mobilebe.converter.Translator;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAllActive();
    }

    @Override
    public List<Product> getByCategory(int categoryId) {
        return productRepository.findByCategory(categoryId);
    }

    @Override
    public List<Product> search(String keyword) {
        return productRepository.search(keyword);
    }

    @Override
    public ProductRes getProductDetail(int productId) {
        Product product = productRepository.findOneActiveById(productId);

        if (product == null) {
            throw new BusinessException(
                    Translator.toLocale("data_not_found"),
                    HttpStatus.NOT_FOUND);
        }

        ProductRes res = new ProductRes();
        res.setId(product.getId());
        res.setName(product.getName());
        res.setDescription(product.getDescription());
        res.setPrice(product.getPrice());
        res.setStock(product.getStock());

        if (product.getCategory() != null) {
            res.setCategoryId(product.getCategory().getId());
            res.setCategoryName(product.getCategory().getName());
        }

        return res;
    }

}
