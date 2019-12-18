package org.slf4j.helpers;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

public class NOPLoggerFactory implements ILoggerFactory {
   public NOPLoggerFactory() {
      super();
   }

   public Logger getLogger(String var1) {
      return NOPLogger.NOP_LOGGER;
   }
}
