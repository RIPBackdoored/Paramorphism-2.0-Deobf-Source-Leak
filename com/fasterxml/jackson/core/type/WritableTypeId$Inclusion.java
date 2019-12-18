package com.fasterxml.jackson.core.type;

public enum Inclusion
{
    WRAPPER_ARRAY, 
    WRAPPER_OBJECT, 
    METADATA_PROPERTY, 
    PAYLOAD_PROPERTY, 
    PARENT_PROPERTY;
    
    private static final Inclusion[] $VALUES;
    
    public static Inclusion[] values() {
        return Inclusion.$VALUES.clone();
    }
    
    public static Inclusion valueOf(final String s) {
        return Enum.valueOf(Inclusion.class, s);
    }
    
    public boolean requiresObjectContext() {
        return this == Inclusion.METADATA_PROPERTY || this == Inclusion.PAYLOAD_PROPERTY;
    }
    
    static {
        $VALUES = new Inclusion[] { Inclusion.WRAPPER_ARRAY, Inclusion.WRAPPER_OBJECT, Inclusion.METADATA_PROPERTY, Inclusion.PAYLOAD_PROPERTY, Inclusion.PARENT_PROPERTY };
    }
}
