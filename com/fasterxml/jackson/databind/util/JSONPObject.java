package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonpCharacterEscapes;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;

public class JSONPObject implements JsonSerializable {
   protected final String _function;
   protected final Object _value;
   protected final JavaType _serializationType;

   public JSONPObject(String var1, Object var2) {
      this(var1, var2, (JavaType)null);
   }

   public JSONPObject(String var1, Object var2, JavaType var3) {
      super();
      this._function = var1;
      this._value = var2;
      this._serializationType = var3;
   }

   public void serializeWithType(JsonGenerator var1, SerializerProvider var2, TypeSerializer var3) throws IOException {
      this.serialize(var1, var2);
   }

   public void serialize(JsonGenerator var1, SerializerProvider var2) throws IOException {
      var1.writeRaw(this._function);
      var1.writeRaw('(');
      if (this._value == null) {
         var2.defaultSerializeNull(var1);
      } else {
         boolean var3 = var1.getCharacterEscapes() == null;
         if (var3) {
            var1.setCharacterEscapes(JsonpCharacterEscapes.instance());
         }

         boolean var6 = false;

         try {
            var6 = true;
            if (this._serializationType != null) {
               var2.findTypedValueSerializer((JavaType)this._serializationType, true, (BeanProperty)null).serialize(this._value, var1, var2);
               var6 = false;
            } else {
               var2.findTypedValueSerializer((Class)this._value.getClass(), true, (BeanProperty)null).serialize(this._value, var1, var2);
               var6 = false;
            }
         } finally {
            if (var6) {
               if (var3) {
                  var1.setCharacterEscapes((CharacterEscapes)null);
               }

            }
         }

         if (var3) {
            var1.setCharacterEscapes((CharacterEscapes)null);
         }
      }

      var1.writeRaw(')');
   }

   public String getFunction() {
      return this._function;
   }

   public Object getValue() {
      return this._value;
   }

   public JavaType getSerializationType() {
      return this._serializationType;
   }
}
