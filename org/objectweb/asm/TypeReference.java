package org.objectweb.asm;

public class TypeReference {
   public static final int CLASS_TYPE_PARAMETER = 0;
   public static final int METHOD_TYPE_PARAMETER = 1;
   public static final int CLASS_EXTENDS = 16;
   public static final int CLASS_TYPE_PARAMETER_BOUND = 17;
   public static final int METHOD_TYPE_PARAMETER_BOUND = 18;
   public static final int FIELD = 19;
   public static final int METHOD_RETURN = 20;
   public static final int METHOD_RECEIVER = 21;
   public static final int METHOD_FORMAL_PARAMETER = 22;
   public static final int THROWS = 23;
   public static final int LOCAL_VARIABLE = 64;
   public static final int RESOURCE_VARIABLE = 65;
   public static final int EXCEPTION_PARAMETER = 66;
   public static final int INSTANCEOF = 67;
   public static final int NEW = 68;
   public static final int CONSTRUCTOR_REFERENCE = 69;
   public static final int METHOD_REFERENCE = 70;
   public static final int CAST = 71;
   public static final int CONSTRUCTOR_INVOCATION_TYPE_ARGUMENT = 72;
   public static final int METHOD_INVOCATION_TYPE_ARGUMENT = 73;
   public static final int CONSTRUCTOR_REFERENCE_TYPE_ARGUMENT = 74;
   public static final int METHOD_REFERENCE_TYPE_ARGUMENT = 75;
   private final int targetTypeAndInfo;

   public TypeReference(int var1) {
      super();
      this.targetTypeAndInfo = var1;
   }

   public static TypeReference newTypeReference(int var0) {
      return new TypeReference(var0 << 24);
   }

   public static TypeReference newTypeParameterReference(int var0, int var1) {
      return new TypeReference(var0 << 24 | var1 << 16);
   }

   public static TypeReference newTypeParameterBoundReference(int var0, int var1, int var2) {
      return new TypeReference(var0 << 24 | var1 << 16 | var2 << 8);
   }

   public static TypeReference newSuperTypeReference(int var0) {
      return new TypeReference(268435456 | (var0 & '\uffff') << 8);
   }

   public static TypeReference newFormalParameterReference(int var0) {
      return new TypeReference(369098752 | var0 << 16);
   }

   public static TypeReference newExceptionReference(int var0) {
      return new TypeReference(385875968 | var0 << 8);
   }

   public static TypeReference newTryCatchReference(int var0) {
      return new TypeReference(1107296256 | var0 << 8);
   }

   public static TypeReference newTypeArgumentReference(int var0, int var1) {
      return new TypeReference(var0 << 24 | var1);
   }

   public int getSort() {
      return this.targetTypeAndInfo >>> 24;
   }

   public int getTypeParameterIndex() {
      return (this.targetTypeAndInfo & 16711680) >> 16;
   }

   public int getTypeParameterBoundIndex() {
      return (this.targetTypeAndInfo & '\uff00') >> 8;
   }

   public int getSuperTypeIndex() {
      return (short)((this.targetTypeAndInfo & 16776960) >> 8);
   }

   public int getFormalParameterIndex() {
      return (this.targetTypeAndInfo & 16711680) >> 16;
   }

   public int getExceptionIndex() {
      return (this.targetTypeAndInfo & 16776960) >> 8;
   }

   public int getTryCatchBlockIndex() {
      return (this.targetTypeAndInfo & 16776960) >> 8;
   }

   public int getTypeArgumentIndex() {
      return this.targetTypeAndInfo & 255;
   }

   public int getValue() {
      return this.targetTypeAndInfo;
   }

   static void putTarget(int var0, ByteVector var1) {
      switch(var0 >>> 24) {
      case 0:
      case 1:
      case 22:
         var1.putShort(var0 >>> 16);
         break;
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
      case 15:
      case 24:
      case 25:
      case 26:
      case 27:
      case 28:
      case 29:
      case 30:
      case 31:
      case 32:
      case 33:
      case 34:
      case 35:
      case 36:
      case 37:
      case 38:
      case 39:
      case 40:
      case 41:
      case 42:
      case 43:
      case 44:
      case 45:
      case 46:
      case 47:
      case 48:
      case 49:
      case 50:
      case 51:
      case 52:
      case 53:
      case 54:
      case 55:
      case 56:
      case 57:
      case 58:
      case 59:
      case 60:
      case 61:
      case 62:
      case 63:
      case 64:
      case 65:
      default:
         throw new IllegalArgumentException();
      case 16:
      case 17:
      case 18:
      case 23:
      case 66:
      case 67:
      case 68:
      case 69:
      case 70:
         var1.put12(var0 >>> 24, (var0 & 16776960) >> 8);
         break;
      case 19:
      case 20:
      case 21:
         var1.putByte(var0 >>> 24);
         break;
      case 71:
      case 72:
      case 73:
      case 74:
      case 75:
         var1.putInt(var0);
      }

   }
}
