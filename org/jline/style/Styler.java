package org.jline.style;

import java.util.*;
import java.util.logging.*;

public class Styler
{
    private static final Logger log;
    private static StyleSource source;
    
    private Styler() {
        super();
    }
    
    public static StyleSource getSource() {
        return Styler.source;
    }
    
    public static void setSource(final StyleSource styleSource) {
        Styler.source = Objects.requireNonNull(styleSource);
        if (Styler.log.isLoggable(Level.FINE)) {
            Styler.log.fine("Source: " + styleSource);
        }
    }
    
    public static StyleResolver resolver(final String s) {
        return new StyleResolver(Styler.source, s);
    }
    
    public static StyleFactory factory(final String s) {
        return new StyleFactory(resolver(s));
    }
    
    public static <T extends StyleBundle> T bundle(final Class<T> clazz) {
        return StyleBundleInvocationHandler.create(Styler.source, clazz);
    }
    
    public static <T extends StyleBundle> T bundle(final String s, final Class<T> clazz) {
        return StyleBundleInvocationHandler.create(resolver(s), clazz);
    }
    
    static {
        log = Logger.getLogger(Styler.class.getName());
        Styler.source = new NopStyleSource();
    }
}
