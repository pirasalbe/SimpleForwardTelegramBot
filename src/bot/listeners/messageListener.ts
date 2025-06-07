import { Client } from 'tdl';
import { updateNewMessage } from 'tdlib-types';
import { SENDER } from '../../constants';
import { photoHandler } from './contents/photoHandler';

const CONTENT_HANDLERS: Record<
  string,
  (update: updateNewMessage, client: Client) => void
> = {
  // messageVideoNote: (update) => messageListener(update as updateNewMessage),
  messagePhoto: (update, client) => photoHandler(update, client),
  // messageDocument: (update) => messageListener(update as updateNewMessage),
};

const defaultHandler = (update: updateNewMessage) => {
  console.log('Received content', update.message.content._);
};

export const messageListener = (
  update: updateNewMessage,
  client: Client
): void => {
  const message = update.message;
  // only accept sender
  if (message.chat_id !== SENDER) {
    return;
  }

  const content = message.content;
  // based on content
  const handler = CONTENT_HANDLERS[content._] ?? defaultHandler;

  handler(update, client);
};
