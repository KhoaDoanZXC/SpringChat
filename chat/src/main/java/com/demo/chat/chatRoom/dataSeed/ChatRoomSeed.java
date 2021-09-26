//package com.demo.chat.room.dataSeed;
//
//import com.demo.chat.room.model.ChatRoom;
//import com.demo.chat.room.repository.IChatRoomRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//
//@Component
//public class ChatRoomSeed {
//	private IChatRoomRepo chatRoomRepo;
//
//	@Autowired
//	ChatRoomSeed(IChatRoomRepo chatRoomRepo) {
//		this.chatRoomRepo = chatRoomRepo;
//	}
//
//	@PostConstruct
//	private void seed() {
//		ChatRoom room1 = new ChatRoom();
//		room1.setName("meme");
//
//		ChatRoom room2 = new ChatRoom();
//		room2.setName("public");
//
//		ChatRoom room3 = new ChatRoom();
//		room3.setName("public2");
//
//		chatRoomRepo.save(room1);
//		chatRoomRepo.save(room2);
//		chatRoomRepo.save(room3);
//	}
//}
