package com.s3np41k1r1t0;

import org.eclipse.lsp4j.DidChangeConfigurationParams;
import org.eclipse.lsp4j.DidChangeWatchedFilesParams;
import org.eclipse.lsp4j.RenameFilesParams;
import org.eclipse.lsp4j.services.WorkspaceService;

public class L22WorkspaceService implements WorkspaceService {
    private L22LanguageServer languageServer;
    private Logger logger = Logger.getInstance();

    public L22WorkspaceService(L22LanguageServer languageServer) {
        this.languageServer = languageServer;
    }

    @Override
    public void didChangeConfiguration(DidChangeConfigurationParams params) {
        logger.log("onChangeConfiguration");
    }

    @Override
    public void didChangeWatchedFiles(DidChangeWatchedFilesParams params) {
        logger.log("onChangeWatchedFiles");
    }

    @Override
    public void didRenameFiles(RenameFilesParams params) {
        logger.log("onRenameFiles");
    }
}
