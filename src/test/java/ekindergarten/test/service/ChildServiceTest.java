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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class ChildServiceTest extends BaseJpaTestConfig {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChildRepository childRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void setup() {
        UserValidationService userValidationService = new UserValidationService(userRepository);
        UserService userService = new UserService(userRepository, userValidationService, roleRepository, passwordEncoder);

        roleRepository.save(new Role(Constans.ROLE_USER));
        userService.registerNewParent(TestUtil.createUserDto());
    }

    @Test
    public void shouldAddChild() {
        // given
        ChildService childService = new ChildService(childRepository, userRepository);
        childService.addChild(TestUtil.createChild(), 3L);
        // when
        List<Child> result = childRepository.findAll();
        // then
        Assert.assertEquals(result.size(), 1);
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotAddChildWithTheSamePesel() {
        // given
        ChildService childService = new ChildService(childRepository, userRepository);
        childService.addChild(TestUtil.createChild(), 2L);
        childService.addChild(TestUtil.createChild(), 2L);
    }

    @Test
    public void shouldAddSecondChild() {
        // given
        ChildService childService = new ChildService(childRepository, userRepository);
        childService.addChild(TestUtil.createChild(), 1L);
        childService.addChild(
                Child.builder()
                        .withName(Constans.NAME)
                        .withSurname(Constans.SURNAME)
                        .withPesel("77788899955")
                        .build()
                , 1L);
        // when
        List<Child> result = childRepository.findAll();
        // then
        Assert.assertEquals(result.size(), 2);
    }
}