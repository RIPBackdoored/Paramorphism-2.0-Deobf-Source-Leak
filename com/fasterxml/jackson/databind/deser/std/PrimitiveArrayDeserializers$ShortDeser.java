package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.databind.deser.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.util.*;
import java.io.*;
import java.util.*;
import com.fasterxml.jackson.core.*;

@JacksonStdImpl
static final class ShortDeser extends PrimitiveArrayDeserializers<short[]>
{
    private static final long serialVersionUID = 1L;
    
    public ShortDeser() {
        super(short[].class);
    }
    
    protected ShortDeser(final ShortDeser shortDeser, final NullValueProvider nullValueProvider, final Boolean b) {
        super(shortDeser, nullValueProvider, b);
    }
    
    @Override
    protected PrimitiveArrayDeserializers<?> withResolved(final NullValueProvider nullValueProvider, final Boolean b) {
        return new ShortDeser(this, nullValueProvider, b);
    }
    
    @Override
    protected short[] _constructEmpty() {
        return new short[0];
    }
    
    @Override
    public short[] deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        if (!jsonParser.isExpectedStartArrayToken()) {
            return this.handleNonArray(jsonParser, deserializationContext);
        }
        final ArrayBuilders.ShortBuilder shortBuilder = deserializationContext.getArrayBuilders().getShortBuilder();
        short[] array = shortBuilder.resetAndStart();
        int n = 0;
        try {
            JsonToken nextToken;
            while ((nextToken = jsonParser.nextToken()) != JsonToken.END_ARRAY) {
                short parseShortPrimitive;
                if (nextToken == JsonToken.VALUE_NULL) {
                    if (this._nuller != null) {
                        this._nuller.getNullValue(deserializationContext);
                        continue;
                    }
                    this._verifyNullForPrimitive(deserializationContext);
                    parseShortPrimitive = 0;
                }
                else {
                    parseShortPrimitive = this._parseShortPrimitive(jsonParser, deserializationContext);
                }
                if (n >= array.length) {
                    array = shortBuilder.appendCompletedChunk(array, n);
                    n = 0;
                }
                array[n++] = parseShortPrimitive;
            }
        }
        catch (Exception ex) {
            throw JsonMappingException.wrapWithPath(ex, array, shortBuilder.bufferedSize() + n);
        }
        return shortBuilder.completeAndClearBuffer(array, n);
    }
    
    @Override
    protected short[] handleSingleElementUnwrapped(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        return new short[] { this._parseShortPrimitive(jsonParser, deserializationContext) };
    }
    
    @Override
    protected short[] _concat(final short[] array, final short[] array2) {
        final int length = array.length;
        final int length2 = array2.length;
        final short[] copy = Arrays.copyOf(array, length + length2);
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
        return this._concat((short[])o, (short[])o2);
    }
    
    @Override
    public Object deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return this.deserialize(jsonParser, deserializationContext);
    }
}
