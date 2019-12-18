package org.jline.terminal.impl.jansi.win;

import java.io.BufferedWriter;
import java.io.IOError;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.function.IntConsumer;
import org.fusesource.jansi.internal.Kernel32;
import org.fusesource.jansi.internal.WindowsSupport;
import org.fusesource.jansi.internal.Kernel32.CONSOLE_SCREEN_BUFFER_INFO;
import org.fusesource.jansi.internal.Kernel32.INPUT_RECORD;
import org.fusesource.jansi.internal.Kernel32.KEY_EVENT_RECORD;
import org.fusesource.jansi.internal.Kernel32.MOUSE_EVENT_RECORD;
import org.jline.terminal.Cursor;
import org.jline.terminal.Size;
import org.jline.terminal.Terminal$MouseTracking;
import org.jline.terminal.Terminal$Signal;
import org.jline.terminal.Terminal$SignalHandler;
import org.jline.terminal.impl.AbstractWindowsTerminal;
import org.jline.terminal.impl.jansi.JansiSupportImpl;
import org.jline.utils.InfoCmp$Capability;
import org.jline.utils.OSUtils;

public class JansiWinSysTerminal extends AbstractWindowsTerminal {
   private char[] focus = new char[]{'\u001b', '[', ' '};
   private char[] mouse = new char[]{'\u001b', '[', 'M', ' ', ' ', ' '};

   public static JansiWinSysTerminal createTerminal(String var0, String var1, boolean var2, Charset var3, int var4, boolean var5, Terminal$SignalHandler var6, boolean var7) throws IOException {
      Object var8;
      if (var2) {
         if (var1 == null) {
            var1 = OSUtils.IS_CONEMU ? "windows-conemu" : "windows";
         }

         var8 = new JansiWinConsoleWriter();
      } else {
         long var9 = Kernel32.GetStdHandle(Kernel32.STD_OUTPUT_HANDLE);
         int[] var11 = new int[1];
         if (Kernel32.GetConsoleMode(var9, var11) == 0) {
            throw new IOException("Failed to get console mode: " + WindowsSupport.getLastErrorMessage());
         }

         if (Kernel32.SetConsoleMode(var9, var11[0] | 4) != 0) {
            if (var1 == null) {
               var1 = "windows-vtp";
            }

            var8 = new JansiWinConsoleWriter();
         } else if (OSUtils.IS_CONEMU) {
            if (var1 == null) {
               var1 = "windows-conemu";
            }

            var8 = new JansiWinConsoleWriter();
         } else {
            if (var1 == null) {
               var1 = "windows";
            }

            var8 = new WindowsAnsiWriter(new BufferedWriter(new JansiWinConsoleWriter()));
         }
      }

      JansiWinSysTerminal var12 = new JansiWinSysTerminal((Writer)var8, var0, var1, var3, var4, var5, var6);
      if (!var7) {
         var12.resume();
      }

      return var12;
   }

   JansiWinSysTerminal(Writer var1, String var2, String var3, Charset var4, int var5, boolean var6, Terminal$SignalHandler var7) throws IOException {
      super(var1, var2, var3, var4, var5, var6, var7);
   }

   protected int getConsoleOutputCP() {
      return Kernel32.GetConsoleOutputCP();
   }

   protected int getConsoleMode() {
      return WindowsSupport.getConsoleMode();
   }

   protected void setConsoleMode(int var1) {
      WindowsSupport.setConsoleMode(var1);
   }

   public Size getSize() {
      long var1 = Kernel32.GetStdHandle(Kernel32.STD_OUTPUT_HANDLE);
      CONSOLE_SCREEN_BUFFER_INFO var3 = new CONSOLE_SCREEN_BUFFER_INFO();
      Kernel32.GetConsoleScreenBufferInfo(var1, var3);
      return new Size(var3.windowWidth(), var3.windowHeight());
   }

   public Size getBufferSize() {
      long var1 = Kernel32.GetStdHandle(Kernel32.STD_OUTPUT_HANDLE);
      CONSOLE_SCREEN_BUFFER_INFO var3 = new CONSOLE_SCREEN_BUFFER_INFO();
      Kernel32.GetConsoleScreenBufferInfo(var1, var3);
      return new Size(var3.size.x, var3.size.y);
   }

   protected boolean processConsoleInput() throws IOException {
      INPUT_RECORD[] var1;
      if (JansiSupportImpl.isAtLeast(1, 17)) {
         var1 = WindowsSupport.readConsoleInput(1, 100);
      } else {
         var1 = WindowsSupport.readConsoleInput(1);
      }

      if (var1 == null) {
         return false;
      } else {
         boolean var2 = false;
         INPUT_RECORD[] var3 = var1;
         int var4 = var1.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            INPUT_RECORD var6 = var3[var5];
            if (var6.eventType == INPUT_RECORD.KEY_EVENT) {
               KEY_EVENT_RECORD var7 = var6.keyEvent;
               this.processKeyEvent(var7.keyDown, var7.keyCode, var7.uchar, var7.controlKeyState);
               var2 = true;
            } else if (var6.eventType == INPUT_RECORD.WINDOW_BUFFER_SIZE_EVENT) {
               this.raise(Terminal$Signal.WINCH);
            } else if (var6.eventType == INPUT_RECORD.MOUSE_EVENT) {
               this.processMouseEvent(var6.mouseEvent);
               var2 = true;
            } else if (var6.eventType == INPUT_RECORD.FOCUS_EVENT) {
               this.processFocusEvent(var6.focusEvent.setFocus);
            }
         }

         return var2;
      }
   }

   private void processFocusEvent(boolean var1) throws IOException {
      if (this.focusTracking) {
         this.focus[2] = (char)(var1 ? 73 : 79);
         this.slaveInputPipe.write(this.focus);
      }

   }

   private void processMouseEvent(MOUSE_EVENT_RECORD var1) throws IOException {
      int var2 = var1.eventFlags;
      int var3 = var1.buttonState;
      if (this.tracking != Terminal$MouseTracking.Off && (this.tracking != Terminal$MouseTracking.Normal || var2 != MOUSE_EVENT_RECORD.MOUSE_MOVED) && (this.tracking != Terminal$MouseTracking.Button || var2 != MOUSE_EVENT_RECORD.MOUSE_MOVED || var3 != 0)) {
         byte var4 = 0;
         var2 &= ~MOUSE_EVENT_RECORD.DOUBLE_CLICK;
         int var7;
         if (var2 == MOUSE_EVENT_RECORD.MOUSE_WHEELED) {
            var7 = var4 | 64;
            if (var3 >> 16 < 0) {
               var7 |= 1;
            }
         } else {
            if (var2 == MOUSE_EVENT_RECORD.MOUSE_HWHEELED) {
               return;
            }

            if ((var3 & MOUSE_EVENT_RECORD.FROM_LEFT_1ST_BUTTON_PRESSED) != 0) {
               var7 = var4 | 0;
            } else if ((var3 & MOUSE_EVENT_RECORD.RIGHTMOST_BUTTON_PRESSED) != 0) {
               var7 = var4 | 1;
            } else if ((var3 & MOUSE_EVENT_RECORD.FROM_LEFT_2ND_BUTTON_PRESSED) != 0) {
               var7 = var4 | 2;
            } else {
               var7 = var4 | 3;
            }
         }

         short var5 = var1.mousePosition.x;
         short var6 = var1.mousePosition.y;
         this.mouse[3] = (char)(32 + var7);
         this.mouse[4] = (char)(32 + var5 + 1);
         this.mouse[5] = (char)(32 + var6 + 1);
         this.slaveInputPipe.write(this.mouse);
      }
   }

   public Cursor getCursorPosition(IntConsumer var1) {
      CONSOLE_SCREEN_BUFFER_INFO var2 = new CONSOLE_SCREEN_BUFFER_INFO();
      long var3 = Kernel32.GetStdHandle(Kernel32.STD_OUTPUT_HANDLE);
      if (Kernel32.GetConsoleScreenBufferInfo(var3, var2) == 0) {
         throw new IOError(new IOException("Could not get the cursor position: " + WindowsSupport.getLastErrorMessage()));
      } else {
         return new Cursor(var2.cursorPosition.x, var2.cursorPosition.y);
      }
   }

   public void disableScrolling() {
      this.strings.remove(InfoCmp$Capability.insert_line);
      this.strings.remove(InfoCmp$Capability.parm_insert_line);
      this.strings.remove(InfoCmp$Capability.delete_line);
      this.strings.remove(InfoCmp$Capability.parm_delete_line);
   }
}
