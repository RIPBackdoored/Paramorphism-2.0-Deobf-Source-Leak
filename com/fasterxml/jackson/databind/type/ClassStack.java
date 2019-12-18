package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.databind.*;
import java.util.*;

public final class ClassStack
{
    protected final ClassStack _parent;
    protected final Class<?> _current;
    private ArrayList<ResolvedRecursiveType> _selfRefs;
    
    public ClassStack(final Class<?> clazz) {
        this(null, clazz);
    }
    
    private ClassStack(final ClassStack parent, final Class<?> current) {
        super();
        this._parent = parent;
        this._current = current;
    }
    
    public ClassStack child(final Class<?> clazz) {
        return new ClassStack(this, clazz);
    }
    
    public void addSelfReference(final ResolvedRecursiveType resolvedRecursiveType) {
        if (this._selfRefs == null) {
            this._selfRefs = new ArrayList<ResolvedRecursiveType>();
        }
        this._selfRefs.add(resolvedRecursiveType);
    }
    
    public void resolveSelfReferences(final JavaType reference) {
        if (this._selfRefs != null) {
            final Iterator<ResolvedRecursiveType> iterator = this._selfRefs.iterator();
            while (iterator.hasNext()) {
                iterator.next().setReference(reference);
            }
        }
    }
    
    public ClassStack find(final Class<?> clazz) {
        if (this._current == clazz) {
            return this;
        }
        for (ClassStack classStack = this._parent; classStack != null; classStack = classStack._parent) {
            if (classStack._current == clazz) {
                return classStack;
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("[ClassStack (self-refs: ").append((this._selfRefs == null) ? "0" : String.valueOf(this._selfRefs.size())).append(')');
        for (ClassStack parent = this; parent != null; parent = parent._parent) {
            sb.append(' ').append(parent._current.getName());
        }
        sb.append(']');
        return sb.toString();
    }
}
