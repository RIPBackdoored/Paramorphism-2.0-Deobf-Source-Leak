package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.annotation.JsonFormat$Feature;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import java.io.IOException;
import java.util.Collection;

@JacksonStdImpl
public final class StringCollectionDeserializer extends ContainerDeserializerBase implements ContextualDeserializer {
   private static final long serialVersionUID = 1L;
   protected final JsonDeserializer _valueDeserializer;
   protected final ValueInstantiator _valueInstantiator;
   protected final JsonDeserializer _delegateDeserializer;

   public StringCollectionDeserializer(JavaType var1, JsonDeserializer var2, ValueInstantiator var3) {
      this(var1, var3, (JsonDeserializer)null, var2, var2, (Boolean)null);
   }

   protected StringCollectionDeserializer(JavaType var1, ValueInstantiator var2, JsonDeserializer var3, JsonDeserializer var4, NullValueProvider var5, Boolean var6) {
      super(var1, var5, var6);
      this._valueDeserializer = var4;
      this._valueInstantiator = var2;
      this._delegateDeserializer = var3;
   }

   protected StringCollectionDeserializer withResolved(JsonDeserializer var1, JsonDeserializer var2, NullValueProvider var3, Boolean var4) {
      return this._unwrapSingle == var4 && this._nullProvider == var3 && this._valueDeserializer == var2 && this._delegateDeserializer == var1 ? this : new StringCollectionDeserializer(this._containerType, this._valueInstantiator, var1, var2, var3, var4);
   }

   public boolean isCachable() {
      return this._valueDeserializer == null && this._delegateDeserializer == null;
   }

   public JsonDeserializer createContextual(DeserializationContext var1, BeanProperty var2) throws JsonMappingException {
      JsonDeserializer var3 = null;
      JavaType var5;
      if (this._valueInstantiator != null) {
         AnnotatedWithParams var4 = this._valueInstantiator.getDelegateCreator();
         if (var4 != null) {
            var5 = this._valueInstantiator.getDelegateType(var1.getConfig());
            var3 = this.findDeserializer(var1, var5, var2);
         }
      }

      JsonDeserializer var8 = this._valueDeserializer;
      var5 = this._containerType.getContentType();
      if (var8 == null) {
         var8 = this.findConvertingContentDeserializer(var1, var2, var8);
         if (var8 == null) {
            var8 = var1.findContextualValueDeserializer(var5, var2);
         }
      } else {
         var8 = var1.handleSecondaryContextualization(var8, var2, var5);
      }

      Boolean var6 = this.findFormatFeature(var1, var2, Collection.class, JsonFormat$Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
      NullValueProvider var7 = this.findContentNullProvider(var1, var2, var8);
      if (this.isDefaultDeserializer(var8)) {
         var8 = null;
      }

      return this.withResolved(var3, var8, var7, var6);
   }

   public JsonDeserializer getContentDeserializer() {
      JsonDeserializer var1 = this._valueDeserializer;
      return var1;
   }

   public ValueInstantiator getValueInstantiator() {
      return this._valueInstantiator;
   }

   public Collection deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      if (this._delegateDeserializer != null) {
         return (Collection)this._valueInstantiator.createUsingDelegate(var2, this._delegateDeserializer.deserialize(var1, var2));
      } else {
         Collection var3 = (Collection)this._valueInstantiator.createUsingDefault(var2);
         return this.deserialize(var1, var2, var3);
      }
   }

   public Collection deserialize(JsonParser var1, DeserializationContext var2, Collection var3) throws IOException {
      if (!var1.isExpectedStartArrayToken()) {
         return this.handleNonArray(var1, var2, var3);
      } else if (this._valueDeserializer != null) {
         return this.deserializeUsingCustom(var1, var2, var3, this._valueDeserializer);
      } else {
         try {
            while(true) {
               while(true) {
                  String var4 = var1.nextTextValue();
                  if (var4 == null) {
                     JsonToken var5 = var1.getCurrentToken();
                     if (var5 == JsonToken.END_ARRAY) {
                        return var3;
                     }

                     if (var5 == JsonToken.VALUE_NULL) {
                        if (this._skipNullValues) {
                           continue;
                        }

                        var4 = (String)this._nullProvider.getNullValue(var2);
                     } else {
                        var4 = this._parseString(var1, var2);
                     }

                     var3.add(var4);
                  } else {
                     var3.add(var4);
                  }
               }
            }
         } catch (Exception var6) {
            throw JsonMappingException.wrapWithPath(var6, var3, var3.size());
         }
      }
   }

   private Collection deserializeUsingCustom(JsonParser var1, DeserializationContext var2, Collection var3, JsonDeserializer var4) throws IOException {
      while(true) {
         String var5;
         if (var1.nextTextValue() == null) {
            JsonToken var6 = var1.getCurrentToken();
            if (var6 == JsonToken.END_ARRAY) {
               return var3;
            }

            if (var6 == JsonToken.VALUE_NULL) {
               if (this._skipNullValues) {
                  continue;
               }

               var5 = (String)this._nullProvider.getNullValue(var2);
            } else {
               var5 = (String)var4.deserialize(var1, var2);
            }
         } else {
            var5 = (String)var4.deserialize(var1, var2);
         }

         var3.add(var5);
      }
   }

   public Object deserializeWithType(JsonParser var1, DeserializationContext var2, TypeDeserializer var3) throws IOException {
      return var3.deserializeTypedFromArray(var1, var2);
   }

   private final Collection handleNonArray(JsonParser var1, DeserializationContext var2, Collection var3) throws IOException {
      boolean var4 = this._unwrapSingle == Boolean.TRUE || this._unwrapSingle == null && var2.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
      if (!var4) {
         return (Collection)var2.handleUnexpectedToken(this._containerType.getRawClass(), var1);
      } else {
         JsonDeserializer var5 = this._valueDeserializer;
         JsonToken var6 = var1.getCurrentToken();
         String var7;
         if (var6 == JsonToken.VALUE_NULL) {
            if (this._skipNullValues) {
               return var3;
            }

            var7 = (String)this._nullProvider.getNullValue(var2);
         } else {
            var7 = var5 == null ? this._parseString(var1, var2) : (String)var5.deserialize(var1, var2);
         }

         var3.add(var7);
         return var3;
      }
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2, Object var3) throws IOException {
      return this.deserialize(var1, var2, (Collection)var3);
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException, JsonProcessingException {
      return this.deserialize(var1, var2);
   }
}
