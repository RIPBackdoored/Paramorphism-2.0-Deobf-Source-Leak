package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.databind.deser.*;
import com.fasterxml.jackson.databind.util.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.*;
import java.util.*;
import com.fasterxml.jackson.core.*;
import java.text.*;
import java.io.*;

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
