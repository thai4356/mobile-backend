package mobibe.mobilebe.converter;

import java.util.Locale;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class Translator implements ApplicationContextAware {

    private static MessageSource messageSource;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        messageSource = applicationContext.getBean(MessageSource.class);
    }

    public static String toLocale(String msgCode) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(
                msgCode,
                null,
                msgCode,   // ✅ fallback – KHÔNG BAO GIỜ CRASH
                locale
        );
    }
}
