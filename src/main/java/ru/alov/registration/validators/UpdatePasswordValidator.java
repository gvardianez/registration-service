package ru.alov.registration.validators;

import org.springframework.stereotype.Component;
import ru.alov.registration.dto.UpdateUserPasswordDto;
import ru.alov.registration.exceptions.FieldValidationException;

import java.util.ArrayList;
import java.util.List;

@Component
public class UpdatePasswordValidator {

    public void validate(UpdateUserPasswordDto updateUserPasswordDto) {
        List<String> errors = new ArrayList<>();
        if (updateUserPasswordDto.getLogin() == null || updateUserPasswordDto.getLogin().isBlank()) {
            errors.add("Поле имя пользователя не должно быть пустым");
        }
        if (updateUserPasswordDto.getNewPassword() == null || updateUserPasswordDto.getConfirmNewPassword() == null || updateUserPasswordDto.getOldPassword() == null ||
                updateUserPasswordDto.getNewPassword().isBlank() || updateUserPasswordDto.getConfirmNewPassword().isBlank() || updateUserPasswordDto.getOldPassword().isBlank()) {
            errors.add("Поля с паролями должны быть заполнены");
        }
        if (updateUserPasswordDto.getNewPassword() != null && updateUserPasswordDto.getConfirmNewPassword() != null && !updateUserPasswordDto.getNewPassword().equals(updateUserPasswordDto.getConfirmNewPassword())) {
            errors.add("Введеные пароли не совпадают");
        }
        if (!errors.isEmpty()) {
            throw new FieldValidationException(errors);
        }
    }

}
