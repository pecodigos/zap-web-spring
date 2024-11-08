package com.pecodigos.zapweb.chats.repositories;

import com.pecodigos.zapweb.chats.models.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, UUID> {

    @Query("SELECT c FROM ChatRoom c WHERE " +
            "(c.userOne.id = :firstUserId AND c.userTwo.id = :secondUserId) " +
            "OR (c.userOne.id = :secondUserId AND c.userTwo.id = :firstUserId)")
    Optional<ChatRoom> findChatRoomByUsers(UUID firstUserId, UUID secondUserId);

    @Query("SELECT c FROM ChatRoom c WHERE (c.userOne.id = :userId OR c.userTwo.id = :userId)")
    List<ChatRoom> findChatRoomsByUserId(@Param("userId") UUID userId);
}
