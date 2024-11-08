package com.pecodigos.zapweb.chats.controller;

import com.pecodigos.zapweb.chats.dtos.ChatRoomDTO;
import com.pecodigos.zapweb.chats.dtos.CreateChatRequest;
import com.pecodigos.zapweb.chats.services.ChatRoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/chats")
@AllArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping("/create-or-fetch")
    public ResponseEntity<ChatRoomDTO> createOrFetchChat(@RequestBody CreateChatRequest request) {
        var chatRoomDTO = chatRoomService.createOrFetchChat(request);

        return ResponseEntity.ok(chatRoomDTO);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ChatRoomDTO>> getChatRoomForUsers(@PathVariable UUID userId) {
        var chatRooms = chatRoomService.getUserChatRooms(userId);
        return ResponseEntity.ok(chatRooms);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChatRoom(@PathVariable(name = "id") UUID id) {
        chatRoomService.deleteChatRoom(id);
        return ResponseEntity.noContent().build();
    }
}
