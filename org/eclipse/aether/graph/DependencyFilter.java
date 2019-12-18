package org.eclipse.aether.graph;

import java.util.List;

public interface DependencyFilter {
   boolean accept(DependencyNode var1, List var2);
}
