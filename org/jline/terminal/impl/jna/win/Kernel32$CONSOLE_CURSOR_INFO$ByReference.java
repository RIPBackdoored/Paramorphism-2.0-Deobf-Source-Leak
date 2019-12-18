package org.jline.terminal.impl.jna.win;

import com.sun.jna.*;

public static class ByReference extends CONSOLE_CURSOR_INFO implements Structure.ByReference
{
    public ByReference() {
        super();
    }
}
