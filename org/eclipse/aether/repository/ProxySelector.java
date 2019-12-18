package org.eclipse.aether.repository;

public interface ProxySelector
{
    Proxy getProxy(final RemoteRepository p0);
}
