import path = require("path");
import * as vscode from "vscode";
import { LanguageClientOptions, RevealOutputChannelOn } from "vscode-languageclient";
import { LanguageClient, ServerOptions, State } from "vscode-languageclient/node";

const outputChannel = vscode.window.createOutputChannel("L22");

export class L22Client {
  private languageClient?: LanguageClient;
  private context?: vscode.ExtensionContext;

  setContext(context: vscode.ExtensionContext) {
    this.context = context;
  }
  
  async init(): Promise<void> {
    try {
      let options: ServerOptions = getServerOptions();

      const clientId = "L22-vscode-client";
      const clientName = "L22 VSCode Client";
      const clientOptions: LanguageClientOptions = {
        documentSelector: [
          {
            scheme: "file",
            language: "l22"
          }
        ],
        outputChannel: outputChannel,
        revealOutputChannelOn: RevealOutputChannelOn.Never 
      };

      this.languageClient = new LanguageClient(
        clientId,
        clientName,
        options,
        clientOptions
      );
      
      const disposeDidChange = this.languageClient.onDidChangeState((stateChangeEvent) => {
        if (stateChangeEvent.newState === State.Stopped) {
          vscode.window.showErrorMessage("Extension initialization failed!");
        } else if (stateChangeEvent.newState === State.Running) {
          vscode.window.showInformationMessage("Extension initialized :D");
        }
      });
      
      this.languageClient.start().then(() => {
        disposeDidChange.dispose();
      });
    } catch (e) {
      return Promise.reject("Extension error");
    }
  }
}

function getServerOptions() {
  const LS_HOME = "/home/s3np41k1r1t0/src/l22-lsp/l22-language-server/l22-language_server_bin/";
  const java = "/home/s3np41k1r1t0/.nix-profile/bin/java";
  // const args: string[] = ["-cp", LS_HOME];
  const args: string[] = ["-jar", path.join(LS_HOME, "l22-language-server-0.0.1-SNAPSHOT-jar-with-dependencies.jar")];

  const serverOptions: ServerOptions = {
    command: java,
    // args: [...args, LS_LAUNCHER_MAIN],
    args,
    options: {}
  };
  
  return serverOptions;
}

export const extensionInstance = new L22Client();