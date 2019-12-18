package kotlin.io;

import kotlin.collections.*;
import kotlin.*;
import java.io.*;
import java.util.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 1, d1 = { "\u0000'\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\t\u0010\u0011\u001a\u00020\u0003H\u0096\u0002J\b\u0010\b\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0014H\u0002R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0005\"\u0004\b\u0010\u0010\u0007¨\u0006\u0015" }, d2 = { "kotlin/io/ByteStreamsKt$iterator$1", "Lkotlin/collections/ByteIterator;", "finished", "", "getFinished", "()Z", "setFinished", "(Z)V", "nextByte", "", "getNextByte", "()I", "setNextByte", "(I)V", "nextPrepared", "getNextPrepared", "setNextPrepared", "hasNext", "", "prepareNext", "", "kotlin-stdlib" })
public static final class ByteStreamsKt$iterator$1 extends ByteIterator {
    private int nextByte = -1;
    private boolean nextPrepared;
    private boolean finished;
    final BufferedInputStream $this_iterator;
    
    public final int getNextByte() {
        return this.nextByte;
    }
    
    public final void setNextByte(final int nextByte) {
        this.nextByte = nextByte;
    }
    
    public final boolean getNextPrepared() {
        return this.nextPrepared;
    }
    
    public final void setNextPrepared(final boolean nextPrepared) {
        this.nextPrepared = nextPrepared;
    }
    
    public final boolean getFinished() {
        return this.finished;
    }
    
    public final void setFinished(final boolean finished) {
        this.finished = finished;
    }
    
    private final void prepareNext() {
        if (!this.nextPrepared && !this.finished) {
            this.nextByte = this.$this_iterator.read();
            this.nextPrepared = true;
            this.finished = (this.nextByte == -1);
        }
    }
    
    @Override
    public boolean hasNext() {
        this.prepareNext();
        return !this.finished;
    }
    
    @Override
    public byte nextByte() {
        this.prepareNext();
        if (this.finished) {
            throw new NoSuchElementException("Input stream is over.");
        }
        final byte b = (byte)this.nextByte;
        this.nextPrepared = false;
        return b;
    }
    
    ByteStreamsKt$iterator$1(final BufferedInputStream $this_iterator) {
        this.$this_iterator = $this_iterator;
        super();
    }
}