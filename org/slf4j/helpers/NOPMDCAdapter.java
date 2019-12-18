package org.slf4j.helpers;

import java.util.Map;
import org.slf4j.spi.MDCAdapter;

public class NOPMDCAdapter implements MDCAdapter {
   public NOPMDCAdapter() {
      super();
   }

   public void clear() {
   }

   public String get(String var1) {
      return null;
   }

   public void put(String var1, String var2) {
   }

   public void remove(String var1) {
   }

   public Map getCopyOfContextMap() {
      return null;
   }

   public void setContextMap(Map var1) {
   }
}
