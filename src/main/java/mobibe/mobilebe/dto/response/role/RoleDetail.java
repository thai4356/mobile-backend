package mobibe.mobilebe.dto.response.role;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import mobibe.mobilebe.dto.constant.RoleType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleDetail {
    Integer roleId;
    Integer objectId;
    String roleName;
    RoleType roleType;
}
