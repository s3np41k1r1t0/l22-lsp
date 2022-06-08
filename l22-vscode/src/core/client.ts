import * as path from 'path';
import * as vscode from 'vscode';
import { workspace, ExtensionContext } from 'vscode';

import {
  RevealOutputChannelOn,
} from "vscode-languageclient";

import {
	LanguageClient,
	LanguageClientOptions,
	ServerOptions,
} from 'vscode-languageclient/node';

const BIN_PATH = '/home/s3np41k1r1t0/src/l22-lsp/l22-language-server/l22-language_server_bin/l22-language-server-0.0.1-SNAPSHOT-jar-with-dependencies.jar';
const JAVA_PATH = '/home/s3np41k1r1t0/.nix-profile/bin/java';

const outputChannel = vscode.window.createOutputChannel("l22");

export class Client {
  private static instance: LanguageClient;

  static async inititalize(context: ExtensionContext) {
    if (Client.instance) {
      return;
    }

    const serverOptions: ServerOptions = {
      command: JAVA_PATH,
      args: ['-jar', BIN_PATH],
      options: {}
    };

    // Options to control the language client
    const clientOptions: LanguageClientOptions = {
      // Register the server for plain text documents
      documentSelector: [{ scheme: 'file', language: 'plaintext' }],
      outputChannel,
      revealOutputChannelOn: RevealOutputChannelOn.Never
    };

    // Create the language client and start the client.
    Client.instance = new LanguageClient(
      'l22LanguageServer',
      'L22 Language Server',
      serverOptions,
      clientOptions
    );

    // Start the client. This will also launch the server
    await Client.instance.start();
  }
  
  static async shutdown() {
    if (!Client.instance) {
      return;
    }
    
    await Client.instance.stop();
  }
};
