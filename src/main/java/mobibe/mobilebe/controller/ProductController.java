package mobibe.mobilebe.controller;

import lombok.RequiredArgsConstructor;
import mobibe.mobilebe.dto.request.product.ProductReq;
import mobibe.mobilebe.dto.response.BaseResponse;
import mobibe.mobilebe.dto.response.product.ProductRes;
import mobibe.mobilebe.entity.product.Product;
import mobibe.mobilebe.service.product.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @InitBinder("productReq")
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("files");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse<ProductRes> create(
            @ModelAttribute ProductReq productReq,
            @RequestPart(required = false) List<MultipartFile> files) {
        return new BaseResponse<>(
                productService.create(productReq, files));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse<ProductRes> update(
            @ModelAttribute ProductReq productReq,
            @RequestPart(required = false) List<MultipartFile> files) {
        return new BaseResponse<>(
                productService.update(productReq, files));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public BaseResponse<Void> delete(
            @PathVariable Integer id) {
        productService.delete(id);
        return new BaseResponse<>("Deleted");
    }
}
