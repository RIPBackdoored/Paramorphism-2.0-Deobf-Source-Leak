package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.annotation.JsonFormat$Shape;
import com.fasterxml.jackson.annotation.JsonFormat$Value;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import java.io.IOException;
import java.net.InetAddress;

public class InetAddressSerializer extends StdScalarSerializer implements ContextualSerializer {
   protected final boolean _asNumeric;

   public InetAddressSerializer() {
      this(false);
   }

   public InetAddressSerializer(boolean var1) {
      super(InetAddress.class);
      this._asNumeric = var1;
   }

   public JsonSerializer createContextual(SerializerProvider var1, BeanProperty var2) throws JsonMappingException {
      JsonFormat$Value var3 = this.findFormatOverrides(var1, var2, this.handledType());
      boolean var4 = false;
      if (var3 != null) {
         JsonFormat$Shape var5 = var3.getShape();
         if (var5.isNumeric() || var5 == JsonFormat$Shape.ARRAY) {
            var4 = true;
         }
      }

      return var4 != this._asNumeric ? new InetAddressSerializer(var4) : this;
   }

   public void serialize(InetAddress var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      String var4;
      if (this._asNumeric) {
         var4 = var1.getHostAddress();
      } else {
         var4 = var1.toString().trim();
         int var5 = var4.indexOf(47);
         if (var5 >= 0) {
            if (var5 == 0) {
               var4 = var4.substring(1);
            } else {
               var4 = var4.substring(0, var5);
            }
         }
      }

      var2.writeString(var4);
   }

   public void serializeWithType(InetAddress var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException {
      WritableTypeId var5 = var4.writeTypePrefix(var2, var4.typeId(var1, (Class)InetAddress.class, (JsonToken)JsonToken.VALUE_STRING));
      this.serialize(var1, var2, var3);
      var4.writeTypeSuffix(var2, var5);
   }

   public void serializeWithType(Object var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException {
      this.serializeWithType((InetAddress)var1, var2, var3, var4);
   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      this.serialize((InetAddress)var1, var2, var3);
   }
}
