package ekindergarten.test.service;

import ekindergarten.domain.Child;
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

import java.util.Set;

import static ekindergarten.testingUtils.Constans.CIVIL_ID;
import static ekindergarten.testingUtils.Constans.PHONE_NUMBER;
import static ekindergarten.testingUtils.TestUtil.*;
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
        Child child = childService.addChild(createChildDto());
        trustedPersonService.addTrustedPerson(createTrustedPerson(), child.getId());
        // when
        TrustedPerson result = trustedPersonRepository.findByCivilId(CIVIL_ID);
        // then
        assertEquals(result.getPhoneNumber(), PHONE_NUMBER);
    }

    @Test
    public void shouldAddSameTrustedPersonForTwoChildren() {
        // given
        Child firstChild = childService.addChild(createChildDto());
        Child secondChild = childService.addChild(createChildDtoWithTwoCivilIds());
        trustedPersonService.addTrustedPerson(createTrustedPerson(), firstChild.getId());
        trustedPersonService.addTrustedPerson(createTrustedPerson(), secondChild.getId());
        // when
        TrustedPerson result = trustedPersonRepository.findByCivilId(CIVIL_ID);
        // then
        assertEquals(result.getPhoneNumber(), PHONE_NUMBER);
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotAddSameTrustedPersonToOneChild() {
        // given
        Child child = childService.addChild(createChildDto());
        trustedPersonService.addTrustedPerson(createTrustedPerson(), child.getId());
        trustedPersonService.addTrustedPerson(createTrustedPerson(), child.getId());
    }

    @Test
    public void shouldFindTrustedPeopleForSpecificChild() {
        // given
        Child child = childService.addChild(createChildDto());
        trustedPersonService.addTrustedPerson(createTrustedPerson(), child.getId());
        // when
        Set<TrustedPerson> result = childService.getTrustedPeopleForSpecificChild(child.getId());
        // then
        assertEquals(result.size(), 1);
    }

}
