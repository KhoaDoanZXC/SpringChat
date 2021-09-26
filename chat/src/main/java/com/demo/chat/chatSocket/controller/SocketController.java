package com.demo.chat.chatSocket.controller;

import com.demo.chat.chatMessage.model.ChatMessage;
import com.demo.chat.chatRoom.service.IChatRoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class SocketController {
	private static final Logger log = LoggerFactory.getLogger(SocketController.class);
	private final IChatRoomService chatRoomService;

	@Autowired
	public SocketController(IChatRoomService chatRoomService) {
		this.chatRoomService = chatRoomService;
	}

	@MessageMapping("{roomId}/chat")
	@SendTo("/topic/{roomId}")
	public ChatMessage sendMessage(@DestinationVariable("roomId") String roomId, ChatMessage chatMessage) {
		chatRoomService.insertRoomMessage(roomId, chatMessage);
		return chatMessage;
	}

	@MessageMapping("/{roomId}/join")
	@SendTo("/topic/{roomId}")
	public ChatMessage addUser(@DestinationVariable("roomId") String roomId,
							   ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
		headerAccessor.getSessionAttributes().put("username", chatMessage.getUserId());
		return chatMessage;
	}
}
