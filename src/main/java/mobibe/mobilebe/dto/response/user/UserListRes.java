package mobibe.mobilebe.dto.response.user;

import java.sql.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import mobibe.mobilebe.dto.constant.ActiveStatus;
import mobibe.mobilebe.dto.response.role.RoleDetail;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserListRes {
    int id;
    String code;
    String name;
    String email;
    String address;
    String phone;
    ActiveStatus status;
    Date birthday;
    RoleDetail role;
}
