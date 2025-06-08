import { Client } from 'tdl';
import { updateNewMessage } from 'tdlib-types';
import { VIDEO_MESSAGE_DESTINATION } from '../../../constants';
import { refreshChats } from '../../helper';

export const videoMessageHandler = async (
  update: updateNewMessage,
  client: Client
): Promise<void> => {
  console.log('Received video message');

  // get chats
  const { topic } = await refreshChats(client, update.message.chat_id, {
    chatId: VIDEO_MESSAGE_DESTINATION.chat,
    topicName: VIDEO_MESSAGE_DESTINATION.thread,
  });

  // forward files
  await client.invoke({
    _: 'forwardMessages',
    chat_id: VIDEO_MESSAGE_DESTINATION.chat,
    message_thread_id: topic?.info.message_thread_id,
    message_ids: [update.message.id],
    from_chat_id: update.message.chat_id,
  });
};
