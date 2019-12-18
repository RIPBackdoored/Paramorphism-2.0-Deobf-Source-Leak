package com.fasterxml.jackson.databind.type;

import java.io.*;

public final class ClassKey implements Comparable<ClassKey>, Serializable
{
    private static final long serialVersionUID = 1L;
    private String _className;
    private Class<?> _class;
    private int _hashCode;
    
    public ClassKey() {
        super();
        this._class = null;
        this._className = null;
        this._hashCode = 0;
    }
    
    public ClassKey(final Class<?> class1) {
        super();
        this._class = class1;
        this._className = class1.getName();
        this._hashCode = this._className.hashCode();
    }
    
    public void reset(final Class<?> class1) {
        this._class = class1;
        this._className = class1.getName();
        this._hashCode = this._className.hashCode();
    }
    
    @Override
    public int compareTo(final ClassKey classKey) {
        return this._className.compareTo(classKey._className);
    }
    
    @Override
    public boolean equals(final Object o) {
        return o == this || (o != null && o.getClass() == this.getClass() && ((ClassKey)o)._class == this._class);
    }
    
    @Override
    public int hashCode() {
        return this._hashCode;
    }
    
    @Override
    public String toString() {
        return this._className;
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((ClassKey)o);
    }
}
