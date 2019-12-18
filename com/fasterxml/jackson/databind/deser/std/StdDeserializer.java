package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.databind.jsontype.*;
import java.io.*;
import com.fasterxml.jackson.core.io.*;
import com.fasterxml.jackson.core.*;
import java.util.*;
import com.fasterxml.jackson.databind.introspect.*;
import com.fasterxml.jackson.databind.cfg.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.deser.*;
import com.fasterxml.jackson.databind.util.*;
import com.fasterxml.jackson.databind.deser.impl.*;

public abstract class StdDeserializer<T> extends JsonDeserializer<T> implements Serializable
{
    private static final long serialVersionUID = 1L;
    protected static final int F_MASK_INT_COERCIONS;
    protected static final int F_MASK_ACCEPT_ARRAYS;
    protected final Class<?> _valueClass;
    
    protected StdDeserializer(final Class<?> valueClass) {
        super();
        this._valueClass = valueClass;
    }
    
    protected StdDeserializer(final JavaType javaType) {
        super();
        this._valueClass = ((javaType == null) ? Object.class : javaType.getRawClass());
    }
    
    protected StdDeserializer(final StdDeserializer<?> stdDeserializer) {
        super();
        this._valueClass = stdDeserializer._valueClass;
    }
    
    @Override
    public Class<?> handledType() {
        return this._valueClass;
    }
    
    @Deprecated
    public final Class<?> getValueClass() {
        return this._valueClass;
    }
    
    public JavaType getValueType() {
        return null;
    }
    
    protected boolean isDefaultDeserializer(final JsonDeserializer<?> jsonDeserializer) {
        return ClassUtil.isJacksonStdImpl(jsonDeserializer);
    }
    
    protected boolean isDefaultKeyDeserializer(final KeyDeserializer keyDeserializer) {
        return ClassUtil.isJacksonStdImpl(keyDeserializer);
    }
    
    @Override
    public Object deserializeWithType(final JsonParser jsonParser, final DeserializationContext deserializationContext, final TypeDeserializer typeDeserializer) throws IOException {
        return typeDeserializer.deserializeTypedFromAny(jsonParser, deserializationContext);
    }
    
    protected final boolean _parseBooleanPrimitive(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        final JsonToken currentToken = jsonParser.getCurrentToken();
        if (currentToken == JsonToken.VALUE_TRUE) {
            return true;
        }
        if (currentToken == JsonToken.VALUE_FALSE) {
            return false;
        }
        if (currentToken == JsonToken.VALUE_NULL) {
            this._verifyNullForPrimitive(deserializationContext);
            return false;
        }
        if (currentToken == JsonToken.VALUE_NUMBER_INT) {
            return this._parseBooleanFromInt(jsonParser, deserializationContext);
        }
        if (currentToken == JsonToken.VALUE_STRING) {
            final String trim = jsonParser.getText().trim();
            if ("true".equals(trim) || "True".equals(trim)) {
                return true;
            }
            if ("false".equals(trim) || "False".equals(trim)) {
                return false;
            }
            if (this._isEmptyOrTextualNull(trim)) {
                this._verifyNullForPrimitiveCoercion(deserializationContext, trim);
                return false;
            }
            return Boolean.TRUE.equals(deserializationContext.handleWeirdStringValue(this._valueClass, trim, "only \"true\" or \"false\" recognized", new Object[0]));
        }
        else {
            if (currentToken == JsonToken.START_ARRAY && deserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
                jsonParser.nextToken();
                final boolean parseBooleanPrimitive = this._parseBooleanPrimitive(jsonParser, deserializationContext);
                this._verifyEndArrayForSingle(jsonParser, deserializationContext);
                return parseBooleanPrimitive;
            }
            return (boolean)deserializationContext.handleUnexpectedToken(this._valueClass, jsonParser);
        }
    }
    
    protected boolean _parseBooleanFromInt(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        this._verifyNumberForScalarCoercion(deserializationContext, jsonParser);
        return !"0".equals(jsonParser.getText());
    }
    
    protected final byte _parseBytePrimitive(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        final int parseIntPrimitive = this._parseIntPrimitive(jsonParser, deserializationContext);
        if (this._byteOverflow(parseIntPrimitive)) {
            return this._nonNullNumber((Number)deserializationContext.handleWeirdStringValue(this._valueClass, String.valueOf(parseIntPrimitive), "overflow, value cannot be represented as 8-bit value", new Object[0])).byteValue();
        }
        return (byte)parseIntPrimitive;
    }
    
    protected final short _parseShortPrimitive(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        final int parseIntPrimitive = this._parseIntPrimitive(jsonParser, deserializationContext);
        if (this._shortOverflow(parseIntPrimitive)) {
            return this._nonNullNumber((Number)deserializationContext.handleWeirdStringValue(this._valueClass, String.valueOf(parseIntPrimitive), "overflow, value cannot be represented as 16-bit value", new Object[0])).shortValue();
        }
        return (short)parseIntPrimitive;
    }
    
    protected final int _parseIntPrimitive(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        if (jsonParser.hasToken(JsonToken.VALUE_NUMBER_INT)) {
            return jsonParser.getIntValue();
        }
        switch (jsonParser.getCurrentTokenId()) {
            case 6: {
                final String trim = jsonParser.getText().trim();
                if (this._isEmptyOrTextualNull(trim)) {
                    this._verifyNullForPrimitiveCoercion(deserializationContext, trim);
                    return 0;
                }
                return this._parseIntPrimitive(deserializationContext, trim);
            }
            case 8: {
                if (!deserializationContext.isEnabled(DeserializationFeature.ACCEPT_FLOAT_AS_INT)) {
                    this._failDoubleToIntCoercion(jsonParser, deserializationContext, "int");
                }
                return jsonParser.getValueAsInt();
            }
            case 11: {
                this._verifyNullForPrimitive(deserializationContext);
                return 0;
            }
            case 3: {
                if (deserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
                    jsonParser.nextToken();
                    final int parseIntPrimitive = this._parseIntPrimitive(jsonParser, deserializationContext);
                    this._verifyEndArrayForSingle(jsonParser, deserializationContext);
                    return parseIntPrimitive;
                }
                break;
            }
        }
        return ((Number)deserializationContext.handleUnexpectedToken(this._valueClass, jsonParser)).intValue();
    }
    
    protected final int _parseIntPrimitive(final DeserializationContext deserializationContext, final String s) throws IOException {
        try {
            if (s.length() <= 9) {
                return NumberInput.parseInt(s);
            }
            final long long1 = Long.parseLong(s);
            if (this._intOverflow(long1)) {
                return this._nonNullNumber((Number)deserializationContext.handleWeirdStringValue(this._valueClass, s, "Overflow: numeric value (%s) out of range of int (%d -%d)", s, Integer.MIN_VALUE, 0)).intValue();
            }
            return (int)long1;
        }
        catch (IllegalArgumentException ex) {
            return this._nonNullNumber((Number)deserializationContext.handleWeirdStringValue(this._valueClass, s, "not a valid int value", new Object[0])).intValue();
        }
    }
    
    protected final long _parseLongPrimitive(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        if (jsonParser.hasToken(JsonToken.VALUE_NUMBER_INT)) {
            return jsonParser.getLongValue();
        }
        switch (jsonParser.getCurrentTokenId()) {
            case 6: {
                final String trim = jsonParser.getText().trim();
                if (this._isEmptyOrTextualNull(trim)) {
                    this._verifyNullForPrimitiveCoercion(deserializationContext, trim);
                    return 0L;
                }
                return this._parseLongPrimitive(deserializationContext, trim);
            }
            case 8: {
                if (!deserializationContext.isEnabled(DeserializationFeature.ACCEPT_FLOAT_AS_INT)) {
                    this._failDoubleToIntCoercion(jsonParser, deserializationContext, "long");
                }
                return jsonParser.getValueAsLong();
            }
            case 11: {
                this._verifyNullForPrimitive(deserializationContext);
                return 0L;
            }
            case 3: {
                if (deserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
                    jsonParser.nextToken();
                    final long parseLongPrimitive = this._parseLongPrimitive(jsonParser, deserializationContext);
                    this._verifyEndArrayForSingle(jsonParser, deserializationContext);
                    return parseLongPrimitive;
                }
                break;
            }
        }
        return ((Number)deserializationContext.handleUnexpectedToken(this._valueClass, jsonParser)).longValue();
    }
    
    protected final long _parseLongPrimitive(final DeserializationContext deserializationContext, final String s) throws IOException {
        try {
            return NumberInput.parseLong(s);
        }
        catch (IllegalArgumentException ex) {
            return this._nonNullNumber((Number)deserializationContext.handleWeirdStringValue(this._valueClass, s, "not a valid long value", new Object[0])).longValue();
        }
    }
    
    protected final float _parseFloatPrimitive(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        if (jsonParser.hasToken(JsonToken.VALUE_NUMBER_FLOAT)) {
            return jsonParser.getFloatValue();
        }
        switch (jsonParser.getCurrentTokenId()) {
            case 6: {
                final String trim = jsonParser.getText().trim();
                if (this._isEmptyOrTextualNull(trim)) {
                    this._verifyNullForPrimitiveCoercion(deserializationContext, trim);
                    return 0.0f;
                }
                return this._parseFloatPrimitive(deserializationContext, trim);
            }
            case 7: {
                return jsonParser.getFloatValue();
            }
            case 11: {
                this._verifyNullForPrimitive(deserializationContext);
                return 0.0f;
            }
            case 3: {
                if (deserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
                    jsonParser.nextToken();
                    final float parseFloatPrimitive = this._parseFloatPrimitive(jsonParser, deserializationContext);
                    this._verifyEndArrayForSingle(jsonParser, deserializationContext);
                    return parseFloatPrimitive;
                }
                break;
            }
        }
        return ((Number)deserializationContext.handleUnexpectedToken(this._valueClass, jsonParser)).floatValue();
    }
    
    protected final float _parseFloatPrimitive(final DeserializationContext deserializationContext, final String s) throws IOException {
        switch (s.charAt(0)) {
            case 'I': {
                if (this._isPosInf(s)) {
                    return Float.POSITIVE_INFINITY;
                }
                break;
            }
            case 'N': {
                if (this._isNaN(s)) {
                    return Float.NaN;
                }
                break;
            }
            case '-': {
                if (this._isNegInf(s)) {
                    return Float.NEGATIVE_INFINITY;
                }
                break;
            }
        }
        try {
            return Float.parseFloat(s);
        }
        catch (IllegalArgumentException ex) {
            return this._nonNullNumber((Number)deserializationContext.handleWeirdStringValue(this._valueClass, s, "not a valid float value", new Object[0])).floatValue();
        }
    }
    
    protected final double _parseDoublePrimitive(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        if (jsonParser.hasToken(JsonToken.VALUE_NUMBER_FLOAT)) {
            return jsonParser.getDoubleValue();
        }
        switch (jsonParser.getCurrentTokenId()) {
            case 6: {
                final String trim = jsonParser.getText().trim();
                if (this._isEmptyOrTextualNull(trim)) {
                    this._verifyNullForPrimitiveCoercion(deserializationContext, trim);
                    return 0.0;
                }
                return this._parseDoublePrimitive(deserializationContext, trim);
            }
            case 7: {
                return jsonParser.getDoubleValue();
            }
            case 11: {
                this._verifyNullForPrimitive(deserializationContext);
                return 0.0;
            }
            case 3: {
                if (deserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
                    jsonParser.nextToken();
                    final double parseDoublePrimitive = this._parseDoublePrimitive(jsonParser, deserializationContext);
                    this._verifyEndArrayForSingle(jsonParser, deserializationContext);
                    return parseDoublePrimitive;
                }
                break;
            }
        }
        return ((Number)deserializationContext.handleUnexpectedToken(this._valueClass, jsonParser)).doubleValue();
    }
    
    protected final double _parseDoublePrimitive(final DeserializationContext deserializationContext, final String s) throws IOException {
        switch (s.charAt(0)) {
            case 'I': {
                if (this._isPosInf(s)) {
                    return Double.POSITIVE_INFINITY;
                }
                break;
            }
            case 'N': {
                if (this._isNaN(s)) {
                    return Double.NaN;
                }
                break;
            }
            case '-': {
                if (this._isNegInf(s)) {
                    return Double.NEGATIVE_INFINITY;
                }
                break;
            }
        }
        try {
            return parseDouble(s);
        }
        catch (IllegalArgumentException ex) {
            return this._nonNullNumber((Number)deserializationContext.handleWeirdStringValue(this._valueClass, s, "not a valid double value (as String to convert)", new Object[0])).doubleValue();
        }
    }
    
    protected Date _parseDate(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        switch (jsonParser.getCurrentTokenId()) {
            case 6: {
                return this._parseDate(jsonParser.getText().trim(), deserializationContext);
            }
            case 7: {
                long n;
                try {
                    n = jsonParser.getLongValue();
                }
                catch (JsonParseException ex) {
                    n = ((Number)deserializationContext.handleWeirdNumberValue(this._valueClass, jsonParser.getNumberValue(), "not a valid 64-bit long for creating `java.util.Date`", new Object[0])).longValue();
                }
                return new Date(n);
            }
            case 11: {
                return this.getNullValue(deserializationContext);
            }
            case 3: {
                return this._parseDateFromArray(jsonParser, deserializationContext);
            }
            default: {
                return (Date)deserializationContext.handleUnexpectedToken(this._valueClass, jsonParser);
            }
        }
    }
    
    protected Date _parseDateFromArray(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        JsonToken jsonToken;
        if (deserializationContext.hasSomeOfFeatures(StdDeserializer.F_MASK_ACCEPT_ARRAYS)) {
            jsonToken = jsonParser.nextToken();
            if (jsonToken == JsonToken.END_ARRAY && deserializationContext.isEnabled(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT)) {
                return this.getNullValue(deserializationContext);
            }
            if (deserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
                final Date parseDate = this._parseDate(jsonParser, deserializationContext);
                this._verifyEndArrayForSingle(jsonParser, deserializationContext);
                return parseDate;
            }
        }
        else {
            jsonToken = jsonParser.getCurrentToken();
        }
        return (Date)deserializationContext.handleUnexpectedToken(this._valueClass, jsonToken, jsonParser, null, new Object[0]);
    }
    
    protected Date _parseDate(final String s, final DeserializationContext deserializationContext) throws IOException {
        try {
            if (this._isEmptyOrTextualNull(s)) {
                return this.getNullValue(deserializationContext);
            }
            return deserializationContext.parseDate(s);
        }
        catch (IllegalArgumentException ex) {
            return (Date)deserializationContext.handleWeirdStringValue(this._valueClass, s, "not a valid representation (error: %s)", ex.getMessage());
        }
    }
    
    protected static final double parseDouble(final String s) throws NumberFormatException {
        if ("2.2250738585072012e-308".equals(s)) {
            return Double.MIN_NORMAL;
        }
        return Double.parseDouble(s);
    }
    
    protected final String _parseString(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        if (jsonParser.getCurrentToken() == JsonToken.VALUE_STRING) {
            return jsonParser.getText();
        }
        final String valueAsString = jsonParser.getValueAsString();
        if (valueAsString != null) {
            return valueAsString;
        }
        return (String)deserializationContext.handleUnexpectedToken(String.class, jsonParser);
    }
    
    protected T _deserializeFromEmpty(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        final JsonToken currentToken = jsonParser.getCurrentToken();
        if (currentToken == JsonToken.START_ARRAY) {
            if (deserializationContext.isEnabled(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT)) {
                if (jsonParser.nextToken() == JsonToken.END_ARRAY) {
                    return null;
                }
                return (T)deserializationContext.handleUnexpectedToken(this.handledType(), jsonParser);
            }
        }
        else if (currentToken == JsonToken.VALUE_STRING && deserializationContext.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT) && jsonParser.getText().trim().isEmpty()) {
            return null;
        }
        return (T)deserializationContext.handleUnexpectedToken(this.handledType(), jsonParser);
    }
    
    protected boolean _hasTextualNull(final String s) {
        return "null".equals(s);
    }
    
    protected boolean _isEmptyOrTextualNull(final String s) {
        return s.isEmpty() || "null".equals(s);
    }
    
    protected final boolean _isNegInf(final String s) {
        return "-Infinity".equals(s) || "-INF".equals(s);
    }
    
    protected final boolean _isPosInf(final String s) {
        return "Infinity".equals(s) || "INF".equals(s);
    }
    
    protected final boolean _isNaN(final String s) {
        return "NaN".equals(s);
    }
    
    protected T _deserializeFromArray(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        JsonToken jsonToken;
        if (deserializationContext.hasSomeOfFeatures(StdDeserializer.F_MASK_ACCEPT_ARRAYS)) {
            jsonToken = jsonParser.nextToken();
            if (jsonToken == JsonToken.END_ARRAY && deserializationContext.isEnabled(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT)) {
                return this.getNullValue(deserializationContext);
            }
            if (deserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
                final T deserialize = this.deserialize(jsonParser, deserializationContext);
                if (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    this.handleMissingEndArrayForSingle(jsonParser, deserializationContext);
                }
                return deserialize;
            }
        }
        else {
            jsonToken = jsonParser.getCurrentToken();
        }
        return (T)deserializationContext.handleUnexpectedToken(this._valueClass, jsonToken, jsonParser, null, new Object[0]);
    }
    
    protected T _deserializeWrappedValue(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        if (jsonParser.hasToken(JsonToken.START_ARRAY)) {
            return (T)deserializationContext.handleUnexpectedToken(this._valueClass, jsonParser.getCurrentToken(), jsonParser, String.format("Cannot deserialize instance of %s out of %s token: nested Arrays not allowed with %s", ClassUtil.nameOf(this._valueClass), JsonToken.START_ARRAY, "DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS"), new Object[0]);
        }
        return this.deserialize(jsonParser, deserializationContext);
    }
    
    protected void _failDoubleToIntCoercion(final JsonParser jsonParser, final DeserializationContext deserializationContext, final String s) throws IOException {
        deserializationContext.reportInputMismatch(this.handledType(), "Cannot coerce a floating-point value ('%s') into %s (enable `DeserializationFeature.ACCEPT_FLOAT_AS_INT` to allow)", jsonParser.getValueAsString(), s);
    }
    
    protected Object _coerceIntegral(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        final int deserializationFeatures = deserializationContext.getDeserializationFeatures();
        if (DeserializationFeature.USE_BIG_INTEGER_FOR_INTS.enabledIn(deserializationFeatures)) {
            return jsonParser.getBigIntegerValue();
        }
        if (DeserializationFeature.USE_LONG_FOR_INTS.enabledIn(deserializationFeatures)) {
            return jsonParser.getLongValue();
        }
        return jsonParser.getBigIntegerValue();
    }
    
    protected Object _coerceNullToken(final DeserializationContext deserializationContext, final boolean b) throws JsonMappingException {
        if (b) {
            this._verifyNullForPrimitive(deserializationContext);
        }
        return this.getNullValue(deserializationContext);
    }
    
    protected Object _coerceTextualNull(final DeserializationContext deserializationContext, final boolean b) throws JsonMappingException {
        ConfigFeature configFeature;
        boolean b2;
        if (!deserializationContext.isEnabled(MapperFeature.ALLOW_COERCION_OF_SCALARS)) {
            configFeature = MapperFeature.ALLOW_COERCION_OF_SCALARS;
            b2 = true;
        }
        else {
            if (!b || !deserializationContext.isEnabled(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)) {
                return this.getNullValue(deserializationContext);
            }
            configFeature = DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES;
            b2 = false;
        }
        this._reportFailedNullCoerce(deserializationContext, b2, (Enum<?>)configFeature, "String \"null\"");
        return null;
    }
    
    protected Object _coerceEmptyString(final DeserializationContext deserializationContext, final boolean b) throws JsonMappingException {
        ConfigFeature configFeature;
        boolean b2;
        if (!deserializationContext.isEnabled(MapperFeature.ALLOW_COERCION_OF_SCALARS)) {
            configFeature = MapperFeature.ALLOW_COERCION_OF_SCALARS;
            b2 = true;
        }
        else {
            if (!b || !deserializationContext.isEnabled(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)) {
                return this.getNullValue(deserializationContext);
            }
            configFeature = DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES;
            b2 = false;
        }
        this._reportFailedNullCoerce(deserializationContext, b2, (Enum<?>)configFeature, "empty String (\"\")");
        return null;
    }
    
    protected final void _verifyNullForPrimitive(final DeserializationContext deserializationContext) throws JsonMappingException {
        if (deserializationContext.isEnabled(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)) {
            deserializationContext.reportInputMismatch(this, "Cannot coerce `null` %s (disable `DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES` to allow)", this._coercedTypeDesc());
        }
    }
    
    protected final void _verifyNullForPrimitiveCoercion(final DeserializationContext deserializationContext, final String s) throws JsonMappingException {
        ConfigFeature configFeature;
        boolean b;
        if (!deserializationContext.isEnabled(MapperFeature.ALLOW_COERCION_OF_SCALARS)) {
            configFeature = MapperFeature.ALLOW_COERCION_OF_SCALARS;
            b = true;
        }
        else {
            if (!deserializationContext.isEnabled(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)) {
                return;
            }
            configFeature = DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES;
            b = false;
        }
        this._reportFailedNullCoerce(deserializationContext, b, (Enum<?>)configFeature, s.isEmpty() ? "empty String (\"\")" : String.format("String \"%s\"", s));
    }
    
    protected final void _verifyNullForScalarCoercion(final DeserializationContext deserializationContext, final String s) throws JsonMappingException {
        if (!deserializationContext.isEnabled(MapperFeature.ALLOW_COERCION_OF_SCALARS)) {
            this._reportFailedNullCoerce(deserializationContext, true, MapperFeature.ALLOW_COERCION_OF_SCALARS, s.isEmpty() ? "empty String (\"\")" : String.format("String \"%s\"", s));
        }
    }
    
    protected void _verifyStringForScalarCoercion(final DeserializationContext deserializationContext, final String s) throws JsonMappingException {
        final MapperFeature allow_COERCION_OF_SCALARS = MapperFeature.ALLOW_COERCION_OF_SCALARS;
        if (!deserializationContext.isEnabled(allow_COERCION_OF_SCALARS)) {
            deserializationContext.reportInputMismatch(this, "Cannot coerce String \"%s\" %s (enable `%s.%s` to allow)", s, this._coercedTypeDesc(), allow_COERCION_OF_SCALARS.getClass().getSimpleName(), allow_COERCION_OF_SCALARS.name());
        }
    }
    
    protected void _verifyNumberForScalarCoercion(final DeserializationContext deserializationContext, final JsonParser jsonParser) throws IOException {
        final MapperFeature allow_COERCION_OF_SCALARS = MapperFeature.ALLOW_COERCION_OF_SCALARS;
        if (!deserializationContext.isEnabled(allow_COERCION_OF_SCALARS)) {
            deserializationContext.reportInputMismatch(this, "Cannot coerce Number (%s) %s (enable `%s.%s` to allow)", jsonParser.getText(), this._coercedTypeDesc(), allow_COERCION_OF_SCALARS.getClass().getSimpleName(), allow_COERCION_OF_SCALARS.name());
        }
    }
    
    protected void _reportFailedNullCoerce(final DeserializationContext deserializationContext, final boolean b, final Enum<?> enum1, final String s) throws JsonMappingException {
        deserializationContext.reportInputMismatch(this, "Cannot coerce %s to Null value %s (%s `%s.%s` to allow)", s, this._coercedTypeDesc(), b ? "enable" : "disable", enum1.getClass().getSimpleName(), enum1.name());
    }
    
    protected String _coercedTypeDesc() {
        final JavaType valueType = this.getValueType();
        boolean b;
        String s;
        if (valueType != null && !valueType.isPrimitive()) {
            b = (valueType.isContainerType() || valueType.isReferenceType());
            s = "'" + valueType.toString() + "'";
        }
        else {
            final Class<?> handledType = this.handledType();
            b = (handledType.isArray() || Collection.class.isAssignableFrom(handledType) || Map.class.isAssignableFrom(handledType));
            s = ClassUtil.nameOf(handledType);
        }
        if (b) {
            return "as content of type " + s;
        }
        return "for type " + s;
    }
    
    protected JsonDeserializer<Object> findDeserializer(final DeserializationContext deserializationContext, final JavaType javaType, final BeanProperty beanProperty) throws JsonMappingException {
        return deserializationContext.findContextualValueDeserializer(javaType, beanProperty);
    }
    
    protected final boolean _isIntNumber(final String s) {
        final int length = s.length();
        if (length > 0) {
            final char char1 = s.charAt(0);
            for (int i = (char1 == '-' || char1 == '+') ? 1 : 0; i < length; ++i) {
                final char char2 = s.charAt(i);
                if (char2 > '9' || char2 < '0') {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    protected JsonDeserializer<?> findConvertingContentDeserializer(final DeserializationContext deserializationContext, final BeanProperty beanProperty, JsonDeserializer<?> contextualValueDeserializer) throws JsonMappingException {
        final AnnotationIntrospector annotationIntrospector = deserializationContext.getAnnotationIntrospector();
        if (_neitherNull(annotationIntrospector, beanProperty)) {
            final AnnotatedMember member = beanProperty.getMember();
            if (member != null) {
                final Object deserializationContentConverter = annotationIntrospector.findDeserializationContentConverter(member);
                if (deserializationContentConverter != null) {
                    final Converter<Object, Object> converterInstance = deserializationContext.converterInstance(beanProperty.getMember(), deserializationContentConverter);
                    final JavaType inputType = converterInstance.getInputType(deserializationContext.getTypeFactory());
                    if (contextualValueDeserializer == null) {
                        contextualValueDeserializer = deserializationContext.findContextualValueDeserializer(inputType, beanProperty);
                    }
                    return new StdDelegatingDeserializer<Object>(converterInstance, inputType, contextualValueDeserializer);
                }
            }
        }
        return contextualValueDeserializer;
    }
    
    protected JsonFormat.Value findFormatOverrides(final DeserializationContext deserializationContext, final BeanProperty beanProperty, final Class<?> clazz) {
        if (beanProperty != null) {
            return beanProperty.findPropertyFormat(deserializationContext.getConfig(), clazz);
        }
        return deserializationContext.getDefaultPropertyFormat(clazz);
    }
    
    protected Boolean findFormatFeature(final DeserializationContext deserializationContext, final BeanProperty beanProperty, final Class<?> clazz, final JsonFormat.Feature feature) {
        final JsonFormat.Value formatOverrides = this.findFormatOverrides(deserializationContext, beanProperty, clazz);
        if (formatOverrides != null) {
            return formatOverrides.getFeature(feature);
        }
        return null;
    }
    
    protected final NullValueProvider findValueNullProvider(final DeserializationContext deserializationContext, final SettableBeanProperty settableBeanProperty, final PropertyMetadata propertyMetadata) throws JsonMappingException {
        if (settableBeanProperty != null) {
            return this._findNullProvider(deserializationContext, settableBeanProperty, propertyMetadata.getValueNulls(), settableBeanProperty.getValueDeserializer());
        }
        return null;
    }
    
    protected NullValueProvider findContentNullProvider(final DeserializationContext deserializationContext, final BeanProperty beanProperty, final JsonDeserializer<?> jsonDeserializer) throws JsonMappingException {
        final Nulls contentNullStyle = this.findContentNullStyle(deserializationContext, beanProperty);
        if (contentNullStyle == Nulls.SKIP) {
            return NullsConstantProvider.skipper();
        }
        final NullValueProvider findNullProvider = this._findNullProvider(deserializationContext, beanProperty, contentNullStyle, jsonDeserializer);
        if (findNullProvider != null) {
            return findNullProvider;
        }
        return jsonDeserializer;
    }
    
    protected Nulls findContentNullStyle(final DeserializationContext deserializationContext, final BeanProperty beanProperty) throws JsonMappingException {
        if (beanProperty != null) {
            return beanProperty.getMetadata().getContentNulls();
        }
        return null;
    }
    
    protected final NullValueProvider _findNullProvider(final DeserializationContext deserializationContext, final BeanProperty beanProperty, final Nulls nulls, final JsonDeserializer<?> jsonDeserializer) throws JsonMappingException {
        if (nulls == Nulls.FAIL) {
            if (beanProperty == null) {
                return NullsFailProvider.constructForRootValue(deserializationContext.constructType(jsonDeserializer.handledType()));
            }
            return NullsFailProvider.constructForProperty(beanProperty);
        }
        else if (nulls == Nulls.AS_EMPTY) {
            if (jsonDeserializer == null) {
                return null;
            }
            if (jsonDeserializer instanceof BeanDeserializerBase && !((BeanDeserializerBase)jsonDeserializer).getValueInstantiator().canCreateUsingDefault()) {
                final JavaType type = beanProperty.getType();
                deserializationContext.reportBadDefinition(type, String.format("Cannot create empty instance of %s, no default Creator", type));
            }
            final AccessPattern emptyAccessPattern = jsonDeserializer.getEmptyAccessPattern();
            if (emptyAccessPattern == AccessPattern.ALWAYS_NULL) {
                return NullsConstantProvider.nuller();
            }
            if (emptyAccessPattern == AccessPattern.CONSTANT) {
                return NullsConstantProvider.forValue(jsonDeserializer.getEmptyValue(deserializationContext));
            }
            return new NullsAsEmptyProvider(jsonDeserializer);
        }
        else {
            if (nulls == Nulls.SKIP) {
                return NullsConstantProvider.skipper();
            }
            return null;
        }
    }
    
    protected void handleUnknownProperty(final JsonParser jsonParser, final DeserializationContext deserializationContext, Object handledType, final String s) throws IOException {
        if (handledType == null) {
            handledType = this.handledType();
        }
        if (deserializationContext.handleUnknownProperty(jsonParser, this, handledType, s)) {
            return;
        }
        jsonParser.skipChildren();
    }
    
    protected void handleMissingEndArrayForSingle(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        deserializationContext.reportWrongTokenException(this, JsonToken.END_ARRAY, "Attempted to unwrap '%s' value from an array (with `DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS`) but it contains more than one value", this.handledType().getName());
    }
    
    protected void _verifyEndArrayForSingle(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        if (jsonParser.nextToken() != JsonToken.END_ARRAY) {
            this.handleMissingEndArrayForSingle(jsonParser, deserializationContext);
        }
    }
    
    protected static final boolean _neitherNull(final Object o, final Object o2) {
        return o != null && o2 != null;
    }
    
    protected final boolean _byteOverflow(final int n) {
        return n < -128 || n > 255;
    }
    
    protected final boolean _shortOverflow(final int n) {
        return n < -32768 || n > 32767;
    }
    
    protected final boolean _intOverflow(final long n) {
        return n < -2147483648L || n > 0L;
    }
    
    protected Number _nonNullNumber(Number value) {
        if (value == null) {
            value = 0;
        }
        return value;
    }
    
    static {
        F_MASK_INT_COERCIONS = (DeserializationFeature.USE_BIG_INTEGER_FOR_INTS.getMask() | DeserializationFeature.USE_LONG_FOR_INTS.getMask());
        F_MASK_ACCEPT_ARRAYS = (DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS.getMask() | DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT.getMask());
    }
}
