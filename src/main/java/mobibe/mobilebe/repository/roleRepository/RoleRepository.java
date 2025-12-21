package mobibe.mobilebe.repository.roleRepository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mobibe.mobilebe.entity.role.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>, RoleRepositoryCustom {
}
