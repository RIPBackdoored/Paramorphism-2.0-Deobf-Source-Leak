package paramorphism-obfuscator.verifierDisabler;

import kotlin.jvm.internal.*;
import kotlin.jvm.functions.*;
import paramorphism-obfuscator.*;
import kotlin.*;
import org.objectweb.asm.tree.*;
import org.jetbrains.annotations.*;
import paramorphism-obfuscator.wrappers.*;
import paramorphism-obfuscator.instruction.*;

public final class UnsafeLambda extends Lambda implements Function1<xj, Unit>
{
    public final ClassWrapper sv;
    public final FieldNode sw;
    public final String sx;
    public final FieldNode sy;
    
    @Override
    public Object invoke(final Object o) {
        this.a((MethodWrapper)o);
        return Unit.INSTANCE;
    }
    
    public final void a(@NotNull final MethodWrapper methodWrapper) {
        NumberInstruction.addIZero(methodWrapper);
        FieldInstruction.addPutStatic(methodWrapper, this.sv.getType(), this.sw.name, methodWrapper.bm());
        MethodInstruction.addInvokeStatic(methodWrapper, this.sv.getType(), this.sx, "sun/misc/Unsafe", new Object[0]);
        FieldInstruction.addPutStatic(methodWrapper, this.sv.getType(), this.sy.name, "sun/misc/Unsafe");
        JumpInstruction.addDuplicateReturn(methodWrapper);
    }
    
    public UnsafeLambda(final ClassWrapper sv, final FieldNode sw, final String sx, final FieldNode sy) {
        this.sv = sv;
        this.sw = sw;
        this.sx = sx;
        this.sy = sy;
        super(1);
    }
}
