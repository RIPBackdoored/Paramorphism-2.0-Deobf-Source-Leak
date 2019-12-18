package org.eclipse.aether.transfer;

public enum RequestType
{
    GET, 
    GET_EXISTENCE, 
    PUT;
    
    private static final RequestType[] $VALUES;
    
    public static RequestType[] values() {
        return RequestType.$VALUES.clone();
    }
    
    public static RequestType valueOf(final String s) {
        return Enum.valueOf(RequestType.class, s);
    }
    
    static {
        $VALUES = new RequestType[] { RequestType.GET, RequestType.GET_EXISTENCE, RequestType.PUT };
    }
}
