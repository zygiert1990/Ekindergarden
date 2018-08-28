package ekindergarten.testingUtils;

import ekindergarten.MainTest;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = MainTest.class)
public class BaseTestContext {

    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;

}
