package org.objectweb.asm.commons;

import org.objectweb.asm.*;

public final class ModuleTargetAttribute extends Attribute
{
    public String platform;
    
    public ModuleTargetAttribute(final String platform) {
        super("ModuleTarget");
        this.platform = platform;
    }
    
    public ModuleTargetAttribute() {
        this(null);
    }
    
    @Override
    protected Attribute read(final ClassReader classReader, final int offset, final int length, final char[] charBuffer, final int codeOffset, final Label[] labels) {
        return new ModuleTargetAttribute(classReader.readUTF8(offset, charBuffer));
    }
    
    @Override
    protected ByteVector write(final ClassWriter classWriter, final byte[] code, final int codeLength, final int maxStack, final int maxLocals) {
        final ByteVector byteVector = new ByteVector();
        byteVector.putShort((this.platform == null) ? 0 : classWriter.newUTF8(this.platform));
        return byteVector;
    }
}
