package ekindergarten.test.service;

import ekindergarten.MainTest;
import ekindergarten.domain.Role;
import ekindergarten.domain.User;
import ekindergarten.repositories.RoleRepository;
import ekindergarten.repositories.UserRepository;
import ekindergarten.service.UserService;
import ekindergarten.utils.UserValidationService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import ekindergarten.testingUtils.Constans;
import ekindergarten.testingUtils.TestUtil;

@SpringBootTest(classes = MainTest.class)
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserValidationService userValidationService;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test(expected = RuntimeException.class)
    public void shouldNotRegisterNewUserWhenEmailIsNotUnique() {
        //given
        userValidatorServiceMock(false, true, true);
        userService.registerNewParent(TestUtil.createUserDto());
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotRegisterNewUserWhenCivilIdIsNotUnique() {
        //given
        userValidatorServiceMock(true, false, true);
        userService.registerNewParent(TestUtil.createUserDto());
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotRegisterNewUserWhenPhoneNumberIsNotUnique() {
        //given
        userValidatorServiceMock(true, true, false);
        userService.registerNewParent(TestUtil.createUserDto());
    }

    @Test
    public void shouldRegisterNewUser() {
        //given
        userRepositoryMock();
        userValidatorServiceMock(true, true, true);
        Mockito.when(roleRepository.findByRoleName(Constans.ROLE_USER)).thenReturn(new Role(Constans.ROLE_USER));
        Mockito.when(passwordEncoder.encode(Constans.PASSWORD)).thenReturn(Constans.PASSWORD);
        //when
        User result = userService.registerNewParent(TestUtil.createUserDto());
        //then
        Assert.assertEquals(result.getName(), Constans.NAME);
    }

    private void userValidatorServiceMock(
            boolean checkEmailResult, boolean checkCivilIdResult, boolean checkPhoneNumberResult) {
        Mockito.when(userValidationService.isEmailUnique(Constans.EMAIL)).thenReturn(checkEmailResult);
        Mockito.when(userValidationService.isCivilIdUnique(Constans.CIVIL_ID)).thenReturn(checkCivilIdResult);
        Mockito.when(userValidationService.isPhoneNumberUnique(Constans.PHONE_NUMBER)).thenReturn(checkPhoneNumberResult);
    }

    private void userRepositoryMock() {
        User user = TestUtil.createUser();
        Mockito.when(userRepository.save(user)).thenReturn(user);
    }
}