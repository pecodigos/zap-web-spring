package com.pecodigos.zapweb.chats.repositories;

import com.pecodigos.zapweb.chats.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
}
