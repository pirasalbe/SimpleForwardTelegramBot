import { updateNewMessage } from 'tdlib-types';
import { CHAT_ID } from '../constants';

export const isChat = ({ message }: updateNewMessage): boolean =>
  message.chat_id === CHAT_ID;

export const isSender = ({ message }: updateNewMessage): boolean =>
  message.sender_id._ === 'messageSenderUser' &&
  message.sender_id.user_id === CHAT_ID;
