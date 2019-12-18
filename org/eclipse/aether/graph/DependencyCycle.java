package org.eclipse.aether.graph;

import java.util.List;

public interface DependencyCycle {
   List getPrecedingDependencies();

   List getCyclicDependencies();
}
