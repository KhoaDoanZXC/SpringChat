package com.demo.chat.chatRoom.model;

import com.demo.chat.chatMessage.model.ChatMessage;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

public class ChatRoom {
	@Id
	private String id;
	private String name;
	private List<ChatMessage> messageList = new ArrayList<ChatMessage>();

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ChatMessage> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<ChatMessage> messageList) {
		this.messageList = messageList;
	}

	public void insertMessage(ChatMessage chatMessage) {
		this.messageList.add(chatMessage);
	}

	@Override
	public String toString() {
		return "ChatRoom{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", messageList=" + messageList +
				'}';
	}
}
