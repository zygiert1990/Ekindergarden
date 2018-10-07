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
                .withName(Constans.NAME)
                .withSurname(Constans.SURNAME)
                .withCivilId(Constans.CIVIL_ID)
                .withEmail(Constans.EMAIL)
                .withPhoneNumber(Constans.PHONE_NUMBER)
                .withPassword(Constans.PASSWORD)
                .withRole(new Role(UserRoles.PARENT))
                .build();
    }

    public static UserDto createUserDto() {
        return UserDto.builder()
                .withName(Constans.NAME)
                .withSurname(Constans.SURNAME)
                .withCivilId(Constans.CIVIL_ID)
                .withEmail(Constans.EMAIL)
                .withPhoneNumber(Constans.PHONE_NUMBER)
                .withPassword(Constans.PASSWORD)
                .withMatchingPassword(Constans.PASSWORD)
                .build();
    }

    public static Child createChild() {
        return Child.builder()
                .withName(Constans.NAME)
                .withSurname(Constans.SURNAME)
                .withPesel(Constans.PESEL)
                .build();
    }

    public static TrustedPerson createTrustedPerson() {
        return TrustedPerson.builder()
                .withName(Constans.NAME)
                .withSurname(Constans.SURNAME)
                .withCivilId(Constans.CIVIL_ID)
                .withPhoneNumber(Constans.PHONE_NUMBER)
                .build();
    }
}
