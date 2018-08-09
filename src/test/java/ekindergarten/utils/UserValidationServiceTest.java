package ekindergarten.utils;

import utils.Constans;
import ekindergarten.domain.User;
import ekindergarten.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserValidationServiceTest {

    @InjectMocks
    private UserValidationService userValidationService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp() {
        User user = new User.Builder()
                .withCivilId(Constans.CIVIL_ID)
                .withEmail(Constans.EMAIL)
                .withPhoneNumber(Constans.PHONE_NUMBER)
                .build();
        Mockito.when(userRepository.findByEmail(Constans.EMAIL)).thenReturn(user);
        Mockito.when(userRepository.findByCivilId(Constans.CIVIL_ID)).thenReturn(user);
        Mockito.when(userRepository.findByPhoneNumber(Constans.PHONE_NUMBER)).thenReturn(user);
    }

    @Test
    public void shouldValidateIsEmailUnique() {
        //when
        boolean expectedFalse = userValidationService.isEmailUnique(Constans.EMAIL);
        boolean expectedTrue = userValidationService.isEmailUnique("fakeEmail");
        //then
        Assert.assertFalse(expectedFalse);
        Assert.assertTrue(expectedTrue);
    }

    @Test
    public void shouldValidateIsCivilIdUnique() {
        //when
        boolean expectedFalse = userValidationService.isCivilIdUnique((Constans.CIVIL_ID));
        boolean expectedTrue = userValidationService.isCivilIdUnique("fakeCivilId");
        //then
        Assert.assertFalse(expectedFalse);
        Assert.assertTrue(expectedTrue);
    }

    @Test
    public void shouldValidateIsPhoneNumberUnique() {
        //when
        boolean expectedFalse = userValidationService.isPhoneNumberUnique((Constans.PHONE_NUMBER));
        boolean expectedTrue = userValidationService.isPhoneNumberUnique("fakePhoneNumber");
        //then
        Assert.assertFalse(expectedFalse);
        Assert.assertTrue(expectedTrue);
    }

}