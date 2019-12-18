package paramorphism-obfuscator.access;

import paramorphism-obfuscator.wrappers.*;

public final class AbstractAccess extends AccessWrapper
{
    public static final AbstractAccess bkz;
    
    private AbstractAccess() {
        super.setAccess(1024);
    }
    
    static {
        bkz = new AbstractAccess();
    }
}
