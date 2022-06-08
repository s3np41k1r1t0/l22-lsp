package com.s3np41k1r1t0;

import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.services.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class L22LanguageServer implements LanguageServer, LanguageClientAware {
    private TextDocumentService textDocumentService;
    private WorkspaceService workspaceService;
    private ClientCapabilities clientCapabilities;
    private LanguageClient languageClient;
    private int shutdown = -1;

    public L22LanguageServer() {
        this.textDocumentService = new L22TextDocumentService(this);
        this.workspaceService = new L22WorkspaceService(this);
    }

    @Override
    public CompletableFuture<InitializeResult> initialize(InitializeParams initializeParams) {
        final InitializeResult response = new InitializeResult(new ServerCapabilities());
        //Set the document synchronization capabilities to full.
        response.getCapabilities().setTextDocumentSync(TextDocumentSyncKind.Full);
        this.clientCapabilities = initializeParams.getCapabilities();

        /*
            Check if dynamic registration of completion capability is allowed by the client.
            If so we don't register the capability.
            Else, we register the completion capability.
         */
        if (!isDynamicCompletionRegistration()) {
            response.getCapabilities().setCompletionProvider(new CompletionOptions());
        }
        return CompletableFuture.supplyAsync(() -> response);
    }

    @Override
    public void initialized(InitializedParams params) {
        // Check if dynamic completion support is allowed, if so register.
        if (isDynamicCompletionRegistration()) {
            CompletionRegistrationOptions completionRegistrationOptions = new CompletionRegistrationOptions();
            Registration completionRegistration = new Registration(UUID.randomUUID().toString(),
                    "textDocument/completion", completionRegistrationOptions);
            languageClient.registerCapability(new RegistrationParams(List.of(completionRegistration)));
        }
    }

    @Override
    public CompletableFuture<Object> shutdown() {
        shutdown = 0;
        return CompletableFuture.supplyAsync(Object::new);
    }

    @Override
    public void exit() {
        System.exit(shutdown);
    }

    @Override
    public TextDocumentService getTextDocumentService() {
        return this.textDocumentService;
    }

    @Override
    public WorkspaceService getWorkspaceService() {
        return this.workspaceService;
    }

    @Override
    public void connect(LanguageClient languageClient) {
        this.languageClient = languageClient;
        Logger.init(this.languageClient);
    }

    private boolean isDynamicCompletionRegistration() {
        TextDocumentClientCapabilities textDocumentCapabilities =
                clientCapabilities.getTextDocument();
        return textDocumentCapabilities != null && textDocumentCapabilities.getCompletion() != null
                && Boolean.FALSE.equals(textDocumentCapabilities.getCompletion().getDynamicRegistration());
    }

}
