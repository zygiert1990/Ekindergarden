package ekindergarten.testingUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import ekindergarten.domain.Child;
import ekindergarten.domain.Role;
import ekindergarten.domain.TrustedPerson;
import ekindergarten.domain.User;
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

    public static UserDto createUserDtoWithParameters(String email, String civilId, String phoneNumber) {
        return UserDto.builder()
                .name(NAME)
                .surname(SURNAME)
                .email(email)
                .civilId(civilId)
                .phoneNumber(phoneNumber)
                .password(Constans.PASSWORD)
                .build();
    }
}
