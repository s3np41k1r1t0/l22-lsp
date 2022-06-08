import * as vscode from 'vscode';
import { extensionInstance } from './core/l22client';

export function activate(context: vscode.ExtensionContext) {
	extensionInstance.setContext(context);
	extensionInstance.init().catch((error)=> {
		console.log(`Extension activation failed: ${error}`);
	});
}

export function deactivate() {}