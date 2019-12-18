package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.annotation.JacksonInject$Value;
import com.fasterxml.jackson.annotation.JsonCreator$Mode;
import com.fasterxml.jackson.annotation.JsonProperty$Access;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

public class POJOPropertiesCollector {
   protected final MapperConfig _config;
   protected final boolean _forSerialization;
   protected final boolean _stdBeanNaming;
   protected final JavaType _type;
   protected final AnnotatedClass _classDef;
   protected final VisibilityChecker _visibilityChecker;
   protected final AnnotationIntrospector _annotationIntrospector;
   protected final boolean _useAnnotations;
   protected final String _mutatorPrefix;
   protected boolean _collected;
   protected LinkedHashMap _properties;
   protected LinkedList _creatorProperties;
   protected LinkedList _anyGetters;
   protected LinkedList _anySetters;
   protected LinkedList _anySetterField;
   protected LinkedList _jsonValueAccessors;
   protected HashSet _ignoredPropertyNames;
   protected LinkedHashMap _injectables;

   protected POJOPropertiesCollector(MapperConfig var1, boolean var2, JavaType var3, AnnotatedClass var4, String var5) {
      super();
      this._config = var1;
      this._stdBeanNaming = var1.isEnabled(MapperFeature.USE_STD_BEAN_NAMING);
      this._forSerialization = var2;
      this._type = var3;
      this._classDef = var4;
      this._mutatorPrefix = var5 == null ? "set" : var5;
      if (var1.isAnnotationProcessingEnabled()) {
         this._useAnnotations = true;
         this._annotationIntrospector = this._config.getAnnotationIntrospector();
      } else {
         this._useAnnotations = false;
         this._annotationIntrospector = AnnotationIntrospector.nopInstance();
      }

      this._visibilityChecker = this._config.getDefaultVisibilityChecker(var3.getRawClass(), var4);
   }

   public MapperConfig getConfig() {
      return this._config;
   }

   public JavaType getType() {
      return this._type;
   }

   public AnnotatedClass getClassDef() {
      return this._classDef;
   }

   public AnnotationIntrospector getAnnotationIntrospector() {
      return this._annotationIntrospector;
   }

   public List getProperties() {
      Map var1 = this.getPropertyMap();
      return new ArrayList(var1.values());
   }

   public Map getInjectables() {
      if (!this._collected) {
         this.collectAll();
      }

      return this._injectables;
   }

   /** @deprecated */
   @Deprecated
   public AnnotatedMethod getJsonValueMethod() {
      AnnotatedMember var1 = this.getJsonValueAccessor();
      return var1 instanceof AnnotatedMethod ? (AnnotatedMethod)var1 : null;
   }

   public AnnotatedMember getJsonValueAccessor() {
      if (!this._collected) {
         this.collectAll();
      }

      if (this._jsonValueAccessors != null) {
         if (this._jsonValueAccessors.size() > 1) {
            this.reportProblem("Multiple 'as-value' properties defined (%s vs %s)", this._jsonValueAccessors.get(0), this._jsonValueAccessors.get(1));
         }

         return (AnnotatedMember)this._jsonValueAccessors.get(0);
      } else {
         return null;
      }
   }

   public AnnotatedMember getAnyGetter() {
      if (!this._collected) {
         this.collectAll();
      }

      if (this._anyGetters != null) {
         if (this._anyGetters.size() > 1) {
            this.reportProblem("Multiple 'any-getters' defined (%s vs %s)", this._anyGetters.get(0), this._anyGetters.get(1));
         }

         return (AnnotatedMember)this._anyGetters.getFirst();
      } else {
         return null;
      }
   }

   public AnnotatedMember getAnySetterField() {
      if (!this._collected) {
         this.collectAll();
      }

      if (this._anySetterField != null) {
         if (this._anySetterField.size() > 1) {
            this.reportProblem("Multiple 'any-setter' fields defined (%s vs %s)", this._anySetterField.get(0), this._anySetterField.get(1));
         }

         return (AnnotatedMember)this._anySetterField.getFirst();
      } else {
         return null;
      }
   }

   public AnnotatedMethod getAnySetterMethod() {
      if (!this._collected) {
         this.collectAll();
      }

      if (this._anySetters != null) {
         if (this._anySetters.size() > 1) {
            this.reportProblem("Multiple 'any-setter' methods defined (%s vs %s)", this._anySetters.get(0), this._anySetters.get(1));
         }

         return (AnnotatedMethod)this._anySetters.getFirst();
      } else {
         return null;
      }
   }

   public Set getIgnoredPropertyNames() {
      return this._ignoredPropertyNames;
   }

   public ObjectIdInfo getObjectIdInfo() {
      ObjectIdInfo var1 = this._annotationIntrospector.findObjectIdInfo(this._classDef);
      if (var1 != null) {
         var1 = this._annotationIntrospector.findObjectReferenceInfo(this._classDef, var1);
      }

      return var1;
   }

   public Class findPOJOBuilderClass() {
      return this._annotationIntrospector.findPOJOBuilder(this._classDef);
   }

   protected Map getPropertyMap() {
      if (!this._collected) {
         this.collectAll();
      }

      return this._properties;
   }

   protected void collectAll() {
      LinkedHashMap var1 = new LinkedHashMap();
      this._addFields(var1);
      this._addMethods(var1);
      if (!this._classDef.isNonStaticInnerClass()) {
         this._addCreators(var1);
      }

      this._addInjectables(var1);
      this._removeUnwantedProperties(var1);
      this._removeUnwantedAccessor(var1);
      this._renameProperties(var1);
      Iterator var2 = var1.values().iterator();

      while(var2.hasNext()) {
         POJOPropertyBuilder var3 = (POJOPropertyBuilder)var2.next();
         var3.mergeAnnotations(this._forSerialization);
      }

      PropertyNamingStrategy var5 = this._findNamingStrategy();
      if (var5 != null) {
         this._renameUsing(var1, var5);
      }

      Iterator var6 = var1.values().iterator();

      while(var6.hasNext()) {
         POJOPropertyBuilder var4 = (POJOPropertyBuilder)var6.next();
         var4.trimByVisibility();
      }

      if (this._config.isEnabled(MapperFeature.USE_WRAPPER_NAME_AS_PROPERTY_NAME)) {
         this._renameWithWrappers(var1);
      }

      this._sortProperties(var1);
      this._properties = var1;
      this._collected = true;
   }

   protected void _addFields(Map var1) {
      AnnotationIntrospector var2 = this._annotationIntrospector;
      boolean var3 = !this._forSerialization && !this._config.isEnabled(MapperFeature.ALLOW_FINAL_FIELDS_AS_MUTATORS);
      boolean var4 = this._config.isEnabled(MapperFeature.PROPAGATE_TRANSIENT_MARKER);
      Iterator var5 = this._classDef.fields().iterator();

      while(true) {
         while(var5.hasNext()) {
            AnnotatedField var6 = (AnnotatedField)var5.next();
            String var7 = var2.findImplicitPropertyName(var6);
            if (Boolean.TRUE.equals(var2.hasAsValue(var6))) {
               if (this._jsonValueAccessors == null) {
                  this._jsonValueAccessors = new LinkedList();
               }

               this._jsonValueAccessors.add(var6);
            } else if (Boolean.TRUE.equals(var2.hasAnySetter(var6))) {
               if (this._anySetterField == null) {
                  this._anySetterField = new LinkedList();
               }

               this._anySetterField.add(var6);
            } else {
               if (var7 == null) {
                  var7 = var6.getName();
               }

               PropertyName var8;
               if (this._forSerialization) {
                  var8 = var2.findNameForSerialization(var6);
               } else {
                  var8 = var2.findNameForDeserialization(var6);
               }

               boolean var9 = var8 != null;
               boolean var10 = var9;
               if (var9 && var8.isEmpty()) {
                  var8 = this._propNameFromSimple(var7);
                  var10 = false;
               }

               boolean var11 = var8 != null;
               if (!var11) {
                  var11 = this._visibilityChecker.isFieldVisible(var6);
               }

               boolean var12 = var2.hasIgnoreMarker(var6);
               if (var6.isTransient() && !var9) {
                  var11 = false;
                  if (var4) {
                     var12 = true;
                  }
               }

               if (!var3 || var8 != null || var12 || !Modifier.isFinal(var6.getModifiers())) {
                  this._property(var1, var7).addField(var6, var8, var10, var11, var12);
               }
            }
         }

         return;
      }
   }

   protected void _addCreators(Map var1) {
      if (this._useAnnotations) {
         Iterator var2 = this._classDef.getConstructors().iterator();

         int var4;
         int var5;
         while(var2.hasNext()) {
            AnnotatedConstructor var3 = (AnnotatedConstructor)var2.next();
            if (this._creatorProperties == null) {
               this._creatorProperties = new LinkedList();
            }

            var4 = 0;

            for(var5 = var3.getParameterCount(); var4 < var5; ++var4) {
               this._addCreatorParam(var1, var3.getParameter(var4));
            }
         }

         var2 = this._classDef.getFactoryMethods().iterator();

         while(var2.hasNext()) {
            AnnotatedMethod var6 = (AnnotatedMethod)var2.next();
            if (this._creatorProperties == null) {
               this._creatorProperties = new LinkedList();
            }

            var4 = 0;

            for(var5 = var6.getParameterCount(); var4 < var5; ++var4) {
               this._addCreatorParam(var1, var6.getParameter(var4));
            }
         }

      }
   }

   protected void _addCreatorParam(Map var1, AnnotatedParameter var2) {
      String var3 = this._annotationIntrospector.findImplicitPropertyName(var2);
      if (var3 == null) {
         var3 = "";
      }

      PropertyName var4 = this._annotationIntrospector.findNameForDeserialization(var2);
      boolean var5 = var4 != null && !var4.isEmpty();
      if (!var5) {
         if (var3.isEmpty()) {
            return;
         }

         JsonCreator$Mode var6 = this._annotationIntrospector.findCreatorAnnotation(this._config, var2.getOwner());
         if (var6 == null || var6 == JsonCreator$Mode.DISABLED) {
            return;
         }

         var4 = PropertyName.construct(var3);
      }

      POJOPropertyBuilder var7 = var5 && var3.isEmpty() ? this._property(var1, var4) : this._property(var1, var3);
      var7.addCtor(var2, var4, var5, true, false);
      this._creatorProperties.add(var7);
   }

   protected void _addMethods(Map var1) {
      AnnotationIntrospector var2 = this._annotationIntrospector;
      Iterator var3 = this._classDef.memberMethods().iterator();

      while(var3.hasNext()) {
         AnnotatedMethod var4 = (AnnotatedMethod)var3.next();
         int var5 = var4.getParameterCount();
         if (var5 == 0) {
            this._addGetterMethod(var1, var4, var2);
         } else if (var5 == 1) {
            this._addSetterMethod(var1, var4, var2);
         } else if (var5 == 2 && var2 != null && Boolean.TRUE.equals(var2.hasAnySetter(var4))) {
            if (this._anySetters == null) {
               this._anySetters = new LinkedList();
            }

            this._anySetters.add(var4);
         }
      }

   }

   protected void _addGetterMethod(Map var1, AnnotatedMethod var2, AnnotationIntrospector var3) {
      if (var2.hasReturnType()) {
         if (Boolean.TRUE.equals(var3.hasAnyGetter(var2))) {
            if (this._anyGetters == null) {
               this._anyGetters = new LinkedList();
            }

            this._anyGetters.add(var2);
         } else if (Boolean.TRUE.equals(var3.hasAsValue(var2))) {
            if (this._jsonValueAccessors == null) {
               this._jsonValueAccessors = new LinkedList();
            }

            this._jsonValueAccessors.add(var2);
         } else {
            PropertyName var6 = var3.findNameForSerialization(var2);
            boolean var7 = var6 != null;
            String var4;
            boolean var5;
            if (!var7) {
               var4 = var3.findImplicitPropertyName(var2);
               if (var4 == null) {
                  var4 = BeanUtil.okNameForRegularGetter(var2, var2.getName(), this._stdBeanNaming);
               }

               if (var4 == null) {
                  var4 = BeanUtil.okNameForIsGetter(var2, var2.getName(), this._stdBeanNaming);
                  if (var4 == null) {
                     return;
                  }

                  var5 = this._visibilityChecker.isIsGetterVisible(var2);
               } else {
                  var5 = this._visibilityChecker.isGetterVisible(var2);
               }
            } else {
               var4 = var3.findImplicitPropertyName(var2);
               if (var4 == null) {
                  var4 = BeanUtil.okNameForGetter(var2, this._stdBeanNaming);
               }

               if (var4 == null) {
                  var4 = var2.getName();
               }

               if (var6.isEmpty()) {
                  var6 = this._propNameFromSimple(var4);
                  var7 = false;
               }

               var5 = true;
            }

            boolean var8 = var3.hasIgnoreMarker(var2);
            this._property(var1, var4).addGetter(var2, var6, var7, var5, var8);
         }
      }
   }

   protected void _addSetterMethod(Map var1, AnnotatedMethod var2, AnnotationIntrospector var3) {
      PropertyName var6 = var3 == null ? null : var3.findNameForDeserialization(var2);
      boolean var7 = var6 != null;
      String var4;
      boolean var5;
      if (!var7) {
         var4 = var3 == null ? null : var3.findImplicitPropertyName(var2);
         if (var4 == null) {
            var4 = BeanUtil.okNameForMutator(var2, this._mutatorPrefix, this._stdBeanNaming);
         }

         if (var4 == null) {
            return;
         }

         var5 = this._visibilityChecker.isSetterVisible(var2);
      } else {
         var4 = var3 == null ? null : var3.findImplicitPropertyName(var2);
         if (var4 == null) {
            var4 = BeanUtil.okNameForMutator(var2, this._mutatorPrefix, this._stdBeanNaming);
         }

         if (var4 == null) {
            var4 = var2.getName();
         }

         if (var6.isEmpty()) {
            var6 = this._propNameFromSimple(var4);
            var7 = false;
         }

         var5 = true;
      }

      boolean var8 = var3 == null ? false : var3.hasIgnoreMarker(var2);
      this._property(var1, var4).addSetter(var2, var6, var7, var5, var8);
   }

   protected void _addInjectables(Map var1) {
      AnnotationIntrospector var2 = this._annotationIntrospector;
      Iterator var3 = this._classDef.fields().iterator();

      while(var3.hasNext()) {
         AnnotatedField var4 = (AnnotatedField)var3.next();
         this._doAddInjectable(var2.findInjectableValue(var4), var4);
      }

      var3 = this._classDef.memberMethods().iterator();

      while(var3.hasNext()) {
         AnnotatedMethod var5 = (AnnotatedMethod)var3.next();
         if (var5.getParameterCount() == 1) {
            this._doAddInjectable(var2.findInjectableValue(var5), var5);
         }
      }

   }

   protected void _doAddInjectable(JacksonInject$Value var1, AnnotatedMember var2) {
      if (var1 != null) {
         Object var3 = var1.getId();
         if (this._injectables == null) {
            this._injectables = new LinkedHashMap();
         }

         AnnotatedMember var4 = (AnnotatedMember)this._injectables.put(var3, var2);
         if (var4 != null && var4.getClass() == var2.getClass()) {
            String var5 = var3.getClass().getName();
            throw new IllegalArgumentException("Duplicate injectable value with id '" + String.valueOf(var3) + "' (of type " + var5 + ")");
         }
      }
   }

   private PropertyName _propNameFromSimple(String var1) {
      return PropertyName.construct(var1, (String)null);
   }

   protected void _removeUnwantedProperties(Map var1) {
      Iterator var2 = var1.values().iterator();

      while(var2.hasNext()) {
         POJOPropertyBuilder var3 = (POJOPropertyBuilder)var2.next();
         if (!var3.anyVisible()) {
            var2.remove();
         } else if (var3.anyIgnorals()) {
            if (!var3.isExplicitlyIncluded()) {
               var2.remove();
               this._collectIgnorals(var3.getName());
            } else {
               var3.removeIgnored();
               if (!var3.couldDeserialize()) {
                  this._collectIgnorals(var3.getName());
               }
            }
         }
      }

   }

   protected void _removeUnwantedAccessor(Map var1) {
      boolean var2 = this._config.isEnabled(MapperFeature.INFER_PROPERTY_MUTATORS);
      Iterator var3 = var1.values().iterator();

      while(var3.hasNext()) {
         POJOPropertyBuilder var4 = (POJOPropertyBuilder)var3.next();
         JsonProperty$Access var5 = var4.removeNonVisible(var2);
         if (var5 == JsonProperty$Access.READ_ONLY) {
            this._collectIgnorals(var4.getName());
         }
      }

   }

   private void _collectIgnorals(String var1) {
      if (!this._forSerialization) {
         if (this._ignoredPropertyNames == null) {
            this._ignoredPropertyNames = new HashSet();
         }

         this._ignoredPropertyNames.add(var1);
      }

   }

   protected void _renameProperties(Map var1) {
      Iterator var2 = var1.entrySet().iterator();
      LinkedList var3 = null;

      POJOPropertyBuilder var5;
      while(var2.hasNext()) {
         Entry var4 = (Entry)var2.next();
         var5 = (POJOPropertyBuilder)var4.getValue();
         Set var6 = var5.findExplicitNames();
         if (!var6.isEmpty()) {
            var2.remove();
            if (var3 == null) {
               var3 = new LinkedList();
            }

            if (var6.size() == 1) {
               PropertyName var7 = (PropertyName)var6.iterator().next();
               var3.add(var5.withName(var7));
            } else {
               var3.addAll(var5.explode(var6));
            }
         }
      }

      if (var3 != null) {
         Iterator var8 = var3.iterator();

         while(var8.hasNext()) {
            var5 = (POJOPropertyBuilder)var8.next();
            String var9 = var5.getName();
            POJOPropertyBuilder var10 = (POJOPropertyBuilder)var1.get(var9);
            if (var10 == null) {
               var1.put(var9, var5);
            } else {
               var10.addAll(var5);
            }

            this._updateCreatorProperty(var5, this._creatorProperties);
            if (this._ignoredPropertyNames != null) {
               this._ignoredPropertyNames.remove(var9);
            }
         }
      }

   }

   protected void _renameUsing(Map var1, PropertyNamingStrategy var2) {
      POJOPropertyBuilder[] var3 = (POJOPropertyBuilder[])var1.values().toArray(new POJOPropertyBuilder[var1.size()]);
      var1.clear();
      POJOPropertyBuilder[] var4 = var3;
      int var5 = var3.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         POJOPropertyBuilder var7 = var4[var6];
         PropertyName var8 = var7.getFullName();
         String var9 = null;
         if (!var7.isExplicitlyNamed() || this._config.isEnabled(MapperFeature.ALLOW_EXPLICIT_PROPERTY_RENAMING)) {
            if (this._forSerialization) {
               if (var7.hasGetter()) {
                  var9 = var2.nameForGetterMethod(this._config, var7.getGetter(), var8.getSimpleName());
               } else if (var7.hasField()) {
                  var9 = var2.nameForField(this._config, var7.getField(), var8.getSimpleName());
               }
            } else if (var7.hasSetter()) {
               var9 = var2.nameForSetterMethod(this._config, var7.getSetter(), var8.getSimpleName());
            } else if (var7.hasConstructorParameter()) {
               var9 = var2.nameForConstructorParameter(this._config, var7.getConstructorParameter(), var8.getSimpleName());
            } else if (var7.hasField()) {
               var9 = var2.nameForField(this._config, var7.getField(), var8.getSimpleName());
            } else if (var7.hasGetter()) {
               var9 = var2.nameForGetterMethod(this._config, var7.getGetter(), var8.getSimpleName());
            }
         }

         String var10;
         if (var9 != null && !var8.hasSimpleName(var9)) {
            var7 = var7.withSimpleName(var9);
            var10 = var9;
         } else {
            var10 = var8.getSimpleName();
         }

         POJOPropertyBuilder var11 = (POJOPropertyBuilder)var1.get(var10);
         if (var11 == null) {
            var1.put(var10, var7);
         } else {
            var11.addAll(var7);
         }

         this._updateCreatorProperty(var7, this._creatorProperties);
      }

   }

   protected void _renameWithWrappers(Map var1) {
      Iterator var2 = var1.entrySet().iterator();
      LinkedList var3 = null;

      POJOPropertyBuilder var5;
      while(var2.hasNext()) {
         Entry var4 = (Entry)var2.next();
         var5 = (POJOPropertyBuilder)var4.getValue();
         AnnotatedMember var6 = var5.getPrimaryMember();
         if (var6 != null) {
            PropertyName var7 = this._annotationIntrospector.findWrapperName(var6);
            if (var7 != null && var7.hasSimpleName() && !var7.equals(var5.getFullName())) {
               if (var3 == null) {
                  var3 = new LinkedList();
               }

               var5 = var5.withName(var7);
               var3.add(var5);
               var2.remove();
            }
         }
      }

      if (var3 != null) {
         Iterator var8 = var3.iterator();

         while(var8.hasNext()) {
            var5 = (POJOPropertyBuilder)var8.next();
            String var9 = var5.getName();
            POJOPropertyBuilder var10 = (POJOPropertyBuilder)var1.get(var9);
            if (var10 == null) {
               var1.put(var9, var5);
            } else {
               var10.addAll(var5);
            }
         }
      }

   }

   protected void _sortProperties(Map var1) {
      AnnotationIntrospector var2 = this._annotationIntrospector;
      Boolean var3 = var2.findSerializationSortAlphabetically(this._classDef);
      boolean var4;
      if (var3 == null) {
         var4 = this._config.shouldSortPropertiesAlphabetically();
      } else {
         var4 = var3;
      }

      String[] var5 = var2.findSerializationPropertyOrder(this._classDef);
      if (var4 || this._creatorProperties != null || var5 != null) {
         int var6 = var1.size();
         Object var7;
         if (var4) {
            var7 = new TreeMap();
         } else {
            var7 = new LinkedHashMap(var6 + var6);
         }

         Iterator var8 = var1.values().iterator();

         while(var8.hasNext()) {
            POJOPropertyBuilder var9 = (POJOPropertyBuilder)var8.next();
            ((Map)var7).put(var9.getName(), var9);
         }

         LinkedHashMap var16 = new LinkedHashMap(var6 + var6);
         String var12;
         if (var5 != null) {
            String[] var17 = var5;
            int var10 = var5.length;

            for(int var11 = 0; var11 < var10; ++var11) {
               var12 = var17[var11];
               POJOPropertyBuilder var13 = (POJOPropertyBuilder)((Map)var7).get(var12);
               if (var13 == null) {
                  Iterator var14 = var1.values().iterator();

                  while(var14.hasNext()) {
                     POJOPropertyBuilder var15 = (POJOPropertyBuilder)var14.next();
                     if (var12.equals(var15.getInternalName())) {
                        var13 = var15;
                        var12 = var15.getName();
                        break;
                     }
                  }
               }

               if (var13 != null) {
                  var16.put(var12, var13);
               }
            }
         }

         if (this._creatorProperties != null) {
            Object var18;
            if (!var4) {
               var18 = this._creatorProperties;
            } else {
               TreeMap var19 = new TreeMap();
               Iterator var21 = this._creatorProperties.iterator();

               while(var21.hasNext()) {
                  POJOPropertyBuilder var23 = (POJOPropertyBuilder)var21.next();
                  var19.put(var23.getName(), var23);
               }

               var18 = var19.values();
            }

            Iterator var20 = ((Collection)var18).iterator();

            while(var20.hasNext()) {
               POJOPropertyBuilder var22 = (POJOPropertyBuilder)var20.next();
               var12 = var22.getName();
               if (((Map)var7).containsKey(var12)) {
                  var16.put(var12, var22);
               }
            }
         }

         var16.putAll((Map)var7);
         var1.clear();
         var1.putAll(var16);
      }
   }

   protected void reportProblem(String var1, Object... var2) {
      if (var2.length > 0) {
         var1 = String.format(var1, var2);
      }

      throw new IllegalArgumentException("Problem with definition of " + this._classDef + ": " + var1);
   }

   protected POJOPropertyBuilder _property(Map var1, PropertyName var2) {
      String var3 = var2.getSimpleName();
      POJOPropertyBuilder var4 = (POJOPropertyBuilder)var1.get(var3);
      if (var4 == null) {
         var4 = new POJOPropertyBuilder(this._config, this._annotationIntrospector, this._forSerialization, var2);
         var1.put(var3, var4);
      }

      return var4;
   }

   protected POJOPropertyBuilder _property(Map var1, String var2) {
      POJOPropertyBuilder var3 = (POJOPropertyBuilder)var1.get(var2);
      if (var3 == null) {
         var3 = new POJOPropertyBuilder(this._config, this._annotationIntrospector, this._forSerialization, PropertyName.construct(var2));
         var1.put(var2, var3);
      }

      return var3;
   }

   private PropertyNamingStrategy _findNamingStrategy() {
      Object var1 = this._annotationIntrospector.findNamingStrategy(this._classDef);
      if (var1 == null) {
         return this._config.getPropertyNamingStrategy();
      } else if (var1 instanceof PropertyNamingStrategy) {
         return (PropertyNamingStrategy)var1;
      } else if (!(var1 instanceof Class)) {
         throw new IllegalStateException("AnnotationIntrospector returned PropertyNamingStrategy definition of type " + var1.getClass().getName() + "; expected type PropertyNamingStrategy or Class<PropertyNamingStrategy> instead");
      } else {
         Class var2 = (Class)var1;
         if (var2 == PropertyNamingStrategy.class) {
            return null;
         } else if (!PropertyNamingStrategy.class.isAssignableFrom(var2)) {
            throw new IllegalStateException("AnnotationIntrospector returned Class " + var2.getName() + "; expected Class<PropertyNamingStrategy>");
         } else {
            HandlerInstantiator var3 = this._config.getHandlerInstantiator();
            if (var3 != null) {
               PropertyNamingStrategy var4 = var3.namingStrategyInstance(this._config, this._classDef, var2);
               if (var4 != null) {
                  return var4;
               }
            }

            return (PropertyNamingStrategy)ClassUtil.createInstance(var2, this._config.canOverrideAccessModifiers());
         }
      }
   }

   protected void _updateCreatorProperty(POJOPropertyBuilder var1, List var2) {
      if (var2 != null) {
         String var3 = var1.getInternalName();
         int var4 = 0;

         for(int var5 = var2.size(); var4 < var5; ++var4) {
            if (((POJOPropertyBuilder)var2.get(var4)).getInternalName().equals(var3)) {
               var2.set(var4, var1);
               break;
            }
         }
      }

   }
}
