package org.slf4j.event;

import java.util.Queue;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.helpers.SubstituteLogger;

public class EventRecodingLogger implements Logger {
   String name;
   SubstituteLogger logger;
   Queue eventQueue;

   public EventRecodingLogger(SubstituteLogger var1, Queue var2) {
      super();
      this.logger = var1;
      this.name = var1.getName();
      this.eventQueue = var2;
   }

   public String getName() {
      return this.name;
   }

   private void recordEvent(Level var1, String var2, Object[] var3, Throwable var4) {
      this.recordEvent(var1, (Marker)null, var2, var3, var4);
   }

   private void recordEvent(Level var1, Marker var2, String var3, Object[] var4, Throwable var5) {
      SubstituteLoggingEvent var6 = new SubstituteLoggingEvent();
      var6.setTimeStamp(System.currentTimeMillis());
      var6.setLevel(var1);
      var6.setLogger(this.logger);
      var6.setLoggerName(this.name);
      var6.setMarker(var2);
      var6.setMessage(var3);
      var6.setArgumentArray(var4);
      var6.setThrowable(var5);
      var6.setThreadName(Thread.currentThread().getName());
      this.eventQueue.add(var6);
   }

   public boolean isTraceEnabled() {
      return true;
   }

   public void trace(String var1) {
      this.recordEvent(Level.TRACE, var1, (Object[])null, (Throwable)null);
   }

   public void trace(String var1, Object var2) {
      this.recordEvent(Level.TRACE, var1, new Object[]{var2}, (Throwable)null);
   }

   public void trace(String var1, Object var2, Object var3) {
      this.recordEvent(Level.TRACE, var1, new Object[]{var2, var3}, (Throwable)null);
   }

   public void trace(String var1, Object... var2) {
      this.recordEvent(Level.TRACE, var1, var2, (Throwable)null);
   }

   public void trace(String var1, Throwable var2) {
      this.recordEvent(Level.TRACE, var1, (Object[])null, var2);
   }

   public boolean isTraceEnabled(Marker var1) {
      return true;
   }

   public void trace(Marker var1, String var2) {
      this.recordEvent(Level.TRACE, var1, var2, (Object[])null, (Throwable)null);
   }

   public void trace(Marker var1, String var2, Object var3) {
      this.recordEvent(Level.TRACE, var1, var2, new Object[]{var3}, (Throwable)null);
   }

   public void trace(Marker var1, String var2, Object var3, Object var4) {
      this.recordEvent(Level.TRACE, var1, var2, new Object[]{var3, var4}, (Throwable)null);
   }

   public void trace(Marker var1, String var2, Object... var3) {
      this.recordEvent(Level.TRACE, var1, var2, var3, (Throwable)null);
   }

   public void trace(Marker var1, String var2, Throwable var3) {
      this.recordEvent(Level.TRACE, var1, var2, (Object[])null, var3);
   }

   public boolean isDebugEnabled() {
      return true;
   }

   public void debug(String var1) {
      this.recordEvent(Level.TRACE, var1, (Object[])null, (Throwable)null);
   }

   public void debug(String var1, Object var2) {
      this.recordEvent(Level.DEBUG, var1, new Object[]{var2}, (Throwable)null);
   }

   public void debug(String var1, Object var2, Object var3) {
      this.recordEvent(Level.DEBUG, var1, new Object[]{var2, var3}, (Throwable)null);
   }

   public void debug(String var1, Object... var2) {
      this.recordEvent(Level.DEBUG, var1, var2, (Throwable)null);
   }

   public void debug(String var1, Throwable var2) {
      this.recordEvent(Level.DEBUG, var1, (Object[])null, var2);
   }

   public boolean isDebugEnabled(Marker var1) {
      return true;
   }

   public void debug(Marker var1, String var2) {
      this.recordEvent(Level.DEBUG, var1, var2, (Object[])null, (Throwable)null);
   }

   public void debug(Marker var1, String var2, Object var3) {
      this.recordEvent(Level.DEBUG, var1, var2, new Object[]{var3}, (Throwable)null);
   }

   public void debug(Marker var1, String var2, Object var3, Object var4) {
      this.recordEvent(Level.DEBUG, var1, var2, new Object[]{var3, var4}, (Throwable)null);
   }

   public void debug(Marker var1, String var2, Object... var3) {
      this.recordEvent(Level.DEBUG, var1, var2, var3, (Throwable)null);
   }

   public void debug(Marker var1, String var2, Throwable var3) {
      this.recordEvent(Level.DEBUG, var1, var2, (Object[])null, var3);
   }

   public boolean isInfoEnabled() {
      return true;
   }

   public void info(String var1) {
      this.recordEvent(Level.INFO, var1, (Object[])null, (Throwable)null);
   }

   public void info(String var1, Object var2) {
      this.recordEvent(Level.INFO, var1, new Object[]{var2}, (Throwable)null);
   }

   public void info(String var1, Object var2, Object var3) {
      this.recordEvent(Level.INFO, var1, new Object[]{var2, var3}, (Throwable)null);
   }

   public void info(String var1, Object... var2) {
      this.recordEvent(Level.INFO, var1, var2, (Throwable)null);
   }

   public void info(String var1, Throwable var2) {
      this.recordEvent(Level.INFO, var1, (Object[])null, var2);
   }

   public boolean isInfoEnabled(Marker var1) {
      return true;
   }

   public void info(Marker var1, String var2) {
      this.recordEvent(Level.INFO, var1, var2, (Object[])null, (Throwable)null);
   }

   public void info(Marker var1, String var2, Object var3) {
      this.recordEvent(Level.INFO, var1, var2, new Object[]{var3}, (Throwable)null);
   }

   public void info(Marker var1, String var2, Object var3, Object var4) {
      this.recordEvent(Level.INFO, var1, var2, new Object[]{var3, var4}, (Throwable)null);
   }

   public void info(Marker var1, String var2, Object... var3) {
      this.recordEvent(Level.INFO, var1, var2, var3, (Throwable)null);
   }

   public void info(Marker var1, String var2, Throwable var3) {
      this.recordEvent(Level.INFO, var1, var2, (Object[])null, var3);
   }

   public boolean isWarnEnabled() {
      return true;
   }

   public void warn(String var1) {
      this.recordEvent(Level.WARN, var1, (Object[])null, (Throwable)null);
   }

   public void warn(String var1, Object var2) {
      this.recordEvent(Level.WARN, var1, new Object[]{var2}, (Throwable)null);
   }

   public void warn(String var1, Object var2, Object var3) {
      this.recordEvent(Level.WARN, var1, new Object[]{var2, var3}, (Throwable)null);
   }

   public void warn(String var1, Object... var2) {
      this.recordEvent(Level.WARN, var1, var2, (Throwable)null);
   }

   public void warn(String var1, Throwable var2) {
      this.recordEvent(Level.WARN, var1, (Object[])null, var2);
   }

   public boolean isWarnEnabled(Marker var1) {
      return true;
   }

   public void warn(Marker var1, String var2) {
      this.recordEvent(Level.WARN, var2, (Object[])null, (Throwable)null);
   }

   public void warn(Marker var1, String var2, Object var3) {
      this.recordEvent(Level.WARN, var2, new Object[]{var3}, (Throwable)null);
   }

   public void warn(Marker var1, String var2, Object var3, Object var4) {
      this.recordEvent(Level.WARN, var1, var2, new Object[]{var3, var4}, (Throwable)null);
   }

   public void warn(Marker var1, String var2, Object... var3) {
      this.recordEvent(Level.WARN, var1, var2, var3, (Throwable)null);
   }

   public void warn(Marker var1, String var2, Throwable var3) {
      this.recordEvent(Level.WARN, var1, var2, (Object[])null, var3);
   }

   public boolean isErrorEnabled() {
      return true;
   }

   public void error(String var1) {
      this.recordEvent(Level.ERROR, var1, (Object[])null, (Throwable)null);
   }

   public void error(String var1, Object var2) {
      this.recordEvent(Level.ERROR, var1, new Object[]{var2}, (Throwable)null);
   }

   public void error(String var1, Object var2, Object var3) {
      this.recordEvent(Level.ERROR, var1, new Object[]{var2, var3}, (Throwable)null);
   }

   public void error(String var1, Object... var2) {
      this.recordEvent(Level.ERROR, var1, var2, (Throwable)null);
   }

   public void error(String var1, Throwable var2) {
      this.recordEvent(Level.ERROR, var1, (Object[])null, var2);
   }

   public boolean isErrorEnabled(Marker var1) {
      return true;
   }

   public void error(Marker var1, String var2) {
      this.recordEvent(Level.ERROR, var1, var2, (Object[])null, (Throwable)null);
   }

   public void error(Marker var1, String var2, Object var3) {
      this.recordEvent(Level.ERROR, var1, var2, new Object[]{var3}, (Throwable)null);
   }

   public void error(Marker var1, String var2, Object var3, Object var4) {
      this.recordEvent(Level.ERROR, var1, var2, new Object[]{var3, var4}, (Throwable)null);
   }

   public void error(Marker var1, String var2, Object... var3) {
      this.recordEvent(Level.ERROR, var1, var2, var3, (Throwable)null);
   }

   public void error(Marker var1, String var2, Throwable var3) {
      this.recordEvent(Level.ERROR, var1, var2, (Object[])null, var3);
   }
}
