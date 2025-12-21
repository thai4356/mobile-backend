package mobibe.mobilebe.repository.productRepository;

import com.querydsl.core.BooleanBuilder;
import lombok.extern.slf4j.Slf4j;
import mobibe.mobilebe.entity.product.Product;
import mobibe.mobilebe.entity.product.QProduct;
import mobibe.mobilebe.repository.BaseRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Repository
public class ProductRepositoryImpl extends BaseRepository implements ProductRepositoryCustom {

    private final QProduct product = QProduct.product;

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
}