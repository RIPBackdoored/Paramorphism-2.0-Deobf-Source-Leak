package org.objectweb.asm;

class Frame {
   static final int SAME_FRAME = 0;
   static final int SAME_LOCALS_1_STACK_ITEM_FRAME = 64;
   static final int RESERVED = 128;
   static final int SAME_LOCALS_1_STACK_ITEM_FRAME_EXTENDED = 247;
   static final int CHOP_FRAME = 248;
   static final int SAME_FRAME_EXTENDED = 251;
   static final int APPEND_FRAME = 252;
   static final int FULL_FRAME = 255;
   static final int ITEM_TOP = 0;
   static final int ITEM_INTEGER = 1;
   static final int ITEM_FLOAT = 2;
   static final int ITEM_DOUBLE = 3;
   static final int ITEM_LONG = 4;
   static final int ITEM_NULL = 5;
   static final int ITEM_UNINITIALIZED_THIS = 6;
   static final int ITEM_OBJECT = 7;
   static final int ITEM_UNINITIALIZED = 8;
   private static final int ITEM_ASM_BOOLEAN = 9;
   private static final int ITEM_ASM_BYTE = 10;
   private static final int ITEM_ASM_CHAR = 11;
   private static final int ITEM_ASM_SHORT = 12;
   private static final int DIM_SIZE = 6;
   private static final int KIND_SIZE = 4;
   private static final int FLAGS_SIZE = 2;
   private static final int VALUE_SIZE = 20;
   private static final int DIM_SHIFT = 26;
   private static final int KIND_SHIFT = 22;
   private static final int FLAGS_SHIFT = 20;
   private static final int DIM_MASK = -67108864;
   private static final int KIND_MASK = 62914560;
   private static final int VALUE_MASK = 1048575;
   private static final int ARRAY_OF = 67108864;
   private static final int ELEMENT_OF = -67108864;
   private static final int CONSTANT_KIND = 4194304;
   private static final int REFERENCE_KIND = 8388608;
   private static final int UNINITIALIZED_KIND = 12582912;
   private static final int LOCAL_KIND = 16777216;
   private static final int STACK_KIND = 20971520;
   private static final int TOP_IF_LONG_OR_DOUBLE_FLAG = 1048576;
   private static final int TOP = 4194304;
   private static final int BOOLEAN = 4194313;
   private static final int BYTE = 4194314;
   private static final int CHAR = 4194315;
   private static final int SHORT = 4194316;
   private static final int INTEGER = 4194305;
   private static final int FLOAT = 4194306;
   private static final int LONG = 4194308;
   private static final int DOUBLE = 4194307;
   private static final int NULL = 4194309;
   private static final int UNINITIALIZED_THIS = 4194310;
   Label owner;
   private int[] inputLocals;
   private int[] inputStack;
   private int[] outputLocals;
   private int[] outputStack;
   private short outputStackStart;
   private short outputStackTop;
   private int initializationCount;
   private int[] initializations;

   Frame(Label var1) {
      super();
      this.owner = var1;
   }

   final void copyFrom(Frame var1) {
      this.inputLocals = var1.inputLocals;
      this.inputStack = var1.inputStack;
      this.outputStackStart = 0;
      this.outputLocals = var1.outputLocals;
      this.outputStack = var1.outputStack;
      this.outputStackTop = var1.outputStackTop;
      this.initializationCount = var1.initializationCount;
      this.initializations = var1.initializations;
   }

   static int getAbstractTypeFromApiFormat(SymbolTable var0, Object var1) {
      if (var1 instanceof Integer) {
         return 4194304 | (Integer)var1;
      } else if (var1 instanceof String) {
         String var2 = Type.getObjectType((String)var1).getDescriptor();
         return getAbstractTypeFromDescriptor(var0, var2, 0);
      } else {
         return 12582912 | var0.addUninitializedType("", ((Label)var1).bytecodeOffset);
      }
   }

   static int getAbstractTypeFromInternalName(SymbolTable var0, String var1) {
      return 8388608 | var0.addType(var1);
   }

   private static int getAbstractTypeFromDescriptor(SymbolTable var0, String var1, int var2) {
      String var3;
      switch(var1.charAt(var2)) {
      case 'B':
      case 'C':
      case 'I':
      case 'S':
      case 'Z':
         return 4194305;
      case 'D':
         return 4194307;
      case 'E':
      case 'G':
      case 'H':
      case 'K':
      case 'M':
      case 'N':
      case 'O':
      case 'P':
      case 'Q':
      case 'R':
      case 'T':
      case 'U':
      case 'W':
      case 'X':
      case 'Y':
      default:
         throw new IllegalArgumentException();
      case 'F':
         return 4194306;
      case 'J':
         return 4194308;
      case 'L':
         var3 = var1.substring(var2 + 1, var1.length() - 1);
         return 8388608 | var0.addType(var3);
      case 'V':
         return 0;
      case '[':
         int var4;
         for(var4 = var2 + 1; var1.charAt(var4) == '['; ++var4) {
         }

         int var5;
         switch(var1.charAt(var4)) {
         case 'B':
            var5 = 4194314;
            break;
         case 'C':
            var5 = 4194315;
            break;
         case 'D':
            var5 = 4194307;
            break;
         case 'E':
         case 'G':
         case 'H':
         case 'K':
         case 'M':
         case 'N':
         case 'O':
         case 'P':
         case 'Q':
         case 'R':
         case 'T':
         case 'U':
         case 'V':
         case 'W':
         case 'X':
         case 'Y':
         default:
            throw new IllegalArgumentException();
         case 'F':
            var5 = 4194306;
            break;
         case 'I':
            var5 = 4194305;
            break;
         case 'J':
            var5 = 4194308;
            break;
         case 'L':
            var3 = var1.substring(var4 + 1, var1.length() - 1);
            var5 = 8388608 | var0.addType(var3);
            break;
         case 'S':
            var5 = 4194316;
            break;
         case 'Z':
            var5 = 4194313;
         }

         return var4 - var2 << 26 | var5;
      }
   }

   final void setInputFrameFromDescriptor(SymbolTable var1, int var2, String var3, int var4) {
      this.inputLocals = new int[var4];
      this.inputStack = new int[0];
      int var5 = 0;
      if ((var2 & 8) == 0) {
         if ((var2 & 262144) == 0) {
            this.inputLocals[var5++] = 8388608 | var1.addType(var1.getClassName());
         } else {
            this.inputLocals[var5++] = 4194310;
         }
      }

      Type[] var6 = Type.getArgumentTypes(var3);
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         Type var9 = var6[var8];
         int var10 = getAbstractTypeFromDescriptor(var1, var9.getDescriptor(), 0);
         this.inputLocals[var5++] = var10;
         if (var10 == 4194308 || var10 == 4194307) {
            this.inputLocals[var5++] = 4194304;
         }
      }

      while(var5 < var4) {
         this.inputLocals[var5++] = 4194304;
      }

   }

   final void setInputFrameFromApiFormat(SymbolTable var1, int var2, Object[] var3, int var4, Object[] var5) {
      int var6 = 0;

      int var7;
      for(var7 = 0; var7 < var2; ++var7) {
         this.inputLocals[var6++] = getAbstractTypeFromApiFormat(var1, var3[var7]);
         if (var3[var7] == Opcodes.LONG || var3[var7] == Opcodes.DOUBLE) {
            this.inputLocals[var6++] = 4194304;
         }
      }

      while(var6 < this.inputLocals.length) {
         this.inputLocals[var6++] = 4194304;
      }

      var7 = 0;

      int var8;
      for(var8 = 0; var8 < var4; ++var8) {
         if (var5[var8] == Opcodes.LONG || var5[var8] == Opcodes.DOUBLE) {
            ++var7;
         }
      }

      this.inputStack = new int[var4 + var7];
      var8 = 0;

      for(int var9 = 0; var9 < var4; ++var9) {
         this.inputStack[var8++] = getAbstractTypeFromApiFormat(var1, var5[var9]);
         if (var5[var9] == Opcodes.LONG || var5[var9] == Opcodes.DOUBLE) {
            this.inputStack[var8++] = 4194304;
         }
      }

      this.outputStackTop = 0;
      this.initializationCount = 0;
   }

   final int getInputStackSize() {
      return this.inputStack.length;
   }

   private int getLocal(int var1) {
      if (this.outputLocals != null && var1 < this.outputLocals.length) {
         int var2 = this.outputLocals[var1];
         if (var2 == 0) {
            var2 = this.outputLocals[var1] = 16777216 | var1;
         }

         return var2;
      } else {
         return 16777216 | var1;
      }
   }

   private void setLocal(int var1, int var2) {
      if (this.outputLocals == null) {
         this.outputLocals = new int[10];
      }

      int var3 = this.outputLocals.length;
      if (var1 >= var3) {
         int[] var4 = new int[Math.max(var1 + 1, 2 * var3)];
         System.arraycopy(this.outputLocals, 0, var4, 0, var3);
         this.outputLocals = var4;
      }

      this.outputLocals[var1] = var2;
   }

   private void push(int var1) {
      if (this.outputStack == null) {
         this.outputStack = new int[10];
      }

      int var2 = this.outputStack.length;
      if (this.outputStackTop >= var2) {
         int[] var3 = new int[Math.max(this.outputStackTop + 1, 2 * var2)];
         System.arraycopy(this.outputStack, 0, var3, 0, var2);
         this.outputStack = var3;
      }

      int[] var10000 = this.outputStack;
      short var10003 = this.outputStackTop;
      this.outputStackTop = (short)(var10003 + 1);
      var10000[var10003] = var1;
      short var4 = (short)(this.outputStackStart + this.outputStackTop);
      if (var4 > this.owner.outputStackMax) {
         this.owner.outputStackMax = var4;
      }

   }

   private void push(SymbolTable var1, String var2) {
      int var3 = var2.charAt(0) == '(' ? Type.getReturnTypeOffset(var2) : 0;
      int var4 = getAbstractTypeFromDescriptor(var1, var2, var3);
      if (var4 != 0) {
         this.push(var4);
         if (var4 == 4194308 || var4 == 4194307) {
            this.push(4194304);
         }
      }

   }

   private int pop() {
      return this.outputStackTop > 0 ? this.outputStack[--this.outputStackTop] : 20971520 | -(--this.outputStackStart);
   }

   private void pop(int var1) {
      if (this.outputStackTop >= var1) {
         this.outputStackTop = (short)(this.outputStackTop - var1);
      } else {
         this.outputStackStart = (short)(this.outputStackStart - (var1 - this.outputStackTop));
         this.outputStackTop = 0;
      }

   }

   private void pop(String var1) {
      char var2 = var1.charAt(0);
      if (var2 == '(') {
         this.pop((Type.getArgumentsAndReturnSizes(var1) >> 2) - 1);
      } else if (var2 != 'J' && var2 != 'D') {
         this.pop(1);
      } else {
         this.pop(2);
      }

   }

   private void addInitializedType(int var1) {
      if (this.initializations == null) {
         this.initializations = new int[2];
      }

      int var2 = this.initializations.length;
      if (this.initializationCount >= var2) {
         int[] var3 = new int[Math.max(this.initializationCount + 1, 2 * var2)];
         System.arraycopy(this.initializations, 0, var3, 0, var2);
         this.initializations = var3;
      }

      this.initializations[this.initializationCount++] = var1;
   }

   private int getInitializedType(SymbolTable var1, int var2) {
      if (var2 == 4194310 || (var2 & -4194304) == 12582912) {
         for(int var3 = 0; var3 < this.initializationCount; ++var3) {
            int var4 = this.initializations[var3];
            int var5 = var4 & -67108864;
            int var6 = var4 & 62914560;
            int var7 = var4 & 1048575;
            if (var6 == 16777216) {
               var4 = var5 + this.inputLocals[var7];
            } else if (var6 == 20971520) {
               var4 = var5 + this.inputStack[this.inputStack.length - var7];
            }

            if (var2 == var4) {
               if (var2 == 4194310) {
                  return 8388608 | var1.addType(var1.getClassName());
               }

               return 8388608 | var1.addType(var1.getType(var2 & 1048575).value);
            }
         }
      }

      return var2;
   }

   void execute(int var1, int var2, Symbol var3, SymbolTable var4) {
      int var5;
      int var6;
      int var7;
      int var11;
      switch(var1) {
      case 0:
      case 116:
      case 117:
      case 118:
      case 119:
      case 145:
      case 146:
      case 147:
      case 167:
      case 177:
         break;
      case 1:
         this.push(4194309);
         break;
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 16:
      case 17:
      case 21:
         this.push(4194305);
         break;
      case 9:
      case 10:
      case 22:
         this.push(4194308);
         this.push(4194304);
         break;
      case 11:
      case 12:
      case 13:
      case 23:
         this.push(4194306);
         break;
      case 14:
      case 15:
      case 24:
         this.push(4194307);
         this.push(4194304);
         break;
      case 18:
         switch(var3.tag) {
         case 3:
            this.push(4194305);
            return;
         case 4:
            this.push(4194306);
            return;
         case 5:
            this.push(4194308);
            this.push(4194304);
            return;
         case 6:
            this.push(4194307);
            this.push(4194304);
            return;
         case 7:
            this.push(8388608 | var4.addType("java/lang/Class"));
            return;
         case 8:
            this.push(8388608 | var4.addType("java/lang/String"));
            return;
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         default:
            throw new AssertionError();
         case 15:
            this.push(8388608 | var4.addType("java/lang/invoke/MethodHandle"));
            return;
         case 16:
            this.push(8388608 | var4.addType("java/lang/invoke/MethodType"));
            return;
         case 17:
            this.push(var4, var3.value);
            return;
         }
      case 19:
      case 20:
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
      case 59:
      case 60:
      case 61:
      case 62:
      case 63:
      case 64:
      case 65:
      case 66:
      case 67:
      case 68:
      case 69:
      case 70:
      case 71:
      case 72:
      case 73:
      case 74:
      case 75:
      case 76:
      case 77:
      case 78:
      case 196:
      default:
         throw new IllegalArgumentException();
      case 25:
         this.push(this.getLocal(var2));
         break;
      case 46:
      case 51:
      case 52:
      case 53:
      case 96:
      case 100:
      case 104:
      case 108:
      case 112:
      case 120:
      case 122:
      case 124:
      case 126:
      case 128:
      case 130:
      case 136:
      case 142:
      case 149:
      case 150:
         this.pop(2);
         this.push(4194305);
         break;
      case 47:
      case 143:
         this.pop(2);
         this.push(4194308);
         this.push(4194304);
         break;
      case 48:
      case 98:
      case 102:
      case 106:
      case 110:
      case 114:
      case 137:
      case 144:
         this.pop(2);
         this.push(4194306);
         break;
      case 49:
      case 138:
         this.pop(2);
         this.push(4194307);
         this.push(4194304);
         break;
      case 50:
         this.pop(1);
         var5 = this.pop();
         this.push(var5 == 4194309 ? var5 : -67108864 + var5);
         break;
      case 54:
      case 56:
      case 58:
         var5 = this.pop();
         this.setLocal(var2, var5);
         if (var2 > 0) {
            var11 = this.getLocal(var2 - 1);
            if (var11 != 4194308 && var11 != 4194307) {
               if ((var11 & 62914560) == 16777216 || (var11 & 62914560) == 20971520) {
                  this.setLocal(var2 - 1, var11 | 1048576);
               }
            } else {
               this.setLocal(var2 - 1, 4194304);
            }
         }
         break;
      case 55:
      case 57:
         this.pop(1);
         var5 = this.pop();
         this.setLocal(var2, var5);
         this.setLocal(var2 + 1, 4194304);
         if (var2 > 0) {
            var11 = this.getLocal(var2 - 1);
            if (var11 != 4194308 && var11 != 4194307) {
               if ((var11 & 62914560) == 16777216 || (var11 & 62914560) == 20971520) {
                  this.setLocal(var2 - 1, var11 | 1048576);
               }
            } else {
               this.setLocal(var2 - 1, 4194304);
            }
         }
         break;
      case 79:
      case 81:
      case 83:
      case 84:
      case 85:
      case 86:
         this.pop(3);
         break;
      case 80:
      case 82:
         this.pop(4);
         break;
      case 87:
      case 153:
      case 154:
      case 155:
      case 156:
      case 157:
      case 158:
      case 170:
      case 171:
      case 172:
      case 174:
      case 176:
      case 191:
      case 194:
      case 195:
      case 198:
      case 199:
         this.pop(1);
         break;
      case 88:
      case 159:
      case 160:
      case 161:
      case 162:
      case 163:
      case 164:
      case 165:
      case 166:
      case 173:
      case 175:
         this.pop(2);
         break;
      case 89:
         var5 = this.pop();
         this.push(var5);
         this.push(var5);
         break;
      case 90:
         var5 = this.pop();
         var6 = this.pop();
         this.push(var5);
         this.push(var6);
         this.push(var5);
         break;
      case 91:
         var5 = this.pop();
         var6 = this.pop();
         var7 = this.pop();
         this.push(var5);
         this.push(var7);
         this.push(var6);
         this.push(var5);
         break;
      case 92:
         var5 = this.pop();
         var6 = this.pop();
         this.push(var6);
         this.push(var5);
         this.push(var6);
         this.push(var5);
         break;
      case 93:
         var5 = this.pop();
         var6 = this.pop();
         var7 = this.pop();
         this.push(var6);
         this.push(var5);
         this.push(var7);
         this.push(var6);
         this.push(var5);
         break;
      case 94:
         var5 = this.pop();
         var6 = this.pop();
         var7 = this.pop();
         int var8 = this.pop();
         this.push(var6);
         this.push(var5);
         this.push(var8);
         this.push(var7);
         this.push(var6);
         this.push(var5);
         break;
      case 95:
         var5 = this.pop();
         var6 = this.pop();
         this.push(var5);
         this.push(var6);
         break;
      case 97:
      case 101:
      case 105:
      case 109:
      case 113:
      case 127:
      case 129:
      case 131:
         this.pop(4);
         this.push(4194308);
         this.push(4194304);
         break;
      case 99:
      case 103:
      case 107:
      case 111:
      case 115:
         this.pop(4);
         this.push(4194307);
         this.push(4194304);
         break;
      case 121:
      case 123:
      case 125:
         this.pop(3);
         this.push(4194308);
         this.push(4194304);
         break;
      case 132:
         this.setLocal(var2, 4194305);
         break;
      case 133:
      case 140:
         this.pop(1);
         this.push(4194308);
         this.push(4194304);
         break;
      case 134:
         this.pop(1);
         this.push(4194306);
         break;
      case 135:
      case 141:
         this.pop(1);
         this.push(4194307);
         this.push(4194304);
         break;
      case 139:
      case 190:
      case 193:
         this.pop(1);
         this.push(4194305);
         break;
      case 148:
      case 151:
      case 152:
         this.pop(4);
         this.push(4194305);
         break;
      case 168:
      case 169:
         throw new IllegalArgumentException("JSR/RET are not supported with computeFrames option");
      case 178:
         this.push(var4, var3.value);
         break;
      case 179:
         this.pop(var3.value);
         break;
      case 180:
         this.pop(1);
         this.push(var4, var3.value);
         break;
      case 181:
         this.pop(var3.value);
         this.pop();
         break;
      case 182:
      case 183:
      case 184:
      case 185:
         this.pop(var3.value);
         if (var1 != 184) {
            var5 = this.pop();
            if (var1 == 183 && var3.name.charAt(0) == '<') {
               this.addInitializedType(var5);
            }
         }

         this.push(var4, var3.value);
         break;
      case 186:
         this.pop(var3.value);
         this.push(var4, var3.value);
         break;
      case 187:
         this.push(12582912 | var4.addUninitializedType(var3.value, var2));
         break;
      case 188:
         this.pop();
         switch(var2) {
         case 4:
            this.push(71303177);
            return;
         case 5:
            this.push(71303179);
            return;
         case 6:
            this.push(71303170);
            return;
         case 7:
            this.push(71303171);
            return;
         case 8:
            this.push(71303178);
            return;
         case 9:
            this.push(71303180);
            return;
         case 10:
            this.push(71303169);
            return;
         case 11:
            this.push(71303172);
            return;
         default:
            throw new IllegalArgumentException();
         }
      case 189:
         String var9 = var3.value;
         this.pop();
         if (var9.charAt(0) == '[') {
            this.push(var4, '[' + var9);
         } else {
            this.push(75497472 | var4.addType(var9));
         }
         break;
      case 192:
         String var10 = var3.value;
         this.pop();
         if (var10.charAt(0) == '[') {
            this.push(var4, var10);
         } else {
            this.push(8388608 | var4.addType(var10));
         }
         break;
      case 197:
         this.pop(var2);
         this.push(var4, var3.value);
      }

   }

   private int getConcreteOutputType(int var1, int var2) {
      int var3 = var1 & -67108864;
      int var4 = var1 & 62914560;
      int var5;
      if (var4 == 16777216) {
         var5 = var3 + this.inputLocals[var1 & 1048575];
         if ((var1 & 1048576) != 0 && (var5 == 4194308 || var5 == 4194307)) {
            var5 = 4194304;
         }

         return var5;
      } else if (var4 != 20971520) {
         return var1;
      } else {
         var5 = var3 + this.inputStack[var2 - (var1 & 1048575)];
         if ((var1 & 1048576) != 0 && (var5 == 4194308 || var5 == 4194307)) {
            var5 = 4194304;
         }

         return var5;
      }
   }

   final boolean merge(SymbolTable var1, Frame var2, int var3) {
      boolean var4 = false;
      int var5 = this.inputLocals.length;
      int var6 = this.inputStack.length;
      if (var2.inputLocals == null) {
         var2.inputLocals = new int[var5];
         var4 = true;
      }

      int var7;
      int var8;
      int var9;
      for(var7 = 0; var7 < var5; ++var7) {
         if (this.outputLocals != null && var7 < this.outputLocals.length) {
            var9 = this.outputLocals[var7];
            if (var9 == 0) {
               var8 = this.inputLocals[var7];
            } else {
               var8 = this.getConcreteOutputType(var9, var6);
            }
         } else {
            var8 = this.inputLocals[var7];
         }

         if (this.initializations != null) {
            var8 = this.getInitializedType(var1, var8);
         }

         var4 |= merge(var1, var8, var2.inputLocals, var7);
      }

      if (var3 <= 0) {
         var7 = this.inputStack.length + this.outputStackStart;
         if (var2.inputStack == null) {
            var2.inputStack = new int[var7 + this.outputStackTop];
            var4 = true;
         }

         for(var8 = 0; var8 < var7; ++var8) {
            var9 = this.inputStack[var8];
            if (this.initializations != null) {
               var9 = this.getInitializedType(var1, var9);
            }

            var4 |= merge(var1, var9, var2.inputStack, var8);
         }

         for(var8 = 0; var8 < this.outputStackTop; ++var8) {
            var9 = this.outputStack[var8];
            int var10 = this.getConcreteOutputType(var9, var6);
            if (this.initializations != null) {
               var10 = this.getInitializedType(var1, var10);
            }

            var4 |= merge(var1, var10, var2.inputStack, var7 + var8);
         }

         return var4;
      } else {
         for(var7 = 0; var7 < var5; ++var7) {
            var4 |= merge(var1, this.inputLocals[var7], var2.inputLocals, var7);
         }

         if (var2.inputStack == null) {
            var2.inputStack = new int[1];
            var4 = true;
         }

         var4 |= merge(var1, var3, var2.inputStack, 0);
         return var4;
      }
   }

   private static boolean merge(SymbolTable var0, int var1, int[] var2, int var3) {
      int var4 = var2[var3];
      if (var4 == var1) {
         return false;
      } else {
         int var5 = var1;
         if ((var1 & 67108863) == 4194309) {
            if (var4 == 4194309) {
               return false;
            }

            var5 = 4194309;
         }

         if (var4 == 0) {
            var2[var3] = var5;
            return true;
         } else {
            int var6;
            if ((var4 & -67108864) == 0 && (var4 & 62914560) != 8388608) {
               if (var4 == 4194309) {
                  var6 = (var5 & -67108864) == 0 && (var5 & 62914560) != 8388608 ? 4194304 : var5;
               } else {
                  var6 = 4194304;
               }
            } else {
               if (var5 == 4194309) {
                  return false;
               }

               int var7;
               if ((var5 & -4194304) == (var4 & -4194304)) {
                  if ((var4 & 62914560) == 8388608) {
                     var6 = var5 & -67108864 | 8388608 | var0.addMergedType(var5 & 1048575, var4 & 1048575);
                  } else {
                     var7 = -67108864 + (var5 & -67108864);
                     var6 = var7 | 8388608 | var0.addType("java/lang/Object");
                  }
               } else if ((var5 & -67108864) == 0 && (var5 & 62914560) != 8388608) {
                  var6 = 4194304;
               } else {
                  var7 = var5 & -67108864;
                  if (var7 != 0 && (var5 & 62914560) != 8388608) {
                     var7 += -67108864;
                  }

                  int var8 = var4 & -67108864;
                  if (var8 != 0 && (var4 & 62914560) != 8388608) {
                     var8 += -67108864;
                  }

                  var6 = Math.min(var7, var8) | 8388608 | var0.addType("java/lang/Object");
               }
            }

            if (var6 != var4) {
               var2[var3] = var6;
               return true;
            } else {
               return false;
            }
         }
      }
   }

   final void accept(MethodWriter var1) {
      int[] var2 = this.inputLocals;
      int var3 = 0;
      int var4 = 0;
      int var5 = 0;

      while(var5 < var2.length) {
         int var6 = var2[var5];
         var5 += var6 != 4194308 && var6 != 4194307 ? 1 : 2;
         if (var6 == 4194304) {
            ++var4;
         } else {
            var3 += var4 + 1;
            var4 = 0;
         }
      }

      int[] var10 = this.inputStack;
      int var7 = 0;

      int var8;
      for(var5 = 0; var5 < var10.length; ++var7) {
         var8 = var10[var5];
         var5 += var8 != 4194308 && var8 != 4194307 ? 1 : 2;
      }

      var8 = var1.visitFrameStart(this.owner.bytecodeOffset, var3, var7);
      var5 = 0;

      int var9;
      while(var3-- > 0) {
         var9 = var2[var5];
         var5 += var9 != 4194308 && var9 != 4194307 ? 1 : 2;
         var1.visitAbstractType(var8++, var9);
      }

      var5 = 0;

      while(var7-- > 0) {
         var9 = var10[var5];
         var5 += var9 != 4194308 && var9 != 4194307 ? 1 : 2;
         var1.visitAbstractType(var8++, var9);
      }

      var1.visitFrameEnd();
   }

   static void putAbstractType(SymbolTable var0, int var1, ByteVector var2) {
      int var3 = (var1 & -67108864) >> 26;
      if (var3 == 0) {
         int var4 = var1 & 1048575;
         switch(var1 & 62914560) {
         case 4194304:
            var2.putByte(var4);
            break;
         case 8388608:
            var2.putByte(7).putShort(var0.addConstantClass(var0.getType(var4).value).index);
            break;
         case 12582912:
            var2.putByte(8).putShort((int)var0.getType(var4).data);
            break;
         default:
            throw new AssertionError();
         }
      } else {
         StringBuilder var5 = new StringBuilder();

         while(var3-- > 0) {
            var5.append('[');
         }

         if ((var1 & 62914560) == 8388608) {
            var5.append('L').append(var0.getType(var1 & 1048575).value).append(';');
         } else {
            switch(var1 & 1048575) {
            case 1:
               var5.append('I');
               break;
            case 2:
               var5.append('F');
               break;
            case 3:
               var5.append('D');
               break;
            case 4:
               var5.append('J');
               break;
            case 5:
            case 6:
            case 7:
            case 8:
            default:
               throw new AssertionError();
            case 9:
               var5.append('Z');
               break;
            case 10:
               var5.append('B');
               break;
            case 11:
               var5.append('C');
               break;
            case 12:
               var5.append('S');
            }
         }

         var2.putByte(7).putShort(var0.addConstantClass(var5.toString()).index);
      }

   }
}
