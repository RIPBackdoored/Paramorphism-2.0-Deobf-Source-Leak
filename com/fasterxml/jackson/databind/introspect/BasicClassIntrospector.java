package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder$Value;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.LRUMap;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public class BasicClassIntrospector extends ClassIntrospector implements Serializable {
   private static final long serialVersionUID = 1L;
   protected static final BasicBeanDescription STRING_DESC = BasicBeanDescription.forOtherUse((MapperConfig)null, SimpleType.constructUnsafe(String.class), AnnotatedClassResolver.createPrimordial(String.class));
   protected static final BasicBeanDescription BOOLEAN_DESC;
   protected static final BasicBeanDescription INT_DESC;
   protected static final BasicBeanDescription LONG_DESC;
   protected final LRUMap _cachedFCA = new LRUMap(16, 64);

   public BasicClassIntrospector() {
      super();
   }

   public ClassIntrospector copy() {
      return new BasicClassIntrospector();
   }

   public BasicBeanDescription forSerialization(SerializationConfig var1, JavaType var2, ClassIntrospector$MixInResolver var3) {
      BasicBeanDescription var4 = this._findStdTypeDesc(var2);
      if (var4 == null) {
         var4 = this._findStdJdkCollectionDesc(var1, var2);
         if (var4 == null) {
            var4 = BasicBeanDescription.forSerialization(this.collectProperties(var1, var2, var3, true, "set"));
         }

         this._cachedFCA.putIfAbsent(var2, var4);
      }

      return var4;
   }

   public BasicBeanDescription forDeserialization(DeserializationConfig var1, JavaType var2, ClassIntrospector$MixInResolver var3) {
      BasicBeanDescription var4 = this._findStdTypeDesc(var2);
      if (var4 == null) {
         var4 = this._findStdJdkCollectionDesc(var1, var2);
         if (var4 == null) {
            var4 = BasicBeanDescription.forDeserialization(this.collectProperties(var1, var2, var3, false, "set"));
         }

         this._cachedFCA.putIfAbsent(var2, var4);
      }

      return var4;
   }

   public BasicBeanDescription forDeserializationWithBuilder(DeserializationConfig var1, JavaType var2, ClassIntrospector$MixInResolver var3) {
      BasicBeanDescription var4 = BasicBeanDescription.forDeserialization(this.collectPropertiesWithBuilder(var1, var2, var3, false));
      this._cachedFCA.putIfAbsent(var2, var4);
      return var4;
   }

   public BasicBeanDescription forCreation(DeserializationConfig var1, JavaType var2, ClassIntrospector$MixInResolver var3) {
      BasicBeanDescription var4 = this._findStdTypeDesc(var2);
      if (var4 == null) {
         var4 = this._findStdJdkCollectionDesc(var1, var2);
         if (var4 == null) {
            var4 = BasicBeanDescription.forDeserialization(this.collectProperties(var1, var2, var3, false, "set"));
         }
      }

      return var4;
   }

   public BasicBeanDescription forClassAnnotations(MapperConfig var1, JavaType var2, ClassIntrospector$MixInResolver var3) {
      BasicBeanDescription var4 = this._findStdTypeDesc(var2);
      if (var4 == null) {
         var4 = (BasicBeanDescription)this._cachedFCA.get(var2);
         if (var4 == null) {
            var4 = BasicBeanDescription.forOtherUse(var1, var2, this._resolveAnnotatedClass(var1, var2, var3));
            this._cachedFCA.put(var2, var4);
         }
      }

      return var4;
   }

   public BasicBeanDescription forDirectClassAnnotations(MapperConfig var1, JavaType var2, ClassIntrospector$MixInResolver var3) {
      BasicBeanDescription var4 = this._findStdTypeDesc(var2);
      if (var4 == null) {
         var4 = BasicBeanDescription.forOtherUse(var1, var2, this._resolveAnnotatedWithoutSuperTypes(var1, var2, var3));
      }

      return var4;
   }

   protected POJOPropertiesCollector collectProperties(MapperConfig var1, JavaType var2, ClassIntrospector$MixInResolver var3, boolean var4, String var5) {
      return this.constructPropertyCollector(var1, this._resolveAnnotatedClass(var1, var2, var3), var2, var4, var5);
   }

   protected POJOPropertiesCollector collectPropertiesWithBuilder(MapperConfig var1, JavaType var2, ClassIntrospector$MixInResolver var3, boolean var4) {
      AnnotatedClass var5 = this._resolveAnnotatedClass(var1, var2, var3);
      AnnotationIntrospector var6 = var1.isAnnotationProcessingEnabled() ? var1.getAnnotationIntrospector() : null;
      JsonPOJOBuilder$Value var7 = var6 == null ? null : var6.findPOJOBuilderConfig(var5);
      String var8 = var7 == null ? "with" : var7.withPrefix;
      return this.constructPropertyCollector(var1, var5, var2, var4, var8);
   }

   protected POJOPropertiesCollector constructPropertyCollector(MapperConfig var1, AnnotatedClass var2, JavaType var3, boolean var4, String var5) {
      return new POJOPropertiesCollector(var1, var4, var3, var2, var5);
   }

   protected BasicBeanDescription _findStdTypeDesc(JavaType var1) {
      Class var2 = var1.getRawClass();
      if (var2.isPrimitive()) {
         if (var2 == Boolean.TYPE) {
            return BOOLEAN_DESC;
         }

         if (var2 == Integer.TYPE) {
            return INT_DESC;
         }

         if (var2 == Long.TYPE) {
            return LONG_DESC;
         }
      } else if (var2 == String.class) {
         return STRING_DESC;
      }

      return null;
   }

   protected boolean _isStdJDKCollection(JavaType var1) {
      if (var1.isContainerType() && !var1.isArrayType()) {
         Class var2 = var1.getRawClass();
         String var3 = ClassUtil.getPackageName(var2);
         return var3 != null && (var3.startsWith("java.lang") || var3.startsWith("java.util")) && (Collection.class.isAssignableFrom(var2) || Map.class.isAssignableFrom(var2));
      } else {
         return false;
      }
   }

   protected BasicBeanDescription _findStdJdkCollectionDesc(MapperConfig var1, JavaType var2) {
      return this._isStdJDKCollection(var2) ? BasicBeanDescription.forOtherUse(var1, var2, this._resolveAnnotatedClass(var1, var2, var1)) : null;
   }

   protected AnnotatedClass _resolveAnnotatedClass(MapperConfig var1, JavaType var2, ClassIntrospector$MixInResolver var3) {
      return AnnotatedClassResolver.resolve(var1, var2, var3);
   }

   protected AnnotatedClass _resolveAnnotatedWithoutSuperTypes(MapperConfig var1, JavaType var2, ClassIntrospector$MixInResolver var3) {
      return AnnotatedClassResolver.resolveWithoutSuperTypes(var1, var2, var3);
   }

   public BeanDescription forDirectClassAnnotations(MapperConfig var1, JavaType var2, ClassIntrospector$MixInResolver var3) {
      return this.forDirectClassAnnotations(var1, var2, var3);
   }

   public BeanDescription forClassAnnotations(MapperConfig var1, JavaType var2, ClassIntrospector$MixInResolver var3) {
      return this.forClassAnnotations(var1, var2, var3);
   }

   public BeanDescription forCreation(DeserializationConfig var1, JavaType var2, ClassIntrospector$MixInResolver var3) {
      return this.forCreation(var1, var2, var3);
   }

   public BeanDescription forDeserializationWithBuilder(DeserializationConfig var1, JavaType var2, ClassIntrospector$MixInResolver var3) {
      return this.forDeserializationWithBuilder(var1, var2, var3);
   }

   public BeanDescription forDeserialization(DeserializationConfig var1, JavaType var2, ClassIntrospector$MixInResolver var3) {
      return this.forDeserialization(var1, var2, var3);
   }

   public BeanDescription forSerialization(SerializationConfig var1, JavaType var2, ClassIntrospector$MixInResolver var3) {
      return this.forSerialization(var1, var2, var3);
   }

   static {
      BOOLEAN_DESC = BasicBeanDescription.forOtherUse((MapperConfig)null, SimpleType.constructUnsafe(Boolean.TYPE), AnnotatedClassResolver.createPrimordial(Boolean.TYPE));
      INT_DESC = BasicBeanDescription.forOtherUse((MapperConfig)null, SimpleType.constructUnsafe(Integer.TYPE), AnnotatedClassResolver.createPrimordial(Integer.TYPE));
      LONG_DESC = BasicBeanDescription.forOtherUse((MapperConfig)null, SimpleType.constructUnsafe(Long.TYPE), AnnotatedClassResolver.createPrimordial(Long.TYPE));
   }
}
