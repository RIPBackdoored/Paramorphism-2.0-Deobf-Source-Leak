package com.fasterxml.jackson.databind.node;

public enum JsonNodeType
{
    ARRAY, 
    BINARY, 
    BOOLEAN, 
    MISSING, 
    NULL, 
    NUMBER, 
    OBJECT, 
    POJO, 
    STRING;
    
    private static final JsonNodeType[] $VALUES;
    
    public static JsonNodeType[] values() {
        return JsonNodeType.$VALUES.clone();
    }
    
    public static JsonNodeType valueOf(final String s) {
        return Enum.valueOf(JsonNodeType.class, s);
    }
    
    static {
        $VALUES = new JsonNodeType[] { JsonNodeType.ARRAY, JsonNodeType.BINARY, JsonNodeType.BOOLEAN, JsonNodeType.MISSING, JsonNodeType.NULL, JsonNodeType.NUMBER, JsonNodeType.OBJECT, JsonNodeType.POJO, JsonNodeType.STRING };
    }
}
