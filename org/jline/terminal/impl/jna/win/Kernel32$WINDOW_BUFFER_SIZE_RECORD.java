package org.jline.terminal.impl.jna.win;

import com.sun.jna.*;
import java.util.*;

public static class WINDOW_BUFFER_SIZE_RECORD extends Structure
{
    public COORD dwSize;
    private static String[] fieldOrder;
    
    public WINDOW_BUFFER_SIZE_RECORD() {
        super();
    }
    
    protected List<String> getFieldOrder() {
        return Arrays.asList(WINDOW_BUFFER_SIZE_RECORD.fieldOrder);
    }
    
    static {
        WINDOW_BUFFER_SIZE_RECORD.fieldOrder = new String[] { "dwSize" };
    }
}
