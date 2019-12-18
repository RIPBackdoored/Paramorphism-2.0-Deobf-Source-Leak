package org.jline.reader.impl;

protected enum State
{
    NORMAL, 
    DONE, 
    EOF, 
    INTERRUPT;
    
    private static final State[] $VALUES;
    
    public static State[] values() {
        return State.$VALUES.clone();
    }
    
    public static State valueOf(final String s) {
        return Enum.valueOf(State.class, s);
    }
    
    static {
        $VALUES = new State[] { State.NORMAL, State.DONE, State.EOF, State.INTERRUPT };
    }
}
