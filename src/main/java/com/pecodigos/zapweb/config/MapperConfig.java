package com.pecodigos.zapweb.config;

import com.pecodigos.zapweb.chats.dtos.mappers.ChatRoomMapper;
import com.pecodigos.zapweb.chats.dtos.mappers.MessageMapper;
import com.pecodigos.zapweb.users.dtos.mapper.UserMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public MessageMapper messageMapper() {
        return Mappers.getMapper(MessageMapper.class);
    }

    @Bean
    public ChatRoomMapper chatRoomMapper() {
        return Mappers.getMapper(ChatRoomMapper.class);
    }

    @Bean
    public UserMapper userMapper() {
        return Mappers.getMapper(UserMapper.class);
    }
}
