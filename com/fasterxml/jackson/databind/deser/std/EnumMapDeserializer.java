package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import com.fasterxml.jackson.databind.deser.ResolvableDeserializer;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.deser.impl.PropertyBasedCreator;
import com.fasterxml.jackson.databind.deser.impl.PropertyValueBuffer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.IOException;
import java.util.EnumMap;

public class EnumMapDeserializer extends ContainerDeserializerBase implements ContextualDeserializer, ResolvableDeserializer {
   private static final long serialVersionUID = 1L;
   protected final Class _enumClass;
   protected KeyDeserializer _keyDeserializer;
   protected JsonDeserializer _valueDeserializer;
   protected final TypeDeserializer _valueTypeDeserializer;
   protected final ValueInstantiator _valueInstantiator;
   protected JsonDeserializer _delegateDeserializer;
   protected PropertyBasedCreator _propertyBasedCreator;

   public EnumMapDeserializer(JavaType var1, ValueInstantiator var2, KeyDeserializer var3, JsonDeserializer var4, TypeDeserializer var5, NullValueProvider var6) {
      super((JavaType)var1, var6, (Boolean)null);
      this._enumClass = var1.getKeyType().getRawClass();
      this._keyDeserializer = var3;
      this._valueDeserializer = var4;
      this._valueTypeDeserializer = var5;
      this._valueInstantiator = var2;
   }

   protected EnumMapDeserializer(EnumMapDeserializer var1, KeyDeserializer var2, JsonDeserializer var3, TypeDeserializer var4, NullValueProvider var5) {
      super((ContainerDeserializerBase)var1, var5, var1._unwrapSingle);
      this._enumClass = var1._enumClass;
      this._keyDeserializer = var2;
      this._valueDeserializer = var3;
      this._valueTypeDeserializer = var4;
      this._valueInstantiator = var1._valueInstantiator;
      this._delegateDeserializer = var1._delegateDeserializer;
      this._propertyBasedCreator = var1._propertyBasedCreator;
   }

   /** @deprecated */
   @Deprecated
   public EnumMapDeserializer(JavaType var1, KeyDeserializer var2, JsonDeserializer var3, TypeDeserializer var4) {
      this(var1, (ValueInstantiator)null, var2, var3, var4, (NullValueProvider)null);
   }

   public EnumMapDeserializer withResolved(KeyDeserializer var1, JsonDeserializer var2, TypeDeserializer var3, NullValueProvider var4) {
      return var1 == this._keyDeserializer && var4 == this._nullProvider && var2 == this._valueDeserializer && var3 == this._valueTypeDeserializer ? this : new EnumMapDeserializer(this, var1, var2, var3, var4);
   }

   public void resolve(DeserializationContext var1) throws JsonMappingException {
      if (this._valueInstantiator != null) {
         JavaType var2;
         if (this._valueInstantiator.canCreateUsingDelegate()) {
            var2 = this._valueInstantiator.getDelegateType(var1.getConfig());
            if (var2 == null) {
               var1.reportBadDefinition(this._containerType, String.format("Invalid delegate-creator definition for %s: value instantiator (%s) returned true for 'canCreateUsingDelegate()', but null for 'getDelegateType()'", this._containerType, this._valueInstantiator.getClass().getName()));
            }

            this._delegateDeserializer = this.findDeserializer(var1, var2, (BeanProperty)null);
         } else if (this._valueInstantiator.canCreateUsingArrayDelegate()) {
            var2 = this._valueInstantiator.getArrayDelegateType(var1.getConfig());
            if (var2 == null) {
               var1.reportBadDefinition(this._containerType, String.format("Invalid delegate-creator definition for %s: value instantiator (%s) returned true for 'canCreateUsingArrayDelegate()', but null for 'getArrayDelegateType()'", this._containerType, this._valueInstantiator.getClass().getName()));
            }

            this._delegateDeserializer = this.findDeserializer(var1, var2, (BeanProperty)null);
         } else if (this._valueInstantiator.canCreateFromObjectWith()) {
            SettableBeanProperty[] var3 = this._valueInstantiator.getFromObjectArguments(var1.getConfig());
            this._propertyBasedCreator = PropertyBasedCreator.construct(var1, this._valueInstantiator, var3, var1.isEnabled(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES));
         }
      }

   }

   public JsonDeserializer createContextual(DeserializationContext var1, BeanProperty var2) throws JsonMappingException {
      KeyDeserializer var3 = this._keyDeserializer;
      if (var3 == null) {
         var3 = var1.findKeyDeserializer(this._containerType.getKeyType(), var2);
      }

      JsonDeserializer var4 = this._valueDeserializer;
      JavaType var5 = this._containerType.getContentType();
      if (var4 == null) {
         var4 = var1.findContextualValueDeserializer(var5, var2);
      } else {
         var4 = var1.handleSecondaryContextualization(var4, var2, var5);
      }

      TypeDeserializer var6 = this._valueTypeDeserializer;
      if (var6 != null) {
         var6 = var6.forProperty(var2);
      }

      return this.withResolved(var3, var4, var6, this.findContentNullProvider(var1, var2, var4));
   }

   public boolean isCachable() {
      return this._valueDeserializer == null && this._keyDeserializer == null && this._valueTypeDeserializer == null;
   }

   public JsonDeserializer getContentDeserializer() {
      return this._valueDeserializer;
   }

   public Object getEmptyValue(DeserializationContext var1) throws JsonMappingException {
      return this.constructMap(var1);
   }

   public EnumMap deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      if (this._propertyBasedCreator != null) {
         return this._deserializeUsingProperties(var1, var2);
      } else if (this._delegateDeserializer != null) {
         return (EnumMap)this._valueInstantiator.createUsingDelegate(var2, this._delegateDeserializer.deserialize(var1, var2));
      } else {
         JsonToken var3 = var1.getCurrentToken();
         if (var3 != JsonToken.START_OBJECT && var3 != JsonToken.FIELD_NAME && var3 != JsonToken.END_OBJECT) {
            return var3 == JsonToken.VALUE_STRING ? (EnumMap)this._valueInstantiator.createFromString(var2, var1.getText()) : (EnumMap)this._deserializeFromEmpty(var1, var2);
         } else {
            EnumMap var4 = this.constructMap(var2);
            return this.deserialize(var1, var2, var4);
         }
      }
   }

   public EnumMap deserialize(JsonParser var1, DeserializationContext var2, EnumMap var3) throws IOException {
      var1.setCurrentValue(var3);
      JsonDeserializer var4 = this._valueDeserializer;
      TypeDeserializer var5 = this._valueTypeDeserializer;
      String var6;
      if (var1.isExpectedStartObjectToken()) {
         var6 = var1.nextFieldName();
      } else {
         JsonToken var7 = var1.getCurrentToken();
         if (var7 != JsonToken.FIELD_NAME) {
            if (var7 == JsonToken.END_OBJECT) {
               return var3;
            }

            var2.reportWrongTokenException((JsonDeserializer)this, JsonToken.FIELD_NAME, (String)null);
         }

         var6 = var1.getCurrentName();
      }

      for(; var6 != null; var6 = var1.nextFieldName()) {
         Enum var12 = (Enum)this._keyDeserializer.deserializeKey(var6, var2);
         JsonToken var8 = var1.nextToken();
         if (var12 == null) {
            if (!var2.isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)) {
               return (EnumMap)var2.handleWeirdStringValue(this._enumClass, var6, "value not one of declared Enum instance names for %s", this._containerType.getKeyType());
            }

            var1.skipChildren();
         } else {
            Object var9;
            try {
               if (var8 == JsonToken.VALUE_NULL) {
                  if (this._skipNullValues) {
                     continue;
                  }

                  var9 = this._nullProvider.getNullValue(var2);
               } else if (var5 == null) {
                  var9 = var4.deserialize(var1, var2);
               } else {
                  var9 = var4.deserializeWithType(var1, var2, var5);
               }
            } catch (Exception var11) {
               return (EnumMap)this.wrapAndThrow(var11, var3, var6);
            }

            var3.put(var12, var9);
         }
      }

      return var3;
   }

   public Object deserializeWithType(JsonParser var1, DeserializationContext var2, TypeDeserializer var3) throws IOException {
      return var3.deserializeTypedFromObject(var1, var2);
   }

   protected EnumMap constructMap(DeserializationContext var1) throws JsonMappingException {
      if (this._valueInstantiator == null) {
         return new EnumMap(this._enumClass);
      } else {
         EnumMap var10000;
         try {
            if (!this._valueInstantiator.canCreateUsingDefault()) {
               var10000 = (EnumMap)var1.handleMissingInstantiator(this.handledType(), this.getValueInstantiator(), (JsonParser)null, "no default constructor found");
               return var10000;
            }

            var10000 = (EnumMap)this._valueInstantiator.createUsingDefault(var1);
         } catch (IOException var3) {
            return (EnumMap)ClassUtil.throwAsMappingException(var1, var3);
         }

         return var10000;
      }
   }

   public EnumMap _deserializeUsingProperties(JsonParser var1, DeserializationContext var2) throws IOException {
      PropertyBasedCreator var3 = this._propertyBasedCreator;
      PropertyValueBuffer var4 = var3.startBuilding(var1, var2, (ObjectIdReader)null);
      String var5;
      if (var1.isExpectedStartObjectToken()) {
         var5 = var1.nextFieldName();
      } else if (var1.hasToken(JsonToken.FIELD_NAME)) {
         var5 = var1.getCurrentName();
      } else {
         var5 = null;
      }

      for(; var5 != null; var5 = var1.nextFieldName()) {
         JsonToken var6 = var1.nextToken();
         SettableBeanProperty var7 = var3.findCreatorProperty(var5);
         if (var7 != null) {
            if (var4.assignParameter(var7, var7.deserialize(var1, var2))) {
               var1.nextToken();

               EnumMap var14;
               try {
                  var14 = (EnumMap)var3.build(var2, var4);
               } catch (Exception var11) {
                  return (EnumMap)this.wrapAndThrow(var11, this._containerType.getRawClass(), var5);
               }

               return this.deserialize(var1, var2, var14);
            }
         } else {
            Enum var8 = (Enum)this._keyDeserializer.deserializeKey(var5, var2);
            if (var8 == null) {
               if (!var2.isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)) {
                  return (EnumMap)var2.handleWeirdStringValue(this._enumClass, var5, "value not one of declared Enum instance names for %s", this._containerType.getKeyType());
               }

               var1.nextToken();
               var1.skipChildren();
            } else {
               Object var9;
               try {
                  if (var6 == JsonToken.VALUE_NULL) {
                     if (this._skipNullValues) {
                        continue;
                     }

                     var9 = this._nullProvider.getNullValue(var2);
                  } else if (this._valueTypeDeserializer == null) {
                     var9 = this._valueDeserializer.deserialize(var1, var2);
                  } else {
                     var9 = this._valueDeserializer.deserializeWithType(var1, var2, this._valueTypeDeserializer);
                  }
               } catch (Exception var13) {
                  this.wrapAndThrow(var13, this._containerType.getRawClass(), var5);
                  return null;
               }

               var4.bufferMapProperty(var8, var9);
            }
         }
      }

      EnumMap var10000;
      try {
         var10000 = (EnumMap)var3.build(var2, var4);
      } catch (Exception var12) {
         this.wrapAndThrow(var12, this._containerType.getRawClass(), var5);
         return null;
      }

      return var10000;
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2, Object var3) throws IOException {
      return this.deserialize(var1, var2, (EnumMap)var3);
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException, JsonProcessingException {
      return this.deserialize(var1, var2);
   }
}
