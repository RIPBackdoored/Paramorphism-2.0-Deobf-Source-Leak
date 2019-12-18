package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class InetSocketAddressSerializer extends StdScalarSerializer {
   public InetSocketAddressSerializer() {
      super(InetSocketAddress.class);
   }

   public void serialize(InetSocketAddress var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      InetAddress var4 = var1.getAddress();
      String var5 = var4 == null ? var1.getHostName() : var4.toString().trim();
      int var6 = var5.indexOf(47);
      if (var6 >= 0) {
         if (var6 == 0) {
            var5 = var4 instanceof Inet6Address ? "[" + var5.substring(1) + "]" : var5.substring(1);
         } else {
            var5 = var5.substring(0, var6);
         }
      }

      var2.writeString(var5 + ":" + var1.getPort());
   }

   public void serializeWithType(InetSocketAddress var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException {
      WritableTypeId var5 = var4.writeTypePrefix(var2, var4.typeId(var1, (Class)InetSocketAddress.class, (JsonToken)JsonToken.VALUE_STRING));
      this.serialize(var1, var2, var3);
      var4.writeTypeSuffix(var2, var5);
   }

   public void serializeWithType(Object var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException {
      this.serializeWithType((InetSocketAddress)var1, var2, var3, var4);
   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      this.serialize((InetSocketAddress)var1, var2, var3);
   }
}
