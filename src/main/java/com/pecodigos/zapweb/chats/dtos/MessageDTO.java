package com.pecodigos.zapweb.chats.dtos;

import com.pecodigos.zapweb.enums.ContentType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record MessageDTO(@NotNull UUID id, @NotNull String text, String imagePath, @NotNull UUID senderId, @NotNull UUID recipientId, @NotNull UUID chatRoomId, @NotNull ContentType contentType, LocalDateTime timestamp) {
}
