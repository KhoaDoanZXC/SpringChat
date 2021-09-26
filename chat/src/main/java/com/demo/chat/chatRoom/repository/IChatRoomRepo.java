package com.demo.chat.chatRoom.repository;

import com.demo.chat.chatRoom.model.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IChatRoomRepo extends MongoRepository<ChatRoom, String> {
	ChatRoom findChatRoomById(String chatRoomId);

	List<ChatRoom> findAll();
}
