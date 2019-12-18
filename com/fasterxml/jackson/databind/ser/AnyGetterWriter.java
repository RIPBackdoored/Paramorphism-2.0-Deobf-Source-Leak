package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.ser.std.MapSerializer;
import java.util.Map;

public class AnyGetterWriter {
   protected final BeanProperty _property;
   protected final AnnotatedMember _accessor;
   protected JsonSerializer _serializer;
   protected MapSerializer _mapSerializer;

   public AnyGetterWriter(BeanProperty var1, AnnotatedMember var2, JsonSerializer var3) {
      super();
      this._accessor = var2;
      this._property = var1;
      this._serializer = var3;
      if (var3 instanceof MapSerializer) {
         this._mapSerializer = (MapSerializer)var3;
      }

   }

   public void fixAccess(SerializationConfig var1) {
      this._accessor.fixAccess(var1.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
   }

   public void getAndSerialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws Exception {
      Object var4 = this._accessor.getValue(var1);
      if (var4 != null) {
         if (!(var4 instanceof Map)) {
            var3.reportBadDefinition(this._property.getType(), String.format("Value returned by 'any-getter' %s() not java.util.Map but %s", this._accessor.getName(), var4.getClass().getName()));
         }

         if (this._mapSerializer != null) {
            this._mapSerializer.serializeFields((Map)var4, var2, var3);
         } else {
            this._serializer.serialize(var4, var2, var3);
         }
      }
   }

   public void getAndFilter(Object var1, JsonGenerator var2, SerializerProvider var3, PropertyFilter var4) throws Exception {
      Object var5 = this._accessor.getValue(var1);
      if (var5 != null) {
         if (!(var5 instanceof Map)) {
            var3.reportBadDefinition(this._property.getType(), String.format("Value returned by 'any-getter' (%s()) not java.util.Map but %s", this._accessor.getName(), var5.getClass().getName()));
         }

         if (this._mapSerializer != null) {
            this._mapSerializer.serializeFilteredAnyProperties(var3, var2, var1, (Map)var5, var4, (Object)null);
         } else {
            this._serializer.serialize(var5, var2, var3);
         }
      }
   }

   public void resolve(SerializerProvider var1) throws JsonMappingException {
      if (this._serializer instanceof ContextualSerializer) {
         JsonSerializer var2 = var1.handlePrimaryContextualization(this._serializer, this._property);
         this._serializer = var2;
         if (var2 instanceof MapSerializer) {
            this._mapSerializer = (MapSerializer)var2;
         }
      }

   }
}
