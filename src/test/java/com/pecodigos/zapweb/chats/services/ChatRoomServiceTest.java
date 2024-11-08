package com.pecodigos.zapweb.chats.services;

import com.pecodigos.zapweb.chats.dtos.ChatRoomDTO;
import com.pecodigos.zapweb.chats.dtos.CreateChatRequest;
import com.pecodigos.zapweb.chats.dtos.mappers.ChatRoomMapper;
import com.pecodigos.zapweb.chats.models.ChatRoom;
import com.pecodigos.zapweb.chats.repositories.ChatRoomRepository;
import com.pecodigos.zapweb.enums.Role;
import com.pecodigos.zapweb.users.dtos.UserDTO;
import com.pecodigos.zapweb.users.model.User;
import com.pecodigos.zapweb.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ChatRoomServiceTest {

    @Mock
    private ChatRoomRepository chatRoomRepository;

    @Mock
    private ChatRoomMapper chatRoomMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    private UUID userOneId;

    @InjectMocks
    private ChatRoomService chatRoomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userOneId = UUID.randomUUID();

        when(authentication.getName()).thenReturn("userOne");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        UserDTO userOneDTO = new UserDTO(userOneId, "User One", "userOne", "userone@gmail.com", "testingPassword", Role.MEMBER);
        when(userRepository.findByUsername("userOne")).thenReturn(Optional.of(userOneDTO));
    }

    @Test
    void createOrFetchChatShouldReturnExistingChat() {
        userOneId = UUID.randomUUID();
        UUID userTwoId = UUID.randomUUID();
        UUID chatRoomId = UUID.randomUUID();

        CreateChatRequest request = new CreateChatRequest(userOneId, userTwoId);

        User userOne = new User();
        userOne.setId(userOneId);
        userOne.setName("User One");
        userOne.setUsername("userOne");
        userOne.setEmail("userone@gmail.com");
        userOne.setPassword("testingPassword");
        userOne.setRole(Role.MEMBER);
        userOne.setCreatedAt(LocalDateTime.now());
        userOne.setUpdatedAt(LocalDateTime.now());

        User userTwo = new User();
        userTwo.setId(userTwoId);
        userTwo.setName("User Two");
        userTwo.setUsername("userTwo");
        userTwo.setEmail("usertwo@gmail.com");
        userTwo.setPassword("testingPassword");
        userTwo.setRole(Role.MEMBER);
        userTwo.setCreatedAt(LocalDateTime.now());
        userTwo.setUpdatedAt(LocalDateTime.now());

        ChatRoom existingChatRoom = ChatRoom.builder()
                .id(chatRoomId)
                .name("User Two")
                .lastMessage("")
                .userOne(userOne)
                .userTwo(userTwo)
                .messages(new ArrayList<>())
                .build();

        when(chatRoomRepository.findChatRoomByUsers(userOneId, userTwoId)).thenReturn(Optional.of(existingChatRoom));
        when(userRepository.findById(userOneId)).thenReturn(Optional.of(userOne));
        when(userRepository.findById(userTwoId)).thenReturn(Optional.of(userTwo));

        when(chatRoomMapper.toDto(existingChatRoom)).thenReturn(new ChatRoomDTO(
                chatRoomId,
                "User Two",
                "",
                userOneId,
                userTwoId,
                new ArrayList<>()
        ));

        ChatRoomDTO result = chatRoomService.createOrFetchChat(request);

        assertNotNull(result);
        assertEquals(chatRoomId, result.id());
        assertEquals("User Two", result.name());
        assertEquals(userOneId, result.userOneId());
        assertEquals(userTwoId, result.userTwoId());

        verify(chatRoomRepository).findChatRoomByUsers(userOneId, userTwoId);
        verify(userRepository).findById(userOneId);
        verify(userRepository).findById(userTwoId);
    }
}
