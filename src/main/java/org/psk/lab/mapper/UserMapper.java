package org.psk.lab.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.psk.lab.user.data.dto.UserDTO;
import org.psk.lab.user.data.model.MyUser;
import org.psk.lab.user.data.response.UserResponse;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source="roleType", target="role")
    MyUser toEntity(UserDTO userDTO);

    @Mapping(source = "uuid", target = "id")
    UserResponse toUserResponse(MyUser myUser);
}