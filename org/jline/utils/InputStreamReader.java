package org.jline.utils;

import java.io.*;
import java.nio.*;
import java.nio.charset.*;

public class InputStreamReader extends Reader
{
    private InputStream in;
    private static final int BUFFER_SIZE = 4;
    private boolean endOfInput;
    CharsetDecoder decoder;
    ByteBuffer bytes;
    char pending;
    
    public InputStreamReader(final InputStream in) {
        super(in);
        this.endOfInput = false;
        this.bytes = ByteBuffer.allocate(4);
        this.pending = '\uffff';
        this.in = in;
        this.decoder = Charset.defaultCharset().newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
        this.bytes.limit(0);
    }
    
    public InputStreamReader(final InputStream in, final String s) throws UnsupportedEncodingException {
        super(in);
        this.endOfInput = false;
        this.bytes = ByteBuffer.allocate(4);
        this.pending = '\uffff';
        if (s == null) {
            throw new NullPointerException();
        }
        this.in = in;
        try {
            this.decoder = Charset.forName(s).newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
        }
        catch (IllegalArgumentException ex) {
            throw (UnsupportedEncodingException)new UnsupportedEncodingException(s).initCause(ex);
        }
        this.bytes.limit(0);
    }
    
    public InputStreamReader(final InputStream in, final CharsetDecoder decoder) {
        super(in);
        this.endOfInput = false;
        this.bytes = ByteBuffer.allocate(4);
        this.pending = '\uffff';
        decoder.averageCharsPerByte();
        this.in = in;
        this.decoder = decoder;
        this.bytes.limit(0);
    }
    
    public InputStreamReader(final InputStream in, final Charset charset) {
        super(in);
        this.endOfInput = false;
        this.bytes = ByteBuffer.allocate(4);
        this.pending = '\uffff';
        this.in = in;
        this.decoder = charset.newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
        this.bytes.limit(0);
    }
    
    @Override
    public void close() throws IOException {
        synchronized (this.lock) {
            this.decoder = null;
            if (this.in != null) {
                this.in.close();
                this.in = null;
            }
        }
    }
    
    public String getEncoding() {
        if (!this.isOpen()) {
            return null;
        }
        return this.decoder.charset().name();
    }
    
    @Override
    public int read() throws IOException {
        synchronized (this.lock) {
            if (!this.isOpen()) {
                throw new ClosedException("InputStreamReader is closed.");
            }
            if (this.pending != '\uffff') {
                final char pending = this.pending;
                this.pending = '\uffff';
                return pending;
            }
            final char[] array = new char[2];
            final int read = this.read(array, 0, 2);
            if (read == 2) {
                this.pending = array[1];
            }
            if (read > 0) {
                return array[0];
            }
            return -1;
        }
    }
    
    @Override
    public int read(final char[] array, final int n, final int n2) throws IOException {
        synchronized (this.lock) {
            if (!this.isOpen()) {
                throw new IOException("InputStreamReader is closed.");
            }
            if (n < 0 || n > array.length - n2 || n2 < 0) {
                throw new IndexOutOfBoundsException();
            }
            if (n2 == 0) {
                return 0;
            }
            final CharBuffer wrap = CharBuffer.wrap(array, n, n2);
            CoderResult coderResult = CoderResult.UNDERFLOW;
            int n3 = this.bytes.hasRemaining() ? 0 : 1;
            while (wrap.position() == n) {
                if (n3 != 0) {
                    try {
                        if (this.in.available() == 0 && wrap.position() > n) {
                            break;
                        }
                    }
                    catch (IOException ex) {}
                    final int read = this.in.read(this.bytes.array(), this.bytes.arrayOffset() + this.bytes.limit(), 1);
                    if (read == -1) {
                        this.endOfInput = true;
                        break;
                    }
                    if (read == 0) {
                        break;
                    }
                    this.bytes.limit(this.bytes.limit() + read);
                }
                coderResult = this.decoder.decode(this.bytes, wrap, false);
                if (!coderResult.isUnderflow()) {
                    break;
                }
                if (this.bytes.limit() == this.bytes.capacity()) {
                    this.bytes.compact();
                    this.bytes.limit(this.bytes.position());
                    this.bytes.position(0);
                }
                n3 = 1;
            }
            if (coderResult == CoderResult.UNDERFLOW && this.endOfInput) {
                coderResult = this.decoder.decode(this.bytes, wrap, true);
                this.decoder.flush(wrap);
                this.decoder.reset();
            }
            if (coderResult.isMalformed()) {
                throw new MalformedInputException(coderResult.length());
            }
            if (coderResult.isUnmappable()) {
                throw new UnmappableCharacterException(coderResult.length());
            }
            return (wrap.position() - n == 0) ? -1 : (wrap.position() - n);
        }
    }
    
    private boolean isOpen() {
        return this.in != null;
    }
    
    @Override
    public boolean ready() throws IOException {
        synchronized (this.lock) {
            if (this.in == null) {
                throw new IOException("InputStreamReader is closed.");
            }
            try {
                return this.bytes.hasRemaining() || this.in.available() > 0;
            }
            catch (IOException ex) {
                return false;
            }
        }
    }
}
