package com.pirasalbe.services.telegram.listeners;

import java.util.function.BiConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pirasalbe.configurations.TelegramConfiguration;
import com.pirasalbe.helpers.FilterHelper;
import com.pirasalbe.services.telegram.listeners.contents.DocumentHandler;
import com.pirasalbe.services.telegram.listeners.contents.PhotoHandler;
import com.pirasalbe.services.telegram.listeners.contents.VideoMessageHandler;

import it.tdlight.client.SimpleTelegramClient;
import it.tdlight.jni.TdApi.MessageContent;
import it.tdlight.jni.TdApi.MessageDocument;
import it.tdlight.jni.TdApi.MessagePhoto;
import it.tdlight.jni.TdApi.MessageVideoNote;
import it.tdlight.jni.TdApi.UpdateNewMessage;

@Component
public class MessageListener implements BiConsumer<SimpleTelegramClient, UpdateNewMessage> {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageListener.class);

	@Autowired
	private TelegramConfiguration configuration;

	@Autowired
	private PhotoHandler photoHandler;
	@Autowired
	private DocumentHandler documentHandler;
	@Autowired
	private VideoMessageHandler videoMessageHandler;

	@Override
	public void accept(SimpleTelegramClient client, UpdateNewMessage update) {
		if (!FilterHelper.isChat(configuration.getChatId(), update)) {
			LOGGER.debug("Received update from other sender {}", update);
			return;
		}

		LOGGER.info("Received new message");

		MessageContent content = update.message.content;
		if (content instanceof MessagePhoto) {
			photoHandler.accept(client, update);
		} else if (content instanceof MessageDocument) {
			documentHandler.accept(client, update, (MessageDocument) content);
		} else if (content instanceof MessageVideoNote) {
			videoMessageHandler.accept(client, update);
		} else {
			LOGGER.debug("Received content {}", update.message.content);
		}
	}

}
