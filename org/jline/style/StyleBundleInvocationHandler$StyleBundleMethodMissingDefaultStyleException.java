package org.jline.style;

import java.lang.reflect.*;

static class StyleBundleMethodMissingDefaultStyleException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    
    public StyleBundleMethodMissingDefaultStyleException(final Method method) {
        super(String.format("%s method missing @%s: %s", StyleBundle.class.getSimpleName(), StyleBundle.DefaultStyle.class.getSimpleName(), method));
    }
}
