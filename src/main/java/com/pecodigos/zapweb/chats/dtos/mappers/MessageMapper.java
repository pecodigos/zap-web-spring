package com.pecodigos.zapweb.chats.dtos.mappers;

import com.pecodigos.zapweb.chats.dtos.MessageDTO;
import com.pecodigos.zapweb.chats.models.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MessageMapper {
    @Mapping(source = "chatRoom.id", target = "chatRoomId")
    @Mapping(source = "sender.id", target = "senderId")
    @Mapping(source = "recipient.id", target = "recipientId")
    MessageDTO toDto(Message message);

    @Mapping(source = "chatRoomId", target = "chatRoom.id")
    @Mapping(source = "senderId", target = "sender.id")
    @Mapping(source = "recipientId", target = "recipient.id")
    Message toEntity(MessageDTO messageDTO);
}


