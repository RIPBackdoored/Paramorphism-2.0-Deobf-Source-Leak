package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

@JacksonStdImpl
public class DateDeserializers$DateDeserializer extends DateDeserializers$DateBasedDeserializer {
   public static final DateDeserializers$DateDeserializer instance = new DateDeserializers$DateDeserializer();

   public DateDeserializers$DateDeserializer() {
      super(Date.class);
   }

   public DateDeserializers$DateDeserializer(DateDeserializers$DateDeserializer var1, DateFormat var2, String var3) {
      super(var1, var2, var3);
   }

   protected DateDeserializers$DateDeserializer withDateFormat(DateFormat var1, String var2) {
      return new DateDeserializers$DateDeserializer(this, var1, var2);
   }

   public Date deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      return this._parseDate(var1, var2);
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
