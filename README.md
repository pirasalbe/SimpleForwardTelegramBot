# SimpleForwardTelegramBot

Telegram bot to manage requests.

# Instructions

## Build

The following command will create an executable jar.

```
./gradlew bootJar
```

Use the following to generate a Raspberry executable jar.

```
./gradlew -Ppi bootJar
```

### Build and Run

The following command runs build the project and runs it.

```
./gradlew bootRun
```

## Check health

```
curl localhost:8080/actuator/health
```

### Variables

- `bot.api-id`: application ID obtained from Telegram
- `bot.api-hash`: application hash obtained from Telegram
- `bot.chat-id`: chat to monitor
- `bot.photo-destination-chat-id`: destination for photos
- `bot.photo-destination-thread-name`: thread in the destination
- `bot.document-destination-chat-id`: destination for documents
- `bot.document-destination-thread-name`: thread in the destination
- `bot.video-message-destination-chat-id`: destination for video messages
- `bot.video-message-destination-thread-name`: thread in the destination
