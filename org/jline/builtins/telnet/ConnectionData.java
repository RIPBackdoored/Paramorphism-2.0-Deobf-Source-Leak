package org.jline.builtins.telnet;

import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ConnectionData {
   private ConnectionManager connectionManager;
   private Socket socket;
   private InetAddress address;
   private Map environment;
   private String hostName;
   private String hostAddress;
   private int port;
   private Locale locale;
   private long lastActivity;
   private boolean warned;
   private String negotiatedTerminalType;
   private int[] terminalGeometry;
   private boolean terminalGeometryChanged = true;
   private String loginShell;
   private boolean lineMode = false;

   public ConnectionData(Socket var1, ConnectionManager var2) {
      super();
      this.socket = var1;
      this.connectionManager = var2;
      this.address = var1.getInetAddress();
      this.setHostName();
      this.setHostAddress();
      this.setLocale();
      this.port = var1.getPort();
      this.terminalGeometry = new int[2];
      this.terminalGeometry[0] = 80;
      this.terminalGeometry[1] = 25;
      this.negotiatedTerminalType = "default";
      this.environment = new HashMap(20);
      this.activity();
   }

   public ConnectionManager getManager() {
      return this.connectionManager;
   }

   public Socket getSocket() {
      return this.socket;
   }

   public int getPort() {
      return this.port;
   }

   public String getHostName() {
      return this.hostName;
   }

   public String getHostAddress() {
      return this.hostAddress;
   }

   public InetAddress getInetAddress() {
      return this.address;
   }

   public Locale getLocale() {
      return this.locale;
   }

   public long getLastActivity() {
      return this.lastActivity;
   }

   public void activity() {
      this.warned = false;
      this.lastActivity = System.currentTimeMillis();
   }

   public boolean isWarned() {
      return this.warned;
   }

   public void setWarned(boolean var1) {
      this.warned = var1;
      if (!var1) {
         this.lastActivity = System.currentTimeMillis();
      }

   }

   public void setTerminalGeometry(int var1, int var2) {
      this.terminalGeometry[0] = var1;
      this.terminalGeometry[1] = var2;
      this.terminalGeometryChanged = true;
   }

   public int[] getTerminalGeometry() {
      if (this.terminalGeometryChanged) {
         this.terminalGeometryChanged = false;
      }

      return this.terminalGeometry;
   }

   public int getTerminalColumns() {
      return this.terminalGeometry[0];
   }

   public int getTerminalRows() {
      return this.terminalGeometry[1];
   }

   public boolean isTerminalGeometryChanged() {
      return this.terminalGeometryChanged;
   }

   public String getNegotiatedTerminalType() {
      return this.negotiatedTerminalType;
   }

   public void setNegotiatedTerminalType(String var1) {
      this.negotiatedTerminalType = var1;
   }

   public Map getEnvironment() {
      return this.environment;
   }

   public String getLoginShell() {
      return this.loginShell;
   }

   public void setLoginShell(String var1) {
      this.loginShell = var1;
   }

   public boolean isLineMode() {
      return this.lineMode;
   }

   public void setLineMode(boolean var1) {
      this.lineMode = var1;
   }

   private void setHostName() {
      this.hostName = this.address.getHostName();
   }

   private void setHostAddress() {
      this.hostAddress = this.address.getHostAddress();
   }

   private void setLocale() {
      String var1 = this.getHostName();

      try {
         var1 = var1.substring(var1.lastIndexOf(".") + 1);
         if (var1.equals("at")) {
            this.locale = new Locale("de", "AT");
         } else if (var1.equals("de")) {
            this.locale = new Locale("de", "DE");
         } else if (var1.equals("mx")) {
            this.locale = new Locale("es", "MX");
         } else if (var1.equals("es")) {
            this.locale = new Locale("es", "ES");
         } else if (var1.equals("it")) {
            this.locale = Locale.ITALY;
         } else if (var1.equals("fr")) {
            this.locale = Locale.FRANCE;
         } else if (var1.equals("uk")) {
            this.locale = new Locale("en", "GB");
         } else if (var1.equals("arpa")) {
            this.locale = Locale.US;
         } else if (var1.equals("com")) {
            this.locale = Locale.US;
         } else if (var1.equals("edu")) {
            this.locale = Locale.US;
         } else if (var1.equals("gov")) {
            this.locale = Locale.US;
         } else if (var1.equals("org")) {
            this.locale = Locale.US;
         } else if (var1.equals("mil")) {
            this.locale = Locale.US;
         } else {
            this.locale = Locale.ENGLISH;
         }
      } catch (Exception var3) {
         this.locale = Locale.ENGLISH;
      }

   }
}
