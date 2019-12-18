package org.slf4j;

import org.slf4j.helpers.BasicMarkerFactory;
import org.slf4j.helpers.Util;
import org.slf4j.impl.StaticMarkerBinder;

public class MarkerFactory {
   static IMarkerFactory MARKER_FACTORY;

   private MarkerFactory() {
      super();
   }

   private static IMarkerFactory bwCompatibleGetMarkerFactoryFromBinder() throws NoClassDefFoundError {
      IMarkerFactory var10000;
      try {
         var10000 = StaticMarkerBinder.getSingleton().getMarkerFactory();
      } catch (NoSuchMethodError var1) {
         return StaticMarkerBinder.SINGLETON.getMarkerFactory();
      }

      return var10000;
   }

   public static Marker getMarker(String var0) {
      return MARKER_FACTORY.getMarker(var0);
   }

   public static Marker getDetachedMarker(String var0) {
      return MARKER_FACTORY.getDetachedMarker(var0);
   }

   public static IMarkerFactory getIMarkerFactory() {
      return MARKER_FACTORY;
   }

   static {
      try {
         MARKER_FACTORY = bwCompatibleGetMarkerFactoryFromBinder();
      } catch (NoClassDefFoundError var1) {
         MARKER_FACTORY = new BasicMarkerFactory();
      } catch (Exception var2) {
         Util.report("Unexpected failure while binding MarkerFactory", var2);
      }

   }
}
