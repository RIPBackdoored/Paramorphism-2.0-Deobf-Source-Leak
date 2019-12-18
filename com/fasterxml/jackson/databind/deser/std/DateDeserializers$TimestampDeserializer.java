package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;

public class DateDeserializers$TimestampDeserializer extends DateDeserializers$DateBasedDeserializer {
   public DateDeserializers$TimestampDeserializer() {
      super(Timestamp.class);
   }

   public DateDeserializers$TimestampDeserializer(DateDeserializers$TimestampDeserializer var1, DateFormat var2, String var3) {
      super(var1, var2, var3);
   }

   protected DateDeserializers$TimestampDeserializer withDateFormat(DateFormat var1, String var2) {
      return new DateDeserializers$TimestampDeserializer(this, var1, var2);
   }

   public Timestamp deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      Date var3 = this._parseDate(var1, var2);
      return var3 == null ? null : new Timestamp(var3.getTime());
   }

   public JsonDeserializer createContextual(DeserializationContext var1, BeanProperty var2) throws JsonMappingException {
      return super.createContextual(var1, var2);
   }

   protected DateDeserializers$DateBasedDeserializer withDateFormat(DateFormat var1, String var2) {
      return this.withDateFormat(var1, var2);
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException, JsonProcessingException {
      return this.deserialize(var1, var2);
   }
}
