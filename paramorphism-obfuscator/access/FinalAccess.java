package paramorphism-obfuscator.access;

import paramorphism-obfuscator.wrappers.*;

public final class FinalAccess extends AccessWrapper
{
    public static final FinalAccess ble;
    
    private FinalAccess() {
        super.setAccess(16);
    }
    
    static {
        ble = new FinalAccess();
    }
}
