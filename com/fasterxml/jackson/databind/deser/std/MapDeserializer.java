package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties$Value;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualKeyDeserializer;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import com.fasterxml.jackson.databind.deser.ResolvableDeserializer;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.UnresolvedForwardReference;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.deser.impl.PropertyBasedCreator;
import com.fasterxml.jackson.databind.deser.impl.PropertyValueBuffer;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId$Referring;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@JacksonStdImpl
public class MapDeserializer extends ContainerDeserializerBase implements ContextualDeserializer, ResolvableDeserializer {
   private static final long serialVersionUID = 1L;
   protected final KeyDeserializer _keyDeserializer;
   protected boolean _standardStringKey;
   protected final JsonDeserializer _valueDeserializer;
   protected final TypeDeserializer _valueTypeDeserializer;
   protected final ValueInstantiator _valueInstantiator;
   protected JsonDeserializer _delegateDeserializer;
   protected PropertyBasedCreator _propertyBasedCreator;
   protected final boolean _hasDefaultCreator;
   protected Set _ignorableProperties;

   public MapDeserializer(JavaType var1, ValueInstantiator var2, KeyDeserializer var3, JsonDeserializer var4, TypeDeserializer var5) {
      super((JavaType)var1, (NullValueProvider)null, (Boolean)null);
      this._keyDeserializer = var3;
      this._valueDeserializer = var4;
      this._valueTypeDeserializer = var5;
      this._valueInstantiator = var2;
      this._hasDefaultCreator = var2.canCreateUsingDefault();
      this._delegateDeserializer = null;
      this._propertyBasedCreator = null;
      this._standardStringKey = this._isStdKeyDeser(var1, var3);
   }

   protected MapDeserializer(MapDeserializer var1) {
      super((ContainerDeserializerBase)var1);
      this._keyDeserializer = var1._keyDeserializer;
      this._valueDeserializer = var1._valueDeserializer;
      this._valueTypeDeserializer = var1._valueTypeDeserializer;
      this._valueInstantiator = var1._valueInstantiator;
      this._propertyBasedCreator = var1._propertyBasedCreator;
      this._delegateDeserializer = var1._delegateDeserializer;
      this._hasDefaultCreator = var1._hasDefaultCreator;
      this._ignorableProperties = var1._ignorableProperties;
      this._standardStringKey = var1._standardStringKey;
   }

   protected MapDeserializer(MapDeserializer var1, KeyDeserializer var2, JsonDeserializer var3, TypeDeserializer var4, NullValueProvider var5, Set var6) {
      super((ContainerDeserializerBase)var1, var5, var1._unwrapSingle);
      this._keyDeserializer = var2;
      this._valueDeserializer = var3;
      this._valueTypeDeserializer = var4;
      this._valueInstantiator = var1._valueInstantiator;
      this._propertyBasedCreator = var1._propertyBasedCreator;
      this._delegateDeserializer = var1._delegateDeserializer;
      this._hasDefaultCreator = var1._hasDefaultCreator;
      this._ignorableProperties = var6;
      this._standardStringKey = this._isStdKeyDeser(this._containerType, var2);
   }

   protected MapDeserializer withResolved(KeyDeserializer var1, TypeDeserializer var2, JsonDeserializer var3, NullValueProvider var4, Set var5) {
      return this._keyDeserializer == var1 && this._valueDeserializer == var3 && this._valueTypeDeserializer == var2 && this._nullProvider == var4 && this._ignorableProperties == var5 ? this : new MapDeserializer(this, var1, var3, var2, var4, var5);
   }

   protected final boolean _isStdKeyDeser(JavaType var1, KeyDeserializer var2) {
      if (var2 == null) {
         return true;
      } else {
         JavaType var3 = var1.getKeyType();
         if (var3 == null) {
            return true;
         } else {
            Class var4 = var3.getRawClass();
            return (var4 == String.class || var4 == Object.class) && this.isDefaultKeyDeserializer(var2);
         }
      }
   }

   public void setIgnorableProperties(String[] var1) {
      this._ignorableProperties = var1 != null && var1.length != 0 ? ArrayBuilders.arrayToSet(var1) : null;
   }

   public void setIgnorableProperties(Set var1) {
      this._ignorableProperties = var1 != null && var1.size() != 0 ? var1 : null;
   }

   public void resolve(DeserializationContext var1) throws JsonMappingException {
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
      }

      if (this._valueInstantiator.canCreateFromObjectWith()) {
         SettableBeanProperty[] var3 = this._valueInstantiator.getFromObjectArguments(var1.getConfig());
         this._propertyBasedCreator = PropertyBasedCreator.construct(var1, this._valueInstantiator, var3, var1.isEnabled(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES));
      }

      this._standardStringKey = this._isStdKeyDeser(this._containerType, this._keyDeserializer);
   }

   public JsonDeserializer createContextual(DeserializationContext var1, BeanProperty var2) throws JsonMappingException {
      KeyDeserializer var3 = this._keyDeserializer;
      if (var3 == null) {
         var3 = var1.findKeyDeserializer(this._containerType.getKeyType(), var2);
      } else if (var3 instanceof ContextualKeyDeserializer) {
         var3 = ((ContextualKeyDeserializer)var3).createContextual(var1, var2);
      }

      JsonDeserializer var4 = this._valueDeserializer;
      if (var2 != null) {
         var4 = this.findConvertingContentDeserializer(var1, var2, var4);
      }

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

      Object var7 = this._ignorableProperties;
      AnnotationIntrospector var8 = var1.getAnnotationIntrospector();
      if (_neitherNull(var8, var2)) {
         AnnotatedMember var9 = var2.getMember();
         if (var9 != null) {
            JsonIgnoreProperties$Value var10 = var8.findPropertyIgnorals(var9);
            if (var10 != null) {
               Set var11 = var10.findIgnoredForDeserialization();
               if (!var11.isEmpty()) {
                  var7 = var7 == null ? new HashSet() : new HashSet((Collection)var7);
                  Iterator var12 = var11.iterator();

                  while(var12.hasNext()) {
                     String var13 = (String)var12.next();
                     ((Set)var7).add(var13);
                  }
               }
            }
         }
      }

      return this.withResolved(var3, var6, var4, this.findContentNullProvider(var1, var2, var4), (Set)var7);
   }

   public JsonDeserializer getContentDeserializer() {
      return this._valueDeserializer;
   }

   public ValueInstantiator getValueInstantiator() {
      return this._valueInstantiator;
   }

   public boolean isCachable() {
      return this._valueDeserializer == null && this._keyDeserializer == null && this._valueTypeDeserializer == null && this._ignorableProperties == null;
   }

   public Map deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      if (this._propertyBasedCreator != null) {
         return this._deserializeUsingCreator(var1, var2);
      } else if (this._delegateDeserializer != null) {
         return (Map)this._valueInstantiator.createUsingDelegate(var2, this._delegateDeserializer.deserialize(var1, var2));
      } else if (!this._hasDefaultCreator) {
         return (Map)var2.handleMissingInstantiator(this.getMapClass(), this.getValueInstantiator(), var1, "no default constructor found");
      } else {
         JsonToken var3 = var1.getCurrentToken();
         if (var3 != JsonToken.START_OBJECT && var3 != JsonToken.FIELD_NAME && var3 != JsonToken.END_OBJECT) {
            return var3 == JsonToken.VALUE_STRING ? (Map)this._valueInstantiator.createFromString(var2, var1.getText()) : (Map)this._deserializeFromEmpty(var1, var2);
         } else {
            Map var4 = (Map)this._valueInstantiator.createUsingDefault(var2);
            if (this._standardStringKey) {
               this._readAndBindStringKeyMap(var1, var2, var4);
               return var4;
            } else {
               this._readAndBind(var1, var2, var4);
               return var4;
            }
         }
      }
   }

   public Map deserialize(JsonParser var1, DeserializationContext var2, Map var3) throws IOException {
      var1.setCurrentValue(var3);
      JsonToken var4 = var1.getCurrentToken();
      if (var4 != JsonToken.START_OBJECT && var4 != JsonToken.FIELD_NAME) {
         return (Map)var2.handleUnexpectedToken(this.getMapClass(), var1);
      } else if (this._standardStringKey) {
         this._readAndUpdateStringKeyMap(var1, var2, var3);
         return var3;
      } else {
         this._readAndUpdate(var1, var2, var3);
         return var3;
      }
   }

   public Object deserializeWithType(JsonParser var1, DeserializationContext var2, TypeDeserializer var3) throws IOException {
      return var3.deserializeTypedFromObject(var1, var2);
   }

   public final Class getMapClass() {
      return this._containerType.getRawClass();
   }

   public JavaType getValueType() {
      return this._containerType;
   }

   protected final void _readAndBind(JsonParser var1, DeserializationContext var2, Map var3) throws IOException {
      KeyDeserializer var4 = this._keyDeserializer;
      JsonDeserializer var5 = this._valueDeserializer;
      TypeDeserializer var6 = this._valueTypeDeserializer;
      MapDeserializer$MapReferringAccumulator var7 = null;
      boolean var8 = var5.getObjectIdReader() != null;
      if (var8) {
         var7 = new MapDeserializer$MapReferringAccumulator(this._containerType.getContentType().getRawClass(), var3);
      }

      String var9;
      if (var1.isExpectedStartObjectToken()) {
         var9 = var1.nextFieldName();
      } else {
         JsonToken var10 = var1.getCurrentToken();
         if (var10 != JsonToken.FIELD_NAME) {
            if (var10 == JsonToken.END_OBJECT) {
               return;
            }

            var2.reportWrongTokenException((JsonDeserializer)this, JsonToken.FIELD_NAME, (String)null);
         }

         var9 = var1.getCurrentName();
      }

      for(; var9 != null; var9 = var1.nextFieldName()) {
         Object var15 = var4.deserializeKey(var9, var2);
         JsonToken var11 = var1.nextToken();
         if (this._ignorableProperties != null && this._ignorableProperties.contains(var9)) {
            var1.skipChildren();
         } else {
            try {
               Object var12;
               if (var11 == JsonToken.VALUE_NULL) {
                  if (this._skipNullValues) {
                     continue;
                  }

                  var12 = this._nullProvider.getNullValue(var2);
               } else if (var6 == null) {
                  var12 = var5.deserialize(var1, var2);
               } else {
                  var12 = var5.deserializeWithType(var1, var2, var6);
               }

               if (var8) {
                  var7.put(var15, var12);
               } else {
                  var3.put(var15, var12);
               }
            } catch (UnresolvedForwardReference var13) {
               this.handleUnresolvedReference(var2, var7, var15, var13);
            } catch (Exception var14) {
               this.wrapAndThrow(var14, var3, var9);
            }
         }
      }

   }

   protected final void _readAndBindStringKeyMap(JsonParser var1, DeserializationContext var2, Map var3) throws IOException {
      JsonDeserializer var4 = this._valueDeserializer;
      TypeDeserializer var5 = this._valueTypeDeserializer;
      MapDeserializer$MapReferringAccumulator var6 = null;
      boolean var7 = var4.getObjectIdReader() != null;
      if (var7) {
         var6 = new MapDeserializer$MapReferringAccumulator(this._containerType.getContentType().getRawClass(), var3);
      }

      String var8;
      JsonToken var9;
      if (var1.isExpectedStartObjectToken()) {
         var8 = var1.nextFieldName();
      } else {
         var9 = var1.getCurrentToken();
         if (var9 == JsonToken.END_OBJECT) {
            return;
         }

         if (var9 != JsonToken.FIELD_NAME) {
            var2.reportWrongTokenException((JsonDeserializer)this, JsonToken.FIELD_NAME, (String)null);
         }

         var8 = var1.getCurrentName();
      }

      for(; var8 != null; var8 = var1.nextFieldName()) {
         var9 = var1.nextToken();
         if (this._ignorableProperties != null && this._ignorableProperties.contains(var8)) {
            var1.skipChildren();
         } else {
            try {
               Object var10;
               if (var9 == JsonToken.VALUE_NULL) {
                  if (this._skipNullValues) {
                     continue;
                  }

                  var10 = this._nullProvider.getNullValue(var2);
               } else if (var5 == null) {
                  var10 = var4.deserialize(var1, var2);
               } else {
                  var10 = var4.deserializeWithType(var1, var2, var5);
               }

               if (var7) {
                  var6.put(var8, var10);
               } else {
                  var3.put(var8, var10);
               }
            } catch (UnresolvedForwardReference var11) {
               this.handleUnresolvedReference(var2, var6, var8, var11);
            } catch (Exception var12) {
               this.wrapAndThrow(var12, var3, var8);
            }
         }
      }

   }

   public Map _deserializeUsingCreator(JsonParser var1, DeserializationContext var2) throws IOException {
      PropertyBasedCreator var3 = this._propertyBasedCreator;
      PropertyValueBuffer var4 = var3.startBuilding(var1, var2, (ObjectIdReader)null);
      JsonDeserializer var5 = this._valueDeserializer;
      TypeDeserializer var6 = this._valueTypeDeserializer;
      String var7;
      if (var1.isExpectedStartObjectToken()) {
         var7 = var1.nextFieldName();
      } else if (var1.hasToken(JsonToken.FIELD_NAME)) {
         var7 = var1.getCurrentName();
      } else {
         var7 = null;
      }

      for(; var7 != null; var7 = var1.nextFieldName()) {
         JsonToken var8 = var1.nextToken();
         if (this._ignorableProperties != null && this._ignorableProperties.contains(var7)) {
            var1.skipChildren();
         } else {
            SettableBeanProperty var9 = var3.findCreatorProperty(var7);
            if (var9 != null) {
               if (var4.assignParameter(var9, var9.deserialize(var1, var2))) {
                  var1.nextToken();

                  Map var16;
                  try {
                     var16 = (Map)var3.build(var2, var4);
                  } catch (Exception var13) {
                     return (Map)this.wrapAndThrow(var13, this._containerType.getRawClass(), var7);
                  }

                  this._readAndBind(var1, var2, var16);
                  return var16;
               }
            } else {
               Object var10 = this._keyDeserializer.deserializeKey(var7, var2);

               Object var11;
               try {
                  if (var8 == JsonToken.VALUE_NULL) {
                     if (this._skipNullValues) {
                        continue;
                     }

                     var11 = this._nullProvider.getNullValue(var2);
                  } else if (var6 == null) {
                     var11 = var5.deserialize(var1, var2);
                  } else {
                     var11 = var5.deserializeWithType(var1, var2, var6);
                  }
               } catch (Exception var15) {
                  this.wrapAndThrow(var15, this._containerType.getRawClass(), var7);
                  return null;
               }

               var4.bufferMapProperty(var10, var11);
            }
         }
      }

      Map var10000;
      try {
         var10000 = (Map)var3.build(var2, var4);
      } catch (Exception var14) {
         this.wrapAndThrow(var14, this._containerType.getRawClass(), var7);
         return null;
      }

      return var10000;
   }

   protected final void _readAndUpdate(JsonParser var1, DeserializationContext var2, Map var3) throws IOException {
      KeyDeserializer var4 = this._keyDeserializer;
      JsonDeserializer var5 = this._valueDeserializer;
      TypeDeserializer var6 = this._valueTypeDeserializer;
      String var7;
      if (var1.isExpectedStartObjectToken()) {
         var7 = var1.nextFieldName();
      } else {
         JsonToken var8 = var1.getCurrentToken();
         if (var8 == JsonToken.END_OBJECT) {
            return;
         }

         if (var8 != JsonToken.FIELD_NAME) {
            var2.reportWrongTokenException((JsonDeserializer)this, JsonToken.FIELD_NAME, (String)null);
         }

         var7 = var1.getCurrentName();
      }

      for(; var7 != null; var7 = var1.nextFieldName()) {
         Object var13 = var4.deserializeKey(var7, var2);
         JsonToken var9 = var1.nextToken();
         if (this._ignorableProperties != null && this._ignorableProperties.contains(var7)) {
            var1.skipChildren();
         } else {
            try {
               if (var9 == JsonToken.VALUE_NULL) {
                  if (!this._skipNullValues) {
                     var3.put(var13, this._nullProvider.getNullValue(var2));
                  }
               } else {
                  Object var10 = var3.get(var13);
                  Object var11;
                  if (var10 != null) {
                     var11 = var5.deserialize(var1, var2, var10);
                  } else if (var6 == null) {
                     var11 = var5.deserialize(var1, var2);
                  } else {
                     var11 = var5.deserializeWithType(var1, var2, var6);
                  }

                  if (var11 != var10) {
                     var3.put(var13, var11);
                  }
               }
            } catch (Exception var12) {
               this.wrapAndThrow(var12, var3, var7);
            }
         }
      }

   }

   protected final void _readAndUpdateStringKeyMap(JsonParser var1, DeserializationContext var2, Map var3) throws IOException {
      JsonDeserializer var4 = this._valueDeserializer;
      TypeDeserializer var5 = this._valueTypeDeserializer;
      String var6;
      JsonToken var7;
      if (var1.isExpectedStartObjectToken()) {
         var6 = var1.nextFieldName();
      } else {
         var7 = var1.getCurrentToken();
         if (var7 == JsonToken.END_OBJECT) {
            return;
         }

         if (var7 != JsonToken.FIELD_NAME) {
            var2.reportWrongTokenException((JsonDeserializer)this, JsonToken.FIELD_NAME, (String)null);
         }

         var6 = var1.getCurrentName();
      }

      for(; var6 != null; var6 = var1.nextFieldName()) {
         var7 = var1.nextToken();
         if (this._ignorableProperties != null && this._ignorableProperties.contains(var6)) {
            var1.skipChildren();
         } else {
            try {
               if (var7 == JsonToken.VALUE_NULL) {
                  if (!this._skipNullValues) {
                     var3.put(var6, this._nullProvider.getNullValue(var2));
                  }
               } else {
                  Object var8 = var3.get(var6);
                  Object var9;
                  if (var8 != null) {
                     var9 = var4.deserialize(var1, var2, var8);
                  } else if (var5 == null) {
                     var9 = var4.deserialize(var1, var2);
                  } else {
                     var9 = var4.deserializeWithType(var1, var2, var5);
                  }

                  if (var9 != var8) {
                     var3.put(var6, var9);
                  }
               }
            } catch (Exception var10) {
               this.wrapAndThrow(var10, var3, var6);
            }
         }
      }

   }

   private void handleUnresolvedReference(DeserializationContext var1, MapDeserializer$MapReferringAccumulator var2, Object var3, UnresolvedForwardReference var4) throws JsonMappingException {
      if (var2 == null) {
         var1.reportInputMismatch((JsonDeserializer)this, "Unresolved forward reference but no identity info: " + var4);
      }

      ReadableObjectId$Referring var5 = var2.handleUnresolvedReference(var4, var3);
      var4.getRoid().appendReferring(var5);
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2, Object var3) throws IOException {
      return this.deserialize(var1, var2, (Map)var3);
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException, JsonProcessingException {
      return this.deserialize(var1, var2);
   }
}
