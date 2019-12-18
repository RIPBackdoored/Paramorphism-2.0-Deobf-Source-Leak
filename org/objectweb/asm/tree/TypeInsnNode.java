package org.objectweb.asm.tree;

import org.objectweb.asm.*;
import java.util.*;

public class TypeInsnNode extends AbstractInsnNode
{
    public String desc;
    
    public TypeInsnNode(final int opcode, final String descriptor) {
        super(opcode);
        this.desc = descriptor;
    }
    
    public void setOpcode(final int opcode) {
        this.opcode = opcode;
    }
    
    @Override
    public int getType() {
        return 3;
    }
    
    @Override
    public void accept(final MethodVisitor methodVisitor) {
        methodVisitor.visitTypeInsn(this.opcode, this.desc);
        this.acceptAnnotations(methodVisitor);
    }
    
    @Override
    public AbstractInsnNode clone(final Map<LabelNode, LabelNode> clonedLabels) {
        return new TypeInsnNode(this.opcode, this.desc).cloneAnnotations(this);
    }
}
