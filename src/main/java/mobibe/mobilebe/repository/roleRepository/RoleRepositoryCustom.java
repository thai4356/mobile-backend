package mobibe.mobilebe.repository.roleRepository;


import java.util.List;

import mobibe.mobilebe.dto.constant.ActiveStatus;
import mobibe.mobilebe.dto.constant.RoleType;
import mobibe.mobilebe.dto.response.permission.PermissionRes;
import mobibe.mobilebe.dto.response.role.RoleDetail;
import mobibe.mobilebe.dto.response.role.RoleRes;
import mobibe.mobilebe.entity.role.Role;
import mobibe.mobilebe.entity.role.constant.PermissionKey;
import mobibe.mobilebe.entity.role.constant.PermissionType;

public interface RoleRepositoryCustom {
    List<Integer> getAllIdToCheckExist(Role role, List<Integer> ids);

    void deleteRoleByIds(List<Integer> ids);

    long countRoles(Role role, ActiveStatus status, String name);

    List<RoleRes> getRoles(int page, Role role, ActiveStatus status, String name);

    boolean existPermission(int roleId, PermissionType[] groups, PermissionKey key, RoleType roleType);

    Role getRoleForAdmin(int roleId, Role role);

    List<PermissionRes> getPermissions(int roleId, Role role);

    RoleDetail getRoleById(Integer roleId);

    Role getUserRole();
}
