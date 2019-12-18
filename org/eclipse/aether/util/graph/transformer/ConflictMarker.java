package org.eclipse.aether.util.graph.transformer;

import org.eclipse.aether.collection.*;
import org.eclipse.aether.*;
import java.util.*;
import org.eclipse.aether.artifact.*;
import org.eclipse.aether.graph.*;

public final class ConflictMarker implements DependencyGraphTransformer
{
    public ConflictMarker() {
        super();
    }
    
    @Override
    public DependencyNode transformGraph(final DependencyNode dependencyNode, final DependencyGraphTransformationContext dependencyGraphTransformationContext) throws RepositoryException {
        final Map map = (Map)dependencyGraphTransformationContext.get(TransformationContextKeys.STATS);
        final long nanoTime = System.nanoTime();
        final IdentityHashMap<Object, Object> identityHashMap = new IdentityHashMap<Object, Object>(1024);
        final HashMap<Object, ConflictGroup> hashMap = new HashMap<Object, ConflictGroup>(1024);
        this.analyze(dependencyNode, (Map<DependencyNode, Object>)identityHashMap, hashMap, new int[] { 0 });
        final long nanoTime2 = System.nanoTime();
        dependencyGraphTransformationContext.put(TransformationContextKeys.CONFLICT_IDS, this.mark((Collection<DependencyNode>)identityHashMap.keySet(), hashMap));
        if (map != null) {
            final long nanoTime3 = System.nanoTime();
            map.put("ConflictMarker.analyzeTime", nanoTime2 - nanoTime);
            map.put("ConflictMarker.markTime", nanoTime3 - nanoTime2);
            map.put("ConflictMarker.nodeCount", identityHashMap.size());
        }
        return dependencyNode;
    }
    
    private void analyze(final DependencyNode dependencyNode, final Map<DependencyNode, Object> map, final Map<Object, ConflictGroup> map2, final int[] array) {
        if (map.put(dependencyNode, Boolean.TRUE) != null) {
            return;
        }
        final Set<Object> keys = this.getKeys(dependencyNode);
        if (!keys.isEmpty()) {
            ConflictGroup conflictGroup = null;
            boolean b = false;
            final Iterator<Object> iterator = keys.iterator();
            while (iterator.hasNext()) {
                final ConflictGroup conflictGroup2 = map2.get(iterator.next());
                if (conflictGroup != conflictGroup2) {
                    if (conflictGroup == null) {
                        final Set<Object> merge = this.merge(conflictGroup2.keys, keys);
                        if (merge == conflictGroup2.keys) {
                            conflictGroup = conflictGroup2;
                            break;
                        }
                        conflictGroup = new ConflictGroup(merge, array[0]++);
                        b = true;
                    }
                    else if (conflictGroup2 == null) {
                        b = true;
                    }
                    else {
                        final Set<Object> merge2 = this.merge(conflictGroup2.keys, conflictGroup.keys);
                        if (merge2 == conflictGroup2.keys) {
                            conflictGroup = conflictGroup2;
                            b = false;
                            break;
                        }
                        if (merge2 == conflictGroup.keys) {
                            continue;
                        }
                        conflictGroup = new ConflictGroup(merge2, array[0]++);
                        b = true;
                    }
                }
            }
            if (conflictGroup == null) {
                conflictGroup = new ConflictGroup(keys, array[0]++);
                b = true;
            }
            if (b) {
                final Iterator<Object> iterator2 = conflictGroup.keys.iterator();
                while (iterator2.hasNext()) {
                    map2.put(iterator2.next(), conflictGroup);
                }
            }
        }
        final Iterator<DependencyNode> iterator3 = dependencyNode.getChildren().iterator();
        while (iterator3.hasNext()) {
            this.analyze(iterator3.next(), map, map2, array);
        }
    }
    
    private Set<Object> merge(final Set<Object> set, final Set<Object> set2) {
        if (set.size() < set2.size()) {
            if (set2.containsAll(set)) {
                return set2;
            }
        }
        else if (set.containsAll(set2)) {
            return set;
        }
        final HashSet<Object> set3 = new HashSet<Object>();
        set3.addAll(set);
        set3.addAll(set2);
        return set3;
    }
    
    private Set<Object> getKeys(final DependencyNode dependencyNode) {
        final Dependency dependency = dependencyNode.getDependency();
        Set<Object> set;
        if (dependency == null) {
            set = Collections.emptySet();
        }
        else {
            final Object key = toKey(dependency.getArtifact());
            if (dependencyNode.getRelocations().isEmpty() && dependencyNode.getAliases().isEmpty()) {
                set = Collections.singleton(key);
            }
            else {
                set = new HashSet<Object>();
                set.add(key);
                final Iterator<? extends Artifact> iterator = dependencyNode.getRelocations().iterator();
                while (iterator.hasNext()) {
                    set.add(toKey((Artifact)iterator.next()));
                }
                final Iterator<? extends Artifact> iterator2 = dependencyNode.getAliases().iterator();
                while (iterator2.hasNext()) {
                    set.add(toKey((Artifact)iterator2.next()));
                }
            }
        }
        return set;
    }
    
    private Map<DependencyNode, Object> mark(final Collection<DependencyNode> collection, final Map<Object, ConflictGroup> map) {
        final IdentityHashMap<DependencyNode, Integer> identityHashMap = (IdentityHashMap<DependencyNode, Integer>)new IdentityHashMap<DependencyNode, Object>(collection.size() + 1);
        for (final DependencyNode dependencyNode : collection) {
            final Dependency dependency = dependencyNode.getDependency();
            if (dependency != null) {
                identityHashMap.put(dependencyNode, map.get(toKey(dependency.getArtifact())).index);
            }
        }
        return (Map<DependencyNode, Object>)identityHashMap;
    }
    
    private static Object toKey(final Artifact artifact) {
        return new Key(artifact);
    }
    
    static class ConflictGroup
    {
        final Set<Object> keys;
        final int index;
        
        ConflictGroup(final Set<Object> keys, final int index) {
            super();
            this.keys = keys;
            this.index = index;
        }
        
        @Override
        public String toString() {
            return String.valueOf(this.keys);
        }
    }
    
    static class Key
    {
        private final Artifact artifact;
        
        Key(final Artifact artifact) {
            super();
            this.artifact = artifact;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof Key)) {
                return false;
            }
            final Key key = (Key)o;
            return this.artifact.getArtifactId().equals(key.artifact.getArtifactId()) && this.artifact.getGroupId().equals(key.artifact.getGroupId()) && this.artifact.getExtension().equals(key.artifact.getExtension()) && this.artifact.getClassifier().equals(key.artifact.getClassifier());
        }
        
        @Override
        public int hashCode() {
            return (((17 * 31 + this.artifact.getArtifactId().hashCode()) * 31 + this.artifact.getGroupId().hashCode()) * 31 + this.artifact.getClassifier().hashCode()) * 31 + this.artifact.getExtension().hashCode();
        }
        
        @Override
        public String toString() {
            return this.artifact.getGroupId() + ':' + this.artifact.getArtifactId() + ':' + this.artifact.getClassifier() + ':' + this.artifact.getExtension();
        }
    }
}
