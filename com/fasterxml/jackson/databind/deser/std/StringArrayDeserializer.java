package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.annotation.JsonFormat$Feature;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import com.fasterxml.jackson.databind.deser.impl.NullsConstantProvider;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.AccessPattern;
import com.fasterxml.jackson.databind.util.ObjectBuffer;
import java.io.IOException;

@JacksonStdImpl
public final class StringArrayDeserializer extends StdDeserializer implements ContextualDeserializer {
   private static final long serialVersionUID = 2L;
   private static final String[] NO_STRINGS = new String[0];
   public static final StringArrayDeserializer instance = new StringArrayDeserializer();
   protected JsonDeserializer _elementDeserializer;
   protected final NullValueProvider _nullProvider;
   protected final Boolean _unwrapSingle;
   protected final boolean _skipNullValues;

   public StringArrayDeserializer() {
      this((JsonDeserializer)null, (NullValueProvider)null, (Boolean)null);
   }

   protected StringArrayDeserializer(JsonDeserializer var1, NullValueProvider var2, Boolean var3) {
      super(String[].class);
      this._elementDeserializer = var1;
      this._nullProvider = var2;
      this._unwrapSingle = var3;
      this._skipNullValues = NullsConstantProvider.isSkipper(var2);
   }

   public Boolean supportsUpdate(DeserializationConfig var1) {
      return Boolean.TRUE;
   }

   public AccessPattern getEmptyAccessPattern() {
      return AccessPattern.CONSTANT;
   }

   public Object getEmptyValue(DeserializationContext var1) throws JsonMappingException {
      return NO_STRINGS;
   }

   public JsonDeserializer createContextual(DeserializationContext var1, BeanProperty var2) throws JsonMappingException {
      JsonDeserializer var3 = this._elementDeserializer;
      var3 = this.findConvertingContentDeserializer(var1, var2, var3);
      JavaType var4 = var1.constructType(String.class);
      if (var3 == null) {
         var3 = var1.findContextualValueDeserializer(var4, var2);
      } else {
         var3 = var1.handleSecondaryContextualization(var3, var2, var4);
      }

      Boolean var5 = this.findFormatFeature(var1, var2, String[].class, JsonFormat$Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
      NullValueProvider var6 = this.findContentNullProvider(var1, var2, var3);
      if (var3 != null && this.isDefaultDeserializer(var3)) {
         var3 = null;
      }

      return this._elementDeserializer == var3 && this._unwrapSingle == var5 && this._nullProvider == var6 ? this : new StringArrayDeserializer(var3, var6, var5);
   }

   public String[] deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      if (!var1.isExpectedStartArrayToken()) {
         return this.handleNonArray(var1, var2);
      } else if (this._elementDeserializer != null) {
         return this._deserializeCustom(var1, var2, (String[])null);
      } else {
         ObjectBuffer var3 = var2.leaseObjectBuffer();
         Object[] var4 = var3.resetAndStart();
         int var5 = 0;

         try {
            while(true) {
               String var6 = var1.nextTextValue();
               if (var6 == null) {
                  JsonToken var7 = var1.getCurrentToken();
                  if (var7 == JsonToken.END_ARRAY) {
                     break;
                  }

                  if (var7 == JsonToken.VALUE_NULL) {
                     if (this._skipNullValues) {
                        continue;
                     }

                     var6 = (String)this._nullProvider.getNullValue(var2);
                  } else {
                     var6 = this._parseString(var1, var2);
                  }
               }

               if (var5 >= var4.length) {
                  var4 = var3.appendCompletedChunk(var4);
                  var5 = 0;
               }

               var4[var5++] = var6;
            }
         } catch (Exception var8) {
            throw JsonMappingException.wrapWithPath(var8, var4, var3.bufferedSize() + var5);
         }

         String[] var9 = (String[])var3.completeAndClearBuffer(var4, var5, String.class);
         var2.returnObjectBuffer(var3);
         return var9;
      }
   }

   protected final String[] _deserializeCustom(JsonParser var1, DeserializationContext var2, String[] var3) throws IOException {
      ObjectBuffer var4 = var2.leaseObjectBuffer();
      int var5;
      Object[] var6;
      if (var3 == null) {
         var5 = 0;
         var6 = var4.resetAndStart();
      } else {
         var5 = var3.length;
         var6 = var4.resetAndStart(var3, var5);
      }

      JsonDeserializer var7 = this._elementDeserializer;

      try {
         while(true) {
            String var8;
            if (var1.nextTextValue() == null) {
               JsonToken var9 = var1.getCurrentToken();
               if (var9 == JsonToken.END_ARRAY) {
                  break;
               }

               if (var9 == JsonToken.VALUE_NULL) {
                  if (this._skipNullValues) {
                     continue;
                  }

                  var8 = (String)this._nullProvider.getNullValue(var2);
               } else {
                  var8 = (String)var7.deserialize(var1, var2);
               }
            } else {
               var8 = (String)var7.deserialize(var1, var2);
            }

            if (var5 >= var6.length) {
               var6 = var4.appendCompletedChunk(var6);
               var5 = 0;
            }

            var6[var5++] = var8;
         }
      } catch (Exception var10) {
         throw JsonMappingException.wrapWithPath(var10, String.class, var5);
      }

      String[] var11 = (String[])var4.completeAndClearBuffer(var6, var5, String.class);
      var2.returnObjectBuffer(var4);
      return var11;
   }

   public Object deserializeWithType(JsonParser var1, DeserializationContext var2, TypeDeserializer var3) throws IOException {
      return var3.deserializeTypedFromArray(var1, var2);
   }

   public String[] deserialize(JsonParser var1, DeserializationContext var2, String[] var3) throws IOException {
      int var5;
      if (!var1.isExpectedStartArrayToken()) {
         String[] var10 = this.handleNonArray(var1, var2);
         if (var10 == null) {
            return var3;
         } else {
            var5 = var3.length;
            String[] var11 = new String[var5 + var10.length];
            System.arraycopy(var3, 0, var11, 0, var5);
            System.arraycopy(var10, 0, var11, var5, var10.length);
            return var11;
         }
      } else if (this._elementDeserializer != null) {
         return this._deserializeCustom(var1, var2, var3);
      } else {
         ObjectBuffer var4 = var2.leaseObjectBuffer();
         var5 = var3.length;
         Object[] var6 = var4.resetAndStart(var3, var5);

         try {
            while(true) {
               String var7 = var1.nextTextValue();
               if (var7 == null) {
                  JsonToken var8 = var1.getCurrentToken();
                  if (var8 == JsonToken.END_ARRAY) {
                     break;
                  }

                  if (var8 == JsonToken.VALUE_NULL) {
                     if (this._skipNullValues) {
                        String[] var10000 = NO_STRINGS;
                        return var10000;
                     }

                     var7 = (String)this._nullProvider.getNullValue(var2);
                  } else {
                     var7 = this._parseString(var1, var2);
                  }
               }

               if (var5 >= var6.length) {
                  var6 = var4.appendCompletedChunk(var6);
                  var5 = 0;
               }

               var6[var5++] = var7;
            }
         } catch (Exception var9) {
            throw JsonMappingException.wrapWithPath(var9, var6, var4.bufferedSize() + var5);
         }

         String[] var12 = (String[])var4.completeAndClearBuffer(var6, var5, String.class);
         var2.returnObjectBuffer(var4);
         return var12;
      }
   }

   private final String[] handleNonArray(JsonParser var1, DeserializationContext var2) throws IOException {
      boolean var3 = this._unwrapSingle == Boolean.TRUE || this._unwrapSingle == null && var2.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
      String var4;
      if (var3) {
         var4 = var1.hasToken(JsonToken.VALUE_NULL) ? (String)this._nullProvider.getNullValue(var2) : this._parseString(var1, var2);
         return new String[]{var4};
      } else {
         if (var1.hasToken(JsonToken.VALUE_STRING) && var2.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)) {
            var4 = var1.getText();
            if (var4.length() == 0) {
               return null;
            }
         }

         return (String[])((String[])var2.handleUnexpectedToken(this._valueClass, var1));
      }
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2, Object var3) throws IOException {
      return this.deserialize(var1, var2, (String[])var3);
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException, JsonProcessingException {
      return this.deserialize(var1, var2);
   }
}
