package ru.alov.registration.validators;

import org.springframework.stereotype.Component;
import ru.alov.registration.dto.RegistrationUserDto;
import ru.alov.registration.exceptions.FieldValidationException;

import java.util.ArrayList;
import java.util.List;

@Component
public class RegistrationValidator {

    public void validate(RegistrationUserDto registerUserDto) {
        List<String> errors = new ArrayList<>();
        if (registerUserDto.getLogin() == null || registerUserDto.getLogin().isBlank()) {
            errors.add("Поле имя пользователя не должно быть пустым");
        }
        if (registerUserDto.getPassword() == null || registerUserDto.getConfirmPassword() == null ||
                registerUserDto.getPassword().isBlank() || registerUserDto.getConfirmPassword().isBlank()) {
            errors.add("Поля с паролями должны быть заполнены");
        }
        if (registerUserDto.getPassword() != null && registerUserDto.getConfirmPassword() != null && !registerUserDto.getPassword().equals(registerUserDto.getConfirmPassword())) {
            errors.add("Введеные пароли не совпадают");
        }
        if (!errors.isEmpty()) {
            throw new FieldValidationException(errors);
        }
    }
}
