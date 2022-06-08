package com.s3np41k1r1t0;

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.launch.LSPLauncher;
import org.eclipse.lsp4j.services.LanguageClient;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class L22LanguageServerLauncher {
    public static void main(String[] args) {
        L22LanguageServer server = new L22LanguageServer();
        Launcher<LanguageClient> launcher = LSPLauncher.createServerLauncher(
                server,
                System.in,
                System.out
        );
        Future<?> listener = launcher.startListening();
        try {
            listener.get();
        } catch (InterruptedException | ExecutionException e) {
            // Note to self: this block might not be needed but always nice to have logging :^)
            e.printStackTrace();
            server.shutdown();
        }
    }
}
