package com.fasterxml.jackson.core.sym;

private static final class TableInfo
{
    final int size;
    final int longestCollisionList;
    final String[] symbols;
    final Bucket[] buckets;
    
    public TableInfo(final int size, final int longestCollisionList, final String[] symbols, final Bucket[] buckets) {
        super();
        this.size = size;
        this.longestCollisionList = longestCollisionList;
        this.symbols = symbols;
        this.buckets = buckets;
    }
    
    public TableInfo(final CharsToNameCanonicalizer charsToNameCanonicalizer) {
        super();
        this.size = CharsToNameCanonicalizer.access$000(charsToNameCanonicalizer);
        this.longestCollisionList = CharsToNameCanonicalizer.access$100(charsToNameCanonicalizer);
        this.symbols = CharsToNameCanonicalizer.access$200(charsToNameCanonicalizer);
        this.buckets = CharsToNameCanonicalizer.access$300(charsToNameCanonicalizer);
    }
    
    public static TableInfo createInitial(final int n) {
        return new TableInfo(0, 0, new String[n], new Bucket[n >> 1]);
    }
}
