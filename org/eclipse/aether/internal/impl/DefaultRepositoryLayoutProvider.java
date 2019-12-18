package org.eclipse.aether.internal.impl;

import javax.inject.*;
import org.eclipse.aether.spi.locator.*;
import org.eclipse.aether.*;
import org.eclipse.aether.repository.*;
import org.eclipse.aether.spi.connector.layout.*;
import org.eclipse.aether.transfer.*;
import java.util.*;
import org.slf4j.*;

@Named
public final class DefaultRepositoryLayoutProvider implements RepositoryLayoutProvider, Service
{
    private static final Logger LOGGER;
    private Collection<RepositoryLayoutFactory> factories;
    
    public DefaultRepositoryLayoutProvider() {
        super();
        this.factories = new ArrayList<RepositoryLayoutFactory>();
    }
    
    @Inject
    DefaultRepositoryLayoutProvider(final Set<RepositoryLayoutFactory> repositoryLayoutFactories) {
        super();
        this.factories = new ArrayList<RepositoryLayoutFactory>();
        this.setRepositoryLayoutFactories(repositoryLayoutFactories);
    }
    
    @Override
    public void initService(final ServiceLocator serviceLocator) {
        this.setRepositoryLayoutFactories(serviceLocator.getServices(RepositoryLayoutFactory.class));
    }
    
    public DefaultRepositoryLayoutProvider addRepositoryLayoutFactory(final RepositoryLayoutFactory repositoryLayoutFactory) {
        this.factories.add(Objects.requireNonNull(repositoryLayoutFactory, "layout factory cannot be null"));
        return this;
    }
    
    public DefaultRepositoryLayoutProvider setRepositoryLayoutFactories(final Collection<RepositoryLayoutFactory> factories) {
        if (factories == null) {
            this.factories = new ArrayList<RepositoryLayoutFactory>();
        }
        else {
            this.factories = factories;
        }
        return this;
    }
    
    @Override
    public RepositoryLayout newRepositoryLayout(final RepositorySystemSession repositorySystemSession, final RemoteRepository remoteRepository) throws NoRepositoryLayoutException {
        Objects.requireNonNull(remoteRepository, "remote repository cannot be null");
        final PrioritizedComponents<RepositoryLayoutFactory> prioritizedComponents = new PrioritizedComponents<RepositoryLayoutFactory>(repositorySystemSession);
        for (final RepositoryLayoutFactory repositoryLayoutFactory : this.factories) {
            prioritizedComponents.add(repositoryLayoutFactory, repositoryLayoutFactory.getPriority());
        }
        final ArrayList<NoRepositoryLayoutException> list = new ArrayList<NoRepositoryLayoutException>();
        for (final PrioritizedComponent<RepositoryLayoutFactory> prioritizedComponent : prioritizedComponents.getEnabled()) {
            try {
                return prioritizedComponent.getComponent().newInstance(repositorySystemSession, remoteRepository);
            }
            catch (NoRepositoryLayoutException ex) {
                list.add(ex);
                continue;
            }
            break;
        }
        if (DefaultRepositoryLayoutProvider.LOGGER.isDebugEnabled() && list.size() > 1) {
            final String string = "Could not obtain layout factory for " + remoteRepository;
            final Iterator<Object> iterator3 = list.iterator();
            while (iterator3.hasNext()) {
                DefaultRepositoryLayoutProvider.LOGGER.debug(string, iterator3.next());
            }
        }
        final StringBuilder sb = new StringBuilder(256);
        if (prioritizedComponents.isEmpty()) {
            sb.append("No layout factories registered");
        }
        else {
            sb.append("Cannot access ").append(remoteRepository.getUrl());
            sb.append(" with type ").append(remoteRepository.getContentType());
            sb.append(" using the available layout factories: ");
            prioritizedComponents.list(sb);
        }
        throw new NoRepositoryLayoutException(remoteRepository, sb.toString(), (list.size() == 1) ? list.get(0) : null);
    }
    
    static {
        LOGGER = LoggerFactory.getLogger(DefaultRepositoryLayoutProvider.class);
    }
}
