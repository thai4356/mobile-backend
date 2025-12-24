package mobibe.mobilebe.repository.productTagMapping;

import java.util.List;

import mobibe.mobilebe.entity.product.Product;
import mobibe.mobilebe.entity.productTag.ProductTag;
import mobibe.mobilebe.other_service.ai.BudgetCondition;

public interface ProductTagMappingRepositoryCustom {

      boolean existsMapping(Integer productId, Integer tagId);

      List<ProductTag> findTagsByProductId(Integer productId);

      List<Product> findProductsByTagIds(List<Integer> tagIds);

      List<Product> suggestProductsByTagIds(List<Integer> tagIds, int limit);

      List<Product> findProductsByTags(List<String> tagNames, List<String> tagTypes, int limit);

      List<Product> findProductsByTagsAndPrice( List<String> tagNames,List<String> tagTypes,BudgetCondition budget,int limit);
}
