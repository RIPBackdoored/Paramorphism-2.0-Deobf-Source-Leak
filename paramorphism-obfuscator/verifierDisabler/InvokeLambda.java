package paramorphism-obfuscator.verifierDisabler;

import kotlin.jvm.functions.*;
import paramorphism-obfuscator.*;
import kotlin.*;
import org.objectweb.asm.tree.*;
import org.jetbrains.annotations.*;
import java.lang.reflect.*;
import kotlin.jvm.internal.*;
import paramorphism-obfuscator.wrappers.*;
import paramorphism-obfuscator.instruction.*;
import java.util.*;

public final class InvokeLambda extends Lambda implements Function1<xj, Unit>
{
    public final ClassWrapper sj;
    public final FieldNode sk;
    public final FieldNode sl;
    public final FieldNode sm;
    
    @Override
    public Object invoke(final Object o) {
        this.a((MethodWrapper)o);
        return Unit.INSTANCE;
    }
    
    public final void a(@NotNull final MethodWrapper methodWrapper) {
        FieldInstruction.addGetStatic(methodWrapper, this.sj.getType(), this.sk.name, Reflection.getOrCreateKotlinClass(Method.class));
        NumberInstruction.addNull(methodWrapper);
        NumberInstruction.addITwo(methodWrapper);
        ArrayVarInstruction.addNewArray(methodWrapper, Reflection.getOrCreateKotlinClass(Object.class));
        StackInstruction.addDup(methodWrapper);
        NumberInstruction.addIZero(methodWrapper);
        FieldInstruction.addGetStatic(methodWrapper, this.sj.getType(), this.sl.name, Reflection.getOrCreateKotlinClass(ClassLoader.class));
        ArrayVarInstruction.addObjectArrayStore(methodWrapper);
        StackInstruction.addDup(methodWrapper);
        NumberInstruction.addIOne(methodWrapper);
        VarInstruction.addObjectLoadZero(methodWrapper);
        ArrayVarInstruction.addObjectArrayStore(methodWrapper);
        MethodInstruction.addInvokeVirtual(methodWrapper, Reflection.getOrCreateKotlinClass(Method.class), "invoke", Reflection.getOrCreateKotlinClass(Object.class), Reflection.getOrCreateKotlinClass(Object.class), Reflection.getOrCreateKotlinClass(Object[].class));
        TypeInstruction.addCheckCast(methodWrapper, Long.class);
        MethodInstruction.addInvokeVirtual(methodWrapper, Long.class, "longValue", methodWrapper.bk(), new Object[0]);
        VarInstruction.addLongStoreOne(methodWrapper);
        VarInstruction.addLongLoadOne(methodWrapper);
        NumberInstruction.addLZero(methodWrapper);
        JumpInstruction.addLongCompare(methodWrapper);
        JumpInstruction.addIfNotEqual(methodWrapper, methodWrapper.getLabels().a("non_zero"));
        TypeInstruction.addNew(methodWrapper, Reflection.getOrCreateKotlinClass(NoSuchElementException.class));
        StackInstruction.addDup(methodWrapper);
        VarInstruction.addObjectLoadZero(methodWrapper);
        MethodInstruction.addInvokeSpecial(methodWrapper, Reflection.getOrCreateKotlinClass(NoSuchElementException.class), "<init>", methodWrapper.be(), Reflection.getOrCreateKotlinClass(String.class));
        JumpInstruction.addAThrow(methodWrapper);
        methodWrapper.getLabels().a("non_zero").a();
        FieldInstruction.addGetStatic(methodWrapper, this.sj.getType(), this.sm.name, "sun/misc/Unsafe");
        VarInstruction.addLongLoadOne(methodWrapper);
        MethodInstruction.addInvokeVirtual(methodWrapper, "sun/misc/Unsafe", "getLong", methodWrapper.bk(), methodWrapper.bk());
        JumpInstruction.addLReturn(methodWrapper);
    }
    
    public InvokeLambda(final ClassWrapper sj, final FieldNode sk, final FieldNode sl, final FieldNode sm) {
        this.sj = sj;
        this.sk = sk;
        this.sl = sl;
        this.sm = sm;
        super(1);
    }
}
