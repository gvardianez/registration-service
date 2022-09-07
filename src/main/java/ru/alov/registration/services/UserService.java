package ru.alov.registration.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alov.registration.dto.DeleteUserDto;
import ru.alov.registration.dto.RegistrationUserDto;
import ru.alov.registration.dto.UpdateUserPasswordDto;
import ru.alov.registration.entities.User;
import ru.alov.registration.exceptions.FieldValidationException;
import ru.alov.registration.exceptions.ResourceNotFoundException;
import ru.alov.registration.repositories.Repository;
import ru.alov.registration.repositories.UserRepository;
import ru.alov.registration.validators.RegistrationValidator;
import ru.alov.registration.validators.UpdatePasswordValidator;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RegistrationValidator registrationValidator;

    private final UpdatePasswordValidator updatePasswordValidator;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByLogin(username);
    }

    public User createNewUser(RegistrationUserDto registrationUserDto) {
        registrationValidator.validate(registrationUserDto);
        if (findByUsername(registrationUserDto.getLogin()).isPresent())
            throw new IllegalStateException("Имя пользователя уже используется");
        return userRepository.create(new User(registrationUserDto.getLogin(), registrationUserDto.getPassword())).orElseThrow(RuntimeException::new);
    }

    public void updateUserPassword(UpdateUserPasswordDto updateUserPasswordDto) {
        updatePasswordValidator.validate(updateUserPasswordDto);
        User user = findByUsername(updateUserPasswordDto.getLogin()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (!user.getPassword().equals(updateUserPasswordDto.getOldPassword())) {
            throw new IllegalStateException("Wrong old password");
        }
        user.setPassword(updateUserPasswordDto.getNewPassword());
        System.out.println(user.getLogin() +" " +user.getPassword());
        userRepository.update(user);
    }

    public void deleteUser(DeleteUserDto deleteUserDto) {
        if (deleteUserDto.getLogin() == null || deleteUserDto.getPassword() == null || deleteUserDto.getPassword().isBlank() || deleteUserDto.getLogin().isBlank()) {
            throw new FieldValidationException(Collections.singletonList("Поля должны быть заполнены"));
        }
        User user = findByUsername(deleteUserDto.getLogin()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (!user.getPassword().equals(deleteUserDto.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        userRepository.delete(user);
    }

}
