package org.eclipse.aether.repository;

public final class RepositoryPolicy
{
    public static final String UPDATE_POLICY_NEVER = "never";
    public static final String UPDATE_POLICY_ALWAYS = "always";
    public static final String UPDATE_POLICY_DAILY = "daily";
    public static final String UPDATE_POLICY_INTERVAL = "interval";
    public static final String CHECKSUM_POLICY_FAIL = "fail";
    public static final String CHECKSUM_POLICY_WARN = "warn";
    public static final String CHECKSUM_POLICY_IGNORE = "ignore";
    private final boolean enabled;
    private final String updatePolicy;
    private final String checksumPolicy;
    
    public RepositoryPolicy() {
        this(true, "daily", "warn");
    }
    
    public RepositoryPolicy(final boolean enabled, final String s, final String s2) {
        super();
        this.enabled = enabled;
        this.updatePolicy = ((s != null) ? s : "");
        this.checksumPolicy = ((s2 != null) ? s2 : "");
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public String getUpdatePolicy() {
        return this.updatePolicy;
    }
    
    public String getChecksumPolicy() {
        return this.checksumPolicy;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(256);
        sb.append("enabled=").append(this.isEnabled());
        sb.append(", checksums=").append(this.getChecksumPolicy());
        sb.append(", updates=").append(this.getUpdatePolicy());
        return sb.toString();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }
        final RepositoryPolicy repositoryPolicy = (RepositoryPolicy)o;
        return this.enabled == repositoryPolicy.enabled && this.updatePolicy.equals(repositoryPolicy.updatePolicy) && this.checksumPolicy.equals(repositoryPolicy.checksumPolicy);
    }
    
    @Override
    public int hashCode() {
        return ((17 * 31 + (this.enabled ? 1 : 0)) * 31 + this.updatePolicy.hashCode()) * 31 + this.checksumPolicy.hashCode();
    }
}
