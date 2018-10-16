package ekindergarten.model;

import ekindergarten.validation.ValidCivilId;
import ekindergarten.validation.ValidName;
import ekindergarten.validation.ValidSurname;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
public class ChildDto {

    @ValidName
    @NotNull
    private String name;

    @ValidSurname
    @NotNull
    private String surname;

    @Pattern(regexp = "\\d{11}")
    @NotNull
    private String pesel;

    @ValidCivilId
    @NotNull
    private String firstParentCivilId;

    @ValidCivilId
    private String secondParentCivilId;

}
