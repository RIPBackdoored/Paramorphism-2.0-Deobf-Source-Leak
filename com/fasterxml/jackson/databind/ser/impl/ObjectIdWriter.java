package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.PropertyName;

public final class ObjectIdWriter {
   public final JavaType idType;
   public final SerializableString propertyName;
   public final ObjectIdGenerator generator;
   public final JsonSerializer serializer;
   public final boolean alwaysAsId;

   protected ObjectIdWriter(JavaType var1, SerializableString var2, ObjectIdGenerator var3, JsonSerializer var4, boolean var5) {
      super();
      this.idType = var1;
      this.propertyName = var2;
      this.generator = var3;
      this.serializer = var4;
      this.alwaysAsId = var5;
   }

   public static ObjectIdWriter construct(JavaType var0, PropertyName var1, ObjectIdGenerator var2, boolean var3) {
      String var4 = var1 == null ? null : var1.getSimpleName();
      SerializedString var5 = var4 == null ? null : new SerializedString(var4);
      return new ObjectIdWriter(var0, var5, var2, (JsonSerializer)null, var3);
   }

   public ObjectIdWriter withSerializer(JsonSerializer var1) {
      return new ObjectIdWriter(this.idType, this.propertyName, this.generator, var1, this.alwaysAsId);
   }

   public ObjectIdWriter withAlwaysAsId(boolean var1) {
      return var1 == this.alwaysAsId ? this : new ObjectIdWriter(this.idType, this.propertyName, this.generator, this.serializer, var1);
   }
}
