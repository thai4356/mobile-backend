package mobibe.mobilebe.repository.productRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mobibe.mobilebe.entity.product.Product;
import mobibe.mobilebe.entity.product.QProduct;
import mobibe.mobilebe.repository.BaseRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl extends BaseRepository implements ProductRepositoryCustom {

    private final QProduct product = QProduct.product;
    private final EntityManager em;

    @Override
    public List<Product> findAllActive() {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(product.deleted.isFalse());
        builder.and(product.active.isTrue());

        return query()
                .from(product)
                .where(builder)
                .select(product)
                .fetch();
    }

    @Override
    public List<Product> findByCategory(int categoryId) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(product.deleted.isFalse());
        builder.and(product.active.isTrue());
        builder.and(product.category.id.eq(categoryId));

        return query()
                .from(product)
                .where(builder)
                .select(product)
                .fetch();
    }

    @Override
    public List<Product> search(String keyword) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(product.deleted.isFalse());
        builder.and(product.active.isTrue());

        if (StringUtils.hasText(keyword)) {
            builder.and(product.name.containsIgnoreCase(keyword));
        }

        return query()
                .from(product)
                .where(builder)
                .select(product)
                .fetch();
    }

    @Override
    public Product findOneActiveById(int id) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(product.id.eq(id));
        builder.and(product.deleted.isFalse());
        builder.and(product.active.isTrue());

        return query()
                .from(product)
                .where(builder)
                .select(product)
                .fetchOne();
    }

    @Override
    public void insert(Product p) {
        em.persist(p);
    }

    @Override
    public void update(Product p) {
        em.merge(p);
    }

    @Override
    public void deleteById(int id) {
        query()
                .update(product)
                .set(product.deleted, true)
                .where(product.id.eq(id))
                .execute();
    }

}