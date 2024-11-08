package com.pecodigos.zapweb.chats.controller;

import com.pecodigos.zapweb.chats.dtos.MessageDTO;
import com.pecodigos.zapweb.chats.services.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/messages")
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat-room/{chatRoomId}/sendMessage")
    public void sendMessage(@DestinationVariable String chatRoomId, @Payload MessageDTO message) {
        var savedMessage = messageService.saveMessage(message);

        messagingTemplate.convertAndSend("/topic/chat-rooms/" + chatRoomId, savedMessage);
    }

    @GetMapping("/")
    public List<MessageDTO> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("/{id}")
    public List<MessageDTO> getMessageByChatRoom(@PathVariable(name = "id") UUID chatRoomId) {
        return messageService.getAllMessages()
                .stream()
                .filter(msg -> msg.chatRoomId().equals(chatRoomId))
                .toList();
    }

    @PostMapping("/")
    public ResponseEntity<MessageDTO> sendMessageRest(@RequestBody MessageDTO messageDTO) {
        var savedMessage = messageService.saveMessage(messageDTO);
        return ResponseEntity.ok(savedMessage);
    }
}
