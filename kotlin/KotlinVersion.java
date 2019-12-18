package kotlin;

import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0007\u0018\u0000 \u00172\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0017B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\u0011\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u0000H\u0096\u0002J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u000e\u001a\u0004\u0018\u00010\u0011H\u0096\u0002J\b\u0010\u0012\u001a\u00020\u0003H\u0016J\u0016\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003J\u001e\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0003J\b\u0010\u0014\u001a\u00020\u0015H\u0016J \u0010\u0016\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0003H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u000e\u0010\f\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"},
   d2 = {"Lkotlin/KotlinVersion;", "", "major", "", "minor", "(II)V", "patch", "(III)V", "getMajor", "()I", "getMinor", "getPatch", "version", "compareTo", "other", "equals", "", "", "hashCode", "isAtLeast", "toString", "", "versionOf", "Companion", "kotlin-stdlib"}
)
@SinceKotlin(
   version = "1.1"
)
public final class KotlinVersion implements Comparable {
   private final int version;
   private final int major;
   private final int minor;
   private final int patch;
   public static final int MAX_COMPONENT_VALUE = 255;
   @JvmField
   @NotNull
   public static final KotlinVersion CURRENT = new KotlinVersion(1, 3, 41);
   public static final KotlinVersion$Companion Companion = new KotlinVersion$Companion((DefaultConstructorMarker)null);

   private final int versionOf(int var1, int var2, int var3) {
      boolean var10000;
      label27: {
         if (0 <= var1) {
            if (255 >= var1) {
               if (0 <= var2) {
                  if (255 >= var2) {
                     if (0 <= var3) {
                        if (255 >= var3) {
                           var10000 = true;
                           break label27;
                        }
                     }
                  }
               }
            }
         }

         var10000 = false;
      }

      boolean var4 = var10000;
      boolean var5 = false;
      boolean var6 = false;
      if (!var4) {
         boolean var7 = false;
         String var8 = "Version components are out of range: " + var1 + '.' + var2 + '.' + var3;
         throw (Throwable)(new IllegalArgumentException(var8.toString()));
      } else {
         return (var1 << 16) + (var2 << 8) + var3;
      }
   }

   @NotNull
   public String toString() {
      return "" + this.major + '.' + this.minor + '.' + this.patch;
   }

   public boolean equals(@Nullable Object var1) {
      if ((KotlinVersion)this == var1) {
         return true;
      } else {
         Object var10000 = var1;
         if (!(var1 instanceof KotlinVersion)) {
            var10000 = null;
         }

         KotlinVersion var3 = (KotlinVersion)var10000;
         if (var3 != null) {
            KotlinVersion var2 = var3;
            return this.version == var2.version;
         } else {
            return false;
         }
      }
   }

   public int hashCode() {
      return this.version;
   }

   public int compareTo(@NotNull KotlinVersion var1) {
      return this.version - var1.version;
   }

   public int compareTo(Object var1) {
      return this.compareTo((KotlinVersion)var1);
   }

   public final boolean isAtLeast(int var1, int var2) {
      return this.major > var1 || this.major == var1 && this.minor >= var2;
   }

   public final boolean isAtLeast(int var1, int var2, int var3) {
      return this.major > var1 || this.major == var1 && (this.minor > var2 || this.minor == var2 && this.patch >= var3);
   }

   public final int getMajor() {
      return this.major;
   }

   public final int getMinor() {
      return this.minor;
   }

   public final int getPatch() {
      return this.patch;
   }

   public KotlinVersion(int var1, int var2, int var3) {
      super();
      this.major = var1;
      this.minor = var2;
      this.patch = var3;
      this.version = this.versionOf(this.major, this.minor, this.patch);
   }

   public KotlinVersion(int var1, int var2) {
      this(var1, var2, 0);
   }
}
