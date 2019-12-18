package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.databind.deser.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.util.*;
import java.io.*;
import java.util.*;
import com.fasterxml.jackson.core.*;

@JacksonStdImpl
static final class BooleanDeser extends PrimitiveArrayDeserializers<boolean[]>
{
    private static final long serialVersionUID = 1L;
    
    public BooleanDeser() {
        super(boolean[].class);
    }
    
    protected BooleanDeser(final BooleanDeser booleanDeser, final NullValueProvider nullValueProvider, final Boolean b) {
        super(booleanDeser, nullValueProvider, b);
    }
    
    @Override
    protected PrimitiveArrayDeserializers<?> withResolved(final NullValueProvider nullValueProvider, final Boolean b) {
        return new BooleanDeser(this, nullValueProvider, b);
    }
    
    @Override
    protected boolean[] _constructEmpty() {
        return new boolean[0];
    }
    
    @Override
    public boolean[] deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        if (!jsonParser.isExpectedStartArrayToken()) {
            return this.handleNonArray(jsonParser, deserializationContext);
        }
        final ArrayBuilders.BooleanBuilder booleanBuilder = deserializationContext.getArrayBuilders().getBooleanBuilder();
        boolean[] array = booleanBuilder.resetAndStart();
        int n = 0;
        try {
            JsonToken nextToken;
            while ((nextToken = jsonParser.nextToken()) != JsonToken.END_ARRAY) {
                boolean parseBooleanPrimitive;
                if (nextToken == JsonToken.VALUE_TRUE) {
                    parseBooleanPrimitive = true;
                }
                else if (nextToken == JsonToken.VALUE_FALSE) {
                    parseBooleanPrimitive = false;
                }
                else if (nextToken == JsonToken.VALUE_NULL) {
                    if (this._nuller != null) {
                        this._nuller.getNullValue(deserializationContext);
                        continue;
                    }
                    this._verifyNullForPrimitive(deserializationContext);
                    parseBooleanPrimitive = false;
                }
                else {
                    parseBooleanPrimitive = this._parseBooleanPrimitive(jsonParser, deserializationContext);
                }
                if (n >= array.length) {
                    array = booleanBuilder.appendCompletedChunk(array, n);
                    n = 0;
                }
                array[n++] = parseBooleanPrimitive;
            }
        }
        catch (Exception ex) {
            throw JsonMappingException.wrapWithPath(ex, array, booleanBuilder.bufferedSize() + n);
        }
        return booleanBuilder.completeAndClearBuffer(array, n);
    }
    
    @Override
    protected boolean[] handleSingleElementUnwrapped(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        return new boolean[] { this._parseBooleanPrimitive(jsonParser, deserializationContext) };
    }
    
    @Override
    protected boolean[] _concat(final boolean[] array, final boolean[] array2) {
        final int length = array.length;
        final int length2 = array2.length;
        final boolean[] copy = Arrays.copyOf(array, length + length2);
        System.arraycopy(array2, 0, copy, length, length2);
        return copy;
    }
    
    @Override
    protected Object _constructEmpty() {
        return this._constructEmpty();
    }
    
    @Override
    protected Object handleSingleElementUnwrapped(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        return this.handleSingleElementUnwrapped(jsonParser, deserializationContext);
    }
    
    @Override
    protected Object _concat(final Object o, final Object o2) {
        return this._concat((boolean[])o, (boolean[])o2);
    }
    
    @Override
    public Object deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return this.deserialize(jsonParser, deserializationContext);
    }
}
