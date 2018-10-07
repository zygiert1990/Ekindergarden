package ekindergarten.test.repositories;

import ekindergarten.domain.TrustedPerson;
import ekindergarten.repositories.TrustedPersonRepository;
import ekindergarten.testingUtils.Constans;
import ekindergarten.testingUtils.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TrustedPersonRepositoryTest extends BaseJpaTestConfig {

    @Autowired
    private TrustedPersonRepository trustedPersonRepository;

    @Before
    public void setup() {
        trustedPersonRepository.save(TestUtil.createTrustedPerson());
    }

    @Test
    public void shouldFindTrustedPersonByCivilId() {
        //when
        TrustedPerson result = trustedPersonRepository.findByCivilId(Constans.CIVIL_ID);
        //then
        assertNotNull(result);
        assertEquals(result.getName(), Constans.NAME);
    }

    @Test
    public void shouldFindTrustedPersonByPhoneNumber() {
        //when
        TrustedPerson result = trustedPersonRepository.findByPhoneNumber(Constans.PHONE_NUMBER);
        //then
        assertNotNull(result);
        assertEquals(result.getName(), Constans.NAME);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveTrustedPersonWithSameCivilId() {
        //given
        TrustedPerson trustedPerson = TestUtil.createTrustedPerson();
        trustedPerson.setPhoneNumber("777555111");
        trustedPersonRepository.save(trustedPerson);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveTrustedPersonWithSamePhoneNumber() {
        //given
        TrustedPerson trustedPerson = TestUtil.createTrustedPerson();
        trustedPerson.setCivilId("ASD789321");
        trustedPersonRepository.save(trustedPerson);
    }
}