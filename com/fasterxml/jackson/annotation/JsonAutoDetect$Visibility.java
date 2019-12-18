package com.fasterxml.jackson.annotation;

import java.lang.reflect.*;

public enum Visibility
{
    ANY, 
    NON_PRIVATE, 
    PROTECTED_AND_PUBLIC, 
    PUBLIC_ONLY, 
    NONE, 
    DEFAULT;
    
    private static final Visibility[] $VALUES;
    
    public static Visibility[] values() {
        return Visibility.$VALUES.clone();
    }
    
    public static Visibility valueOf(final String s) {
        return Enum.valueOf(Visibility.class, s);
    }
    
    public boolean isVisible(final Member member) {
        switch (this) {
            case ANY: {
                return true;
            }
            case NONE: {
                return false;
            }
            case NON_PRIVATE: {
                return !Modifier.isPrivate(member.getModifiers());
            }
            case PROTECTED_AND_PUBLIC: {
                if (Modifier.isProtected(member.getModifiers())) {
                    return true;
                }
                return Modifier.isPublic(member.getModifiers());
            }
            case PUBLIC_ONLY: {
                return Modifier.isPublic(member.getModifiers());
            }
            default: {
                return false;
            }
        }
    }
    
    static {
        $VALUES = new Visibility[] { Visibility.ANY, Visibility.NON_PRIVATE, Visibility.PROTECTED_AND_PUBLIC, Visibility.PUBLIC_ONLY, Visibility.NONE, Visibility.DEFAULT };
    }
}
