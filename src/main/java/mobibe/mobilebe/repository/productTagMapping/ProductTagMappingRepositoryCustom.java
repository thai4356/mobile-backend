package mobibe.mobilebe.repository.productTagMapping;

import java.util.List;

import mobibe.mobilebe.entity.product.Product;
import mobibe.mobilebe.entity.productTag.ProductTag;

public interface ProductTagMappingRepositoryCustom {

     boolean existsMapping(Integer productId, Integer tagId);

     List<ProductTag> findTagsByProductId(Integer productId);

     List<Product> findProductsByTagIds(List<Integer> tagIds);

      List<Product> suggestProductsByTagIds(List<Integer> tagIds, int limit);
}
