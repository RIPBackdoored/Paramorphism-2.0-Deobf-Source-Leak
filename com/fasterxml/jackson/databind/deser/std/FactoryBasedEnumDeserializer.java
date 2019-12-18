package com.fasterxml.jackson.databind.deser.std;

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
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.deser.impl.PropertyBasedCreator;
import com.fasterxml.jackson.databind.deser.impl.PropertyValueBuffer;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.IOException;

class FactoryBasedEnumDeserializer extends StdDeserializer implements ContextualDeserializer {
   private static final long serialVersionUID = 1L;
   protected final JavaType _inputType;
   protected final boolean _hasArgs;
   protected final AnnotatedMethod _factory;
   protected final JsonDeserializer _deser;
   protected final ValueInstantiator _valueInstantiator;
   protected final SettableBeanProperty[] _creatorProps;
   private transient PropertyBasedCreator _propCreator;

   public FactoryBasedEnumDeserializer(Class var1, AnnotatedMethod var2, JavaType var3, ValueInstantiator var4, SettableBeanProperty[] var5) {
      super(var1);
      this._factory = var2;
      this._hasArgs = true;
      this._inputType = var3.hasRawClass(String.class) ? null : var3;
      this._deser = null;
      this._valueInstantiator = var4;
      this._creatorProps = var5;
   }

   public FactoryBasedEnumDeserializer(Class var1, AnnotatedMethod var2) {
      super(var1);
      this._factory = var2;
      this._hasArgs = false;
      this._inputType = null;
      this._deser = null;
      this._valueInstantiator = null;
      this._creatorProps = null;
   }

   protected FactoryBasedEnumDeserializer(FactoryBasedEnumDeserializer var1, JsonDeserializer var2) {
      super(var1._valueClass);
      this._inputType = var1._inputType;
      this._factory = var1._factory;
      this._hasArgs = var1._hasArgs;
      this._valueInstantiator = var1._valueInstantiator;
      this._creatorProps = var1._creatorProps;
      this._deser = var2;
   }

   public JsonDeserializer createContextual(DeserializationContext var1, BeanProperty var2) throws JsonMappingException {
      return this._deser == null && this._inputType != null && this._creatorProps == null ? new FactoryBasedEnumDeserializer(this, var1.findContextualValueDeserializer(this._inputType, var2)) : this;
   }

   public Boolean supportsUpdate(DeserializationConfig var1) {
      return Boolean.FALSE;
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      Object var3 = null;
      Object var10000;
      Throwable var5;
      if (this._deser != null) {
         var3 = this._deser.deserialize(var1, var2);
      } else {
         if (!this._hasArgs) {
            var1.skipChildren();

            try {
               var10000 = this._factory.call();
            } catch (Exception var6) {
               var5 = ClassUtil.throwRootCauseIfIOE(var6);
               return var2.handleInstantiationProblem(this._valueClass, (Object)null, var5);
            }

            return var10000;
         }

         JsonToken var4 = var1.getCurrentToken();
         if (var4 != JsonToken.VALUE_STRING && var4 != JsonToken.FIELD_NAME) {
            if (this._creatorProps != null && var1.isExpectedStartObjectToken()) {
               if (this._propCreator == null) {
                  this._propCreator = PropertyBasedCreator.construct(var2, this._valueInstantiator, this._creatorProps, var2.isEnabled(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES));
               }

               var1.nextToken();
               return this.deserializeEnumUsingPropertyBased(var1, var2, this._propCreator);
            }

            var3 = var1.getValueAsString();
         } else {
            var3 = var1.getText();
         }
      }

      try {
         var10000 = this._factory.callOnWith(this._valueClass, var3);
      } catch (Exception var7) {
         var5 = ClassUtil.throwRootCauseIfIOE(var7);
         if (var2.isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL) && var5 instanceof IllegalArgumentException) {
            return null;
         }

         return var2.handleInstantiationProblem(this._valueClass, var3, var5);
      }

      return var10000;
   }

   public Object deserializeWithType(JsonParser var1, DeserializationContext var2, TypeDeserializer var3) throws IOException {
      return this._deser == null ? this.deserialize(var1, var2) : var3.deserializeTypedFromAny(var1, var2);
   }

   protected Object deserializeEnumUsingPropertyBased(JsonParser var1, DeserializationContext var2, PropertyBasedCreator var3) throws IOException {
      PropertyValueBuffer var4 = var3.startBuilding(var1, var2, (ObjectIdReader)null);

      for(JsonToken var5 = var1.getCurrentToken(); var5 == JsonToken.FIELD_NAME; var5 = var1.nextToken()) {
         String var6 = var1.getCurrentName();
         var1.nextToken();
         SettableBeanProperty var7 = var3.findCreatorProperty(var6);
         if (var7 != null) {
            var4.assignParameter(var7, this._deserializeWithErrorWrapping(var1, var2, var7));
         } else if (var4.readIdProperty(var6)) {
         }
      }

      return var3.build(var2, var4);
   }

   protected final Object _deserializeWithErrorWrapping(JsonParser var1, DeserializationContext var2, SettableBeanProperty var3) throws IOException {
      Object var10000;
      try {
         var10000 = var3.deserialize(var1, var2);
      } catch (Exception var5) {
         return this.wrapAndThrow(var5, this.handledType(), var3.getName(), var2);
      }

      return var10000;
   }

   protected Object wrapAndThrow(Throwable var1, Object var2, String var3, DeserializationContext var4) throws IOException {
      throw JsonMappingException.wrapWithPath(this.throwOrReturnThrowable(var1, var4), var2, var3);
   }

   private Throwable throwOrReturnThrowable(Throwable var1, DeserializationContext var2) throws IOException {
      var1 = ClassUtil.getRootCause(var1);
      ClassUtil.throwIfError(var1);
      boolean var3 = var2 == null || var2.isEnabled(DeserializationFeature.WRAP_EXCEPTIONS);
      if (var1 instanceof IOException) {
         if (!var3 || !(var1 instanceof JsonProcessingException)) {
            throw (IOException)var1;
         }
      } else if (!var3) {
         ClassUtil.throwIfRTE(var1);
      }

      return var1;
   }
}
