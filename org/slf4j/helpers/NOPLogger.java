package org.slf4j.helpers;

public class NOPLogger extends MarkerIgnoringBase {
   private static final long serialVersionUID = -517220405410904473L;
   public static final NOPLogger NOP_LOGGER = new NOPLogger();

   protected NOPLogger() {
      super();
   }

   public String getName() {
      return "NOP";
   }

   public final boolean isTraceEnabled() {
      return false;
   }

   public final void trace(String var1) {
   }

   public final void trace(String var1, Object var2) {
   }

   public final void trace(String var1, Object var2, Object var3) {
   }

   public final void trace(String var1, Object... var2) {
   }

   public final void trace(String var1, Throwable var2) {
   }

   public final boolean isDebugEnabled() {
      return false;
   }

   public final void debug(String var1) {
   }

   public final void debug(String var1, Object var2) {
   }

   public final void debug(String var1, Object var2, Object var3) {
   }

   public final void debug(String var1, Object... var2) {
   }

   public final void debug(String var1, Throwable var2) {
   }

   public final boolean isInfoEnabled() {
      return false;
   }

   public final void info(String var1) {
   }

   public final void info(String var1, Object var2) {
   }

   public final void info(String var1, Object var2, Object var3) {
   }

   public final void info(String var1, Object... var2) {
   }

   public final void info(String var1, Throwable var2) {
   }

   public final boolean isWarnEnabled() {
      return false;
   }

   public final void warn(String var1) {
   }

   public final void warn(String var1, Object var2) {
   }

   public final void warn(String var1, Object var2, Object var3) {
   }

   public final void warn(String var1, Object... var2) {
   }

   public final void warn(String var1, Throwable var2) {
   }

   public final boolean isErrorEnabled() {
      return false;
   }

   public final void error(String var1) {
   }

   public final void error(String var1, Object var2) {
   }

   public final void error(String var1, Object var2, Object var3) {
   }

   public final void error(String var1, Object... var2) {
   }

   public final void error(String var1, Throwable var2) {
   }
}
