package paramorphism-obfuscator.wrappers;

import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.InsnList;

public interface IInstructionWrapper {
   @NotNull
   InsnList getInstructions();
}
