package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.annotation.JsonFormat$Value;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser$NumberType;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;

@JacksonStdImpl
public class NumberSerializer extends StdScalarSerializer implements ContextualSerializer {
   public static final NumberSerializer instance = new NumberSerializer(Number.class);
   protected final boolean _isInt;

   public NumberSerializer(Class var1) {
      super(var1, false);
      this._isInt = var1 == BigInteger.class;
   }

   public JsonSerializer createContextual(SerializerProvider var1, BeanProperty var2) throws JsonMappingException {
      // $FF: Couldn't be decompiled
   }

   public void serialize(Number var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      if (var1 instanceof BigDecimal) {
         var2.writeNumber((BigDecimal)var1);
      } else if (var1 instanceof BigInteger) {
         var2.writeNumber((BigInteger)var1);
      } else if (var1 instanceof Long) {
         var2.writeNumber(var1.longValue());
      } else if (var1 instanceof Double) {
         var2.writeNumber(var1.doubleValue());
      } else if (var1 instanceof Float) {
         var2.writeNumber(var1.floatValue());
      } else if (!(var1 instanceof Integer) && !(var1 instanceof Byte) && !(var1 instanceof Short)) {
         var2.writeNumber(var1.toString());
      } else {
         var2.writeNumber(var1.intValue());
      }

   }

   public JsonNode getSchema(SerializerProvider var1, Type var2) {
      return this.createSchemaNode(this._isInt ? "integer" : "number", true);
   }

   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper var1, JavaType var2) throws JsonMappingException {
      if (this._isInt) {
         this.visitIntFormat(var1, var2, JsonParser$NumberType.BIG_INTEGER);
      } else {
         Class var3 = this.handledType();
         if (var3 == BigDecimal.class) {
            this.visitFloatFormat(var1, var2, JsonParser$NumberType.BIG_DECIMAL);
         } else {
            var1.expectNumberFormat(var2);
         }
      }

   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      this.serialize((Number)var1, var2, var3);
   }
}
