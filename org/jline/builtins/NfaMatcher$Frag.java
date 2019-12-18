package org.jline.builtins;

import java.util.function.*;
import java.util.*;

private static class Frag
{
    final State start;
    final List<Consumer<State>> out;
    
    public Frag(final State start, final Collection<Consumer<State>> collection) {
        super();
        this.out = new ArrayList<Consumer<State>>();
        this.start = start;
        this.out.addAll(collection);
    }
    
    public Frag(final State start, final Collection<Consumer<State>> collection, final Collection<Consumer<State>> collection2) {
        super();
        this.out = new ArrayList<Consumer<State>>();
        this.start = start;
        this.out.addAll(collection);
        this.out.addAll(collection2);
    }
    
    public Frag(final State start, final Consumer<State> consumer) {
        super();
        this.out = new ArrayList<Consumer<State>>();
        this.start = start;
        this.out.add(consumer);
    }
    
    public Frag(final State start, final Collection<Consumer<State>> collection, final Consumer<State> consumer) {
        super();
        this.out = new ArrayList<Consumer<State>>();
        this.start = start;
        this.out.addAll(collection);
        this.out.add(consumer);
    }
    
    public void patch(final State state) {
        this.out.forEach(Frag::lambda$patch$0);
    }
    
    private static void lambda$patch$0(final State state, final Consumer consumer) {
        consumer.accept(state);
    }
}
