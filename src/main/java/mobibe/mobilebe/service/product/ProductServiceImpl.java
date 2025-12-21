package mobibe.mobilebe.service.product;

import lombok.RequiredArgsConstructor;
import mobibe.mobilebe.dto.request.product.ProductReq;
import mobibe.mobilebe.dto.request.uploadFile.UploadFileReq;
import mobibe.mobilebe.dto.response.product.ProductRes;
import mobibe.mobilebe.dto.response.upload_file.UploadFileRes;
import mobibe.mobilebe.entity.category.Category;
import mobibe.mobilebe.entity.product.Product;
import mobibe.mobilebe.entity.upload_file.UploadFile;
import mobibe.mobilebe.entity.upload_file.constant.UploadFileType;
import mobibe.mobilebe.exceptions.BusinessException;
import mobibe.mobilebe.other_service.StorageResource;
import mobibe.mobilebe.repository.categoryRepository.CategoryRepository;
import mobibe.mobilebe.repository.productRepository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import mobibe.mobilebe.converter.Translator;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private StorageResource storageResource;

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
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

        // ✅ MAP ẢNH → UploadFileRes
        if (product.getFiles() != null && !product.getFiles().isEmpty()) {

            List<UploadFileRes> files = new ArrayList<>();

            for (UploadFile uf : product.getFiles()) {
                UploadFileRes f = new UploadFileRes();
                f.setId(uf.getId());
                f.setOriginUrl(uf.getOriginUrl());
                f.setThumbUrl(uf.getThumbUrl());
                f.setType(uf.getType());
                f.setWidth(uf.getWidth());
                f.setHeight(uf.getHeight());
                files.add(f);
            }

            res.setFiles(files);
        }

        return res;
    }

    @Override
    public ProductRes create(ProductReq req, List<MultipartFile> files) {

        Category category = categoryRepository.findById(req.getCategoryId())
                .orElseThrow(() -> new BusinessException("Category not found"));

        Product product = new Product();
        product.setName(req.getName());
        product.setDescription(req.getDescription());
        product.setPrice(req.getPrice());
        product.setStock(req.getStock());
        product.setCategory(category);

        // 🔥 giữ nguyên logic insert
        productRepository.save(product);

        // 🔥 xử lý upload file sau khi có product.id
        handleUploadFiles(product, files, UploadFileType.IMAGE);

        return toRes(product);
    }

    @Override
    public ProductRes update(ProductReq req, List<MultipartFile> files) {

        Product product = productRepository.findOneActiveById(req.getId());
        if (product == null) {
            throw new BusinessException("Product not found");
        }

        if (req.getName() != null) {
            product.setName(req.getName());
        }
        if (req.getDescription() != null) {
            product.setDescription(req.getDescription());
        }
        if (req.getPrice() != null) {
            product.setPrice(req.getPrice());
        }
        if (req.getStock() != null) {
            product.setStock(req.getStock());
        }

        if (req.getCategoryId() != null) {
            Category category = categoryRepository.findById(req.getCategoryId())
                    .orElseThrow(() -> new BusinessException("Category not found"));
            product.setCategory(category);
        }

        // 🔥 nếu có file mới → thay toàn bộ ảnh cũ
        if (files != null && !files.isEmpty()) {
            product.getFiles().clear(); // orphanRemoval = true
            handleUploadFiles(product, files, UploadFileType.IMAGE);
        }

        productRepository.update(product);

        return toRes(product);
    }

    @Override
    public ProductRes delete(int id) {

        Product product = productRepository.findOneActiveById(id);
        if (product == null) {
            throw new BusinessException("Product not found");
        }

        productRepository.deleteById(id);

        return toRes(product);
    }

    private void handleUploadFiles(
            Product product,
            List<MultipartFile> files,
            UploadFileType type) {

        if (files == null || files.isEmpty()) {
            return;
        }

        for (MultipartFile file : files) {
            UploadFile uploadFile = uploadSingleFile(file, product, type);

            // ⚠️ BẮT BUỘC set quan hệ ngược
            uploadFile.setProduct(product);

            // ⚠️ CHỈ ADD – TUYỆT ĐỐI KHÔNG SET
            product.getFiles().add(uploadFile);
        }
    }

    private UploadFile uploadSingleFile(
            MultipartFile file,
            Product product,
            UploadFileType type) {

        try {
            String path = "products/"
                    + product.getId()
                    + "/"
                    + System.currentTimeMillis();

            String originUrl = storageResource.writeResource(
                    file.getInputStream(),
                    path);

            UploadFile uf = new UploadFile();
            uf.setOriginFilePath(path);
            uf.setOriginUrl(originUrl);
            uf.setType(type);
            uf.setSize(file.getSize()); // Long (entity chuẩn)
            uf.setProduct(product);

            return uf;

        } catch (Exception e) {
            throw new BusinessException("Upload file failed");
        }
    }

    private ProductRes toRes(Product product) {

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

        if (product.getFiles() != null) {
            List<UploadFileRes> fileResList = new ArrayList<>();

            for (UploadFile uf : product.getFiles()) {
                UploadFileRes f = new UploadFileRes();
                f.setOriginUrl(uf.getOriginUrl());
                f.setThumbUrl(uf.getThumbUrl());
                f.setOriginUrl(uf.getOriginUrl());
                f.setThumbUrl(uf.getThumbUrl());
                f.setType(uf.getType());
                f.setWidth(uf.getWidth());
                f.setHeight(uf.getHeight());
                fileResList.add(f);
            }

            res.setFiles(fileResList);
        }

        return res;
    }

}
