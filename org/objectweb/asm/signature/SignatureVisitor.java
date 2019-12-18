package org.objectweb.asm.signature;

public abstract class SignatureVisitor
{
    public static final char EXTENDS = '+';
    public static final char SUPER = '-';
    public static final char INSTANCEOF = '=';
    protected final int api;
    
    public SignatureVisitor(final int api) {
        super();
        if (api != 458752 && api != 393216 && api != 327680 && api != 262144) {
            throw new IllegalArgumentException("Unsupported api " + api);
        }
        this.api = api;
    }
    
    public void visitFormalTypeParameter(final String name) {
    }
    
    public SignatureVisitor visitClassBound() {
        return this;
    }
    
    public SignatureVisitor visitInterfaceBound() {
        return this;
    }
    
    public SignatureVisitor visitSuperclass() {
        return this;
    }
    
    public SignatureVisitor visitInterface() {
        return this;
    }
    
    public SignatureVisitor visitParameterType() {
        return this;
    }
    
    public SignatureVisitor visitReturnType() {
        return this;
    }
    
    public SignatureVisitor visitExceptionType() {
        return this;
    }
    
    public void visitBaseType(final char descriptor) {
    }
    
    public void visitTypeVariable(final String name) {
    }
    
    public SignatureVisitor visitArrayType() {
        return this;
    }
    
    public void visitClassType(final String name) {
    }
    
    public void visitInnerClassType(final String name) {
    }
    
    public void visitTypeArgument() {
    }
    
    public SignatureVisitor visitTypeArgument(final char wildcard) {
        return this;
    }
    
    public void visitEnd() {
    }
}
