package org.jline.builtins;

enum State
{
    None, 
    Esc, 
    Str, 
    Csi;
    
    private static final State[] $VALUES;
    
    public static State[] values() {
        return State.$VALUES.clone();
    }
    
    public static State valueOf(final String s) {
        return Enum.valueOf(State.class, s);
    }
    
    static {
        $VALUES = new State[] { State.None, State.Esc, State.Str, State.Csi };
    }
}
