import { format } from 'date-fns';
import { Client } from 'tdl';
import { message, updateNewMessage } from 'tdlib-types';
import { PHOTO_DESTINATION } from '../../../constants';
import { isSender } from '../../filters';
import { debug, refreshChats } from '../../helper';

export const photoHandler = async (
  update: updateNewMessage,
  client: Client
): Promise<void> => {
  // only accept the exact sender
  if (!isSender(update)) {
    debug('Received update from other sender', JSON.stringify(update));
    return;
  }

  console.log('Received new photo');

  // get chats
  const { topic } = await refreshChats(client, update.message.chat_id, {
    chatId: PHOTO_DESTINATION.chat,
    topicName: PHOTO_DESTINATION.thread,
  });

  // forward photo
  const messages = await client.invoke({
    _: 'forwardMessages',
    chat_id: PHOTO_DESTINATION.chat,
    message_thread_id: topic?.info.message_thread_id,
    message_ids: [update.message.id],
    from_chat_id: update.message.chat_id,
  });

  const message = messages.messages[0] as message;

  // send timestamp
  const timestamp = format(
    new Date(update.message.date * 1000),
    'yyyy-MM-dd HH:mm X'
  );
  await client.invoke({
    _: 'sendMessage',
    chat_id: message.chat_id,
    message_thread_id: message.message_thread_id,
    reply_to: { _: 'inputMessageReplyToMessage', message_id: message.id },
    input_message_content: {
      _: 'inputMessageText',
      text: { _: 'formattedText', text: timestamp },
    },
  });
};
