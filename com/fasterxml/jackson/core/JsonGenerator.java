package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.core.type.WritableTypeId$Inclusion;
import com.fasterxml.jackson.core.util.VersionUtil;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public abstract class JsonGenerator implements Closeable, Flushable, Versioned {
   protected PrettyPrinter _cfgPrettyPrinter;

   protected JsonGenerator() {
      super();
   }

   public abstract JsonGenerator setCodec(ObjectCodec var1);

   public abstract ObjectCodec getCodec();

   public abstract Version version();

   public abstract JsonGenerator enable(JsonGenerator$Feature var1);

   public abstract JsonGenerator disable(JsonGenerator$Feature var1);

   public final JsonGenerator configure(JsonGenerator$Feature var1, boolean var2) {
      if (var2) {
         this.enable(var1);
      } else {
         this.disable(var1);
      }

      return this;
   }

   public abstract boolean isEnabled(JsonGenerator$Feature var1);

   public abstract int getFeatureMask();

   /** @deprecated */
   @Deprecated
   public abstract JsonGenerator setFeatureMask(int var1);

   public JsonGenerator overrideStdFeatures(int var1, int var2) {
      int var3 = this.getFeatureMask();
      int var4 = var3 & ~var2 | var1 & var2;
      return this.setFeatureMask(var4);
   }

   public int getFormatFeatures() {
      return 0;
   }

   public JsonGenerator overrideFormatFeatures(int var1, int var2) {
      throw new IllegalArgumentException("No FormatFeatures defined for generator of type " + this.getClass().getName());
   }

   public void setSchema(FormatSchema var1) {
      throw new UnsupportedOperationException("Generator of type " + this.getClass().getName() + " does not support schema of type '" + var1.getSchemaType() + "'");
   }

   public FormatSchema getSchema() {
      return null;
   }

   public JsonGenerator setPrettyPrinter(PrettyPrinter var1) {
      this._cfgPrettyPrinter = var1;
      return this;
   }

   public PrettyPrinter getPrettyPrinter() {
      return this._cfgPrettyPrinter;
   }

   public abstract JsonGenerator useDefaultPrettyPrinter();

   public JsonGenerator setHighestNonEscapedChar(int var1) {
      return this;
   }

   public int getHighestEscapedChar() {
      return 0;
   }

   public CharacterEscapes getCharacterEscapes() {
      return null;
   }

   public JsonGenerator setCharacterEscapes(CharacterEscapes var1) {
      return this;
   }

   public JsonGenerator setRootValueSeparator(SerializableString var1) {
      throw new UnsupportedOperationException();
   }

   public Object getOutputTarget() {
      return null;
   }

   public int getOutputBuffered() {
      return -1;
   }

   public Object getCurrentValue() {
      JsonStreamContext var1 = this.getOutputContext();
      return var1 == null ? null : var1.getCurrentValue();
   }

   public void setCurrentValue(Object var1) {
      JsonStreamContext var2 = this.getOutputContext();
      if (var2 != null) {
         var2.setCurrentValue(var1);
      }

   }

   public boolean canUseSchema(FormatSchema var1) {
      return false;
   }

   public boolean canWriteObjectId() {
      return false;
   }

   public boolean canWriteTypeId() {
      return false;
   }

   public boolean canWriteBinaryNatively() {
      return false;
   }

   public boolean canOmitFields() {
      return true;
   }

   public boolean canWriteFormattedNumbers() {
      return false;
   }

   public abstract void writeStartArray() throws IOException;

   public void writeStartArray(int var1) throws IOException {
      this.writeStartArray();
   }

   public abstract void writeEndArray() throws IOException;

   public abstract void writeStartObject() throws IOException;

   public void writeStartObject(Object var1) throws IOException {
      this.writeStartObject();
      this.setCurrentValue(var1);
   }

   public abstract void writeEndObject() throws IOException;

   public abstract void writeFieldName(String var1) throws IOException;

   public abstract void writeFieldName(SerializableString var1) throws IOException;

   public void writeFieldId(long var1) throws IOException {
      this.writeFieldName(Long.toString(var1));
   }

   public void writeArray(int[] var1, int var2, int var3) throws IOException {
      if (var1 == null) {
         throw new IllegalArgumentException("null array");
      } else {
         this._verifyOffsets(var1.length, var2, var3);
         this.writeStartArray();
         int var4 = var2;

         for(int var5 = var2 + var3; var4 < var5; ++var4) {
            this.writeNumber(var1[var4]);
         }

         this.writeEndArray();
      }
   }

   public void writeArray(long[] var1, int var2, int var3) throws IOException {
      if (var1 == null) {
         throw new IllegalArgumentException("null array");
      } else {
         this._verifyOffsets(var1.length, var2, var3);
         this.writeStartArray();
         int var4 = var2;

         for(int var5 = var2 + var3; var4 < var5; ++var4) {
            this.writeNumber(var1[var4]);
         }

         this.writeEndArray();
      }
   }

   public void writeArray(double[] var1, int var2, int var3) throws IOException {
      if (var1 == null) {
         throw new IllegalArgumentException("null array");
      } else {
         this._verifyOffsets(var1.length, var2, var3);
         this.writeStartArray();
         int var4 = var2;

         for(int var5 = var2 + var3; var4 < var5; ++var4) {
            this.writeNumber(var1[var4]);
         }

         this.writeEndArray();
      }
   }

   public abstract void writeString(String var1) throws IOException;

   public void writeString(Reader var1, int var2) throws IOException {
      this._reportUnsupportedOperation();
   }

   public abstract void writeString(char[] var1, int var2, int var3) throws IOException;

   public abstract void writeString(SerializableString var1) throws IOException;

   public abstract void writeRawUTF8String(byte[] var1, int var2, int var3) throws IOException;

   public abstract void writeUTF8String(byte[] var1, int var2, int var3) throws IOException;

   public abstract void writeRaw(String var1) throws IOException;

   public abstract void writeRaw(String var1, int var2, int var3) throws IOException;

   public abstract void writeRaw(char[] var1, int var2, int var3) throws IOException;

   public abstract void writeRaw(char var1) throws IOException;

   public void writeRaw(SerializableString var1) throws IOException {
      this.writeRaw(var1.getValue());
   }

   public abstract void writeRawValue(String var1) throws IOException;

   public abstract void writeRawValue(String var1, int var2, int var3) throws IOException;

   public abstract void writeRawValue(char[] var1, int var2, int var3) throws IOException;

   public void writeRawValue(SerializableString var1) throws IOException {
      this.writeRawValue(var1.getValue());
   }

   public abstract void writeBinary(Base64Variant var1, byte[] var2, int var3, int var4) throws IOException;

   public void writeBinary(byte[] var1, int var2, int var3) throws IOException {
      this.writeBinary(Base64Variants.getDefaultVariant(), var1, var2, var3);
   }

   public void writeBinary(byte[] var1) throws IOException {
      this.writeBinary(Base64Variants.getDefaultVariant(), var1, 0, var1.length);
   }

   public int writeBinary(InputStream var1, int var2) throws IOException {
      return this.writeBinary(Base64Variants.getDefaultVariant(), var1, var2);
   }

   public abstract int writeBinary(Base64Variant var1, InputStream var2, int var3) throws IOException;

   public void writeNumber(short var1) throws IOException {
      this.writeNumber((int)var1);
   }

   public abstract void writeNumber(int var1) throws IOException;

   public abstract void writeNumber(long var1) throws IOException;

   public abstract void writeNumber(BigInteger var1) throws IOException;

   public abstract void writeNumber(double var1) throws IOException;

   public abstract void writeNumber(float var1) throws IOException;

   public abstract void writeNumber(BigDecimal var1) throws IOException;

   public abstract void writeNumber(String var1) throws IOException;

   public abstract void writeBoolean(boolean var1) throws IOException;

   public abstract void writeNull() throws IOException;

   public void writeEmbeddedObject(Object var1) throws IOException {
      if (var1 == null) {
         this.writeNull();
      } else if (var1 instanceof byte[]) {
         this.writeBinary((byte[])((byte[])var1));
      } else {
         throw new JsonGenerationException("No native support for writing embedded objects of type " + var1.getClass().getName(), this);
      }
   }

   public void writeObjectId(Object var1) throws IOException {
      throw new JsonGenerationException("No native support for writing Object Ids", this);
   }

   public void writeObjectRef(Object var1) throws IOException {
      throw new JsonGenerationException("No native support for writing Object Ids", this);
   }

   public void writeTypeId(Object var1) throws IOException {
      throw new JsonGenerationException("No native support for writing Type Ids", this);
   }

   public WritableTypeId writeTypePrefix(WritableTypeId var1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public WritableTypeId writeTypeSuffix(WritableTypeId var1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public abstract void writeObject(Object var1) throws IOException;

   public abstract void writeTree(TreeNode var1) throws IOException;

   public void writeStringField(String var1, String var2) throws IOException {
      this.writeFieldName(var1);
      this.writeString(var2);
   }

   public final void writeBooleanField(String var1, boolean var2) throws IOException {
      this.writeFieldName(var1);
      this.writeBoolean(var2);
   }

   public final void writeNullField(String var1) throws IOException {
      this.writeFieldName(var1);
      this.writeNull();
   }

   public final void writeNumberField(String var1, int var2) throws IOException {
      this.writeFieldName(var1);
      this.writeNumber(var2);
   }

   public final void writeNumberField(String var1, long var2) throws IOException {
      this.writeFieldName(var1);
      this.writeNumber(var2);
   }

   public final void writeNumberField(String var1, double var2) throws IOException {
      this.writeFieldName(var1);
      this.writeNumber(var2);
   }

   public final void writeNumberField(String var1, float var2) throws IOException {
      this.writeFieldName(var1);
      this.writeNumber(var2);
   }

   public final void writeNumberField(String var1, BigDecimal var2) throws IOException {
      this.writeFieldName(var1);
      this.writeNumber(var2);
   }

   public final void writeBinaryField(String var1, byte[] var2) throws IOException {
      this.writeFieldName(var1);
      this.writeBinary(var2);
   }

   public final void writeArrayFieldStart(String var1) throws IOException {
      this.writeFieldName(var1);
      this.writeStartArray();
   }

   public final void writeObjectFieldStart(String var1) throws IOException {
      this.writeFieldName(var1);
      this.writeStartObject();
   }

   public final void writeObjectField(String var1, Object var2) throws IOException {
      this.writeFieldName(var1);
      this.writeObject(var2);
   }

   public void writeOmittedField(String var1) throws IOException {
   }

   public void copyCurrentEvent(JsonParser var1) throws IOException {
      JsonToken var2 = var1.currentToken();
      if (var2 == null) {
         this._reportError("No current event to copy");
      }

      JsonParser$NumberType var3;
      switch(var2.id()) {
      case -1:
         this._reportError("No current event to copy");
         break;
      case 0:
      default:
         this._throwInternal();
         break;
      case 1:
         this.writeStartObject();
         break;
      case 2:
         this.writeEndObject();
         break;
      case 3:
         this.writeStartArray();
         break;
      case 4:
         this.writeEndArray();
         break;
      case 5:
         this.writeFieldName(var1.getCurrentName());
         break;
      case 6:
         if (var1.hasTextCharacters()) {
            this.writeString(var1.getTextCharacters(), var1.getTextOffset(), var1.getTextLength());
         } else {
            this.writeString(var1.getText());
         }
         break;
      case 7:
         var3 = var1.getNumberType();
         if (var3 == JsonParser$NumberType.INT) {
            this.writeNumber(var1.getIntValue());
         } else if (var3 == JsonParser$NumberType.BIG_INTEGER) {
            this.writeNumber(var1.getBigIntegerValue());
         } else {
            this.writeNumber(var1.getLongValue());
         }
         break;
      case 8:
         var3 = var1.getNumberType();
         if (var3 == JsonParser$NumberType.BIG_DECIMAL) {
            this.writeNumber(var1.getDecimalValue());
         } else if (var3 == JsonParser$NumberType.FLOAT) {
            this.writeNumber(var1.getFloatValue());
         } else {
            this.writeNumber(var1.getDoubleValue());
         }
         break;
      case 9:
         this.writeBoolean(true);
         break;
      case 10:
         this.writeBoolean(false);
         break;
      case 11:
         this.writeNull();
         break;
      case 12:
         this.writeObject(var1.getEmbeddedObject());
      }

   }

   public void copyCurrentStructure(JsonParser var1) throws IOException {
      JsonToken var2 = var1.currentToken();
      if (var2 == null) {
         this._reportError("No current event to copy");
      }

      int var3 = var2.id();
      if (var3 == 5) {
         this.writeFieldName(var1.getCurrentName());
         var2 = var1.nextToken();
         var3 = var2.id();
      }

      switch(var3) {
      case 1:
         this.writeStartObject();

         while(var1.nextToken() != JsonToken.END_OBJECT) {
            this.copyCurrentStructure(var1);
         }

         this.writeEndObject();
         break;
      case 3:
         this.writeStartArray();

         while(var1.nextToken() != JsonToken.END_ARRAY) {
            this.copyCurrentStructure(var1);
         }

         this.writeEndArray();
         break;
      default:
         this.copyCurrentEvent(var1);
      }

   }

   public abstract JsonStreamContext getOutputContext();

   public abstract void flush() throws IOException;

   public abstract boolean isClosed();

   public abstract void close() throws IOException;

   protected void _reportError(String var1) throws JsonGenerationException {
      throw new JsonGenerationException(var1, this);
   }

   protected final void _throwInternal() {
      VersionUtil.throwInternal();
   }

   protected void _reportUnsupportedOperation() {
      throw new UnsupportedOperationException("Operation not supported by generator of type " + this.getClass().getName());
   }

   protected final void _verifyOffsets(int var1, int var2, int var3) {
      if (var2 < 0 || var2 + var3 > var1) {
         throw new IllegalArgumentException(String.format("invalid argument(s) (offset=%d, length=%d) for input array of %d element", var2, var3, var1));
      }
   }

   protected void _writeSimpleObject(Object var1) throws IOException {
      if (var1 == null) {
         this.writeNull();
      } else if (var1 instanceof String) {
         this.writeString((String)var1);
      } else {
         if (var1 instanceof Number) {
            Number var2 = (Number)var1;
            if (var2 instanceof Integer) {
               this.writeNumber(var2.intValue());
               return;
            }

            if (var2 instanceof Long) {
               this.writeNumber(var2.longValue());
               return;
            }

            if (var2 instanceof Double) {
               this.writeNumber(var2.doubleValue());
               return;
            }

            if (var2 instanceof Float) {
               this.writeNumber(var2.floatValue());
               return;
            }

            if (var2 instanceof Short) {
               this.writeNumber(var2.shortValue());
               return;
            }

            if (var2 instanceof Byte) {
               this.writeNumber((short)var2.byteValue());
               return;
            }

            if (var2 instanceof BigInteger) {
               this.writeNumber((BigInteger)var2);
               return;
            }

            if (var2 instanceof BigDecimal) {
               this.writeNumber((BigDecimal)var2);
               return;
            }

            if (var2 instanceof AtomicInteger) {
               this.writeNumber(((AtomicInteger)var2).get());
               return;
            }

            if (var2 instanceof AtomicLong) {
               this.writeNumber(((AtomicLong)var2).get());
               return;
            }
         } else {
            if (var1 instanceof byte[]) {
               this.writeBinary((byte[])((byte[])var1));
               return;
            }

            if (var1 instanceof Boolean) {
               this.writeBoolean((Boolean)var1);
               return;
            }

            if (var1 instanceof AtomicBoolean) {
               this.writeBoolean(((AtomicBoolean)var1).get());
               return;
            }
         }

         throw new IllegalStateException("No ObjectCodec defined for the generator, can only serialize simple wrapper types (type passed " + var1.getClass().getName() + ")");
      }
   }
}
