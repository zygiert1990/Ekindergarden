package ekindergarten.repositories;

import ekindergarten.domain.Child;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import utils.Constans;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest
public class ChildRepositoryTest {

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