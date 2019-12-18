package paramorphism-obfuscator.wrappers;

import org.objectweb.asm.tree.*;
import org.jetbrains.annotations.*;
import paramorphism-obfuscator.*;
import java.util.*;

public final class LabelsWrapper
{
    private Map<String, LabelNode> labels;
    private final IInstructionWrapper INSTANCE;
    
    @NotNull
    public final LabelsWrapper a(@NotNull final IInstructionWrapper instructionWrapper) {
        final LabelsWrapper labelsWrapper = new LabelsWrapper(instructionWrapper);
        labelsWrapper.labels = this.labels;
        return labelsWrapper;
    }
    
    @NotNull
    public final LabelsWrapper b(@NotNull final IInstructionWrapper instructionWrapper) {
        final LabelsWrapper labelsWrapper = new LabelsWrapper(instructionWrapper);
        labelsWrapper.labels.putAll(this.labels);
        return labelsWrapper;
    }
    
    @NotNull
    public final yg a(final int n) {
        return this.a("label_" + n);
    }
    
    @NotNull
    public final yg a(@NotNull final String s) {
        final IInstructionWrapper instance = this.INSTANCE;
        final Map<String, LabelNode> labels = this.labels;
        final IInstructionWrapper instructionWrapper = instance;
        final LabelNode value = labels.get(s);
        LabelNode labelNode2;
        if (value == null) {
            final LabelNode labelNode = new LabelNode();
            labels.put(s, labelNode);
            labelNode2 = labelNode;
        }
        else {
            labelNode2 = value;
        }
        return new yg(instructionWrapper, labelNode2);
    }
    
    public LabelsWrapper(@NotNull final IInstructionWrapper instance) {
        super();
        this.INSTANCE = instance;
        this.labels = new LinkedHashMap<String, LabelNode>();
    }
}
