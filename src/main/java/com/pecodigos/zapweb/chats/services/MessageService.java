package com.pecodigos.zapweb.chats.services;

import com.pecodigos.zapweb.chats.dtos.MessageDTO;
import com.pecodigos.zapweb.chats.dtos.mappers.MessageMapper;
import com.pecodigos.zapweb.chats.repositories.ChatRoomRepository;
import com.pecodigos.zapweb.chats.repositories.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final ChatRoomRepository chatRoomRepository;

    public List<MessageDTO> getMessagesByChatRoom(UUID chatRoomId) {
        return messageRepository.findByChatRoomId(chatRoomId);
    }

    public MessageDTO saveMessage(MessageDTO messageDTO) {
        var chat = messageMapper.toEntity(messageDTO);
        var savedMessage = messageRepository.save(chat);

        var chatRoom = chatRoomRepository.findById(messageDTO.chatRoomId())
                .orElseThrow(() -> new NoSuchElementException("Chat room not found."));

        chatRoom.setLastMessage(savedMessage.getText());
        chatRoomRepository.save(chatRoom);

        return messageMapper.toDto(savedMessage);
    }

    public void deleteMessage(UUID id) {
        if (!messageRepository.existsById(id)) {
            throw new NoSuchElementException("No message with that id.");
        }

        messageRepository.deleteById(id);
    }
}