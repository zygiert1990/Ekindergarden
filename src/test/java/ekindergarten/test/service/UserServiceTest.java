package ekindergarten.test.service;

import ekindergarten.domain.Role;
import ekindergarten.domain.User;
import ekindergarten.model.UserDto;
import ekindergarten.repositories.RoleRepository;
import ekindergarten.repositories.UserRepository;
import ekindergarten.service.UserService;
import ekindergarten.test.repositories.BaseJpaTestConfig;
import ekindergarten.testingUtils.Constans;
import ekindergarten.testingUtils.TestUtil;
import ekindergarten.utils.UserValidationService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserServiceTest extends BaseJpaTestConfig {

    private static final String NEW_CIVIL_ID = "QWE123456";
    private static final String NEW_PHONE_NUMBER = "999666333";
    private static final String NEW_EMAIL = "just@wp.pl";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    @Before
    public void setup() {
        UserValidationService userValidationService = new UserValidationService(userRepository);
        userService = new UserService(userRepository, userValidationService, roleRepository, passwordEncoder);

        roleRepository.save(new Role(Constans.ROLE_USER));

        userService.registerNewParent(TestUtil.createUserDto());
    }

    @Test
    public void shouldRegisterNewUser() {
        //when
        List<User> result = userRepository.findAll();
        //then
        assertEquals(result.size(), 1);
    }

    @Test
    public void shouldRegisterSecondUser() {
        //given
        userService.registerNewParent(createUserDtoWithParameters(NEW_EMAIL, NEW_CIVIL_ID, NEW_PHONE_NUMBER));
        //when
        List<User> result = userRepository.findAll();
        //then
        assertEquals(result.size(), 2);
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotRegisterNewUserWhenEmailIsNotUnique() {
        //given
        UserDto userDtoWithSameEMail =
                createUserDtoWithParameters(Constans.EMAIL, NEW_CIVIL_ID, NEW_PHONE_NUMBER);
        userService.registerNewParent(userDtoWithSameEMail);
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotRegisterNewUserWhenCivilIdIsNotUnique() {
        //given
        UserDto userDtoWithSameEMail =
                createUserDtoWithParameters(NEW_EMAIL, Constans.CIVIL_ID, NEW_PHONE_NUMBER);
        userService.registerNewParent(userDtoWithSameEMail);
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotRegisterNewUserWhenPhoneNumberIsNotUnique() {
        //given
        UserDto userDtoWithSameEMail =
                createUserDtoWithParameters(NEW_EMAIL, NEW_CIVIL_ID, Constans.PHONE_NUMBER);
        userService.registerNewParent(userDtoWithSameEMail);
    }

    private UserDto createUserDtoWithParameters(String email, String civilId, String phoneNumber) {
        return UserDto.builder()
                .withName(Constans.NAME)
                .withSurname(Constans.SURNAME)
                .withEmail(email)
                .withCivilId(civilId)
                .withPhoneNumber(phoneNumber)
                .withPassword(Constans.PASSWORD)
                .build();
    }
}