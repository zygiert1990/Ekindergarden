package ekindergarten.test.service;

import ekindergarten.domain.Child;
import ekindergarten.domain.User;
import ekindergarten.model.ChildDto;
import ekindergarten.repositories.ChildRepository;
import ekindergarten.repositories.UserRepository;
import ekindergarten.service.ChildService;
import ekindergarten.test.repositories.BaseJpaTestConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static ekindergarten.testingUtils.Constans.*;
import static ekindergarten.testingUtils.TestUtil.createChildDto;
import static ekindergarten.testingUtils.TestUtil.createChildDtoWithTwoCivilIds;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ChildServiceTest extends BaseJpaTestConfig {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChildRepository childRepository;

    private ChildService childService;

    @Before
    public void setup() {
        childService = new ChildService(childRepository, userRepository);
    }

    @Test
    public void shouldAddChild() {
        // given
        childService.addChild(createChildDto());
        // when
        Child child = childRepository.findByPesel(PESEL);
        User user = userRepository.findByCivilId(CIVIL_ID);
        // then
        assertNotNull(child);
        assertNotNull(user);
    }

    @Test
    public void shouldAddChildToBothParents() {
        // given
        childService.addChild(createChildDtoWithTwoCivilIds());
        // when
        Child child = childRepository.findByPesel(PESEL);
        User firstUser = userRepository.findByCivilId(CIVIL_ID);
        User secondUser = userRepository.findByCivilId(NEW_CIVIL_ID);
        // then
        assertNotNull(child);
        assertNotNull(firstUser);
        assertNotNull(secondUser);
    }

    @Test
    public void shouldAddChildToExistingParent() {
        // given
        childService.addChild(createChildDto());
        childService.addChild(ChildDto.builder()
                .name(NAME).surname(SURNAME).pesel(NEW_PESEL).firstParentCivilId(CIVIL_ID).build());
        // when
        User user = userRepository.findByCivilId(CIVIL_ID);
        // then
        assertEquals(user.getChildren().size(), 2);
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotAddChildWhenPeselIsTheSame() {
        // given
        childService.addChild(createChildDto());
        childService.addChild(createChildDto());
    }
}