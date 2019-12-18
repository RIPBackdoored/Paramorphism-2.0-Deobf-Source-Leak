package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.annotation.JsonFormat$Value;
import com.fasterxml.jackson.core.JsonParser$NumberType;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import java.lang.reflect.Type;

public abstract class NumberSerializers$Base extends StdScalarSerializer implements ContextualSerializer {
   protected final JsonParser$NumberType _numberType;
   protected final String _schemaType;
   protected final boolean _isInt;

   protected NumberSerializers$Base(Class var1, JsonParser$NumberType var2, String var3) {
      super(var1, false);
      this._numberType = var2;
      this._schemaType = var3;
      this._isInt = var2 == JsonParser$NumberType.INT || var2 == JsonParser$NumberType.LONG || var2 == JsonParser$NumberType.BIG_INTEGER;
   }

   public JsonNode getSchema(SerializerProvider var1, Type var2) {
      return this.createSchemaNode(this._schemaType, true);
   }

   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper var1, JavaType var2) throws JsonMappingException {
      if (this._isInt) {
         this.visitIntFormat(var1, var2, this._numberType);
      } else {
         this.visitFloatFormat(var1, var2, this._numberType);
      }

   }

   public JsonSerializer createContextual(SerializerProvider var1, BeanProperty var2) throws JsonMappingException {
      // $FF: Couldn't be decompiled
   }
}
