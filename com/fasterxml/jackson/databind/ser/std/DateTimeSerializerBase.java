package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.annotation.JsonFormat$Shape;
import com.fasterxml.jackson.annotation.JsonFormat$Value;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser$NumberType;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicReference;

public abstract class DateTimeSerializerBase extends StdScalarSerializer implements ContextualSerializer {
   protected final Boolean _useTimestamp;
   protected final DateFormat _customFormat;
   protected final AtomicReference _reusedCustomFormat;

   protected DateTimeSerializerBase(Class var1, Boolean var2, DateFormat var3) {
      super(var1);
      this._useTimestamp = var2;
      this._customFormat = var3;
      this._reusedCustomFormat = var3 == null ? null : new AtomicReference();
   }

   public abstract DateTimeSerializerBase withFormat(Boolean var1, DateFormat var2);

   public JsonSerializer createContextual(SerializerProvider var1, BeanProperty var2) throws JsonMappingException {
      if (var2 == null) {
         return this;
      } else {
         JsonFormat$Value var3 = this.findFormatOverrides(var1, var2, this.handledType());
         if (var3 == null) {
            return this;
         } else {
            JsonFormat$Shape var4 = var3.getShape();
            if (var4.isNumeric()) {
               return this.withFormat(Boolean.TRUE, (DateFormat)null);
            } else if (var3.hasPattern()) {
               Locale var12 = var3.hasLocale() ? var3.getLocale() : var1.getLocale();
               SimpleDateFormat var13 = new SimpleDateFormat(var3.getPattern(), var12);
               TimeZone var14 = var3.hasTimeZone() ? var3.getTimeZone() : var1.getTimeZone();
               var13.setTimeZone(var14);
               return this.withFormat(Boolean.FALSE, var13);
            } else {
               boolean var5 = var3.hasLocale();
               boolean var6 = var3.hasTimeZone();
               boolean var7 = var4 == JsonFormat$Shape.STRING;
               if (!var5 && !var6 && !var7) {
                  return this;
               } else {
                  DateFormat var8 = var1.getConfig().getDateFormat();
                  if (var8 instanceof StdDateFormat) {
                     StdDateFormat var15 = (StdDateFormat)var8;
                     if (var3.hasLocale()) {
                        var15 = var15.withLocale(var3.getLocale());
                     }

                     if (var3.hasTimeZone()) {
                        var15 = var15.withTimeZone(var3.getTimeZone());
                     }

                     return this.withFormat(Boolean.FALSE, var15);
                  } else {
                     if (!(var8 instanceof SimpleDateFormat)) {
                        var1.reportBadDefinition(this.handledType(), String.format("Configured `DateFormat` (%s) not a `SimpleDateFormat`; cannot configure `Locale` or `TimeZone`", var8.getClass().getName()));
                     }

                     SimpleDateFormat var9 = (SimpleDateFormat)var8;
                     if (var5) {
                        var9 = new SimpleDateFormat(var9.toPattern(), var3.getLocale());
                     } else {
                        var9 = (SimpleDateFormat)var9.clone();
                     }

                     TimeZone var10 = var3.getTimeZone();
                     boolean var11 = var10 != null && !var10.equals(var9.getTimeZone());
                     if (var11) {
                        var9.setTimeZone(var10);
                     }

                     return this.withFormat(Boolean.FALSE, var9);
                  }
               }
            }
         }
      }
   }

   public boolean isEmpty(SerializerProvider var1, Object var2) {
      return false;
   }

   protected abstract long _timestamp(Object var1);

   public JsonNode getSchema(SerializerProvider var1, Type var2) {
      return this.createSchemaNode(this._asTimestamp(var1) ? "number" : "string", true);
   }

   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper var1, JavaType var2) throws JsonMappingException {
      this._acceptJsonFormatVisitor(var1, var2, this._asTimestamp(var1.getProvider()));
   }

   public abstract void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException;

   protected boolean _asTimestamp(SerializerProvider var1) {
      if (this._useTimestamp != null) {
         return this._useTimestamp;
      } else if (this._customFormat == null) {
         if (var1 != null) {
            return var1.isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
         } else {
            throw new IllegalArgumentException("Null SerializerProvider passed for " + this.handledType().getName());
         }
      } else {
         return false;
      }
   }

   protected void _acceptJsonFormatVisitor(JsonFormatVisitorWrapper var1, JavaType var2, boolean var3) throws JsonMappingException {
      if (var3) {
         this.visitIntFormat(var1, var2, JsonParser$NumberType.LONG, JsonValueFormat.UTC_MILLISEC);
      } else {
         this.visitStringFormat(var1, var2, JsonValueFormat.DATE_TIME);
      }

   }

   protected void _serializeAsString(Date var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      if (this._customFormat == null) {
         var3.defaultSerializeDateValue(var1, var2);
      } else {
         DateFormat var4 = (DateFormat)this._reusedCustomFormat.getAndSet((Object)null);
         if (var4 == null) {
            var4 = (DateFormat)this._customFormat.clone();
         }

         var2.writeString(var4.format(var1));
         this._reusedCustomFormat.compareAndSet((Object)null, var4);
      }
   }
}
