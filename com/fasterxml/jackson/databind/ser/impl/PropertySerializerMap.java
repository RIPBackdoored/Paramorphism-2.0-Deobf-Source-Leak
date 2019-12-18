package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public abstract class PropertySerializerMap {
   protected final boolean _resetWhenFull;

   protected PropertySerializerMap(boolean var1) {
      super();
      this._resetWhenFull = var1;
   }

   protected PropertySerializerMap(PropertySerializerMap var1) {
      super();
      this._resetWhenFull = var1._resetWhenFull;
   }

   public abstract JsonSerializer serializerFor(Class var1);

   public final PropertySerializerMap$SerializerAndMapResult findAndAddPrimarySerializer(Class var1, SerializerProvider var2, BeanProperty var3) throws JsonMappingException {
      JsonSerializer var4 = var2.findPrimaryPropertySerializer(var1, var3);
      return new PropertySerializerMap$SerializerAndMapResult(var4, this.newWith(var1, var4));
   }

   public final PropertySerializerMap$SerializerAndMapResult findAndAddPrimarySerializer(JavaType var1, SerializerProvider var2, BeanProperty var3) throws JsonMappingException {
      JsonSerializer var4 = var2.findPrimaryPropertySerializer(var1, var3);
      return new PropertySerializerMap$SerializerAndMapResult(var4, this.newWith(var1.getRawClass(), var4));
   }

   public final PropertySerializerMap$SerializerAndMapResult findAndAddSecondarySerializer(Class var1, SerializerProvider var2, BeanProperty var3) throws JsonMappingException {
      JsonSerializer var4 = var2.findValueSerializer(var1, var3);
      return new PropertySerializerMap$SerializerAndMapResult(var4, this.newWith(var1, var4));
   }

   public final PropertySerializerMap$SerializerAndMapResult findAndAddSecondarySerializer(JavaType var1, SerializerProvider var2, BeanProperty var3) throws JsonMappingException {
      JsonSerializer var4 = var2.findValueSerializer(var1, var3);
      return new PropertySerializerMap$SerializerAndMapResult(var4, this.newWith(var1.getRawClass(), var4));
   }

   public final PropertySerializerMap$SerializerAndMapResult findAndAddRootValueSerializer(Class var1, SerializerProvider var2) throws JsonMappingException {
      JsonSerializer var3 = var2.findTypedValueSerializer((Class)var1, false, (BeanProperty)null);
      return new PropertySerializerMap$SerializerAndMapResult(var3, this.newWith(var1, var3));
   }

   public final PropertySerializerMap$SerializerAndMapResult findAndAddRootValueSerializer(JavaType var1, SerializerProvider var2) throws JsonMappingException {
      JsonSerializer var3 = var2.findTypedValueSerializer((JavaType)var1, false, (BeanProperty)null);
      return new PropertySerializerMap$SerializerAndMapResult(var3, this.newWith(var1.getRawClass(), var3));
   }

   public final PropertySerializerMap$SerializerAndMapResult findAndAddKeySerializer(Class var1, SerializerProvider var2, BeanProperty var3) throws JsonMappingException {
      JsonSerializer var4 = var2.findKeySerializer(var1, var3);
      return new PropertySerializerMap$SerializerAndMapResult(var4, this.newWith(var1, var4));
   }

   public final PropertySerializerMap$SerializerAndMapResult addSerializer(Class var1, JsonSerializer var2) {
      return new PropertySerializerMap$SerializerAndMapResult(var2, this.newWith(var1, var2));
   }

   public final PropertySerializerMap$SerializerAndMapResult addSerializer(JavaType var1, JsonSerializer var2) {
      return new PropertySerializerMap$SerializerAndMapResult(var2, this.newWith(var1.getRawClass(), var2));
   }

   public abstract PropertySerializerMap newWith(Class var1, JsonSerializer var2);

   /** @deprecated */
   @Deprecated
   public static PropertySerializerMap emptyMap() {
      return emptyForProperties();
   }

   public static PropertySerializerMap emptyForProperties() {
      return PropertySerializerMap$Empty.FOR_PROPERTIES;
   }

   public static PropertySerializerMap emptyForRootValues() {
      return PropertySerializerMap$Empty.FOR_ROOT_VALUES;
   }
}
