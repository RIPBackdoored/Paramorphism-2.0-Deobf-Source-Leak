package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser$NumberType;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.base.ParserMinimalBase;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.databind.cfg.PackageVersion;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

public final class TokenBuffer$Parser extends ParserMinimalBase {
   protected ObjectCodec _codec;
   protected final boolean _hasNativeTypeIds;
   protected final boolean _hasNativeObjectIds;
   protected final boolean _hasNativeIds;
   protected TokenBuffer$Segment _segment;
   protected int _segmentPtr;
   protected TokenBufferReadContext _parsingContext;
   protected boolean _closed;
   protected transient ByteArrayBuilder _byteBuilder;
   protected JsonLocation _location;

   /** @deprecated */
   @Deprecated
   public TokenBuffer$Parser(TokenBuffer$Segment var1, ObjectCodec var2, boolean var3, boolean var4) {
      this(var1, var2, var3, var4, (JsonStreamContext)null);
   }

   public TokenBuffer$Parser(TokenBuffer$Segment var1, ObjectCodec var2, boolean var3, boolean var4, JsonStreamContext var5) {
      super(0);
      this._location = null;
      this._segment = var1;
      this._segmentPtr = -1;
      this._codec = var2;
      this._parsingContext = TokenBufferReadContext.createRootContext(var5);
      this._hasNativeTypeIds = var3;
      this._hasNativeObjectIds = var4;
      this._hasNativeIds = var3 | var4;
   }

   public void setLocation(JsonLocation var1) {
      this._location = var1;
   }

   public ObjectCodec getCodec() {
      return this._codec;
   }

   public void setCodec(ObjectCodec var1) {
      this._codec = var1;
   }

   public Version version() {
      return PackageVersion.VERSION;
   }

   public JsonToken peekNextToken() throws IOException {
      if (this._closed) {
         return null;
      } else {
         TokenBuffer$Segment var1 = this._segment;
         int var2 = this._segmentPtr + 1;
         if (var2 >= 16) {
            var2 = 0;
            var1 = var1 == null ? null : var1.next();
         }

         return var1 == null ? null : var1.type(var2);
      }
   }

   public void close() throws IOException {
      if (!this._closed) {
         this._closed = true;
      }

   }

   public JsonToken nextToken() throws IOException {
      if (!this._closed && this._segment != null) {
         if (++this._segmentPtr >= 16) {
            this._segmentPtr = 0;
            this._segment = this._segment.next();
            if (this._segment == null) {
               return null;
            }
         }

         this._currToken = this._segment.type(this._segmentPtr);
         if (this._currToken == JsonToken.FIELD_NAME) {
            Object var1 = this._currentObject();
            String var2 = var1 instanceof String ? (String)var1 : var1.toString();
            this._parsingContext.setCurrentName(var2);
         } else if (this._currToken == JsonToken.START_OBJECT) {
            this._parsingContext = this._parsingContext.createChildObjectContext();
         } else if (this._currToken == JsonToken.START_ARRAY) {
            this._parsingContext = this._parsingContext.createChildArrayContext();
         } else if (this._currToken == JsonToken.END_OBJECT || this._currToken == JsonToken.END_ARRAY) {
            this._parsingContext = this._parsingContext.parentOrCopy();
         }

         return this._currToken;
      } else {
         return null;
      }
   }

   public String nextFieldName() throws IOException {
      if (!this._closed && this._segment != null) {
         int var1 = this._segmentPtr + 1;
         if (var1 < 16 && this._segment.type(var1) == JsonToken.FIELD_NAME) {
            this._segmentPtr = var1;
            this._currToken = JsonToken.FIELD_NAME;
            Object var2 = this._segment.get(var1);
            String var3 = var2 instanceof String ? (String)var2 : var2.toString();
            this._parsingContext.setCurrentName(var3);
            return var3;
         } else {
            return this.nextToken() == JsonToken.FIELD_NAME ? this.getCurrentName() : null;
         }
      } else {
         return null;
      }
   }

   public boolean isClosed() {
      return this._closed;
   }

   public JsonStreamContext getParsingContext() {
      return this._parsingContext;
   }

   public JsonLocation getTokenLocation() {
      return this.getCurrentLocation();
   }

   public JsonLocation getCurrentLocation() {
      return this._location == null ? JsonLocation.NA : this._location;
   }

   public String getCurrentName() {
      if (this._currToken != JsonToken.START_OBJECT && this._currToken != JsonToken.START_ARRAY) {
         return this._parsingContext.getCurrentName();
      } else {
         JsonStreamContext var1 = this._parsingContext.getParent();
         return var1.getCurrentName();
      }
   }

   public void overrideCurrentName(String var1) {
      Object var2 = this._parsingContext;
      if (this._currToken == JsonToken.START_OBJECT || this._currToken == JsonToken.START_ARRAY) {
         var2 = ((JsonStreamContext)var2).getParent();
      }

      if (var2 instanceof TokenBufferReadContext) {
         try {
            ((TokenBufferReadContext)var2).setCurrentName(var1);
         } catch (IOException var4) {
            throw new RuntimeException(var4);
         }
      }

   }

   public String getText() {
      // $FF: Couldn't be decompiled
   }

   public char[] getTextCharacters() {
      String var1 = this.getText();
      return var1 == null ? null : var1.toCharArray();
   }

   public int getTextLength() {
      String var1 = this.getText();
      return var1 == null ? 0 : var1.length();
   }

   public int getTextOffset() {
      return 0;
   }

   public boolean hasTextCharacters() {
      return false;
   }

   public boolean isNaN() {
      if (this._currToken == JsonToken.VALUE_NUMBER_FLOAT) {
         Object var1 = this._currentObject();
         if (var1 instanceof Double) {
            Double var3 = (Double)var1;
            return var3.isNaN() || var3.isInfinite();
         }

         if (var1 instanceof Float) {
            Float var2 = (Float)var1;
            return var2.isNaN() || var2.isInfinite();
         }
      }

      return false;
   }

   public BigInteger getBigIntegerValue() throws IOException {
      Number var1 = this.getNumberValue();
      if (var1 instanceof BigInteger) {
         return (BigInteger)var1;
      } else {
         return this.getNumberType() == JsonParser$NumberType.BIG_DECIMAL ? ((BigDecimal)var1).toBigInteger() : BigInteger.valueOf(var1.longValue());
      }
   }

   public BigDecimal getDecimalValue() throws IOException {
      // $FF: Couldn't be decompiled
   }

   public double getDoubleValue() throws IOException {
      return this.getNumberValue().doubleValue();
   }

   public float getFloatValue() throws IOException {
      return this.getNumberValue().floatValue();
   }

   public int getIntValue() throws IOException {
      Number var1 = this._currToken == JsonToken.VALUE_NUMBER_INT ? (Number)this._currentObject() : this.getNumberValue();
      return !(var1 instanceof Integer) && !this._smallerThanInt(var1) ? this._convertNumberToInt(var1) : var1.intValue();
   }

   public long getLongValue() throws IOException {
      Number var1 = this._currToken == JsonToken.VALUE_NUMBER_INT ? (Number)this._currentObject() : this.getNumberValue();
      return !(var1 instanceof Long) && !this._smallerThanLong(var1) ? this._convertNumberToLong(var1) : var1.longValue();
   }

   public JsonParser$NumberType getNumberType() throws IOException {
      Number var1 = this.getNumberValue();
      if (var1 instanceof Integer) {
         return JsonParser$NumberType.INT;
      } else if (var1 instanceof Long) {
         return JsonParser$NumberType.LONG;
      } else if (var1 instanceof Double) {
         return JsonParser$NumberType.DOUBLE;
      } else if (var1 instanceof BigDecimal) {
         return JsonParser$NumberType.BIG_DECIMAL;
      } else if (var1 instanceof BigInteger) {
         return JsonParser$NumberType.BIG_INTEGER;
      } else if (var1 instanceof Float) {
         return JsonParser$NumberType.FLOAT;
      } else {
         return var1 instanceof Short ? JsonParser$NumberType.INT : null;
      }
   }

   public final Number getNumberValue() throws IOException {
      this._checkIsNumber();
      Object var1 = this._currentObject();
      if (var1 instanceof Number) {
         return (Number)var1;
      } else if (var1 instanceof String) {
         String var2 = (String)var1;
         return (Number)(var2.indexOf(46) >= 0 ? Double.parseDouble(var2) : Long.parseLong(var2));
      } else if (var1 == null) {
         return null;
      } else {
         throw new IllegalStateException("Internal error: entry should be a Number, but is of type " + var1.getClass().getName());
      }
   }

   private final boolean _smallerThanInt(Number var1) {
      return var1 instanceof Short || var1 instanceof Byte;
   }

   private final boolean _smallerThanLong(Number var1) {
      return var1 instanceof Integer || var1 instanceof Short || var1 instanceof Byte;
   }

   protected int _convertNumberToInt(Number var1) throws IOException {
      if (var1 instanceof Long) {
         long var7 = var1.longValue();
         int var4 = (int)var7;
         if ((long)var4 != var7) {
            this.reportOverflowInt();
         }

         return var4;
      } else {
         if (var1 instanceof BigInteger) {
            BigInteger var6 = (BigInteger)var1;
            if (BI_MIN_INT.compareTo(var6) > 0 || BI_MAX_INT.compareTo(var6) < 0) {
               this.reportOverflowInt();
            }
         } else {
            if (var1 instanceof Double || var1 instanceof Float) {
               double var5 = var1.doubleValue();
               if (var5 < -2.147483648E9D || var5 > 2.147483647E9D) {
                  this.reportOverflowInt();
               }

               return (int)var5;
            }

            if (var1 instanceof BigDecimal) {
               BigDecimal var2 = (BigDecimal)var1;
               if (BD_MIN_INT.compareTo(var2) > 0 || BD_MAX_INT.compareTo(var2) < 0) {
                  this.reportOverflowInt();
               }
            } else {
               this._throwInternal();
            }
         }

         return var1.intValue();
      }
   }

   protected long _convertNumberToLong(Number var1) throws IOException {
      if (var1 instanceof BigInteger) {
         BigInteger var5 = (BigInteger)var1;
         if (BI_MIN_LONG.compareTo(var5) > 0 || BI_MAX_LONG.compareTo(var5) < 0) {
            this.reportOverflowLong();
         }
      } else {
         if (var1 instanceof Double || var1 instanceof Float) {
            double var4 = var1.doubleValue();
            if (var4 < -9.223372036854776E18D || var4 > 9.223372036854776E18D) {
               this.reportOverflowLong();
            }

            return (long)var4;
         }

         if (var1 instanceof BigDecimal) {
            BigDecimal var2 = (BigDecimal)var1;
            if (BD_MIN_LONG.compareTo(var2) > 0 || BD_MAX_LONG.compareTo(var2) < 0) {
               this.reportOverflowLong();
            }
         } else {
            this._throwInternal();
         }
      }

      return var1.longValue();
   }

   public Object getEmbeddedObject() {
      return this._currToken == JsonToken.VALUE_EMBEDDED_OBJECT ? this._currentObject() : null;
   }

   public byte[] getBinaryValue(Base64Variant var1) throws IOException, JsonParseException {
      if (this._currToken == JsonToken.VALUE_EMBEDDED_OBJECT) {
         Object var2 = this._currentObject();
         if (var2 instanceof byte[]) {
            return (byte[])((byte[])var2);
         }
      }

      if (this._currToken != JsonToken.VALUE_STRING) {
         throw this._constructError("Current token (" + this._currToken + ") not VALUE_STRING (or VALUE_EMBEDDED_OBJECT with byte[]), cannot access as binary");
      } else {
         String var4 = this.getText();
         if (var4 == null) {
            return null;
         } else {
            ByteArrayBuilder var3 = this._byteBuilder;
            if (var3 == null) {
               this._byteBuilder = var3 = new ByteArrayBuilder(100);
            } else {
               this._byteBuilder.reset();
            }

            this._decodeBase64(var4, var3, var1);
            return var3.toByteArray();
         }
      }
   }

   public int readBinaryValue(Base64Variant var1, OutputStream var2) throws IOException {
      byte[] var3 = this.getBinaryValue(var1);
      if (var3 != null) {
         var2.write(var3, 0, var3.length);
         return var3.length;
      } else {
         return 0;
      }
   }

   public boolean canReadObjectId() {
      return this._hasNativeObjectIds;
   }

   public boolean canReadTypeId() {
      return this._hasNativeTypeIds;
   }

   public Object getTypeId() {
      return TokenBuffer$Segment.access$100(this._segment, this._segmentPtr);
   }

   public Object getObjectId() {
      return TokenBuffer$Segment.access$000(this._segment, this._segmentPtr);
   }

   protected final Object _currentObject() {
      return this._segment.get(this._segmentPtr);
   }

   protected final void _checkIsNumber() throws JsonParseException {
      if (this._currToken == null || !this._currToken.isNumeric()) {
         throw this._constructError("Current token (" + this._currToken + ") not numeric, cannot use numeric value accessors");
      }
   }

   protected void _handleEOF() throws JsonParseException {
      this._throwInternal();
   }
}
