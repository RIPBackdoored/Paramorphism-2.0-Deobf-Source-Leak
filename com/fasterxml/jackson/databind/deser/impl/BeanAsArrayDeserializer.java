package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBase;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.IOException;
import java.util.Set;

public class BeanAsArrayDeserializer extends BeanDeserializerBase {
   private static final long serialVersionUID = 1L;
   protected final BeanDeserializerBase _delegate;
   protected final SettableBeanProperty[] _orderedProperties;

   public BeanAsArrayDeserializer(BeanDeserializerBase var1, SettableBeanProperty[] var2) {
      super(var1);
      this._delegate = var1;
      this._orderedProperties = var2;
   }

   public JsonDeserializer unwrappingDeserializer(NameTransformer var1) {
      return this._delegate.unwrappingDeserializer(var1);
   }

   public BeanDeserializerBase withObjectIdReader(ObjectIdReader var1) {
      return new BeanAsArrayDeserializer(this._delegate.withObjectIdReader(var1), this._orderedProperties);
   }

   public BeanDeserializerBase withIgnorableProperties(Set var1) {
      return new BeanAsArrayDeserializer(this._delegate.withIgnorableProperties(var1), this._orderedProperties);
   }

   public BeanDeserializerBase withBeanProperties(BeanPropertyMap var1) {
      return new BeanAsArrayDeserializer(this._delegate.withBeanProperties(var1), this._orderedProperties);
   }

   protected BeanDeserializerBase asArrayDeserializer() {
      return this;
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      if (!var1.isExpectedStartArrayToken()) {
         return this._deserializeFromNonArray(var1, var2);
      } else if (!this._vanillaProcessing) {
         return this._deserializeNonVanilla(var1, var2);
      } else {
         Object var3 = this._valueInstantiator.createUsingDefault(var2);
         var1.setCurrentValue(var3);
         SettableBeanProperty[] var4 = this._orderedProperties;
         int var5 = 0;

         for(int var6 = var4.length; var1.nextToken() != JsonToken.END_ARRAY; ++var5) {
            if (var5 == var6) {
               if (!this._ignoreAllUnknown && var2.isEnabled(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)) {
                  var2.reportWrongTokenException((JsonDeserializer)this, JsonToken.END_ARRAY, "Unexpected JSON values; expected at most %d properties (in JSON Array)", var6);
               }

               do {
                  var1.skipChildren();
               } while(var1.nextToken() != JsonToken.END_ARRAY);

               return var3;
            }

            SettableBeanProperty var7 = var4[var5];
            if (var7 != null) {
               try {
                  var7.deserializeAndSet(var1, var2, var3);
               } catch (Exception var9) {
                  this.wrapAndThrow(var9, var3, var7.getName(), var2);
               }
            } else {
               var1.skipChildren();
            }
         }

         return var3;
      }
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2, Object var3) throws IOException {
      var1.setCurrentValue(var3);
      if (!var1.isExpectedStartArrayToken()) {
         return this._deserializeFromNonArray(var1, var2);
      } else {
         if (this._injectables != null) {
            this.injectValues(var2, var3);
         }

         SettableBeanProperty[] var4 = this._orderedProperties;
         int var5 = 0;

         for(int var6 = var4.length; var1.nextToken() != JsonToken.END_ARRAY; ++var5) {
            if (var5 == var6) {
               if (!this._ignoreAllUnknown && var2.isEnabled(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)) {
                  var2.reportWrongTokenException((JsonDeserializer)this, JsonToken.END_ARRAY, "Unexpected JSON values; expected at most %d properties (in JSON Array)", var6);
               }

               do {
                  var1.skipChildren();
               } while(var1.nextToken() != JsonToken.END_ARRAY);

               return var3;
            }

            SettableBeanProperty var7 = var4[var5];
            if (var7 != null) {
               try {
                  var7.deserializeAndSet(var1, var2, var3);
               } catch (Exception var9) {
                  this.wrapAndThrow(var9, var3, var7.getName(), var2);
               }
            } else {
               var1.skipChildren();
            }
         }

         return var3;
      }
   }

   public Object deserializeFromObject(JsonParser var1, DeserializationContext var2) throws IOException {
      return this._deserializeFromNonArray(var1, var2);
   }

   protected Object _deserializeNonVanilla(JsonParser var1, DeserializationContext var2) throws IOException {
      if (this._nonStandardCreation) {
         return this.deserializeFromObjectUsingNonDefault(var1, var2);
      } else {
         Object var3 = this._valueInstantiator.createUsingDefault(var2);
         var1.setCurrentValue(var3);
         if (this._injectables != null) {
            this.injectValues(var2, var3);
         }

         Class var4 = this._needViewProcesing ? var2.getActiveView() : null;
         SettableBeanProperty[] var5 = this._orderedProperties;
         int var6 = 0;
         int var7 = var5.length;

         while(true) {
            while(var1.nextToken() != JsonToken.END_ARRAY) {
               if (var6 == var7) {
                  if (!this._ignoreAllUnknown) {
                     var2.reportWrongTokenException((JsonDeserializer)this, JsonToken.END_ARRAY, "Unexpected JSON values; expected at most %d properties (in JSON Array)", var7);
                  }

                  do {
                     var1.skipChildren();
                  } while(var1.nextToken() != JsonToken.END_ARRAY);

                  return var3;
               }

               SettableBeanProperty var8 = var5[var6];
               ++var6;
               if (var8 != null && (var4 == null || var8.visibleInView(var4))) {
                  try {
                     var8.deserializeAndSet(var1, var2, var3);
                  } catch (Exception var10) {
                     this.wrapAndThrow(var10, var3, var8.getName(), var2);
                  }
               } else {
                  var1.skipChildren();
               }
            }

            return var3;
         }
      }
   }

   protected final Object _deserializeUsingPropertyBased(JsonParser var1, DeserializationContext var2) throws IOException {
      PropertyBasedCreator var3 = this._propertyBasedCreator;
      PropertyValueBuffer var4 = var3.startBuilding(var1, var2, this._objectIdReader);
      SettableBeanProperty[] var5 = this._orderedProperties;
      int var6 = var5.length;
      int var7 = 0;
      Object var8 = null;

      for(Class var9 = this._needViewProcesing ? var2.getActiveView() : null; var1.nextToken() != JsonToken.END_ARRAY; ++var7) {
         SettableBeanProperty var10 = var7 < var6 ? var5[var7] : null;
         if (var10 == null) {
            var1.skipChildren();
         } else if (var9 != null && !var10.visibleInView(var9)) {
            var1.skipChildren();
         } else if (var8 != null) {
            try {
               var10.deserializeAndSet(var1, var2, var8);
            } catch (Exception var15) {
               this.wrapAndThrow(var15, var8, var10.getName(), var2);
            }
         } else {
            String var11 = var10.getName();
            SettableBeanProperty var12 = var3.findCreatorProperty(var11);
            if (var12 != null) {
               if (var4.assignParameter(var12, var12.deserialize(var1, var2))) {
                  try {
                     var8 = var3.build(var2, var4);
                  } catch (Exception var16) {
                     this.wrapAndThrow(var16, this._beanType.getRawClass(), var11, var2);
                     continue;
                  }

                  var1.setCurrentValue(var8);
                  if (var8.getClass() != this._beanType.getRawClass()) {
                     var2.reportBadDefinition(this._beanType, String.format("Cannot support implicit polymorphic deserialization for POJOs-as-Arrays style: nominal type %s, actual type %s", this._beanType.getRawClass().getName(), var8.getClass().getName()));
                  }
               }
            } else if (!var4.readIdProperty(var11)) {
               var4.bufferProperty(var10, var10.deserialize(var1, var2));
            }
         }
      }

      if (var8 == null) {
         try {
            var8 = var3.build(var2, var4);
         } catch (Exception var14) {
            return this.wrapInstantiationProblem(var14, var2);
         }
      }

      return var8;
   }

   protected Object _deserializeFromNonArray(JsonParser var1, DeserializationContext var2) throws IOException {
      return var2.handleUnexpectedToken(this.handledType(), var1.getCurrentToken(), var1, "Cannot deserialize a POJO (of type %s) from non-Array representation (token: %s): type/property designed to be serialized as JSON Array", this._beanType.getRawClass().getName(), var1.getCurrentToken());
   }
}
