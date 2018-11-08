package ekindergarten.service;

import ekindergarten.domain.Child;
import ekindergarten.domain.Payment;
import ekindergarten.domain.TrustedPerson;
import ekindergarten.domain.User;
import ekindergarten.model.ChildDto;
import ekindergarten.repositories.ChildRepository;
import ekindergarten.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ChildService {

    private final ChildRepository childRepository;
    private final UserRepository userRepository;

    public ChildService(ChildRepository childRepository, UserRepository userRepository) {
        this.childRepository = childRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Child addChild(final ChildDto childDto) throws RuntimeException {
        if (childRepository.findByPesel(childDto.getPesel()) != null)
            throw new RuntimeException("Child with this pesel has already added");
        else {
            Child childToPersist = Child.builder()
                    .name(childDto.getName())
                    .surname(childDto.getSurname())
                    .pesel(childDto.getPesel())
                    .isActive(true)
                    .payment(Payment
                            .builder()
                            .balance(new BigDecimal(-300.00))
                            .paymentMonth(LocalDate.now().withDayOfMonth(1))
                            .build())
                    .build();
            checkIfParentAlreadyExist(childDto.getFirstParentCivilId(), childToPersist);
            if (childDto.getSecondParentCivilId() == null) {
                return childToPersist;
            } else {
                checkIfParentAlreadyExist(childDto.getSecondParentCivilId(), childToPersist);
                return childToPersist;
            }
        }
    }

    public Set<TrustedPerson> getTrustedPeopleForSpecificChild(long id) {
        return childRepository.findById(id).getTrustedPeople();
    }

    public List<Child> findAllChildren() {
        return childRepository.findAll();
    }

    public Child getSpecificChildById(long id) {
        return childRepository.findById(id);
    }

    public Set<Child> findAllParentChildren(String email) {
        return userRepository.findByEmail(email).getChildren();
    }

    public String getChildAdditionalInfo(long childId) {
        return childRepository.findById(childId).getAdditionalInfo();
    }

    public void setChildAdditionalInfo(long childId, String additionalInfo) {
        Child child = childRepository.findById(childId);
        child.setAdditionalInfo(additionalInfo);
        childRepository.flush();
    }

    private void addUserToChild(Child child, User user) {
        if (child.getUsers() != null) {
            child.getUsers().add(user);
        } else {
            Set<User> users = new HashSet<>();
            users.add(user);
            child.setUsers(users);
        }
    }

    private void checkIfParentAlreadyExist(String civilId, Child child) {
        User user = userRepository.findByCivilId(civilId);
        if (user == null) {
            User userToPersist = User.builder().civilId(civilId).build();
            addUserToChild(child, userToPersist);
            childRepository.save(child);
        } else {
            addUserToChild(child, user);
            childRepository.save(child);
        }
    }

}
