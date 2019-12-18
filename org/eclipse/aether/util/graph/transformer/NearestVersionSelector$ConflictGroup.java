package org.eclipse.aether.util.graph.transformer;

import org.eclipse.aether.version.*;
import java.util.*;

static final class ConflictGroup
{
    final Collection<VersionConstraint> constraints;
    final Collection<ConflictResolver.ConflictItem> candidates;
    ConflictResolver.ConflictItem winner;
    
    ConflictGroup() {
        super();
        this.constraints = new HashSet<VersionConstraint>();
        this.candidates = new ArrayList<ConflictResolver.ConflictItem>(64);
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.winner);
    }
}
