package ekindergarten.test.service;

import ekindergarten.domain.Role;
import ekindergarten.domain.User;
import ekindergarten.model.UserDto;
import ekindergarten.repositories.ChildRepository;
import ekindergarten.repositories.RoleRepository;
import ekindergarten.repositories.UserRepository;
import ekindergarten.service.ChildService;
import ekindergarten.service.UserService;
import ekindergarten.test.repositories.BaseJpaTestConfig;
import ekindergarten.utils.UserValidationService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import static ekindergarten.testingUtils.Constans.*;
import static ekindergarten.testingUtils.TestUtil.*;
import static ekindergarten.utils.UserAuthorities.PARENT;
import static org.junit.Assert.assertEquals;

public class UserServiceTest extends BaseJpaTestConfig {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ChildRepository childRepository;

    private UserService userService;
    private ChildService childService;

    @Before
    public void setup() {
        UserValidationService userValidationService = new UserValidationService(userRepository);
        userService = new UserService(userRepository, userValidationService, roleRepository, passwordEncoder);
        childService = new ChildService(childRepository, userRepository);

        roleRepository.save(new Role(PARENT));
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotRegisterNewParentWhenThereIsNoChildRelatedToUser() {
        userService.registerParent(createUserDto());
    }

    @Test
    public void shouldRegisterNewParent() {
        //give
        childService.addChild(createChildDto());
        userService.registerParent(createUserDto());
        //when
        User result = userRepository.findByEmail(EMAIL);
        //then
        assertEquals(result.getName(), NAME);
    }

    @Test
    public void shouldRegisterTwoParentsToOneChild() {
        //give
        childService.addChild(createChildDtoWithTwoCivilIds());
        userService.registerParent(createUserDto());
        userService.registerParent(UserDto.builder()
                .name(NAME)
                .surname(SURNAME)
                .email(NEW_EMAIL)
                .phoneNumber(NEW_PHONE_NUMBER)
                .civilId(NEW_CIVIL_ID)
                .password(PASSWORD)
                .matchingPassword(PASSWORD)
                .build());
        //when
        User firstParent = userRepository.findByEmail(EMAIL);
        User secondParent = userRepository.findByEmail(NEW_EMAIL);
        //then
        assertEquals(firstParent.getCivilId(), CIVIL_ID);
        assertEquals(firstParent.getChildren().size(), 1);
        assertEquals(secondParent.getCivilId(), NEW_CIVIL_ID);
        assertEquals(secondParent.getChildren().size(), 1);
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotRegisterSameParentTwice() {
        //give
        childService.addChild(createChildDto());
        userService.registerParent(createUserDto());
        userService.registerParent(UserDto.builder()
                .name(NAME)
                .civilId(CIVIL_ID)
                .build());
    }
}