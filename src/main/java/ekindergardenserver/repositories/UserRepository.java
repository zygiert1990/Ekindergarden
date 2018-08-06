package ekindergardenserver.repositories;

import ekindergardenserver.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    List<User> findAll();

    User findByCivilId(String civilId);

    User findByEmail(String email);

    User findByPhoneNumber(String phoneNumber);

}
