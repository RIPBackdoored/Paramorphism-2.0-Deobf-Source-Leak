package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.annotation.JsonFormat$Shape;
import com.fasterxml.jackson.annotation.JsonFormat$Value;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import java.io.IOException;
import java.lang.reflect.Type;

@JacksonStdImpl
public final class BooleanSerializer extends StdScalarSerializer implements ContextualSerializer {
   private static final long serialVersionUID = 1L;
   protected final boolean _forPrimitive;

   public BooleanSerializer(boolean var1) {
      super(var1 ? Boolean.TYPE : Boolean.class, false);
      this._forPrimitive = var1;
   }

   public JsonSerializer createContextual(SerializerProvider var1, BeanProperty var2) throws JsonMappingException {
      JsonFormat$Value var3 = this.findFormatOverrides(var1, var2, Boolean.class);
      if (var3 != null) {
         JsonFormat$Shape var4 = var3.getShape();
         if (var4.isNumeric()) {
            return new BooleanSerializer$AsNumber(this._forPrimitive);
         }
      }

      return this;
   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      var2.writeBoolean(Boolean.TRUE.equals(var1));
   }

   public final void serializeWithType(Object var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException {
      var2.writeBoolean(Boolean.TRUE.equals(var1));
   }

   public JsonNode getSchema(SerializerProvider var1, Type var2) {
      return this.createSchemaNode("boolean", !this._forPrimitive);
   }

   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper var1, JavaType var2) throws JsonMappingException {
      var1.expectBooleanFormat(var2);
   }
}
