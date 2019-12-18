package org.slf4j.helpers;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.slf4j.IMarkerFactory;
import org.slf4j.Marker;

public class BasicMarkerFactory implements IMarkerFactory {
   private final ConcurrentMap markerMap = new ConcurrentHashMap();

   public BasicMarkerFactory() {
      super();
   }

   public Marker getMarker(String var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("Marker name cannot be null");
      } else {
         Object var2 = (Marker)this.markerMap.get(var1);
         if (var2 == null) {
            var2 = new BasicMarker(var1);
            Marker var3 = (Marker)this.markerMap.putIfAbsent(var1, var2);
            if (var3 != null) {
               var2 = var3;
            }
         }

         return (Marker)var2;
      }
   }

   public boolean exists(String var1) {
      return var1 == null ? false : this.markerMap.containsKey(var1);
   }

   public boolean detachMarker(String var1) {
      if (var1 == null) {
         return false;
      } else {
         return this.markerMap.remove(var1) != null;
      }
   }

   public Marker getDetachedMarker(String var1) {
      return new BasicMarker(var1);
   }
}
