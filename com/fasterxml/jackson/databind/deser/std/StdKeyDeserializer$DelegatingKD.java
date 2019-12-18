package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.IOException;
import java.io.Serializable;

final class StdKeyDeserializer$DelegatingKD extends KeyDeserializer implements Serializable {
   private static final long serialVersionUID = 1L;
   protected final Class _keyClass;
   protected final JsonDeserializer _delegate;

   protected StdKeyDeserializer$DelegatingKD(Class var1, JsonDeserializer var2) {
      super();
      this._keyClass = var1;
      this._delegate = var2;
   }

   public final Object deserializeKey(String var1, DeserializationContext var2) throws IOException {
      if (var1 == null) {
         return null;
      } else {
         TokenBuffer var3 = new TokenBuffer(var2.getParser(), var2);
         var3.writeString(var1);

         Object var10000;
         try {
            JsonParser var4 = var3.asParser();
            var4.nextToken();
            Object var5 = this._delegate.deserialize(var4, var2);
            if (var5 != null) {
               var10000 = var5;
               return var10000;
            }

            var10000 = var2.handleWeirdKey(this._keyClass, var1, "not a valid representation");
         } catch (Exception var6) {
            return var2.handleWeirdKey(this._keyClass, var1, "not a valid representation: %s", var6.getMessage());
         }

         return var10000;
      }
   }

   public Class getKeyClass() {
      return this._keyClass;
   }
}
