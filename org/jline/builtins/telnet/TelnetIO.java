package org.jline.builtins.telnet;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TelnetIO {
   protected static final int IAC = 255;
   protected static final int GA = 249;
   protected static final int WILL = 251;
   protected static final int WONT = 252;
   protected static final int DO = 253;
   protected static final int DONT = 254;
   protected static final int SB = 250;
   protected static final int SE = 240;
   protected static final int NOP = 241;
   protected static final int DM = 242;
   protected static final int BRK = 243;
   protected static final int IP = 244;
   protected static final int AO = 245;
   protected static final int AYT = 246;
   protected static final int EC = 247;
   protected static final int EL = 248;
   protected static final int ECHO = 1;
   protected static final int SUPGA = 3;
   protected static final int NAWS = 31;
   protected static final int TTYPE = 24;
   protected static final int IS = 0;
   protected static final int SEND = 1;
   protected static final int LOGOUT = 18;
   protected static final int LINEMODE = 34;
   protected static final int LM_MODE = 1;
   protected static final int LM_EDIT = 1;
   protected static final int LM_TRAPSIG = 2;
   protected static final int LM_MODEACK = 4;
   protected static final int LM_FORWARDMASK = 2;
   protected static final int LM_SLC = 3;
   protected static final int LM_SLC_NOSUPPORT = 0;
   protected static final int LM_SLC_DEFAULT = 3;
   protected static final int LM_SLC_VALUE = 2;
   protected static final int LM_SLC_CANTCHANGE = 1;
   protected static final int LM_SLC_LEVELBITS = 3;
   protected static final int LM_SLC_ACK = 128;
   protected static final int LM_SLC_FLUSHIN = 64;
   protected static final int LM_SLC_FLUSHOUT = 32;
   protected static final int LM_SLC_SYNCH = 1;
   protected static final int LM_SLC_BRK = 2;
   protected static final int LM_SLC_IP = 3;
   protected static final int LM_SLC_AO = 4;
   protected static final int LM_SLC_AYT = 5;
   protected static final int LM_SLC_EOR = 6;
   protected static final int LM_SLC_ABORT = 7;
   protected static final int LM_SLC_EOF = 8;
   protected static final int LM_SLC_SUSP = 9;
   protected static final int NEWENV = 39;
   protected static final int NE_INFO = 2;
   protected static final int NE_VAR = 0;
   protected static final int NE_VALUE = 1;
   protected static final int NE_ESC = 2;
   protected static final int NE_USERVAR = 3;
   protected static final int NE_VAR_OK = 2;
   protected static final int NE_VAR_DEFINED = 1;
   protected static final int NE_VAR_DEFINED_EMPTY = 0;
   protected static final int NE_VAR_UNDEFINED = -1;
   protected static final int NE_IN_ERROR = -2;
   protected static final int NE_IN_END = -3;
   protected static final int NE_VAR_NAME_MAXLENGTH = 50;
   protected static final int NE_VAR_VALUE_MAXLENGTH = 1000;
   protected static final int EXT_ASCII = 17;
   protected static final int SEND_LOC = 23;
   protected static final int AUTHENTICATION = 37;
   protected static final int ENCRYPT = 38;
   private static final Logger LOG = Logger.getLogger(TelnetIO.class.getName());
   private static final int SMALLEST_BELIEVABLE_WIDTH = 20;
   private static final int SMALLEST_BELIEVABLE_HEIGHT = 6;
   private static final int DEFAULT_WIDTH = 80;
   private static final int DEFAULT_HEIGHT = 25;
   private Connection connection;
   private ConnectionData connectionData;
   private DataOutputStream out;
   private DataInputStream in;
   private TelnetIO$IACHandler iacHandler;
   private InetAddress localAddress;
   private boolean noIac = false;
   private boolean initializing;
   private boolean crFlag;

   public TelnetIO() {
      super();
   }

   public void initIO() throws IOException {
      this.iacHandler = new TelnetIO$IACHandler(this);
      this.in = new DataInputStream(this.connectionData.getSocket().getInputStream());
      this.out = new DataOutputStream(new BufferedOutputStream(this.connectionData.getSocket().getOutputStream()));
      this.localAddress = this.connectionData.getSocket().getLocalAddress();
      this.crFlag = false;
      this.initTelnetCommunication();
   }

   public void setConnection(Connection var1) {
      this.connection = var1;
      this.connectionData = this.connection.getConnectionData();
   }

   public void write(byte var1) throws IOException {
      if (!this.crFlag && var1 == 10) {
         this.out.write(13);
      }

      this.out.write(var1);
      if (var1 == 13) {
         this.crFlag = true;
      } else {
         this.crFlag = false;
      }

   }

   public void write(int var1) throws IOException {
      this.write((byte)var1);
   }

   public void write(byte[] var1) throws IOException {
      byte[] var2 = var1;
      int var3 = var1.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         byte var5 = var2[var4];
         this.write(var5);
      }

   }

   public void write(int[] var1) throws IOException {
      int[] var2 = var1;
      int var3 = var1.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         int var5 = var2[var4];
         this.write((byte)var5);
      }

   }

   public void write(char var1) throws IOException {
      this.write((byte)var1);
   }

   public void write(String var1) throws IOException {
      this.write(var1.getBytes());
   }

   public void flush() throws IOException {
      this.out.flush();
   }

   public void closeOutput() {
      try {
         this.write((int)255);
         this.write((int)253);
         this.write((int)18);
         this.out.close();
      } catch (IOException var2) {
         LOG.log(Level.SEVERE, "closeOutput()", var2);
      }

   }

   private void rawWrite(int var1) throws IOException {
      this.out.write(var1);
   }

   public int read() throws IOException {
      int var1 = this.rawread();
      this.noIac = false;

      while(var1 == 255 && !this.noIac) {
         var1 = this.rawread();
         if (var1 != 255) {
            this.iacHandler.handleC(var1);
            var1 = this.rawread();
         } else {
            this.noIac = true;
         }
      }

      return this.stripCRSeq(var1);
   }

   public void closeInput() {
      try {
         this.in.close();
      } catch (IOException var2) {
      }

   }

   private int read16int() throws IOException {
      int var1 = this.in.readUnsignedShort();
      return var1;
   }

   private int rawread() throws IOException {
      boolean var1 = false;
      int var2 = this.in.readUnsignedByte();
      this.connectionData.activity();
      return var2;
   }

   private int stripCRSeq(int var1) throws IOException {
      if (var1 == 13) {
         this.rawread();
         return 10;
      } else {
         return var1;
      }
   }

   private void initTelnetCommunication() {
      this.initializing = true;
      boolean var9 = false;

      label77: {
         label76: {
            try {
               var9 = true;
               if (this.connectionData.isLineMode()) {
                  this.iacHandler.doLineModeInit();
                  LOG.log(Level.FINE, "Line mode initialized.");
               } else {
                  this.iacHandler.doCharacterModeInit();
                  LOG.log(Level.FINE, "Character mode initialized.");
               }

               this.connectionData.getSocket().setSoTimeout(1000);
               this.read();
               var9 = false;
               break label76;
            } catch (Exception var13) {
               var9 = false;
            } finally {
               if (var9) {
                  try {
                     this.connectionData.getSocket().setSoTimeout(0);
                  } catch (Exception var10) {
                     LOG.log(Level.SEVERE, "initTelnetCommunication()", var10);
                  }

               }
            }

            try {
               this.connectionData.getSocket().setSoTimeout(0);
            } catch (Exception var11) {
               LOG.log(Level.SEVERE, "initTelnetCommunication()", var11);
            }
            break label77;
         }

         try {
            this.connectionData.getSocket().setSoTimeout(0);
         } catch (Exception var12) {
            LOG.log(Level.SEVERE, "initTelnetCommunication()", var12);
         }
      }

      this.initializing = false;
   }

   private void IamHere() {
      try {
         this.write("[" + this.localAddress.toString() + ":Yes]");
         this.flush();
      } catch (Exception var2) {
         LOG.log(Level.SEVERE, "IamHere()", var2);
      }

   }

   private void nvtBreak() {
      this.connection.processConnectionEvent(new ConnectionEvent(this.connection, ConnectionEvent$Type.CONNECTION_BREAK));
   }

   private void setTerminalGeometry(int var1, int var2) {
      if (var1 < 20) {
         var1 = 80;
      }

      if (var2 < 6) {
         var2 = 25;
      }

      this.connectionData.setTerminalGeometry(var1, var2);
      this.connection.processConnectionEvent(new ConnectionEvent(this.connection, ConnectionEvent$Type.CONNECTION_TERMINAL_GEOMETRY_CHANGED));
   }

   public void setEcho(boolean var1) {
   }

   static int access$000(TelnetIO var0) throws IOException {
      return var0.rawread();
   }

   static void access$100(TelnetIO var0) {
      var0.IamHere();
   }

   static void access$200(TelnetIO var0) {
      var0.nvtBreak();
   }

   static int access$300(TelnetIO var0) throws IOException {
      return var0.read16int();
   }

   static void access$400(TelnetIO var0, int var1, int var2) {
      var0.setTerminalGeometry(var1, var2);
   }

   static Logger access$500() {
      return LOG;
   }

   static ConnectionData access$600(TelnetIO var0) {
      return var0.connectionData;
   }

   static void access$700(TelnetIO var0, int var1) throws IOException {
      var0.rawWrite(var1);
   }
}
