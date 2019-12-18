package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.io.*;

public abstract class JsonStreamContext
{
    protected static final int TYPE_ROOT = 0;
    protected static final int TYPE_ARRAY = 1;
    protected static final int TYPE_OBJECT = 2;
    protected int _type;
    protected int _index;
    
    protected JsonStreamContext() {
        super();
    }
    
    protected JsonStreamContext(final JsonStreamContext jsonStreamContext) {
        super();
        this._type = jsonStreamContext._type;
        this._index = jsonStreamContext._index;
    }
    
    protected JsonStreamContext(final int type, final int index) {
        super();
        this._type = type;
        this._index = index;
    }
    
    public abstract JsonStreamContext getParent();
    
    public final boolean inArray() {
        return this._type == 1;
    }
    
    public final boolean inRoot() {
        return this._type == 0;
    }
    
    public final boolean inObject() {
        return this._type == 2;
    }
    
    @Deprecated
    public final String getTypeDesc() {
        switch (this._type) {
            case 0: {
                return "ROOT";
            }
            case 1: {
                return "ARRAY";
            }
            case 2: {
                return "OBJECT";
            }
            default: {
                return "?";
            }
        }
    }
    
    public String typeDesc() {
        switch (this._type) {
            case 0: {
                return "root";
            }
            case 1: {
                return "Array";
            }
            case 2: {
                return "Object";
            }
            default: {
                return "?";
            }
        }
    }
    
    public final int getEntryCount() {
        return this._index + 1;
    }
    
    public final int getCurrentIndex() {
        return (this._index < 0) ? 0 : this._index;
    }
    
    public boolean hasCurrentIndex() {
        return this._index >= 0;
    }
    
    public boolean hasPathSegment() {
        if (this._type == 2) {
            return this.hasCurrentName();
        }
        return this._type == 1 && this.hasCurrentIndex();
    }
    
    public abstract String getCurrentName();
    
    public boolean hasCurrentName() {
        return this.getCurrentName() != null;
    }
    
    public Object getCurrentValue() {
        return null;
    }
    
    public void setCurrentValue(final Object o) {
    }
    
    public JsonPointer pathAsPointer() {
        return JsonPointer.forPath(this, false);
    }
    
    public JsonPointer pathAsPointer(final boolean b) {
        return JsonPointer.forPath(this, b);
    }
    
    public JsonLocation getStartLocation(final Object o) {
        return JsonLocation.NA;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(64);
        switch (this._type) {
            case 0: {
                sb.append("/");
                break;
            }
            case 1: {
                sb.append('[');
                sb.append(this.getCurrentIndex());
                sb.append(']');
                break;
            }
            default: {
                sb.append('{');
                final String currentName = this.getCurrentName();
                if (currentName != null) {
                    sb.append('\"');
                    CharTypes.appendQuoted(sb, currentName);
                    sb.append('\"');
                }
                else {
                    sb.append('?');
                }
                sb.append('}');
                break;
            }
        }
        return sb.toString();
    }
}
