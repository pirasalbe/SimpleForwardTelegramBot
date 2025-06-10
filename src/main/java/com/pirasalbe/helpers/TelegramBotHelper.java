package com.pirasalbe.helpers;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import com.pirasalbe.helpers.models.ForumInfo;
import com.pirasalbe.helpers.models.RefreshResult;
import com.pirasalbe.models.exceptions.UserBotException;

import it.tdlight.client.Result;
import it.tdlight.client.SimpleTelegramClient;
import it.tdlight.jni.TdApi.Chat;
import it.tdlight.jni.TdApi.ForumTopic;
import it.tdlight.jni.TdApi.ForumTopics;
import it.tdlight.jni.TdApi.Function;
import it.tdlight.jni.TdApi.GetChat;
import it.tdlight.jni.TdApi.GetForumTopics;
import it.tdlight.jni.TdApi.Object;

/**
 * Helper methods for Telegram
 *
 * @author pirasalbe
 *
 */
public class TelegramBotHelper {

	private TelegramBotHelper() {
		super();
	}

	/**
	 * Execute method async
	 *
	 * @param <R>      Result type
	 * @param function Method to execute
	 * @return Future
	 */
	public static <R extends Object> Future<Result<R>> sendAsync(SimpleTelegramClient client, Function<R> function) {
		CompletableFuture<Result<R>> completableFuture = new CompletableFuture<>();

		client.send(function, completableFuture::complete);

		return completableFuture;
	}

	/**
	 * Execute method sync
	 *
	 * @param <R>      Result type
	 * @param function Method to execute
	 * @return Result of R
	 */
	public static <R extends Object> Result<R> sendSync(SimpleTelegramClient client, Function<R> function) {
		try {
			return sendAsync(client, function).get();
		} catch (InterruptedException | ExecutionException e) {
			throw new UserBotException(e);
		}
	}

	public static RefreshResult refreshChats(SimpleTelegramClient client, long chatId, ForumInfo forum) {
		Result<Chat> chat = sendSync(client, new GetChat(chatId));

		Result<Chat> forumChat = sendSync(client, new GetChat(forum.getChatId()));

		Result<ForumTopics> topicsResult = sendSync(client,
				new GetForumTopics(forum.getChatId(), null, 0, 0l, 0l, 100));

		ForumTopic[] topics = topicsResult.get().topics;
		Optional<ForumTopic> forumTopic = Stream.of(topics)
				.filter(topic -> topic.info.name.equals(forum.getTopicName())).findFirst();

		return new RefreshResult(chat.get(), forumChat.get(), forumTopic);
	}

}
