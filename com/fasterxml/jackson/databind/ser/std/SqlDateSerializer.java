package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;

@JacksonStdImpl
public class SqlDateSerializer extends DateTimeSerializerBase {
   public SqlDateSerializer() {
      this((Boolean)null, (DateFormat)null);
   }

   protected SqlDateSerializer(Boolean var1, DateFormat var2) {
      super(Date.class, var1, var2);
   }

   public SqlDateSerializer withFormat(Boolean var1, DateFormat var2) {
      return new SqlDateSerializer(var1, var2);
   }

   protected long _timestamp(Date var1) {
      return var1 == null ? 0L : var1.getTime();
   }

   public void serialize(Date var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      if (this._asTimestamp(var3)) {
         var2.writeNumber(this._timestamp(var1));
      } else if (this._customFormat == null) {
         var2.writeString(var1.toString());
      } else {
         this._serializeAsString(var1, var2, var3);
      }
   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      this.serialize((Date)var1, var2, var3);
   }

   protected long _timestamp(Object var1) {
      return this._timestamp((Date)var1);
   }

   public DateTimeSerializerBase withFormat(Boolean var1, DateFormat var2) {
      return this.withFormat(var1, var2);
   }
}
