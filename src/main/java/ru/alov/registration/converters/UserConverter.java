package ru.alov.registration.converters;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.alov.registration.dto.UserProfileDto;
import ru.alov.registration.entities.User;

@Mapper
public interface UserConverter {

    UserConverter USER_CONVERTER = Mappers.getMapper(UserConverter.class);

    UserProfileDto entityToDto(User user);

}
