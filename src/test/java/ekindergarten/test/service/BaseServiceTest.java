package ekindergarten.test.service;

import ekindergarten.MainTest;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = MainTest.class)
@RunWith(MockitoJUnitRunner.class)
abstract public class BaseServiceTest {
}
