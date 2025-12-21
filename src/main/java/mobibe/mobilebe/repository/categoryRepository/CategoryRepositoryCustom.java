package mobibe.mobilebe.repository.categoryRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import mobibe.mobilebe.entity.category.Category;

@Repository
public interface CategoryRepositoryCustom {

    List<Category> findAllActive() ;
    List<Category> search(String keyword);
}
