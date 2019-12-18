package org.jline.terminal.impl.jna.win;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import java.io.IOException;
import java.io.Writer;
import org.jline.utils.AnsiWriter;
import org.jline.utils.Colors;

public final class WindowsAnsiWriter extends AnsiWriter {
   private static final short FOREGROUND_BLACK = 0;
   private static final short FOREGROUND_YELLOW = 6;
   private static final short FOREGROUND_MAGENTA = 5;
   private static final short FOREGROUND_CYAN = 3;
   private static final short FOREGROUND_WHITE = 7;
   private static final short BACKGROUND_BLACK = 0;
   private static final short BACKGROUND_YELLOW = 96;
   private static final short BACKGROUND_MAGENTA = 80;
   private static final short BACKGROUND_CYAN = 48;
   private static final short BACKGROUND_WHITE = 112;
   private static final short[] ANSI_FOREGROUND_COLOR_MAP = new short[]{0, 4, 2, 6, 1, 5, 3, 7};
   private static final short[] ANSI_BACKGROUND_COLOR_MAP = new short[]{0, 64, 32, 96, 16, 80, 48, 112};
   private static final int MAX_ESCAPE_SEQUENCE_LENGTH = 100;
   private final Pointer console;
   private final Kernel32$CONSOLE_SCREEN_BUFFER_INFO info = new Kernel32$CONSOLE_SCREEN_BUFFER_INFO();
   private final short originalColors;
   private boolean negative;
   private boolean bold;
   private boolean underline;
   private short savedX = -1;
   private short savedY = -1;

   public WindowsAnsiWriter(Writer var1, Pointer var2) throws IOException {
      super(var1);
      this.console = var2;
      this.getConsoleInfo();
      this.originalColors = this.info.wAttributes;
   }

   private void getConsoleInfo() throws IOException {
      this.out.flush();
      Kernel32.INSTANCE.GetConsoleScreenBufferInfo(this.console, this.info);
      if (this.negative) {
         this.info.wAttributes = this.invertAttributeColors(this.info.wAttributes);
      }

   }

   private void applyAttribute() throws IOException {
      this.out.flush();
      short var1 = this.info.wAttributes;
      if (this.bold) {
         var1 = (short)(var1 | 8);
      }

      if (this.underline) {
         var1 = (short)(var1 | 128);
      }

      if (this.negative) {
         var1 = this.invertAttributeColors(var1);
      }

      Kernel32.INSTANCE.SetConsoleTextAttribute(this.console, var1);
   }

   private short invertAttributeColors(short var1) {
      int var2 = 15 & var1;
      var2 <<= 4;
      int var3 = 240 & var1;
      var3 >>= 4;
      var1 = (short)(var1 & '\uff00' | var2 | var3);
      return var1;
   }

   private void applyCursorPosition() throws IOException {
      this.info.dwCursorPosition.X = (short)Math.max(0, Math.min(this.info.dwSize.X - 1, this.info.dwCursorPosition.X));
      this.info.dwCursorPosition.Y = (short)Math.max(0, Math.min(this.info.dwSize.Y - 1, this.info.dwCursorPosition.Y));
      Kernel32.INSTANCE.SetConsoleCursorPosition(this.console, this.info.dwCursorPosition);
   }

   protected void processEraseScreen(int var1) throws IOException {
      this.getConsoleInfo();
      IntByReference var2 = new IntByReference();
      switch(var1) {
      case 0:
         int var7 = (this.info.srWindow.Bottom - this.info.dwCursorPosition.Y) * this.info.dwSize.X + (this.info.dwSize.X - this.info.dwCursorPosition.X);
         Kernel32.INSTANCE.FillConsoleOutputCharacter(this.console, ' ', var7, this.info.dwCursorPosition, var2);
         Kernel32.INSTANCE.FillConsoleOutputAttribute(this.console, this.info.wAttributes, var7, this.info.dwCursorPosition, var2);
         break;
      case 1:
         Kernel32$COORD var5 = new Kernel32$COORD();
         var5.X = 0;
         var5.Y = this.info.srWindow.Top;
         int var6 = (this.info.dwCursorPosition.Y - this.info.srWindow.Top) * this.info.dwSize.X + this.info.dwCursorPosition.X;
         Kernel32.INSTANCE.FillConsoleOutputCharacter(this.console, ' ', var6, var5, var2);
         Kernel32.INSTANCE.FillConsoleOutputAttribute(this.console, this.info.wAttributes, var6, var5, var2);
         break;
      case 2:
         Kernel32$COORD var3 = new Kernel32$COORD();
         var3.X = 0;
         var3.Y = this.info.srWindow.Top;
         int var4 = this.info.srWindow.height() * this.info.dwSize.X;
         Kernel32.INSTANCE.FillConsoleOutputCharacter(this.console, ' ', var4, var3, var2);
         Kernel32.INSTANCE.FillConsoleOutputAttribute(this.console, this.info.wAttributes, var4, var3, var2);
      }

   }

   protected void processEraseLine(int var1) throws IOException {
      this.getConsoleInfo();
      IntByReference var2 = new IntByReference();
      switch(var1) {
      case 0:
         int var5 = this.info.dwSize.X - this.info.dwCursorPosition.X;
         Kernel32.INSTANCE.FillConsoleOutputCharacter(this.console, ' ', var5, this.info.dwCursorPosition, var2);
         Kernel32.INSTANCE.FillConsoleOutputAttribute(this.console, this.info.wAttributes, var5, this.info.dwCursorPosition, var2);
         break;
      case 1:
         Kernel32$COORD var4 = new Kernel32$COORD((short)0, this.info.dwCursorPosition.Y);
         Kernel32.INSTANCE.FillConsoleOutputCharacter(this.console, ' ', this.info.dwCursorPosition.X, var4, var2);
         Kernel32.INSTANCE.FillConsoleOutputAttribute(this.console, this.info.wAttributes, this.info.dwCursorPosition.X, var4, var2);
         break;
      case 2:
         Kernel32$COORD var3 = new Kernel32$COORD((short)0, this.info.dwCursorPosition.Y);
         Kernel32.INSTANCE.FillConsoleOutputCharacter(this.console, ' ', this.info.dwSize.X, var3, var2);
         Kernel32.INSTANCE.FillConsoleOutputAttribute(this.console, this.info.wAttributes, this.info.dwSize.X, var3, var2);
      }

   }

   protected void processCursorUpLine(int var1) throws IOException {
      this.getConsoleInfo();
      this.info.dwCursorPosition.X = 0;
      Kernel32$COORD var10000 = this.info.dwCursorPosition;
      var10000.Y = (short)(var10000.Y - var1);
      this.applyCursorPosition();
   }

   protected void processCursorDownLine(int var1) throws IOException {
      this.getConsoleInfo();
      this.info.dwCursorPosition.X = 0;
      Kernel32$COORD var10000 = this.info.dwCursorPosition;
      var10000.Y = (short)(var10000.Y + var1);
      this.applyCursorPosition();
   }

   protected void processCursorLeft(int var1) throws IOException {
      this.getConsoleInfo();
      Kernel32$COORD var10000 = this.info.dwCursorPosition;
      var10000.X = (short)(var10000.X - var1);
      this.applyCursorPosition();
   }

   protected void processCursorRight(int var1) throws IOException {
      this.getConsoleInfo();
      Kernel32$COORD var10000 = this.info.dwCursorPosition;
      var10000.X = (short)(var10000.X + var1);
      this.applyCursorPosition();
   }

   protected void processCursorDown(int var1) throws IOException {
      this.getConsoleInfo();
      int var2 = Math.max(0, this.info.dwCursorPosition.Y + var1 - this.info.dwSize.Y + 1);
      if (var2 != var1) {
         Kernel32$COORD var10000 = this.info.dwCursorPosition;
         var10000.Y = (short)(var10000.Y + var1);
         this.applyCursorPosition();
      }

      if (var2 > 0) {
         Kernel32$SMALL_RECT var3 = new Kernel32$SMALL_RECT(this.info.srWindow);
         var3.Top = 0;
         Kernel32$COORD var4 = new Kernel32$COORD();
         var4.X = 0;
         var4.Y = (short)(-var2);
         Kernel32$CHAR_INFO var5 = new Kernel32$CHAR_INFO(' ', this.originalColors);
         Kernel32.INSTANCE.ScrollConsoleScreenBuffer(this.console, var3, var3, var4, var5);
      }

   }

   protected void processCursorUp(int var1) throws IOException {
      this.getConsoleInfo();
      Kernel32$COORD var10000 = this.info.dwCursorPosition;
      var10000.Y = (short)(var10000.Y - var1);
      this.applyCursorPosition();
   }

   protected void processCursorTo(int var1, int var2) throws IOException {
      this.getConsoleInfo();
      this.info.dwCursorPosition.Y = (short)(this.info.srWindow.Top + var1 - 1);
      this.info.dwCursorPosition.X = (short)(var2 - 1);
      this.applyCursorPosition();
   }

   protected void processCursorToColumn(int var1) throws IOException {
      this.getConsoleInfo();
      this.info.dwCursorPosition.X = (short)(var1 - 1);
      this.applyCursorPosition();
   }

   protected void processSetForegroundColorExt(int var1) throws IOException {
      int var2 = Colors.roundColor(var1, 16);
      this.info.wAttributes = (short)(this.info.wAttributes & -8 | ANSI_FOREGROUND_COLOR_MAP[var2 & 7]);
      this.info.wAttributes = (short)(this.info.wAttributes & -9 | (var2 >= 8 ? 8 : 0));
      this.applyAttribute();
   }

   protected void processSetBackgroundColorExt(int var1) throws IOException {
      int var2 = Colors.roundColor(var1, 16);
      this.info.wAttributes = (short)(this.info.wAttributes & -113 | ANSI_BACKGROUND_COLOR_MAP[var2 & 7]);
      this.info.wAttributes = (short)(this.info.wAttributes & -129 | (var2 >= 8 ? 128 : 0));
      this.applyAttribute();
   }

   protected void processDefaultTextColor() throws IOException {
      this.info.wAttributes = (short)(this.info.wAttributes & -16 | this.originalColors & 15);
      this.applyAttribute();
   }

   protected void processDefaultBackgroundColor() throws IOException {
      this.info.wAttributes = (short)(this.info.wAttributes & -241 | this.originalColors & 240);
      this.applyAttribute();
   }

   protected void processAttributeRest() throws IOException {
      this.info.wAttributes = (short)(this.info.wAttributes & -256 | this.originalColors);
      this.negative = false;
      this.bold = false;
      this.underline = false;
      this.applyAttribute();
   }

   protected void processSetAttribute(int var1) throws IOException {
      switch(var1) {
      case 1:
         this.bold = true;
         this.applyAttribute();
         break;
      case 4:
         this.underline = true;
         this.applyAttribute();
         break;
      case 7:
         this.negative = true;
         this.applyAttribute();
         break;
      case 22:
         this.bold = false;
         this.applyAttribute();
         break;
      case 24:
         this.underline = false;
         this.applyAttribute();
         break;
      case 27:
         this.negative = false;
         this.applyAttribute();
      }

   }

   protected void processSaveCursorPosition() throws IOException {
      this.getConsoleInfo();
      this.savedX = this.info.dwCursorPosition.X;
      this.savedY = this.info.dwCursorPosition.Y;
   }

   protected void processRestoreCursorPosition() throws IOException {
      if (this.savedX != -1 && this.savedY != -1) {
         this.out.flush();
         this.info.dwCursorPosition.X = this.savedX;
         this.info.dwCursorPosition.Y = this.savedY;
         this.applyCursorPosition();
      }

   }

   protected void processInsertLine(int var1) throws IOException {
      this.getConsoleInfo();
      Kernel32$SMALL_RECT var2 = new Kernel32$SMALL_RECT(this.info.srWindow);
      var2.Top = this.info.dwCursorPosition.Y;
      Kernel32$COORD var3 = new Kernel32$COORD();
      var3.X = 0;
      var3.Y = (short)(this.info.dwCursorPosition.Y + var1);
      Kernel32$CHAR_INFO var4 = new Kernel32$CHAR_INFO(' ', this.originalColors);
      Kernel32.INSTANCE.ScrollConsoleScreenBuffer(this.console, var2, var2, var3, var4);
   }

   protected void processDeleteLine(int var1) throws IOException {
      this.getConsoleInfo();
      Kernel32$SMALL_RECT var2 = new Kernel32$SMALL_RECT(this.info.srWindow);
      var2.Top = this.info.dwCursorPosition.Y;
      Kernel32$COORD var3 = new Kernel32$COORD();
      var3.X = 0;
      var3.Y = (short)(this.info.dwCursorPosition.Y - var1);
      Kernel32$CHAR_INFO var4 = new Kernel32$CHAR_INFO(' ', this.originalColors);
      Kernel32.INSTANCE.ScrollConsoleScreenBuffer(this.console, var2, var2, var3, var4);
   }

   protected void processChangeWindowTitle(String var1) {
      Kernel32.INSTANCE.SetConsoleTitle(var1);
   }
}
