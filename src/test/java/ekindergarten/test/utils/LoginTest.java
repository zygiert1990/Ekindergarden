package ekindergarten.test.utils;

import ekindergarten.domain.Role;
import ekindergarten.repositories.RoleRepository;
import ekindergarten.service.UserService;
import ekindergarten.testingUtils.BaseTestContext;
import ekindergarten.testingUtils.Constans;
import ekindergarten.testingUtils.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;

@Transactional
public class LoginTest extends BaseTestContext {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Before
    public void setup() {
        super.setup();

        roleRepository.save(new Role(Constans.ROLE_USER));
        userService.registerNewParent(TestUtil.createUserDto());
    }

    //todo
    //check why project doesn't build with this test class

    @Test
    public void shouldBeAuthenticated() throws Exception {
        mockMvc
                .perform(formLogin("/login/signin").user(Constans.EMAIL).password(Constans.PASSWORD))
                .andExpect(authenticated());
    }

    @Test
    public void shouldNotBeAuthenticatedWhenPasswordIsIncorrect() throws Exception {
        mockMvc
                .perform(formLogin("/login/signin").user(Constans.EMAIL))
                .andExpect(unauthenticated());
    }

    @Test
    public void shouldNotBeAuthenticatedWhenLoginIsIncorrect() throws Exception {
        mockMvc
                .perform(formLogin("/login/signin").password(Constans.PASSWORD))
                .andExpect(unauthenticated());
    }

    @Test
    public void shouldNotBeAuthenticatedWhenLoginAndPasswordAreIncorrect() throws Exception {
        mockMvc
                .perform(formLogin("/login/signin"))
                .andExpect(unauthenticated());
    }
}