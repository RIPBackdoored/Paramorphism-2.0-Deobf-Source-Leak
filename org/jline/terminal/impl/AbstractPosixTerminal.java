package org.jline.terminal.impl;

import org.jline.terminal.spi.*;
import java.nio.charset.*;
import java.util.*;
import java.io.*;
import java.util.function.*;
import org.jline.terminal.*;

public abstract class AbstractPosixTerminal extends AbstractTerminal
{
    protected final Pty pty;
    protected final Attributes originalAttributes;
    
    public AbstractPosixTerminal(final String s, final String s2, final Pty pty) throws IOException {
        this(s, s2, pty, null, Terminal.SignalHandler.SIG_DFL);
    }
    
    public AbstractPosixTerminal(final String s, final String s2, final Pty pty, final Charset charset, final Terminal.SignalHandler signalHandler) throws IOException {
        super(s, s2, charset, signalHandler);
        Objects.requireNonNull(pty);
        this.pty = pty;
        this.originalAttributes = this.pty.getAttr();
    }
    
    public Pty getPty() {
        return this.pty;
    }
    
    @Override
    public Attributes getAttributes() {
        try {
            return this.pty.getAttr();
        }
        catch (IOException ex) {
            throw new IOError(ex);
        }
    }
    
    @Override
    public void setAttributes(final Attributes attr) {
        try {
            this.pty.setAttr(attr);
        }
        catch (IOException ex) {
            throw new IOError(ex);
        }
    }
    
    @Override
    public Size getSize() {
        try {
            return this.pty.getSize();
        }
        catch (IOException ex) {
            throw new IOError(ex);
        }
    }
    
    @Override
    public void setSize(final Size size) {
        try {
            this.pty.setSize(size);
        }
        catch (IOException ex) {
            throw new IOError(ex);
        }
    }
    
    @Override
    public void close() throws IOException {
        super.close();
        this.pty.setAttr(this.originalAttributes);
        this.pty.close();
    }
    
    @Override
    public Cursor getCursorPosition(final IntConsumer intConsumer) {
        return CursorSupport.getCursorPosition(this, intConsumer);
    }
}
