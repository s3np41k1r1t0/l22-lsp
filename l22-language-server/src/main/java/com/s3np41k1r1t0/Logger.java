package com.s3np41k1r1t0;

import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.MessageType;
import org.eclipse.lsp4j.services.LanguageClient;

public class Logger {
    private static Logger instance = null;
    private static boolean initialized = false;
    private static LanguageClient client;

    private Logger() {}

    public static void init(LanguageClient client) {
        if (!initialized) {
            client = client;
        }

        initialized = true;
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }

        return instance;
    }

    public void log(String msg) {
        if (!initialized) {
            return;
        }
        client.logMessage(new MessageParams(MessageType.Info, msg));
    }
}
