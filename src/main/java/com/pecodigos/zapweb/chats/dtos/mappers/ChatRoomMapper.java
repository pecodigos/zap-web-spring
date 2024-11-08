package com.pecodigos.zapweb.chats.dtos.mappers;

import com.pecodigos.zapweb.chats.dtos.ChatRoomDTO;
import com.pecodigos.zapweb.chats.models.ChatRoom;
import com.pecodigos.zapweb.users.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper
public interface ChatRoomMapper {

    @Mapping(source = "userOne", target = "userOneId", qualifiedByName = "mapUserToId")
    @Mapping(source = "userTwo", target = "userTwoId", qualifiedByName = "mapUserToId")
    ChatRoomDTO toDto(ChatRoom chatRoom);

    @Mapping(target = "userOne", source = "userOneId", qualifiedByName = "mapIdToUser")
    @Mapping(target = "userTwo", source = "userTwoId", qualifiedByName = "mapIdToUser")
    ChatRoom toEntity(ChatRoomDTO chatRoomDTO);

    @Named("mapUserToId")
    static UUID mapUserToId(User user) {
        return user != null ? user.getId() : null;
    }

    @Named("mapIdToUser")
    static User mapIdToUser(UUID userId) {
        return userId != null ? User.builder().id(userId).build() : null;
    }
}

