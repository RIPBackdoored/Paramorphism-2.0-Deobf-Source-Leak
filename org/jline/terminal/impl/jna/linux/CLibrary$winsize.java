package org.jline.terminal.impl.jna.linux;

import com.sun.jna.*;
import org.jline.terminal.*;
import java.util.*;

public static class winsize extends Structure
{
    public short ws_row;
    public short ws_col;
    public short ws_xpixel;
    public short ws_ypixel;
    
    public winsize() {
        super();
    }
    
    public winsize(final Size size) {
        super();
        this.ws_row = (short)size.getRows();
        this.ws_col = (short)size.getColumns();
    }
    
    public Size toSize() {
        return new Size(this.ws_col, this.ws_row);
    }
    
    protected List<String> getFieldOrder() {
        return Arrays.asList("ws_row", "ws_col", "ws_xpixel", "ws_ypixel");
    }
}
