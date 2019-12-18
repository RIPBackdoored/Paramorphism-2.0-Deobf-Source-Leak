package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonParser$Feature;
import com.fasterxml.jackson.core.JsonParser$NumberType;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.Version;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;

public class JsonParserDelegate extends JsonParser {
   protected JsonParser delegate;

   public JsonParserDelegate(JsonParser var1) {
      super();
      this.delegate = var1;
   }

   public Object getCurrentValue() {
      return this.delegate.getCurrentValue();
   }

   public void setCurrentValue(Object var1) {
      this.delegate.setCurrentValue(var1);
   }

   public void setCodec(ObjectCodec var1) {
      this.delegate.setCodec(var1);
   }

   public ObjectCodec getCodec() {
      return this.delegate.getCodec();
   }

   public JsonParser enable(JsonParser$Feature var1) {
      this.delegate.enable(var1);
      return this;
   }

   public JsonParser disable(JsonParser$Feature var1) {
      this.delegate.disable(var1);
      return this;
   }

   public boolean isEnabled(JsonParser$Feature var1) {
      return this.delegate.isEnabled(var1);
   }

   public int getFeatureMask() {
      return this.delegate.getFeatureMask();
   }

   /** @deprecated */
   @Deprecated
   public JsonParser setFeatureMask(int var1) {
      this.delegate.setFeatureMask(var1);
      return this;
   }

   public JsonParser overrideStdFeatures(int var1, int var2) {
      this.delegate.overrideStdFeatures(var1, var2);
      return this;
   }

   public JsonParser overrideFormatFeatures(int var1, int var2) {
      this.delegate.overrideFormatFeatures(var1, var2);
      return this;
   }

   public FormatSchema getSchema() {
      return this.delegate.getSchema();
   }

   public void setSchema(FormatSchema var1) {
      this.delegate.setSchema(var1);
   }

   public boolean canUseSchema(FormatSchema var1) {
      return this.delegate.canUseSchema(var1);
   }

   public Version version() {
      return this.delegate.version();
   }

   public Object getInputSource() {
      return this.delegate.getInputSource();
   }

   public boolean requiresCustomCodec() {
      return this.delegate.requiresCustomCodec();
   }

   public void close() throws IOException {
      this.delegate.close();
   }

   public boolean isClosed() {
      return this.delegate.isClosed();
   }

   public JsonToken currentToken() {
      return this.delegate.currentToken();
   }

   public int currentTokenId() {
      return this.delegate.currentTokenId();
   }

   public JsonToken getCurrentToken() {
      return this.delegate.getCurrentToken();
   }

   public int getCurrentTokenId() {
      return this.delegate.getCurrentTokenId();
   }

   public boolean hasCurrentToken() {
      return this.delegate.hasCurrentToken();
   }

   public boolean hasTokenId(int var1) {
      return this.delegate.hasTokenId(var1);
   }

   public boolean hasToken(JsonToken var1) {
      return this.delegate.hasToken(var1);
   }

   public String getCurrentName() throws IOException {
      return this.delegate.getCurrentName();
   }

   public JsonLocation getCurrentLocation() {
      return this.delegate.getCurrentLocation();
   }

   public JsonStreamContext getParsingContext() {
      return this.delegate.getParsingContext();
   }

   public boolean isExpectedStartArrayToken() {
      return this.delegate.isExpectedStartArrayToken();
   }

   public boolean isExpectedStartObjectToken() {
      return this.delegate.isExpectedStartObjectToken();
   }

   public boolean isNaN() throws IOException {
      return this.delegate.isNaN();
   }

   public void clearCurrentToken() {
      this.delegate.clearCurrentToken();
   }

   public JsonToken getLastClearedToken() {
      return this.delegate.getLastClearedToken();
   }

   public void overrideCurrentName(String var1) {
      this.delegate.overrideCurrentName(var1);
   }

   public String getText() throws IOException {
      return this.delegate.getText();
   }

   public boolean hasTextCharacters() {
      return this.delegate.hasTextCharacters();
   }

   public char[] getTextCharacters() throws IOException {
      return this.delegate.getTextCharacters();
   }

   public int getTextLength() throws IOException {
      return this.delegate.getTextLength();
   }

   public int getTextOffset() throws IOException {
      return this.delegate.getTextOffset();
   }

   public int getText(Writer var1) throws IOException, UnsupportedOperationException {
      return this.delegate.getText(var1);
   }

   public BigInteger getBigIntegerValue() throws IOException {
      return this.delegate.getBigIntegerValue();
   }

   public boolean getBooleanValue() throws IOException {
      return this.delegate.getBooleanValue();
   }

   public byte getByteValue() throws IOException {
      return this.delegate.getByteValue();
   }

   public short getShortValue() throws IOException {
      return this.delegate.getShortValue();
   }

   public BigDecimal getDecimalValue() throws IOException {
      return this.delegate.getDecimalValue();
   }

   public double getDoubleValue() throws IOException {
      return this.delegate.getDoubleValue();
   }

   public float getFloatValue() throws IOException {
      return this.delegate.getFloatValue();
   }

   public int getIntValue() throws IOException {
      return this.delegate.getIntValue();
   }

   public long getLongValue() throws IOException {
      return this.delegate.getLongValue();
   }

   public JsonParser$NumberType getNumberType() throws IOException {
      return this.delegate.getNumberType();
   }

   public Number getNumberValue() throws IOException {
      return this.delegate.getNumberValue();
   }

   public int getValueAsInt() throws IOException {
      return this.delegate.getValueAsInt();
   }

   public int getValueAsInt(int var1) throws IOException {
      return this.delegate.getValueAsInt(var1);
   }

   public long getValueAsLong() throws IOException {
      return this.delegate.getValueAsLong();
   }

   public long getValueAsLong(long var1) throws IOException {
      return this.delegate.getValueAsLong(var1);
   }

   public double getValueAsDouble() throws IOException {
      return this.delegate.getValueAsDouble();
   }

   public double getValueAsDouble(double var1) throws IOException {
      return this.delegate.getValueAsDouble(var1);
   }

   public boolean getValueAsBoolean() throws IOException {
      return this.delegate.getValueAsBoolean();
   }

   public boolean getValueAsBoolean(boolean var1) throws IOException {
      return this.delegate.getValueAsBoolean(var1);
   }

   public String getValueAsString() throws IOException {
      return this.delegate.getValueAsString();
   }

   public String getValueAsString(String var1) throws IOException {
      return this.delegate.getValueAsString(var1);
   }

   public Object getEmbeddedObject() throws IOException {
      return this.delegate.getEmbeddedObject();
   }

   public byte[] getBinaryValue(Base64Variant var1) throws IOException {
      return this.delegate.getBinaryValue(var1);
   }

   public int readBinaryValue(Base64Variant var1, OutputStream var2) throws IOException {
      return this.delegate.readBinaryValue(var1, var2);
   }

   public JsonLocation getTokenLocation() {
      return this.delegate.getTokenLocation();
   }

   public JsonToken nextToken() throws IOException {
      return this.delegate.nextToken();
   }

   public JsonToken nextValue() throws IOException {
      return this.delegate.nextValue();
   }

   public void finishToken() throws IOException {
      this.delegate.finishToken();
   }

   public JsonParser skipChildren() throws IOException {
      this.delegate.skipChildren();
      return this;
   }

   public boolean canReadObjectId() {
      return this.delegate.canReadObjectId();
   }

   public boolean canReadTypeId() {
      return this.delegate.canReadTypeId();
   }

   public Object getObjectId() throws IOException {
      return this.delegate.getObjectId();
   }

   public Object getTypeId() throws IOException {
      return this.delegate.getTypeId();
   }
}
