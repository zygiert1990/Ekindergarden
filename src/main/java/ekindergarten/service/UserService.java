package ekindergarten.service;

import ekindergarten.domain.Role;
import ekindergarten.domain.User;
import ekindergarten.model.UserDto;
import ekindergarten.repositories.RoleRepository;
import ekindergarten.repositories.UserRepository;
import ekindergarten.utils.UserAuthorities;
import ekindergarten.utils.UserValidationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserValidationService userValidationService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserValidationService userValidationService, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userValidationService = userValidationService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerParent(UserDto userDto) throws RuntimeException {
        User user = userRepository.findByCivilId(userDto.getCivilId());
        if (user == null)
            throw new RuntimeException("There is no child related to this civil id, please contact with admin");
        if (user.getName() != null)
            throw new RuntimeException("Account with this civil id already exists");
        if (!userValidationService.isPhoneNumberUnique(userDto.getPhoneNumber()))
            throw new RuntimeException("Account with this phone number already exists");
        if (!userValidationService.isEmailUnique(userDto.getEmail()))
            throw new RuntimeException("Account with this e-mail already exists");

        Role role = roleRepository.findByRoleName(UserAuthorities.PARENT);
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(role);

        if (role.getUsers() == null) {
            Set<User> users = new HashSet<>();
            users.add(user);
            role.setUsers(users);
        } else {
            role.getUsers().add(user);
        }

        userRepository.flush();
        return user;
    }
}