package org.objectweb.asm.tree.analysis;

import java.util.*;
import org.objectweb.asm.tree.*;

public class SourceValue implements Value
{
    public final int size;
    public final Set<AbstractInsnNode> insns;
    
    public SourceValue(final int size) {
        this(size, new SmallSet<AbstractInsnNode>());
    }
    
    public SourceValue(final int size, final AbstractInsnNode insnNode) {
        super();
        this.size = size;
        this.insns = new SmallSet<AbstractInsnNode>(insnNode);
    }
    
    public SourceValue(final int size, final Set<AbstractInsnNode> insnSet) {
        super();
        this.size = size;
        this.insns = insnSet;
    }
    
    public int getSize() {
        return this.size;
    }
    
    @Override
    public boolean equals(final Object value) {
        if (!(value instanceof SourceValue)) {
            return false;
        }
        final SourceValue sourceValue = (SourceValue)value;
        return this.size == sourceValue.size && this.insns.equals(sourceValue.insns);
    }
    
    @Override
    public int hashCode() {
        return this.insns.hashCode();
    }
}
