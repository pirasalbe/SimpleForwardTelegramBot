export const API_ID = Number(process.env.API_ID);
export const API_HASH = String(process.env.API_HASH);

export const DEBUG = process.env.DEBUG === '1';

export const CHAT_ID = Number(process.env.CHAT_ID);

export const PHOTO_DESTINATION = {
  chat: Number(process.env.PHOTO_DESTINATION_CHAT_ID),
  thread: String(process.env.PHOTO_DESTINATION_THREAD_NAME),
};
export const DOCUMENT_DESTINATION = {
  chat: Number(process.env.DOCUMENT_DESTINATION_CHAT_ID),
  thread: String(process.env.DOCUMENT_DESTINATION_THREAD_NAME),
};
export const VIDEO_MESSAGE_DESTINATION = {
  chat: Number(process.env.VIDEO_MESSAGE_CHAT_ID),
  thread: String(process.env.VIDEO_MESSAGE_THREAD_NAME),
};
