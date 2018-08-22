package ekindergarten.controller;

import ekindergarten.Main;
import ekindergarten.domain.Role;
import ekindergarten.domain.User;
import ekindergarten.model.UserDto;
import ekindergarten.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import utils.Constans;
import utils.TestUtil;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = Main.class)
public class RegistrationControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UserService userService;

    @Before
    public void setup() {
        Mockito.reset(userService);

        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void shouldPassValidationAndRegisterNewUser() throws Exception {

        UserDto userDto = createUserDto();
        User user = createUser();

        Mockito.when(userService.registerNewParent(userDto)).thenReturn(user);

        mockMvc.perform(
                post("/register/parent").with(csrf())
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
    public void shouldNotPassValidationIfNameStartWithLowerCase() throws Exception {

        UserDto userDto = createUserDto();
        userDto.setName("jan");

        mockMvc.perform(post("/register/parent").with(csrf())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userDto)))
                .andExpect(status().isBadRequest());

        Mockito.verifyZeroInteractions(userService);
    }

    @Test
    public void shouldNotPassValidationIfSecondPartOfSurnameStartWithLowerCase() throws Exception {

        UserDto userDto = createUserDto();
        userDto.setSurname("Kowalska-rychter");

        mockMvc.perform(post("/register/parent").with(csrf())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userDto)))
                .andExpect(status().isBadRequest());

        Mockito.verifyZeroInteractions(userService);
    }

    @Test
    public void shouldNotPassValidationIfSurnameHasWhiteSpace() throws Exception {

        UserDto userDto = createUserDto();
        userDto.setSurname("Kowalska Nowak");

        mockMvc.perform(post("/register/parent").with(csrf())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userDto)))
                .andExpect(status().isBadRequest());

        Mockito.verifyZeroInteractions(userService);
    }

    @Test
    public void shouldNotPassValidationIfCivilIdIsToLong() throws Exception {

        UserDto userDto = createUserDto();
        userDto.setCivilId("ABC1234567");

        mockMvc.perform(post("/register/parent").with(csrf())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userDto)))
                .andExpect(status().isBadRequest());

        Mockito.verifyZeroInteractions(userService);
    }

    @Test
    public void shouldNotPassValidationIfEmailIsIncorrect() throws Exception {

        UserDto userDto = createUserDto();
        userDto.setEmail("jan@op");

        mockMvc.perform(post("/register/parent").with(csrf())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userDto)))
                .andExpect(status().isBadRequest());

        Mockito.verifyZeroInteractions(userService);
    }

    @Test
    public void shouldNotPassValidationIfPhoneNumberContainsLetter() throws Exception {

        UserDto userDto = createUserDto();
        userDto.setPhoneNumber("1111g1111");

        mockMvc.perform(post("/register/parent").with(csrf())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userDto)))
                .andExpect(status().isBadRequest());

        Mockito.verifyZeroInteractions(userService);
    }

    @Test
    public void shouldNotPassValidationIfMatchingPasswordIsNotTheSameAsPassword() throws Exception {

        UserDto userDto = createUserDto();
        userDto.setMatchingPassword("Lubelski1@1");

        mockMvc.perform(post("/register/parent").with(csrf())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userDto)))
                .andExpect(status().isBadRequest());

        Mockito.verifyZeroInteractions(userService);
    }

    private UserDto createUserDto() {
        return new UserDto.Builder()
                .withName(Constans.NAME)
                .withSurname(Constans.SURNAME)
                .withCivilId(Constans.CIVIL_ID)
                .withEmail(Constans.EMAIL)
                .withPhoneNumber(Constans.PHONE_NUMBER)
                .withPassword(Constans.PASSWORD)
                .withMatchingPassword(Constans.PASSWORD)
                .build();
    }

    private User createUser() {
        return new User.Builder()
                .withName(Constans.NAME)
                .withSurname(Constans.SURNAME)
                .withCivilId(Constans.CIVIL_ID)
                .withEmail(Constans.EMAIL)
                .withPhoneNumber(Constans.PHONE_NUMBER)
                .withPassword(Constans.PASSWORD)
                .withRole(new Role(Constans.ROLE_USER))
                .build();
    }
}