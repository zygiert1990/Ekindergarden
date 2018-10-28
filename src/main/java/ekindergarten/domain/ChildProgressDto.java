package ekindergarten.domain;

import lombok.Data;

import java.util.List;

@Data
public class ChildProgressDto {
    private final String progressCategory;
    private final List<TaskGradeDto> taskGradeDtos;


    public ChildProgressDto(ProgressCategory progressCategory, List<TaskGradeDto> taskGradeDtos) {
        this.progressCategory = progressCategory.toString();
        this.taskGradeDtos = taskGradeDtos;
    }
}
