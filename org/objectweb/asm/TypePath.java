package org.objectweb.asm;

public final class TypePath
{
    public static final int ARRAY_ELEMENT = 0;
    public static final int INNER_TYPE = 1;
    public static final int WILDCARD_BOUND = 2;
    public static final int TYPE_ARGUMENT = 3;
    private final byte[] typePathContainer;
    private final int typePathOffset;
    
    TypePath(final byte[] typePathContainer, final int typePathOffset) {
        super();
        this.typePathContainer = typePathContainer;
        this.typePathOffset = typePathOffset;
    }
    
    public int getLength() {
        return this.typePathContainer[this.typePathOffset];
    }
    
    public int getStep(final int index) {
        return this.typePathContainer[this.typePathOffset + 2 * index + 1];
    }
    
    public int getStepArgument(final int index) {
        return this.typePathContainer[this.typePathOffset + 2 * index + 2];
    }
    
    public static TypePath fromString(final String typePath) {
        if (typePath == null || typePath.length() == 0) {
            return null;
        }
        final int typePathLength = typePath.length();
        final ByteVector output = new ByteVector(typePathLength);
        output.putByte(0);
        int typePathIndex = 0;
        while (typePathIndex < typePathLength) {
            char c = typePath.charAt(typePathIndex++);
            if (c == '[') {
                output.put11(0, 0);
            }
            else if (c == '.') {
                output.put11(1, 0);
            }
            else if (c == '*') {
                output.put11(2, 0);
            }
            else {
                if (c < '0' || c > '9') {
                    throw new IllegalArgumentException();
                }
                int typeArg = c - '0';
                while (typePathIndex < typePathLength) {
                    c = typePath.charAt(typePathIndex++);
                    if (c >= '0' && c <= '9') {
                        typeArg = typeArg * 10 + c - 48;
                    }
                    else {
                        if (c == ';') {
                            break;
                        }
                        throw new IllegalArgumentException();
                    }
                }
                output.put11(3, typeArg);
            }
        }
        output.data[0] = (byte)(output.length / 2);
        return new TypePath(output.data, 0);
    }
    
    @Override
    public String toString() {
        final int length = this.getLength();
        final StringBuilder result = new StringBuilder(length * 2);
        for (int i = 0; i < length; ++i) {
            switch (this.getStep(i)) {
                case 0: {
                    result.append('[');
                    break;
                }
                case 1: {
                    result.append('.');
                    break;
                }
                case 2: {
                    result.append('*');
                    break;
                }
                case 3: {
                    result.append(this.getStepArgument(i)).append(';');
                    break;
                }
                default: {
                    throw new AssertionError();
                }
            }
        }
        return result.toString();
    }
    
    static void put(final TypePath typePath, final ByteVector output) {
        if (typePath == null) {
            output.putByte(0);
        }
        else {
            final int length = typePath.typePathContainer[typePath.typePathOffset] * 2 + 1;
            output.putByteArray(typePath.typePathContainer, typePath.typePathOffset, length);
        }
    }
}
