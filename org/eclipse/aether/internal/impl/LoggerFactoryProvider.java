package org.eclipse.aether.internal.impl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;
import org.eclipse.aether.spi.log.LoggerFactory;

@Named
@Singleton
public class LoggerFactoryProvider implements Provider {
   @Inject
   @Named("slf4j")
   private Provider slf4j;

   public LoggerFactoryProvider() {
      super();
   }

   public LoggerFactory get() {
      return (LoggerFactory)this.slf4j.get();
   }

   public Object get() {
      return this.get();
   }
}
