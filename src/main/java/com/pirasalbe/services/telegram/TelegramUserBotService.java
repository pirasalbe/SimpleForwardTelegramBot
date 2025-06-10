package com.pirasalbe.services.telegram;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pirasalbe.configurations.TelegramConfiguration;
import com.pirasalbe.services.telegram.listeners.MessageListener;

import it.tdlight.Init;
import it.tdlight.client.APIToken;
import it.tdlight.client.AuthenticationSupplier;
import it.tdlight.client.SimpleAuthenticationSupplier;
import it.tdlight.client.SimpleTelegramClient;
import it.tdlight.client.SimpleTelegramClientBuilder;
import it.tdlight.client.SimpleTelegramClientFactory;
import it.tdlight.client.TDLibSettings;
import it.tdlight.jni.TdApi.UpdateNewMessage;
import it.tdlight.util.UnsupportedNativeLibraryException;

/**
 * Telegram Service for the user bot
 *
 * @author pirasalbe
 *
 */
@Component
public class TelegramUserBotService implements AutoCloseable {

	private static final Logger LOGGER = LoggerFactory.getLogger(TelegramUserBotService.class);

	private static final String USERBOT_PATH = "userbot-data";

	private static SimpleTelegramClientFactory clientFactory;
	private static SimpleTelegramClient client;

	@Autowired
	private MessageListener messageListener;

	public TelegramUserBotService(TelegramConfiguration configuration)
			throws InterruptedException, UnsupportedNativeLibraryException {
		// Initialize TDLight native libraries
		Init.init();

		clientFactory = new SimpleTelegramClientFactory();

		// Obtain the API token
		APIToken apiToken = new APIToken(configuration.getApiId(), configuration.getApiHash());

		// Configure the client
		TDLibSettings settings = TDLibSettings.create(apiToken);

		// Configure the session directory
		Path sessionPath = Paths.get(USERBOT_PATH);
		settings.setDatabaseDirectoryPath(sessionPath.resolve("data"));
		settings.setDownloadedFilesDirectoryPath(sessionPath.resolve("downloads"));

		// Create a client
		SimpleTelegramClientBuilder clientBuilder = clientFactory.builder(settings);

		// Configure the authentication info
		SimpleAuthenticationSupplier<?> authenticationData = AuthenticationSupplier
				.user(configuration.getPhoneNumber());

		ExecutorService executorService = Executors.newFixedThreadPool(10);

		// Add an update handler that prints every received message
		clientBuilder.addUpdateHandler(UpdateNewMessage.class,
				(message) -> executorService.execute(() -> wrapHandler(() -> messageListener.accept(client, message))));

		executorService.execute(() -> {
			client = clientBuilder.build(authenticationData);
			LOGGER.info("User bot initialized");
		});
	}

	@Override
	public void close() throws Exception {
		client.close();
		clientFactory.close();
	}

	private void wrapHandler(Runnable method) {
		try {
			method.run();
		} catch (Exception e) {
			LOGGER.error("Unexpected error", e);
		}
	}

}