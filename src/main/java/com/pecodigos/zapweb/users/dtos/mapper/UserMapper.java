package com.pecodigos.zapweb.users.dtos.mapper;

import com.pecodigos.zapweb.users.dtos.UserDTO;
import com.pecodigos.zapweb.users.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserDTO toDto(User user);
    User toEntity(UserDTO userDTO);
}
