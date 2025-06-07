import { Client } from 'tdl';
import { message, updateNewMessage } from 'tdlib-types';
import { PHOTO_DESTINATION } from '../../../constants';

export const photoHandler = async (
  update: updateNewMessage,
  client: Client
): Promise<void> => {
  // get chats
  await client.invoke({ _: 'openChat', chat_id: update.message.chat_id });
  await client.invoke({ _: 'openChat', chat_id: PHOTO_DESTINATION.chat });

  // forward photo
  const messages = await client.invoke({
    _: 'forwardMessages',
    chat_id: PHOTO_DESTINATION.chat,
    message_thread_id: PHOTO_DESTINATION.thread,
    message_ids: [update.message.id],
  });

  const message = messages.messages[0] as message;

  await client.invoke({
    _: 'sendMessage',
    chat_id: PHOTO_DESTINATION.chat,
    message_thread_id: PHOTO_DESTINATION.thread,
    reply_to: { _: 'inputMessageReplyToMessage', message_id: message.id },
    input_message_content: {
      _: 'inputMessageText',
      text: {
        _: 'formattedText',
        text: "format(new Date(update.message.date), 'yyyy-MM-dd HH:mm X')",
      },
    },
  });
};
