package com.fasterxml.jackson.databind.introspect;

import java.lang.reflect.*;

public final class MemberKey
{
    static final Class<?>[] NO_CLASSES;
    final String _name;
    final Class<?>[] _argTypes;
    
    public MemberKey(final Method method) {
        this(method.getName(), method.getParameterTypes());
    }
    
    public MemberKey(final Constructor<?> constructor) {
        this("", constructor.getParameterTypes());
    }
    
    public MemberKey(final String name, final Class<?>[] array) {
        super();
        this._name = name;
        this._argTypes = ((array == null) ? MemberKey.NO_CLASSES : array);
    }
    
    public String getName() {
        return this._name;
    }
    
    public int argCount() {
        return this._argTypes.length;
    }
    
    @Override
    public String toString() {
        return this._name + "(" + this._argTypes.length + "-args)";
    }
    
    @Override
    public int hashCode() {
        return this._name.hashCode() + this._argTypes.length;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }
        final MemberKey memberKey = (MemberKey)o;
        if (!this._name.equals(memberKey._name)) {
            return false;
        }
        final Class<?>[] argTypes = memberKey._argTypes;
        final int length = this._argTypes.length;
        if (argTypes.length != length) {
            return false;
        }
        for (int i = 0; i < length; ++i) {
            if (argTypes[i] != this._argTypes[i]) {
                return false;
            }
        }
        return true;
    }
    
    static {
        NO_CLASSES = new Class[0];
    }
}
