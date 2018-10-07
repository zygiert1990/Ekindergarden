package ekindergarten.test.controller;

import ekindergarten.domain.Child;
import ekindergarten.service.ChildService;
import ekindergarten.testingUtils.BaseTestContext;
import ekindergarten.testingUtils.Constans;
import ekindergarten.testingUtils.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(authorities = "ROLE_ADMIN")
public class ChildControllerTest extends BaseTestContext {

    private static final String URL_TEMPLATE = "/child/add";

    @MockBean
    private ChildService childService;

    private Child child;

    @Before
    public void setup() {
        super.setup();

        child = TestUtil.createChild();
    }

    @Test
    public void shouldPassValidationAndAddChild() throws Exception {

        when(childService.addChild(child, "user")).thenReturn(child);

        mockMvc.perform(
                post(URL_TEMPLATE).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(child)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(Constans.NAME)))
                .andExpect(jsonPath("$.surname", is(Constans.SURNAME)))
                .andExpect(jsonPath("$.pesel", is(Constans.PESEL)));
    }

    @Test
    public void shouldNotPassValidationWhenNameStartsWithSmallLetter() throws Exception {

        child.setName("jan");

        mockMvc.perform(
                post(URL_TEMPLATE).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(child)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotPassValidationWhenPeselIsToShort() throws Exception {

        child.setPesel("1111111111");

        mockMvc.perform(
                post(URL_TEMPLATE).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(child)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotPassValidationWhenPeselHasOneLetter() throws Exception {

        child.setPesel("111A1111111");

        mockMvc.perform(
                post(URL_TEMPLATE).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(child)))
                .andExpect(status().isBadRequest());
    }
}
