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
            throw new RuntimeException("Nie ma dziecka powiązanego z tym numerem dowodu, prosimy o kontakt z administratorem");
        if (user.getName() != null)
            throw new RuntimeException("Konto z tym numerem dowodu już istnieje!");
        if (!userValidationService.isPhoneNumberUnique(userDto.getPhoneNumber()))
            throw new RuntimeException("Konto z tym numerem telefonu już istnieje!");
        if (!userValidationService.isEmailUnique(userDto.getEmail()))
            throw new RuntimeException("Konto z tym adresem e-mail już istnieje!");

        Role role = roleRepository.findByRoleName(UserAuthorities.PARENT);
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(role);

        userRepository.flush();
        return user;
    }

    public User registerTeacher(UserDto userDto) throws RuntimeException {
        if (!userValidationService.isCivilIdUnique(userDto.getCivilId()))
            throw new RuntimeException("Konto z tym numerem dowodu już istnieje!!");
        if (!userValidationService.isPhoneNumberUnique(userDto.getPhoneNumber()))
            throw new RuntimeException("Konto z tym numerem telefonu już istnieje!");
        if (!userValidationService.isEmailUnique(userDto.getEmail()))
            throw new RuntimeException("Konto z tym adresem e-mail już istnieje!");

        Role role = roleRepository.findByRoleName(UserAuthorities.TEACHER);
        return userRepository.save(User.builder()
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .civilId(userDto.getCivilId())
                .email(userDto.getEmail())
                .phoneNumber(userDto.getPhoneNumber())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(role)
                .build()
        );
    }
}