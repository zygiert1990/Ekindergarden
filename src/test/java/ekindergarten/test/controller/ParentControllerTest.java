package ekindergarten.test.controller;

import ekindergarten.domain.Child;
import ekindergarten.service.ChildService;
import ekindergarten.testingUtils.BaseTestContext;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.HashSet;
import java.util.Set;

import static ekindergarten.testingUtils.Constans.*;
import static ekindergarten.testingUtils.TestUtil.createChild;
import static ekindergarten.utils.UserAuthorities.PARENT;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(authorities = PARENT)
public class ParentControllerTest extends BaseTestContext {

    private static final String URL_TEMPLATE = "/parent/";

    @MockBean
    private ChildService childService;

    private Child child;
    private Set<Child> children;

    @Before
    public void setup() {
        super.setup();

        child = createChild();
        children = new HashSet<>();
        children.add(child);
    }

    @Test
    public void shouldGetAllParentChildren() throws Exception {
        when(childService.findAllParentChildren("user")).thenReturn(children);

        mockMvc.perform(
                get(URL_TEMPLATE + "getAll").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name", is(NAME)))
                .andExpect(jsonPath("$.[0].surname", is(SURNAME)))
                .andExpect(jsonPath("$.[0].pesel", is(PESEL)));
    }
}
