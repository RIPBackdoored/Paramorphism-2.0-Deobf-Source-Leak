package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.annotation.JsonFormat$Shape;
import com.fasterxml.jackson.annotation.JsonFormat$Value;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties$Value;
import com.fasterxml.jackson.annotation.JsonInclude$Include;
import com.fasterxml.jackson.annotation.JsonInclude$Value;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize$Typing;
import com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig;
import com.fasterxml.jackson.databind.ext.OptionalHandlerFactory;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.BasicBeanDescription;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.impl.IndexedListSerializer;
import com.fasterxml.jackson.databind.ser.impl.IndexedStringListSerializer;
import com.fasterxml.jackson.databind.ser.impl.IteratorSerializer;
import com.fasterxml.jackson.databind.ser.impl.MapEntrySerializer;
import com.fasterxml.jackson.databind.ser.impl.StringArraySerializer;
import com.fasterxml.jackson.databind.ser.impl.StringCollectionSerializer;
import com.fasterxml.jackson.databind.ser.std.BooleanSerializer;
import com.fasterxml.jackson.databind.ser.std.ByteBufferSerializer;
import com.fasterxml.jackson.databind.ser.std.CalendarSerializer;
import com.fasterxml.jackson.databind.ser.std.CollectionSerializer;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.databind.ser.std.EnumSerializer;
import com.fasterxml.jackson.databind.ser.std.EnumSetSerializer;
import com.fasterxml.jackson.databind.ser.std.InetAddressSerializer;
import com.fasterxml.jackson.databind.ser.std.InetSocketAddressSerializer;
import com.fasterxml.jackson.databind.ser.std.IterableSerializer;
import com.fasterxml.jackson.databind.ser.std.JsonValueSerializer;
import com.fasterxml.jackson.databind.ser.std.MapSerializer;
import com.fasterxml.jackson.databind.ser.std.NumberSerializer;
import com.fasterxml.jackson.databind.ser.std.NumberSerializers;
import com.fasterxml.jackson.databind.ser.std.ObjectArraySerializer;
import com.fasterxml.jackson.databind.ser.std.SerializableSerializer;
import com.fasterxml.jackson.databind.ser.std.StdArraySerializers;
import com.fasterxml.jackson.databind.ser.std.StdDelegatingSerializer;
import com.fasterxml.jackson.databind.ser.std.StdJdkSerializers;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.fasterxml.jackson.databind.ser.std.TimeZoneSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.ser.std.TokenBufferSerializer;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.ReferenceType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.Converter;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.RandomAccess;
import java.util.Set;
import java.util.TimeZone;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicReference;

public abstract class BasicSerializerFactory extends SerializerFactory implements Serializable {
   protected static final HashMap _concrete;
   protected static final HashMap _concreteLazy;
   protected final SerializerFactoryConfig _factoryConfig;

   protected BasicSerializerFactory(SerializerFactoryConfig var1) {
      super();
      this._factoryConfig = var1 == null ? new SerializerFactoryConfig() : var1;
   }

   public SerializerFactoryConfig getFactoryConfig() {
      return this._factoryConfig;
   }

   public abstract SerializerFactory withConfig(SerializerFactoryConfig var1);

   public final SerializerFactory withAdditionalSerializers(Serializers var1) {
      return this.withConfig(this._factoryConfig.withAdditionalSerializers(var1));
   }

   public final SerializerFactory withAdditionalKeySerializers(Serializers var1) {
      return this.withConfig(this._factoryConfig.withAdditionalKeySerializers(var1));
   }

   public final SerializerFactory withSerializerModifier(BeanSerializerModifier var1) {
      return this.withConfig(this._factoryConfig.withSerializerModifier(var1));
   }

   public abstract JsonSerializer createSerializer(SerializerProvider var1, JavaType var2) throws JsonMappingException;

   public JsonSerializer createKeySerializer(SerializationConfig var1, JavaType var2, JsonSerializer var3) {
      BeanDescription var4 = var1.introspectClassAnnotations(var2.getRawClass());
      Object var5 = null;
      Iterator var6;
      if (this._factoryConfig.hasKeySerializers()) {
         var6 = this._factoryConfig.keySerializers().iterator();

         while(var6.hasNext()) {
            Serializers var7 = (Serializers)var6.next();
            var5 = var7.findSerializer(var1, var2, var4);
            if (var5 != null) {
               break;
            }
         }
      }

      if (var5 == null) {
         var5 = var3;
         if (var3 == null) {
            var5 = StdKeySerializers.getStdKeySerializer(var1, var2.getRawClass(), false);
            if (var5 == null) {
               var4 = var1.introspect(var2);
               AnnotatedMember var9 = var4.findJsonValueAccessor();
               if (var9 != null) {
                  Class var10 = var9.getRawType();
                  JsonSerializer var8 = StdKeySerializers.getStdKeySerializer(var1, var10, true);
                  if (var1.canOverrideAccessModifiers()) {
                     ClassUtil.checkAndFixAccess(var9.getMember(), var1.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
                  }

                  var5 = new JsonValueSerializer(var9, var8);
               } else {
                  var5 = StdKeySerializers.getFallbackKeySerializer(var1, var2.getRawClass());
               }
            }
         }
      }

      BeanSerializerModifier var11;
      if (this._factoryConfig.hasSerializerModifiers()) {
         for(var6 = this._factoryConfig.serializerModifiers().iterator(); var6.hasNext(); var5 = var11.modifyKeySerializer(var1, var2, var4, (JsonSerializer)var5)) {
            var11 = (BeanSerializerModifier)var6.next();
         }
      }

      return (JsonSerializer)var5;
   }

   public TypeSerializer createTypeSerializer(SerializationConfig var1, JavaType var2) {
      BeanDescription var3 = var1.introspectClassAnnotations(var2.getRawClass());
      AnnotatedClass var4 = var3.getClassInfo();
      AnnotationIntrospector var5 = var1.getAnnotationIntrospector();
      TypeResolverBuilder var6 = var5.findTypeResolver(var1, var4, var2);
      Collection var7 = null;
      if (var6 == null) {
         var6 = var1.getDefaultTyper(var2);
      } else {
         var7 = var1.getSubtypeResolver().collectAndResolveSubtypesByClass(var1, var4);
      }

      return var6 == null ? null : var6.buildTypeSerializer(var1, var2, var7);
   }

   protected abstract Iterable customSerializers();

   protected final JsonSerializer findSerializerByLookup(JavaType var1, SerializationConfig var2, BeanDescription var3, boolean var4) {
      Class var5 = var1.getRawClass();
      String var6 = var5.getName();
      JsonSerializer var7 = (JsonSerializer)_concrete.get(var6);
      if (var7 == null) {
         Class var8 = (Class)_concreteLazy.get(var6);
         if (var8 != null) {
            return (JsonSerializer)ClassUtil.createInstance(var8, false);
         }
      }

      return var7;
   }

   protected final JsonSerializer findSerializerByAnnotations(SerializerProvider var1, JavaType var2, BeanDescription var3) throws JsonMappingException {
      Class var4 = var2.getRawClass();
      if (JsonSerializable.class.isAssignableFrom(var4)) {
         return SerializableSerializer.instance;
      } else {
         AnnotatedMember var5 = var3.findJsonValueAccessor();
         if (var5 != null) {
            if (var1.canOverrideAccessModifiers()) {
               ClassUtil.checkAndFixAccess(var5.getMember(), var1.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
            }

            JsonSerializer var6 = this.findSerializerFromAnnotation(var1, var5);
            return new JsonValueSerializer(var5, var6);
         } else {
            return null;
         }
      }
   }

   protected final JsonSerializer findSerializerByPrimaryType(SerializerProvider var1, JavaType var2, BeanDescription var3, boolean var4) throws JsonMappingException {
      // $FF: Couldn't be decompiled
   }

   protected JsonSerializer findOptionalStdSerializer(SerializerProvider var1, JavaType var2, BeanDescription var3, boolean var4) throws JsonMappingException {
      return OptionalHandlerFactory.instance.findSerializer(var1.getConfig(), var2, var3);
   }

   protected final JsonSerializer findSerializerByAddonType(SerializationConfig var1, JavaType var2, BeanDescription var3, boolean var4) throws JsonMappingException {
      Class var5 = var2.getRawClass();
      JavaType[] var6;
      JavaType var7;
      if (Iterator.class.isAssignableFrom(var5)) {
         var6 = var1.getTypeFactory().findTypeParameters(var2, Iterator.class);
         var7 = var6 != null && var6.length == 1 ? var6[0] : TypeFactory.unknownType();
         return this.buildIteratorSerializer(var1, var2, var3, var4, var7);
      } else if (!Iterable.class.isAssignableFrom(var5)) {
         return CharSequence.class.isAssignableFrom(var5) ? ToStringSerializer.instance : null;
      } else {
         var6 = var1.getTypeFactory().findTypeParameters(var2, Iterable.class);
         var7 = var6 != null && var6.length == 1 ? var6[0] : TypeFactory.unknownType();
         return this.buildIterableSerializer(var1, var2, var3, var4, var7);
      }
   }

   protected JsonSerializer findSerializerFromAnnotation(SerializerProvider var1, Annotated var2) throws JsonMappingException {
      Object var3 = var1.getAnnotationIntrospector().findSerializer(var2);
      if (var3 == null) {
         return null;
      } else {
         JsonSerializer var4 = var1.serializerInstance(var2, var3);
         return this.findConvertingSerializer(var1, var2, var4);
      }
   }

   protected JsonSerializer findConvertingSerializer(SerializerProvider var1, Annotated var2, JsonSerializer var3) throws JsonMappingException {
      Converter var4 = this.findConverter(var1, var2);
      if (var4 == null) {
         return var3;
      } else {
         JavaType var5 = var4.getOutputType(var1.getTypeFactory());
         return new StdDelegatingSerializer(var4, var5, var3);
      }
   }

   protected Converter findConverter(SerializerProvider var1, Annotated var2) throws JsonMappingException {
      Object var3 = var1.getAnnotationIntrospector().findSerializationConverter(var2);
      return var3 == null ? null : var1.converterInstance(var2, var3);
   }

   protected JsonSerializer buildContainerSerializer(SerializerProvider var1, JavaType var2, BeanDescription var3, boolean var4) throws JsonMappingException {
      SerializationConfig var5 = var1.getConfig();
      if (!var4 && var2.useStaticType() && (!var2.isContainerType() || !var2.getContentType().isJavaLangObject())) {
         var4 = true;
      }

      JavaType var6 = var2.getContentType();
      TypeSerializer var7 = this.createTypeSerializer(var5, var6);
      if (var7 != null) {
         var4 = false;
      }

      JsonSerializer var8 = this._findContentSerializer(var1, var3.getClassInfo());
      JsonSerializer var10;
      if (var2.isMapLikeType()) {
         MapLikeType var15 = (MapLikeType)var2;
         var10 = this._findKeySerializer(var1, var3.getClassInfo());
         if (var15.isTrueMapType()) {
            return this.buildMapSerializer(var1, (MapType)var15, var3, var4, var10, var7, var8);
         } else {
            JsonSerializer var16 = null;
            MapLikeType var17 = (MapLikeType)var2;
            Iterator var19 = this.customSerializers().iterator();

            while(var19.hasNext()) {
               Serializers var14 = (Serializers)var19.next();
               var16 = var14.findMapLikeSerializer(var5, var17, var3, var10, var7, var8);
               if (var16 != null) {
                  break;
               }
            }

            if (var16 == null) {
               var16 = this.findSerializerByAnnotations(var1, var2, var3);
            }

            BeanSerializerModifier var20;
            if (var16 != null && this._factoryConfig.hasSerializerModifiers()) {
               for(var19 = this._factoryConfig.serializerModifiers().iterator(); var19.hasNext(); var16 = var20.modifyMapLikeSerializer(var5, var17, var3, var16)) {
                  var20 = (BeanSerializerModifier)var19.next();
               }
            }

            return var16;
         }
      } else if (!var2.isCollectionLikeType()) {
         return var2.isArrayType() ? this.buildArraySerializer(var1, (ArrayType)var2, var3, var4, var7, var8) : null;
      } else {
         CollectionLikeType var9 = (CollectionLikeType)var2;
         if (var9.isTrueCollectionType()) {
            return this.buildCollectionSerializer(var1, (CollectionType)var9, var3, var4, var7, var8);
         } else {
            var10 = null;
            CollectionLikeType var11 = (CollectionLikeType)var2;
            Iterator var12 = this.customSerializers().iterator();

            while(var12.hasNext()) {
               Serializers var13 = (Serializers)var12.next();
               var10 = var13.findCollectionLikeSerializer(var5, var11, var3, var7, var8);
               if (var10 != null) {
                  break;
               }
            }

            if (var10 == null) {
               var10 = this.findSerializerByAnnotations(var1, var2, var3);
            }

            BeanSerializerModifier var18;
            if (var10 != null && this._factoryConfig.hasSerializerModifiers()) {
               for(var12 = this._factoryConfig.serializerModifiers().iterator(); var12.hasNext(); var10 = var18.modifyCollectionLikeSerializer(var5, var11, var3, var10)) {
                  var18 = (BeanSerializerModifier)var12.next();
               }
            }

            return var10;
         }
      }
   }

   protected JsonSerializer buildCollectionSerializer(SerializerProvider var1, CollectionType var2, BeanDescription var3, boolean var4, TypeSerializer var5, JsonSerializer var6) throws JsonMappingException {
      SerializationConfig var7 = var1.getConfig();
      Object var8 = null;
      Iterator var9 = this.customSerializers().iterator();

      while(var9.hasNext()) {
         Serializers var10 = (Serializers)var9.next();
         var8 = var10.findCollectionSerializer(var7, var2, var3, var5, var6);
         if (var8 != null) {
            break;
         }
      }

      if (var8 == null) {
         var8 = this.findSerializerByAnnotations(var1, var2, var3);
         if (var8 == null) {
            JsonFormat$Value var12 = var3.findExpectedFormat((JsonFormat$Value)null);
            if (var12 != null && var12.getShape() == JsonFormat$Shape.OBJECT) {
               return null;
            }

            Class var13 = var2.getRawClass();
            if (EnumSet.class.isAssignableFrom(var13)) {
               JavaType var11 = var2.getContentType();
               if (!var11.isEnumType()) {
                  var11 = null;
               }

               var8 = this.buildEnumSetSerializer(var11);
            } else {
               Class var15 = var2.getContentType().getRawClass();
               if (this.isIndexedList(var13)) {
                  if (var15 == String.class) {
                     if (ClassUtil.isJacksonStdImpl((Object)var6)) {
                        var8 = IndexedStringListSerializer.instance;
                     }
                  } else {
                     var8 = this.buildIndexedListSerializer(var2.getContentType(), var4, var5, var6);
                  }
               } else if (var15 == String.class && ClassUtil.isJacksonStdImpl((Object)var6)) {
                  var8 = StringCollectionSerializer.instance;
               }

               if (var8 == null) {
                  var8 = this.buildCollectionSerializer(var2.getContentType(), var4, var5, var6);
               }
            }
         }
      }

      BeanSerializerModifier var14;
      if (this._factoryConfig.hasSerializerModifiers()) {
         for(var9 = this._factoryConfig.serializerModifiers().iterator(); var9.hasNext(); var8 = var14.modifyCollectionSerializer(var7, var2, var3, (JsonSerializer)var8)) {
            var14 = (BeanSerializerModifier)var9.next();
         }
      }

      return (JsonSerializer)var8;
   }

   protected boolean isIndexedList(Class var1) {
      return RandomAccess.class.isAssignableFrom(var1);
   }

   public ContainerSerializer buildIndexedListSerializer(JavaType var1, boolean var2, TypeSerializer var3, JsonSerializer var4) {
      return new IndexedListSerializer(var1, var2, var3, var4);
   }

   public ContainerSerializer buildCollectionSerializer(JavaType var1, boolean var2, TypeSerializer var3, JsonSerializer var4) {
      return new CollectionSerializer(var1, var2, var3, var4);
   }

   public JsonSerializer buildEnumSetSerializer(JavaType var1) {
      return new EnumSetSerializer(var1);
   }

   protected JsonSerializer buildMapSerializer(SerializerProvider var1, MapType var2, BeanDescription var3, boolean var4, JsonSerializer var5, TypeSerializer var6, JsonSerializer var7) throws JsonMappingException {
      JsonFormat$Value var8 = var3.findExpectedFormat((JsonFormat$Value)null);
      if (var8 != null && var8.getShape() == JsonFormat$Shape.OBJECT) {
         return null;
      } else {
         Object var9 = null;
         SerializationConfig var10 = var1.getConfig();
         Iterator var11 = this.customSerializers().iterator();

         while(var11.hasNext()) {
            Serializers var12 = (Serializers)var11.next();
            var9 = var12.findMapSerializer(var10, var2, var3, var5, var6, var7);
            if (var9 != null) {
               break;
            }
         }

         if (var9 == null) {
            var9 = this.findSerializerByAnnotations(var1, var2, var3);
            if (var9 == null) {
               Object var15 = this.findFilterId(var10, var3);
               JsonIgnoreProperties$Value var16 = var10.getDefaultPropertyIgnorals(Map.class, var3.getClassInfo());
               Set var13 = var16 == null ? null : var16.findIgnoredForSerialization();
               MapSerializer var14 = MapSerializer.construct((Set)var13, var2, var4, var6, var5, var7, var15);
               var9 = this._checkMapContentInclusion(var1, var3, var14);
            }
         }

         BeanSerializerModifier var17;
         if (this._factoryConfig.hasSerializerModifiers()) {
            for(var11 = this._factoryConfig.serializerModifiers().iterator(); var11.hasNext(); var9 = var17.modifyMapSerializer(var10, var2, var3, (JsonSerializer)var9)) {
               var17 = (BeanSerializerModifier)var11.next();
            }
         }

         return (JsonSerializer)var9;
      }
   }

   protected MapSerializer _checkMapContentInclusion(SerializerProvider var1, BeanDescription var2, MapSerializer var3) throws JsonMappingException {
      // $FF: Couldn't be decompiled
   }

   protected JsonSerializer buildMapEntrySerializer(SerializerProvider var1, JavaType var2, BeanDescription var3, boolean var4, JavaType var5, JavaType var6) throws JsonMappingException {
      // $FF: Couldn't be decompiled
   }

   protected JsonInclude$Value _findInclusionWithContent(SerializerProvider var1, BeanDescription var2, JavaType var3, Class var4) throws JsonMappingException {
      // $FF: Couldn't be decompiled
   }

   protected JsonSerializer buildArraySerializer(SerializerProvider var1, ArrayType var2, BeanDescription var3, boolean var4, TypeSerializer var5, JsonSerializer var6) throws JsonMappingException {
      SerializationConfig var7 = var1.getConfig();
      Object var8 = null;
      Iterator var9 = this.customSerializers().iterator();

      while(var9.hasNext()) {
         Serializers var10 = (Serializers)var9.next();
         var8 = var10.findArraySerializer(var7, var2, var3, var5, var6);
         if (var8 != null) {
            break;
         }
      }

      if (var8 == null) {
         Class var12 = var2.getRawClass();
         if (var6 == null || ClassUtil.isJacksonStdImpl((Object)var6)) {
            if (String[].class == var12) {
               var8 = StringArraySerializer.instance;
            } else {
               var8 = StdArraySerializers.findStandardImpl(var12);
            }
         }

         if (var8 == null) {
            var8 = new ObjectArraySerializer(var2.getContentType(), var4, var5, var6);
         }
      }

      BeanSerializerModifier var11;
      if (this._factoryConfig.hasSerializerModifiers()) {
         for(var9 = this._factoryConfig.serializerModifiers().iterator(); var9.hasNext(); var8 = var11.modifyArraySerializer(var7, var2, var3, (JsonSerializer)var8)) {
            var11 = (BeanSerializerModifier)var9.next();
         }
      }

      return (JsonSerializer)var8;
   }

   public JsonSerializer findReferenceSerializer(SerializerProvider var1, ReferenceType var2, BeanDescription var3, boolean var4) throws JsonMappingException {
      JavaType var5 = var2.getContentType();
      TypeSerializer var6 = (TypeSerializer)var5.getTypeHandler();
      SerializationConfig var7 = var1.getConfig();
      if (var6 == null) {
         var6 = this.createTypeSerializer(var7, var5);
      }

      JsonSerializer var8 = (JsonSerializer)var5.getValueHandler();
      Iterator var9 = this.customSerializers().iterator();

      JsonSerializer var11;
      do {
         if (!var9.hasNext()) {
            if (var2.isTypeOrSubTypeOf(AtomicReference.class)) {
               return this.buildAtomicReferenceSerializer(var1, var2, var3, var4, var6, var8);
            }

            return null;
         }

         Serializers var10 = (Serializers)var9.next();
         var11 = var10.findReferenceSerializer(var7, var2, var3, var6, var8);
      } while(var11 == null);

      return var11;
   }

   protected JsonSerializer buildAtomicReferenceSerializer(SerializerProvider var1, ReferenceType var2, BeanDescription var3, boolean var4, TypeSerializer var5, JsonSerializer var6) throws JsonMappingException {
      // $FF: Couldn't be decompiled
   }

   protected JsonSerializer buildIteratorSerializer(SerializationConfig var1, JavaType var2, BeanDescription var3, boolean var4, JavaType var5) throws JsonMappingException {
      return new IteratorSerializer(var5, var4, this.createTypeSerializer(var1, var5));
   }

   protected JsonSerializer buildIterableSerializer(SerializationConfig var1, JavaType var2, BeanDescription var3, boolean var4, JavaType var5) throws JsonMappingException {
      return new IterableSerializer(var5, var4, this.createTypeSerializer(var1, var5));
   }

   protected JsonSerializer buildEnumSerializer(SerializationConfig var1, JavaType var2, BeanDescription var3) throws JsonMappingException {
      JsonFormat$Value var4 = var3.findExpectedFormat((JsonFormat$Value)null);
      if (var4 != null && var4.getShape() == JsonFormat$Shape.OBJECT) {
         ((BasicBeanDescription)var3).removeProperty("declaringClass");
         return null;
      } else {
         Class var5 = var2.getRawClass();
         Object var6 = EnumSerializer.construct(var5, var1, var3, var4);
         BeanSerializerModifier var8;
         if (this._factoryConfig.hasSerializerModifiers()) {
            for(Iterator var7 = this._factoryConfig.serializerModifiers().iterator(); var7.hasNext(); var6 = var8.modifyEnumSerializer(var1, var2, var3, (JsonSerializer)var6)) {
               var8 = (BeanSerializerModifier)var7.next();
            }
         }

         return (JsonSerializer)var6;
      }
   }

   protected JsonSerializer _findKeySerializer(SerializerProvider var1, Annotated var2) throws JsonMappingException {
      AnnotationIntrospector var3 = var1.getAnnotationIntrospector();
      Object var4 = var3.findKeySerializer(var2);
      return var4 != null ? var1.serializerInstance(var2, var4) : null;
   }

   protected JsonSerializer _findContentSerializer(SerializerProvider var1, Annotated var2) throws JsonMappingException {
      AnnotationIntrospector var3 = var1.getAnnotationIntrospector();
      Object var4 = var3.findContentSerializer(var2);
      return var4 != null ? var1.serializerInstance(var2, var4) : null;
   }

   protected Object findFilterId(SerializationConfig var1, BeanDescription var2) {
      return var1.getAnnotationIntrospector().findFilterId(var2.getClassInfo());
   }

   protected boolean usesStaticTyping(SerializationConfig var1, BeanDescription var2, TypeSerializer var3) {
      if (var3 != null) {
         return false;
      } else {
         AnnotationIntrospector var4 = var1.getAnnotationIntrospector();
         JsonSerialize$Typing var5 = var4.findSerializationTyping(var2.getClassInfo());
         if (var5 != null && var5 != JsonSerialize$Typing.DEFAULT_TYPING) {
            return var5 == JsonSerialize$Typing.STATIC;
         } else {
            return var1.isEnabled(MapperFeature.USE_STATIC_TYPING);
         }
      }
   }

   static {
      HashMap var0 = new HashMap();
      HashMap var1 = new HashMap();
      var1.put(String.class.getName(), new StringSerializer());
      ToStringSerializer var2 = ToStringSerializer.instance;
      var1.put(StringBuffer.class.getName(), var2);
      var1.put(StringBuilder.class.getName(), var2);
      var1.put(Character.class.getName(), var2);
      var1.put(Character.TYPE.getName(), var2);
      NumberSerializers.addAll(var1);
      var1.put(Boolean.TYPE.getName(), new BooleanSerializer(true));
      var1.put(Boolean.class.getName(), new BooleanSerializer(false));
      var1.put(BigInteger.class.getName(), new NumberSerializer(BigInteger.class));
      var1.put(BigDecimal.class.getName(), new NumberSerializer(BigDecimal.class));
      var1.put(Calendar.class.getName(), CalendarSerializer.instance);
      var1.put(Date.class.getName(), DateSerializer.instance);
      Iterator var3 = StdJdkSerializers.all().iterator();

      while(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         Object var5 = var4.getValue();
         if (var5 instanceof JsonSerializer) {
            var1.put(((Class)var4.getKey()).getName(), (JsonSerializer)var5);
         } else {
            Class var6 = (Class)var5;
            var0.put(((Class)var4.getKey()).getName(), var6);
         }
      }

      var0.put(TokenBuffer.class.getName(), TokenBufferSerializer.class);
      _concrete = var1;
      _concreteLazy = var0;
   }
}
