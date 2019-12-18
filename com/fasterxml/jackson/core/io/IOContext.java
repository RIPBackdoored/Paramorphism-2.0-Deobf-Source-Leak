package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.util.*;

public class IOContext
{
    protected final Object _sourceRef;
    protected JsonEncoding _encoding;
    protected final boolean _managedResource;
    protected final BufferRecycler _bufferRecycler;
    protected byte[] _readIOBuffer;
    protected byte[] _writeEncodingBuffer;
    protected byte[] _base64Buffer;
    protected char[] _tokenCBuffer;
    protected char[] _concatCBuffer;
    protected char[] _nameCopyBuffer;
    
    public IOContext(final BufferRecycler bufferRecycler, final Object sourceRef, final boolean managedResource) {
        super();
        this._bufferRecycler = bufferRecycler;
        this._sourceRef = sourceRef;
        this._managedResource = managedResource;
    }
    
    public void setEncoding(final JsonEncoding encoding) {
        this._encoding = encoding;
    }
    
    public IOContext withEncoding(final JsonEncoding encoding) {
        this._encoding = encoding;
        return this;
    }
    
    public Object getSourceReference() {
        return this._sourceRef;
    }
    
    public JsonEncoding getEncoding() {
        return this._encoding;
    }
    
    public boolean isResourceManaged() {
        return this._managedResource;
    }
    
    public TextBuffer constructTextBuffer() {
        return new TextBuffer(this._bufferRecycler);
    }
    
    public byte[] allocReadIOBuffer() {
        this._verifyAlloc(this._readIOBuffer);
        return this._readIOBuffer = this._bufferRecycler.allocByteBuffer(0);
    }
    
    public byte[] allocReadIOBuffer(final int n) {
        this._verifyAlloc(this._readIOBuffer);
        return this._readIOBuffer = this._bufferRecycler.allocByteBuffer(0, n);
    }
    
    public byte[] allocWriteEncodingBuffer() {
        this._verifyAlloc(this._writeEncodingBuffer);
        return this._writeEncodingBuffer = this._bufferRecycler.allocByteBuffer(1);
    }
    
    public byte[] allocWriteEncodingBuffer(final int n) {
        this._verifyAlloc(this._writeEncodingBuffer);
        return this._writeEncodingBuffer = this._bufferRecycler.allocByteBuffer(1, n);
    }
    
    public byte[] allocBase64Buffer() {
        this._verifyAlloc(this._base64Buffer);
        return this._base64Buffer = this._bufferRecycler.allocByteBuffer(3);
    }
    
    public byte[] allocBase64Buffer(final int n) {
        this._verifyAlloc(this._base64Buffer);
        return this._base64Buffer = this._bufferRecycler.allocByteBuffer(3, n);
    }
    
    public char[] allocTokenBuffer() {
        this._verifyAlloc(this._tokenCBuffer);
        return this._tokenCBuffer = this._bufferRecycler.allocCharBuffer(0);
    }
    
    public char[] allocTokenBuffer(final int n) {
        this._verifyAlloc(this._tokenCBuffer);
        return this._tokenCBuffer = this._bufferRecycler.allocCharBuffer(0, n);
    }
    
    public char[] allocConcatBuffer() {
        this._verifyAlloc(this._concatCBuffer);
        return this._concatCBuffer = this._bufferRecycler.allocCharBuffer(1);
    }
    
    public char[] allocNameCopyBuffer(final int n) {
        this._verifyAlloc(this._nameCopyBuffer);
        return this._nameCopyBuffer = this._bufferRecycler.allocCharBuffer(3, n);
    }
    
    public void releaseReadIOBuffer(final byte[] array) {
        if (array != null) {
            this._verifyRelease(array, this._readIOBuffer);
            this._readIOBuffer = null;
            this._bufferRecycler.releaseByteBuffer(0, array);
        }
    }
    
    public void releaseWriteEncodingBuffer(final byte[] array) {
        if (array != null) {
            this._verifyRelease(array, this._writeEncodingBuffer);
            this._writeEncodingBuffer = null;
            this._bufferRecycler.releaseByteBuffer(1, array);
        }
    }
    
    public void releaseBase64Buffer(final byte[] array) {
        if (array != null) {
            this._verifyRelease(array, this._base64Buffer);
            this._base64Buffer = null;
            this._bufferRecycler.releaseByteBuffer(3, array);
        }
    }
    
    public void releaseTokenBuffer(final char[] array) {
        if (array != null) {
            this._verifyRelease(array, this._tokenCBuffer);
            this._tokenCBuffer = null;
            this._bufferRecycler.releaseCharBuffer(0, array);
        }
    }
    
    public void releaseConcatBuffer(final char[] array) {
        if (array != null) {
            this._verifyRelease(array, this._concatCBuffer);
            this._concatCBuffer = null;
            this._bufferRecycler.releaseCharBuffer(1, array);
        }
    }
    
    public void releaseNameCopyBuffer(final char[] array) {
        if (array != null) {
            this._verifyRelease(array, this._nameCopyBuffer);
            this._nameCopyBuffer = null;
            this._bufferRecycler.releaseCharBuffer(3, array);
        }
    }
    
    protected final void _verifyAlloc(final Object o) {
        if (o != null) {
            throw new IllegalStateException("Trying to call same allocXxx() method second time");
        }
    }
    
    protected final void _verifyRelease(final byte[] array, final byte[] array2) {
        if (array != array2 && array.length < array2.length) {
            throw this.wrongBuf();
        }
    }
    
    protected final void _verifyRelease(final char[] array, final char[] array2) {
        if (array != array2 && array.length < array2.length) {
            throw this.wrongBuf();
        }
    }
    
    private IllegalArgumentException wrongBuf() {
        return new IllegalArgumentException("Trying to release buffer smaller than original");
    }
}
