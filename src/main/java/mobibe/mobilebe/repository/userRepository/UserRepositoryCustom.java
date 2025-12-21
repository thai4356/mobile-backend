package mobibe.mobilebe.repository.userRepository;

import java.util.List;

import mobibe.mobilebe.dto.constant.ActiveStatus;
import mobibe.mobilebe.dto.response.user.UserDetailRes;
import mobibe.mobilebe.dto.response.user.UserListRes;
import mobibe.mobilebe.entity.role.Role;
import mobibe.mobilebe.entity.user.User;

public interface UserRepositoryCustom {
    User loginByEmail(String email);

    User getUserByPhone(String phone);

    boolean existsByCode (String code);

    long countUser(ActiveStatus status, String searchKeyword, Role role);

    List<UserListRes> getUsers(ActiveStatus status, String searchKeyword, int page, Role role);

    UserDetailRes getProfileUser(int accountId, Role role);

    List<Integer> getAllIdToCheckExist(List<Integer> userIds, Role role);

    void deleteUsers(List<Integer> userIds);

    User getUserToUpdate(int userId, Role role);
}
