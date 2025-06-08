import { debug } from 'console';
import { Client } from 'tdl';
import { messageDocument, updateNewMessage } from 'tdlib-types';
import { DOCUMENT_DESTINATION } from '../../../constants';
import { isSender } from '../../filters';
import { refreshChats } from '../../helper';

export const documentHandler = async (
  update: updateNewMessage,
  client: Client,
  content: messageDocument
): Promise<void> => {
  // only accept the exact sender
  if (!isSender(update)) {
    debug('Received update from other sender', JSON.stringify(update));
    return;
  }

  // accepts only media files
  const mimeType = content.document.mime_type;
  if (!mimeType.startsWith('image') && !mimeType.startsWith('video')) {
    debug('Received wrong mime type', JSON.stringify(content));
    return;
  }

  console.log('Received new document', mimeType);

  // get chats
  const { topic } = await refreshChats(client, update.message.chat_id, {
    chatId: DOCUMENT_DESTINATION.chat,
    topicName: DOCUMENT_DESTINATION.thread,
  });

  // forward files
  await client.invoke({
    _: 'forwardMessages',
    chat_id: DOCUMENT_DESTINATION.chat,
    message_thread_id: topic?.info.message_thread_id,
    message_ids: [update.message.id],
    from_chat_id: update.message.chat_id,
  });
};
