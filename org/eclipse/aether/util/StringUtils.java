package org.eclipse.aether.util;

public final class StringUtils
{
    private StringUtils() {
        super();
    }
    
    public static boolean isEmpty(final String s) {
        return s == null || s.length() <= 0;
    }
}
