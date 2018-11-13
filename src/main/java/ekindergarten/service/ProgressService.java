package ekindergarten.service;

import ekindergarten.domain.Child;
import ekindergarten.domain.childProgress.Progress;
import ekindergarten.domain.childProgress.ProgressCategory;
import ekindergarten.domain.childProgress.ProgressGrade;
import ekindergarten.domain.childProgress.ProgressTask;
import ekindergarten.model.ChildProgressDto;
import ekindergarten.model.TaskGradeDto;
import ekindergarten.repositories.ChildRepository;
import ekindergarten.repositories.childProgress.ProgressCategoryRepository;
import ekindergarten.repositories.childProgress.ProgressGradeRepository;
import ekindergarten.repositories.childProgress.ProgressRepository;
import ekindergarten.repositories.childProgress.ProgressTaskRepository;
import ekindergarten.utils.CustomCollector;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProgressService {

    private final ProgressRepository progressRepository;
    private final ProgressCategoryRepository progressCategoryRepository;
    private final ProgressTaskRepository progressTaskRepository;
    private final ProgressGradeRepository progressGradeRepository;
    private final ChildRepository childRepository;

    public ProgressService(
            ProgressRepository progressRepository,
            ProgressCategoryRepository progressCategoryRepository,
            ProgressTaskRepository progressTaskRepository,
            ProgressGradeRepository progressGradeRepository,
            ChildRepository childRepository) {
        this.progressRepository = progressRepository;
        this.progressCategoryRepository = progressCategoryRepository;
        this.progressTaskRepository = progressTaskRepository;
        this.progressGradeRepository = progressGradeRepository;
        this.childRepository = childRepository;
    }

    public List<Progress> addChildProgressRecords(List<ChildProgressDto> childProgressDtos, long childId) {
        Child child = childRepository.findById(childId);
        List<ProgressGrade> progressGrades = progressGradeRepository.findAll();
        List<ProgressTask> progressTasks = progressTaskRepository.findAll();
        List<ProgressCategory> progressCategories = progressCategoryRepository.findAll();

        return progressRepository.saveAll(childProgressDtos.stream()
                .map(childProgressDto -> childProgressDto.getTaskGradeDtos()
                        .stream()
                        .map(taskGradeDto ->
                                Progress.builder()
                                        .child(child)
                                        .progressCategory(
                                                progressCategories.stream()
                                                        .filter(category -> category.getProgressCategory().equalsIgnoreCase(childProgressDto.getProgressCategory()))
                                                        .collect(CustomCollector.toSingleton())
                                        )
                                        .progressGrade(
                                                progressGrades.stream()
                                                        .filter(grade -> grade.getProgressGrade().equalsIgnoreCase(taskGradeDto.getGrade()))
                                                        .collect(CustomCollector.toSingleton())
                                        )
                                        .progressTask(
                                                progressTasks.stream()
                                                        .filter(task -> task.getProgressTask().equalsIgnoreCase(taskGradeDto.getTask()))
                                                        .collect(CustomCollector.toSingleton())
                                        )
                                        .build())
                        .collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList()));
    }

    public List<ChildProgressDto> getEvaluationForSpecificChild(long id) {
        List<Progress> progressList = progressRepository.findAllByChild_Id(id);

        return Stream.of(
                ekindergarten.model.ProgressCategory.MENTAL,
                ekindergarten.model.ProgressCategory.PHYSICAL,
                ekindergarten.model.ProgressCategory.SOCIAL_AND_MORAL)
                .map(
                        progressCategory ->
                                new ChildProgressDto(
                                        progressCategory,
                                        getTaskGradeDtos(progressCategory, progressList)
                                )
                )
                .collect(Collectors.toList());
    }

    private List<TaskGradeDto> getTaskGradeDtos(ekindergarten.model.ProgressCategory progressCategory, List<Progress> progressList) {
        return progressList
                .stream()
                .filter(progress -> progress.getProgressCategory().getProgressCategory().equals(progressCategory.toString()))
                .map(progress -> new TaskGradeDto(
                        progress.getProgressTask().getProgressTask(),
                        progress.getProgressGrade().getProgressGrade()))
                .collect(Collectors.toList());
    }

}
