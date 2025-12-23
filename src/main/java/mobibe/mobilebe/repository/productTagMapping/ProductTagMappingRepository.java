package mobibe.mobilebe.repository.productTagMapping;

import org.springframework.data.jpa.repository.JpaRepository;

import mobibe.mobilebe.entity.productTagMapping.ProductTagMapping;

public interface ProductTagMappingRepository extends JpaRepository<ProductTagMapping, Integer>, ProductTagMappingRepositoryCustom {
}