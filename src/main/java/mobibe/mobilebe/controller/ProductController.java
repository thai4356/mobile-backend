package mobibe.mobilebe.controller;

import lombok.RequiredArgsConstructor;
import mobibe.mobilebe.dto.response.BaseResponse;
import mobibe.mobilebe.dto.response.product.ProductRes;
import mobibe.mobilebe.entity.product.Product;
import mobibe.mobilebe.service.product.ProductService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getByCategory(
            @PathVariable int categoryId) {
        return ResponseEntity.ok(productService.getByCategory(categoryId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> search(
            @RequestParam(required = false) String keyword) {
        return ResponseEntity.ok(productService.search(keyword));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ProductRes>> getProductDetail(
            @PathVariable int id) {
        return ResponseEntity.ok(
                new BaseResponse<>(productService.getProductDetail(id)));
    }

    
}
