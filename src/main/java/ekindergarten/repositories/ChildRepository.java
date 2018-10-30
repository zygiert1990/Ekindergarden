package ekindergarten.repositories;

import ekindergarten.domain.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {

    List<Child> findAllByName(String name);

    List<Child> findAllByNameAndSurname(String name, String surname);

    Child findByPesel(String pesel);

    Child findById(long id);

}
