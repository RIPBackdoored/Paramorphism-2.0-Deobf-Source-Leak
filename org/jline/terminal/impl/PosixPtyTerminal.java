package org.jline.terminal.impl;

import org.jline.terminal.spi.*;
import java.nio.charset.*;
import org.jline.terminal.*;
import java.util.*;
import java.io.*;
import java.util.concurrent.atomic.*;
import org.jline.utils.*;

public class PosixPtyTerminal extends AbstractPosixTerminal
{
    private final InputStream in;
    private final OutputStream out;
    private final InputStream masterInput;
    private final OutputStream masterOutput;
    private final NonBlockingInputStream input;
    private final OutputStream output;
    private final NonBlockingReader reader;
    private final PrintWriter writer;
    private final Object lock;
    private Thread inputPumpThread;
    private Thread outputPumpThread;
    private boolean paused;
    
    public PosixPtyTerminal(final String s, final String s2, final Pty pty, final InputStream inputStream, final OutputStream outputStream, final Charset charset) throws IOException {
        this(s, s2, pty, inputStream, outputStream, charset, Terminal.SignalHandler.SIG_DFL);
    }
    
    public PosixPtyTerminal(final String s, final String s2, final Pty pty, final InputStream inputStream, final OutputStream outputStream, final Charset charset, final Terminal.SignalHandler signalHandler) throws IOException {
        this(s, s2, pty, inputStream, outputStream, charset, signalHandler, false);
    }
    
    public PosixPtyTerminal(final String s, final String s2, final Pty pty, final InputStream inputStream, final OutputStream outputStream, final Charset charset, final Terminal.SignalHandler signalHandler, final boolean b) throws IOException {
        super(s, s2, pty, charset, signalHandler);
        this.lock = new Object();
        this.paused = true;
        this.in = Objects.requireNonNull(inputStream);
        this.out = Objects.requireNonNull(outputStream);
        this.masterInput = pty.getMasterInput();
        this.masterOutput = pty.getMasterOutput();
        this.input = new InputStreamWrapper(NonBlocking.nonBlocking(s, pty.getSlaveInput()));
        this.output = pty.getSlaveOutput();
        this.reader = NonBlocking.nonBlocking(s, this.input, this.encoding());
        this.writer = new PrintWriter(new OutputStreamWriter(this.output, this.encoding()));
        this.parseInfoCmp();
        if (!b) {
            this.resume();
        }
    }
    
    @Override
    public InputStream input() {
        return this.input;
    }
    
    @Override
    public NonBlockingReader reader() {
        return this.reader;
    }
    
    @Override
    public OutputStream output() {
        return this.output;
    }
    
    @Override
    public PrintWriter writer() {
        return this.writer;
    }
    
    @Override
    public void close() throws IOException {
        super.close();
        this.reader.close();
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
        final Thread inputPumpThread;
        final Thread outputPumpThread;
        synchronized (this.lock) {
            this.paused = true;
            inputPumpThread = this.inputPumpThread;
            outputPumpThread = this.outputPumpThread;
        }
        if (inputPumpThread != null) {
            inputPumpThread.interrupt();
        }
        if (outputPumpThread != null) {
            outputPumpThread.interrupt();
        }
        if (inputPumpThread != null) {
            inputPumpThread.join();
        }
        if (outputPumpThread != null) {
            outputPumpThread.join();
        }
    }
    
    @Override
    public void resume() {
        synchronized (this.lock) {
            this.paused = false;
            if (this.inputPumpThread == null) {
                (this.inputPumpThread = new Thread(this::pumpIn, this.toString() + " input pump thread")).setDaemon(true);
                this.inputPumpThread.start();
            }
            if (this.outputPumpThread == null) {
                (this.outputPumpThread = new Thread(this::pumpOut, this.toString() + " output pump thread")).setDaemon(true);
                this.outputPumpThread.start();
            }
        }
    }
    
    @Override
    public boolean paused() {
        synchronized (this.lock) {
            return this.paused;
        }
    }
    
    private void pumpIn() {
        try {
            while (true) {
                synchronized (this.lock) {
                    if (this.paused) {
                        this.inputPumpThread = null;
                        return;
                    }
                }
                final int read = this.in.read();
                if (read < 0) {
                    break;
                }
                this.masterOutput.write(read);
                this.masterOutput.flush();
            }
            this.input.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
            synchronized (this.lock) {
                this.inputPumpThread = null;
            }
        }
        finally {
            synchronized (this.lock) {
                this.inputPumpThread = null;
            }
        }
    }
    
    private void pumpOut() {
        try {
            while (true) {
                synchronized (this.lock) {
                    if (this.paused) {
                        this.outputPumpThread = null;
                        return;
                    }
                }
                final int read = this.masterInput.read();
                if (read < 0) {
                    break;
                }
                this.out.write(read);
                this.out.flush();
            }
            this.input.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
            synchronized (this.lock) {
                this.outputPumpThread = null;
            }
        }
        finally {
            synchronized (this.lock) {
                this.outputPumpThread = null;
            }
        }
        try {
            this.close();
        }
        catch (Throwable t) {}
    }
    
    private class InputStreamWrapper extends NonBlockingInputStream
    {
        private final NonBlockingInputStream in;
        private final AtomicBoolean closed;
        final PosixPtyTerminal this$0;
        
        protected InputStreamWrapper(final PosixPtyTerminal this$0, final NonBlockingInputStream in) {
            this.this$0 = this$0;
            super();
            this.closed = new AtomicBoolean();
            this.in = in;
        }
        
        @Override
        public int read(final long n, final boolean b) throws IOException {
            if (this.closed.get()) {
                throw new ClosedException();
            }
            return this.in.read(n, b);
        }
        
        @Override
        public void close() throws IOException {
            this.closed.set(true);
        }
    }
}
