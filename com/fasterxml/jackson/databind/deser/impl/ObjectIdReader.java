package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import java.io.IOException;
import java.io.Serializable;

public class ObjectIdReader implements Serializable {
   private static final long serialVersionUID = 1L;
   protected final JavaType _idType;
   public final PropertyName propertyName;
   public final ObjectIdGenerator generator;
   public final ObjectIdResolver resolver;
   protected final JsonDeserializer _deserializer;
   public final SettableBeanProperty idProperty;

   protected ObjectIdReader(JavaType var1, PropertyName var2, ObjectIdGenerator var3, JsonDeserializer var4, SettableBeanProperty var5, ObjectIdResolver var6) {
      super();
      this._idType = var1;
      this.propertyName = var2;
      this.generator = var3;
      this.resolver = var6;
      this._deserializer = var4;
      this.idProperty = var5;
   }

   public static ObjectIdReader construct(JavaType var0, PropertyName var1, ObjectIdGenerator var2, JsonDeserializer var3, SettableBeanProperty var4, ObjectIdResolver var5) {
      return new ObjectIdReader(var0, var1, var2, var3, var4, var5);
   }

   public JsonDeserializer getDeserializer() {
      return this._deserializer;
   }

   public JavaType getIdType() {
      return this._idType;
   }

   public boolean maySerializeAsObject() {
      return this.generator.maySerializeAsObject();
   }

   public boolean isValidReferencePropertyName(String var1, JsonParser var2) {
      return this.generator.isValidReferencePropertyName(var1, var2);
   }

   public Object readObjectReference(JsonParser var1, DeserializationContext var2) throws IOException {
      return this._deserializer.deserialize(var1, var2);
   }
}
