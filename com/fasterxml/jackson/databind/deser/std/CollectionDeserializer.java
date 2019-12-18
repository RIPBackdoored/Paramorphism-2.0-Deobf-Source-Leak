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
import com.fasterxml.jackson.databind.deser.UnresolvedForwardReference;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId$Referring;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.IOException;
import java.util.Collection;

@JacksonStdImpl
public class CollectionDeserializer extends ContainerDeserializerBase implements ContextualDeserializer {
   private static final long serialVersionUID = -1L;
   protected final JsonDeserializer _valueDeserializer;
   protected final TypeDeserializer _valueTypeDeserializer;
   protected final ValueInstantiator _valueInstantiator;
   protected final JsonDeserializer _delegateDeserializer;

   public CollectionDeserializer(JavaType var1, JsonDeserializer var2, TypeDeserializer var3, ValueInstantiator var4) {
      this(var1, var2, var3, var4, (JsonDeserializer)null, (NullValueProvider)null, (Boolean)null);
   }

   protected CollectionDeserializer(JavaType var1, JsonDeserializer var2, TypeDeserializer var3, ValueInstantiator var4, JsonDeserializer var5, NullValueProvider var6, Boolean var7) {
      super(var1, var6, var7);
      this._valueDeserializer = var2;
      this._valueTypeDeserializer = var3;
      this._valueInstantiator = var4;
      this._delegateDeserializer = var5;
   }

   protected CollectionDeserializer(CollectionDeserializer var1) {
      super((ContainerDeserializerBase)var1);
      this._valueDeserializer = var1._valueDeserializer;
      this._valueTypeDeserializer = var1._valueTypeDeserializer;
      this._valueInstantiator = var1._valueInstantiator;
      this._delegateDeserializer = var1._delegateDeserializer;
   }

   protected CollectionDeserializer withResolved(JsonDeserializer var1, JsonDeserializer var2, TypeDeserializer var3, NullValueProvider var4, Boolean var5) {
      return new CollectionDeserializer(this._containerType, var2, var3, this._valueInstantiator, var1, var4, var5);
   }

   public boolean isCachable() {
      return this._valueDeserializer == null && this._valueTypeDeserializer == null && this._delegateDeserializer == null;
   }

   public CollectionDeserializer createContextual(DeserializationContext var1, BeanProperty var2) throws JsonMappingException {
      JsonDeserializer var3 = null;
      if (this._valueInstantiator != null) {
         JavaType var4;
         if (this._valueInstantiator.canCreateUsingDelegate()) {
            var4 = this._valueInstantiator.getDelegateType(var1.getConfig());
            if (var4 == null) {
               var1.reportBadDefinition(this._containerType, String.format("Invalid delegate-creator definition for %s: value instantiator (%s) returned true for 'canCreateUsingDelegate()', but null for 'getDelegateType()'", this._containerType, this._valueInstantiator.getClass().getName()));
            }

            var3 = this.findDeserializer(var1, var4, var2);
         } else if (this._valueInstantiator.canCreateUsingArrayDelegate()) {
            var4 = this._valueInstantiator.getArrayDelegateType(var1.getConfig());
            if (var4 == null) {
               var1.reportBadDefinition(this._containerType, String.format("Invalid delegate-creator definition for %s: value instantiator (%s) returned true for 'canCreateUsingArrayDelegate()', but null for 'getArrayDelegateType()'", this._containerType, this._valueInstantiator.getClass().getName()));
            }

            var3 = this.findDeserializer(var1, var4, var2);
         }
      }

      Boolean var9 = this.findFormatFeature(var1, var2, Collection.class, JsonFormat$Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
      JsonDeserializer var5 = this._valueDeserializer;
      var5 = this.findConvertingContentDeserializer(var1, var2, var5);
      JavaType var6 = this._containerType.getContentType();
      if (var5 == null) {
         var5 = var1.findContextualValueDeserializer(var6, var2);
      } else {
         var5 = var1.handleSecondaryContextualization(var5, var2, var6);
      }

      TypeDeserializer var7 = this._valueTypeDeserializer;
      if (var7 != null) {
         var7 = var7.forProperty(var2);
      }

      NullValueProvider var8 = this.findContentNullProvider(var1, var2, var5);
      return var9 == this._unwrapSingle && var8 == this._nullProvider && var3 == this._delegateDeserializer && var5 == this._valueDeserializer && var7 == this._valueTypeDeserializer ? this : this.withResolved(var3, var5, var7, var8, var9);
   }

   public JsonDeserializer getContentDeserializer() {
      return this._valueDeserializer;
   }

   public ValueInstantiator getValueInstantiator() {
      return this._valueInstantiator;
   }

   public Collection deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      if (this._delegateDeserializer != null) {
         return (Collection)this._valueInstantiator.createUsingDelegate(var2, this._delegateDeserializer.deserialize(var1, var2));
      } else {
         if (var1.hasToken(JsonToken.VALUE_STRING)) {
            String var3 = var1.getText();
            if (var3.length() == 0) {
               return (Collection)this._valueInstantiator.createFromString(var2, var3);
            }
         }

         return this.deserialize(var1, var2, this.createDefaultInstance(var2));
      }
   }

   protected Collection createDefaultInstance(DeserializationContext var1) throws IOException {
      return (Collection)this._valueInstantiator.createUsingDefault(var1);
   }

   public Collection deserialize(JsonParser var1, DeserializationContext var2, Collection var3) throws IOException {
      if (!var1.isExpectedStartArrayToken()) {
         return this.handleNonArray(var1, var2, var3);
      } else {
         var1.setCurrentValue(var3);
         JsonDeserializer var4 = this._valueDeserializer;
         if (var4.getObjectIdReader() != null) {
            return this._deserializeWithObjectId(var1, var2, var3);
         } else {
            TypeDeserializer var5 = this._valueTypeDeserializer;

            JsonToken var6;
            while((var6 = var1.nextToken()) != JsonToken.END_ARRAY) {
               try {
                  Object var7;
                  if (var6 == JsonToken.VALUE_NULL) {
                     if (this._skipNullValues) {
                        continue;
                     }

                     var7 = this._nullProvider.getNullValue(var2);
                  } else if (var5 == null) {
                     var7 = var4.deserialize(var1, var2);
                  } else {
                     var7 = var4.deserializeWithType(var1, var2, var5);
                  }

                  var3.add(var7);
               } catch (Exception var9) {
                  boolean var8 = var2 == null || var2.isEnabled(DeserializationFeature.WRAP_EXCEPTIONS);
                  if (!var8) {
                     ClassUtil.throwIfRTE(var9);
                  }

                  throw JsonMappingException.wrapWithPath(var9, var3, var3.size());
               }
            }

            return var3;
         }
      }
   }

   public Object deserializeWithType(JsonParser var1, DeserializationContext var2, TypeDeserializer var3) throws IOException {
      return var3.deserializeTypedFromArray(var1, var2);
   }

   protected final Collection handleNonArray(JsonParser var1, DeserializationContext var2, Collection var3) throws IOException {
      boolean var4 = this._unwrapSingle == Boolean.TRUE || this._unwrapSingle == null && var2.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
      if (!var4) {
         return (Collection)var2.handleUnexpectedToken(this._containerType.getRawClass(), var1);
      } else {
         JsonDeserializer var5 = this._valueDeserializer;
         TypeDeserializer var6 = this._valueTypeDeserializer;
         JsonToken var7 = var1.getCurrentToken();

         Object var8;
         label36: {
            Collection var10000;
            try {
               if (var7 != JsonToken.VALUE_NULL) {
                  if (var6 == null) {
                     var8 = var5.deserialize(var1, var2);
                  } else {
                     var8 = var5.deserializeWithType(var1, var2, var6);
                  }
                  break label36;
               }

               if (!this._skipNullValues) {
                  var8 = this._nullProvider.getNullValue(var2);
                  break label36;
               }

               var10000 = var3;
            } catch (Exception var10) {
               throw JsonMappingException.wrapWithPath(var10, Object.class, var3.size());
            }

            return var10000;
         }

         var3.add(var8);
         return var3;
      }
   }

   protected Collection _deserializeWithObjectId(JsonParser var1, DeserializationContext var2, Collection var3) throws IOException {
      if (!var1.isExpectedStartArrayToken()) {
         return this.handleNonArray(var1, var2, var3);
      } else {
         var1.setCurrentValue(var3);
         JsonDeserializer var4 = this._valueDeserializer;
         TypeDeserializer var5 = this._valueTypeDeserializer;
         CollectionDeserializer$CollectionReferringAccumulator var6 = new CollectionDeserializer$CollectionReferringAccumulator(this._containerType.getContentType().getRawClass(), var3);

         JsonToken var7;
         while((var7 = var1.nextToken()) != JsonToken.END_ARRAY) {
            try {
               Object var8;
               if (var7 == JsonToken.VALUE_NULL) {
                  if (this._skipNullValues) {
                     continue;
                  }

                  var8 = this._nullProvider.getNullValue(var2);
               } else if (var5 == null) {
                  var8 = var4.deserialize(var1, var2);
               } else {
                  var8 = var4.deserializeWithType(var1, var2, var5);
               }

               var6.add(var8);
            } catch (UnresolvedForwardReference var10) {
               ReadableObjectId$Referring var12 = var6.handleUnresolvedReference(var10);
               var10.getRoid().appendReferring(var12);
            } catch (Exception var11) {
               boolean var9 = var2 == null || var2.isEnabled(DeserializationFeature.WRAP_EXCEPTIONS);
               if (!var9) {
                  ClassUtil.throwIfRTE(var11);
               }

               throw JsonMappingException.wrapWithPath(var11, var3, var3.size());
            }
         }

         return var3;
      }
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2, Object var3) throws IOException {
      return this.deserialize(var1, var2, (Collection)var3);
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException, JsonProcessingException {
      return this.deserialize(var1, var2);
   }

   public JsonDeserializer createContextual(DeserializationContext var1, BeanProperty var2) throws JsonMappingException {
      return this.createContextual(var1, var2);
   }
}
