package paramorphism-obfuscator.verifierDisabler;

import kotlin.jvm.functions.*;
import paramorphism-obfuscator.*;
import kotlin.*;
import org.objectweb.asm.tree.*;
import org.jetbrains.annotations.*;
import paramorphism-obfuscator.wrappers.*;
import kotlin.jvm.internal.*;
import java.util.*;
import paramorphism-obfuscator.instruction.*;

public final class PutUnsafeLambda extends Lambda implements Function1<xj, Unit>
{
    public final ClassWrapper sz;
    public final String ta;
    public final FieldNode tb;
    public final String tc;
    
    @Override
    public Object invoke(final Object o) {
        this.a((MethodWrapper)o);
        return Unit.INSTANCE;
    }
    
    public final void a(@NotNull final MethodWrapper methodWrapper) {
        final String[] array = { "gHotSpotVMTypes", "gHotSpotVMTypeEntryTypeNameOffset", "gHotSpotVMTypeEntrySuperclassNameOffset", "gHotSpotVMTypeEntryIsOopTypeOffset", "gHotSpotVMTypeEntryIsIntegerTypeOffset", "gHotSpotVMTypeEntryIsUnsignedOffset", "gHotSpotVMTypeEntrySizeOffset", "gHotSpotVMTypeEntryArrayStride" };
        for (int length = array.length, i = 0; i < length; ++i) {
            NumberInstruction.addLdc(methodWrapper, array[i]);
            MethodInstruction.addInvokeStatic(methodWrapper, this.sz.getType(), this.ta, methodWrapper.bk(), Reflection.getOrCreateKotlinClass(String.class));
            VarInstruction.addLongStore(methodWrapper, 2 + 2 * i);
        }
        methodWrapper.getLabels().a(8).a();
        FieldInstruction.addGetStatic(methodWrapper, this.sz.getType(), this.tb.name, "sun/misc/Unsafe");
        VarInstruction.addLongLoadTwo(methodWrapper);
        VarInstruction.addLongLoad(methodWrapper, 4);
        MathInstruction.addLAdd(methodWrapper);
        MethodInstruction.addInvokeVirtual(methodWrapper, "sun/misc/Unsafe", "getLong", methodWrapper.bk(), methodWrapper.bk());
        MethodInstruction.addInvokeStatic(methodWrapper, this.sz.getType(), this.tc, Reflection.getOrCreateKotlinClass(String.class), methodWrapper.bk());
        VarInstruction.addObjectStore(methodWrapper, 18);
        VarInstruction.addObjectLoad(methodWrapper, 18);
        JumpInstruction.addIfNotNull(methodWrapper, methodWrapper.getLabels().a(10));
        JumpInstruction.addGoto(methodWrapper, methodWrapper.getLabels().a(11));
        methodWrapper.getLabels().a(10).a();
        FieldInstruction.addGetStatic(methodWrapper, this.sz.getType(), this.tb.name, "sun/misc/Unsafe");
        VarInstruction.addLongLoadTwo(methodWrapper);
        VarInstruction.addLongLoad(methodWrapper, 6);
        MathInstruction.addLAdd(methodWrapper);
        MethodInstruction.addInvokeVirtual(methodWrapper, "sun/misc/Unsafe", "getLong", methodWrapper.bk(), methodWrapper.bk());
        MethodInstruction.addInvokeStatic(methodWrapper, this.sz.getType(), this.tc, Reflection.getOrCreateKotlinClass(String.class), methodWrapper.bk());
        VarInstruction.addObjectStore(methodWrapper, 19);
        FieldInstruction.addGetStatic(methodWrapper, this.sz.getType(), this.tb.name, "sun/misc/Unsafe");
        VarInstruction.addLongLoadTwo(methodWrapper);
        VarInstruction.addLongLoad(methodWrapper, 8);
        MathInstruction.addLAdd(methodWrapper);
        MethodInstruction.addInvokeVirtual(methodWrapper, "sun/misc/Unsafe", "getInt", methodWrapper.bi(), methodWrapper.bk());
        JumpInstruction.addIfEqual(methodWrapper, methodWrapper.getLabels().a(13));
        NumberInstruction.addIOne(methodWrapper);
        JumpInstruction.addGoto(methodWrapper, methodWrapper.getLabels().a(14));
        methodWrapper.getLabels().a(13).a();
        NumberInstruction.addIZero(methodWrapper);
        methodWrapper.getLabels().a(14).a();
        VarInstruction.addIntStore(methodWrapper, 20);
        FieldInstruction.addGetStatic(methodWrapper, this.sz.getType(), this.tb.name, "sun/misc/Unsafe");
        VarInstruction.addLongLoadTwo(methodWrapper);
        VarInstruction.addLongLoad(methodWrapper, 10);
        MathInstruction.addLAdd(methodWrapper);
        MethodInstruction.addInvokeVirtual(methodWrapper, "sun/misc/Unsafe", "getInt", methodWrapper.bi(), methodWrapper.bk());
        JumpInstruction.addIfEqual(methodWrapper, methodWrapper.getLabels().a(16));
        NumberInstruction.addIOne(methodWrapper);
        JumpInstruction.addGoto(methodWrapper, methodWrapper.getLabels().a(17));
        methodWrapper.getLabels().a(16).a();
        NumberInstruction.addIZero(methodWrapper);
        methodWrapper.getLabels().a(17).a();
        VarInstruction.addIntStore(methodWrapper, 21);
        FieldInstruction.addGetStatic(methodWrapper, this.sz.getType(), this.tb.name, "sun/misc/Unsafe");
        VarInstruction.addLongLoadTwo(methodWrapper);
        VarInstruction.addLongLoad(methodWrapper, 12);
        MathInstruction.addLAdd(methodWrapper);
        MethodInstruction.addInvokeVirtual(methodWrapper, "sun/misc/Unsafe", "getInt", methodWrapper.bi(), methodWrapper.bk());
        JumpInstruction.addIfEqual(methodWrapper, methodWrapper.getLabels().a(19));
        NumberInstruction.addIOne(methodWrapper);
        JumpInstruction.addGoto(methodWrapper, methodWrapper.getLabels().a(20));
        methodWrapper.getLabels().a(19).a();
        NumberInstruction.addIZero(methodWrapper);
        methodWrapper.getLabels().a(20).a();
        VarInstruction.addIntStore(methodWrapper, 22);
        FieldInstruction.addGetStatic(methodWrapper, this.sz.getType(), this.tb.name, "sun/misc/Unsafe");
        VarInstruction.addLongLoadTwo(methodWrapper);
        VarInstruction.addLongLoad(methodWrapper, 14);
        MathInstruction.addLAdd(methodWrapper);
        MethodInstruction.addInvokeVirtual(methodWrapper, "sun/misc/Unsafe", "getInt", methodWrapper.bi(), methodWrapper.bk());
        VarInstruction.addIntStore(methodWrapper, 23);
        VarInstruction.addObjectLoadOne(methodWrapper);
        VarInstruction.addObjectLoad(methodWrapper, 18);
        MethodInstruction.addInvokeInterface(methodWrapper, Reflection.getOrCreateKotlinClass(Map.class), "get", Reflection.getOrCreateKotlinClass(Object.class), Reflection.getOrCreateKotlinClass(Object.class));
        TypeInstruction.addCheckCast(methodWrapper, Reflection.getOrCreateKotlinClass(Set.class));
        VarInstruction.addObjectStore(methodWrapper, 24);
        VarInstruction.addObjectLoadZero(methodWrapper);
        VarInstruction.addObjectLoad(methodWrapper, 18);
        IntegerInstruction.addInteger(methodWrapper, 7);
        ArrayVarInstruction.addNewArray(methodWrapper, Reflection.getOrCreateKotlinClass(Object.class));
        StackInstruction.addDup(methodWrapper);
        NumberInstruction.addIZero(methodWrapper);
        VarInstruction.addObjectLoad(methodWrapper, 18);
        ArrayVarInstruction.addObjectArrayStore(methodWrapper);
        StackInstruction.addDup(methodWrapper);
        NumberInstruction.addIOne(methodWrapper);
        VarInstruction.addObjectLoad(methodWrapper, 19);
        ArrayVarInstruction.addObjectArrayStore(methodWrapper);
        StackInstruction.addDup(methodWrapper);
        NumberInstruction.addITwo(methodWrapper);
        VarInstruction.addIntLoad(methodWrapper, 23);
        MethodInstruction.addInvokeStatic(methodWrapper, Integer.class, "valueOf", Integer.class, methodWrapper.bi());
        ArrayVarInstruction.addObjectArrayStore(methodWrapper);
        StackInstruction.addDup(methodWrapper);
        NumberInstruction.addIThree(methodWrapper);
        VarInstruction.addIntLoad(methodWrapper, 20);
        MethodInstruction.addInvokeStatic(methodWrapper, Boolean.class, "valueOf", Boolean.class, methodWrapper.bm());
        ArrayVarInstruction.addObjectArrayStore(methodWrapper);
        StackInstruction.addDup(methodWrapper);
        NumberInstruction.addIFour(methodWrapper);
        VarInstruction.addIntLoad(methodWrapper, 21);
        MethodInstruction.addInvokeStatic(methodWrapper, Boolean.class, "valueOf", Boolean.class, methodWrapper.bm());
        ArrayVarInstruction.addObjectArrayStore(methodWrapper);
        StackInstruction.addDup(methodWrapper);
        NumberInstruction.addIFive(methodWrapper);
        VarInstruction.addIntLoad(methodWrapper, 22);
        MethodInstruction.addInvokeStatic(methodWrapper, Boolean.class, "valueOf", Boolean.class, methodWrapper.bm());
        ArrayVarInstruction.addObjectArrayStore(methodWrapper);
        StackInstruction.addDup(methodWrapper);
        IntegerInstruction.addInteger(methodWrapper, 6);
        VarInstruction.addObjectLoad(methodWrapper, 24);
        ArrayVarInstruction.addObjectArrayStore(methodWrapper);
        MethodInstruction.addInvokeInterface(methodWrapper, Reflection.getOrCreateKotlinClass(Map.class), "put", Reflection.getOrCreateKotlinClass(Object.class), Reflection.getOrCreateKotlinClass(Object.class), Reflection.getOrCreateKotlinClass(Object.class));
        StackInstruction.addPop(methodWrapper);
        VarInstruction.addLongLoadTwo(methodWrapper);
        VarInstruction.addLongLoad(methodWrapper, 16);
        MathInstruction.addLAdd(methodWrapper);
        VarInstruction.addLongStoreTwo(methodWrapper);
        JumpInstruction.addGoto(methodWrapper, methodWrapper.getLabels().a(8));
        methodWrapper.getLabels().a(11).a();
        JumpInstruction.addDuplicateReturn(methodWrapper);
    }
    
    public PutUnsafeLambda(final ClassWrapper sz, final String ta, final FieldNode tb, final String tc) {
        this.sz = sz;
        this.ta = ta;
        this.tb = tb;
        this.tc = tc;
        super(1);
    }
}
