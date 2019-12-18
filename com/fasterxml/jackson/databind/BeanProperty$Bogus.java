package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.annotation.JsonFormat$Value;
import com.fasterxml.jackson.annotation.JsonInclude$Value;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;

public class BeanProperty$Bogus implements BeanProperty {
   public BeanProperty$Bogus() {
      super();
   }

   public String getName() {
      return "";
   }

   public PropertyName getFullName() {
      return PropertyName.NO_NAME;
   }

   public JavaType getType() {
      return TypeFactory.unknownType();
   }

   public PropertyName getWrapperName() {
      return null;
   }

   public PropertyMetadata getMetadata() {
      return PropertyMetadata.STD_REQUIRED_OR_OPTIONAL;
   }

   public boolean isRequired() {
      return false;
   }

   public boolean isVirtual() {
      return false;
   }

   public Annotation getAnnotation(Class var1) {
      return null;
   }

   public Annotation getContextAnnotation(Class var1) {
      return null;
   }

   public AnnotatedMember getMember() {
      return null;
   }

   /** @deprecated */
   @Deprecated
   public JsonFormat$Value findFormatOverrides(AnnotationIntrospector var1) {
      return JsonFormat$Value.empty();
   }

   public JsonFormat$Value findPropertyFormat(MapperConfig var1, Class var2) {
      return JsonFormat$Value.empty();
   }

   public JsonInclude$Value findPropertyInclusion(MapperConfig var1, Class var2) {
      return null;
   }

   public List findAliases(MapperConfig var1) {
      return Collections.emptyList();
   }

   public void depositSchemaProperty(JsonObjectFormatVisitor var1, SerializerProvider var2) throws JsonMappingException {
   }
}
