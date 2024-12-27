package com.monitor.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.monitor.rest.dto.user.UserRequest;
import com.monitor.rest.dto.user.UserResponse;
import com.monitor.rest.dto.user.UserWithPlants;
import com.monitor.rest.model.User;

@Mapper(
    componentModel = "spring",
    uses = {PlantMapper.class}
)
public interface UserMapper {
    
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "plants", ignore = true),
        @Mapping(target = "isEnabled", ignore = true),
        @Mapping(target = "accountNoExpired", ignore = true),
        @Mapping(target = "accountNoLocked", ignore = true),
        @Mapping(target = "credentialNoExpired", ignore = true),
        @Mapping(target = "roles", ignore = true),
    })
    User toUser(UserRequest user);

    @Mappings({
        @Mapping(target = "id", source = "userId"),
        @Mapping(target = "password", ignore = true),
        @Mapping(target = "plants", ignore = true),
        @Mapping(target = "isEnabled", ignore = true),
        @Mapping(target = "accountNoExpired", ignore = true),
        @Mapping(target = "accountNoLocked", ignore = true),
        @Mapping(target = "credentialNoExpired", ignore = true),
        @Mapping(target = "roles", ignore = true),
    })
    User toUser(UserResponse user);

    @Mapping(target = "userId", source = "id")
    UserResponse toUserResponse(User user);

    @Mapping(target = "userId", source = "id")
    UserWithPlants toUserWithPlants(User user);
}
