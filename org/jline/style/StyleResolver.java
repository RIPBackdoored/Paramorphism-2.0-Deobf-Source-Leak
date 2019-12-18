package org.jline.style;

import java.util.*;

public class StyleResolver extends org.jline.utils.StyleResolver
{
    private final StyleSource source;
    private final String group;
    
    public StyleResolver(final StyleSource styleSource, final String s) {
        super(StyleResolver::lambda$new$0);
        this.source = Objects.requireNonNull(styleSource);
        this.group = Objects.requireNonNull(s);
    }
    
    public StyleSource getSource() {
        return this.source;
    }
    
    public String getGroup() {
        return this.group;
    }
    
    private static String lambda$new$0(final StyleSource styleSource, final String s, final String s2) {
        return styleSource.get(s, s2);
    }
}
