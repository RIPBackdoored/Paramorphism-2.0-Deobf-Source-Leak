package org.objectweb.asm.tree.analysis;

import java.util.ArrayList;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.IincInsnNode;
import org.objectweb.asm.tree.InvokeDynamicInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MultiANewArrayInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

public class Frame {
   private Value returnValue;
   private Value[] values;
   private int numLocals;
   private int numStack;

   public Frame(int var1, int var2) {
      super();
      this.values = (Value[])(new Value[var1 + var2]);
      this.numLocals = var1;
   }

   public Frame(Frame var1) {
      this(var1.numLocals, var1.values.length - var1.numLocals);
      this.init(var1);
   }

   public Frame init(Frame var1) {
      this.returnValue = var1.returnValue;
      System.arraycopy(var1.values, 0, this.values, 0, this.values.length);
      this.numStack = var1.numStack;
      return this;
   }

   public void initJumpTarget(int var1, LabelNode var2) {
   }

   public void setReturn(Value var1) {
      this.returnValue = var1;
   }

   public int getLocals() {
      return this.numLocals;
   }

   public int getMaxStackSize() {
      return this.values.length - this.numLocals;
   }

   public Value getLocal(int var1) {
      if (var1 >= this.numLocals) {
         throw new IndexOutOfBoundsException("Trying to get an inexistant local variable " + var1);
      } else {
         return this.values[var1];
      }
   }

   public void setLocal(int var1, Value var2) {
      if (var1 >= this.numLocals) {
         throw new IndexOutOfBoundsException("Trying to set an inexistant local variable " + var1);
      } else {
         this.values[var1] = var2;
      }
   }

   public int getStackSize() {
      return this.numStack;
   }

   public Value getStack(int var1) {
      return this.values[this.numLocals + var1];
   }

   public void setStack(int var1, Value var2) {
      this.values[this.numLocals + var1] = var2;
   }

   public void clearStack() {
      this.numStack = 0;
   }

   public Value pop() {
      if (this.numStack == 0) {
         throw new IndexOutOfBoundsException("Cannot pop operand off an empty stack.");
      } else {
         return this.values[this.numLocals + --this.numStack];
      }
   }

   public void push(Value var1) {
      if (this.numLocals + this.numStack >= this.values.length) {
         throw new IndexOutOfBoundsException("Insufficient maximum stack size.");
      } else {
         this.values[this.numLocals + this.numStack++] = var1;
      }
   }

   public void execute(AbstractInsnNode var1, Interpreter var2) throws AnalyzerException {
      Value var3;
      Value var4;
      Value var5;
      int var7;
      switch(var1.getOpcode()) {
      case 0:
      case 167:
      case 169:
         break;
      case 1:
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
      case 16:
      case 17:
      case 18:
         this.push(var2.newOperation(var1));
         break;
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
         throw new AnalyzerException(var1, "Illegal opcode " + var1.getOpcode());
      case 21:
      case 22:
      case 23:
      case 24:
      case 25:
         this.push(var2.copyOperation(var1, this.getLocal(((VarInsnNode)var1).var)));
         break;
      case 46:
      case 47:
      case 48:
      case 49:
      case 50:
      case 51:
      case 52:
      case 53:
      case 96:
      case 97:
      case 98:
      case 99:
      case 100:
      case 101:
      case 102:
      case 103:
      case 104:
      case 105:
      case 106:
      case 107:
      case 108:
      case 109:
      case 110:
      case 111:
      case 112:
      case 113:
      case 114:
      case 115:
      case 120:
      case 121:
      case 122:
      case 123:
      case 124:
      case 125:
      case 126:
      case 127:
      case 128:
      case 129:
      case 130:
      case 131:
      case 148:
      case 149:
      case 150:
      case 151:
      case 152:
         var4 = this.pop();
         var3 = this.pop();
         this.push(var2.binaryOperation(var1, var3, var4));
         break;
      case 54:
      case 55:
      case 56:
      case 57:
      case 58:
         var3 = var2.copyOperation(var1, this.pop());
         var7 = ((VarInsnNode)var1).var;
         this.setLocal(var7, var3);
         if (var3.getSize() == 2) {
            this.setLocal(var7 + 1, var2.newEmptyValue(var7 + 1));
         }

         if (var7 > 0) {
            Value var10 = this.getLocal(var7 - 1);
            if (var10 != null && var10.getSize() == 2) {
               this.setLocal(var7 - 1, var2.newEmptyValue(var7 - 1));
            }
         }
         break;
      case 79:
      case 80:
      case 81:
      case 82:
      case 83:
      case 84:
      case 85:
      case 86:
         var5 = this.pop();
         var4 = this.pop();
         var3 = this.pop();
         var2.ternaryOperation(var1, var3, var4, var5);
         break;
      case 87:
         if (this.pop().getSize() == 2) {
            throw new AnalyzerException(var1, "Illegal use of POP");
         }
         break;
      case 88:
         if (this.pop().getSize() == 1 && this.pop().getSize() != 1) {
            throw new AnalyzerException(var1, "Illegal use of POP2");
         }
         break;
      case 89:
         var3 = this.pop();
         if (var3.getSize() != 1) {
            throw new AnalyzerException(var1, "Illegal use of DUP");
         }

         this.push(var3);
         this.push(var2.copyOperation(var1, var3));
         break;
      case 90:
         var3 = this.pop();
         var4 = this.pop();
         if (var3.getSize() != 1 || var4.getSize() != 1) {
            throw new AnalyzerException(var1, "Illegal use of DUP_X1");
         }

         this.push(var2.copyOperation(var1, var3));
         this.push(var4);
         this.push(var3);
         break;
      case 91:
         var3 = this.pop();
         if (var3.getSize() == 1) {
            var4 = this.pop();
            if (var4.getSize() != 1) {
               this.push(var2.copyOperation(var1, var3));
               this.push(var4);
               this.push(var3);
               break;
            }

            var5 = this.pop();
            if (var5.getSize() == 1) {
               this.push(var2.copyOperation(var1, var3));
               this.push(var5);
               this.push(var4);
               this.push(var3);
               break;
            }
         }

         throw new AnalyzerException(var1, "Illegal use of DUP_X2");
      case 92:
         var3 = this.pop();
         if (var3.getSize() == 1) {
            var4 = this.pop();
            if (var4.getSize() != 1) {
               throw new AnalyzerException(var1, "Illegal use of DUP2");
            }

            this.push(var4);
            this.push(var3);
            this.push(var2.copyOperation(var1, var4));
            this.push(var2.copyOperation(var1, var3));
         } else {
            this.push(var3);
            this.push(var2.copyOperation(var1, var3));
         }
         break;
      case 93:
         var3 = this.pop();
         if (var3.getSize() == 1) {
            var4 = this.pop();
            if (var4.getSize() == 1) {
               var5 = this.pop();
               if (var5.getSize() == 1) {
                  this.push(var2.copyOperation(var1, var4));
                  this.push(var2.copyOperation(var1, var3));
                  this.push(var5);
                  this.push(var4);
                  this.push(var3);
                  break;
               }
            }
         } else {
            var4 = this.pop();
            if (var4.getSize() == 1) {
               this.push(var2.copyOperation(var1, var3));
               this.push(var4);
               this.push(var3);
               break;
            }
         }

         throw new AnalyzerException(var1, "Illegal use of DUP2_X1");
      case 94:
         var3 = this.pop();
         if (var3.getSize() == 1) {
            var4 = this.pop();
            if (var4.getSize() != 1) {
               throw new AnalyzerException(var1, "Illegal use of DUP2_X2");
            }

            var5 = this.pop();
            if (var5.getSize() == 1) {
               Value var6 = this.pop();
               if (var6.getSize() != 1) {
                  throw new AnalyzerException(var1, "Illegal use of DUP2_X2");
               }

               this.push(var2.copyOperation(var1, var4));
               this.push(var2.copyOperation(var1, var3));
               this.push(var6);
               this.push(var5);
               this.push(var4);
               this.push(var3);
            } else {
               this.push(var2.copyOperation(var1, var4));
               this.push(var2.copyOperation(var1, var3));
               this.push(var5);
               this.push(var4);
               this.push(var3);
            }
         } else {
            var4 = this.pop();
            if (var4.getSize() == 1) {
               var5 = this.pop();
               if (var5.getSize() != 1) {
                  throw new AnalyzerException(var1, "Illegal use of DUP2_X2");
               }

               this.push(var2.copyOperation(var1, var3));
               this.push(var5);
               this.push(var4);
               this.push(var3);
            } else {
               this.push(var2.copyOperation(var1, var3));
               this.push(var4);
               this.push(var3);
            }
         }
         break;
      case 95:
         var4 = this.pop();
         var3 = this.pop();
         if (var3.getSize() == 1 && var4.getSize() == 1) {
            this.push(var2.copyOperation(var1, var4));
            this.push(var2.copyOperation(var1, var3));
            break;
         }

         throw new AnalyzerException(var1, "Illegal use of SWAP");
      case 116:
      case 117:
      case 118:
      case 119:
         this.push(var2.unaryOperation(var1, this.pop()));
         break;
      case 132:
         var7 = ((IincInsnNode)var1).var;
         this.setLocal(var7, var2.unaryOperation(var1, this.getLocal(var7)));
         break;
      case 133:
      case 134:
      case 135:
      case 136:
      case 137:
      case 138:
      case 139:
      case 140:
      case 141:
      case 142:
      case 143:
      case 144:
      case 145:
      case 146:
      case 147:
         this.push(var2.unaryOperation(var1, this.pop()));
         break;
      case 153:
      case 154:
      case 155:
      case 156:
      case 157:
      case 158:
         var2.unaryOperation(var1, this.pop());
         break;
      case 159:
      case 160:
      case 161:
      case 162:
      case 163:
      case 164:
      case 165:
      case 166:
      case 181:
         var4 = this.pop();
         var3 = this.pop();
         var2.binaryOperation(var1, var3, var4);
         break;
      case 168:
         this.push(var2.newOperation(var1));
         break;
      case 170:
      case 171:
         var2.unaryOperation(var1, this.pop());
         break;
      case 172:
      case 173:
      case 174:
      case 175:
      case 176:
         var3 = this.pop();
         var2.unaryOperation(var1, var3);
         var2.returnOperation(var1, var3, this.returnValue);
         break;
      case 177:
         if (this.returnValue != null) {
            throw new AnalyzerException(var1, "Incompatible return type");
         }
         break;
      case 178:
         this.push(var2.newOperation(var1));
         break;
      case 179:
         var2.unaryOperation(var1, this.pop());
         break;
      case 180:
         this.push(var2.unaryOperation(var1, this.pop()));
         break;
      case 182:
      case 183:
      case 184:
      case 185:
         this.executeInvokeInsn(var1, ((MethodInsnNode)var1).desc, var2);
         break;
      case 186:
         this.executeInvokeInsn(var1, ((InvokeDynamicInsnNode)var1).desc, var2);
         break;
      case 187:
         this.push(var2.newOperation(var1));
         break;
      case 188:
      case 189:
      case 190:
         this.push(var2.unaryOperation(var1, this.pop()));
         break;
      case 191:
         var2.unaryOperation(var1, this.pop());
         break;
      case 192:
      case 193:
         this.push(var2.unaryOperation(var1, this.pop()));
         break;
      case 194:
      case 195:
         var2.unaryOperation(var1, this.pop());
         break;
      case 197:
         ArrayList var8 = new ArrayList();

         for(int var9 = ((MultiANewArrayInsnNode)var1).dims; var9 > 0; --var9) {
            var8.add(0, this.pop());
         }

         this.push(var2.naryOperation(var1, var8));
         break;
      case 198:
      case 199:
         var2.unaryOperation(var1, this.pop());
      }

   }

   private void executeInvokeInsn(AbstractInsnNode var1, String var2, Interpreter var3) throws AnalyzerException {
      ArrayList var4 = new ArrayList();

      for(int var5 = Type.getArgumentTypes(var2).length; var5 > 0; --var5) {
         var4.add(0, this.pop());
      }

      if (var1.getOpcode() != 184 && var1.getOpcode() != 186) {
         var4.add(0, this.pop());
      }

      if (Type.getReturnType(var2) == Type.VOID_TYPE) {
         var3.naryOperation(var1, var4);
      } else {
         this.push(var3.naryOperation(var1, var4));
      }

   }

   public boolean merge(Frame var1, Interpreter var2) throws AnalyzerException {
      if (this.numStack != var1.numStack) {
         throw new AnalyzerException((AbstractInsnNode)null, "Incompatible stack heights");
      } else {
         boolean var3 = false;

         for(int var4 = 0; var4 < this.numLocals + this.numStack; ++var4) {
            Value var5 = var2.merge(this.values[var4], var1.values[var4]);
            if (!var5.equals(this.values[var4])) {
               this.values[var4] = var5;
               var3 = true;
            }
         }

         return var3;
      }
   }

   public boolean merge(Frame var1, boolean[] var2) {
      boolean var3 = false;

      for(int var4 = 0; var4 < this.numLocals; ++var4) {
         if (!var2[var4] && !this.values[var4].equals(var1.values[var4])) {
            this.values[var4] = var1.values[var4];
            var3 = true;
         }
      }

      return var3;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();

      int var2;
      for(var2 = 0; var2 < this.getLocals(); ++var2) {
         var1.append(this.getLocal(var2));
      }

      var1.append(' ');

      for(var2 = 0; var2 < this.getStackSize(); ++var2) {
         var1.append(this.getStack(var2).toString());
      }

      return var1.toString();
   }
}
