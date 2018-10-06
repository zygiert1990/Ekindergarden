package ekindergarten.test.utils;

import ekindergarten.domain.User;
import ekindergarten.repositories.UserRepository;
import ekindergarten.test.service.BaseServiceTest;
import ekindergarten.testingUtils.Constans;
import ekindergarten.utils.UserValidationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class UserValidationServiceTest extends BaseServiceTest {

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
        when(userRepository.findByEmail(Constans.EMAIL)).thenReturn(user);
        when(userRepository.findByCivilId(Constans.CIVIL_ID)).thenReturn(user);
        when(userRepository.findByPhoneNumber(Constans.PHONE_NUMBER)).thenReturn(user);
    }

    @Test
    public void shouldValidateIsEmailUnique() {
        //when
        boolean expectedFalse = userValidationService.isEmailUnique(Constans.EMAIL);
        boolean expectedTrue = userValidationService.isEmailUnique("fakeEmail");
        //then
        assertFalse(expectedFalse);
        assertTrue(expectedTrue);
    }

    @Test
    public void shouldValidateIsCivilIdUnique() {
        //when
        boolean expectedFalse = userValidationService.isCivilIdUnique((Constans.CIVIL_ID));
        boolean expectedTrue = userValidationService.isCivilIdUnique("fakeCivilId");
        //then
        assertFalse(expectedFalse);
        assertTrue(expectedTrue);
    }

    @Test
    public void shouldValidateIsPhoneNumberUnique() {
        //when
        boolean expectedFalse = userValidationService.isPhoneNumberUnique((Constans.PHONE_NUMBER));
        boolean expectedTrue = userValidationService.isPhoneNumberUnique("fakePhoneNumber");
        //then
        assertFalse(expectedFalse);
        assertTrue(expectedTrue);
    }

}