package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonGenerator$Feature;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.json.DupDetector;
import com.fasterxml.jackson.core.json.JsonWriteContext;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.cfg.PackageVersion;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

public class TokenBuffer extends JsonGenerator {
   protected static final int DEFAULT_GENERATOR_FEATURES = JsonGenerator$Feature.collectDefaults();
   protected ObjectCodec _objectCodec;
   protected JsonStreamContext _parentContext;
   protected int _generatorFeatures;
   protected boolean _closed;
   protected boolean _hasNativeTypeIds;
   protected boolean _hasNativeObjectIds;
   protected boolean _mayHaveNativeIds;
   protected boolean _forceBigDecimal;
   protected TokenBuffer$Segment _first;
   protected TokenBuffer$Segment _last;
   protected int _appendAt;
   protected Object _typeId;
   protected Object _objectId;
   protected boolean _hasNativeId;
   protected JsonWriteContext _writeContext;

   public TokenBuffer(ObjectCodec var1, boolean var2) {
      super();
      this._hasNativeId = false;
      this._objectCodec = var1;
      this._generatorFeatures = DEFAULT_GENERATOR_FEATURES;
      this._writeContext = JsonWriteContext.createRootContext((DupDetector)null);
      this._first = this._last = new TokenBuffer$Segment();
      this._appendAt = 0;
      this._hasNativeTypeIds = var2;
      this._hasNativeObjectIds = var2;
      this._mayHaveNativeIds = this._hasNativeTypeIds | this._hasNativeObjectIds;
   }

   public TokenBuffer(JsonParser var1) {
      this(var1, (DeserializationContext)null);
   }

   public TokenBuffer(JsonParser var1, DeserializationContext var2) {
      super();
      this._hasNativeId = false;
      this._objectCodec = var1.getCodec();
      this._parentContext = var1.getParsingContext();
      this._generatorFeatures = DEFAULT_GENERATOR_FEATURES;
      this._writeContext = JsonWriteContext.createRootContext((DupDetector)null);
      this._first = this._last = new TokenBuffer$Segment();
      this._appendAt = 0;
      this._hasNativeTypeIds = var1.canReadTypeId();
      this._hasNativeObjectIds = var1.canReadObjectId();
      this._mayHaveNativeIds = this._hasNativeTypeIds | this._hasNativeObjectIds;
      this._forceBigDecimal = var2 == null ? false : var2.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
   }

   public static TokenBuffer asCopyOfValue(JsonParser var0) throws IOException {
      TokenBuffer var1 = new TokenBuffer(var0);
      var1.copyCurrentStructure(var0);
      return var1;
   }

   public TokenBuffer overrideParentContext(JsonStreamContext var1) {
      this._parentContext = var1;
      return this;
   }

   public TokenBuffer forceUseOfBigDecimal(boolean var1) {
      this._forceBigDecimal = var1;
      return this;
   }

   public Version version() {
      return PackageVersion.VERSION;
   }

   public JsonParser asParser() {
      return this.asParser(this._objectCodec);
   }

   public JsonParser asParserOnFirstToken() throws IOException {
      JsonParser var1 = this.asParser(this._objectCodec);
      var1.nextToken();
      return var1;
   }

   public JsonParser asParser(ObjectCodec var1) {
      return new TokenBuffer$Parser(this._first, var1, this._hasNativeTypeIds, this._hasNativeObjectIds, this._parentContext);
   }

   public JsonParser asParser(JsonParser var1) {
      TokenBuffer$Parser var2 = new TokenBuffer$Parser(this._first, var1.getCodec(), this._hasNativeTypeIds, this._hasNativeObjectIds, this._parentContext);
      var2.setLocation(var1.getTokenLocation());
      return var2;
   }

   public JsonToken firstToken() {
      return this._first.type(0);
   }

   public TokenBuffer append(TokenBuffer var1) throws IOException {
      if (!this._hasNativeTypeIds) {
         this._hasNativeTypeIds = var1.canWriteTypeId();
      }

      if (!this._hasNativeObjectIds) {
         this._hasNativeObjectIds = var1.canWriteObjectId();
      }

      this._mayHaveNativeIds = this._hasNativeTypeIds | this._hasNativeObjectIds;
      JsonParser var2 = var1.asParser();

      while(var2.nextToken() != null) {
         this.copyCurrentStructure(var2);
      }

      return this;
   }

   public void serialize(JsonGenerator var1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public TokenBuffer deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      if (var1.getCurrentTokenId() != JsonToken.FIELD_NAME.id()) {
         this.copyCurrentStructure(var1);
         return this;
      } else {
         this.writeStartObject();

         JsonToken var3;
         do {
            this.copyCurrentStructure(var1);
         } while((var3 = var1.nextToken()) == JsonToken.FIELD_NAME);

         if (var3 != JsonToken.END_OBJECT) {
            var2.reportWrongTokenException(TokenBuffer.class, JsonToken.END_OBJECT, "Expected END_OBJECT after copying contents of a JsonParser into TokenBuffer, got " + var3);
         }

         this.writeEndObject();
         return this;
      }
   }

   public String toString() {
      boolean var1 = true;
      StringBuilder var2 = new StringBuilder();
      var2.append("[TokenBuffer: ");
      JsonParser var3 = this.asParser();
      int var4 = 0;
      boolean var5 = this._hasNativeTypeIds || this._hasNativeObjectIds;

      while(true) {
         try {
            JsonToken var6 = var3.nextToken();
            if (var6 == null) {
               break;
            }

            if (var5) {
               this._appendNativeIds(var2);
            }

            if (var4 < 100) {
               if (var4 > 0) {
                  var2.append(", ");
               }

               var2.append(var6.toString());
               if (var6 == JsonToken.FIELD_NAME) {
                  var2.append('(');
                  var2.append(var3.getCurrentName());
                  var2.append(')');
               }
            }
         } catch (IOException var8) {
            throw new IllegalStateException(var8);
         }

         ++var4;
      }

      if (var4 >= 100) {
         var2.append(" ... (truncated ").append(var4 - 100).append(" entries)");
      }

      var2.append(']');
      return var2.toString();
   }

   private final void _appendNativeIds(StringBuilder var1) {
      Object var2 = TokenBuffer$Segment.access$000(this._last, this._appendAt - 1);
      if (var2 != null) {
         var1.append("[objectId=").append(String.valueOf(var2)).append(']');
      }

      Object var3 = TokenBuffer$Segment.access$100(this._last, this._appendAt - 1);
      if (var3 != null) {
         var1.append("[typeId=").append(String.valueOf(var3)).append(']');
      }

   }

   public JsonGenerator enable(JsonGenerator$Feature var1) {
      this._generatorFeatures |= var1.getMask();
      return this;
   }

   public JsonGenerator disable(JsonGenerator$Feature var1) {
      this._generatorFeatures &= ~var1.getMask();
      return this;
   }

   public boolean isEnabled(JsonGenerator$Feature var1) {
      return (this._generatorFeatures & var1.getMask()) != 0;
   }

   public int getFeatureMask() {
      return this._generatorFeatures;
   }

   /** @deprecated */
   @Deprecated
   public JsonGenerator setFeatureMask(int var1) {
      this._generatorFeatures = var1;
      return this;
   }

   public JsonGenerator overrideStdFeatures(int var1, int var2) {
      int var3 = this.getFeatureMask();
      this._generatorFeatures = var3 & ~var2 | var1 & var2;
      return this;
   }

   public JsonGenerator useDefaultPrettyPrinter() {
      return this;
   }

   public JsonGenerator setCodec(ObjectCodec var1) {
      this._objectCodec = var1;
      return this;
   }

   public ObjectCodec getCodec() {
      return this._objectCodec;
   }

   public final JsonWriteContext getOutputContext() {
      return this._writeContext;
   }

   public boolean canWriteBinaryNatively() {
      return true;
   }

   public void flush() throws IOException {
   }

   public void close() throws IOException {
      this._closed = true;
   }

   public boolean isClosed() {
      return this._closed;
   }

   public final void writeStartArray() throws IOException {
      this._writeContext.writeValue();
      this._append(JsonToken.START_ARRAY);
      this._writeContext = this._writeContext.createChildArrayContext();
   }

   public final void writeEndArray() throws IOException {
      this._append(JsonToken.END_ARRAY);
      JsonWriteContext var1 = this._writeContext.getParent();
      if (var1 != null) {
         this._writeContext = var1;
      }

   }

   public final void writeStartObject() throws IOException {
      this._writeContext.writeValue();
      this._append(JsonToken.START_OBJECT);
      this._writeContext = this._writeContext.createChildObjectContext();
   }

   public void writeStartObject(Object var1) throws IOException {
      this._writeContext.writeValue();
      this._append(JsonToken.START_OBJECT);
      JsonWriteContext var2 = this._writeContext.createChildObjectContext();
      this._writeContext = var2;
      if (var1 != null) {
         var2.setCurrentValue(var1);
      }

   }

   public final void writeEndObject() throws IOException {
      this._append(JsonToken.END_OBJECT);
      JsonWriteContext var1 = this._writeContext.getParent();
      if (var1 != null) {
         this._writeContext = var1;
      }

   }

   public final void writeFieldName(String var1) throws IOException {
      this._writeContext.writeFieldName(var1);
      this._append(JsonToken.FIELD_NAME, var1);
   }

   public void writeFieldName(SerializableString var1) throws IOException {
      this._writeContext.writeFieldName(var1.getValue());
      this._append(JsonToken.FIELD_NAME, var1);
   }

   public void writeString(String var1) throws IOException {
      if (var1 == null) {
         this.writeNull();
      } else {
         this._appendValue(JsonToken.VALUE_STRING, var1);
      }

   }

   public void writeString(char[] var1, int var2, int var3) throws IOException {
      this.writeString(new String(var1, var2, var3));
   }

   public void writeString(SerializableString var1) throws IOException {
      if (var1 == null) {
         this.writeNull();
      } else {
         this._appendValue(JsonToken.VALUE_STRING, var1);
      }

   }

   public void writeRawUTF8String(byte[] var1, int var2, int var3) throws IOException {
      this._reportUnsupportedOperation();
   }

   public void writeUTF8String(byte[] var1, int var2, int var3) throws IOException {
      this._reportUnsupportedOperation();
   }

   public void writeRaw(String var1) throws IOException {
      this._reportUnsupportedOperation();
   }

   public void writeRaw(String var1, int var2, int var3) throws IOException {
      this._reportUnsupportedOperation();
   }

   public void writeRaw(SerializableString var1) throws IOException {
      this._reportUnsupportedOperation();
   }

   public void writeRaw(char[] var1, int var2, int var3) throws IOException {
      this._reportUnsupportedOperation();
   }

   public void writeRaw(char var1) throws IOException {
      this._reportUnsupportedOperation();
   }

   public void writeRawValue(String var1) throws IOException {
      this._appendValue(JsonToken.VALUE_EMBEDDED_OBJECT, new RawValue(var1));
   }

   public void writeRawValue(String var1, int var2, int var3) throws IOException {
      if (var2 > 0 || var3 != var1.length()) {
         var1 = var1.substring(var2, var2 + var3);
      }

      this._appendValue(JsonToken.VALUE_EMBEDDED_OBJECT, new RawValue(var1));
   }

   public void writeRawValue(char[] var1, int var2, int var3) throws IOException {
      this._appendValue(JsonToken.VALUE_EMBEDDED_OBJECT, new String(var1, var2, var3));
   }

   public void writeNumber(short var1) throws IOException {
      this._appendValue(JsonToken.VALUE_NUMBER_INT, var1);
   }

   public void writeNumber(int var1) throws IOException {
      this._appendValue(JsonToken.VALUE_NUMBER_INT, var1);
   }

   public void writeNumber(long var1) throws IOException {
      this._appendValue(JsonToken.VALUE_NUMBER_INT, var1);
   }

   public void writeNumber(double var1) throws IOException {
      this._appendValue(JsonToken.VALUE_NUMBER_FLOAT, var1);
   }

   public void writeNumber(float var1) throws IOException {
      this._appendValue(JsonToken.VALUE_NUMBER_FLOAT, var1);
   }

   public void writeNumber(BigDecimal var1) throws IOException {
      if (var1 == null) {
         this.writeNull();
      } else {
         this._appendValue(JsonToken.VALUE_NUMBER_FLOAT, var1);
      }

   }

   public void writeNumber(BigInteger var1) throws IOException {
      if (var1 == null) {
         this.writeNull();
      } else {
         this._appendValue(JsonToken.VALUE_NUMBER_INT, var1);
      }

   }

   public void writeNumber(String var1) throws IOException {
      this._appendValue(JsonToken.VALUE_NUMBER_FLOAT, var1);
   }

   public void writeBoolean(boolean var1) throws IOException {
      this._appendValue(var1 ? JsonToken.VALUE_TRUE : JsonToken.VALUE_FALSE);
   }

   public void writeNull() throws IOException {
      this._appendValue(JsonToken.VALUE_NULL);
   }

   public void writeObject(Object var1) throws IOException {
      if (var1 == null) {
         this.writeNull();
      } else {
         Class var2 = var1.getClass();
         if (var2 != byte[].class && !(var1 instanceof RawValue)) {
            if (this._objectCodec == null) {
               this._appendValue(JsonToken.VALUE_EMBEDDED_OBJECT, var1);
            } else {
               this._objectCodec.writeValue(this, var1);
            }

         } else {
            this._appendValue(JsonToken.VALUE_EMBEDDED_OBJECT, var1);
         }
      }
   }

   public void writeTree(TreeNode var1) throws IOException {
      if (var1 == null) {
         this.writeNull();
      } else {
         if (this._objectCodec == null) {
            this._appendValue(JsonToken.VALUE_EMBEDDED_OBJECT, var1);
         } else {
            this._objectCodec.writeTree(this, var1);
         }

      }
   }

   public void writeBinary(Base64Variant var1, byte[] var2, int var3, int var4) throws IOException {
      byte[] var5 = new byte[var4];
      System.arraycopy(var2, var3, var5, 0, var4);
      this.writeObject(var5);
   }

   public int writeBinary(Base64Variant var1, InputStream var2, int var3) {
      throw new UnsupportedOperationException();
   }

   public boolean canWriteTypeId() {
      return this._hasNativeTypeIds;
   }

   public boolean canWriteObjectId() {
      return this._hasNativeObjectIds;
   }

   public void writeTypeId(Object var1) {
      this._typeId = var1;
      this._hasNativeId = true;
   }

   public void writeObjectId(Object var1) {
      this._objectId = var1;
      this._hasNativeId = true;
   }

   public void writeEmbeddedObject(Object var1) throws IOException {
      this._appendValue(JsonToken.VALUE_EMBEDDED_OBJECT, var1);
   }

   public void copyCurrentEvent(JsonParser var1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public void copyCurrentStructure(JsonParser var1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   private final void _checkNativeIds(JsonParser var1) throws IOException {
      if ((this._typeId = var1.getTypeId()) != null) {
         this._hasNativeId = true;
      }

      if ((this._objectId = var1.getObjectId()) != null) {
         this._hasNativeId = true;
      }

   }

   protected final void _append(JsonToken var1) {
      TokenBuffer$Segment var2 = this._hasNativeId ? this._last.append(this._appendAt, var1, this._objectId, this._typeId) : this._last.append(this._appendAt, var1);
      if (var2 == null) {
         ++this._appendAt;
      } else {
         this._last = var2;
         this._appendAt = 1;
      }

   }

   protected final void _append(JsonToken var1, Object var2) {
      TokenBuffer$Segment var3 = this._hasNativeId ? this._last.append(this._appendAt, var1, var2, this._objectId, this._typeId) : this._last.append(this._appendAt, var1, var2);
      if (var3 == null) {
         ++this._appendAt;
      } else {
         this._last = var3;
         this._appendAt = 1;
      }

   }

   protected final void _appendValue(JsonToken var1) {
      this._writeContext.writeValue();
      TokenBuffer$Segment var2 = this._hasNativeId ? this._last.append(this._appendAt, var1, this._objectId, this._typeId) : this._last.append(this._appendAt, var1);
      if (var2 == null) {
         ++this._appendAt;
      } else {
         this._last = var2;
         this._appendAt = 1;
      }

   }

   protected final void _appendValue(JsonToken var1, Object var2) {
      this._writeContext.writeValue();
      TokenBuffer$Segment var3 = this._hasNativeId ? this._last.append(this._appendAt, var1, var2, this._objectId, this._typeId) : this._last.append(this._appendAt, var1, var2);
      if (var3 == null) {
         ++this._appendAt;
      } else {
         this._last = var3;
         this._appendAt = 1;
      }

   }

   protected void _reportUnsupportedOperation() {
      throw new UnsupportedOperationException("Called operation not supported for TokenBuffer");
   }

   public JsonStreamContext getOutputContext() {
      return this.getOutputContext();
   }
}
