package paramorphism-obfuscator.verifierDisabler;

import kotlin.jvm.functions.*;
import paramorphism-obfuscator.*;
import kotlin.*;
import org.objectweb.asm.tree.*;
import org.jetbrains.annotations.*;
import paramorphism-obfuscator.wrappers.*;
import kotlin.jvm.internal.*;
import paramorphism-obfuscator.instruction.*;
import java.lang.reflect.*;
import org.objectweb.asm.*;

public final class JVMDllLambda extends Lambda implements Function1<xj, Unit>
{
    public final ClassWrapper rx;
    public final FieldNode ry;
    public final FieldNode rz;
    public final FieldNode sa;
    
    @Override
    public Object invoke(final Object o) {
        this.a((MethodWrapper)o);
        return Unit.INSTANCE;
    }
    
    public final void a(@NotNull final MethodWrapper methodWrapper) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: goto            18
        //     6: nop            
        //     7: nop            
        //     8: athrow         
        //     9: nop            
        //    10: nop            
        //    11: athrow         
        //    12: goto            3
        //    15: nop            
        //    16: nop            
        //    17: athrow         
        //    18: aload_1        
        //    19: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //    22: ldc             "os.name"
        //    24: invokestatic    paramorphism-obfuscator/instruction/NumberInstruction.addLdc:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;Ljava/lang/Object;)V
        //    27: aload_1        
        //    28: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //    31: ldc             Ljava/lang/System;.class
        //    33: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //    36: ldc             "getProperty"
        //    38: ldc             Ljava/lang/String;.class
        //    40: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //    43: iconst_1       
        //    44: anewarray       Ljava/lang/Object;
        //    47: dup            
        //    48: iconst_0       
        //    49: ldc             Ljava/lang/String;.class
        //    51: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //    54: aastore        
        //    55: invokestatic    paramorphism-obfuscator/instruction/MethodInstruction.addInvokeStatic:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;[Ljava/lang/Object;)V
        //    58: aload_1        
        //    59: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //    62: ldc             Ljava/lang/String;.class
        //    64: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //    67: ldc             "toLowerCase"
        //    69: ldc             Ljava/lang/String;.class
        //    71: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //    74: iconst_0       
        //    75: anewarray       Ljava/lang/Object;
        //    78: invokestatic    paramorphism-obfuscator/instruction/MethodInstruction.addInvokeVirtual:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;[Ljava/lang/Object;)V
        //    81: aload_1        
        //    82: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //    85: invokestatic    paramorphism-obfuscator/instruction/VarInstruction.addObjectStoreZero:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;)Lkotlin/Unit;
        //    88: pop            
        //    89: aload_1        
        //    90: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //    93: invokestatic    paramorphism-obfuscator/instruction/VarInstruction.addObjectLoadZero:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;)Lkotlin/Unit;
        //    96: pop            
        //    97: aload_1        
        //    98: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   101: ldc             "windows"
        //   103: invokestatic    paramorphism-obfuscator/instruction/NumberInstruction.addLdc:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;Ljava/lang/Object;)V
        //   106: aload_1        
        //   107: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   110: ldc             Ljava/lang/String;.class
        //   112: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //   115: ldc             "contains"
        //   117: aload_1        
        //   118: invokevirtual   paramorphism-obfuscator/wrappers/MethodWrapper.bm:()Lorg/objectweb/asm/Type;
        //   121: iconst_1       
        //   122: anewarray       Ljava/lang/Object;
        //   125: dup            
        //   126: iconst_0       
        //   127: ldc             Ljava/lang/CharSequence;.class
        //   129: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //   132: aastore        
        //   133: invokestatic    paramorphism-obfuscator/instruction/MethodInstruction.addInvokeVirtual:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;[Ljava/lang/Object;)V
        //   136: aload_1        
        //   137: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   140: aload_1        
        //   141: invokevirtual   paramorphism-obfuscator/wrappers/MethodWrapper.getLabels:()Lparamorphism-obfuscator/wrappers/LabelsWrapper;
        //   144: ldc             "is_not_windows"
        //   146: invokevirtual   paramorphism-obfuscator/wrappers/LabelsWrapper.a:(Ljava/lang/String;)Lparamorphism-obfuscator/yg;
        //   149: invokestatic    paramorphism-obfuscator/instruction/JumpInstruction.addIfEqual:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;Ljava/lang/Object;)V
        //   152: aload_1        
        //   153: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   156: ldc             "java.vm.name"
        //   158: invokestatic    paramorphism-obfuscator/instruction/NumberInstruction.addLdc:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;Ljava/lang/Object;)V
        //   161: aload_1        
        //   162: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   165: ldc             Ljava/lang/System;.class
        //   167: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //   170: ldc             "getProperty"
        //   172: ldc             Ljava/lang/String;.class
        //   174: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //   177: iconst_1       
        //   178: anewarray       Ljava/lang/Object;
        //   181: dup            
        //   182: iconst_0       
        //   183: ldc             Ljava/lang/String;.class
        //   185: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //   188: aastore        
        //   189: invokestatic    paramorphism-obfuscator/instruction/MethodInstruction.addInvokeStatic:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;[Ljava/lang/Object;)V
        //   192: aload_1        
        //   193: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   196: invokestatic    paramorphism-obfuscator/instruction/VarInstruction.addObjectStoreOne:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;)Lkotlin/Unit;
        //   199: pop            
        //   200: aload_1        
        //   201: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   204: invokestatic    paramorphism-obfuscator/instruction/VarInstruction.addObjectLoadOne:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;)Lkotlin/Unit;
        //   207: pop            
        //   208: aload_1        
        //   209: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   212: ldc             "Client VM"
        //   214: invokestatic    paramorphism-obfuscator/instruction/NumberInstruction.addLdc:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;Ljava/lang/Object;)V
        //   217: aload_1        
        //   218: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   221: ldc             Ljava/lang/String;.class
        //   223: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //   226: ldc             "contains"
        //   228: aload_1        
        //   229: invokevirtual   paramorphism-obfuscator/wrappers/MethodWrapper.bm:()Lorg/objectweb/asm/Type;
        //   232: iconst_1       
        //   233: anewarray       Ljava/lang/Object;
        //   236: dup            
        //   237: iconst_0       
        //   238: ldc             Ljava/lang/CharSequence;.class
        //   240: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //   243: aastore        
        //   244: invokestatic    paramorphism-obfuscator/instruction/MethodInstruction.addInvokeVirtual:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;[Ljava/lang/Object;)V
        //   247: aload_1        
        //   248: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   251: aload_1        
        //   252: invokevirtual   paramorphism-obfuscator/wrappers/MethodWrapper.getLabels:()Lparamorphism-obfuscator/wrappers/LabelsWrapper;
        //   255: ldc             "is_not_client"
        //   257: invokevirtual   paramorphism-obfuscator/wrappers/LabelsWrapper.a:(Ljava/lang/String;)Lparamorphism-obfuscator/yg;
        //   260: invokestatic    paramorphism-obfuscator/instruction/JumpInstruction.addIfEqual:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;Ljava/lang/Object;)V
        //   263: aload_1        
        //   264: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   267: ldc             "/bin/client/jvm.dll"
        //   269: invokestatic    paramorphism-obfuscator/instruction/NumberInstruction.addLdc:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;Ljava/lang/Object;)V
        //   272: aload_1        
        //   273: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   276: aload_1        
        //   277: invokevirtual   paramorphism-obfuscator/wrappers/MethodWrapper.getLabels:()Lparamorphism-obfuscator/wrappers/LabelsWrapper;
        //   280: ldc             "post_vm_check"
        //   282: invokevirtual   paramorphism-obfuscator/wrappers/LabelsWrapper.a:(Ljava/lang/String;)Lparamorphism-obfuscator/yg;
        //   285: invokestatic    paramorphism-obfuscator/instruction/JumpInstruction.addGoto:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;Ljava/lang/Object;)V
        //   288: aload_1        
        //   289: invokevirtual   paramorphism-obfuscator/wrappers/MethodWrapper.getLabels:()Lparamorphism-obfuscator/wrappers/LabelsWrapper;
        //   292: ldc             "is_not_client"
        //   294: invokevirtual   paramorphism-obfuscator/wrappers/LabelsWrapper.a:(Ljava/lang/String;)Lparamorphism-obfuscator/yg;
        //   297: invokevirtual   paramorphism-obfuscator/yg.a:()V
        //   300: aload_1        
        //   301: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   304: ldc             "/bin/server/jvm.dll"
        //   306: invokestatic    paramorphism-obfuscator/instruction/NumberInstruction.addLdc:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;Ljava/lang/Object;)V
        //   309: aload_1        
        //   310: invokevirtual   paramorphism-obfuscator/wrappers/MethodWrapper.getLabels:()Lparamorphism-obfuscator/wrappers/LabelsWrapper;
        //   313: ldc             "post_vm_check"
        //   315: invokevirtual   paramorphism-obfuscator/wrappers/LabelsWrapper.a:(Ljava/lang/String;)Lparamorphism-obfuscator/yg;
        //   318: invokevirtual   paramorphism-obfuscator/yg.a:()V
        //   321: aload_1        
        //   322: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   325: invokestatic    paramorphism-obfuscator/instruction/VarInstruction.addObjectStoreTwo:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;)Lkotlin/Unit;
        //   328: pop            
        //   329: aload_1        
        //   330: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   333: ldc             Ljava/lang/StringBuilder;.class
        //   335: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //   338: invokestatic    paramorphism-obfuscator/instruction/TypeInstruction.addNew:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;Ljava/lang/Object;)V
        //   341: aload_1        
        //   342: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   345: invokestatic    paramorphism-obfuscator/instruction/StackInstruction.addDup:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;)Lkotlin/Unit;
        //   348: pop            
        //   349: aload_1        
        //   350: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   353: ldc             Ljava/lang/StringBuilder;.class
        //   355: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //   358: ldc             "<init>"
        //   360: aload_1        
        //   361: invokevirtual   paramorphism-obfuscator/wrappers/MethodWrapper.be:()Lorg/objectweb/asm/Type;
        //   364: iconst_0       
        //   365: anewarray       Ljava/lang/Object;
        //   368: invokestatic    paramorphism-obfuscator/instruction/MethodInstruction.addInvokeSpecial:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;[Ljava/lang/Object;)V
        //   371: aload_1        
        //   372: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   375: ldc             "java.home"
        //   377: invokestatic    paramorphism-obfuscator/instruction/NumberInstruction.addLdc:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;Ljava/lang/Object;)V
        //   380: aload_1        
        //   381: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   384: ldc             Ljava/lang/System;.class
        //   386: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //   389: ldc             "getProperty"
        //   391: ldc             Ljava/lang/String;.class
        //   393: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //   396: iconst_1       
        //   397: anewarray       Ljava/lang/Object;
        //   400: dup            
        //   401: iconst_0       
        //   402: ldc             Ljava/lang/String;.class
        //   404: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //   407: aastore        
        //   408: invokestatic    paramorphism-obfuscator/instruction/MethodInstruction.addInvokeStatic:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;[Ljava/lang/Object;)V
        //   411: aload_1        
        //   412: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   415: ldc             Ljava/lang/StringBuilder;.class
        //   417: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //   420: ldc             "append"
        //   422: ldc             Ljava/lang/StringBuilder;.class
        //   424: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //   427: iconst_1       
        //   428: anewarray       Ljava/lang/Object;
        //   431: dup            
        //   432: iconst_0       
        //   433: ldc             Ljava/lang/String;.class
        //   435: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //   438: aastore        
        //   439: invokestatic    paramorphism-obfuscator/instruction/MethodInstruction.addInvokeVirtual:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;[Ljava/lang/Object;)V
        //   442: aload_1        
        //   443: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   446: invokestatic    paramorphism-obfuscator/instruction/VarInstruction.addObjectLoadTwo:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;)Lkotlin/Unit;
        //   449: pop            
        //   450: aload_1        
        //   451: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   454: ldc             Ljava/lang/StringBuilder;.class
        //   456: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //   459: ldc             "append"
        //   461: ldc             Ljava/lang/StringBuilder;.class
        //   463: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //   466: iconst_1       
        //   467: anewarray       Ljava/lang/Object;
        //   470: dup            
        //   471: iconst_0       
        //   472: ldc             Ljava/lang/String;.class
        //   474: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //   477: aastore        
        //   478: invokestatic    paramorphism-obfuscator/instruction/MethodInstruction.addInvokeVirtual:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;[Ljava/lang/Object;)V
        //   481: aload_1        
        //   482: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   485: ldc             Ljava/lang/StringBuilder;.class
        //   487: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //   490: ldc             "toString"
        //   492: ldc             Ljava/lang/String;.class
        //   494: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //   497: iconst_0       
        //   498: anewarray       Ljava/lang/Object;
        //   501: invokestatic    paramorphism-obfuscator/instruction/MethodInstruction.addInvokeVirtual:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;[Ljava/lang/Object;)V
        //   504: aload_1        
        //   505: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   508: ldc             Ljava/lang/System;.class
        //   510: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //   513: ldc             "load"
        //   515: aload_1        
        //   516: invokevirtual   paramorphism-obfuscator/wrappers/MethodWrapper.be:()Lorg/objectweb/asm/Type;
        //   519: iconst_1       
        //   520: anewarray       Ljava/lang/Object;
        //   523: dup            
        //   524: iconst_0       
        //   525: ldc             Ljava/lang/String;.class
        //   527: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //   530: aastore        
        //   531: invokestatic    paramorphism-obfuscator/instruction/MethodInstruction.addInvokeStatic:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;[Ljava/lang/Object;)V
        //   534: aload_1        
        //   535: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   538: aload_0        
        //   539: getfield        paramorphism-obfuscator/verifierDisabler/JVMDllLambda.rx:Lparamorphism-obfuscator/wrappers/ClassWrapper;
        //   542: invokevirtual   paramorphism-obfuscator/wrappers/ClassWrapper.getType:()Lorg/objectweb/asm/Type;
        //   545: invokestatic    paramorphism-obfuscator/instruction/NumberInstruction.addLdc:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;Ljava/lang/Object;)V
        //   548: aload_1        
        //   549: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   552: ldc             Ljava/lang/Class;.class
        //   554: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //   557: ldc             "getClassLoader"
        //   559: ldc             Ljava/lang/ClassLoader;.class
        //   561: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //   564: iconst_0       
        //   565: anewarray       Ljava/lang/Object;
        //   568: invokestatic    paramorphism-obfuscator/instruction/MethodInstruction.addInvokeVirtual:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;[Ljava/lang/Object;)V
        //   571: aload_1        
        //   572: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   575: aload_0        
        //   576: getfield        paramorphism-obfuscator/verifierDisabler/JVMDllLambda.rx:Lparamorphism-obfuscator/wrappers/ClassWrapper;
        //   579: invokevirtual   paramorphism-obfuscator/wrappers/ClassWrapper.getType:()Lorg/objectweb/asm/Type;
        //   582: aload_0        
        //   583: getfield        paramorphism-obfuscator/verifierDisabler/JVMDllLambda.ry:Lorg/objectweb/asm/tree/FieldNode;
        //   586: getfield        org/objectweb/asm/tree/FieldNode.name:Ljava/lang/String;
        //   589: ldc             Ljava/lang/ClassLoader;.class
        //   591: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //   594: invokestatic    paramorphism-obfuscator/instruction/FieldInstruction.addPutStatic:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)V
        //   597: aload_1        
        //   598: invokevirtual   paramorphism-obfuscator/wrappers/MethodWrapper.getLabels:()Lparamorphism-obfuscator/wrappers/LabelsWrapper;
        //   601: ldc             "is_not_windows"
        //   603: invokevirtual   paramorphism-obfuscator/wrappers/LabelsWrapper.a:(Ljava/lang/String;)Lparamorphism-obfuscator/yg;
        //   606: invokevirtual   paramorphism-obfuscator/yg.a:()V
        //   609: aload_1        
        //   610: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   613: aload_1        
        //   614: ldc             Ljava/lang/ClassLoader;.class
        //   616: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //   619: invokevirtual   paramorphism-obfuscator/wrappers/MethodWrapper.a:(Ljava/lang/Object;)Lorg/objectweb/asm/Type;
        //   622: invokestatic    paramorphism-obfuscator/instruction/NumberInstruction.addLdc:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;Ljava/lang/Object;)V
        //   625: aload_1        
        //   626: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   629: ldc             "findNative"
        //   631: invokestatic    paramorphism-obfuscator/instruction/NumberInstruction.addLdc:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;Ljava/lang/Object;)V
        //   634: aload_1        
        //   635: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   638: invokestatic    paramorphism-obfuscator/instruction/NumberInstruction.addITwo:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;)Lkotlin/Unit;
        //   641: pop            
        //   642: aload_1        
        //   643: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   646: ldc             Ljava/lang/Class;.class
        //   648: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //   651: invokestatic    paramorphism-obfuscator/instruction/ArrayVarInstruction.addNewArray:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;Ljava/lang/Object;)V
        //   654: aload_1        
        //   655: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   658: invokestatic    paramorphism-obfuscator/instruction/StackInstruction.addDup:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;)Lkotlin/Unit;
        //   661: pop            
        //   662: aload_1        
        //   663: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   666: invokestatic    paramorphism-obfuscator/instruction/NumberInstruction.addIZero:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;)Lkotlin/Unit;
        //   669: pop            
        //   670: aload_1        
        //   671: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   674: aload_1        
        //   675: ldc             Ljava/lang/ClassLoader;.class
        //   677: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //   680: invokevirtual   paramorphism-obfuscator/wrappers/MethodWrapper.a:(Ljava/lang/Object;)Lorg/objectweb/asm/Type;
        //   683: invokestatic    paramorphism-obfuscator/instruction/NumberInstruction.addLdc:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;Ljava/lang/Object;)V
        //   686: aload_1        
        //   687: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   690: invokestatic    paramorphism-obfuscator/instruction/ArrayVarInstruction.addObjectArrayStore:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;)Lkotlin/Unit;
        //   693: pop            
        //   694: aload_1        
        //   695: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   698: invokestatic    paramorphism-obfuscator/instruction/StackInstruction.addDup:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;)Lkotlin/Unit;
        //   701: pop            
        //   702: aload_1        
        //   703: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   706: invokestatic    paramorphism-obfuscator/instruction/NumberInstruction.addIOne:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;)Lkotlin/Unit;
        //   709: pop            
        //   710: aload_1        
        //   711: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   714: aload_1        
        //   715: ldc             Ljava/lang/String;.class
        //   717: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //   720: invokevirtual   paramorphism-obfuscator/wrappers/MethodWrapper.a:(Ljava/lang/Object;)Lorg/objectweb/asm/Type;
        //   723: invokestatic    paramorphism-obfuscator/instruction/NumberInstruction.addLdc:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;Ljava/lang/Object;)V
        //   726: aload_1        
        //   727: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   730: invokestatic    paramorphism-obfuscator/instruction/ArrayVarInstruction.addObjectArrayStore:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;)Lkotlin/Unit;
        //   733: pop            
        //   734: aload_1        
        //   735: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   738: ldc             Ljava/lang/Class;.class
        //   740: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //   743: ldc             "getDeclaredMethod"
        //   745: ldc             Ljava/lang/reflect/Method;.class
        //   747: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //   750: iconst_2       
        //   751: anewarray       Ljava/lang/Object;
        //   754: dup            
        //   755: iconst_0       
        //   756: ldc             Ljava/lang/String;.class
        //   758: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //   761: aastore        
        //   762: dup            
        //   763: iconst_1       
        //   764: ldc             "[Ljava/lang/Class;"
        //   766: invokestatic    org/objectweb/asm/Type.getType:(Ljava/lang/String;)Lorg/objectweb/asm/Type;
        //   769: aastore        
        //   770: invokestatic    paramorphism-obfuscator/instruction/MethodInstruction.addInvokeVirtual:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;[Ljava/lang/Object;)V
        //   773: aload_1        
        //   774: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   777: aload_0        
        //   778: getfield        paramorphism-obfuscator/verifierDisabler/JVMDllLambda.rx:Lparamorphism-obfuscator/wrappers/ClassWrapper;
        //   781: invokevirtual   paramorphism-obfuscator/wrappers/ClassWrapper.getType:()Lorg/objectweb/asm/Type;
        //   784: aload_0        
        //   785: getfield        paramorphism-obfuscator/verifierDisabler/JVMDllLambda.rz:Lorg/objectweb/asm/tree/FieldNode;
        //   788: getfield        org/objectweb/asm/tree/FieldNode.name:Ljava/lang/String;
        //   791: ldc             Ljava/lang/reflect/Method;.class
        //   793: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //   796: invokestatic    paramorphism-obfuscator/instruction/FieldInstruction.addPutStatic:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)V
        //   799: aload_1        
        //   800: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   803: new             Lparamorphism-obfuscator/verifierDisabler/PutVolatileLambda;
        //   806: dup            
        //   807: aload_0        
        //   808: invokespecial   paramorphism-obfuscator/verifierDisabler/PutVolatileLambda.<init>:(Lparamorphism-obfuscator/verifierDisabler/JVMDllLambda;)V
        //   811: checkcast       Lkotlin/jvm/functions/Function1;
        //   814: invokestatic    paramorphism-obfuscator/yc.a:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;Lkotlin/jvm/functions/Function1;)Lparamorphism-obfuscator/xt;
        //   817: ldc             Ljava/lang/Throwable;.class
        //   819: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //   822: iconst_0       
        //   823: getstatic       paramorphism-obfuscator/kz.te:Lparamorphism-obfuscator/kz;
        //   826: checkcast       Lkotlin/jvm/functions/Function1;
        //   829: iconst_2       
        //   830: aconst_null    
        //   831: invokestatic    paramorphism-obfuscator/xt.a:(Lparamorphism-obfuscator/xt;Ljava/lang/Object;ZLkotlin/jvm/functions/Function1;ILjava/lang/Object;)Lparamorphism-obfuscator/xt;
        //   834: pop            
        //   835: aload_1        
        //   836: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   839: aload_0        
        //   840: getfield        paramorphism-obfuscator/verifierDisabler/JVMDllLambda.rx:Lparamorphism-obfuscator/wrappers/ClassWrapper;
        //   843: invokevirtual   paramorphism-obfuscator/wrappers/ClassWrapper.getType:()Lorg/objectweb/asm/Type;
        //   846: aload_0        
        //   847: getfield        paramorphism-obfuscator/verifierDisabler/JVMDllLambda.rz:Lorg/objectweb/asm/tree/FieldNode;
        //   850: getfield        org/objectweb/asm/tree/FieldNode.name:Ljava/lang/String;
        //   853: ldc             Ljava/lang/reflect/Method;.class
        //   855: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //   858: invokestatic    paramorphism-obfuscator/instruction/FieldInstruction.addGetStatic:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)V
        //   861: aload_1        
        //   862: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   865: invokestatic    paramorphism-obfuscator/instruction/NumberInstruction.addIOne:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;)Lkotlin/Unit;
        //   868: pop            
        //   869: aload_1        
        //   870: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   873: ldc             Ljava/lang/reflect/Method;.class
        //   875: invokestatic    kotlin/jvm/internal/Reflection.getOrCreateKotlinClass:(Ljava/lang/Class;)Lkotlin/reflect/KClass;
        //   878: ldc_w           "setAccessible"
        //   881: aload_1        
        //   882: invokevirtual   paramorphism-obfuscator/wrappers/MethodWrapper.be:()Lorg/objectweb/asm/Type;
        //   885: iconst_1       
        //   886: anewarray       Ljava/lang/Object;
        //   889: dup            
        //   890: iconst_0       
        //   891: aload_1        
        //   892: invokevirtual   paramorphism-obfuscator/wrappers/MethodWrapper.bm:()Lorg/objectweb/asm/Type;
        //   895: aastore        
        //   896: invokestatic    paramorphism-obfuscator/instruction/MethodInstruction.addInvokeVirtual:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;[Ljava/lang/Object;)V
        //   899: aload_1        
        //   900: checkcast       Lparamorphism-obfuscator/wrappers/IInstructionWrapper;
        //   903: invokestatic    paramorphism-obfuscator/instruction/JumpInstruction.addDuplicateReturn:(Lparamorphism-obfuscator/wrappers/IInstructionWrapper;)Lkotlin/Unit;
        //   906: pop            
        //   907: return         
        //    StackMapTable: 00 06 03 FF 00 02 00 00 00 01 07 00 1E 42 07 00 1E FD 00 02 07 00 02 07 00 22 FF 00 02 00 00 00 01 07 00 1E FD 00 02 07 00 02 07 00 22
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public JVMDllLambda(final ClassWrapper rx, final FieldNode ry, final FieldNode rz, final FieldNode sa) {
        this.rx = rx;
        this.ry = ry;
        this.rz = rz;
        this.sa = sa;
        super(1);
    }
}
