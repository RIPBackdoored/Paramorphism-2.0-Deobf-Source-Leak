package org.eclipse.aether.util.graph.transformer;

public static final class ScopeContext
{
    String parentScope;
    String childScope;
    String derivedScope;
    
    public ScopeContext(final String s, final String s2) {
        super();
        this.parentScope = ((s != null) ? s : "");
        this.derivedScope = ((s2 != null) ? s2 : "");
        this.childScope = ((s2 != null) ? s2 : "");
    }
    
    public String getParentScope() {
        return this.parentScope;
    }
    
    public String getChildScope() {
        return this.childScope;
    }
    
    public String getDerivedScope() {
        return this.derivedScope;
    }
    
    public void setDerivedScope(final String s) {
        this.derivedScope = ((s != null) ? s : "");
    }
}
