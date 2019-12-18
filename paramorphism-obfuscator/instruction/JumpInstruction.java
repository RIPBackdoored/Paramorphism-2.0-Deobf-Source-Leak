package paramorphism-obfuscator.instruction;

import paramorphism-obfuscator.wrappers.*;
import org.jetbrains.annotations.*;
import kotlin.*;
import org.objectweb.asm.*;
import paramorphism-obfuscator.*;
import org.objectweb.asm.tree.*;

public final class JumpInstruction
{
    @NotNull
    public static final Unit addIReturn(@NotNull final IInstructionWrapper instructionWrapper) {
        instructionWrapper.getInstructions().add(new InsnNode(Opcodes.IRETURN));
        return Unit.INSTANCE;
    }
    
    @NotNull
    public static final Unit addLReturn(@NotNull final IInstructionWrapper instructionWrapper) {
        instructionWrapper.getInstructions().add(new InsnNode(Opcodes.LRETURN));
        return Unit.INSTANCE;
    }
    
    @NotNull
    public static final Unit addFReturn(@NotNull final IInstructionWrapper instructionWrapper) {
        instructionWrapper.getInstructions().add(new InsnNode(Opcodes.FRETURN));
        return Unit.INSTANCE;
    }
    
    @NotNull
    public static final Unit addDReturn(@NotNull final IInstructionWrapper instructionWrapper) {
        instructionWrapper.getInstructions().add(new InsnNode(Opcodes.DRETURN));
        return Unit.INSTANCE;
    }
    
    @NotNull
    public static final Unit addAReturn(@NotNull final IInstructionWrapper instructionWrapper) {
        instructionWrapper.getInstructions().add(new InsnNode(Opcodes.ARETURN));
        return Unit.INSTANCE;
    }
    
    @NotNull
    public static final Unit addReturn(@NotNull final IInstructionWrapper instructionWrapper) {
        instructionWrapper.getInstructions().add(new InsnNode(Opcodes.RETURN));
        return Unit.INSTANCE;
    }
    
    @NotNull
    public static final Unit addDuplicateReturn(@NotNull final IInstructionWrapper instructionWrapper) {
        return addReturn(instructionWrapper);
    }
    
    @NotNull
    public static final Unit addLongCompare(@NotNull final IInstructionWrapper instructionWrapper) {
        instructionWrapper.getInstructions().add(new InsnNode(Opcodes.LCMP));
        return Unit.INSTANCE;
    }
    
    @NotNull
    public static final Unit addFloatCompareLess(@NotNull final IInstructionWrapper instructionWrapper) {
        instructionWrapper.getInstructions().add(new InsnNode(Opcodes.FCMPL));
        return Unit.INSTANCE;
    }
    
    @NotNull
    public static final Unit addFloatCompareGreater(@NotNull final IInstructionWrapper instructionWrapper) {
        instructionWrapper.getInstructions().add(new InsnNode(Opcodes.FCMPG));
        return Unit.INSTANCE;
    }
    
    @NotNull
    public static final Unit addDoubleCompareLess(@NotNull final IInstructionWrapper instructionWrapper) {
        instructionWrapper.getInstructions().add(new InsnNode(Opcodes.DCMPL));
        return Unit.INSTANCE;
    }
    
    @NotNull
    public static final Unit addDoubleCompareGreater(@NotNull final IInstructionWrapper instructionWrapper) {
        instructionWrapper.getInstructions().add(new InsnNode(Opcodes.DCMPG));
        return Unit.INSTANCE;
    }
    
    public static final void addIfEqual(@NotNull final IInstructionWrapper instructionWrapper, @NotNull final Object o) {
        instructionWrapper.getInstructions().add(new JumpInsnNode(Opcodes.IFEQ, yj.a(o)));
    }
    
    public static final void addIfNotEqual(@NotNull final IInstructionWrapper instructionWrapper, @NotNull final Object o) {
        instructionWrapper.getInstructions().add(new JumpInsnNode(Opcodes.IFNE, yj.a(o)));
    }
    
    public static final void addIfLess(@NotNull final IInstructionWrapper instructionWrapper, @NotNull final Object o) {
        instructionWrapper.getInstructions().add(new JumpInsnNode(Opcodes.IFLT, yj.a(o)));
    }
    
    public static final void addIfGreaterEqual(@NotNull final IInstructionWrapper instructionWrapper, @NotNull final Object o) {
        instructionWrapper.getInstructions().add(new JumpInsnNode(Opcodes.IFGE, yj.a(o)));
    }
    
    public static final void addIfGreater(@NotNull final IInstructionWrapper instructionWrapper, @NotNull final Object o) {
        instructionWrapper.getInstructions().add(new JumpInsnNode(Opcodes.IFGT, yj.a(o)));
    }
    
    public static final void addIfLessEqual(@NotNull final IInstructionWrapper instructionWrapper, @NotNull final Object o) {
        instructionWrapper.getInstructions().add(new JumpInsnNode(Opcodes.IFLE, yj.a(o)));
    }
    
    public static final void addIfIntCompareEqual(@NotNull final IInstructionWrapper instructionWrapper, @NotNull final Object o) {
        instructionWrapper.getInstructions().add(new JumpInsnNode(Opcodes.IF_ICMPEQ, yj.a(o)));
    }
    
    public static final void addIfIntCompareNotEqual(@NotNull final IInstructionWrapper instructionWrapper, @NotNull final Object o) {
        instructionWrapper.getInstructions().add(new JumpInsnNode(Opcodes.IF_ICMPNE, yj.a(o)));
    }
    
    public static final void addIfIntCompareLess(@NotNull final IInstructionWrapper instructionWrapper, @NotNull final Object o) {
        instructionWrapper.getInstructions().add(new JumpInsnNode(Opcodes.IF_ICMPLT, yj.a(o)));
    }
    
    public static final void addIfIntCompareGreaterEqual(@NotNull final IInstructionWrapper instructionWrapper, @NotNull final Object o) {
        instructionWrapper.getInstructions().add(new JumpInsnNode(Opcodes.IF_ICMPGE, yj.a(o)));
    }
    
    public static final void addIfIntCompareGreater(@NotNull final IInstructionWrapper instructionWrapper, @NotNull final Object o) {
        instructionWrapper.getInstructions().add(new JumpInsnNode(Opcodes.IF_ICMPGT, yj.a(o)));
    }
    
    public static final void addIfIntCompareLessEqual(@NotNull final IInstructionWrapper instructionWrapper, @NotNull final Object o) {
        instructionWrapper.getInstructions().add(new JumpInsnNode(Opcodes.IF_ICMPLE, yj.a(o)));
    }
    
    public static final void addIfArrayCompareEqual(@NotNull final IInstructionWrapper instructionWrapper, @NotNull final Object o) {
        instructionWrapper.getInstructions().add(new JumpInsnNode(Opcodes.IF_ACMPEQ, yj.a(o)));
    }
    
    public static final void addIfArrayCompareNotEqual(@NotNull final IInstructionWrapper instructionWrapper, @NotNull final Object o) {
        instructionWrapper.getInstructions().add(new JumpInsnNode(Opcodes.IF_ACMPNE, yj.a(o)));
    }
    
    public static final void addGoto(@NotNull final IInstructionWrapper instructionWrapper, @NotNull final Object o) {
        instructionWrapper.getInstructions().add(new JumpInsnNode(Opcodes.GOTO, yj.a(o)));
    }
    
    public static final void addIfNull(@NotNull final IInstructionWrapper instructionWrapper, @NotNull final Object o) {
        instructionWrapper.getInstructions().add(new JumpInsnNode(Opcodes.IFNULL, yj.a(o)));
    }
    
    public static final void addIfNotNull(@NotNull final IInstructionWrapper instructionWrapper, @NotNull final Object o) {
        instructionWrapper.getInstructions().add(new JumpInsnNode(Opcodes.IFNONNULL, yj.a(o)));
    }
    
    public static final void addJsr(@NotNull final IInstructionWrapper instructionWrapper, @NotNull final Object o) {
        instructionWrapper.getInstructions().add(new JumpInsnNode(Opcodes.JSR, yj.a(o)));
    }
    
    public static final void addRet(@NotNull final IInstructionWrapper instructionWrapper, final int var) {
        instructionWrapper.getInstructions().add(new VarInsnNode(Opcodes.RET, var));
    }
    
    @NotNull
    public static final Unit addAThrow(@NotNull final IInstructionWrapper instructionWrapper) {
        instructionWrapper.getInstructions().add(new InsnNode(Opcodes.ATHROW));
        return Unit.INSTANCE;
    }
}
