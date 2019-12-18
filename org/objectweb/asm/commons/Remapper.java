package org.objectweb.asm.commons;

import org.objectweb.asm.*;
import org.objectweb.asm.signature.*;

public abstract class Remapper
{
    public Remapper() {
        super();
    }
    
    public String mapDesc(final String descriptor) {
        return this.mapType(Type.getType(descriptor)).getDescriptor();
    }
    
    private Type mapType(final Type type) {
        switch (type.getSort()) {
            case 9: {
                final StringBuilder remappedDescriptor = new StringBuilder();
                for (int i = 0; i < type.getDimensions(); ++i) {
                    remappedDescriptor.append('[');
                }
                remappedDescriptor.append(this.mapType(type.getElementType()).getDescriptor());
                return Type.getType(remappedDescriptor.toString());
            }
            case 10: {
                final String remappedInternalName = this.map(type.getInternalName());
                return (remappedInternalName != null) ? Type.getObjectType(remappedInternalName) : type;
            }
            case 11: {
                return Type.getMethodType(this.mapMethodDesc(type.getDescriptor()));
            }
            default: {
                return type;
            }
        }
    }
    
    public String mapType(final String internalName) {
        if (internalName == null) {
            return null;
        }
        return this.mapType(Type.getObjectType(internalName)).getInternalName();
    }
    
    public String[] mapTypes(final String[] internalNames) {
        String[] remappedInternalNames = null;
        for (int i = 0; i < internalNames.length; ++i) {
            final String internalName = internalNames[i];
            final String remappedInternalName = this.mapType(internalName);
            if (remappedInternalName != null) {
                if (remappedInternalNames == null) {
                    remappedInternalNames = new String[internalNames.length];
                    System.arraycopy(internalNames, 0, remappedInternalNames, 0, internalNames.length);
                }
                remappedInternalNames[i] = remappedInternalName;
            }
        }
        return (remappedInternalNames != null) ? remappedInternalNames : internalNames;
    }
    
    public String mapMethodDesc(final String methodDescriptor) {
        if ("()V".equals(methodDescriptor)) {
            return methodDescriptor;
        }
        final StringBuilder stringBuilder = new StringBuilder("(");
        for (final Type argumentType : Type.getArgumentTypes(methodDescriptor)) {
            stringBuilder.append(this.mapType(argumentType).getDescriptor());
        }
        final Type returnType = Type.getReturnType(methodDescriptor);
        if (returnType == Type.VOID_TYPE) {
            stringBuilder.append(")V");
        }
        else {
            stringBuilder.append(')').append(this.mapType(returnType).getDescriptor());
        }
        return stringBuilder.toString();
    }
    
    public Object mapValue(final Object value) {
        if (value instanceof Type) {
            return this.mapType((Type)value);
        }
        if (value instanceof Handle) {
            final Handle handle = (Handle)value;
            return new Handle(handle.getTag(), this.mapType(handle.getOwner()), this.mapMethodName(handle.getOwner(), handle.getName(), handle.getDesc()), (handle.getTag() <= 4) ? this.mapDesc(handle.getDesc()) : this.mapMethodDesc(handle.getDesc()), handle.isInterface());
        }
        if (value instanceof ConstantDynamic) {
            final ConstantDynamic constantDynamic = (ConstantDynamic)value;
            final int bootstrapMethodArgumentCount = constantDynamic.getBootstrapMethodArgumentCount();
            final Object[] remappedBootstrapMethodArguments = new Object[bootstrapMethodArgumentCount];
            for (int i = 0; i < bootstrapMethodArgumentCount; ++i) {
                remappedBootstrapMethodArguments[i] = this.mapValue(constantDynamic.getBootstrapMethodArgument(i));
            }
            final String descriptor = constantDynamic.getDescriptor();
            return new ConstantDynamic(this.mapInvokeDynamicMethodName(constantDynamic.getName(), descriptor), this.mapDesc(descriptor), (Handle)this.mapValue(constantDynamic.getBootstrapMethod()), remappedBootstrapMethodArguments);
        }
        return value;
    }
    
    public String mapSignature(final String signature, final boolean typeSignature) {
        if (signature == null) {
            return null;
        }
        final SignatureReader signatureReader = new SignatureReader(signature);
        final SignatureWriter signatureWriter = new SignatureWriter();
        final SignatureVisitor signatureRemapper = this.createSignatureRemapper(signatureWriter);
        if (typeSignature) {
            signatureReader.acceptType(signatureRemapper);
        }
        else {
            signatureReader.accept(signatureRemapper);
        }
        return signatureWriter.toString();
    }
    
    @Deprecated
    protected SignatureVisitor createRemappingSignatureAdapter(final SignatureVisitor signatureVisitor) {
        return this.createSignatureRemapper(signatureVisitor);
    }
    
    protected SignatureVisitor createSignatureRemapper(final SignatureVisitor signatureVisitor) {
        return new SignatureRemapper(signatureVisitor, this);
    }
    
    public String mapInnerClassName(final String name, final String ownerName, final String innerName) {
        final String remappedInnerName = this.mapType(name);
        if (remappedInnerName.contains("$")) {
            int index;
            for (index = remappedInnerName.lastIndexOf(36) + 1; index < remappedInnerName.length() && Character.isDigit(remappedInnerName.charAt(index)); ++index) {}
            return remappedInnerName.substring(index);
        }
        return innerName;
    }
    
    public String mapMethodName(final String owner, final String name, final String descriptor) {
        return name;
    }
    
    public String mapInvokeDynamicMethodName(final String name, final String descriptor) {
        return name;
    }
    
    public String mapFieldName(final String owner, final String name, final String descriptor) {
        return name;
    }
    
    public String mapPackageName(final String name) {
        return name;
    }
    
    public String mapModuleName(final String name) {
        return name;
    }
    
    public String map(final String internalName) {
        return internalName;
    }
}
