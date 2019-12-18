package paramorphism-obfuscator.instruction;

import paramorphism-obfuscator.wrappers.*;
import org.jetbrains.annotations.*;
import kotlin.*;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

public final class NopInstruction
{
    @NotNull
    public static final Unit addNoOperation(@NotNull final IInstructionWrapper instructionWrapper) {
        instructionWrapper.getInstructions().add(new InsnNode(Opcodes.NOP));
        return Unit.INSTANCE;
    }
}
