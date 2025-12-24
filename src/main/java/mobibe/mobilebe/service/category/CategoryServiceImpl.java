package mobibe.mobilebe.service.category;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mobibe.mobilebe.entity.category.Category;
import mobibe.mobilebe.entity.product.Product;
import mobibe.mobilebe.exceptions.BusinessException;
import mobibe.mobilebe.repository.categoryRepository.CategoryRepository;

import java.util.List;

import mobibe.mobilebe.repository.productRepository.ProductRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Category create(Category category) {

        if (category.getName() == null || category.getName().trim().isEmpty()) {
            throw new BusinessException("category_name_required");
        }

        category.setId(0); // đảm bảo insert
        category.setActive(true);
        return categoryRepository.save(category);
    }

    public List<Category> findAllActive() {
        return categoryRepository.findAllActive();
    }

    @Override
    public List<Category> search(String keyword) {
        return categoryRepository.search(keyword);
    }

    @Override
    @Transactional
    public Category update(Integer id, Category req) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException("category_not_found"));

        if (req.getName() != null) {
            category.setName(req.getName());
        }

        if (req.getDescription() != null) {
            category.setDescription(req.getDescription());
        }

        category.setActive(req.isActive());
        System.out.println("hien tai : " + req.isActive());

        List<Product> products = productRepository.findByCategoryId(id);

        for (Product product : products) {
            product.setActive(req.isActive());
        }

        productRepository.saveAll(products);

        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void delete(Integer id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException("category_not_found"));

        category.setDeleted(true);
        category.setActive(false);

        List<Product> products = productRepository.findByCategoryIdAndDeletedFalse(id);

        for (Product product : products) {
            product.setDeleted(true);
            product.setActive(false);
        }

        // 3. Save
        productRepository.saveAll(products);
        categoryRepository.save(category);
    }
}
