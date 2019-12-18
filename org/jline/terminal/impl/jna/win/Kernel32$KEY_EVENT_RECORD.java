package org.jline.terminal.impl.jna.win;

import com.sun.jna.*;
import java.util.*;

public static class KEY_EVENT_RECORD extends Structure
{
    public boolean bKeyDown;
    public short wRepeatCount;
    public short wVirtualKeyCode;
    public short wVirtualScanCode;
    public UnionChar uChar;
    public int dwControlKeyState;
    private static String[] fieldOrder;
    
    public KEY_EVENT_RECORD() {
        super();
    }
    
    protected List<String> getFieldOrder() {
        return Arrays.asList(KEY_EVENT_RECORD.fieldOrder);
    }
    
    static {
        KEY_EVENT_RECORD.fieldOrder = new String[] { "bKeyDown", "wRepeatCount", "wVirtualKeyCode", "wVirtualScanCode", "uChar", "dwControlKeyState" };
    }
}
