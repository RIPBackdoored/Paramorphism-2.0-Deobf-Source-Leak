package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.databind.*;
import java.io.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.jsontype.*;

public final class MissingNode extends ValueNode
{
    private static final MissingNode instance;
    
    protected MissingNode() {
        super();
    }
    
    @Override
    public boolean isMissingNode() {
        return true;
    }
    
    @Override
    public <T extends JsonNode> T deepCopy() {
        return (T)this;
    }
    
    public static MissingNode getInstance() {
        return MissingNode.instance;
    }
    
    @Override
    public JsonNodeType getNodeType() {
        return JsonNodeType.MISSING;
    }
    
    @Override
    public JsonToken asToken() {
        return JsonToken.NOT_AVAILABLE;
    }
    
    @Override
    public String asText() {
        return "";
    }
    
    @Override
    public String asText(final String s) {
        return s;
    }
    
    @Override
    public final void serialize(final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeNull();
    }
    
    @Override
    public void serializeWithType(final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider, final TypeSerializer typeSerializer) throws IOException, JsonProcessingException {
        jsonGenerator.writeNull();
    }
    
    @Override
    public boolean equals(final Object o) {
        return o == this;
    }
    
    @Override
    public String toString() {
        return "";
    }
    
    @Override
    public int hashCode() {
        return JsonNodeType.MISSING.ordinal();
    }
    
    static {
        instance = new MissingNode();
    }
}
