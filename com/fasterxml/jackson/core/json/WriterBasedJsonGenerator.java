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
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;

public class WriterBasedJsonGenerator extends JsonGeneratorImpl {
   protected static final int SHORT_WRITE = 32;
   protected static final char[] HEX_CHARS = CharTypes.copyHexChars();
   protected final Writer _writer;
   protected char _quoteChar = '"';
   protected char[] _outputBuffer;
   protected int _outputHead;
   protected int _outputTail;
   protected int _outputEnd;
   protected char[] _entityBuffer;
   protected SerializableString _currentEscape;
   protected char[] _charBuffer;

   public WriterBasedJsonGenerator(IOContext var1, int var2, ObjectCodec var3, Writer var4) {
      super(var1, var2, var3);
      this._writer = var4;
      this._outputBuffer = var1.allocConcatBuffer();
      this._outputEnd = this._outputBuffer.length;
   }

   public Object getOutputTarget() {
      return this._writer;
   }

   public int getOutputBuffered() {
      int var1 = this._outputTail - this._outputHead;
      return Math.max(0, var1);
   }

   public boolean canWriteFormattedNumbers() {
      return true;
   }

   public void writeFieldName(String var1) throws IOException {
      int var2 = this._writeContext.writeFieldName(var1);
      if (var2 == 4) {
         this._reportError("Can not write a field name, expecting a value");
      }

      this._writeFieldName(var1, var2 == 1);
   }

   public void writeFieldName(SerializableString var1) throws IOException {
      int var2 = this._writeContext.writeFieldName(var1.getValue());
      if (var2 == 4) {
         this._reportError("Can not write a field name, expecting a value");
      }

      this._writeFieldName(var1, var2 == 1);
   }

   protected final void _writeFieldName(String var1, boolean var2) throws IOException {
      if (this._cfgPrettyPrinter != null) {
         this._writePPFieldName(var1, var2);
      } else {
         if (this._outputTail + 1 >= this._outputEnd) {
            this._flushBuffer();
         }

         if (var2) {
            this._outputBuffer[this._outputTail++] = ',';
         }

         if (this._cfgUnqNames) {
            this._writeString(var1);
         } else {
            this._outputBuffer[this._outputTail++] = this._quoteChar;
            this._writeString(var1);
            if (this._outputTail >= this._outputEnd) {
               this._flushBuffer();
            }

            this._outputBuffer[this._outputTail++] = this._quoteChar;
         }
      }
   }

   protected final void _writeFieldName(SerializableString var1, boolean var2) throws IOException {
      if (this._cfgPrettyPrinter != null) {
         this._writePPFieldName(var1, var2);
      } else {
         if (this._outputTail + 1 >= this._outputEnd) {
            this._flushBuffer();
         }

         if (var2) {
            this._outputBuffer[this._outputTail++] = ',';
         }

         char[] var3 = var1.asQuotedChars();
         if (this._cfgUnqNames) {
            this.writeRaw((char[])var3, 0, var3.length);
         } else {
            this._outputBuffer[this._outputTail++] = this._quoteChar;
            int var4 = var3.length;
            if (this._outputTail + var4 + 1 >= this._outputEnd) {
               this.writeRaw((char[])var3, 0, var4);
               if (this._outputTail >= this._outputEnd) {
                  this._flushBuffer();
               }

               this._outputBuffer[this._outputTail++] = this._quoteChar;
            } else {
               System.arraycopy(var3, 0, this._outputBuffer, this._outputTail, var4);
               this._outputTail += var4;
               this._outputBuffer[this._outputTail++] = this._quoteChar;
            }

         }
      }
   }

   public void writeStartArray() throws IOException {
      this._verifyValueWrite("start an array");
      this._writeContext = this._writeContext.createChildArrayContext();
      if (this._cfgPrettyPrinter != null) {
         this._cfgPrettyPrinter.writeStartArray(this);
      } else {
         if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         this._outputBuffer[this._outputTail++] = '[';
      }

   }

   public void writeEndArray() throws IOException {
      if (!this._writeContext.inArray()) {
         this._reportError("Current context not Array but " + this._writeContext.typeDesc());
      }

      if (this._cfgPrettyPrinter != null) {
         this._cfgPrettyPrinter.writeEndArray(this, this._writeContext.getEntryCount());
      } else {
         if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         this._outputBuffer[this._outputTail++] = ']';
      }

      this._writeContext = this._writeContext.clearAndGetParent();
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

         this._outputBuffer[this._outputTail++] = '{';
      }

   }

   public void writeStartObject() throws IOException {
      this._verifyValueWrite("start an object");
      this._writeContext = this._writeContext.createChildObjectContext();
      if (this._cfgPrettyPrinter != null) {
         this._cfgPrettyPrinter.writeStartObject(this);
      } else {
         if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         this._outputBuffer[this._outputTail++] = '{';
      }

   }

   public void writeEndObject() throws IOException {
      if (!this._writeContext.inObject()) {
         this._reportError("Current context not Object but " + this._writeContext.typeDesc());
      }

      if (this._cfgPrettyPrinter != null) {
         this._cfgPrettyPrinter.writeEndObject(this, this._writeContext.getEntryCount());
      } else {
         if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         this._outputBuffer[this._outputTail++] = '}';
      }

      this._writeContext = this._writeContext.clearAndGetParent();
   }

   protected final void _writePPFieldName(String var1, boolean var2) throws IOException {
      if (var2) {
         this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
      } else {
         this._cfgPrettyPrinter.beforeObjectEntries(this);
      }

      if (this._cfgUnqNames) {
         this._writeString(var1);
      } else {
         if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         this._outputBuffer[this._outputTail++] = this._quoteChar;
         this._writeString(var1);
         if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         this._outputBuffer[this._outputTail++] = this._quoteChar;
      }

   }

   protected final void _writePPFieldName(SerializableString var1, boolean var2) throws IOException {
      if (var2) {
         this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
      } else {
         this._cfgPrettyPrinter.beforeObjectEntries(this);
      }

      char[] var3 = var1.asQuotedChars();
      if (this._cfgUnqNames) {
         this.writeRaw((char[])var3, 0, var3.length);
      } else {
         if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         this._outputBuffer[this._outputTail++] = this._quoteChar;
         this.writeRaw((char[])var3, 0, var3.length);
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
         if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         this._outputBuffer[this._outputTail++] = this._quoteChar;
         this._writeString(var1);
         if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         this._outputBuffer[this._outputTail++] = this._quoteChar;
      }
   }

   public void writeString(Reader var1, int var2) throws IOException {
      this._verifyValueWrite("write a string");
      if (var1 == null) {
         this._reportError("null reader");
      }

      int var3 = var2 >= 0 ? var2 : 0;
      char[] var4 = this._allocateCopyBuffer();
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

         this._writeString(var4, 0, var6);
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
      this._writeString(var1, var2, var3);
      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      this._outputBuffer[this._outputTail++] = this._quoteChar;
   }

   public void writeString(SerializableString var1) throws IOException {
      this._verifyValueWrite("write a string");
      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      this._outputBuffer[this._outputTail++] = this._quoteChar;
      char[] var2 = var1.asQuotedChars();
      int var3 = var2.length;
      if (var3 < 32) {
         int var4 = this._outputEnd - this._outputTail;
         if (var3 > var4) {
            this._flushBuffer();
         }

         System.arraycopy(var2, 0, this._outputBuffer, this._outputTail, var3);
         this._outputTail += var3;
      } else {
         this._flushBuffer();
         this._writer.write(var2, 0, var3);
      }

      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      this._outputBuffer[this._outputTail++] = this._quoteChar;
   }

   public void writeRawUTF8String(byte[] var1, int var2, int var3) throws IOException {
      this._reportUnsupportedOperation();
   }

   public void writeUTF8String(byte[] var1, int var2, int var3) throws IOException {
      this._reportUnsupportedOperation();
   }

   public void writeRaw(String var1) throws IOException {
      int var2 = var1.length();
      int var3 = this._outputEnd - this._outputTail;
      if (var3 == 0) {
         this._flushBuffer();
         var3 = this._outputEnd - this._outputTail;
      }

      if (var3 >= var2) {
         var1.getChars(0, var2, this._outputBuffer, this._outputTail);
         this._outputTail += var2;
      } else {
         this.writeRawLong(var1);
      }

   }

   public void writeRaw(String var1, int var2, int var3) throws IOException {
      int var4 = this._outputEnd - this._outputTail;
      if (var4 < var3) {
         this._flushBuffer();
         var4 = this._outputEnd - this._outputTail;
      }

      if (var4 >= var3) {
         var1.getChars(var2, var2 + var3, this._outputBuffer, this._outputTail);
         this._outputTail += var3;
      } else {
         this.writeRawLong(var1.substring(var2, var2 + var3));
      }

   }

   public void writeRaw(SerializableString var1) throws IOException {
      this.writeRaw(var1.getValue());
   }

   public void writeRaw(char[] var1, int var2, int var3) throws IOException {
      if (var3 < 32) {
         int var4 = this._outputEnd - this._outputTail;
         if (var3 > var4) {
            this._flushBuffer();
         }

         System.arraycopy(var1, var2, this._outputBuffer, this._outputTail, var3);
         this._outputTail += var3;
      } else {
         this._flushBuffer();
         this._writer.write(var1, var2, var3);
      }
   }

   public void writeRaw(char var1) throws IOException {
      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      this._outputBuffer[this._outputTail++] = var1;
   }

   private void writeRawLong(String var1) throws IOException {
      int var2 = this._outputEnd - this._outputTail;
      var1.getChars(0, var2, this._outputBuffer, this._outputTail);
      this._outputTail += var2;
      this._flushBuffer();
      int var3 = var2;

      int var4;
      int var5;
      for(var4 = var1.length() - var2; var4 > this._outputEnd; var4 -= var5) {
         var5 = this._outputEnd;
         var1.getChars(var3, var3 + var5, this._outputBuffer, 0);
         this._outputHead = 0;
         this._outputTail = var5;
         this._flushBuffer();
         var3 += var5;
      }

      var1.getChars(var3, var3 + var4, this._outputBuffer, 0);
      this._outputHead = 0;
      this._outputTail = var4;
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
      if (this._cfgNumbersAsStrings) {
         this._writeQuotedShort(var1);
      } else {
         if (this._outputTail + 6 >= this._outputEnd) {
            this._flushBuffer();
         }

         this._outputTail = NumberOutput.outputInt(var1, (char[])this._outputBuffer, this._outputTail);
      }
   }

   private void _writeQuotedShort(short var1) throws IOException {
      if (this._outputTail + 8 >= this._outputEnd) {
         this._flushBuffer();
      }

      this._outputBuffer[this._outputTail++] = this._quoteChar;
      this._outputTail = NumberOutput.outputInt(var1, (char[])this._outputBuffer, this._outputTail);
      this._outputBuffer[this._outputTail++] = this._quoteChar;
   }

   public void writeNumber(int var1) throws IOException {
      this._verifyValueWrite("write a number");
      if (this._cfgNumbersAsStrings) {
         this._writeQuotedInt(var1);
      } else {
         if (this._outputTail + 11 >= this._outputEnd) {
            this._flushBuffer();
         }

         this._outputTail = NumberOutput.outputInt(var1, this._outputBuffer, this._outputTail);
      }
   }

   private void _writeQuotedInt(int var1) throws IOException {
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

   private void _writeQuotedLong(long var1) throws IOException {
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
      if (!this._cfgNumbersAsStrings && (!this.isEnabled(JsonGenerator$Feature.QUOTE_NON_NUMERIC_NUMBERS) || !Double.isNaN(var1) && !Double.isInfinite(var1))) {
         this._verifyValueWrite("write a number");
         this.writeRaw(String.valueOf(var1));
      } else {
         this.writeString(String.valueOf(var1));
      }
   }

   public void writeNumber(float var1) throws IOException {
      if (!this._cfgNumbersAsStrings && (!this.isEnabled(JsonGenerator$Feature.QUOTE_NON_NUMERIC_NUMBERS) || !Float.isNaN(var1) && !Float.isInfinite(var1))) {
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

   private void _writeQuotedRaw(String var1) throws IOException {
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

      int var2 = this._outputTail;
      char[] var3 = this._outputBuffer;
      if (var1) {
         var3[var2] = 't';
         ++var2;
         var3[var2] = 'r';
         ++var2;
         var3[var2] = 'u';
         ++var2;
         var3[var2] = 'e';
      } else {
         var3[var2] = 'f';
         ++var2;
         var3[var2] = 'a';
         ++var2;
         var3[var2] = 'l';
         ++var2;
         var3[var2] = 's';
         ++var2;
         var3[var2] = 'e';
      }

      this._outputTail = var2 + 1;
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
               this.writeRaw(this._rootValueSeparator.getValue());
            }

            return;
         case 5:
            this._reportCantWriteValueExpectName(var1);
            return;
         }

         if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         this._outputBuffer[this._outputTail++] = (char)var3;
      }
   }

   public void flush() throws IOException {
      this._flushBuffer();
      if (this._writer != null && this.isEnabled(JsonGenerator$Feature.FLUSH_PASSED_TO_STREAM)) {
         this._writer.flush();
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
      this._outputHead = 0;
      this._outputTail = 0;
      if (this._writer != null) {
         if (!this._ioContext.isResourceManaged() && !this.isEnabled(JsonGenerator$Feature.AUTO_CLOSE_TARGET)) {
            if (this.isEnabled(JsonGenerator$Feature.FLUSH_PASSED_TO_STREAM)) {
               this._writer.flush();
            }
         } else {
            this._writer.close();
         }
      }

      this._releaseBuffers();
   }

   protected void _releaseBuffers() {
      char[] var1 = this._outputBuffer;
      if (var1 != null) {
         this._outputBuffer = null;
         this._ioContext.releaseConcatBuffer(var1);
      }

      var1 = this._charBuffer;
      if (var1 != null) {
         this._charBuffer = null;
         this._ioContext.releaseNameCopyBuffer(var1);
      }

   }

   private void _writeString(String var1) throws IOException {
      int var2 = var1.length();
      if (var2 > this._outputEnd) {
         this._writeLongString(var1);
      } else {
         if (this._outputTail + var2 > this._outputEnd) {
            this._flushBuffer();
         }

         var1.getChars(0, var2, this._outputBuffer, this._outputTail);
         if (this._characterEscapes != null) {
            this._writeStringCustom(var2);
         } else if (this._maximumNonEscapedChar != 0) {
            this._writeStringASCII(var2, this._maximumNonEscapedChar);
         } else {
            this._writeString2(var2);
         }

      }
   }

   private void _writeString2(int var1) throws IOException {
      int var2 = this._outputTail + var1;
      int[] var3 = this._outputEscapes;
      int var4 = var3.length;

      while(this._outputTail < var2) {
         while(true) {
            char var5 = this._outputBuffer[this._outputTail];
            if (var5 < var4 && var3[var5] != 0) {
               int var7 = this._outputTail - this._outputHead;
               if (var7 > 0) {
                  this._writer.write(this._outputBuffer, this._outputHead, var7);
               }

               char var6 = this._outputBuffer[this._outputTail++];
               this._prependOrWriteCharacterEscape(var6, var3[var6]);
            } else if (++this._outputTail >= var2) {
               return;
            }
         }
      }

   }

   private void _writeLongString(String var1) throws IOException {
      this._flushBuffer();
      int var2 = var1.length();
      int var3 = 0;

      do {
         int var4 = this._outputEnd;
         int var5 = var3 + var4 > var2 ? var2 - var3 : var4;
         var1.getChars(var3, var3 + var5, this._outputBuffer, 0);
         if (this._characterEscapes != null) {
            this._writeSegmentCustom(var5);
         } else if (this._maximumNonEscapedChar != 0) {
            this._writeSegmentASCII(var5, this._maximumNonEscapedChar);
         } else {
            this._writeSegment(var5);
         }

         var3 += var5;
      } while(var3 < var2);

   }

   private void _writeSegment(int var1) throws IOException {
      int[] var2 = this._outputEscapes;
      int var3 = var2.length;
      int var4 = 0;

      char var6;
      for(int var5 = var4; var4 < var1; var5 = this._prependOrWriteCharacterEscape(this._outputBuffer, var4, var1, var6, var2[var6])) {
         do {
            var6 = this._outputBuffer[var4];
            if (var6 < var3 && var2[var6] != 0) {
               break;
            }

            ++var4;
         } while(var4 < var1);

         int var7 = var4 - var5;
         if (var7 > 0) {
            this._writer.write(this._outputBuffer, var5, var7);
            if (var4 >= var1) {
               break;
            }
         }

         ++var4;
      }

   }

   private void _writeString(char[] var1, int var2, int var3) throws IOException {
      if (this._characterEscapes != null) {
         this._writeStringCustom(var1, var2, var3);
      } else if (this._maximumNonEscapedChar != 0) {
         this._writeStringASCII(var1, var2, var3, this._maximumNonEscapedChar);
      } else {
         var3 += var2;
         int[] var4 = this._outputEscapes;
         int var5 = var4.length;

         while(var2 < var3) {
            int var6 = var2;

            do {
               char var7 = var1[var2];
               if (var7 < var5 && var4[var7] != 0) {
                  break;
               }

               ++var2;
            } while(var2 < var3);

            int var9 = var2 - var6;
            if (var9 < 32) {
               if (this._outputTail + var9 > this._outputEnd) {
                  this._flushBuffer();
               }

               if (var9 > 0) {
                  System.arraycopy(var1, var6, this._outputBuffer, this._outputTail, var9);
                  this._outputTail += var9;
               }
            } else {
               this._flushBuffer();
               this._writer.write(var1, var6, var9);
            }

            if (var2 >= var3) {
               break;
            }

            char var8 = var1[var2++];
            this._appendCharacterEscape(var8, var4[var8]);
         }

      }
   }

   private void _writeStringASCII(int var1, int var2) throws IOException, JsonGenerationException {
      int var3 = this._outputTail + var1;
      int[] var4 = this._outputEscapes;
      int var5 = Math.min(var4.length, var2 + 1);
      boolean var6 = false;

      while(this._outputTail < var3) {
         char var7;
         int var9;
         while(true) {
            var7 = this._outputBuffer[this._outputTail];
            if (var7 < var5) {
               var9 = var4[var7];
               if (var9 != 0) {
                  break;
               }
            } else if (var7 > var2) {
               var9 = -1;
               break;
            }

            if (++this._outputTail >= var3) {
               return;
            }
         }

         int var8 = this._outputTail - this._outputHead;
         if (var8 > 0) {
            this._writer.write(this._outputBuffer, this._outputHead, var8);
         }

         ++this._outputTail;
         this._prependOrWriteCharacterEscape(var7, var9);
      }

   }

   private void _writeSegmentASCII(int var1, int var2) throws IOException, JsonGenerationException {
      int[] var3 = this._outputEscapes;
      int var4 = Math.min(var3.length, var2 + 1);
      int var5 = 0;
      int var6 = 0;

      char var8;
      for(int var7 = var5; var5 < var1; var7 = this._prependOrWriteCharacterEscape(this._outputBuffer, var5, var1, var8, var6)) {
         do {
            var8 = this._outputBuffer[var5];
            if (var8 < var4) {
               var6 = var3[var8];
               if (var6 != 0) {
                  break;
               }
            } else if (var8 > var2) {
               var6 = -1;
               break;
            }

            ++var5;
         } while(var5 < var1);

         int var9 = var5 - var7;
         if (var9 > 0) {
            this._writer.write(this._outputBuffer, var7, var9);
            if (var5 >= var1) {
               break;
            }
         }

         ++var5;
      }

   }

   private void _writeStringASCII(char[] var1, int var2, int var3, int var4) throws IOException, JsonGenerationException {
      var3 += var2;
      int[] var5 = this._outputEscapes;
      int var6 = Math.min(var5.length, var4 + 1);
      int var7 = 0;

      while(var2 < var3) {
         int var8 = var2;

         char var9;
         do {
            var9 = var1[var2];
            if (var9 < var6) {
               var7 = var5[var9];
               if (var7 != 0) {
                  break;
               }
            } else if (var9 > var4) {
               var7 = -1;
               break;
            }

            ++var2;
         } while(var2 < var3);

         int var10 = var2 - var8;
         if (var10 < 32) {
            if (this._outputTail + var10 > this._outputEnd) {
               this._flushBuffer();
            }

            if (var10 > 0) {
               System.arraycopy(var1, var8, this._outputBuffer, this._outputTail, var10);
               this._outputTail += var10;
            }
         } else {
            this._flushBuffer();
            this._writer.write(var1, var8, var10);
         }

         if (var2 >= var3) {
            break;
         }

         ++var2;
         this._appendCharacterEscape(var9, var7);
      }

   }

   private void _writeStringCustom(int var1) throws IOException, JsonGenerationException {
      int var2 = this._outputTail + var1;
      int[] var3 = this._outputEscapes;
      int var4 = this._maximumNonEscapedChar < 1 ? '\uffff' : this._maximumNonEscapedChar;
      int var5 = Math.min(var3.length, var4 + 1);
      boolean var6 = false;
      CharacterEscapes var7 = this._characterEscapes;

      while(this._outputTail < var2) {
         char var8;
         int var10;
         while(true) {
            var8 = this._outputBuffer[this._outputTail];
            if (var8 < var5) {
               var10 = var3[var8];
               if (var10 != 0) {
                  break;
               }
            } else {
               if (var8 > var4) {
                  var10 = -1;
                  break;
               }

               if ((this._currentEscape = var7.getEscapeSequence(var8)) != null) {
                  var10 = -2;
                  break;
               }
            }

            if (++this._outputTail >= var2) {
               return;
            }
         }

         int var9 = this._outputTail - this._outputHead;
         if (var9 > 0) {
            this._writer.write(this._outputBuffer, this._outputHead, var9);
         }

         ++this._outputTail;
         this._prependOrWriteCharacterEscape(var8, var10);
      }

   }

   private void _writeSegmentCustom(int var1) throws IOException, JsonGenerationException {
      int[] var2 = this._outputEscapes;
      int var3 = this._maximumNonEscapedChar < 1 ? '\uffff' : this._maximumNonEscapedChar;
      int var4 = Math.min(var2.length, var3 + 1);
      CharacterEscapes var5 = this._characterEscapes;
      int var6 = 0;
      int var7 = 0;

      char var9;
      for(int var8 = var6; var6 < var1; var8 = this._prependOrWriteCharacterEscape(this._outputBuffer, var6, var1, var9, var7)) {
         do {
            var9 = this._outputBuffer[var6];
            if (var9 < var4) {
               var7 = var2[var9];
               if (var7 != 0) {
                  break;
               }
            } else {
               if (var9 > var3) {
                  var7 = -1;
                  break;
               }

               if ((this._currentEscape = var5.getEscapeSequence(var9)) != null) {
                  var7 = -2;
                  break;
               }
            }

            ++var6;
         } while(var6 < var1);

         int var10 = var6 - var8;
         if (var10 > 0) {
            this._writer.write(this._outputBuffer, var8, var10);
            if (var6 >= var1) {
               break;
            }
         }

         ++var6;
      }

   }

   private void _writeStringCustom(char[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      var3 += var2;
      int[] var4 = this._outputEscapes;
      int var5 = this._maximumNonEscapedChar < 1 ? '\uffff' : this._maximumNonEscapedChar;
      int var6 = Math.min(var4.length, var5 + 1);
      CharacterEscapes var7 = this._characterEscapes;
      int var8 = 0;

      while(var2 < var3) {
         int var9 = var2;

         char var10;
         do {
            var10 = var1[var2];
            if (var10 < var6) {
               var8 = var4[var10];
               if (var8 != 0) {
                  break;
               }
            } else {
               if (var10 > var5) {
                  var8 = -1;
                  break;
               }

               if ((this._currentEscape = var7.getEscapeSequence(var10)) != null) {
                  var8 = -2;
                  break;
               }
            }

            ++var2;
         } while(var2 < var3);

         int var11 = var2 - var9;
         if (var11 < 32) {
            if (this._outputTail + var11 > this._outputEnd) {
               this._flushBuffer();
            }

            if (var11 > 0) {
               System.arraycopy(var1, var9, this._outputBuffer, this._outputTail, var11);
               this._outputTail += var11;
            }
         } else {
            this._flushBuffer();
            this._writer.write(var1, var9, var11);
         }

         if (var2 >= var3) {
            break;
         }

         ++var2;
         this._appendCharacterEscape(var10, var8);
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
            this._outputBuffer[this._outputTail++] = '\\';
            this._outputBuffer[this._outputTail++] = 'n';
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
            this._outputBuffer[this._outputTail++] = '\\';
            this._outputBuffer[this._outputTail++] = 'n';
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

            this._outputTail = var1.encodeBase64Partial(var10, var11, (char[])this._outputBuffer, this._outputTail);
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
                  this._outputTail = var1.encodeBase64Partial(var10, var11, (char[])this._outputBuffer, this._outputTail);
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
            this._outputBuffer[this._outputTail++] = '\\';
            this._outputBuffer[this._outputTail++] = 'n';
            var9 = var1.getMaxLineLength() >> 2;
         }
      }
   }

   private int _readMore(InputStream var1, byte[] var2, int var3, int var4, int var5) throws IOException {
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

   private final void _writeNull() throws IOException {
      if (this._outputTail + 4 >= this._outputEnd) {
         this._flushBuffer();
      }

      int var1 = this._outputTail;
      char[] var2 = this._outputBuffer;
      var2[var1] = 'n';
      ++var1;
      var2[var1] = 'u';
      ++var1;
      var2[var1] = 'l';
      ++var1;
      var2[var1] = 'l';
      this._outputTail = var1 + 1;
   }

   private void _prependOrWriteCharacterEscape(char var1, int var2) throws IOException, JsonGenerationException {
      char[] var6;
      if (var2 >= 0) {
         if (this._outputTail >= 2) {
            int var7 = this._outputTail - 2;
            this._outputHead = var7;
            this._outputBuffer[var7++] = '\\';
            this._outputBuffer[var7] = (char)var2;
         } else {
            var6 = this._entityBuffer;
            if (var6 == null) {
               var6 = this._allocateEntityBuffer();
            }

            this._outputHead = this._outputTail;
            var6[1] = (char)var2;
            this._writer.write(var6, 0, 2);
         }
      } else {
         int var4;
         int var5;
         if (var2 != -2) {
            if (this._outputTail >= 6) {
               var6 = this._outputBuffer;
               var4 = this._outputTail - 6;
               this._outputHead = var4;
               var6[var4] = '\\';
               ++var4;
               var6[var4] = 'u';
               if (var1 > 255) {
                  var5 = var1 >> 8 & 255;
                  ++var4;
                  var6[var4] = HEX_CHARS[var5 >> 4];
                  ++var4;
                  var6[var4] = HEX_CHARS[var5 & 15];
                  var1 = (char)(var1 & 255);
               } else {
                  ++var4;
                  var6[var4] = '0';
                  ++var4;
                  var6[var4] = '0';
               }

               ++var4;
               var6[var4] = HEX_CHARS[var1 >> 4];
               ++var4;
               var6[var4] = HEX_CHARS[var1 & 15];
            } else {
               var6 = this._entityBuffer;
               if (var6 == null) {
                  var6 = this._allocateEntityBuffer();
               }

               this._outputHead = this._outputTail;
               if (var1 > 255) {
                  var4 = var1 >> 8 & 255;
                  var5 = var1 & 255;
                  var6[10] = HEX_CHARS[var4 >> 4];
                  var6[11] = HEX_CHARS[var4 & 15];
                  var6[12] = HEX_CHARS[var5 >> 4];
                  var6[13] = HEX_CHARS[var5 & 15];
                  this._writer.write(var6, 8, 6);
               } else {
                  var6[6] = HEX_CHARS[var1 >> 4];
                  var6[7] = HEX_CHARS[var1 & 15];
                  this._writer.write(var6, 2, 6);
               }

            }
         } else {
            String var3;
            if (this._currentEscape == null) {
               var3 = this._characterEscapes.getEscapeSequence(var1).getValue();
            } else {
               var3 = this._currentEscape.getValue();
               this._currentEscape = null;
            }

            var4 = var3.length();
            if (this._outputTail >= var4) {
               var5 = this._outputTail - var4;
               this._outputHead = var5;
               var3.getChars(0, var4, this._outputBuffer, var5);
            } else {
               this._outputHead = this._outputTail;
               this._writer.write(var3);
            }
         }
      }
   }

   private int _prependOrWriteCharacterEscape(char[] var1, int var2, int var3, char var4, int var5) throws IOException, JsonGenerationException {
      char[] var9;
      if (var5 >= 0) {
         if (var2 > 1 && var2 < var3) {
            var2 -= 2;
            var1[var2] = '\\';
            var1[var2 + 1] = (char)var5;
         } else {
            var9 = this._entityBuffer;
            if (var9 == null) {
               var9 = this._allocateEntityBuffer();
            }

            var9[1] = (char)var5;
            this._writer.write(var9, 0, 2);
         }

         return var2;
      } else {
         int var7;
         if (var5 != -2) {
            if (var2 > 5 && var2 < var3) {
               var2 -= 6;
               var1[var2++] = '\\';
               var1[var2++] = 'u';
               if (var4 > 255) {
                  int var10 = var4 >> 8 & 255;
                  var1[var2++] = HEX_CHARS[var10 >> 4];
                  var1[var2++] = HEX_CHARS[var10 & 15];
                  var4 = (char)(var4 & 255);
               } else {
                  var1[var2++] = '0';
                  var1[var2++] = '0';
               }

               var1[var2++] = HEX_CHARS[var4 >> 4];
               var1[var2] = HEX_CHARS[var4 & 15];
               var2 -= 5;
            } else {
               var9 = this._entityBuffer;
               if (var9 == null) {
                  var9 = this._allocateEntityBuffer();
               }

               this._outputHead = this._outputTail;
               if (var4 > 255) {
                  var7 = var4 >> 8 & 255;
                  int var8 = var4 & 255;
                  var9[10] = HEX_CHARS[var7 >> 4];
                  var9[11] = HEX_CHARS[var7 & 15];
                  var9[12] = HEX_CHARS[var8 >> 4];
                  var9[13] = HEX_CHARS[var8 & 15];
                  this._writer.write(var9, 8, 6);
               } else {
                  var9[6] = HEX_CHARS[var4 >> 4];
                  var9[7] = HEX_CHARS[var4 & 15];
                  this._writer.write(var9, 2, 6);
               }
            }

            return var2;
         } else {
            String var6;
            if (this._currentEscape == null) {
               var6 = this._characterEscapes.getEscapeSequence(var4).getValue();
            } else {
               var6 = this._currentEscape.getValue();
               this._currentEscape = null;
            }

            var7 = var6.length();
            if (var2 >= var7 && var2 < var3) {
               var2 -= var7;
               var6.getChars(0, var7, var1, var2);
            } else {
               this._writer.write(var6);
            }

            return var2;
         }
      }
   }

   private void _appendCharacterEscape(char var1, int var2) throws IOException, JsonGenerationException {
      if (var2 >= 0) {
         if (this._outputTail + 2 > this._outputEnd) {
            this._flushBuffer();
         }

         this._outputBuffer[this._outputTail++] = '\\';
         this._outputBuffer[this._outputTail++] = (char)var2;
      } else if (var2 != -2) {
         if (this._outputTail + 5 >= this._outputEnd) {
            this._flushBuffer();
         }

         int var6 = this._outputTail;
         char[] var7 = this._outputBuffer;
         var7[var6++] = '\\';
         var7[var6++] = 'u';
         if (var1 > 255) {
            int var5 = var1 >> 8 & 255;
            var7[var6++] = HEX_CHARS[var5 >> 4];
            var7[var6++] = HEX_CHARS[var5 & 15];
            var1 = (char)(var1 & 255);
         } else {
            var7[var6++] = '0';
            var7[var6++] = '0';
         }

         var7[var6++] = HEX_CHARS[var1 >> 4];
         var7[var6++] = HEX_CHARS[var1 & 15];
         this._outputTail = var6;
      } else {
         String var3;
         if (this._currentEscape == null) {
            var3 = this._characterEscapes.getEscapeSequence(var1).getValue();
         } else {
            var3 = this._currentEscape.getValue();
            this._currentEscape = null;
         }

         int var4 = var3.length();
         if (this._outputTail + var4 > this._outputEnd) {
            this._flushBuffer();
            if (var4 > this._outputEnd) {
               this._writer.write(var3);
               return;
            }
         }

         var3.getChars(0, var4, this._outputBuffer, this._outputTail);
         this._outputTail += var4;
      }
   }

   private char[] _allocateEntityBuffer() {
      char[] var1 = new char[14];
      var1[0] = '\\';
      var1[2] = '\\';
      var1[3] = 'u';
      var1[4] = '0';
      var1[5] = '0';
      var1[8] = '\\';
      var1[9] = 'u';
      this._entityBuffer = var1;
      return var1;
   }

   private char[] _allocateCopyBuffer() {
      if (this._charBuffer == null) {
         this._charBuffer = this._ioContext.allocNameCopyBuffer(2000);
      }

      return this._charBuffer;
   }

   protected void _flushBuffer() throws IOException {
      int var1 = this._outputTail - this._outputHead;
      if (var1 > 0) {
         int var2 = this._outputHead;
         this._outputTail = this._outputHead = 0;
         this._writer.write(this._outputBuffer, var2, var1);
      }

   }
}
