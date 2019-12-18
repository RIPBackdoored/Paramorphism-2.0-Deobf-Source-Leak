package org.jline.terminal.impl.jna.solaris;

import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
import org.jline.terminal.Size;

public class CLibrary$winsize extends Structure {
   public short ws_row;
   public short ws_col;
   public short ws_xpixel;
   public short ws_ypixel;

   public CLibrary$winsize() {
      super();
   }

   public CLibrary$winsize(Size var1) {
      super();
      this.ws_row = (short)var1.getRows();
      this.ws_col = (short)var1.getColumns();
   }

   public Size toSize() {
      return new Size(this.ws_col, this.ws_row);
   }

   protected List getFieldOrder() {
      return Arrays.asList("ws_row", "ws_col", "ws_xpixel", "ws_ypixel");
   }
}
