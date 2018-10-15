package ekindergarten.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class CivilIdValidator implements ConstraintValidator<ValidCivilId, String> {
    private static final String CIVIL_ID_PATTERN = "[A-Z]{3}\\d{6}";

    @Override
    public void initialize(final ValidCivilId constraintAnnotation) {
    }

    @Override
    public boolean isValid(String civilId, ConstraintValidatorContext context) {
        return validateCivilId(civilId);
    }

    private boolean validateCivilId(final String civilId) {
        if (civilId == null) return true;
        return Pattern.compile(CIVIL_ID_PATTERN).matcher(civilId).matches();
    }
}
