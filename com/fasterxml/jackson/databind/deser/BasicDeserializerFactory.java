package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.annotation.JacksonInject$Value;
import com.fasterxml.jackson.annotation.JsonCreator$Mode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties$Value;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.AbstractTypeResolver;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.BeanProperty$Std;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.cfg.DeserializerFactoryConfig;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.databind.deser.impl.CreatorCandidate;
import com.fasterxml.jackson.databind.deser.impl.CreatorCollector;
import com.fasterxml.jackson.databind.deser.impl.JavaUtilCollectionsDeserializers;
import com.fasterxml.jackson.databind.deser.std.ArrayBlockingQueueDeserializer;
import com.fasterxml.jackson.databind.deser.std.AtomicReferenceDeserializer;
import com.fasterxml.jackson.databind.deser.std.CollectionDeserializer;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.deser.std.EnumDeserializer;
import com.fasterxml.jackson.databind.deser.std.EnumMapDeserializer;
import com.fasterxml.jackson.databind.deser.std.EnumSetDeserializer;
import com.fasterxml.jackson.databind.deser.std.JdkDeserializers;
import com.fasterxml.jackson.databind.deser.std.JsonLocationInstantiator;
import com.fasterxml.jackson.databind.deser.std.JsonNodeDeserializer;
import com.fasterxml.jackson.databind.deser.std.MapDeserializer;
import com.fasterxml.jackson.databind.deser.std.MapEntryDeserializer;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;
import com.fasterxml.jackson.databind.deser.std.ObjectArrayDeserializer;
import com.fasterxml.jackson.databind.deser.std.PrimitiveArrayDeserializers;
import com.fasterxml.jackson.databind.deser.std.StdKeyDeserializers;
import com.fasterxml.jackson.databind.deser.std.StringArrayDeserializer;
import com.fasterxml.jackson.databind.deser.std.StringCollectionDeserializer;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.fasterxml.jackson.databind.deser.std.TokenBufferDeserializer;
import com.fasterxml.jackson.databind.deser.std.UntypedObjectDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.fasterxml.jackson.databind.ext.OptionalHandlerFactory;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedConstructor;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
import com.fasterxml.jackson.databind.introspect.BasicBeanDescription;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.ReferenceType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.ConstantValueInstantiator;
import com.fasterxml.jackson.databind.util.EnumResolver;
import com.fasterxml.jackson.databind.util.NameTransformer;
import com.fasterxml.jackson.databind.util.SimpleBeanPropertyDefinition;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicReference;

public abstract class BasicDeserializerFactory extends DeserializerFactory implements Serializable {
   private static final Class CLASS_OBJECT = Object.class;
   private static final Class CLASS_STRING = String.class;
   private static final Class CLASS_CHAR_SEQUENCE = CharSequence.class;
   private static final Class CLASS_ITERABLE = Iterable.class;
   private static final Class CLASS_MAP_ENTRY = Entry.class;
   protected static final PropertyName UNWRAPPED_CREATOR_PARAM_NAME = new PropertyName("@JsonUnwrapped");
   static final HashMap _mapFallbacks = new HashMap();
   static final HashMap _collectionFallbacks;
   protected final DeserializerFactoryConfig _factoryConfig;

   protected BasicDeserializerFactory(DeserializerFactoryConfig var1) {
      super();
      this._factoryConfig = var1;
   }

   public DeserializerFactoryConfig getFactoryConfig() {
      return this._factoryConfig;
   }

   protected abstract DeserializerFactory withConfig(DeserializerFactoryConfig var1);

   public final DeserializerFactory withAdditionalDeserializers(Deserializers var1) {
      return this.withConfig(this._factoryConfig.withAdditionalDeserializers(var1));
   }

   public final DeserializerFactory withAdditionalKeyDeserializers(KeyDeserializers var1) {
      return this.withConfig(this._factoryConfig.withAdditionalKeyDeserializers(var1));
   }

   public final DeserializerFactory withDeserializerModifier(BeanDeserializerModifier var1) {
      return this.withConfig(this._factoryConfig.withDeserializerModifier(var1));
   }

   public final DeserializerFactory withAbstractTypeResolver(AbstractTypeResolver var1) {
      return this.withConfig(this._factoryConfig.withAbstractTypeResolver(var1));
   }

   public final DeserializerFactory withValueInstantiators(ValueInstantiators var1) {
      return this.withConfig(this._factoryConfig.withValueInstantiators(var1));
   }

   public JavaType mapAbstractType(DeserializationConfig var1, JavaType var2) throws JsonMappingException {
      while(true) {
         JavaType var3 = this._mapAbstractType2(var1, var2);
         if (var3 == null) {
            return var2;
         }

         Class var4 = var2.getRawClass();
         Class var5 = var3.getRawClass();
         if (var4 == var5 || !var4.isAssignableFrom(var5)) {
            throw new IllegalArgumentException("Invalid abstract type resolution from " + var2 + " to " + var3 + ": latter is not a subtype of former");
         }

         var2 = var3;
      }
   }

   private JavaType _mapAbstractType2(DeserializationConfig var1, JavaType var2) throws JsonMappingException {
      Class var3 = var2.getRawClass();
      if (this._factoryConfig.hasAbstractTypeResolvers()) {
         Iterator var4 = this._factoryConfig.abstractTypeResolvers().iterator();

         while(var4.hasNext()) {
            AbstractTypeResolver var5 = (AbstractTypeResolver)var4.next();
            JavaType var6 = var5.findTypeMapping(var1, var2);
            if (var6 != null && !var6.hasRawClass(var3)) {
               return var6;
            }
         }
      }

      return null;
   }

   public ValueInstantiator findValueInstantiator(DeserializationContext var1, BeanDescription var2) throws JsonMappingException {
      DeserializationConfig var3 = var1.getConfig();
      ValueInstantiator var4 = null;
      AnnotatedClass var5 = var2.getClassInfo();
      Object var6 = var1.getAnnotationIntrospector().findValueInstantiator(var5);
      if (var6 != null) {
         var4 = this._valueInstantiatorInstance(var3, var5, var6);
      }

      if (var4 == null) {
         var4 = this._findStdValueInstantiator(var3, var2);
         if (var4 == null) {
            var4 = this._constructDefaultValueInstantiator(var1, var2);
         }
      }

      if (this._factoryConfig.hasValueInstantiators()) {
         Iterator var7 = this._factoryConfig.valueInstantiators().iterator();

         while(var7.hasNext()) {
            ValueInstantiators var8 = (ValueInstantiators)var7.next();
            var4 = var8.findValueInstantiator(var3, var2, var4);
            if (var4 == null) {
               var1.reportBadTypeDefinition(var2, "Broken registered ValueInstantiators (of type %s): returned null ValueInstantiator", var8.getClass().getName());
            }
         }
      }

      if (var4.getIncompleteParameter() != null) {
         AnnotatedParameter var9 = var4.getIncompleteParameter();
         AnnotatedWithParams var10 = var9.getOwner();
         throw new IllegalArgumentException("Argument #" + var9.getIndex() + " of constructor " + var10 + " has no property name annotation; must have name when multiple-parameter constructor annotated as Creator");
      } else {
         return var4;
      }
   }

   private ValueInstantiator _findStdValueInstantiator(DeserializationConfig var1, BeanDescription var2) throws JsonMappingException {
      Class var3 = var2.getBeanClass();
      if (var3 == JsonLocation.class) {
         return new JsonLocationInstantiator();
      } else {
         if (Collection.class.isAssignableFrom(var3)) {
            if (Collections.EMPTY_SET.getClass() == var3) {
               return new ConstantValueInstantiator(Collections.EMPTY_SET);
            }

            if (Collections.EMPTY_LIST.getClass() == var3) {
               return new ConstantValueInstantiator(Collections.EMPTY_LIST);
            }
         } else if (Map.class.isAssignableFrom(var3) && Collections.EMPTY_MAP.getClass() == var3) {
            return new ConstantValueInstantiator(Collections.EMPTY_MAP);
         }

         return null;
      }
   }

   protected ValueInstantiator _constructDefaultValueInstantiator(DeserializationContext var1, BeanDescription var2) throws JsonMappingException {
      CreatorCollector var3 = new CreatorCollector(var2, var1.getConfig());
      AnnotationIntrospector var4 = var1.getAnnotationIntrospector();
      DeserializationConfig var5 = var1.getConfig();
      VisibilityChecker var6 = var5.getDefaultVisibilityChecker(var2.getBeanClass(), var2.getClassInfo());
      Map var7 = this._findCreatorsFromProperties(var1, var2);
      this._addDeserializerFactoryMethods(var1, var2, var6, var4, var3, var7);
      if (var2.getType().isConcrete()) {
         this._addDeserializerConstructors(var1, var2, var6, var4, var3, var7);
      }

      return var3.constructValueInstantiator(var1);
   }

   protected Map _findCreatorsFromProperties(DeserializationContext var1, BeanDescription var2) throws JsonMappingException {
      Object var3 = Collections.emptyMap();
      Iterator var4 = var2.findProperties().iterator();

      while(var4.hasNext()) {
         BeanPropertyDefinition var5 = (BeanPropertyDefinition)var4.next();

         BeanPropertyDefinition[] var9;
         int var10;
         for(Iterator var6 = var5.getConstructorParameters(); var6.hasNext(); var9[var10] = var5) {
            AnnotatedParameter var7 = (AnnotatedParameter)var6.next();
            AnnotatedWithParams var8 = var7.getOwner();
            var9 = (BeanPropertyDefinition[])((Map)var3).get(var8);
            var10 = var7.getIndex();
            if (var9 == null) {
               if (((Map)var3).isEmpty()) {
                  var3 = new LinkedHashMap();
               }

               var9 = new BeanPropertyDefinition[var8.getParameterCount()];
               ((Map)var3).put(var8, var9);
            } else if (var9[var10] != null) {
               var1.reportBadTypeDefinition(var2, "Conflict: parameter #%d of %s bound to more than one property; %s vs %s", var10, var8, var9[var10], var5);
            }
         }
      }

      return (Map)var3;
   }

   public ValueInstantiator _valueInstantiatorInstance(DeserializationConfig var1, Annotated var2, Object var3) throws JsonMappingException {
      if (var3 == null) {
         return null;
      } else if (var3 instanceof ValueInstantiator) {
         return (ValueInstantiator)var3;
      } else if (!(var3 instanceof Class)) {
         throw new IllegalStateException("AnnotationIntrospector returned key deserializer definition of type " + var3.getClass().getName() + "; expected type KeyDeserializer or Class<KeyDeserializer> instead");
      } else {
         Class var5 = (Class)var3;
         if (ClassUtil.isBogusClass(var5)) {
            return null;
         } else if (!ValueInstantiator.class.isAssignableFrom(var5)) {
            throw new IllegalStateException("AnnotationIntrospector returned Class " + var5.getName() + "; expected Class<ValueInstantiator>");
         } else {
            HandlerInstantiator var6 = var1.getHandlerInstantiator();
            if (var6 != null) {
               ValueInstantiator var4 = var6.valueInstantiatorInstance(var1, var2, var5);
               if (var4 != null) {
                  return var4;
               }
            }

            return (ValueInstantiator)ClassUtil.createInstance(var5, var1.canOverrideAccessModifiers());
         }
      }
   }

   protected void _addDeserializerConstructors(DeserializationContext var1, BeanDescription var2, VisibilityChecker var3, AnnotationIntrospector var4, CreatorCollector var5, Map var6) throws JsonMappingException {
      // $FF: Couldn't be decompiled
   }

   protected void _addExplicitDelegatingCreator(DeserializationContext var1, BeanDescription var2, CreatorCollector var3, CreatorCandidate var4) throws JsonMappingException {
      int var5 = -1;
      int var6 = var4.paramCount();
      SettableBeanProperty[] var7 = new SettableBeanProperty[var6];

      for(int var8 = 0; var8 < var6; ++var8) {
         AnnotatedParameter var9 = var4.parameter(var8);
         JacksonInject$Value var10 = var4.injection(var8);
         if (var10 != null) {
            var7[var8] = this.constructCreatorProperty(var1, var2, (PropertyName)null, var8, var9, var10);
         } else if (var5 < 0) {
            var5 = var8;
         } else {
            var1.reportBadTypeDefinition(var2, "More than one argument (#%d and #%d) left as delegating for Creator %s: only one allowed", var5, var8, var4);
         }
      }

      if (var5 < 0) {
         var1.reportBadTypeDefinition(var2, "No argument left as delegating for Creator %s: exactly one required", var4);
      }

      if (var6 == 1) {
         this._handleSingleArgumentCreator(var3, var4.creator(), true, true);
         BeanPropertyDefinition var11 = var4.propertyDef(0);
         if (var11 != null) {
            ((POJOPropertyBuilder)var11).removeConstructors();
         }

      } else {
         var3.addDelegatingCreator(var4.creator(), true, var7, var5);
      }
   }

   protected void _addExplicitPropertyCreator(DeserializationContext var1, BeanDescription var2, CreatorCollector var3, CreatorCandidate var4) throws JsonMappingException {
      int var5 = var4.paramCount();
      SettableBeanProperty[] var6 = new SettableBeanProperty[var5];

      for(int var7 = 0; var7 < var5; ++var7) {
         JacksonInject$Value var8 = var4.injection(var7);
         AnnotatedParameter var9 = var4.parameter(var7);
         PropertyName var10 = var4.paramName(var7);
         if (var10 == null) {
            NameTransformer var11 = var1.getAnnotationIntrospector().findUnwrappingNameTransformer(var9);
            if (var11 != null) {
               this._reportUnwrappedCreatorProperty(var1, var2, var9);
            }

            var10 = var4.findImplicitParamName(var7);
            if (var10 == null && var8 == null) {
               var1.reportBadTypeDefinition(var2, "Argument #%d has no property name, is not Injectable: can not use as Creator %s", var7, var4);
            }
         }

         var6[var7] = this.constructCreatorProperty(var1, var2, var10, var7, var9, var8);
      }

      var3.addPropertyCreator(var4.creator(), true, var6);
   }

   protected void _addExplicitAnyCreator(DeserializationContext var1, BeanDescription var2, CreatorCollector var3, CreatorCandidate var4) throws JsonMappingException {
      if (1 != var4.paramCount()) {
         int var11 = var4.findOnlyParamWithoutInjection();
         if (var11 >= 0 && var4.paramName(var11) == null) {
            this._addExplicitDelegatingCreator(var1, var2, var3, var4);
         } else {
            this._addExplicitPropertyCreator(var1, var2, var3, var4);
         }
      } else {
         AnnotatedParameter var5 = var4.parameter(0);
         JacksonInject$Value var6 = var4.injection(0);
         PropertyName var7 = var4.explicitParamName(0);
         BeanPropertyDefinition var8 = var4.propertyDef(0);
         boolean var9 = var7 != null || var6 != null;
         if (!var9 && var8 != null) {
            var7 = var4.paramName(0);
            var9 = var7 != null && var8.couldSerialize();
         }

         if (var9) {
            SettableBeanProperty[] var10 = new SettableBeanProperty[]{this.constructCreatorProperty(var1, var2, var7, 0, var5, var6)};
            var3.addPropertyCreator(var4.creator(), true, var10);
         } else {
            this._handleSingleArgumentCreator(var3, var4.creator(), true, true);
            if (var8 != null) {
               ((POJOPropertyBuilder)var8).removeConstructors();
            }

         }
      }
   }

   private boolean _checkIfCreatorPropertyBased(AnnotationIntrospector var1, AnnotatedWithParams var2, BeanPropertyDefinition var3) {
      if ((var3 == null || !var3.isExplicitlyNamed()) && var1.findInjectableValue(var2.getParameter(0)) == null) {
         if (var3 != null) {
            String var4 = var3.getName();
            if (var4 != null && !var4.isEmpty() && var3.couldSerialize()) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   private void _checkImplicitlyNamedConstructors(DeserializationContext var1, BeanDescription var2, VisibilityChecker var3, AnnotationIntrospector var4, CreatorCollector var5, List var6) throws JsonMappingException {
      AnnotatedWithParams var7 = null;
      SettableBeanProperty[] var8 = null;
      Iterator var9 = var6.iterator();

      int var11;
      label48:
      while(var9.hasNext()) {
         AnnotatedWithParams var10 = (AnnotatedWithParams)var9.next();
         if (var3.isCreatorVisible((AnnotatedMember)var10)) {
            var11 = var10.getParameterCount();
            SettableBeanProperty[] var12 = new SettableBeanProperty[var11];

            for(int var13 = 0; var13 < var11; ++var13) {
               AnnotatedParameter var14 = var10.getParameter(var13);
               PropertyName var15 = this._findParamName(var14, var4);
               if (var15 == null || var15.isEmpty()) {
                  continue label48;
               }

               var12[var13] = this.constructCreatorProperty(var1, var2, var15, var14.getIndex(), var14, (JacksonInject$Value)null);
            }

            if (var7 != null) {
               var7 = null;
               break;
            }

            var7 = var10;
            var8 = var12;
         }
      }

      if (var7 != null) {
         var5.addPropertyCreator(var7, false, var8);
         BasicBeanDescription var16 = (BasicBeanDescription)var2;
         SettableBeanProperty[] var17 = var8;
         var11 = var8.length;

         for(int var18 = 0; var18 < var11; ++var18) {
            Object var19 = var17[var18];
            PropertyName var20 = ((SettableBeanProperty)var19).getFullName();
            if (!var16.hasProperty(var20)) {
               SimpleBeanPropertyDefinition var21 = SimpleBeanPropertyDefinition.construct(var1.getConfig(), ((SettableBeanProperty)var19).getMember(), var20);
               var16.addProperty(var21);
            }
         }
      }

   }

   protected void _addDeserializerFactoryMethods(DeserializationContext var1, BeanDescription var2, VisibilityChecker var3, AnnotationIntrospector var4, CreatorCollector var5, Map var6) throws JsonMappingException {
      // $FF: Couldn't be decompiled
   }

   protected boolean _handleSingleArgumentCreator(CreatorCollector var1, AnnotatedWithParams var2, boolean var3, boolean var4) {
      Class var5 = var2.getRawParameterType(0);
      if (var5 != String.class && var5 != CLASS_CHAR_SEQUENCE) {
         if (var5 != Integer.TYPE && var5 != Integer.class) {
            if (var5 != Long.TYPE && var5 != Long.class) {
               if (var5 != Double.TYPE && var5 != Double.class) {
                  if (var5 != Boolean.TYPE && var5 != Boolean.class) {
                     if (var3) {
                        var1.addDelegatingCreator(var2, var3, (SettableBeanProperty[])null, 0);
                        return true;
                     } else {
                        return false;
                     }
                  } else {
                     if (var3 || var4) {
                        var1.addBooleanCreator(var2, var3);
                     }

                     return true;
                  }
               } else {
                  if (var3 || var4) {
                     var1.addDoubleCreator(var2, var3);
                  }

                  return true;
               }
            } else {
               if (var3 || var4) {
                  var1.addLongCreator(var2, var3);
               }

               return true;
            }
         } else {
            if (var3 || var4) {
               var1.addIntCreator(var2, var3);
            }

            return true;
         }
      } else {
         if (var3 || var4) {
            var1.addStringCreator(var2, var3);
         }

         return true;
      }
   }

   protected void _reportUnwrappedCreatorProperty(DeserializationContext var1, BeanDescription var2, AnnotatedParameter var3) throws JsonMappingException {
      var1.reportBadDefinition(var2.getType(), String.format("Cannot define Creator parameter %d as `@JsonUnwrapped`: combination not yet supported", var3.getIndex()));
   }

   protected SettableBeanProperty constructCreatorProperty(DeserializationContext var1, BeanDescription var2, PropertyName var3, int var4, AnnotatedParameter var5, JacksonInject$Value var6) throws JsonMappingException {
      DeserializationConfig var7 = var1.getConfig();
      AnnotationIntrospector var8 = var1.getAnnotationIntrospector();
      PropertyMetadata var9;
      if (var8 == null) {
         var9 = PropertyMetadata.STD_REQUIRED_OR_OPTIONAL;
      } else {
         Boolean var10 = var8.hasRequiredMarker(var5);
         String var11 = var8.findPropertyDescription(var5);
         Integer var12 = var8.findPropertyIndex(var5);
         String var13 = var8.findPropertyDefaultValue(var5);
         var9 = PropertyMetadata.construct(var10, var11, var12, var13);
      }

      JavaType var16 = this.resolveMemberAndTypeAnnotations(var1, var5, var5.getType());
      BeanProperty$Std var17 = new BeanProperty$Std(var3, var16, var8.findWrapperName(var5), var5, var9);
      TypeDeserializer var18 = (TypeDeserializer)var16.getTypeHandler();
      if (var18 == null) {
         var18 = this.findTypeDeserializer(var7, var16);
      }

      Object var19 = var6 == null ? null : var6.getId();
      Object var14 = new CreatorProperty(var3, var16, var17.getWrapperName(), var18, var2.getClassAnnotations(), var5, var4, var19, var9);
      JsonDeserializer var15 = this.findDeserializerFromAnnotation(var1, var5);
      if (var15 == null) {
         var15 = (JsonDeserializer)var16.getValueHandler();
      }

      if (var15 != null) {
         var15 = var1.handlePrimaryContextualization(var15, (BeanProperty)var14, var16);
         var14 = ((SettableBeanProperty)var14).withValueDeserializer(var15);
      }

      return (SettableBeanProperty)var14;
   }

   private PropertyName _findParamName(AnnotatedParameter var1, AnnotationIntrospector var2) {
      if (var1 != null && var2 != null) {
         PropertyName var3 = var2.findNameForDeserialization(var1);
         if (var3 != null) {
            return var3;
         }

         String var4 = var2.findImplicitPropertyName(var1);
         if (var4 != null && !var4.isEmpty()) {
            return PropertyName.construct(var4);
         }
      }

      return null;
   }

   public JsonDeserializer createArrayDeserializer(DeserializationContext var1, ArrayType var2, BeanDescription var3) throws JsonMappingException {
      DeserializationConfig var4 = var1.getConfig();
      JavaType var5 = var2.getContentType();
      JsonDeserializer var6 = (JsonDeserializer)var5.getValueHandler();
      TypeDeserializer var7 = (TypeDeserializer)var5.getTypeHandler();
      if (var7 == null) {
         var7 = this.findTypeDeserializer(var4, var5);
      }

      Object var8 = this._findCustomArrayDeserializer(var2, var4, var3, var7, var6);
      if (var8 == null) {
         if (var6 == null) {
            Class var9 = var5.getRawClass();
            if (var5.isPrimitive()) {
               return PrimitiveArrayDeserializers.forType(var9);
            }

            if (var9 == String.class) {
               return StringArrayDeserializer.instance;
            }
         }

         var8 = new ObjectArrayDeserializer(var2, var6, var7);
      }

      BeanDeserializerModifier var10;
      if (this._factoryConfig.hasDeserializerModifiers()) {
         for(Iterator var11 = this._factoryConfig.deserializerModifiers().iterator(); var11.hasNext(); var8 = var10.modifyArrayDeserializer(var4, var2, var3, (JsonDeserializer)var8)) {
            var10 = (BeanDeserializerModifier)var11.next();
         }
      }

      return (JsonDeserializer)var8;
   }

   public JsonDeserializer createCollectionDeserializer(DeserializationContext var1, CollectionType var2, BeanDescription var3) throws JsonMappingException {
      JavaType var4 = var2.getContentType();
      JsonDeserializer var5 = (JsonDeserializer)var4.getValueHandler();
      DeserializationConfig var6 = var1.getConfig();
      TypeDeserializer var7 = (TypeDeserializer)var4.getTypeHandler();
      if (var7 == null) {
         var7 = this.findTypeDeserializer(var6, var4);
      }

      Object var8 = this._findCustomCollectionDeserializer(var2, var6, var3, var7, var5);
      if (var8 == null) {
         Class var9 = var2.getRawClass();
         if (var5 == null && EnumSet.class.isAssignableFrom(var9)) {
            var8 = new EnumSetDeserializer(var4, (JsonDeserializer)null);
         }
      }

      if (var8 == null) {
         if (var2.isInterface() || var2.isAbstract()) {
            CollectionType var11 = this._mapAbstractCollectionType(var2, var6);
            if (var11 == null) {
               if (var2.getTypeHandler() == null) {
                  throw new IllegalArgumentException("Cannot find a deserializer for non-concrete Collection type " + var2);
               }

               var8 = AbstractDeserializer.constructForNonPOJO(var3);
            } else {
               var2 = var11;
               var3 = var6.introspectForCreation(var11);
            }
         }

         if (var8 == null) {
            ValueInstantiator var12 = this.findValueInstantiator(var1, var3);
            if (!var12.canCreateUsingDefault()) {
               if (var2.hasRawClass(ArrayBlockingQueue.class)) {
                  return new ArrayBlockingQueueDeserializer(var2, var5, var7, var12);
               }

               JsonDeserializer var13 = JavaUtilCollectionsDeserializers.findForCollection(var1, var2);
               if (var13 != null) {
                  return var13;
               }
            }

            if (var4.hasRawClass(String.class)) {
               var8 = new StringCollectionDeserializer(var2, var5, var12);
            } else {
               var8 = new CollectionDeserializer(var2, var5, var7, var12);
            }
         }
      }

      BeanDeserializerModifier var10;
      if (this._factoryConfig.hasDeserializerModifiers()) {
         for(Iterator var14 = this._factoryConfig.deserializerModifiers().iterator(); var14.hasNext(); var8 = var10.modifyCollectionDeserializer(var6, var2, var3, (JsonDeserializer)var8)) {
            var10 = (BeanDeserializerModifier)var14.next();
         }
      }

      return (JsonDeserializer)var8;
   }

   protected CollectionType _mapAbstractCollectionType(JavaType var1, DeserializationConfig var2) {
      Class var3 = var1.getRawClass();
      var3 = (Class)_collectionFallbacks.get(var3.getName());
      return var3 == null ? null : (CollectionType)var2.constructSpecializedType(var1, var3);
   }

   public JsonDeserializer createCollectionLikeDeserializer(DeserializationContext var1, CollectionLikeType var2, BeanDescription var3) throws JsonMappingException {
      JavaType var4 = var2.getContentType();
      JsonDeserializer var5 = (JsonDeserializer)var4.getValueHandler();
      DeserializationConfig var6 = var1.getConfig();
      TypeDeserializer var7 = (TypeDeserializer)var4.getTypeHandler();
      if (var7 == null) {
         var7 = this.findTypeDeserializer(var6, var4);
      }

      JsonDeserializer var8 = this._findCustomCollectionLikeDeserializer(var2, var6, var3, var7, var5);
      BeanDeserializerModifier var10;
      if (var8 != null && this._factoryConfig.hasDeserializerModifiers()) {
         for(Iterator var9 = this._factoryConfig.deserializerModifiers().iterator(); var9.hasNext(); var8 = var10.modifyCollectionLikeDeserializer(var6, var2, var3, var8)) {
            var10 = (BeanDeserializerModifier)var9.next();
         }
      }

      return var8;
   }

   public JsonDeserializer createMapDeserializer(DeserializationContext var1, MapType var2, BeanDescription var3) throws JsonMappingException {
      DeserializationConfig var4 = var1.getConfig();
      JavaType var5 = var2.getKeyType();
      JavaType var6 = var2.getContentType();
      JsonDeserializer var7 = (JsonDeserializer)var6.getValueHandler();
      KeyDeserializer var8 = (KeyDeserializer)var5.getValueHandler();
      TypeDeserializer var9 = (TypeDeserializer)var6.getTypeHandler();
      if (var9 == null) {
         var9 = this.findTypeDeserializer(var4, var6);
      }

      Object var10 = this._findCustomMapDeserializer(var2, var4, var3, var8, var9, var7);
      if (var10 == null) {
         Class var11 = var2.getRawClass();
         ValueInstantiator var12;
         if (EnumMap.class.isAssignableFrom(var11)) {
            if (var11 == EnumMap.class) {
               var12 = null;
            } else {
               var12 = this.findValueInstantiator(var1, var3);
            }

            Class var13 = var5.getRawClass();
            if (var13 == null || !var13.isEnum()) {
               throw new IllegalArgumentException("Cannot construct EnumMap; generic (key) type not available");
            }

            var10 = new EnumMapDeserializer(var2, var12, (KeyDeserializer)null, var7, var9, (NullValueProvider)null);
         }

         if (var10 == null) {
            if (!var2.isInterface() && !var2.isAbstract()) {
               var10 = JavaUtilCollectionsDeserializers.findForMap(var1, var2);
               if (var10 != null) {
                  return (JsonDeserializer)var10;
               }
            } else {
               Class var17 = (Class)_mapFallbacks.get(var11.getName());
               if (var17 != null) {
                  var2 = (MapType)var4.constructSpecializedType(var2, var17);
                  var3 = var4.introspectForCreation(var2);
               } else {
                  if (var2.getTypeHandler() == null) {
                     throw new IllegalArgumentException("Cannot find a deserializer for non-concrete Map type " + var2);
                  }

                  var10 = AbstractDeserializer.constructForNonPOJO(var3);
               }
            }

            if (var10 == null) {
               var12 = this.findValueInstantiator(var1, var3);
               MapDeserializer var18 = new MapDeserializer(var2, var12, var8, var7, var9);
               JsonIgnoreProperties$Value var14 = var4.getDefaultPropertyIgnorals(Map.class, var3.getClassInfo());
               Set var15 = var14 == null ? null : var14.findIgnoredForDeserialization();
               var18.setIgnorableProperties(var15);
               var10 = var18;
            }
         }
      }

      BeanDeserializerModifier var19;
      if (this._factoryConfig.hasDeserializerModifiers()) {
         for(Iterator var16 = this._factoryConfig.deserializerModifiers().iterator(); var16.hasNext(); var10 = var19.modifyMapDeserializer(var4, var2, var3, (JsonDeserializer)var10)) {
            var19 = (BeanDeserializerModifier)var16.next();
         }
      }

      return (JsonDeserializer)var10;
   }

   public JsonDeserializer createMapLikeDeserializer(DeserializationContext var1, MapLikeType var2, BeanDescription var3) throws JsonMappingException {
      JavaType var4 = var2.getKeyType();
      JavaType var5 = var2.getContentType();
      DeserializationConfig var6 = var1.getConfig();
      JsonDeserializer var7 = (JsonDeserializer)var5.getValueHandler();
      KeyDeserializer var8 = (KeyDeserializer)var4.getValueHandler();
      TypeDeserializer var9 = (TypeDeserializer)var5.getTypeHandler();
      if (var9 == null) {
         var9 = this.findTypeDeserializer(var6, var5);
      }

      JsonDeserializer var10 = this._findCustomMapLikeDeserializer(var2, var6, var3, var8, var9, var7);
      BeanDeserializerModifier var12;
      if (var10 != null && this._factoryConfig.hasDeserializerModifiers()) {
         for(Iterator var11 = this._factoryConfig.deserializerModifiers().iterator(); var11.hasNext(); var10 = var12.modifyMapLikeDeserializer(var6, var2, var3, var10)) {
            var12 = (BeanDeserializerModifier)var11.next();
         }
      }

      return var10;
   }

   public JsonDeserializer createEnumDeserializer(DeserializationContext var1, JavaType var2, BeanDescription var3) throws JsonMappingException {
      DeserializationConfig var4 = var1.getConfig();
      Class var5 = var2.getRawClass();
      Object var6 = this._findCustomEnumDeserializer(var5, var4, var3);
      if (var6 == null) {
         ValueInstantiator var7 = this._constructDefaultValueInstantiator(var1, var3);
         SettableBeanProperty[] var8 = var7 == null ? null : var7.getFromObjectArguments(var1.getConfig());
         Iterator var9 = var3.getFactoryMethods().iterator();

         while(var9.hasNext()) {
            AnnotatedMethod var10 = (AnnotatedMethod)var9.next();
            if (this._hasCreatorAnnotation(var1, var10)) {
               if (var10.getParameterCount() == 0) {
                  var6 = EnumDeserializer.deserializerForNoArgsCreator(var4, var5, var10);
                  break;
               }

               Class var11 = var10.getRawReturnType();
               if (var11.isAssignableFrom(var5)) {
                  var6 = EnumDeserializer.deserializerForCreator(var4, var5, var10, var7, var8);
                  break;
               }
            }
         }

         if (var6 == null) {
            var6 = new EnumDeserializer(this.constructEnumResolver(var5, var4, var3.findJsonValueAccessor()), var4.isEnabled(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS));
         }
      }

      BeanDeserializerModifier var13;
      if (this._factoryConfig.hasDeserializerModifiers()) {
         for(Iterator var12 = this._factoryConfig.deserializerModifiers().iterator(); var12.hasNext(); var6 = var13.modifyEnumDeserializer(var4, var2, var3, (JsonDeserializer)var6)) {
            var13 = (BeanDeserializerModifier)var12.next();
         }
      }

      return (JsonDeserializer)var6;
   }

   public JsonDeserializer createTreeDeserializer(DeserializationConfig var1, JavaType var2, BeanDescription var3) throws JsonMappingException {
      Class var4 = var2.getRawClass();
      JsonDeserializer var5 = this._findCustomTreeNodeDeserializer(var4, var1, var3);
      return var5 != null ? var5 : JsonNodeDeserializer.getDeserializer(var4);
   }

   public JsonDeserializer createReferenceDeserializer(DeserializationContext var1, ReferenceType var2, BeanDescription var3) throws JsonMappingException {
      JavaType var4 = var2.getContentType();
      JsonDeserializer var5 = (JsonDeserializer)var4.getValueHandler();
      DeserializationConfig var6 = var1.getConfig();
      TypeDeserializer var7 = (TypeDeserializer)var4.getTypeHandler();
      if (var7 == null) {
         var7 = this.findTypeDeserializer(var6, var4);
      }

      JsonDeserializer var8 = this._findCustomReferenceDeserializer(var2, var6, var3, var7, var5);
      if (var8 == null && var2.isTypeOrSubTypeOf(AtomicReference.class)) {
         Class var12 = var2.getRawClass();
         ValueInstantiator var11;
         if (var12 == AtomicReference.class) {
            var11 = null;
         } else {
            var11 = this.findValueInstantiator(var1, var3);
         }

         return new AtomicReferenceDeserializer(var2, var11, var7, var5);
      } else {
         BeanDeserializerModifier var10;
         if (var8 != null && this._factoryConfig.hasDeserializerModifiers()) {
            for(Iterator var9 = this._factoryConfig.deserializerModifiers().iterator(); var9.hasNext(); var8 = var10.modifyReferenceDeserializer(var6, var2, var3, var8)) {
               var10 = (BeanDeserializerModifier)var9.next();
            }
         }

         return var8;
      }
   }

   public TypeDeserializer findTypeDeserializer(DeserializationConfig var1, JavaType var2) throws JsonMappingException {
      BeanDescription var3 = var1.introspectClassAnnotations(var2.getRawClass());
      AnnotatedClass var4 = var3.getClassInfo();
      AnnotationIntrospector var5 = var1.getAnnotationIntrospector();
      TypeResolverBuilder var6 = var5.findTypeResolver(var1, var4, var2);
      Collection var7 = null;
      if (var6 == null) {
         var6 = var1.getDefaultTyper(var2);
         if (var6 == null) {
            return null;
         }
      } else {
         var7 = var1.getSubtypeResolver().collectAndResolveSubtypesByTypeId(var1, var4);
      }

      if (var6.getDefaultImpl() == null && var2.isAbstract()) {
         JavaType var8 = this.mapAbstractType(var1, var2);
         if (var8 != null && !var8.hasRawClass(var2.getRawClass())) {
            var6 = var6.defaultImpl(var8.getRawClass());
         }
      }

      TypeDeserializer var10000;
      try {
         var10000 = var6.buildTypeDeserializer(var1, var2, var7);
      } catch (IllegalArgumentException var10) {
         InvalidDefinitionException var9 = InvalidDefinitionException.from((JsonParser)null, var10.getMessage(), var2);
         var9.initCause(var10);
         throw var9;
      }

      return var10000;
   }

   protected JsonDeserializer findOptionalStdDeserializer(DeserializationContext var1, JavaType var2, BeanDescription var3) throws JsonMappingException {
      return OptionalHandlerFactory.instance.findDeserializer(var2, var1.getConfig(), var3);
   }

   public KeyDeserializer createKeyDeserializer(DeserializationContext var1, JavaType var2) throws JsonMappingException {
      DeserializationConfig var3 = var1.getConfig();
      KeyDeserializer var4 = null;
      if (this._factoryConfig.hasKeyDeserializers()) {
         BeanDescription var5 = var3.introspectClassAnnotations(var2.getRawClass());
         Iterator var6 = this._factoryConfig.keyDeserializers().iterator();

         while(var6.hasNext()) {
            KeyDeserializers var7 = (KeyDeserializers)var6.next();
            var4 = var7.findKeyDeserializer(var2, var3, var5);
            if (var4 != null) {
               break;
            }
         }
      }

      if (var4 == null) {
         if (var2.isEnumType()) {
            var4 = this._createEnumKeyDeserializer(var1, var2);
         } else {
            var4 = StdKeyDeserializers.findStringBasedKeyDeserializer(var3, var2);
         }
      }

      BeanDeserializerModifier var9;
      if (var4 != null && this._factoryConfig.hasDeserializerModifiers()) {
         for(Iterator var8 = this._factoryConfig.deserializerModifiers().iterator(); var8.hasNext(); var4 = var9.modifyKeyDeserializer(var3, var2, var4)) {
            var9 = (BeanDeserializerModifier)var8.next();
         }
      }

      return var4;
   }

   private KeyDeserializer _createEnumKeyDeserializer(DeserializationContext var1, JavaType var2) throws JsonMappingException {
      DeserializationConfig var3 = var1.getConfig();
      Class var4 = var2.getRawClass();
      BeanDescription var5 = var3.introspect(var2);
      KeyDeserializer var6 = this.findKeyDeserializerFromAnnotation(var1, var5.getClassInfo());
      if (var6 != null) {
         return var6;
      } else {
         JsonDeserializer var7 = this._findCustomEnumDeserializer(var4, var3, var5);
         if (var7 != null) {
            return StdKeyDeserializers.constructDelegatingKeyDeserializer(var3, var2, var7);
         } else {
            JsonDeserializer var8 = this.findDeserializerFromAnnotation(var1, var5.getClassInfo());
            if (var8 != null) {
               return StdKeyDeserializers.constructDelegatingKeyDeserializer(var3, var2, var8);
            } else {
               EnumResolver var12 = this.constructEnumResolver(var4, var3, var5.findJsonValueAccessor());
               Iterator var13 = var5.getFactoryMethods().iterator();

               AnnotatedMethod var9;
               do {
                  if (!var13.hasNext()) {
                     return StdKeyDeserializers.constructEnumKeyDeserializer(var12);
                  }

                  var9 = (AnnotatedMethod)var13.next();
               } while(!this._hasCreatorAnnotation(var1, var9));

               int var10 = var9.getParameterCount();
               if (var10 == 1) {
                  Class var11 = var9.getRawReturnType();
                  if (var11.isAssignableFrom(var4)) {
                     if (var9.getRawParameterType(0) != String.class) {
                        throw new IllegalArgumentException("Parameter #0 type for factory method (" + var9 + ") not suitable, must be java.lang.String");
                     }

                     if (var3.canOverrideAccessModifiers()) {
                        ClassUtil.checkAndFixAccess(var9.getMember(), var1.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
                     }

                     return StdKeyDeserializers.constructEnumKeyDeserializer(var12, var9);
                  }
               }

               throw new IllegalArgumentException("Unsuitable method (" + var9 + ") decorated with @JsonCreator (for Enum type " + var4.getName() + ")");
            }
         }
      }
   }

   public TypeDeserializer findPropertyTypeDeserializer(DeserializationConfig var1, JavaType var2, AnnotatedMember var3) throws JsonMappingException {
      AnnotationIntrospector var4 = var1.getAnnotationIntrospector();
      TypeResolverBuilder var5 = var4.findPropertyTypeResolver(var1, var3, var2);
      if (var5 == null) {
         return this.findTypeDeserializer(var1, var2);
      } else {
         Collection var6 = var1.getSubtypeResolver().collectAndResolveSubtypesByTypeId(var1, var3, var2);
         return var5.buildTypeDeserializer(var1, var2, var6);
      }
   }

   public TypeDeserializer findPropertyContentTypeDeserializer(DeserializationConfig var1, JavaType var2, AnnotatedMember var3) throws JsonMappingException {
      AnnotationIntrospector var4 = var1.getAnnotationIntrospector();
      TypeResolverBuilder var5 = var4.findPropertyContentTypeResolver(var1, var3, var2);
      JavaType var6 = var2.getContentType();
      if (var5 == null) {
         return this.findTypeDeserializer(var1, var6);
      } else {
         Collection var7 = var1.getSubtypeResolver().collectAndResolveSubtypesByTypeId(var1, var3, var6);
         return var5.buildTypeDeserializer(var1, var6, var7);
      }
   }

   public JsonDeserializer findDefaultDeserializer(DeserializationContext var1, JavaType var2, BeanDescription var3) throws JsonMappingException {
      Class var4 = var2.getRawClass();
      JavaType var13;
      JavaType var15;
      if (var4 == CLASS_OBJECT) {
         DeserializationConfig var12 = var1.getConfig();
         if (this._factoryConfig.hasAbstractTypeResolvers()) {
            var13 = this._findRemappedType(var12, List.class);
            var15 = this._findRemappedType(var12, Map.class);
         } else {
            var15 = null;
            var13 = null;
         }

         return new UntypedObjectDeserializer(var13, var15);
      } else if (var4 != CLASS_STRING && var4 != CLASS_CHAR_SEQUENCE) {
         if (var4 == CLASS_ITERABLE) {
            TypeFactory var11 = var1.getTypeFactory();
            JavaType[] var14 = var11.findTypeParameters(var2, CLASS_ITERABLE);
            var15 = var14 != null && var14.length == 1 ? var14[0] : TypeFactory.unknownType();
            CollectionType var16 = var11.constructCollectionType(Collection.class, var15);
            return this.createCollectionDeserializer(var1, var16, var3);
         } else if (var4 == CLASS_MAP_ENTRY) {
            JavaType var10 = var2.containedTypeOrUnknown(0);
            var13 = var2.containedTypeOrUnknown(1);
            TypeDeserializer var7 = (TypeDeserializer)var13.getTypeHandler();
            if (var7 == null) {
               var7 = this.findTypeDeserializer(var1.getConfig(), var13);
            }

            JsonDeserializer var8 = (JsonDeserializer)var13.getValueHandler();
            KeyDeserializer var9 = (KeyDeserializer)var10.getValueHandler();
            return new MapEntryDeserializer(var2, var9, var8, var7);
         } else {
            String var5 = var4.getName();
            JsonDeserializer var6;
            if (var4.isPrimitive() || var5.startsWith("java.")) {
               var6 = NumberDeserializers.find(var4, var5);
               if (var6 == null) {
                  var6 = DateDeserializers.find(var4, var5);
               }

               if (var6 != null) {
                  return var6;
               }
            }

            if (var4 == TokenBuffer.class) {
               return new TokenBufferDeserializer();
            } else {
               var6 = this.findOptionalStdDeserializer(var1, var2, var3);
               return var6 != null ? var6 : JdkDeserializers.find(var4, var5);
            }
         }
      } else {
         return StringDeserializer.instance;
      }
   }

   protected JavaType _findRemappedType(DeserializationConfig var1, Class var2) throws JsonMappingException {
      JavaType var3 = this.mapAbstractType(var1, var1.constructType(var2));
      return var3 != null && !var3.hasRawClass(var2) ? var3 : null;
   }

   protected JsonDeserializer _findCustomTreeNodeDeserializer(Class var1, DeserializationConfig var2, BeanDescription var3) throws JsonMappingException {
      Iterator var4 = this._factoryConfig.deserializers().iterator();

      JsonDeserializer var6;
      do {
         if (!var4.hasNext()) {
            return null;
         }

         Deserializers var5 = (Deserializers)var4.next();
         var6 = var5.findTreeNodeDeserializer(var1, var2, var3);
      } while(var6 == null);

      return var6;
   }

   protected JsonDeserializer _findCustomReferenceDeserializer(ReferenceType var1, DeserializationConfig var2, BeanDescription var3, TypeDeserializer var4, JsonDeserializer var5) throws JsonMappingException {
      Iterator var6 = this._factoryConfig.deserializers().iterator();

      JsonDeserializer var8;
      do {
         if (!var6.hasNext()) {
            return null;
         }

         Deserializers var7 = (Deserializers)var6.next();
         var8 = var7.findReferenceDeserializer(var1, var2, var3, var4, var5);
      } while(var8 == null);

      return var8;
   }

   protected JsonDeserializer _findCustomBeanDeserializer(JavaType var1, DeserializationConfig var2, BeanDescription var3) throws JsonMappingException {
      Iterator var4 = this._factoryConfig.deserializers().iterator();

      JsonDeserializer var6;
      do {
         if (!var4.hasNext()) {
            return null;
         }

         Deserializers var5 = (Deserializers)var4.next();
         var6 = var5.findBeanDeserializer(var1, var2, var3);
      } while(var6 == null);

      return var6;
   }

   protected JsonDeserializer _findCustomArrayDeserializer(ArrayType var1, DeserializationConfig var2, BeanDescription var3, TypeDeserializer var4, JsonDeserializer var5) throws JsonMappingException {
      Iterator var6 = this._factoryConfig.deserializers().iterator();

      JsonDeserializer var8;
      do {
         if (!var6.hasNext()) {
            return null;
         }

         Deserializers var7 = (Deserializers)var6.next();
         var8 = var7.findArrayDeserializer(var1, var2, var3, var4, var5);
      } while(var8 == null);

      return var8;
   }

   protected JsonDeserializer _findCustomCollectionDeserializer(CollectionType var1, DeserializationConfig var2, BeanDescription var3, TypeDeserializer var4, JsonDeserializer var5) throws JsonMappingException {
      Iterator var6 = this._factoryConfig.deserializers().iterator();

      JsonDeserializer var8;
      do {
         if (!var6.hasNext()) {
            return null;
         }

         Deserializers var7 = (Deserializers)var6.next();
         var8 = var7.findCollectionDeserializer(var1, var2, var3, var4, var5);
      } while(var8 == null);

      return var8;
   }

   protected JsonDeserializer _findCustomCollectionLikeDeserializer(CollectionLikeType var1, DeserializationConfig var2, BeanDescription var3, TypeDeserializer var4, JsonDeserializer var5) throws JsonMappingException {
      Iterator var6 = this._factoryConfig.deserializers().iterator();

      JsonDeserializer var8;
      do {
         if (!var6.hasNext()) {
            return null;
         }

         Deserializers var7 = (Deserializers)var6.next();
         var8 = var7.findCollectionLikeDeserializer(var1, var2, var3, var4, var5);
      } while(var8 == null);

      return var8;
   }

   protected JsonDeserializer _findCustomEnumDeserializer(Class var1, DeserializationConfig var2, BeanDescription var3) throws JsonMappingException {
      Iterator var4 = this._factoryConfig.deserializers().iterator();

      JsonDeserializer var6;
      do {
         if (!var4.hasNext()) {
            return null;
         }

         Deserializers var5 = (Deserializers)var4.next();
         var6 = var5.findEnumDeserializer(var1, var2, var3);
      } while(var6 == null);

      return var6;
   }

   protected JsonDeserializer _findCustomMapDeserializer(MapType var1, DeserializationConfig var2, BeanDescription var3, KeyDeserializer var4, TypeDeserializer var5, JsonDeserializer var6) throws JsonMappingException {
      Iterator var7 = this._factoryConfig.deserializers().iterator();

      JsonDeserializer var9;
      do {
         if (!var7.hasNext()) {
            return null;
         }

         Deserializers var8 = (Deserializers)var7.next();
         var9 = var8.findMapDeserializer(var1, var2, var3, var4, var5, var6);
      } while(var9 == null);

      return var9;
   }

   protected JsonDeserializer _findCustomMapLikeDeserializer(MapLikeType var1, DeserializationConfig var2, BeanDescription var3, KeyDeserializer var4, TypeDeserializer var5, JsonDeserializer var6) throws JsonMappingException {
      Iterator var7 = this._factoryConfig.deserializers().iterator();

      JsonDeserializer var9;
      do {
         if (!var7.hasNext()) {
            return null;
         }

         Deserializers var8 = (Deserializers)var7.next();
         var9 = var8.findMapLikeDeserializer(var1, var2, var3, var4, var5, var6);
      } while(var9 == null);

      return var9;
   }

   protected JsonDeserializer findDeserializerFromAnnotation(DeserializationContext var1, Annotated var2) throws JsonMappingException {
      AnnotationIntrospector var3 = var1.getAnnotationIntrospector();
      if (var3 != null) {
         Object var4 = var3.findDeserializer(var2);
         if (var4 != null) {
            return var1.deserializerInstance(var2, var4);
         }
      }

      return null;
   }

   protected KeyDeserializer findKeyDeserializerFromAnnotation(DeserializationContext var1, Annotated var2) throws JsonMappingException {
      AnnotationIntrospector var3 = var1.getAnnotationIntrospector();
      if (var3 != null) {
         Object var4 = var3.findKeyDeserializer(var2);
         if (var4 != null) {
            return var1.keyDeserializerInstance(var2, var4);
         }
      }

      return null;
   }

   protected JsonDeserializer findContentDeserializerFromAnnotation(DeserializationContext var1, Annotated var2) throws JsonMappingException {
      AnnotationIntrospector var3 = var1.getAnnotationIntrospector();
      if (var3 != null) {
         Object var4 = var3.findContentDeserializer(var2);
         if (var4 != null) {
            return var1.deserializerInstance(var2, var4);
         }
      }

      return null;
   }

   protected JavaType resolveMemberAndTypeAnnotations(DeserializationContext var1, AnnotatedMember var2, JavaType var3) throws JsonMappingException {
      AnnotationIntrospector var4 = var1.getAnnotationIntrospector();
      if (var4 == null) {
         return (JavaType)var3;
      } else {
         if (((JavaType)var3).isMapLikeType()) {
            JavaType var5 = ((JavaType)var3).getKeyType();
            if (var5 != null) {
               Object var6 = var4.findKeyDeserializer(var2);
               KeyDeserializer var7 = var1.keyDeserializerInstance(var2, var6);
               if (var7 != null) {
                  var3 = ((MapLikeType)var3).withKeyValueHandler(var7);
                  var5 = ((JavaType)var3).getKeyType();
               }
            }
         }

         if (((JavaType)var3).hasContentType()) {
            Object var8 = var4.findContentDeserializer(var2);
            JsonDeserializer var11 = var1.deserializerInstance(var2, var8);
            if (var11 != null) {
               var3 = ((JavaType)var3).withContentValueHandler(var11);
            }

            TypeDeserializer var12 = this.findPropertyContentTypeDeserializer(var1.getConfig(), (JavaType)var3, var2);
            if (var12 != null) {
               var3 = ((JavaType)var3).withContentTypeHandler(var12);
            }
         }

         TypeDeserializer var9 = this.findPropertyTypeDeserializer(var1.getConfig(), (JavaType)var3, var2);
         if (var9 != null) {
            var3 = ((JavaType)var3).withTypeHandler(var9);
         }

         JavaType var10 = var4.refineDeserializationType(var1.getConfig(), var2, (JavaType)var3);
         return var10;
      }
   }

   protected EnumResolver constructEnumResolver(Class var1, DeserializationConfig var2, AnnotatedMember var3) {
      if (var3 != null) {
         if (var2.canOverrideAccessModifiers()) {
            ClassUtil.checkAndFixAccess(var3.getMember(), var2.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
         }

         return EnumResolver.constructUnsafeUsingMethod(var1, var3, var2.getAnnotationIntrospector());
      } else {
         return EnumResolver.constructUnsafe(var1, var2.getAnnotationIntrospector());
      }
   }

   protected boolean _hasCreatorAnnotation(DeserializationContext var1, Annotated var2) {
      AnnotationIntrospector var3 = var1.getAnnotationIntrospector();
      if (var3 == null) {
         return false;
      } else {
         JsonCreator$Mode var4 = var3.findCreatorAnnotation(var1.getConfig(), var2);
         return var4 != null && var4 != JsonCreator$Mode.DISABLED;
      }
   }

   /** @deprecated */
   @Deprecated
   protected JavaType modifyTypeByAnnotation(DeserializationContext var1, Annotated var2, JavaType var3) throws JsonMappingException {
      AnnotationIntrospector var4 = var1.getAnnotationIntrospector();
      return var4 == null ? var3 : var4.refineDeserializationType(var1.getConfig(), var2, var3);
   }

   /** @deprecated */
   @Deprecated
   protected JavaType resolveType(DeserializationContext var1, BeanDescription var2, JavaType var3, AnnotatedMember var4) throws JsonMappingException {
      return this.resolveMemberAndTypeAnnotations(var1, var4, var3);
   }

   /** @deprecated */
   @Deprecated
   protected AnnotatedMethod _findJsonValueFor(DeserializationConfig var1, JavaType var2) {
      if (var2 == null) {
         return null;
      } else {
         BeanDescription var3 = var1.introspect(var2);
         return var3.findJsonValueMethod();
      }
   }

   static {
      _mapFallbacks.put(Map.class.getName(), LinkedHashMap.class);
      _mapFallbacks.put(ConcurrentMap.class.getName(), ConcurrentHashMap.class);
      _mapFallbacks.put(SortedMap.class.getName(), TreeMap.class);
      _mapFallbacks.put(NavigableMap.class.getName(), TreeMap.class);
      _mapFallbacks.put(ConcurrentNavigableMap.class.getName(), ConcurrentSkipListMap.class);
      _collectionFallbacks = new HashMap();
      _collectionFallbacks.put(Collection.class.getName(), ArrayList.class);
      _collectionFallbacks.put(List.class.getName(), ArrayList.class);
      _collectionFallbacks.put(Set.class.getName(), HashSet.class);
      _collectionFallbacks.put(SortedSet.class.getName(), TreeSet.class);
      _collectionFallbacks.put(Queue.class.getName(), LinkedList.class);
      _collectionFallbacks.put("java.util.Deque", LinkedList.class);
      _collectionFallbacks.put("java.util.NavigableSet", TreeSet.class);
   }
}
