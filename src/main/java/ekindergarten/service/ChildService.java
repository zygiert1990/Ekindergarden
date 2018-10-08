package ekindergarten.service;

import ekindergarten.domain.Child;
import ekindergarten.domain.User;
import ekindergarten.repositories.ChildRepository;
import ekindergarten.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ChildService {

    private final ChildRepository childRepository;
    private final UserRepository userRepository;

    public ChildService(ChildRepository childRepository, UserRepository userRepository) {
        this.childRepository = childRepository;
        this.userRepository = userRepository;
    }

    public Child addChild(final Child child, final String email) throws RuntimeException {
        User user = userRepository.findByEmail(email);
        child.setActive(true);
        if (user.getChildren() == null) {
            Set<Child> children = new HashSet<>();
            children.add(child);
            user.setChildren(children);
            addUserToChild(child, user);
            return childRepository.save(child);
        }
        if (user.getChildren().contains(child))
            throw new RuntimeException("this child has already added");
        user.getChildren().add(child);
        addUserToChild(child, user);
        return childRepository.save(child);
    }

    private void addUserToChild(Child child, User user) {
        Set<User> users = new HashSet<>();
        users.add(user);
        child.setUsers(users);
    }
}
