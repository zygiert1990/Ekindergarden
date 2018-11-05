package ekindergarten.repositories;

import ekindergarten.domain.TrustedPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrustedPersonRepository extends JpaRepository<TrustedPerson, Long> {

    TrustedPerson findByCivilId(String civilId);

    TrustedPerson findByPhoneNumber(String phoneNumber);

    TrustedPerson findById(long id);

}
