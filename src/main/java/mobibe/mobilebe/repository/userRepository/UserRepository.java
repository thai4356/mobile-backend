package mobibe.mobilebe.repository.userRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mobibe.mobilebe.entity.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, UserRepositoryCustom {
    boolean existsByPhone(String phone);
    User findByEmail(String email);
}
