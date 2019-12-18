package kotlin.jvm.internal;

import kotlin.reflect.*;

public class MutablePropertyReference2Impl extends MutablePropertyReference2
{
    private final KDeclarationContainer owner;
    private final String name;
    private final String signature;
    
    public MutablePropertyReference2Impl(final KDeclarationContainer owner, final String name, final String signature) {
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
    public Object get(final Object o, final Object o2) {
        return this.getGetter().call(o, o2);
    }
    
    @Override
    public void set(final Object o, final Object o2, final Object o3) {
        this.getSetter().call(o, o2, o3);
    }
}
