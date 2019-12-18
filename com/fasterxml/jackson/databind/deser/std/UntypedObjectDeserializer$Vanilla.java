package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.ObjectBuffer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@JacksonStdImpl
public class UntypedObjectDeserializer$Vanilla extends StdDeserializer {
   private static final long serialVersionUID = 1L;
   public static final UntypedObjectDeserializer$Vanilla std = new UntypedObjectDeserializer$Vanilla();
   protected final boolean _nonMerging;

   public UntypedObjectDeserializer$Vanilla() {
      this(false);
   }

   protected UntypedObjectDeserializer$Vanilla(boolean var1) {
      super(Object.class);
      this._nonMerging = var1;
   }

   public static UntypedObjectDeserializer$Vanilla instance(boolean var0) {
      return var0 ? new UntypedObjectDeserializer$Vanilla(true) : std;
   }

   public Boolean supportsUpdate(DeserializationConfig var1) {
      return this._nonMerging ? Boolean.FALSE : null;
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      JsonToken var3;
      switch(var1.getCurrentTokenId()) {
      case 1:
         var3 = var1.nextToken();
         if (var3 == JsonToken.END_OBJECT) {
            return new LinkedHashMap(2);
         }
      case 5:
         return this.mapObject(var1, var2);
      case 2:
         return new LinkedHashMap(2);
      case 3:
         var3 = var1.nextToken();
         if (var3 == JsonToken.END_ARRAY) {
            if (var2.isEnabled(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY)) {
               return UntypedObjectDeserializer.NO_OBJECTS;
            }

            return new ArrayList(2);
         } else {
            if (var2.isEnabled(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY)) {
               return this.mapArrayToArray(var1, var2);
            }

            return this.mapArray(var1, var2);
         }
      case 4:
      default:
         return var2.handleUnexpectedToken(Object.class, var1);
      case 6:
         return var1.getText();
      case 7:
         if (var2.hasSomeOfFeatures(F_MASK_INT_COERCIONS)) {
            return this._coerceIntegral(var1, var2);
         }

         return var1.getNumberValue();
      case 8:
         if (var2.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
            return var1.getDecimalValue();
         }

         return var1.getNumberValue();
      case 9:
         return Boolean.TRUE;
      case 10:
         return Boolean.FALSE;
      case 11:
         return null;
      case 12:
         return var1.getEmbeddedObject();
      }
   }

   public Object deserializeWithType(JsonParser var1, DeserializationContext var2, TypeDeserializer var3) throws IOException {
      switch(var1.getCurrentTokenId()) {
      case 1:
      case 3:
      case 5:
         return var3.deserializeTypedFromAny(var1, var2);
      case 2:
      case 4:
      default:
         return var2.handleUnexpectedToken(Object.class, var1);
      case 6:
         return var1.getText();
      case 7:
         if (var2.isEnabled(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)) {
            return var1.getBigIntegerValue();
         }

         return var1.getNumberValue();
      case 8:
         if (var2.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
            return var1.getDecimalValue();
         }

         return var1.getNumberValue();
      case 9:
         return Boolean.TRUE;
      case 10:
         return Boolean.FALSE;
      case 11:
         return null;
      case 12:
         return var1.getEmbeddedObject();
      }
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2, Object var3) throws IOException {
      if (this._nonMerging) {
         return this.deserialize(var1, var2);
      } else {
         JsonToken var4;
         switch(var1.getCurrentTokenId()) {
         case 1:
            var4 = var1.nextToken();
            if (var4 == JsonToken.END_OBJECT) {
               return var3;
            }
         case 5:
            if (var3 instanceof Map) {
               Map var9 = (Map)var3;
               String var5 = var1.getCurrentName();

               do {
                  var1.nextToken();
                  Object var6 = var9.get(var5);
                  Object var7;
                  if (var6 != null) {
                     var7 = this.deserialize(var1, var2, var6);
                  } else {
                     var7 = this.deserialize(var1, var2);
                  }

                  if (var7 != var6) {
                     var9.put(var5, var7);
                  }
               } while((var5 = var1.nextFieldName()) != null);

               return var3;
            }
            break;
         case 2:
         case 4:
            return var3;
         case 3:
            var4 = var1.nextToken();
            if (var4 == JsonToken.END_ARRAY) {
               return var3;
            }

            if (var3 instanceof Collection) {
               Collection var8 = (Collection)var3;

               do {
                  var8.add(this.deserialize(var1, var2));
               } while(var1.nextToken() != JsonToken.END_ARRAY);

               return var3;
            }
         }

         return this.deserialize(var1, var2);
      }
   }

   protected Object mapArray(JsonParser var1, DeserializationContext var2) throws IOException {
      Object var3 = this.deserialize(var1, var2);
      if (var1.nextToken() == JsonToken.END_ARRAY) {
         ArrayList var10 = new ArrayList(2);
         var10.add(var3);
         return var10;
      } else {
         Object var4 = this.deserialize(var1, var2);
         if (var1.nextToken() == JsonToken.END_ARRAY) {
            ArrayList var11 = new ArrayList(2);
            var11.add(var3);
            var11.add(var4);
            return var11;
         } else {
            ObjectBuffer var5 = var2.leaseObjectBuffer();
            Object[] var6 = var5.resetAndStart();
            byte var7 = 0;
            int var12 = var7 + 1;
            var6[var7] = var3;
            var6[var12++] = var4;
            int var8 = var12;

            do {
               var3 = this.deserialize(var1, var2);
               ++var8;
               if (var12 >= var6.length) {
                  var6 = var5.appendCompletedChunk(var6);
                  var12 = 0;
               }

               var6[var12++] = var3;
            } while(var1.nextToken() != JsonToken.END_ARRAY);

            ArrayList var9 = new ArrayList(var8);
            var5.completeAndClearBuffer(var6, var12, (List)var9);
            return var9;
         }
      }
   }

   protected Object[] mapArrayToArray(JsonParser var1, DeserializationContext var2) throws IOException {
      ObjectBuffer var3 = var2.leaseObjectBuffer();
      Object[] var4 = var3.resetAndStart();
      int var5 = 0;

      do {
         Object var6 = this.deserialize(var1, var2);
         if (var5 >= var4.length) {
            var4 = var3.appendCompletedChunk(var4);
            var5 = 0;
         }

         var4[var5++] = var6;
      } while(var1.nextToken() != JsonToken.END_ARRAY);

      return var3.completeAndClearBuffer(var4, var5);
   }

   protected Object mapObject(JsonParser var1, DeserializationContext var2) throws IOException {
      String var3 = var1.getText();
      var1.nextToken();
      Object var4 = this.deserialize(var1, var2);
      String var5 = var1.nextFieldName();
      if (var5 == null) {
         LinkedHashMap var9 = new LinkedHashMap(2);
         var9.put(var3, var4);
         return var9;
      } else {
         var1.nextToken();
         Object var6 = this.deserialize(var1, var2);
         String var7 = var1.nextFieldName();
         LinkedHashMap var8;
         if (var7 == null) {
            var8 = new LinkedHashMap(4);
            var8.put(var3, var4);
            var8.put(var5, var6);
            return var8;
         } else {
            var8 = new LinkedHashMap();
            var8.put(var3, var4);
            var8.put(var5, var6);

            do {
               var1.nextToken();
               var8.put(var7, this.deserialize(var1, var2));
            } while((var7 = var1.nextFieldName()) != null);

            return var8;
         }
      }
   }
}
