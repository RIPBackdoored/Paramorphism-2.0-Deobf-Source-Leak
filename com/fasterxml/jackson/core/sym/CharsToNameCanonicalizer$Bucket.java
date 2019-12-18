package com.fasterxml.jackson.core.sym;

static final class Bucket
{
    public final String symbol;
    public final Bucket next;
    public final int length;
    
    public Bucket(final String symbol, final Bucket next) {
        super();
        this.symbol = symbol;
        this.next = next;
        this.length = ((next == null) ? 1 : (next.length + 1));
    }
    
    public String has(final char[] array, final int n, final int n2) {
        if (this.symbol.length() != n2) {
            return null;
        }
        int n3 = 0;
        while (this.symbol.charAt(n3) == array[n + n3]) {
            if (++n3 >= n2) {
                return this.symbol;
            }
        }
        return null;
    }
}
