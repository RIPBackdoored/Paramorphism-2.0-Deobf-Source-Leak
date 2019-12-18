package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.annotation.JsonInclude$Value;
import com.fasterxml.jackson.annotation.JsonProperty$Access;
import com.fasterxml.jackson.annotation.JsonSetter$Value;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.AnnotationIntrospector$ReferenceProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyMetadata$MergeInfo;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.cfg.ConfigOverride;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class POJOPropertyBuilder extends BeanPropertyDefinition implements Comparable {
   private static final AnnotationIntrospector$ReferenceProperty NOT_REFEFERENCE_PROP = AnnotationIntrospector$ReferenceProperty.managed("");
   protected final boolean _forSerialization;
   protected final MapperConfig _config;
   protected final AnnotationIntrospector _annotationIntrospector;
   protected final PropertyName _name;
   protected final PropertyName _internalName;
   protected POJOPropertyBuilder$Linked _fields;
   protected POJOPropertyBuilder$Linked _ctorParameters;
   protected POJOPropertyBuilder$Linked _getters;
   protected POJOPropertyBuilder$Linked _setters;
   protected transient PropertyMetadata _metadata;
   protected transient AnnotationIntrospector$ReferenceProperty _referenceInfo;

   public POJOPropertyBuilder(MapperConfig var1, AnnotationIntrospector var2, boolean var3, PropertyName var4) {
      this(var1, var2, var3, var4, var4);
   }

   protected POJOPropertyBuilder(MapperConfig var1, AnnotationIntrospector var2, boolean var3, PropertyName var4, PropertyName var5) {
      super();
      this._config = var1;
      this._annotationIntrospector = var2;
      this._internalName = var4;
      this._name = var5;
      this._forSerialization = var3;
   }

   protected POJOPropertyBuilder(POJOPropertyBuilder var1, PropertyName var2) {
      super();
      this._config = var1._config;
      this._annotationIntrospector = var1._annotationIntrospector;
      this._internalName = var1._internalName;
      this._name = var2;
      this._fields = var1._fields;
      this._ctorParameters = var1._ctorParameters;
      this._getters = var1._getters;
      this._setters = var1._setters;
      this._forSerialization = var1._forSerialization;
   }

   public POJOPropertyBuilder withName(PropertyName var1) {
      return new POJOPropertyBuilder(this, var1);
   }

   public POJOPropertyBuilder withSimpleName(String var1) {
      PropertyName var2 = this._name.withSimpleName(var1);
      return var2 == this._name ? this : new POJOPropertyBuilder(this, var2);
   }

   public int compareTo(POJOPropertyBuilder var1) {
      if (this._ctorParameters != null) {
         if (var1._ctorParameters == null) {
            return -1;
         }
      } else if (var1._ctorParameters != null) {
         return 1;
      }

      return this.getName().compareTo(var1.getName());
   }

   public String getName() {
      return this._name == null ? null : this._name.getSimpleName();
   }

   public PropertyName getFullName() {
      return this._name;
   }

   public boolean hasName(PropertyName var1) {
      return this._name.equals(var1);
   }

   public String getInternalName() {
      return this._internalName.getSimpleName();
   }

   public PropertyName getWrapperName() {
      AnnotatedMember var1 = this.getPrimaryMember();
      return var1 != null && this._annotationIntrospector != null ? this._annotationIntrospector.findWrapperName(var1) : null;
   }

   public boolean isExplicitlyIncluded() {
      return this._anyExplicits(this._fields) || this._anyExplicits(this._getters) || this._anyExplicits(this._setters) || this._anyExplicitNames(this._ctorParameters);
   }

   public boolean isExplicitlyNamed() {
      return this._anyExplicitNames(this._fields) || this._anyExplicitNames(this._getters) || this._anyExplicitNames(this._setters) || this._anyExplicitNames(this._ctorParameters);
   }

   public PropertyMetadata getMetadata() {
      if (this._metadata == null) {
         Boolean var1 = this._findRequired();
         String var2 = this._findDescription();
         Integer var3 = this._findIndex();
         String var4 = this._findDefaultValue();
         if (var1 == null && var3 == null && var4 == null) {
            this._metadata = var2 == null ? PropertyMetadata.STD_REQUIRED_OR_OPTIONAL : PropertyMetadata.STD_REQUIRED_OR_OPTIONAL.withDescription(var2);
         } else {
            this._metadata = PropertyMetadata.construct(var1, var2, var3, var4);
         }

         if (!this._forSerialization) {
            this._metadata = this._getSetterInfo(this._metadata);
         }
      }

      return this._metadata;
   }

   protected PropertyMetadata _getSetterInfo(PropertyMetadata var1) {
      boolean var2 = true;
      Nulls var3 = null;
      Nulls var4 = null;
      AnnotatedMember var5 = this.getPrimaryMember();
      AnnotatedMember var6 = this.getAccessor();
      JsonSetter$Value var11;
      if (var5 != null) {
         if (this._annotationIntrospector != null) {
            if (var6 != null) {
               Boolean var7 = this._annotationIntrospector.findMergeInfo(var5);
               if (var7 != null) {
                  var2 = false;
                  if (var7) {
                     var1 = var1.withMergeInfo(PropertyMetadata$MergeInfo.createForPropertyOverride(var6));
                  }
               }
            }

            var11 = this._annotationIntrospector.findSetterInfo(var5);
            if (var11 != null) {
               var3 = var11.nonDefaultValueNulls();
               var4 = var11.nonDefaultContentNulls();
            }
         }

         if (var2 || var3 == null || var4 == null) {
            Class var12 = this.getRawPrimaryType();
            ConfigOverride var8 = this._config.getConfigOverride(var12);
            JsonSetter$Value var9 = var8.getSetterInfo();
            if (var9 != null) {
               if (var3 == null) {
                  var3 = var9.nonDefaultValueNulls();
               }

               if (var4 == null) {
                  var4 = var9.nonDefaultContentNulls();
               }
            }

            if (var2 && var6 != null) {
               Boolean var10 = var8.getMergeable();
               if (var10 != null) {
                  var2 = false;
                  if (var10) {
                     var1 = var1.withMergeInfo(PropertyMetadata$MergeInfo.createForTypeOverride(var6));
                  }
               }
            }
         }
      }

      if (var2 || var3 == null || var4 == null) {
         var11 = this._config.getDefaultSetterInfo();
         if (var3 == null) {
            var3 = var11.nonDefaultValueNulls();
         }

         if (var4 == null) {
            var4 = var11.nonDefaultContentNulls();
         }

         if (var2) {
            Boolean var13 = this._config.getDefaultMergeable();
            if (Boolean.TRUE.equals(var13) && var6 != null) {
               var1 = var1.withMergeInfo(PropertyMetadata$MergeInfo.createForDefaults(var6));
            }
         }
      }

      if (var3 != null || var4 != null) {
         var1 = var1.withNulls(var3, var4);
      }

      return var1;
   }

   public JavaType getPrimaryType() {
      AnnotatedMethod var2;
      if (this._forSerialization) {
         var2 = this.getGetter();
         if (var2 == null) {
            AnnotatedField var3 = this.getField();
            return var3 == null ? TypeFactory.unknownType() : var3.getType();
         } else {
            return var2.getType();
         }
      } else {
         Object var1 = this.getConstructorParameter();
         if (var1 == null) {
            var2 = this.getSetter();
            if (var2 != null) {
               return ((AnnotatedMethod)var2).getParameterType(0);
            }

            var1 = this.getField();
         }

         if (var1 == null) {
            var1 = this.getGetter();
            if (var1 == null) {
               return TypeFactory.unknownType();
            }
         }

         return ((AnnotatedMember)var1).getType();
      }
   }

   public Class getRawPrimaryType() {
      return this.getPrimaryType().getRawClass();
   }

   public boolean hasGetter() {
      return this._getters != null;
   }

   public boolean hasSetter() {
      return this._setters != null;
   }

   public boolean hasField() {
      return this._fields != null;
   }

   public boolean hasConstructorParameter() {
      return this._ctorParameters != null;
   }

   public boolean couldDeserialize() {
      return this._ctorParameters != null || this._setters != null || this._fields != null;
   }

   public boolean couldSerialize() {
      return this._getters != null || this._fields != null;
   }

   public AnnotatedMethod getGetter() {
      POJOPropertyBuilder$Linked var1 = this._getters;
      if (var1 == null) {
         return null;
      } else {
         POJOPropertyBuilder$Linked var2 = var1.next;
         if (var2 == null) {
            return (AnnotatedMethod)var1.value;
         } else {
            for(; var2 != null; var2 = var2.next) {
               Class var3 = ((AnnotatedMethod)var1.value).getDeclaringClass();
               Class var4 = ((AnnotatedMethod)var2.value).getDeclaringClass();
               if (var3 != var4) {
                  if (var3.isAssignableFrom(var4)) {
                     var1 = var2;
                     continue;
                  }

                  if (var4.isAssignableFrom(var3)) {
                     continue;
                  }
               }

               int var5 = this._getterPriority((AnnotatedMethod)var2.value);
               int var6 = this._getterPriority((AnnotatedMethod)var1.value);
               if (var5 == var6) {
                  throw new IllegalArgumentException("Conflicting getter definitions for property \"" + this.getName() + "\": " + ((AnnotatedMethod)var1.value).getFullName() + " vs " + ((AnnotatedMethod)var2.value).getFullName());
               }

               if (var5 < var6) {
                  var1 = var2;
               }
            }

            this._getters = var1.withoutNext();
            return (AnnotatedMethod)var1.value;
         }
      }
   }

   public AnnotatedMethod getSetter() {
      POJOPropertyBuilder$Linked var1 = this._setters;
      if (var1 == null) {
         return null;
      } else {
         POJOPropertyBuilder$Linked var2 = var1.next;
         if (var2 == null) {
            return (AnnotatedMethod)var1.value;
         } else {
            while(true) {
               if (var2 == null) {
                  this._setters = var1.withoutNext();
                  return (AnnotatedMethod)var1.value;
               }

               label42: {
                  Class var3 = ((AnnotatedMethod)var1.value).getDeclaringClass();
                  Class var4 = ((AnnotatedMethod)var2.value).getDeclaringClass();
                  if (var3 != var4) {
                     if (var3.isAssignableFrom(var4)) {
                        var1 = var2;
                        break label42;
                     }

                     if (var4.isAssignableFrom(var3)) {
                        break label42;
                     }
                  }

                  AnnotatedMethod var5 = (AnnotatedMethod)var2.value;
                  AnnotatedMethod var6 = (AnnotatedMethod)var1.value;
                  int var7 = this._setterPriority(var5);
                  int var8 = this._setterPriority(var6);
                  if (var7 != var8) {
                     if (var7 < var8) {
                        var1 = var2;
                     }
                  } else {
                     if (this._annotationIntrospector == null) {
                        break;
                     }

                     AnnotatedMethod var9 = this._annotationIntrospector.resolveSetterConflict(this._config, var6, var5);
                     if (var9 != var6) {
                        if (var9 != var5) {
                           break;
                        }

                        var1 = var2;
                     }
                  }
               }

               var2 = var2.next;
            }

            throw new IllegalArgumentException(String.format("Conflicting setter definitions for property \"%s\": %s vs %s", this.getName(), ((AnnotatedMethod)var1.value).getFullName(), ((AnnotatedMethod)var2.value).getFullName()));
         }
      }
   }

   public AnnotatedField getField() {
      if (this._fields == null) {
         return null;
      } else {
         AnnotatedField var1 = (AnnotatedField)this._fields.value;
         POJOPropertyBuilder$Linked var2 = this._fields.next;

         AnnotatedField var3;
         while(true) {
            if (var2 == null) {
               return var1;
            }

            var3 = (AnnotatedField)var2.value;
            Class var4 = var1.getDeclaringClass();
            Class var5 = var3.getDeclaringClass();
            if (var4 == var5) {
               break;
            }

            if (var4.isAssignableFrom(var5)) {
               var1 = var3;
            } else if (!var5.isAssignableFrom(var4)) {
               break;
            }

            var2 = var2.next;
         }

         throw new IllegalArgumentException("Multiple fields representing property \"" + this.getName() + "\": " + var1.getFullName() + " vs " + var3.getFullName());
      }
   }

   public AnnotatedParameter getConstructorParameter() {
      if (this._ctorParameters == null) {
         return null;
      } else {
         POJOPropertyBuilder$Linked var1 = this._ctorParameters;

         while(!(((AnnotatedParameter)var1.value).getOwner() instanceof AnnotatedConstructor)) {
            var1 = var1.next;
            if (var1 == null) {
               return (AnnotatedParameter)this._ctorParameters.value;
            }
         }

         return (AnnotatedParameter)var1.value;
      }
   }

   public Iterator getConstructorParameters() {
      return (Iterator)(this._ctorParameters == null ? ClassUtil.emptyIterator() : new POJOPropertyBuilder$MemberIterator(this._ctorParameters));
   }

   public AnnotatedMember getPrimaryMember() {
      if (this._forSerialization) {
         return this.getAccessor();
      } else {
         AnnotatedMember var1 = this.getMutator();
         if (var1 == null) {
            var1 = this.getAccessor();
         }

         return var1;
      }
   }

   protected int _getterPriority(AnnotatedMethod var1) {
      String var2 = var1.getName();
      if (var2.startsWith("get") && var2.length() > 3) {
         return 1;
      } else {
         return var2.startsWith("is") && var2.length() > 2 ? 2 : 3;
      }
   }

   protected int _setterPriority(AnnotatedMethod var1) {
      String var2 = var1.getName();
      return var2.startsWith("set") && var2.length() > 3 ? 1 : 2;
   }

   public Class[] findViews() {
      return (Class[])this.fromMemberAnnotations(new POJOPropertyBuilder$1(this));
   }

   public AnnotationIntrospector$ReferenceProperty findReferenceType() {
      AnnotationIntrospector$ReferenceProperty var1 = this._referenceInfo;
      if (var1 != null) {
         return var1 == NOT_REFEFERENCE_PROP ? null : var1;
      } else {
         var1 = (AnnotationIntrospector$ReferenceProperty)this.fromMemberAnnotations(new POJOPropertyBuilder$2(this));
         this._referenceInfo = var1 == null ? NOT_REFEFERENCE_PROP : var1;
         return var1;
      }
   }

   public boolean isTypeId() {
      Boolean var1 = (Boolean)this.fromMemberAnnotations(new POJOPropertyBuilder$3(this));
      return var1 != null && var1;
   }

   protected Boolean _findRequired() {
      return (Boolean)this.fromMemberAnnotations(new POJOPropertyBuilder$4(this));
   }

   protected String _findDescription() {
      return (String)this.fromMemberAnnotations(new POJOPropertyBuilder$5(this));
   }

   protected Integer _findIndex() {
      return (Integer)this.fromMemberAnnotations(new POJOPropertyBuilder$6(this));
   }

   protected String _findDefaultValue() {
      return (String)this.fromMemberAnnotations(new POJOPropertyBuilder$7(this));
   }

   public ObjectIdInfo findObjectIdInfo() {
      return (ObjectIdInfo)this.fromMemberAnnotations(new POJOPropertyBuilder$8(this));
   }

   public JsonInclude$Value findInclusion() {
      AnnotatedMember var1 = this.getAccessor();
      JsonInclude$Value var2 = this._annotationIntrospector == null ? null : this._annotationIntrospector.findPropertyInclusion(var1);
      return var2 == null ? JsonInclude$Value.empty() : var2;
   }

   public JsonProperty$Access findAccess() {
      return (JsonProperty$Access)this.fromMemberAnnotationsExcept(new POJOPropertyBuilder$9(this), JsonProperty$Access.AUTO);
   }

   public void addField(AnnotatedField var1, PropertyName var2, boolean var3, boolean var4, boolean var5) {
      this._fields = new POJOPropertyBuilder$Linked(var1, this._fields, var2, var3, var4, var5);
   }

   public void addCtor(AnnotatedParameter var1, PropertyName var2, boolean var3, boolean var4, boolean var5) {
      this._ctorParameters = new POJOPropertyBuilder$Linked(var1, this._ctorParameters, var2, var3, var4, var5);
   }

   public void addGetter(AnnotatedMethod var1, PropertyName var2, boolean var3, boolean var4, boolean var5) {
      this._getters = new POJOPropertyBuilder$Linked(var1, this._getters, var2, var3, var4, var5);
   }

   public void addSetter(AnnotatedMethod var1, PropertyName var2, boolean var3, boolean var4, boolean var5) {
      this._setters = new POJOPropertyBuilder$Linked(var1, this._setters, var2, var3, var4, var5);
   }

   public void addAll(POJOPropertyBuilder var1) {
      this._fields = merge(this._fields, var1._fields);
      this._ctorParameters = merge(this._ctorParameters, var1._ctorParameters);
      this._getters = merge(this._getters, var1._getters);
      this._setters = merge(this._setters, var1._setters);
   }

   private static POJOPropertyBuilder$Linked merge(POJOPropertyBuilder$Linked var0, POJOPropertyBuilder$Linked var1) {
      if (var0 == null) {
         return var1;
      } else {
         return var1 == null ? var0 : var0.append(var1);
      }
   }

   public void removeIgnored() {
      this._fields = this._removeIgnored(this._fields);
      this._getters = this._removeIgnored(this._getters);
      this._setters = this._removeIgnored(this._setters);
      this._ctorParameters = this._removeIgnored(this._ctorParameters);
   }

   public JsonProperty$Access removeNonVisible(boolean var1) {
      // $FF: Couldn't be decompiled
   }

   public void removeConstructors() {
      this._ctorParameters = null;
   }

   public void trimByVisibility() {
      this._fields = this._trimByVisibility(this._fields);
      this._getters = this._trimByVisibility(this._getters);
      this._setters = this._trimByVisibility(this._setters);
      this._ctorParameters = this._trimByVisibility(this._ctorParameters);
   }

   public void mergeAnnotations(boolean var1) {
      AnnotationMap var2;
      if (var1) {
         if (this._getters != null) {
            var2 = this._mergeAnnotations(0, this._getters, this._fields, this._ctorParameters, this._setters);
            this._getters = this._applyAnnotations(this._getters, var2);
         } else if (this._fields != null) {
            var2 = this._mergeAnnotations(0, this._fields, this._ctorParameters, this._setters);
            this._fields = this._applyAnnotations(this._fields, var2);
         }
      } else if (this._ctorParameters != null) {
         var2 = this._mergeAnnotations(0, this._ctorParameters, this._setters, this._fields, this._getters);
         this._ctorParameters = this._applyAnnotations(this._ctorParameters, var2);
      } else if (this._setters != null) {
         var2 = this._mergeAnnotations(0, this._setters, this._fields, this._getters);
         this._setters = this._applyAnnotations(this._setters, var2);
      } else if (this._fields != null) {
         var2 = this._mergeAnnotations(0, this._fields, this._getters);
         this._fields = this._applyAnnotations(this._fields, var2);
      }

   }

   private AnnotationMap _mergeAnnotations(int var1, POJOPropertyBuilder$Linked... var2) {
      AnnotationMap var3 = this._getAllAnnotations(var2[var1]);

      do {
         ++var1;
         if (var1 >= var2.length) {
            return var3;
         }
      } while(var2[var1] == null);

      return AnnotationMap.merge(var3, this._mergeAnnotations(var1, var2));
   }

   private AnnotationMap _getAllAnnotations(POJOPropertyBuilder$Linked var1) {
      AnnotationMap var2 = ((AnnotatedMember)var1.value).getAllAnnotations();
      if (var1.next != null) {
         var2 = AnnotationMap.merge(var2, this._getAllAnnotations(var1.next));
      }

      return var2;
   }

   private POJOPropertyBuilder$Linked _applyAnnotations(POJOPropertyBuilder$Linked var1, AnnotationMap var2) {
      AnnotatedMember var3 = (AnnotatedMember)((AnnotatedMember)var1.value).withAnnotations(var2);
      if (var1.next != null) {
         var1 = var1.withNext(this._applyAnnotations(var1.next, var2));
      }

      return var1.withValue(var3);
   }

   private POJOPropertyBuilder$Linked _removeIgnored(POJOPropertyBuilder$Linked var1) {
      return var1 == null ? var1 : var1.withoutIgnored();
   }

   private POJOPropertyBuilder$Linked _removeNonVisible(POJOPropertyBuilder$Linked var1) {
      return var1 == null ? var1 : var1.withoutNonVisible();
   }

   private POJOPropertyBuilder$Linked _trimByVisibility(POJOPropertyBuilder$Linked var1) {
      return var1 == null ? var1 : var1.trimByVisibility();
   }

   private boolean _anyExplicits(POJOPropertyBuilder$Linked var1) {
      while(var1 != null) {
         if (var1.name != null && var1.name.hasSimpleName()) {
            return true;
         }

         var1 = var1.next;
      }

      return false;
   }

   private boolean _anyExplicitNames(POJOPropertyBuilder$Linked var1) {
      while(var1 != null) {
         if (var1.name != null && var1.isNameExplicit) {
            return true;
         }

         var1 = var1.next;
      }

      return false;
   }

   public boolean anyVisible() {
      return this._anyVisible(this._fields) || this._anyVisible(this._getters) || this._anyVisible(this._setters) || this._anyVisible(this._ctorParameters);
   }

   private boolean _anyVisible(POJOPropertyBuilder$Linked var1) {
      while(var1 != null) {
         if (var1.isVisible) {
            return true;
         }

         var1 = var1.next;
      }

      return false;
   }

   public boolean anyIgnorals() {
      return this._anyIgnorals(this._fields) || this._anyIgnorals(this._getters) || this._anyIgnorals(this._setters) || this._anyIgnorals(this._ctorParameters);
   }

   private boolean _anyIgnorals(POJOPropertyBuilder$Linked var1) {
      while(var1 != null) {
         if (var1.isMarkedIgnored) {
            return true;
         }

         var1 = var1.next;
      }

      return false;
   }

   public Set findExplicitNames() {
      Set var1 = null;
      var1 = this._findExplicitNames(this._fields, var1);
      var1 = this._findExplicitNames(this._getters, var1);
      var1 = this._findExplicitNames(this._setters, var1);
      var1 = this._findExplicitNames(this._ctorParameters, var1);
      return var1 == null ? Collections.emptySet() : var1;
   }

   public Collection explode(Collection var1) {
      HashMap var2 = new HashMap();
      this._explode(var1, var2, this._fields);
      this._explode(var1, var2, this._getters);
      this._explode(var1, var2, this._setters);
      this._explode(var1, var2, this._ctorParameters);
      return var2.values();
   }

   private void _explode(Collection var1, Map var2, POJOPropertyBuilder$Linked var3) {
      POJOPropertyBuilder$Linked var4 = var3;

      for(POJOPropertyBuilder$Linked var5 = var3; var5 != null; var5 = var5.next) {
         PropertyName var6 = var5.name;
         if (var5.isNameExplicit && var6 != null) {
            POJOPropertyBuilder var7 = (POJOPropertyBuilder)var2.get(var6);
            if (var7 == null) {
               var7 = new POJOPropertyBuilder(this._config, this._annotationIntrospector, this._forSerialization, this._internalName, var6);
               var2.put(var6, var7);
            }

            if (var4 == this._fields) {
               var7._fields = var5.withNext(var7._fields);
            } else if (var4 == this._getters) {
               var7._getters = var5.withNext(var7._getters);
            } else if (var4 == this._setters) {
               var7._setters = var5.withNext(var7._setters);
            } else {
               if (var4 != this._ctorParameters) {
                  throw new IllegalStateException("Internal error: mismatched accessors, property: " + this);
               }

               var7._ctorParameters = var5.withNext(var7._ctorParameters);
            }
         } else if (var5.isVisible) {
            throw new IllegalStateException("Conflicting/ambiguous property name definitions (implicit name '" + this._name + "'): found multiple explicit names: " + var1 + ", but also implicit accessor: " + var5);
         }
      }

   }

   private Set _findExplicitNames(POJOPropertyBuilder$Linked var1, Set var2) {
      for(; var1 != null; var1 = var1.next) {
         if (var1.isNameExplicit && var1.name != null) {
            if (var2 == null) {
               var2 = new HashSet();
            }

            ((Set)var2).add(var1.name);
         }
      }

      return (Set)var2;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("[Property '").append(this._name).append("'; ctors: ").append(this._ctorParameters).append(", field(s): ").append(this._fields).append(", getter(s): ").append(this._getters).append(", setter(s): ").append(this._setters);
      var1.append("]");
      return var1.toString();
   }

   protected Object fromMemberAnnotations(POJOPropertyBuilder$WithMember var1) {
      Object var2 = null;
      if (this._annotationIntrospector != null) {
         if (this._forSerialization) {
            if (this._getters != null) {
               var2 = var1.withMember((AnnotatedMember)this._getters.value);
            }
         } else {
            if (this._ctorParameters != null) {
               var2 = var1.withMember((AnnotatedMember)this._ctorParameters.value);
            }

            if (var2 == null && this._setters != null) {
               var2 = var1.withMember((AnnotatedMember)this._setters.value);
            }
         }

         if (var2 == null && this._fields != null) {
            var2 = var1.withMember((AnnotatedMember)this._fields.value);
         }
      }

      return var2;
   }

   protected Object fromMemberAnnotationsExcept(POJOPropertyBuilder$WithMember var1, Object var2) {
      if (this._annotationIntrospector == null) {
         return null;
      } else {
         Object var3;
         if (this._forSerialization) {
            if (this._getters != null) {
               var3 = var1.withMember((AnnotatedMember)this._getters.value);
               if (var3 != null && var3 != var2) {
                  return var3;
               }
            }

            if (this._fields != null) {
               var3 = var1.withMember((AnnotatedMember)this._fields.value);
               if (var3 != null && var3 != var2) {
                  return var3;
               }
            }

            if (this._ctorParameters != null) {
               var3 = var1.withMember((AnnotatedMember)this._ctorParameters.value);
               if (var3 != null && var3 != var2) {
                  return var3;
               }
            }

            if (this._setters != null) {
               var3 = var1.withMember((AnnotatedMember)this._setters.value);
               if (var3 != null && var3 != var2) {
                  return var3;
               }
            }

            return null;
         } else {
            if (this._ctorParameters != null) {
               var3 = var1.withMember((AnnotatedMember)this._ctorParameters.value);
               if (var3 != null && var3 != var2) {
                  return var3;
               }
            }

            if (this._setters != null) {
               var3 = var1.withMember((AnnotatedMember)this._setters.value);
               if (var3 != null && var3 != var2) {
                  return var3;
               }
            }

            if (this._fields != null) {
               var3 = var1.withMember((AnnotatedMember)this._fields.value);
               if (var3 != null && var3 != var2) {
                  return var3;
               }
            }

            if (this._getters != null) {
               var3 = var1.withMember((AnnotatedMember)this._getters.value);
               if (var3 != null && var3 != var2) {
                  return var3;
               }
            }

            return null;
         }
      }
   }

   public BeanPropertyDefinition withSimpleName(String var1) {
      return this.withSimpleName(var1);
   }

   public BeanPropertyDefinition withName(PropertyName var1) {
      return this.withName(var1);
   }

   public int compareTo(Object var1) {
      return this.compareTo((POJOPropertyBuilder)var1);
   }
}
