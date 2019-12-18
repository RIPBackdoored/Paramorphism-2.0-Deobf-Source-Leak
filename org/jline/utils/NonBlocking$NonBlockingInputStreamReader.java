package org.jline.utils;

import java.nio.*;
import java.nio.charset.*;
import java.io.*;

private static class NonBlockingInputStreamReader extends NonBlockingReader
{
    private final NonBlockingInputStream input;
    private final CharsetDecoder decoder;
    private final ByteBuffer bytes;
    private final CharBuffer chars;
    
    public NonBlockingInputStreamReader(final NonBlockingInputStream nonBlockingInputStream, final Charset charset) {
        this(nonBlockingInputStream, ((charset != null) ? charset : Charset.defaultCharset()).newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE));
    }
    
    public NonBlockingInputStreamReader(final NonBlockingInputStream input, final CharsetDecoder decoder) {
        super();
        this.input = input;
        this.decoder = decoder;
        this.bytes = ByteBuffer.allocate(4);
        this.chars = CharBuffer.allocate(2);
        this.bytes.limit(0);
        this.chars.limit(0);
    }
    
    @Override
    protected int read(long n, final boolean b) throws IOException {
        final boolean b2 = n <= 0L;
        while (!this.chars.hasRemaining() && (b2 || n > 0L)) {
            long currentTimeMillis = 0L;
            if (!b2) {
                currentTimeMillis = System.currentTimeMillis();
            }
            final int read = this.input.read(n);
            if (read == -1) {
                return -1;
            }
            if (read >= 0) {
                if (!this.bytes.hasRemaining()) {
                    this.bytes.position(0);
                    this.bytes.limit(0);
                }
                final int limit = this.bytes.limit();
                this.bytes.array()[this.bytes.arrayOffset() + limit] = (byte)read;
                this.bytes.limit(limit + 1);
                this.chars.clear();
                this.decoder.decode(this.bytes, this.chars, false);
                this.chars.flip();
            }
            if (b2) {
                continue;
            }
            n -= System.currentTimeMillis() - currentTimeMillis;
        }
        if (!this.chars.hasRemaining()) {
            return -2;
        }
        if (b) {
            return this.chars.get(this.chars.position());
        }
        return this.chars.get();
    }
    
    @Override
    public void shutdown() {
        this.input.shutdown();
    }
    
    @Override
    public void close() throws IOException {
        this.input.close();
    }
}
