package org.eclipse.aether.util.repository;

import java.util.regex.*;
import java.util.*;

static class NonProxyHosts
{
    private final Pattern[] patterns;
    
    NonProxyHosts(final String s) {
        super();
        final ArrayList<Pattern> list = new ArrayList<Pattern>();
        if (s != null) {
            final StringTokenizer stringTokenizer = new StringTokenizer(s, "|");
            while (stringTokenizer.hasMoreTokens()) {
                list.add(Pattern.compile(stringTokenizer.nextToken().replace(".", "\\.").replace("*", ".*"), 2));
            }
        }
        this.patterns = list.toArray(new Pattern[list.size()]);
    }
    
    boolean isNonProxyHost(final String s) {
        if (s != null) {
            final Pattern[] patterns = this.patterns;
            for (int length = patterns.length, i = 0; i < length; ++i) {
                if (patterns[i].matcher(s).matches()) {
                    return true;
                }
            }
        }
        return false;
    }
}
