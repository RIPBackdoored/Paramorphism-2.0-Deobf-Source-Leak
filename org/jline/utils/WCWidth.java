package org.jline.utils;

public final class WCWidth {
   static WCWidth$Interval[] combining = new WCWidth$Interval[]{new WCWidth$Interval(768, 879), new WCWidth$Interval(1155, 1158), new WCWidth$Interval(1160, 1161), new WCWidth$Interval(1425, 1469), new WCWidth$Interval(1471, 1471), new WCWidth$Interval(1473, 1474), new WCWidth$Interval(1476, 1477), new WCWidth$Interval(1479, 1479), new WCWidth$Interval(1536, 1539), new WCWidth$Interval(1552, 1557), new WCWidth$Interval(1611, 1630), new WCWidth$Interval(1648, 1648), new WCWidth$Interval(1750, 1764), new WCWidth$Interval(1767, 1768), new WCWidth$Interval(1770, 1773), new WCWidth$Interval(1807, 1807), new WCWidth$Interval(1809, 1809), new WCWidth$Interval(1840, 1866), new WCWidth$Interval(1958, 1968), new WCWidth$Interval(2027, 2035), new WCWidth$Interval(2305, 2306), new WCWidth$Interval(2364, 2364), new WCWidth$Interval(2369, 2376), new WCWidth$Interval(2381, 2381), new WCWidth$Interval(2385, 2388), new WCWidth$Interval(2402, 2403), new WCWidth$Interval(2433, 2433), new WCWidth$Interval(2492, 2492), new WCWidth$Interval(2497, 2500), new WCWidth$Interval(2509, 2509), new WCWidth$Interval(2530, 2531), new WCWidth$Interval(2561, 2562), new WCWidth$Interval(2620, 2620), new WCWidth$Interval(2625, 2626), new WCWidth$Interval(2631, 2632), new WCWidth$Interval(2635, 2637), new WCWidth$Interval(2672, 2673), new WCWidth$Interval(2689, 2690), new WCWidth$Interval(2748, 2748), new WCWidth$Interval(2753, 2757), new WCWidth$Interval(2759, 2760), new WCWidth$Interval(2765, 2765), new WCWidth$Interval(2786, 2787), new WCWidth$Interval(2817, 2817), new WCWidth$Interval(2876, 2876), new WCWidth$Interval(2879, 2879), new WCWidth$Interval(2881, 2883), new WCWidth$Interval(2893, 2893), new WCWidth$Interval(2902, 2902), new WCWidth$Interval(2946, 2946), new WCWidth$Interval(3008, 3008), new WCWidth$Interval(3021, 3021), new WCWidth$Interval(3134, 3136), new WCWidth$Interval(3142, 3144), new WCWidth$Interval(3146, 3149), new WCWidth$Interval(3157, 3158), new WCWidth$Interval(3260, 3260), new WCWidth$Interval(3263, 3263), new WCWidth$Interval(3270, 3270), new WCWidth$Interval(3276, 3277), new WCWidth$Interval(3298, 3299), new WCWidth$Interval(3393, 3395), new WCWidth$Interval(3405, 3405), new WCWidth$Interval(3530, 3530), new WCWidth$Interval(3538, 3540), new WCWidth$Interval(3542, 3542), new WCWidth$Interval(3633, 3633), new WCWidth$Interval(3636, 3642), new WCWidth$Interval(3655, 3662), new WCWidth$Interval(3761, 3761), new WCWidth$Interval(3764, 3769), new WCWidth$Interval(3771, 3772), new WCWidth$Interval(3784, 3789), new WCWidth$Interval(3864, 3865), new WCWidth$Interval(3893, 3893), new WCWidth$Interval(3895, 3895), new WCWidth$Interval(3897, 3897), new WCWidth$Interval(3953, 3966), new WCWidth$Interval(3968, 3972), new WCWidth$Interval(3974, 3975), new WCWidth$Interval(3984, 3991), new WCWidth$Interval(3993, 4028), new WCWidth$Interval(4038, 4038), new WCWidth$Interval(4141, 4144), new WCWidth$Interval(4146, 4146), new WCWidth$Interval(4150, 4151), new WCWidth$Interval(4153, 4153), new WCWidth$Interval(4184, 4185), new WCWidth$Interval(4448, 4607), new WCWidth$Interval(4959, 4959), new WCWidth$Interval(5906, 5908), new WCWidth$Interval(5938, 5940), new WCWidth$Interval(5970, 5971), new WCWidth$Interval(6002, 6003), new WCWidth$Interval(6068, 6069), new WCWidth$Interval(6071, 6077), new WCWidth$Interval(6086, 6086), new WCWidth$Interval(6089, 6099), new WCWidth$Interval(6109, 6109), new WCWidth$Interval(6155, 6157), new WCWidth$Interval(6313, 6313), new WCWidth$Interval(6432, 6434), new WCWidth$Interval(6439, 6440), new WCWidth$Interval(6450, 6450), new WCWidth$Interval(6457, 6459), new WCWidth$Interval(6679, 6680), new WCWidth$Interval(6912, 6915), new WCWidth$Interval(6964, 6964), new WCWidth$Interval(6966, 6970), new WCWidth$Interval(6972, 6972), new WCWidth$Interval(6978, 6978), new WCWidth$Interval(7019, 7027), new WCWidth$Interval(7616, 7626), new WCWidth$Interval(7678, 7679), new WCWidth$Interval(8203, 8207), new WCWidth$Interval(8234, 8238), new WCWidth$Interval(8288, 8291), new WCWidth$Interval(8298, 8303), new WCWidth$Interval(8400, 8431), new WCWidth$Interval(12330, 12335), new WCWidth$Interval(12441, 12442), new WCWidth$Interval(43014, 43014), new WCWidth$Interval(43019, 43019), new WCWidth$Interval(43045, 43046), new WCWidth$Interval(64286, 64286), new WCWidth$Interval(65024, 65039), new WCWidth$Interval(65056, 65059), new WCWidth$Interval(65279, 65279), new WCWidth$Interval(65529, 65531), new WCWidth$Interval(68097, 68099), new WCWidth$Interval(68101, 68102), new WCWidth$Interval(68108, 68111), new WCWidth$Interval(68152, 68154), new WCWidth$Interval(68159, 68159), new WCWidth$Interval(119143, 119145), new WCWidth$Interval(119155, 119170), new WCWidth$Interval(119173, 119179), new WCWidth$Interval(119210, 119213), new WCWidth$Interval(119362, 119364), new WCWidth$Interval(917505, 917505), new WCWidth$Interval(917536, 917631), new WCWidth$Interval(917760, 917999)};

   private WCWidth() {
      super();
   }

   public static int wcwidth(int var0) {
      if (var0 == 0) {
         return 0;
      } else if (var0 >= 32 && (var0 < 127 || var0 >= 160)) {
         return bisearch(var0, combining, combining.length - 1) ? 0 : 1 + (var0 < 4352 || var0 > 4447 && var0 != 9001 && var0 != 9002 && (var0 < 11904 || var0 > 42191 || var0 == 12351) && (var0 < 44032 || var0 > 55203) && (var0 < 63744 || var0 > 64255) && (var0 < 65040 || var0 > 65049) && (var0 < 65072 || var0 > 65135) && (var0 < 65280 || var0 > 65376) && (var0 < 65504 || var0 > 65510) && (var0 < 131072 || var0 > 196605) && (var0 < 196608 || var0 > 262141) ? 0 : 1);
      } else {
         return -1;
      }
   }

   private static boolean bisearch(int var0, WCWidth$Interval[] var1, int var2) {
      int var3 = 0;
      if (var0 >= var1[0].first && var0 <= var1[var2].last) {
         while(var2 >= var3) {
            int var4 = (var3 + var2) / 2;
            if (var0 > var1[var4].last) {
               var3 = var4 + 1;
            } else {
               if (var0 >= var1[var4].first) {
                  return true;
               }

               var2 = var4 - 1;
            }
         }

         return false;
      } else {
         return false;
      }
   }
}
