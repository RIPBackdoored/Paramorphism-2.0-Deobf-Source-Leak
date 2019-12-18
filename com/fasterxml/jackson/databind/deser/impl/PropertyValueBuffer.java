package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.SettableAnyProperty;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import java.io.IOException;
import java.util.BitSet;

public class PropertyValueBuffer {
   protected final JsonParser _parser;
   protected final DeserializationContext _context;
   protected final ObjectIdReader _objectIdReader;
   protected final Object[] _creatorParameters;
   protected int _paramsNeeded;
   protected int _paramsSeen;
   protected final BitSet _paramsSeenBig;
   protected PropertyValue _buffered;
   protected Object _idValue;

   public PropertyValueBuffer(JsonParser var1, DeserializationContext var2, int var3, ObjectIdReader var4) {
      super();
      this._parser = var1;
      this._context = var2;
      this._paramsNeeded = var3;
      this._objectIdReader = var4;
      this._creatorParameters = new Object[var3];
      if (var3 < 32) {
         this._paramsSeenBig = null;
      } else {
         this._paramsSeenBig = new BitSet();
      }

   }

   public final boolean hasParameter(SettableBeanProperty var1) {
      if (this._paramsSeenBig == null) {
         return (this._paramsSeen >> var1.getCreatorIndex() & 1) == 1;
      } else {
         return this._paramsSeenBig.get(var1.getCreatorIndex());
      }
   }

   public Object getParameter(SettableBeanProperty var1) throws JsonMappingException {
      Object var2;
      if (this.hasParameter(var1)) {
         var2 = this._creatorParameters[var1.getCreatorIndex()];
      } else {
         var2 = this._creatorParameters[var1.getCreatorIndex()] = this._findMissing(var1);
      }

      return var2 == null && this._context.isEnabled(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES) ? this._context.reportInputMismatch((BeanProperty)var1, "Null value for creator property '%s' (index %d); `DeserializationFeature.FAIL_ON_NULL_FOR_CREATOR_PARAMETERS` enabled", var1.getName(), var1.getCreatorIndex()) : var2;
   }

   public Object[] getParameters(SettableBeanProperty[] var1) throws JsonMappingException {
      int var2;
      if (this._paramsNeeded > 0) {
         int var3;
         if (this._paramsSeenBig == null) {
            var2 = this._paramsSeen;
            var3 = 0;

            for(int var4 = this._creatorParameters.length; var3 < var4; var2 >>= 1) {
               if ((var2 & 1) == 0) {
                  this._creatorParameters[var3] = this._findMissing(var1[var3]);
               }

               ++var3;
            }
         } else {
            var2 = this._creatorParameters.length;

            for(var3 = 0; (var3 = this._paramsSeenBig.nextClearBit(var3)) < var2; ++var3) {
               this._creatorParameters[var3] = this._findMissing(var1[var3]);
            }
         }
      }

      if (this._context.isEnabled(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES)) {
         for(var2 = 0; var2 < var1.length; ++var2) {
            if (this._creatorParameters[var2] == null) {
               SettableBeanProperty var5 = var1[var2];
               this._context.reportInputMismatch(var5.getType(), "Null value for creator property '%s' (index %d); `DeserializationFeature.FAIL_ON_NULL_FOR_CREATOR_PARAMETERS` enabled", var5.getName(), var1[var2].getCreatorIndex());
            }
         }
      }

      return this._creatorParameters;
   }

   protected Object _findMissing(SettableBeanProperty var1) throws JsonMappingException {
      Object var2 = var1.getInjectableValueId();
      if (var2 != null) {
         return this._context.findInjectableValue(var1.getInjectableValueId(), var1, (Object)null);
      } else {
         if (var1.isRequired()) {
            this._context.reportInputMismatch((BeanProperty)var1, "Missing required creator property '%s' (index %d)", var1.getName(), var1.getCreatorIndex());
         }

         if (this._context.isEnabled(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES)) {
            this._context.reportInputMismatch((BeanProperty)var1, "Missing creator property '%s' (index %d); `DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES` enabled", var1.getName(), var1.getCreatorIndex());
         }

         JsonDeserializer var3 = var1.getValueDeserializer();
         return var3.getNullValue(this._context);
      }
   }

   public boolean readIdProperty(String var1) throws IOException {
      if (this._objectIdReader != null && var1.equals(this._objectIdReader.propertyName.getSimpleName())) {
         this._idValue = this._objectIdReader.readObjectReference(this._parser, this._context);
         return true;
      } else {
         return false;
      }
   }

   public Object handleIdValue(DeserializationContext var1, Object var2) throws IOException {
      if (this._objectIdReader != null) {
         if (this._idValue != null) {
            ReadableObjectId var3 = var1.findObjectId(this._idValue, this._objectIdReader.generator, this._objectIdReader.resolver);
            var3.bindItem(var2);
            SettableBeanProperty var4 = this._objectIdReader.idProperty;
            if (var4 != null) {
               return var4.setAndReturn(var2, this._idValue);
            }
         } else {
            var1.reportUnresolvedObjectId(this._objectIdReader, var2);
         }
      }

      return var2;
   }

   protected PropertyValue buffered() {
      return this._buffered;
   }

   public boolean isComplete() {
      return this._paramsNeeded <= 0;
   }

   public boolean assignParameter(SettableBeanProperty var1, Object var2) {
      int var3 = var1.getCreatorIndex();
      this._creatorParameters[var3] = var2;
      if (this._paramsSeenBig == null) {
         int var4 = this._paramsSeen;
         int var5 = var4 | 1 << var3;
         if (var4 != var5) {
            this._paramsSeen = var5;
            if (--this._paramsNeeded <= 0) {
               return this._objectIdReader == null || this._idValue != null;
            }
         }
      } else if (!this._paramsSeenBig.get(var3)) {
         this._paramsSeenBig.set(var3);
         if (--this._paramsNeeded <= 0) {
         }
      }

      return false;
   }

   public void bufferProperty(SettableBeanProperty var1, Object var2) {
      this._buffered = new PropertyValue$Regular(this._buffered, var2, var1);
   }

   public void bufferAnyProperty(SettableAnyProperty var1, String var2, Object var3) {
      this._buffered = new PropertyValue$Any(this._buffered, var3, var1, var2);
   }

   public void bufferMapProperty(Object var1, Object var2) {
      this._buffered = new PropertyValue$Map(this._buffered, var2, var1);
   }
}
