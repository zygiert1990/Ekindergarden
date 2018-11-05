package ekindergarten.service;

import ekindergarten.domain.Child;
import ekindergarten.domain.TrustedPerson;
import ekindergarten.repositories.ChildRepository;
import ekindergarten.repositories.TrustedPersonRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TrustedPersonService {

    private final TrustedPersonRepository trustedPersonRepository;
    private final ChildRepository childRepository;

    public TrustedPersonService(TrustedPersonRepository trustedPersonRepository, ChildRepository childRepository) {
        this.trustedPersonRepository = trustedPersonRepository;
        this.childRepository = childRepository;
    }

    public TrustedPerson addTrustedPerson(TrustedPerson trustedPerson, long id) throws RuntimeException {

        Child child = childRepository.findById(id);
        TrustedPerson trustedPersonFromDB = trustedPersonRepository.findByCivilId(trustedPerson.getCivilId());
        Set<TrustedPerson> trustedPeople = child.getTrustedPeople();

        if (trustedPersonFromDB != null && (trustedPeople != null && trustedPeople.contains(trustedPersonFromDB)))
            throw new RuntimeException("Dodałeś już wcześniej tą osobę!");

        if (trustedPersonFromDB == null) {
            checkIfChildHasAlreadyAnyTrustedPerson(trustedPerson, child, trustedPeople);
            return trustedPerson;
        }
        checkIfChildHasAlreadyAnyTrustedPerson(trustedPersonFromDB, child, trustedPeople);
        return trustedPersonFromDB;
    }

    public void deleteTrustedPerson(long childId, long trustedPersonId) {
        Child child = childRepository.findById(childId);
        TrustedPerson trustedPerson = trustedPersonRepository.findById(trustedPersonId);
        child.getTrustedPeople().remove(trustedPerson);
        childRepository.flush();
    }

    private void checkIfChildHasAlreadyAnyTrustedPerson(
            TrustedPerson trustedPerson, Child child, Set<TrustedPerson> trustedPeople) {
        if (trustedPeople == null) {
            addTrustedPersonToChild(trustedPerson, child);
        } else {
            trustedPeople.add(trustedPerson);
        }
        childRepository.save(child);
    }

    private void addTrustedPersonToChild(TrustedPerson trustedPerson, Child child) {
        Set<TrustedPerson> trustedPeople = new HashSet<>();
        trustedPeople.add(trustedPerson);
        child.setTrustedPeople(trustedPeople);
    }

}
