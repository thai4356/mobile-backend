package mobibe.mobilebe.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdminLoginReq {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
