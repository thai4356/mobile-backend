package mobibe.mobilebe.repository.roleRepository;


import org.springframework.data.jpa.repository.JpaRepository;

import mobibe.mobilebe.entity.role.role_permission.RolePermission;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {
    RolePermission findByRoleIdAndPermissionId(int roleId, int permissionId);
}
