package ekindergarten.test.repositories;

import ekindergarten.domain.Child;
import ekindergarten.repositories.ChildRepository;
import ekindergarten.testingUtils.Constans;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public class ChildRepositoryTest extends BaseRepositoryTest {

    @Autowired
    ChildRepository childRepository;

    @Test
    public void shouldSaveChild() {
        //given
        childRepository.save(createChild());
        //when
        List<Child> result = childRepository.findAll();
        //then
        Assert.assertEquals(result.size(), 1);
    }

    @Test
    public void shouldFindChildrenByName() {
        //given
        childRepository.save(createChild());
        //when
        List<Child> result = childRepository.findAllByName(Constans.NAME);
        //then
        Assert.assertEquals(result.size(), 1);
    }

    @Test
    public void shouldFindChildrenByNameAndSurname() {
        //given
        childRepository.save(createChild());
        //when
        List<Child> result = childRepository.findAllByNameAndSurname(Constans.NAME, Constans.SURNAME);
        //then
        Assert.assertEquals(result.size(), 1);
    }

    @Test
    public void shouldFindChildByPesel() {
        //given
        childRepository.save(createChild());
        //when
        Child result = childRepository.findByPesel(Constans.PESEL);
        //then
        Assert.assertEquals(Constans.NAME, result.getName());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveChildWithSamePesel() {
        //given
        childRepository.save(createChild());
        //given
        childRepository.save(createChild());
        List<Child> result = childRepository.findAll();
    }

    private Child createChild() {
        return new Child.Builder()
                .withName(Constans.NAME)
                .withSurname(Constans.SURNAME)
                .withPesel(Constans.PESEL)
                .build();
    }

}