package com.demo.chat.chatRoom.service;

import com.demo.chat.chatMessage.model.ChatMessage;
import com.demo.chat.chatRoom.model.ChatRoom;
import com.demo.chat.chatRoom.repository.IChatRoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ChatRoomService implements IChatRoomService {
	private IChatRoomRepo repo;

	@Autowired
	ChatRoomService(IChatRoomRepo repo) {
		this.repo = repo;
	}

	@Override
	public ChatRoom insertRoomMessage(String chatRoomId, ChatMessage chatMessage) {
		Date date = new Date();
		chatMessage.setTime(date.getTime());

		ChatRoom chatRoom = repo.findChatRoomById(chatRoomId);
		chatRoom.insertMessage(chatMessage);
		chatRoom = repo.save(chatRoom);

		return chatRoom;
	}

	@Override
	public List<ChatRoom> getAllRooms() {
		return repo.findAll();
	}

	@Override
	public List<ChatMessage> loadRoomMessages(String chatRoomId) {
		ChatRoom chatRoom = repo.findChatRoomById(chatRoomId);
		return chatRoom.getMessageList();
	}
}
