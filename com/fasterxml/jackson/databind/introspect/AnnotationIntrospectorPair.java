package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.annotation.JacksonInject$Value;
import com.fasterxml.jackson.annotation.JsonCreator$Mode;
import com.fasterxml.jackson.annotation.JsonFormat$Value;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties$Value;
import com.fasterxml.jackson.annotation.JsonInclude$Include;
import com.fasterxml.jackson.annotation.JsonInclude$Value;
import com.fasterxml.jackson.annotation.JsonProperty$Access;
import com.fasterxml.jackson.annotation.JsonSetter$Value;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.AnnotationIntrospector$ReferenceProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer$None;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer$None;
import com.fasterxml.jackson.databind.KeyDeserializer$None;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder$Value;
import com.fasterxml.jackson.databind.annotation.JsonSerialize$Typing;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AnnotationIntrospectorPair extends AnnotationIntrospector implements Serializable {
   private static final long serialVersionUID = 1L;
   protected final AnnotationIntrospector _primary;
   protected final AnnotationIntrospector _secondary;

   public AnnotationIntrospectorPair(AnnotationIntrospector var1, AnnotationIntrospector var2) {
      super();
      this._primary = var1;
      this._secondary = var2;
   }

   public Version version() {
      return this._primary.version();
   }

   public static AnnotationIntrospector create(AnnotationIntrospector var0, AnnotationIntrospector var1) {
      if (var0 == null) {
         return var1;
      } else {
         return (AnnotationIntrospector)(var1 == null ? var0 : new AnnotationIntrospectorPair(var0, var1));
      }
   }

   public Collection allIntrospectors() {
      return this.allIntrospectors(new ArrayList());
   }

   public Collection allIntrospectors(Collection var1) {
      this._primary.allIntrospectors(var1);
      this._secondary.allIntrospectors(var1);
      return var1;
   }

   public boolean isAnnotationBundle(Annotation var1) {
      return this._primary.isAnnotationBundle(var1) || this._secondary.isAnnotationBundle(var1);
   }

   public PropertyName findRootName(AnnotatedClass var1) {
      PropertyName var2 = this._primary.findRootName(var1);
      if (var2 == null) {
         return this._secondary.findRootName(var1);
      } else if (var2.hasSimpleName()) {
         return var2;
      } else {
         PropertyName var3 = this._secondary.findRootName(var1);
         return var3 == null ? var2 : var3;
      }
   }

   public JsonIgnoreProperties$Value findPropertyIgnorals(Annotated var1) {
      JsonIgnoreProperties$Value var2 = this._secondary.findPropertyIgnorals(var1);
      JsonIgnoreProperties$Value var3 = this._primary.findPropertyIgnorals(var1);
      return var2 == null ? var3 : var2.withOverrides(var3);
   }

   public Boolean isIgnorableType(AnnotatedClass var1) {
      Boolean var2 = this._primary.isIgnorableType(var1);
      if (var2 == null) {
         var2 = this._secondary.isIgnorableType(var1);
      }

      return var2;
   }

   public Object findFilterId(Annotated var1) {
      Object var2 = this._primary.findFilterId(var1);
      if (var2 == null) {
         var2 = this._secondary.findFilterId(var1);
      }

      return var2;
   }

   public Object findNamingStrategy(AnnotatedClass var1) {
      Object var2 = this._primary.findNamingStrategy(var1);
      if (var2 == null) {
         var2 = this._secondary.findNamingStrategy(var1);
      }

      return var2;
   }

   public String findClassDescription(AnnotatedClass var1) {
      String var2 = this._primary.findClassDescription(var1);
      if (var2 == null || var2.isEmpty()) {
         var2 = this._secondary.findClassDescription(var1);
      }

      return var2;
   }

   /** @deprecated */
   @Deprecated
   public String[] findPropertiesToIgnore(Annotated var1) {
      String[] var2 = this._primary.findPropertiesToIgnore(var1);
      if (var2 == null) {
         var2 = this._secondary.findPropertiesToIgnore(var1);
      }

      return var2;
   }

   /** @deprecated */
   @Deprecated
   public String[] findPropertiesToIgnore(Annotated var1, boolean var2) {
      String[] var3 = this._primary.findPropertiesToIgnore(var1, var2);
      if (var3 == null) {
         var3 = this._secondary.findPropertiesToIgnore(var1, var2);
      }

      return var3;
   }

   /** @deprecated */
   @Deprecated
   public Boolean findIgnoreUnknownProperties(AnnotatedClass var1) {
      Boolean var2 = this._primary.findIgnoreUnknownProperties(var1);
      if (var2 == null) {
         var2 = this._secondary.findIgnoreUnknownProperties(var1);
      }

      return var2;
   }

   public VisibilityChecker findAutoDetectVisibility(AnnotatedClass var1, VisibilityChecker var2) {
      var2 = this._secondary.findAutoDetectVisibility(var1, var2);
      return this._primary.findAutoDetectVisibility(var1, var2);
   }

   public TypeResolverBuilder findTypeResolver(MapperConfig var1, AnnotatedClass var2, JavaType var3) {
      TypeResolverBuilder var4 = this._primary.findTypeResolver(var1, var2, var3);
      if (var4 == null) {
         var4 = this._secondary.findTypeResolver(var1, var2, var3);
      }

      return var4;
   }

   public TypeResolverBuilder findPropertyTypeResolver(MapperConfig var1, AnnotatedMember var2, JavaType var3) {
      TypeResolverBuilder var4 = this._primary.findPropertyTypeResolver(var1, var2, var3);
      if (var4 == null) {
         var4 = this._secondary.findPropertyTypeResolver(var1, var2, var3);
      }

      return var4;
   }

   public TypeResolverBuilder findPropertyContentTypeResolver(MapperConfig var1, AnnotatedMember var2, JavaType var3) {
      TypeResolverBuilder var4 = this._primary.findPropertyContentTypeResolver(var1, var2, var3);
      if (var4 == null) {
         var4 = this._secondary.findPropertyContentTypeResolver(var1, var2, var3);
      }

      return var4;
   }

   public List findSubtypes(Annotated var1) {
      List var2 = this._primary.findSubtypes(var1);
      List var3 = this._secondary.findSubtypes(var1);
      if (var2 != null && !var2.isEmpty()) {
         if (var3 != null && !var3.isEmpty()) {
            ArrayList var4 = new ArrayList(var2.size() + var3.size());
            var4.addAll(var2);
            var4.addAll(var3);
            return var4;
         } else {
            return var2;
         }
      } else {
         return var3;
      }
   }

   public String findTypeName(AnnotatedClass var1) {
      String var2 = this._primary.findTypeName(var1);
      if (var2 == null || var2.length() == 0) {
         var2 = this._secondary.findTypeName(var1);
      }

      return var2;
   }

   public AnnotationIntrospector$ReferenceProperty findReferenceType(AnnotatedMember var1) {
      AnnotationIntrospector$ReferenceProperty var2 = this._primary.findReferenceType(var1);
      return var2 == null ? this._secondary.findReferenceType(var1) : var2;
   }

   public NameTransformer findUnwrappingNameTransformer(AnnotatedMember var1) {
      NameTransformer var2 = this._primary.findUnwrappingNameTransformer(var1);
      return var2 == null ? this._secondary.findUnwrappingNameTransformer(var1) : var2;
   }

   public JacksonInject$Value findInjectableValue(AnnotatedMember var1) {
      JacksonInject$Value var2 = this._primary.findInjectableValue(var1);
      return var2 == null ? this._secondary.findInjectableValue(var1) : var2;
   }

   public boolean hasIgnoreMarker(AnnotatedMember var1) {
      return this._primary.hasIgnoreMarker(var1) || this._secondary.hasIgnoreMarker(var1);
   }

   public Boolean hasRequiredMarker(AnnotatedMember var1) {
      Boolean var2 = this._primary.hasRequiredMarker(var1);
      return var2 == null ? this._secondary.hasRequiredMarker(var1) : var2;
   }

   /** @deprecated */
   @Deprecated
   public Object findInjectableValueId(AnnotatedMember var1) {
      Object var2 = this._primary.findInjectableValueId(var1);
      return var2 == null ? this._secondary.findInjectableValueId(var1) : var2;
   }

   public Object findSerializer(Annotated var1) {
      Object var2 = this._primary.findSerializer(var1);
      return this._isExplicitClassOrOb(var2, JsonSerializer$None.class) ? var2 : this._explicitClassOrOb(this._secondary.findSerializer(var1), JsonSerializer$None.class);
   }

   public Object findKeySerializer(Annotated var1) {
      Object var2 = this._primary.findKeySerializer(var1);
      return this._isExplicitClassOrOb(var2, JsonSerializer$None.class) ? var2 : this._explicitClassOrOb(this._secondary.findKeySerializer(var1), JsonSerializer$None.class);
   }

   public Object findContentSerializer(Annotated var1) {
      Object var2 = this._primary.findContentSerializer(var1);
      return this._isExplicitClassOrOb(var2, JsonSerializer$None.class) ? var2 : this._explicitClassOrOb(this._secondary.findContentSerializer(var1), JsonSerializer$None.class);
   }

   public Object findNullSerializer(Annotated var1) {
      Object var2 = this._primary.findNullSerializer(var1);
      return this._isExplicitClassOrOb(var2, JsonSerializer$None.class) ? var2 : this._explicitClassOrOb(this._secondary.findNullSerializer(var1), JsonSerializer$None.class);
   }

   /** @deprecated */
   @Deprecated
   public JsonInclude$Include findSerializationInclusion(Annotated var1, JsonInclude$Include var2) {
      var2 = this._secondary.findSerializationInclusion(var1, var2);
      var2 = this._primary.findSerializationInclusion(var1, var2);
      return var2;
   }

   /** @deprecated */
   @Deprecated
   public JsonInclude$Include findSerializationInclusionForContent(Annotated var1, JsonInclude$Include var2) {
      var2 = this._secondary.findSerializationInclusionForContent(var1, var2);
      var2 = this._primary.findSerializationInclusionForContent(var1, var2);
      return var2;
   }

   public JsonInclude$Value findPropertyInclusion(Annotated var1) {
      JsonInclude$Value var2 = this._secondary.findPropertyInclusion(var1);
      JsonInclude$Value var3 = this._primary.findPropertyInclusion(var1);
      return var2 == null ? var3 : var2.withOverrides(var3);
   }

   public JsonSerialize$Typing findSerializationTyping(Annotated var1) {
      JsonSerialize$Typing var2 = this._primary.findSerializationTyping(var1);
      return var2 == null ? this._secondary.findSerializationTyping(var1) : var2;
   }

   public Object findSerializationConverter(Annotated var1) {
      Object var2 = this._primary.findSerializationConverter(var1);
      return var2 == null ? this._secondary.findSerializationConverter(var1) : var2;
   }

   public Object findSerializationContentConverter(AnnotatedMember var1) {
      Object var2 = this._primary.findSerializationContentConverter(var1);
      return var2 == null ? this._secondary.findSerializationContentConverter(var1) : var2;
   }

   public Class[] findViews(Annotated var1) {
      Class[] var2 = this._primary.findViews(var1);
      if (var2 == null) {
         var2 = this._secondary.findViews(var1);
      }

      return var2;
   }

   public Boolean isTypeId(AnnotatedMember var1) {
      Boolean var2 = this._primary.isTypeId(var1);
      return var2 == null ? this._secondary.isTypeId(var1) : var2;
   }

   public ObjectIdInfo findObjectIdInfo(Annotated var1) {
      ObjectIdInfo var2 = this._primary.findObjectIdInfo(var1);
      return var2 == null ? this._secondary.findObjectIdInfo(var1) : var2;
   }

   public ObjectIdInfo findObjectReferenceInfo(Annotated var1, ObjectIdInfo var2) {
      var2 = this._secondary.findObjectReferenceInfo(var1, var2);
      var2 = this._primary.findObjectReferenceInfo(var1, var2);
      return var2;
   }

   public JsonFormat$Value findFormat(Annotated var1) {
      JsonFormat$Value var2 = this._primary.findFormat(var1);
      JsonFormat$Value var3 = this._secondary.findFormat(var1);
      return var3 == null ? var2 : var3.withOverrides(var2);
   }

   public PropertyName findWrapperName(Annotated var1) {
      PropertyName var2 = this._primary.findWrapperName(var1);
      if (var2 == null) {
         var2 = this._secondary.findWrapperName(var1);
      } else if (var2 == PropertyName.USE_DEFAULT) {
         PropertyName var3 = this._secondary.findWrapperName(var1);
         if (var3 != null) {
            var2 = var3;
         }
      }

      return var2;
   }

   public String findPropertyDefaultValue(Annotated var1) {
      String var2 = this._primary.findPropertyDefaultValue(var1);
      return var2 != null && !var2.isEmpty() ? var2 : this._secondary.findPropertyDefaultValue(var1);
   }

   public String findPropertyDescription(Annotated var1) {
      String var2 = this._primary.findPropertyDescription(var1);
      return var2 == null ? this._secondary.findPropertyDescription(var1) : var2;
   }

   public Integer findPropertyIndex(Annotated var1) {
      Integer var2 = this._primary.findPropertyIndex(var1);
      return var2 == null ? this._secondary.findPropertyIndex(var1) : var2;
   }

   public String findImplicitPropertyName(AnnotatedMember var1) {
      String var2 = this._primary.findImplicitPropertyName(var1);
      return var2 == null ? this._secondary.findImplicitPropertyName(var1) : var2;
   }

   public List findPropertyAliases(Annotated var1) {
      List var2 = this._primary.findPropertyAliases(var1);
      return var2 == null ? this._secondary.findPropertyAliases(var1) : var2;
   }

   public JsonProperty$Access findPropertyAccess(Annotated var1) {
      JsonProperty$Access var2 = this._primary.findPropertyAccess(var1);
      if (var2 != null && var2 != JsonProperty$Access.AUTO) {
         return var2;
      } else {
         var2 = this._secondary.findPropertyAccess(var1);
         return var2 != null ? var2 : JsonProperty$Access.AUTO;
      }
   }

   public AnnotatedMethod resolveSetterConflict(MapperConfig var1, AnnotatedMethod var2, AnnotatedMethod var3) {
      AnnotatedMethod var4 = this._primary.resolveSetterConflict(var1, var2, var3);
      if (var4 == null) {
         var4 = this._secondary.resolveSetterConflict(var1, var2, var3);
      }

      return var4;
   }

   public JavaType refineSerializationType(MapperConfig var1, Annotated var2, JavaType var3) throws JsonMappingException {
      JavaType var4 = this._secondary.refineSerializationType(var1, var2, var3);
      return this._primary.refineSerializationType(var1, var2, var4);
   }

   /** @deprecated */
   @Deprecated
   public Class findSerializationType(Annotated var1) {
      Class var2 = this._primary.findSerializationType(var1);
      return var2 == null ? this._secondary.findSerializationType(var1) : var2;
   }

   /** @deprecated */
   @Deprecated
   public Class findSerializationKeyType(Annotated var1, JavaType var2) {
      Class var3 = this._primary.findSerializationKeyType(var1, var2);
      return var3 == null ? this._secondary.findSerializationKeyType(var1, var2) : var3;
   }

   /** @deprecated */
   @Deprecated
   public Class findSerializationContentType(Annotated var1, JavaType var2) {
      Class var3 = this._primary.findSerializationContentType(var1, var2);
      return var3 == null ? this._secondary.findSerializationContentType(var1, var2) : var3;
   }

   public String[] findSerializationPropertyOrder(AnnotatedClass var1) {
      String[] var2 = this._primary.findSerializationPropertyOrder(var1);
      return var2 == null ? this._secondary.findSerializationPropertyOrder(var1) : var2;
   }

   public Boolean findSerializationSortAlphabetically(Annotated var1) {
      Boolean var2 = this._primary.findSerializationSortAlphabetically(var1);
      return var2 == null ? this._secondary.findSerializationSortAlphabetically(var1) : var2;
   }

   public void findAndAddVirtualProperties(MapperConfig var1, AnnotatedClass var2, List var3) {
      this._primary.findAndAddVirtualProperties(var1, var2, var3);
      this._secondary.findAndAddVirtualProperties(var1, var2, var3);
   }

   public PropertyName findNameForSerialization(Annotated var1) {
      PropertyName var2 = this._primary.findNameForSerialization(var1);
      if (var2 == null) {
         var2 = this._secondary.findNameForSerialization(var1);
      } else if (var2 == PropertyName.USE_DEFAULT) {
         PropertyName var3 = this._secondary.findNameForSerialization(var1);
         if (var3 != null) {
            var2 = var3;
         }
      }

      return var2;
   }

   public Boolean hasAsValue(Annotated var1) {
      Boolean var2 = this._primary.hasAsValue(var1);
      if (var2 == null) {
         var2 = this._secondary.hasAsValue(var1);
      }

      return var2;
   }

   public Boolean hasAnyGetter(Annotated var1) {
      Boolean var2 = this._primary.hasAnyGetter(var1);
      if (var2 == null) {
         var2 = this._secondary.hasAnyGetter(var1);
      }

      return var2;
   }

   public String[] findEnumValues(Class var1, Enum[] var2, String[] var3) {
      var3 = this._secondary.findEnumValues(var1, var2, var3);
      var3 = this._primary.findEnumValues(var1, var2, var3);
      return var3;
   }

   public Enum findDefaultEnumValue(Class var1) {
      Enum var2 = this._primary.findDefaultEnumValue(var1);
      return var2 == null ? this._secondary.findDefaultEnumValue(var1) : var2;
   }

   /** @deprecated */
   @Deprecated
   public String findEnumValue(Enum var1) {
      String var2 = this._primary.findEnumValue(var1);
      return var2 == null ? this._secondary.findEnumValue(var1) : var2;
   }

   /** @deprecated */
   @Deprecated
   public boolean hasAsValueAnnotation(AnnotatedMethod var1) {
      return this._primary.hasAsValueAnnotation(var1) || this._secondary.hasAsValueAnnotation(var1);
   }

   /** @deprecated */
   @Deprecated
   public boolean hasAnyGetterAnnotation(AnnotatedMethod var1) {
      return this._primary.hasAnyGetterAnnotation(var1) || this._secondary.hasAnyGetterAnnotation(var1);
   }

   public Object findDeserializer(Annotated var1) {
      Object var2 = this._primary.findDeserializer(var1);
      return this._isExplicitClassOrOb(var2, JsonDeserializer$None.class) ? var2 : this._explicitClassOrOb(this._secondary.findDeserializer(var1), JsonDeserializer$None.class);
   }

   public Object findKeyDeserializer(Annotated var1) {
      Object var2 = this._primary.findKeyDeserializer(var1);
      return this._isExplicitClassOrOb(var2, KeyDeserializer$None.class) ? var2 : this._explicitClassOrOb(this._secondary.findKeyDeserializer(var1), KeyDeserializer$None.class);
   }

   public Object findContentDeserializer(Annotated var1) {
      Object var2 = this._primary.findContentDeserializer(var1);
      return this._isExplicitClassOrOb(var2, JsonDeserializer$None.class) ? var2 : this._explicitClassOrOb(this._secondary.findContentDeserializer(var1), JsonDeserializer$None.class);
   }

   public Object findDeserializationConverter(Annotated var1) {
      Object var2 = this._primary.findDeserializationConverter(var1);
      return var2 == null ? this._secondary.findDeserializationConverter(var1) : var2;
   }

   public Object findDeserializationContentConverter(AnnotatedMember var1) {
      Object var2 = this._primary.findDeserializationContentConverter(var1);
      return var2 == null ? this._secondary.findDeserializationContentConverter(var1) : var2;
   }

   public JavaType refineDeserializationType(MapperConfig var1, Annotated var2, JavaType var3) throws JsonMappingException {
      JavaType var4 = this._secondary.refineDeserializationType(var1, var2, var3);
      return this._primary.refineDeserializationType(var1, var2, var4);
   }

   /** @deprecated */
   @Deprecated
   public Class findDeserializationType(Annotated var1, JavaType var2) {
      Class var3 = this._primary.findDeserializationType(var1, var2);
      return var3 != null ? var3 : this._secondary.findDeserializationType(var1, var2);
   }

   /** @deprecated */
   @Deprecated
   public Class findDeserializationKeyType(Annotated var1, JavaType var2) {
      Class var3 = this._primary.findDeserializationKeyType(var1, var2);
      return var3 == null ? this._secondary.findDeserializationKeyType(var1, var2) : var3;
   }

   /** @deprecated */
   @Deprecated
   public Class findDeserializationContentType(Annotated var1, JavaType var2) {
      Class var3 = this._primary.findDeserializationContentType(var1, var2);
      return var3 == null ? this._secondary.findDeserializationContentType(var1, var2) : var3;
   }

   public Object findValueInstantiator(AnnotatedClass var1) {
      Object var2 = this._primary.findValueInstantiator(var1);
      return var2 == null ? this._secondary.findValueInstantiator(var1) : var2;
   }

   public Class findPOJOBuilder(AnnotatedClass var1) {
      Class var2 = this._primary.findPOJOBuilder(var1);
      return var2 == null ? this._secondary.findPOJOBuilder(var1) : var2;
   }

   public JsonPOJOBuilder$Value findPOJOBuilderConfig(AnnotatedClass var1) {
      JsonPOJOBuilder$Value var2 = this._primary.findPOJOBuilderConfig(var1);
      return var2 == null ? this._secondary.findPOJOBuilderConfig(var1) : var2;
   }

   public PropertyName findNameForDeserialization(Annotated var1) {
      PropertyName var2 = this._primary.findNameForDeserialization(var1);
      if (var2 == null) {
         var2 = this._secondary.findNameForDeserialization(var1);
      } else if (var2 == PropertyName.USE_DEFAULT) {
         PropertyName var3 = this._secondary.findNameForDeserialization(var1);
         if (var3 != null) {
            var2 = var3;
         }
      }

      return var2;
   }

   public Boolean hasAnySetter(Annotated var1) {
      Boolean var2 = this._primary.hasAnySetter(var1);
      if (var2 == null) {
         var2 = this._secondary.hasAnySetter(var1);
      }

      return var2;
   }

   public JsonSetter$Value findSetterInfo(Annotated var1) {
      JsonSetter$Value var2 = this._secondary.findSetterInfo(var1);
      JsonSetter$Value var3 = this._primary.findSetterInfo(var1);
      return var2 == null ? var3 : var2.withOverrides(var3);
   }

   public Boolean findMergeInfo(Annotated var1) {
      Boolean var2 = this._primary.findMergeInfo(var1);
      if (var2 == null) {
         var2 = this._secondary.findMergeInfo(var1);
      }

      return var2;
   }

   /** @deprecated */
   @Deprecated
   public boolean hasCreatorAnnotation(Annotated var1) {
      return this._primary.hasCreatorAnnotation(var1) || this._secondary.hasCreatorAnnotation(var1);
   }

   /** @deprecated */
   @Deprecated
   public JsonCreator$Mode findCreatorBinding(Annotated var1) {
      JsonCreator$Mode var2 = this._primary.findCreatorBinding(var1);
      return var2 != null ? var2 : this._secondary.findCreatorBinding(var1);
   }

   public JsonCreator$Mode findCreatorAnnotation(MapperConfig var1, Annotated var2) {
      JsonCreator$Mode var3 = this._primary.findCreatorAnnotation(var1, var2);
      return var3 == null ? this._secondary.findCreatorAnnotation(var1, var2) : var3;
   }

   /** @deprecated */
   @Deprecated
   public boolean hasAnySetterAnnotation(AnnotatedMethod var1) {
      return this._primary.hasAnySetterAnnotation(var1) || this._secondary.hasAnySetterAnnotation(var1);
   }

   protected boolean _isExplicitClassOrOb(Object var1, Class var2) {
      if (var1 != null && var1 != var2) {
         if (var1 instanceof Class) {
            return !ClassUtil.isBogusClass((Class)var1);
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   protected Object _explicitClassOrOb(Object var1, Class var2) {
      if (var1 != null && var1 != var2) {
         return var1 instanceof Class && ClassUtil.isBogusClass((Class)var1) ? null : var1;
      } else {
         return null;
      }
   }
}
