package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualKeyDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

@JacksonStdImpl
public class MapEntryDeserializer extends ContainerDeserializerBase implements ContextualDeserializer {
   private static final long serialVersionUID = 1L;
   protected final KeyDeserializer _keyDeserializer;
   protected final JsonDeserializer _valueDeserializer;
   protected final TypeDeserializer _valueTypeDeserializer;

   public MapEntryDeserializer(JavaType var1, KeyDeserializer var2, JsonDeserializer var3, TypeDeserializer var4) {
      super(var1);
      if (var1.containedTypeCount() != 2) {
         throw new IllegalArgumentException("Missing generic type information for " + var1);
      } else {
         this._keyDeserializer = var2;
         this._valueDeserializer = var3;
         this._valueTypeDeserializer = var4;
      }
   }

   protected MapEntryDeserializer(MapEntryDeserializer var1) {
      super((ContainerDeserializerBase)var1);
      this._keyDeserializer = var1._keyDeserializer;
      this._valueDeserializer = var1._valueDeserializer;
      this._valueTypeDeserializer = var1._valueTypeDeserializer;
   }

   protected MapEntryDeserializer(MapEntryDeserializer var1, KeyDeserializer var2, JsonDeserializer var3, TypeDeserializer var4) {
      super((ContainerDeserializerBase)var1);
      this._keyDeserializer = var2;
      this._valueDeserializer = var3;
      this._valueTypeDeserializer = var4;
   }

   protected MapEntryDeserializer withResolved(KeyDeserializer var1, TypeDeserializer var2, JsonDeserializer var3) {
      return this._keyDeserializer == var1 && this._valueDeserializer == var3 && this._valueTypeDeserializer == var2 ? this : new MapEntryDeserializer(this, var1, var3, var2);
   }

   public JsonDeserializer createContextual(DeserializationContext var1, BeanProperty var2) throws JsonMappingException {
      KeyDeserializer var3 = this._keyDeserializer;
      if (var3 == null) {
         var3 = var1.findKeyDeserializer(this._containerType.containedType(0), var2);
      } else if (var3 instanceof ContextualKeyDeserializer) {
         var3 = ((ContextualKeyDeserializer)var3).createContextual(var1, var2);
      }

      JsonDeserializer var4 = this._valueDeserializer;
      var4 = this.findConvertingContentDeserializer(var1, var2, var4);
      JavaType var5 = this._containerType.containedType(1);
      if (var4 == null) {
         var4 = var1.findContextualValueDeserializer(var5, var2);
      } else {
         var4 = var1.handleSecondaryContextualization(var4, var2, var5);
      }

      TypeDeserializer var6 = this._valueTypeDeserializer;
      if (var6 != null) {
         var6 = var6.forProperty(var2);
      }

      return this.withResolved(var3, var6, var4);
   }

   public JavaType getContentType() {
      return this._containerType.containedType(1);
   }

   public JsonDeserializer getContentDeserializer() {
      return this._valueDeserializer;
   }

   public Entry deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      JsonToken var3 = var1.getCurrentToken();
      if (var3 != JsonToken.START_OBJECT && var3 != JsonToken.FIELD_NAME && var3 != JsonToken.END_OBJECT) {
         return (Entry)this._deserializeFromEmpty(var1, var2);
      } else {
         if (var3 == JsonToken.START_OBJECT) {
            var3 = var1.nextToken();
         }

         if (var3 != JsonToken.FIELD_NAME) {
            return var3 == JsonToken.END_OBJECT ? (Entry)var2.reportInputMismatch((JsonDeserializer)this, "Cannot deserialize a Map.Entry out of empty JSON Object") : (Entry)var2.handleUnexpectedToken(this.handledType(), var1);
         } else {
            KeyDeserializer var4 = this._keyDeserializer;
            JsonDeserializer var5 = this._valueDeserializer;
            TypeDeserializer var6 = this._valueTypeDeserializer;
            String var7 = var1.getCurrentName();
            Object var8 = var4.deserializeKey(var7, var2);
            Object var9 = null;
            var3 = var1.nextToken();

            try {
               if (var3 == JsonToken.VALUE_NULL) {
                  var9 = var5.getNullValue(var2);
               } else if (var6 == null) {
                  var9 = var5.deserialize(var1, var2);
               } else {
                  var9 = var5.deserializeWithType(var1, var2, var6);
               }
            } catch (Exception var11) {
               this.wrapAndThrow(var11, Entry.class, var7);
            }

            var3 = var1.nextToken();
            if (var3 != JsonToken.END_OBJECT) {
               if (var3 == JsonToken.FIELD_NAME) {
                  var2.reportInputMismatch((JsonDeserializer)this, "Problem binding JSON into Map.Entry: more than one entry in JSON (second field: '%s')", var1.getCurrentName());
               } else {
                  var2.reportInputMismatch((JsonDeserializer)this, "Problem binding JSON into Map.Entry: unexpected content after JSON Object entry: " + var3);
               }

               return null;
            } else {
               return new SimpleEntry(var8, var9);
            }
         }
      }
   }

   public Entry deserialize(JsonParser var1, DeserializationContext var2, Entry var3) throws IOException {
      throw new IllegalStateException("Cannot update Map.Entry values");
   }

   public Object deserializeWithType(JsonParser var1, DeserializationContext var2, TypeDeserializer var3) throws IOException {
      return var3.deserializeTypedFromObject(var1, var2);
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2, Object var3) throws IOException {
      return this.deserialize(var1, var2, (Entry)var3);
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException, JsonProcessingException {
      return this.deserialize(var1, var2);
   }
}
