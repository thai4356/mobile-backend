package mobibe.mobilebe.repository.categoryRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import mobibe.mobilebe.entity.category.Category;
import mobibe.mobilebe.entity.category.QCategory;


@Repository
public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

     private final JPAQueryFactory queryFactory;

    QCategory qCategory = QCategory.category;
    public CategoryRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<Category> findAllActive() {
    
        return queryFactory
                .selectFrom(qCategory)
                .where(qCategory.active.isTrue())
                .fetch(); 
    }

    @Override
    public List<Category> search(String keyword) {

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qCategory.deleted.eq(false));

        if (keyword != null && !keyword.trim().isEmpty()) {
            builder.and(
                    qCategory.name.containsIgnoreCase(keyword)
                            .or(qCategory.description.containsIgnoreCase(keyword)));
        }

        return queryFactory
                .selectFrom(qCategory)
                .where(builder)
                .orderBy(qCategory.createdAt.desc())
                .fetch();
    }
}
