package com.fasterxml.jackson.databind.deser.std;

import java.sql.*;
import com.fasterxml.jackson.databind.deser.*;
import com.fasterxml.jackson.annotation.*;
import java.util.*;
import com.fasterxml.jackson.databind.*;
import java.text.*;
import java.io.*;
import com.fasterxml.jackson.databind.annotation.*;
import java.lang.reflect.*;
import com.fasterxml.jackson.databind.util.*;
import com.fasterxml.jackson.core.*;

public class DateDeserializers
{
    private static final HashSet<String> _classNames;
    
    public DateDeserializers() {
        super();
    }
    
    public static JsonDeserializer<?> find(final Class<?> clazz, final String s) {
        if (DateDeserializers._classNames.contains(s)) {
            if (clazz == Calendar.class) {
                return new CalendarDeserializer();
            }
            if (clazz == Date.class) {
                return DateDeserializer.instance;
            }
            if (clazz == java.sql.Date.class) {
                return new SqlDateDeserializer();
            }
            if (clazz == Timestamp.class) {
                return new TimestampDeserializer();
            }
            if (clazz == GregorianCalendar.class) {
                return new CalendarDeserializer(GregorianCalendar.class);
            }
        }
        return null;
    }
    
    static {
        _classNames = new HashSet<String>();
        final Class[] array = { Calendar.class, GregorianCalendar.class, java.sql.Date.class, Date.class, Timestamp.class };
        for (int length = array.length, i = 0; i < length; ++i) {
            DateDeserializers._classNames.add(array[i].getName());
        }
    }
    
    protected abstract static class DateBasedDeserializer<T> extends StdScalarDeserializer<T> implements ContextualDeserializer
    {
        protected final DateFormat _customFormat;
        protected final String _formatString;
        
        protected DateBasedDeserializer(final Class<?> clazz) {
            super(clazz);
            this._customFormat = null;
            this._formatString = null;
        }
        
        protected DateBasedDeserializer(final DateBasedDeserializer<T> dateBasedDeserializer, final DateFormat customFormat, final String formatString) {
            super(dateBasedDeserializer._valueClass);
            this._customFormat = customFormat;
            this._formatString = formatString;
        }
        
        protected abstract DateBasedDeserializer<T> withDateFormat(final DateFormat p0, final String p1);
        
        @Override
        public JsonDeserializer<?> createContextual(final DeserializationContext deserializationContext, final BeanProperty beanProperty) throws JsonMappingException {
            final JsonFormat.Value formatOverrides = this.findFormatOverrides(deserializationContext, beanProperty, this.handledType());
            if (formatOverrides != null) {
                TimeZone timeZone = formatOverrides.getTimeZone();
                final Boolean lenient = formatOverrides.getLenient();
                if (formatOverrides.hasPattern()) {
                    final String pattern = formatOverrides.getPattern();
                    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, formatOverrides.hasLocale() ? formatOverrides.getLocale() : deserializationContext.getLocale());
                    if (timeZone == null) {
                        timeZone = deserializationContext.getTimeZone();
                    }
                    simpleDateFormat.setTimeZone(timeZone);
                    if (lenient != null) {
                        simpleDateFormat.setLenient(lenient);
                    }
                    return this.withDateFormat(simpleDateFormat, pattern);
                }
                if (timeZone != null) {
                    final DateFormat dateFormat = deserializationContext.getConfig().getDateFormat();
                    DateFormat dateFormat2;
                    if (((StdDateFormat)dateFormat).getClass() == StdDateFormat.class) {
                        StdDateFormat stdDateFormat = ((StdDateFormat)dateFormat).withTimeZone(timeZone).withLocale(formatOverrides.hasLocale() ? formatOverrides.getLocale() : deserializationContext.getLocale());
                        if (lenient != null) {
                            stdDateFormat = stdDateFormat.withLenient(lenient);
                        }
                        dateFormat2 = stdDateFormat;
                    }
                    else {
                        dateFormat2 = (DateFormat)dateFormat.clone();
                        dateFormat2.setTimeZone(timeZone);
                        if (lenient != null) {
                            dateFormat2.setLenient(lenient);
                        }
                    }
                    return this.withDateFormat(dateFormat2, this._formatString);
                }
                if (lenient != null) {
                    final DateFormat dateFormat3 = deserializationContext.getConfig().getDateFormat();
                    String s = this._formatString;
                    DateFormat withLenient;
                    if (((StdDateFormat)dateFormat3).getClass() == StdDateFormat.class) {
                        s = ((StdDateFormat)(withLenient = ((StdDateFormat)dateFormat3).withLenient(lenient))).toPattern();
                    }
                    else {
                        withLenient = (DateFormat)dateFormat3.clone();
                        withLenient.setLenient(lenient);
                        if (withLenient instanceof SimpleDateFormat) {
                            ((SimpleDateFormat)withLenient).toPattern();
                        }
                    }
                    if (s == null) {
                        s = "[unknown]";
                    }
                    return this.withDateFormat(withLenient, s);
                }
            }
            return this;
        }
        
        @Override
        protected Date _parseDate(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
            if (this._customFormat != null && jsonParser.hasToken(JsonToken.VALUE_STRING)) {
                final String trim = jsonParser.getText().trim();
                if (trim.length() == 0) {
                    return (Date)this.getEmptyValue(deserializationContext);
                }
                synchronized (this._customFormat) {
                    try {
                        return this._customFormat.parse(trim);
                    }
                    catch (ParseException ex) {
                        return (Date)deserializationContext.handleWeirdStringValue(this.handledType(), trim, "expected format \"%s\"", this._formatString);
                    }
                }
            }
            return super._parseDate(jsonParser, deserializationContext);
        }
    }
    
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
    
    @JacksonStdImpl
    public static class DateDeserializer extends DateBasedDeserializer<Date>
    {
        public static final DateDeserializer instance;
        
        public DateDeserializer() {
            super(Date.class);
        }
        
        public DateDeserializer(final DateDeserializer dateDeserializer, final DateFormat dateFormat, final String s) {
            super(dateDeserializer, dateFormat, s);
        }
        
        @Override
        protected DateDeserializer withDateFormat(final DateFormat dateFormat, final String s) {
            return new DateDeserializer(this, dateFormat, s);
        }
        
        @Override
        public Date deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
            return this._parseDate(jsonParser, deserializationContext);
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
        
        static {
            instance = new DateDeserializer();
        }
    }
    
    public static class SqlDateDeserializer extends DateBasedDeserializer<java.sql.Date>
    {
        public SqlDateDeserializer() {
            super(java.sql.Date.class);
        }
        
        public SqlDateDeserializer(final SqlDateDeserializer sqlDateDeserializer, final DateFormat dateFormat, final String s) {
            super(sqlDateDeserializer, dateFormat, s);
        }
        
        @Override
        protected SqlDateDeserializer withDateFormat(final DateFormat dateFormat, final String s) {
            return new SqlDateDeserializer(this, dateFormat, s);
        }
        
        @Override
        public java.sql.Date deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
            final Date parseDate = this._parseDate(jsonParser, deserializationContext);
            return (parseDate == null) ? null : new java.sql.Date(parseDate.getTime());
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
    
    public static class TimestampDeserializer extends DateBasedDeserializer<Timestamp>
    {
        public TimestampDeserializer() {
            super(Timestamp.class);
        }
        
        public TimestampDeserializer(final TimestampDeserializer timestampDeserializer, final DateFormat dateFormat, final String s) {
            super(timestampDeserializer, dateFormat, s);
        }
        
        @Override
        protected TimestampDeserializer withDateFormat(final DateFormat dateFormat, final String s) {
            return new TimestampDeserializer(this, dateFormat, s);
        }
        
        @Override
        public Timestamp deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
            final Date parseDate = this._parseDate(jsonParser, deserializationContext);
            return (parseDate == null) ? null : new Timestamp(parseDate.getTime());
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
}
