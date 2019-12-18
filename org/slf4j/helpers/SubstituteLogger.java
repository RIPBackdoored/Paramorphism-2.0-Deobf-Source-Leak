package org.slf4j.helpers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Queue;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.EventRecodingLogger;
import org.slf4j.event.LoggingEvent;

public class SubstituteLogger implements Logger {
   private final String name;
   private Logger _delegate;
   private Boolean delegateEventAware;
   private Method logMethodCache;
   private EventRecodingLogger eventRecodingLogger;
   private Queue eventQueue;
   private final boolean createdPostInitialization;

   public SubstituteLogger(String var1, Queue var2, boolean var3) {
      super();
      this.name = var1;
      this.eventQueue = var2;
      this.createdPostInitialization = var3;
   }

   public String getName() {
      return this.name;
   }

   public boolean isTraceEnabled() {
      return this.delegate().isTraceEnabled();
   }

   public void trace(String var1) {
      this.delegate().trace(var1);
   }

   public void trace(String var1, Object var2) {
      this.delegate().trace(var1, var2);
   }

   public void trace(String var1, Object var2, Object var3) {
      this.delegate().trace(var1, var2, var3);
   }

   public void trace(String var1, Object... var2) {
      this.delegate().trace(var1, var2);
   }

   public void trace(String var1, Throwable var2) {
      this.delegate().trace(var1, var2);
   }

   public boolean isTraceEnabled(Marker var1) {
      return this.delegate().isTraceEnabled(var1);
   }

   public void trace(Marker var1, String var2) {
      this.delegate().trace(var1, var2);
   }

   public void trace(Marker var1, String var2, Object var3) {
      this.delegate().trace(var1, var2, var3);
   }

   public void trace(Marker var1, String var2, Object var3, Object var4) {
      this.delegate().trace(var1, var2, var3, var4);
   }

   public void trace(Marker var1, String var2, Object... var3) {
      this.delegate().trace(var1, var2, var3);
   }

   public void trace(Marker var1, String var2, Throwable var3) {
      this.delegate().trace(var1, var2, var3);
   }

   public boolean isDebugEnabled() {
      return this.delegate().isDebugEnabled();
   }

   public void debug(String var1) {
      this.delegate().debug(var1);
   }

   public void debug(String var1, Object var2) {
      this.delegate().debug(var1, var2);
   }

   public void debug(String var1, Object var2, Object var3) {
      this.delegate().debug(var1, var2, var3);
   }

   public void debug(String var1, Object... var2) {
      this.delegate().debug(var1, var2);
   }

   public void debug(String var1, Throwable var2) {
      this.delegate().debug(var1, var2);
   }

   public boolean isDebugEnabled(Marker var1) {
      return this.delegate().isDebugEnabled(var1);
   }

   public void debug(Marker var1, String var2) {
      this.delegate().debug(var1, var2);
   }

   public void debug(Marker var1, String var2, Object var3) {
      this.delegate().debug(var1, var2, var3);
   }

   public void debug(Marker var1, String var2, Object var3, Object var4) {
      this.delegate().debug(var1, var2, var3, var4);
   }

   public void debug(Marker var1, String var2, Object... var3) {
      this.delegate().debug(var1, var2, var3);
   }

   public void debug(Marker var1, String var2, Throwable var3) {
      this.delegate().debug(var1, var2, var3);
   }

   public boolean isInfoEnabled() {
      return this.delegate().isInfoEnabled();
   }

   public void info(String var1) {
      this.delegate().info(var1);
   }

   public void info(String var1, Object var2) {
      this.delegate().info(var1, var2);
   }

   public void info(String var1, Object var2, Object var3) {
      this.delegate().info(var1, var2, var3);
   }

   public void info(String var1, Object... var2) {
      this.delegate().info(var1, var2);
   }

   public void info(String var1, Throwable var2) {
      this.delegate().info(var1, var2);
   }

   public boolean isInfoEnabled(Marker var1) {
      return this.delegate().isInfoEnabled(var1);
   }

   public void info(Marker var1, String var2) {
      this.delegate().info(var1, var2);
   }

   public void info(Marker var1, String var2, Object var3) {
      this.delegate().info(var1, var2, var3);
   }

   public void info(Marker var1, String var2, Object var3, Object var4) {
      this.delegate().info(var1, var2, var3, var4);
   }

   public void info(Marker var1, String var2, Object... var3) {
      this.delegate().info(var1, var2, var3);
   }

   public void info(Marker var1, String var2, Throwable var3) {
      this.delegate().info(var1, var2, var3);
   }

   public boolean isWarnEnabled() {
      return this.delegate().isWarnEnabled();
   }

   public void warn(String var1) {
      this.delegate().warn(var1);
   }

   public void warn(String var1, Object var2) {
      this.delegate().warn(var1, var2);
   }

   public void warn(String var1, Object var2, Object var3) {
      this.delegate().warn(var1, var2, var3);
   }

   public void warn(String var1, Object... var2) {
      this.delegate().warn(var1, var2);
   }

   public void warn(String var1, Throwable var2) {
      this.delegate().warn(var1, var2);
   }

   public boolean isWarnEnabled(Marker var1) {
      return this.delegate().isWarnEnabled(var1);
   }

   public void warn(Marker var1, String var2) {
      this.delegate().warn(var1, var2);
   }

   public void warn(Marker var1, String var2, Object var3) {
      this.delegate().warn(var1, var2, var3);
   }

   public void warn(Marker var1, String var2, Object var3, Object var4) {
      this.delegate().warn(var1, var2, var3, var4);
   }

   public void warn(Marker var1, String var2, Object... var3) {
      this.delegate().warn(var1, var2, var3);
   }

   public void warn(Marker var1, String var2, Throwable var3) {
      this.delegate().warn(var1, var2, var3);
   }

   public boolean isErrorEnabled() {
      return this.delegate().isErrorEnabled();
   }

   public void error(String var1) {
      this.delegate().error(var1);
   }

   public void error(String var1, Object var2) {
      this.delegate().error(var1, var2);
   }

   public void error(String var1, Object var2, Object var3) {
      this.delegate().error(var1, var2, var3);
   }

   public void error(String var1, Object... var2) {
      this.delegate().error(var1, var2);
   }

   public void error(String var1, Throwable var2) {
      this.delegate().error(var1, var2);
   }

   public boolean isErrorEnabled(Marker var1) {
      return this.delegate().isErrorEnabled(var1);
   }

   public void error(Marker var1, String var2) {
      this.delegate().error(var1, var2);
   }

   public void error(Marker var1, String var2, Object var3) {
      this.delegate().error(var1, var2, var3);
   }

   public void error(Marker var1, String var2, Object var3, Object var4) {
      this.delegate().error(var1, var2, var3, var4);
   }

   public void error(Marker var1, String var2, Object... var3) {
      this.delegate().error(var1, var2, var3);
   }

   public void error(Marker var1, String var2, Throwable var3) {
      this.delegate().error(var1, var2, var3);
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         SubstituteLogger var2 = (SubstituteLogger)var1;
         return this.name.equals(var2.name);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.name.hashCode();
   }

   Logger delegate() {
      if (this._delegate != null) {
         return this._delegate;
      } else {
         return (Logger)(this.createdPostInitialization ? NOPLogger.NOP_LOGGER : this.getEventRecordingLogger());
      }
   }

   private Logger getEventRecordingLogger() {
      if (this.eventRecodingLogger == null) {
         this.eventRecodingLogger = new EventRecodingLogger(this, this.eventQueue);
      }

      return this.eventRecodingLogger;
   }

   public void setDelegate(Logger var1) {
      this._delegate = var1;
   }

   public boolean isDelegateEventAware() {
      if (this.delegateEventAware != null) {
         return this.delegateEventAware;
      } else {
         try {
            this.logMethodCache = this._delegate.getClass().getMethod("log", LoggingEvent.class);
            this.delegateEventAware = Boolean.TRUE;
         } catch (NoSuchMethodException var2) {
            this.delegateEventAware = Boolean.FALSE;
         }

         return this.delegateEventAware;
      }
   }

   public void log(LoggingEvent var1) {
      if (this.isDelegateEventAware()) {
         try {
            this.logMethodCache.invoke(this._delegate, var1);
         } catch (IllegalAccessException var3) {
         } catch (IllegalArgumentException var4) {
         } catch (InvocationTargetException var5) {
         }
      }

   }

   public boolean isDelegateNull() {
      return this._delegate == null;
   }

   public boolean isDelegateNOP() {
      return this._delegate instanceof NOPLogger;
   }
}
