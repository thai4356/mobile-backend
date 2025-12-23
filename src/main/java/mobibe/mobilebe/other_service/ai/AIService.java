package mobibe.mobilebe.other_service.ai;

import lombok.RequiredArgsConstructor;
import mobibe.mobilebe.configuration.ai.TagDef;
import mobibe.mobilebe.dto.response.ai.AIChatRes;
import mobibe.mobilebe.dto.response.ai.AIProductRes;
import mobibe.mobilebe.dto.response.ai.AITagRes;
import mobibe.mobilebe.entity.product.Product;
import mobibe.mobilebe.entity.productTag.ProductTag;
import mobibe.mobilebe.repository.productTag.ProductTagRepository;
import mobibe.mobilebe.repository.productTagMapping.ProductTagMappingRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mobibe.mobilebe.configuration.ai.TagDictionary;

@Service
public class AIService {

    private final ProductTagRepository productTagRepository;
    private final ProductTagMappingRepository productTagMappingRepository;

    public AIService(ProductTagMappingRepository productTagMappingRepository,
            ProductTagRepository productTagRepository) {
        this.productTagMappingRepository = productTagMappingRepository;
        this.productTagRepository = productTagRepository;
    }

    public AIChatRes chat(String message) {

        AIChatRes res = new AIChatRes();

        List<AITagRes> tags = extractTags(message);
        res.setTags(tags);

        // 1. Không extract được tag
        if (tags.isEmpty()) {
            res.setProducts(List.of());
            res.setMessage("cant find product match your need");
            return res;
        }

        List<Integer> tagIds = new ArrayList<>();
        for (AITagRes t : tags) {
            List<Integer> ids = productTagRepository.findTagIdsByNameAndType(
                    t.getName(), t.getType());
            tagIds.addAll(ids);
        }

        // 2. Có tag nhưng không map được tagId trong DB
        if (tagIds.isEmpty()) {
            res.setProducts(List.of());
            res.setMessage("cant find product match your need");
            return res;
        }

        List<Product> products = productTagMappingRepository.suggestProductsByTagIds(tagIds, 10);

        // 3. Có tagId nhưng không có product
        if (products.isEmpty()) {
            res.setProducts(List.of());
            res.setMessage("cant find product match your need");
            return res;
        }

        List<AIProductRes> productRes = products.stream().map(p -> {
            AIProductRes r = new AIProductRes();
            r.setId(p.getId());
            r.setName(p.getName());
            r.setPrice(p.getPrice());
            r.setLink("/products/" + p.getId());
            return r;
        }).toList();

        res.setProducts(productRes);
        res.setMessage("found products match your need");

        return res;
    }

    private List<AITagRes> extractTags(String message) {

        List<AITagRes> result = new ArrayList<>();
        if (message == null || message.isBlank())
            return result;

        String msg = message.toLowerCase();

        for (var entry : TagDictionary.DICTIONARY.entrySet()) {

            if (msg.contains(entry.getKey())) {
                for (TagDef def : entry.getValue()) {
                    result.add(new AITagRes(def.getName(), def.getType()));
                }
            }
        }

        return result;
    }

}
