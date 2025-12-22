package mobibe.mobilebe.dto.request.auth.user;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import mobibe.mobilebe.dto.constant.RoleType;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterUser {

    @NotBlank
    String accountPhone;
    String accountName;
    @NotBlank
    String email;
    @NotBlank
    String password;
    @NotBlank
    String confirmPassword;

    @NotNull
    RoleType businessRole;

    @NotBlank
    String code;
    
    @AssertTrue(message = "Password don't matching")
    public boolean isValid() {
        return password.equals(confirmPassword);
    }

    MultipartFile avatar;
}
