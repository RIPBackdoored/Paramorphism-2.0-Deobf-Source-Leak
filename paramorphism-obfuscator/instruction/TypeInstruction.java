package paramorphism-obfuscator.instruction;

import paramorphism-obfuscator.wrappers.*;
import org.jetbrains.annotations.*;
import org.objectweb.asm.*;
import paramorphism-obfuscator.*;
import org.objectweb.asm.tree.*;

public final class TypeInstruction
{
    public static final void addNew(@NotNull final IInstructionWrapper instructionWrapper, @NotNull final Object o) {
        instructionWrapper.getInstructions().add(new TypeInsnNode(Opcodes.NEW, Primitives.getType(o).getInternalName()));
    }
    
    public static final void addCheckCast(@NotNull final IInstructionWrapper instructionWrapper, @NotNull final Object o) {
        instructionWrapper.getInstructions().add(new TypeInsnNode(Opcodes.CHECKCAST, Primitives.getType(o).getInternalName()));
    }
    
    public static final void addInstanceOf(@NotNull final IInstructionWrapper instructionWrapper, @NotNull final Object o) {
        instructionWrapper.getInstructions().add(new TypeInsnNode(Opcodes.INSTANCEOF, Primitives.getType(o).getInternalName()));
    }
}
