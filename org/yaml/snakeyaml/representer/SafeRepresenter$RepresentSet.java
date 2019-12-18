package org.yaml.snakeyaml.representer;

import org.yaml.snakeyaml.nodes.*;
import java.util.*;

protected class RepresentSet implements Represent
{
    final /* synthetic */ SafeRepresenter this$0;
    
    protected RepresentSet(final SafeRepresenter this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public Node representData(final Object data) {
        final Map<Object, Object> value = new LinkedHashMap<Object, Object>();
        final Set<Object> set = (Set<Object>)data;
        for (final Object key : set) {
            value.put(key, null);
        }
        return this.this$0.representMapping(this.this$0.getTag(data.getClass(), Tag.SET), value, null);
    }
}
