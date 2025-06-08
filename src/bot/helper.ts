import { Client } from 'tdl';
import { DEBUG } from '../constants';

export const debug = (...message: unknown[]) => {
  if (DEBUG) {
    console.log(...message);
  }
};

export const refreshChats = async (
  client: Client,
  chatId: number,
  forum: { chatId: number; topicName: string }
) => {
  const chat = await client.invoke({ _: 'getChat', chat_id: chatId });
  const forumChat = await client.invoke({
    _: 'getChat',
    chat_id: forum.chatId,
  });

  const topics = await client.invoke({
    _: 'getForumTopics',
    chat_id: forum.chatId,
    limit: 100,
  });

  const topic = topics.topics.find(
    (topic) => topic.info.name === forum.topicName
  );

  return { chat, forumChat, topic };
};
