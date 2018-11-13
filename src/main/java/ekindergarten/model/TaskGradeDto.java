package ekindergarten.model;

import lombok.Data;

@Data
public class TaskGradeDto {
    private final String task;
    private final String grade;

    public TaskGradeDto(ProgressTask task, ProgressGrade grade) {
        this.task = task.toString();
        this.grade = grade.toString();
    }

    public TaskGradeDto(String task, String grade) {
        this.task = task;
        this.grade = grade;
    }
}
