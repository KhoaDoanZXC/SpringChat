package com.demo.chat.chatRoom.service;

import com.demo.chat.chatMessage.model.ChatMessage;
import com.demo.chat.chatRoom.model.ChatRoom;

import java.util.List;

public interface IChatRoomService {
	ChatRoom insertRoomMessage(String chatRoomId, ChatMessage chatMessage);

	List<ChatRoom> getAllRooms();

	List<ChatMessage> loadRoomMessages(String chatRoomId);
}
