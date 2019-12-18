package com.fasterxml.jackson.databind.cfg;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.util.*;

public final class PackageVersion implements Versioned
{
    public static final Version VERSION;
    
    public PackageVersion() {
        super();
    }
    
    @Override
    public Version version() {
        return PackageVersion.VERSION;
    }
    
    static {
        VERSION = VersionUtil.parseVersion("2.9.6", "com.fasterxml.jackson.core", "jackson-databind");
    }
}
