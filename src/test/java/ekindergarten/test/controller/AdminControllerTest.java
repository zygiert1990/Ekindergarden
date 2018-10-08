package ekindergarten.test.controller;

import ekindergarten.domain.Child;
import ekindergarten.domain.User;
import ekindergarten.service.ChildService;
import ekindergarten.service.UserService;
import ekindergarten.testingUtils.BaseTestContext;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import static ekindergarten.testingUtils.Constans.*;
import static ekindergarten.testingUtils.TestUtil.*;
import static ekindergarten.utils.UserRoles.ADMIN;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(authorities = ADMIN)
public class AdminControllerTest extends BaseTestContext {

    private static final String URL_TEMPLATE = "/admin/parent/get";

    @MockBean
    private ChildService childService;

    @MockBean
    private UserService userService;

    private Child child;
    private User user;

    @Before
    public void setup() {
        super.setup();

        child = createChild();
        user = createUser();
    }

    @Test
    public void shouldPassValidationAndAddChild() throws Exception {
        when(childService.addChild(child, EMAIL)).thenReturn(child);
        when(userService.findUserById(1L)).thenReturn(user);

        mockMvc.perform(
                post(URL_TEMPLATE + "/{id}/child/add", 1L).with(csrf())
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(convertObjectToJsonBytes(child)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(NAME)))
                .andExpect(jsonPath("$.surname", is(SURNAME)))
                .andExpect(jsonPath("$.pesel", is(PESEL)));
    }

    @Test
    public void shouldNotPassValidationWhenNameStartsWithSmallLetter() throws Exception {
        child.setName("jan");

        mockMvc.perform(
                post(URL_TEMPLATE + "/{id}/child/add", 1L).with(csrf())
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(convertObjectToJsonBytes(child)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotPassValidationWhenPeselIsToShort() throws Exception {
        child.setPesel("1111111111");

        mockMvc.perform(
                post(URL_TEMPLATE + "/{id}/child/add", 1L).with(csrf())
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(convertObjectToJsonBytes(child)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotPassValidationWhenPeselHasOneLetter() throws Exception {
        child.setPesel("111A1111111");

        mockMvc.perform(
                post(URL_TEMPLATE + "/{id}/child/add", 1L).with(csrf())
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(convertObjectToJsonBytes(child)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldFindUserByCivilId() throws Exception {
        when(userService.findUserByCivilId(CIVIL_ID)).thenReturn(user);

        mockMvc.perform(
                get(URL_TEMPLATE).with(csrf())
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(convertObjectToJsonBytes(user)))
                .andExpect(status().isOk());
    }

}
