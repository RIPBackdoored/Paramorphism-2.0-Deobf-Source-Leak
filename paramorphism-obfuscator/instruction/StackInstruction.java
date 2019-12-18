package paramorphism-obfuscator.instruction;

import paramorphism-obfuscator.wrappers.*;
import org.jetbrains.annotations.*;
import kotlin.*;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

public final class StackInstruction
{
    @NotNull
    public static final Unit addPop(@NotNull final IInstructionWrapper instructionWrapper) {
        instructionWrapper.getInstructions().add(new InsnNode(Opcodes.POP));
        return Unit.INSTANCE;
    }
    
    @NotNull
    public static final Unit addPop2(@NotNull final IInstructionWrapper instructionWrapper) {
        instructionWrapper.getInstructions().add(new InsnNode(Opcodes.POP2));
        return Unit.INSTANCE;
    }
    
    @NotNull
    public static final Unit addDup(@NotNull final IInstructionWrapper instructionWrapper) {
        instructionWrapper.getInstructions().add(new InsnNode(Opcodes.DUP));
        return Unit.INSTANCE;
    }
    
    @NotNull
    public static final Unit addDupX1(@NotNull final IInstructionWrapper instructionWrapper) {
        instructionWrapper.getInstructions().add(new InsnNode(Opcodes.DUP_X1));
        return Unit.INSTANCE;
    }
    
    @NotNull
    public static final Unit addDupX2(@NotNull final IInstructionWrapper instructionWrapper) {
        instructionWrapper.getInstructions().add(new InsnNode(Opcodes.DUP_X2));
        return Unit.INSTANCE;
    }
    
    @NotNull
    public static final Unit addDup2(@NotNull final IInstructionWrapper instructionWrapper) {
        instructionWrapper.getInstructions().add(new InsnNode(Opcodes.DUP2));
        return Unit.INSTANCE;
    }
    
    @NotNull
    public static final Unit addDup2X1(@NotNull final IInstructionWrapper instructionWrapper) {
        instructionWrapper.getInstructions().add(new InsnNode(Opcodes.DUP2_X1));
        return Unit.INSTANCE;
    }
    
    @NotNull
    public static final Unit addDup2X2(@NotNull final IInstructionWrapper instructionWrapper) {
        instructionWrapper.getInstructions().add(new InsnNode(Opcodes.DUP2_X2));
        return Unit.INSTANCE;
    }
    
    @NotNull
    public static final Unit addSwap(@NotNull final IInstructionWrapper instructionWrapper) {
        instructionWrapper.getInstructions().add(new InsnNode(Opcodes.SWAP));
        return Unit.INSTANCE;
    }
}
