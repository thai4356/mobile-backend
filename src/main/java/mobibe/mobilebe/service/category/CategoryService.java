package mobibe.mobilebe.service.category;

import java.util.List;

import org.springframework.stereotype.Service;

import mobibe.mobilebe.entity.category.Category;

@Service
public interface CategoryService {
    List<Category> findAllActive();
    List<Category> search(String keyword);
    Category create(Category category);
    Category update(Integer id, Category req);
     void delete(Integer id);
}
