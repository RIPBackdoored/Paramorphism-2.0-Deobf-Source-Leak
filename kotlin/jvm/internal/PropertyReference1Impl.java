package kotlin.jvm.internal;

import kotlin.reflect.*;

public class PropertyReference1Impl extends PropertyReference1
{
    private final KDeclarationContainer owner;
    private final String name;
    private final String signature;
    
    public PropertyReference1Impl(final KDeclarationContainer owner, final String name, final String signature) {
        super();
        this.owner = owner;
        this.name = name;
        this.signature = signature;
    }
    
    @Override
    public KDeclarationContainer getOwner() {
        return this.owner;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public String getSignature() {
        return this.signature;
    }
    
    @Override
    public Object get(final Object o) {
        return this.getGetter().call(o);
    }
}
