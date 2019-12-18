package org.yaml.snakeyaml;

public class LoaderOptions
{
    private boolean allowDuplicateKeys;
    
    public LoaderOptions() {
        super();
        this.allowDuplicateKeys = true;
    }
    
    public boolean isAllowDuplicateKeys() {
        return this.allowDuplicateKeys;
    }
}
