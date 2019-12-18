package org.objectweb.asm.tree;

import org.objectweb.asm.*;
import java.util.*;

public class MethodInsnNode extends AbstractInsnNode
{
    public String owner;
    public String name;
    public String desc;
    public boolean itf;
    
    public MethodInsnNode(final int opcode, final String owner, final String name, final String descriptor) {
        this(opcode, owner, name, descriptor, opcode == 185);
    }
    
    public MethodInsnNode(final int opcode, final String owner, final String name, final String descriptor, final boolean isInterface) {
        super(opcode);
        this.owner = owner;
        this.name = name;
        this.desc = descriptor;
        this.itf = isInterface;
    }
    
    public void setOpcode(final int opcode) {
        this.opcode = opcode;
    }
    
    @Override
    public int getType() {
        return 5;
    }
    
    @Override
    public void accept(final MethodVisitor methodVisitor) {
        methodVisitor.visitMethodInsn(this.opcode, this.owner, this.name, this.desc, this.itf);
        this.acceptAnnotations(methodVisitor);
    }
    
    @Override
    public AbstractInsnNode clone(final Map<LabelNode, LabelNode> clonedLabels) {
        return new MethodInsnNode(this.opcode, this.owner, this.name, this.desc, this.itf).cloneAnnotations(this);
    }
}
