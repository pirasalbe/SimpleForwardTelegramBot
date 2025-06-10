package com.pirasalbe.helpers.models;

public class ForumInfo {
	public long chatId;
	public String topicName;

	public ForumInfo(long chatId, String topicName) {
		super();
		this.chatId = chatId;
		this.topicName = topicName;
	}

	public long getChatId() {
		return chatId;
	}

	public String getTopicName() {
		return topicName;
	}
}
