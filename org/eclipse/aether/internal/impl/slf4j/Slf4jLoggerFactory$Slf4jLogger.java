package org.eclipse.aether.internal.impl.slf4j;

import org.eclipse.aether.spi.log.Logger;

final class Slf4jLoggerFactory$Slf4jLogger implements Logger {
   private final org.slf4j.Logger logger;

   Slf4jLoggerFactory$Slf4jLogger(org.slf4j.Logger var1) {
      super();
      this.logger = var1;
   }

   public boolean isDebugEnabled() {
      return this.logger.isDebugEnabled();
   }

   public void debug(String var1) {
      this.logger.debug(var1);
   }

   public void debug(String var1, Throwable var2) {
      this.logger.debug(var1, var2);
   }

   public boolean isWarnEnabled() {
      return this.logger.isWarnEnabled();
   }

   public void warn(String var1) {
      this.logger.warn(var1);
   }

   public void warn(String var1, Throwable var2) {
      this.logger.warn(var1, var2);
   }
}
