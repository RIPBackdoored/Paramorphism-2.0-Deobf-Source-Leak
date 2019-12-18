package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.annotation.JsonInclude$Include;
import com.fasterxml.jackson.annotation.JsonInclude$Value;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize$Typing;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.util.Annotations;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.fasterxml.jackson.databind.util.ClassUtil;

public class PropertyBuilder {
   private static final Object NO_DEFAULT_MARKER;
   protected final SerializationConfig _config;
   protected final BeanDescription _beanDesc;
   protected final AnnotationIntrospector _annotationIntrospector;
   protected Object _defaultBean;
   protected final JsonInclude$Value _defaultInclusion;
   protected final boolean _useRealPropertyDefaults;

   public PropertyBuilder(SerializationConfig var1, BeanDescription var2) {
      super();
      this._config = var1;
      this._beanDesc = var2;
      JsonInclude$Value var3 = JsonInclude$Value.merge(var2.findPropertyInclusion(JsonInclude$Value.empty()), var1.getDefaultPropertyInclusion(var2.getBeanClass(), JsonInclude$Value.empty()));
      this._defaultInclusion = JsonInclude$Value.merge(var1.getDefaultPropertyInclusion(), var3);
      this._useRealPropertyDefaults = var3.getValueInclusion() == JsonInclude$Include.NON_DEFAULT;
      this._annotationIntrospector = this._config.getAnnotationIntrospector();
   }

   public Annotations getClassAnnotations() {
      return this._beanDesc.getClassAnnotations();
   }

   protected BeanPropertyWriter buildWriter(SerializerProvider var1, BeanPropertyDefinition var2, JavaType var3, JsonSerializer var4, TypeSerializer var5, TypeSerializer var6, AnnotatedMember var7, boolean var8) throws JsonMappingException {
      // $FF: Couldn't be decompiled
   }

   protected JavaType findSerializationType(Annotated var1, boolean var2, JavaType var3) throws JsonMappingException {
      JavaType var4 = this._annotationIntrospector.refineSerializationType(this._config, var1, var3);
      if (var4 != var3) {
         Class var5 = var4.getRawClass();
         Class var6 = var3.getRawClass();
         if (!var5.isAssignableFrom(var6) && !var6.isAssignableFrom(var5)) {
            throw new IllegalArgumentException("Illegal concrete-type annotation for method '" + var1.getName() + "': class " + var5.getName() + " not a super-type of (declared) class " + var6.getName());
         }

         var2 = true;
         var3 = var4;
      }

      JsonSerialize$Typing var7 = this._annotationIntrospector.findSerializationTyping(var1);
      if (var7 != null && var7 != JsonSerialize$Typing.DEFAULT_TYPING) {
         var2 = var7 == JsonSerialize$Typing.STATIC;
      }

      return var2 ? var3.withStaticTyping() : null;
   }

   protected Object getDefaultBean() {
      Object var1 = this._defaultBean;
      if (var1 == null) {
         var1 = this._beanDesc.instantiateBean(this._config.canOverrideAccessModifiers());
         if (var1 == null) {
            var1 = NO_DEFAULT_MARKER;
         }

         this._defaultBean = var1;
      }

      return var1 == NO_DEFAULT_MARKER ? null : this._defaultBean;
   }

   /** @deprecated */
   @Deprecated
   protected Object getPropertyDefaultValue(String var1, AnnotatedMember var2, JavaType var3) {
      Object var4 = this.getDefaultBean();
      if (var4 == null) {
         return this.getDefaultValue(var3);
      } else {
         Object var10000;
         try {
            var10000 = var2.getValue(var4);
         } catch (Exception var6) {
            return this._throwWrapped(var6, var1, var4);
         }

         return var10000;
      }
   }

   /** @deprecated */
   @Deprecated
   protected Object getDefaultValue(JavaType var1) {
      return BeanUtil.getDefaultValue(var1);
   }

   protected Object _throwWrapped(Exception var1, String var2, Object var3) {
      Object var4;
      for(var4 = var1; ((Throwable)var4).getCause() != null; var4 = ((Throwable)var4).getCause()) {
      }

      ClassUtil.throwIfError((Throwable)var4);
      ClassUtil.throwIfRTE((Throwable)var4);
      throw new IllegalArgumentException("Failed to get property '" + var2 + "' of default " + var3.getClass().getName() + " instance");
   }

   static {
      NO_DEFAULT_MARKER = Boolean.FALSE;
   }
}
