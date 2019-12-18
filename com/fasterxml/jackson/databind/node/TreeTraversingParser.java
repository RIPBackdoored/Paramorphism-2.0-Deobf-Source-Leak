package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonParser$NumberType;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.base.ParserMinimalBase;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.cfg.PackageVersion;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

public class TreeTraversingParser extends ParserMinimalBase {
   protected ObjectCodec _objectCodec;
   protected NodeCursor _nodeCursor;
   protected JsonToken _nextToken;
   protected boolean _startContainer;
   protected boolean _closed;

   public TreeTraversingParser(JsonNode var1) {
      this(var1, (ObjectCodec)null);
   }

   public TreeTraversingParser(JsonNode var1, ObjectCodec var2) {
      super(0);
      this._objectCodec = var2;
      if (var1.isArray()) {
         this._nextToken = JsonToken.START_ARRAY;
         this._nodeCursor = new NodeCursor$ArrayCursor(var1, (NodeCursor)null);
      } else if (var1.isObject()) {
         this._nextToken = JsonToken.START_OBJECT;
         this._nodeCursor = new NodeCursor$ObjectCursor(var1, (NodeCursor)null);
      } else {
         this._nodeCursor = new NodeCursor$RootCursor(var1, (NodeCursor)null);
      }

   }

   public void setCodec(ObjectCodec var1) {
      this._objectCodec = var1;
   }

   public ObjectCodec getCodec() {
      return this._objectCodec;
   }

   public Version version() {
      return PackageVersion.VERSION;
   }

   public void close() throws IOException {
      if (!this._closed) {
         this._closed = true;
         this._nodeCursor = null;
         this._currToken = null;
      }

   }

   public JsonToken nextToken() throws IOException, JsonParseException {
      if (this._nextToken != null) {
         this._currToken = this._nextToken;
         this._nextToken = null;
         return this._currToken;
      } else if (this._startContainer) {
         this._startContainer = false;
         if (!this._nodeCursor.currentHasChildren()) {
            this._currToken = this._currToken == JsonToken.START_OBJECT ? JsonToken.END_OBJECT : JsonToken.END_ARRAY;
            return this._currToken;
         } else {
            this._nodeCursor = this._nodeCursor.iterateChildren();
            this._currToken = this._nodeCursor.nextToken();
            if (this._currToken == JsonToken.START_OBJECT || this._currToken == JsonToken.START_ARRAY) {
               this._startContainer = true;
            }

            return this._currToken;
         }
      } else if (this._nodeCursor == null) {
         this._closed = true;
         return null;
      } else {
         this._currToken = this._nodeCursor.nextToken();
         if (this._currToken == null) {
            this._currToken = this._nodeCursor.endToken();
            this._nodeCursor = this._nodeCursor.getParent();
            return this._currToken;
         } else {
            if (this._currToken == JsonToken.START_OBJECT || this._currToken == JsonToken.START_ARRAY) {
               this._startContainer = true;
            }

            return this._currToken;
         }
      }
   }

   public JsonParser skipChildren() throws IOException, JsonParseException {
      if (this._currToken == JsonToken.START_OBJECT) {
         this._startContainer = false;
         this._currToken = JsonToken.END_OBJECT;
      } else if (this._currToken == JsonToken.START_ARRAY) {
         this._startContainer = false;
         this._currToken = JsonToken.END_ARRAY;
      }

      return this;
   }

   public boolean isClosed() {
      return this._closed;
   }

   public String getCurrentName() {
      return this._nodeCursor == null ? null : this._nodeCursor.getCurrentName();
   }

   public void overrideCurrentName(String var1) {
      if (this._nodeCursor != null) {
         this._nodeCursor.overrideCurrentName(var1);
      }

   }

   public JsonStreamContext getParsingContext() {
      return this._nodeCursor;
   }

   public JsonLocation getTokenLocation() {
      return JsonLocation.NA;
   }

   public JsonLocation getCurrentLocation() {
      return JsonLocation.NA;
   }

   public String getText() {
      // $FF: Couldn't be decompiled
   }

   public char[] getTextCharacters() throws IOException, JsonParseException {
      return this.getText().toCharArray();
   }

   public int getTextLength() throws IOException, JsonParseException {
      return this.getText().length();
   }

   public int getTextOffset() throws IOException, JsonParseException {
      return 0;
   }

   public boolean hasTextCharacters() {
      return false;
   }

   public JsonParser$NumberType getNumberType() throws IOException, JsonParseException {
      JsonNode var1 = this.currentNumericNode();
      return var1 == null ? null : var1.numberType();
   }

   public BigInteger getBigIntegerValue() throws IOException, JsonParseException {
      return this.currentNumericNode().bigIntegerValue();
   }

   public BigDecimal getDecimalValue() throws IOException, JsonParseException {
      return this.currentNumericNode().decimalValue();
   }

   public double getDoubleValue() throws IOException, JsonParseException {
      return this.currentNumericNode().doubleValue();
   }

   public float getFloatValue() throws IOException, JsonParseException {
      return (float)this.currentNumericNode().doubleValue();
   }

   public long getLongValue() throws IOException, JsonParseException {
      return this.currentNumericNode().longValue();
   }

   public int getIntValue() throws IOException, JsonParseException {
      return this.currentNumericNode().intValue();
   }

   public Number getNumberValue() throws IOException, JsonParseException {
      return this.currentNumericNode().numberValue();
   }

   public Object getEmbeddedObject() {
      if (!this._closed) {
         JsonNode var1 = this.currentNode();
         if (var1 != null) {
            if (var1.isPojo()) {
               return ((POJONode)var1).getPojo();
            }

            if (var1.isBinary()) {
               return ((BinaryNode)var1).binaryValue();
            }
         }
      }

      return null;
   }

   public boolean isNaN() {
      if (!this._closed) {
         JsonNode var1 = this.currentNode();
         if (var1 instanceof NumericNode) {
            return ((NumericNode)var1).isNaN();
         }
      }

      return false;
   }

   public byte[] getBinaryValue(Base64Variant var1) throws IOException, JsonParseException {
      JsonNode var2 = this.currentNode();
      if (var2 != null) {
         byte[] var3 = var2.binaryValue();
         if (var3 != null) {
            return var3;
         }

         if (var2.isPojo()) {
            Object var4 = ((POJONode)var2).getPojo();
            if (var4 instanceof byte[]) {
               return (byte[])((byte[])var4);
            }
         }
      }

      return null;
   }

   public int readBinaryValue(Base64Variant var1, OutputStream var2) throws IOException, JsonParseException {
      byte[] var3 = this.getBinaryValue(var1);
      if (var3 != null) {
         var2.write(var3, 0, var3.length);
         return var3.length;
      } else {
         return 0;
      }
   }

   protected JsonNode currentNode() {
      return !this._closed && this._nodeCursor != null ? this._nodeCursor.currentNode() : null;
   }

   protected JsonNode currentNumericNode() throws JsonParseException {
      JsonNode var1 = this.currentNode();
      if (var1 != null && var1.isNumber()) {
         return var1;
      } else {
         JsonToken var2 = var1 == null ? null : var1.asToken();
         throw this._constructError("Current token (" + var2 + ") not numeric, cannot use numeric value accessors");
      }
   }

   protected void _handleEOF() throws JsonParseException {
      this._throwInternal();
   }
}
