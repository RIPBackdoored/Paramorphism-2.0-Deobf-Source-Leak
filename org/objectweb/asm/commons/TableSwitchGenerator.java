package org.objectweb.asm.commons;

import org.objectweb.asm.*;

public interface TableSwitchGenerator
{
    void generateCase(final int p0, final Label p1);
    
    void generateDefault();
}
