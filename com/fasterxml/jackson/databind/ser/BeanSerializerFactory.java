package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties$Value;
import com.fasterxml.jackson.annotation.JsonTypeInfo$As;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators$PropertyGenerator;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.AnnotationIntrospector$ReferenceProperty;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.BeanProperty$Std;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.impl.FilteredBeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.ObjectIdWriter;
import com.fasterxml.jackson.databind.ser.impl.PropertyBasedObjectIdGenerator;
import com.fasterxml.jackson.databind.ser.std.MapSerializer;
import com.fasterxml.jackson.databind.ser.std.StdDelegatingSerializer;
import com.fasterxml.jackson.databind.type.ReferenceType;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.Converter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class BeanSerializerFactory extends BasicSerializerFactory implements Serializable {
   private static final long serialVersionUID = 1L;
   public static final BeanSerializerFactory instance = new BeanSerializerFactory((SerializerFactoryConfig)null);

   protected BeanSerializerFactory(SerializerFactoryConfig var1) {
      super(var1);
   }

   public SerializerFactory withConfig(SerializerFactoryConfig var1) {
      if (this._factoryConfig == var1) {
         return this;
      } else if (this.getClass() != BeanSerializerFactory.class) {
         throw new IllegalStateException("Subtype of BeanSerializerFactory (" + this.getClass().getName() + ") has not properly overridden method 'withAdditionalSerializers': cannot instantiate subtype with " + "additional serializer definitions");
      } else {
         return new BeanSerializerFactory(var1);
      }
   }

   protected Iterable customSerializers() {
      return this._factoryConfig.serializers();
   }

   public JsonSerializer createSerializer(SerializerProvider var1, JavaType var2) throws JsonMappingException {
      SerializationConfig var3 = var1.getConfig();
      BeanDescription var4 = var3.introspect(var2);
      JsonSerializer var5 = this.findSerializerFromAnnotation(var1, var4.getClassInfo());
      if (var5 != null) {
         return var5;
      } else {
         AnnotationIntrospector var7 = var3.getAnnotationIntrospector();
         JavaType var8;
         if (var7 == null) {
            var8 = var2;
         } else {
            try {
               var8 = var7.refineSerializationType(var3, var4.getClassInfo(), var2);
            } catch (JsonMappingException var11) {
               return (JsonSerializer)var1.reportBadTypeDefinition(var4, var11.getMessage());
            }
         }

         boolean var6;
         if (var8 == var2) {
            var6 = false;
         } else {
            var6 = true;
            if (!var8.hasRawClass(var2.getRawClass())) {
               var4 = var3.introspect(var8);
            }
         }

         Converter var9 = var4.findSerializationConverter();
         if (var9 == null) {
            return this._createSerializer2(var1, var8, var4, var6);
         } else {
            JavaType var10 = var9.getOutputType(var1.getTypeFactory());
            if (!var10.hasRawClass(var8.getRawClass())) {
               var4 = var3.introspect(var10);
               var5 = this.findSerializerFromAnnotation(var1, var4.getClassInfo());
            }

            if (var5 == null && !var10.isJavaLangObject()) {
               var5 = this._createSerializer2(var1, var10, var4, true);
            }

            return new StdDelegatingSerializer(var9, var10, var5);
         }
      }
   }

   protected JsonSerializer _createSerializer2(SerializerProvider var1, JavaType var2, BeanDescription var3, boolean var4) throws JsonMappingException {
      JsonSerializer var5 = null;
      SerializationConfig var6 = var1.getConfig();
      Iterator var7;
      if (var2.isContainerType()) {
         if (!var4) {
            var4 = this.usesStaticTyping(var6, var3, (TypeSerializer)null);
         }

         var5 = this.buildContainerSerializer(var1, var2, var3, var4);
         if (var5 != null) {
            return var5;
         }
      } else {
         if (var2.isReferenceType()) {
            var5 = this.findReferenceSerializer(var1, (ReferenceType)var2, var3, var4);
         } else {
            var7 = this.customSerializers().iterator();

            while(var7.hasNext()) {
               Serializers var8 = (Serializers)var7.next();
               var5 = var8.findSerializer(var6, var2, var3);
               if (var5 != null) {
                  break;
               }
            }
         }

         if (var5 == null) {
            var5 = this.findSerializerByAnnotations(var1, var2, var3);
         }
      }

      if (var5 == null) {
         var5 = this.findSerializerByLookup(var2, var6, var3, var4);
         if (var5 == null) {
            var5 = this.findSerializerByPrimaryType(var1, var2, var3, var4);
            if (var5 == null) {
               var5 = this.findBeanSerializer(var1, var2, var3);
               if (var5 == null) {
                  var5 = this.findSerializerByAddonType(var6, var2, var3, var4);
                  if (var5 == null) {
                     var5 = var1.getUnknownTypeSerializer(var3.getBeanClass());
                  }
               }
            }
         }
      }

      BeanSerializerModifier var9;
      if (var5 != null && this._factoryConfig.hasSerializerModifiers()) {
         for(var7 = this._factoryConfig.serializerModifiers().iterator(); var7.hasNext(); var5 = var9.modifySerializer(var6, var3, var5)) {
            var9 = (BeanSerializerModifier)var7.next();
         }
      }

      return var5;
   }

   public JsonSerializer findBeanSerializer(SerializerProvider var1, JavaType var2, BeanDescription var3) throws JsonMappingException {
      return !this.isPotentialBeanType(var2.getRawClass()) && !var2.isEnumType() ? null : this.constructBeanSerializer(var1, var3);
   }

   public TypeSerializer findPropertyTypeSerializer(JavaType var1, SerializationConfig var2, AnnotatedMember var3) throws JsonMappingException {
      AnnotationIntrospector var4 = var2.getAnnotationIntrospector();
      TypeResolverBuilder var5 = var4.findPropertyTypeResolver(var2, var3, var1);
      TypeSerializer var6;
      if (var5 == null) {
         var6 = this.createTypeSerializer(var2, var1);
      } else {
         Collection var7 = var2.getSubtypeResolver().collectAndResolveSubtypesByClass(var2, var3, var1);
         var6 = var5.buildTypeSerializer(var2, var1, var7);
      }

      return var6;
   }

   public TypeSerializer findPropertyContentTypeSerializer(JavaType var1, SerializationConfig var2, AnnotatedMember var3) throws JsonMappingException {
      JavaType var4 = var1.getContentType();
      AnnotationIntrospector var5 = var2.getAnnotationIntrospector();
      TypeResolverBuilder var6 = var5.findPropertyContentTypeResolver(var2, var3, var1);
      TypeSerializer var7;
      if (var6 == null) {
         var7 = this.createTypeSerializer(var2, var4);
      } else {
         Collection var8 = var2.getSubtypeResolver().collectAndResolveSubtypesByClass(var2, var3, var4);
         var7 = var6.buildTypeSerializer(var2, var4, var8);
      }

      return var7;
   }

   protected JsonSerializer constructBeanSerializer(SerializerProvider var1, BeanDescription var2) throws JsonMappingException {
      if (var2.getBeanClass() == Object.class) {
         return var1.getUnknownTypeSerializer(Object.class);
      } else {
         SerializationConfig var3 = var1.getConfig();
         BeanSerializerBuilder var4 = this.constructBeanSerializerBuilder(var2);
         var4.setConfig(var3);
         List var5 = this.findBeanProperties(var1, var2, var4);
         Object var15;
         if (var5 == null) {
            var15 = new ArrayList();
         } else {
            var15 = this.removeOverlappingTypeIds(var1, var2, var4, var5);
         }

         var1.getAnnotationIntrospector().findAndAddVirtualProperties(var3, var2.getClassInfo(), (List)var15);
         Iterator var6;
         BeanSerializerModifier var7;
         if (this._factoryConfig.hasSerializerModifiers()) {
            for(var6 = this._factoryConfig.serializerModifiers().iterator(); var6.hasNext(); var15 = var7.changeProperties(var3, var2, (List)var15)) {
               var7 = (BeanSerializerModifier)var6.next();
            }
         }

         var5 = this.filterBeanProperties(var3, var2, (List)var15);
         if (this._factoryConfig.hasSerializerModifiers()) {
            for(var6 = this._factoryConfig.serializerModifiers().iterator(); var6.hasNext(); var5 = var7.orderProperties(var3, var2, var5)) {
               var7 = (BeanSerializerModifier)var6.next();
            }
         }

         var4.setObjectIdWriter(this.constructObjectIdHandler(var1, var2, var5));
         var4.setProperties(var5);
         var4.setFilterId(this.findFilterId(var3, var2));
         AnnotatedMember var16 = var2.findAnyGetter();
         if (var16 != null) {
            JavaType var17 = var16.getType();
            boolean var8 = var3.isEnabled(MapperFeature.USE_STATIC_TYPING);
            JavaType var9 = var17.getContentType();
            TypeSerializer var10 = this.createTypeSerializer(var3, var9);
            Object var11 = this.findSerializerFromAnnotation(var1, var16);
            if (var11 == null) {
               var11 = MapSerializer.construct((Set)((Set)null), var17, var8, var10, (JsonSerializer)null, (JsonSerializer)null, (Object)null);
            }

            PropertyName var12 = PropertyName.construct(var16.getName());
            BeanProperty$Std var13 = new BeanProperty$Std(var12, var9, (PropertyName)null, var16, PropertyMetadata.STD_OPTIONAL);
            var4.setAnyGetter(new AnyGetterWriter(var13, var16, (JsonSerializer)var11));
         }

         this.processViews(var3, var4);
         BeanSerializerModifier var20;
         if (this._factoryConfig.hasSerializerModifiers()) {
            for(Iterator var18 = this._factoryConfig.serializerModifiers().iterator(); var18.hasNext(); var4 = var20.updateBuilder(var3, var2, var4)) {
               var20 = (BeanSerializerModifier)var18.next();
            }
         }

         JsonSerializer var19 = null;

         try {
            var19 = var4.build();
         } catch (RuntimeException var14) {
            var1.reportBadTypeDefinition(var2, "Failed to construct BeanSerializer for %s: (%s) %s", var2.getType(), var14.getClass().getName(), var14.getMessage());
         }

         return (JsonSerializer)(var19 == null && var2.hasKnownClassAnnotations() ? var4.createDummy() : var19);
      }
   }

   protected ObjectIdWriter constructObjectIdHandler(SerializerProvider var1, BeanDescription var2, List var3) throws JsonMappingException {
      ObjectIdInfo var4 = var2.getObjectIdInfo();
      if (var4 == null) {
         return null;
      } else {
         Class var6 = var4.getGeneratorType();
         JavaType var8;
         if (var6 != ObjectIdGenerators$PropertyGenerator.class) {
            JavaType var13 = var1.constructType(var6);
            var8 = var1.getTypeFactory().findTypeParameters(var13, ObjectIdGenerator.class)[0];
            ObjectIdGenerator var12 = var1.objectIdGeneratorInstance(var2.getClassInfo(), var4);
            return ObjectIdWriter.construct(var8, var4.getPropertyName(), var12, var4.getAlwaysAsId());
         } else {
            String var7 = var4.getPropertyName().getSimpleName();
            var8 = null;
            int var9 = 0;

            for(int var10 = var3.size(); var9 != var10; ++var9) {
               BeanPropertyWriter var11 = (BeanPropertyWriter)var3.get(var9);
               if (var7.equals(var11.getName())) {
                  if (var9 > 0) {
                     var3.remove(var9);
                     var3.add(0, var11);
                  }

                  JavaType var14 = var11.getType();
                  PropertyBasedObjectIdGenerator var5 = new PropertyBasedObjectIdGenerator(var4, var11);
                  return ObjectIdWriter.construct(var14, (PropertyName)null, var5, var4.getAlwaysAsId());
               }
            }

            throw new IllegalArgumentException("Invalid Object Id definition for " + var2.getBeanClass().getName() + ": cannot find property with name '" + var7 + "'");
         }
      }
   }

   protected BeanPropertyWriter constructFilteredBeanWriter(BeanPropertyWriter var1, Class[] var2) {
      return FilteredBeanPropertyWriter.constructViewBased(var1, var2);
   }

   protected PropertyBuilder constructPropertyBuilder(SerializationConfig var1, BeanDescription var2) {
      return new PropertyBuilder(var1, var2);
   }

   protected BeanSerializerBuilder constructBeanSerializerBuilder(BeanDescription var1) {
      return new BeanSerializerBuilder(var1);
   }

   protected boolean isPotentialBeanType(Class var1) {
      return ClassUtil.canBeABeanType(var1) == null && !ClassUtil.isProxyType(var1);
   }

   protected List findBeanProperties(SerializerProvider var1, BeanDescription var2, BeanSerializerBuilder var3) throws JsonMappingException {
      List var4 = var2.findProperties();
      SerializationConfig var5 = var1.getConfig();
      this.removeIgnorableTypes(var5, var2, var4);
      if (var5.isEnabled(MapperFeature.REQUIRE_SETTERS_FOR_GETTERS)) {
         this.removeSetterlessGetters(var5, var2, var4);
      }

      if (var4.isEmpty()) {
         return null;
      } else {
         boolean var6 = this.usesStaticTyping(var5, var2, (TypeSerializer)null);
         PropertyBuilder var7 = this.constructPropertyBuilder(var5, var2);
         ArrayList var8 = new ArrayList(var4.size());
         Iterator var9 = var4.iterator();

         while(true) {
            while(var9.hasNext()) {
               BeanPropertyDefinition var10 = (BeanPropertyDefinition)var9.next();
               AnnotatedMember var11 = var10.getAccessor();
               if (var10.isTypeId()) {
                  if (var11 != null) {
                     var3.setTypeId(var11);
                  }
               } else {
                  AnnotationIntrospector$ReferenceProperty var12 = var10.findReferenceType();
                  if (var12 == null || !var12.isBackReference()) {
                     if (var11 instanceof AnnotatedMethod) {
                        var8.add(this._constructWriter(var1, var10, var7, var6, (AnnotatedMethod)var11));
                     } else {
                        var8.add(this._constructWriter(var1, var10, var7, var6, (AnnotatedField)var11));
                     }
                  }
               }
            }

            return var8;
         }
      }
   }

   protected List filterBeanProperties(SerializationConfig var1, BeanDescription var2, List var3) {
      JsonIgnoreProperties$Value var4 = var1.getDefaultPropertyIgnorals(var2.getBeanClass(), var2.getClassInfo());
      if (var4 != null) {
         Set var5 = var4.findIgnoredForSerialization();
         if (!var5.isEmpty()) {
            Iterator var6 = var3.iterator();

            while(var6.hasNext()) {
               if (var5.contains(((BeanPropertyWriter)var6.next()).getName())) {
                  var6.remove();
               }
            }
         }
      }

      return var3;
   }

   protected void processViews(SerializationConfig var1, BeanSerializerBuilder var2) {
      List var3 = var2.getProperties();
      boolean var4 = var1.isEnabled(MapperFeature.DEFAULT_VIEW_INCLUSION);
      int var5 = var3.size();
      int var6 = 0;
      BeanPropertyWriter[] var7 = new BeanPropertyWriter[var5];

      for(int var8 = 0; var8 < var5; ++var8) {
         BeanPropertyWriter var9 = (BeanPropertyWriter)var3.get(var8);
         Class[] var10 = var9.getViews();
         if (var10 == null) {
            if (var4) {
               var7[var8] = var9;
            }
         } else {
            ++var6;
            var7[var8] = this.constructFilteredBeanWriter(var9, var10);
         }
      }

      if (!var4 || var6 != 0) {
         var2.setFilteredProperties(var7);
      }
   }

   protected void removeIgnorableTypes(SerializationConfig var1, BeanDescription var2, List var3) {
      AnnotationIntrospector var4 = var1.getAnnotationIntrospector();
      HashMap var5 = new HashMap();
      Iterator var6 = var3.iterator();

      while(var6.hasNext()) {
         BeanPropertyDefinition var7 = (BeanPropertyDefinition)var6.next();
         AnnotatedMember var8 = var7.getAccessor();
         if (var8 == null) {
            var6.remove();
         } else {
            Class var9 = var7.getRawPrimaryType();
            Boolean var10 = (Boolean)var5.get(var9);
            if (var10 == null) {
               var10 = var1.getConfigOverride(var9).getIsIgnoredType();
               if (var10 == null) {
                  BeanDescription var11 = var1.introspectClassAnnotations(var9);
                  AnnotatedClass var12 = var11.getClassInfo();
                  var10 = var4.isIgnorableType(var12);
                  if (var10 == null) {
                     var10 = Boolean.FALSE;
                  }
               }

               var5.put(var9, var10);
            }

            if (var10) {
               var6.remove();
            }
         }
      }

   }

   protected void removeSetterlessGetters(SerializationConfig var1, BeanDescription var2, List var3) {
      Iterator var4 = var3.iterator();

      while(var4.hasNext()) {
         BeanPropertyDefinition var5 = (BeanPropertyDefinition)var4.next();
         if (!var5.couldDeserialize() && !var5.isExplicitlyIncluded()) {
            var4.remove();
         }
      }

   }

   protected List removeOverlappingTypeIds(SerializerProvider var1, BeanDescription var2, BeanSerializerBuilder var3, List var4) {
      int var5 = 0;

      for(int var6 = var4.size(); var5 < var6; ++var5) {
         BeanPropertyWriter var7 = (BeanPropertyWriter)var4.get(var5);
         TypeSerializer var8 = var7.getTypeSerializer();
         if (var8 != null && var8.getTypeInclusion() == JsonTypeInfo$As.EXTERNAL_PROPERTY) {
            String var9 = var8.getPropertyName();
            PropertyName var10 = PropertyName.construct(var9);
            Iterator var11 = var4.iterator();

            while(var11.hasNext()) {
               BeanPropertyWriter var12 = (BeanPropertyWriter)var11.next();
               if (var12 != var7 && var12.wouldConflictWithName(var10)) {
                  var7.assignTypeSerializer((TypeSerializer)null);
                  break;
               }
            }
         }
      }

      return var4;
   }

   protected BeanPropertyWriter _constructWriter(SerializerProvider var1, BeanPropertyDefinition var2, PropertyBuilder var3, boolean var4, AnnotatedMember var5) throws JsonMappingException {
      PropertyName var6 = var2.getFullName();
      JavaType var7 = var5.getType();
      BeanProperty$Std var8 = new BeanProperty$Std(var6, var7, var2.getWrapperName(), var5, var2.getMetadata());
      JsonSerializer var9 = this.findSerializerFromAnnotation(var1, var5);
      if (var9 instanceof ResolvableSerializer) {
         ((ResolvableSerializer)var9).resolve(var1);
      }

      var9 = var1.handlePrimaryContextualization(var9, var8);
      TypeSerializer var10 = null;
      if (var7.isContainerType() || var7.isReferenceType()) {
         var10 = this.findPropertyContentTypeSerializer(var7, var1.getConfig(), var5);
      }

      TypeSerializer var11 = this.findPropertyTypeSerializer(var7, var1.getConfig(), var5);
      return var3.buildWriter(var1, var2, var7, var9, var11, var10, var5, var4);
   }
}
