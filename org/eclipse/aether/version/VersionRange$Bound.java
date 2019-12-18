package org.eclipse.aether.version;

import java.util.Objects;

public final class VersionRange$Bound {
   private final Version version;
   private final boolean inclusive;

   public VersionRange$Bound(Version var1, boolean var2) {
      super();
      this.version = (Version)Objects.requireNonNull(var1, "version cannot be null");
      this.inclusive = var2;
   }

   public Version getVersion() {
      return this.version;
   }

   public boolean isInclusive() {
      return this.inclusive;
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 != null && this.getClass().equals(var1.getClass())) {
         VersionRange$Bound var2 = (VersionRange$Bound)var1;
         return this.inclusive == var2.inclusive && this.version.equals(var2.version);
      } else {
         return false;
      }
   }

   public int hashCode() {
      byte var1 = 17;
      int var2 = var1 * 31 + this.version.hashCode();
      var2 = var2 * 31 + (this.inclusive ? 1 : 0);
      return var2;
   }

   public String toString() {
      return String.valueOf(this.version);
   }
}
