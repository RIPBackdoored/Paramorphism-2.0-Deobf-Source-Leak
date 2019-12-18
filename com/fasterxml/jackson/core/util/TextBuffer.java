package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.io.NumberInput;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

public final class TextBuffer {
   static final char[] NO_CHARS = new char[0];
   static final int MIN_SEGMENT_LEN = 1000;
   static final int MAX_SEGMENT_LEN = 262144;
   private final BufferRecycler _allocator;
   private char[] _inputBuffer;
   private int _inputStart;
   private int _inputLen;
   private ArrayList _segments;
   private boolean _hasSegments;
   private int _segmentSize;
   private char[] _currentSegment;
   private int _currentSize;
   private String _resultString;
   private char[] _resultArray;

   public TextBuffer(BufferRecycler var1) {
      super();
      this._allocator = var1;
   }

   public void releaseBuffers() {
      if (this._allocator == null) {
         this.resetWithEmpty();
      } else if (this._currentSegment != null) {
         this.resetWithEmpty();
         char[] var1 = this._currentSegment;
         this._currentSegment = null;
         this._allocator.releaseCharBuffer(2, var1);
      }

   }

   public void resetWithEmpty() {
      this._inputStart = -1;
      this._currentSize = 0;
      this._inputLen = 0;
      this._inputBuffer = null;
      this._resultString = null;
      this._resultArray = null;
      if (this._hasSegments) {
         this.clearSegments();
      }

   }

   public void resetWith(char var1) {
      this._inputStart = -1;
      this._inputLen = 0;
      this._resultString = null;
      this._resultArray = null;
      if (this._hasSegments) {
         this.clearSegments();
      } else if (this._currentSegment == null) {
         this._currentSegment = this.buf(1);
      }

      this._currentSegment[0] = var1;
      this._currentSize = this._segmentSize = 1;
   }

   public void resetWithShared(char[] var1, int var2, int var3) {
      this._resultString = null;
      this._resultArray = null;
      this._inputBuffer = var1;
      this._inputStart = var2;
      this._inputLen = var3;
      if (this._hasSegments) {
         this.clearSegments();
      }

   }

   public void resetWithCopy(char[] var1, int var2, int var3) {
      this._inputBuffer = null;
      this._inputStart = -1;
      this._inputLen = 0;
      this._resultString = null;
      this._resultArray = null;
      if (this._hasSegments) {
         this.clearSegments();
      } else if (this._currentSegment == null) {
         this._currentSegment = this.buf(var3);
      }

      this._currentSize = this._segmentSize = 0;
      this.append(var1, var2, var3);
   }

   public void resetWithCopy(String var1, int var2, int var3) {
      this._inputBuffer = null;
      this._inputStart = -1;
      this._inputLen = 0;
      this._resultString = null;
      this._resultArray = null;
      if (this._hasSegments) {
         this.clearSegments();
      } else if (this._currentSegment == null) {
         this._currentSegment = this.buf(var3);
      }

      this._currentSize = this._segmentSize = 0;
      this.append(var1, var2, var3);
   }

   public void resetWithString(String var1) {
      this._inputBuffer = null;
      this._inputStart = -1;
      this._inputLen = 0;
      this._resultString = var1;
      this._resultArray = null;
      if (this._hasSegments) {
         this.clearSegments();
      }

      this._currentSize = 0;
   }

   public char[] getBufferWithoutReset() {
      return this._currentSegment;
   }

   private char[] buf(int var1) {
      return this._allocator != null ? this._allocator.allocCharBuffer(2, var1) : new char[Math.max(var1, 1000)];
   }

   private void clearSegments() {
      this._hasSegments = false;
      this._segments.clear();
      this._currentSize = this._segmentSize = 0;
   }

   public int size() {
      if (this._inputStart >= 0) {
         return this._inputLen;
      } else if (this._resultArray != null) {
         return this._resultArray.length;
      } else {
         return this._resultString != null ? this._resultString.length() : this._segmentSize + this._currentSize;
      }
   }

   public int getTextOffset() {
      return this._inputStart >= 0 ? this._inputStart : 0;
   }

   public boolean hasTextAsCharacters() {
      if (this._inputStart < 0 && this._resultArray == null) {
         return this._resultString == null;
      } else {
         return true;
      }
   }

   public char[] getTextBuffer() {
      if (this._inputStart >= 0) {
         return this._inputBuffer;
      } else if (this._resultArray != null) {
         return this._resultArray;
      } else if (this._resultString != null) {
         return this._resultArray = this._resultString.toCharArray();
      } else if (!this._hasSegments) {
         return this._currentSegment == null ? NO_CHARS : this._currentSegment;
      } else {
         return this.contentsAsArray();
      }
   }

   public String contentsAsString() {
      if (this._resultString == null) {
         if (this._resultArray != null) {
            this._resultString = new String(this._resultArray);
         } else if (this._inputStart >= 0) {
            if (this._inputLen < 1) {
               return this._resultString = "";
            }

            this._resultString = new String(this._inputBuffer, this._inputStart, this._inputLen);
         } else {
            int var1 = this._segmentSize;
            int var2 = this._currentSize;
            if (var1 == 0) {
               this._resultString = var2 == 0 ? "" : new String(this._currentSegment, 0, var2);
            } else {
               StringBuilder var3 = new StringBuilder(var1 + var2);
               if (this._segments != null) {
                  int var4 = 0;

                  for(int var5 = this._segments.size(); var4 < var5; ++var4) {
                     char[] var6 = (char[])this._segments.get(var4);
                     var3.append(var6, 0, var6.length);
                  }
               }

               var3.append(this._currentSegment, 0, this._currentSize);
               this._resultString = var3.toString();
            }
         }
      }

      return this._resultString;
   }

   public char[] contentsAsArray() {
      char[] var1 = this._resultArray;
      if (var1 == null) {
         this._resultArray = var1 = this.resultArray();
      }

      return var1;
   }

   public BigDecimal contentsAsDecimal() throws NumberFormatException {
      if (this._resultArray != null) {
         return NumberInput.parseBigDecimal(this._resultArray);
      } else if (this._inputStart >= 0 && this._inputBuffer != null) {
         return NumberInput.parseBigDecimal(this._inputBuffer, this._inputStart, this._inputLen);
      } else {
         return this._segmentSize == 0 && this._currentSegment != null ? NumberInput.parseBigDecimal(this._currentSegment, 0, this._currentSize) : NumberInput.parseBigDecimal(this.contentsAsArray());
      }
   }

   public double contentsAsDouble() throws NumberFormatException {
      return NumberInput.parseDouble(this.contentsAsString());
   }

   public int contentsAsInt(boolean var1) {
      if (this._inputStart >= 0 && this._inputBuffer != null) {
         return var1 ? -NumberInput.parseInt(this._inputBuffer, this._inputStart + 1, this._inputLen - 1) : NumberInput.parseInt(this._inputBuffer, this._inputStart, this._inputLen);
      } else {
         return var1 ? -NumberInput.parseInt(this._currentSegment, 1, this._currentSize - 1) : NumberInput.parseInt(this._currentSegment, 0, this._currentSize);
      }
   }

   public long contentsAsLong(boolean var1) {
      if (this._inputStart >= 0 && this._inputBuffer != null) {
         return var1 ? -NumberInput.parseLong(this._inputBuffer, this._inputStart + 1, this._inputLen - 1) : NumberInput.parseLong(this._inputBuffer, this._inputStart, this._inputLen);
      } else {
         return var1 ? -NumberInput.parseLong(this._currentSegment, 1, this._currentSize - 1) : NumberInput.parseLong(this._currentSegment, 0, this._currentSize);
      }
   }

   public int contentsToWriter(Writer var1) throws IOException {
      if (this._resultArray != null) {
         var1.write(this._resultArray);
         return this._resultArray.length;
      } else if (this._resultString != null) {
         var1.write(this._resultString);
         return this._resultString.length();
      } else {
         int var2;
         if (this._inputStart >= 0) {
            var2 = this._inputLen;
            if (var2 > 0) {
               var1.write(this._inputBuffer, this._inputStart, var2);
            }

            return var2;
         } else {
            var2 = 0;
            int var3;
            if (this._segments != null) {
               var3 = 0;

               for(int var4 = this._segments.size(); var3 < var4; ++var3) {
                  char[] var5 = (char[])this._segments.get(var3);
                  int var6 = var5.length;
                  var1.write(var5, 0, var6);
                  var2 += var6;
               }
            }

            var3 = this._currentSize;
            if (var3 > 0) {
               var1.write(this._currentSegment, 0, var3);
               var2 += var3;
            }

            return var2;
         }
      }
   }

   public void ensureNotShared() {
      if (this._inputStart >= 0) {
         this.unshare(16);
      }

   }

   public void append(char var1) {
      if (this._inputStart >= 0) {
         this.unshare(16);
      }

      this._resultString = null;
      this._resultArray = null;
      char[] var2 = this._currentSegment;
      if (this._currentSize >= var2.length) {
         this.expand(1);
         var2 = this._currentSegment;
      }

      var2[this._currentSize++] = var1;
   }

   public void append(char[] var1, int var2, int var3) {
      if (this._inputStart >= 0) {
         this.unshare(var3);
      }

      this._resultString = null;
      this._resultArray = null;
      char[] var4 = this._currentSegment;
      int var5 = var4.length - this._currentSize;
      if (var5 >= var3) {
         System.arraycopy(var1, var2, var4, this._currentSize, var3);
         this._currentSize += var3;
      } else {
         if (var5 > 0) {
            System.arraycopy(var1, var2, var4, this._currentSize, var5);
            var2 += var5;
            var3 -= var5;
         }

         do {
            this.expand(var3);
            int var6 = Math.min(this._currentSegment.length, var3);
            System.arraycopy(var1, var2, this._currentSegment, 0, var6);
            this._currentSize += var6;
            var2 += var6;
            var3 -= var6;
         } while(var3 > 0);

      }
   }

   public void append(String var1, int var2, int var3) {
      if (this._inputStart >= 0) {
         this.unshare(var3);
      }

      this._resultString = null;
      this._resultArray = null;
      char[] var4 = this._currentSegment;
      int var5 = var4.length - this._currentSize;
      if (var5 >= var3) {
         var1.getChars(var2, var2 + var3, var4, this._currentSize);
         this._currentSize += var3;
      } else {
         if (var5 > 0) {
            var1.getChars(var2, var2 + var5, var4, this._currentSize);
            var3 -= var5;
            var2 += var5;
         }

         do {
            this.expand(var3);
            int var6 = Math.min(this._currentSegment.length, var3);
            var1.getChars(var2, var2 + var6, this._currentSegment, 0);
            this._currentSize += var6;
            var2 += var6;
            var3 -= var6;
         } while(var3 > 0);

      }
   }

   public char[] getCurrentSegment() {
      if (this._inputStart >= 0) {
         this.unshare(1);
      } else {
         char[] var1 = this._currentSegment;
         if (var1 == null) {
            this._currentSegment = this.buf(0);
         } else if (this._currentSize >= var1.length) {
            this.expand(1);
         }
      }

      return this._currentSegment;
   }

   public char[] emptyAndGetCurrentSegment() {
      this._inputStart = -1;
      this._currentSize = 0;
      this._inputLen = 0;
      this._inputBuffer = null;
      this._resultString = null;
      this._resultArray = null;
      if (this._hasSegments) {
         this.clearSegments();
      }

      char[] var1 = this._currentSegment;
      if (var1 == null) {
         this._currentSegment = var1 = this.buf(0);
      }

      return var1;
   }

   public int getCurrentSegmentSize() {
      return this._currentSize;
   }

   public void setCurrentLength(int var1) {
      this._currentSize = var1;
   }

   public String setCurrentAndReturn(int var1) {
      this._currentSize = var1;
      if (this._segmentSize > 0) {
         return this.contentsAsString();
      } else {
         int var2 = this._currentSize;
         String var3 = var2 == 0 ? "" : new String(this._currentSegment, 0, var2);
         this._resultString = var3;
         return var3;
      }
   }

   public char[] finishCurrentSegment() {
      if (this._segments == null) {
         this._segments = new ArrayList();
      }

      this._hasSegments = true;
      this._segments.add(this._currentSegment);
      int var1 = this._currentSegment.length;
      this._segmentSize += var1;
      this._currentSize = 0;
      int var2 = var1 + (var1 >> 1);
      if (var2 < 1000) {
         var2 = 1000;
      } else if (var2 > 262144) {
         var2 = 262144;
      }

      char[] var3 = this.carr(var2);
      this._currentSegment = var3;
      return var3;
   }

   public char[] expandCurrentSegment() {
      char[] var1 = this._currentSegment;
      int var2 = var1.length;
      int var3 = var2 + (var2 >> 1);
      if (var3 > 262144) {
         var3 = var2 + (var2 >> 2);
      }

      return this._currentSegment = Arrays.copyOf(var1, var3);
   }

   public char[] expandCurrentSegment(int var1) {
      char[] var2 = this._currentSegment;
      if (var2.length >= var1) {
         return var2;
      } else {
         this._currentSegment = var2 = Arrays.copyOf(var2, var1);
         return var2;
      }
   }

   public String toString() {
      return this.contentsAsString();
   }

   private void unshare(int var1) {
      int var2 = this._inputLen;
      this._inputLen = 0;
      char[] var3 = this._inputBuffer;
      this._inputBuffer = null;
      int var4 = this._inputStart;
      this._inputStart = -1;
      int var5 = var2 + var1;
      if (this._currentSegment == null || var5 > this._currentSegment.length) {
         this._currentSegment = this.buf(var5);
      }

      if (var2 > 0) {
         System.arraycopy(var3, var4, this._currentSegment, 0, var2);
      }

      this._segmentSize = 0;
      this._currentSize = var2;
   }

   private void expand(int var1) {
      if (this._segments == null) {
         this._segments = new ArrayList();
      }

      char[] var2 = this._currentSegment;
      this._hasSegments = true;
      this._segments.add(var2);
      this._segmentSize += var2.length;
      this._currentSize = 0;
      int var3 = var2.length;
      int var4 = var3 + (var3 >> 1);
      if (var4 < 1000) {
         var4 = 1000;
      } else if (var4 > 262144) {
         var4 = 262144;
      }

      this._currentSegment = this.carr(var4);
   }

   private char[] resultArray() {
      if (this._resultString != null) {
         return this._resultString.toCharArray();
      } else {
         int var1;
         int var2;
         if (this._inputStart >= 0) {
            var1 = this._inputLen;
            if (var1 < 1) {
               return NO_CHARS;
            } else {
               var2 = this._inputStart;
               return var2 == 0 ? Arrays.copyOf(this._inputBuffer, var1) : Arrays.copyOfRange(this._inputBuffer, var2, var2 + var1);
            }
         } else {
            var1 = this.size();
            if (var1 < 1) {
               return NO_CHARS;
            } else {
               var2 = 0;
               char[] var3 = this.carr(var1);
               if (this._segments != null) {
                  int var4 = 0;

                  for(int var5 = this._segments.size(); var4 < var5; ++var4) {
                     char[] var6 = (char[])this._segments.get(var4);
                     int var7 = var6.length;
                     System.arraycopy(var6, 0, var3, var2, var7);
                     var2 += var7;
                  }
               }

               System.arraycopy(this._currentSegment, 0, var3, var2, this._currentSize);
               return var3;
            }
         }
      }
   }

   private char[] carr(int var1) {
      return new char[var1];
   }
}
