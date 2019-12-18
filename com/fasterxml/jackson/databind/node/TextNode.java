package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.Base64Variants;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.io.NumberInput;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.io.IOException;

public class TextNode extends ValueNode {
   static final TextNode EMPTY_STRING_NODE = new TextNode("");
   protected final String _value;

   public TextNode(String var1) {
      super();
      this._value = var1;
   }

   public static TextNode valueOf(String var0) {
      if (var0 == null) {
         return null;
      } else {
         return var0.length() == 0 ? EMPTY_STRING_NODE : new TextNode(var0);
      }
   }

   public JsonNodeType getNodeType() {
      return JsonNodeType.STRING;
   }

   public JsonToken asToken() {
      return JsonToken.VALUE_STRING;
   }

   public String textValue() {
      return this._value;
   }

   public byte[] getBinaryValue(Base64Variant var1) throws IOException {
      String var2 = this._value.trim();
      ByteArrayBuilder var3 = new ByteArrayBuilder(4 + (var2.length() * 3 << 2));

      try {
         var1.decode(var2, var3);
      } catch (IllegalArgumentException var5) {
         throw InvalidFormatException.from((JsonParser)null, String.format("Cannot access contents of TextNode as binary due to broken Base64 encoding: %s", var5.getMessage()), var2, byte[].class);
      }

      return var3.toByteArray();
   }

   public byte[] binaryValue() throws IOException {
      return this.getBinaryValue(Base64Variants.getDefaultVariant());
   }

   public String asText() {
      return this._value;
   }

   public String asText(String var1) {
      return this._value == null ? var1 : this._value;
   }

   public boolean asBoolean(boolean var1) {
      if (this._value != null) {
         String var2 = this._value.trim();
         if ("true".equals(var2)) {
            return true;
         }

         if ("false".equals(var2)) {
            return false;
         }
      }

      return var1;
   }

   public int asInt(int var1) {
      return NumberInput.parseAsInt(this._value, var1);
   }

   public long asLong(long var1) {
      return NumberInput.parseAsLong(this._value, var1);
   }

   public double asDouble(double var1) {
      return NumberInput.parseAsDouble(this._value, var1);
   }

   public final void serialize(JsonGenerator var1, SerializerProvider var2) throws IOException {
      if (this._value == null) {
         var1.writeNull();
      } else {
         var1.writeString(this._value);
      }

   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 == null) {
         return false;
      } else {
         return var1 instanceof TextNode ? ((TextNode)var1)._value.equals(this._value) : false;
      }
   }

   public int hashCode() {
      return this._value.hashCode();
   }

   public String toString() {
      int var1 = this._value.length();
      var1 = var1 + 2 + (var1 >> 4);
      StringBuilder var2 = new StringBuilder(var1);
      appendQuoted(var2, this._value);
      return var2.toString();
   }

   protected static void appendQuoted(StringBuilder var0, String var1) {
      var0.append('"');
      CharTypes.appendQuoted(var0, var1);
      var0.append('"');
   }
}
