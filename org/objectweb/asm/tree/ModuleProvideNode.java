package org.objectweb.asm.tree;

import java.util.*;
import org.objectweb.asm.*;

public class ModuleProvideNode
{
    public String service;
    public List<String> providers;
    
    public ModuleProvideNode(final String service, final List<String> providers) {
        super();
        this.service = service;
        this.providers = providers;
    }
    
    public void accept(final ModuleVisitor moduleVisitor) {
        moduleVisitor.visitProvide(this.service, (String[])this.providers.toArray(new String[0]));
    }
}
