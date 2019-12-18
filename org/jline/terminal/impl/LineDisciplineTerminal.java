package org.jline.terminal.impl;

import java.nio.charset.*;
import org.jline.terminal.*;
import org.jline.utils.*;
import java.io.*;
import java.util.*;

public class LineDisciplineTerminal extends AbstractTerminal
{
    private static final String DEFAULT_TERMINAL_ATTRIBUTES = "speed 9600 baud; 24 rows; 80 columns;\nlflags: icanon isig iexten echo echoe -echok echoke -echonl echoctl\n\t-echoprt -altwerase -noflsh -tostop -flusho pendin -nokerninfo\n\t-extproc\niflags: -istrip icrnl -inlcr -igncr ixon -ixoff ixany imaxbel iutf8\n\t-ignbrk brkint -inpck -ignpar -parmrk\noflags: opost onlcr -oxtabs -onocr -onlret\ncflags: cread cs8 -parenb -parodd hupcl -clocal -cstopb -crtscts -dsrflow\n\t-dtrflow -mdmbuf\ncchars: discard = ^O; dsusp = ^Y; eof = ^D; eol = <undef>;\n\teol2 = <undef>; erase = ^?; intr = ^C; kill = ^U; lnext = ^V;\n\tmin = 1; quit = ^\\; reprint = ^R; start = ^Q; status = ^T;\n\tstop = ^S; susp = ^Z; time = 0; werase = ^W;\n";
    private static final int PIPE_SIZE = 1024;
    protected final OutputStream masterOutput;
    protected final OutputStream slaveInputPipe;
    protected final NonBlockingPumpInputStream slaveInput;
    protected final NonBlockingReader slaveReader;
    protected final PrintWriter slaveWriter;
    protected final OutputStream slaveOutput;
    protected final Attributes attributes;
    protected final Size size;
    
    public LineDisciplineTerminal(final String s, final String s2, final OutputStream outputStream, final Charset charset) throws IOException {
        this(s, s2, outputStream, charset, Terminal.SignalHandler.SIG_DFL);
    }
    
    public LineDisciplineTerminal(final String s, final String s2, final OutputStream masterOutput, final Charset charset, final Terminal.SignalHandler signalHandler) throws IOException {
        super(s, s2, charset, signalHandler);
        final NonBlockingPumpInputStream nonBlockingPumpInputStream = NonBlocking.nonBlockingPumpInputStream(1024);
        this.slaveInputPipe = nonBlockingPumpInputStream.getOutputStream();
        this.slaveInput = nonBlockingPumpInputStream;
        this.slaveReader = NonBlocking.nonBlocking(this.getName(), this.slaveInput, this.encoding());
        this.slaveOutput = new FilteringOutputStream(null);
        this.slaveWriter = new PrintWriter(new OutputStreamWriter(this.slaveOutput, this.encoding()));
        this.masterOutput = masterOutput;
        this.attributes = ExecPty.doGetAttr("speed 9600 baud; 24 rows; 80 columns;\nlflags: icanon isig iexten echo echoe -echok echoke -echonl echoctl\n\t-echoprt -altwerase -noflsh -tostop -flusho pendin -nokerninfo\n\t-extproc\niflags: -istrip icrnl -inlcr -igncr ixon -ixoff ixany imaxbel iutf8\n\t-ignbrk brkint -inpck -ignpar -parmrk\noflags: opost onlcr -oxtabs -onocr -onlret\ncflags: cread cs8 -parenb -parodd hupcl -clocal -cstopb -crtscts -dsrflow\n\t-dtrflow -mdmbuf\ncchars: discard = ^O; dsusp = ^Y; eof = ^D; eol = <undef>;\n\teol2 = <undef>; erase = ^?; intr = ^C; kill = ^U; lnext = ^V;\n\tmin = 1; quit = ^\\; reprint = ^R; start = ^Q; status = ^T;\n\tstop = ^S; susp = ^Z; time = 0; werase = ^W;\n");
        this.size = new Size(160, 50);
        this.parseInfoCmp();
    }
    
    @Override
    public NonBlockingReader reader() {
        return this.slaveReader;
    }
    
    @Override
    public PrintWriter writer() {
        return this.slaveWriter;
    }
    
    @Override
    public InputStream input() {
        return this.slaveInput;
    }
    
    @Override
    public OutputStream output() {
        return this.slaveOutput;
    }
    
    @Override
    public Attributes getAttributes() {
        final Attributes attributes = new Attributes();
        attributes.copy(this.attributes);
        return attributes;
    }
    
    @Override
    public void setAttributes(final Attributes attributes) {
        this.attributes.copy(attributes);
    }
    
    @Override
    public Size getSize() {
        final Size size = new Size();
        size.copy(this.size);
        return size;
    }
    
    @Override
    public void setSize(final Size size) {
        this.size.copy(size);
    }
    
    @Override
    public void raise(final Terminal.Signal signal) {
        Objects.requireNonNull(signal);
        this.echoSignal(signal);
        super.raise(signal);
    }
    
    public void processInputByte(final int n) throws IOException {
        final boolean doProcessInputByte = this.doProcessInputByte(n);
        this.slaveInputPipe.flush();
        if (doProcessInputByte) {
            this.masterOutput.flush();
        }
    }
    
    public void processInputBytes(final byte[] array) throws IOException {
        this.processInputBytes(array, 0, array.length);
    }
    
    public void processInputBytes(final byte[] array, final int n, final int n2) throws IOException {
        boolean b = false;
        for (int i = 0; i < n2; ++i) {
            b |= this.doProcessInputByte(array[n + i]);
        }
        this.slaveInputPipe.flush();
        if (b) {
            this.masterOutput.flush();
        }
    }
    
    protected boolean doProcessInputByte(int n) throws IOException {
        if (this.attributes.getLocalFlag(Attributes.LocalFlag.ISIG)) {
            if (n == this.attributes.getControlChar(Attributes.ControlChar.VINTR)) {
                this.raise(Terminal.Signal.INT);
                return false;
            }
            if (n == this.attributes.getControlChar(Attributes.ControlChar.VQUIT)) {
                this.raise(Terminal.Signal.QUIT);
                return false;
            }
            if (n == this.attributes.getControlChar(Attributes.ControlChar.VSUSP)) {
                this.raise(Terminal.Signal.TSTP);
                return false;
            }
            if (n == this.attributes.getControlChar(Attributes.ControlChar.VSTATUS)) {
                this.raise(Terminal.Signal.INFO);
            }
        }
        if (n == 13) {
            if (this.attributes.getInputFlag(Attributes.InputFlag.IGNCR)) {
                return false;
            }
            if (this.attributes.getInputFlag(Attributes.InputFlag.ICRNL)) {
                n = 10;
            }
        }
        else if (n == 10 && this.attributes.getInputFlag(Attributes.InputFlag.INLCR)) {
            n = 13;
        }
        boolean b = false;
        if (this.attributes.getLocalFlag(Attributes.LocalFlag.ECHO)) {
            this.processOutputByte(n);
            b = true;
        }
        this.slaveInputPipe.write(n);
        return b;
    }
    
    protected void processOutputByte(final int n) throws IOException {
        if (this.attributes.getOutputFlag(Attributes.OutputFlag.OPOST) && n == 10 && this.attributes.getOutputFlag(Attributes.OutputFlag.ONLCR)) {
            this.masterOutput.write(13);
            this.masterOutput.write(10);
            return;
        }
        this.masterOutput.write(n);
    }
    
    protected void processIOException(final IOException ioException) {
        this.slaveInput.setIoException(ioException);
    }
    
    @Override
    public void close() throws IOException {
        super.close();
        try {
            this.slaveReader.close();
        }
        finally {
            try {
                this.slaveInputPipe.close();
            }
            finally {
                this.slaveWriter.close();
            }
        }
    }
    
    private class FilteringOutputStream extends OutputStream
    {
        final LineDisciplineTerminal this$0;
        
        private FilteringOutputStream(final LineDisciplineTerminal this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public void write(final int n) throws IOException {
            this.this$0.processOutputByte(n);
            this.flush();
        }
        
        @Override
        public void write(final byte[] array, final int n, final int n2) throws IOException {
            if (array == null) {
                throw new NullPointerException();
            }
            if (n < 0 || n > array.length || n2 < 0 || n + n2 > array.length || n + n2 < 0) {
                throw new IndexOutOfBoundsException();
            }
            if (n2 == 0) {
                return;
            }
            for (int i = 0; i < n2; ++i) {
                this.this$0.processOutputByte(array[n + i]);
            }
            this.flush();
        }
        
        @Override
        public void flush() throws IOException {
            this.this$0.masterOutput.flush();
        }
        
        @Override
        public void close() throws IOException {
            this.this$0.masterOutput.close();
        }
        
        FilteringOutputStream(final LineDisciplineTerminal lineDisciplineTerminal, final LineDisciplineTerminal$1 object) {
            this(lineDisciplineTerminal);
        }
    }
}
