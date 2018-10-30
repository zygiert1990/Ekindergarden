package ekindergarten.repositories;

import ekindergarten.domain.TrustedPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrustedPersonRepository extends JpaRepository<TrustedPerson, Long> {

    TrustedPerson findByCivilId(String civilId);

    TrustedPerson findByPhoneNumber(String phoneNumber);

//    @Query("select tp from TrustedPerson tp join tp.children c join c.users u where u.email like ?1 and c.id=?2")
//    List<TrustedPerson> findAllTrustedPeopleForSpecificChild(String email, long id);

}
