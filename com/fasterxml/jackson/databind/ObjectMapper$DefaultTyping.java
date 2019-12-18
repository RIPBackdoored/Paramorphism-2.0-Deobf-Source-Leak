package com.fasterxml.jackson.databind;

public enum DefaultTyping
{
    JAVA_LANG_OBJECT, 
    OBJECT_AND_NON_CONCRETE, 
    NON_CONCRETE_AND_ARRAYS, 
    NON_FINAL;
    
    private static final DefaultTyping[] $VALUES;
    
    public static DefaultTyping[] values() {
        return DefaultTyping.$VALUES.clone();
    }
    
    public static DefaultTyping valueOf(final String s) {
        return Enum.valueOf(DefaultTyping.class, s);
    }
    
    static {
        $VALUES = new DefaultTyping[] { DefaultTyping.JAVA_LANG_OBJECT, DefaultTyping.OBJECT_AND_NON_CONCRETE, DefaultTyping.NON_CONCRETE_AND_ARRAYS, DefaultTyping.NON_FINAL };
    }
}
