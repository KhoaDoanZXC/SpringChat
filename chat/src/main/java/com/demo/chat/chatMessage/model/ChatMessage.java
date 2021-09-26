package com.demo.chat.chatMessage.model;

import org.springframework.data.annotation.Id;

public class ChatMessage {
	@Id
	private String id;
	private MessageType type;
	private Long time;
	private String userId;
	private String text;

	public enum MessageType {
		CHAT, JOIN, LEAVE
	}

	public ChatMessage() {
	}

	public String getId() {
		return id;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "ChatMessage{" +
				"id='" + id + '\'' +
				", type=" + type +
				", time=" + time +
				", userId='" + userId + '\'' +
				", text='" + text + '\'' +
				'}';
	}
}
