package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.databind.*;
import java.io.*;
import com.fasterxml.jackson.core.*;
import java.util.*;

public class BinaryNode extends ValueNode
{
    static final BinaryNode EMPTY_BINARY_NODE;
    protected final byte[] _data;
    
    public BinaryNode(final byte[] data) {
        super();
        this._data = data;
    }
    
    public BinaryNode(final byte[] data, final int n, final int n2) {
        super();
        if (n == 0 && n2 == data.length) {
            this._data = data;
        }
        else {
            System.arraycopy(data, n, this._data = new byte[n2], 0, n2);
        }
    }
    
    public static BinaryNode valueOf(final byte[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return BinaryNode.EMPTY_BINARY_NODE;
        }
        return new BinaryNode(array);
    }
    
    public static BinaryNode valueOf(final byte[] array, final int n, final int n2) {
        if (array == null) {
            return null;
        }
        if (n2 == 0) {
            return BinaryNode.EMPTY_BINARY_NODE;
        }
        return new BinaryNode(array, n, n2);
    }
    
    @Override
    public JsonNodeType getNodeType() {
        return JsonNodeType.BINARY;
    }
    
    @Override
    public JsonToken asToken() {
        return JsonToken.VALUE_EMBEDDED_OBJECT;
    }
    
    @Override
    public byte[] binaryValue() {
        return this._data;
    }
    
    @Override
    public String asText() {
        return Base64Variants.getDefaultVariant().encode(this._data, false);
    }
    
    @Override
    public final void serialize(final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeBinary(serializerProvider.getConfig().getBase64Variant(), this._data, 0, this._data.length);
    }
    
    @Override
    public boolean equals(final Object o) {
        return o == this || (o != null && o instanceof BinaryNode && Arrays.equals(((BinaryNode)o)._data, this._data));
    }
    
    @Override
    public int hashCode() {
        return (this._data == null) ? -1 : this._data.length;
    }
    
    @Override
    public String toString() {
        return Base64Variants.getDefaultVariant().encode(this._data, true);
    }
    
    static {
        EMPTY_BINARY_NODE = new BinaryNode(new byte[0]);
    }
}
