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

import java.util.List;

import static ekindergarten.testingUtils.Constans.*;
import static ekindergarten.testingUtils.TestUtil.createChildDto;
import static ekindergarten.testingUtils.TestUtil.createUserDto;
import static ekindergarten.testingUtils.TestUtil.createUserDtoWithParameters;
import static ekindergarten.utils.UserAuthorities.PARENT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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