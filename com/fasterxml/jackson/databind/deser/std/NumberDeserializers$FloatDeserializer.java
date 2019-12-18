package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.databind.annotation.*;
import java.io.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.util.*;
import com.fasterxml.jackson.core.*;

@JacksonStdImpl
public static class FloatDeserializer extends PrimitiveOrWrapperDeserializer<Float>
{
    private static final long serialVersionUID = 1L;
    static final FloatDeserializer primitiveInstance;
    static final FloatDeserializer wrapperInstance;
    
    public FloatDeserializer(final Class<Float> clazz, final Float n) {
        super(clazz, n, 0.0f);
    }
    
    @Override
    public Float deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        return this._parseFloat(jsonParser, deserializationContext);
    }
    
    protected final Float _parseFloat(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        final JsonToken currentToken = jsonParser.getCurrentToken();
        if (currentToken == JsonToken.VALUE_NUMBER_FLOAT || currentToken == JsonToken.VALUE_NUMBER_INT) {
            return jsonParser.getFloatValue();
        }
        if (currentToken == JsonToken.VALUE_STRING) {
            final String trim = jsonParser.getText().trim();
            if (trim.length() == 0) {
                return (Float)this._coerceEmptyString(deserializationContext, this._primitive);
            }
            if (this._hasTextualNull(trim)) {
                return (Float)this._coerceTextualNull(deserializationContext, this._primitive);
            }
            switch (trim.charAt(0)) {
                case 'I': {
                    if (this._isPosInf(trim)) {
                        return Float.POSITIVE_INFINITY;
                    }
                    break;
                }
                case 'N': {
                    if (this._isNaN(trim)) {
                        return Float.NaN;
                    }
                    break;
                }
                case '-': {
                    if (this._isNegInf(trim)) {
                        return Float.NEGATIVE_INFINITY;
                    }
                    break;
                }
            }
            this._verifyStringForScalarCoercion(deserializationContext, trim);
            try {
                return Float.parseFloat(trim);
            }
            catch (IllegalArgumentException ex) {
                return (Float)deserializationContext.handleWeirdStringValue(this._valueClass, trim, "not a valid Float value", new Object[0]);
            }
        }
        if (currentToken == JsonToken.VALUE_NULL) {
            return (Float)this._coerceNullToken(deserializationContext, this._primitive);
        }
        if (currentToken == JsonToken.START_ARRAY) {
            return this._deserializeFromArray(jsonParser, deserializationContext);
        }
        return (Float)deserializationContext.handleUnexpectedToken(this._valueClass, jsonParser);
    }
    
    @Override
    public Object getEmptyValue(final DeserializationContext deserializationContext) throws JsonMappingException {
        return super.getEmptyValue(deserializationContext);
    }
    
    @Override
    public AccessPattern getNullAccessPattern() {
        return super.getNullAccessPattern();
    }
    
    @Override
    public Object deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return this.deserialize(jsonParser, deserializationContext);
    }
    
    static {
        primitiveInstance = new FloatDeserializer(Float.TYPE, 0.0f);
        wrapperInstance = new FloatDeserializer(Float.class, null);
    }
}
