package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.databind.type.MapType;
import java.util.List;

public abstract class BeanSerializerModifier {
   public BeanSerializerModifier() {
      super();
   }

   public List changeProperties(SerializationConfig var1, BeanDescription var2, List var3) {
      return var3;
   }

   public List orderProperties(SerializationConfig var1, BeanDescription var2, List var3) {
      return var3;
   }

   public BeanSerializerBuilder updateBuilder(SerializationConfig var1, BeanDescription var2, BeanSerializerBuilder var3) {
      return var3;
   }

   public JsonSerializer modifySerializer(SerializationConfig var1, BeanDescription var2, JsonSerializer var3) {
      return var3;
   }

   public JsonSerializer modifyArraySerializer(SerializationConfig var1, ArrayType var2, BeanDescription var3, JsonSerializer var4) {
      return var4;
   }

   public JsonSerializer modifyCollectionSerializer(SerializationConfig var1, CollectionType var2, BeanDescription var3, JsonSerializer var4) {
      return var4;
   }

   public JsonSerializer modifyCollectionLikeSerializer(SerializationConfig var1, CollectionLikeType var2, BeanDescription var3, JsonSerializer var4) {
      return var4;
   }

   public JsonSerializer modifyMapSerializer(SerializationConfig var1, MapType var2, BeanDescription var3, JsonSerializer var4) {
      return var4;
   }

   public JsonSerializer modifyMapLikeSerializer(SerializationConfig var1, MapLikeType var2, BeanDescription var3, JsonSerializer var4) {
      return var4;
   }

   public JsonSerializer modifyEnumSerializer(SerializationConfig var1, JavaType var2, BeanDescription var3, JsonSerializer var4) {
      return var4;
   }

   public JsonSerializer modifyKeySerializer(SerializationConfig var1, JavaType var2, BeanDescription var3, JsonSerializer var4) {
      return var4;
   }
}
