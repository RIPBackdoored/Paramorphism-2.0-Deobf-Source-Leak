package paramorphism-obfuscator.access;

import paramorphism-obfuscator.wrappers.*;

public final class EnumAccess extends AccessWrapper
{
    public static final EnumAccess bld;
    
    private EnumAccess() {
        super.setAccess(16384);
    }
    
    static {
        bld = new EnumAccess();
    }
}
