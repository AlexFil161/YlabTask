package ThirdLesson.PasswordValidator;

import java.util.regex.Pattern;

public class PasswordValidator {
    static boolean passwordValidator(String login, String password, String confirmPassword)
            throws WrongLoginException, WrongPasswordException {

        Pattern pattern = Pattern.compile("\\w+");
        if(!Pattern.matches(String.valueOf(pattern), login)) {
            throw new WrongLoginException("Логин содержит недопустимые символы”");
        } else if(login.length() > 19) {
            throw new WrongLoginException("Логин слишком длинный");
        } else if(!Pattern.matches(String.valueOf(pattern), password)) {
            throw new WrongPasswordException("Пароль содержит недопустимые символы");
        } else if(password.length() > 19) {
            throw new WrongPasswordException("Пароль слишком длинный");
        } else if(!password.equals(confirmPassword)) {
            throw new WrongPasswordException("Пароль и подтверждение не совпадают");
        }
        return true;
    }
}
