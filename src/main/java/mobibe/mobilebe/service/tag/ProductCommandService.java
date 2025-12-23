package mobibe.mobilebe.service.tag;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import mobibe.mobilebe.dto.request.tag.TagReq;
import mobibe.mobilebe.entity.product.Product;
import mobibe.mobilebe.entity.productTag.ProductTag;
import mobibe.mobilebe.entity.productTagMapping.ProductTagMapping;
import mobibe.mobilebe.repository.productTag.ProductTagRepository;
import mobibe.mobilebe.repository.productTagMapping.ProductTagMappingRepository;

@Service
@Transactional
public class ProductCommandService {

    private final ProductTagRepository productTagRepository;
    private final ProductTagMappingRepository productTagMappingRepository;

    public ProductCommandService(ProductTagMappingRepository productTagMappingRepository,
            ProductTagRepository productTagRepository) {
        this.productTagMappingRepository = productTagMappingRepository;
        this.productTagRepository = productTagRepository;
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void handleTags(Product product, List<TagReq> tags) {

        if (tags == null) {
            return;
        }

        Integer productId = product.getId();

        // 1. Lấy tag hiện tại
        List<ProductTag> currentTags = productTagMappingRepository.findTagsByProductId(productId);

        // 2. Build key set từ request (null-safe)
        Set<String> newTagKeys = new HashSet<>();

        for (TagReq req : tags) {

            if (req.getName() == null || req.getType() == null) {
                continue; // ❗ bỏ qua tag lỗi
            }

            String key = req.getName().trim().toLowerCase()
                    + "|" + req.getType().trim().toUpperCase();

            newTagKeys.add(key);
        }

        // 3. REMOVE mapping cũ không còn trong request
        for (ProductTag tag : currentTags) {

            String key = tag.getName().toLowerCase()
                    + "|" + tag.getType().toUpperCase();

            if (!newTagKeys.contains(key)) {
                entityManager.createQuery(
                        """
                                DELETE FROM ProductTagMapping m
                                WHERE m.product.id = :productId
                                  AND m.tag.id = :tagId
                                """)
                        .setParameter("productId", productId)
                        .setParameter("tagId", tag.getId())
                        .executeUpdate();
            }
        }

        // 4. ADD mapping mới
        for (TagReq req : tags) {

            if (req.getName() == null || req.getType() == null) {
                continue;
            }

            String name = req.getName().trim().toLowerCase();
            String type = req.getType().trim().toUpperCase();

            ProductTag tag = productTagRepository.findByNameAndType(name, type);

            if (tag == null) {
                tag = new ProductTag();
                tag.setName(name);
                tag.setType(type);
                productTagRepository.save(tag);
            }

            boolean existed = productTagMappingRepository.existsMapping(productId, tag.getId());

            if (!existed) {
                ProductTagMapping m = new ProductTagMapping();
                m.setProduct(product);
                m.setTag(tag);
                entityManager.persist(m);
            }
        }
    }

}
