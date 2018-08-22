package ekindergarten.service;

import utils.Constans;
import ekindergarten.domain.Role;
import ekindergarten.domain.User;
import ekindergarten.model.UserDto;
import ekindergarten.repositories.RoleRepository;
import ekindergarten.repositories.UserRepository;
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

@SpringBootTest
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
        userService.registerNewParent(createUserDto());
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotRegisterNewUserWhenCivilIdIsNotUnique() {
        //given
        userValidatorServiceMock(true, false, true);
        userService.registerNewParent(createUserDto());
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotRegisterNewUserWhenPhoneNumberIsNotUnique() {
        //given
        userValidatorServiceMock(true, true, false);
        userService.registerNewParent(createUserDto());
    }

    @Test
    public void shouldRegisterNewUser() {
        //given
        createUser();
        userValidatorServiceMock(true, true, true);
        Mockito.when(roleRepository.findByRoleName(Constans.ROLE_USER)).thenReturn(new Role(Constans.ROLE_USER));
        Mockito.when(passwordEncoder.encode(Constans.PASSWORD)).thenReturn("");
        //when
        User result = userService.registerNewParent(createUserDto());
        //then
        Assert.assertEquals(result.getName(), Constans.NAME);
    }

    private UserDto createUserDto() {
        return new UserDto.Builder()
                .withName(Constans.NAME)
                .withSurname(Constans.SURNAME)
                .withCivilId(Constans.CIVIL_ID)
                .withEmail(Constans.EMAIL)
                .withPhoneNumber(Constans.PHONE_NUMBER)
                .withPassword(Constans.PASSWORD)
                .build();
    }

    private void userValidatorServiceMock(
            boolean checkEmailResult, boolean checkCivilIdResult, boolean checkPhoneNumberResult) {
        Mockito.when(userValidationService.isEmailUnique(Constans.EMAIL)).thenReturn(checkEmailResult);
        Mockito.when(userValidationService.isCivilIdUnique(Constans.CIVIL_ID)).thenReturn(checkCivilIdResult);
        Mockito.when(userValidationService.isPhoneNumberUnique(Constans.PHONE_NUMBER)).thenReturn(checkPhoneNumberResult);
    }

    private void createUser(){
        User user = new User.Builder()
                .withName(Constans.NAME)
                .withSurname(Constans.SURNAME)
                .withCivilId(Constans.CIVIL_ID)
                .withEmail(Constans.EMAIL)
                .withPhoneNumber(Constans.PHONE_NUMBER)
                .withPassword("")
                .withRole(new Role(Constans.ROLE_USER))
                .build();
        Mockito.when(userRepository.save(user)).thenReturn(user);
    }
}