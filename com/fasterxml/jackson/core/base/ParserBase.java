package com.fasterxml.jackson.core.base;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonParser$Feature;
import com.fasterxml.jackson.core.JsonParser$NumberType;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.io.NumberInput;
import com.fasterxml.jackson.core.json.DupDetector;
import com.fasterxml.jackson.core.json.JsonReadContext;
import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.TextBuffer;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

public abstract class ParserBase extends ParserMinimalBase {
   protected final IOContext _ioContext;
   protected boolean _closed;
   protected int _inputPtr;
   protected int _inputEnd;
   protected long _currInputProcessed;
   protected int _currInputRow = 1;
   protected int _currInputRowStart;
   protected long _tokenInputTotal;
   protected int _tokenInputRow = 1;
   protected int _tokenInputCol;
   protected JsonReadContext _parsingContext;
   protected JsonToken _nextToken;
   protected final TextBuffer _textBuffer;
   protected char[] _nameCopyBuffer;
   protected boolean _nameCopied;
   protected ByteArrayBuilder _byteArrayBuilder;
   protected byte[] _binaryValue;
   protected int _numTypesValid = 0;
   protected int _numberInt;
   protected long _numberLong;
   protected double _numberDouble;
   protected BigInteger _numberBigInt;
   protected BigDecimal _numberBigDecimal;
   protected boolean _numberNegative;
   protected int _intLength;
   protected int _fractLength;
   protected int _expLength;

   protected ParserBase(IOContext var1, int var2) {
      super(var2);
      this._ioContext = var1;
      this._textBuffer = var1.constructTextBuffer();
      DupDetector var3 = JsonParser$Feature.STRICT_DUPLICATE_DETECTION.enabledIn(var2) ? DupDetector.rootDetector((JsonParser)this) : null;
      this._parsingContext = JsonReadContext.createRootContext(var3);
   }

   public Version version() {
      return PackageVersion.VERSION;
   }

   public Object getCurrentValue() {
      return this._parsingContext.getCurrentValue();
   }

   public void setCurrentValue(Object var1) {
      this._parsingContext.setCurrentValue(var1);
   }

   public JsonParser enable(JsonParser$Feature var1) {
      this._features |= var1.getMask();
      if (var1 == JsonParser$Feature.STRICT_DUPLICATE_DETECTION && this._parsingContext.getDupDetector() == null) {
         this._parsingContext = this._parsingContext.withDupDetector(DupDetector.rootDetector((JsonParser)this));
      }

      return this;
   }

   public JsonParser disable(JsonParser$Feature var1) {
      this._features &= ~var1.getMask();
      if (var1 == JsonParser$Feature.STRICT_DUPLICATE_DETECTION) {
         this._parsingContext = this._parsingContext.withDupDetector((DupDetector)null);
      }

      return this;
   }

   /** @deprecated */
   @Deprecated
   public JsonParser setFeatureMask(int var1) {
      int var2 = this._features ^ var1;
      if (var2 != 0) {
         this._features = var1;
         this._checkStdFeatureChanges(var1, var2);
      }

      return this;
   }

   public JsonParser overrideStdFeatures(int var1, int var2) {
      int var3 = this._features;
      int var4 = var3 & ~var2 | var1 & var2;
      int var5 = var3 ^ var4;
      if (var5 != 0) {
         this._features = var4;
         this._checkStdFeatureChanges(var4, var5);
      }

      return this;
   }

   protected void _checkStdFeatureChanges(int var1, int var2) {
      int var3 = JsonParser$Feature.STRICT_DUPLICATE_DETECTION.getMask();
      if ((var2 & var3) != 0 && (var1 & var3) != 0) {
         if (this._parsingContext.getDupDetector() == null) {
            this._parsingContext = this._parsingContext.withDupDetector(DupDetector.rootDetector((JsonParser)this));
         } else {
            this._parsingContext = this._parsingContext.withDupDetector((DupDetector)null);
         }
      }

   }

   public String getCurrentName() throws IOException {
      if (this._currToken == JsonToken.START_OBJECT || this._currToken == JsonToken.START_ARRAY) {
         JsonReadContext var1 = this._parsingContext.getParent();
         if (var1 != null) {
            return var1.getCurrentName();
         }
      }

      return this._parsingContext.getCurrentName();
   }

   public void overrideCurrentName(String var1) {
      JsonReadContext var2 = this._parsingContext;
      if (this._currToken == JsonToken.START_OBJECT || this._currToken == JsonToken.START_ARRAY) {
         var2 = var2.getParent();
      }

      try {
         var2.setCurrentName(var1);
      } catch (IOException var4) {
         throw new IllegalStateException(var4);
      }

   }

   public void close() throws IOException {
      if (!this._closed) {
         this._inputPtr = Math.max(this._inputPtr, this._inputEnd);
         this._closed = true;
         boolean var3 = false;

         try {
            var3 = true;
            this._closeInput();
            var3 = false;
         } finally {
            if (var3) {
               this._releaseBuffers();
            }
         }

         this._releaseBuffers();
      }

   }

   public boolean isClosed() {
      return this._closed;
   }

   public JsonReadContext getParsingContext() {
      return this._parsingContext;
   }

   public JsonLocation getTokenLocation() {
      return new JsonLocation(this._getSourceReference(), -1L, this.getTokenCharacterOffset(), this.getTokenLineNr(), this.getTokenColumnNr());
   }

   public JsonLocation getCurrentLocation() {
      int var1 = this._inputPtr - this._currInputRowStart + 1;
      return new JsonLocation(this._getSourceReference(), -1L, this._currInputProcessed + (long)this._inputPtr, this._currInputRow, var1);
   }

   public boolean hasTextCharacters() {
      if (this._currToken == JsonToken.VALUE_STRING) {
         return true;
      } else {
         return this._currToken == JsonToken.FIELD_NAME ? this._nameCopied : false;
      }
   }

   public byte[] getBinaryValue(Base64Variant var1) throws IOException {
      if (this._binaryValue == null) {
         if (this._currToken != JsonToken.VALUE_STRING) {
            this._reportError("Current token (" + this._currToken + ") not VALUE_STRING, can not access as binary");
         }

         ByteArrayBuilder var2 = this._getByteArrayBuilder();
         this._decodeBase64(this.getText(), var2, var1);
         this._binaryValue = var2.toByteArray();
      }

      return this._binaryValue;
   }

   public long getTokenCharacterOffset() {
      return this._tokenInputTotal;
   }

   public int getTokenLineNr() {
      return this._tokenInputRow;
   }

   public int getTokenColumnNr() {
      int var1 = this._tokenInputCol;
      return var1 < 0 ? var1 : var1 + 1;
   }

   protected abstract void _closeInput() throws IOException;

   protected void _releaseBuffers() throws IOException {
      this._textBuffer.releaseBuffers();
      char[] var1 = this._nameCopyBuffer;
      if (var1 != null) {
         this._nameCopyBuffer = null;
         this._ioContext.releaseNameCopyBuffer(var1);
      }

   }

   protected void _handleEOF() throws JsonParseException {
      if (!this._parsingContext.inRoot()) {
         String var1 = this._parsingContext.inArray() ? "Array" : "Object";
         this._reportInvalidEOF(String.format(": expected close marker for %s (start marker at %s)", var1, this._parsingContext.getStartLocation(this._getSourceReference())), (JsonToken)null);
      }

   }

   protected final int _eofAsNextChar() throws JsonParseException {
      this._handleEOF();
      return -1;
   }

   public ByteArrayBuilder _getByteArrayBuilder() {
      if (this._byteArrayBuilder == null) {
         this._byteArrayBuilder = new ByteArrayBuilder();
      } else {
         this._byteArrayBuilder.reset();
      }

      return this._byteArrayBuilder;
   }

   protected final JsonToken reset(boolean var1, int var2, int var3, int var4) {
      return var3 < 1 && var4 < 1 ? this.resetInt(var1, var2) : this.resetFloat(var1, var2, var3, var4);
   }

   protected final JsonToken resetInt(boolean var1, int var2) {
      this._numberNegative = var1;
      this._intLength = var2;
      this._fractLength = 0;
      this._expLength = 0;
      this._numTypesValid = 0;
      return JsonToken.VALUE_NUMBER_INT;
   }

   protected final JsonToken resetFloat(boolean var1, int var2, int var3, int var4) {
      this._numberNegative = var1;
      this._intLength = var2;
      this._fractLength = var3;
      this._expLength = var4;
      this._numTypesValid = 0;
      return JsonToken.VALUE_NUMBER_FLOAT;
   }

   protected final JsonToken resetAsNaN(String var1, double var2) {
      this._textBuffer.resetWithString(var1);
      this._numberDouble = var2;
      this._numTypesValid = 8;
      return JsonToken.VALUE_NUMBER_FLOAT;
   }

   public boolean isNaN() {
      if (this._currToken == JsonToken.VALUE_NUMBER_FLOAT && (this._numTypesValid & 8) != 0) {
         double var1 = this._numberDouble;
         return Double.isNaN(var1) || Double.isInfinite(var1);
      } else {
         return false;
      }
   }

   public Number getNumberValue() throws IOException {
      if (this._numTypesValid == 0) {
         this._parseNumericValue(0);
      }

      if (this._currToken == JsonToken.VALUE_NUMBER_INT) {
         if ((this._numTypesValid & 1) != 0) {
            return this._numberInt;
         } else if ((this._numTypesValid & 2) != 0) {
            return this._numberLong;
         } else {
            return (Number)((this._numTypesValid & 4) != 0 ? this._numberBigInt : this._numberBigDecimal);
         }
      } else if ((this._numTypesValid & 16) != 0) {
         return this._numberBigDecimal;
      } else {
         if ((this._numTypesValid & 8) == 0) {
            this._throwInternal();
         }

         return this._numberDouble;
      }
   }

   public JsonParser$NumberType getNumberType() throws IOException {
      if (this._numTypesValid == 0) {
         this._parseNumericValue(0);
      }

      if (this._currToken == JsonToken.VALUE_NUMBER_INT) {
         if ((this._numTypesValid & 1) != 0) {
            return JsonParser$NumberType.INT;
         } else {
            return (this._numTypesValid & 2) != 0 ? JsonParser$NumberType.LONG : JsonParser$NumberType.BIG_INTEGER;
         }
      } else {
         return (this._numTypesValid & 16) != 0 ? JsonParser$NumberType.BIG_DECIMAL : JsonParser$NumberType.DOUBLE;
      }
   }

   public int getIntValue() throws IOException {
      if ((this._numTypesValid & 1) == 0) {
         if (this._numTypesValid == 0) {
            return this._parseIntValue();
         }

         if ((this._numTypesValid & 1) == 0) {
            this.convertNumberToInt();
         }
      }

      return this._numberInt;
   }

   public long getLongValue() throws IOException {
      if ((this._numTypesValid & 2) == 0) {
         if (this._numTypesValid == 0) {
            this._parseNumericValue(2);
         }

         if ((this._numTypesValid & 2) == 0) {
            this.convertNumberToLong();
         }
      }

      return this._numberLong;
   }

   public BigInteger getBigIntegerValue() throws IOException {
      if ((this._numTypesValid & 4) == 0) {
         if (this._numTypesValid == 0) {
            this._parseNumericValue(4);
         }

         if ((this._numTypesValid & 4) == 0) {
            this.convertNumberToBigInteger();
         }
      }

      return this._numberBigInt;
   }

   public float getFloatValue() throws IOException {
      double var1 = this.getDoubleValue();
      return (float)var1;
   }

   public double getDoubleValue() throws IOException {
      if ((this._numTypesValid & 8) == 0) {
         if (this._numTypesValid == 0) {
            this._parseNumericValue(8);
         }

         if ((this._numTypesValid & 8) == 0) {
            this.convertNumberToDouble();
         }
      }

      return this._numberDouble;
   }

   public BigDecimal getDecimalValue() throws IOException {
      if ((this._numTypesValid & 16) == 0) {
         if (this._numTypesValid == 0) {
            this._parseNumericValue(16);
         }

         if ((this._numTypesValid & 16) == 0) {
            this.convertNumberToBigDecimal();
         }
      }

      return this._numberBigDecimal;
   }

   protected void _parseNumericValue(int var1) throws IOException {
      if (this._currToken == JsonToken.VALUE_NUMBER_INT) {
         int var2 = this._intLength;
         if (var2 <= 9) {
            int var5 = this._textBuffer.contentsAsInt(this._numberNegative);
            this._numberInt = var5;
            this._numTypesValid = 1;
         } else if (var2 <= 18) {
            long var3 = this._textBuffer.contentsAsLong(this._numberNegative);
            if (var2 == 10) {
               if (this._numberNegative) {
                  if (var3 >= -2147483648L) {
                     this._numberInt = (int)var3;
                     this._numTypesValid = 1;
                     return;
                  }
               } else if (var3 <= 0L) {
                  this._numberInt = (int)var3;
                  this._numTypesValid = 1;
                  return;
               }
            }

            this._numberLong = var3;
            this._numTypesValid = 2;
         } else {
            this._parseSlowInt(var1);
         }
      } else if (this._currToken == JsonToken.VALUE_NUMBER_FLOAT) {
         this._parseSlowFloat(var1);
      } else {
         this._reportError("Current token (%s) not numeric, can not use numeric value accessors", this._currToken);
      }
   }

   protected int _parseIntValue() throws IOException {
      if (this._currToken == JsonToken.VALUE_NUMBER_INT && this._intLength <= 9) {
         int var1 = this._textBuffer.contentsAsInt(this._numberNegative);
         this._numberInt = var1;
         this._numTypesValid = 1;
         return var1;
      } else {
         this._parseNumericValue(1);
         if ((this._numTypesValid & 1) == 0) {
            this.convertNumberToInt();
         }

         return this._numberInt;
      }
   }

   private void _parseSlowFloat(int var1) throws IOException {
      try {
         if (var1 == 16) {
            this._numberBigDecimal = this._textBuffer.contentsAsDecimal();
            this._numTypesValid = 16;
         } else {
            this._numberDouble = this._textBuffer.contentsAsDouble();
            this._numTypesValid = 8;
         }
      } catch (NumberFormatException var3) {
         this._wrapError("Malformed numeric value '" + this._textBuffer.contentsAsString() + "'", var3);
      }

   }

   private void _parseSlowInt(int var1) throws IOException {
      String var2 = this._textBuffer.contentsAsString();

      try {
         int var3 = this._intLength;
         char[] var4 = this._textBuffer.getTextBuffer();
         int var5 = this._textBuffer.getTextOffset();
         if (this._numberNegative) {
            ++var5;
         }

         if (NumberInput.inLongRange(var4, var5, var3, this._numberNegative)) {
            this._numberLong = Long.parseLong(var2);
            this._numTypesValid = 2;
         } else {
            this._numberBigInt = new BigInteger(var2);
            this._numTypesValid = 4;
         }
      } catch (NumberFormatException var6) {
         this._wrapError("Malformed numeric value '" + var2 + "'", var6);
      }

   }

   protected void convertNumberToInt() throws IOException {
      if ((this._numTypesValid & 2) != 0) {
         int var1 = (int)this._numberLong;
         if ((long)var1 != this._numberLong) {
            this._reportError("Numeric value (" + this.getText() + ") out of range of int");
         }

         this._numberInt = var1;
      } else if ((this._numTypesValid & 4) != 0) {
         if (BI_MIN_INT.compareTo(this._numberBigInt) > 0 || BI_MAX_INT.compareTo(this._numberBigInt) < 0) {
            this.reportOverflowInt();
         }

         this._numberInt = this._numberBigInt.intValue();
      } else if ((this._numTypesValid & 8) != 0) {
         if (this._numberDouble < -2.147483648E9D || this._numberDouble > 2.147483647E9D) {
            this.reportOverflowInt();
         }

         this._numberInt = (int)this._numberDouble;
      } else if ((this._numTypesValid & 16) != 0) {
         if (BD_MIN_INT.compareTo(this._numberBigDecimal) > 0 || BD_MAX_INT.compareTo(this._numberBigDecimal) < 0) {
            this.reportOverflowInt();
         }

         this._numberInt = this._numberBigDecimal.intValue();
      } else {
         this._throwInternal();
      }

      this._numTypesValid |= 1;
   }

   protected void convertNumberToLong() throws IOException {
      if ((this._numTypesValid & 1) != 0) {
         this._numberLong = (long)this._numberInt;
      } else if ((this._numTypesValid & 4) != 0) {
         if (BI_MIN_LONG.compareTo(this._numberBigInt) > 0 || BI_MAX_LONG.compareTo(this._numberBigInt) < 0) {
            this.reportOverflowLong();
         }

         this._numberLong = this._numberBigInt.longValue();
      } else if ((this._numTypesValid & 8) != 0) {
         if (this._numberDouble < -9.223372036854776E18D || this._numberDouble > 9.223372036854776E18D) {
            this.reportOverflowLong();
         }

         this._numberLong = (long)this._numberDouble;
      } else if ((this._numTypesValid & 16) != 0) {
         if (BD_MIN_LONG.compareTo(this._numberBigDecimal) > 0 || BD_MAX_LONG.compareTo(this._numberBigDecimal) < 0) {
            this.reportOverflowLong();
         }

         this._numberLong = this._numberBigDecimal.longValue();
      } else {
         this._throwInternal();
      }

      this._numTypesValid |= 2;
   }

   protected void convertNumberToBigInteger() throws IOException {
      if ((this._numTypesValid & 16) != 0) {
         this._numberBigInt = this._numberBigDecimal.toBigInteger();
      } else if ((this._numTypesValid & 2) != 0) {
         this._numberBigInt = BigInteger.valueOf(this._numberLong);
      } else if ((this._numTypesValid & 1) != 0) {
         this._numberBigInt = BigInteger.valueOf((long)this._numberInt);
      } else if ((this._numTypesValid & 8) != 0) {
         this._numberBigInt = BigDecimal.valueOf(this._numberDouble).toBigInteger();
      } else {
         this._throwInternal();
      }

      this._numTypesValid |= 4;
   }

   protected void convertNumberToDouble() throws IOException {
      if ((this._numTypesValid & 16) != 0) {
         this._numberDouble = this._numberBigDecimal.doubleValue();
      } else if ((this._numTypesValid & 4) != 0) {
         this._numberDouble = this._numberBigInt.doubleValue();
      } else if ((this._numTypesValid & 2) != 0) {
         this._numberDouble = (double)this._numberLong;
      } else if ((this._numTypesValid & 1) != 0) {
         this._numberDouble = (double)this._numberInt;
      } else {
         this._throwInternal();
      }

      this._numTypesValid |= 8;
   }

   protected void convertNumberToBigDecimal() throws IOException {
      if ((this._numTypesValid & 8) != 0) {
         this._numberBigDecimal = NumberInput.parseBigDecimal(this.getText());
      } else if ((this._numTypesValid & 4) != 0) {
         this._numberBigDecimal = new BigDecimal(this._numberBigInt);
      } else if ((this._numTypesValid & 2) != 0) {
         this._numberBigDecimal = BigDecimal.valueOf(this._numberLong);
      } else if ((this._numTypesValid & 1) != 0) {
         this._numberBigDecimal = BigDecimal.valueOf((long)this._numberInt);
      } else {
         this._throwInternal();
      }

      this._numTypesValid |= 16;
   }

   protected void _reportMismatchedEndMarker(int var1, char var2) throws JsonParseException {
      JsonReadContext var3 = this.getParsingContext();
      this._reportError(String.format("Unexpected close marker '%s': expected '%c' (for %s starting at %s)", (char)var1, var2, var3.typeDesc(), var3.getStartLocation(this._getSourceReference())));
   }

   protected char _decodeEscaped() throws IOException {
      throw new UnsupportedOperationException();
   }

   protected final int _decodeBase64Escape(Base64Variant var1, int var2, int var3) throws IOException {
      if (var2 != 92) {
         throw this.reportInvalidBase64Char(var1, var2, var3);
      } else {
         char var4 = this._decodeEscaped();
         if (var4 <= ' ' && var3 == 0) {
            return -1;
         } else {
            int var5 = var1.decodeBase64Char((int)var4);
            if (var5 < 0) {
               throw this.reportInvalidBase64Char(var1, var4, var3);
            } else {
               return var5;
            }
         }
      }
   }

   protected final int _decodeBase64Escape(Base64Variant var1, char var2, int var3) throws IOException {
      if (var2 != '\\') {
         throw this.reportInvalidBase64Char(var1, var2, var3);
      } else {
         char var4 = this._decodeEscaped();
         if (var4 <= ' ' && var3 == 0) {
            return -1;
         } else {
            int var5 = var1.decodeBase64Char(var4);
            if (var5 < 0) {
               throw this.reportInvalidBase64Char(var1, var4, var3);
            } else {
               return var5;
            }
         }
      }
   }

   protected IllegalArgumentException reportInvalidBase64Char(Base64Variant var1, int var2, int var3) throws IllegalArgumentException {
      return this.reportInvalidBase64Char(var1, var2, var3, (String)null);
   }

   protected IllegalArgumentException reportInvalidBase64Char(Base64Variant var1, int var2, int var3, String var4) throws IllegalArgumentException {
      String var5;
      if (var2 <= 32) {
         var5 = String.format("Illegal white space character (code 0x%s) as character #%d of 4-char base64 unit: can only used between units", Integer.toHexString(var2), var3 + 1);
      } else if (var1.usesPaddingChar(var2)) {
         var5 = "Unexpected padding character ('" + var1.getPaddingChar() + "') as character #" + (var3 + 1) + " of 4-char base64 unit: padding only legal as 3rd or 4th character";
      } else if (Character.isDefined(var2) && !Character.isISOControl(var2)) {
         var5 = "Illegal character '" + (char)var2 + "' (code 0x" + Integer.toHexString(var2) + ") in base64 content";
      } else {
         var5 = "Illegal character (code 0x" + Integer.toHexString(var2) + ") in base64 content";
      }

      if (var4 != null) {
         var5 = var5 + ": " + var4;
      }

      return new IllegalArgumentException(var5);
   }

   protected Object _getSourceReference() {
      return JsonParser$Feature.INCLUDE_SOURCE_IN_LOCATION.enabledIn(this._features) ? this._ioContext.getSourceReference() : null;
   }

   protected static int[] growArrayBy(int[] var0, int var1) {
      return var0 == null ? new int[var1] : Arrays.copyOf(var0, var0.length + var1);
   }

   /** @deprecated */
   @Deprecated
   protected void loadMoreGuaranteed() throws IOException {
      if (!this.loadMore()) {
         this._reportInvalidEOF();
      }

   }

   /** @deprecated */
   @Deprecated
   protected boolean loadMore() throws IOException {
      return false;
   }

   protected void _finishString() throws IOException {
   }

   public JsonStreamContext getParsingContext() {
      return this.getParsingContext();
   }
}
