package com.pirasalbe.services.telegram.listeners.contents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pirasalbe.configurations.TelegramConfiguration;
import com.pirasalbe.helpers.FilterHelper;
import com.pirasalbe.helpers.TelegramBotHelper;
import com.pirasalbe.helpers.models.ForumInfo;
import com.pirasalbe.helpers.models.RefreshResult;

import it.tdlight.client.SimpleTelegramClient;
import it.tdlight.jni.TdApi.ForwardMessages;
import it.tdlight.jni.TdApi.MessageDocument;
import it.tdlight.jni.TdApi.UpdateNewMessage;

@Component
public class DocumentHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentHandler.class);

	@Autowired
	private TelegramConfiguration configuration;

	public void accept(SimpleTelegramClient client, UpdateNewMessage update, MessageDocument content) {
		if (!FilterHelper.isSender(configuration.getChatId(), update)) {
			LOGGER.debug("Received update from other sender", update);
			return;
		}

		// accepts only media files
		String mimeType = content.document.mimeType;
		if (!mimeType.startsWith("image") && !mimeType.startsWith("video")) {
			LOGGER.debug("Received wrong mime type {}", content);
			return;
		}

		LOGGER.info("Received new document {}", mimeType);

		// get chats
		RefreshResult refreshResult = TelegramBotHelper.refreshChats(client, update.message.chatId, new ForumInfo(
				configuration.getDocumentDestinationChatId(), configuration.getDocumentDestinationThreadName()));

		TelegramBotHelper.sendSync(client,
				new ForwardMessages(configuration.getDocumentDestinationChatId(),
						refreshResult.getTopic().map(topic -> topic.info.messageThreadId).orElse(0l),
						update.message.chatId, new long[] { update.message.id }, null, false, false));
	}

}
