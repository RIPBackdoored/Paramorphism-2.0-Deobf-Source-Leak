package org.objectweb.asm;

final class MethodWriter extends MethodVisitor {
   static final int COMPUTE_NOTHING = 0;
   static final int COMPUTE_MAX_STACK_AND_LOCAL = 1;
   static final int COMPUTE_MAX_STACK_AND_LOCAL_FROM_FRAMES = 2;
   static final int COMPUTE_INSERTED_FRAMES = 3;
   static final int COMPUTE_ALL_FRAMES = 4;
   private static final int NA = 0;
   private static final int[] STACK_SIZE_DELTA = new int[]{0, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 1, 1, 2, 2, 1, 1, 1, 0, 0, 1, 2, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, -1, 0, -1, -1, -1, -1, -1, -2, -1, -2, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -3, -4, -3, -4, -3, -3, -3, -3, -1, -2, 1, 1, 1, 2, 2, 2, 0, -1, -2, -1, -2, -1, -2, -1, -2, -1, -2, -1, -2, -1, -2, -1, -2, -1, -2, -1, -2, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -2, -1, -2, -1, -2, 0, 1, 0, 1, -1, -1, 0, 0, 1, 1, -1, 0, -1, 0, 0, 0, -3, -1, -1, -3, -3, -1, -1, -1, -1, -1, -1, -2, -2, -2, -2, -2, -2, -2, -2, 0, 1, 0, -1, -1, -1, -2, -1, -2, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, -1, -1, 0, 0};
   private final SymbolTable symbolTable;
   private final int accessFlags;
   private final int nameIndex;
   private final String name;
   private final int descriptorIndex;
   private final String descriptor;
   private int maxStack;
   private int maxLocals;
   private final ByteVector code = new ByteVector();
   private Handler firstHandler;
   private Handler lastHandler;
   private int lineNumberTableLength;
   private ByteVector lineNumberTable;
   private int localVariableTableLength;
   private ByteVector localVariableTable;
   private int localVariableTypeTableLength;
   private ByteVector localVariableTypeTable;
   private int stackMapTableNumberOfEntries;
   private ByteVector stackMapTableEntries;
   private AnnotationWriter lastCodeRuntimeVisibleTypeAnnotation;
   private AnnotationWriter lastCodeRuntimeInvisibleTypeAnnotation;
   private Attribute firstCodeAttribute;
   private final int numberOfExceptions;
   private final int[] exceptionIndexTable;
   private final int signatureIndex;
   private AnnotationWriter lastRuntimeVisibleAnnotation;
   private AnnotationWriter lastRuntimeInvisibleAnnotation;
   private int visibleAnnotableParameterCount;
   private AnnotationWriter[] lastRuntimeVisibleParameterAnnotations;
   private int invisibleAnnotableParameterCount;
   private AnnotationWriter[] lastRuntimeInvisibleParameterAnnotations;
   private AnnotationWriter lastRuntimeVisibleTypeAnnotation;
   private AnnotationWriter lastRuntimeInvisibleTypeAnnotation;
   private ByteVector defaultValue;
   private int parametersCount;
   private ByteVector parameters;
   private Attribute firstAttribute;
   private final int compute;
   private Label firstBasicBlock;
   private Label lastBasicBlock;
   private Label currentBasicBlock;
   private int relativeStackSize;
   private int maxRelativeStackSize;
   private int currentLocals;
   private int previousFrameOffset;
   private int[] previousFrame;
   private int[] currentFrame;
   private boolean hasSubroutines;
   private boolean hasAsmInstructions;
   private int lastBytecodeOffset;
   private int sourceOffset;
   private int sourceLength;

   MethodWriter(SymbolTable var1, int var2, String var3, String var4, String var5, String[] var6, int var7) {
      super(458752);
      this.symbolTable = var1;
      this.accessFlags = "<init>".equals(var3) ? var2 | 262144 : var2;
      this.nameIndex = var1.addConstantUtf8(var3);
      this.name = var3;
      this.descriptorIndex = var1.addConstantUtf8(var4);
      this.descriptor = var4;
      this.signatureIndex = var5 == null ? 0 : var1.addConstantUtf8(var5);
      int var8;
      if (var6 != null && var6.length > 0) {
         this.numberOfExceptions = var6.length;
         this.exceptionIndexTable = new int[this.numberOfExceptions];

         for(var8 = 0; var8 < this.numberOfExceptions; ++var8) {
            this.exceptionIndexTable[var8] = var1.addConstantClass(var6[var8]).index;
         }
      } else {
         this.numberOfExceptions = 0;
         this.exceptionIndexTable = null;
      }

      this.compute = var7;
      if (var7 != 0) {
         var8 = Type.getArgumentsAndReturnSizes(var4) >> 2;
         if ((var2 & 8) != 0) {
            --var8;
         }

         this.maxLocals = var8;
         this.currentLocals = var8;
         this.firstBasicBlock = new Label();
         this.visitLabel(this.firstBasicBlock);
      }

   }

   boolean hasFrames() {
      return this.stackMapTableNumberOfEntries > 0;
   }

   boolean hasAsmInstructions() {
      return this.hasAsmInstructions;
   }

   public void visitParameter(String var1, int var2) {
      if (this.parameters == null) {
         this.parameters = new ByteVector();
      }

      ++this.parametersCount;
      this.parameters.putShort(var1 == null ? 0 : this.symbolTable.addConstantUtf8(var1)).putShort(var2);
   }

   public AnnotationVisitor visitAnnotationDefault() {
      this.defaultValue = new ByteVector();
      return new AnnotationWriter(this.symbolTable, false, this.defaultValue, (AnnotationWriter)null);
   }

   public AnnotationVisitor visitAnnotation(String var1, boolean var2) {
      return var2 ? (this.lastRuntimeVisibleAnnotation = AnnotationWriter.create(this.symbolTable, var1, this.lastRuntimeVisibleAnnotation)) : (this.lastRuntimeInvisibleAnnotation = AnnotationWriter.create(this.symbolTable, var1, this.lastRuntimeInvisibleAnnotation));
   }

   public AnnotationVisitor visitTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      return var4 ? (this.lastRuntimeVisibleTypeAnnotation = AnnotationWriter.create(this.symbolTable, var1, var2, var3, this.lastRuntimeVisibleTypeAnnotation)) : (this.lastRuntimeInvisibleTypeAnnotation = AnnotationWriter.create(this.symbolTable, var1, var2, var3, this.lastRuntimeInvisibleTypeAnnotation));
   }

   public void visitAnnotableParameterCount(int var1, boolean var2) {
      if (var2) {
         this.visibleAnnotableParameterCount = var1;
      } else {
         this.invisibleAnnotableParameterCount = var1;
      }

   }

   public AnnotationVisitor visitParameterAnnotation(int var1, String var2, boolean var3) {
      if (var3) {
         if (this.lastRuntimeVisibleParameterAnnotations == null) {
            this.lastRuntimeVisibleParameterAnnotations = new AnnotationWriter[Type.getArgumentTypes(this.descriptor).length];
         }

         return this.lastRuntimeVisibleParameterAnnotations[var1] = AnnotationWriter.create(this.symbolTable, var2, this.lastRuntimeVisibleParameterAnnotations[var1]);
      } else {
         if (this.lastRuntimeInvisibleParameterAnnotations == null) {
            this.lastRuntimeInvisibleParameterAnnotations = new AnnotationWriter[Type.getArgumentTypes(this.descriptor).length];
         }

         return this.lastRuntimeInvisibleParameterAnnotations[var1] = AnnotationWriter.create(this.symbolTable, var2, this.lastRuntimeInvisibleParameterAnnotations[var1]);
      }
   }

   public void visitAttribute(Attribute var1) {
      if (var1.isCodeAttribute()) {
         var1.nextAttribute = this.firstCodeAttribute;
         this.firstCodeAttribute = var1;
      } else {
         var1.nextAttribute = this.firstAttribute;
         this.firstAttribute = var1;
      }

   }

   public void visitCode() {
   }

   public void visitFrame(int var1, int var2, Object[] var3, int var4, Object[] var5) {
      if (this.compute != 4) {
         int var6;
         if (this.compute == 3) {
            if (this.currentBasicBlock.frame == null) {
               this.currentBasicBlock.frame = new CurrentFrame(this.currentBasicBlock);
               this.currentBasicBlock.frame.setInputFrameFromDescriptor(this.symbolTable, this.accessFlags, this.descriptor, var2);
               this.currentBasicBlock.frame.accept(this);
            } else {
               if (var1 == -1) {
                  this.currentBasicBlock.frame.setInputFrameFromApiFormat(this.symbolTable, var2, var3, var4, var5);
               }

               this.currentBasicBlock.frame.accept(this);
            }
         } else {
            int var7;
            if (var1 == -1) {
               if (this.previousFrame == null) {
                  var6 = Type.getArgumentsAndReturnSizes(this.descriptor) >> 2;
                  Frame var8 = new Frame(new Label());
                  var8.setInputFrameFromDescriptor(this.symbolTable, this.accessFlags, this.descriptor, var6);
                  var8.accept(this);
               }

               this.currentLocals = var2;
               var6 = this.visitFrameStart(this.code.length, var2, var4);

               for(var7 = 0; var7 < var2; ++var7) {
                  this.currentFrame[var6++] = Frame.getAbstractTypeFromApiFormat(this.symbolTable, var3[var7]);
               }

               for(var7 = 0; var7 < var4; ++var7) {
                  this.currentFrame[var6++] = Frame.getAbstractTypeFromApiFormat(this.symbolTable, var5[var7]);
               }

               this.visitFrameEnd();
            } else {
               if (this.stackMapTableEntries == null) {
                  this.stackMapTableEntries = new ByteVector();
                  var6 = this.code.length;
               } else {
                  var6 = this.code.length - this.previousFrameOffset - 1;
                  if (var6 < 0) {
                     if (var1 == 3) {
                        return;
                     }

                     throw new IllegalStateException();
                  }
               }

               label119:
               switch(var1) {
               case 0:
                  this.currentLocals = var2;
                  this.stackMapTableEntries.putByte(255).putShort(var6).putShort(var2);

                  for(var7 = 0; var7 < var2; ++var7) {
                     this.putFrameType(var3[var7]);
                  }

                  this.stackMapTableEntries.putShort(var4);
                  var7 = 0;

                  while(true) {
                     if (var7 >= var4) {
                        break label119;
                     }

                     this.putFrameType(var5[var7]);
                     ++var7;
                  }
               case 1:
                  this.currentLocals += var2;
                  this.stackMapTableEntries.putByte(251 + var2).putShort(var6);
                  var7 = 0;

                  while(true) {
                     if (var7 >= var2) {
                        break label119;
                     }

                     this.putFrameType(var3[var7]);
                     ++var7;
                  }
               case 2:
                  this.currentLocals -= var2;
                  this.stackMapTableEntries.putByte(251 - var2).putShort(var6);
                  break;
               case 3:
                  if (var6 < 64) {
                     this.stackMapTableEntries.putByte(var6);
                  } else {
                     this.stackMapTableEntries.putByte(251).putShort(var6);
                  }
                  break;
               case 4:
                  if (var6 < 64) {
                     this.stackMapTableEntries.putByte(64 + var6);
                  } else {
                     this.stackMapTableEntries.putByte(247).putShort(var6);
                  }

                  this.putFrameType(var5[0]);
                  break;
               default:
                  throw new IllegalArgumentException();
               }

               this.previousFrameOffset = this.code.length;
               ++this.stackMapTableNumberOfEntries;
            }
         }

         if (this.compute == 2) {
            this.relativeStackSize = var4;

            for(var6 = 0; var6 < var4; ++var6) {
               if (var5[var6] == Opcodes.LONG || var5[var6] == Opcodes.DOUBLE) {
                  ++this.relativeStackSize;
               }
            }

            if (this.relativeStackSize > this.maxRelativeStackSize) {
               this.maxRelativeStackSize = this.relativeStackSize;
            }
         }

         this.maxStack = Math.max(this.maxStack, var4);
         this.maxLocals = Math.max(this.maxLocals, this.currentLocals);
      }
   }

   public void visitInsn(int var1) {
      this.lastBytecodeOffset = this.code.length;
      this.code.putByte(var1);
      if (this.currentBasicBlock != null) {
         if (this.compute != 4 && this.compute != 3) {
            int var2 = this.relativeStackSize + STACK_SIZE_DELTA[var1];
            if (var2 > this.maxRelativeStackSize) {
               this.maxRelativeStackSize = var2;
            }

            this.relativeStackSize = var2;
         } else {
            this.currentBasicBlock.frame.execute(var1, 0, (Symbol)null, (SymbolTable)null);
         }

         if (var1 >= 172 && var1 <= 177 || var1 == 191) {
            this.endCurrentBasicBlockWithNoSuccessor();
         }
      }

   }

   public void visitIntInsn(int var1, int var2) {
      this.lastBytecodeOffset = this.code.length;
      if (var1 == 17) {
         this.code.put12(var1, var2);
      } else {
         this.code.put11(var1, var2);
      }

      if (this.currentBasicBlock != null) {
         if (this.compute != 4 && this.compute != 3) {
            if (var1 != 188) {
               int var3 = this.relativeStackSize + 1;
               if (var3 > this.maxRelativeStackSize) {
                  this.maxRelativeStackSize = var3;
               }

               this.relativeStackSize = var3;
            }
         } else {
            this.currentBasicBlock.frame.execute(var1, var2, (Symbol)null, (SymbolTable)null);
         }
      }

   }

   public void visitVarInsn(int var1, int var2) {
      this.lastBytecodeOffset = this.code.length;
      int var3;
      if (var2 < 4 && var1 != 169) {
         if (var1 < 54) {
            var3 = 26 + (var1 - 21 << 2) + var2;
         } else {
            var3 = 59 + (var1 - 54 << 2) + var2;
         }

         this.code.putByte(var3);
      } else if (var2 >= 256) {
         this.code.putByte(196).put12(var1, var2);
      } else {
         this.code.put11(var1, var2);
      }

      if (this.currentBasicBlock != null) {
         if (this.compute != 4 && this.compute != 3) {
            if (var1 == 169) {
               Label var10000 = this.currentBasicBlock;
               var10000.flags = (short)(var10000.flags | 64);
               this.currentBasicBlock.outputStackSize = (short)this.relativeStackSize;
               this.endCurrentBasicBlockWithNoSuccessor();
            } else {
               var3 = this.relativeStackSize + STACK_SIZE_DELTA[var1];
               if (var3 > this.maxRelativeStackSize) {
                  this.maxRelativeStackSize = var3;
               }

               this.relativeStackSize = var3;
            }
         } else {
            this.currentBasicBlock.frame.execute(var1, var2, (Symbol)null, (SymbolTable)null);
         }
      }

      if (this.compute != 0) {
         if (var1 != 22 && var1 != 24 && var1 != 55 && var1 != 57) {
            var3 = var2 + 1;
         } else {
            var3 = var2 + 2;
         }

         if (var3 > this.maxLocals) {
            this.maxLocals = var3;
         }
      }

      if (var1 >= 54 && this.compute == 4 && this.firstHandler != null) {
         this.visitLabel(new Label());
      }

   }

   public void visitTypeInsn(int var1, String var2) {
      this.lastBytecodeOffset = this.code.length;
      Symbol var3 = this.symbolTable.addConstantClass(var2);
      this.code.put12(var1, var3.index);
      if (this.currentBasicBlock != null) {
         if (this.compute != 4 && this.compute != 3) {
            if (var1 == 187) {
               int var4 = this.relativeStackSize + 1;
               if (var4 > this.maxRelativeStackSize) {
                  this.maxRelativeStackSize = var4;
               }

               this.relativeStackSize = var4;
            }
         } else {
            this.currentBasicBlock.frame.execute(var1, this.lastBytecodeOffset, var3, this.symbolTable);
         }
      }

   }

   public void visitFieldInsn(int var1, String var2, String var3, String var4) {
      this.lastBytecodeOffset = this.code.length;
      Symbol var5 = this.symbolTable.addConstantFieldref(var2, var3, var4);
      this.code.put12(var1, var5.index);
      if (this.currentBasicBlock != null) {
         if (this.compute != 4 && this.compute != 3) {
            int var6;
            label77: {
               char var7 = var4.charAt(0);
               switch(var1) {
               case 178:
                  var6 = this.relativeStackSize + (var7 != 'D' && var7 != 'J' ? 1 : 2);
                  break label77;
               case 179:
                  var6 = this.relativeStackSize + (var7 != 'D' && var7 != 'J' ? -1 : -2);
                  break label77;
               case 180:
                  var6 = this.relativeStackSize + (var7 != 'D' && var7 != 'J' ? 0 : 1);
                  break label77;
               case 181:
               }

               var6 = this.relativeStackSize + (var7 != 'D' && var7 != 'J' ? -2 : -3);
            }

            if (var6 > this.maxRelativeStackSize) {
               this.maxRelativeStackSize = var6;
            }

            this.relativeStackSize = var6;
         } else {
            this.currentBasicBlock.frame.execute(var1, 0, var5, this.symbolTable);
         }
      }

   }

   public void visitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      this.lastBytecodeOffset = this.code.length;
      Symbol var6 = this.symbolTable.addConstantMethodref(var2, var3, var4, var5);
      if (var1 == 185) {
         this.code.put12(185, var6.index).put11(var6.getArgumentsAndReturnSizes() >> 2, 0);
      } else {
         this.code.put12(var1, var6.index);
      }

      if (this.currentBasicBlock != null) {
         if (this.compute != 4 && this.compute != 3) {
            int var7 = var6.getArgumentsAndReturnSizes();
            int var8 = (var7 & 3) - (var7 >> 2);
            int var9;
            if (var1 == 184) {
               var9 = this.relativeStackSize + var8 + 1;
            } else {
               var9 = this.relativeStackSize + var8;
            }

            if (var9 > this.maxRelativeStackSize) {
               this.maxRelativeStackSize = var9;
            }

            this.relativeStackSize = var9;
         } else {
            this.currentBasicBlock.frame.execute(var1, 0, var6, this.symbolTable);
         }
      }

   }

   public void visitInvokeDynamicInsn(String var1, String var2, Handle var3, Object... var4) {
      this.lastBytecodeOffset = this.code.length;
      Symbol var5 = this.symbolTable.addConstantInvokeDynamic(var1, var2, var3, var4);
      this.code.put12(186, var5.index);
      this.code.putShort(0);
      if (this.currentBasicBlock != null) {
         if (this.compute != 4 && this.compute != 3) {
            int var6 = var5.getArgumentsAndReturnSizes();
            int var7 = (var6 & 3) - (var6 >> 2) + 1;
            int var8 = this.relativeStackSize + var7;
            if (var8 > this.maxRelativeStackSize) {
               this.maxRelativeStackSize = var8;
            }

            this.relativeStackSize = var8;
         } else {
            this.currentBasicBlock.frame.execute(186, 0, var5, this.symbolTable);
         }
      }

   }

   public void visitJumpInsn(int var1, Label var2) {
      this.lastBytecodeOffset = this.code.length;
      int var3 = var1 >= 200 ? var1 - 33 : var1;
      boolean var4 = false;
      if ((var2.flags & 4) != 0 && var2.bytecodeOffset - this.code.length < -32768) {
         if (var3 == 167) {
            this.code.putByte(200);
         } else if (var3 == 168) {
            this.code.putByte(201);
         } else {
            this.code.putByte(var3 >= 198 ? var3 ^ 1 : (var3 + 1 ^ 1) - 1);
            this.code.putShort(8);
            this.code.putByte(220);
            this.hasAsmInstructions = true;
            var4 = true;
         }

         var2.put(this.code, this.code.length - 1, true);
      } else if (var3 != var1) {
         this.code.putByte(var1);
         var2.put(this.code, this.code.length - 1, true);
      } else {
         this.code.putByte(var3);
         var2.put(this.code, this.code.length - 1, false);
      }

      if (this.currentBasicBlock != null) {
         Label var5 = null;
         Label var10000;
         if (this.compute == 4) {
            this.currentBasicBlock.frame.execute(var3, 0, (Symbol)null, (SymbolTable)null);
            var10000 = var2.getCanonicalInstance();
            var10000.flags = (short)(var10000.flags | 2);
            this.addSuccessorToCurrentBasicBlock(0, var2);
            if (var3 != 167) {
               var5 = new Label();
            }
         } else if (this.compute == 3) {
            this.currentBasicBlock.frame.execute(var3, 0, (Symbol)null, (SymbolTable)null);
         } else if (this.compute == 2) {
            this.relativeStackSize += STACK_SIZE_DELTA[var3];
         } else if (var3 == 168) {
            if ((var2.flags & 32) == 0) {
               var2.flags = (short)(var2.flags | 32);
               this.hasSubroutines = true;
            }

            var10000 = this.currentBasicBlock;
            var10000.flags = (short)(var10000.flags | 16);
            this.addSuccessorToCurrentBasicBlock(this.relativeStackSize + 1, var2);
            var5 = new Label();
         } else {
            this.relativeStackSize += STACK_SIZE_DELTA[var3];
            this.addSuccessorToCurrentBasicBlock(this.relativeStackSize, var2);
         }

         if (var5 != null) {
            if (var4) {
               var5.flags = (short)(var5.flags | 2);
            }

            this.visitLabel(var5);
         }

         if (var3 == 167) {
            this.endCurrentBasicBlockWithNoSuccessor();
         }
      }

   }

   public void visitLabel(Label var1) {
      this.hasAsmInstructions |= var1.resolve(this.code.data, this.code.length);
      if ((var1.flags & 1) == 0) {
         if (this.compute == 4) {
            Label var10000;
            if (this.currentBasicBlock != null) {
               if (var1.bytecodeOffset == this.currentBasicBlock.bytecodeOffset) {
                  var10000 = this.currentBasicBlock;
                  var10000.flags = (short)(var10000.flags | var1.flags & 2);
                  var1.frame = this.currentBasicBlock.frame;
                  return;
               }

               this.addSuccessorToCurrentBasicBlock(0, var1);
            }

            if (this.lastBasicBlock != null) {
               if (var1.bytecodeOffset == this.lastBasicBlock.bytecodeOffset) {
                  var10000 = this.lastBasicBlock;
                  var10000.flags = (short)(var10000.flags | var1.flags & 2);
                  var1.frame = this.lastBasicBlock.frame;
                  this.currentBasicBlock = this.lastBasicBlock;
                  return;
               }

               this.lastBasicBlock.nextBasicBlock = var1;
            }

            this.lastBasicBlock = var1;
            this.currentBasicBlock = var1;
            var1.frame = new Frame(var1);
         } else if (this.compute == 3) {
            if (this.currentBasicBlock == null) {
               this.currentBasicBlock = var1;
            } else {
               this.currentBasicBlock.frame.owner = var1;
            }
         } else if (this.compute == 1) {
            if (this.currentBasicBlock != null) {
               this.currentBasicBlock.outputStackMax = (short)this.maxRelativeStackSize;
               this.addSuccessorToCurrentBasicBlock(this.relativeStackSize, var1);
            }

            this.currentBasicBlock = var1;
            this.relativeStackSize = 0;
            this.maxRelativeStackSize = 0;
            if (this.lastBasicBlock != null) {
               this.lastBasicBlock.nextBasicBlock = var1;
            }

            this.lastBasicBlock = var1;
         } else if (this.compute == 2 && this.currentBasicBlock == null) {
            this.currentBasicBlock = var1;
         }

      }
   }

   public void visitLdcInsn(Object var1) {
      this.lastBytecodeOffset = this.code.length;
      Symbol var2 = this.symbolTable.addConstant(var1);
      int var3 = var2.index;
      char var4;
      boolean var5 = var2.tag == 5 || var2.tag == 6 || var2.tag == 17 && ((var4 = var2.value.charAt(0)) == 'J' || var4 == 'D');
      if (var5) {
         this.code.put12(20, var3);
      } else if (var3 >= 256) {
         this.code.put12(19, var3);
      } else {
         this.code.put11(18, var3);
      }

      if (this.currentBasicBlock != null) {
         if (this.compute != 4 && this.compute != 3) {
            int var6 = this.relativeStackSize + (var5 ? 2 : 1);
            if (var6 > this.maxRelativeStackSize) {
               this.maxRelativeStackSize = var6;
            }

            this.relativeStackSize = var6;
         } else {
            this.currentBasicBlock.frame.execute(18, 0, var2, this.symbolTable);
         }
      }

   }

   public void visitIincInsn(int var1, int var2) {
      this.lastBytecodeOffset = this.code.length;
      if (var1 <= 255 && var2 <= 127 && var2 >= -128) {
         this.code.putByte(132).put11(var1, var2);
      } else {
         this.code.putByte(196).put12(132, var1).putShort(var2);
      }

      if (this.currentBasicBlock != null && (this.compute == 4 || this.compute == 3)) {
         this.currentBasicBlock.frame.execute(132, var1, (Symbol)null, (SymbolTable)null);
      }

      if (this.compute != 0) {
         int var3 = var1 + 1;
         if (var3 > this.maxLocals) {
            this.maxLocals = var3;
         }
      }

   }

   public void visitTableSwitchInsn(int var1, int var2, Label var3, Label... var4) {
      this.lastBytecodeOffset = this.code.length;
      this.code.putByte(170).putByteArray((byte[])null, 0, (4 - this.code.length % 4) % 4);
      var3.put(this.code, this.lastBytecodeOffset, true);
      this.code.putInt(var1).putInt(var2);
      Label[] var5 = var4;
      int var6 = var4.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Label var8 = var5[var7];
         var8.put(this.code, this.lastBytecodeOffset, true);
      }

      this.visitSwitchInsn(var3, var4);
   }

   public void visitLookupSwitchInsn(Label var1, int[] var2, Label[] var3) {
      this.lastBytecodeOffset = this.code.length;
      this.code.putByte(171).putByteArray((byte[])null, 0, (4 - this.code.length % 4) % 4);
      var1.put(this.code, this.lastBytecodeOffset, true);
      this.code.putInt(var3.length);

      for(int var4 = 0; var4 < var3.length; ++var4) {
         this.code.putInt(var2[var4]);
         var3[var4].put(this.code, this.lastBytecodeOffset, true);
      }

      this.visitSwitchInsn(var1, var3);
   }

   private void visitSwitchInsn(Label var1, Label[] var2) {
      if (this.currentBasicBlock != null) {
         Label[] var3;
         int var4;
         int var5;
         Label var6;
         if (this.compute == 4) {
            this.currentBasicBlock.frame.execute(171, 0, (Symbol)null, (SymbolTable)null);
            this.addSuccessorToCurrentBasicBlock(0, var1);
            Label var10000 = var1.getCanonicalInstance();
            var10000.flags = (short)(var10000.flags | 2);
            var3 = var2;
            var4 = var2.length;

            for(var5 = 0; var5 < var4; ++var5) {
               var6 = var3[var5];
               this.addSuccessorToCurrentBasicBlock(0, var6);
               var10000 = var6.getCanonicalInstance();
               var10000.flags = (short)(var10000.flags | 2);
            }
         } else if (this.compute == 1) {
            --this.relativeStackSize;
            this.addSuccessorToCurrentBasicBlock(this.relativeStackSize, var1);
            var3 = var2;
            var4 = var2.length;

            for(var5 = 0; var5 < var4; ++var5) {
               var6 = var3[var5];
               this.addSuccessorToCurrentBasicBlock(this.relativeStackSize, var6);
            }
         }

         this.endCurrentBasicBlockWithNoSuccessor();
      }

   }

   public void visitMultiANewArrayInsn(String var1, int var2) {
      this.lastBytecodeOffset = this.code.length;
      Symbol var3 = this.symbolTable.addConstantClass(var1);
      this.code.put12(197, var3.index).putByte(var2);
      if (this.currentBasicBlock != null) {
         if (this.compute != 4 && this.compute != 3) {
            this.relativeStackSize += 1 - var2;
         } else {
            this.currentBasicBlock.frame.execute(197, var2, var3, this.symbolTable);
         }
      }

   }

   public AnnotationVisitor visitInsnAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      return var4 ? (this.lastCodeRuntimeVisibleTypeAnnotation = AnnotationWriter.create(this.symbolTable, var1 & -16776961 | this.lastBytecodeOffset << 8, var2, var3, this.lastCodeRuntimeVisibleTypeAnnotation)) : (this.lastCodeRuntimeInvisibleTypeAnnotation = AnnotationWriter.create(this.symbolTable, var1 & -16776961 | this.lastBytecodeOffset << 8, var2, var3, this.lastCodeRuntimeInvisibleTypeAnnotation));
   }

   public void visitTryCatchBlock(Label var1, Label var2, Label var3, String var4) {
      Handler var5 = new Handler(var1, var2, var3, var4 != null ? this.symbolTable.addConstantClass(var4).index : 0, var4);
      if (this.firstHandler == null) {
         this.firstHandler = var5;
      } else {
         this.lastHandler.nextHandler = var5;
      }

      this.lastHandler = var5;
   }

   public AnnotationVisitor visitTryCatchAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      return var4 ? (this.lastCodeRuntimeVisibleTypeAnnotation = AnnotationWriter.create(this.symbolTable, var1, var2, var3, this.lastCodeRuntimeVisibleTypeAnnotation)) : (this.lastCodeRuntimeInvisibleTypeAnnotation = AnnotationWriter.create(this.symbolTable, var1, var2, var3, this.lastCodeRuntimeInvisibleTypeAnnotation));
   }

   public void visitLocalVariable(String var1, String var2, String var3, Label var4, Label var5, int var6) {
      if (var3 != null) {
         if (this.localVariableTypeTable == null) {
            this.localVariableTypeTable = new ByteVector();
         }

         ++this.localVariableTypeTableLength;
         this.localVariableTypeTable.putShort(var4.bytecodeOffset).putShort(var5.bytecodeOffset - var4.bytecodeOffset).putShort(this.symbolTable.addConstantUtf8(var1)).putShort(this.symbolTable.addConstantUtf8(var3)).putShort(var6);
      }

      if (this.localVariableTable == null) {
         this.localVariableTable = new ByteVector();
      }

      ++this.localVariableTableLength;
      this.localVariableTable.putShort(var4.bytecodeOffset).putShort(var5.bytecodeOffset - var4.bytecodeOffset).putShort(this.symbolTable.addConstantUtf8(var1)).putShort(this.symbolTable.addConstantUtf8(var2)).putShort(var6);
      if (this.compute != 0) {
         char var7 = var2.charAt(0);
         int var8 = var6 + (var7 != 'J' && var7 != 'D' ? 1 : 2);
         if (var8 > this.maxLocals) {
            this.maxLocals = var8;
         }
      }

   }

   public AnnotationVisitor visitLocalVariableAnnotation(int var1, TypePath var2, Label[] var3, Label[] var4, int[] var5, String var6, boolean var7) {
      ByteVector var8 = new ByteVector();
      var8.putByte(var1 >>> 24).putShort(var3.length);

      for(int var9 = 0; var9 < var3.length; ++var9) {
         var8.putShort(var3[var9].bytecodeOffset).putShort(var4[var9].bytecodeOffset - var3[var9].bytecodeOffset).putShort(var5[var9]);
      }

      TypePath.put(var2, var8);
      var8.putShort(this.symbolTable.addConstantUtf8(var6)).putShort(0);
      return var7 ? (this.lastCodeRuntimeVisibleTypeAnnotation = new AnnotationWriter(this.symbolTable, true, var8, this.lastCodeRuntimeVisibleTypeAnnotation)) : (this.lastCodeRuntimeInvisibleTypeAnnotation = new AnnotationWriter(this.symbolTable, true, var8, this.lastCodeRuntimeInvisibleTypeAnnotation));
   }

   public void visitLineNumber(int var1, Label var2) {
      if (this.lineNumberTable == null) {
         this.lineNumberTable = new ByteVector();
      }

      ++this.lineNumberTableLength;
      this.lineNumberTable.putShort(var2.bytecodeOffset);
      this.lineNumberTable.putShort(var1);
   }

   public void visitMaxs(int var1, int var2) {
      if (this.compute == 4) {
         this.computeAllFrames();
      } else if (this.compute == 1) {
         this.computeMaxStackAndLocal();
      } else if (this.compute == 2) {
         this.maxStack = this.maxRelativeStackSize;
      } else {
         this.maxStack = var1;
         this.maxLocals = var2;
      }

   }

   private void computeAllFrames() {
      Label var5;
      Label var6;
      for(Handler var1 = this.firstHandler; var1 != null; var1 = var1.nextHandler) {
         String var2 = var1.catchTypeDescriptor == null ? "java/lang/Throwable" : var1.catchTypeDescriptor;
         int var3 = Frame.getAbstractTypeFromInternalName(this.symbolTable, var2);
         Label var4 = var1.handlerPc.getCanonicalInstance();
         var4.flags = (short)(var4.flags | 2);
         var5 = var1.startPc.getCanonicalInstance();

         for(var6 = var1.endPc.getCanonicalInstance(); var5 != var6; var5 = var5.nextBasicBlock) {
            var5.outgoingEdges = new Edge(var3, var4, var5.outgoingEdges);
         }
      }

      Frame var10 = this.firstBasicBlock.frame;
      var10.setInputFrameFromDescriptor(this.symbolTable, this.accessFlags, this.descriptor, this.maxLocals);
      var10.accept(this);
      Label var11 = this.firstBasicBlock;
      var11.nextListElement = Label.EMPTY_LIST;
      int var12 = 0;

      while(var11 != Label.EMPTY_LIST) {
         var5 = var11;
         var11 = var11.nextListElement;
         var5.nextListElement = null;
         var5.flags = (short)(var5.flags | 8);
         int var13 = var5.frame.getInputStackSize() + var5.outputStackMax;
         if (var13 > var12) {
            var12 = var13;
         }

         for(Edge var7 = var5.outgoingEdges; var7 != null; var7 = var7.nextEdge) {
            Label var8 = var7.successor.getCanonicalInstance();
            boolean var9 = var5.frame.merge(this.symbolTable, var8.frame, var7.info);
            if (var9 && var8.nextListElement == null) {
               var8.nextListElement = var11;
               var11 = var8;
            }
         }
      }

      for(var5 = this.firstBasicBlock; var5 != null; var5 = var5.nextBasicBlock) {
         if ((var5.flags & 10) == 10) {
            var5.frame.accept(this);
         }

         if ((var5.flags & 8) == 0) {
            var6 = var5.nextBasicBlock;
            int var14 = var5.bytecodeOffset;
            int var15 = (var6 == null ? this.code.length : var6.bytecodeOffset) - 1;
            if (var15 >= var14) {
               int var16;
               for(var16 = var14; var16 < var15; ++var16) {
                  this.code.data[var16] = 0;
               }

               this.code.data[var15] = -65;
               var16 = this.visitFrameStart(var14, 0, 1);
               this.currentFrame[var16] = Frame.getAbstractTypeFromInternalName(this.symbolTable, "java/lang/Throwable");
               this.visitFrameEnd();
               this.firstHandler = Handler.removeRange(this.firstHandler, var5, var6);
               var12 = Math.max(var12, 1);
            }
         }
      }

      this.maxStack = var12;
   }

   private void computeMaxStackAndLocal() {
      Label var2;
      Label var3;
      Label var4;
      for(Handler var1 = this.firstHandler; var1 != null; var1 = var1.nextHandler) {
         var2 = var1.handlerPc;
         var3 = var1.startPc;

         for(var4 = var1.endPc; var3 != var4; var3 = var3.nextBasicBlock) {
            if ((var3.flags & 16) == 0) {
               var3.outgoingEdges = new Edge(0, var2, var3.outgoingEdges);
            } else {
               var3.outgoingEdges.nextEdge.nextEdge = new Edge(0, var2, var3.outgoingEdges.nextEdge.nextEdge);
            }
         }
      }

      if (this.hasSubroutines) {
         short var9 = 1;
         this.firstBasicBlock.markSubroutine(var9);

         for(short var10 = 1; var10 <= var9; ++var10) {
            for(var4 = this.firstBasicBlock; var4 != null; var4 = var4.nextBasicBlock) {
               if ((var4.flags & 16) != 0 && var4.subroutineId == var10) {
                  Label var5 = var4.outgoingEdges.nextEdge.successor;
                  if (var5.subroutineId == 0) {
                     ++var9;
                     var5.markSubroutine(var9);
                  }
               }
            }
         }

         for(var3 = this.firstBasicBlock; var3 != null; var3 = var3.nextBasicBlock) {
            if ((var3.flags & 16) != 0) {
               var4 = var3.outgoingEdges.nextEdge.successor;
               var4.addSubroutineRetSuccessors(var3);
            }
         }
      }

      var2 = this.firstBasicBlock;
      var2.nextListElement = Label.EMPTY_LIST;
      int var11 = this.maxStack;

      while(var2 != Label.EMPTY_LIST) {
         var4 = var2;
         var2 = var2.nextListElement;
         short var12 = var4.inputStackSize;
         int var6 = var12 + var4.outputStackMax;
         if (var6 > var11) {
            var11 = var6;
         }

         Edge var7 = var4.outgoingEdges;
         if ((var4.flags & 16) != 0) {
            var7 = var7.nextEdge;
         }

         for(; var7 != null; var7 = var7.nextEdge) {
            Label var8 = var7.successor;
            if (var8.nextListElement == null) {
               var8.inputStackSize = (short)(var7.info == 0 ? 1 : var12 + var7.info);
               var8.nextListElement = var2;
               var2 = var8;
            }
         }
      }

      this.maxStack = var11;
   }

   public void visitEnd() {
   }

   private void addSuccessorToCurrentBasicBlock(int var1, Label var2) {
      this.currentBasicBlock.outgoingEdges = new Edge(var1, var2, this.currentBasicBlock.outgoingEdges);
   }

   private void endCurrentBasicBlockWithNoSuccessor() {
      if (this.compute == 4) {
         Label var1 = new Label();
         var1.frame = new Frame(var1);
         var1.resolve(this.code.data, this.code.length);
         this.lastBasicBlock.nextBasicBlock = var1;
         this.lastBasicBlock = var1;
         this.currentBasicBlock = null;
      } else if (this.compute == 1) {
         this.currentBasicBlock.outputStackMax = (short)this.maxRelativeStackSize;
         this.currentBasicBlock = null;
      }

   }

   int visitFrameStart(int var1, int var2, int var3) {
      int var4 = 3 + var2 + var3;
      if (this.currentFrame == null || this.currentFrame.length < var4) {
         this.currentFrame = new int[var4];
      }

      this.currentFrame[0] = var1;
      this.currentFrame[1] = var2;
      this.currentFrame[2] = var3;
      return 3;
   }

   void visitAbstractType(int var1, int var2) {
      this.currentFrame[var1] = var2;
   }

   void visitFrameEnd() {
      if (this.previousFrame != null) {
         if (this.stackMapTableEntries == null) {
            this.stackMapTableEntries = new ByteVector();
         }

         this.putFrame();
         ++this.stackMapTableNumberOfEntries;
      }

      this.previousFrame = this.currentFrame;
      this.currentFrame = null;
   }

   private void putFrame() {
      int var1 = this.currentFrame[1];
      int var2 = this.currentFrame[2];
      if (this.symbolTable.getMajorVersion() < 50) {
         this.stackMapTableEntries.putShort(this.currentFrame[0]).putShort(var1);
         this.putAbstractTypes(3, 3 + var1);
         this.stackMapTableEntries.putShort(var2);
         this.putAbstractTypes(3 + var1, 3 + var1 + var2);
      } else {
         int var3 = this.stackMapTableNumberOfEntries == 0 ? this.currentFrame[0] : this.currentFrame[0] - this.previousFrame[0] - 1;
         int var4 = this.previousFrame[1];
         int var5 = var1 - var4;
         int var6 = 255;
         if (var2 == 0) {
            switch(var5) {
            case -3:
            case -2:
            case -1:
               var6 = 248;
               break;
            case 0:
               var6 = var3 < 64 ? 0 : 251;
               break;
            case 1:
            case 2:
            case 3:
               var6 = 252;
            }
         } else if (var5 == 0 && var2 == 1) {
            var6 = var3 < 63 ? 64 : 247;
         }

         if (var6 != 255) {
            int var7 = 3;

            for(int var8 = 0; var8 < var4 && var8 < var1; ++var8) {
               if (this.currentFrame[var7] != this.previousFrame[var7]) {
                  var6 = 255;
                  break;
               }

               ++var7;
            }
         }

         switch(var6) {
         case 0:
            this.stackMapTableEntries.putByte(var3);
            break;
         case 64:
            this.stackMapTableEntries.putByte(64 + var3);
            this.putAbstractTypes(3 + var1, 4 + var1);
            break;
         case 247:
            this.stackMapTableEntries.putByte(247).putShort(var3);
            this.putAbstractTypes(3 + var1, 4 + var1);
            break;
         case 248:
            this.stackMapTableEntries.putByte(251 + var5).putShort(var3);
            break;
         case 251:
            this.stackMapTableEntries.putByte(251).putShort(var3);
            break;
         case 252:
            this.stackMapTableEntries.putByte(251 + var5).putShort(var3);
            this.putAbstractTypes(3 + var4, 3 + var1);
            break;
         case 255:
         default:
            this.stackMapTableEntries.putByte(255).putShort(var3).putShort(var1);
            this.putAbstractTypes(3, 3 + var1);
            this.stackMapTableEntries.putShort(var2);
            this.putAbstractTypes(3 + var1, 3 + var1 + var2);
         }

      }
   }

   private void putAbstractTypes(int var1, int var2) {
      for(int var3 = var1; var3 < var2; ++var3) {
         Frame.putAbstractType(this.symbolTable, this.currentFrame[var3], this.stackMapTableEntries);
      }

   }

   private void putFrameType(Object var1) {
      if (var1 instanceof Integer) {
         this.stackMapTableEntries.putByte((Integer)var1);
      } else if (var1 instanceof String) {
         this.stackMapTableEntries.putByte(7).putShort(this.symbolTable.addConstantClass((String)var1).index);
      } else {
         this.stackMapTableEntries.putByte(8).putShort(((Label)var1).bytecodeOffset);
      }

   }

   boolean canCopyMethodAttributes(ClassReader var1, boolean var2, boolean var3, int var4, int var5, int var6) {
      if (var1 == this.symbolTable.getSource() && var4 == this.descriptorIndex && var5 == this.signatureIndex && var3 == ((this.accessFlags & 131072) != 0)) {
         boolean var7 = this.symbolTable.getMajorVersion() < 49 && (this.accessFlags & 4096) != 0;
         if (var2 != var7) {
            return false;
         } else {
            if (var6 == 0) {
               if (this.numberOfExceptions != 0) {
                  return false;
               }
            } else if (var1.readUnsignedShort(var6) == this.numberOfExceptions) {
               int var8 = var6 + 2;

               for(int var9 = 0; var9 < this.numberOfExceptions; ++var9) {
                  if (var1.readUnsignedShort(var8) != this.exceptionIndexTable[var9]) {
                     return false;
                  }

                  var8 += 2;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   void setMethodAttributesSource(int var1, int var2) {
      this.sourceOffset = var1 + 6;
      this.sourceLength = var2 - 6;
   }

   int computeMethodInfoSize() {
      if (this.sourceOffset != 0) {
         return 6 + this.sourceLength;
      } else {
         int var1 = 8;
         if (this.code.length > 0) {
            if (this.code.length > 65535) {
               throw new MethodTooLargeException(this.symbolTable.getClassName(), this.name, this.descriptor, this.code.length);
            }

            this.symbolTable.addConstantUtf8("Code");
            var1 += 16 + this.code.length + Handler.getExceptionTableSize(this.firstHandler);
            if (this.stackMapTableEntries != null) {
               boolean var2 = this.symbolTable.getMajorVersion() >= 50;
               this.symbolTable.addConstantUtf8(var2 ? "StackMapTable" : "StackMap");
               var1 += 8 + this.stackMapTableEntries.length;
            }

            if (this.lineNumberTable != null) {
               this.symbolTable.addConstantUtf8("LineNumberTable");
               var1 += 8 + this.lineNumberTable.length;
            }

            if (this.localVariableTable != null) {
               this.symbolTable.addConstantUtf8("LocalVariableTable");
               var1 += 8 + this.localVariableTable.length;
            }

            if (this.localVariableTypeTable != null) {
               this.symbolTable.addConstantUtf8("LocalVariableTypeTable");
               var1 += 8 + this.localVariableTypeTable.length;
            }

            if (this.lastCodeRuntimeVisibleTypeAnnotation != null) {
               var1 += this.lastCodeRuntimeVisibleTypeAnnotation.computeAnnotationsSize("RuntimeVisibleTypeAnnotations");
            }

            if (this.lastCodeRuntimeInvisibleTypeAnnotation != null) {
               var1 += this.lastCodeRuntimeInvisibleTypeAnnotation.computeAnnotationsSize("RuntimeInvisibleTypeAnnotations");
            }

            if (this.firstCodeAttribute != null) {
               var1 += this.firstCodeAttribute.computeAttributesSize(this.symbolTable, this.code.data, this.code.length, this.maxStack, this.maxLocals);
            }
         }

         if (this.numberOfExceptions > 0) {
            this.symbolTable.addConstantUtf8("Exceptions");
            var1 += 8 + 2 * this.numberOfExceptions;
         }

         var1 += Attribute.computeAttributesSize(this.symbolTable, this.accessFlags, this.signatureIndex);
         var1 += AnnotationWriter.computeAnnotationsSize(this.lastRuntimeVisibleAnnotation, this.lastRuntimeInvisibleAnnotation, this.lastRuntimeVisibleTypeAnnotation, this.lastRuntimeInvisibleTypeAnnotation);
         if (this.lastRuntimeVisibleParameterAnnotations != null) {
            var1 += AnnotationWriter.computeParameterAnnotationsSize("RuntimeVisibleParameterAnnotations", this.lastRuntimeVisibleParameterAnnotations, this.visibleAnnotableParameterCount == 0 ? this.lastRuntimeVisibleParameterAnnotations.length : this.visibleAnnotableParameterCount);
         }

         if (this.lastRuntimeInvisibleParameterAnnotations != null) {
            var1 += AnnotationWriter.computeParameterAnnotationsSize("RuntimeInvisibleParameterAnnotations", this.lastRuntimeInvisibleParameterAnnotations, this.invisibleAnnotableParameterCount == 0 ? this.lastRuntimeInvisibleParameterAnnotations.length : this.invisibleAnnotableParameterCount);
         }

         if (this.defaultValue != null) {
            this.symbolTable.addConstantUtf8("AnnotationDefault");
            var1 += 6 + this.defaultValue.length;
         }

         if (this.parameters != null) {
            this.symbolTable.addConstantUtf8("MethodParameters");
            var1 += 7 + this.parameters.length;
         }

         if (this.firstAttribute != null) {
            var1 += this.firstAttribute.computeAttributesSize(this.symbolTable);
         }

         return var1;
      }
   }

   void putMethodInfo(ByteVector var1) {
      boolean var2 = this.symbolTable.getMajorVersion() < 49;
      int var3 = var2 ? 4096 : 0;
      var1.putShort(this.accessFlags & ~var3).putShort(this.nameIndex).putShort(this.descriptorIndex);
      if (this.sourceOffset != 0) {
         var1.putByteArray(this.symbolTable.getSource().classFileBuffer, this.sourceOffset, this.sourceLength);
      } else {
         int var4 = 0;
         if (this.code.length > 0) {
            ++var4;
         }

         if (this.numberOfExceptions > 0) {
            ++var4;
         }

         if ((this.accessFlags & 4096) != 0 && var2) {
            ++var4;
         }

         if (this.signatureIndex != 0) {
            ++var4;
         }

         if ((this.accessFlags & 131072) != 0) {
            ++var4;
         }

         if (this.lastRuntimeVisibleAnnotation != null) {
            ++var4;
         }

         if (this.lastRuntimeInvisibleAnnotation != null) {
            ++var4;
         }

         if (this.lastRuntimeVisibleParameterAnnotations != null) {
            ++var4;
         }

         if (this.lastRuntimeInvisibleParameterAnnotations != null) {
            ++var4;
         }

         if (this.lastRuntimeVisibleTypeAnnotation != null) {
            ++var4;
         }

         if (this.lastRuntimeInvisibleTypeAnnotation != null) {
            ++var4;
         }

         if (this.defaultValue != null) {
            ++var4;
         }

         if (this.parameters != null) {
            ++var4;
         }

         if (this.firstAttribute != null) {
            var4 += this.firstAttribute.getAttributeCount();
         }

         var1.putShort(var4);
         int var6;
         if (this.code.length > 0) {
            int var5 = 10 + this.code.length + Handler.getExceptionTableSize(this.firstHandler);
            var6 = 0;
            if (this.stackMapTableEntries != null) {
               var5 += 8 + this.stackMapTableEntries.length;
               ++var6;
            }

            if (this.lineNumberTable != null) {
               var5 += 8 + this.lineNumberTable.length;
               ++var6;
            }

            if (this.localVariableTable != null) {
               var5 += 8 + this.localVariableTable.length;
               ++var6;
            }

            if (this.localVariableTypeTable != null) {
               var5 += 8 + this.localVariableTypeTable.length;
               ++var6;
            }

            if (this.lastCodeRuntimeVisibleTypeAnnotation != null) {
               var5 += this.lastCodeRuntimeVisibleTypeAnnotation.computeAnnotationsSize("RuntimeVisibleTypeAnnotations");
               ++var6;
            }

            if (this.lastCodeRuntimeInvisibleTypeAnnotation != null) {
               var5 += this.lastCodeRuntimeInvisibleTypeAnnotation.computeAnnotationsSize("RuntimeInvisibleTypeAnnotations");
               ++var6;
            }

            if (this.firstCodeAttribute != null) {
               var5 += this.firstCodeAttribute.computeAttributesSize(this.symbolTable, this.code.data, this.code.length, this.maxStack, this.maxLocals);
               var6 += this.firstCodeAttribute.getAttributeCount();
            }

            var1.putShort(this.symbolTable.addConstantUtf8("Code")).putInt(var5).putShort(this.maxStack).putShort(this.maxLocals).putInt(this.code.length).putByteArray(this.code.data, 0, this.code.length);
            Handler.putExceptionTable(this.firstHandler, var1);
            var1.putShort(var6);
            if (this.stackMapTableEntries != null) {
               boolean var7 = this.symbolTable.getMajorVersion() >= 50;
               var1.putShort(this.symbolTable.addConstantUtf8(var7 ? "StackMapTable" : "StackMap")).putInt(2 + this.stackMapTableEntries.length).putShort(this.stackMapTableNumberOfEntries).putByteArray(this.stackMapTableEntries.data, 0, this.stackMapTableEntries.length);
            }

            if (this.lineNumberTable != null) {
               var1.putShort(this.symbolTable.addConstantUtf8("LineNumberTable")).putInt(2 + this.lineNumberTable.length).putShort(this.lineNumberTableLength).putByteArray(this.lineNumberTable.data, 0, this.lineNumberTable.length);
            }

            if (this.localVariableTable != null) {
               var1.putShort(this.symbolTable.addConstantUtf8("LocalVariableTable")).putInt(2 + this.localVariableTable.length).putShort(this.localVariableTableLength).putByteArray(this.localVariableTable.data, 0, this.localVariableTable.length);
            }

            if (this.localVariableTypeTable != null) {
               var1.putShort(this.symbolTable.addConstantUtf8("LocalVariableTypeTable")).putInt(2 + this.localVariableTypeTable.length).putShort(this.localVariableTypeTableLength).putByteArray(this.localVariableTypeTable.data, 0, this.localVariableTypeTable.length);
            }

            if (this.lastCodeRuntimeVisibleTypeAnnotation != null) {
               this.lastCodeRuntimeVisibleTypeAnnotation.putAnnotations(this.symbolTable.addConstantUtf8("RuntimeVisibleTypeAnnotations"), var1);
            }

            if (this.lastCodeRuntimeInvisibleTypeAnnotation != null) {
               this.lastCodeRuntimeInvisibleTypeAnnotation.putAnnotations(this.symbolTable.addConstantUtf8("RuntimeInvisibleTypeAnnotations"), var1);
            }

            if (this.firstCodeAttribute != null) {
               this.firstCodeAttribute.putAttributes(this.symbolTable, this.code.data, this.code.length, this.maxStack, this.maxLocals, var1);
            }
         }

         if (this.numberOfExceptions > 0) {
            var1.putShort(this.symbolTable.addConstantUtf8("Exceptions")).putInt(2 + 2 * this.numberOfExceptions).putShort(this.numberOfExceptions);
            int[] var10 = this.exceptionIndexTable;
            var6 = var10.length;

            for(int var9 = 0; var9 < var6; ++var9) {
               int var8 = var10[var9];
               var1.putShort(var8);
            }
         }

         Attribute.putAttributes(this.symbolTable, this.accessFlags, this.signatureIndex, var1);
         AnnotationWriter.putAnnotations(this.symbolTable, this.lastRuntimeVisibleAnnotation, this.lastRuntimeInvisibleAnnotation, this.lastRuntimeVisibleTypeAnnotation, this.lastRuntimeInvisibleTypeAnnotation, var1);
         if (this.lastRuntimeVisibleParameterAnnotations != null) {
            AnnotationWriter.putParameterAnnotations(this.symbolTable.addConstantUtf8("RuntimeVisibleParameterAnnotations"), this.lastRuntimeVisibleParameterAnnotations, this.visibleAnnotableParameterCount == 0 ? this.lastRuntimeVisibleParameterAnnotations.length : this.visibleAnnotableParameterCount, var1);
         }

         if (this.lastRuntimeInvisibleParameterAnnotations != null) {
            AnnotationWriter.putParameterAnnotations(this.symbolTable.addConstantUtf8("RuntimeInvisibleParameterAnnotations"), this.lastRuntimeInvisibleParameterAnnotations, this.invisibleAnnotableParameterCount == 0 ? this.lastRuntimeInvisibleParameterAnnotations.length : this.invisibleAnnotableParameterCount, var1);
         }

         if (this.defaultValue != null) {
            var1.putShort(this.symbolTable.addConstantUtf8("AnnotationDefault")).putInt(this.defaultValue.length).putByteArray(this.defaultValue.data, 0, this.defaultValue.length);
         }

         if (this.parameters != null) {
            var1.putShort(this.symbolTable.addConstantUtf8("MethodParameters")).putInt(1 + this.parameters.length).putByte(this.parametersCount).putByteArray(this.parameters.data, 0, this.parameters.length);
         }

         if (this.firstAttribute != null) {
            this.firstAttribute.putAttributes(this.symbolTable, var1);
         }

      }
   }

   final void collectAttributePrototypes(Attribute$Set var1) {
      var1.addAttributes(this.firstAttribute);
      var1.addAttributes(this.firstCodeAttribute);
   }
}
