package org.objectweb.asm.tree.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.IincInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TableSwitchInsnNode;
import org.objectweb.asm.tree.TryCatchBlockNode;
import org.objectweb.asm.tree.VarInsnNode;

public class Analyzer implements Opcodes {
   private final Interpreter interpreter;
   private InsnList insnList;
   private int insnListSize;
   private List[] handlers;
   private Frame[] frames;
   private Subroutine[] subroutines;
   private boolean[] inInstructionsToProcess;
   private int[] instructionsToProcess;
   private int numInstructionsToProcess;

   public Analyzer(Interpreter var1) {
      super();
      this.interpreter = var1;
   }

   public Frame[] analyze(String var1, MethodNode var2) throws AnalyzerException {
      if ((var2.access & 1280) != 0) {
         this.frames = (Frame[])(new Frame[0]);
         return this.frames;
      } else {
         this.insnList = var2.instructions;
         this.insnListSize = this.insnList.size();
         this.handlers = (List[])(new List[this.insnListSize]);
         this.frames = (Frame[])(new Frame[this.insnListSize]);
         this.subroutines = new Subroutine[this.insnListSize];
         this.inInstructionsToProcess = new boolean[this.insnListSize];
         this.instructionsToProcess = new int[this.insnListSize];
         this.numInstructionsToProcess = 0;

         int var6;
         int var7;
         for(int var3 = 0; var3 < var2.tryCatchBlocks.size(); ++var3) {
            TryCatchBlockNode var4 = (TryCatchBlockNode)var2.tryCatchBlocks.get(var3);
            int var5 = this.insnList.indexOf(var4.start);
            var6 = this.insnList.indexOf(var4.end);

            for(var7 = var5; var7 < var6; ++var7) {
               Object var8 = this.handlers[var7];
               if (var8 == null) {
                  var8 = new ArrayList();
                  this.handlers[var7] = (List)var8;
               }

               ((List)var8).add(var4);
            }
         }

         Subroutine var20 = new Subroutine((LabelNode)null, var2.maxLocals, (JumpInsnNode)null);
         ArrayList var21 = new ArrayList();
         this.findSubroutine(0, var20, var21);
         HashMap var22 = new HashMap();

         while(!var21.isEmpty()) {
            JumpInsnNode var23 = (JumpInsnNode)var21.remove(0);
            Subroutine var25 = (Subroutine)var22.get(var23.label);
            if (var25 == null) {
               var25 = new Subroutine(var23.label, var2.maxLocals, var23);
               var22.put(var23.label, var25);
               this.findSubroutine(this.insnList.indexOf(var23.label), var25, var21);
            } else {
               var25.callers.add(var23);
            }
         }

         for(var6 = 0; var6 < this.insnListSize; ++var6) {
            if (this.subroutines[var6] != null && this.subroutines[var6].start == null) {
               this.subroutines[var6] = null;
            }
         }

         Frame var24 = this.computeInitialFrame(var1, var2);
         this.merge(0, var24, (Subroutine)null);
         this.init(var1, var2);

         while(this.numInstructionsToProcess > 0) {
            var7 = this.instructionsToProcess[--this.numInstructionsToProcess];
            Frame var26 = this.frames[var7];
            Subroutine var9 = this.subroutines[var7];
            this.inInstructionsToProcess[var7] = false;
            AbstractInsnNode var10 = null;

            try {
               var10 = var2.instructions.get(var7);
               int var11 = var10.getOpcode();
               int var12 = var10.getType();
               if (var12 != 8 && var12 != 15 && var12 != 14) {
                  var24.init(var26).execute(var10, this.interpreter);
                  var9 = var9 == null ? null : new Subroutine(var9);
                  int var14;
                  if (var10 instanceof JumpInsnNode) {
                     JumpInsnNode var13 = (JumpInsnNode)var10;
                     if (var11 != 167 && var11 != 168) {
                        var24.initJumpTarget(var11, (LabelNode)null);
                        this.merge(var7 + 1, var24, var9);
                        this.newControlFlowEdge(var7, var7 + 1);
                     }

                     var14 = this.insnList.indexOf(var13.label);
                     var24.initJumpTarget(var11, var13.label);
                     if (var11 == 168) {
                        this.merge(var14, var24, new Subroutine(var13.label, var2.maxLocals, var13));
                     } else {
                        this.merge(var14, var24, var9);
                     }

                     this.newControlFlowEdge(var7, var14);
                  } else {
                     int var15;
                     LabelNode var16;
                     if (var10 instanceof LookupSwitchInsnNode) {
                        LookupSwitchInsnNode var27 = (LookupSwitchInsnNode)var10;
                        var14 = this.insnList.indexOf(var27.dflt);
                        var24.initJumpTarget(var11, var27.dflt);
                        this.merge(var14, var24, var9);
                        this.newControlFlowEdge(var7, var14);

                        for(var15 = 0; var15 < var27.labels.size(); ++var15) {
                           var16 = (LabelNode)var27.labels.get(var15);
                           var14 = this.insnList.indexOf(var16);
                           var24.initJumpTarget(var11, var16);
                           this.merge(var14, var24, var9);
                           this.newControlFlowEdge(var7, var14);
                        }
                     } else if (var10 instanceof TableSwitchInsnNode) {
                        TableSwitchInsnNode var28 = (TableSwitchInsnNode)var10;
                        var14 = this.insnList.indexOf(var28.dflt);
                        var24.initJumpTarget(var11, var28.dflt);
                        this.merge(var14, var24, var9);
                        this.newControlFlowEdge(var7, var14);

                        for(var15 = 0; var15 < var28.labels.size(); ++var15) {
                           var16 = (LabelNode)var28.labels.get(var15);
                           var24.initJumpTarget(var11, var16);
                           var14 = this.insnList.indexOf(var16);
                           this.merge(var14, var24, var9);
                           this.newControlFlowEdge(var7, var14);
                        }
                     } else {
                        int var29;
                        if (var11 == 169) {
                           if (var9 == null) {
                              throw new AnalyzerException(var10, "RET instruction outside of a subroutine");
                           }

                           for(var29 = 0; var29 < var9.callers.size(); ++var29) {
                              JumpInsnNode var31 = (JumpInsnNode)var9.callers.get(var29);
                              var15 = this.insnList.indexOf(var31);
                              if (this.frames[var15] != null) {
                                 this.merge(var15 + 1, this.frames[var15], var24, this.subroutines[var15], var9.localsUsed);
                                 this.newControlFlowEdge(var7, var15 + 1);
                              }
                           }
                        } else if (var11 != 191 && (var11 < 172 || var11 > 177)) {
                           if (var9 != null) {
                              if (var10 instanceof VarInsnNode) {
                                 var29 = ((VarInsnNode)var10).var;
                                 var9.localsUsed[var29] = true;
                                 if (var11 == 22 || var11 == 24 || var11 == 55 || var11 == 57) {
                                    var9.localsUsed[var29 + 1] = true;
                                 }
                              } else if (var10 instanceof IincInsnNode) {
                                 var29 = ((IincInsnNode)var10).var;
                                 var9.localsUsed[var29] = true;
                              }
                           }

                           this.merge(var7 + 1, var24, var9);
                           this.newControlFlowEdge(var7, var7 + 1);
                        }
                     }
                  }
               } else {
                  this.merge(var7 + 1, var26, var9);
                  this.newControlFlowEdge(var7, var7 + 1);
               }

               List var30 = this.handlers[var7];
               if (var30 != null) {
                  Iterator var32 = var30.iterator();

                  while(var32.hasNext()) {
                     TryCatchBlockNode var33 = (TryCatchBlockNode)var32.next();
                     Type var34;
                     if (var33.type == null) {
                        var34 = Type.getObjectType("java/lang/Throwable");
                     } else {
                        var34 = Type.getObjectType(var33.type);
                     }

                     if (this.newControlFlowExceptionEdge(var7, var33)) {
                        Frame var17 = this.newFrame(var26);
                        var17.clearStack();
                        var17.push(this.interpreter.newExceptionValue(var33, var17, var34));
                        this.merge(this.insnList.indexOf(var33.handler), var17, var9);
                     }
                  }
               }
            } catch (AnalyzerException var18) {
               throw new AnalyzerException(var18.node, "Error at instruction " + var7 + ": " + var18.getMessage(), var18);
            } catch (RuntimeException var19) {
               throw new AnalyzerException(var10, "Error at instruction " + var7 + ": " + var19.getMessage(), var19);
            }
         }

         return this.frames;
      }
   }

   private void findSubroutine(int var1, Subroutine var2, List var3) throws AnalyzerException {
      ArrayList var4 = new ArrayList();
      var4.add(var1);

      while(!var4.isEmpty()) {
         int var5 = (Integer)var4.remove(var4.size() - 1);
         if (var5 < 0 || var5 >= this.insnListSize) {
            throw new AnalyzerException((AbstractInsnNode)null, "Execution can fall off the end of the code");
         }

         if (this.subroutines[var5] == null) {
            this.subroutines[var5] = new Subroutine(var2);
            AbstractInsnNode var6 = this.insnList.get(var5);
            if (var6 instanceof JumpInsnNode) {
               if (var6.getOpcode() == 168) {
                  var3.add(var6);
               } else {
                  JumpInsnNode var7 = (JumpInsnNode)var6;
                  var4.add(this.insnList.indexOf(var7.label));
               }
            } else {
               int var8;
               LabelNode var9;
               if (var6 instanceof TableSwitchInsnNode) {
                  TableSwitchInsnNode var10 = (TableSwitchInsnNode)var6;
                  this.findSubroutine(this.insnList.indexOf(var10.dflt), var2, var3);

                  for(var8 = var10.labels.size() - 1; var8 >= 0; --var8) {
                     var9 = (LabelNode)var10.labels.get(var8);
                     var4.add(this.insnList.indexOf(var9));
                  }
               } else if (var6 instanceof LookupSwitchInsnNode) {
                  LookupSwitchInsnNode var11 = (LookupSwitchInsnNode)var6;
                  this.findSubroutine(this.insnList.indexOf(var11.dflt), var2, var3);

                  for(var8 = var11.labels.size() - 1; var8 >= 0; --var8) {
                     var9 = (LabelNode)var11.labels.get(var8);
                     var4.add(this.insnList.indexOf(var9));
                  }
               }
            }

            List var12 = this.handlers[var5];
            if (var12 != null) {
               Iterator var13 = var12.iterator();

               while(var13.hasNext()) {
                  TryCatchBlockNode var14 = (TryCatchBlockNode)var13.next();
                  var4.add(this.insnList.indexOf(var14.handler));
               }
            }

            switch(var6.getOpcode()) {
            case 167:
            case 169:
            case 170:
            case 171:
            case 172:
            case 173:
            case 174:
            case 175:
            case 176:
            case 177:
            case 191:
               break;
            case 168:
            case 178:
            case 179:
            case 180:
            case 181:
            case 182:
            case 183:
            case 184:
            case 185:
            case 186:
            case 187:
            case 188:
            case 189:
            case 190:
            default:
               var4.add(var5 + 1);
            }
         }
      }

   }

   private Frame computeInitialFrame(String var1, MethodNode var2) {
      Frame var3 = this.newFrame(var2.maxLocals, var2.maxStack);
      int var4 = 0;
      boolean var5 = (var2.access & 8) == 0;
      if (var5) {
         Type var6 = Type.getObjectType(var1);
         var3.setLocal(var4, this.interpreter.newParameterValue(var5, var4, var6));
         ++var4;
      }

      Type[] var11 = Type.getArgumentTypes(var2.desc);
      Type[] var7 = var11;
      int var8 = var11.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         Type var10 = var7[var9];
         var3.setLocal(var4, this.interpreter.newParameterValue(var5, var4, var10));
         ++var4;
         if (var10.getSize() == 2) {
            var3.setLocal(var4, this.interpreter.newEmptyValue(var4));
            ++var4;
         }
      }

      while(var4 < var2.maxLocals) {
         var3.setLocal(var4, this.interpreter.newEmptyValue(var4));
         ++var4;
      }

      var3.setReturn(this.interpreter.newReturnTypeValue(Type.getReturnType(var2.desc)));
      return var3;
   }

   public Frame[] getFrames() {
      return this.frames;
   }

   public List getHandlers(int var1) {
      return this.handlers[var1];
   }

   protected void init(String var1, MethodNode var2) throws AnalyzerException {
   }

   protected Frame newFrame(int var1, int var2) {
      return new Frame(var1, var2);
   }

   protected Frame newFrame(Frame var1) {
      return new Frame(var1);
   }

   protected void newControlFlowEdge(int var1, int var2) {
   }

   protected boolean newControlFlowExceptionEdge(int var1, int var2) {
      return true;
   }

   protected boolean newControlFlowExceptionEdge(int var1, TryCatchBlockNode var2) {
      return this.newControlFlowExceptionEdge(var1, this.insnList.indexOf(var2.handler));
   }

   private void merge(int var1, Frame var2, Subroutine var3) throws AnalyzerException {
      Frame var5 = this.frames[var1];
      boolean var4;
      if (var5 == null) {
         this.frames[var1] = this.newFrame(var2);
         var4 = true;
      } else {
         var4 = var5.merge(var2, this.interpreter);
      }

      Subroutine var6 = this.subroutines[var1];
      if (var6 == null) {
         if (var3 != null) {
            this.subroutines[var1] = new Subroutine(var3);
            var4 = true;
         }
      } else if (var3 != null) {
         var4 |= var6.merge(var3);
      }

      if (var4 && !this.inInstructionsToProcess[var1]) {
         this.inInstructionsToProcess[var1] = true;
         this.instructionsToProcess[this.numInstructionsToProcess++] = var1;
      }

   }

   private void merge(int var1, Frame var2, Frame var3, Subroutine var4, boolean[] var5) throws AnalyzerException {
      var3.merge(var2, var5);
      Frame var7 = this.frames[var1];
      boolean var6;
      if (var7 == null) {
         this.frames[var1] = this.newFrame(var3);
         var6 = true;
      } else {
         var6 = var7.merge(var3, this.interpreter);
      }

      Subroutine var8 = this.subroutines[var1];
      if (var8 != null && var4 != null) {
         var6 |= var8.merge(var4);
      }

      if (var6 && !this.inInstructionsToProcess[var1]) {
         this.inInstructionsToProcess[var1] = true;
         this.instructionsToProcess[this.numInstructionsToProcess++] = var1;
      }

   }
}
