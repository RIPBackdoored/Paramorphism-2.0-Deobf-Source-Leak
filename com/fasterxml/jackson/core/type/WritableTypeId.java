package com.fasterxml.jackson.core.type;

import com.fasterxml.jackson.core.JsonToken;

public class WritableTypeId {
   public Object forValue;
   public Class forValueType;
   public Object id;
   public String asProperty;
   public WritableTypeId$Inclusion include;
   public JsonToken valueShape;
   public boolean wrapperWritten;
   public Object extra;

   public WritableTypeId() {
      super();
   }

   public WritableTypeId(Object var1, JsonToken var2) {
      this(var1, (JsonToken)var2, (Object)null);
   }

   public WritableTypeId(Object var1, Class var2, JsonToken var3) {
      this(var1, (JsonToken)var3, (Object)null);
      this.forValueType = var2;
   }

   public WritableTypeId(Object var1, JsonToken var2, Object var3) {
      super();
      this.forValue = var1;
      this.id = var3;
      this.valueShape = var2;
   }
}
