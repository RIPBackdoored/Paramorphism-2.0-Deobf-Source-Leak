package org.eclipse.aether.impl;

import java.util.*;
import org.eclipse.aether.spi.locator.*;
import java.lang.reflect.*;

private class Entry<T>
{
    private final Class<T> type;
    private final Collection<Object> providers;
    private List<T> instances;
    final DefaultServiceLocator this$0;
    
    Entry(final DefaultServiceLocator this$0, final Class<T> clazz) {
        this.this$0 = this$0;
        super();
        this.type = Objects.requireNonNull(clazz, "service type cannot be null");
        this.providers = new LinkedHashSet<Object>(8);
    }
    
    public synchronized void setServices(final T... array) {
        this.providers.clear();
        if (array != null) {
            for (int length = array.length, i = 0; i < length; ++i) {
                this.providers.add(Objects.requireNonNull(array[i], "service instance cannot be null"));
            }
        }
        this.instances = null;
    }
    
    public synchronized void setService(final Class<? extends T> clazz) {
        this.providers.clear();
        this.addService(clazz);
    }
    
    public synchronized void addService(final Class<? extends T> clazz) {
        this.providers.add(Objects.requireNonNull(clazz, "implementation class cannot be null"));
        this.instances = null;
    }
    
    public T getInstance() {
        final List<T> instances = this.getInstances();
        return instances.isEmpty() ? null : instances.get(0);
    }
    
    public synchronized List<T> getInstances() {
        if (this.instances == null) {
            this.instances = new ArrayList<T>(this.providers.size());
            for (final Class<?> next : this.providers) {
                T t;
                if (next instanceof Class) {
                    t = this.newInstance(next);
                }
                else {
                    t = this.type.cast(next);
                }
                if (t != null) {
                    this.instances.add(t);
                }
            }
            this.instances = Collections.unmodifiableList((List<? extends T>)this.instances);
        }
        return this.instances;
    }
    
    private T newInstance(final Class<?> clazz) {
        try {
            final Constructor<?> declaredConstructor = clazz.getDeclaredConstructor((Class<?>[])new Class[0]);
            if (!Modifier.isPublic(declaredConstructor.getModifiers())) {
                declaredConstructor.setAccessible(true);
            }
            final T cast = this.type.cast(declaredConstructor.newInstance(new Object[0]));
            if (cast instanceof Service) {
                ((Service)cast).initService(this.this$0);
            }
            return cast;
        }
        catch (Exception | LinkageError ex) {
            final Throwable t;
            DefaultServiceLocator.access$000(this.this$0, this.type, clazz, t);
            return null;
        }
    }
}
