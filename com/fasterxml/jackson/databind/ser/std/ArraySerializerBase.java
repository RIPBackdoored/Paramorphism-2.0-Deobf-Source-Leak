package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.annotation.JsonFormat$Feature;
import com.fasterxml.jackson.annotation.JsonFormat$Value;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import java.io.IOException;

public abstract class ArraySerializerBase extends ContainerSerializer implements ContextualSerializer {
   protected final BeanProperty _property;
   protected final Boolean _unwrapSingle;

   protected ArraySerializerBase(Class var1) {
      super(var1);
      this._property = null;
      this._unwrapSingle = null;
   }

   /** @deprecated */
   @Deprecated
   protected ArraySerializerBase(Class var1, BeanProperty var2) {
      super(var1);
      this._property = var2;
      this._unwrapSingle = null;
   }

   protected ArraySerializerBase(ArraySerializerBase var1) {
      super(var1._handledType, false);
      this._property = var1._property;
      this._unwrapSingle = var1._unwrapSingle;
   }

   protected ArraySerializerBase(ArraySerializerBase var1, BeanProperty var2, Boolean var3) {
      super(var1._handledType, false);
      this._property = var2;
      this._unwrapSingle = var3;
   }

   /** @deprecated */
   @Deprecated
   protected ArraySerializerBase(ArraySerializerBase var1, BeanProperty var2) {
      super(var1._handledType, false);
      this._property = var2;
      this._unwrapSingle = var1._unwrapSingle;
   }

   public abstract JsonSerializer _withResolved(BeanProperty var1, Boolean var2);

   public JsonSerializer createContextual(SerializerProvider var1, BeanProperty var2) throws JsonMappingException {
      Boolean var3 = null;
      if (var2 != null) {
         JsonFormat$Value var4 = this.findFormatOverrides(var1, var2, this.handledType());
         if (var4 != null) {
            var3 = var4.getFeature(JsonFormat$Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED);
            if (var3 != this._unwrapSingle) {
               return this._withResolved(var2, var3);
            }
         }
      }

      return this;
   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      if (this._shouldUnwrapSingle(var3) && this.hasSingleElement(var1)) {
         this.serializeContents(var1, var2, var3);
      } else {
         var2.setCurrentValue(var1);
         var2.writeStartArray();
         this.serializeContents(var1, var2, var3);
         var2.writeEndArray();
      }
   }

   public final void serializeWithType(Object var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException {
      var2.setCurrentValue(var1);
      WritableTypeId var5 = var4.writeTypePrefix(var2, var4.typeId(var1, JsonToken.START_ARRAY));
      this.serializeContents(var1, var2, var3);
      var4.writeTypeSuffix(var2, var5);
   }

   protected abstract void serializeContents(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException;

   protected final boolean _shouldUnwrapSingle(SerializerProvider var1) {
      return this._unwrapSingle == null ? var1.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED) : this._unwrapSingle;
   }
}
