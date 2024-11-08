package com.pecodigos.zapweb.chats.dtos;

import com.pecodigos.zapweb.chats.models.Message;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record ChatRoomDTO(@NotNull UUID id, @NotNull String name, String lastMessage, UUID userOneId, UUID userTwoId, List<Message> messages) {
}
