package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.util.*;
import java.io.*;
import java.nio.*;

public class SerializedString implements SerializableString, Serializable
{
    private static final long serialVersionUID = 1L;
    protected final String _value;
    protected byte[] _quotedUTF8Ref;
    protected byte[] _unquotedUTF8Ref;
    protected char[] _quotedChars;
    protected transient String _jdkSerializeValue;
    
    public SerializedString(final String value) {
        super();
        if (value == null) {
            throw new IllegalStateException("Null String illegal for SerializedString");
        }
        this._value = value;
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException {
        this._jdkSerializeValue = objectInputStream.readUTF();
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeUTF(this._value);
    }
    
    protected Object readResolve() {
        return new SerializedString(this._jdkSerializeValue);
    }
    
    @Override
    public final String getValue() {
        return this._value;
    }
    
    @Override
    public final int charLength() {
        return this._value.length();
    }
    
    @Override
    public final char[] asQuotedChars() {
        char[] quotedChars = this._quotedChars;
        if (quotedChars == null) {
            quotedChars = BufferRecyclers.quoteAsJsonText(this._value);
            this._quotedChars = quotedChars;
        }
        return quotedChars;
    }
    
    @Override
    public final byte[] asUnquotedUTF8() {
        byte[] unquotedUTF8Ref = this._unquotedUTF8Ref;
        if (unquotedUTF8Ref == null) {
            unquotedUTF8Ref = BufferRecyclers.encodeAsUTF8(this._value);
            this._unquotedUTF8Ref = unquotedUTF8Ref;
        }
        return unquotedUTF8Ref;
    }
    
    @Override
    public final byte[] asQuotedUTF8() {
        byte[] quotedUTF8Ref = this._quotedUTF8Ref;
        if (quotedUTF8Ref == null) {
            quotedUTF8Ref = BufferRecyclers.quoteAsJsonUTF8(this._value);
            this._quotedUTF8Ref = quotedUTF8Ref;
        }
        return quotedUTF8Ref;
    }
    
    @Override
    public int appendQuotedUTF8(final byte[] array, final int n) {
        byte[] quotedUTF8Ref = this._quotedUTF8Ref;
        if (quotedUTF8Ref == null) {
            quotedUTF8Ref = BufferRecyclers.quoteAsJsonUTF8(this._value);
            this._quotedUTF8Ref = quotedUTF8Ref;
        }
        final int length = quotedUTF8Ref.length;
        if (n + length > array.length) {
            return -1;
        }
        System.arraycopy(quotedUTF8Ref, 0, array, n, length);
        return length;
    }
    
    @Override
    public int appendQuoted(final char[] array, final int n) {
        char[] quotedChars = this._quotedChars;
        if (quotedChars == null) {
            quotedChars = BufferRecyclers.quoteAsJsonText(this._value);
            this._quotedChars = quotedChars;
        }
        final int length = quotedChars.length;
        if (n + length > array.length) {
            return -1;
        }
        System.arraycopy(quotedChars, 0, array, n, length);
        return length;
    }
    
    @Override
    public int appendUnquotedUTF8(final byte[] array, final int n) {
        byte[] unquotedUTF8Ref = this._unquotedUTF8Ref;
        if (unquotedUTF8Ref == null) {
            unquotedUTF8Ref = BufferRecyclers.encodeAsUTF8(this._value);
            this._unquotedUTF8Ref = unquotedUTF8Ref;
        }
        final int length = unquotedUTF8Ref.length;
        if (n + length > array.length) {
            return -1;
        }
        System.arraycopy(unquotedUTF8Ref, 0, array, n, length);
        return length;
    }
    
    @Override
    public int appendUnquoted(final char[] array, final int n) {
        final String value = this._value;
        final int length = value.length();
        if (n + length > array.length) {
            return -1;
        }
        value.getChars(0, length, array, n);
        return length;
    }
    
    @Override
    public int writeQuotedUTF8(final OutputStream outputStream) throws IOException {
        byte[] quotedUTF8Ref = this._quotedUTF8Ref;
        if (quotedUTF8Ref == null) {
            quotedUTF8Ref = BufferRecyclers.quoteAsJsonUTF8(this._value);
            this._quotedUTF8Ref = quotedUTF8Ref;
        }
        final int length = quotedUTF8Ref.length;
        outputStream.write(quotedUTF8Ref, 0, length);
        return length;
    }
    
    @Override
    public int writeUnquotedUTF8(final OutputStream outputStream) throws IOException {
        byte[] unquotedUTF8Ref = this._unquotedUTF8Ref;
        if (unquotedUTF8Ref == null) {
            unquotedUTF8Ref = BufferRecyclers.encodeAsUTF8(this._value);
            this._unquotedUTF8Ref = unquotedUTF8Ref;
        }
        final int length = unquotedUTF8Ref.length;
        outputStream.write(unquotedUTF8Ref, 0, length);
        return length;
    }
    
    @Override
    public int putQuotedUTF8(final ByteBuffer byteBuffer) {
        byte[] quotedUTF8Ref = this._quotedUTF8Ref;
        if (quotedUTF8Ref == null) {
            quotedUTF8Ref = BufferRecyclers.quoteAsJsonUTF8(this._value);
            this._quotedUTF8Ref = quotedUTF8Ref;
        }
        final int length = quotedUTF8Ref.length;
        if (length > byteBuffer.remaining()) {
            return -1;
        }
        byteBuffer.put(quotedUTF8Ref, 0, length);
        return length;
    }
    
    @Override
    public int putUnquotedUTF8(final ByteBuffer byteBuffer) {
        byte[] unquotedUTF8Ref = this._unquotedUTF8Ref;
        if (unquotedUTF8Ref == null) {
            unquotedUTF8Ref = BufferRecyclers.encodeAsUTF8(this._value);
            this._unquotedUTF8Ref = unquotedUTF8Ref;
        }
        final int length = unquotedUTF8Ref.length;
        if (length > byteBuffer.remaining()) {
            return -1;
        }
        byteBuffer.put(unquotedUTF8Ref, 0, length);
        return length;
    }
    
    @Override
    public final String toString() {
        return this._value;
    }
    
    @Override
    public final int hashCode() {
        return this._value.hashCode();
    }
    
    @Override
    public final boolean equals(final Object o) {
        return o == this || (o != null && o.getClass() == this.getClass() && this._value.equals(((SerializedString)o)._value));
    }
}
