package org.yaml.snakeyaml.constructor;

import org.yaml.snakeyaml.nodes.*;
import java.util.*;
import org.yaml.snakeyaml.error.*;

public class ConstructYamlSeq implements Construct
{
    final /* synthetic */ SafeConstructor this$0;
    
    public ConstructYamlSeq(final SafeConstructor this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public Object construct(final Node node) {
        final SequenceNode seqNode = (SequenceNode)node;
        if (node.isTwoStepsConstruction()) {
            return this.this$0.createDefaultList(seqNode.getValue().size());
        }
        return this.this$0.constructSequence(seqNode);
    }
    
    @Override
    public void construct2ndStep(final Node node, final Object data) {
        if (node.isTwoStepsConstruction()) {
            this.this$0.constructSequenceStep2((SequenceNode)node, (Collection<Object>)data);
            return;
        }
        throw new YAMLException("Unexpected recursive sequence structure. Node: " + node);
    }
}
