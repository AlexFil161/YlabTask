package ThirdLesson.PasswordValidator;

import static ThirdLesson.PasswordValidator.PasswordValidator.passwordValidator;

public class PasswordValidatorTest {
    public static void main(String[] args) throws WrongLoginException, WrongPasswordException {
        System.out.println(passwordValidator("Alex", "QWERT", "QWERT"));
    }
}
