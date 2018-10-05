package ekindergarten.test.repositories;

import ekindergarten.domain.Child;
import ekindergarten.repositories.ChildRepository;
import ekindergarten.testingUtils.Constans;
import ekindergarten.testingUtils.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ChildRepositoryTest extends BaseJpaTestConfig {

    @Autowired
    ChildRepository childRepository;

    @Before
    public void setup() {
        childRepository.save(TestUtil.createChild());
    }

    @Test
    public void shouldSaveChildAndFindAllChildren() {
        //when
        List<Child> result = childRepository.findAll();
        //then
        assertEquals(result.size(), 1);
    }

    @Test
    public void shouldFindChildrenByName() {
        //when
        List<Child> result = childRepository.findAllByName(Constans.NAME);
        //then
        assertEquals(result.size(), 1);
    }

    @Test
    public void shouldFindChildrenByNameAndSurname() {
        //when
        List<Child> result = childRepository.findAllByNameAndSurname(Constans.NAME, Constans.SURNAME);
        //then
        assertEquals(result.size(), 1);
    }

    @Test
    public void shouldFindChildByPesel() {
        //when
        Child result = childRepository.findByPesel(Constans.PESEL);
        //then
        assertEquals(Constans.NAME, result.getName());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveChildWithSamePesel() {
        //when
        childRepository.save(TestUtil.createChild());
        List<Child> result = childRepository.findAll();
    }
}