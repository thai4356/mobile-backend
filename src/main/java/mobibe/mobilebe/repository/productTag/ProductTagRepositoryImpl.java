package mobibe.mobilebe.repository.productTag;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import mobibe.mobilebe.entity.product.Product;
import mobibe.mobilebe.entity.product.QProduct;
import mobibe.mobilebe.entity.productTag.ProductTag;
import mobibe.mobilebe.entity.productTag.QProductTag;
import mobibe.mobilebe.entity.productTagMapping.QProductTagMapping;
import mobibe.mobilebe.repository.BaseRepository;

@Repository
public class ProductTagRepositoryImpl extends BaseRepository implements ProductTagRepositoryCustom {

        QProductTag tag = QProductTag.productTag;
        QProductTagMapping mapping = QProductTagMapping.productTagMapping;
        QProduct product = QProduct.product;

        @Override
        public ProductTag findByNameAndType(String name, String type) {

                BooleanBuilder builder = new BooleanBuilder();
                builder.and(tag.name.eq(name));
                builder.and(tag.type.eq(type));

                return query().from(tag).where(builder).select(tag).fetchFirst();
        }

        @Override
        public List<ProductTag> findByDeletedFalse() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'findByDeletedFalse'");
        }

        @Override
        public List<Product> suggestProductsByTagIds(List<Integer> tagIds, int limit) {

                if (tagIds == null || tagIds.isEmpty()) {
                        return List.of();
                }

                BooleanBuilder builder = new BooleanBuilder();
                builder.and(mapping.tag.id.in(tagIds));
                builder.and(mapping.deleted.isFalse());
                builder.and(product.deleted.isFalse());
                builder.and(product.active.isTrue());

                return query()
                                .from(mapping)
                                .join(mapping.product, product)
                                .where(builder)
                                .select(product)
                                .distinct() // 🔑 tránh trùng product
                                .limit(limit) // 🔑 gợi ý nhiều nhưng có giới hạn
                                .fetch();
        }

        @Override
        public List<Integer> findTagIdsByNameAndType(String name, String type) {

                BooleanBuilder builder = new BooleanBuilder();
                builder.and(tag.name.equalsIgnoreCase(name));
                builder.and(tag.type.equalsIgnoreCase(type));
                builder.and(tag.deleted.isFalse());

                return query()
                                .from(tag)
                                .where(builder)
                                .select(tag.id)
                                .fetch(); 
        }

}
