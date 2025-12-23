package mobibe.mobilebe.repository.productTag;

import org.springframework.data.jpa.repository.JpaRepository;

import mobibe.mobilebe.entity.productTag.ProductTag;

public interface ProductTagRepository extends JpaRepository<ProductTag, Integer>, ProductTagRepositoryCustom {
}