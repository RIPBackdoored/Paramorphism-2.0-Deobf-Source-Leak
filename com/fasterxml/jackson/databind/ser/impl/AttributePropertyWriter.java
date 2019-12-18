package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.annotation.JsonInclude$Value;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.VirtualBeanPropertyWriter;
import com.fasterxml.jackson.databind.util.Annotations;

public class AttributePropertyWriter extends VirtualBeanPropertyWriter {
   private static final long serialVersionUID = 1L;
   protected final String _attrName;

   protected AttributePropertyWriter(String var1, BeanPropertyDefinition var2, Annotations var3, JavaType var4) {
      this(var1, var2, var3, var4, var2.findInclusion());
   }

   protected AttributePropertyWriter(String var1, BeanPropertyDefinition var2, Annotations var3, JavaType var4, JsonInclude$Value var5) {
      super(var2, var3, var4, (JsonSerializer)null, (TypeSerializer)null, (JavaType)null, var5, (Class[])null);
      this._attrName = var1;
   }

   public static AttributePropertyWriter construct(String var0, BeanPropertyDefinition var1, Annotations var2, JavaType var3) {
      return new AttributePropertyWriter(var0, var1, var2, var3);
   }

   protected AttributePropertyWriter(AttributePropertyWriter var1) {
      super(var1);
      this._attrName = var1._attrName;
   }

   public VirtualBeanPropertyWriter withConfig(MapperConfig var1, AnnotatedClass var2, BeanPropertyDefinition var3, JavaType var4) {
      throw new IllegalStateException("Should not be called on this type");
   }

   protected Object value(Object var1, JsonGenerator var2, SerializerProvider var3) throws Exception {
      return var3.getAttribute(this._attrName);
   }
}
