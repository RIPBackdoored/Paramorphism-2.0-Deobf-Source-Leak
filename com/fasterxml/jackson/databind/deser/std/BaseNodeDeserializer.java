package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonParser$NumberType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.RawValue;
import java.io.IOException;

abstract class BaseNodeDeserializer extends StdDeserializer {
   protected final Boolean _supportsUpdates;

   public BaseNodeDeserializer(Class var1, Boolean var2) {
      super(var1);
      this._supportsUpdates = var2;
   }

   public Object deserializeWithType(JsonParser var1, DeserializationContext var2, TypeDeserializer var3) throws IOException {
      return var3.deserializeTypedFromAny(var1, var2);
   }

   public boolean isCachable() {
      return true;
   }

   public Boolean supportsUpdate(DeserializationConfig var1) {
      return this._supportsUpdates;
   }

   protected void _handleDuplicateField(JsonParser var1, DeserializationContext var2, JsonNodeFactory var3, String var4, ObjectNode var5, JsonNode var6, JsonNode var7) throws JsonProcessingException {
      if (var2.isEnabled(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY)) {
         var2.reportInputMismatch(JsonNode.class, "Duplicate field '%s' for ObjectNode: not allowed when FAIL_ON_READING_DUP_TREE_KEY enabled", var4);
      }

   }

   protected final ObjectNode deserializeObject(JsonParser var1, DeserializationContext var2, JsonNodeFactory var3) throws IOException {
      ObjectNode var4 = var3.objectNode();

      for(String var5 = var1.nextFieldName(); var5 != null; var5 = var1.nextFieldName()) {
         JsonToken var7 = var1.nextToken();
         if (var7 == null) {
            var7 = JsonToken.NOT_AVAILABLE;
         }

         Object var6;
         switch(var7.id()) {
         case 1:
            var6 = this.deserializeObject(var1, var2, var3);
            break;
         case 2:
         case 4:
         case 5:
         case 8:
         default:
            var6 = this.deserializeAny(var1, var2, var3);
            break;
         case 3:
            var6 = this.deserializeArray(var1, var2, var3);
            break;
         case 6:
            var6 = var3.textNode(var1.getText());
            break;
         case 7:
            var6 = this._fromInt(var1, var2, var3);
            break;
         case 9:
            var6 = var3.booleanNode(true);
            break;
         case 10:
            var6 = var3.booleanNode(false);
            break;
         case 11:
            var6 = var3.nullNode();
            break;
         case 12:
            var6 = this._fromEmbedded(var1, var2, var3);
         }

         JsonNode var8 = var4.replace(var5, (JsonNode)var6);
         if (var8 != null) {
            this._handleDuplicateField(var1, var2, var3, var5, var4, var8, (JsonNode)var6);
         }
      }

      return var4;
   }

   protected final ObjectNode deserializeObjectAtName(JsonParser var1, DeserializationContext var2, JsonNodeFactory var3) throws IOException {
      ObjectNode var4 = var3.objectNode();

      for(String var5 = var1.getCurrentName(); var5 != null; var5 = var1.nextFieldName()) {
         JsonToken var7 = var1.nextToken();
         if (var7 == null) {
            var7 = JsonToken.NOT_AVAILABLE;
         }

         Object var6;
         switch(var7.id()) {
         case 1:
            var6 = this.deserializeObject(var1, var2, var3);
            break;
         case 2:
         case 4:
         case 5:
         case 8:
         default:
            var6 = this.deserializeAny(var1, var2, var3);
            break;
         case 3:
            var6 = this.deserializeArray(var1, var2, var3);
            break;
         case 6:
            var6 = var3.textNode(var1.getText());
            break;
         case 7:
            var6 = this._fromInt(var1, var2, var3);
            break;
         case 9:
            var6 = var3.booleanNode(true);
            break;
         case 10:
            var6 = var3.booleanNode(false);
            break;
         case 11:
            var6 = var3.nullNode();
            break;
         case 12:
            var6 = this._fromEmbedded(var1, var2, var3);
         }

         JsonNode var8 = var4.replace(var5, (JsonNode)var6);
         if (var8 != null) {
            this._handleDuplicateField(var1, var2, var3, var5, var4, var8, (JsonNode)var6);
         }
      }

      return var4;
   }

   protected final JsonNode updateObject(JsonParser var1, DeserializationContext var2, ObjectNode var3) throws IOException {
      String var4;
      if (var1.isExpectedStartObjectToken()) {
         var4 = var1.nextFieldName();
      } else {
         if (!var1.hasToken(JsonToken.FIELD_NAME)) {
            return (JsonNode)this.deserialize(var1, var2);
         }

         var4 = var1.getCurrentName();
      }

      for(; var4 != null; var4 = var1.nextFieldName()) {
         JsonToken var5 = var1.nextToken();
         JsonNode var6 = var3.get(var4);
         if (var6 != null) {
            JsonNode var9;
            if (var6 instanceof ObjectNode) {
               var9 = this.updateObject(var1, var2, (ObjectNode)var6);
               if (var9 != var6) {
                  var3.set(var4, var9);
               }
               continue;
            }

            if (var6 instanceof ArrayNode) {
               var9 = this.updateArray(var1, var2, (ArrayNode)var6);
               if (var9 != var6) {
                  var3.set(var4, var9);
               }
               continue;
            }
         }

         if (var5 == null) {
            var5 = JsonToken.NOT_AVAILABLE;
         }

         JsonNodeFactory var8 = var2.getNodeFactory();
         Object var7;
         switch(var5.id()) {
         case 1:
            var7 = this.deserializeObject(var1, var2, var8);
            break;
         case 2:
         case 4:
         case 5:
         case 8:
         default:
            var7 = this.deserializeAny(var1, var2, var8);
            break;
         case 3:
            var7 = this.deserializeArray(var1, var2, var8);
            break;
         case 6:
            var7 = var8.textNode(var1.getText());
            break;
         case 7:
            var7 = this._fromInt(var1, var2, var8);
            break;
         case 9:
            var7 = var8.booleanNode(true);
            break;
         case 10:
            var7 = var8.booleanNode(false);
            break;
         case 11:
            var7 = var8.nullNode();
            break;
         case 12:
            var7 = this._fromEmbedded(var1, var2, var8);
         }

         if (var6 != null) {
            this._handleDuplicateField(var1, var2, var8, var4, var3, var6, (JsonNode)var7);
         }

         var3.set(var4, (JsonNode)var7);
      }

      return var3;
   }

   protected final ArrayNode deserializeArray(JsonParser var1, DeserializationContext var2, JsonNodeFactory var3) throws IOException {
      ArrayNode var4 = var3.arrayNode();

      while(true) {
         JsonToken var5 = var1.nextToken();
         switch(var5.id()) {
         case 1:
            var4.add((JsonNode)this.deserializeObject(var1, var2, var3));
            break;
         case 2:
         case 5:
         case 8:
         default:
            var4.add(this.deserializeAny(var1, var2, var3));
            break;
         case 3:
            var4.add((JsonNode)this.deserializeArray(var1, var2, var3));
            break;
         case 4:
            return var4;
         case 6:
            var4.add((JsonNode)var3.textNode(var1.getText()));
            break;
         case 7:
            var4.add(this._fromInt(var1, var2, var3));
            break;
         case 9:
            var4.add((JsonNode)var3.booleanNode(true));
            break;
         case 10:
            var4.add((JsonNode)var3.booleanNode(false));
            break;
         case 11:
            var4.add((JsonNode)var3.nullNode());
            break;
         case 12:
            var4.add(this._fromEmbedded(var1, var2, var3));
         }
      }
   }

   protected final JsonNode updateArray(JsonParser var1, DeserializationContext var2, ArrayNode var3) throws IOException {
      JsonNodeFactory var4 = var2.getNodeFactory();

      while(true) {
         JsonToken var5 = var1.nextToken();
         switch(var5.id()) {
         case 1:
            var3.add((JsonNode)this.deserializeObject(var1, var2, var4));
            break;
         case 2:
         case 5:
         case 8:
         default:
            var3.add(this.deserializeAny(var1, var2, var4));
            break;
         case 3:
            var3.add((JsonNode)this.deserializeArray(var1, var2, var4));
            break;
         case 4:
            return var3;
         case 6:
            var3.add((JsonNode)var4.textNode(var1.getText()));
            break;
         case 7:
            var3.add(this._fromInt(var1, var2, var4));
            break;
         case 9:
            var3.add((JsonNode)var4.booleanNode(true));
            break;
         case 10:
            var3.add((JsonNode)var4.booleanNode(false));
            break;
         case 11:
            var3.add((JsonNode)var4.nullNode());
            break;
         case 12:
            var3.add(this._fromEmbedded(var1, var2, var4));
         }
      }
   }

   protected final JsonNode deserializeAny(JsonParser var1, DeserializationContext var2, JsonNodeFactory var3) throws IOException {
      switch(var1.getCurrentTokenId()) {
      case 2:
         return var3.objectNode();
      case 3:
      case 4:
      default:
         return (JsonNode)var2.handleUnexpectedToken(this.handledType(), var1);
      case 5:
         return this.deserializeObjectAtName(var1, var2, var3);
      case 6:
         return var3.textNode(var1.getText());
      case 7:
         return this._fromInt(var1, var2, var3);
      case 8:
         return this._fromFloat(var1, var2, var3);
      case 9:
         return var3.booleanNode(true);
      case 10:
         return var3.booleanNode(false);
      case 11:
         return var3.nullNode();
      case 12:
         return this._fromEmbedded(var1, var2, var3);
      }
   }

   protected final JsonNode _fromInt(JsonParser var1, DeserializationContext var2, JsonNodeFactory var3) throws IOException {
      int var5 = var2.getDeserializationFeatures();
      JsonParser$NumberType var4;
      if ((var5 & F_MASK_INT_COERCIONS) != 0) {
         if (DeserializationFeature.USE_BIG_INTEGER_FOR_INTS.enabledIn(var5)) {
            var4 = JsonParser$NumberType.BIG_INTEGER;
         } else if (DeserializationFeature.USE_LONG_FOR_INTS.enabledIn(var5)) {
            var4 = JsonParser$NumberType.LONG;
         } else {
            var4 = var1.getNumberType();
         }
      } else {
         var4 = var1.getNumberType();
      }

      if (var4 == JsonParser$NumberType.INT) {
         return var3.numberNode(var1.getIntValue());
      } else {
         return (JsonNode)(var4 == JsonParser$NumberType.LONG ? var3.numberNode(var1.getLongValue()) : var3.numberNode(var1.getBigIntegerValue()));
      }
   }

   protected final JsonNode _fromFloat(JsonParser var1, DeserializationContext var2, JsonNodeFactory var3) throws IOException {
      JsonParser$NumberType var4 = var1.getNumberType();
      if (var4 == JsonParser$NumberType.BIG_DECIMAL) {
         return var3.numberNode(var1.getDecimalValue());
      } else if (var2.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
         return (JsonNode)(var1.isNaN() ? var3.numberNode(var1.getDoubleValue()) : var3.numberNode(var1.getDecimalValue()));
      } else {
         return var4 == JsonParser$NumberType.FLOAT ? var3.numberNode(var1.getFloatValue()) : var3.numberNode(var1.getDoubleValue());
      }
   }

   protected final JsonNode _fromEmbedded(JsonParser var1, DeserializationContext var2, JsonNodeFactory var3) throws IOException {
      Object var4 = var1.getEmbeddedObject();
      if (var4 == null) {
         return var3.nullNode();
      } else {
         Class var5 = var4.getClass();
         if (var5 == byte[].class) {
            return var3.binaryNode((byte[])((byte[])var4));
         } else if (var4 instanceof RawValue) {
            return var3.rawValueNode((RawValue)var4);
         } else {
            return (JsonNode)(var4 instanceof JsonNode ? (JsonNode)var4 : var3.pojoNode(var4));
         }
      }
   }
}
