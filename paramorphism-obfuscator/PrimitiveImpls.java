package paramorphism-obfuscator;

import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;

public final class PrimitiveImpls {
   public static Type getVoid(IPrimitives var0) {
      return Primitives.getVoid();
   }

   public static Type getChar(IPrimitives var0) {
      return Primitives.getChar();
   }

   public static Type getByte(IPrimitives var0) {
      return Primitives.getByte();
   }

   public static Type getShort(IPrimitives var0) {
      return Primitives.getShort();
   }

   public static Type getInt(IPrimitives var0) {
      return Primitives.getInt();
   }

   public static Type getFloat(IPrimitives var0) {
      return Primitives.getFloat();
   }

   public static Type getLong(IPrimitives var0) {
      return Primitives.getLong();
   }

   public static Type getDouble(IPrimitives var0) {
      return Primitives.getDouble();
   }

   public static Type getBoolean(IPrimitives var0) {
      return Primitives.getBoolean();
   }

   @NotNull
   public static Type getType(IPrimitives var0, @NotNull Object var1) {
      return Primitives.getType(var1);
   }
}
