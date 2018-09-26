package ekindergarten.model;

import ekindergarten.validation.*;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
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
    private String password;
    @NotNull
    private String matchingPassword;

    public static Builder builder() {
        return new UserDto.Builder();
    }

    public static class Builder {
        private UserDto instance;

        public Builder() {
            instance = new UserDto();
        }

        public Builder withName(String name) {
            instance.name = name;
            return this;
        }

        public Builder withSurname(String surname) {
            instance.surname = surname;
            return this;
        }

        public Builder withCivilId(String civilId) {
            instance.civilId = civilId;
            return this;
        }

        public Builder withEmail(String email) {
            instance.email = email;
            return this;
        }

        public Builder withPhoneNumber(String phoneNumber) {
            instance.phoneNumber = phoneNumber;
            return this;
        }

        public Builder withPassword(String password) {
            instance.password = password;
            return this;
        }

        public Builder withMatchingPassword(String matchingPassword) {
            instance.matchingPassword = matchingPassword;
            return this;
        }

        public UserDto build() {
            return instance;
        }
    }
}
