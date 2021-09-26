package com.demo.chat.chatRoom.controller;

import com.demo.chat.chatMessage.model.ChatMessage;
import com.demo.chat.chatRoom.model.ChatRoom;
import com.demo.chat.chatRoom.service.IChatRoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/api/chat")
public class ChatRoomAPI {
	private static final Logger log = LoggerFactory.getLogger(ChatRoomAPI.class);
	private final IChatRoomService chatRoomService;

	@Autowired
	ChatRoomAPI(IChatRoomService chatRoomService) {
		this.chatRoomService = chatRoomService;
	}

	@GetMapping("/rooms")
	public ResponseEntity<List<ChatRoom>> getRooms() {
		return ResponseEntity.status(200).body(chatRoomService.getAllRooms());
	}

	@GetMapping("/{roomId}")
	public ResponseEntity<List<ChatMessage>> chat(@PathVariable("roomId") String roomId) {
		return ResponseEntity.status(200).body(chatRoomService.loadRoomMessages(roomId));
	}
}
