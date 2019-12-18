package com.fasterxml.jackson.databind.annotation;

public enum Typing
{
    DYNAMIC, 
    STATIC, 
    DEFAULT_TYPING;
    
    private static final Typing[] $VALUES;
    
    public static Typing[] values() {
        return Typing.$VALUES.clone();
    }
    
    public static Typing valueOf(final String s) {
        return Enum.valueOf(Typing.class, s);
    }
    
    static {
        $VALUES = new Typing[] { Typing.DYNAMIC, Typing.STATIC, Typing.DEFAULT_TYPING };
    }
}
