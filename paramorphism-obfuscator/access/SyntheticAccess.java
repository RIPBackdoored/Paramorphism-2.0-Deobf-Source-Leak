package paramorphism-obfuscator.access;

import paramorphism-obfuscator.wrappers.*;

public final class SyntheticAccess extends AccessWrapper
{
    public static final SyntheticAccess blt;
    
    private SyntheticAccess() {
        super.setAccess(4096);
    }
    
    static {
        blt = new SyntheticAccess();
    }
}
