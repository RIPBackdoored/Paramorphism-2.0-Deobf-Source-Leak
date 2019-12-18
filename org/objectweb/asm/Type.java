package org.objectweb.asm;

import java.lang.reflect.*;

public final class Type
{
    public static final int VOID = 0;
    public static final int BOOLEAN = 1;
    public static final int CHAR = 2;
    public static final int BYTE = 3;
    public static final int SHORT = 4;
    public static final int INT = 5;
    public static final int FLOAT = 6;
    public static final int LONG = 7;
    public static final int DOUBLE = 8;
    public static final int ARRAY = 9;
    public static final int OBJECT = 10;
    public static final int METHOD = 11;
    private static final int INTERNAL = 12;
    private static final String PRIMITIVE_DESCRIPTORS = "VZCBSIFJD";
    public static final Type VOID_TYPE;
    public static final Type BOOLEAN_TYPE;
    public static final Type CHAR_TYPE;
    public static final Type BYTE_TYPE;
    public static final Type SHORT_TYPE;
    public static final Type INT_TYPE;
    public static final Type FLOAT_TYPE;
    public static final Type LONG_TYPE;
    public static final Type DOUBLE_TYPE;
    private final int sort;
    private final String valueBuffer;
    private final int valueBegin;
    private final int valueEnd;
    
    private Type(final int sort, final String valueBuffer, final int valueBegin, final int valueEnd) {
        super();
        this.sort = sort;
        this.valueBuffer = valueBuffer;
        this.valueBegin = valueBegin;
        this.valueEnd = valueEnd;
    }
    
    public static Type getType(final String typeDescriptor) {
        return getTypeInternal(typeDescriptor, 0, typeDescriptor.length());
    }
    
    public static Type getType(final Class<?> clazz) {
        if (!clazz.isPrimitive()) {
            return getType(getDescriptor(clazz));
        }
        if (clazz == Integer.TYPE) {
            return Type.INT_TYPE;
        }
        if (clazz == Void.TYPE) {
            return Type.VOID_TYPE;
        }
        if (clazz == Boolean.TYPE) {
            return Type.BOOLEAN_TYPE;
        }
        if (clazz == Byte.TYPE) {
            return Type.BYTE_TYPE;
        }
        if (clazz == Character.TYPE) {
            return Type.CHAR_TYPE;
        }
        if (clazz == Short.TYPE) {
            return Type.SHORT_TYPE;
        }
        if (clazz == Double.TYPE) {
            return Type.DOUBLE_TYPE;
        }
        if (clazz == Float.TYPE) {
            return Type.FLOAT_TYPE;
        }
        if (clazz == Long.TYPE) {
            return Type.LONG_TYPE;
        }
        throw new AssertionError();
    }
    
    public static Type getType(final Constructor<?> constructor) {
        return getType(getConstructorDescriptor(constructor));
    }
    
    public static Type getType(final Method method) {
        return getType(getMethodDescriptor(method));
    }
    
    public Type getElementType() {
        final int numDimensions = this.getDimensions();
        return getTypeInternal(this.valueBuffer, this.valueBegin + numDimensions, this.valueEnd);
    }
    
    public static Type getObjectType(final String internalName) {
        return new Type((internalName.charAt(0) == '[') ? 9 : 12, internalName, 0, internalName.length());
    }
    
    public static Type getMethodType(final String methodDescriptor) {
        return new Type(11, methodDescriptor, 0, methodDescriptor.length());
    }
    
    public static Type getMethodType(final Type returnType, final Type... argumentTypes) {
        return getType(getMethodDescriptor(returnType, argumentTypes));
    }
    
    public Type[] getArgumentTypes() {
        return getArgumentTypes(this.getDescriptor());
    }
    
    public static Type[] getArgumentTypes(final String methodDescriptor) {
        int numArgumentTypes = 0;
        int currentOffset = 1;
        while (methodDescriptor.charAt(currentOffset) != ')') {
            while (methodDescriptor.charAt(currentOffset) == '[') {
                ++currentOffset;
            }
            if (methodDescriptor.charAt(currentOffset++) == 'L') {
                currentOffset = methodDescriptor.indexOf(59, currentOffset) + 1;
            }
            ++numArgumentTypes;
        }
        final Type[] argumentTypes = new Type[numArgumentTypes];
        currentOffset = 1;
        int currentArgumentTypeIndex = 0;
        while (methodDescriptor.charAt(currentOffset) != ')') {
            final int currentArgumentTypeOffset = currentOffset;
            while (methodDescriptor.charAt(currentOffset) == '[') {
                ++currentOffset;
            }
            if (methodDescriptor.charAt(currentOffset++) == 'L') {
                currentOffset = methodDescriptor.indexOf(59, currentOffset) + 1;
            }
            argumentTypes[currentArgumentTypeIndex++] = getTypeInternal(methodDescriptor, currentArgumentTypeOffset, currentOffset);
        }
        return argumentTypes;
    }
    
    public static Type[] getArgumentTypes(final Method method) {
        final Class<?>[] classes = method.getParameterTypes();
        final Type[] types = new Type[classes.length];
        for (int i = classes.length - 1; i >= 0; --i) {
            types[i] = getType(classes[i]);
        }
        return types;
    }
    
    public Type getReturnType() {
        return getReturnType(this.getDescriptor());
    }
    
    public static Type getReturnType(final String methodDescriptor) {
        return getTypeInternal(methodDescriptor, getReturnTypeOffset(methodDescriptor), methodDescriptor.length());
    }
    
    public static Type getReturnType(final Method method) {
        return getType(method.getReturnType());
    }
    
    static int getReturnTypeOffset(final String methodDescriptor) {
        int currentOffset;
        for (currentOffset = 1; methodDescriptor.charAt(currentOffset) != ')'; currentOffset = methodDescriptor.indexOf(59, currentOffset) + 1) {
            while (methodDescriptor.charAt(currentOffset) == '[') {
                ++currentOffset;
            }
            if (methodDescriptor.charAt(currentOffset++) == 'L') {}
        }
        return currentOffset + 1;
    }
    
    private static Type getTypeInternal(final String descriptorBuffer, final int descriptorBegin, final int descriptorEnd) {
        switch (descriptorBuffer.charAt(descriptorBegin)) {
            case 'V': {
                return Type.VOID_TYPE;
            }
            case 'Z': {
                return Type.BOOLEAN_TYPE;
            }
            case 'C': {
                return Type.CHAR_TYPE;
            }
            case 'B': {
                return Type.BYTE_TYPE;
            }
            case 'S': {
                return Type.SHORT_TYPE;
            }
            case 'I': {
                return Type.INT_TYPE;
            }
            case 'F': {
                return Type.FLOAT_TYPE;
            }
            case 'J': {
                return Type.LONG_TYPE;
            }
            case 'D': {
                return Type.DOUBLE_TYPE;
            }
            case '[': {
                return new Type(9, descriptorBuffer, descriptorBegin, descriptorEnd);
            }
            case 'L': {
                return new Type(10, descriptorBuffer, descriptorBegin + 1, descriptorEnd - 1);
            }
            case '(': {
                return new Type(11, descriptorBuffer, descriptorBegin, descriptorEnd);
            }
            default: {
                throw new IllegalArgumentException();
            }
        }
    }
    
    public String getClassName() {
        switch (this.sort) {
            case 0: {
                return "void";
            }
            case 1: {
                return "boolean";
            }
            case 2: {
                return "char";
            }
            case 3: {
                return "byte";
            }
            case 4: {
                return "short";
            }
            case 5: {
                return "int";
            }
            case 6: {
                return "float";
            }
            case 7: {
                return "long";
            }
            case 8: {
                return "double";
            }
            case 9: {
                final StringBuilder stringBuilder = new StringBuilder(this.getElementType().getClassName());
                for (int i = this.getDimensions(); i > 0; --i) {
                    stringBuilder.append("[]");
                }
                return stringBuilder.toString();
            }
            case 10:
            case 12: {
                return this.valueBuffer.substring(this.valueBegin, this.valueEnd).replace('/', '.');
            }
            default: {
                throw new AssertionError();
            }
        }
    }
    
    public String getInternalName() {
        return this.valueBuffer.substring(this.valueBegin, this.valueEnd);
    }
    
    public static String getInternalName(final Class<?> clazz) {
        return clazz.getName().replace('.', '/');
    }
    
    public String getDescriptor() {
        if (this.sort == 10) {
            return this.valueBuffer.substring(this.valueBegin - 1, this.valueEnd + 1);
        }
        if (this.sort == 12) {
            return 'L' + this.valueBuffer.substring(this.valueBegin, this.valueEnd) + ';';
        }
        return this.valueBuffer.substring(this.valueBegin, this.valueEnd);
    }
    
    public static String getDescriptor(final Class<?> clazz) {
        final StringBuilder stringBuilder = new StringBuilder();
        appendDescriptor(clazz, stringBuilder);
        return stringBuilder.toString();
    }
    
    public static String getConstructorDescriptor(final Constructor<?> constructor) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('(');
        final Class<?>[] parameterTypes;
        final Class<?>[] parameters = parameterTypes = constructor.getParameterTypes();
        for (final Class<?> parameter : parameterTypes) {
            appendDescriptor(parameter, stringBuilder);
        }
        return stringBuilder.append(")V").toString();
    }
    
    public static String getMethodDescriptor(final Type returnType, final Type... argumentTypes) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('(');
        for (final Type argumentType : argumentTypes) {
            argumentType.appendDescriptor(stringBuilder);
        }
        stringBuilder.append(')');
        returnType.appendDescriptor(stringBuilder);
        return stringBuilder.toString();
    }
    
    public static String getMethodDescriptor(final Method method) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('(');
        final Class<?>[] parameterTypes;
        final Class<?>[] parameters = parameterTypes = method.getParameterTypes();
        for (final Class<?> parameter : parameterTypes) {
            appendDescriptor(parameter, stringBuilder);
        }
        stringBuilder.append(')');
        appendDescriptor(method.getReturnType(), stringBuilder);
        return stringBuilder.toString();
    }
    
    private void appendDescriptor(final StringBuilder stringBuilder) {
        if (this.sort == 10) {
            stringBuilder.append(this.valueBuffer, this.valueBegin - 1, this.valueEnd + 1);
        }
        else if (this.sort == 12) {
            stringBuilder.append('L').append(this.valueBuffer, this.valueBegin, this.valueEnd).append(';');
        }
        else {
            stringBuilder.append(this.valueBuffer, this.valueBegin, this.valueEnd);
        }
    }
    
    private static void appendDescriptor(final Class<?> clazz, final StringBuilder stringBuilder) {
        Class<?> currentClass;
        for (currentClass = clazz; currentClass.isArray(); currentClass = currentClass.getComponentType()) {
            stringBuilder.append('[');
        }
        if (currentClass.isPrimitive()) {
            char descriptor;
            if (currentClass == Integer.TYPE) {
                descriptor = 'I';
            }
            else if (currentClass == Void.TYPE) {
                descriptor = 'V';
            }
            else if (currentClass == Boolean.TYPE) {
                descriptor = 'Z';
            }
            else if (currentClass == Byte.TYPE) {
                descriptor = 'B';
            }
            else if (currentClass == Character.TYPE) {
                descriptor = 'C';
            }
            else if (currentClass == Short.TYPE) {
                descriptor = 'S';
            }
            else if (currentClass == Double.TYPE) {
                descriptor = 'D';
            }
            else if (currentClass == Float.TYPE) {
                descriptor = 'F';
            }
            else {
                if (currentClass != Long.TYPE) {
                    throw new AssertionError();
                }
                descriptor = 'J';
            }
            stringBuilder.append(descriptor);
        }
        else {
            stringBuilder.append('L').append(getInternalName(currentClass)).append(';');
        }
    }
    
    public int getSort() {
        return (this.sort == 12) ? 10 : this.sort;
    }
    
    public int getDimensions() {
        int numDimensions;
        for (numDimensions = 1; this.valueBuffer.charAt(this.valueBegin + numDimensions) == '['; ++numDimensions) {}
        return numDimensions;
    }
    
    public int getSize() {
        switch (this.sort) {
            case 0: {
                return 0;
            }
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 9:
            case 10:
            case 12: {
                return 1;
            }
            case 7:
            case 8: {
                return 2;
            }
            default: {
                throw new AssertionError();
            }
        }
    }
    
    public int getArgumentsAndReturnSizes() {
        return getArgumentsAndReturnSizes(this.getDescriptor());
    }
    
    public static int getArgumentsAndReturnSizes(final String methodDescriptor) {
        int argumentsSize = 1;
        int currentOffset = 1;
        for (int currentChar = methodDescriptor.charAt(currentOffset); currentChar != 41; currentChar = methodDescriptor.charAt(currentOffset)) {
            if (currentChar == 74 || currentChar == 68) {
                ++currentOffset;
                argumentsSize += 2;
            }
            else {
                while (methodDescriptor.charAt(currentOffset) == '[') {
                    ++currentOffset;
                }
                if (methodDescriptor.charAt(currentOffset++) == 'L') {
                    currentOffset = methodDescriptor.indexOf(59, currentOffset) + 1;
                }
                ++argumentsSize;
            }
        }
        int currentChar = methodDescriptor.charAt(currentOffset + 1);
        if (currentChar == 86) {
            return argumentsSize << 2;
        }
        final int returnSize = (currentChar == 74 || currentChar == 68) ? 2 : 1;
        return argumentsSize << 2 | returnSize;
    }
    
    public int getOpcode(final int opcode) {
        if (opcode == 46 || opcode == 79) {
            switch (this.sort) {
                case 1:
                case 3: {
                    return opcode + 5;
                }
                case 2: {
                    return opcode + 6;
                }
                case 4: {
                    return opcode + 7;
                }
                case 5: {
                    return opcode;
                }
                case 6: {
                    return opcode + 2;
                }
                case 7: {
                    return opcode + 1;
                }
                case 8: {
                    return opcode + 3;
                }
                case 9:
                case 10:
                case 12: {
                    return opcode + 4;
                }
                case 0:
                case 11: {
                    throw new UnsupportedOperationException();
                }
                default: {
                    throw new AssertionError();
                }
            }
        }
        else {
            switch (this.sort) {
                case 0: {
                    if (opcode != 172) {
                        throw new UnsupportedOperationException();
                    }
                    return 177;
                }
                case 1:
                case 2:
                case 3:
                case 4:
                case 5: {
                    return opcode;
                }
                case 6: {
                    return opcode + 2;
                }
                case 7: {
                    return opcode + 1;
                }
                case 8: {
                    return opcode + 3;
                }
                case 9:
                case 10:
                case 12: {
                    if (opcode != 21 && opcode != 54 && opcode != 172) {
                        throw new UnsupportedOperationException();
                    }
                    return opcode + 4;
                }
                case 11: {
                    throw new UnsupportedOperationException();
                }
                default: {
                    throw new AssertionError();
                }
            }
        }
    }
    
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Type)) {
            return false;
        }
        final Type other = (Type)object;
        if (((this.sort == 12) ? 10 : this.sort) != ((other.sort == 12) ? 10 : other.sort)) {
            return false;
        }
        final int begin = this.valueBegin;
        final int end = this.valueEnd;
        final int otherBegin = other.valueBegin;
        final int otherEnd = other.valueEnd;
        if (end - begin != otherEnd - otherBegin) {
            return false;
        }
        for (int i = begin, j = otherBegin; i < end; ++i, ++j) {
            if (this.valueBuffer.charAt(i) != other.valueBuffer.charAt(j)) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        int hashCode = 13 * ((this.sort == 12) ? 10 : this.sort);
        if (this.sort >= 9) {
            for (int i = this.valueBegin, end = this.valueEnd; i < end; ++i) {
                hashCode = 17 * (hashCode + this.valueBuffer.charAt(i));
            }
        }
        return hashCode;
    }
    
    @Override
    public String toString() {
        return this.getDescriptor();
    }
    
    static {
        VOID_TYPE = new Type(0, "VZCBSIFJD", 0, 1);
        BOOLEAN_TYPE = new Type(1, "VZCBSIFJD", 1, 2);
        CHAR_TYPE = new Type(2, "VZCBSIFJD", 2, 3);
        BYTE_TYPE = new Type(3, "VZCBSIFJD", 3, 4);
        SHORT_TYPE = new Type(4, "VZCBSIFJD", 4, 5);
        INT_TYPE = new Type(5, "VZCBSIFJD", 5, 6);
        FLOAT_TYPE = new Type(6, "VZCBSIFJD", 6, 7);
        LONG_TYPE = new Type(7, "VZCBSIFJD", 7, 8);
        DOUBLE_TYPE = new Type(8, "VZCBSIFJD", 8, 9);
    }
}
