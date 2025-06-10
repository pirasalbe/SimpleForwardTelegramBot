package com.pirasalbe.helpers;

import it.tdlight.jni.TdApi.MessageSender;
import it.tdlight.jni.TdApi.MessageSenderUser;
import it.tdlight.jni.TdApi.UpdateNewMessage;

/**
 * Helper methods for filtering
 *
 * @author pirasalbe
 *
 */
public class FilterHelper {

	private FilterHelper() {
		super();
	}

	public static boolean isChat(long chatId, UpdateNewMessage message) {
		return chatId == message.message.chatId;
	}

	public static boolean isSender(long chatId, UpdateNewMessage message) {
		MessageSender senderId = message.message.senderId;

		return senderId instanceof MessageSenderUser && ((MessageSenderUser) senderId).userId == chatId;
	}

}
