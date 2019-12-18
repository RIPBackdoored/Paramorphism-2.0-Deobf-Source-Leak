package org.eclipse.aether.internal.impl.slf4j;

import org.eclipse.aether.spi.log.Logger;
import org.slf4j.Marker;
import org.slf4j.spi.LocationAwareLogger;

final class Slf4jLoggerFactory$Slf4jLoggerEx implements Logger {
   private static final String FQCN = Slf4jLoggerFactory$Slf4jLoggerEx.class.getName();
   private final LocationAwareLogger logger;

   Slf4jLoggerFactory$Slf4jLoggerEx(LocationAwareLogger var1) {
      super();
      this.logger = var1;
   }

   public boolean isDebugEnabled() {
      return this.logger.isDebugEnabled();
   }

   public void debug(String var1) {
      this.logger.log((Marker)null, FQCN, 10, var1, (Object[])null, (Throwable)null);
   }

   public void debug(String var1, Throwable var2) {
      this.logger.log((Marker)null, FQCN, 10, var1, (Object[])null, var2);
   }

   public boolean isWarnEnabled() {
      return this.logger.isWarnEnabled();
   }

   public void warn(String var1) {
      this.logger.log((Marker)null, FQCN, 30, var1, (Object[])null, (Throwable)null);
   }

   public void warn(String var1, Throwable var2) {
      this.logger.log((Marker)null, FQCN, 30, var1, (Object[])null, var2);
   }
}
