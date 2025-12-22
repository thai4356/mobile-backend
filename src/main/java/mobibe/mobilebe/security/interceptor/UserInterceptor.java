package mobibe.mobilebe.security.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import mobibe.mobilebe.converter.Translator;
import mobibe.mobilebe.entity.user.User;
import mobibe.mobilebe.exceptions.BusinessException;
import mobibe.mobilebe.repository.userRepository.UserRepository;
import mobibe.mobilebe.security.JwtTokenProvider;
import mobibe.mobilebe.security.SecurityContexts;

import org.apache.logging.log4j.util.Strings;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

@Log4j2
@Component
public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
            @NotNull Object handler) throws Exception {
        BusinessException exception = new BusinessException(Translator.toLocale("login_required"),
                HttpStatus.UNAUTHORIZED);
        String vendorCode = request.getHeader("Authorization");
        if (Strings.isEmpty(vendorCode)) {
            throw exception;
        }
        String[] header = vendorCode.split(" ");
        if (header.length != 2) {
            throw exception;
        }
        String token = header[1];
        if (jwtTokenProvider.validateTokenRs256(token)) {

            Integer sub = Integer.parseInt(
                    jwtTokenProvider.getSubIdFromJwtRs256(token));

            User user = userRepository.findById(sub)
                    .orElseThrow(() -> exception);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    String.valueOf(user.getId()),
                    null,
                    List.of(
                            new SimpleGrantedAuthority(
                                    "ROLE_" + user.getRole().getName())));

            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);

            SecurityContexts.newContext();
            SecurityContexts.getContext().setData(user);

            return true;
        }

        throw exception;
    }
}
