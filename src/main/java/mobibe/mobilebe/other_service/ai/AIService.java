package mobibe.mobilebe.other_service.ai;

import mobibe.mobilebe.dto.response.ai.AIChatRes;
import mobibe.mobilebe.dto.response.ai.AIProductRes;
import mobibe.mobilebe.dto.response.ai.AITagRes;
import mobibe.mobilebe.entity.product.Product;
import mobibe.mobilebe.entity.productTag.ProductTag;
import mobibe.mobilebe.repository.productTag.ProductTagRepository;
import mobibe.mobilebe.repository.productTagMapping.ProductTagMappingRepository;
import mobibe.mobilebe.other_service.ai.enumuration.BudgetCondition;
import mobibe.mobilebe.other_service.ai.enumuration.BudgetType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        List<ProductTag> tags = productTagRepository.findTagsByMessage(message);

        if (tags.isEmpty()) {
            res.setMessage("Bạn đang tìm sản phẩm gì?");
            res.setProducts(List.of());
            return res;
        }

        res.setTags(tags.stream()
                .map(t -> new AITagRes(t.getName(), t.getType()))
                .distinct()
                .toList());

        BudgetCondition budget = extractBudget(message);

        List<String> tagNames = tags.stream()
                .map(ProductTag::getName)
                .distinct()
                .toList();

        List<String> tagTypes = tags.stream()
                .map(ProductTag::getType)
                .distinct()
                .toList();

        if (budget == null) {

            List<Product> products = productTagMappingRepository.findProductsByTags(
                    tagNames,
                    tagTypes,
                    5);

            if (products.isEmpty()) {
                res.setMessage("Bạn muốn mua trong khoảng giá bao nhiêu?");
                res.setSuggestions(List.of(
                        "Dưới 1 triệu",
                        "1 – 2 triệu",
                        "Trên 2 triệu"));
                return res;
            }

            res.setProducts(products.stream().map(this::mapProduct).toList());
            res.setMessage(
                    "Mình tìm được một số sản phẩm. " +
                            "Bạn muốn mua trong khoảng giá bao nhiêu để mình lọc chính xác hơn?");
            res.setSuggestions(List.of(
                    "Dưới 1 triệu",
                    "1 – 2 triệu",
                    "Trên 2 triệu"));
            return res;
        }

        List<Product> products = productTagMappingRepository.findProductsByTagsAndPrice(
                tagNames,
                tagTypes,
                budget,
                10);

        if (products.isEmpty()) {
            res.setMessage("Không tìm thấy sản phẩm phù hợp ngân sách của bạn");
            res.setProducts(List.of());
            return res;
        }

        res.setProducts(products.stream().map(this::mapProduct).toList());
        res.setMessage("Mình tìm được sản phẩm phù hợp với ngân sách của bạn");

        return res;
    }

    private AIProductRes mapProduct(Product p) {
        AIProductRes r = new AIProductRes();
        r.setId(p.getId());
        r.setName(p.getName());
        r.setPrice(p.getPrice());
        r.setLink("/products/" + p.getId());
        return r;
    }

    private BudgetCondition extractBudget(String message) {

        if (message == null)
            return null;

        String msg = message.toLowerCase();

        if (msg.contains("trên")) {
            Integer value = extractNumber(msg);
            if (value != null) {
                return new BudgetCondition(BudgetType.OVER, value, null);
            }
        }

        if (msg.contains("dưới")) {
            Integer value = extractNumber(msg);
            if (value != null) {
                return new BudgetCondition(BudgetType.UNDER, null, value);
            }
        }

        Pattern range = Pattern.compile("(\\d+)\\s*(tr|triệu)?\\s*-\\s*(\\d+)\\s*(tr|triệu)?");
        Matcher m = range.matcher(msg);
        if (m.find()) {
            int min = Integer.parseInt(m.group(1)) * 1_000_000;
            int max = Integer.parseInt(m.group(3)) * 1_000_000;
            return new BudgetCondition(BudgetType.BETWEEN, min, max);
        }

        return null;
    }

    private Integer extractNumber(String msg) {

        Pattern p = Pattern.compile("(\\d+)(\\s*)(k|nghìn|tr|triệu)?");
        Matcher m = p.matcher(msg);

        if (!m.find())
            return null;

        int value = Integer.parseInt(m.group(1));
        String unit = m.group(3);

        if (unit == null)
            return value;
        if (unit.equals("k") || unit.equals("nghìn"))
            return value * 1_000;
        if (unit.equals("tr") || unit.equals("triệu"))
            return value * 1_000_000;

        return value;
    }
}
