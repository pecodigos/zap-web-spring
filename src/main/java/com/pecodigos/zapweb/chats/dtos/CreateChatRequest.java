package com.pecodigos.zapweb.chats.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateChatRequest(@NotNull UUID userOneId, @NotNull UUID userTwoId) {
}
