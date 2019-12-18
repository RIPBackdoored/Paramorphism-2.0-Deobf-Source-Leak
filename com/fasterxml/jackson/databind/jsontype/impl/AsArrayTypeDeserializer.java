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

public class AsArrayTypeDeserializer extends TypeDeserializerBase implements Serializable {
   private static final long serialVersionUID = 1L;

   public AsArrayTypeDeserializer(JavaType var1, TypeIdResolver var2, String var3, boolean var4, JavaType var5) {
      super(var1, var2, var3, var4, var5);
   }

   public AsArrayTypeDeserializer(AsArrayTypeDeserializer var1, BeanProperty var2) {
      super(var1, var2);
   }

   public TypeDeserializer forProperty(BeanProperty var1) {
      return var1 == this._property ? this : new AsArrayTypeDeserializer(this, var1);
   }

   public JsonTypeInfo$As getTypeInclusion() {
      return JsonTypeInfo$As.WRAPPER_ARRAY;
   }

   public Object deserializeTypedFromArray(JsonParser var1, DeserializationContext var2) throws IOException {
      return this._deserialize(var1, var2);
   }

   public Object deserializeTypedFromObject(JsonParser var1, DeserializationContext var2) throws IOException {
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

      boolean var7 = ((JsonParser)var1).isExpectedStartArrayToken();
      String var4 = this._locateTypeId((JsonParser)var1, var2);
      JsonDeserializer var5 = this._findDeserializer(var2, var4);
      if (this._typeIdVisible && !this._usesExternalId() && ((JsonParser)var1).getCurrentToken() == JsonToken.START_OBJECT) {
         TokenBuffer var6 = new TokenBuffer((ObjectCodec)null, false);
         var6.writeStartObject();
         var6.writeFieldName(this._typePropertyName);
         var6.writeString(var4);
         ((JsonParser)var1).clearCurrentToken();
         var1 = JsonParserSequence.createFlattened(false, var6.asParser((JsonParser)var1), (JsonParser)var1);
         ((JsonParser)var1).nextToken();
      }

      Object var8 = var5.deserialize((JsonParser)var1, var2);
      if (var7 && ((JsonParser)var1).nextToken() != JsonToken.END_ARRAY) {
         var2.reportWrongTokenException(this.baseType(), JsonToken.END_ARRAY, "expected closing END_ARRAY after type information and deserialized value");
      }

      return var8;
   }

   protected String _locateTypeId(JsonParser var1, DeserializationContext var2) throws IOException {
      if (!var1.isExpectedStartArrayToken()) {
         if (this._defaultImpl != null) {
            return this._idResolver.idFromBaseType();
         } else {
            var2.reportWrongTokenException(this.baseType(), JsonToken.START_ARRAY, "need JSON Array to contain As.WRAPPER_ARRAY type information for class " + this.baseTypeName());
            return null;
         }
      } else {
         JsonToken var3 = var1.nextToken();
         if (var3 == JsonToken.VALUE_STRING) {
            String var4 = var1.getText();
            var1.nextToken();
            return var4;
         } else if (this._defaultImpl != null) {
            return this._idResolver.idFromBaseType();
         } else {
            var2.reportWrongTokenException(this.baseType(), JsonToken.VALUE_STRING, "need JSON String that contains type id (for subtype of %s)", this.baseTypeName());
            return null;
         }
      }
   }

   protected boolean _usesExternalId() {
      return false;
   }
}
