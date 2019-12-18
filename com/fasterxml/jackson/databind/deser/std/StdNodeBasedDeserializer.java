package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.databind.deser.*;
import com.fasterxml.jackson.databind.*;
import java.io.*;
import com.fasterxml.jackson.databind.jsontype.*;
import com.fasterxml.jackson.core.*;

public abstract class StdNodeBasedDeserializer<T> extends StdDeserializer<T> implements ResolvableDeserializer
{
    private static final long serialVersionUID = 1L;
    protected JsonDeserializer<Object> _treeDeserializer;
    
    protected StdNodeBasedDeserializer(final JavaType javaType) {
        super(javaType);
    }
    
    protected StdNodeBasedDeserializer(final Class<T> clazz) {
        super(clazz);
    }
    
    protected StdNodeBasedDeserializer(final StdNodeBasedDeserializer<?> stdNodeBasedDeserializer) {
        super(stdNodeBasedDeserializer);
        this._treeDeserializer = stdNodeBasedDeserializer._treeDeserializer;
    }
    
    @Override
    public void resolve(final DeserializationContext deserializationContext) throws JsonMappingException {
        this._treeDeserializer = deserializationContext.findRootValueDeserializer(deserializationContext.constructType(JsonNode.class));
    }
    
    public abstract T convert(final JsonNode p0, final DeserializationContext p1) throws IOException;
    
    @Override
    public T deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        return this.convert(this._treeDeserializer.deserialize(jsonParser, deserializationContext), deserializationContext);
    }
    
    @Override
    public Object deserializeWithType(final JsonParser jsonParser, final DeserializationContext deserializationContext, final TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
        return this.convert((JsonNode)this._treeDeserializer.deserializeWithType(jsonParser, deserializationContext, typeDeserializer), deserializationContext);
    }
}
