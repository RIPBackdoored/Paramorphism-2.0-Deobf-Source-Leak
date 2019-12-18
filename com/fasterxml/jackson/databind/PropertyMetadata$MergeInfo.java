package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.databind.introspect.*;

public static final class MergeInfo
{
    public final AnnotatedMember getter;
    public final boolean fromDefaults;
    
    protected MergeInfo(final AnnotatedMember getter, final boolean fromDefaults) {
        super();
        this.getter = getter;
        this.fromDefaults = fromDefaults;
    }
    
    public static MergeInfo createForDefaults(final AnnotatedMember annotatedMember) {
        return new MergeInfo(annotatedMember, true);
    }
    
    public static MergeInfo createForTypeOverride(final AnnotatedMember annotatedMember) {
        return new MergeInfo(annotatedMember, false);
    }
    
    public static MergeInfo createForPropertyOverride(final AnnotatedMember annotatedMember) {
        return new MergeInfo(annotatedMember, false);
    }
}
