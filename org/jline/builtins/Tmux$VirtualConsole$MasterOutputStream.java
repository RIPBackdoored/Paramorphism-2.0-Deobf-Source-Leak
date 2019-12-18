package org.jline.builtins;

import java.nio.charset.*;
import java.io.*;
import java.nio.*;

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
