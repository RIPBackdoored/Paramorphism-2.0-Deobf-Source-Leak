package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder$Value;
import com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdValueProperty;
import com.fasterxml.jackson.databind.deser.impl.ValueInjector;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.util.Annotations;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BeanDeserializerBuilder {
   protected final DeserializationConfig _config;
   protected final DeserializationContext _context;
   protected final BeanDescription _beanDesc;
   protected final Map _properties = new LinkedHashMap();
   protected List _injectables;
   protected HashMap _backRefProperties;
   protected HashSet _ignorableProps;
   protected ValueInstantiator _valueInstantiator;
   protected ObjectIdReader _objectIdReader;
   protected SettableAnyProperty _anySetter;
   protected boolean _ignoreAllUnknown;
   protected AnnotatedMethod _buildMethod;
   protected JsonPOJOBuilder$Value _builderConfig;

   public BeanDeserializerBuilder(BeanDescription var1, DeserializationContext var2) {
      super();
      this._beanDesc = var1;
      this._context = var2;
      this._config = var2.getConfig();
   }

   protected BeanDeserializerBuilder(BeanDeserializerBuilder var1) {
      super();
      this._beanDesc = var1._beanDesc;
      this._context = var1._context;
      this._config = var1._config;
      this._properties.putAll(var1._properties);
      this._injectables = _copy(var1._injectables);
      this._backRefProperties = _copy(var1._backRefProperties);
      this._ignorableProps = var1._ignorableProps;
      this._valueInstantiator = var1._valueInstantiator;
      this._objectIdReader = var1._objectIdReader;
      this._anySetter = var1._anySetter;
      this._ignoreAllUnknown = var1._ignoreAllUnknown;
      this._buildMethod = var1._buildMethod;
      this._builderConfig = var1._builderConfig;
   }

   private static HashMap _copy(HashMap var0) {
      return var0 == null ? null : new HashMap(var0);
   }

   private static List _copy(List var0) {
      return var0 == null ? null : new ArrayList(var0);
   }

   public void addOrReplaceProperty(SettableBeanProperty var1, boolean var2) {
      this._properties.put(var1.getName(), var1);
   }

   public void addProperty(SettableBeanProperty var1) {
      SettableBeanProperty var2 = (SettableBeanProperty)this._properties.put(var1.getName(), var1);
      if (var2 != null && var2 != var1) {
         throw new IllegalArgumentException("Duplicate property '" + var1.getName() + "' for " + this._beanDesc.getType());
      }
   }

   public void addBackReferenceProperty(String var1, SettableBeanProperty var2) {
      if (this._backRefProperties == null) {
         this._backRefProperties = new HashMap(4);
      }

      var2.fixAccess(this._config);
      this._backRefProperties.put(var1, var2);
   }

   public void addInjectable(PropertyName var1, JavaType var2, Annotations var3, AnnotatedMember var4, Object var5) {
      if (this._injectables == null) {
         this._injectables = new ArrayList();
      }

      boolean var6 = this._config.canOverrideAccessModifiers();
      boolean var7 = var6 && this._config.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS);
      if (var6) {
         var4.fixAccess(var7);
      }

      this._injectables.add(new ValueInjector(var1, var2, var4, var5));
   }

   public void addIgnorable(String var1) {
      if (this._ignorableProps == null) {
         this._ignorableProps = new HashSet();
      }

      this._ignorableProps.add(var1);
   }

   public void addCreatorProperty(SettableBeanProperty var1) {
      this.addProperty(var1);
   }

   public void setAnySetter(SettableAnyProperty var1) {
      if (this._anySetter != null && var1 != null) {
         throw new IllegalStateException("_anySetter already set to non-null");
      } else {
         this._anySetter = var1;
      }
   }

   public void setIgnoreUnknownProperties(boolean var1) {
      this._ignoreAllUnknown = var1;
   }

   public void setValueInstantiator(ValueInstantiator var1) {
      this._valueInstantiator = var1;
   }

   public void setObjectIdReader(ObjectIdReader var1) {
      this._objectIdReader = var1;
   }

   public void setPOJOBuilder(AnnotatedMethod var1, JsonPOJOBuilder$Value var2) {
      this._buildMethod = var1;
      this._builderConfig = var2;
   }

   public Iterator getProperties() {
      return this._properties.values().iterator();
   }

   public SettableBeanProperty findProperty(PropertyName var1) {
      return (SettableBeanProperty)this._properties.get(var1.getSimpleName());
   }

   public boolean hasProperty(PropertyName var1) {
      return this.findProperty(var1) != null;
   }

   public SettableBeanProperty removeProperty(PropertyName var1) {
      return (SettableBeanProperty)this._properties.remove(var1.getSimpleName());
   }

   public SettableAnyProperty getAnySetter() {
      return this._anySetter;
   }

   public ValueInstantiator getValueInstantiator() {
      return this._valueInstantiator;
   }

   public List getInjectables() {
      return this._injectables;
   }

   public ObjectIdReader getObjectIdReader() {
      return this._objectIdReader;
   }

   public AnnotatedMethod getBuildMethod() {
      return this._buildMethod;
   }

   public JsonPOJOBuilder$Value getBuilderConfig() {
      return this._builderConfig;
   }

   public boolean hasIgnorable(String var1) {
      return this._ignorableProps != null && this._ignorableProps.contains(var1);
   }

   public JsonDeserializer build() {
      Collection var1 = this._properties.values();
      this._fixAccess(var1);
      BeanPropertyMap var2 = BeanPropertyMap.construct(var1, this._config.isEnabled(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES), this._collectAliases(var1));
      var2.assignIndexes();
      boolean var3 = !this._config.isEnabled(MapperFeature.DEFAULT_VIEW_INCLUSION);
      if (!var3) {
         Iterator var4 = var1.iterator();

         while(var4.hasNext()) {
            SettableBeanProperty var5 = (SettableBeanProperty)var4.next();
            if (var5.hasViews()) {
               var3 = true;
               break;
            }
         }
      }

      if (this._objectIdReader != null) {
         ObjectIdValueProperty var6 = new ObjectIdValueProperty(this._objectIdReader, PropertyMetadata.STD_REQUIRED);
         var2 = var2.withProperty(var6);
      }

      return new BeanDeserializer(this, this._beanDesc, var2, this._backRefProperties, this._ignorableProps, this._ignoreAllUnknown, var3);
   }

   public AbstractDeserializer buildAbstract() {
      return new AbstractDeserializer(this, this._beanDesc, this._backRefProperties, this._properties);
   }

   public JsonDeserializer buildBuilderBased(JavaType var1, String var2) throws JsonMappingException {
      if (this._buildMethod == null) {
         if (!var2.isEmpty()) {
            this._context.reportBadDefinition(this._beanDesc.getType(), String.format("Builder class %s does not have build method (name: '%s')", this._beanDesc.getBeanClass().getName(), var2));
         }
      } else {
         Class var3 = this._buildMethod.getRawReturnType();
         Class var4 = var1.getRawClass();
         if (var3 != var4 && !var3.isAssignableFrom(var4) && !var4.isAssignableFrom(var3)) {
            this._context.reportBadDefinition(this._beanDesc.getType(), String.format("Build method '%s' has wrong return type (%s), not compatible with POJO type (%s)", this._buildMethod.getFullName(), var3.getName(), var1.getRawClass().getName()));
         }
      }

      Collection var8 = this._properties.values();
      this._fixAccess(var8);
      BeanPropertyMap var9 = BeanPropertyMap.construct(var8, this._config.isEnabled(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES), this._collectAliases(var8));
      var9.assignIndexes();
      boolean var5 = !this._config.isEnabled(MapperFeature.DEFAULT_VIEW_INCLUSION);
      if (!var5) {
         Iterator var6 = var8.iterator();

         while(var6.hasNext()) {
            SettableBeanProperty var7 = (SettableBeanProperty)var6.next();
            if (var7.hasViews()) {
               var5 = true;
               break;
            }
         }
      }

      if (this._objectIdReader != null) {
         ObjectIdValueProperty var10 = new ObjectIdValueProperty(this._objectIdReader, PropertyMetadata.STD_REQUIRED);
         var9 = var9.withProperty(var10);
      }

      return new BuilderBasedDeserializer(this, this._beanDesc, var1, var9, this._backRefProperties, this._ignorableProps, this._ignoreAllUnknown, var5);
   }

   protected void _fixAccess(Collection var1) {
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         SettableBeanProperty var3 = (SettableBeanProperty)var2.next();
         var3.fixAccess(this._config);
      }

      if (this._anySetter != null) {
         this._anySetter.fixAccess(this._config);
      }

      if (this._buildMethod != null) {
         this._buildMethod.fixAccess(this._config.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
      }

   }

   protected Map _collectAliases(Collection var1) {
      HashMap var2 = null;
      AnnotationIntrospector var3 = this._config.getAnnotationIntrospector();
      if (var3 != null) {
         Iterator var4 = var1.iterator();

         while(var4.hasNext()) {
            SettableBeanProperty var5 = (SettableBeanProperty)var4.next();
            List var6 = var3.findPropertyAliases(var5.getMember());
            if (var6 != null && !var6.isEmpty()) {
               if (var2 == null) {
                  var2 = new HashMap();
               }

               var2.put(var5.getName(), var6);
            }
         }
      }

      return (Map)(var2 == null ? Collections.emptyMap() : var2);
   }
}
