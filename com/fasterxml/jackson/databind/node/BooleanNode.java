package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class BooleanNode extends ValueNode {
   public static final BooleanNode TRUE = new BooleanNode(true);
   public static final BooleanNode FALSE = new BooleanNode(false);
   private final boolean _value;

   protected BooleanNode(boolean var1) {
      super();
      this._value = var1;
   }

   public static BooleanNode getTrue() {
      return TRUE;
   }

   public static BooleanNode getFalse() {
      return FALSE;
   }

   public static BooleanNode valueOf(boolean var0) {
      return var0 ? TRUE : FALSE;
   }

   public JsonNodeType getNodeType() {
      return JsonNodeType.BOOLEAN;
   }

   public JsonToken asToken() {
      return this._value ? JsonToken.VALUE_TRUE : JsonToken.VALUE_FALSE;
   }

   public boolean booleanValue() {
      return this._value;
   }

   public String asText() {
      return this._value ? "true" : "false";
   }

   public boolean asBoolean() {
      return this._value;
   }

   public boolean asBoolean(boolean var1) {
      return this._value;
   }

   public int asInt(int var1) {
      return this._value ? 1 : 0;
   }

   public long asLong(long var1) {
      return this._value ? 1L : 0L;
   }

   public double asDouble(double var1) {
      return this._value ? 1.0D : 0.0D;
   }

   public final void serialize(JsonGenerator var1, SerializerProvider var2) throws IOException {
      var1.writeBoolean(this._value);
   }

   public int hashCode() {
      return this._value ? 3 : 1;
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 == null) {
         return false;
      } else if (!(var1 instanceof BooleanNode)) {
         return false;
      } else {
         return this._value == ((BooleanNode)var1)._value;
      }
   }
}
