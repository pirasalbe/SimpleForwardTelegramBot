import { Client } from 'tdl';
import { messageDocument, updateNewMessage } from 'tdlib-types';
import { isChat } from '../filters';
import { debug } from '../helper';
import { documentHandler } from './contents/documentHandler';
import { photoHandler } from './contents/photoHandler';
import { videoMessageHandler } from './contents/videoMessageHandler';

const CONTENT_HANDLERS: Record<
  string,
  (update: updateNewMessage, client: Client) => void
> = {
  messageVideoNote: (update, client) => videoMessageHandler(update, client),
  messagePhoto: (update, client) => photoHandler(update, client),
  messageDocument: (update, client) =>
    documentHandler(update, client, update.message.content as messageDocument),
};

const defaultHandler = (update: updateNewMessage) => {
  debug(
    'Received content',
    update.message.content._,
    JSON.stringify(update.message.content)
  );
};

export const messageListener = (
  update: updateNewMessage,
  client: Client
): void => {
  // only accept specific chat
  if (!isChat(update)) {
    debug('Received update from other chat', JSON.stringify(update));
    return;
  }

  console.log('Received new message');

  const content = update.message.content;
  // based on content
  const handler = CONTENT_HANDLERS[content._] ?? defaultHandler;

  handler(update, client);
};
