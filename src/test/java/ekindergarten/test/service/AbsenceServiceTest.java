package ekindergarten.test.service;

import ekindergarten.domain.Absence;
import ekindergarten.domain.Child;
import ekindergarten.repositories.AbsenceRepository;
import ekindergarten.repositories.ChildRepository;
import ekindergarten.repositories.UserRepository;
import ekindergarten.service.AbsenceService;
import ekindergarten.service.ChildService;
import ekindergarten.test.repositories.BaseJpaTestConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.List;

import static ekindergarten.testingUtils.TestUtil.*;
import static org.junit.Assert.assertEquals;

public class AbsenceServiceTest extends BaseJpaTestConfig {

    @Autowired
    private ChildRepository childRepository;
    @Autowired
    private AbsenceRepository absenceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager entityManager;

    private ChildService childService;
    private AbsenceService absenceService;

    @Before
    public void setup() {
        childService = new ChildService(childRepository, userRepository);
        absenceService = new AbsenceService(childRepository, absenceRepository);
    }

    @Test
    public void shouldAddOneDayAbsence() {
        // given
        Child child = childService.addChild(createChildDto());
        absenceService.addAbsence(createAbsenceDtoOneDayLong(), child.getId());
        entityManager.clear();
        // when
        List<Absence> result = absenceRepository.findAll();
        BigDecimal balance = childRepository.findById(child.getId()).getPayment().getBalance();
        // then
        assertEquals(result.size(), 1);
        assertEquals(balance.intValue(), 290);
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotAddOneDayAbsenceWhenItIsOnWeekend() {
        // given
        Child child = childService.addChild(createChildDto());
        absenceService.addAbsence(createAbsenceDtoOneDayLongAtWeekend(), child.getId());
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotAddTwoDaysAbsenceWhenItIsOnWeekend() {
        // given
        Child child = childService.addChild(createChildDto());
        absenceService.addAbsence(createAbsenceDtoTwoDaysLongAtWeekend(), child.getId());
    }

    @Test
    public void shouldAddOneDayAbsenceFromThreeDaysAbsenceWhenTwoDaysAreAtWeekend() {
        // given
        Child child = childService.addChild(createChildDto());
        absenceService.addAbsence(createAbsenceDtoThreeDaysLongWithWeekend(), child.getId());
        // when
        List<Absence> result = absenceRepository.findAll();
        // then
        assertEquals(result.size(), 1);
    }

    @Test
    public void shouldAddThreeDaysAbsence() {
        // given
        Child child = childService.addChild(createChildDto());
        absenceService.addAbsence(createAbsenceDtoThreeDaysLong(), child.getId());
        entityManager.clear();
        // when
        BigDecimal balance = childRepository.findById(child.getId()).getPayment().getBalance();
        List<Absence> result = absenceRepository.findAll();
        // then
        assertEquals(result.size(), 3);
        assertEquals(balance.intValue(), 270);
    }

    @Test
    public void shouldUpdateTwoAbsences() {
        // given
        Child child = childService.addChild(createChildDto());
        List<Absence> absences = absenceService.addAbsence(createAbsenceDtoThreeDaysLong(), child.getId());
        long[] ids = {absences.get(0).getId(), absences.get(1).getId()};
        absenceService.updateAbsence(ids, "nowy powód");
        // when
        List<Absence> result = absenceRepository.findAll();
        // then
        assertEquals(result.get(0).getReason(), "nowy powód");
    }

    @Test
    public void shouldDeleteAbsence() {
        // given
        Child child = childService.addChild(createChildDto());
        List<Absence> absences = absenceService.addAbsence(createAbsenceDtoThreeDaysLong(), child.getId());
        long[] ids = {absences.get(0).getId()};
        absenceService.deleteAbsence(ids);
        entityManager.clear();
        // when
        BigDecimal balance = childRepository.findById(child.getId()).getPayment().getBalance();
        List<Absence> result = absenceRepository.findAll();
        // then
        assertEquals(balance.intValue(), 280);
        assertEquals(result.size(), 2);
    }

    @Test
    public void shouldGetAllAbsences() {
        // given
        Child child = childService.addChild(createChildDto());
        absenceService.addAbsence(createAbsenceDtoThreeDaysLong(), child.getId());
        entityManager.clear();
        // when
        List<Absence> result = absenceService.getChildAbsences(child.getId());
        // then
        assertEquals(result.size(), 3);
    }

}
