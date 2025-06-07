import { UserBot } from './bot/bot';

const userBot = new UserBot();

userBot.execute().catch((error) => console.error('Unexpected error', error));
