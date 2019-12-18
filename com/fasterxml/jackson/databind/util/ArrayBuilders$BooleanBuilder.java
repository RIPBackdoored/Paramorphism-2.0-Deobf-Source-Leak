package com.fasterxml.jackson.databind.util;

public static final class BooleanBuilder extends PrimitiveArrayBuilder<boolean[]>
{
    public BooleanBuilder() {
        super();
    }
    
    public final boolean[] _constructArray(final int n) {
        return new boolean[n];
    }
    
    public Object _constructArray(final int n) {
        return this._constructArray(n);
    }
}
