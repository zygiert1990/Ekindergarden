package ekindergarten.test.controller;

import ekindergarten.domain.User;
import ekindergarten.model.UserDto;
import ekindergarten.service.UserService;
import ekindergarten.testingUtils.BaseTestContext;
import ekindergarten.testingUtils.Constans;
import ekindergarten.testingUtils.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegisterControllerTest extends BaseTestContext {

    private static final String URL_TEMPLATE = "/register/parent";

    @MockBean
    private UserService userService;

    @Before
    public void setup() {
        Mockito.reset(userService);

        super.setup();
    }

    @Test
    public void shouldPassValidationAndRegisterNewUser() throws Exception {

        UserDto userDto = TestUtil.createUserDto();
        User user = TestUtil.createUser();

        Mockito.when(userService.registerNewParent(userDto)).thenReturn(user);

        mockMvc.perform(
                post(URL_TEMPLATE).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(Constans.NAME)))
                .andExpect(jsonPath("$.surname", is(Constans.SURNAME)))
                .andExpect(jsonPath("$.civilId", is(Constans.CIVIL_ID)))
                .andExpect(jsonPath("$.email", is(Constans.EMAIL)))
                .andExpect(jsonPath("$.phoneNumber", is(Constans.PHONE_NUMBER)));

        ArgumentCaptor<UserDto> userDtoCaptor = ArgumentCaptor.forClass(UserDto.class);
        Mockito.verify(userService, times(1)).registerNewParent(userDtoCaptor.capture());
        Mockito.verifyNoMoreInteractions(userService);
    }

    @Test
    public void shouldPassValidationIfSurnameHasSpace() throws Exception {

        UserDto userDto = TestUtil.createUserDto();
        userDto.setSurname("Kowalska Nowak");

        mockMvc.perform(post(URL_TEMPLATE).with(csrf())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotPassValidationIfNameStartWithLowerCase() throws Exception {

        UserDto userDto = TestUtil.createUserDto();
        userDto.setName("jan");

        mockMvc.perform(post(URL_TEMPLATE).with(csrf())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userDto)))
                .andExpect(status().isBadRequest());

        Mockito.verifyZeroInteractions(userService);
    }

    @Test
    public void shouldNotPassValidationIfSecondPartOfSurnameStartWithLowerCase() throws Exception {

        UserDto userDto = TestUtil.createUserDto();
        userDto.setSurname("Kowalska-rychter");

        mockMvc.perform(post(URL_TEMPLATE).with(csrf())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userDto)))
                .andExpect(status().isBadRequest());

        Mockito.verifyZeroInteractions(userService);
    }

    @Test
    public void shouldNotPassValidationIfCivilIdIsToLong() throws Exception {

        UserDto userDto = TestUtil.createUserDto();
        userDto.setCivilId("ABC1234567");

        mockMvc.perform(post(URL_TEMPLATE).with(csrf())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userDto)))
                .andExpect(status().isBadRequest());

        Mockito.verifyZeroInteractions(userService);
    }

    @Test
    public void shouldNotPassValidationIfEmailIsIncorrect() throws Exception {

        UserDto userDto = TestUtil.createUserDto();
        userDto.setEmail("jan@op");

        mockMvc.perform(post(URL_TEMPLATE).with(csrf())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userDto)))
                .andExpect(status().isBadRequest());

        Mockito.verifyZeroInteractions(userService);
    }

    @Test
    public void shouldNotPassValidationIfPhoneNumberContainsLetter() throws Exception {

        UserDto userDto = TestUtil.createUserDto();
        userDto.setPhoneNumber("1111g1111");

        mockMvc.perform(post(URL_TEMPLATE).with(csrf())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userDto)))
                .andExpect(status().isBadRequest());

        Mockito.verifyZeroInteractions(userService);
    }

    @Test
    public void shouldNotPassValidationIfMatchingPasswordIsNotTheSameAsPassword() throws Exception {

        UserDto userDto = TestUtil.createUserDto();
        userDto.setMatchingPassword("Lubelski1@1");

        mockMvc.perform(post(URL_TEMPLATE).with(csrf())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userDto)))
                .andExpect(status().isBadRequest());

        Mockito.verifyZeroInteractions(userService);
    }
}