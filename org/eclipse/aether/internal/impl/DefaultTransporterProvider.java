package org.eclipse.aether.internal.impl;

import javax.inject.*;
import org.eclipse.aether.spi.locator.*;
import org.eclipse.aether.*;
import org.eclipse.aether.repository.*;
import org.eclipse.aether.spi.connector.transport.*;
import org.eclipse.aether.transfer.*;
import java.util.*;
import org.slf4j.*;

@Named
public final class DefaultTransporterProvider implements TransporterProvider, Service
{
    private static final Logger LOGGER;
    private Collection<TransporterFactory> factories;
    
    public DefaultTransporterProvider() {
        super();
        this.factories = new ArrayList<TransporterFactory>();
    }
    
    @Inject
    DefaultTransporterProvider(final Set<TransporterFactory> transporterFactories) {
        super();
        this.factories = new ArrayList<TransporterFactory>();
        this.setTransporterFactories(transporterFactories);
    }
    
    @Override
    public void initService(final ServiceLocator serviceLocator) {
        this.setTransporterFactories(serviceLocator.getServices(TransporterFactory.class));
    }
    
    public DefaultTransporterProvider addTransporterFactory(final TransporterFactory transporterFactory) {
        this.factories.add(Objects.requireNonNull(transporterFactory, "transporter factory cannot be null"));
        return this;
    }
    
    public DefaultTransporterProvider setTransporterFactories(final Collection<TransporterFactory> factories) {
        if (factories == null) {
            this.factories = new ArrayList<TransporterFactory>();
        }
        else {
            this.factories = factories;
        }
        return this;
    }
    
    @Override
    public Transporter newTransporter(final RepositorySystemSession repositorySystemSession, final RemoteRepository remoteRepository) throws NoTransporterException {
        Objects.requireNonNull(remoteRepository, "remote repository cannot be null");
        final PrioritizedComponents<TransporterFactory> prioritizedComponents = new PrioritizedComponents<TransporterFactory>(repositorySystemSession);
        for (final TransporterFactory transporterFactory : this.factories) {
            prioritizedComponents.add(transporterFactory, transporterFactory.getPriority());
        }
        final ArrayList<NoTransporterException> list = new ArrayList<NoTransporterException>();
        for (final PrioritizedComponent<TransporterFactory> prioritizedComponent : prioritizedComponents.getEnabled()) {
            try {
                final Transporter instance = prioritizedComponent.getComponent().newInstance(repositorySystemSession, remoteRepository);
                if (DefaultTransporterProvider.LOGGER.isDebugEnabled()) {
                    final StringBuilder sb = new StringBuilder(256);
                    sb.append("Using transporter ").append(instance.getClass().getSimpleName());
                    Utils.appendClassLoader(sb, instance);
                    sb.append(" with priority ").append(prioritizedComponent.getPriority());
                    sb.append(" for ").append(remoteRepository.getUrl());
                    DefaultTransporterProvider.LOGGER.debug(sb.toString());
                }
                return instance;
            }
            catch (NoTransporterException ex) {
                list.add(ex);
                continue;
            }
            break;
        }
        if (DefaultTransporterProvider.LOGGER.isDebugEnabled() && list.size() > 1) {
            final String string = "Could not obtain transporter factory for " + remoteRepository;
            final Iterator<Object> iterator3 = list.iterator();
            while (iterator3.hasNext()) {
                DefaultTransporterProvider.LOGGER.debug(string, iterator3.next());
            }
        }
        final StringBuilder sb2 = new StringBuilder(256);
        if (prioritizedComponents.isEmpty()) {
            sb2.append("No transporter factories registered");
        }
        else {
            sb2.append("Cannot access ").append(remoteRepository.getUrl());
            sb2.append(" using the registered transporter factories: ");
            prioritizedComponents.list(sb2);
        }
        throw new NoTransporterException(remoteRepository, sb2.toString(), (list.size() == 1) ? list.get(0) : null);
    }
    
    static {
        LOGGER = LoggerFactory.getLogger(DefaultTransporterProvider.class);
    }
}
