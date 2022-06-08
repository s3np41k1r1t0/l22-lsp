package com.s3np41k1r1t0;

import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.TextDocumentService;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class L22TextDocumentService implements TextDocumentService {
    private L22LanguageServer languageServer;
    private Logger logger = Logger.getInstance();

    public L22TextDocumentService(L22LanguageServer languageServer) {
        this.languageServer = languageServer;
    }

    @Override
    public void didOpen(DidOpenTextDocumentParams params)  {
        logger.log("onOpen, uri='" + params.getTextDocument().getUri() + "'");

    }

    @Override
    public void didChange(DidChangeTextDocumentParams params)  {
        logger.log("onChange, uri='" + params.getTextDocument().getUri() + "'");
    }

    @Override
    public void didClose(DidCloseTextDocumentParams params) {
        logger.log("onClose, uri='" + params.getTextDocument().getUri() + "'");
    }

    @Override
    public void didSave(DidSaveTextDocumentParams params) {
        logger.log("onSave, uri='" + params.getTextDocument().getUri() + "'");
    }

    @Override
    public CompletableFuture<Either<List<CompletionItem>, CompletionList>> completion(CompletionParams params) {
        return CompletableFuture.supplyAsync( () -> {
            logger.log("onCompletion");
            CompletionItem item = new CompletionItem();
            item.setLabel("test");
            item.setInsertText("test text");
            item.setDetail("test snippet");
            item.setKind(CompletionItemKind.Snippet);
            return Either.forLeft(Arrays.asList(item));
        });
    }
}
