package mobibe.mobilebe.repository.productTagMapping;

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
import mobibe.mobilebe.other_service.ai.BudgetCondition;
import mobibe.mobilebe.other_service.ai.enumuration.BudgetType;
import mobibe.mobilebe.repository.BaseRepository;

@Repository
@RequiredArgsConstructor
public class ProductTagMappingRepositoryImpl extends BaseRepository implements ProductTagMappingRepositoryCustom {

        QProductTagMapping mapping = QProductTagMapping.productTagMapping;
        QProduct product = QProduct.product;
        QProductTag tag = QProductTag.productTag;

        @Override
        public List<ProductTag> findTagsByProductId(Integer productId) {

                BooleanBuilder builder = new BooleanBuilder();
                builder.and(mapping.product.id.eq(productId));
                builder.and(mapping.deleted.isFalse());

                return query()
                                .from(mapping)
                                .join(mapping.tag, tag)
                                .where(builder)
                                .select(tag)
                                .fetch();
        }

        @Override
        public boolean existsMapping(Integer productId, Integer tagId) {

                BooleanBuilder builder = new BooleanBuilder();
                builder.and(mapping.product.id.eq(productId));
                builder.and(mapping.tag.id.eq(tagId));

                Integer result = query()
                                .from(mapping)
                                .where(builder)
                                .select(mapping.id)
                                .fetchFirst();

                return result != null;
        }

        @Override
        public List<Product> findProductsByTagIds(List<Integer> tagIds) {
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
                                .distinct()
                                .fetch();
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
                                .distinct()
                                .limit(limit)
                                .fetch();
        }

        @Override
        public List<Product> findProductsByTags(
                        List<String> tagNames,
                        List<String> tagTypes,
                        int limit) {

                BooleanBuilder builder = new BooleanBuilder();

                builder.and(mapping.deleted.isFalse());
                builder.and(product.deleted.isFalse());
                builder.and(product.active.isTrue());

                // match type
                builder.and(tag.type.in(tagTypes));

                // 🔥 match TAG NAME (VN)
                BooleanBuilder tagNameBuilder = new BooleanBuilder();
                for (String name : tagNames) {
                        tagNameBuilder.or(tag.name.containsIgnoreCase(name));
                }

                // 🔥 fallback: match PRODUCT NAME
                BooleanBuilder productNameBuilder = new BooleanBuilder();
                for (String name : tagNames) {
                        productNameBuilder.or(product.name.containsIgnoreCase(name));
                }

                builder.andAnyOf(tagNameBuilder, productNameBuilder);

                return query()
                                .from(mapping)
                                .join(mapping.product, product)
                                .join(mapping.tag, tag)
                                .where(builder)
                                .select(product)
                                .distinct()
                                .limit(limit)
                                .fetch();
        }

        @Override
        public List<Product> findProductsByTagsAndPrice(
                        List<String> tagNames,
                        List<String> tagTypes,
                        BudgetCondition budget,
                        int limit) {

                if (tagNames == null || tagNames.isEmpty()) {
                        return List.of();
                }

                BooleanBuilder builder = new BooleanBuilder();

                // ===== base conditions =====
                builder.and(mapping.deleted.isFalse());
                builder.and(product.deleted.isFalse());
                builder.and(product.active.isTrue());

                // match theo type (SHOES, CLOTHING...)
                if (tagTypes != null && !tagTypes.isEmpty()) {
                        builder.and(tag.type.in(tagTypes));
                }

                // ===== MATCH MỀM THEO TAG NAME + PRODUCT NAME =====
                BooleanBuilder nameBuilder = new BooleanBuilder();
                for (String name : tagNames) {
                        if (name == null || name.isBlank())
                                continue;

                        nameBuilder.or(tag.name.containsIgnoreCase(name));
                        nameBuilder.or(product.name.containsIgnoreCase(name));
                }
                builder.and(nameBuilder);

                if (budget.getType() == BudgetType.UNDER) {
                        builder.and(product.price.loe(budget.getMax()));
                }

                if (budget.getType() == BudgetType.OVER) {
                        builder.and(product.price.goe(budget.getMin()));
                }

                if (budget.getType() == BudgetType.BETWEEN) {
                        builder.and(product.price.between(
                                        budget.getMin(),
                                        budget.getMax()));
                }

                return query()
                                .from(mapping)
                                .join(mapping.product, product)
                                .join(mapping.tag, tag)
                                .where(builder)
                                .select(product)
                                .distinct()
                                .orderBy(product.createdAt.desc())
                                .limit(limit)
                                .fetch();
        }

}
