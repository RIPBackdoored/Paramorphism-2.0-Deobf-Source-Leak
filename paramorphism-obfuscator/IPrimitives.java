package paramorphism-obfuscator;

import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;

public interface IPrimitives {
   Type getVoid();

   Type getChar();

   Type getByte();

   Type getShort();

   Type getInt();

   Type getFloat();

   Type getLong();

   Type getDouble();

   Type getBoolean();

   @NotNull
   Type getType(@NotNull Object var1);
}
