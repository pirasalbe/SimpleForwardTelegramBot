# SimpleForwardTelegramBot

Telegram Bot to forward messages based on my needs.

## Build the project with TypeScript

```
npm run build
```

## Run the project

Define the following env variables before running the project.

- `API_ID`: application ID obtained from Telegram
- `API_HASH`: application hash obtained from Telegram
- `CHAT_ID`: chat to monitor
- `PHOTO_DESTINATION_CHAT_ID`: destination for photos
- `PHOTO_DESTINATION_THREAD_NAME`: thread in the destination
- `DOCUMENT_DESTINATION_CHAT_ID`: destination for documents
- `DOCUMENT_DESTINATION_THREAD_NAME`: thread in the destination
- `VIDEO_MESSAGE_CHAT_ID`: destination for video messages
- `VIDEO_MESSAGE_THREAD_NAME`: thread in the destination
- `DEBUG`: pass '1' to see all message

&nbsp;

Start the bot

```
npm start
```
