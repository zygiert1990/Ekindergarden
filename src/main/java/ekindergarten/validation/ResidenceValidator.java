package ekindergarten.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResidenceValidator implements ConstraintValidator<ValidResidence, String> {

    private static final String RESIDENCE_PATTERN = "[0-9]+([A-Za-z]+)*";

    @Override
    public void initialize(ValidResidence constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return validateResidenceNumber(value);
    }

    private boolean validateResidenceNumber(final String value) {
        if (value != null) {
            Pattern pattern = Pattern.compile(RESIDENCE_PATTERN);
            Matcher matcher = pattern.matcher(value);
            return matcher.matches();
        }
        return true;
    }
}
