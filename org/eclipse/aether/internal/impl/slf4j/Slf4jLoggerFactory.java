package org.eclipse.aether.internal.impl.slf4j;

import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.aether.spi.locator.Service;
import org.eclipse.aether.spi.locator.ServiceLocator;
import org.eclipse.aether.spi.log.Logger;
import org.eclipse.aether.spi.log.LoggerFactory;
import org.eclipse.sisu.Nullable;
import org.slf4j.ILoggerFactory;
import org.slf4j.spi.LocationAwareLogger;

@Named("slf4j")
public class Slf4jLoggerFactory implements LoggerFactory, Service {
   private static final boolean AVAILABLE;
   private ILoggerFactory factory;

   public static boolean isSlf4jAvailable() {
      return AVAILABLE;
   }

   public Slf4jLoggerFactory() {
      super();
   }

   @Inject
   Slf4jLoggerFactory(@Nullable ILoggerFactory var1) {
      super();
      this.setLoggerFactory(var1);
   }

   public void initService(ServiceLocator var1) {
      this.setLoggerFactory((ILoggerFactory)var1.getService(ILoggerFactory.class));
   }

   public Slf4jLoggerFactory setLoggerFactory(ILoggerFactory var1) {
      this.factory = var1;
      return this;
   }

   public Logger getLogger(String var1) {
      org.slf4j.Logger var2 = this.getFactory().getLogger(var1);
      return (Logger)(var2 instanceof LocationAwareLogger ? new Slf4jLoggerFactory$Slf4jLoggerEx((LocationAwareLogger)var2) : new Slf4jLoggerFactory$Slf4jLogger(var2));
   }

   private ILoggerFactory getFactory() {
      if (this.factory == null) {
         this.factory = org.slf4j.LoggerFactory.getILoggerFactory();
      }

      return this.factory;
   }

   static {
      boolean var0;
      try {
         Slf4jLoggerFactory.class.getClassLoader().loadClass("org.slf4j.ILoggerFactory");
         var0 = true;
      } catch (LinkageError | Exception var2) {
         var0 = false;
      }

      AVAILABLE = var0;
   }
}
