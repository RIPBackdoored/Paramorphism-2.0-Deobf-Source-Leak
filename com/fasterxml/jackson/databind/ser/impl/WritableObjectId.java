package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public final class WritableObjectId {
   public final ObjectIdGenerator generator;
   public Object id;
   protected boolean idWritten = false;

   public WritableObjectId(ObjectIdGenerator var1) {
      super();
      this.generator = var1;
   }

   public boolean writeAsId(JsonGenerator var1, SerializerProvider var2, ObjectIdWriter var3) throws IOException {
      if (this.id != null && (this.idWritten || var3.alwaysAsId)) {
         if (var1.canWriteObjectId()) {
            var1.writeObjectRef(String.valueOf(this.id));
         } else {
            var3.serializer.serialize(this.id, var1, var2);
         }

         return true;
      } else {
         return false;
      }
   }

   public Object generateId(Object var1) {
      if (this.id == null) {
         this.id = this.generator.generateId(var1);
      }

      return this.id;
   }

   public void writeAsField(JsonGenerator var1, SerializerProvider var2, ObjectIdWriter var3) throws IOException {
      this.idWritten = true;
      if (var1.canWriteObjectId()) {
         var1.writeObjectId(String.valueOf(this.id));
      } else {
         SerializableString var4 = var3.propertyName;
         if (var4 != null) {
            var1.writeFieldName(var4);
            var3.serializer.serialize(this.id, var1, var2);
         }

      }
   }
}
