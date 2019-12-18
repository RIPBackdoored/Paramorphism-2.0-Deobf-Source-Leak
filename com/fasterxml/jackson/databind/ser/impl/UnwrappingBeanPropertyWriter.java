package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map.Entry;

public class UnwrappingBeanPropertyWriter extends BeanPropertyWriter implements Serializable {
   private static final long serialVersionUID = 1L;
   protected final NameTransformer _nameTransformer;

   public UnwrappingBeanPropertyWriter(BeanPropertyWriter var1, NameTransformer var2) {
      super(var1);
      this._nameTransformer = var2;
   }

   protected UnwrappingBeanPropertyWriter(UnwrappingBeanPropertyWriter var1, NameTransformer var2, SerializedString var3) {
      super(var1, (SerializedString)var3);
      this._nameTransformer = var2;
   }

   public UnwrappingBeanPropertyWriter rename(NameTransformer var1) {
      String var2 = this._name.getValue();
      String var3 = var1.transform(var2);
      var1 = NameTransformer.chainedTransformer(var1, this._nameTransformer);
      return this._new(var1, new SerializedString(var3));
   }

   protected UnwrappingBeanPropertyWriter _new(NameTransformer var1, SerializedString var2) {
      return new UnwrappingBeanPropertyWriter(this, var1, var2);
   }

   public boolean isUnwrapping() {
      return true;
   }

   public void serializeAsField(Object var1, JsonGenerator var2, SerializerProvider var3) throws Exception {
      Object var4 = this.get(var1);
      if (var4 != null) {
         JsonSerializer var5 = this._serializer;
         if (var5 == null) {
            Class var6 = var4.getClass();
            PropertySerializerMap var7 = this._dynamicSerializers;
            var5 = var7.serializerFor(var6);
            if (var5 == null) {
               var5 = this._findAndAddDynamic(var7, var6, var3);
            }
         }

         if (this._suppressableValue != null) {
            if (MARKER_FOR_EMPTY == this._suppressableValue) {
               if (var5.isEmpty(var3, var4)) {
                  return;
               }
            } else if (this._suppressableValue.equals(var4)) {
               return;
            }
         }

         if (var4 != var1 || !this._handleSelfReference(var1, var2, var3, var5)) {
            if (!var5.isUnwrappingSerializer()) {
               var2.writeFieldName((SerializableString)this._name);
            }

            if (this._typeSerializer == null) {
               var5.serialize(var4, var2, var3);
            } else {
               var5.serializeWithType(var4, var2, var3, this._typeSerializer);
            }

         }
      }
   }

   public void assignSerializer(JsonSerializer var1) {
      if (var1 != null) {
         NameTransformer var2 = this._nameTransformer;
         if (var1.isUnwrappingSerializer() && var1 instanceof UnwrappingBeanSerializer) {
            var2 = NameTransformer.chainedTransformer(var2, ((UnwrappingBeanSerializer)var1)._nameTransformer);
         }

         var1 = var1.unwrappingSerializer(var2);
      }

      super.assignSerializer(var1);
   }

   public void depositSchemaProperty(JsonObjectFormatVisitor var1, SerializerProvider var2) throws JsonMappingException {
      JsonSerializer var3 = var2.findValueSerializer((JavaType)this.getType(), this).unwrappingSerializer(this._nameTransformer);
      if (var3.isUnwrappingSerializer()) {
         var3.acceptJsonFormatVisitor(new UnwrappingBeanPropertyWriter$1(this, var2, var1), this.getType());
      } else {
         super.depositSchemaProperty(var1, var2);
      }

   }

   protected void _depositSchemaProperty(ObjectNode var1, JsonNode var2) {
      JsonNode var3 = var2.get("properties");
      Entry var5;
      String var6;
      if (var3 != null) {
         for(Iterator var4 = var3.fields(); var4.hasNext(); var1.set(var6, (JsonNode)var5.getValue())) {
            var5 = (Entry)var4.next();
            var6 = (String)var5.getKey();
            if (this._nameTransformer != null) {
               var6 = this._nameTransformer.transform(var6);
            }
         }
      }

   }

   protected JsonSerializer _findAndAddDynamic(PropertySerializerMap var1, Class var2, SerializerProvider var3) throws JsonMappingException {
      JsonSerializer var4;
      if (this._nonTrivialBaseType != null) {
         JavaType var5 = var3.constructSpecializedType(this._nonTrivialBaseType, var2);
         var4 = var3.findValueSerializer((JavaType)var5, this);
      } else {
         var4 = var3.findValueSerializer((Class)var2, this);
      }

      NameTransformer var6 = this._nameTransformer;
      if (var4.isUnwrappingSerializer()) {
         var6 = NameTransformer.chainedTransformer(var6, ((UnwrappingBeanSerializer)var4)._nameTransformer);
      }

      var4 = var4.unwrappingSerializer(var6);
      this._dynamicSerializers = this._dynamicSerializers.newWith(var2, var4);
      return var4;
   }

   public BeanPropertyWriter rename(NameTransformer var1) {
      return this.rename(var1);
   }
}
