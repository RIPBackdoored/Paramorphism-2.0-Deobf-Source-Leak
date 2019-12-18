package org.jline.builtins.telnet;

import java.net.*;

public interface ConnectionFilter
{
    boolean isAllowed(final InetAddress p0);
}
