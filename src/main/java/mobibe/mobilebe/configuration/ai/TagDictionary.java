package mobibe.mobilebe.configuration.ai;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TagDictionary {

    public static final Map<String, List<TagDef>> DICTIONARY = new HashMap<>();

    static {

        // =======================
        // SHOES
        // =======================
        DICTIONARY.put("giày", List.of(
                new TagDef("giày", "SHOES")));

        DICTIONARY.put("giày chạy bộ", List.of(
                new TagDef("giày chạy bộ", "SHOES")));

        DICTIONARY.put("giày bóng rổ", List.of(
                new TagDef("giày bóng rổ", "SHOES")));

        DICTIONARY.put("running shoes", List.of(
                new TagDef("running shoes", "SHOES")));

        DICTIONARY.put("basketball shoes", List.of(
                new TagDef("basketball shoes", "SHOES")));

        // =======================
        // CLOTHING
        // =======================
        DICTIONARY.put("áo gym", List.of(
                new TagDef("áo gym", "CLOTHING")));

        DICTIONARY.put("quần gym", List.of(
                new TagDef("quần gym", "CLOTHING")));

        DICTIONARY.put("áo thể thao", List.of(
                new TagDef("áo thể thao", "CLOTHING")));

        DICTIONARY.put("gym t-shirt", List.of(
                new TagDef("gym t-shirt", "CLOTHING")));

        DICTIONARY.put("sport shorts", List.of(
                new TagDef("sport shorts", "CLOTHING")));

        // =======================
        // EQUIPMENT
        // =======================
        DICTIONARY.put("tạ", List.of(
                new TagDef("tạ", "EQUIPMENT")));

        DICTIONARY.put("dumbbell", List.of(
                new TagDef("dumbbell", "EQUIPMENT")));

        DICTIONARY.put("barbell", List.of(
                new TagDef("barbell", "EQUIPMENT")));

        DICTIONARY.put("dây kháng lực", List.of(
                new TagDef("dây kháng lực", "EQUIPMENT")));

        // =======================
        // ACCESSORY
        // =======================
        DICTIONARY.put("bình nước", List.of(
                new TagDef("bình nước", "ACCESSORY")));

        DICTIONARY.put("khăn tập", List.of(
                new TagDef("khăn tập", "ACCESSORY")));

        DICTIONARY.put("sport watch", List.of(
                new TagDef("sport watch", "ACCESSORY")));

        // =======================
        // BAG
        // =======================
        DICTIONARY.put("túi gym", List.of(
                new TagDef("túi gym", "BAG")));

        DICTIONARY.put("gym bag", List.of(
                new TagDef("gym bag", "BAG")));

        // =======================
        // PROTECTIVE_GEAR
        // =======================
        DICTIONARY.put("găng tay", List.of(
                new TagDef("găng tay", "PROTECTIVE_GEAR")));

        DICTIONARY.put("đai lưng", List.of(
                new TagDef("đai lưng", "PROTECTIVE_GEAR")));

        DICTIONARY.put("knee support", List.of(
                new TagDef("knee support", "PROTECTIVE_GEAR")));
    }

    private TagDictionary() {
        // prevent instantiation
    }
}
