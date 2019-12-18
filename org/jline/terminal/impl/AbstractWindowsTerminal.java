package org.jline.terminal.impl;

import java.nio.charset.*;
import java.io.*;
import org.jline.terminal.*;
import java.util.*;
import org.jline.utils.*;

public abstract class AbstractWindowsTerminal extends AbstractTerminal
{
    public static final String TYPE_WINDOWS = "windows";
    public static final String TYPE_WINDOWS_256_COLOR = "windows-256color";
    public static final String TYPE_WINDOWS_CONEMU = "windows-conemu";
    public static final String TYPE_WINDOWS_VTP = "windows-vtp";
    public static final int ENABLE_VIRTUAL_TERMINAL_PROCESSING = 4;
    private static final int UTF8_CODE_PAGE = 65001;
    protected static final int ENABLE_PROCESSED_INPUT = 1;
    protected static final int ENABLE_LINE_INPUT = 2;
    protected static final int ENABLE_ECHO_INPUT = 4;
    protected static final int ENABLE_WINDOW_INPUT = 8;
    protected static final int ENABLE_MOUSE_INPUT = 16;
    protected static final int ENABLE_INSERT_MODE = 32;
    protected static final int ENABLE_QUICK_EDIT_MODE = 64;
    protected final Writer slaveInputPipe;
    protected final NonBlockingInputStream input;
    protected final OutputStream output;
    protected final NonBlockingReader reader;
    protected final PrintWriter writer;
    protected final Map<Terminal.Signal, Object> nativeHandlers;
    protected final ShutdownHooks.Task closer;
    protected final Attributes attributes;
    protected final int originalConsoleMode;
    protected final Object lock;
    protected boolean paused;
    protected Thread pump;
    protected Terminal.MouseTracking tracking;
    protected boolean focusTracking;
    private boolean closing;
    static final int SHIFT_FLAG = 1;
    static final int ALT_FLAG = 2;
    static final int CTRL_FLAG = 4;
    static final int RIGHT_ALT_PRESSED = 1;
    static final int LEFT_ALT_PRESSED = 2;
    static final int RIGHT_CTRL_PRESSED = 4;
    static final int LEFT_CTRL_PRESSED = 8;
    static final int SHIFT_PRESSED = 16;
    static final int NUMLOCK_ON = 32;
    static final int SCROLLLOCK_ON = 64;
    static final int CAPSLOCK_ON = 128;
    
    public AbstractWindowsTerminal(final Writer writer, final String s, final String s2, final Charset charset, final int n, final boolean b, final Terminal.SignalHandler signalHandler) throws IOException {
        super(s, s2, selectCharset(charset, n), signalHandler);
        this.nativeHandlers = new HashMap<Terminal.Signal, Object>();
        this.attributes = new Attributes();
        this.lock = new Object();
        this.paused = true;
        this.tracking = Terminal.MouseTracking.Off;
        this.focusTracking = false;
        final NonBlockingPumpReader nonBlockingPumpReader = NonBlocking.nonBlockingPumpReader();
        this.slaveInputPipe = nonBlockingPumpReader.getWriter();
        this.reader = nonBlockingPumpReader;
        this.input = NonBlocking.nonBlockingStream(nonBlockingPumpReader, this.encoding());
        this.writer = new PrintWriter(writer);
        this.output = new WriterOutputStream(writer, this.encoding());
        this.parseInfoCmp();
        this.originalConsoleMode = this.getConsoleMode();
        this.attributes.setLocalFlag(Attributes.LocalFlag.ISIG, true);
        this.attributes.setControlChar(Attributes.ControlChar.VINTR, this.ctrl('C'));
        this.attributes.setControlChar(Attributes.ControlChar.VEOF, this.ctrl('D'));
        this.attributes.setControlChar(Attributes.ControlChar.VSUSP, this.ctrl('Z'));
        if (b) {
            for (final Terminal.Signal signal : Terminal.Signal.values()) {
                if (signalHandler == Terminal.SignalHandler.SIG_DFL) {
                    this.nativeHandlers.put(signal, Signals.registerDefault(signal.name()));
                }
                else {
                    this.nativeHandlers.put(signal, Signals.register(signal.name(), this::lambda$new$0));
                }
            }
        }
        ShutdownHooks.add(this.closer = this::close);
        if ("windows-conemu".equals(this.getType()) && !Boolean.getBoolean("org.jline.terminal.conemu.disable-activate")) {
            writer.write("\u001b[9999E");
            writer.flush();
        }
    }
    
    private static Charset selectCharset(final Charset charset, final int n) {
        if (charset != null) {
            return charset;
        }
        if (n >= 0) {
            return getCodepageCharset(n);
        }
        return StandardCharsets.UTF_8;
    }
    
    private static Charset getCodepageCharset(final int n) {
        if (n == 65001) {
            return StandardCharsets.UTF_8;
        }
        final String string = "ms" + n;
        if (Charset.isSupported(string)) {
            return Charset.forName(string);
        }
        final String string2 = "cp" + n;
        if (Charset.isSupported(string2)) {
            return Charset.forName(string2);
        }
        return Charset.defaultCharset();
    }
    
    @Override
    public Terminal.SignalHandler handle(final Terminal.Signal signal, final Terminal.SignalHandler signalHandler) {
        final Terminal.SignalHandler handle = super.handle(signal, signalHandler);
        if (handle != signalHandler) {
            if (signalHandler == Terminal.SignalHandler.SIG_DFL) {
                Signals.registerDefault(signal.name());
            }
            else {
                Signals.register(signal.name(), this::lambda$handle$1);
            }
        }
        return handle;
    }
    
    @Override
    public NonBlockingReader reader() {
        return this.reader;
    }
    
    @Override
    public PrintWriter writer() {
        return this.writer;
    }
    
    @Override
    public InputStream input() {
        return this.input;
    }
    
    @Override
    public OutputStream output() {
        return this.output;
    }
    
    @Override
    public Attributes getAttributes() {
        final int consoleMode = this.getConsoleMode();
        if ((consoleMode & 0x4) != 0x0) {
            this.attributes.setLocalFlag(Attributes.LocalFlag.ECHO, true);
        }
        if ((consoleMode & 0x2) != 0x0) {
            this.attributes.setLocalFlag(Attributes.LocalFlag.ICANON, true);
        }
        return new Attributes(this.attributes);
    }
    
    @Override
    public void setAttributes(final Attributes attributes) {
        this.attributes.copy(attributes);
        this.updateConsoleMode();
    }
    
    protected void updateConsoleMode() {
        int consoleMode = 8;
        if (this.attributes.getLocalFlag(Attributes.LocalFlag.ECHO)) {
            consoleMode |= 0x4;
        }
        if (this.attributes.getLocalFlag(Attributes.LocalFlag.ICANON)) {
            consoleMode |= 0x2;
        }
        if (this.tracking != Terminal.MouseTracking.Off) {
            consoleMode |= 0x10;
        }
        this.setConsoleMode(consoleMode);
    }
    
    protected int ctrl(final char c) {
        return Character.toUpperCase(c) & '\u001f';
    }
    
    @Override
    public void setSize(final Size size) {
        throw new UnsupportedOperationException("Can not resize windows terminal");
    }
    
    @Override
    public void close() throws IOException {
        super.close();
        this.closing = true;
        this.pump.interrupt();
        ShutdownHooks.remove(this.closer);
        for (final Map.Entry<Terminal.Signal, Object> entry : this.nativeHandlers.entrySet()) {
            Signals.unregister(entry.getKey().name(), entry.getValue());
        }
        this.reader.close();
        this.writer.close();
        this.setConsoleMode(this.originalConsoleMode);
    }
    
    protected void processKeyEvent(final boolean b, final short n, char c, final int n2) throws IOException {
        final boolean b2 = (n2 & 0xC) > 0;
        final boolean b3 = (n2 & 0x3) > 0;
        final int n3 = ((n2 & 0x10) > 0) ? 1 : 0;
        if (b && c != '\u0003') {
            if (c != '\0' && (n2 & 0x1F) == 0x9) {
                this.processInputChar(c);
            }
            else {
                final String escapeSequence = this.getEscapeSequence(n, (b2 ? 4 : 0) + (b3 ? 2 : 0) + n3);
                if (escapeSequence != null) {
                    final char[] charArray = escapeSequence.toCharArray();
                    for (int length = charArray.length, i = 0; i < length; ++i) {
                        this.processInputChar(charArray[i]);
                    }
                    return;
                }
                if (c > '\0') {
                    if (b3) {
                        this.processInputChar('\u001b');
                    }
                    if (b2 && c != ' ' && c != '\n' && c != '\u007f') {
                        this.processInputChar((char)((c == '?') ? 127 : (Character.toUpperCase(c) & '\u001f')));
                    }
                    else {
                        this.processInputChar(c);
                    }
                }
                else if (b2) {
                    if (n >= 65 && n <= 90) {
                        c = (char)(n - 64);
                    }
                    else if (n == 191) {
                        c = '\u007f';
                    }
                    if (c > '\0') {
                        if (b3) {
                            this.processInputChar('\u001b');
                        }
                        this.processInputChar(c);
                    }
                }
            }
        }
        else if (b && c == '\u0003') {
            this.processInputChar('\u0003');
        }
        else if (n == 18 && c > '\0') {
            this.processInputChar(c);
        }
    }
    
    protected String getEscapeSequence(final short n, final int n2) {
        String s = null;
        switch (n) {
            case 8: {
                s = (((n2 & 0x2) > 0) ? "\\E^H" : this.getRawSequence(InfoCmp.Capability.key_backspace));
                break;
            }
            case 9: {
                s = (((n2 & 0x1) > 0) ? this.getRawSequence(InfoCmp.Capability.key_btab) : null);
                break;
            }
            case 33: {
                s = this.getRawSequence(InfoCmp.Capability.key_ppage);
                break;
            }
            case 34: {
                s = this.getRawSequence(InfoCmp.Capability.key_npage);
                break;
            }
            case 35: {
                s = ((n2 > 0) ? "\\E[1;%p1%dF" : this.getRawSequence(InfoCmp.Capability.key_end));
                break;
            }
            case 36: {
                s = ((n2 > 0) ? "\\E[1;%p1%dH" : this.getRawSequence(InfoCmp.Capability.key_home));
                break;
            }
            case 37: {
                s = ((n2 > 0) ? "\\E[1;%p1%dD" : this.getRawSequence(InfoCmp.Capability.key_left));
                break;
            }
            case 38: {
                s = ((n2 > 0) ? "\\E[1;%p1%dA" : this.getRawSequence(InfoCmp.Capability.key_up));
                break;
            }
            case 39: {
                s = ((n2 > 0) ? "\\E[1;%p1%dC" : this.getRawSequence(InfoCmp.Capability.key_right));
                break;
            }
            case 40: {
                s = ((n2 > 0) ? "\\E[1;%p1%dB" : this.getRawSequence(InfoCmp.Capability.key_down));
                break;
            }
            case 45: {
                s = this.getRawSequence(InfoCmp.Capability.key_ic);
                break;
            }
            case 46: {
                s = this.getRawSequence(InfoCmp.Capability.key_dc);
                break;
            }
            case 112: {
                s = ((n2 > 0) ? "\\E[1;%p1%dP" : this.getRawSequence(InfoCmp.Capability.key_f1));
                break;
            }
            case 113: {
                s = ((n2 > 0) ? "\\E[1;%p1%dQ" : this.getRawSequence(InfoCmp.Capability.key_f2));
                break;
            }
            case 114: {
                s = ((n2 > 0) ? "\\E[1;%p1%dR" : this.getRawSequence(InfoCmp.Capability.key_f3));
                break;
            }
            case 115: {
                s = ((n2 > 0) ? "\\E[1;%p1%dS" : this.getRawSequence(InfoCmp.Capability.key_f4));
                break;
            }
            case 116: {
                s = ((n2 > 0) ? "\\E[15;%p1%d~" : this.getRawSequence(InfoCmp.Capability.key_f5));
                break;
            }
            case 117: {
                s = ((n2 > 0) ? "\\E[17;%p1%d~" : this.getRawSequence(InfoCmp.Capability.key_f6));
                break;
            }
            case 118: {
                s = ((n2 > 0) ? "\\E[18;%p1%d~" : this.getRawSequence(InfoCmp.Capability.key_f7));
                break;
            }
            case 119: {
                s = ((n2 > 0) ? "\\E[19;%p1%d~" : this.getRawSequence(InfoCmp.Capability.key_f8));
                break;
            }
            case 120: {
                s = ((n2 > 0) ? "\\E[20;%p1%d~" : this.getRawSequence(InfoCmp.Capability.key_f9));
                break;
            }
            case 121: {
                s = ((n2 > 0) ? "\\E[21;%p1%d~" : this.getRawSequence(InfoCmp.Capability.key_f10));
                break;
            }
            case 122: {
                s = ((n2 > 0) ? "\\E[23;%p1%d~" : this.getRawSequence(InfoCmp.Capability.key_f11));
                break;
            }
            case 123: {
                s = ((n2 > 0) ? "\\E[24;%p1%d~" : this.getRawSequence(InfoCmp.Capability.key_f12));
                break;
            }
            default: {
                return null;
            }
        }
        return Curses.tputs(s, n2 + 1);
    }
    
    protected String getRawSequence(final InfoCmp.Capability capability) {
        return this.strings.get(capability);
    }
    
    @Override
    public boolean hasFocusSupport() {
        return true;
    }
    
    @Override
    public boolean trackFocus(final boolean focusTracking) {
        this.focusTracking = focusTracking;
        return true;
    }
    
    @Override
    public boolean canPauseResume() {
        return true;
    }
    
    @Override
    public void pause() {
        synchronized (this.lock) {
            this.paused = true;
        }
    }
    
    @Override
    public void pause(final boolean b) throws InterruptedException {
        final Thread pump;
        synchronized (this.lock) {
            this.paused = true;
            pump = this.pump;
        }
        if (pump != null) {
            pump.interrupt();
            pump.join();
        }
    }
    
    @Override
    public void resume() {
        synchronized (this.lock) {
            this.paused = false;
            if (this.pump == null) {
                (this.pump = new Thread(this::pump, "WindowsStreamPump")).setDaemon(true);
                this.pump.start();
            }
        }
    }
    
    @Override
    public boolean paused() {
        synchronized (this.lock) {
            return this.paused;
        }
    }
    
    protected void pump() {
        try {
            while (!this.closing) {
                synchronized (this.lock) {
                    if (this.paused) {
                        this.pump = null;
                        break;
                    }
                }
                if (this.processConsoleInput()) {
                    this.slaveInputPipe.flush();
                }
            }
        }
        catch (IOException ex) {
            if (!this.closing) {
                Log.warn("Error in WindowsStreamPump", ex);
                try {
                    this.close();
                }
                catch (IOException ex2) {
                    Log.warn("Error closing terminal", ex);
                }
            }
            synchronized (this.lock) {
                this.pump = null;
            }
        }
        finally {
            synchronized (this.lock) {
                this.pump = null;
            }
        }
    }
    
    public void processInputChar(char c) throws IOException {
        if (this.attributes.getLocalFlag(Attributes.LocalFlag.ISIG)) {
            if (c == this.attributes.getControlChar(Attributes.ControlChar.VINTR)) {
                this.raise(Terminal.Signal.INT);
                return;
            }
            if (c == this.attributes.getControlChar(Attributes.ControlChar.VQUIT)) {
                this.raise(Terminal.Signal.QUIT);
                return;
            }
            if (c == this.attributes.getControlChar(Attributes.ControlChar.VSUSP)) {
                this.raise(Terminal.Signal.TSTP);
                return;
            }
            if (c == this.attributes.getControlChar(Attributes.ControlChar.VSTATUS)) {
                this.raise(Terminal.Signal.INFO);
            }
        }
        if (c == '\r') {
            if (this.attributes.getInputFlag(Attributes.InputFlag.IGNCR)) {
                return;
            }
            if (this.attributes.getInputFlag(Attributes.InputFlag.ICRNL)) {
                c = '\n';
            }
        }
        else if (c == '\n' && this.attributes.getInputFlag(Attributes.InputFlag.INLCR)) {
            c = '\r';
        }
        this.slaveInputPipe.write(c);
    }
    
    @Override
    public boolean trackMouse(final Terminal.MouseTracking tracking) {
        this.tracking = tracking;
        this.updateConsoleMode();
        return true;
    }
    
    protected abstract int getConsoleOutputCP();
    
    protected abstract int getConsoleMode();
    
    protected abstract void setConsoleMode(final int p0);
    
    protected abstract boolean processConsoleInput() throws IOException;
    
    private void lambda$handle$1(final Terminal.Signal signal) {
        this.raise(signal);
    }
    
    private void lambda$new$0(final Terminal.Signal signal) {
        this.raise(signal);
    }
}
