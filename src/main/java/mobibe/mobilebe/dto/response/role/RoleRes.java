package mobibe.mobilebe.dto.response.role;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import mobibe.mobilebe.dto.constant.ActiveStatus;
import mobibe.mobilebe.dto.constant.RoleType;
import mobibe.mobilebe.dto.response.permission.PermissionRes;
import mobibe.mobilebe.entity.role.Role;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleRes {
    int id;
    Integer objectId;
    String name;
    String note;
    RoleType type;
    ActiveStatus status;

    List<PermissionRes> permissions;

    public static RoleRes convertObject(Role role) {
        RoleRes roleRes = new RoleRes();
        roleRes.setId(role.getId());
        roleRes.setObjectId(role.getObjectId());
        roleRes.setNote(role.getNote());
        roleRes.setName(role.getName());
        roleRes.setStatus(role.getStatus());
        roleRes.setType(role.getType());
        return roleRes;
    }
}
