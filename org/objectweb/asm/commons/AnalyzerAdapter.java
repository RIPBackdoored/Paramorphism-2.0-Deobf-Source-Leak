package org.objectweb.asm.commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.objectweb.asm.ConstantDynamic;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class AnalyzerAdapter extends MethodVisitor {
   public List locals;
   public List stack;
   private List labels;
   public Map uninitializedTypes;
   private int maxStack;
   private int maxLocals;
   private String owner;

   public AnalyzerAdapter(String var1, int var2, String var3, String var4, MethodVisitor var5) {
      this(458752, var1, var2, var3, var4, var5);
      if (this.getClass() != AnalyzerAdapter.class) {
         throw new IllegalStateException();
      }
   }

   protected AnalyzerAdapter(int var1, String var2, int var3, String var4, String var5, MethodVisitor var6) {
      super(var1, var6);
      this.owner = var2;
      this.locals = new ArrayList();
      this.stack = new ArrayList();
      this.uninitializedTypes = new HashMap();
      if ((var3 & 8) == 0) {
         if ("<init>".equals(var4)) {
            this.locals.add(Opcodes.UNINITIALIZED_THIS);
         } else {
            this.locals.add(var2);
         }
      }

      Type[] var7 = Type.getArgumentTypes(var5);
      int var8 = var7.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         Type var10 = var7[var9];
         switch(var10.getSort()) {
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
            this.locals.add(Opcodes.INTEGER);
            break;
         case 6:
            this.locals.add(Opcodes.FLOAT);
            break;
         case 7:
            this.locals.add(Opcodes.LONG);
            this.locals.add(Opcodes.TOP);
            break;
         case 8:
            this.locals.add(Opcodes.DOUBLE);
            this.locals.add(Opcodes.TOP);
            break;
         case 9:
            this.locals.add(var10.getDescriptor());
            break;
         case 10:
            this.locals.add(var10.getInternalName());
            break;
         default:
            throw new AssertionError();
         }
      }

      this.maxLocals = this.locals.size();
   }

   public void visitFrame(int var1, int var2, Object[] var3, int var4, Object[] var5) {
      if (var1 != -1) {
         throw new IllegalArgumentException("AnalyzerAdapter only accepts expanded frames (see ClassReader.EXPAND_FRAMES)");
      } else {
         super.visitFrame(var1, var2, var3, var4, var5);
         if (this.locals != null) {
            this.locals.clear();
            this.stack.clear();
         } else {
            this.locals = new ArrayList();
            this.stack = new ArrayList();
         }

         visitFrameTypes(var2, var3, this.locals);
         visitFrameTypes(var4, var5, this.stack);
         this.maxLocals = Math.max(this.maxLocals, this.locals.size());
         this.maxStack = Math.max(this.maxStack, this.stack.size());
      }
   }

   private static void visitFrameTypes(int var0, Object[] var1, List var2) {
      for(int var3 = 0; var3 < var0; ++var3) {
         Object var4 = var1[var3];
         var2.add(var4);
         if (var4 == Opcodes.LONG || var4 == Opcodes.DOUBLE) {
            var2.add(Opcodes.TOP);
         }
      }

   }

   public void visitInsn(int var1) {
      super.visitInsn(var1);
      this.execute(var1, 0, (String)null);
      if (var1 >= 172 && var1 <= 177 || var1 == 191) {
         this.locals = null;
         this.stack = null;
      }

   }

   public void visitIntInsn(int var1, int var2) {
      super.visitIntInsn(var1, var2);
      this.execute(var1, var2, (String)null);
   }

   public void visitVarInsn(int var1, int var2) {
      super.visitVarInsn(var1, var2);
      boolean var3 = var1 == 22 || var1 == 24 || var1 == 55 || var1 == 57;
      this.maxLocals = Math.max(this.maxLocals, var2 + (var3 ? 2 : 1));
      this.execute(var1, var2, (String)null);
   }

   public void visitTypeInsn(int var1, String var2) {
      if (var1 == 187) {
         if (this.labels == null) {
            Label var3 = new Label();
            this.labels = new ArrayList(3);
            this.labels.add(var3);
            if (this.mv != null) {
               this.mv.visitLabel(var3);
            }
         }

         Iterator var5 = this.labels.iterator();

         while(var5.hasNext()) {
            Label var4 = (Label)var5.next();
            this.uninitializedTypes.put(var4, var2);
         }
      }

      super.visitTypeInsn(var1, var2);
      this.execute(var1, 0, var2);
   }

   public void visitFieldInsn(int var1, String var2, String var3, String var4) {
      super.visitFieldInsn(var1, var2, var3, var4);
      this.execute(var1, 0, var4);
   }

   public void visitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      if (this.api < 327680 && (var1 & 256) == 0) {
         super.visitMethodInsn(var1, var2, var3, var4, var5);
      } else {
         super.visitMethodInsn(var1, var2, var3, var4, var5);
         int var6 = var1 & -257;
         if (this.locals == null) {
            this.labels = null;
         } else {
            this.pop(var4);
            if (var6 != 184) {
               Object var7 = this.pop();
               if (var6 == 183 && var3.equals("<init>")) {
                  Object var8;
                  if (var7 == Opcodes.UNINITIALIZED_THIS) {
                     var8 = this.owner;
                  } else {
                     var8 = this.uninitializedTypes.get(var7);
                  }

                  int var9;
                  for(var9 = 0; var9 < this.locals.size(); ++var9) {
                     if (this.locals.get(var9) == var7) {
                        this.locals.set(var9, var8);
                     }
                  }

                  for(var9 = 0; var9 < this.stack.size(); ++var9) {
                     if (this.stack.get(var9) == var7) {
                        this.stack.set(var9, var8);
                     }
                  }
               }
            }

            this.pushDescriptor(var4);
            this.labels = null;
         }
      }
   }

   public void visitInvokeDynamicInsn(String var1, String var2, Handle var3, Object... var4) {
      super.visitInvokeDynamicInsn(var1, var2, var3, var4);
      if (this.locals == null) {
         this.labels = null;
      } else {
         this.pop(var2);
         this.pushDescriptor(var2);
         this.labels = null;
      }
   }

   public void visitJumpInsn(int var1, Label var2) {
      super.visitJumpInsn(var1, var2);
      this.execute(var1, 0, (String)null);
      if (var1 == 167) {
         this.locals = null;
         this.stack = null;
      }

   }

   public void visitLabel(Label var1) {
      super.visitLabel(var1);
      if (this.labels == null) {
         this.labels = new ArrayList(3);
      }

      this.labels.add(var1);
   }

   public void visitLdcInsn(Object var1) {
      super.visitLdcInsn(var1);
      if (this.locals == null) {
         this.labels = null;
      } else {
         if (var1 instanceof Integer) {
            this.push(Opcodes.INTEGER);
         } else if (var1 instanceof Long) {
            this.push(Opcodes.LONG);
            this.push(Opcodes.TOP);
         } else if (var1 instanceof Float) {
            this.push(Opcodes.FLOAT);
         } else if (var1 instanceof Double) {
            this.push(Opcodes.DOUBLE);
            this.push(Opcodes.TOP);
         } else if (var1 instanceof String) {
            this.push("java/lang/String");
         } else if (var1 instanceof Type) {
            int var2 = ((Type)var1).getSort();
            if (var2 != 10 && var2 != 9) {
               if (var2 != 11) {
                  throw new IllegalArgumentException();
               }

               this.push("java/lang/invoke/MethodType");
            } else {
               this.push("java/lang/Class");
            }
         } else if (var1 instanceof Handle) {
            this.push("java/lang/invoke/MethodHandle");
         } else {
            if (!(var1 instanceof ConstantDynamic)) {
               throw new IllegalArgumentException();
            }

            this.pushDescriptor(((ConstantDynamic)var1).getDescriptor());
         }

         this.labels = null;
      }
   }

   public void visitIincInsn(int var1, int var2) {
      super.visitIincInsn(var1, var2);
      this.maxLocals = Math.max(this.maxLocals, var1 + 1);
      this.execute(132, var1, (String)null);
   }

   public void visitTableSwitchInsn(int var1, int var2, Label var3, Label... var4) {
      super.visitTableSwitchInsn(var1, var2, var3, var4);
      this.execute(170, 0, (String)null);
      this.locals = null;
      this.stack = null;
   }

   public void visitLookupSwitchInsn(Label var1, int[] var2, Label[] var3) {
      super.visitLookupSwitchInsn(var1, var2, var3);
      this.execute(171, 0, (String)null);
      this.locals = null;
      this.stack = null;
   }

   public void visitMultiANewArrayInsn(String var1, int var2) {
      super.visitMultiANewArrayInsn(var1, var2);
      this.execute(197, var2, var1);
   }

   public void visitLocalVariable(String var1, String var2, String var3, Label var4, Label var5, int var6) {
      char var7 = var2.charAt(0);
      this.maxLocals = Math.max(this.maxLocals, var6 + (var7 != 'J' && var7 != 'D' ? 1 : 2));
      super.visitLocalVariable(var1, var2, var3, var4, var5, var6);
   }

   public void visitMaxs(int var1, int var2) {
      if (this.mv != null) {
         this.maxStack = Math.max(this.maxStack, var1);
         this.maxLocals = Math.max(this.maxLocals, var2);
         this.mv.visitMaxs(this.maxStack, this.maxLocals);
      }

   }

   private Object get(int var1) {
      this.maxLocals = Math.max(this.maxLocals, var1 + 1);
      return var1 < this.locals.size() ? this.locals.get(var1) : Opcodes.TOP;
   }

   private void set(int var1, Object var2) {
      this.maxLocals = Math.max(this.maxLocals, var1 + 1);

      while(var1 >= this.locals.size()) {
         this.locals.add(Opcodes.TOP);
      }

      this.locals.set(var1, var2);
   }

   private void push(Object var1) {
      this.stack.add(var1);
      this.maxStack = Math.max(this.maxStack, this.stack.size());
   }

   private void pushDescriptor(String var1) {
      String var2 = var1.charAt(0) == '(' ? Type.getReturnType(var1).getDescriptor() : var1;
      switch(var2.charAt(0)) {
      case 'B':
      case 'C':
      case 'I':
      case 'S':
      case 'Z':
         this.push(Opcodes.INTEGER);
         return;
      case 'D':
         this.push(Opcodes.DOUBLE);
         this.push(Opcodes.TOP);
         return;
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
         throw new AssertionError();
      case 'F':
         this.push(Opcodes.FLOAT);
         return;
      case 'J':
         this.push(Opcodes.LONG);
         this.push(Opcodes.TOP);
         return;
      case 'L':
         this.push(var2.substring(1, var2.length() - 1));
         break;
      case 'V':
         return;
      case '[':
         this.push(var2);
      }

   }

   private Object pop() {
      return this.stack.remove(this.stack.size() - 1);
   }

   private void pop(int var1) {
      int var2 = this.stack.size();
      int var3 = var2 - var1;

      for(int var4 = var2 - 1; var4 >= var3; --var4) {
         this.stack.remove(var4);
      }

   }

   private void pop(String var1) {
      char var2 = var1.charAt(0);
      if (var2 == '(') {
         int var3 = 0;
         Type[] var4 = Type.getArgumentTypes(var1);
         Type[] var5 = var4;
         int var6 = var4.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Type var8 = var5[var7];
            var3 += var8.getSize();
         }

         this.pop(var3);
      } else if (var2 != 'J' && var2 != 'D') {
         this.pop(1);
      } else {
         this.pop(2);
      }

   }

   private void execute(int var1, int var2, String var3) {
      if (var1 != 168 && var1 != 169) {
         if (this.locals == null) {
            this.labels = null;
         } else {
            Object var4;
            Object var5;
            Object var6;
            label89:
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
               this.push(Opcodes.NULL);
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
               this.push(Opcodes.INTEGER);
               break;
            case 9:
            case 10:
               this.push(Opcodes.LONG);
               this.push(Opcodes.TOP);
               break;
            case 11:
            case 12:
            case 13:
               this.push(Opcodes.FLOAT);
               break;
            case 14:
            case 15:
               this.push(Opcodes.DOUBLE);
               this.push(Opcodes.TOP);
               break;
            case 18:
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
            case 168:
            case 169:
            case 182:
            case 183:
            case 184:
            case 185:
            case 186:
            case 196:
            default:
               throw new IllegalArgumentException("Invalid opcode " + var1);
            case 21:
            case 23:
            case 25:
               this.push(this.get(var2));
               break;
            case 22:
            case 24:
               this.push(this.get(var2));
               this.push(Opcodes.TOP);
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
               this.push(Opcodes.INTEGER);
               break;
            case 47:
            case 143:
               this.pop(2);
               this.push(Opcodes.LONG);
               this.push(Opcodes.TOP);
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
               this.push(Opcodes.FLOAT);
               break;
            case 49:
            case 138:
               this.pop(2);
               this.push(Opcodes.DOUBLE);
               this.push(Opcodes.TOP);
               break;
            case 50:
               this.pop(1);
               var4 = this.pop();
               if (var4 instanceof String) {
                  this.pushDescriptor(((String)var4).substring(1));
               } else if (var4 == Opcodes.NULL) {
                  this.push(var4);
               } else {
                  this.push("java/lang/Object");
               }
               break;
            case 54:
            case 56:
            case 58:
               var4 = this.pop();
               this.set(var2, var4);
               if (var2 > 0) {
                  var5 = this.get(var2 - 1);
                  if (var5 == Opcodes.LONG || var5 == Opcodes.DOUBLE) {
                     this.set(var2 - 1, Opcodes.TOP);
                  }
               }
               break;
            case 55:
            case 57:
               this.pop(1);
               var4 = this.pop();
               this.set(var2, var4);
               this.set(var2 + 1, Opcodes.TOP);
               if (var2 > 0) {
                  var5 = this.get(var2 - 1);
                  if (var5 == Opcodes.LONG || var5 == Opcodes.DOUBLE) {
                     this.set(var2 - 1, Opcodes.TOP);
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
               var4 = this.pop();
               this.push(var4);
               this.push(var4);
               break;
            case 90:
               var4 = this.pop();
               var5 = this.pop();
               this.push(var4);
               this.push(var5);
               this.push(var4);
               break;
            case 91:
               var4 = this.pop();
               var5 = this.pop();
               var6 = this.pop();
               this.push(var4);
               this.push(var6);
               this.push(var5);
               this.push(var4);
               break;
            case 92:
               var4 = this.pop();
               var5 = this.pop();
               this.push(var5);
               this.push(var4);
               this.push(var5);
               this.push(var4);
               break;
            case 93:
               var4 = this.pop();
               var5 = this.pop();
               var6 = this.pop();
               this.push(var5);
               this.push(var4);
               this.push(var6);
               this.push(var5);
               this.push(var4);
               break;
            case 94:
               var4 = this.pop();
               var5 = this.pop();
               var6 = this.pop();
               Object var7 = this.pop();
               this.push(var5);
               this.push(var4);
               this.push(var7);
               this.push(var6);
               this.push(var5);
               this.push(var4);
               break;
            case 95:
               var4 = this.pop();
               var5 = this.pop();
               this.push(var4);
               this.push(var5);
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
               this.push(Opcodes.LONG);
               this.push(Opcodes.TOP);
               break;
            case 99:
            case 103:
            case 107:
            case 111:
            case 115:
               this.pop(4);
               this.push(Opcodes.DOUBLE);
               this.push(Opcodes.TOP);
               break;
            case 121:
            case 123:
            case 125:
               this.pop(3);
               this.push(Opcodes.LONG);
               this.push(Opcodes.TOP);
               break;
            case 132:
               this.set(var2, Opcodes.INTEGER);
               break;
            case 133:
            case 140:
               this.pop(1);
               this.push(Opcodes.LONG);
               this.push(Opcodes.TOP);
               break;
            case 134:
               this.pop(1);
               this.push(Opcodes.FLOAT);
               break;
            case 135:
            case 141:
               this.pop(1);
               this.push(Opcodes.DOUBLE);
               this.push(Opcodes.TOP);
               break;
            case 139:
            case 190:
            case 193:
               this.pop(1);
               this.push(Opcodes.INTEGER);
               break;
            case 148:
            case 151:
            case 152:
               this.pop(4);
               this.push(Opcodes.INTEGER);
               break;
            case 178:
               this.pushDescriptor(var3);
               break;
            case 179:
               this.pop(var3);
               break;
            case 180:
               this.pop(1);
               this.pushDescriptor(var3);
               break;
            case 181:
               this.pop(var3);
               this.pop();
               break;
            case 187:
               this.push(this.labels.get(0));
               break;
            case 188:
               this.pop();
               switch(var2) {
               case 4:
                  this.pushDescriptor("[Z");
                  break label89;
               case 5:
                  this.pushDescriptor("[C");
                  break label89;
               case 6:
                  this.pushDescriptor("[F");
                  break label89;
               case 7:
                  this.pushDescriptor("[D");
                  break label89;
               case 8:
                  this.pushDescriptor("[B");
                  break label89;
               case 9:
                  this.pushDescriptor("[S");
                  break label89;
               case 10:
                  this.pushDescriptor("[I");
                  break label89;
               case 11:
                  this.pushDescriptor("[J");
                  break label89;
               default:
                  throw new IllegalArgumentException("Invalid array type " + var2);
               }
            case 189:
               this.pop();
               this.pushDescriptor("[" + Type.getObjectType(var3));
               break;
            case 192:
               this.pop();
               this.pushDescriptor(Type.getObjectType(var3).getDescriptor());
               break;
            case 197:
               this.pop(var2);
               this.pushDescriptor(var3);
            }

            this.labels = null;
         }
      } else {
         throw new IllegalArgumentException("JSR/RET are not supported");
      }
   }
}
