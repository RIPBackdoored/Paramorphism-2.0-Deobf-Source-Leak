package org.jline.terminal.impl.jna.win;

import org.jline.terminal.impl.*;
import com.sun.jna.ptr.*;
import java.nio.charset.*;
import com.sun.jna.*;
import java.io.*;
import org.jline.utils.*;
import java.util.function.*;
import org.jline.terminal.*;

public class JnaWinSysTerminal extends AbstractWindowsTerminal
{
    private static final Pointer consoleIn;
    private static final Pointer consoleOut;
    private char[] focus;
    private char[] mouse;
    private final Kernel32.INPUT_RECORD[] inputEvents;
    private final IntByReference eventsRead;
    
    public static JnaWinSysTerminal createTerminal(final String s, String s2, final boolean b, final Charset charset, final int n, final boolean b2, final Terminal.SignalHandler signalHandler, final boolean b3) throws IOException {
        Writer writer;
        if (b) {
            if (s2 == null) {
                s2 = (OSUtils.IS_CONEMU ? "windows-conemu" : "windows");
            }
            writer = new JnaWinConsoleWriter(JnaWinSysTerminal.consoleOut);
        }
        else {
            final IntByReference intByReference = new IntByReference();
            Kernel32.INSTANCE.GetConsoleMode(JnaWinSysTerminal.consoleOut, intByReference);
            try {
                Kernel32.INSTANCE.SetConsoleMode(JnaWinSysTerminal.consoleOut, intByReference.getValue() | 0x4);
                if (s2 == null) {
                    s2 = "windows-vtp";
                }
                writer = new JnaWinConsoleWriter(JnaWinSysTerminal.consoleOut);
            }
            catch (LastErrorException ex) {
                if (OSUtils.IS_CONEMU) {
                    if (s2 == null) {
                        s2 = "windows-conemu";
                    }
                    writer = new JnaWinConsoleWriter(JnaWinSysTerminal.consoleOut);
                }
                else {
                    if (s2 == null) {
                        s2 = "windows";
                    }
                    writer = new WindowsAnsiWriter(new BufferedWriter(new JnaWinConsoleWriter(JnaWinSysTerminal.consoleOut)), JnaWinSysTerminal.consoleOut);
                }
            }
        }
        final JnaWinSysTerminal jnaWinSysTerminal = new JnaWinSysTerminal(writer, s, s2, charset, n, b2, signalHandler);
        if (!b3) {
            jnaWinSysTerminal.resume();
        }
        return jnaWinSysTerminal;
    }
    
    JnaWinSysTerminal(final Writer writer, final String s, final String s2, final Charset charset, final int n, final boolean b, final Terminal.SignalHandler signalHandler) throws IOException {
        super(writer, s, s2, charset, n, b, signalHandler);
        this.focus = new char[] { '\u001b', '[', ' ' };
        this.mouse = new char[] { '\u001b', '[', 'M', ' ', ' ', ' ' };
        this.inputEvents = new Kernel32.INPUT_RECORD[1];
        this.eventsRead = new IntByReference();
        this.strings.put(InfoCmp.Capability.key_mouse, "\\E[M");
    }
    
    @Override
    protected int getConsoleOutputCP() {
        return Kernel32.INSTANCE.GetConsoleOutputCP();
    }
    
    @Override
    protected int getConsoleMode() {
        final IntByReference intByReference = new IntByReference();
        Kernel32.INSTANCE.GetConsoleMode(JnaWinSysTerminal.consoleIn, intByReference);
        return intByReference.getValue();
    }
    
    @Override
    protected void setConsoleMode(final int n) {
        Kernel32.INSTANCE.SetConsoleMode(JnaWinSysTerminal.consoleIn, n);
    }
    
    @Override
    public Size getSize() {
        final Kernel32.CONSOLE_SCREEN_BUFFER_INFO console_SCREEN_BUFFER_INFO = new Kernel32.CONSOLE_SCREEN_BUFFER_INFO();
        Kernel32.INSTANCE.GetConsoleScreenBufferInfo(JnaWinSysTerminal.consoleOut, console_SCREEN_BUFFER_INFO);
        return new Size(console_SCREEN_BUFFER_INFO.windowWidth(), console_SCREEN_BUFFER_INFO.windowHeight());
    }
    
    @Override
    public Size getBufferSize() {
        final Kernel32.CONSOLE_SCREEN_BUFFER_INFO console_SCREEN_BUFFER_INFO = new Kernel32.CONSOLE_SCREEN_BUFFER_INFO();
        Kernel32.INSTANCE.GetConsoleScreenBufferInfo(JnaWinSysTerminal.consoleOut, console_SCREEN_BUFFER_INFO);
        return new Size(console_SCREEN_BUFFER_INFO.dwSize.X, console_SCREEN_BUFFER_INFO.dwSize.Y);
    }
    
    @Override
    protected boolean processConsoleInput() throws IOException {
        final Kernel32.INPUT_RECORD consoleInput = this.readConsoleInput(100);
        if (consoleInput == null) {
            return false;
        }
        switch (consoleInput.EventType) {
            case 1: {
                this.processKeyEvent(consoleInput.Event.KeyEvent);
                return true;
            }
            case 4: {
                this.raise(Terminal.Signal.WINCH);
                return false;
            }
            case 2: {
                this.processMouseEvent(consoleInput.Event.MouseEvent);
                return true;
            }
            case 16: {
                this.processFocusEvent(consoleInput.Event.FocusEvent.bSetFocus);
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    private void processKeyEvent(final Kernel32.KEY_EVENT_RECORD key_EVENT_RECORD) throws IOException {
        this.processKeyEvent(key_EVENT_RECORD.bKeyDown, key_EVENT_RECORD.wVirtualKeyCode, key_EVENT_RECORD.uChar.UnicodeChar, key_EVENT_RECORD.dwControlKeyState);
    }
    
    private void processFocusEvent(final boolean b) throws IOException {
        if (this.focusTracking) {
            this.focus[2] = (b ? 'I' : 'O');
            this.slaveInputPipe.write(this.focus);
        }
    }
    
    private void processMouseEvent(final Kernel32.MOUSE_EVENT_RECORD mouse_EVENT_RECORD) throws IOException {
        final int dwEventFlags = mouse_EVENT_RECORD.dwEventFlags;
        final int dwButtonState = mouse_EVENT_RECORD.dwButtonState;
        if (this.tracking == Terminal.MouseTracking.Off || (this.tracking == Terminal.MouseTracking.Normal && dwEventFlags == 1) || (this.tracking == Terminal.MouseTracking.Button && dwEventFlags == 1 && dwButtonState == 0)) {
            return;
        }
        final int n = 0;
        final int n2 = dwEventFlags & 0xFFFFFFFD;
        int n3;
        if (n2 == 4) {
            n3 = (n | 0x40);
            if (dwButtonState >> 16 < 0) {
                n3 |= 0x1;
            }
        }
        else {
            if (n2 == 8) {
                return;
            }
            if ((dwButtonState & 0x1) != 0x0) {
                n3 = (n | 0x0);
            }
            else if ((dwButtonState & 0x2) != 0x0) {
                n3 = (n | 0x1);
            }
            else if ((dwButtonState & 0x4) != 0x0) {
                n3 = (n | 0x2);
            }
            else {
                n3 = (n | 0x3);
            }
        }
        final short x = mouse_EVENT_RECORD.dwMousePosition.X;
        final short y = mouse_EVENT_RECORD.dwMousePosition.Y;
        this.mouse[3] = (char)(32 + n3);
        this.mouse[4] = (char)(32 + x + 1);
        this.mouse[5] = (char)(32 + y + 1);
        this.slaveInputPipe.write(this.mouse);
    }
    
    private Kernel32.INPUT_RECORD readConsoleInput(final int n) throws IOException {
        if (Kernel32.INSTANCE.WaitForSingleObject(JnaWinSysTerminal.consoleIn, n) != 0) {
            return null;
        }
        Kernel32.INSTANCE.ReadConsoleInput(JnaWinSysTerminal.consoleIn, this.inputEvents, 1, this.eventsRead);
        if (this.eventsRead.getValue() == 1) {
            return this.inputEvents[0];
        }
        return null;
    }
    
    @Override
    public Cursor getCursorPosition(final IntConsumer intConsumer) {
        final Kernel32.CONSOLE_SCREEN_BUFFER_INFO console_SCREEN_BUFFER_INFO = new Kernel32.CONSOLE_SCREEN_BUFFER_INFO();
        Kernel32.INSTANCE.GetConsoleScreenBufferInfo(JnaWinSysTerminal.consoleOut, console_SCREEN_BUFFER_INFO);
        return new Cursor(console_SCREEN_BUFFER_INFO.dwCursorPosition.X, console_SCREEN_BUFFER_INFO.dwCursorPosition.Y);
    }
    
    static {
        consoleIn = Kernel32.INSTANCE.GetStdHandle(-10);
        consoleOut = Kernel32.INSTANCE.GetStdHandle(-11);
    }
}
