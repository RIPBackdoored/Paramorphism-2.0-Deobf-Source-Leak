package org.slf4j.helpers;

import java.util.*;

class BasicMDCAdapter$1 extends InheritableThreadLocal<Map<String, String>> {
    final /* synthetic */ BasicMDCAdapter this$0;
    
    BasicMDCAdapter$1(final BasicMDCAdapter this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    protected Map<String, String> childValue(final Map<String, String> parentValue) {
        if (parentValue == null) {
            return null;
        }
        return new HashMap<String, String>(parentValue);
    }
    
    @Override
    protected /* bridge */ Object childValue(final Object o) {
        return this.childValue((Map<String, String>)o);
    }
}