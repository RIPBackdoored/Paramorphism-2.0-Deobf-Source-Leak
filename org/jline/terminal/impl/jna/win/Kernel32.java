package org.jline.terminal.impl.jna.win;

import com.sun.jna.LastErrorException;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

interface Kernel32 extends StdCallLibrary {
   Kernel32 INSTANCE = (Kernel32)Native.loadLibrary("kernel32", Kernel32.class, W32APIOptions.UNICODE_OPTIONS);
   Pointer INVALID_HANDLE_VALUE = Pointer.createConstant(-1L);
   int STD_INPUT_HANDLE = -10;
   int STD_OUTPUT_HANDLE = -11;
   int STD_ERROR_HANDLE = -12;
   int ENABLE_PROCESSED_INPUT = 1;
   int ENABLE_LINE_INPUT = 2;
   int ENABLE_ECHO_INPUT = 4;
   int ENABLE_WINDOW_INPUT = 8;
   int ENABLE_MOUSE_INPUT = 16;
   int ENABLE_INSERT_MODE = 32;
   int ENABLE_QUICK_EDIT_MODE = 64;
   int ENABLE_EXTENDED_FLAGS = 128;
   int RIGHT_ALT_PRESSED = 1;
   int LEFT_ALT_PRESSED = 2;
   int RIGHT_CTRL_PRESSED = 4;
   int LEFT_CTRL_PRESSED = 8;
   int SHIFT_PRESSED = 16;
   int FOREGROUND_BLUE = 1;
   int FOREGROUND_GREEN = 2;
   int FOREGROUND_RED = 4;
   int FOREGROUND_INTENSITY = 8;
   int BACKGROUND_BLUE = 16;
   int BACKGROUND_GREEN = 32;
   int BACKGROUND_RED = 64;
   int BACKGROUND_INTENSITY = 128;
   int FROM_LEFT_1ST_BUTTON_PRESSED = 1;
   int RIGHTMOST_BUTTON_PRESSED = 2;
   int FROM_LEFT_2ND_BUTTON_PRESSED = 4;
   int FROM_LEFT_3RD_BUTTON_PRESSED = 8;
   int FROM_LEFT_4TH_BUTTON_PRESSED = 16;
   int MOUSE_MOVED = 1;
   int DOUBLE_CLICK = 2;
   int MOUSE_WHEELED = 4;
   int MOUSE_HWHEELED = 8;

   int WaitForSingleObject(Pointer var1, int var2);

   Pointer GetStdHandle(int var1);

   void AllocConsole() throws LastErrorException;

   void FreeConsole() throws LastErrorException;

   Pointer GetConsoleWindow();

   int GetConsoleCP();

   int GetConsoleOutputCP();

   void FillConsoleOutputCharacter(Pointer var1, char var2, int var3, Kernel32$COORD var4, IntByReference var5) throws LastErrorException;

   void FillConsoleOutputAttribute(Pointer var1, short var2, int var3, Kernel32$COORD var4, IntByReference var5) throws LastErrorException;

   void GetConsoleCursorInfo(Pointer var1, Kernel32$CONSOLE_CURSOR_INFO$ByReference var2) throws LastErrorException;

   void GetConsoleMode(Pointer var1, IntByReference var2) throws LastErrorException;

   void GetConsoleScreenBufferInfo(Pointer var1, Kernel32$CONSOLE_SCREEN_BUFFER_INFO var2) throws LastErrorException;

   void GetNumberOfConsoleInputEvents(Pointer var1, IntByReference var2) throws LastErrorException;

   void ReadConsoleInput(Pointer var1, Kernel32$INPUT_RECORD[] var2, int var3, IntByReference var4) throws LastErrorException;

   void SetConsoleCtrlHandler(Pointer var1, boolean var2) throws LastErrorException;

   void ReadConsoleOutput(Pointer var1, Kernel32$CHAR_INFO[] var2, Kernel32$COORD var3, Kernel32$COORD var4, Kernel32$SMALL_RECT var5) throws LastErrorException;

   void ReadConsoleOutputA(Pointer var1, Kernel32$CHAR_INFO[] var2, Kernel32$COORD var3, Kernel32$COORD var4, Kernel32$SMALL_RECT var5) throws LastErrorException;

   void ReadConsoleOutputCharacter(Pointer var1, char[] var2, int var3, Kernel32$COORD var4, IntByReference var5) throws LastErrorException;

   void ReadConsoleOutputCharacterA(Pointer var1, byte[] var2, int var3, Kernel32$COORD var4, IntByReference var5) throws LastErrorException;

   void SetConsoleCursorInfo(Pointer var1, Kernel32$CONSOLE_CURSOR_INFO var2) throws LastErrorException;

   void SetConsoleCP(int var1) throws LastErrorException;

   void SetConsoleOutputCP(int var1) throws LastErrorException;

   void SetConsoleCursorPosition(Pointer var1, Kernel32$COORD var2) throws LastErrorException;

   void SetConsoleMode(Pointer var1, int var2) throws LastErrorException;

   void SetConsoleScreenBufferSize(Pointer var1, Kernel32$COORD var2) throws LastErrorException;

   void SetConsoleTextAttribute(Pointer var1, short var2) throws LastErrorException;

   void SetConsoleTitle(String var1) throws LastErrorException;

   void SetConsoleWindowInfo(Pointer var1, boolean var2, Kernel32$SMALL_RECT var3) throws LastErrorException;

   void WriteConsoleW(Pointer var1, char[] var2, int var3, IntByReference var4, Pointer var5) throws LastErrorException;

   void WriteConsoleOutput(Pointer var1, Kernel32$CHAR_INFO[] var2, Kernel32$COORD var3, Kernel32$COORD var4, Kernel32$SMALL_RECT var5) throws LastErrorException;

   void WriteConsoleOutputA(Pointer var1, Kernel32$CHAR_INFO[] var2, Kernel32$COORD var3, Kernel32$COORD var4, Kernel32$SMALL_RECT var5) throws LastErrorException;

   void WriteConsoleOutputCharacter(Pointer var1, char[] var2, int var3, Kernel32$COORD var4, IntByReference var5) throws LastErrorException;

   void WriteConsoleOutputCharacterA(Pointer var1, byte[] var2, int var3, Kernel32$COORD var4, IntByReference var5) throws LastErrorException;

   void ScrollConsoleScreenBuffer(Pointer var1, Kernel32$SMALL_RECT var2, Kernel32$SMALL_RECT var3, Kernel32$COORD var4, Kernel32$CHAR_INFO var5) throws LastErrorException;
}
