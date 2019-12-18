package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import java.io.IOException;

public class StackTraceElementDeserializer extends StdScalarDeserializer {
   private static final long serialVersionUID = 1L;

   public StackTraceElementDeserializer() {
      super(StackTraceElement.class);
   }

   public StackTraceElement deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      JsonToken var3 = var1.getCurrentToken();
      if (var3 == JsonToken.START_OBJECT) {
         String var12 = "";
         String var5 = "";
         String var6 = "";
         String var7 = null;
         String var8 = null;
         String var9 = null;

         int var10;
         for(var10 = -1; (var3 = var1.nextValue()) != JsonToken.END_OBJECT; var1.skipChildren()) {
            String var11 = var1.getCurrentName();
            if ("className".equals(var11)) {
               var12 = var1.getText();
            } else if ("classLoaderName".equals(var11)) {
               var9 = var1.getText();
            } else if ("fileName".equals(var11)) {
               var6 = var1.getText();
            } else if ("lineNumber".equals(var11)) {
               if (var3.isNumeric()) {
                  var10 = var1.getIntValue();
               } else {
                  var10 = this._parseIntPrimitive(var1, var2);
               }
            } else if ("methodName".equals(var11)) {
               var5 = var1.getText();
            } else if (!"nativeMethod".equals(var11)) {
               if ("moduleName".equals(var11)) {
                  var7 = var1.getText();
               } else if ("moduleVersion".equals(var11)) {
                  var8 = var1.getText();
               } else if (!"declaringClass".equals(var11) && !"format".equals(var11)) {
                  this.handleUnknownProperty(var1, var2, this._valueClass, var11);
               }
            }
         }

         return this.constructValue(var2, var12, var5, var6, var10, var7, var8, var9);
      } else if (var3 == JsonToken.START_ARRAY && var2.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
         var1.nextToken();
         StackTraceElement var4 = this.deserialize(var1, var2);
         if (var1.nextToken() != JsonToken.END_ARRAY) {
            this.handleMissingEndArrayForSingle(var1, var2);
         }

         return var4;
      } else {
         return (StackTraceElement)var2.handleUnexpectedToken(this._valueClass, var1);
      }
   }

   /** @deprecated */
   @Deprecated
   protected StackTraceElement constructValue(DeserializationContext var1, String var2, String var3, String var4, int var5, String var6, String var7) {
      return this.constructValue(var1, var2, var3, var4, var5, var6, var7, (String)null);
   }

   protected StackTraceElement constructValue(DeserializationContext var1, String var2, String var3, String var4, int var5, String var6, String var7, String var8) {
      return new StackTraceElement(var2, var3, var4, var5);
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException, JsonProcessingException {
      return this.deserialize(var1, var2);
   }
}
