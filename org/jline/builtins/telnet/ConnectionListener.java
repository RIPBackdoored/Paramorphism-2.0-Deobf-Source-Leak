package org.jline.builtins.telnet;

public interface ConnectionListener
{
    default void connectionIdle(final ConnectionEvent connectionEvent) {
    }
    
    default void connectionTimedOut(final ConnectionEvent connectionEvent) {
    }
    
    default void connectionLogoutRequest(final ConnectionEvent connectionEvent) {
    }
    
    default void connectionSentBreak(final ConnectionEvent connectionEvent) {
    }
    
    default void connectionTerminalGeometryChanged(final ConnectionEvent connectionEvent) {
    }
}
