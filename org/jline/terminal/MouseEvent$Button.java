package org.jline.terminal;

public enum Button
{
    NoButton, 
    Button1, 
    Button2, 
    Button3, 
    WheelUp, 
    WheelDown;
    
    private static final Button[] $VALUES;
    
    public static Button[] values() {
        return Button.$VALUES.clone();
    }
    
    public static Button valueOf(final String s) {
        return Enum.valueOf(Button.class, s);
    }
    
    static {
        $VALUES = new Button[] { Button.NoButton, Button.Button1, Button.Button2, Button.Button3, Button.WheelUp, Button.WheelDown };
    }
}
