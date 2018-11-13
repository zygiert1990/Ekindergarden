package ekindergarten.testingUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import ekindergarten.domain.Child;
import ekindergarten.domain.Role;
import ekindergarten.domain.TrustedPerson;
import ekindergarten.domain.User;
import ekindergarten.domain.childProgress.Progress;
import ekindergarten.model.*;
import ekindergarten.utils.UserAuthorities;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static ekindergarten.testingUtils.Constans.*;

public class TestUtil {

    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

    public static User createUser() {
        return User.builder()
                .name(NAME)
                .surname(SURNAME)
                .civilId(Constans.CIVIL_ID)
                .email(Constans.EMAIL)
                .phoneNumber(Constans.PHONE_NUMBER)
                .password(Constans.PASSWORD)
                .role(new Role(UserAuthorities.PARENT))
                .build();
    }

    public static UserDto createUserDto() {
        return UserDto.builder()
                .name(NAME)
                .surname(SURNAME)
                .civilId(CIVIL_ID)
                .email(EMAIL)
                .phoneNumber(PHONE_NUMBER)
                .password(PASSWORD)
                .matchingPassword(PASSWORD)
                .build();
    }

    public static UserDto createTeacherDto() {
        return UserDto.builder()
                .name(NAME)
                .surname(SURNAME)
                .civilId(NEW_CIVIL_ID)
                .email(EMAIL)
                .phoneNumber(PHONE_NUMBER)
                .password(PASSWORD)
                .matchingPassword(PASSWORD)
                .build();
    }

    public static Child createChild() {
        return Child.builder()
                .name(NAME)
                .surname(SURNAME)
                .pesel(PESEL)
                .build();
    }

    public static RemarkDto createRemarkDto() {
        return RemarkDto.builder()
                .isPositive(true)
                .comment("some comment")
                .build();
    }

    public static ChildDto createChildDto() {
        return ChildDto.builder()
                .name(NAME)
                .surname(SURNAME)
                .pesel(PESEL)
                .firstParentCivilId(CIVIL_ID)
                .build();
    }

    public static ChildDto createChildDtoWithTwoCivilIds() {
        return ChildDto.builder()
                .name(NAME)
                .surname(SURNAME)
                .pesel(NEW_PESEL)
                .firstParentCivilId(CIVIL_ID)
                .secondParentCivilId(NEW_CIVIL_ID)
                .build();
    }

    public static TrustedPerson createTrustedPerson() {
        return TrustedPerson.builder()
                .name(NAME)
                .surname(SURNAME)
                .civilId(CIVIL_ID)
                .phoneNumber(PHONE_NUMBER)
                .build();
    }

    public static List<ekindergarten.domain.childProgress.ProgressCategory> createProgressCategories() {
        return Arrays.asList(
                ekindergarten.domain.childProgress.ProgressCategory.builder().progressCategory(ProgressCategory.PHYSICAL.toString()).build(),
                ekindergarten.domain.childProgress.ProgressCategory.builder().progressCategory(ProgressCategory.MENTAL.toString()).build(),
                ekindergarten.domain.childProgress.ProgressCategory.builder().progressCategory(ProgressCategory.SOCIAL_AND_MORAL.toString()).build()
        );
    }

    public static List<ChildProgressDto> createChildProgressDtos() {
        return Arrays.asList(
                new ChildProgressDto(ProgressCategory.PHYSICAL, Arrays.asList(
                        new TaskGradeDto(ProgressTask.PHYSICAL_1, ProgressGrade.SOMETIMES),
                        new TaskGradeDto(ProgressTask.PHYSICAL_2, ProgressGrade.NO),
                        new TaskGradeDto(ProgressTask.PHYSICAL_3, ProgressGrade.YES),
                        new TaskGradeDto(ProgressTask.PHYSICAL_5, ProgressGrade.YES),
                        new TaskGradeDto(ProgressTask.PHYSICAL_4, ProgressGrade.SOMETIMES)
                )),
                new ChildProgressDto(ProgressCategory.MENTAL, Arrays.asList(
                        new TaskGradeDto(ProgressTask.MENTAL_1, ProgressGrade.SOMETIMES),
                        new TaskGradeDto(ProgressTask.MENTAL_2, ProgressGrade.YES),
                        new TaskGradeDto(ProgressTask.MENTAL_3, ProgressGrade.NO),
                        new TaskGradeDto(ProgressTask.MENTAL_5, ProgressGrade.NO),
                        new TaskGradeDto(ProgressTask.MENTAL_4, ProgressGrade.SOMETIMES)
                )),
                new ChildProgressDto(ProgressCategory.SOCIAL_AND_MORAL, Arrays.asList(
                        new TaskGradeDto(ProgressTask.MORAL_1, ProgressGrade.SOMETIMES),
                        new TaskGradeDto(ProgressTask.MORAL_2, ProgressGrade.YES),
                        new TaskGradeDto(ProgressTask.MORAL_5, ProgressGrade.YES),
                        new TaskGradeDto(ProgressTask.MORAL_3, ProgressGrade.NO),
                        new TaskGradeDto(ProgressTask.MORAL_4, ProgressGrade.NO))));
    }

    public static List<ekindergarten.domain.childProgress.ProgressGrade> createProgressGrades() {
        return Arrays.asList(
                ekindergarten.domain.childProgress.ProgressGrade.builder().progressGrade(ProgressGrade.SOMETIMES.toString()).build(),
                ekindergarten.domain.childProgress.ProgressGrade.builder().progressGrade(ProgressGrade.YES.toString()).build(),
                ekindergarten.domain.childProgress.ProgressGrade.builder().progressGrade(ProgressGrade.NO.toString()).build()
        );
    }

    public static List<ekindergarten.domain.childProgress.ProgressTask> createProgressTasks() {
        return Arrays.asList(
                ekindergarten.domain.childProgress.ProgressTask.builder().progressTask(ProgressTask.MENTAL_1.toString()).build(),
                ekindergarten.domain.childProgress.ProgressTask.builder().progressTask(ProgressTask.MENTAL_2.toString()).build(),
                ekindergarten.domain.childProgress.ProgressTask.builder().progressTask(ProgressTask.MENTAL_3.toString()).build(),
                ekindergarten.domain.childProgress.ProgressTask.builder().progressTask(ProgressTask.MENTAL_4.toString()).build(),
                ekindergarten.domain.childProgress.ProgressTask.builder().progressTask(ProgressTask.MENTAL_5.toString()).build(),
                ekindergarten.domain.childProgress.ProgressTask.builder().progressTask(ProgressTask.MORAL_1.toString()).build(),
                ekindergarten.domain.childProgress.ProgressTask.builder().progressTask(ProgressTask.MORAL_2.toString()).build(),
                ekindergarten.domain.childProgress.ProgressTask.builder().progressTask(ProgressTask.MORAL_3.toString()).build(),
                ekindergarten.domain.childProgress.ProgressTask.builder().progressTask(ProgressTask.MORAL_4.toString()).build(),
                ekindergarten.domain.childProgress.ProgressTask.builder().progressTask(ProgressTask.MORAL_5.toString()).build(),
                ekindergarten.domain.childProgress.ProgressTask.builder().progressTask(ProgressTask.PHYSICAL_1.toString()).build(),
                ekindergarten.domain.childProgress.ProgressTask.builder().progressTask(ProgressTask.PHYSICAL_2.toString()).build(),
                ekindergarten.domain.childProgress.ProgressTask.builder().progressTask(ProgressTask.PHYSICAL_3.toString()).build(),
                ekindergarten.domain.childProgress.ProgressTask.builder().progressTask(ProgressTask.PHYSICAL_4.toString()).build(),
                ekindergarten.domain.childProgress.ProgressTask.builder().progressTask(ProgressTask.PHYSICAL_5.toString()).build()
        );
    }

}
