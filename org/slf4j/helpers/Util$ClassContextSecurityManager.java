package org.slf4j.helpers;

private static final class ClassContextSecurityManager extends SecurityManager
{
    private ClassContextSecurityManager() {
        super();
    }
    
    @Override
    protected Class<?>[] getClassContext() {
        return (Class<?>[])super.getClassContext();
    }
    
    ClassContextSecurityManager(final Util$1 x0) {
        this();
    }
}
