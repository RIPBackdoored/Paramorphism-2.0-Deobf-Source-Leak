package com.fasterxml.jackson.databind.node;

import java.math.*;
import com.fasterxml.jackson.core.io.*;
import com.fasterxml.jackson.databind.*;
import java.io.*;
import com.fasterxml.jackson.core.*;

public class IntNode extends NumericNode
{
    static final int MIN_CANONICAL = -1;
    static final int MAX_CANONICAL = 10;
    private static final IntNode[] CANONICALS;
    protected final int _value;
    
    public IntNode(final int value) {
        super();
        this._value = value;
    }
    
    public static IntNode valueOf(final int n) {
        if (n > 10 || n < -1) {
            return new IntNode(n);
        }
        return IntNode.CANONICALS[n + 1];
    }
    
    @Override
    public JsonToken asToken() {
        return JsonToken.VALUE_NUMBER_INT;
    }
    
    @Override
    public JsonParser.NumberType numberType() {
        return JsonParser.NumberType.INT;
    }
    
    @Override
    public boolean isIntegralNumber() {
        return true;
    }
    
    @Override
    public boolean isInt() {
        return true;
    }
    
    @Override
    public boolean canConvertToInt() {
        return true;
    }
    
    @Override
    public boolean canConvertToLong() {
        return true;
    }
    
    @Override
    public Number numberValue() {
        return this._value;
    }
    
    @Override
    public short shortValue() {
        return (short)this._value;
    }
    
    @Override
    public int intValue() {
        return this._value;
    }
    
    @Override
    public long longValue() {
        return this._value;
    }
    
    @Override
    public float floatValue() {
        return (float)this._value;
    }
    
    @Override
    public double doubleValue() {
        return this._value;
    }
    
    @Override
    public BigDecimal decimalValue() {
        return BigDecimal.valueOf(this._value);
    }
    
    @Override
    public BigInteger bigIntegerValue() {
        return BigInteger.valueOf(this._value);
    }
    
    @Override
    public String asText() {
        return NumberOutput.toString(this._value);
    }
    
    @Override
    public boolean asBoolean(final boolean b) {
        return this._value != 0;
    }
    
    @Override
    public final void serialize(final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeNumber(this._value);
    }
    
    @Override
    public boolean equals(final Object o) {
        return o == this || (o != null && o instanceof IntNode && ((IntNode)o)._value == this._value);
    }
    
    @Override
    public int hashCode() {
        return this._value;
    }
    
    static {
        final int n = 12;
        CANONICALS = new IntNode[n];
        for (int i = 0; i < n; ++i) {
            IntNode.CANONICALS[i] = new IntNode(-1 + i);
        }
    }
}
