package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.databind.cfg.*;

public enum DeserializationFeature implements ConfigFeature
{
    USE_BIG_DECIMAL_FOR_FLOATS(false), 
    USE_BIG_INTEGER_FOR_INTS(false), 
    USE_LONG_FOR_INTS(false), 
    USE_JAVA_ARRAY_FOR_JSON_ARRAY(false), 
    FAIL_ON_UNKNOWN_PROPERTIES(true), 
    FAIL_ON_NULL_FOR_PRIMITIVES(false), 
    FAIL_ON_NUMBERS_FOR_ENUMS(false), 
    FAIL_ON_INVALID_SUBTYPE(true), 
    FAIL_ON_READING_DUP_TREE_KEY(false), 
    FAIL_ON_IGNORED_PROPERTIES(false), 
    FAIL_ON_UNRESOLVED_OBJECT_IDS(true), 
    FAIL_ON_MISSING_CREATOR_PROPERTIES(false), 
    FAIL_ON_NULL_CREATOR_PROPERTIES(false), 
    FAIL_ON_MISSING_EXTERNAL_TYPE_ID_PROPERTY(true), 
    FAIL_ON_TRAILING_TOKENS(false), 
    WRAP_EXCEPTIONS(true), 
    ACCEPT_SINGLE_VALUE_AS_ARRAY(false), 
    UNWRAP_SINGLE_VALUE_ARRAYS(false), 
    UNWRAP_ROOT_VALUE(false), 
    ACCEPT_EMPTY_STRING_AS_NULL_OBJECT(false), 
    ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT(false), 
    ACCEPT_FLOAT_AS_INT(true), 
    READ_ENUMS_USING_TO_STRING(false), 
    READ_UNKNOWN_ENUM_VALUES_AS_NULL(false), 
    READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE(false), 
    READ_DATE_TIMESTAMPS_AS_NANOSECONDS(true), 
    ADJUST_DATES_TO_CONTEXT_TIME_ZONE(true), 
    EAGER_DESERIALIZER_FETCH(true);
    
    private final boolean _defaultState;
    private final int _mask;
    private static final DeserializationFeature[] $VALUES;
    
    public static DeserializationFeature[] values() {
        return DeserializationFeature.$VALUES.clone();
    }
    
    public static DeserializationFeature valueOf(final String s) {
        return Enum.valueOf(DeserializationFeature.class, s);
    }
    
    private DeserializationFeature(final boolean defaultState) {
        this._defaultState = defaultState;
        this._mask = 1 << this.ordinal();
    }
    
    @Override
    public boolean enabledByDefault() {
        return this._defaultState;
    }
    
    @Override
    public int getMask() {
        return this._mask;
    }
    
    @Override
    public boolean enabledIn(final int n) {
        return (n & this._mask) != 0x0;
    }
    
    static {
        $VALUES = new DeserializationFeature[] { DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, DeserializationFeature.USE_BIG_INTEGER_FOR_INTS, DeserializationFeature.USE_LONG_FOR_INTS, DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY, DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, DeserializationFeature.FAIL_ON_UNRESOLVED_OBJECT_IDS, DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, DeserializationFeature.FAIL_ON_MISSING_EXTERNAL_TYPE_ID_PROPERTY, DeserializationFeature.FAIL_ON_TRAILING_TOKENS, DeserializationFeature.WRAP_EXCEPTIONS, DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS, DeserializationFeature.UNWRAP_ROOT_VALUE, DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, DeserializationFeature.ACCEPT_FLOAT_AS_INT, DeserializationFeature.READ_ENUMS_USING_TO_STRING, DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE, DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, DeserializationFeature.EAGER_DESERIALIZER_FETCH };
    }
}
