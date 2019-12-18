package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.databind.*;
import java.io.*;
import com.fasterxml.jackson.databind.jsontype.*;
import com.fasterxml.jackson.core.*;

@JacksonStdImpl
public class StringDeserializer extends StdScalarDeserializer<String>
{
    private static final long serialVersionUID = 1L;
    public static final StringDeserializer instance;
    
    public StringDeserializer() {
        super(String.class);
    }
    
    @Override
    public boolean isCachable() {
        return true;
    }
    
    @Override
    public Object getEmptyValue(final DeserializationContext deserializationContext) throws JsonMappingException {
        return "";
    }
    
    @Override
    public String deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        if (jsonParser.hasToken(JsonToken.VALUE_STRING)) {
            return jsonParser.getText();
        }
        final JsonToken currentToken = jsonParser.getCurrentToken();
        if (currentToken == JsonToken.START_ARRAY) {
            return this._deserializeFromArray(jsonParser, deserializationContext);
        }
        if (currentToken != JsonToken.VALUE_EMBEDDED_OBJECT) {
            if (currentToken.isScalarValue()) {
                final String valueAsString = jsonParser.getValueAsString();
                if (valueAsString != null) {
                    return valueAsString;
                }
            }
            return (String)deserializationContext.handleUnexpectedToken(this._valueClass, jsonParser);
        }
        final Object embeddedObject = jsonParser.getEmbeddedObject();
        if (embeddedObject == null) {
            return null;
        }
        if (embeddedObject instanceof byte[]) {
            return deserializationContext.getBase64Variant().encode((byte[])embeddedObject, false);
        }
        return embeddedObject.toString();
    }
    
    @Override
    public String deserializeWithType(final JsonParser jsonParser, final DeserializationContext deserializationContext, final TypeDeserializer typeDeserializer) throws IOException {
        return this.deserialize(jsonParser, deserializationContext);
    }
    
    @Override
    public Object deserializeWithType(final JsonParser jsonParser, final DeserializationContext deserializationContext, final TypeDeserializer typeDeserializer) throws IOException {
        return this.deserializeWithType(jsonParser, deserializationContext, typeDeserializer);
    }
    
    @Override
    public Object deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return this.deserialize(jsonParser, deserializationContext);
    }
    
    static {
        instance = new StringDeserializer();
    }
}
