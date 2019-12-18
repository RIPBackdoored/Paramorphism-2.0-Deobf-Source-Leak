package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.async.NonBlockingInputFeeder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.RequestPayload;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;

public abstract class JsonParser implements Closeable, Versioned {
   private static final int MIN_BYTE_I = -128;
   private static final int MAX_BYTE_I = 255;
   private static final int MIN_SHORT_I = -32768;
   private static final int MAX_SHORT_I = 32767;
   protected int _features;
   protected transient RequestPayload _requestPayload;

   protected JsonParser() {
      super();
   }

   protected JsonParser(int var1) {
      super();
      this._features = var1;
   }

   public abstract ObjectCodec getCodec();

   public abstract void setCodec(ObjectCodec var1);

   public Object getInputSource() {
      return null;
   }

   public Object getCurrentValue() {
      JsonStreamContext var1 = this.getParsingContext();
      return var1 == null ? null : var1.getCurrentValue();
   }

   public void setCurrentValue(Object var1) {
      JsonStreamContext var2 = this.getParsingContext();
      if (var2 != null) {
         var2.setCurrentValue(var1);
      }

   }

   public void setRequestPayloadOnError(RequestPayload var1) {
      this._requestPayload = var1;
   }

   public void setRequestPayloadOnError(byte[] var1, String var2) {
      this._requestPayload = var1 == null ? null : new RequestPayload(var1, var2);
   }

   public void setRequestPayloadOnError(String var1) {
      this._requestPayload = var1 == null ? null : new RequestPayload(var1);
   }

   public void setSchema(FormatSchema var1) {
      throw new UnsupportedOperationException("Parser of type " + this.getClass().getName() + " does not support schema of type '" + var1.getSchemaType() + "'");
   }

   public FormatSchema getSchema() {
      return null;
   }

   public boolean canUseSchema(FormatSchema var1) {
      return false;
   }

   public boolean requiresCustomCodec() {
      return false;
   }

   public boolean canParseAsync() {
      return false;
   }

   public NonBlockingInputFeeder getNonBlockingInputFeeder() {
      return null;
   }

   public abstract Version version();

   public abstract void close() throws IOException;

   public abstract boolean isClosed();

   public abstract JsonStreamContext getParsingContext();

   public abstract JsonLocation getTokenLocation();

   public abstract JsonLocation getCurrentLocation();

   public int releaseBuffered(OutputStream var1) throws IOException {
      return -1;
   }

   public int releaseBuffered(Writer var1) throws IOException {
      return -1;
   }

   public JsonParser enable(JsonParser$Feature var1) {
      this._features |= var1.getMask();
      return this;
   }

   public JsonParser disable(JsonParser$Feature var1) {
      this._features &= ~var1.getMask();
      return this;
   }

   public JsonParser configure(JsonParser$Feature var1, boolean var2) {
      if (var2) {
         this.enable(var1);
      } else {
         this.disable(var1);
      }

      return this;
   }

   public boolean isEnabled(JsonParser$Feature var1) {
      return var1.enabledIn(this._features);
   }

   public int getFeatureMask() {
      return this._features;
   }

   /** @deprecated */
   @Deprecated
   public JsonParser setFeatureMask(int var1) {
      this._features = var1;
      return this;
   }

   public JsonParser overrideStdFeatures(int var1, int var2) {
      int var3 = this._features & ~var2 | var1 & var2;
      return this.setFeatureMask(var3);
   }

   public int getFormatFeatures() {
      return 0;
   }

   public JsonParser overrideFormatFeatures(int var1, int var2) {
      throw new IllegalArgumentException("No FormatFeatures defined for parser of type " + this.getClass().getName());
   }

   public abstract JsonToken nextToken() throws IOException;

   public abstract JsonToken nextValue() throws IOException;

   public boolean nextFieldName(SerializableString var1) throws IOException {
      return this.nextToken() == JsonToken.FIELD_NAME && var1.getValue().equals(this.getCurrentName());
   }

   public String nextFieldName() throws IOException {
      return this.nextToken() == JsonToken.FIELD_NAME ? this.getCurrentName() : null;
   }

   public String nextTextValue() throws IOException {
      return this.nextToken() == JsonToken.VALUE_STRING ? this.getText() : null;
   }

   public int nextIntValue(int var1) throws IOException {
      return this.nextToken() == JsonToken.VALUE_NUMBER_INT ? this.getIntValue() : var1;
   }

   public long nextLongValue(long var1) throws IOException {
      return this.nextToken() == JsonToken.VALUE_NUMBER_INT ? this.getLongValue() : var1;
   }

   public Boolean nextBooleanValue() throws IOException {
      JsonToken var1 = this.nextToken();
      if (var1 == JsonToken.VALUE_TRUE) {
         return Boolean.TRUE;
      } else {
         return var1 == JsonToken.VALUE_FALSE ? Boolean.FALSE : null;
      }
   }

   public abstract JsonParser skipChildren() throws IOException;

   public void finishToken() throws IOException {
   }

   public JsonToken currentToken() {
      return this.getCurrentToken();
   }

   public int currentTokenId() {
      return this.getCurrentTokenId();
   }

   public abstract JsonToken getCurrentToken();

   public abstract int getCurrentTokenId();

   public abstract boolean hasCurrentToken();

   public abstract boolean hasTokenId(int var1);

   public abstract boolean hasToken(JsonToken var1);

   public boolean isExpectedStartArrayToken() {
      return this.currentToken() == JsonToken.START_ARRAY;
   }

   public boolean isExpectedStartObjectToken() {
      return this.currentToken() == JsonToken.START_OBJECT;
   }

   public boolean isNaN() throws IOException {
      return false;
   }

   public abstract void clearCurrentToken();

   public abstract JsonToken getLastClearedToken();

   public abstract void overrideCurrentName(String var1);

   public abstract String getCurrentName() throws IOException;

   public String currentName() throws IOException {
      return this.getCurrentName();
   }

   public abstract String getText() throws IOException;

   public int getText(Writer var1) throws IOException, UnsupportedOperationException {
      String var2 = this.getText();
      if (var2 == null) {
         return 0;
      } else {
         var1.write(var2);
         return var2.length();
      }
   }

   public abstract char[] getTextCharacters() throws IOException;

   public abstract int getTextLength() throws IOException;

   public abstract int getTextOffset() throws IOException;

   public abstract boolean hasTextCharacters();

   public abstract Number getNumberValue() throws IOException;

   public abstract JsonParser$NumberType getNumberType() throws IOException;

   public byte getByteValue() throws IOException {
      int var1 = this.getIntValue();
      if (var1 >= -128 && var1 <= 255) {
         return (byte)var1;
      } else {
         throw this._constructError("Numeric value (" + this.getText() + ") out of range of Java byte");
      }
   }

   public short getShortValue() throws IOException {
      int var1 = this.getIntValue();
      if (var1 >= -32768 && var1 <= 32767) {
         return (short)var1;
      } else {
         throw this._constructError("Numeric value (" + this.getText() + ") out of range of Java short");
      }
   }

   public abstract int getIntValue() throws IOException;

   public abstract long getLongValue() throws IOException;

   public abstract BigInteger getBigIntegerValue() throws IOException;

   public abstract float getFloatValue() throws IOException;

   public abstract double getDoubleValue() throws IOException;

   public abstract BigDecimal getDecimalValue() throws IOException;

   public boolean getBooleanValue() throws IOException {
      JsonToken var1 = this.currentToken();
      if (var1 == JsonToken.VALUE_TRUE) {
         return true;
      } else if (var1 == JsonToken.VALUE_FALSE) {
         return false;
      } else {
         throw (new JsonParseException(this, String.format("Current token (%s) not of boolean type", var1))).withRequestPayload(this._requestPayload);
      }
   }

   public Object getEmbeddedObject() throws IOException {
      return null;
   }

   public abstract byte[] getBinaryValue(Base64Variant var1) throws IOException;

   public byte[] getBinaryValue() throws IOException {
      return this.getBinaryValue(Base64Variants.getDefaultVariant());
   }

   public int readBinaryValue(OutputStream var1) throws IOException {
      return this.readBinaryValue(Base64Variants.getDefaultVariant(), var1);
   }

   public int readBinaryValue(Base64Variant var1, OutputStream var2) throws IOException {
      this._reportUnsupportedOperation();
      return 0;
   }

   public int getValueAsInt() throws IOException {
      return this.getValueAsInt(0);
   }

   public int getValueAsInt(int var1) throws IOException {
      return var1;
   }

   public long getValueAsLong() throws IOException {
      return this.getValueAsLong(0L);
   }

   public long getValueAsLong(long var1) throws IOException {
      return var1;
   }

   public double getValueAsDouble() throws IOException {
      return this.getValueAsDouble(0.0D);
   }

   public double getValueAsDouble(double var1) throws IOException {
      return var1;
   }

   public boolean getValueAsBoolean() throws IOException {
      return this.getValueAsBoolean(false);
   }

   public boolean getValueAsBoolean(boolean var1) throws IOException {
      return var1;
   }

   public String getValueAsString() throws IOException {
      return this.getValueAsString((String)null);
   }

   public abstract String getValueAsString(String var1) throws IOException;

   public boolean canReadObjectId() {
      return false;
   }

   public boolean canReadTypeId() {
      return false;
   }

   public Object getObjectId() throws IOException {
      return null;
   }

   public Object getTypeId() throws IOException {
      return null;
   }

   public Object readValueAs(Class var1) throws IOException {
      return this._codec().readValue(this, var1);
   }

   public Object readValueAs(TypeReference var1) throws IOException {
      return this._codec().readValue(this, var1);
   }

   public Iterator readValuesAs(Class var1) throws IOException {
      return this._codec().readValues(this, var1);
   }

   public Iterator readValuesAs(TypeReference var1) throws IOException {
      return this._codec().readValues(this, var1);
   }

   public TreeNode readValueAsTree() throws IOException {
      return this._codec().readTree(this);
   }

   protected ObjectCodec _codec() {
      ObjectCodec var1 = this.getCodec();
      if (var1 == null) {
         throw new IllegalStateException("No ObjectCodec defined for parser, needed for deserialization");
      } else {
         return var1;
      }
   }

   protected JsonParseException _constructError(String var1) {
      return (new JsonParseException(this, var1)).withRequestPayload(this._requestPayload);
   }

   protected void _reportUnsupportedOperation() {
      throw new UnsupportedOperationException("Operation not supported by parser of type " + this.getClass().getName());
   }
}
