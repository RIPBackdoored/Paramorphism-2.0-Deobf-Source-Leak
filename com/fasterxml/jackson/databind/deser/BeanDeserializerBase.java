package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.annotation.JsonFormat$Feature;
import com.fasterxml.jackson.annotation.JsonFormat$Shape;
import com.fasterxml.jackson.annotation.JsonFormat$Value;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties$Value;
import com.fasterxml.jackson.annotation.JsonTypeInfo$As;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators$PropertyGenerator;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonParser$NumberType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.BeanProperty$Std;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyMetadata$MergeInfo;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;
import com.fasterxml.jackson.databind.deser.impl.ExternalTypeHandler;
import com.fasterxml.jackson.databind.deser.impl.ExternalTypeHandler$Builder;
import com.fasterxml.jackson.databind.deser.impl.InnerClassProperty;
import com.fasterxml.jackson.databind.deser.impl.ManagedReferenceProperty;
import com.fasterxml.jackson.databind.deser.impl.MergingSettableBeanProperty;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReferenceProperty;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdValueProperty;
import com.fasterxml.jackson.databind.deser.impl.PropertyBasedCreator;
import com.fasterxml.jackson.databind.deser.impl.PropertyBasedObjectIdGenerator;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
import com.fasterxml.jackson.databind.deser.impl.SetterlessProperty;
import com.fasterxml.jackson.databind.deser.impl.TypeWrappedDeserializer;
import com.fasterxml.jackson.databind.deser.impl.UnwrappedPropertyHandler;
import com.fasterxml.jackson.databind.deser.impl.ValueInjector;
import com.fasterxml.jackson.databind.deser.std.StdDelegatingDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.ClassKey;
import com.fasterxml.jackson.databind.util.AccessPattern;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.Converter;
import com.fasterxml.jackson.databind.util.NameTransformer;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class BeanDeserializerBase extends StdDeserializer implements ContextualDeserializer, ResolvableDeserializer, ValueInstantiator$Gettable, Serializable {
   private static final long serialVersionUID = 1L;
   protected static final PropertyName TEMP_PROPERTY_NAME = new PropertyName("#temporary-name");
   protected final JavaType _beanType;
   protected final JsonFormat$Shape _serializationShape;
   protected final ValueInstantiator _valueInstantiator;
   protected JsonDeserializer _delegateDeserializer;
   protected JsonDeserializer _arrayDelegateDeserializer;
   protected PropertyBasedCreator _propertyBasedCreator;
   protected boolean _nonStandardCreation;
   protected boolean _vanillaProcessing;
   protected final BeanPropertyMap _beanProperties;
   protected final ValueInjector[] _injectables;
   protected SettableAnyProperty _anySetter;
   protected final Set _ignorableProps;
   protected final boolean _ignoreAllUnknown;
   protected final boolean _needViewProcesing;
   protected final Map _backRefs;
   protected transient HashMap _subDeserializers;
   protected UnwrappedPropertyHandler _unwrappedPropertyHandler;
   protected ExternalTypeHandler _externalTypeIdHandler;
   protected final ObjectIdReader _objectIdReader;

   protected BeanDeserializerBase(BeanDeserializerBuilder var1, BeanDescription var2, BeanPropertyMap var3, Map var4, Set var5, boolean var6, boolean var7) {
      super(var2.getType());
      this._beanType = var2.getType();
      this._valueInstantiator = var1.getValueInstantiator();
      this._beanProperties = var3;
      this._backRefs = var4;
      this._ignorableProps = var5;
      this._ignoreAllUnknown = var6;
      this._anySetter = var1.getAnySetter();
      List var8 = var1.getInjectables();
      this._injectables = var8 != null && !var8.isEmpty() ? (ValueInjector[])var8.toArray(new ValueInjector[var8.size()]) : null;
      this._objectIdReader = var1.getObjectIdReader();
      this._nonStandardCreation = this._unwrappedPropertyHandler != null || this._valueInstantiator.canCreateUsingDelegate() || this._valueInstantiator.canCreateUsingArrayDelegate() || this._valueInstantiator.canCreateFromObjectWith() || !this._valueInstantiator.canCreateUsingDefault();
      JsonFormat$Value var9 = var2.findExpectedFormat((JsonFormat$Value)null);
      this._serializationShape = var9 == null ? null : var9.getShape();
      this._needViewProcesing = var7;
      this._vanillaProcessing = !this._nonStandardCreation && this._injectables == null && !this._needViewProcesing && this._objectIdReader == null;
   }

   protected BeanDeserializerBase(BeanDeserializerBase var1) {
      this(var1, var1._ignoreAllUnknown);
   }

   protected BeanDeserializerBase(BeanDeserializerBase var1, boolean var2) {
      super(var1._beanType);
      this._beanType = var1._beanType;
      this._valueInstantiator = var1._valueInstantiator;
      this._delegateDeserializer = var1._delegateDeserializer;
      this._propertyBasedCreator = var1._propertyBasedCreator;
      this._beanProperties = var1._beanProperties;
      this._backRefs = var1._backRefs;
      this._ignorableProps = var1._ignorableProps;
      this._ignoreAllUnknown = var2;
      this._anySetter = var1._anySetter;
      this._injectables = var1._injectables;
      this._objectIdReader = var1._objectIdReader;
      this._nonStandardCreation = var1._nonStandardCreation;
      this._unwrappedPropertyHandler = var1._unwrappedPropertyHandler;
      this._needViewProcesing = var1._needViewProcesing;
      this._serializationShape = var1._serializationShape;
      this._vanillaProcessing = var1._vanillaProcessing;
   }

   protected BeanDeserializerBase(BeanDeserializerBase var1, NameTransformer var2) {
      super(var1._beanType);
      this._beanType = var1._beanType;
      this._valueInstantiator = var1._valueInstantiator;
      this._delegateDeserializer = var1._delegateDeserializer;
      this._propertyBasedCreator = var1._propertyBasedCreator;
      this._backRefs = var1._backRefs;
      this._ignorableProps = var1._ignorableProps;
      this._ignoreAllUnknown = var2 != null || var1._ignoreAllUnknown;
      this._anySetter = var1._anySetter;
      this._injectables = var1._injectables;
      this._objectIdReader = var1._objectIdReader;
      this._nonStandardCreation = var1._nonStandardCreation;
      UnwrappedPropertyHandler var3 = var1._unwrappedPropertyHandler;
      if (var2 != null) {
         if (var3 != null) {
            var3 = var3.renameAll(var2);
         }

         this._beanProperties = var1._beanProperties.renameAll(var2);
      } else {
         this._beanProperties = var1._beanProperties;
      }

      this._unwrappedPropertyHandler = var3;
      this._needViewProcesing = var1._needViewProcesing;
      this._serializationShape = var1._serializationShape;
      this._vanillaProcessing = false;
   }

   public BeanDeserializerBase(BeanDeserializerBase var1, ObjectIdReader var2) {
      super(var1._beanType);
      this._beanType = var1._beanType;
      this._valueInstantiator = var1._valueInstantiator;
      this._delegateDeserializer = var1._delegateDeserializer;
      this._propertyBasedCreator = var1._propertyBasedCreator;
      this._backRefs = var1._backRefs;
      this._ignorableProps = var1._ignorableProps;
      this._ignoreAllUnknown = var1._ignoreAllUnknown;
      this._anySetter = var1._anySetter;
      this._injectables = var1._injectables;
      this._nonStandardCreation = var1._nonStandardCreation;
      this._unwrappedPropertyHandler = var1._unwrappedPropertyHandler;
      this._needViewProcesing = var1._needViewProcesing;
      this._serializationShape = var1._serializationShape;
      this._objectIdReader = var2;
      if (var2 == null) {
         this._beanProperties = var1._beanProperties;
         this._vanillaProcessing = var1._vanillaProcessing;
      } else {
         ObjectIdValueProperty var3 = new ObjectIdValueProperty(var2, PropertyMetadata.STD_REQUIRED);
         this._beanProperties = var1._beanProperties.withProperty(var3);
         this._vanillaProcessing = false;
      }

   }

   public BeanDeserializerBase(BeanDeserializerBase var1, Set var2) {
      super(var1._beanType);
      this._beanType = var1._beanType;
      this._valueInstantiator = var1._valueInstantiator;
      this._delegateDeserializer = var1._delegateDeserializer;
      this._propertyBasedCreator = var1._propertyBasedCreator;
      this._backRefs = var1._backRefs;
      this._ignorableProps = var2;
      this._ignoreAllUnknown = var1._ignoreAllUnknown;
      this._anySetter = var1._anySetter;
      this._injectables = var1._injectables;
      this._nonStandardCreation = var1._nonStandardCreation;
      this._unwrappedPropertyHandler = var1._unwrappedPropertyHandler;
      this._needViewProcesing = var1._needViewProcesing;
      this._serializationShape = var1._serializationShape;
      this._vanillaProcessing = var1._vanillaProcessing;
      this._objectIdReader = var1._objectIdReader;
      this._beanProperties = var1._beanProperties.withoutProperties(var2);
   }

   protected BeanDeserializerBase(BeanDeserializerBase var1, BeanPropertyMap var2) {
      super(var1._beanType);
      this._beanType = var1._beanType;
      this._valueInstantiator = var1._valueInstantiator;
      this._delegateDeserializer = var1._delegateDeserializer;
      this._propertyBasedCreator = var1._propertyBasedCreator;
      this._beanProperties = var2;
      this._backRefs = var1._backRefs;
      this._ignorableProps = var1._ignorableProps;
      this._ignoreAllUnknown = var1._ignoreAllUnknown;
      this._anySetter = var1._anySetter;
      this._injectables = var1._injectables;
      this._objectIdReader = var1._objectIdReader;
      this._nonStandardCreation = var1._nonStandardCreation;
      this._unwrappedPropertyHandler = var1._unwrappedPropertyHandler;
      this._needViewProcesing = var1._needViewProcesing;
      this._serializationShape = var1._serializationShape;
      this._vanillaProcessing = var1._vanillaProcessing;
   }

   public abstract JsonDeserializer unwrappingDeserializer(NameTransformer var1);

   public abstract BeanDeserializerBase withObjectIdReader(ObjectIdReader var1);

   public abstract BeanDeserializerBase withIgnorableProperties(Set var1);

   public BeanDeserializerBase withBeanProperties(BeanPropertyMap var1) {
      throw new UnsupportedOperationException("Class " + this.getClass().getName() + " does not override `withBeanProperties()`, needs to");
   }

   protected abstract BeanDeserializerBase asArrayDeserializer();

   public void resolve(DeserializationContext var1) throws JsonMappingException {
      ExternalTypeHandler$Builder var2 = null;
      SettableBeanProperty[] var3;
      SettableBeanProperty var6;
      if (this._valueInstantiator.canCreateFromObjectWith()) {
         var3 = this._valueInstantiator.getFromObjectArguments(var1.getConfig());
         if (this._ignorableProps != null) {
            int var4 = 0;

            for(int var5 = var3.length; var4 < var5; ++var4) {
               var6 = var3[var4];
               if (this._ignorableProps.contains(var6.getName())) {
                  var3[var4].markAsIgnorable();
               }
            }
         }
      } else {
         var3 = null;
      }

      UnwrappedPropertyHandler var12 = null;
      Iterator var13 = this._beanProperties.iterator();

      while(var13.hasNext()) {
         var6 = (SettableBeanProperty)var13.next();
         if (!var6.hasValueDeserializer()) {
            JsonDeserializer var7 = this.findConvertingDeserializer(var1, var6);
            if (var7 == null) {
               var7 = var1.findNonContextualValueDeserializer(var6.getType());
            }

            SettableBeanProperty var8 = var6.withValueDeserializer(var7);
            this._replaceProperty(this._beanProperties, var3, var6, var8);
         }
      }

      var13 = this._beanProperties.iterator();

      while(var13.hasNext()) {
         var6 = (SettableBeanProperty)var13.next();
         JsonDeserializer var16 = var6.getValueDeserializer();
         var16 = var1.handlePrimaryContextualization(var16, var6, var6.getType());
         SettableBeanProperty var15 = var6.withValueDeserializer(var16);
         var15 = this._resolveManagedReferenceProperty(var1, var15);
         if (!(var15 instanceof ManagedReferenceProperty)) {
            var15 = this._resolvedObjectIdProperty(var1, var15);
         }

         NameTransformer var9 = this._findPropertyUnwrapper(var1, var15);
         if (var9 != null) {
            JsonDeserializer var10 = var15.getValueDeserializer();
            JsonDeserializer var11 = var10.unwrappingDeserializer(var9);
            if (var11 != var10 && var11 != null) {
               var15 = var15.withValueDeserializer(var11);
               if (var12 == null) {
                  var12 = new UnwrappedPropertyHandler();
               }

               var12.addProperty(var15);
               this._beanProperties.remove(var15);
               continue;
            }
         }

         PropertyMetadata var17 = var15.getMetadata();
         var15 = this._resolveMergeAndNullSettings(var1, var15, var17);
         var15 = this._resolveInnerClassValuedProperty(var1, var15);
         if (var15 != var6) {
            this._replaceProperty(this._beanProperties, var3, var6, var15);
         }

         if (var15.hasValueTypeDeserializer()) {
            TypeDeserializer var18 = var15.getValueTypeDeserializer();
            if (var18.getTypeInclusion() == JsonTypeInfo$As.EXTERNAL_PROPERTY) {
               if (var2 == null) {
                  var2 = ExternalTypeHandler.builder(this._beanType);
               }

               var2.addExternal(var15, var18);
               this._beanProperties.remove(var15);
            }
         }
      }

      if (this._anySetter != null && !this._anySetter.hasValueDeserializer()) {
         this._anySetter = this._anySetter.withValueDeserializer(this.findDeserializer(var1, this._anySetter.getType(), this._anySetter.getProperty()));
      }

      JavaType var14;
      if (this._valueInstantiator.canCreateUsingDelegate()) {
         var14 = this._valueInstantiator.getDelegateType(var1.getConfig());
         if (var14 == null) {
            var1.reportBadDefinition(this._beanType, String.format("Invalid delegate-creator definition for %s: value instantiator (%s) returned true for 'canCreateUsingDelegate()', but null for 'getDelegateType()'", this._beanType, this._valueInstantiator.getClass().getName()));
         }

         this._delegateDeserializer = this._findDelegateDeserializer(var1, var14, this._valueInstantiator.getDelegateCreator());
      }

      if (this._valueInstantiator.canCreateUsingArrayDelegate()) {
         var14 = this._valueInstantiator.getArrayDelegateType(var1.getConfig());
         if (var14 == null) {
            var1.reportBadDefinition(this._beanType, String.format("Invalid delegate-creator definition for %s: value instantiator (%s) returned true for 'canCreateUsingArrayDelegate()', but null for 'getArrayDelegateType()'", this._beanType, this._valueInstantiator.getClass().getName()));
         }

         this._arrayDelegateDeserializer = this._findDelegateDeserializer(var1, var14, this._valueInstantiator.getArrayDelegateCreator());
      }

      if (var3 != null) {
         this._propertyBasedCreator = PropertyBasedCreator.construct(var1, this._valueInstantiator, var3, this._beanProperties);
      }

      if (var2 != null) {
         this._externalTypeIdHandler = var2.build(this._beanProperties);
         this._nonStandardCreation = true;
      }

      this._unwrappedPropertyHandler = var12;
      if (var12 != null) {
         this._nonStandardCreation = true;
      }

      this._vanillaProcessing = this._vanillaProcessing && !this._nonStandardCreation;
   }

   protected void _replaceProperty(BeanPropertyMap var1, SettableBeanProperty[] var2, SettableBeanProperty var3, SettableBeanProperty var4) {
      var1.replace(var3, var4);
      if (var2 != null) {
         int var5 = 0;

         for(int var6 = var2.length; var5 < var6; ++var5) {
            if (var2[var5] == var3) {
               var2[var5] = var4;
               return;
            }
         }
      }

   }

   private JsonDeserializer _findDelegateDeserializer(DeserializationContext var1, JavaType var2, AnnotatedWithParams var3) throws JsonMappingException {
      BeanProperty$Std var4 = new BeanProperty$Std(TEMP_PROPERTY_NAME, var2, (PropertyName)null, var3, PropertyMetadata.STD_OPTIONAL);
      TypeDeserializer var5 = (TypeDeserializer)var2.getTypeHandler();
      if (var5 == null) {
         var5 = var1.getConfig().findTypeDeserializer(var2);
      }

      JsonDeserializer var6 = (JsonDeserializer)var2.getValueHandler();
      if (var6 == null) {
         var6 = this.findDeserializer(var1, var2, var4);
      } else {
         var6 = var1.handleSecondaryContextualization(var6, var4, var2);
      }

      if (var5 != null) {
         var5 = var5.forProperty(var4);
         return new TypeWrappedDeserializer(var5, var6);
      } else {
         return var6;
      }
   }

   protected JsonDeserializer findConvertingDeserializer(DeserializationContext var1, SettableBeanProperty var2) throws JsonMappingException {
      AnnotationIntrospector var3 = var1.getAnnotationIntrospector();
      if (var3 != null) {
         Object var4 = var3.findDeserializationConverter(var2.getMember());
         if (var4 != null) {
            Converter var5 = var1.converterInstance(var2.getMember(), var4);
            JavaType var6 = var5.getInputType(var1.getTypeFactory());
            JsonDeserializer var7 = var1.findNonContextualValueDeserializer(var6);
            return new StdDelegatingDeserializer(var5, var6, var7);
         }
      }

      return null;
   }

   public JsonDeserializer createContextual(DeserializationContext var1, BeanProperty var2) throws JsonMappingException {
      ObjectIdReader var3 = this._objectIdReader;
      AnnotationIntrospector var4 = var1.getAnnotationIntrospector();
      AnnotatedMember var5 = _neitherNull(var2, var4) ? var2.getMember() : null;
      if (var5 != null) {
         ObjectIdInfo var6 = var4.findObjectIdInfo(var5);
         if (var6 != null) {
            var6 = var4.findObjectReferenceInfo(var5, var6);
            Class var7 = var6.getGeneratorType();
            ObjectIdResolver var11 = var1.objectIdResolverInstance(var5, var6);
            JavaType var8;
            SettableBeanProperty var9;
            Object var10;
            if (var7 == ObjectIdGenerators$PropertyGenerator.class) {
               PropertyName var12 = var6.getPropertyName();
               var9 = this.findProperty(var12);
               if (var9 == null) {
                  var1.reportBadDefinition(this._beanType, String.format("Invalid Object Id definition for %s: cannot find property with name '%s'", this.handledType().getName(), var12));
               }

               var8 = var9.getType();
               var10 = new PropertyBasedObjectIdGenerator(var6.getScope());
            } else {
               JavaType var22 = var1.constructType(var7);
               var8 = var1.getTypeFactory().findTypeParameters(var22, ObjectIdGenerator.class)[0];
               var9 = null;
               var10 = var1.objectIdGeneratorInstance(var5, var6);
            }

            JsonDeserializer var23 = var1.findRootValueDeserializer(var8);
            var3 = ObjectIdReader.construct(var8, var6.getPropertyName(), (ObjectIdGenerator)var10, var23, var9, var11);
         }
      }

      BeanDeserializerBase var13 = this;
      if (var3 != null && var3 != this._objectIdReader) {
         var13 = this.withObjectIdReader(var3);
      }

      if (var5 != null) {
         JsonIgnoreProperties$Value var14 = var4.findPropertyIgnorals(var5);
         if (var14 != null) {
            Object var16 = var14.findIgnoredForDeserialization();
            if (!((Set)var16).isEmpty()) {
               Set var18 = var13._ignorableProps;
               if (var18 != null && !var18.isEmpty()) {
                  var16 = new HashSet((Collection)var16);
                  ((Set)var16).addAll(var18);
               }

               var13 = var13.withIgnorableProperties((Set)var16);
            }
         }
      }

      JsonFormat$Value var15 = this.findFormatOverrides(var1, var2, this.handledType());
      JsonFormat$Shape var17 = null;
      if (var15 != null) {
         if (var15.hasShape()) {
            var17 = var15.getShape();
         }

         Boolean var19 = var15.getFeature(JsonFormat$Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES);
         if (var19 != null) {
            BeanPropertyMap var20 = this._beanProperties;
            BeanPropertyMap var21 = var20.withCaseInsensitivity(var19);
            if (var21 != var20) {
               var13 = var13.withBeanProperties(var21);
            }
         }
      }

      if (var17 == null) {
         var17 = this._serializationShape;
      }

      if (var17 == JsonFormat$Shape.ARRAY) {
         var13 = var13.asArrayDeserializer();
      }

      return var13;
   }

   protected SettableBeanProperty _resolveManagedReferenceProperty(DeserializationContext var1, SettableBeanProperty var2) throws JsonMappingException {
      String var3 = var2.getManagedReferenceName();
      if (var3 == null) {
         return var2;
      } else {
         JsonDeserializer var4 = var2.getValueDeserializer();
         SettableBeanProperty var5 = var4.findBackReference(var3);
         if (var5 == null) {
            var1.reportBadDefinition(this._beanType, String.format("Cannot handle managed/back reference '%s': no back reference property found from type %s", var3, var2.getType()));
         }

         JavaType var6 = this._beanType;
         JavaType var7 = var5.getType();
         boolean var8 = var2.getType().isContainerType();
         if (!var7.getRawClass().isAssignableFrom(var6.getRawClass())) {
            var1.reportBadDefinition(this._beanType, String.format("Cannot handle managed/back reference '%s': back reference type (%s) not compatible with managed type (%s)", var3, var7.getRawClass().getName(), var6.getRawClass().getName()));
         }

         return new ManagedReferenceProperty(var2, var3, var5, var8);
      }
   }

   protected SettableBeanProperty _resolvedObjectIdProperty(DeserializationContext var1, SettableBeanProperty var2) throws JsonMappingException {
      ObjectIdInfo var3 = var2.getObjectIdInfo();
      JsonDeserializer var4 = var2.getValueDeserializer();
      ObjectIdReader var5 = var4 == null ? null : var4.getObjectIdReader();
      return (SettableBeanProperty)(var3 == null && var5 == null ? var2 : new ObjectIdReferenceProperty(var2, var3));
   }

   protected NameTransformer _findPropertyUnwrapper(DeserializationContext var1, SettableBeanProperty var2) throws JsonMappingException {
      AnnotatedMember var3 = var2.getMember();
      if (var3 != null) {
         NameTransformer var4 = var1.getAnnotationIntrospector().findUnwrappingNameTransformer(var3);
         if (var4 != null) {
            if (var2 instanceof CreatorProperty) {
               var1.reportBadDefinition(this.getValueType(), String.format("Cannot define Creator property \"%s\" as `@JsonUnwrapped`: combination not yet supported", var2.getName()));
            }

            return var4;
         }
      }

      return null;
   }

   protected SettableBeanProperty _resolveInnerClassValuedProperty(DeserializationContext var1, SettableBeanProperty var2) {
      JsonDeserializer var3 = var2.getValueDeserializer();
      if (var3 instanceof BeanDeserializerBase) {
         BeanDeserializerBase var4 = (BeanDeserializerBase)var3;
         ValueInstantiator var5 = var4.getValueInstantiator();
         if (!var5.canCreateUsingDefault()) {
            Class var6 = var2.getType().getRawClass();
            Class var7 = ClassUtil.getOuterClass(var6);
            if (var7 != null && var7 == this._beanType.getRawClass()) {
               Constructor[] var8 = var6.getConstructors();
               int var9 = var8.length;

               for(int var10 = 0; var10 < var9; ++var10) {
                  Constructor var11 = var8[var10];
                  Class[] var12 = var11.getParameterTypes();
                  if (var12.length == 1 && var7.equals(var12[0])) {
                     if (var1.canOverrideAccessModifiers()) {
                        ClassUtil.checkAndFixAccess(var11, var1.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
                     }

                     return new InnerClassProperty(var2, var11);
                  }
               }
            }
         }
      }

      return var2;
   }

   protected SettableBeanProperty _resolveMergeAndNullSettings(DeserializationContext var1, SettableBeanProperty var2, PropertyMetadata var3) throws JsonMappingException {
      PropertyMetadata$MergeInfo var4 = var3.getMergeInfo();
      if (var4 != null) {
         JsonDeserializer var5 = ((SettableBeanProperty)var2).getValueDeserializer();
         Boolean var6 = var5.supportsUpdate(var1.getConfig());
         if (var6 == null) {
            if (var4.fromDefaults) {
               return (SettableBeanProperty)var2;
            }
         } else if (!var6) {
            if (!var4.fromDefaults) {
               var1.reportBadMerge(var5);
            }

            return (SettableBeanProperty)var2;
         }

         AnnotatedMember var7 = var4.getter;
         var7.fixAccess(var1.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
         if (!(var2 instanceof SetterlessProperty)) {
            var2 = MergingSettableBeanProperty.construct((SettableBeanProperty)var2, var7);
         }
      }

      NullValueProvider var8 = this.findValueNullProvider(var1, (SettableBeanProperty)var2, var3);
      if (var8 != null) {
         var2 = ((SettableBeanProperty)var2).withNullProvider(var8);
      }

      return (SettableBeanProperty)var2;
   }

   public AccessPattern getNullAccessPattern() {
      return AccessPattern.ALWAYS_NULL;
   }

   public AccessPattern getEmptyAccessPattern() {
      return AccessPattern.DYNAMIC;
   }

   public Object getEmptyValue(DeserializationContext var1) throws JsonMappingException {
      Object var10000;
      try {
         var10000 = this._valueInstantiator.createUsingDefault(var1);
      } catch (IOException var3) {
         return ClassUtil.throwAsMappingException(var1, var3);
      }

      return var10000;
   }

   public boolean isCachable() {
      return true;
   }

   public Boolean supportsUpdate(DeserializationConfig var1) {
      return Boolean.TRUE;
   }

   public Class handledType() {
      return this._beanType.getRawClass();
   }

   public ObjectIdReader getObjectIdReader() {
      return this._objectIdReader;
   }

   public boolean hasProperty(String var1) {
      return this._beanProperties.find(var1) != null;
   }

   public boolean hasViews() {
      return this._needViewProcesing;
   }

   public int getPropertyCount() {
      return this._beanProperties.size();
   }

   public Collection getKnownPropertyNames() {
      ArrayList var1 = new ArrayList();
      Iterator var2 = this._beanProperties.iterator();

      while(var2.hasNext()) {
         SettableBeanProperty var3 = (SettableBeanProperty)var2.next();
         var1.add(var3.getName());
      }

      return var1;
   }

   /** @deprecated */
   @Deprecated
   public final Class getBeanClass() {
      return this._beanType.getRawClass();
   }

   public JavaType getValueType() {
      return this._beanType;
   }

   public Iterator properties() {
      if (this._beanProperties == null) {
         throw new IllegalStateException("Can only call after BeanDeserializer has been resolved");
      } else {
         return this._beanProperties.iterator();
      }
   }

   public Iterator creatorProperties() {
      return this._propertyBasedCreator == null ? Collections.emptyList().iterator() : this._propertyBasedCreator.properties().iterator();
   }

   public SettableBeanProperty findProperty(PropertyName var1) {
      return this.findProperty(var1.getSimpleName());
   }

   public SettableBeanProperty findProperty(String var1) {
      SettableBeanProperty var2 = this._beanProperties == null ? null : this._beanProperties.find(var1);
      if (var2 == null && this._propertyBasedCreator != null) {
         var2 = this._propertyBasedCreator.findCreatorProperty(var1);
      }

      return var2;
   }

   public SettableBeanProperty findProperty(int var1) {
      SettableBeanProperty var2 = this._beanProperties == null ? null : this._beanProperties.find(var1);
      if (var2 == null && this._propertyBasedCreator != null) {
         var2 = this._propertyBasedCreator.findCreatorProperty(var1);
      }

      return var2;
   }

   public SettableBeanProperty findBackReference(String var1) {
      return this._backRefs == null ? null : (SettableBeanProperty)this._backRefs.get(var1);
   }

   public ValueInstantiator getValueInstantiator() {
      return this._valueInstantiator;
   }

   public void replaceProperty(SettableBeanProperty var1, SettableBeanProperty var2) {
      this._beanProperties.replace(var1, var2);
   }

   public abstract Object deserializeFromObject(JsonParser var1, DeserializationContext var2) throws IOException;

   public Object deserializeWithType(JsonParser var1, DeserializationContext var2, TypeDeserializer var3) throws IOException {
      if (this._objectIdReader != null) {
         if (var1.canReadObjectId()) {
            Object var4 = var1.getObjectId();
            if (var4 != null) {
               Object var5 = var3.deserializeTypedFromObject(var1, var2);
               return this._handleTypedObjectId(var1, var2, var5, var4);
            }
         }

         JsonToken var6 = var1.getCurrentToken();
         if (var6 != null) {
            if (var6.isScalarValue()) {
               return this.deserializeFromObjectId(var1, var2);
            }

            if (var6 == JsonToken.START_OBJECT) {
               var6 = var1.nextToken();
            }

            if (var6 == JsonToken.FIELD_NAME && this._objectIdReader.maySerializeAsObject() && this._objectIdReader.isValidReferencePropertyName(var1.getCurrentName(), var1)) {
               return this.deserializeFromObjectId(var1, var2);
            }
         }
      }

      return var3.deserializeTypedFromObject(var1, var2);
   }

   protected Object _handleTypedObjectId(JsonParser var1, DeserializationContext var2, Object var3, Object var4) throws IOException {
      JsonDeserializer var5 = this._objectIdReader.getDeserializer();
      Object var6;
      if (var5.handledType() == var4.getClass()) {
         var6 = var4;
      } else {
         var6 = this._convertObjectId(var1, var2, var4, var5);
      }

      ReadableObjectId var7 = var2.findObjectId(var6, this._objectIdReader.generator, this._objectIdReader.resolver);
      var7.bindItem(var3);
      SettableBeanProperty var8 = this._objectIdReader.idProperty;
      return var8 != null ? var8.setAndReturn(var3, var6) : var3;
   }

   protected Object _convertObjectId(JsonParser var1, DeserializationContext var2, Object var3, JsonDeserializer var4) throws IOException {
      TokenBuffer var5 = new TokenBuffer(var1, var2);
      if (var3 instanceof String) {
         var5.writeString((String)var3);
      } else if (var3 instanceof Long) {
         var5.writeNumber((Long)var3);
      } else if (var3 instanceof Integer) {
         var5.writeNumber((Integer)var3);
      } else {
         var5.writeObject(var3);
      }

      JsonParser var6 = var5.asParser();
      var6.nextToken();
      return var4.deserialize(var6, var2);
   }

   protected Object deserializeWithObjectId(JsonParser var1, DeserializationContext var2) throws IOException {
      return this.deserializeFromObject(var1, var2);
   }

   protected Object deserializeFromObjectId(JsonParser var1, DeserializationContext var2) throws IOException {
      Object var3 = this._objectIdReader.readObjectReference(var1, var2);
      ReadableObjectId var4 = var2.findObjectId(var3, this._objectIdReader.generator, this._objectIdReader.resolver);
      Object var5 = var4.resolve();
      if (var5 == null) {
         throw new UnresolvedForwardReference(var1, "Could not resolve Object Id [" + var3 + "] (for " + this._beanType + ").", var1.getCurrentLocation(), var4);
      } else {
         return var5;
      }
   }

   protected Object deserializeFromObjectUsingNonDefault(JsonParser var1, DeserializationContext var2) throws IOException {
      JsonDeserializer var3 = this._delegateDeserializer();
      if (var3 != null) {
         return this._valueInstantiator.createUsingDelegate(var2, var3.deserialize(var1, var2));
      } else if (this._propertyBasedCreator != null) {
         return this._deserializeUsingPropertyBased(var1, var2);
      } else {
         Class var4 = this._beanType.getRawClass();
         return ClassUtil.isNonStaticInnerClass(var4) ? var2.handleMissingInstantiator(var4, (ValueInstantiator)null, var1, "can only instantiate non-static inner class by using default, no-argument constructor") : var2.handleMissingInstantiator(var4, this.getValueInstantiator(), var1, "cannot deserialize from Object value (no delegate- or property-based Creator)");
      }
   }

   protected abstract Object _deserializeUsingPropertyBased(JsonParser var1, DeserializationContext var2) throws IOException;

   public Object deserializeFromNumber(JsonParser var1, DeserializationContext var2) throws IOException {
      if (this._objectIdReader != null) {
         return this.deserializeFromObjectId(var1, var2);
      } else {
         JsonDeserializer var3 = this._delegateDeserializer();
         JsonParser$NumberType var4 = var1.getNumberType();
         Object var5;
         if (var4 == JsonParser$NumberType.INT) {
            if (var3 != null && !this._valueInstantiator.canCreateFromInt()) {
               var5 = this._valueInstantiator.createUsingDelegate(var2, var3.deserialize(var1, var2));
               if (this._injectables != null) {
                  this.injectValues(var2, var5);
               }

               return var5;
            } else {
               return this._valueInstantiator.createFromInt(var2, var1.getIntValue());
            }
         } else if (var4 == JsonParser$NumberType.LONG) {
            if (var3 != null && !this._valueInstantiator.canCreateFromInt()) {
               var5 = this._valueInstantiator.createUsingDelegate(var2, var3.deserialize(var1, var2));
               if (this._injectables != null) {
                  this.injectValues(var2, var5);
               }

               return var5;
            } else {
               return this._valueInstantiator.createFromLong(var2, var1.getLongValue());
            }
         } else if (var3 != null) {
            var5 = this._valueInstantiator.createUsingDelegate(var2, var3.deserialize(var1, var2));
            if (this._injectables != null) {
               this.injectValues(var2, var5);
            }

            return var5;
         } else {
            return var2.handleMissingInstantiator(this.handledType(), this.getValueInstantiator(), var1, "no suitable creator method found to deserialize from Number value (%s)", var1.getNumberValue());
         }
      }
   }

   public Object deserializeFromString(JsonParser var1, DeserializationContext var2) throws IOException {
      if (this._objectIdReader != null) {
         return this.deserializeFromObjectId(var1, var2);
      } else {
         JsonDeserializer var3 = this._delegateDeserializer();
         if (var3 != null && !this._valueInstantiator.canCreateFromString()) {
            Object var4 = this._valueInstantiator.createUsingDelegate(var2, var3.deserialize(var1, var2));
            if (this._injectables != null) {
               this.injectValues(var2, var4);
            }

            return var4;
         } else {
            return this._valueInstantiator.createFromString(var2, var1.getText());
         }
      }
   }

   public Object deserializeFromDouble(JsonParser var1, DeserializationContext var2) throws IOException {
      JsonParser$NumberType var3 = var1.getNumberType();
      JsonDeserializer var4;
      if (var3 != JsonParser$NumberType.DOUBLE && var3 != JsonParser$NumberType.FLOAT) {
         var4 = this._delegateDeserializer();
         return var4 != null ? this._valueInstantiator.createUsingDelegate(var2, var4.deserialize(var1, var2)) : var2.handleMissingInstantiator(this.handledType(), this.getValueInstantiator(), var1, "no suitable creator method found to deserialize from Number value (%s)", var1.getNumberValue());
      } else {
         var4 = this._delegateDeserializer();
         if (var4 != null && !this._valueInstantiator.canCreateFromDouble()) {
            Object var5 = this._valueInstantiator.createUsingDelegate(var2, var4.deserialize(var1, var2));
            if (this._injectables != null) {
               this.injectValues(var2, var5);
            }

            return var5;
         } else {
            return this._valueInstantiator.createFromDouble(var2, var1.getDoubleValue());
         }
      }
   }

   public Object deserializeFromBoolean(JsonParser var1, DeserializationContext var2) throws IOException {
      JsonDeserializer var3 = this._delegateDeserializer();
      if (var3 != null && !this._valueInstantiator.canCreateFromBoolean()) {
         Object var5 = this._valueInstantiator.createUsingDelegate(var2, var3.deserialize(var1, var2));
         if (this._injectables != null) {
            this.injectValues(var2, var5);
         }

         return var5;
      } else {
         boolean var4 = var1.getCurrentToken() == JsonToken.VALUE_TRUE;
         return this._valueInstantiator.createFromBoolean(var2, var4);
      }
   }

   public Object deserializeFromArray(JsonParser var1, DeserializationContext var2) throws IOException {
      JsonDeserializer var3 = this._arrayDelegateDeserializer;
      if (var3 == null && (var3 = this._delegateDeserializer) == null) {
         JsonToken var6;
         if (var2.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
            var6 = var1.nextToken();
            if (var6 == JsonToken.END_ARRAY && var2.isEnabled(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT)) {
               return null;
            } else {
               Object var5 = this.deserialize(var1, var2);
               if (var1.nextToken() != JsonToken.END_ARRAY) {
                  this.handleMissingEndArrayForSingle(var1, var2);
               }

               return var5;
            }
         } else if (var2.isEnabled(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT)) {
            var6 = var1.nextToken();
            return var6 == JsonToken.END_ARRAY ? null : var2.handleUnexpectedToken(this.handledType(), JsonToken.START_ARRAY, var1, (String)null);
         } else {
            return var2.handleUnexpectedToken(this.handledType(), var1);
         }
      } else {
         Object var4 = this._valueInstantiator.createUsingArrayDelegate(var2, var3.deserialize(var1, var2));
         if (this._injectables != null) {
            this.injectValues(var2, var4);
         }

         return var4;
      }
   }

   public Object deserializeFromEmbedded(JsonParser var1, DeserializationContext var2) throws IOException {
      if (this._objectIdReader != null) {
         return this.deserializeFromObjectId(var1, var2);
      } else {
         JsonDeserializer var3 = this._delegateDeserializer();
         Object var4;
         if (var3 != null && !this._valueInstantiator.canCreateFromString()) {
            var4 = this._valueInstantiator.createUsingDelegate(var2, var3.deserialize(var1, var2));
            if (this._injectables != null) {
               this.injectValues(var2, var4);
            }

            return var4;
         } else {
            var4 = var1.getEmbeddedObject();
            if (var4 != null && !this._beanType.isTypeOrSuperTypeOf(var4.getClass())) {
               var4 = var2.handleWeirdNativeValue(this._beanType, var4, var1);
            }

            return var4;
         }
      }
   }

   private final JsonDeserializer _delegateDeserializer() {
      JsonDeserializer var1 = this._delegateDeserializer;
      if (var1 == null) {
         var1 = this._arrayDelegateDeserializer;
      }

      return var1;
   }

   protected void injectValues(DeserializationContext var1, Object var2) throws IOException {
      ValueInjector[] var3 = this._injectables;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ValueInjector var6 = var3[var5];
         var6.inject(var1, var2);
      }

   }

   protected Object handleUnknownProperties(DeserializationContext var1, Object var2, TokenBuffer var3) throws IOException {
      var3.writeEndObject();
      JsonParser var4 = var3.asParser();

      while(var4.nextToken() != JsonToken.END_OBJECT) {
         String var5 = var4.getCurrentName();
         var4.nextToken();
         this.handleUnknownProperty(var4, var1, var2, var5);
      }

      return var2;
   }

   protected void handleUnknownVanilla(JsonParser var1, DeserializationContext var2, Object var3, String var4) throws IOException {
      if (this._ignorableProps != null && this._ignorableProps.contains(var4)) {
         this.handleIgnoredProperty(var1, var2, var3, var4);
      } else if (this._anySetter != null) {
         try {
            this._anySetter.deserializeAndSet(var1, var2, var3, var4);
         } catch (Exception var6) {
            this.wrapAndThrow(var6, var3, var4, var2);
         }
      } else {
         this.handleUnknownProperty(var1, var2, var3, var4);
      }

   }

   protected void handleUnknownProperty(JsonParser var1, DeserializationContext var2, Object var3, String var4) throws IOException {
      if (this._ignoreAllUnknown) {
         var1.skipChildren();
      } else {
         if (this._ignorableProps != null && this._ignorableProps.contains(var4)) {
            this.handleIgnoredProperty(var1, var2, var3, var4);
         }

         super.handleUnknownProperty(var1, var2, var3, var4);
      }
   }

   protected void handleIgnoredProperty(JsonParser var1, DeserializationContext var2, Object var3, String var4) throws IOException {
      if (var2.isEnabled(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES)) {
         throw IgnoredPropertyException.from(var1, var3, var4, this.getKnownPropertyNames());
      } else {
         var1.skipChildren();
      }
   }

   protected Object handlePolymorphic(JsonParser var1, DeserializationContext var2, Object var3, TokenBuffer var4) throws IOException {
      JsonDeserializer var5 = this._findSubclassDeserializer(var2, var3, var4);
      if (var5 != null) {
         if (var4 != null) {
            var4.writeEndObject();
            JsonParser var6 = var4.asParser();
            var6.nextToken();
            var3 = var5.deserialize(var6, var2, var3);
         }

         if (var1 != null) {
            var3 = var5.deserialize(var1, var2, var3);
         }

         return var3;
      } else {
         if (var4 != null) {
            var3 = this.handleUnknownProperties(var2, var3, var4);
         }

         if (var1 != null) {
            var3 = this.deserialize(var1, var2, var3);
         }

         return var3;
      }
   }

   protected JsonDeserializer _findSubclassDeserializer(DeserializationContext var1, Object var2, TokenBuffer var3) throws IOException {
      JsonDeserializer var4;
      synchronized(this) {
         var4 = this._subDeserializers == null ? null : (JsonDeserializer)this._subDeserializers.get(new ClassKey(var2.getClass()));
      }

      if (var4 != null) {
         return var4;
      } else {
         JavaType var5 = var1.constructType(var2.getClass());
         var4 = var1.findRootValueDeserializer(var5);
         if (var4 != null) {
            synchronized(this) {
               if (this._subDeserializers == null) {
                  this._subDeserializers = new HashMap();
               }

               this._subDeserializers.put(new ClassKey(var2.getClass()), var4);
            }
         }

         return var4;
      }
   }

   public void wrapAndThrow(Throwable var1, Object var2, String var3, DeserializationContext var4) throws IOException {
      throw JsonMappingException.wrapWithPath(this.throwOrReturnThrowable(var1, var4), var2, var3);
   }

   private Throwable throwOrReturnThrowable(Throwable var1, DeserializationContext var2) throws IOException {
      while(var1 instanceof InvocationTargetException && var1.getCause() != null) {
         var1 = var1.getCause();
      }

      ClassUtil.throwIfError(var1);
      boolean var3 = var2 == null || var2.isEnabled(DeserializationFeature.WRAP_EXCEPTIONS);
      if (var1 instanceof IOException) {
         if (!var3 || !(var1 instanceof JsonProcessingException)) {
            throw (IOException)var1;
         }
      } else if (!var3) {
         ClassUtil.throwIfRTE(var1);
      }

      return var1;
   }

   protected Object wrapInstantiationProblem(Throwable var1, DeserializationContext var2) throws IOException {
      while(var1 instanceof InvocationTargetException && var1.getCause() != null) {
         var1 = var1.getCause();
      }

      ClassUtil.throwIfError(var1);
      if (var1 instanceof IOException) {
         throw (IOException)var1;
      } else {
         boolean var3 = var2 == null || var2.isEnabled(DeserializationFeature.WRAP_EXCEPTIONS);
         if (!var3) {
            ClassUtil.throwIfRTE(var1);
         }

         return var2.handleInstantiationProblem(this._beanType.getRawClass(), (Object)null, var1);
      }
   }
}
