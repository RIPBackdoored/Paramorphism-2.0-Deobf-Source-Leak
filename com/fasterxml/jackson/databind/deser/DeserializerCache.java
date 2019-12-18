package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.annotation.JsonFormat$Shape;
import com.fasterxml.jackson.annotation.JsonFormat$Value;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonDeserializer$None;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDelegatingDeserializer;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.ReferenceType;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.Converter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public final class DeserializerCache implements Serializable {
   private static final long serialVersionUID = 1L;
   protected final ConcurrentHashMap _cachedDeserializers = new ConcurrentHashMap(64, 0.75F, 4);
   protected final HashMap _incompleteDeserializers = new HashMap(8);

   public DeserializerCache() {
      super();
   }

   Object writeReplace() {
      this._incompleteDeserializers.clear();
      return this;
   }

   public int cachedDeserializersCount() {
      return this._cachedDeserializers.size();
   }

   public void flushCachedDeserializers() {
      this._cachedDeserializers.clear();
   }

   public JsonDeserializer findValueDeserializer(DeserializationContext var1, DeserializerFactory var2, JavaType var3) throws JsonMappingException {
      JsonDeserializer var4 = this._findCachedDeserializer(var3);
      if (var4 == null) {
         var4 = this._createAndCacheValueDeserializer(var1, var2, var3);
         if (var4 == null) {
            var4 = this._handleUnknownValueDeserializer(var1, var3);
         }
      }

      return var4;
   }

   public KeyDeserializer findKeyDeserializer(DeserializationContext var1, DeserializerFactory var2, JavaType var3) throws JsonMappingException {
      KeyDeserializer var4 = var2.createKeyDeserializer(var1, var3);
      if (var4 == null) {
         return this._handleUnknownKeyDeserializer(var1, var3);
      } else {
         if (var4 instanceof ResolvableDeserializer) {
            ((ResolvableDeserializer)var4).resolve(var1);
         }

         return var4;
      }
   }

   public boolean hasValueDeserializerFor(DeserializationContext var1, DeserializerFactory var2, JavaType var3) throws JsonMappingException {
      JsonDeserializer var4 = this._findCachedDeserializer(var3);
      if (var4 == null) {
         var4 = this._createAndCacheValueDeserializer(var1, var2, var3);
      }

      return var4 != null;
   }

   protected JsonDeserializer _findCachedDeserializer(JavaType var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("Null JavaType passed");
      } else {
         return this._hasCustomHandlers(var1) ? null : (JsonDeserializer)this._cachedDeserializers.get(var1);
      }
   }

   protected JsonDeserializer _createAndCacheValueDeserializer(DeserializationContext var1, DeserializerFactory var2, JavaType var3) throws JsonMappingException {
      JsonDeserializer var7;
      synchronized(this._incompleteDeserializers) {
         JsonDeserializer var5 = this._findCachedDeserializer(var3);
         JsonDeserializer var10000;
         if (var5 != null) {
            var10000 = var5;
            return var10000;
         }

         int var6 = this._incompleteDeserializers.size();
         if (var6 > 0) {
            var5 = (JsonDeserializer)this._incompleteDeserializers.get(var3);
            if (var5 != null) {
               var10000 = var5;
               return var10000;
            }
         }

         boolean var12 = false;

         try {
            var12 = true;
            var7 = this._createAndCache2(var1, var2, var3);
            var12 = false;
         } finally {
            if (var12) {
               if (var6 == 0 && this._incompleteDeserializers.size() > 0) {
                  this._incompleteDeserializers.clear();
               }

            }
         }

         if (var6 == 0 && this._incompleteDeserializers.size() > 0) {
            this._incompleteDeserializers.clear();
         }
      }

      return var7;
   }

   protected JsonDeserializer _createAndCache2(DeserializationContext var1, DeserializerFactory var2, JavaType var3) throws JsonMappingException {
      JsonDeserializer var4;
      try {
         var4 = this._createDeserializer(var1, var2, var3);
      } catch (IllegalArgumentException var6) {
         throw JsonMappingException.from((DeserializationContext)var1, var6.getMessage(), var6);
      }

      if (var4 == null) {
         return null;
      } else {
         boolean var5 = !this._hasCustomHandlers(var3) && var4.isCachable();
         if (var4 instanceof ResolvableDeserializer) {
            this._incompleteDeserializers.put(var3, var4);
            ((ResolvableDeserializer)var4).resolve(var1);
            this._incompleteDeserializers.remove(var3);
         }

         if (var5) {
            this._cachedDeserializers.put(var3, var4);
         }

         return var4;
      }
   }

   protected JsonDeserializer _createDeserializer(DeserializationContext var1, DeserializerFactory var2, JavaType var3) throws JsonMappingException {
      DeserializationConfig var4 = var1.getConfig();
      if (var3.isAbstract() || var3.isMapLikeType() || var3.isCollectionLikeType()) {
         var3 = var2.mapAbstractType(var4, var3);
      }

      BeanDescription var5 = var4.introspect(var3);
      JsonDeserializer var6 = this.findDeserializerFromAnnotation(var1, var5.getClassInfo());
      if (var6 != null) {
         return var6;
      } else {
         JavaType var7 = this.modifyTypeByAnnotation(var1, var5.getClassInfo(), var3);
         if (var7 != var3) {
            var3 = var7;
            var5 = var4.introspect(var7);
         }

         Class var8 = var5.findPOJOBuilder();
         if (var8 != null) {
            return var2.createBuilderBasedDeserializer(var1, var3, var5, var8);
         } else {
            Converter var9 = var5.findDeserializationConverter();
            if (var9 == null) {
               return this._createDeserializer2(var1, var2, var3, var5);
            } else {
               JavaType var10 = var9.getInputType(var1.getTypeFactory());
               if (!var10.hasRawClass(var3.getRawClass())) {
                  var5 = var4.introspect(var10);
               }

               return new StdDelegatingDeserializer(var9, var10, this._createDeserializer2(var1, var2, var10, var5));
            }
         }
      }
   }

   protected JsonDeserializer _createDeserializer2(DeserializationContext var1, DeserializerFactory var2, JavaType var3, BeanDescription var4) throws JsonMappingException {
      DeserializationConfig var5 = var1.getConfig();
      if (var3.isEnumType()) {
         return var2.createEnumDeserializer(var1, var3, var4);
      } else {
         if (var3.isContainerType()) {
            if (var3.isArrayType()) {
               return var2.createArrayDeserializer(var1, (ArrayType)var3, var4);
            }

            JsonFormat$Value var6;
            if (var3.isMapLikeType()) {
               var6 = var4.findExpectedFormat((JsonFormat$Value)null);
               if (var6 == null || var6.getShape() != JsonFormat$Shape.OBJECT) {
                  MapLikeType var8 = (MapLikeType)var3;
                  if (var8.isTrueMapType()) {
                     return var2.createMapDeserializer(var1, (MapType)var8, var4);
                  }

                  return var2.createMapLikeDeserializer(var1, var8, var4);
               }
            }

            if (var3.isCollectionLikeType()) {
               var6 = var4.findExpectedFormat((JsonFormat$Value)null);
               if (var6 == null || var6.getShape() != JsonFormat$Shape.OBJECT) {
                  CollectionLikeType var7 = (CollectionLikeType)var3;
                  if (var7.isTrueCollectionType()) {
                     return var2.createCollectionDeserializer(var1, (CollectionType)var7, var4);
                  }

                  return var2.createCollectionLikeDeserializer(var1, var7, var4);
               }
            }
         }

         if (var3.isReferenceType()) {
            return var2.createReferenceDeserializer(var1, (ReferenceType)var3, var4);
         } else {
            return JsonNode.class.isAssignableFrom(var3.getRawClass()) ? var2.createTreeDeserializer(var5, var3, var4) : var2.createBeanDeserializer(var1, var3, var4);
         }
      }
   }

   protected JsonDeserializer findDeserializerFromAnnotation(DeserializationContext var1, Annotated var2) throws JsonMappingException {
      Object var3 = var1.getAnnotationIntrospector().findDeserializer(var2);
      if (var3 == null) {
         return null;
      } else {
         JsonDeserializer var4 = var1.deserializerInstance(var2, var3);
         return this.findConvertingDeserializer(var1, var2, var4);
      }
   }

   protected JsonDeserializer findConvertingDeserializer(DeserializationContext var1, Annotated var2, JsonDeserializer var3) throws JsonMappingException {
      Converter var4 = this.findConverter(var1, var2);
      if (var4 == null) {
         return var3;
      } else {
         JavaType var5 = var4.getInputType(var1.getTypeFactory());
         return new StdDelegatingDeserializer(var4, var5, var3);
      }
   }

   protected Converter findConverter(DeserializationContext var1, Annotated var2) throws JsonMappingException {
      Object var3 = var1.getAnnotationIntrospector().findDeserializationConverter(var2);
      return var3 == null ? null : var1.converterInstance(var2, var3);
   }

   private JavaType modifyTypeByAnnotation(DeserializationContext var1, Annotated var2, JavaType var3) throws JsonMappingException {
      AnnotationIntrospector var4 = var1.getAnnotationIntrospector();
      if (var4 == null) {
         return (JavaType)var3;
      } else {
         JavaType var5;
         Object var6;
         if (((JavaType)var3).isMapLikeType()) {
            var5 = ((JavaType)var3).getKeyType();
            if (var5 != null && var5.getValueHandler() == null) {
               var6 = var4.findKeyDeserializer(var2);
               if (var6 != null) {
                  KeyDeserializer var7 = var1.keyDeserializerInstance(var2, var6);
                  if (var7 != null) {
                     var3 = ((MapLikeType)var3).withKeyValueHandler(var7);
                     var5 = ((JavaType)var3).getKeyType();
                  }
               }
            }
         }

         var5 = ((JavaType)var3).getContentType();
         if (var5 != null && var5.getValueHandler() == null) {
            var6 = var4.findContentDeserializer(var2);
            if (var6 != null) {
               JsonDeserializer var11 = null;
               if (var6 instanceof JsonDeserializer) {
                  JsonDeserializer var10 = (JsonDeserializer)var6;
               } else {
                  Class var8 = this._verifyAsClass(var6, "findContentDeserializer", JsonDeserializer$None.class);
                  if (var8 != null) {
                     var11 = var1.deserializerInstance(var2, var8);
                  }
               }

               if (var11 != null) {
                  var3 = ((JavaType)var3).withContentValueHandler(var11);
               }
            }
         }

         JavaType var9 = var4.refineDeserializationType(var1.getConfig(), var2, (JavaType)var3);
         return var9;
      }
   }

   private boolean _hasCustomHandlers(JavaType var1) {
      if (var1.isContainerType()) {
         JavaType var2 = var1.getContentType();
         if (var2 != null && (var2.getValueHandler() != null || var2.getTypeHandler() != null)) {
            return true;
         }

         if (var1.isMapLikeType()) {
            JavaType var3 = var1.getKeyType();
            if (var3.getValueHandler() != null) {
               return true;
            }
         }
      }

      return false;
   }

   private Class _verifyAsClass(Object var1, String var2, Class var3) {
      if (var1 == null) {
         return null;
      } else if (!(var1 instanceof Class)) {
         throw new IllegalStateException("AnnotationIntrospector." + var2 + "() returned value of type " + var1.getClass().getName() + ": expected type JsonSerializer or Class<JsonSerializer> instead");
      } else {
         Class var4 = (Class)var1;
         return var4 != var3 && !ClassUtil.isBogusClass(var4) ? var4 : null;
      }
   }

   protected JsonDeserializer _handleUnknownValueDeserializer(DeserializationContext var1, JavaType var2) throws JsonMappingException {
      Class var3 = var2.getRawClass();
      return !ClassUtil.isConcrete(var3) ? (JsonDeserializer)var1.reportBadDefinition(var2, "Cannot find a Value deserializer for abstract type " + var2) : (JsonDeserializer)var1.reportBadDefinition(var2, "Cannot find a Value deserializer for type " + var2);
   }

   protected KeyDeserializer _handleUnknownKeyDeserializer(DeserializationContext var1, JavaType var2) throws JsonMappingException {
      return (KeyDeserializer)var1.reportBadDefinition(var2, "Cannot find a (Map) Key deserializer for type " + var2);
   }
}
