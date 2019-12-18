package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.ReferenceType;
import java.util.List;

public abstract class BeanDeserializerModifier {
   public BeanDeserializerModifier() {
      super();
   }

   public List updateProperties(DeserializationConfig var1, BeanDescription var2, List var3) {
      return var3;
   }

   public BeanDeserializerBuilder updateBuilder(DeserializationConfig var1, BeanDescription var2, BeanDeserializerBuilder var3) {
      return var3;
   }

   public JsonDeserializer modifyDeserializer(DeserializationConfig var1, BeanDescription var2, JsonDeserializer var3) {
      return var3;
   }

   public JsonDeserializer modifyEnumDeserializer(DeserializationConfig var1, JavaType var2, BeanDescription var3, JsonDeserializer var4) {
      return var4;
   }

   public JsonDeserializer modifyReferenceDeserializer(DeserializationConfig var1, ReferenceType var2, BeanDescription var3, JsonDeserializer var4) {
      return var4;
   }

   public JsonDeserializer modifyArrayDeserializer(DeserializationConfig var1, ArrayType var2, BeanDescription var3, JsonDeserializer var4) {
      return var4;
   }

   public JsonDeserializer modifyCollectionDeserializer(DeserializationConfig var1, CollectionType var2, BeanDescription var3, JsonDeserializer var4) {
      return var4;
   }

   public JsonDeserializer modifyCollectionLikeDeserializer(DeserializationConfig var1, CollectionLikeType var2, BeanDescription var3, JsonDeserializer var4) {
      return var4;
   }

   public JsonDeserializer modifyMapDeserializer(DeserializationConfig var1, MapType var2, BeanDescription var3, JsonDeserializer var4) {
      return var4;
   }

   public JsonDeserializer modifyMapLikeDeserializer(DeserializationConfig var1, MapLikeType var2, BeanDescription var3, JsonDeserializer var4) {
      return var4;
   }

   public KeyDeserializer modifyKeyDeserializer(DeserializationConfig var1, JavaType var2, KeyDeserializer var3) {
      return var3;
   }
}
