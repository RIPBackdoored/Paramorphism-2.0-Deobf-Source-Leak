package org.eclipse.aether.spi.log;

public interface Logger {
   boolean isDebugEnabled();

   void debug(String var1);

   void debug(String var1, Throwable var2);

   boolean isWarnEnabled();

   void warn(String var1);

   void warn(String var1, Throwable var2);
}
