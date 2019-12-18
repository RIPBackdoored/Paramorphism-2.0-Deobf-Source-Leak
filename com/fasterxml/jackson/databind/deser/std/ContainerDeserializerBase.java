package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.ValueInstantiator$Gettable;
import com.fasterxml.jackson.databind.deser.impl.NullsConstantProvider;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.AccessPattern;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public abstract class ContainerDeserializerBase extends StdDeserializer implements ValueInstantiator$Gettable {
   protected final JavaType _containerType;
   protected final NullValueProvider _nullProvider;
   protected final Boolean _unwrapSingle;
   protected final boolean _skipNullValues;

   protected ContainerDeserializerBase(JavaType var1, NullValueProvider var2, Boolean var3) {
      super(var1);
      this._containerType = var1;
      this._unwrapSingle = var3;
      this._nullProvider = var2;
      this._skipNullValues = NullsConstantProvider.isSkipper(var2);
   }

   protected ContainerDeserializerBase(JavaType var1) {
      this((JavaType)var1, (NullValueProvider)null, (Boolean)null);
   }

   protected ContainerDeserializerBase(ContainerDeserializerBase var1) {
      this(var1, var1._nullProvider, var1._unwrapSingle);
   }

   protected ContainerDeserializerBase(ContainerDeserializerBase var1, NullValueProvider var2, Boolean var3) {
      super(var1._containerType);
      this._containerType = var1._containerType;
      this._nullProvider = var2;
      this._unwrapSingle = var3;
      this._skipNullValues = NullsConstantProvider.isSkipper(var2);
   }

   public JavaType getValueType() {
      return this._containerType;
   }

   public Boolean supportsUpdate(DeserializationConfig var1) {
      return Boolean.TRUE;
   }

   public SettableBeanProperty findBackReference(String var1) {
      JsonDeserializer var2 = this.getContentDeserializer();
      if (var2 == null) {
         throw new IllegalArgumentException(String.format("Cannot handle managed/back reference '%s': type: container deserializer of type %s returned null for 'getContentDeserializer()'", var1, this.getClass().getName()));
      } else {
         return var2.findBackReference(var1);
      }
   }

   public JavaType getContentType() {
      return this._containerType == null ? TypeFactory.unknownType() : this._containerType.getContentType();
   }

   public abstract JsonDeserializer getContentDeserializer();

   public ValueInstantiator getValueInstantiator() {
      return null;
   }

   public AccessPattern getEmptyAccessPattern() {
      return AccessPattern.DYNAMIC;
   }

   public Object getEmptyValue(DeserializationContext var1) throws JsonMappingException {
      ValueInstantiator var2 = this.getValueInstantiator();
      if (var2 == null || !var2.canCreateUsingDefault()) {
         JavaType var3 = this.getValueType();
         var1.reportBadDefinition(var3, String.format("Cannot create empty instance of %s, no default Creator", var3));
      }

      Object var10000;
      try {
         var10000 = var2.createUsingDefault(var1);
      } catch (IOException var4) {
         return ClassUtil.throwAsMappingException(var1, var4);
      }

      return var10000;
   }

   protected Object wrapAndThrow(Throwable var1, Object var2, String var3) throws IOException {
      while(var1 instanceof InvocationTargetException && var1.getCause() != null) {
         var1 = var1.getCause();
      }

      ClassUtil.throwIfError(var1);
      if (var1 instanceof IOException && !(var1 instanceof JsonMappingException)) {
         throw (IOException)var1;
      } else {
         throw JsonMappingException.wrapWithPath(var1, var2, (String)ClassUtil.nonNull(var3, "N/A"));
      }
   }
}
