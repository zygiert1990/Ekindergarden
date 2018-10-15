package ekindergarten.repositories;

import ekindergarten.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByCivilId(String civilId);

    User findByEmail(String email);

    User findByPhoneNumber(String phoneNumber);

    User findById(long id);

}
