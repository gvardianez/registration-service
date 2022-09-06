package ru.alov.registration.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.alov.registration.converters.UserConverter;
import ru.alov.registration.dto.DeleteUserDto;
import ru.alov.registration.dto.RegistrationUserDto;
import ru.alov.registration.dto.UpdateUserPasswordDto;
import ru.alov.registration.dto.UserProfileDto;
import ru.alov.registration.services.UserService;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @PostMapping()
    public UserProfileDto createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        return UserConverter.USER_CONVERTER.entityToDto(userService.createNewUser(registrationUserDto));
    }

    @PutMapping()
    public void updateUserPassword(@RequestBody UpdateUserPasswordDto updateUserPasswordDto) {
        userService.updateUserPassword(updateUserPasswordDto);
    }

    @PostMapping("/delete")
    public void deleteUser(@RequestBody DeleteUserDto deleteUserDto) {
        userService.deleteUser(deleteUserDto);
    }

}
