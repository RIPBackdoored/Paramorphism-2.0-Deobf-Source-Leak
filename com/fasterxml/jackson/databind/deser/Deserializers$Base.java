package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.ReferenceType;

public class Deserializers$Base implements Deserializers {
   public Deserializers$Base() {
      super();
   }

   public JsonDeserializer findEnumDeserializer(Class var1, DeserializationConfig var2, BeanDescription var3) throws JsonMappingException {
      return null;
   }

   public JsonDeserializer findTreeNodeDeserializer(Class var1, DeserializationConfig var2, BeanDescription var3) throws JsonMappingException {
      return null;
   }

   public JsonDeserializer findReferenceDeserializer(ReferenceType var1, DeserializationConfig var2, BeanDescription var3, TypeDeserializer var4, JsonDeserializer var5) throws JsonMappingException {
      return this.findBeanDeserializer(var1, var2, var3);
   }

   public JsonDeserializer findBeanDeserializer(JavaType var1, DeserializationConfig var2, BeanDescription var3) throws JsonMappingException {
      return null;
   }

   public JsonDeserializer findArrayDeserializer(ArrayType var1, DeserializationConfig var2, BeanDescription var3, TypeDeserializer var4, JsonDeserializer var5) throws JsonMappingException {
      return null;
   }

   public JsonDeserializer findCollectionDeserializer(CollectionType var1, DeserializationConfig var2, BeanDescription var3, TypeDeserializer var4, JsonDeserializer var5) throws JsonMappingException {
      return null;
   }

   public JsonDeserializer findCollectionLikeDeserializer(CollectionLikeType var1, DeserializationConfig var2, BeanDescription var3, TypeDeserializer var4, JsonDeserializer var5) throws JsonMappingException {
      return null;
   }

   public JsonDeserializer findMapDeserializer(MapType var1, DeserializationConfig var2, BeanDescription var3, KeyDeserializer var4, TypeDeserializer var5, JsonDeserializer var6) throws JsonMappingException {
      return null;
   }

   public JsonDeserializer findMapLikeDeserializer(MapLikeType var1, DeserializationConfig var2, BeanDescription var3, KeyDeserializer var4, TypeDeserializer var5, JsonDeserializer var6) throws JsonMappingException {
      return null;
   }
}
