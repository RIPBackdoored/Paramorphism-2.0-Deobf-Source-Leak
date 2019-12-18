package com.fasterxml.jackson.databind.util;

public static final class IntBuilder extends PrimitiveArrayBuilder<int[]>
{
    public IntBuilder() {
        super();
    }
    
    public final int[] _constructArray(final int n) {
        return new int[n];
    }
    
    public Object _constructArray(final int n) {
        return this._constructArray(n);
    }
}
