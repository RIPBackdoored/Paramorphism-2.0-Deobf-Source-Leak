package org.objectweb.asm.tree;

import org.objectweb.asm.*;
import java.util.*;

public class LineNumberNode extends AbstractInsnNode
{
    public int line;
    public LabelNode start;
    
    public LineNumberNode(final int line, final LabelNode start) {
        super(-1);
        this.line = line;
        this.start = start;
    }
    
    @Override
    public int getType() {
        return 15;
    }
    
    @Override
    public void accept(final MethodVisitor methodVisitor) {
        methodVisitor.visitLineNumber(this.line, this.start.getLabel());
    }
    
    @Override
    public AbstractInsnNode clone(final Map<LabelNode, LabelNode> clonedLabels) {
        return new LineNumberNode(this.line, AbstractInsnNode.clone(this.start, clonedLabels));
    }
}
