package ekindergarten.test.service;

import ekindergarten.domain.Role;
import ekindergarten.domain.User;
import ekindergarten.model.UserDto;
import ekindergarten.repositories.RoleRepository;
import ekindergarten.repositories.UserRepository;
import ekindergarten.service.UserService;
import ekindergarten.test.repositories.BaseJpaTestConfig;
import ekindergarten.utils.UserValidationService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static ekindergarten.testingUtils.Constans.*;
import static ekindergarten.testingUtils.TestUtil.createUserDto;
import static ekindergarten.testingUtils.TestUtil.createUserDtoWithParameters;
import static ekindergarten.utils.UserRoles.PARENT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserServiceTest extends BaseJpaTestConfig {

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

        roleRepository.save(new Role(PARENT));

        userService.registerParent(createUserDto());
    }

    @Test
    public void shouldFindUserByCivilId() {
        //when
        User result = userService.findUserByCivilId(CIVIL_ID);
        //then
        assertNotNull(result);
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
        userService.registerParent(createUserDtoWithParameters(NEW_EMAIL, NEW_CIVIL_ID, NEW_PHONE_NUMBER));
        //when
        List<User> result = userRepository.findAll();
        //then
        assertEquals(result.size(), 2);
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotRegisterNewUserWhenEmailIsNotUnique() {
        //given
        UserDto userDtoWithSameEMail =
                createUserDtoWithParameters(EMAIL, NEW_CIVIL_ID, NEW_PHONE_NUMBER);
        userService.registerParent(userDtoWithSameEMail);
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotRegisterNewUserWhenCivilIdIsNotUnique() {
        //given
        UserDto userDtoWithSameEMail =
                createUserDtoWithParameters(NEW_EMAIL, CIVIL_ID, NEW_PHONE_NUMBER);
        userService.registerParent(userDtoWithSameEMail);
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotRegisterNewUserWhenPhoneNumberIsNotUnique() {
        //given
        UserDto userDtoWithSameEMail =
                createUserDtoWithParameters(NEW_EMAIL, NEW_CIVIL_ID, PHONE_NUMBER);
        userService.registerParent(userDtoWithSameEMail);
    }
}