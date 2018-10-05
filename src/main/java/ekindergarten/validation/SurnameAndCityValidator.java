package ekindergarten.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SurnameAndCityValidator implements ConstraintValidator<ValidSurnameAndCity, String> {

    private static final String SURNAME_AND_CITY_PATTERN = "[A-ZŁŻŹŚ][a-zążźćńłóśę]+([- ][A-ZŁŻŹŚ][a-zążźćńłóśę]+)*";

    @Override
    public void initialize(ValidSurnameAndCity constraintAnnotation) {
    }

    @Override
    public boolean isValid(String surname, ConstraintValidatorContext context) {
        return validateSurname(surname);
    }

    private boolean validateSurname(final String surname) {
        Pattern pattern = Pattern.compile(SURNAME_AND_CITY_PATTERN);
        Matcher matcher = pattern.matcher(surname);
        return matcher.matches();
    }
}
