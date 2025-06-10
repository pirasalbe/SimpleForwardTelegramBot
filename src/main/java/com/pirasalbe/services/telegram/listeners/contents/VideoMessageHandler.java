package com.pirasalbe.services.telegram.listeners.contents;

import java.util.function.BiConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pirasalbe.configurations.TelegramConfiguration;
import com.pirasalbe.helpers.TelegramBotHelper;
import com.pirasalbe.helpers.models.ForumInfo;
import com.pirasalbe.helpers.models.RefreshResult;

import it.tdlight.client.SimpleTelegramClient;
import it.tdlight.jni.TdApi.ForwardMessages;
import it.tdlight.jni.TdApi.UpdateNewMessage;

@Component
public class VideoMessageHandler implements BiConsumer<SimpleTelegramClient, UpdateNewMessage> {

	private static final Logger LOGGER = LoggerFactory.getLogger(VideoMessageHandler.class);

	@Autowired
	private TelegramConfiguration configuration;

	@Override
	public void accept(SimpleTelegramClient client, UpdateNewMessage update) {
		LOGGER.info("Received new video message");

		// get chats
		RefreshResult refreshResult = TelegramBotHelper.refreshChats(client, update.message.chatId,
				new ForumInfo(configuration.getVideoMessageDestinationChatId(),
						configuration.getVideoMessageDestinationThreadName()));

		TelegramBotHelper.sendSync(client,
				new ForwardMessages(configuration.getVideoMessageDestinationChatId(),
						refreshResult.getTopic().map(topic -> topic.info.messageThreadId).orElse(0l),
						update.message.chatId, new long[] { update.message.id }, null, false, false));
	}

}
