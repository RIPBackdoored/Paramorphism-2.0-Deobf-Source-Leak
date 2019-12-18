package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.std.AsArraySerializerBase;
import java.io.IOException;
import java.util.Iterator;

@JacksonStdImpl
public class IteratorSerializer extends AsArraySerializerBase {
   public IteratorSerializer(JavaType var1, boolean var2, TypeSerializer var3) {
      super(Iterator.class, var1, var2, var3, (JsonSerializer)null);
   }

   public IteratorSerializer(IteratorSerializer var1, BeanProperty var2, TypeSerializer var3, JsonSerializer var4, Boolean var5) {
      super(var1, var2, var3, var4, var5);
   }

   public boolean isEmpty(SerializerProvider var1, Iterator var2) {
      return !var2.hasNext();
   }

   public boolean hasSingleElement(Iterator var1) {
      return false;
   }

   public ContainerSerializer _withValueTypeSerializer(TypeSerializer var1) {
      return new IteratorSerializer(this, this._property, var1, this._elementSerializer, this._unwrapSingle);
   }

   public IteratorSerializer withResolved(BeanProperty var1, TypeSerializer var2, JsonSerializer var3, Boolean var4) {
      return new IteratorSerializer(this, var1, var2, var3, var4);
   }

   public final void serialize(Iterator var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      var2.writeStartArray();
      this.serializeContents(var1, var2, var3);
      var2.writeEndArray();
   }

   public void serializeContents(Iterator var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      if (var1.hasNext()) {
         JsonSerializer var4 = this._elementSerializer;
         if (var4 == null) {
            this._serializeDynamicContents(var1, var2, var3);
         } else {
            TypeSerializer var5 = this._valueTypeSerializer;

            do {
               Object var6 = var1.next();
               if (var6 == null) {
                  var3.defaultSerializeNull(var2);
               } else if (var5 == null) {
                  var4.serialize(var6, var2, var3);
               } else {
                  var4.serializeWithType(var6, var2, var3, var5);
               }
            } while(var1.hasNext());

         }
      }
   }

   protected void _serializeDynamicContents(Iterator var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      TypeSerializer var4 = this._valueTypeSerializer;
      PropertySerializerMap var5 = this._dynamicSerializers;

      do {
         Object var6 = var1.next();
         if (var6 == null) {
            var3.defaultSerializeNull(var2);
         } else {
            Class var7 = var6.getClass();
            JsonSerializer var8 = var5.serializerFor(var7);
            if (var8 == null) {
               if (this._elementType.hasGenericTypes()) {
                  var8 = this._findAndAddDynamic(var5, var3.constructSpecializedType(this._elementType, var7), var3);
               } else {
                  var8 = this._findAndAddDynamic(var5, var7, var3);
               }

               var5 = this._dynamicSerializers;
            }

            if (var4 == null) {
               var8.serialize(var6, var2, var3);
            } else {
               var8.serializeWithType(var6, var2, var3, var4);
            }
         }
      } while(var1.hasNext());

   }

   public void serializeContents(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      this.serializeContents((Iterator)var1, var2, var3);
   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      this.serialize((Iterator)var1, var2, var3);
   }

   public AsArraySerializerBase withResolved(BeanProperty var1, TypeSerializer var2, JsonSerializer var3, Boolean var4) {
      return this.withResolved(var1, var2, var3, var4);
   }

   public boolean hasSingleElement(Object var1) {
      return this.hasSingleElement((Iterator)var1);
   }

   public boolean isEmpty(SerializerProvider var1, Object var2) {
      return this.isEmpty(var1, (Iterator)var2);
   }
}
