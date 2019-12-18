package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.annotation.JacksonInject$Value;
import com.fasterxml.jackson.annotation.JsonCreator$Mode;
import com.fasterxml.jackson.annotation.JsonFormat$Value;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties$Value;
import com.fasterxml.jackson.annotation.JsonInclude$Include;
import com.fasterxml.jackson.annotation.JsonInclude$Value;
import com.fasterxml.jackson.annotation.JsonProperty$Access;
import com.fasterxml.jackson.annotation.JsonSetter$Value;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder$Value;
import com.fasterxml.jackson.databind.annotation.JsonSerialize$Typing;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import com.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class AnnotationIntrospector implements Versioned, Serializable {
   public AnnotationIntrospector() {
      super();
   }

   public static AnnotationIntrospector nopInstance() {
      return NopAnnotationIntrospector.instance;
   }

   public static AnnotationIntrospector pair(AnnotationIntrospector var0, AnnotationIntrospector var1) {
      return new AnnotationIntrospectorPair(var0, var1);
   }

   public Collection allIntrospectors() {
      return Collections.singletonList(this);
   }

   public Collection allIntrospectors(Collection var1) {
      var1.add(this);
      return var1;
   }

   public abstract Version version();

   public boolean isAnnotationBundle(Annotation var1) {
      return false;
   }

   public ObjectIdInfo findObjectIdInfo(Annotated var1) {
      return null;
   }

   public ObjectIdInfo findObjectReferenceInfo(Annotated var1, ObjectIdInfo var2) {
      return var2;
   }

   public PropertyName findRootName(AnnotatedClass var1) {
      return null;
   }

   public JsonIgnoreProperties$Value findPropertyIgnorals(Annotated var1) {
      return JsonIgnoreProperties$Value.empty();
   }

   public Boolean isIgnorableType(AnnotatedClass var1) {
      return null;
   }

   public Object findFilterId(Annotated var1) {
      return null;
   }

   public Object findNamingStrategy(AnnotatedClass var1) {
      return null;
   }

   public String findClassDescription(AnnotatedClass var1) {
      return null;
   }

   /** @deprecated */
   @Deprecated
   public String[] findPropertiesToIgnore(Annotated var1, boolean var2) {
      return null;
   }

   /** @deprecated */
   @Deprecated
   public String[] findPropertiesToIgnore(Annotated var1) {
      return null;
   }

   /** @deprecated */
   @Deprecated
   public Boolean findIgnoreUnknownProperties(AnnotatedClass var1) {
      return null;
   }

   public VisibilityChecker findAutoDetectVisibility(AnnotatedClass var1, VisibilityChecker var2) {
      return var2;
   }

   public TypeResolverBuilder findTypeResolver(MapperConfig var1, AnnotatedClass var2, JavaType var3) {
      return null;
   }

   public TypeResolverBuilder findPropertyTypeResolver(MapperConfig var1, AnnotatedMember var2, JavaType var3) {
      return null;
   }

   public TypeResolverBuilder findPropertyContentTypeResolver(MapperConfig var1, AnnotatedMember var2, JavaType var3) {
      return null;
   }

   public List findSubtypes(Annotated var1) {
      return null;
   }

   public String findTypeName(AnnotatedClass var1) {
      return null;
   }

   public Boolean isTypeId(AnnotatedMember var1) {
      return null;
   }

   public AnnotationIntrospector$ReferenceProperty findReferenceType(AnnotatedMember var1) {
      return null;
   }

   public NameTransformer findUnwrappingNameTransformer(AnnotatedMember var1) {
      return null;
   }

   public boolean hasIgnoreMarker(AnnotatedMember var1) {
      return false;
   }

   public JacksonInject$Value findInjectableValue(AnnotatedMember var1) {
      Object var2 = this.findInjectableValueId(var1);
      return var2 != null ? JacksonInject$Value.forId(var2) : null;
   }

   public Boolean hasRequiredMarker(AnnotatedMember var1) {
      return null;
   }

   public Class[] findViews(Annotated var1) {
      return null;
   }

   public JsonFormat$Value findFormat(Annotated var1) {
      return JsonFormat$Value.empty();
   }

   public PropertyName findWrapperName(Annotated var1) {
      return null;
   }

   public String findPropertyDefaultValue(Annotated var1) {
      return null;
   }

   public String findPropertyDescription(Annotated var1) {
      return null;
   }

   public Integer findPropertyIndex(Annotated var1) {
      return null;
   }

   public String findImplicitPropertyName(AnnotatedMember var1) {
      return null;
   }

   public List findPropertyAliases(Annotated var1) {
      return null;
   }

   public JsonProperty$Access findPropertyAccess(Annotated var1) {
      return null;
   }

   public AnnotatedMethod resolveSetterConflict(MapperConfig var1, AnnotatedMethod var2, AnnotatedMethod var3) {
      return null;
   }

   /** @deprecated */
   @Deprecated
   public Object findInjectableValueId(AnnotatedMember var1) {
      return null;
   }

   public Object findSerializer(Annotated var1) {
      return null;
   }

   public Object findKeySerializer(Annotated var1) {
      return null;
   }

   public Object findContentSerializer(Annotated var1) {
      return null;
   }

   public Object findNullSerializer(Annotated var1) {
      return null;
   }

   public JsonSerialize$Typing findSerializationTyping(Annotated var1) {
      return null;
   }

   public Object findSerializationConverter(Annotated var1) {
      return null;
   }

   public Object findSerializationContentConverter(AnnotatedMember var1) {
      return null;
   }

   public JsonInclude$Value findPropertyInclusion(Annotated var1) {
      return JsonInclude$Value.empty();
   }

   /** @deprecated */
   @Deprecated
   public JsonInclude$Include findSerializationInclusion(Annotated var1, JsonInclude$Include var2) {
      return var2;
   }

   /** @deprecated */
   @Deprecated
   public JsonInclude$Include findSerializationInclusionForContent(Annotated var1, JsonInclude$Include var2) {
      return var2;
   }

   public JavaType refineSerializationType(MapperConfig var1, Annotated var2, JavaType var3) throws JsonMappingException {
      return var3;
   }

   /** @deprecated */
   @Deprecated
   public Class findSerializationType(Annotated var1) {
      return null;
   }

   /** @deprecated */
   @Deprecated
   public Class findSerializationKeyType(Annotated var1, JavaType var2) {
      return null;
   }

   /** @deprecated */
   @Deprecated
   public Class findSerializationContentType(Annotated var1, JavaType var2) {
      return null;
   }

   public String[] findSerializationPropertyOrder(AnnotatedClass var1) {
      return null;
   }

   public Boolean findSerializationSortAlphabetically(Annotated var1) {
      return null;
   }

   public void findAndAddVirtualProperties(MapperConfig var1, AnnotatedClass var2, List var3) {
   }

   public PropertyName findNameForSerialization(Annotated var1) {
      return null;
   }

   public Boolean hasAsValue(Annotated var1) {
      return var1 instanceof AnnotatedMethod && this.hasAsValueAnnotation((AnnotatedMethod)var1) ? true : null;
   }

   public Boolean hasAnyGetter(Annotated var1) {
      return var1 instanceof AnnotatedMethod && this.hasAnyGetterAnnotation((AnnotatedMethod)var1) ? true : null;
   }

   public String[] findEnumValues(Class var1, Enum[] var2, String[] var3) {
      return var3;
   }

   public Enum findDefaultEnumValue(Class var1) {
      return null;
   }

   /** @deprecated */
   @Deprecated
   public String findEnumValue(Enum var1) {
      return var1.name();
   }

   /** @deprecated */
   @Deprecated
   public boolean hasAsValueAnnotation(AnnotatedMethod var1) {
      return false;
   }

   /** @deprecated */
   @Deprecated
   public boolean hasAnyGetterAnnotation(AnnotatedMethod var1) {
      return false;
   }

   public Object findDeserializer(Annotated var1) {
      return null;
   }

   public Object findKeyDeserializer(Annotated var1) {
      return null;
   }

   public Object findContentDeserializer(Annotated var1) {
      return null;
   }

   public Object findDeserializationConverter(Annotated var1) {
      return null;
   }

   public Object findDeserializationContentConverter(AnnotatedMember var1) {
      return null;
   }

   public JavaType refineDeserializationType(MapperConfig var1, Annotated var2, JavaType var3) throws JsonMappingException {
      return var3;
   }

   /** @deprecated */
   @Deprecated
   public Class findDeserializationType(Annotated var1, JavaType var2) {
      return null;
   }

   /** @deprecated */
   @Deprecated
   public Class findDeserializationKeyType(Annotated var1, JavaType var2) {
      return null;
   }

   /** @deprecated */
   @Deprecated
   public Class findDeserializationContentType(Annotated var1, JavaType var2) {
      return null;
   }

   public Object findValueInstantiator(AnnotatedClass var1) {
      return null;
   }

   public Class findPOJOBuilder(AnnotatedClass var1) {
      return null;
   }

   public JsonPOJOBuilder$Value findPOJOBuilderConfig(AnnotatedClass var1) {
      return null;
   }

   public PropertyName findNameForDeserialization(Annotated var1) {
      return null;
   }

   public Boolean hasAnySetter(Annotated var1) {
      return null;
   }

   public JsonSetter$Value findSetterInfo(Annotated var1) {
      return JsonSetter$Value.empty();
   }

   public Boolean findMergeInfo(Annotated var1) {
      return null;
   }

   public JsonCreator$Mode findCreatorAnnotation(MapperConfig var1, Annotated var2) {
      if (this.hasCreatorAnnotation(var2)) {
         JsonCreator$Mode var3 = this.findCreatorBinding(var2);
         if (var3 == null) {
            var3 = JsonCreator$Mode.DEFAULT;
         }

         return var3;
      } else {
         return null;
      }
   }

   /** @deprecated */
   @Deprecated
   public boolean hasCreatorAnnotation(Annotated var1) {
      return false;
   }

   /** @deprecated */
   @Deprecated
   public JsonCreator$Mode findCreatorBinding(Annotated var1) {
      return null;
   }

   /** @deprecated */
   @Deprecated
   public boolean hasAnySetterAnnotation(AnnotatedMethod var1) {
      return false;
   }

   protected Annotation _findAnnotation(Annotated var1, Class var2) {
      return var1.getAnnotation(var2);
   }

   protected boolean _hasAnnotation(Annotated var1, Class var2) {
      return var1.hasAnnotation(var2);
   }

   protected boolean _hasOneOf(Annotated var1, Class[] var2) {
      return var1.hasOneOf(var2);
   }
}
