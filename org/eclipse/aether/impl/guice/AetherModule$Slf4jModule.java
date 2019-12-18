package org.eclipse.aether.impl.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import javax.inject.Singleton;
import org.eclipse.aether.internal.impl.slf4j.Slf4jLoggerFactory;
import org.eclipse.aether.spi.log.LoggerFactory;
import org.slf4j.ILoggerFactory;

class AetherModule$Slf4jModule extends AbstractModule {
   private AetherModule$Slf4jModule() {
      super();
   }

   protected void configure() {
      this.bind(LoggerFactory.class).to(Slf4jLoggerFactory.class);
   }

   @Provides
   @Singleton
   ILoggerFactory getLoggerFactory() {
      return org.slf4j.LoggerFactory.getILoggerFactory();
   }

   AetherModule$Slf4jModule(AetherModule$1 var1) {
      this();
   }
}
