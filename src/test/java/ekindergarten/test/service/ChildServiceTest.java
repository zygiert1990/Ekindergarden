package ekindergarten.test.service;

import ekindergarten.domain.Child;
import ekindergarten.domain.Role;
import ekindergarten.repositories.ChildRepository;
import ekindergarten.repositories.RoleRepository;
import ekindergarten.repositories.UserRepository;
import ekindergarten.service.ChildService;
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

    @Before
    public void setup() {
        UserValidationService userValidationService = new UserValidationService(userRepository);
        UserService userService = new UserService(userRepository, userValidationService, roleRepository, passwordEncoder);

        roleRepository.save(new Role(Constans.ROLE_USER));
        userService.registerNewParent(TestUtil.createUserDto());

        childService = new ChildService(childRepository, userRepository);
    }

    @Test
    public void shouldAddChild() {
        // given
        childService.addChild(TestUtil.createChild(), Constans.EMAIL);
        // when
        List<Child> result = childRepository.findAll();
        // then
        assertEquals(result.size(), 1);
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotAddChildWithTheSamePesel() {
        // given
        childService.addChild(TestUtil.createChild(), Constans.EMAIL);
        childService.addChild(TestUtil.createChild(), Constans.EMAIL);
    }

    @Test
    public void shouldAddSecondChild() {
        // given
        childService.addChild(TestUtil.createChild(), Constans.EMAIL);
        childService.addChild(
                Child.builder()
                        .withName(Constans.NAME)
                        .withSurname(Constans.SURNAME)
                        .withPesel("77788899955")
                        .build()
                , Constans.EMAIL);
        // when
        List<Child> result = childRepository.findAll();
        // then
        assertEquals(result.size(), 2);
    }
}