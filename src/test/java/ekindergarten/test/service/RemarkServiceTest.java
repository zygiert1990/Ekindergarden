package ekindergarten.test.service;

import ekindergarten.domain.Child;
import ekindergarten.domain.Remark;
import ekindergarten.repositories.ChildRepository;
import ekindergarten.repositories.RemarkRepository;
import ekindergarten.repositories.RoleRepository;
import ekindergarten.repositories.UserRepository;
import ekindergarten.service.ChildService;
import ekindergarten.service.RemarkService;
import ekindergarten.service.UserService;
import ekindergarten.test.repositories.BaseJpaTestConfig;
import ekindergarten.testingUtils.TestUtil;
import ekindergarten.utils.UserValidationService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static ekindergarten.testingUtils.Constans.EMAIL;
import static ekindergarten.testingUtils.TestUtil.createChildDto;
import static ekindergarten.testingUtils.TestUtil.createRemarkDto;
import static ekindergarten.testingUtils.TestUtil.createTeacherDto;
import static org.junit.Assert.assertEquals;

public class RemarkServiceTest extends BaseJpaTestConfig {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChildRepository childRepository;
    @Autowired
    private RemarkRepository remarkRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private ChildService childService;
    private RemarkService remarkService;
    private UserService userService;

    @Before
    public void setup() {
        childService = new ChildService(childRepository, userRepository);
        remarkService = new RemarkService(remarkRepository, userRepository, childRepository);
        userService = new UserService(userRepository, new UserValidationService(userRepository), roleRepository, passwordEncoder);
    }

    @Test
    public void shouldAddRemark() {
        // given
        Child child = childService.addChild(createChildDto());
        userService.registerTeacher(createTeacherDto());
        remarkService.addRemark(createRemarkDto(), EMAIL, child.getId());
        // when
        List<Remark> result = remarkRepository.findAll();
        // then
        assertEquals(result.size(), 1);
    }

}
