package mobibe.mobilebe.repository.permission;

import mobibe.mobilebe.dto.constant.RoleType;
import mobibe.mobilebe.entity.role.permission.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    @Query("select p from Permission p where p.status = 1 and p.deleted = false and p.type = :type")
    List<Permission> getPermissions(@Param("type") RoleType type);
}
