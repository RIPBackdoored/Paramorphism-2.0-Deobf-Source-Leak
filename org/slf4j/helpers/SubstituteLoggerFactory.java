package org.slf4j.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

public class SubstituteLoggerFactory implements ILoggerFactory {
   boolean postInitialization = false;
   final Map loggers = new HashMap();
   final LinkedBlockingQueue eventQueue = new LinkedBlockingQueue();

   public SubstituteLoggerFactory() {
      super();
   }

   public synchronized Logger getLogger(String var1) {
      SubstituteLogger var2 = (SubstituteLogger)this.loggers.get(var1);
      if (var2 == null) {
         var2 = new SubstituteLogger(var1, this.eventQueue, this.postInitialization);
         this.loggers.put(var1, var2);
      }

      return var2;
   }

   public List getLoggerNames() {
      return new ArrayList(this.loggers.keySet());
   }

   public List getLoggers() {
      return new ArrayList(this.loggers.values());
   }

   public LinkedBlockingQueue getEventQueue() {
      return this.eventQueue;
   }

   public void postInitialization() {
      this.postInitialization = true;
   }

   public void clear() {
      this.loggers.clear();
      this.eventQueue.clear();
   }
}
