package com.fasterxml.jackson.annotation;

import java.io.*;
import java.util.*;

public static class Value implements JacksonAnnotationValue<JsonIgnoreProperties>, Serializable
{
    private static final long serialVersionUID = 1L;
    protected static final Value EMPTY;
    protected final Set<String> _ignored;
    protected final boolean _ignoreUnknown;
    protected final boolean _allowGetters;
    protected final boolean _allowSetters;
    protected final boolean _merge;
    
    protected Value(final Set<String> ignored, final boolean ignoreUnknown, final boolean allowGetters, final boolean allowSetters, final boolean merge) {
        super();
        if (ignored == null) {
            this._ignored = Collections.emptySet();
        }
        else {
            this._ignored = ignored;
        }
        this._ignoreUnknown = ignoreUnknown;
        this._allowGetters = allowGetters;
        this._allowSetters = allowSetters;
        this._merge = merge;
    }
    
    public static Value from(final JsonIgnoreProperties jsonIgnoreProperties) {
        if (jsonIgnoreProperties == null) {
            return Value.EMPTY;
        }
        return construct(_asSet(jsonIgnoreProperties.value()), jsonIgnoreProperties.ignoreUnknown(), jsonIgnoreProperties.allowGetters(), jsonIgnoreProperties.allowSetters(), false);
    }
    
    public static Value construct(final Set<String> set, final boolean b, final boolean b2, final boolean b3, final boolean b4) {
        if (_empty(set, b, b2, b3, b4)) {
            return Value.EMPTY;
        }
        return new Value(set, b, b2, b3, b4);
    }
    
    public static Value empty() {
        return Value.EMPTY;
    }
    
    public static Value merge(final Value value, final Value value2) {
        return (value == null) ? value2 : value.withOverrides(value2);
    }
    
    public static Value mergeAll(final Value... array) {
        Value value = null;
        for (final Value value2 : array) {
            if (value2 != null) {
                value = ((value == null) ? value2 : value.withOverrides(value2));
            }
        }
        return value;
    }
    
    public static Value forIgnoredProperties(final Set<String> set) {
        return Value.EMPTY.withIgnored(set);
    }
    
    public static Value forIgnoredProperties(final String... array) {
        if (array.length == 0) {
            return Value.EMPTY;
        }
        return Value.EMPTY.withIgnored(_asSet(array));
    }
    
    public static Value forIgnoreUnknown(final boolean b) {
        return b ? Value.EMPTY.withIgnoreUnknown() : Value.EMPTY.withoutIgnoreUnknown();
    }
    
    public Value withOverrides(final Value value) {
        if (value == null || value == Value.EMPTY) {
            return this;
        }
        if (!value._merge) {
            return value;
        }
        if (_equals(this, value)) {
            return this;
        }
        return construct(_merge(this._ignored, value._ignored), this._ignoreUnknown || value._ignoreUnknown, this._allowGetters || value._allowGetters, this._allowSetters || value._allowSetters, true);
    }
    
    public Value withIgnored(final Set<String> set) {
        return construct(set, this._ignoreUnknown, this._allowGetters, this._allowSetters, this._merge);
    }
    
    public Value withIgnored(final String... array) {
        return construct(_asSet(array), this._ignoreUnknown, this._allowGetters, this._allowSetters, this._merge);
    }
    
    public Value withoutIgnored() {
        return construct(null, this._ignoreUnknown, this._allowGetters, this._allowSetters, this._merge);
    }
    
    public Value withIgnoreUnknown() {
        return this._ignoreUnknown ? this : construct(this._ignored, true, this._allowGetters, this._allowSetters, this._merge);
    }
    
    public Value withoutIgnoreUnknown() {
        return this._ignoreUnknown ? construct(this._ignored, false, this._allowGetters, this._allowSetters, this._merge) : this;
    }
    
    public Value withAllowGetters() {
        return this._allowGetters ? this : construct(this._ignored, this._ignoreUnknown, true, this._allowSetters, this._merge);
    }
    
    public Value withoutAllowGetters() {
        return this._allowGetters ? construct(this._ignored, this._ignoreUnknown, false, this._allowSetters, this._merge) : this;
    }
    
    public Value withAllowSetters() {
        return this._allowSetters ? this : construct(this._ignored, this._ignoreUnknown, this._allowGetters, true, this._merge);
    }
    
    public Value withoutAllowSetters() {
        return this._allowSetters ? construct(this._ignored, this._ignoreUnknown, this._allowGetters, false, this._merge) : this;
    }
    
    public Value withMerge() {
        return this._merge ? this : construct(this._ignored, this._ignoreUnknown, this._allowGetters, this._allowSetters, true);
    }
    
    public Value withoutMerge() {
        return this._merge ? construct(this._ignored, this._ignoreUnknown, this._allowGetters, this._allowSetters, false) : this;
    }
    
    @Override
    public Class<JsonIgnoreProperties> valueFor() {
        return JsonIgnoreProperties.class;
    }
    
    protected Object readResolve() {
        if (_empty(this._ignored, this._ignoreUnknown, this._allowGetters, this._allowSetters, this._merge)) {
            return Value.EMPTY;
        }
        return this;
    }
    
    public Set<String> getIgnored() {
        return this._ignored;
    }
    
    public Set<String> findIgnoredForSerialization() {
        if (this._allowGetters) {
            return Collections.emptySet();
        }
        return this._ignored;
    }
    
    public Set<String> findIgnoredForDeserialization() {
        if (this._allowSetters) {
            return Collections.emptySet();
        }
        return this._ignored;
    }
    
    public boolean getIgnoreUnknown() {
        return this._ignoreUnknown;
    }
    
    public boolean getAllowGetters() {
        return this._allowGetters;
    }
    
    public boolean getAllowSetters() {
        return this._allowSetters;
    }
    
    public boolean getMerge() {
        return this._merge;
    }
    
    @Override
    public String toString() {
        return String.format("JsonIgnoreProperties.Value(ignored=%s,ignoreUnknown=%s,allowGetters=%s,allowSetters=%s,merge=%s)", this._ignored, this._ignoreUnknown, this._allowGetters, this._allowSetters, this._merge);
    }
    
    @Override
    public int hashCode() {
        return this._ignored.size() + (this._ignoreUnknown ? 1 : -3) + (this._allowGetters ? 3 : -7) + (this._allowSetters ? 7 : -11) + (this._merge ? 11 : -13);
    }
    
    @Override
    public boolean equals(final Object o) {
        return o == this || (o != null && o.getClass() == this.getClass() && _equals(this, (Value)o));
    }
    
    private static boolean _equals(final Value value, final Value value2) {
        return value._ignoreUnknown == value2._ignoreUnknown && value._merge == value2._merge && value._allowGetters == value2._allowGetters && value._allowSetters == value2._allowSetters && value._ignored.equals(value2._ignored);
    }
    
    private static Set<String> _asSet(final String[] array) {
        if (array == null || array.length == 0) {
            return Collections.emptySet();
        }
        final HashSet<String> set = new HashSet<String>(array.length);
        for (int length = array.length, i = 0; i < length; ++i) {
            set.add(array[i]);
        }
        return set;
    }
    
    private static Set<String> _merge(final Set<String> set, final Set<String> set2) {
        if (set.isEmpty()) {
            return set2;
        }
        if (set2.isEmpty()) {
            return set;
        }
        final HashSet<Object> set3 = (HashSet<Object>)new HashSet<String>(set.size() + set2.size());
        set3.addAll(set);
        set3.addAll(set2);
        return (Set<String>)set3;
    }
    
    private static boolean _empty(final Set<String> set, final boolean b, final boolean b2, final boolean b3, final boolean b4) {
        return b == Value.EMPTY._ignoreUnknown && b2 == Value.EMPTY._allowGetters && b3 == Value.EMPTY._allowSetters && b4 == Value.EMPTY._merge && (set == null || set.size() == 0);
    }
    
    static {
        EMPTY = new Value(Collections.emptySet(), false, false, false, true);
    }
}
