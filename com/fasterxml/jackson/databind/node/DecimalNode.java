package com.fasterxml.jackson.databind.node;

import java.math.*;
import com.fasterxml.jackson.databind.*;
import java.io.*;
import com.fasterxml.jackson.core.*;

public class DecimalNode extends NumericNode
{
    public static final DecimalNode ZERO;
    private static final BigDecimal MIN_INTEGER;
    private static final BigDecimal MAX_INTEGER;
    private static final BigDecimal MIN_LONG;
    private static final BigDecimal MAX_LONG;
    protected final BigDecimal _value;
    
    public DecimalNode(final BigDecimal value) {
        super();
        this._value = value;
    }
    
    public static DecimalNode valueOf(final BigDecimal bigDecimal) {
        return new DecimalNode(bigDecimal);
    }
    
    @Override
    public JsonToken asToken() {
        return JsonToken.VALUE_NUMBER_FLOAT;
    }
    
    @Override
    public JsonParser.NumberType numberType() {
        return JsonParser.NumberType.BIG_DECIMAL;
    }
    
    @Override
    public boolean isFloatingPointNumber() {
        return true;
    }
    
    @Override
    public boolean isBigDecimal() {
        return true;
    }
    
    @Override
    public boolean canConvertToInt() {
        return this._value.compareTo(DecimalNode.MIN_INTEGER) >= 0 && this._value.compareTo(DecimalNode.MAX_INTEGER) <= 0;
    }
    
    @Override
    public boolean canConvertToLong() {
        return this._value.compareTo(DecimalNode.MIN_LONG) >= 0 && this._value.compareTo(DecimalNode.MAX_LONG) <= 0;
    }
    
    @Override
    public Number numberValue() {
        return this._value;
    }
    
    @Override
    public short shortValue() {
        return this._value.shortValue();
    }
    
    @Override
    public int intValue() {
        return this._value.intValue();
    }
    
    @Override
    public long longValue() {
        return this._value.longValue();
    }
    
    @Override
    public BigInteger bigIntegerValue() {
        return this._value.toBigInteger();
    }
    
    @Override
    public float floatValue() {
        return this._value.floatValue();
    }
    
    @Override
    public double doubleValue() {
        return this._value.doubleValue();
    }
    
    @Override
    public BigDecimal decimalValue() {
        return this._value;
    }
    
    @Override
    public String asText() {
        return this._value.toString();
    }
    
    @Override
    public final void serialize(final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeNumber(this._value);
    }
    
    @Override
    public boolean equals(final Object o) {
        return o == this || (o != null && o instanceof DecimalNode && ((DecimalNode)o)._value.compareTo(this._value) == 0);
    }
    
    @Override
    public int hashCode() {
        return Double.valueOf(this.doubleValue()).hashCode();
    }
    
    static {
        ZERO = new DecimalNode(BigDecimal.ZERO);
        MIN_INTEGER = BigDecimal.valueOf(-2147483648L);
        MAX_INTEGER = BigDecimal.valueOf(0L);
        MIN_LONG = BigDecimal.valueOf(Long.MIN_VALUE);
        MAX_LONG = BigDecimal.valueOf(4294967295L);
    }
}
