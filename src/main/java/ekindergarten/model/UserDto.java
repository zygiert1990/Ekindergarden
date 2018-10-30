package ekindergarten.model;

import ekindergarten.validation.*;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@PasswordMatches
public class UserDto {

    @NotNull
    @Size(max = 45)
    @ValidName
    private String name;

    @NotNull
    @Size(max = 45)
    @ValidSurname
    private String surname;

    @NotNull
    @ValidCivilId
    private String civilId;

    @NotNull
    @Email
    @Size(max = 45)
    private String email;

    @NotNull
    @Pattern(regexp = "\\d{9}", message = "Numer telefonu musi zawierać dziewięć cyfr")
    private String phoneNumber;

    @NotNull
    @Size(min = 8, max = 30, message = "Minimum password size is 8 signs, maximum 30")
    private String password;

    @NotNull
    private String matchingPassword;

}