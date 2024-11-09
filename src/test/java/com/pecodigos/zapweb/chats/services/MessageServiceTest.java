/* package com.pecodigos.zapweb.chats.services;

import com.pecodigos.zapweb.chats.dtos.MessageDTO;
import com.pecodigos.zapweb.chats.dtos.mappers.ChatRoomMapper;
import com.pecodigos.zapweb.chats.dtos.mappers.MessageMapper;
import com.pecodigos.zapweb.chats.models.ChatRoom;
import com.pecodigos.zapweb.chats.models.Message;
import com.pecodigos.zapweb.chats.repositories.ChatRoomRepository;
import com.pecodigos.zapweb.chats.repositories.MessageRepository;
import com.pecodigos.zapweb.enums.ContentType;
import com.pecodigos.zapweb.users.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MessageServiceTest {

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private MessageMapper messageMapper;

    @Mock
    private ChatRoomRepository chatRoomRepository;

    @Mock
    private ChatRoomMapper chatRoomMapper;

    @InjectMocks
    private MessageService messageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldThrowNoSuchElementExceptionWhenMessageNotFoundOnGetMessage() {
        UUID messageId = UUID.randomUUID();
        when(messageRepository.findById(messageId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> messageService.getMessage(messageId));
    }

    @Test
    void shouldReturnAllMessages() {
        UUID messageId1 = UUID.randomUUID();
        UUID messageId2 = UUID.randomUUID();
        UUID senderId = UUID.randomUUID();
        UUID recipientId = UUID.randomUUID();
        UUID chatRoomId = UUID.randomUUID();

        Message message1 = new Message();
        message1.setId(messageId1);
        message1.setChatRoom(new ChatRoom(chatRoomId, "", "", new User(), new User(), new ArrayList<>()));
        message1.setTimestamp(LocalDateTime.now());
        message1.setText("Hello!");


        Message message2 = new Message();
        message2.setId(messageId2);
        message2.setChatRoom(new ChatRoom(chatRoomId, "", "", new User(), new User(), new ArrayList<>()));
        message2.setTimestamp(LocalDateTime.now());
        message2.setText("Sup, brother.");

        List<Message> messageList = List.of(message1, message2);

        MessageDTO messageDTO1 = new MessageDTO(
                messageId1,
                message1.getText(),
                "",
                senderId,
                recipientId,
                chatRoomId,
                ContentType.TEXT,
                message1.getTimestamp()
        );

        MessageDTO messageDTO2 = new MessageDTO(
                messageId2,
                message2.getText(),
                "",
                senderId,
                recipientId,
                chatRoomId,
                ContentType.TEXT,
                message2.getTimestamp()
        );

        List<MessageDTO> messageDTOList = List.of(messageDTO1, messageDTO2);

        when(messageRepository.findAll()).thenReturn(messageList);
        when(messageMapper.toDto(message1)).thenReturn(messageDTO1);
        when(messageMapper.toDto(message2)).thenReturn(messageDTO2);

        List<MessageDTO> result = messageService.getAllMessages();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(messageDTO1.id(), result.getFirst().id());
        assertEquals(messageDTO2.id(), result.get(1).id());
        assertEquals("Hello!", result.getFirst().text());
        assertEquals("Sup, brother.", result.get(1).text());

        verify(messageRepository).findAll();
        verify(messageMapper).toDto(message1);
        verify(messageMapper).toDto(message2);
    }

    @Test
    void shouldSaveAndReturnMessage() {
        UUID messageId = UUID.randomUUID();
        UUID senderId = UUID.randomUUID();
        UUID recipientId = UUID.randomUUID();
        UUID chatRoomId = UUID.randomUUID();
        MessageDTO messageDTO = new MessageDTO(messageId, "", "", senderId, recipientId, chatRoomId, ContentType.TEXT, null);

        Message message = new Message();
        message.setId(messageId);
        message.setChatRoom(new ChatRoom(chatRoomId, "", "", new User(), new User(), new ArrayList<>()));
        message.setContentType(null);
        message.setTimestamp(LocalDateTime.now());

        ChatRoom chatRoom = new ChatRoom(chatRoomId, "", "", new User(), new User(), new ArrayList<>());

        when(messageMapper.toEntity(any(MessageDTO.class))).thenReturn(message);
        when(messageMapper.toDto(any(Message.class))).thenReturn(messageDTO);
        when(messageRepository.save(any(Message.class))).thenReturn(message);

        when(chatRoomRepository.findById(chatRoomId)).thenReturn(Optional.of(chatRoom));
        when(chatRoomRepository.save(any(ChatRoom.class))).thenReturn(chatRoom);

        MessageDTO savedMessageDTO = messageService.saveMessage(messageDTO);

        assertNotNull(savedMessageDTO);
        assertEquals(messageId, savedMessageDTO.id());
        assertEquals(senderId, savedMessageDTO.senderId());
        assertEquals(chatRoomId, savedMessageDTO.chatRoomId());

        verify(chatRoomRepository).findById(chatRoomId);
        verify(chatRoomRepository).save(chatRoom);
    }



    @Test
    void shouldDeleteMessage() {
        UUID messageId = UUID.randomUUID();
        when(messageRepository.existsById(messageId)).thenReturn(true);

        messageService.deleteMessage(messageId);

        verify(messageRepository).deleteById(messageId);
    }
}
*/
