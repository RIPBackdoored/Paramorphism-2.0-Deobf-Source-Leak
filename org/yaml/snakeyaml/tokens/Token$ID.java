package org.yaml.snakeyaml.tokens;

public enum ID
{
    Alias, 
    Anchor, 
    BlockEnd, 
    BlockEntry, 
    BlockMappingStart, 
    BlockSequenceStart, 
    Directive, 
    DocumentEnd, 
    DocumentStart, 
    FlowEntry, 
    FlowMappingEnd, 
    FlowMappingStart, 
    FlowSequenceEnd, 
    FlowSequenceStart, 
    Key, 
    Scalar, 
    StreamEnd, 
    StreamStart, 
    Tag, 
    Value, 
    Whitespace, 
    Comment, 
    Error;
    
    private static final /* synthetic */ ID[] $VALUES;
    
    public static ID[] values() {
        return ID.$VALUES.clone();
    }
    
    public static ID valueOf(final String name) {
        return Enum.valueOf(ID.class, name);
    }
    
    static {
        $VALUES = new ID[] { ID.Alias, ID.Anchor, ID.BlockEnd, ID.BlockEntry, ID.BlockMappingStart, ID.BlockSequenceStart, ID.Directive, ID.DocumentEnd, ID.DocumentStart, ID.FlowEntry, ID.FlowMappingEnd, ID.FlowMappingStart, ID.FlowSequenceEnd, ID.FlowSequenceStart, ID.Key, ID.Scalar, ID.StreamEnd, ID.StreamStart, ID.Tag, ID.Value, ID.Whitespace, ID.Comment, ID.Error };
    }
}
