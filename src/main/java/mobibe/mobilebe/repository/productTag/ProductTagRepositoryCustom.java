package mobibe.mobilebe.repository.productTag;

import java.util.List;

import mobibe.mobilebe.entity.product.Product;
import mobibe.mobilebe.entity.productTag.ProductTag;

public interface ProductTagRepositoryCustom {

    ProductTag findByNameAndType(String name, String type);

    List<ProductTag> findByDeletedFalse();

    List<Product> suggestProductsByTagIds(List<Integer> tagIds, int limit);

    List<Integer> findTagIdsByNameAndType(String name, String type);


}