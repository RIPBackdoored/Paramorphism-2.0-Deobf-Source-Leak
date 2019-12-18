package org.jline.builtins;

import java.util.function.*;
import org.jline.terminal.impl.*;
import org.jline.terminal.*;
import java.io.*;
import java.nio.charset.*;
import java.nio.*;

private static class VirtualConsole implements Closeable
{
    private final ScreenTerminal terminal;
    private final Consumer<VirtualConsole> closer;
    private final int id;
    private int left;
    private int top;
    private final Layout layout;
    private int active;
    private boolean clock;
    private final OutputStream masterOutput;
    private final OutputStream masterInputOutput;
    private final LineDisciplineTerminal console;
    
    public VirtualConsole(final int id, final String s, final int left, final int top, final int n, final int n2, final Runnable runnable, final Consumer<VirtualConsole> closer, final Layout layout) throws IOException {
        super();
        final String format = String.format("tmux%02d", id);
        this.id = id;
        this.left = left;
        this.top = top;
        this.closer = closer;
        this.terminal = new ScreenTerminal(this, n, n2, runnable) {
            final Runnable val$dirty;
            final VirtualConsole this$0;
            
            Tmux$VirtualConsole$1(final VirtualConsole this$0, final int n, final int n2, final Runnable val$dirty) {
                this.this$0 = this$0;
                this.val$dirty = val$dirty;
                super(n, n2);
            }
            
            @Override
            protected void setDirty() {
                super.setDirty();
                this.val$dirty.run();
            }
        };
        this.masterOutput = new MasterOutputStream(null);
        this.masterInputOutput = new OutputStream(this) {
            final VirtualConsole this$0;
            
            Tmux$VirtualConsole$2(final VirtualConsole this$0) {
                this.this$0 = this$0;
                super();
            }
            
            @Override
            public void write(final int n) throws IOException {
                VirtualConsole.access$700(this.this$0).processInputByte(n);
            }
        };
        (this.console = new LineDisciplineTerminal(this, format, s, this.masterOutput, null, closer) {
            final Consumer val$closer;
            final VirtualConsole this$0;
            
            Tmux$VirtualConsole$3(final VirtualConsole this$0, final String s, final String s2, final OutputStream outputStream, final Charset charset, final Consumer val$closer) {
                this.this$0 = this$0;
                this.val$closer = val$closer;
                super(s, s2, outputStream, charset);
            }
            
            @Override
            public void close() throws IOException {
                super.close();
                this.val$closer.accept(this.this$0);
            }
        }).setSize(new Size(n, n2));
        this.layout = layout;
    }
    
    Layout layout() {
        return this.layout;
    }
    
    public int left() {
        return this.left;
    }
    
    public int top() {
        return this.top;
    }
    
    public int right() {
        return this.left() + this.width();
    }
    
    public int bottom() {
        return this.top() + this.height();
    }
    
    public int width() {
        return this.console.getWidth();
    }
    
    public int height() {
        return this.console.getHeight();
    }
    
    public LineDisciplineTerminal getConsole() {
        return this.console;
    }
    
    public OutputStream getMasterInputOutput() {
        return this.masterInputOutput;
    }
    
    public void resize(final int left, final int top, final int n, final int n2) {
        this.left = left;
        this.top = top;
        this.console.setSize(new Size(n, n2));
        this.terminal.setSize(n, n2);
        this.console.raise(Terminal.Signal.WINCH);
    }
    
    public void dump(final long[] array, final int n, final int n2, final int n3, final int n4, final int[] array2) {
        this.terminal.dump(array, n, n2, n3, n4, array2);
    }
    
    @Override
    public void close() throws IOException {
        this.console.close();
    }
    
    static int access$002(final VirtualConsole virtualConsole, final int active) {
        return virtualConsole.active = active;
    }
    
    static boolean access$100(final VirtualConsole virtualConsole) {
        return virtualConsole.clock;
    }
    
    static boolean access$102(final VirtualConsole virtualConsole, final boolean clock) {
        return virtualConsole.clock = clock;
    }
    
    static Layout access$200(final VirtualConsole virtualConsole) {
        return virtualConsole.layout;
    }
    
    static int access$300(final VirtualConsole virtualConsole) {
        return virtualConsole.id;
    }
    
    static int access$400(final VirtualConsole virtualConsole) {
        return virtualConsole.top;
    }
    
    static LineDisciplineTerminal access$700(final VirtualConsole virtualConsole) {
        return virtualConsole.console;
    }
    
    static ScreenTerminal access$800(final VirtualConsole virtualConsole) {
        return virtualConsole.terminal;
    }
    
    static OutputStream access$900(final VirtualConsole virtualConsole) {
        return virtualConsole.masterInputOutput;
    }
    
    static int access$000(final VirtualConsole virtualConsole) {
        return virtualConsole.active;
    }
    
    private class MasterOutputStream extends OutputStream
    {
        private final ByteArrayOutputStream buffer;
        private final CharsetDecoder decoder;
        final VirtualConsole this$0;
        
        private MasterOutputStream(final VirtualConsole this$0) {
            this.this$0 = this$0;
            super();
            this.buffer = new ByteArrayOutputStream();
            this.decoder = Charset.defaultCharset().newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
        }
        
        @Override
        public synchronized void write(final int n) {
            this.buffer.write(n);
        }
        
        @Override
        public void write(final byte[] array, final int n, final int n2) throws IOException {
            this.buffer.write(array, n, n2);
        }
        
        @Override
        public synchronized void flush() throws IOException {
            int size = this.buffer.size();
            if (size > 0) {
                CharBuffer allocate;
                ByteBuffer wrap;
                while (true) {
                    allocate = CharBuffer.allocate(size);
                    wrap = ByteBuffer.wrap(this.buffer.toByteArray());
                    if (!this.decoder.decode(wrap, allocate, false).isOverflow()) {
                        break;
                    }
                    size *= 2;
                }
                this.buffer.reset();
                this.buffer.write(wrap.array(), wrap.arrayOffset(), wrap.remaining());
                if (allocate.position() > 0) {
                    allocate.flip();
                    VirtualConsole.access$800(this.this$0).write(allocate);
                    VirtualConsole.access$900(this.this$0).write(VirtualConsole.access$800(this.this$0).read().getBytes());
                }
            }
        }
        
        @Override
        public void close() throws IOException {
            this.flush();
        }
        
        MasterOutputStream(final VirtualConsole virtualConsole, final Tmux$1 object) {
            this(virtualConsole);
        }
    }
}
