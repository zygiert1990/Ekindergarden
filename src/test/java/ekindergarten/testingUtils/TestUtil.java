package ekindergarten.testingUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import ekindergarten.domain.Child;
import ekindergarten.domain.Role;
import ekindergarten.domain.TrustedPerson;
import ekindergarten.domain.User;
import ekindergarten.model.AbsenceRecordDto;
import ekindergarten.model.ChildDto;
import ekindergarten.model.RemarkDto;
import ekindergarten.model.UserDto;
import ekindergarten.utils.UserAuthorities;

import java.io.IOException;
import java.time.LocalDate;

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

    public static AbsenceRecordDto createAbsenceDtoOneDayLong() {
        return AbsenceRecordDto.builder()
                .startAbsence(LocalDate.of(2018, 11, 2))
                .endAbsence(LocalDate.of(2018, 11, 2))
                .reason("some reason")
                .build();
    }

    public static AbsenceRecordDto createAbsenceDtoOneDayLongAtWeekend() {
        return AbsenceRecordDto.builder()
                .startAbsence(LocalDate.of(2018, 11, 3))
                .endAbsence(LocalDate.of(2018, 11, 3))
                .reason("some reason")
                .build();
    }

    public static AbsenceRecordDto createAbsenceDtoTwoDaysLongAtWeekend() {
        return AbsenceRecordDto.builder()
                .startAbsence(LocalDate.of(2018, 11, 3))
                .endAbsence(LocalDate.of(2018, 11, 4))
                .reason("some reason")
                .build();
    }

    public static AbsenceRecordDto createAbsenceDtoThreeDaysLongWithWeekend() {
        return AbsenceRecordDto.builder()
                .startAbsence(LocalDate.of(2018, 11, 3))
                .endAbsence(LocalDate.of(2018, 11, 5))
                .reason("some reason")
                .build();
    }

    public static AbsenceRecordDto createAbsenceDtoThreeDaysLong() {
        return AbsenceRecordDto.builder()
                .startAbsence(LocalDate.of(2018, 11, 5))
                .endAbsence(LocalDate.of(2018, 11, 7))
                .reason("some reason")
                .build();
    }
}
