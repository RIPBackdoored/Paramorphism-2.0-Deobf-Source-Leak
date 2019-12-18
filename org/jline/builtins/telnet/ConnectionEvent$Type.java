package org.jline.builtins.telnet;

public final class ConnectionEvent$Type extends Enum {
   public static final ConnectionEvent$Type CONNECTION_IDLE = new ConnectionEvent$Type("CONNECTION_IDLE", 0);
   public static final ConnectionEvent$Type CONNECTION_TIMEDOUT = new ConnectionEvent$Type("CONNECTION_TIMEDOUT", 1);
   public static final ConnectionEvent$Type CONNECTION_LOGOUTREQUEST = new ConnectionEvent$Type("CONNECTION_LOGOUTREQUEST", 2);
   public static final ConnectionEvent$Type CONNECTION_BREAK = new ConnectionEvent$Type("CONNECTION_BREAK", 3);
   public static final ConnectionEvent$Type CONNECTION_TERMINAL_GEOMETRY_CHANGED = new ConnectionEvent$Type("CONNECTION_TERMINAL_GEOMETRY_CHANGED", 4);
   private static final ConnectionEvent$Type[] $VALUES = new ConnectionEvent$Type[]{CONNECTION_IDLE, CONNECTION_TIMEDOUT, CONNECTION_LOGOUTREQUEST, CONNECTION_BREAK, CONNECTION_TERMINAL_GEOMETRY_CHANGED};

   public static ConnectionEvent$Type[] values() {
      return (ConnectionEvent$Type[])$VALUES.clone();
   }

   public static ConnectionEvent$Type valueOf(String var0) {
      return (ConnectionEvent$Type)Enum.valueOf(ConnectionEvent$Type.class, var0);
   }

   private ConnectionEvent$Type(String var1, int var2) {
      super(var1, var2);
   }
}
