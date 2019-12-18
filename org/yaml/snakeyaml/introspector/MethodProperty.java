package org.yaml.snakeyaml.introspector;

import java.beans.*;
import org.yaml.snakeyaml.error.*;

public class MethodProperty extends GenericProperty
{
    private final PropertyDescriptor property;
    private final boolean readable;
    private final boolean writable;
    
    public MethodProperty(final PropertyDescriptor property) {
        super(property.getName(), property.getPropertyType(), (property.getReadMethod() == null) ? null : property.getReadMethod().getGenericReturnType());
        this.property = property;
        this.readable = (property.getReadMethod() != null);
        this.writable = (property.getWriteMethod() != null);
    }
    
    @Override
    public void set(final Object o, final Object o2) throws Exception {
        this.property.getWriteMethod().invoke(o, o2);
    }
    
    @Override
    public Object get(final Object o) {
        try {
            this.property.getReadMethod().setAccessible(true);
            return this.property.getReadMethod().invoke(o, new Object[0]);
        }
        catch (Exception ex) {
            throw new YAMLException("Unable to find getter for property '" + this.property.getName() + "' on object " + o + ":" + ex);
        }
    }
    
    @Override
    public boolean isWritable() {
        return this.writable;
    }
    
    @Override
    public boolean isReadable() {
        return this.readable;
    }
}
