package org.jline.reader.impl;

protected enum ViMoveMode
{
    NORMAL, 
    YANK, 
    DELETE, 
    CHANGE;
    
    private static final ViMoveMode[] $VALUES;
    
    public static ViMoveMode[] values() {
        return ViMoveMode.$VALUES.clone();
    }
    
    public static ViMoveMode valueOf(final String s) {
        return Enum.valueOf(ViMoveMode.class, s);
    }
    
    static {
        $VALUES = new ViMoveMode[] { ViMoveMode.NORMAL, ViMoveMode.YANK, ViMoveMode.DELETE, ViMoveMode.CHANGE };
    }
}
