import { Client } from 'tdl';
import { Update, updateNewMessage } from 'tdlib-types';
import { debug } from '../helper';
import { messageListener } from './messageListener';

const LISTENERS: Record<string, (update: Update, client: Client) => void> = {
  updateNewMessage: (update, client) =>
    messageListener(update as updateNewMessage, client),
};

const defaultHandler = (update: Update) => {
  debug('Received update', update._, JSON.stringify(update));
};

export const updateListener = (update: Update, client: Client): void => {
  const listener = LISTENERS[update._] ?? defaultHandler;

  listener(update, client);
};
