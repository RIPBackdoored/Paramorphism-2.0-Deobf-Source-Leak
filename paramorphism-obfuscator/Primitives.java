package paramorphism-obfuscator;

import org.objectweb.asm.*;
import org.jetbrains.annotations.*;
import kotlin.reflect.*;
import kotlin.jvm.*;

public final class Primitives
{
    public static final Type getVoid() {
        return Type.VOID_TYPE;
    }
    
    public static final Type getChar() {
        return Type.CHAR_TYPE;
    }
    
    public static final Type getByte() {
        return Type.BYTE_TYPE;
    }
    
    public static final Type getShort() {
        return Type.SHORT_TYPE;
    }
    
    public static final Type getInt() {
        return Type.INT_TYPE;
    }
    
    public static final Type getFloat() {
        return Type.FLOAT_TYPE;
    }
    
    public static final Type getLong() {
        return Type.LONG_TYPE;
    }
    
    public static final Type getDouble() {
        return Type.DOUBLE_TYPE;
    }
    
    public static final Type getBoolean() {
        return Type.BOOLEAN_TYPE;
    }
    
    @NotNull
    public static final Type getType(@NotNull final Object o) {
        Type type;
        if (o instanceof Type) {
            type = (Type)o;
        }
        else if (o instanceof KClass) {
            type = Type.getType(JvmClassMappingKt.getJavaClass((KClass<Object>)o));
        }
        else if (o instanceof Class) {
            type = Type.getType((Class<?>)o);
        }
        else {
            if (!(o instanceof String)) {
                throw new IllegalStateException("Non type-like object passed to coerceType()".toString());
            }
            type = Type.getObjectType((String)o);
        }
        return type;
    }
}
