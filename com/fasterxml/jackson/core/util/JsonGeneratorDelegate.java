package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonGenerator$Feature;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;

public class JsonGeneratorDelegate extends JsonGenerator {
   protected JsonGenerator delegate;
   protected boolean delegateCopyMethods;

   public JsonGeneratorDelegate(JsonGenerator var1) {
      this(var1, true);
   }

   public JsonGeneratorDelegate(JsonGenerator var1, boolean var2) {
      super();
      this.delegate = var1;
      this.delegateCopyMethods = var2;
   }

   public Object getCurrentValue() {
      return this.delegate.getCurrentValue();
   }

   public void setCurrentValue(Object var1) {
      this.delegate.setCurrentValue(var1);
   }

   public JsonGenerator getDelegate() {
      return this.delegate;
   }

   public ObjectCodec getCodec() {
      return this.delegate.getCodec();
   }

   public JsonGenerator setCodec(ObjectCodec var1) {
      this.delegate.setCodec(var1);
      return this;
   }

   public void setSchema(FormatSchema var1) {
      this.delegate.setSchema(var1);
   }

   public FormatSchema getSchema() {
      return this.delegate.getSchema();
   }

   public Version version() {
      return this.delegate.version();
   }

   public Object getOutputTarget() {
      return this.delegate.getOutputTarget();
   }

   public int getOutputBuffered() {
      return this.delegate.getOutputBuffered();
   }

   public boolean canUseSchema(FormatSchema var1) {
      return this.delegate.canUseSchema(var1);
   }

   public boolean canWriteTypeId() {
      return this.delegate.canWriteTypeId();
   }

   public boolean canWriteObjectId() {
      return this.delegate.canWriteObjectId();
   }

   public boolean canWriteBinaryNatively() {
      return this.delegate.canWriteBinaryNatively();
   }

   public boolean canOmitFields() {
      return this.delegate.canOmitFields();
   }

   public JsonGenerator enable(JsonGenerator$Feature var1) {
      this.delegate.enable(var1);
      return this;
   }

   public JsonGenerator disable(JsonGenerator$Feature var1) {
      this.delegate.disable(var1);
      return this;
   }

   public boolean isEnabled(JsonGenerator$Feature var1) {
      return this.delegate.isEnabled(var1);
   }

   public int getFeatureMask() {
      return this.delegate.getFeatureMask();
   }

   /** @deprecated */
   @Deprecated
   public JsonGenerator setFeatureMask(int var1) {
      this.delegate.setFeatureMask(var1);
      return this;
   }

   public JsonGenerator overrideStdFeatures(int var1, int var2) {
      this.delegate.overrideStdFeatures(var1, var2);
      return this;
   }

   public JsonGenerator overrideFormatFeatures(int var1, int var2) {
      this.delegate.overrideFormatFeatures(var1, var2);
      return this;
   }

   public JsonGenerator setPrettyPrinter(PrettyPrinter var1) {
      this.delegate.setPrettyPrinter(var1);
      return this;
   }

   public PrettyPrinter getPrettyPrinter() {
      return this.delegate.getPrettyPrinter();
   }

   public JsonGenerator useDefaultPrettyPrinter() {
      this.delegate.useDefaultPrettyPrinter();
      return this;
   }

   public JsonGenerator setHighestNonEscapedChar(int var1) {
      this.delegate.setHighestNonEscapedChar(var1);
      return this;
   }

   public int getHighestEscapedChar() {
      return this.delegate.getHighestEscapedChar();
   }

   public CharacterEscapes getCharacterEscapes() {
      return this.delegate.getCharacterEscapes();
   }

   public JsonGenerator setCharacterEscapes(CharacterEscapes var1) {
      this.delegate.setCharacterEscapes(var1);
      return this;
   }

   public JsonGenerator setRootValueSeparator(SerializableString var1) {
      this.delegate.setRootValueSeparator(var1);
      return this;
   }

   public void writeStartArray() throws IOException {
      this.delegate.writeStartArray();
   }

   public void writeStartArray(int var1) throws IOException {
      this.delegate.writeStartArray(var1);
   }

   public void writeEndArray() throws IOException {
      this.delegate.writeEndArray();
   }

   public void writeStartObject() throws IOException {
      this.delegate.writeStartObject();
   }

   public void writeStartObject(Object var1) throws IOException {
      this.delegate.writeStartObject(var1);
   }

   public void writeEndObject() throws IOException {
      this.delegate.writeEndObject();
   }

   public void writeFieldName(String var1) throws IOException {
      this.delegate.writeFieldName(var1);
   }

   public void writeFieldName(SerializableString var1) throws IOException {
      this.delegate.writeFieldName(var1);
   }

   public void writeFieldId(long var1) throws IOException {
      this.delegate.writeFieldId(var1);
   }

   public void writeArray(int[] var1, int var2, int var3) throws IOException {
      this.delegate.writeArray(var1, var2, var3);
   }

   public void writeArray(long[] var1, int var2, int var3) throws IOException {
      this.delegate.writeArray(var1, var2, var3);
   }

   public void writeArray(double[] var1, int var2, int var3) throws IOException {
      this.delegate.writeArray(var1, var2, var3);
   }

   public void writeString(String var1) throws IOException {
      this.delegate.writeString(var1);
   }

   public void writeString(Reader var1, int var2) throws IOException {
      this.delegate.writeString(var1, var2);
   }

   public void writeString(char[] var1, int var2, int var3) throws IOException {
      this.delegate.writeString(var1, var2, var3);
   }

   public void writeString(SerializableString var1) throws IOException {
      this.delegate.writeString(var1);
   }

   public void writeRawUTF8String(byte[] var1, int var2, int var3) throws IOException {
      this.delegate.writeRawUTF8String(var1, var2, var3);
   }

   public void writeUTF8String(byte[] var1, int var2, int var3) throws IOException {
      this.delegate.writeUTF8String(var1, var2, var3);
   }

   public void writeRaw(String var1) throws IOException {
      this.delegate.writeRaw(var1);
   }

   public void writeRaw(String var1, int var2, int var3) throws IOException {
      this.delegate.writeRaw(var1, var2, var3);
   }

   public void writeRaw(SerializableString var1) throws IOException {
      this.delegate.writeRaw(var1);
   }

   public void writeRaw(char[] var1, int var2, int var3) throws IOException {
      this.delegate.writeRaw(var1, var2, var3);
   }

   public void writeRaw(char var1) throws IOException {
      this.delegate.writeRaw(var1);
   }

   public void writeRawValue(String var1) throws IOException {
      this.delegate.writeRawValue(var1);
   }

   public void writeRawValue(String var1, int var2, int var3) throws IOException {
      this.delegate.writeRawValue(var1, var2, var3);
   }

   public void writeRawValue(char[] var1, int var2, int var3) throws IOException {
      this.delegate.writeRawValue(var1, var2, var3);
   }

   public void writeBinary(Base64Variant var1, byte[] var2, int var3, int var4) throws IOException {
      this.delegate.writeBinary(var1, var2, var3, var4);
   }

   public int writeBinary(Base64Variant var1, InputStream var2, int var3) throws IOException {
      return this.delegate.writeBinary(var1, var2, var3);
   }

   public void writeNumber(short var1) throws IOException {
      this.delegate.writeNumber(var1);
   }

   public void writeNumber(int var1) throws IOException {
      this.delegate.writeNumber(var1);
   }

   public void writeNumber(long var1) throws IOException {
      this.delegate.writeNumber(var1);
   }

   public void writeNumber(BigInteger var1) throws IOException {
      this.delegate.writeNumber(var1);
   }

   public void writeNumber(double var1) throws IOException {
      this.delegate.writeNumber(var1);
   }

   public void writeNumber(float var1) throws IOException {
      this.delegate.writeNumber(var1);
   }

   public void writeNumber(BigDecimal var1) throws IOException {
      this.delegate.writeNumber(var1);
   }

   public void writeNumber(String var1) throws IOException, UnsupportedOperationException {
      this.delegate.writeNumber(var1);
   }

   public void writeBoolean(boolean var1) throws IOException {
      this.delegate.writeBoolean(var1);
   }

   public void writeNull() throws IOException {
      this.delegate.writeNull();
   }

   public void writeOmittedField(String var1) throws IOException {
      this.delegate.writeOmittedField(var1);
   }

   public void writeObjectId(Object var1) throws IOException {
      this.delegate.writeObjectId(var1);
   }

   public void writeObjectRef(Object var1) throws IOException {
      this.delegate.writeObjectRef(var1);
   }

   public void writeTypeId(Object var1) throws IOException {
      this.delegate.writeTypeId(var1);
   }

   public void writeEmbeddedObject(Object var1) throws IOException {
      this.delegate.writeEmbeddedObject(var1);
   }

   public void writeObject(Object var1) throws IOException {
      if (this.delegateCopyMethods) {
         this.delegate.writeObject(var1);
      } else {
         if (var1 == null) {
            this.writeNull();
         } else {
            ObjectCodec var2 = this.getCodec();
            if (var2 != null) {
               var2.writeValue(this, var1);
               return;
            }

            this._writeSimpleObject(var1);
         }

      }
   }

   public void writeTree(TreeNode var1) throws IOException {
      if (this.delegateCopyMethods) {
         this.delegate.writeTree(var1);
      } else {
         if (var1 == null) {
            this.writeNull();
         } else {
            ObjectCodec var2 = this.getCodec();
            if (var2 == null) {
               throw new IllegalStateException("No ObjectCodec defined");
            }

            var2.writeTree(this, var1);
         }

      }
   }

   public void copyCurrentEvent(JsonParser var1) throws IOException {
      if (this.delegateCopyMethods) {
         this.delegate.copyCurrentEvent(var1);
      } else {
         super.copyCurrentEvent(var1);
      }

   }

   public void copyCurrentStructure(JsonParser var1) throws IOException {
      if (this.delegateCopyMethods) {
         this.delegate.copyCurrentStructure(var1);
      } else {
         super.copyCurrentStructure(var1);
      }

   }

   public JsonStreamContext getOutputContext() {
      return this.delegate.getOutputContext();
   }

   public void flush() throws IOException {
      this.delegate.flush();
   }

   public void close() throws IOException {
      this.delegate.close();
   }

   public boolean isClosed() {
      return this.delegate.isClosed();
   }
}
