package com.demo.chat.chatRoom.controller;

import com.demo.chat.chatRoom.service.IChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ChatRoomController {
	private final IChatRoomService chatRoomService;

	@Autowired
	ChatRoomController(IChatRoomService chatRoomService) {
		this.chatRoomService = chatRoomService;
	}

	@GetMapping("/rooms")
	public String index(HttpServletRequest request, Model model) {
		String username = (String) request.getSession().getAttribute("username");
		if (username == null || username.isEmpty()) return "redirect:/login";

		model.addAttribute("username", username);
		model.addAttribute("rooms", chatRoomService.getAllRooms());

		return "rooms";
	}

	@GetMapping("/chat/{roomId}")
	public String chat(HttpServletRequest request, Model model,
					   @PathVariable("roomId") String roomId) {
		String username = (String) request.getSession().getAttribute("username");
		if (username == null || username.isEmpty()) return "redirect:/login";

		model.addAttribute("username", username);
		model.addAttribute("roomId", roomId);
		model.addAttribute("messages", chatRoomService.loadRoomMessages(roomId));

		return "chat";
	}
}
