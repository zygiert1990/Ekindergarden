package ekindergarten.test.service;

import ekindergarten.domain.Child;
import ekindergarten.domain.childProgress.Progress;
import ekindergarten.model.ChildProgressDto;
import ekindergarten.repositories.ChildRepository;
import ekindergarten.repositories.UserRepository;
import ekindergarten.repositories.childProgress.ProgressCategoryRepository;
import ekindergarten.repositories.childProgress.ProgressGradeRepository;
import ekindergarten.repositories.childProgress.ProgressRepository;
import ekindergarten.repositories.childProgress.ProgressTaskRepository;
import ekindergarten.service.ChildService;
import ekindergarten.service.ProgressService;
import ekindergarten.test.repositories.BaseJpaTestConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static ekindergarten.testingUtils.TestUtil.*;
import static org.junit.Assert.assertEquals;

public class ProgressServiceTest extends BaseJpaTestConfig {

    @Autowired
    private ProgressRepository progressRepository;
    @Autowired
    private ProgressCategoryRepository progressCategoryRepository;
    @Autowired
    private ProgressTaskRepository progressTaskRepository;
    @Autowired
    private ProgressGradeRepository progressGradeRepository;
    @Autowired
    private ChildRepository childRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    private ProgressService progressService;
    private ChildService childService;

    @Before
    public void setup() {
        progressService = new ProgressService(progressRepository, progressCategoryRepository, progressTaskRepository,
                progressGradeRepository, childRepository);
        childService = new ChildService(childRepository, userRepository);
    }

    @Test
    public void shouldSaveChildProgress() {
        // given
        Child child = childService.addChild(createChildDto());
        progressCategoryRepository.saveAll(createProgressCategories());
        progressGradeRepository.saveAll(createProgressGrades());
        progressTaskRepository.saveAll(createProgressTasks());
        // when
        progressService.addChildProgressRecords(createChildProgressDtos(), child.getId());
        testEntityManager.clear();
        List<Progress> result = progressRepository.findAll();
        List<ChildProgressDto> dtoResult = progressService.getEvaluationForSpecificChild(child.getId());
        // then
        assertEquals(result.size(), 15);
        assertEquals(dtoResult.size(), 3);
    }

}
