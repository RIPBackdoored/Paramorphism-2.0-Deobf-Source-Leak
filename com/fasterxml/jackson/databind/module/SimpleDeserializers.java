package com.fasterxml.jackson.databind.module;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.ClassKey;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.ReferenceType;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class SimpleDeserializers implements Deserializers, Serializable {
   private static final long serialVersionUID = 1L;
   protected HashMap _classMappings = null;
   protected boolean _hasEnumDeserializer = false;

   public SimpleDeserializers() {
      super();
   }

   public SimpleDeserializers(Map var1) {
      super();
      this.addDeserializers(var1);
   }

   public void addDeserializer(Class var1, JsonDeserializer var2) {
      ClassKey var3 = new ClassKey(var1);
      if (this._classMappings == null) {
         this._classMappings = new HashMap();
      }

      this._classMappings.put(var3, var2);
      if (var1 == Enum.class) {
         this._hasEnumDeserializer = true;
      }

   }

   public void addDeserializers(Map var1) {
      Iterator var2 = var1.entrySet().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         Class var4 = (Class)var3.getKey();
         JsonDeserializer var5 = (JsonDeserializer)var3.getValue();
         this.addDeserializer(var4, var5);
      }

   }

   public JsonDeserializer findArrayDeserializer(ArrayType var1, DeserializationConfig var2, BeanDescription var3, TypeDeserializer var4, JsonDeserializer var5) throws JsonMappingException {
      return this._find(var1);
   }

   public JsonDeserializer findBeanDeserializer(JavaType var1, DeserializationConfig var2, BeanDescription var3) throws JsonMappingException {
      return this._find(var1);
   }

   public JsonDeserializer findCollectionDeserializer(CollectionType var1, DeserializationConfig var2, BeanDescription var3, TypeDeserializer var4, JsonDeserializer var5) throws JsonMappingException {
      return this._find(var1);
   }

   public JsonDeserializer findCollectionLikeDeserializer(CollectionLikeType var1, DeserializationConfig var2, BeanDescription var3, TypeDeserializer var4, JsonDeserializer var5) throws JsonMappingException {
      return this._find(var1);
   }

   public JsonDeserializer findEnumDeserializer(Class var1, DeserializationConfig var2, BeanDescription var3) throws JsonMappingException {
      if (this._classMappings == null) {
         return null;
      } else {
         JsonDeserializer var4 = (JsonDeserializer)this._classMappings.get(new ClassKey(var1));
         if (var4 == null && this._hasEnumDeserializer && var1.isEnum()) {
            var4 = (JsonDeserializer)this._classMappings.get(new ClassKey(Enum.class));
         }

         return var4;
      }
   }

   public JsonDeserializer findTreeNodeDeserializer(Class var1, DeserializationConfig var2, BeanDescription var3) throws JsonMappingException {
      return this._classMappings == null ? null : (JsonDeserializer)this._classMappings.get(new ClassKey(var1));
   }

   public JsonDeserializer findReferenceDeserializer(ReferenceType var1, DeserializationConfig var2, BeanDescription var3, TypeDeserializer var4, JsonDeserializer var5) throws JsonMappingException {
      return this._find(var1);
   }

   public JsonDeserializer findMapDeserializer(MapType var1, DeserializationConfig var2, BeanDescription var3, KeyDeserializer var4, TypeDeserializer var5, JsonDeserializer var6) throws JsonMappingException {
      return this._find(var1);
   }

   public JsonDeserializer findMapLikeDeserializer(MapLikeType var1, DeserializationConfig var2, BeanDescription var3, KeyDeserializer var4, TypeDeserializer var5, JsonDeserializer var6) throws JsonMappingException {
      return this._find(var1);
   }

   private final JsonDeserializer _find(JavaType var1) {
      return this._classMappings == null ? null : (JsonDeserializer)this._classMappings.get(new ClassKey(var1.getRawClass()));
   }
}
