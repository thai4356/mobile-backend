package mobibe.mobilebe.service.category;

import java.util.List;

import org.springframework.stereotype.Service;

import mobibe.mobilebe.entity.category.Category;
import mobibe.mobilebe.exceptions.BusinessException;
import mobibe.mobilebe.repository.categoryRepository.CategoryRepository;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category create(Category category) {

        if (category.getName() == null || category.getName().trim().isEmpty()) {
            throw new BusinessException("category_name_required");
        }

        category.setId(0); // đảm bảo insert
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
    public Category update(Integer id, Category req) {

        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new BusinessException("category_not_found"));

        if (req.getName() != null) {
            category.setName(req.getName());
        }
        if (req.getDescription() != null) {
            category.setDescription(req.getDescription());
        }

        category.setActive(req.isActive());

        return categoryRepository.save(category);
    }

    @Override
    public void delete(Integer id) {

        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new BusinessException("category_not_found"));

        category.setDeleted(true);
        categoryRepository.save(category);
    }
}
