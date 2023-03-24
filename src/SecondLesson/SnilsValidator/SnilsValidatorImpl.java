package SecondLesson.SnilsValidator;

import java.util.regex.Pattern;

public class SnilsValidatorImpl implements SnilsValidator {
    @Override
    public boolean validate(String snils) {
        Pattern pattern = Pattern.compile("[0-9]{11}");
        if (!Pattern.matches(String.valueOf(pattern), snils)) {
            return false;
        } else {
            char[] numbers = snils.toCharArray();
            int sum = 0;
            int coefficient = 9;
            for (int i = 0; i < numbers.length - 2; i++) {
                sum = Character.getNumericValue(numbers[i]) * coefficient + sum;
                coefficient--;
            }

            int snilsControlNumber = Integer.parseInt(String.valueOf(numbers[9]) + numbers[10]);

            int controlNumber;

            if (sum < 100) {
                controlNumber = sum;
            } else if (sum == 100) {
                controlNumber = 0;
            } else {
                if (sum % 101 == 100) {
                    controlNumber = 0;
                } else {
                    controlNumber = sum % 101;
                }
            }


            return controlNumber == snilsControlNumber;
        }
    }
}