package com.pirasalbe.services.telegram.listeners.contents;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.BiConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pirasalbe.configurations.TelegramConfiguration;
import com.pirasalbe.helpers.DateHelper;
import com.pirasalbe.helpers.FilterHelper;
import com.pirasalbe.helpers.TelegramBotHelper;
import com.pirasalbe.helpers.models.ForumInfo;
import com.pirasalbe.helpers.models.RefreshResult;

import it.tdlight.client.Result;
import it.tdlight.client.SimpleTelegramClient;
import it.tdlight.jni.TdApi.FormattedText;
import it.tdlight.jni.TdApi.ForwardMessages;
import it.tdlight.jni.TdApi.InputMessageReplyToMessage;
import it.tdlight.jni.TdApi.InputMessageText;
import it.tdlight.jni.TdApi.Message;
import it.tdlight.jni.TdApi.Messages;
import it.tdlight.jni.TdApi.SendMessage;
import it.tdlight.jni.TdApi.TextEntity;
import it.tdlight.jni.TdApi.UpdateNewMessage;

@Component
public class PhotoHandler implements BiConsumer<SimpleTelegramClient, UpdateNewMessage> {

	private static final Logger LOGGER = LoggerFactory.getLogger(PhotoHandler.class);

	@Autowired
	private TelegramConfiguration configuration;

	@Override
	public void accept(SimpleTelegramClient client, UpdateNewMessage update) {
		if (!FilterHelper.isSender(configuration.getChatId(), update)) {
			LOGGER.debug("Received update from other sender", update);
			return;
		}

		LOGGER.info("Received new photo");

		// get chats
		RefreshResult refreshResult = TelegramBotHelper.refreshChats(client, update.message.chatId, new ForumInfo(
				configuration.getPhotoDestinationChatId(), configuration.getPhotoDestinationThreadName()));

		Result<Messages> messages = TelegramBotHelper.sendSync(client,
				new ForwardMessages(configuration.getPhotoDestinationChatId(),
						refreshResult.getTopic().map(topic -> topic.info.messageThreadId).orElse(0l),
						update.message.chatId, new long[] { update.message.id }, null, false, false));

		Message message = messages.get().messages[0];

		LocalDateTime timestamp = DateHelper.integerToLocalDateTime(update.message.date);
		String text = timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

		TelegramBotHelper.sendSync(client,
				new SendMessage(message.chatId, message.messageThreadId,
						new InputMessageReplyToMessage(message.chatId, message.id, null), null, null,
						new InputMessageText(new FormattedText(text, new TextEntity[] {}), null, false)));
	}

}
