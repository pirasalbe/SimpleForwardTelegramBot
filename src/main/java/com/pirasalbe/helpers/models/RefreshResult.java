package com.pirasalbe.helpers.models;

import java.util.Optional;

import it.tdlight.jni.TdApi.Chat;
import it.tdlight.jni.TdApi.ForumTopic;

public class RefreshResult {

	public Chat chat;
	public Chat forumChat;
	public Optional<ForumTopic> topic;

	public RefreshResult(Chat chat, Chat forumChat, Optional<ForumTopic> topic) {
		super();
		this.chat = chat;
		this.forumChat = forumChat;
		this.topic = topic;
	}

	public Chat getChat() {
		return chat;
	}

	public Chat getForumChat() {
		return forumChat;
	}

	public Optional<ForumTopic> getTopic() {
		return topic;
	}

}
