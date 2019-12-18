package org.jline.terminal.impl.jna.win;

import com.sun.jna.*;
import java.util.*;

public static class CONSOLE_CURSOR_INFO extends Structure
{
    public int dwSize;
    public boolean bVisible;
    private static String[] fieldOrder;
    
    public CONSOLE_CURSOR_INFO() {
        super();
    }
    
    protected List<String> getFieldOrder() {
        return Arrays.asList(CONSOLE_CURSOR_INFO.fieldOrder);
    }
    
    static {
        CONSOLE_CURSOR_INFO.fieldOrder = new String[] { "dwSize", "bVisible" };
    }
    
    public static class ByReference extends CONSOLE_CURSOR_INFO implements Structure.ByReference
    {
        public ByReference() {
            super();
        }
    }
}
