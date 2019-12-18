package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.annotation.JsonFormat$Shape;
import com.fasterxml.jackson.annotation.JsonFormat$Value;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser$NumberType;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.util.EnumValues;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.LinkedHashSet;

@JacksonStdImpl
public class EnumSerializer extends StdScalarSerializer implements ContextualSerializer {
   private static final long serialVersionUID = 1L;
   protected final EnumValues _values;
   protected final Boolean _serializeAsIndex;

   public EnumSerializer(EnumValues var1, Boolean var2) {
      super(var1.getEnumClass(), false);
      this._values = var1;
      this._serializeAsIndex = var2;
   }

   public static EnumSerializer construct(Class var0, SerializationConfig var1, BeanDescription var2, JsonFormat$Value var3) {
      EnumValues var4 = EnumValues.constructFromName(var1, var0);
      Boolean var5 = _isShapeWrittenUsingIndex(var0, var3, true, (Boolean)null);
      return new EnumSerializer(var4, var5);
   }

   public JsonSerializer createContextual(SerializerProvider var1, BeanProperty var2) throws JsonMappingException {
      JsonFormat$Value var3 = this.findFormatOverrides(var1, var2, this.handledType());
      if (var3 != null) {
         Class var4 = this.handledType();
         Boolean var5 = _isShapeWrittenUsingIndex(var4, var3, false, this._serializeAsIndex);
         if (var5 != this._serializeAsIndex) {
            return new EnumSerializer(this._values, var5);
         }
      }

      return this;
   }

   public EnumValues getEnumValues() {
      return this._values;
   }

   public final void serialize(Enum var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      if (this._serializeAsIndex(var3)) {
         var2.writeNumber(var1.ordinal());
      } else if (var3.isEnabled(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)) {
         var2.writeString(var1.toString());
      } else {
         var2.writeString(this._values.serializedValueFor(var1));
      }
   }

   public JsonNode getSchema(SerializerProvider var1, Type var2) {
      if (this._serializeAsIndex(var1)) {
         return this.createSchemaNode("integer", true);
      } else {
         ObjectNode var3 = this.createSchemaNode("string", true);
         if (var2 != null) {
            JavaType var4 = var1.constructType(var2);
            if (var4.isEnumType()) {
               ArrayNode var5 = var3.putArray("enum");
               Iterator var6 = this._values.values().iterator();

               while(var6.hasNext()) {
                  SerializableString var7 = (SerializableString)var6.next();
                  var5.add(var7.getValue());
               }
            }
         }

         return var3;
      }
   }

   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper var1, JavaType var2) throws JsonMappingException {
      SerializerProvider var3 = var1.getProvider();
      if (this._serializeAsIndex(var3)) {
         this.visitIntFormat(var1, var2, JsonParser$NumberType.INT);
      } else {
         JsonStringFormatVisitor var4 = var1.expectStringFormat(var2);
         if (var4 != null) {
            LinkedHashSet var5 = new LinkedHashSet();
            Iterator var6;
            if (var3 != null && var3.isEnabled(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)) {
               var6 = this._values.enums().iterator();

               while(var6.hasNext()) {
                  Enum var8 = (Enum)var6.next();
                  var5.add(var8.toString());
               }
            } else {
               var6 = this._values.values().iterator();

               while(var6.hasNext()) {
                  SerializableString var7 = (SerializableString)var6.next();
                  var5.add(var7.getValue());
               }
            }

            var4.enumTypes(var5);
         }

      }
   }

   protected final boolean _serializeAsIndex(SerializerProvider var1) {
      return this._serializeAsIndex != null ? this._serializeAsIndex : var1.isEnabled(SerializationFeature.WRITE_ENUMS_USING_INDEX);
   }

   protected static Boolean _isShapeWrittenUsingIndex(Class var0, JsonFormat$Value var1, boolean var2, Boolean var3) {
      JsonFormat$Shape var4 = var1 == null ? null : var1.getShape();
      if (var4 == null) {
         return var3;
      } else if (var4 != JsonFormat$Shape.ANY && var4 != JsonFormat$Shape.SCALAR) {
         if (var4 != JsonFormat$Shape.STRING && var4 != JsonFormat$Shape.NATURAL) {
            if (!var4.isNumeric() && var4 != JsonFormat$Shape.ARRAY) {
               throw new IllegalArgumentException(String.format("Unsupported serialization shape (%s) for Enum %s, not supported as %s annotation", var4, var0.getName(), var2 ? "class" : "property"));
            } else {
               return Boolean.TRUE;
            }
         } else {
            return Boolean.FALSE;
         }
      } else {
         return var3;
      }
   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      this.serialize((Enum)var1, var2, var3);
   }
}
