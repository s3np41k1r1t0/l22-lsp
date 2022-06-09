import * as vscode from 'vscode';
import { Client } from './core/client';

export function activate(context: vscode.ExtensionContext) {
	Client.inititalize(context);

	let disposable = vscode.commands.registerCommand('l22-vscode.start', () => {
		vscode.window.showInformationMessage('Extension initialized :D');
	});

	context.subscriptions.push(disposable);
}

export function deactivate() {
	Client.shutdown();
}
