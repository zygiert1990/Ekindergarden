package ekindergarten.test.utils;

import ekindergarten.domain.User;
import ekindergarten.repositories.UserRepository;
import ekindergarten.test.service.BaseServiceTest;
import ekindergarten.utils.UserValidationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static ekindergarten.testingUtils.Constans.*;
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
        User user = User.builder()
                .email(EMAIL)
                .phoneNumber(PHONE_NUMBER)
                .build();
        when(userRepository.findByEmail(EMAIL)).thenReturn(user);
        when(userRepository.findByPhoneNumber(PHONE_NUMBER)).thenReturn(user);
    }

    @Test
    public void shouldValidateIsEmailUnique() {
        //when
        boolean expectedFalse = userValidationService.isEmailUnique(EMAIL);
        boolean expectedTrue = userValidationService.isEmailUnique(NEW_EMAIL);
        //then
        assertFalse(expectedFalse);
        assertTrue(expectedTrue);
    }

    @Test
    public void shouldValidateIsPhoneNumberUnique() {
        //when
        boolean expectedFalse = userValidationService.isPhoneNumberUnique((PHONE_NUMBER));
        boolean expectedTrue = userValidationService.isPhoneNumberUnique(NEW_PHONE_NUMBER);
        //then
        assertFalse(expectedFalse);
        assertTrue(expectedTrue);
    }

}