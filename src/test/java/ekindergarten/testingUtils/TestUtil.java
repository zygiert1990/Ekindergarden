package ekindergarten.testingUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import ekindergarten.domain.Address;
import ekindergarten.domain.Child;
import ekindergarten.domain.Role;
import ekindergarten.domain.User;
import ekindergarten.model.UserDto;

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
                .withRole(new Role(Constans.ROLE_USER))
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
                .build();
    }

    public static Child createChild() {
        return Child.builder()
                .withName(Constans.NAME)
                .withSurname(Constans.SURNAME)
                .withPesel(Constans.PESEL)
                .build();
    }

    public static Address createAddress() {
        return Address.builder()
                .withCity(Constans.CITY)
                .withZipCode(Constans.ZIP_CODE)
                .withStreet(Constans.STREET)
                .withHomeNumber(Constans.HOME_NUMBER)
                .withFlatNumber(Constans.FLAT_NUMBER)
                .build();
    }
}
