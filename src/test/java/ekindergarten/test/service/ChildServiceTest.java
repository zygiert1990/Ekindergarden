package ekindergarten.test.service;

import ekindergarten.domain.Child;
import ekindergarten.domain.Role;
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
import java.util.Set;

import static ekindergarten.testingUtils.Constans.*;
import static ekindergarten.testingUtils.TestUtil.*;
import static ekindergarten.utils.UserRoles.PARENT;
import static org.junit.Assert.assertEquals;

public class ChildServiceTest extends BaseJpaTestConfig {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChildRepository childRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private ChildService childService;
    private UserService userService;

    @Before
    public void setup() {
        UserValidationService userValidationService = new UserValidationService(userRepository);
        userService = new UserService(userRepository, userValidationService, roleRepository, passwordEncoder);

        roleRepository.save(new Role(PARENT));
        userService.registerParent(createUserDto());

        childService = new ChildService(childRepository, userRepository);
    }

    @Test
    public void shouldAddChild() {
        // given
        childService.addChild(createChild(), EMAIL);
        // when
        List<Child> result = childRepository.findAll();
        // then
        assertEquals(result.size(), 1);
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotAddChildWithTheSamePesel() {
        // given
        childService.addChild(createChild(), EMAIL);
        childService.addChild(createChild(), EMAIL);
    }

    @Test
    public void shouldAddSecondChild() {
        // given
        childService.addChild(createChild(), EMAIL);
        childService.addChild(createChildWithPesel(NEW_PESEL), EMAIL);
        // when
        List<Child> result = childRepository.findAll();
        // then
        assertEquals(result.size(), 2);
    }

    @Test
    public void shouldFindAllParentChildren() {
        // given
        childService.addChild(createChild(), EMAIL);
        // when
        Set<Child> result = childService.findAllParentChildren(EMAIL);
        // then
        assertEquals(result.size(), 1);
    }

    @Test
    public void shouldFindAllParentChildrenWhenTwoDifferentParentExists() {
        // given
        childService.addChild(createChild(), EMAIL);
        userService.registerParent(
                createUserDtoWithParameters(NEW_EMAIL, NEW_CIVIL_ID, NEW_PHONE_NUMBER)
        );
        childService.addChild(createChildWithPesel(NEW_PESEL), NEW_EMAIL);
        // when
        Set<Child> firstParentChildren = childService.findAllParentChildren(EMAIL);
        Set<Child> secondParentChildren = childService.findAllParentChildren(NEW_EMAIL);
        // then
        assertEquals(firstParentChildren.size(), 1);
        assertEquals(secondParentChildren.size(), 1);
    }
}