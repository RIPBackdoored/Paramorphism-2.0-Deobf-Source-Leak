package com.fasterxml.jackson.core.format;

import com.fasterxml.jackson.core.JsonFactory;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public class InputAccessor$Std implements InputAccessor {
   protected final InputStream _in;
   protected final byte[] _buffer;
   protected final int _bufferedStart;
   protected int _bufferedEnd;
   protected int _ptr;

   public InputAccessor$Std(InputStream var1, byte[] var2) {
      super();
      this._in = var1;
      this._buffer = var2;
      this._bufferedStart = 0;
      this._ptr = 0;
      this._bufferedEnd = 0;
   }

   public InputAccessor$Std(byte[] var1) {
      super();
      this._in = null;
      this._buffer = var1;
      this._bufferedStart = 0;
      this._bufferedEnd = var1.length;
   }

   public InputAccessor$Std(byte[] var1, int var2, int var3) {
      super();
      this._in = null;
      this._buffer = var1;
      this._ptr = var2;
      this._bufferedStart = var2;
      this._bufferedEnd = var2 + var3;
   }

   public boolean hasMoreBytes() throws IOException {
      if (this._ptr < this._bufferedEnd) {
         return true;
      } else if (this._in == null) {
         return false;
      } else {
         int var1 = this._buffer.length - this._ptr;
         if (var1 < 1) {
            return false;
         } else {
            int var2 = this._in.read(this._buffer, this._ptr, var1);
            if (var2 <= 0) {
               return false;
            } else {
               this._bufferedEnd += var2;
               return true;
            }
         }
      }
   }

   public byte nextByte() throws IOException {
      if (this._ptr >= this._bufferedEnd && !this.hasMoreBytes()) {
         throw new EOFException("Failed auto-detect: could not read more than " + this._ptr + " bytes (max buffer size: " + this._buffer.length + ")");
      } else {
         return this._buffer[this._ptr++];
      }
   }

   public void reset() {
      this._ptr = this._bufferedStart;
   }

   public DataFormatMatcher createMatcher(JsonFactory var1, MatchStrength var2) {
      return new DataFormatMatcher(this._in, this._buffer, this._bufferedStart, this._bufferedEnd - this._bufferedStart, var1, var2);
   }
}
