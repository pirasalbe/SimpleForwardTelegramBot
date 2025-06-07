import { getTdjson } from 'prebuilt-tdlib';
import { Client, configure, createClient } from 'tdl';
import { API_HASH, API_ID } from '../constants';
import { updateListener } from './listeners/updateListener';

export class UserBot {
  // TDLib client https://github.com/eilvelia/tdl
  private client: Client;

  constructor() {
    // configure td lib
    configure({ tdjson: getTdjson() });

    // create client
    this.client = createClient({ apiId: Number(API_ID), apiHash: API_HASH });

    // configure client
    this.client.on('error', (update) => console.error(update));

    this.client.on('update', (update) => updateListener(update, this.client));
  }

  async execute() {
    // Log in to a Telegram account
    await this.client.login();
  }

  async close() {
    // Close the instance so that TDLib exits gracefully and the JS runtime can
    // finish the process.
    await this.client.close();
  }
}
