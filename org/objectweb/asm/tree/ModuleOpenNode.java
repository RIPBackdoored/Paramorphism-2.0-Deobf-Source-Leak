package org.objectweb.asm.tree;

import java.util.*;
import org.objectweb.asm.*;

public class ModuleOpenNode
{
    public String packaze;
    public int access;
    public List<String> modules;
    
    public ModuleOpenNode(final String packaze, final int access, final List<String> modules) {
        super();
        this.packaze = packaze;
        this.access = access;
        this.modules = modules;
    }
    
    public void accept(final ModuleVisitor moduleVisitor) {
        moduleVisitor.visitOpen(this.packaze, this.access, (String[])((this.modules == null) ? null : ((String[])this.modules.toArray(new String[0]))));
    }
}
