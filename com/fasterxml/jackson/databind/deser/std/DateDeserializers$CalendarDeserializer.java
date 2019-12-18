package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.databind.annotation.*;
import java.lang.reflect.*;
import com.fasterxml.jackson.databind.util.*;
import java.text.*;
import java.util.*;
import java.io.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.core.*;

@JacksonStdImpl
public static class CalendarDeserializer extends DateBasedDeserializer<Calendar>
{
    protected final Constructor<Calendar> _defaultCtor;
    
    public CalendarDeserializer() {
        super(Calendar.class);
        this._defaultCtor = null;
    }
    
    public CalendarDeserializer(final Class<? extends Calendar> clazz) {
        super(clazz);
        this._defaultCtor = ClassUtil.findConstructor(clazz, false);
    }
    
    public CalendarDeserializer(final CalendarDeserializer calendarDeserializer, final DateFormat dateFormat, final String s) {
        super(calendarDeserializer, dateFormat, s);
        this._defaultCtor = calendarDeserializer._defaultCtor;
    }
    
    @Override
    protected CalendarDeserializer withDateFormat(final DateFormat dateFormat, final String s) {
        return new CalendarDeserializer(this, dateFormat, s);
    }
    
    @Override
    public Calendar deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        final Date parseDate = this._parseDate(jsonParser, deserializationContext);
        if (parseDate == null) {
            return null;
        }
        if (this._defaultCtor == null) {
            return deserializationContext.constructCalendar(parseDate);
        }
        try {
            final Calendar calendar = this._defaultCtor.newInstance(new Object[0]);
            calendar.setTimeInMillis(parseDate.getTime());
            final TimeZone timeZone = deserializationContext.getTimeZone();
            if (timeZone != null) {
                calendar.setTimeZone(timeZone);
            }
            return calendar;
        }
        catch (Exception ex) {
            return (Calendar)deserializationContext.handleInstantiationProblem(this.handledType(), parseDate, ex);
        }
    }
    
    @Override
    public JsonDeserializer createContextual(final DeserializationContext deserializationContext, final BeanProperty beanProperty) throws JsonMappingException {
        return super.createContextual(deserializationContext, beanProperty);
    }
    
    @Override
    protected DateBasedDeserializer withDateFormat(final DateFormat dateFormat, final String s) {
        return this.withDateFormat(dateFormat, s);
    }
    
    @Override
    public Object deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return this.deserialize(jsonParser, deserializationContext);
    }
}
