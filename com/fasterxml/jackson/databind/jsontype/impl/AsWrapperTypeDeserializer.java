package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo$As;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.util.JsonParserSequence;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.IOException;
import java.io.Serializable;

public class AsWrapperTypeDeserializer extends TypeDeserializerBase implements Serializable {
   private static final long serialVersionUID = 1L;

   public AsWrapperTypeDeserializer(JavaType var1, TypeIdResolver var2, String var3, boolean var4, JavaType var5) {
      super(var1, var2, var3, var4, var5);
   }

   protected AsWrapperTypeDeserializer(AsWrapperTypeDeserializer var1, BeanProperty var2) {
      super(var1, var2);
   }

   public TypeDeserializer forProperty(BeanProperty var1) {
      return var1 == this._property ? this : new AsWrapperTypeDeserializer(this, var1);
   }

   public JsonTypeInfo$As getTypeInclusion() {
      return JsonTypeInfo$As.WRAPPER_OBJECT;
   }

   public Object deserializeTypedFromObject(JsonParser var1, DeserializationContext var2) throws IOException {
      return this._deserialize(var1, var2);
   }

   public Object deserializeTypedFromArray(JsonParser var1, DeserializationContext var2) throws IOException {
      return this._deserialize(var1, var2);
   }

   public Object deserializeTypedFromScalar(JsonParser var1, DeserializationContext var2) throws IOException {
      return this._deserialize(var1, var2);
   }

   public Object deserializeTypedFromAny(JsonParser var1, DeserializationContext var2) throws IOException {
      return this._deserialize(var1, var2);
   }

   protected Object _deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      if (((JsonParser)var1).canReadTypeId()) {
         Object var3 = ((JsonParser)var1).getTypeId();
         if (var3 != null) {
            return this._deserializeWithNativeTypeId((JsonParser)var1, var2, var3);
         }
      }

      JsonToken var7 = ((JsonParser)var1).getCurrentToken();
      if (var7 == JsonToken.START_OBJECT) {
         if (((JsonParser)var1).nextToken() != JsonToken.FIELD_NAME) {
            var2.reportWrongTokenException(this.baseType(), JsonToken.FIELD_NAME, "need JSON String that contains type id (for subtype of " + this.baseTypeName() + ")");
         }
      } else if (var7 != JsonToken.FIELD_NAME) {
         var2.reportWrongTokenException(this.baseType(), JsonToken.START_OBJECT, "need JSON Object to contain As.WRAPPER_OBJECT type information for class " + this.baseTypeName());
      }

      String var4 = ((JsonParser)var1).getText();
      JsonDeserializer var5 = this._findDeserializer(var2, var4);
      ((JsonParser)var1).nextToken();
      if (this._typeIdVisible && ((JsonParser)var1).getCurrentToken() == JsonToken.START_OBJECT) {
         TokenBuffer var6 = new TokenBuffer((ObjectCodec)null, false);
         var6.writeStartObject();
         var6.writeFieldName(this._typePropertyName);
         var6.writeString(var4);
         ((JsonParser)var1).clearCurrentToken();
         var1 = JsonParserSequence.createFlattened(false, var6.asParser((JsonParser)var1), (JsonParser)var1);
         ((JsonParser)var1).nextToken();
      }

      Object var8 = var5.deserialize((JsonParser)var1, var2);
      if (((JsonParser)var1).nextToken() != JsonToken.END_OBJECT) {
         var2.reportWrongTokenException(this.baseType(), JsonToken.END_OBJECT, "expected closing END_OBJECT after type information and deserialized value");
      }

      return var8;
   }
}
