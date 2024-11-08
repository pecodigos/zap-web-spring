package com.pecodigos.zapweb.chats.services;

import com.pecodigos.zapweb.chats.dtos.ChatRoomDTO;
import com.pecodigos.zapweb.chats.dtos.CreateChatRequest;
import com.pecodigos.zapweb.chats.dtos.mappers.ChatRoomMapper;
import com.pecodigos.zapweb.chats.models.ChatRoom;
import com.pecodigos.zapweb.chats.repositories.ChatRoomRepository;
import com.pecodigos.zapweb.users.dtos.UserDTO;
import com.pecodigos.zapweb.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMapper chatRoomMapper;
    private final UserRepository userRepository;

    public ChatRoomDTO createOrFetchChat(CreateChatRequest request) {
        UUID userOneId = request.userOneId();
        UUID userTwoId = request.userTwoId();
        UUID currentUserId = getCurrentUserId();

        Optional<ChatRoom> existingChat = chatRoomRepository.findChatRoomByUsers(userOneId, userTwoId);
        var userOne = userRepository.findById(userOneId);
        var userTwo = userRepository.findById(userTwoId);

        if (userOne.isEmpty() || userTwo.isEmpty()) {
            throw new NoSuchElementException("Some error has occur.");
        }

        var chatName = currentUserId.equals(userOneId) ? userTwo.get() : userOne.get();

        if (existingChat.isPresent()) {
            var chatRoom = existingChat.get();
            chatRoom.setName(chatName.getName());
            return chatRoomMapper.toDto(chatRoom);
        } else {
            var newChatRoom = ChatRoom.builder()
                    .name(chatName.getName())
                    .lastMessage("")
                    .userOne(userOne.get())
                    .userTwo(userTwo.get())
                    .messages(new ArrayList<>())
                    .build();

            var savedChatRoom = chatRoomRepository.save(newChatRoom);
            return chatRoomMapper.toDto(savedChatRoom);
        }
    }

    public List<ChatRoomDTO> getUserChatRooms(UUID id) {
        var currentUserId = getCurrentUserId();

        if(!currentUserId.equals(id)) {
            throw new SecurityException("Unauthorized access to chat rooms.");
        }

        return chatRoomRepository.findChatRoomsByUserId(id)
                .stream().map(chatRoom -> {
                    var otherUserId = chatRoom.getUserOne().getId().equals(id)
                            ? chatRoom.getUserTwo().getId()
                            : chatRoom.getUserOne().getId();

                    String otherUserName = userRepository.findById(otherUserId)
                            .orElseThrow(() -> new NoSuchElementException("User not found."))
                            .getName();

                    chatRoom.setName(otherUserName);
                    return chatRoomMapper.toDto(chatRoom);
                })
                .toList();
    }

    public void deleteChatRoom(UUID id) {
        if (!chatRoomRepository.existsById(id)) {
            throw new NoSuchElementException("No chat room with that id.");
        }

        chatRoomRepository.deleteById(id);
    }

    private UUID getCurrentUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .map(UserDTO::id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }
}
