package mobibe.mobilebe.dto.response.user;


import java.util.Date;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import mobibe.mobilebe.dto.constant.ActiveStatus;
import mobibe.mobilebe.dto.response.permission.PermissionRes;
import mobibe.mobilebe.dto.response.role.RoleDetail;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDetailRes {
    int id;
    String code;
    String phone;
    String name;
    String email;
    String address;
    Date birthday;
    Integer avatarId;
    ActiveStatus status;
    RoleDetail role;
    List<PermissionRes> permissions;
    String accessToken;

}
