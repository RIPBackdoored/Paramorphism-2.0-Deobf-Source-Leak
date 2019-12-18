package com.fasterxml.jackson.annotation;

import java.lang.annotation.*;
import java.io.*;
import java.util.*;

@Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotation
public @interface JsonFormat {
    public static final String DEFAULT_LOCALE = "##default";
    public static final String DEFAULT_TIMEZONE = "##default";
    
    String pattern() default "";
    
    Shape shape() default Shape.ANY;
    
    String locale() default "##default";
    
    String timezone() default "##default";
    
    OptBoolean lenient() default OptBoolean.DEFAULT;
    
    Feature[] with() default {};
    
    Feature[] without() default {};
    
    public enum Shape
    {
        ANY, 
        NATURAL, 
        SCALAR, 
        ARRAY, 
        OBJECT, 
        NUMBER, 
        NUMBER_FLOAT, 
        NUMBER_INT, 
        STRING, 
        BOOLEAN;
        
        private static final Shape[] $VALUES;
        
        public static Shape[] values() {
            return Shape.$VALUES.clone();
        }
        
        public static Shape valueOf(final String s) {
            return Enum.valueOf(Shape.class, s);
        }
        
        public boolean isNumeric() {
            return this == Shape.NUMBER || this == Shape.NUMBER_INT || this == Shape.NUMBER_FLOAT;
        }
        
        public boolean isStructured() {
            return this == Shape.OBJECT || this == Shape.ARRAY;
        }
        
        static {
            $VALUES = new Shape[] { Shape.ANY, Shape.NATURAL, Shape.SCALAR, Shape.ARRAY, Shape.OBJECT, Shape.NUMBER, Shape.NUMBER_FLOAT, Shape.NUMBER_INT, Shape.STRING, Shape.BOOLEAN };
        }
    }
    
    public enum Feature
    {
        ACCEPT_SINGLE_VALUE_AS_ARRAY, 
        ACCEPT_CASE_INSENSITIVE_PROPERTIES, 
        WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, 
        WRITE_DATES_WITH_ZONE_ID, 
        WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, 
        WRITE_SORTED_MAP_ENTRIES, 
        ADJUST_DATES_TO_CONTEXT_TIME_ZONE;
        
        private static final Feature[] $VALUES;
        
        public static Feature[] values() {
            return Feature.$VALUES.clone();
        }
        
        public static Feature valueOf(final String s) {
            return Enum.valueOf(Feature.class, s);
        }
        
        static {
            $VALUES = new Feature[] { Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, Feature.WRITE_DATES_WITH_ZONE_ID, Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, Feature.WRITE_SORTED_MAP_ENTRIES, Feature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE };
        }
    }
    
    public static class Features
    {
        private final int _enabled;
        private final int _disabled;
        private static final Features EMPTY;
        
        private Features(final int enabled, final int disabled) {
            super();
            this._enabled = enabled;
            this._disabled = disabled;
        }
        
        public static Features empty() {
            return Features.EMPTY;
        }
        
        public static Features construct(final JsonFormat jsonFormat) {
            return construct(jsonFormat.with(), jsonFormat.without());
        }
        
        public static Features construct(final Feature[] array, final Feature[] array2) {
            int n = 0;
            for (int length = array.length, i = 0; i < length; ++i) {
                n |= 1 << array[i].ordinal();
            }
            int n2 = 0;
            for (int length2 = array2.length, j = 0; j < length2; ++j) {
                n2 |= 1 << array2[j].ordinal();
            }
            return new Features(n, n2);
        }
        
        public Features withOverrides(final Features features) {
            if (features == null) {
                return this;
            }
            final int disabled = features._disabled;
            final int enabled = features._enabled;
            if (disabled == 0 && enabled == 0) {
                return this;
            }
            if (this._enabled == 0 && this._disabled == 0) {
                return features;
            }
            final int n = (this._enabled & ~disabled) | enabled;
            final int n2 = (this._disabled & ~enabled) | disabled;
            if (n == this._enabled && n2 == this._disabled) {
                return this;
            }
            return new Features(n, n2);
        }
        
        public Features with(final Feature... array) {
            int enabled = this._enabled;
            for (int length = array.length, i = 0; i < length; ++i) {
                enabled |= 1 << array[i].ordinal();
            }
            return (enabled == this._enabled) ? this : new Features(enabled, this._disabled);
        }
        
        public Features without(final Feature... array) {
            int disabled = this._disabled;
            for (int length = array.length, i = 0; i < length; ++i) {
                disabled |= 1 << array[i].ordinal();
            }
            return (disabled == this._disabled) ? this : new Features(this._enabled, disabled);
        }
        
        public Boolean get(final Feature feature) {
            final int n = 1 << feature.ordinal();
            if ((this._disabled & n) != 0x0) {
                return Boolean.FALSE;
            }
            if ((this._enabled & n) != 0x0) {
                return Boolean.TRUE;
            }
            return null;
        }
        
        @Override
        public int hashCode() {
            return this._disabled + this._enabled;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            if (o == null) {
                return false;
            }
            if (o.getClass() != this.getClass()) {
                return false;
            }
            final Features features = (Features)o;
            return features._enabled == this._enabled && features._disabled == this._disabled;
        }
        
        static {
            EMPTY = new Features(0, 0);
        }
    }
    
    public static class Value implements JacksonAnnotationValue<JsonFormat>, Serializable
    {
        private static final long serialVersionUID = 1L;
        private static final Value EMPTY;
        private final String _pattern;
        private final Shape _shape;
        private final Locale _locale;
        private final String _timezoneStr;
        private final Boolean _lenient;
        private final Features _features;
        private transient TimeZone _timezone;
        
        public Value() {
            this("", Shape.ANY, "", "", Features.empty(), null);
        }
        
        public Value(final JsonFormat jsonFormat) {
            this(jsonFormat.pattern(), jsonFormat.shape(), jsonFormat.locale(), jsonFormat.timezone(), Features.construct(jsonFormat), jsonFormat.lenient().asBoolean());
        }
        
        public Value(final String s, final Shape shape, final String s2, final String s3, final Features features, final Boolean b) {
            this(s, shape, (s2 == null || s2.length() == 0 || "##default".equals(s2)) ? null : new Locale(s2), (s3 == null || s3.length() == 0 || "##default".equals(s3)) ? null : s3, null, features, b);
        }
        
        public Value(final String pattern, final Shape shape, final Locale locale, final TimeZone timezone, final Features features, final Boolean lenient) {
            super();
            this._pattern = pattern;
            this._shape = ((shape == null) ? Shape.ANY : shape);
            this._locale = locale;
            this._timezone = timezone;
            this._timezoneStr = null;
            this._features = ((features == null) ? Features.empty() : features);
            this._lenient = lenient;
        }
        
        public Value(final String pattern, final Shape shape, final Locale locale, final String timezoneStr, final TimeZone timezone, final Features features, final Boolean lenient) {
            super();
            this._pattern = pattern;
            this._shape = ((shape == null) ? Shape.ANY : shape);
            this._locale = locale;
            this._timezone = timezone;
            this._timezoneStr = timezoneStr;
            this._features = ((features == null) ? Features.empty() : features);
            this._lenient = lenient;
        }
        
        @Deprecated
        public Value(final String s, final Shape shape, final Locale locale, final String s2, final TimeZone timeZone, final Features features) {
            this(s, shape, locale, s2, timeZone, features, null);
        }
        
        @Deprecated
        public Value(final String s, final Shape shape, final String s2, final String s3, final Features features) {
            this(s, shape, s2, s3, features, null);
        }
        
        @Deprecated
        public Value(final String s, final Shape shape, final Locale locale, final TimeZone timeZone, final Features features) {
            this(s, shape, locale, timeZone, features, null);
        }
        
        public static final Value empty() {
            return Value.EMPTY;
        }
        
        public static Value merge(final Value value, final Value value2) {
            return (value == null) ? value2 : value.withOverrides(value2);
        }
        
        public static Value mergeAll(final Value... array) {
            Value value = null;
            for (final Value value2 : array) {
                if (value2 != null) {
                    value = ((value == null) ? value2 : value.withOverrides(value2));
                }
            }
            return value;
        }
        
        public static final Value from(final JsonFormat jsonFormat) {
            return (jsonFormat == null) ? Value.EMPTY : new Value(jsonFormat);
        }
        
        public final Value withOverrides(final Value value) {
            if (value == null || value == Value.EMPTY || value == this) {
                return this;
            }
            if (this == Value.EMPTY) {
                return value;
            }
            String s = value._pattern;
            if (s == null || s.isEmpty()) {
                s = this._pattern;
            }
            Shape shape = value._shape;
            if (shape == Shape.ANY) {
                shape = this._shape;
            }
            Locale locale = value._locale;
            if (locale == null) {
                locale = this._locale;
            }
            final Features features = this._features;
            Features features2;
            if (features == null) {
                features2 = value._features;
            }
            else {
                features2 = features.withOverrides(value._features);
            }
            Boolean b = value._lenient;
            if (b == null) {
                b = this._lenient;
            }
            String s2 = value._timezoneStr;
            TimeZone timeZone;
            if (s2 == null || s2.isEmpty()) {
                s2 = this._timezoneStr;
                timeZone = this._timezone;
            }
            else {
                timeZone = value._timezone;
            }
            return new Value(s, shape, locale, s2, timeZone, features2, b);
        }
        
        public static Value forPattern(final String s) {
            return new Value(s, null, null, null, null, Features.empty(), null);
        }
        
        public static Value forShape(final Shape shape) {
            return new Value(null, shape, null, null, null, Features.empty(), null);
        }
        
        public static Value forLeniency(final boolean b) {
            return new Value(null, null, null, null, null, Features.empty(), b);
        }
        
        public Value withPattern(final String s) {
            return new Value(s, this._shape, this._locale, this._timezoneStr, this._timezone, this._features, this._lenient);
        }
        
        public Value withShape(final Shape shape) {
            if (shape == this._shape) {
                return this;
            }
            return new Value(this._pattern, shape, this._locale, this._timezoneStr, this._timezone, this._features, this._lenient);
        }
        
        public Value withLocale(final Locale locale) {
            return new Value(this._pattern, this._shape, locale, this._timezoneStr, this._timezone, this._features, this._lenient);
        }
        
        public Value withTimeZone(final TimeZone timeZone) {
            return new Value(this._pattern, this._shape, this._locale, null, timeZone, this._features, this._lenient);
        }
        
        public Value withLenient(final Boolean b) {
            if (b == this._lenient) {
                return this;
            }
            return new Value(this._pattern, this._shape, this._locale, this._timezoneStr, this._timezone, this._features, b);
        }
        
        public Value withFeature(final Feature feature) {
            final Features with = this._features.with(feature);
            return (with == this._features) ? this : new Value(this._pattern, this._shape, this._locale, this._timezoneStr, this._timezone, with, this._lenient);
        }
        
        public Value withoutFeature(final Feature feature) {
            final Features without = this._features.without(feature);
            return (without == this._features) ? this : new Value(this._pattern, this._shape, this._locale, this._timezoneStr, this._timezone, without, this._lenient);
        }
        
        @Override
        public Class<JsonFormat> valueFor() {
            return JsonFormat.class;
        }
        
        public String getPattern() {
            return this._pattern;
        }
        
        public Shape getShape() {
            return this._shape;
        }
        
        public Locale getLocale() {
            return this._locale;
        }
        
        public Boolean getLenient() {
            return this._lenient;
        }
        
        public boolean isLenient() {
            return Boolean.TRUE.equals(this._lenient);
        }
        
        public String timeZoneAsString() {
            if (this._timezone != null) {
                return this._timezone.getID();
            }
            return this._timezoneStr;
        }
        
        public TimeZone getTimeZone() {
            TimeZone timezone = this._timezone;
            if (timezone == null) {
                if (this._timezoneStr == null) {
                    return null;
                }
                timezone = TimeZone.getTimeZone(this._timezoneStr);
                this._timezone = timezone;
            }
            return timezone;
        }
        
        public boolean hasShape() {
            return this._shape != Shape.ANY;
        }
        
        public boolean hasPattern() {
            return this._pattern != null && this._pattern.length() > 0;
        }
        
        public boolean hasLocale() {
            return this._locale != null;
        }
        
        public boolean hasTimeZone() {
            return this._timezone != null || (this._timezoneStr != null && !this._timezoneStr.isEmpty());
        }
        
        public boolean hasLenient() {
            return this._lenient != null;
        }
        
        public Boolean getFeature(final Feature feature) {
            return this._features.get(feature);
        }
        
        public Features getFeatures() {
            return this._features;
        }
        
        @Override
        public String toString() {
            return String.format("JsonFormat.Value(pattern=%s,shape=%s,lenient=%s,locale=%s,timezone=%s)", this._pattern, this._shape, this._lenient, this._locale, this._timezoneStr);
        }
        
        @Override
        public int hashCode() {
            int n = (this._timezoneStr == null) ? 1 : this._timezoneStr.hashCode();
            if (this._pattern != null) {
                n ^= this._pattern.hashCode();
            }
            int n2 = n + this._shape.hashCode();
            if (this._lenient != null) {
                n2 ^= this._lenient.hashCode();
            }
            if (this._locale != null) {
                n2 += this._locale.hashCode();
            }
            return n2 ^ this._features.hashCode();
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            if (o == null) {
                return false;
            }
            if (o.getClass() != this.getClass()) {
                return false;
            }
            final Value value = (Value)o;
            return this._shape == value._shape && this._features.equals(value._features) && _equal(this._lenient, value._lenient) && _equal(this._timezoneStr, value._timezoneStr) && _equal(this._pattern, value._pattern) && _equal(this._timezone, value._timezone) && _equal(this._locale, value._locale);
        }
        
        private static <T> boolean _equal(final T t, final T t2) {
            if (t == null) {
                return t2 == null;
            }
            return t2 != null && t.equals(t2);
        }
        
        static {
            EMPTY = new Value();
        }
    }
}
