package ekindergarten.test.service;

import ekindergarten.domain.TrustedPerson;
import ekindergarten.repositories.ChildRepository;
import ekindergarten.repositories.TrustedPersonRepository;
import ekindergarten.repositories.UserRepository;
import ekindergarten.service.ChildService;
import ekindergarten.service.TrustedPersonService;
import ekindergarten.test.repositories.BaseJpaTestConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static ekindergarten.testingUtils.Constans.CIVIL_ID;
import static ekindergarten.testingUtils.Constans.PHONE_NUMBER;
import static ekindergarten.testingUtils.TestUtil.createChildDto;
import static ekindergarten.testingUtils.TestUtil.createChildDtoWithTwoCivilIds;
import static ekindergarten.testingUtils.TestUtil.createTrustedPerson;
import static org.junit.Assert.assertEquals;

public class TrustedPersonServiceTest extends BaseJpaTestConfig {

    @Autowired
    private TrustedPersonRepository trustedPersonRepository;
    @Autowired
    private ChildRepository childRepository;
    @Autowired
    private UserRepository userRepository;

    private TrustedPersonService trustedPersonService;
    private ChildService childService;

    @Before
    public void setUp() {
        trustedPersonService = new TrustedPersonService(trustedPersonRepository, childRepository);
        childService = new ChildService(childRepository, userRepository);
    }

    @Test
    public void shouldAddTrustedPerson() {
        // given
        childService.addChild(createChildDto());
        trustedPersonService.addTrustedPerson(createTrustedPerson(), 1L);
        // when
        TrustedPerson result = trustedPersonRepository.findByCivilId(CIVIL_ID);
        // then
        assertEquals(result.getPhoneNumber(), PHONE_NUMBER);
    }

    @Test
    public void shouldAddSameTrustedPersonForTwoChildren() {
        // given
        childService.addChild(createChildDto());
        childService.addChild(createChildDtoWithTwoCivilIds());
        trustedPersonService.addTrustedPerson(createTrustedPerson(), 3L);
        trustedPersonService.addTrustedPerson(createTrustedPerson(), 2L);
        // when
        TrustedPerson result = trustedPersonRepository.findByCivilId(CIVIL_ID);
        // then
        assertEquals(result.getPhoneNumber(), PHONE_NUMBER);
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotAddSameTrustedPersonToOneChild() {
        // given
        childService.addChild(createChildDto());
        trustedPersonService.addTrustedPerson(createTrustedPerson(), 1L);
        trustedPersonService.addTrustedPerson(createTrustedPerson(), 1L);
    }

}
