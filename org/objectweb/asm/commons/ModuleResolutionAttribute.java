package org.objectweb.asm.commons;

import org.objectweb.asm.*;

public final class ModuleResolutionAttribute extends Attribute
{
    public static final int RESOLUTION_DO_NOT_RESOLVE_BY_DEFAULT = 1;
    public static final int RESOLUTION_WARN_DEPRECATED = 2;
    public static final int RESOLUTION_WARN_DEPRECATED_FOR_REMOVAL = 4;
    public static final int RESOLUTION_WARN_INCUBATING = 8;
    public int resolution;
    
    public ModuleResolutionAttribute(final int resolution) {
        super("ModuleResolution");
        this.resolution = resolution;
    }
    
    public ModuleResolutionAttribute() {
        this(0);
    }
    
    @Override
    protected Attribute read(final ClassReader classReader, final int offset, final int length, final char[] charBuffer, final int codeOffset, final Label[] labels) {
        return new ModuleResolutionAttribute(classReader.readUnsignedShort(offset));
    }
    
    @Override
    protected ByteVector write(final ClassWriter classWriter, final byte[] code, final int codeLength, final int maxStack, final int maxLocals) {
        final ByteVector byteVector = new ByteVector();
        byteVector.putShort(this.resolution);
        return byteVector;
    }
}
