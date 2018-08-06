package ekindergardenserver.service;

import ekindergardenserver.domain.User;
import ekindergardenserver.model.UserDto;
import ekindergardenserver.repositories.RoleRepository;
import ekindergardenserver.repositories.UserRepository;
import ekindergardenserver.utils.UserValidationService;
import org.apache.commons.codec.digest.DigestUtils;
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

    public User registerNewParent(UserDto userDto) throws RuntimeException {
        if (!userValidationService.isEmailUnique(userDto.getEmail()))
            throw new RuntimeException("account with this e-mail already exists");
        if (!userValidationService.isCivilIdUnique(userDto.getCivilId()))
            throw new RuntimeException("account with this civil id already exists");
        if (!userValidationService.isPhoneNumberUnique(userDto.getPhoneNumber()))
            throw new RuntimeException("account with this phone number already exists");
        return userRepository.save(new User.Builder()
                .withName(userDto.getName())
                .withSurname(userDto.getSurname())
                .withCivilId(userDto.getCivilId())
                .withEmail(userDto.getEmail())
                .withPhoneNumber(userDto.getPhoneNumber())
                .withPassword(passwordEncoder.encode(userDto.getPassword()))
                .withRole(roleRepository.findByRoleName("PARENT"))
                .build());
    }
}