package com.fasterxml.jackson.databind.ext;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.CalendarSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.util.Calendar;
import javax.xml.datatype.XMLGregorianCalendar;

public class CoreXMLSerializers$XMLGregorianCalendarSerializer extends StdSerializer implements ContextualSerializer {
   static final CoreXMLSerializers$XMLGregorianCalendarSerializer instance = new CoreXMLSerializers$XMLGregorianCalendarSerializer();
   final JsonSerializer _delegate;

   public CoreXMLSerializers$XMLGregorianCalendarSerializer() {
      this(CalendarSerializer.instance);
   }

   protected CoreXMLSerializers$XMLGregorianCalendarSerializer(JsonSerializer var1) {
      super(XMLGregorianCalendar.class);
      this._delegate = var1;
   }

   public JsonSerializer getDelegatee() {
      return this._delegate;
   }

   public boolean isEmpty(SerializerProvider var1, XMLGregorianCalendar var2) {
      return this._delegate.isEmpty(var1, this._convert(var2));
   }

   public void serialize(XMLGregorianCalendar var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      this._delegate.serialize(this._convert(var1), var2, var3);
   }

   public void serializeWithType(XMLGregorianCalendar var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException {
      this._delegate.serializeWithType(this._convert(var1), var2, var3, var4);
   }

   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper var1, JavaType var2) throws JsonMappingException {
      this._delegate.acceptJsonFormatVisitor(var1, (JavaType)null);
   }

   public JsonSerializer createContextual(SerializerProvider var1, BeanProperty var2) throws JsonMappingException {
      JsonSerializer var3 = var1.handlePrimaryContextualization(this._delegate, var2);
      return var3 != this._delegate ? new CoreXMLSerializers$XMLGregorianCalendarSerializer(var3) : this;
   }

   protected Calendar _convert(XMLGregorianCalendar var1) {
      return var1 == null ? null : var1.toGregorianCalendar();
   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      this.serialize((XMLGregorianCalendar)var1, var2, var3);
   }

   public boolean isEmpty(SerializerProvider var1, Object var2) {
      return this.isEmpty(var1, (XMLGregorianCalendar)var2);
   }

   public void serializeWithType(Object var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException {
      this.serializeWithType((XMLGregorianCalendar)var1, var2, var3, var4);
   }
}
