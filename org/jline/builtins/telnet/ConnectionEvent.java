package org.jline.builtins.telnet;

public class ConnectionEvent
{
    private final Connection source;
    private final Type type;
    
    public ConnectionEvent(final Connection source, final Type type) {
        super();
        this.type = type;
        this.source = source;
    }
    
    public Connection getSource() {
        return this.source;
    }
    
    public Type getType() {
        return this.type;
    }
    
    public enum Type
    {
        CONNECTION_IDLE, 
        CONNECTION_TIMEDOUT, 
        CONNECTION_LOGOUTREQUEST, 
        CONNECTION_BREAK, 
        CONNECTION_TERMINAL_GEOMETRY_CHANGED;
        
        private static final Type[] $VALUES;
        
        public static Type[] values() {
            return Type.$VALUES.clone();
        }
        
        public static Type valueOf(final String s) {
            return Enum.valueOf(Type.class, s);
        }
        
        static {
            $VALUES = new Type[] { Type.CONNECTION_IDLE, Type.CONNECTION_TIMEDOUT, Type.CONNECTION_LOGOUTREQUEST, Type.CONNECTION_BREAK, Type.CONNECTION_TERMINAL_GEOMETRY_CHANGED };
        }
    }
}
