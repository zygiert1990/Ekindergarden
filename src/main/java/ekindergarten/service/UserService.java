package ekindergarten.service;

import ekindergarten.domain.Role;
import ekindergarten.domain.User;
import ekindergarten.model.UserDto;
import ekindergarten.repositories.RoleRepository;
import ekindergarten.repositories.UserRepository;
import ekindergarten.utils.UserRoles;
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

    public User findUserByCivilId(String civilId) throws RuntimeException {
        User user = userRepository.findByCivilId(civilId);
        if (user == null)
            throw new RuntimeException("user with this civilId doesn't exist");
        return user;
    }

    public User findUserById(long id) throws RuntimeException {
        return userRepository.findById(id);
    }

    public User registerNewParent(UserDto userDto) throws RuntimeException {
        if (!userValidationService.isEmailUnique(userDto.getEmail()))
            throw new RuntimeException("account with this e-mail already exists");
        if (!userValidationService.isCivilIdUnique(userDto.getCivilId()))
            throw new RuntimeException("account with this civil id already exists");
        if (!userValidationService.isPhoneNumberUnique(userDto.getPhoneNumber()))
            throw new RuntimeException("account with this phone number already exists");
        Role role = roleRepository.findByRoleName(UserRoles.PARENT);
        User user = User.builder()
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .civilId(userDto.getCivilId())
                .email(userDto.getEmail())
                .phoneNumber(userDto.getPhoneNumber())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(role)
                .build();
        if (role.getUsers() == null){
            Set<User> users = new HashSet<>();
            users.add(user);
            role.setUsers(users);
        } else {
            role.getUsers().add(user);
        }
        return userRepository.save(user);
    }
}