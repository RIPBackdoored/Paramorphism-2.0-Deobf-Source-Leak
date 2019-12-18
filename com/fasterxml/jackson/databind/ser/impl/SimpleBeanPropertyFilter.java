package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.BeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SimpleBeanPropertyFilter implements BeanPropertyFilter, PropertyFilter {
   protected SimpleBeanPropertyFilter() {
      super();
   }

   public static SimpleBeanPropertyFilter serializeAll() {
      return SimpleBeanPropertyFilter$SerializeExceptFilter.INCLUDE_ALL;
   }

   /** @deprecated */
   @Deprecated
   public static SimpleBeanPropertyFilter serializeAll(Set var0) {
      return new SimpleBeanPropertyFilter$FilterExceptFilter(var0);
   }

   public static SimpleBeanPropertyFilter filterOutAllExcept(Set var0) {
      return new SimpleBeanPropertyFilter$FilterExceptFilter(var0);
   }

   public static SimpleBeanPropertyFilter filterOutAllExcept(String... var0) {
      HashSet var1 = new HashSet(var0.length);
      Collections.addAll(var1, var0);
      return new SimpleBeanPropertyFilter$FilterExceptFilter(var1);
   }

   public static SimpleBeanPropertyFilter serializeAllExcept(Set var0) {
      return new SimpleBeanPropertyFilter$SerializeExceptFilter(var0);
   }

   public static SimpleBeanPropertyFilter serializeAllExcept(String... var0) {
      HashSet var1 = new HashSet(var0.length);
      Collections.addAll(var1, var0);
      return new SimpleBeanPropertyFilter$SerializeExceptFilter(var1);
   }

   public static PropertyFilter from(BeanPropertyFilter var0) {
      return new SimpleBeanPropertyFilter$1(var0);
   }

   protected boolean include(BeanPropertyWriter var1) {
      return true;
   }

   protected boolean include(PropertyWriter var1) {
      return true;
   }

   protected boolean includeElement(Object var1) {
      return true;
   }

   /** @deprecated */
   @Deprecated
   public void serializeAsField(Object var1, JsonGenerator var2, SerializerProvider var3, BeanPropertyWriter var4) throws Exception {
      if (this.include(var4)) {
         var4.serializeAsField(var1, var2, var3);
      } else if (!var2.canOmitFields()) {
         var4.serializeAsOmittedField(var1, var2, var3);
      }

   }

   /** @deprecated */
   @Deprecated
   public void depositSchemaProperty(BeanPropertyWriter var1, ObjectNode var2, SerializerProvider var3) throws JsonMappingException {
      if (this.include(var1)) {
         var1.depositSchemaProperty(var2, var3);
      }

   }

   /** @deprecated */
   @Deprecated
   public void depositSchemaProperty(BeanPropertyWriter var1, JsonObjectFormatVisitor var2, SerializerProvider var3) throws JsonMappingException {
      if (this.include(var1)) {
         var1.depositSchemaProperty(var2, var3);
      }

   }

   public void serializeAsField(Object var1, JsonGenerator var2, SerializerProvider var3, PropertyWriter var4) throws Exception {
      if (this.include(var4)) {
         var4.serializeAsField(var1, var2, var3);
      } else if (!var2.canOmitFields()) {
         var4.serializeAsOmittedField(var1, var2, var3);
      }

   }

   public void serializeAsElement(Object var1, JsonGenerator var2, SerializerProvider var3, PropertyWriter var4) throws Exception {
      if (this.includeElement(var1)) {
         var4.serializeAsElement(var1, var2, var3);
      }

   }

   /** @deprecated */
   @Deprecated
   public void depositSchemaProperty(PropertyWriter var1, ObjectNode var2, SerializerProvider var3) throws JsonMappingException {
      if (this.include(var1)) {
         var1.depositSchemaProperty(var2, var3);
      }

   }

   public void depositSchemaProperty(PropertyWriter var1, JsonObjectFormatVisitor var2, SerializerProvider var3) throws JsonMappingException {
      if (this.include(var1)) {
         var1.depositSchemaProperty(var2, var3);
      }

   }
}
