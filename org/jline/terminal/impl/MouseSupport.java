package org.jline.terminal.impl;

import java.io.EOFException;
import java.io.IOError;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.EnumSet;
import java.util.function.IntSupplier;
import org.jline.terminal.MouseEvent;
import org.jline.terminal.MouseEvent$Button;
import org.jline.terminal.MouseEvent$Modifier;
import org.jline.terminal.MouseEvent$Type;
import org.jline.terminal.Terminal;
import org.jline.terminal.Terminal$MouseTracking;
import org.jline.utils.InfoCmp$Capability;
import org.jline.utils.InputStreamReader;

public class MouseSupport {
   public MouseSupport() {
      super();
   }

   public static boolean hasMouseSupport(Terminal var0) {
      return var0.getStringCapability(InfoCmp$Capability.key_mouse) != null;
   }

   public static boolean trackMouse(Terminal var0, Terminal$MouseTracking var1) {
      // $FF: Couldn't be decompiled
   }

   public static MouseEvent readMouse(Terminal var0, MouseEvent var1) {
      return readMouse(MouseSupport::lambda$readMouse$0, var1);
   }

   public static MouseEvent readMouse(IntSupplier var0, MouseEvent var1) {
      int var2 = var0.getAsInt() - 32;
      int var3 = var0.getAsInt() - 32 - 1;
      int var4 = var0.getAsInt() - 32 - 1;
      EnumSet var7 = EnumSet.noneOf(MouseEvent$Modifier.class);
      if ((var2 & 4) == 4) {
         var7.add(MouseEvent$Modifier.Shift);
      }

      if ((var2 & 8) == 8) {
         var7.add(MouseEvent$Modifier.Alt);
      }

      if ((var2 & 16) == 16) {
         var7.add(MouseEvent$Modifier.Control);
      }

      MouseEvent$Type var5;
      MouseEvent$Button var6;
      if ((var2 & 64) == 64) {
         var5 = MouseEvent$Type.Wheel;
         var6 = (var2 & 1) == 1 ? MouseEvent$Button.WheelDown : MouseEvent$Button.WheelUp;
      } else {
         int var8 = var2 & 3;
         switch(var8) {
         case 0:
            var6 = MouseEvent$Button.Button1;
            if (var1.getButton() != var6 || var1.getType() != MouseEvent$Type.Pressed && var1.getType() != MouseEvent$Type.Dragged) {
               var5 = MouseEvent$Type.Pressed;
            } else {
               var5 = MouseEvent$Type.Dragged;
            }
            break;
         case 1:
            var6 = MouseEvent$Button.Button2;
            if (var1.getButton() != var6 || var1.getType() != MouseEvent$Type.Pressed && var1.getType() != MouseEvent$Type.Dragged) {
               var5 = MouseEvent$Type.Pressed;
            } else {
               var5 = MouseEvent$Type.Dragged;
            }
            break;
         case 2:
            var6 = MouseEvent$Button.Button3;
            if (var1.getButton() != var6 || var1.getType() != MouseEvent$Type.Pressed && var1.getType() != MouseEvent$Type.Dragged) {
               var5 = MouseEvent$Type.Pressed;
            } else {
               var5 = MouseEvent$Type.Dragged;
            }
            break;
         default:
            if (var1.getType() != MouseEvent$Type.Pressed && var1.getType() != MouseEvent$Type.Dragged) {
               var6 = MouseEvent$Button.NoButton;
               var5 = MouseEvent$Type.Moved;
            } else {
               var6 = var1.getButton();
               var5 = MouseEvent$Type.Released;
            }
         }
      }

      return new MouseEvent(var5, var6, var7, var3, var4);
   }

   private static int readExt(Terminal var0) {
      int var10000;
      try {
         int var1;
         if (var0.encoding() != StandardCharsets.UTF_8) {
            var1 = (new InputStreamReader(var0.input(), StandardCharsets.UTF_8)).read();
         } else {
            var1 = var0.reader().read();
         }

         if (var1 < 0) {
            throw new EOFException();
         }

         var10000 = var1;
      } catch (IOException var2) {
         throw new IOError(var2);
      }

      return var10000;
   }

   private static int lambda$readMouse$0(Terminal var0) {
      return readExt(var0);
   }
}
