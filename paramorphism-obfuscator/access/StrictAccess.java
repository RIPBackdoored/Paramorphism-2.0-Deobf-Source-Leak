package paramorphism-obfuscator.access;

import paramorphism-obfuscator.wrappers.*;

public final class StrictAccess extends AccessWrapper
{
    public static final StrictAccess blq;
    
    private StrictAccess() {
        super.setAccess(2048);
    }
    
    static {
        blq = new StrictAccess();
    }
}
