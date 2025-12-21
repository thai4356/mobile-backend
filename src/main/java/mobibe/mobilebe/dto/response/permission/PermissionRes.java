package mobibe.mobilebe.dto.response.permission;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import mobibe.mobilebe.entity.role.constant.PermissionGroup;
import mobibe.mobilebe.entity.role.constant.PermissionType;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionRes {
    int id;
    String title;
    PermissionType permission;
    PermissionGroup parentPermission;
    Boolean isView;
    Boolean isWrite;
    Boolean isApproval;
    Boolean isDecision;

}
