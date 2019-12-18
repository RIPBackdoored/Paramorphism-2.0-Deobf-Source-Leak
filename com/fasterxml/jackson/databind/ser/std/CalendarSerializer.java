package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;

@JacksonStdImpl
public class CalendarSerializer extends DateTimeSerializerBase {
   public static final CalendarSerializer instance = new CalendarSerializer();

   public CalendarSerializer() {
      this((Boolean)null, (DateFormat)null);
   }

   public CalendarSerializer(Boolean var1, DateFormat var2) {
      super(Calendar.class, var1, var2);
   }

   public CalendarSerializer withFormat(Boolean var1, DateFormat var2) {
      return new CalendarSerializer(var1, var2);
   }

   protected long _timestamp(Calendar var1) {
      return var1 == null ? 0L : var1.getTimeInMillis();
   }

   public void serialize(Calendar var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      if (this._asTimestamp(var3)) {
         var2.writeNumber(this._timestamp(var1));
      } else {
         this._serializeAsString(var1.getTime(), var2, var3);
      }
   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      this.serialize((Calendar)var1, var2, var3);
   }

   protected long _timestamp(Object var1) {
      return this._timestamp((Calendar)var1);
   }

   public DateTimeSerializerBase withFormat(Boolean var1, DateFormat var2) {
      return this.withFormat(var1, var2);
   }
}
