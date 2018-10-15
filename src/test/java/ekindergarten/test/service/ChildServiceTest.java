package ekindergarten.test.service;

import ekindergarten.domain.Child;
import ekindergarten.repositories.ChildRepository;
import ekindergarten.service.ChildService;
import ekindergarten.test.repositories.BaseJpaTestConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static ekindergarten.testingUtils.Constans.NEW_PESEL;
import static ekindergarten.testingUtils.TestUtil.createChild;
import static ekindergarten.testingUtils.TestUtil.createChildWithPesel;
import static org.junit.Assert.assertEquals;

public class ChildServiceTest extends BaseJpaTestConfig {

    @Autowired
    private ChildRepository childRepository;

    private ChildService childService;

    @Before
    public void setup() {
        childService = new ChildService(childRepository);
        childService.addChild(createChild());
    }

    @Test
    public void shouldAddChild() {
        // when
        List<Child> result = childRepository.findAll();
        // then
        assertEquals(result.size(), 1);
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotAddChildWithTheSamePesel() {
        // given
        childService.addChild(createChild());
    }

    @Test
    public void shouldAddSecondChild() {
        // given
        childService.addChild(createChildWithPesel(NEW_PESEL));
        // when
        List<Child> result = childRepository.findAll();
        // then
        assertEquals(result.size(), 2);
    }
}