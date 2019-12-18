package com.fasterxml.jackson.databind.type;

import java.io.*;
import com.fasterxml.jackson.databind.*;
import java.lang.reflect.*;
import com.fasterxml.jackson.databind.util.*;
import java.util.*;

public class TypeBindings implements Serializable
{
    private static final long serialVersionUID = 1L;
    private static final String[] NO_STRINGS;
    private static final JavaType[] NO_TYPES;
    private static final TypeBindings EMPTY;
    private final String[] _names;
    private final JavaType[] _types;
    private final String[] _unboundVariables;
    private final int _hashCode;
    
    private TypeBindings(final String[] array, final JavaType[] array2, final String[] unboundVariables) {
        super();
        this._names = ((array == null) ? TypeBindings.NO_STRINGS : array);
        this._types = ((array2 == null) ? TypeBindings.NO_TYPES : array2);
        if (this._names.length != this._types.length) {
            throw new IllegalArgumentException("Mismatching names (" + this._names.length + "), types (" + this._types.length + ")");
        }
        int hashCode = 1;
        for (int i = 0; i < this._types.length; ++i) {
            hashCode += this._types[i].hashCode();
        }
        this._unboundVariables = unboundVariables;
        this._hashCode = hashCode;
    }
    
    public static TypeBindings emptyBindings() {
        return TypeBindings.EMPTY;
    }
    
    protected Object readResolve() {
        if (this._names == null || this._names.length == 0) {
            return TypeBindings.EMPTY;
        }
        return this;
    }
    
    public static TypeBindings create(final Class<?> clazz, final List<JavaType> list) {
        return create(clazz, (list == null || list.isEmpty()) ? TypeBindings.NO_TYPES : list.toArray(new JavaType[list.size()]));
    }
    
    public static TypeBindings create(final Class<?> clazz, JavaType[] no_TYPES) {
        if (no_TYPES == null) {
            no_TYPES = TypeBindings.NO_TYPES;
        }
        else {
            switch (no_TYPES.length) {
                case 1: {
                    return create(clazz, no_TYPES[0]);
                }
                case 2: {
                    return create(clazz, no_TYPES[0], no_TYPES[1]);
                }
            }
        }
        final TypeVariable<Class<?>>[] typeParameters = clazz.getTypeParameters();
        String[] no_STRINGS;
        if (typeParameters == null || typeParameters.length == 0) {
            no_STRINGS = TypeBindings.NO_STRINGS;
        }
        else {
            final int length = typeParameters.length;
            no_STRINGS = new String[length];
            for (int i = 0; i < length; ++i) {
                no_STRINGS[i] = typeParameters[i].getName();
            }
        }
        if (no_STRINGS.length != no_TYPES.length) {
            throw new IllegalArgumentException("Cannot create TypeBindings for class " + clazz.getName() + " with " + no_TYPES.length + " type parameter" + ((no_TYPES.length == 1) ? "" : "s") + ": class expects " + no_STRINGS.length);
        }
        return new TypeBindings(no_STRINGS, no_TYPES, null);
    }
    
    public static TypeBindings create(final Class<?> clazz, final JavaType javaType) {
        final TypeVariable<?>[] paramsFor1 = TypeParamStash.paramsFor1(clazz);
        final int n = (paramsFor1 == null) ? 0 : paramsFor1.length;
        if (n != 1) {
            throw new IllegalArgumentException("Cannot create TypeBindings for class " + clazz.getName() + " with 1 type parameter: class expects " + n);
        }
        return new TypeBindings(new String[] { paramsFor1[0].getName() }, new JavaType[] { javaType }, null);
    }
    
    public static TypeBindings create(final Class<?> clazz, final JavaType javaType, final JavaType javaType2) {
        final TypeVariable<?>[] paramsFor2 = TypeParamStash.paramsFor2(clazz);
        final int n = (paramsFor2 == null) ? 0 : paramsFor2.length;
        if (n != 2) {
            throw new IllegalArgumentException("Cannot create TypeBindings for class " + clazz.getName() + " with 2 type parameters: class expects " + n);
        }
        return new TypeBindings(new String[] { paramsFor2[0].getName(), paramsFor2[1].getName() }, new JavaType[] { javaType, javaType2 }, null);
    }
    
    public static TypeBindings createIfNeeded(final Class<?> clazz, final JavaType javaType) {
        final TypeVariable<Class<?>>[] typeParameters = clazz.getTypeParameters();
        final int n = (typeParameters == null) ? 0 : typeParameters.length;
        if (n == 0) {
            return TypeBindings.EMPTY;
        }
        if (n != 1) {
            throw new IllegalArgumentException("Cannot create TypeBindings for class " + clazz.getName() + " with 1 type parameter: class expects " + n);
        }
        return new TypeBindings(new String[] { typeParameters[0].getName() }, new JavaType[] { javaType }, null);
    }
    
    public static TypeBindings createIfNeeded(final Class<?> clazz, JavaType[] no_TYPES) {
        final TypeVariable<Class<?>>[] typeParameters = clazz.getTypeParameters();
        if (typeParameters == null || typeParameters.length == 0) {
            return TypeBindings.EMPTY;
        }
        if (no_TYPES == null) {
            no_TYPES = TypeBindings.NO_TYPES;
        }
        final int length = typeParameters.length;
        final String[] array = new String[length];
        for (int i = 0; i < length; ++i) {
            array[i] = typeParameters[i].getName();
        }
        if (array.length != no_TYPES.length) {
            throw new IllegalArgumentException("Cannot create TypeBindings for class " + clazz.getName() + " with " + no_TYPES.length + " type parameter" + ((no_TYPES.length == 1) ? "" : "s") + ": class expects " + array.length);
        }
        return new TypeBindings(array, no_TYPES, null);
    }
    
    public TypeBindings withUnboundVariable(final String s) {
        final int n = (this._unboundVariables == null) ? 0 : this._unboundVariables.length;
        final String[] array = (n == 0) ? new String[1] : Arrays.copyOf(this._unboundVariables, n + 1);
        array[n] = s;
        return new TypeBindings(this._names, this._types, array);
    }
    
    public JavaType findBoundType(final String s) {
        for (int i = 0; i < this._names.length; ++i) {
            if (s.equals(this._names[i])) {
                JavaType javaType = this._types[i];
                if (javaType instanceof ResolvedRecursiveType) {
                    final JavaType selfReferencedType = ((ResolvedRecursiveType)javaType).getSelfReferencedType();
                    if (selfReferencedType != null) {
                        javaType = selfReferencedType;
                    }
                }
                return javaType;
            }
        }
        return null;
    }
    
    public boolean isEmpty() {
        return this._types.length == 0;
    }
    
    public int size() {
        return this._types.length;
    }
    
    public String getBoundName(final int n) {
        if (n < 0 || n >= this._names.length) {
            return null;
        }
        return this._names[n];
    }
    
    public JavaType getBoundType(final int n) {
        if (n < 0 || n >= this._types.length) {
            return null;
        }
        return this._types[n];
    }
    
    public List<JavaType> getTypeParameters() {
        if (this._types.length == 0) {
            return Collections.emptyList();
        }
        return Arrays.asList(this._types);
    }
    
    public boolean hasUnbound(final String s) {
        if (this._unboundVariables != null) {
            int length = this._unboundVariables.length;
            while (--length >= 0) {
                if (s.equals(this._unboundVariables[length])) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public Object asKey(final Class<?> clazz) {
        return new AsKey(clazz, this._types, this._hashCode);
    }
    
    @Override
    public String toString() {
        if (this._types.length == 0) {
            return "<>";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append('<');
        for (int i = 0; i < this._types.length; ++i) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(this._types[i].getGenericSignature());
        }
        sb.append('>');
        return sb.toString();
    }
    
    @Override
    public int hashCode() {
        return this._hashCode;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!ClassUtil.hasClass(o, this.getClass())) {
            return false;
        }
        final TypeBindings typeBindings = (TypeBindings)o;
        final int length = this._types.length;
        if (length != typeBindings.size()) {
            return false;
        }
        final JavaType[] types = typeBindings._types;
        for (int i = 0; i < length; ++i) {
            if (!types[i].equals(this._types[i])) {
                return false;
            }
        }
        return true;
    }
    
    protected JavaType[] typeParameterArray() {
        return this._types;
    }
    
    static {
        NO_STRINGS = new String[0];
        NO_TYPES = new JavaType[0];
        EMPTY = new TypeBindings(TypeBindings.NO_STRINGS, TypeBindings.NO_TYPES, null);
    }
    
    static class TypeParamStash
    {
        private static final TypeVariable<?>[] VARS_ABSTRACT_LIST;
        private static final TypeVariable<?>[] VARS_COLLECTION;
        private static final TypeVariable<?>[] VARS_ITERABLE;
        private static final TypeVariable<?>[] VARS_LIST;
        private static final TypeVariable<?>[] VARS_ARRAY_LIST;
        private static final TypeVariable<?>[] VARS_MAP;
        private static final TypeVariable<?>[] VARS_HASH_MAP;
        private static final TypeVariable<?>[] VARS_LINKED_HASH_MAP;
        
        TypeParamStash() {
            super();
        }
        
        public static TypeVariable<?>[] paramsFor1(final Class<?> clazz) {
            if (clazz == Collection.class) {
                return TypeParamStash.VARS_COLLECTION;
            }
            if (clazz == List.class) {
                return TypeParamStash.VARS_LIST;
            }
            if (clazz == ArrayList.class) {
                return TypeParamStash.VARS_ARRAY_LIST;
            }
            if (clazz == AbstractList.class) {
                return TypeParamStash.VARS_ABSTRACT_LIST;
            }
            if (clazz == Iterable.class) {
                return TypeParamStash.VARS_ITERABLE;
            }
            return clazz.getTypeParameters();
        }
        
        public static TypeVariable<?>[] paramsFor2(final Class<?> clazz) {
            if (clazz == Map.class) {
                return TypeParamStash.VARS_MAP;
            }
            if (clazz == HashMap.class) {
                return TypeParamStash.VARS_HASH_MAP;
            }
            if (clazz == LinkedHashMap.class) {
                return TypeParamStash.VARS_LINKED_HASH_MAP;
            }
            return clazz.getTypeParameters();
        }
        
        static {
            VARS_ABSTRACT_LIST = AbstractList.class.getTypeParameters();
            VARS_COLLECTION = Collection.class.getTypeParameters();
            VARS_ITERABLE = Iterable.class.getTypeParameters();
            VARS_LIST = List.class.getTypeParameters();
            VARS_ARRAY_LIST = ArrayList.class.getTypeParameters();
            VARS_MAP = Map.class.getTypeParameters();
            VARS_HASH_MAP = HashMap.class.getTypeParameters();
            VARS_LINKED_HASH_MAP = LinkedHashMap.class.getTypeParameters();
        }
    }
    
    static final class AsKey
    {
        private final Class<?> _raw;
        private final JavaType[] _params;
        private final int _hash;
        
        public AsKey(final Class<?> raw, final JavaType[] params, final int hash) {
            super();
            this._raw = raw;
            this._params = params;
            this._hash = hash;
        }
        
        @Override
        public int hashCode() {
            return this._hash;
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
            final AsKey asKey = (AsKey)o;
            if (this._hash == asKey._hash && this._raw == asKey._raw) {
                final JavaType[] params = asKey._params;
                final int length = this._params.length;
                if (length == params.length) {
                    for (int i = 0; i < length; ++i) {
                        if (!this._params[i].equals(params[i])) {
                            return false;
                        }
                    }
                    return true;
                }
            }
            return false;
        }
        
        @Override
        public String toString() {
            return this._raw.getName() + "<>";
        }
    }
}
