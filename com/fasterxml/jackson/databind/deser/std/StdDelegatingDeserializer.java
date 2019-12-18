package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.ResolvableDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.Converter;
import java.io.IOException;

public class StdDelegatingDeserializer extends StdDeserializer implements ContextualDeserializer, ResolvableDeserializer {
   private static final long serialVersionUID = 1L;
   protected final Converter _converter;
   protected final JavaType _delegateType;
   protected final JsonDeserializer _delegateDeserializer;

   public StdDelegatingDeserializer(Converter var1) {
      super(Object.class);
      this._converter = var1;
      this._delegateType = null;
      this._delegateDeserializer = null;
   }

   public StdDelegatingDeserializer(Converter var1, JavaType var2, JsonDeserializer var3) {
      super(var2);
      this._converter = var1;
      this._delegateType = var2;
      this._delegateDeserializer = var3;
   }

   protected StdDelegatingDeserializer(StdDelegatingDeserializer var1) {
      super((StdDeserializer)var1);
      this._converter = var1._converter;
      this._delegateType = var1._delegateType;
      this._delegateDeserializer = var1._delegateDeserializer;
   }

   protected StdDelegatingDeserializer withDelegate(Converter var1, JavaType var2, JsonDeserializer var3) {
      ClassUtil.verifyMustOverride(StdDelegatingDeserializer.class, this, "withDelegate");
      return new StdDelegatingDeserializer(var1, var2, var3);
   }

   public void resolve(DeserializationContext var1) throws JsonMappingException {
      if (this._delegateDeserializer != null && this._delegateDeserializer instanceof ResolvableDeserializer) {
         ((ResolvableDeserializer)this._delegateDeserializer).resolve(var1);
      }

   }

   public JsonDeserializer createContextual(DeserializationContext var1, BeanProperty var2) throws JsonMappingException {
      if (this._delegateDeserializer != null) {
         JsonDeserializer var4 = var1.handleSecondaryContextualization(this._delegateDeserializer, var2, this._delegateType);
         return var4 != this._delegateDeserializer ? this.withDelegate(this._converter, this._delegateType, var4) : this;
      } else {
         JavaType var3 = this._converter.getInputType(var1.getTypeFactory());
         return this.withDelegate(this._converter, var3, var1.findContextualValueDeserializer(var3, var2));
      }
   }

   public JsonDeserializer getDelegatee() {
      return this._delegateDeserializer;
   }

   public Class handledType() {
      return this._delegateDeserializer.handledType();
   }

   public Boolean supportsUpdate(DeserializationConfig var1) {
      return this._delegateDeserializer.supportsUpdate(var1);
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      Object var3 = this._delegateDeserializer.deserialize(var1, var2);
      return var3 == null ? null : this.convertValue(var3);
   }

   public Object deserializeWithType(JsonParser var1, DeserializationContext var2, TypeDeserializer var3) throws IOException {
      Object var4 = this._delegateDeserializer.deserialize(var1, var2);
      return var4 == null ? null : this.convertValue(var4);
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2, Object var3) throws IOException {
      return this._delegateType.getRawClass().isAssignableFrom(var3.getClass()) ? this._delegateDeserializer.deserialize(var1, var2, var3) : this._handleIncompatibleUpdateValue(var1, var2, var3);
   }

   protected Object _handleIncompatibleUpdateValue(JsonParser var1, DeserializationContext var2, Object var3) throws IOException {
      throw new UnsupportedOperationException(String.format("Cannot update object of type %s (using deserializer for type %s)" + var3.getClass().getName(), this._delegateType));
   }

   protected Object convertValue(Object var1) {
      return this._converter.convert(var1);
   }
}
