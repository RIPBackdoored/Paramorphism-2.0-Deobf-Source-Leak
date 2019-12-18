package com.fasterxml.jackson.core.filter;

import com.fasterxml.jackson.core.*;
import java.io.*;
import java.math.*;

public class TokenFilter
{
    public static final TokenFilter INCLUDE_ALL;
    
    protected TokenFilter() {
        super();
    }
    
    public TokenFilter filterStartObject() {
        return this;
    }
    
    public TokenFilter filterStartArray() {
        return this;
    }
    
    public void filterFinishObject() {
    }
    
    public void filterFinishArray() {
    }
    
    public TokenFilter includeProperty(final String s) {
        return this;
    }
    
    public TokenFilter includeElement(final int n) {
        return this;
    }
    
    public TokenFilter includeRootValue(final int n) {
        return this;
    }
    
    public boolean includeValue(final JsonParser jsonParser) throws IOException {
        return this._includeScalar();
    }
    
    public boolean includeBoolean(final boolean b) {
        return this._includeScalar();
    }
    
    public boolean includeNull() {
        return this._includeScalar();
    }
    
    public boolean includeString(final String s) {
        return this._includeScalar();
    }
    
    public boolean includeNumber(final int n) {
        return this._includeScalar();
    }
    
    public boolean includeNumber(final long n) {
        return this._includeScalar();
    }
    
    public boolean includeNumber(final float n) {
        return this._includeScalar();
    }
    
    public boolean includeNumber(final double n) {
        return this._includeScalar();
    }
    
    public boolean includeNumber(final BigDecimal bigDecimal) {
        return this._includeScalar();
    }
    
    public boolean includeNumber(final BigInteger bigInteger) {
        return this._includeScalar();
    }
    
    public boolean includeBinary() {
        return this._includeScalar();
    }
    
    public boolean includeRawValue() {
        return this._includeScalar();
    }
    
    public boolean includeEmbeddedValue(final Object o) {
        return this._includeScalar();
    }
    
    @Override
    public String toString() {
        if (this == TokenFilter.INCLUDE_ALL) {
            return "TokenFilter.INCLUDE_ALL";
        }
        return super.toString();
    }
    
    protected boolean _includeScalar() {
        return true;
    }
    
    static {
        INCLUDE_ALL = new TokenFilter();
    }
}
