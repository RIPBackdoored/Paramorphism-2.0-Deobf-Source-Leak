package org.eclipse.aether.internal.impl;

import org.eclipse.aether.*;
import org.eclipse.aether.resolution.*;
import java.io.*;
import org.eclipse.aether.impl.*;
import org.eclipse.aether.metadata.*;
import org.eclipse.aether.repository.*;
import org.eclipse.aether.transfer.*;
import java.util.*;
import org.eclipse.aether.spi.connector.*;

class ResolveTask implements Runnable
{
    final RepositorySystemSession session;
    final RequestTrace trace;
    final MetadataResult result;
    final MetadataRequest request;
    final File metadataFile;
    final String policy;
    final List<UpdateCheck<Metadata, MetadataTransferException>> checks;
    MetadataTransferException exception;
    final DefaultMetadataResolver this$0;
    
    ResolveTask(final DefaultMetadataResolver this$0, final RepositorySystemSession session, final RequestTrace trace, final MetadataResult result, final File metadataFile, final List<UpdateCheck<Metadata, MetadataTransferException>> checks, final String policy) {
        this.this$0 = this$0;
        super();
        this.session = session;
        this.trace = trace;
        this.result = result;
        this.request = result.getRequest();
        this.metadataFile = metadataFile;
        this.policy = policy;
        this.checks = checks;
    }
    
    @Override
    public void run() {
        final Metadata metadata = this.request.getMetadata();
        final RemoteRepository repository = this.request.getRepository();
        DefaultMetadataResolver.access$000(this.this$0, this.session, this.trace, metadata, repository);
        try {
            final ArrayList<RemoteRepository> repositories = new ArrayList<RemoteRepository>();
            final Iterator<UpdateCheck<Metadata, MetadataTransferException>> iterator = this.checks.iterator();
            while (iterator.hasNext()) {
                repositories.add(iterator.next().getAuthoritativeRepository());
            }
            final MetadataDownload metadataDownload = new MetadataDownload();
            metadataDownload.setMetadata(metadata);
            metadataDownload.setRequestContext(this.request.getRequestContext());
            metadataDownload.setFile(this.metadataFile);
            metadataDownload.setChecksumPolicy(this.policy);
            metadataDownload.setRepositories(repositories);
            metadataDownload.setListener(SafeTransferListener.wrap(this.session));
            metadataDownload.setTrace(this.trace);
            try (final RepositoryConnector repositoryConnector = DefaultMetadataResolver.access$100(this.this$0).newRepositoryConnector(this.session, repository)) {
                repositoryConnector.get(null, Arrays.asList(metadataDownload));
            }
            this.exception = metadataDownload.getException();
            if (this.exception == null) {
                this.session.getLocalRepositoryManager().add(this.session, new LocalMetadataRegistration(metadata, repository, Collections.singletonList(this.request.getRequestContext())));
            }
            else if (this.request.isDeleteLocalCopyIfMissing() && this.exception instanceof MetadataNotFoundException) {
                metadataDownload.getFile().delete();
            }
        }
        catch (NoRepositoryConnectorException ex) {
            this.exception = new MetadataTransferException(metadata, repository, ex);
        }
        final Iterator<UpdateCheck<Metadata, MetadataTransferException>> iterator2 = this.checks.iterator();
        while (iterator2.hasNext()) {
            DefaultMetadataResolver.access$200(this.this$0).touchMetadata(this.session, iterator2.next().setException(this.exception));
        }
        DefaultMetadataResolver.access$300(this.this$0, this.session, this.trace, metadata, repository, this.metadataFile, this.exception);
    }
}
