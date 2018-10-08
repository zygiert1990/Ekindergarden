package ekindergarten.testingUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import ekindergarten.domain.Child;
import ekindergarten.domain.Role;
import ekindergarten.domain.TrustedPerson;
import ekindergarten.domain.User;
import ekindergarten.model.UserDto;
import ekindergarten.utils.UserRoles;

import java.io.IOException;

public class TestUtil {

    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

    public static User createUser() {
        return User.builder()
                .name(Constans.NAME)
                .surname(Constans.SURNAME)
                .civilId(Constans.CIVIL_ID)
                .email(Constans.EMAIL)
                .phoneNumber(Constans.PHONE_NUMBER)
                .password(Constans.PASSWORD)
                .role(new Role(UserRoles.PARENT))
                .build();
    }

    public static UserDto createUserDto() {
        return UserDto.builder()
                .name(Constans.NAME)
                .surname(Constans.SURNAME)
                .civilId(Constans.CIVIL_ID)
                .email(Constans.EMAIL)
                .phoneNumber(Constans.PHONE_NUMBER)
                .password(Constans.PASSWORD)
                .matchingPassword(Constans.PASSWORD)
                .build();
    }

    public static Child createChild() {
        return Child.builder()
                .name(Constans.NAME)
                .surname(Constans.SURNAME)
                .pesel(Constans.PESEL)
                .build();
    }

    public static Child createChildWithPesel(String pesel) {
        return Child.builder()
                .name(Constans.NAME)
                .surname(Constans.SURNAME)
                .pesel(pesel)
                .build();
    }

    public static TrustedPerson createTrustedPerson() {
        return TrustedPerson.builder()
                .name(Constans.NAME)
                .surname(Constans.SURNAME)
                .civilId(Constans.CIVIL_ID)
                .phoneNumber(Constans.PHONE_NUMBER)
                .build();
    }

    public static UserDto createUserDtoWithParameters(String email, String civilId, String phoneNumber) {
        return UserDto.builder()
                .name(Constans.NAME)
                .surname(Constans.SURNAME)
                .email(email)
                .civilId(civilId)
                .phoneNumber(phoneNumber)
                .password(Constans.PASSWORD)
                .build();
    }
}
