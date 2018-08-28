package ekindergarten.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameValidator implements ConstraintValidator<ValidName, String> {

    private static final String NAME_PATTERN = "[A-ZŁ][a-zążźćńłóśę]+";

    @Override
    public void initialize(final ValidName constraintAnnotation) {
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        return validateName(name);
    }

    private boolean validateName(final String name) {
        Pattern pattern = Pattern.compile(NAME_PATTERN);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }
}
