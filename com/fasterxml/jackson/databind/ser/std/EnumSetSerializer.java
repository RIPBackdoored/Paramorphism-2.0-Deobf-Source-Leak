package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Iterator;

public class EnumSetSerializer extends AsArraySerializerBase {
   public EnumSetSerializer(JavaType var1) {
      super(EnumSet.class, var1, true, (TypeSerializer)null, (JsonSerializer)null);
   }

   public EnumSetSerializer(EnumSetSerializer var1, BeanProperty var2, TypeSerializer var3, JsonSerializer var4, Boolean var5) {
      super(var1, var2, var3, var4, var5);
   }

   public EnumSetSerializer _withValueTypeSerializer(TypeSerializer var1) {
      return this;
   }

   public EnumSetSerializer withResolved(BeanProperty var1, TypeSerializer var2, JsonSerializer var3, Boolean var4) {
      return new EnumSetSerializer(this, var1, var2, var3, var4);
   }

   public boolean isEmpty(SerializerProvider var1, EnumSet var2) {
      return var2.isEmpty();
   }

   public boolean hasSingleElement(EnumSet var1) {
      return var1.size() == 1;
   }

   public final void serialize(EnumSet var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      int var4 = var1.size();
      if (var4 != 1 || (this._unwrapSingle != null || !var3.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)) && this._unwrapSingle != Boolean.TRUE) {
         var2.writeStartArray(var4);
         this.serializeContents(var1, var2, var3);
         var2.writeEndArray();
      } else {
         this.serializeContents(var1, var2, var3);
      }
   }

   public void serializeContents(EnumSet var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      JsonSerializer var4 = this._elementSerializer;

      Enum var6;
      for(Iterator var5 = var1.iterator(); var5.hasNext(); var4.serialize(var6, var2, var3)) {
         var6 = (Enum)var5.next();
         if (var4 == null) {
            var4 = var3.findValueSerializer(var6.getDeclaringClass(), this._property);
         }
      }

   }

   public void serializeContents(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      this.serializeContents((EnumSet)var1, var2, var3);
   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      this.serialize((EnumSet)var1, var2, var3);
   }

   public AsArraySerializerBase withResolved(BeanProperty var1, TypeSerializer var2, JsonSerializer var3, Boolean var4) {
      return this.withResolved(var1, var2, var3, var4);
   }

   public ContainerSerializer _withValueTypeSerializer(TypeSerializer var1) {
      return this._withValueTypeSerializer(var1);
   }

   public boolean hasSingleElement(Object var1) {
      return this.hasSingleElement((EnumSet)var1);
   }

   public boolean isEmpty(SerializerProvider var1, Object var2) {
      return this.isEmpty(var1, (EnumSet)var2);
   }
}
