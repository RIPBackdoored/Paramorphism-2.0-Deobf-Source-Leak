package org.jline.builtins.telnet;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Connection extends Thread {
   private static final Logger LOG = Logger.getLogger(Connection.class.getName());
   private static int number;
   private boolean dead;
   private List listeners;
   private ConnectionData connectionData;

   public Connection(ThreadGroup var1, ConnectionData var2) {
      super(var1, "Connection" + ++number);
      this.connectionData = var2;
      this.listeners = new CopyOnWriteArrayList();
      this.dead = false;
   }

   public void run() {
      boolean var5 = false;

      label58: {
         label57: {
            try {
               var5 = true;
               this.doRun();
               var5 = false;
               break label57;
            } catch (Exception var6) {
               LOG.log(Level.SEVERE, "run()", var6);
               var5 = false;
            } finally {
               if (var5) {
                  if (!this.dead) {
                     this.close();
                  }

               }
            }

            if (!this.dead) {
               this.close();
            }
            break label58;
         }

         if (!this.dead) {
            this.close();
         }
      }

      LOG.log(Level.FINE, "run():: Returning from " + this.toString());
   }

   protected abstract void doRun() throws Exception;

   protected abstract void doClose() throws Exception;

   public ConnectionData getConnectionData() {
      return this.connectionData;
   }

   public synchronized void close() {
      if (!this.dead) {
         try {
            this.dead = true;
            this.doClose();
         } catch (Exception var5) {
            LOG.log(Level.SEVERE, "close()", var5);
         }

         try {
            this.connectionData.getSocket().close();
         } catch (Exception var4) {
            LOG.log(Level.SEVERE, "close()", var4);
         }

         try {
            this.connectionData.getManager().registerClosedConnection(this);
         } catch (Exception var3) {
            LOG.log(Level.SEVERE, "close()", var3);
         }

         try {
            this.interrupt();
         } catch (Exception var2) {
            LOG.log(Level.SEVERE, "close()", var2);
         }

         LOG.log(Level.FINE, "Closed " + this.toString() + " and inactive.");
      }
   }

   public boolean isActive() {
      return !this.dead;
   }

   public void addConnectionListener(ConnectionListener var1) {
      this.listeners.add(var1);
   }

   public void removeConnectionListener(ConnectionListener var1) {
      this.listeners.remove(var1);
   }

   public void processConnectionEvent(ConnectionEvent var1) {
      // $FF: Couldn't be decompiled
   }
}
