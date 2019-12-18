package com.fasterxml.jackson.core;

import java.io.Serializable;

public class Version implements Comparable, Serializable {
   private static final long serialVersionUID = 1L;
   private static final Version UNKNOWN_VERSION = new Version(0, 0, 0, (String)null, (String)null, (String)null);
   protected final int _majorVersion;
   protected final int _minorVersion;
   protected final int _patchLevel;
   protected final String _groupId;
   protected final String _artifactId;
   protected final String _snapshotInfo;

   /** @deprecated */
   @Deprecated
   public Version(int var1, int var2, int var3, String var4) {
      this(var1, var2, var3, var4, (String)null, (String)null);
   }

   public Version(int var1, int var2, int var3, String var4, String var5, String var6) {
      super();
      this._majorVersion = var1;
      this._minorVersion = var2;
      this._patchLevel = var3;
      this._snapshotInfo = var4;
      this._groupId = var5 == null ? "" : var5;
      this._artifactId = var6 == null ? "" : var6;
   }

   public static Version unknownVersion() {
      return UNKNOWN_VERSION;
   }

   public boolean isUnknownVersion() {
      return this == UNKNOWN_VERSION;
   }

   public boolean isSnapshot() {
      return this._snapshotInfo != null && this._snapshotInfo.length() > 0;
   }

   /** @deprecated */
   @Deprecated
   public boolean isUknownVersion() {
      return this.isUnknownVersion();
   }

   public int getMajorVersion() {
      return this._majorVersion;
   }

   public int getMinorVersion() {
      return this._minorVersion;
   }

   public int getPatchLevel() {
      return this._patchLevel;
   }

   public String getGroupId() {
      return this._groupId;
   }

   public String getArtifactId() {
      return this._artifactId;
   }

   public String toFullString() {
      return this._groupId + '/' + this._artifactId + '/' + this.toString();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this._majorVersion).append('.');
      var1.append(this._minorVersion).append('.');
      var1.append(this._patchLevel);
      if (this.isSnapshot()) {
         var1.append('-').append(this._snapshotInfo);
      }

      return var1.toString();
   }

   public int hashCode() {
      return this._artifactId.hashCode() ^ this._groupId.hashCode() + this._majorVersion - this._minorVersion + this._patchLevel;
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 == null) {
         return false;
      } else if (var1.getClass() != this.getClass()) {
         return false;
      } else {
         Version var2 = (Version)var1;
         return var2._majorVersion == this._majorVersion && var2._minorVersion == this._minorVersion && var2._patchLevel == this._patchLevel && var2._artifactId.equals(this._artifactId) && var2._groupId.equals(this._groupId);
      }
   }

   public int compareTo(Version var1) {
      if (var1 == this) {
         return 0;
      } else {
         int var2 = this._groupId.compareTo(var1._groupId);
         if (var2 == 0) {
            var2 = this._artifactId.compareTo(var1._artifactId);
            if (var2 == 0) {
               var2 = this._majorVersion - var1._majorVersion;
               if (var2 == 0) {
                  var2 = this._minorVersion - var1._minorVersion;
                  if (var2 == 0) {
                     var2 = this._patchLevel - var1._patchLevel;
                  }
               }
            }
         }

         return var2;
      }
   }

   public int compareTo(Object var1) {
      return this.compareTo((Version)var1);
   }
}
