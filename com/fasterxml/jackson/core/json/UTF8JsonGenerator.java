package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator$Feature;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.io.NumberOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;

public class UTF8JsonGenerator extends JsonGeneratorImpl {
   private static final byte BYTE_u = 117;
   private static final byte BYTE_0 = 48;
   private static final byte BYTE_LBRACKET = 91;
   private static final byte BYTE_RBRACKET = 93;
   private static final byte BYTE_LCURLY = 123;
   private static final byte BYTE_RCURLY = 125;
   private static final byte BYTE_BACKSLASH = 92;
   private static final byte BYTE_COMMA = 44;
   private static final byte BYTE_COLON = 58;
   private static final int MAX_BYTES_TO_BUFFER = 512;
   private static final byte[] HEX_CHARS = CharTypes.copyHexBytes();
   private static final byte[] NULL_BYTES = new byte[]{110, 117, 108, 108};
   private static final byte[] TRUE_BYTES = new byte[]{116, 114, 117, 101};
   private static final byte[] FALSE_BYTES = new byte[]{102, 97, 108, 115, 101};
   protected final OutputStream _outputStream;
   protected byte _quoteChar = 34;
   protected byte[] _outputBuffer;
   protected int _outputTail;
   protected final int _outputEnd;
   protected final int _outputMaxContiguous;
   protected char[] _charBuffer;
   protected final int _charBufferLength;
   protected byte[] _entityBuffer;
   protected boolean _bufferRecyclable;

   public UTF8JsonGenerator(IOContext var1, int var2, ObjectCodec var3, OutputStream var4) {
      super(var1, var2, var3);
      this._outputStream = var4;
      this._bufferRecyclable = true;
      this._outputBuffer = var1.allocWriteEncodingBuffer();
      this._outputEnd = this._outputBuffer.length;
      this._outputMaxContiguous = this._outputEnd >> 3;
      this._charBuffer = var1.allocConcatBuffer();
      this._charBufferLength = this._charBuffer.length;
      if (this.isEnabled(JsonGenerator$Feature.ESCAPE_NON_ASCII)) {
         this.setHighestNonEscapedChar(127);
      }

   }

   public UTF8JsonGenerator(IOContext var1, int var2, ObjectCodec var3, OutputStream var4, byte[] var5, int var6, boolean var7) {
      super(var1, var2, var3);
      this._outputStream = var4;
      this._bufferRecyclable = var7;
      this._outputTail = var6;
      this._outputBuffer = var5;
      this._outputEnd = this._outputBuffer.length;
      this._outputMaxContiguous = this._outputEnd >> 3;
      this._charBuffer = var1.allocConcatBuffer();
      this._charBufferLength = this._charBuffer.length;
   }

   public Object getOutputTarget() {
      return this._outputStream;
   }

   public int getOutputBuffered() {
      return this._outputTail;
   }

   public void writeFieldName(String var1) throws IOException {
      if (this._cfgPrettyPrinter != null) {
         this._writePPFieldName(var1);
      } else {
         int var2 = this._writeContext.writeFieldName(var1);
         if (var2 == 4) {
            this._reportError("Can not write a field name, expecting a value");
         }

         if (var2 == 1) {
            if (this._outputTail >= this._outputEnd) {
               this._flushBuffer();
            }

            this._outputBuffer[this._outputTail++] = 44;
         }

         if (this._cfgUnqNames) {
            this._writeStringSegments(var1, false);
         } else {
            int var3 = var1.length();
            if (var3 > this._charBufferLength) {
               this._writeStringSegments(var1, true);
            } else {
               if (this._outputTail >= this._outputEnd) {
                  this._flushBuffer();
               }

               this._outputBuffer[this._outputTail++] = this._quoteChar;
               if (var3 <= this._outputMaxContiguous) {
                  if (this._outputTail + var3 > this._outputEnd) {
                     this._flushBuffer();
                  }

                  this._writeStringSegment((String)var1, 0, var3);
               } else {
                  this._writeStringSegments((String)var1, 0, var3);
               }

               if (this._outputTail >= this._outputEnd) {
                  this._flushBuffer();
               }

               this._outputBuffer[this._outputTail++] = this._quoteChar;
            }
         }
      }
   }

   public void writeFieldName(SerializableString var1) throws IOException {
      if (this._cfgPrettyPrinter != null) {
         this._writePPFieldName(var1);
      } else {
         int var2 = this._writeContext.writeFieldName(var1.getValue());
         if (var2 == 4) {
            this._reportError("Can not write a field name, expecting a value");
         }

         if (var2 == 1) {
            if (this._outputTail >= this._outputEnd) {
               this._flushBuffer();
            }

            this._outputBuffer[this._outputTail++] = 44;
         }

         if (this._cfgUnqNames) {
            this._writeUnq(var1);
         } else {
            if (this._outputTail >= this._outputEnd) {
               this._flushBuffer();
            }

            this._outputBuffer[this._outputTail++] = this._quoteChar;
            int var3 = var1.appendQuotedUTF8(this._outputBuffer, this._outputTail);
            if (var3 < 0) {
               this._writeBytes(var1.asQuotedUTF8());
            } else {
               this._outputTail += var3;
            }

            if (this._outputTail >= this._outputEnd) {
               this._flushBuffer();
            }

            this._outputBuffer[this._outputTail++] = this._quoteChar;
         }
      }
   }

   private final void _writeUnq(SerializableString var1) throws IOException {
      int var2 = var1.appendQuotedUTF8(this._outputBuffer, this._outputTail);
      if (var2 < 0) {
         this._writeBytes(var1.asQuotedUTF8());
      } else {
         this._outputTail += var2;
      }

   }

   public final void writeStartArray() throws IOException {
      this._verifyValueWrite("start an array");
      this._writeContext = this._writeContext.createChildArrayContext();
      if (this._cfgPrettyPrinter != null) {
         this._cfgPrettyPrinter.writeStartArray(this);
      } else {
         if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         this._outputBuffer[this._outputTail++] = 91;
      }

   }

   public final void writeEndArray() throws IOException {
      if (!this._writeContext.inArray()) {
         this._reportError("Current context not Array but " + this._writeContext.typeDesc());
      }

      if (this._cfgPrettyPrinter != null) {
         this._cfgPrettyPrinter.writeEndArray(this, this._writeContext.getEntryCount());
      } else {
         if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         this._outputBuffer[this._outputTail++] = 93;
      }

      this._writeContext = this._writeContext.clearAndGetParent();
   }

   public final void writeStartObject() throws IOException {
      this._verifyValueWrite("start an object");
      this._writeContext = this._writeContext.createChildObjectContext();
      if (this._cfgPrettyPrinter != null) {
         this._cfgPrettyPrinter.writeStartObject(this);
      } else {
         if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         this._outputBuffer[this._outputTail++] = 123;
      }

   }

   public void writeStartObject(Object var1) throws IOException {
      this._verifyValueWrite("start an object");
      JsonWriteContext var2 = this._writeContext.createChildObjectContext();
      this._writeContext = var2;
      if (var1 != null) {
         var2.setCurrentValue(var1);
      }

      if (this._cfgPrettyPrinter != null) {
         this._cfgPrettyPrinter.writeStartObject(this);
      } else {
         if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         this._outputBuffer[this._outputTail++] = 123;
      }

   }

   public final void writeEndObject() throws IOException {
      if (!this._writeContext.inObject()) {
         this._reportError("Current context not Object but " + this._writeContext.typeDesc());
      }

      if (this._cfgPrettyPrinter != null) {
         this._cfgPrettyPrinter.writeEndObject(this, this._writeContext.getEntryCount());
      } else {
         if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         this._outputBuffer[this._outputTail++] = 125;
      }

      this._writeContext = this._writeContext.clearAndGetParent();
   }

   protected final void _writePPFieldName(String var1) throws IOException {
      int var2 = this._writeContext.writeFieldName(var1);
      if (var2 == 4) {
         this._reportError("Can not write a field name, expecting a value");
      }

      if (var2 == 1) {
         this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
      } else {
         this._cfgPrettyPrinter.beforeObjectEntries(this);
      }

      if (this._cfgUnqNames) {
         this._writeStringSegments(var1, false);
      } else {
         int var3 = var1.length();
         if (var3 > this._charBufferLength) {
            this._writeStringSegments(var1, true);
         } else {
            if (this._outputTail >= this._outputEnd) {
               this._flushBuffer();
            }

            this._outputBuffer[this._outputTail++] = this._quoteChar;
            var1.getChars(0, var3, this._charBuffer, 0);
            if (var3 <= this._outputMaxContiguous) {
               if (this._outputTail + var3 > this._outputEnd) {
                  this._flushBuffer();
               }

               this._writeStringSegment((char[])this._charBuffer, 0, var3);
            } else {
               this._writeStringSegments((char[])this._charBuffer, 0, var3);
            }

            if (this._outputTail >= this._outputEnd) {
               this._flushBuffer();
            }

            this._outputBuffer[this._outputTail++] = this._quoteChar;
         }
      }
   }

   protected final void _writePPFieldName(SerializableString var1) throws IOException {
      int var2 = this._writeContext.writeFieldName(var1.getValue());
      if (var2 == 4) {
         this._reportError("Can not write a field name, expecting a value");
      }

      if (var2 == 1) {
         this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
      } else {
         this._cfgPrettyPrinter.beforeObjectEntries(this);
      }

      boolean var3 = !this._cfgUnqNames;
      if (var3) {
         if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         this._outputBuffer[this._outputTail++] = this._quoteChar;
      }

      this._writeBytes(var1.asQuotedUTF8());
      if (var3) {
         if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         this._outputBuffer[this._outputTail++] = this._quoteChar;
      }

   }

   public void writeString(String var1) throws IOException {
      this._verifyValueWrite("write a string");
      if (var1 == null) {
         this._writeNull();
      } else {
         int var2 = var1.length();
         if (var2 > this._outputMaxContiguous) {
            this._writeStringSegments(var1, true);
         } else {
            if (this._outputTail + var2 >= this._outputEnd) {
               this._flushBuffer();
            }

            this._outputBuffer[this._outputTail++] = this._quoteChar;
            this._writeStringSegment((String)var1, 0, var2);
            if (this._outputTail >= this._outputEnd) {
               this._flushBuffer();
            }

            this._outputBuffer[this._outputTail++] = this._quoteChar;
         }
      }
   }

   public void writeString(Reader var1, int var2) throws IOException {
      this._verifyValueWrite("write a string");
      if (var1 == null) {
         this._reportError("null reader");
      }

      int var3 = var2 >= 0 ? var2 : 0;
      char[] var4 = this._charBuffer;
      if (this._outputTail + var2 >= this._outputEnd) {
         this._flushBuffer();
      }

      int var6;
      for(this._outputBuffer[this._outputTail++] = this._quoteChar; var3 > 0; var3 -= var6) {
         int var5 = Math.min(var3, var4.length);
         var6 = var1.read(var4, 0, var5);
         if (var6 <= 0) {
            break;
         }

         if (this._outputTail + var2 >= this._outputEnd) {
            this._flushBuffer();
         }

         this._writeStringSegments((char[])var4, 0, var6);
      }

      if (this._outputTail + var2 >= this._outputEnd) {
         this._flushBuffer();
      }

      this._outputBuffer[this._outputTail++] = this._quoteChar;
      if (var3 > 0 && var2 >= 0) {
         this._reportError("Didn't read enough from reader");
      }

   }

   public void writeString(char[] var1, int var2, int var3) throws IOException {
      this._verifyValueWrite("write a string");
      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      this._outputBuffer[this._outputTail++] = this._quoteChar;
      if (var3 <= this._outputMaxContiguous) {
         if (this._outputTail + var3 > this._outputEnd) {
            this._flushBuffer();
         }

         this._writeStringSegment(var1, var2, var3);
      } else {
         this._writeStringSegments(var1, var2, var3);
      }

      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      this._outputBuffer[this._outputTail++] = this._quoteChar;
   }

   public final void writeString(SerializableString var1) throws IOException {
      this._verifyValueWrite("write a string");
      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      this._outputBuffer[this._outputTail++] = this._quoteChar;
      int var2 = var1.appendQuotedUTF8(this._outputBuffer, this._outputTail);
      if (var2 < 0) {
         this._writeBytes(var1.asQuotedUTF8());
      } else {
         this._outputTail += var2;
      }

      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      this._outputBuffer[this._outputTail++] = this._quoteChar;
   }

   public void writeRawUTF8String(byte[] var1, int var2, int var3) throws IOException {
      this._verifyValueWrite("write a string");
      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      this._outputBuffer[this._outputTail++] = this._quoteChar;
      this._writeBytes(var1, var2, var3);
      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      this._outputBuffer[this._outputTail++] = this._quoteChar;
   }

   public void writeUTF8String(byte[] var1, int var2, int var3) throws IOException {
      this._verifyValueWrite("write a string");
      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      this._outputBuffer[this._outputTail++] = this._quoteChar;
      if (var3 <= this._outputMaxContiguous) {
         this._writeUTF8Segment(var1, var2, var3);
      } else {
         this._writeUTF8Segments(var1, var2, var3);
      }

      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      this._outputBuffer[this._outputTail++] = this._quoteChar;
   }

   public void writeRaw(String var1) throws IOException {
      int var2 = var1.length();
      char[] var3 = this._charBuffer;
      if (var2 <= var3.length) {
         var1.getChars(0, var2, var3, 0);
         this.writeRaw((char[])var3, 0, var2);
      } else {
         this.writeRaw((String)var1, 0, var2);
      }

   }

   public void writeRaw(String var1, int var2, int var3) throws IOException {
      char[] var4 = this._charBuffer;
      int var5 = var4.length;
      if (var3 <= var5) {
         var1.getChars(var2, var2 + var3, var4, 0);
         this.writeRaw((char[])var4, 0, var3);
      } else {
         int var6 = Math.min(var5, (this._outputEnd >> 2) + (this._outputEnd >> 4));

         int var8;
         for(int var7 = var6 * 3; var3 > 0; var3 -= var8) {
            var8 = Math.min(var6, var3);
            var1.getChars(var2, var2 + var8, var4, 0);
            if (this._outputTail + var7 > this._outputEnd) {
               this._flushBuffer();
            }

            if (var8 > 1) {
               char var9 = var4[var8 - 1];
               if (var9 >= '\ud800' && var9 <= '\udbff') {
                  --var8;
               }
            }

            this._writeRawSegment(var4, 0, var8);
            var2 += var8;
         }

      }
   }

   public void writeRaw(SerializableString var1) throws IOException {
      byte[] var2 = var1.asUnquotedUTF8();
      if (var2.length > 0) {
         this._writeBytes(var2);
      }

   }

   public void writeRawValue(SerializableString var1) throws IOException {
      this._verifyValueWrite("write a raw (unencoded) value");
      byte[] var2 = var1.asUnquotedUTF8();
      if (var2.length > 0) {
         this._writeBytes(var2);
      }

   }

   public final void writeRaw(char[] var1, int var2, int var3) throws IOException {
      int var4 = var3 + var3 + var3;
      if (this._outputTail + var4 > this._outputEnd) {
         if (this._outputEnd < var4) {
            this._writeSegmentedRaw(var1, var2, var3);
            return;
         }

         this._flushBuffer();
      }

      var3 += var2;

      while(var2 < var3) {
         while(true) {
            char var5 = var1[var2];
            if (var5 > 127) {
               var5 = var1[var2++];
               if (var5 < 2048) {
                  this._outputBuffer[this._outputTail++] = (byte)(192 | var5 >> 6);
                  this._outputBuffer[this._outputTail++] = (byte)(128 | var5 & 63);
               } else {
                  var2 = this._outputRawMultiByteChar(var5, var1, var2, var3);
               }
            } else {
               this._outputBuffer[this._outputTail++] = (byte)var5;
               ++var2;
               if (var2 >= var3) {
                  return;
               }
            }
         }
      }

   }

   public void writeRaw(char var1) throws IOException {
      if (this._outputTail + 3 >= this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var2 = this._outputBuffer;
      if (var1 <= 127) {
         var2[this._outputTail++] = (byte)var1;
      } else if (var1 < 2048) {
         var2[this._outputTail++] = (byte)(192 | var1 >> 6);
         var2[this._outputTail++] = (byte)(128 | var1 & 63);
      } else {
         this._outputRawMultiByteChar(var1, (char[])null, 0, 0);
      }

   }

   private final void _writeSegmentedRaw(char[] var1, int var2, int var3) throws IOException {
      int var4 = this._outputEnd;
      byte[] var5 = this._outputBuffer;
      int var6 = var2 + var3;

      while(var2 < var6) {
         while(true) {
            char var7 = var1[var2];
            if (var7 >= 128) {
               if (this._outputTail + 3 >= this._outputEnd) {
                  this._flushBuffer();
               }

               var7 = var1[var2++];
               if (var7 < 2048) {
                  var5[this._outputTail++] = (byte)(192 | var7 >> 6);
                  var5[this._outputTail++] = (byte)(128 | var7 & 63);
               } else {
                  var2 = this._outputRawMultiByteChar(var7, var1, var2, var6);
               }
            } else {
               if (this._outputTail >= var4) {
                  this._flushBuffer();
               }

               var5[this._outputTail++] = (byte)var7;
               ++var2;
               if (var2 >= var6) {
                  return;
               }
            }
         }
      }

   }

   private void _writeRawSegment(char[] var1, int var2, int var3) throws IOException {
      label24:
      while(true) {
         if (var2 < var3) {
            do {
               char var4 = var1[var2];
               if (var4 > 127) {
                  var4 = var1[var2++];
                  if (var4 < 2048) {
                     this._outputBuffer[this._outputTail++] = (byte)(192 | var4 >> 6);
                     this._outputBuffer[this._outputTail++] = (byte)(128 | var4 & 63);
                     continue label24;
                  }

                  var2 = this._outputRawMultiByteChar(var4, var1, var2, var3);
                  continue label24;
               }

               this._outputBuffer[this._outputTail++] = (byte)var4;
               ++var2;
            } while(var2 < var3);
         }

         return;
      }
   }

   public void writeBinary(Base64Variant var1, byte[] var2, int var3, int var4) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write a binary value");
      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      this._outputBuffer[this._outputTail++] = this._quoteChar;
      this._writeBinary(var1, var2, var3, var3 + var4);
      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      this._outputBuffer[this._outputTail++] = this._quoteChar;
   }

   public int writeBinary(Base64Variant var1, InputStream var2, int var3) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write a binary value");
      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      this._outputBuffer[this._outputTail++] = this._quoteChar;
      byte[] var4 = this._ioContext.allocBase64Buffer();
      boolean var9 = false;

      int var5;
      try {
         var9 = true;
         if (var3 < 0) {
            var5 = this._writeBinary(var1, var2, var4);
            var9 = false;
         } else {
            int var6 = this._writeBinary(var1, var2, var4, var3);
            if (var6 > 0) {
               this._reportError("Too few bytes available: missing " + var6 + " bytes (out of " + var3 + ")");
            }

            var5 = var3;
            var9 = false;
         }
      } finally {
         if (var9) {
            this._ioContext.releaseBase64Buffer(var4);
         }
      }

      this._ioContext.releaseBase64Buffer(var4);
      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      this._outputBuffer[this._outputTail++] = this._quoteChar;
      return var5;
   }

   public void writeNumber(short var1) throws IOException {
      this._verifyValueWrite("write a number");
      if (this._outputTail + 6 >= this._outputEnd) {
         this._flushBuffer();
      }

      if (this._cfgNumbersAsStrings) {
         this._writeQuotedShort(var1);
      } else {
         this._outputTail = NumberOutput.outputInt(var1, (byte[])this._outputBuffer, this._outputTail);
      }
   }

   private final void _writeQuotedShort(short var1) throws IOException {
      if (this._outputTail + 8 >= this._outputEnd) {
         this._flushBuffer();
      }

      this._outputBuffer[this._outputTail++] = this._quoteChar;
      this._outputTail = NumberOutput.outputInt(var1, (byte[])this._outputBuffer, this._outputTail);
      this._outputBuffer[this._outputTail++] = this._quoteChar;
   }

   public void writeNumber(int var1) throws IOException {
      this._verifyValueWrite("write a number");
      if (this._outputTail + 11 >= this._outputEnd) {
         this._flushBuffer();
      }

      if (this._cfgNumbersAsStrings) {
         this._writeQuotedInt(var1);
      } else {
         this._outputTail = NumberOutput.outputInt(var1, this._outputBuffer, this._outputTail);
      }
   }

   private final void _writeQuotedInt(int var1) throws IOException {
      if (this._outputTail + 13 >= this._outputEnd) {
         this._flushBuffer();
      }

      this._outputBuffer[this._outputTail++] = this._quoteChar;
      this._outputTail = NumberOutput.outputInt(var1, this._outputBuffer, this._outputTail);
      this._outputBuffer[this._outputTail++] = this._quoteChar;
   }

   public void writeNumber(long var1) throws IOException {
      this._verifyValueWrite("write a number");
      if (this._cfgNumbersAsStrings) {
         this._writeQuotedLong(var1);
      } else {
         if (this._outputTail + 21 >= this._outputEnd) {
            this._flushBuffer();
         }

         this._outputTail = NumberOutput.outputLong(var1, this._outputBuffer, this._outputTail);
      }
   }

   private final void _writeQuotedLong(long var1) throws IOException {
      if (this._outputTail + 23 >= this._outputEnd) {
         this._flushBuffer();
      }

      this._outputBuffer[this._outputTail++] = this._quoteChar;
      this._outputTail = NumberOutput.outputLong(var1, this._outputBuffer, this._outputTail);
      this._outputBuffer[this._outputTail++] = this._quoteChar;
   }

   public void writeNumber(BigInteger var1) throws IOException {
      this._verifyValueWrite("write a number");
      if (var1 == null) {
         this._writeNull();
      } else if (this._cfgNumbersAsStrings) {
         this._writeQuotedRaw(var1.toString());
      } else {
         this.writeRaw(var1.toString());
      }

   }

   public void writeNumber(double var1) throws IOException {
      if (!this._cfgNumbersAsStrings && (!Double.isNaN(var1) && !Double.isInfinite(var1) || !JsonGenerator$Feature.QUOTE_NON_NUMERIC_NUMBERS.enabledIn(this._features))) {
         this._verifyValueWrite("write a number");
         this.writeRaw(String.valueOf(var1));
      } else {
         this.writeString(String.valueOf(var1));
      }
   }

   public void writeNumber(float var1) throws IOException {
      if (!this._cfgNumbersAsStrings && (!Float.isNaN(var1) && !Float.isInfinite(var1) || !JsonGenerator$Feature.QUOTE_NON_NUMERIC_NUMBERS.enabledIn(this._features))) {
         this._verifyValueWrite("write a number");
         this.writeRaw(String.valueOf(var1));
      } else {
         this.writeString(String.valueOf(var1));
      }
   }

   public void writeNumber(BigDecimal var1) throws IOException {
      this._verifyValueWrite("write a number");
      if (var1 == null) {
         this._writeNull();
      } else if (this._cfgNumbersAsStrings) {
         this._writeQuotedRaw(this._asString(var1));
      } else {
         this.writeRaw(this._asString(var1));
      }

   }

   public void writeNumber(String var1) throws IOException {
      this._verifyValueWrite("write a number");
      if (this._cfgNumbersAsStrings) {
         this._writeQuotedRaw(var1);
      } else {
         this.writeRaw(var1);
      }

   }

   private final void _writeQuotedRaw(String var1) throws IOException {
      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      this._outputBuffer[this._outputTail++] = this._quoteChar;
      this.writeRaw(var1);
      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      this._outputBuffer[this._outputTail++] = this._quoteChar;
   }

   public void writeBoolean(boolean var1) throws IOException {
      this._verifyValueWrite("write a boolean value");
      if (this._outputTail + 5 >= this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var2 = var1 ? TRUE_BYTES : FALSE_BYTES;
      int var3 = var2.length;
      System.arraycopy(var2, 0, this._outputBuffer, this._outputTail, var3);
      this._outputTail += var3;
   }

   public void writeNull() throws IOException {
      this._verifyValueWrite("write a null");
      this._writeNull();
   }

   protected final void _verifyValueWrite(String var1) throws IOException {
      int var2 = this._writeContext.writeValue();
      if (this._cfgPrettyPrinter != null) {
         this._verifyPrettyValueWrite(var1, var2);
      } else {
         byte var3;
         switch(var2) {
         case 0:
         case 4:
         default:
            return;
         case 1:
            var3 = 44;
            break;
         case 2:
            var3 = 58;
            break;
         case 3:
            if (this._rootValueSeparator != null) {
               byte[] var4 = this._rootValueSeparator.asUnquotedUTF8();
               if (var4.length > 0) {
                  this._writeBytes(var4);
               }
            }

            return;
         case 5:
            this._reportCantWriteValueExpectName(var1);
            return;
         }

         if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         this._outputBuffer[this._outputTail++] = var3;
      }
   }

   public void flush() throws IOException {
      this._flushBuffer();
      if (this._outputStream != null && this.isEnabled(JsonGenerator$Feature.FLUSH_PASSED_TO_STREAM)) {
         this._outputStream.flush();
      }

   }

   public void close() throws IOException {
      super.close();
      if (this._outputBuffer != null && this.isEnabled(JsonGenerator$Feature.AUTO_CLOSE_JSON_CONTENT)) {
         label34:
         while(true) {
            while(true) {
               JsonStreamContext var1 = this.getOutputContext();
               if (var1.inArray()) {
                  this.writeEndArray();
               } else {
                  if (!var1.inObject()) {
                     break label34;
                  }

                  this.writeEndObject();
               }
            }
         }
      }

      this._flushBuffer();
      this._outputTail = 0;
      if (this._outputStream != null) {
         if (!this._ioContext.isResourceManaged() && !this.isEnabled(JsonGenerator$Feature.AUTO_CLOSE_TARGET)) {
            if (this.isEnabled(JsonGenerator$Feature.FLUSH_PASSED_TO_STREAM)) {
               this._outputStream.flush();
            }
         } else {
            this._outputStream.close();
         }
      }

      this._releaseBuffers();
   }

   protected void _releaseBuffers() {
      byte[] var1 = this._outputBuffer;
      if (var1 != null && this._bufferRecyclable) {
         this._outputBuffer = null;
         this._ioContext.releaseWriteEncodingBuffer(var1);
      }

      char[] var2 = this._charBuffer;
      if (var2 != null) {
         this._charBuffer = null;
         this._ioContext.releaseConcatBuffer(var2);
      }

   }

   private final void _writeBytes(byte[] var1) throws IOException {
      int var2 = var1.length;
      if (this._outputTail + var2 > this._outputEnd) {
         this._flushBuffer();
         if (var2 > 512) {
            this._outputStream.write(var1, 0, var2);
            return;
         }
      }

      System.arraycopy(var1, 0, this._outputBuffer, this._outputTail, var2);
      this._outputTail += var2;
   }

   private final void _writeBytes(byte[] var1, int var2, int var3) throws IOException {
      if (this._outputTail + var3 > this._outputEnd) {
         this._flushBuffer();
         if (var3 > 512) {
            this._outputStream.write(var1, var2, var3);
            return;
         }
      }

      System.arraycopy(var1, var2, this._outputBuffer, this._outputTail, var3);
      this._outputTail += var3;
   }

   private final void _writeStringSegments(String var1, boolean var2) throws IOException {
      if (var2) {
         if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         this._outputBuffer[this._outputTail++] = this._quoteChar;
      }

      int var3 = var1.length();

      int var5;
      for(int var4 = 0; var3 > 0; var3 -= var5) {
         var5 = Math.min(this._outputMaxContiguous, var3);
         if (this._outputTail + var5 > this._outputEnd) {
            this._flushBuffer();
         }

         this._writeStringSegment(var1, var4, var5);
         var4 += var5;
      }

      if (var2) {
         if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         this._outputBuffer[this._outputTail++] = this._quoteChar;
      }

   }

   private final void _writeStringSegments(char[] var1, int var2, int var3) throws IOException {
      do {
         int var4 = Math.min(this._outputMaxContiguous, var3);
         if (this._outputTail + var4 > this._outputEnd) {
            this._flushBuffer();
         }

         this._writeStringSegment(var1, var2, var4);
         var2 += var4;
         var3 -= var4;
      } while(var3 > 0);

   }

   private final void _writeStringSegments(String var1, int var2, int var3) throws IOException {
      do {
         int var4 = Math.min(this._outputMaxContiguous, var3);
         if (this._outputTail + var4 > this._outputEnd) {
            this._flushBuffer();
         }

         this._writeStringSegment(var1, var2, var4);
         var2 += var4;
         var3 -= var4;
      } while(var3 > 0);

   }

   private final void _writeStringSegment(char[] var1, int var2, int var3) throws IOException {
      var3 += var2;
      int var4 = this._outputTail;
      byte[] var5 = this._outputBuffer;

      for(int[] var6 = this._outputEscapes; var2 < var3; ++var2) {
         char var7 = var1[var2];
         if (var7 > 127 || var6[var7] != 0) {
            break;
         }

         var5[var4++] = (byte)var7;
      }

      this._outputTail = var4;
      if (var2 < var3) {
         if (this._characterEscapes != null) {
            this._writeCustomStringSegment2(var1, var2, var3);
         } else if (this._maximumNonEscapedChar == 0) {
            this._writeStringSegment2(var1, var2, var3);
         } else {
            this._writeStringSegmentASCII2(var1, var2, var3);
         }
      }

   }

   private final void _writeStringSegment(String var1, int var2, int var3) throws IOException {
      var3 += var2;
      int var4 = this._outputTail;
      byte[] var5 = this._outputBuffer;

      for(int[] var6 = this._outputEscapes; var2 < var3; ++var2) {
         char var7 = var1.charAt(var2);
         if (var7 > 127 || var6[var7] != 0) {
            break;
         }

         var5[var4++] = (byte)var7;
      }

      this._outputTail = var4;
      if (var2 < var3) {
         if (this._characterEscapes != null) {
            this._writeCustomStringSegment2(var1, var2, var3);
         } else if (this._maximumNonEscapedChar == 0) {
            this._writeStringSegment2(var1, var2, var3);
         } else {
            this._writeStringSegmentASCII2(var1, var2, var3);
         }
      }

   }

   private final void _writeStringSegment2(char[] var1, int var2, int var3) throws IOException {
      if (this._outputTail + 6 * (var3 - var2) > this._outputEnd) {
         this._flushBuffer();
      }

      int var4 = this._outputTail;
      byte[] var5 = this._outputBuffer;
      int[] var6 = this._outputEscapes;

      while(var2 < var3) {
         char var7 = var1[var2++];
         if (var7 <= 127) {
            if (var6[var7] == 0) {
               var5[var4++] = (byte)var7;
            } else {
               int var8 = var6[var7];
               if (var8 > 0) {
                  var5[var4++] = 92;
                  var5[var4++] = (byte)var8;
               } else {
                  var4 = this._writeGenericEscape(var7, var4);
               }
            }
         } else if (var7 <= 2047) {
            var5[var4++] = (byte)(192 | var7 >> 6);
            var5[var4++] = (byte)(128 | var7 & 63);
         } else {
            var4 = this._outputMultiByteChar(var7, var4);
         }
      }

      this._outputTail = var4;
   }

   private final void _writeStringSegment2(String var1, int var2, int var3) throws IOException {
      if (this._outputTail + 6 * (var3 - var2) > this._outputEnd) {
         this._flushBuffer();
      }

      int var4 = this._outputTail;
      byte[] var5 = this._outputBuffer;
      int[] var6 = this._outputEscapes;

      while(var2 < var3) {
         char var7 = var1.charAt(var2++);
         if (var7 <= 127) {
            if (var6[var7] == 0) {
               var5[var4++] = (byte)var7;
            } else {
               int var8 = var6[var7];
               if (var8 > 0) {
                  var5[var4++] = 92;
                  var5[var4++] = (byte)var8;
               } else {
                  var4 = this._writeGenericEscape(var7, var4);
               }
            }
         } else if (var7 <= 2047) {
            var5[var4++] = (byte)(192 | var7 >> 6);
            var5[var4++] = (byte)(128 | var7 & 63);
         } else {
            var4 = this._outputMultiByteChar(var7, var4);
         }
      }

      this._outputTail = var4;
   }

   private final void _writeStringSegmentASCII2(char[] var1, int var2, int var3) throws IOException {
      if (this._outputTail + 6 * (var3 - var2) > this._outputEnd) {
         this._flushBuffer();
      }

      int var4 = this._outputTail;
      byte[] var5 = this._outputBuffer;
      int[] var6 = this._outputEscapes;
      int var7 = this._maximumNonEscapedChar;

      while(var2 < var3) {
         char var8 = var1[var2++];
         if (var8 <= 127) {
            if (var6[var8] == 0) {
               var5[var4++] = (byte)var8;
            } else {
               int var9 = var6[var8];
               if (var9 > 0) {
                  var5[var4++] = 92;
                  var5[var4++] = (byte)var9;
               } else {
                  var4 = this._writeGenericEscape(var8, var4);
               }
            }
         } else if (var8 > var7) {
            var4 = this._writeGenericEscape(var8, var4);
         } else if (var8 <= 2047) {
            var5[var4++] = (byte)(192 | var8 >> 6);
            var5[var4++] = (byte)(128 | var8 & 63);
         } else {
            var4 = this._outputMultiByteChar(var8, var4);
         }
      }

      this._outputTail = var4;
   }

   private final void _writeStringSegmentASCII2(String var1, int var2, int var3) throws IOException {
      if (this._outputTail + 6 * (var3 - var2) > this._outputEnd) {
         this._flushBuffer();
      }

      int var4 = this._outputTail;
      byte[] var5 = this._outputBuffer;
      int[] var6 = this._outputEscapes;
      int var7 = this._maximumNonEscapedChar;

      while(var2 < var3) {
         char var8 = var1.charAt(var2++);
         if (var8 <= 127) {
            if (var6[var8] == 0) {
               var5[var4++] = (byte)var8;
            } else {
               int var9 = var6[var8];
               if (var9 > 0) {
                  var5[var4++] = 92;
                  var5[var4++] = (byte)var9;
               } else {
                  var4 = this._writeGenericEscape(var8, var4);
               }
            }
         } else if (var8 > var7) {
            var4 = this._writeGenericEscape(var8, var4);
         } else if (var8 <= 2047) {
            var5[var4++] = (byte)(192 | var8 >> 6);
            var5[var4++] = (byte)(128 | var8 & 63);
         } else {
            var4 = this._outputMultiByteChar(var8, var4);
         }
      }

      this._outputTail = var4;
   }

   private final void _writeCustomStringSegment2(char[] var1, int var2, int var3) throws IOException {
      if (this._outputTail + 6 * (var3 - var2) > this._outputEnd) {
         this._flushBuffer();
      }

      int var4 = this._outputTail;
      byte[] var5 = this._outputBuffer;
      int[] var6 = this._outputEscapes;
      int var7 = this._maximumNonEscapedChar <= 0 ? '\uffff' : this._maximumNonEscapedChar;
      CharacterEscapes var8 = this._characterEscapes;

      while(var2 < var3) {
         char var9 = var1[var2++];
         if (var9 <= 127) {
            if (var6[var9] == 0) {
               var5[var4++] = (byte)var9;
            } else {
               int var10 = var6[var9];
               if (var10 > 0) {
                  var5[var4++] = 92;
                  var5[var4++] = (byte)var10;
               } else if (var10 == -2) {
                  SerializableString var11 = var8.getEscapeSequence(var9);
                  if (var11 == null) {
                     this._reportError("Invalid custom escape definitions; custom escape not found for character code 0x" + Integer.toHexString(var9) + ", although was supposed to have one");
                  }

                  var4 = this._writeCustomEscape(var5, var4, var11, var3 - var2);
               } else {
                  var4 = this._writeGenericEscape(var9, var4);
               }
            }
         } else if (var9 > var7) {
            var4 = this._writeGenericEscape(var9, var4);
         } else {
            SerializableString var12 = var8.getEscapeSequence(var9);
            if (var12 != null) {
               var4 = this._writeCustomEscape(var5, var4, var12, var3 - var2);
            } else if (var9 <= 2047) {
               var5[var4++] = (byte)(192 | var9 >> 6);
               var5[var4++] = (byte)(128 | var9 & 63);
            } else {
               var4 = this._outputMultiByteChar(var9, var4);
            }
         }
      }

      this._outputTail = var4;
   }

   private final void _writeCustomStringSegment2(String var1, int var2, int var3) throws IOException {
      if (this._outputTail + 6 * (var3 - var2) > this._outputEnd) {
         this._flushBuffer();
      }

      int var4 = this._outputTail;
      byte[] var5 = this._outputBuffer;
      int[] var6 = this._outputEscapes;
      int var7 = this._maximumNonEscapedChar <= 0 ? '\uffff' : this._maximumNonEscapedChar;
      CharacterEscapes var8 = this._characterEscapes;

      while(var2 < var3) {
         char var9 = var1.charAt(var2++);
         if (var9 <= 127) {
            if (var6[var9] == 0) {
               var5[var4++] = (byte)var9;
            } else {
               int var10 = var6[var9];
               if (var10 > 0) {
                  var5[var4++] = 92;
                  var5[var4++] = (byte)var10;
               } else if (var10 == -2) {
                  SerializableString var11 = var8.getEscapeSequence(var9);
                  if (var11 == null) {
                     this._reportError("Invalid custom escape definitions; custom escape not found for character code 0x" + Integer.toHexString(var9) + ", although was supposed to have one");
                  }

                  var4 = this._writeCustomEscape(var5, var4, var11, var3 - var2);
               } else {
                  var4 = this._writeGenericEscape(var9, var4);
               }
            }
         } else if (var9 > var7) {
            var4 = this._writeGenericEscape(var9, var4);
         } else {
            SerializableString var12 = var8.getEscapeSequence(var9);
            if (var12 != null) {
               var4 = this._writeCustomEscape(var5, var4, var12, var3 - var2);
            } else if (var9 <= 2047) {
               var5[var4++] = (byte)(192 | var9 >> 6);
               var5[var4++] = (byte)(128 | var9 & 63);
            } else {
               var4 = this._outputMultiByteChar(var9, var4);
            }
         }
      }

      this._outputTail = var4;
   }

   private final int _writeCustomEscape(byte[] var1, int var2, SerializableString var3, int var4) throws IOException, JsonGenerationException {
      byte[] var5 = var3.asUnquotedUTF8();
      int var6 = var5.length;
      if (var6 > 6) {
         return this._handleLongCustomEscape(var1, var2, this._outputEnd, var5, var4);
      } else {
         System.arraycopy(var5, 0, var1, var2, var6);
         return var2 + var6;
      }
   }

   private final int _handleLongCustomEscape(byte[] var1, int var2, int var3, byte[] var4, int var5) throws IOException, JsonGenerationException {
      int var6 = var4.length;
      if (var2 + var6 > var3) {
         this._outputTail = var2;
         this._flushBuffer();
         var2 = this._outputTail;
         if (var6 > var1.length) {
            this._outputStream.write(var4, 0, var6);
            return var2;
         }

         System.arraycopy(var4, 0, var1, var2, var6);
         var2 += var6;
      }

      if (var2 + 6 * var5 > var3) {
         this._flushBuffer();
         return this._outputTail;
      } else {
         return var2;
      }
   }

   private final void _writeUTF8Segments(byte[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      do {
         int var4 = Math.min(this._outputMaxContiguous, var3);
         this._writeUTF8Segment(var1, var2, var4);
         var2 += var4;
         var3 -= var4;
      } while(var3 > 0);

   }

   private final void _writeUTF8Segment(byte[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      int[] var4 = this._outputEscapes;
      int var5 = var2;
      int var6 = var2 + var3;

      byte var7;
      do {
         if (var5 >= var6) {
            if (this._outputTail + var3 > this._outputEnd) {
               this._flushBuffer();
            }

            System.arraycopy(var1, var2, this._outputBuffer, this._outputTail, var3);
            this._outputTail += var3;
            return;
         }

         var7 = var1[var5++];
      } while(var7 < 0 || var4[var7] == 0);

      this._writeUTF8Segment2(var1, var2, var3);
   }

   private final void _writeUTF8Segment2(byte[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      int var4 = this._outputTail;
      if (var4 + var3 * 6 > this._outputEnd) {
         this._flushBuffer();
         var4 = this._outputTail;
      }

      byte[] var5 = this._outputBuffer;
      int[] var6 = this._outputEscapes;
      var3 += var2;

      while(true) {
         while(var2 < var3) {
            byte var7 = var1[var2++];
            if (var7 >= 0 && var6[var7] != 0) {
               int var9 = var6[var7];
               if (var9 > 0) {
                  var5[var4++] = 92;
                  var5[var4++] = (byte)var9;
               } else {
                  var4 = this._writeGenericEscape(var7, var4);
               }
            } else {
               var5[var4++] = var7;
            }
         }

         this._outputTail = var4;
         return;
      }
   }

   protected final void _writeBinary(Base64Variant var1, byte[] var2, int var3, int var4) throws IOException, JsonGenerationException {
      int var5 = var4 - 3;
      int var6 = this._outputEnd - 6;
      int var7 = var1.getMaxLineLength() >> 2;

      int var8;
      while(var3 <= var5) {
         if (this._outputTail > var6) {
            this._flushBuffer();
         }

         var8 = var2[var3++] << 8;
         var8 |= var2[var3++] & 255;
         var8 = var8 << 8 | var2[var3++] & 255;
         this._outputTail = var1.encodeBase64Chunk(var8, this._outputBuffer, this._outputTail);
         --var7;
         if (var7 <= 0) {
            this._outputBuffer[this._outputTail++] = 92;
            this._outputBuffer[this._outputTail++] = 110;
            var7 = var1.getMaxLineLength() >> 2;
         }
      }

      var8 = var4 - var3;
      if (var8 > 0) {
         if (this._outputTail > var6) {
            this._flushBuffer();
         }

         int var9 = var2[var3++] << 16;
         if (var8 == 2) {
            var9 |= (var2[var3++] & 255) << 8;
         }

         this._outputTail = var1.encodeBase64Partial(var9, var8, this._outputBuffer, this._outputTail);
      }

   }

   protected final int _writeBinary(Base64Variant var1, InputStream var2, byte[] var3, int var4) throws IOException, JsonGenerationException {
      int var5 = 0;
      int var6 = 0;
      int var7 = -3;
      int var8 = this._outputEnd - 6;
      int var9 = var1.getMaxLineLength() >> 2;

      int var10;
      while(var4 > 2) {
         if (var5 > var7) {
            var6 = this._readMore(var2, var3, var5, var6, var4);
            var5 = 0;
            if (var6 < 3) {
               break;
            }

            var7 = var6 - 3;
         }

         if (this._outputTail > var8) {
            this._flushBuffer();
         }

         var10 = var3[var5++] << 8;
         var10 |= var3[var5++] & 255;
         var10 = var10 << 8 | var3[var5++] & 255;
         var4 -= 3;
         this._outputTail = var1.encodeBase64Chunk(var10, this._outputBuffer, this._outputTail);
         --var9;
         if (var9 <= 0) {
            this._outputBuffer[this._outputTail++] = 92;
            this._outputBuffer[this._outputTail++] = 110;
            var9 = var1.getMaxLineLength() >> 2;
         }
      }

      if (var4 > 0) {
         var6 = this._readMore(var2, var3, var5, var6, var4);
         byte var12 = 0;
         if (var6 > 0) {
            if (this._outputTail > var8) {
               this._flushBuffer();
            }

            var5 = var12 + 1;
            var10 = var3[var12] << 16;
            byte var11;
            if (var5 < var6) {
               var10 |= (var3[var5] & 255) << 8;
               var11 = 2;
            } else {
               var11 = 1;
            }

            this._outputTail = var1.encodeBase64Partial(var10, var11, (byte[])this._outputBuffer, this._outputTail);
            var4 -= var11;
         }
      }

      return var4;
   }

   protected final int _writeBinary(Base64Variant var1, InputStream var2, byte[] var3) throws IOException, JsonGenerationException {
      int var4 = 0;
      int var5 = 0;
      int var6 = -3;
      int var7 = 0;
      int var8 = this._outputEnd - 6;
      int var9 = var1.getMaxLineLength() >> 2;

      while(true) {
         int var10;
         if (var4 > var6) {
            var5 = this._readMore(var2, var3, var4, var5, var3.length);
            var4 = 0;
            if (var5 < 3) {
               if (var4 < var5) {
                  if (this._outputTail > var8) {
                     this._flushBuffer();
                  }

                  var10 = var3[var4++] << 16;
                  byte var11 = 1;
                  if (var4 < var5) {
                     var10 |= (var3[var4] & 255) << 8;
                     var11 = 2;
                  }

                  var7 += var11;
                  this._outputTail = var1.encodeBase64Partial(var10, var11, (byte[])this._outputBuffer, this._outputTail);
               }

               return var7;
            }

            var6 = var5 - 3;
         }

         if (this._outputTail > var8) {
            this._flushBuffer();
         }

         var10 = var3[var4++] << 8;
         var10 |= var3[var4++] & 255;
         var10 = var10 << 8 | var3[var4++] & 255;
         var7 += 3;
         this._outputTail = var1.encodeBase64Chunk(var10, this._outputBuffer, this._outputTail);
         --var9;
         if (var9 <= 0) {
            this._outputBuffer[this._outputTail++] = 92;
            this._outputBuffer[this._outputTail++] = 110;
            var9 = var1.getMaxLineLength() >> 2;
         }
      }
   }

   private final int _readMore(InputStream var1, byte[] var2, int var3, int var4, int var5) throws IOException {
      int var6;
      for(var6 = 0; var3 < var4; var2[var6++] = var2[var3++]) {
      }

      boolean var9 = false;
      var4 = var6;
      var5 = Math.min(var5, var2.length);

      do {
         int var7 = var5 - var4;
         if (var7 == 0) {
            break;
         }

         int var8 = var1.read(var2, var4, var7);
         if (var8 < 0) {
            return var4;
         }

         var4 += var8;
      } while(var4 < 3);

      return var4;
   }

   private final int _outputRawMultiByteChar(int var1, char[] var2, int var3, int var4) throws IOException {
      if (var1 >= 55296 && var1 <= 57343) {
         if (var3 >= var4 || var2 == null) {
            this._reportError(String.format("Split surrogate on writeRaw() input (last character): first character 0x%4x", var1));
         }

         this._outputSurrogates(var1, var2[var3]);
         return var3 + 1;
      } else {
         byte[] var5 = this._outputBuffer;
         var5[this._outputTail++] = (byte)(224 | var1 >> 12);
         var5[this._outputTail++] = (byte)(128 | var1 >> 6 & 63);
         var5[this._outputTail++] = (byte)(128 | var1 & 63);
         return var3;
      }
   }

   protected final void _outputSurrogates(int var1, int var2) throws IOException {
      int var3 = this._decodeSurrogate(var1, var2);
      if (this._outputTail + 4 > this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var4 = this._outputBuffer;
      var4[this._outputTail++] = (byte)(240 | var3 >> 18);
      var4[this._outputTail++] = (byte)(128 | var3 >> 12 & 63);
      var4[this._outputTail++] = (byte)(128 | var3 >> 6 & 63);
      var4[this._outputTail++] = (byte)(128 | var3 & 63);
   }

   private final int _outputMultiByteChar(int var1, int var2) throws IOException {
      byte[] var3 = this._outputBuffer;
      if (var1 >= 55296 && var1 <= 57343) {
         var3[var2++] = 92;
         var3[var2++] = 117;
         var3[var2++] = HEX_CHARS[var1 >> 12 & 15];
         var3[var2++] = HEX_CHARS[var1 >> 8 & 15];
         var3[var2++] = HEX_CHARS[var1 >> 4 & 15];
         var3[var2++] = HEX_CHARS[var1 & 15];
      } else {
         var3[var2++] = (byte)(224 | var1 >> 12);
         var3[var2++] = (byte)(128 | var1 >> 6 & 63);
         var3[var2++] = (byte)(128 | var1 & 63);
      }

      return var2;
   }

   private final void _writeNull() throws IOException {
      if (this._outputTail + 4 >= this._outputEnd) {
         this._flushBuffer();
      }

      System.arraycopy(NULL_BYTES, 0, this._outputBuffer, this._outputTail, 4);
      this._outputTail += 4;
   }

   private int _writeGenericEscape(int var1, int var2) throws IOException {
      byte[] var3 = this._outputBuffer;
      var3[var2++] = 92;
      var3[var2++] = 117;
      if (var1 > 255) {
         int var4 = var1 >> 8 & 255;
         var3[var2++] = HEX_CHARS[var4 >> 4];
         var3[var2++] = HEX_CHARS[var4 & 15];
         var1 &= 255;
      } else {
         var3[var2++] = 48;
         var3[var2++] = 48;
      }

      var3[var2++] = HEX_CHARS[var1 >> 4];
      var3[var2++] = HEX_CHARS[var1 & 15];
      return var2;
   }

   protected final void _flushBuffer() throws IOException {
      int var1 = this._outputTail;
      if (var1 > 0) {
         this._outputTail = 0;
         this._outputStream.write(this._outputBuffer, 0, var1);
      }

   }
}
