package org.jline.keymap;

import java.util.*;
import org.jline.reader.*;
import org.jline.utils.*;
import java.io.*;

public class BindingReader
{
    protected final NonBlockingReader reader;
    protected final StringBuilder opBuffer;
    protected final Deque<Integer> pushBackChar;
    protected String lastBinding;
    
    public BindingReader(final NonBlockingReader reader) {
        super();
        this.opBuffer = new StringBuilder();
        this.pushBackChar = new ArrayDeque<Integer>();
        this.reader = reader;
    }
    
    public <T> T readBinding(final KeyMap<T> keyMap) {
        return this.readBinding(keyMap, null, true);
    }
    
    public <T> T readBinding(final KeyMap<T> keyMap, final KeyMap<T> keyMap2) {
        return this.readBinding(keyMap, keyMap2, true);
    }
    
    public <T> T readBinding(final KeyMap<T> keyMap, final KeyMap<T> keyMap2, final boolean b) {
        this.lastBinding = null;
        T t = null;
        final int[] array = { 0 };
        int n = 0;
        while (true) {
            if (keyMap2 != null) {
                t = keyMap2.getBound(this.opBuffer, array);
            }
            if (t == null && (keyMap2 == null || array[0] >= 0)) {
                t = keyMap.getBound(this.opBuffer, array);
            }
            if (t != null) {
                if (array[0] >= 0) {
                    this.runMacro(this.opBuffer.substring(this.opBuffer.length() - array[0]));
                    this.opBuffer.setLength(this.opBuffer.length() - array[0]);
                }
                else {
                    final long ambiguousTimeout = keyMap.getAmbiguousTimeout();
                    if (ambiguousTimeout > 0L && this.peekCharacter(ambiguousTimeout) != -2) {
                        t = null;
                    }
                }
                if (t != null) {
                    this.lastBinding = this.opBuffer.toString();
                    this.opBuffer.setLength(0);
                    return t;
                }
            }
            else if (array[0] > 0) {
                final int codePoint = this.opBuffer.codePointAt(0);
                final String substring = this.opBuffer.substring(Character.charCount(codePoint));
                this.lastBinding = this.opBuffer.substring(0, Character.charCount(codePoint));
                t = ((codePoint >= 128) ? keyMap.getUnicode() : keyMap.getNomatch());
                this.opBuffer.setLength(0);
                this.opBuffer.append(substring);
                if (t != null) {
                    return t;
                }
            }
            if (!b && n != 0) {
                return null;
            }
            final int character = this.readCharacter();
            if (character == -1) {
                return null;
            }
            this.opBuffer.appendCodePoint(character);
            n = 1;
        }
    }
    
    public int readCharacter() {
        if (!this.pushBackChar.isEmpty()) {
            return this.pushBackChar.pop();
        }
        try {
            int i = -2;
            int n = 0;
            while (i == -2) {
                i = this.reader.read(100L);
                if (i >= 0 && Character.isHighSurrogate((char)i)) {
                    n = i;
                    i = -2;
                }
            }
            return (n != 0) ? Character.toCodePoint((char)n, (char)i) : i;
        }
        catch (ClosedException ex) {
            throw new EndOfFileException(ex);
        }
        catch (IOException ex2) {
            throw new IOError(ex2);
        }
    }
    
    public int peekCharacter(final long n) {
        if (!this.pushBackChar.isEmpty()) {
            return this.pushBackChar.peek();
        }
        try {
            return this.reader.peek(n);
        }
        catch (IOException ex) {
            throw new IOError(ex);
        }
    }
    
    public void runMacro(final String s) {
        s.codePoints().forEachOrdered(this.pushBackChar::addLast);
    }
    
    public String getCurrentBuffer() {
        return this.opBuffer.toString();
    }
    
    public String getLastBinding() {
        return this.lastBinding;
    }
}
