package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.annotation.JsonFormat$Value;
import com.fasterxml.jackson.annotation.JsonInclude$Value;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.util.Named;
import java.lang.annotation.Annotation;
import java.util.List;

public interface BeanProperty extends Named {
   JsonFormat$Value EMPTY_FORMAT = new JsonFormat$Value();
   JsonInclude$Value EMPTY_INCLUDE = JsonInclude$Value.empty();

   String getName();

   PropertyName getFullName();

   JavaType getType();

   PropertyName getWrapperName();

   PropertyMetadata getMetadata();

   boolean isRequired();

   boolean isVirtual();

   Annotation getAnnotation(Class var1);

   Annotation getContextAnnotation(Class var1);

   AnnotatedMember getMember();

   /** @deprecated */
   @Deprecated
   JsonFormat$Value findFormatOverrides(AnnotationIntrospector var1);

   JsonFormat$Value findPropertyFormat(MapperConfig var1, Class var2);

   JsonInclude$Value findPropertyInclusion(MapperConfig var1, Class var2);

   List findAliases(MapperConfig var1);

   void depositSchemaProperty(JsonObjectFormatVisitor var1, SerializerProvider var2) throws JsonMappingException;
}
