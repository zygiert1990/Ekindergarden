package ekindergarten.model;

import ekindergarten.validation.*;
import lombok.Builder;
import lombok.Data;

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
    @Pattern(regexp = "[A-Z]{3}\\d{6}")
    private String civilId;

    @NotNull
    @ValidEmail
    @Size(max = 45)
    private String email;

    @NotNull
    @Pattern(regexp = "\\d{9}")
    private String phoneNumber;

    @NotNull
    @ValidPassword
    @Size(min = 8, max = 30)
    private String password;

    @NotNull
    @Size(min = 8, max = 30)
    private String matchingPassword;

}