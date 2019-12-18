package org.yaml.snakeyaml.representer;

import org.yaml.snakeyaml.nodes.*;

protected class RepresentBoolean implements Represent
{
    final /* synthetic */ SafeRepresenter this$0;
    
    protected RepresentBoolean(final SafeRepresenter this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public Node representData(final Object data) {
        String value;
        if (Boolean.TRUE.equals(data)) {
            value = "true";
        }
        else {
            value = "false";
        }
        return this.this$0.representScalar(Tag.BOOL, value);
    }
}
