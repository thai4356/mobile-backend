package mobibe.mobilebe.util;

import mobibe.mobilebe.dto.constant.BaseEnum;
import org.reflections.Reflections;

import java.util.Set;

public class EnumHelper {

    private static class Holder {
        static final Set<Class<? extends BaseEnum>> allEnums = initEnums();

        private static Set<Class<? extends BaseEnum>> initEnums() {
            Reflections reflections = new Reflections("com.app84soft.karaoke");
            return reflections.getSubTypesOf(BaseEnum.class);
        }
    }

    public static Set<Class<? extends BaseEnum>> getAllEnums() {
        return Holder.allEnums;
    }
}
