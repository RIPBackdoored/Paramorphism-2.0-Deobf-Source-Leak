package org.eclipse.aether.util.repository;

import java.net.*;
import org.eclipse.aether.repository.*;
import java.util.*;

private static final class JreProxyAuthentication implements Authentication
{
    public static final Authentication INSTANCE;
    
    private JreProxyAuthentication() {
        super();
    }
    
    @Override
    public void fill(final AuthenticationContext authenticationContext, final String s, final Map<String, String> map) {
        final Proxy proxy = authenticationContext.getProxy();
        if (proxy == null) {
            return;
        }
        if (!"username".equals(s) && !"password".equals(s)) {
            return;
        }
        try {
            URL url;
            try {
                url = new URL(authenticationContext.getRepository().getUrl());
            }
            catch (Exception ex) {
                url = null;
            }
            final PasswordAuthentication requestPasswordAuthentication = Authenticator.requestPasswordAuthentication(proxy.getHost(), null, proxy.getPort(), "http", "Credentials for proxy " + proxy, null, url, Authenticator.RequestorType.PROXY);
            if (requestPasswordAuthentication != null) {
                authenticationContext.put("username", requestPasswordAuthentication.getUserName());
                authenticationContext.put("password", requestPasswordAuthentication.getPassword());
            }
            else {
                authenticationContext.put("username", System.getProperty("http.proxyUser"));
                authenticationContext.put("password", System.getProperty("http.proxyPassword"));
            }
        }
        catch (SecurityException ex2) {}
    }
    
    @Override
    public void digest(final AuthenticationDigest authenticationDigest) {
        authenticationDigest.update(UUID.randomUUID().toString());
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o != null && this.getClass().equals(o.getClass()));
    }
    
    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
    
    static {
        INSTANCE = new JreProxyAuthentication();
    }
}
