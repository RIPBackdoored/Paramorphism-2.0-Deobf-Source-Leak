package kotlin.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.SinceKotlin;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.Grouping;
import kotlin.collections.IndexingIterable;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.collections.SlidingWindowKt;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.random.Random;
import kotlin.ranges.IntProgression;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000Ü\u0001\n\u0000\n\u0002\u0010\u000b\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\f\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u001f\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0010\u000f\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a!\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\n\u0010\u0006\u001a\u00020\u0001*\u00020\u0002\u001a!\u0010\u0006\u001a\u00020\u0001*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\u0010\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\b*\u00020\u0002\u001a\u0010\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\n*\u00020\u0002\u001aE\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u000e0\f\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e*\u00020\u00022\u001e\u0010\u000f\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u000e0\u00100\u0004H\u0086\b\u001a3\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u00020\u00050\f\"\u0004\b\u0000\u0010\r*\u00020\u00022\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\u0086\b\u001aM\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u000e0\f\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e*\u00020\u00022\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0086\b\u001aN\u0010\u0014\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\r\"\u0018\b\u0001\u0010\u0015*\u0012\u0012\u0006\b\u0000\u0012\u0002H\r\u0012\u0006\b\u0000\u0012\u00020\u00050\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\u0086\b¢\u0006\u0002\u0010\u0018\u001ah\u0010\u0014\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e\"\u0018\b\u0002\u0010\u0015*\u0012\u0012\u0006\b\u0000\u0012\u0002H\r\u0012\u0006\b\u0000\u0012\u0002H\u000e0\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0086\b¢\u0006\u0002\u0010\u0019\u001a`\u0010\u001a\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e\"\u0018\b\u0002\u0010\u0015*\u0012\u0012\u0006\b\u0000\u0012\u0002H\r\u0012\u0006\b\u0000\u0012\u0002H\u000e0\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u001e\u0010\u000f\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u000e0\u00100\u0004H\u0086\b¢\u0006\u0002\u0010\u0018\u001a3\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\f\"\u0004\b\u0000\u0010\u000e*\u00020\u00022\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0087\b\u001aN\u0010\u001d\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\u000e\"\u0018\b\u0001\u0010\u0015*\u0012\u0012\u0006\b\u0000\u0012\u00020\u0005\u0012\u0006\b\u0000\u0012\u0002H\u000e0\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0087\b¢\u0006\u0002\u0010\u0018\u001a\u001a\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001f*\u00020\u00022\u0006\u0010!\u001a\u00020\"H\u0007\u001a4\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010!\u001a\u00020\"2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H#0\u0004H\u0007\u001a\u001a\u0010$\u001a\b\u0012\u0004\u0012\u00020 0\n*\u00020\u00022\u0006\u0010!\u001a\u00020\"H\u0007\u001a4\u0010$\u001a\b\u0012\u0004\u0012\u0002H#0\n\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010!\u001a\u00020\"2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H#0\u0004H\u0007\u001a\r\u0010%\u001a\u00020\"*\u00020\u0002H\u0087\b\u001a!\u0010%\u001a\u00020\"*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\u0012\u0010&\u001a\u00020\u0002*\u00020\u00022\u0006\u0010'\u001a\u00020\"\u001a\u0012\u0010&\u001a\u00020 *\u00020 2\u0006\u0010'\u001a\u00020\"\u001a\u0012\u0010(\u001a\u00020\u0002*\u00020\u00022\u0006\u0010'\u001a\u00020\"\u001a\u0012\u0010(\u001a\u00020 *\u00020 2\u0006\u0010'\u001a\u00020\"\u001a!\u0010)\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u0010)\u001a\u00020 *\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u0010*\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u0010*\u001a\u00020 *\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a)\u0010+\u001a\u00020\u0005*\u00020\u00022\u0006\u0010,\u001a\u00020\"2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020\u00050\u0004H\u0087\b\u001a\u001c\u0010.\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0006\u0010,\u001a\u00020\"H\u0087\b¢\u0006\u0002\u0010/\u001a!\u00100\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u00100\u001a\u00020 *\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a6\u00101\u001a\u00020\u0002*\u00020\u00022'\u0010\u0003\u001a#\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000102H\u0086\b\u001a6\u00101\u001a\u00020 *\u00020 2'\u0010\u0003\u001a#\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000102H\u0086\b\u001aQ\u00105\u001a\u0002H6\"\f\b\u0000\u00106*\u000607j\u0002`8*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62'\u0010\u0003\u001a#\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000102H\u0086\b¢\u0006\u0002\u00109\u001a!\u0010:\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u0010:\u001a\u00020 *\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a<\u0010;\u001a\u0002H6\"\f\b\u0000\u00106*\u000607j\u0002`8*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b¢\u0006\u0002\u0010<\u001a<\u0010=\u001a\u0002H6\"\f\b\u0000\u00106*\u000607j\u0002`8*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b¢\u0006\u0002\u0010<\u001a(\u0010>\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0087\b¢\u0006\u0002\u0010?\u001a(\u0010@\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0087\b¢\u0006\u0002\u0010?\u001a\n\u0010A\u001a\u00020\u0005*\u00020\u0002\u001a!\u0010A\u001a\u00020\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\u0011\u0010B\u001a\u0004\u0018\u00010\u0005*\u00020\u0002¢\u0006\u0002\u0010C\u001a(\u0010B\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b¢\u0006\u0002\u0010?\u001a3\u0010D\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\u0000\u0010#*\u00020\u00022\u0018\u0010\u000f\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u0002H#0\b0\u0004H\u0086\b\u001aL\u0010E\u001a\u0002H6\"\u0004\b\u0000\u0010#\"\u0010\b\u0001\u00106*\n\u0012\u0006\b\u0000\u0012\u0002H#0F*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62\u0018\u0010\u000f\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u0002H#0\b0\u0004H\u0086\b¢\u0006\u0002\u0010G\u001aI\u0010H\u001a\u0002H#\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010I\u001a\u0002H#2'\u0010J\u001a#\u0012\u0013\u0012\u0011H#¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#02H\u0086\b¢\u0006\u0002\u0010L\u001a^\u0010M\u001a\u0002H#\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010I\u001a\u0002H#2<\u0010J\u001a8\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0013\u0012\u0011H#¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#0NH\u0086\b¢\u0006\u0002\u0010O\u001aI\u0010P\u001a\u0002H#\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010I\u001a\u0002H#2'\u0010J\u001a#\u0012\u0004\u0012\u00020\u0005\u0012\u0013\u0012\u0011H#¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u0002H#02H\u0086\b¢\u0006\u0002\u0010L\u001a^\u0010Q\u001a\u0002H#\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010I\u001a\u0002H#2<\u0010J\u001a8\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0013\u0012\u0011H#¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u0002H#0NH\u0086\b¢\u0006\u0002\u0010O\u001a!\u0010R\u001a\u00020S*\u00020\u00022\u0012\u0010T\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020S0\u0004H\u0086\b\u001a6\u0010U\u001a\u00020S*\u00020\u00022'\u0010T\u001a#\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020S02H\u0086\b\u001a)\u0010V\u001a\u00020\u0005*\u00020\u00022\u0006\u0010,\u001a\u00020\"2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020\u00050\u0004H\u0087\b\u001a\u0019\u0010W\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0006\u0010,\u001a\u00020\"¢\u0006\u0002\u0010/\u001a9\u0010X\u001a\u0014\u0012\u0004\u0012\u0002H\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u001f0\f\"\u0004\b\u0000\u0010\r*\u00020\u00022\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\u0086\b\u001aS\u0010X\u001a\u0014\u0012\u0004\u0012\u0002H\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0\u001f0\f\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e*\u00020\u00022\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0086\b\u001aR\u0010Y\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\r\"\u001c\b\u0001\u0010\u0015*\u0016\u0012\u0006\b\u0000\u0012\u0002H\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050Z0\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\u0086\b¢\u0006\u0002\u0010\u0018\u001al\u0010Y\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e\"\u001c\b\u0002\u0010\u0015*\u0016\u0012\u0006\b\u0000\u0012\u0002H\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0Z0\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0086\b¢\u0006\u0002\u0010\u0019\u001a5\u0010[\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\\\"\u0004\b\u0000\u0010\r*\u00020\u00022\u0014\b\u0004\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\u0087\b\u001a!\u0010]\u001a\u00020\"*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u0010^\u001a\u00020\"*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\n\u0010_\u001a\u00020\u0005*\u00020\u0002\u001a!\u0010_\u001a\u00020\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\u0011\u0010`\u001a\u0004\u0018\u00010\u0005*\u00020\u0002¢\u0006\u0002\u0010C\u001a(\u0010`\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b¢\u0006\u0002\u0010?\u001a-\u0010a\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\u0000\u0010#*\u00020\u00022\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#0\u0004H\u0086\b\u001aB\u0010b\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\u0000\u0010#*\u00020\u00022'\u0010\u000f\u001a#\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#02H\u0086\b\u001aH\u0010c\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\b\b\u0000\u0010#*\u00020d*\u00020\u00022)\u0010\u000f\u001a%\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u0001H#02H\u0086\b\u001aa\u0010e\u001a\u0002H6\"\b\b\u0000\u0010#*\u00020d\"\u0010\b\u0001\u00106*\n\u0012\u0006\b\u0000\u0012\u0002H#0F*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62)\u0010\u000f\u001a%\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u0001H#02H\u0086\b¢\u0006\u0002\u0010f\u001a[\u0010g\u001a\u0002H6\"\u0004\b\u0000\u0010#\"\u0010\b\u0001\u00106*\n\u0012\u0006\b\u0000\u0012\u0002H#0F*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62'\u0010\u000f\u001a#\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#02H\u0086\b¢\u0006\u0002\u0010f\u001a3\u0010h\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\b\b\u0000\u0010#*\u00020d*\u00020\u00022\u0014\u0010\u000f\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u0001H#0\u0004H\u0086\b\u001aL\u0010i\u001a\u0002H6\"\b\b\u0000\u0010#*\u00020d\"\u0010\b\u0001\u00106*\n\u0012\u0006\b\u0000\u0012\u0002H#0F*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62\u0014\u0010\u000f\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u0001H#0\u0004H\u0086\b¢\u0006\u0002\u0010G\u001aF\u0010j\u001a\u0002H6\"\u0004\b\u0000\u0010#\"\u0010\b\u0001\u00106*\n\u0012\u0006\b\u0000\u0012\u0002H#0F*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#0\u0004H\u0086\b¢\u0006\u0002\u0010G\u001a\u0011\u0010k\u001a\u0004\u0018\u00010\u0005*\u00020\u0002¢\u0006\u0002\u0010C\u001a8\u0010l\u001a\u0004\u0018\u00010\u0005\"\u000e\b\u0000\u0010#*\b\u0012\u0004\u0012\u0002H#0m*\u00020\u00022\u0012\u0010n\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#0\u0004H\u0086\b¢\u0006\u0002\u0010?\u001a-\u0010o\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u001a\u0010p\u001a\u0016\u0012\u0006\b\u0000\u0012\u00020\u00050qj\n\u0012\u0006\b\u0000\u0012\u00020\u0005`r¢\u0006\u0002\u0010s\u001a\u0011\u0010t\u001a\u0004\u0018\u00010\u0005*\u00020\u0002¢\u0006\u0002\u0010C\u001a8\u0010u\u001a\u0004\u0018\u00010\u0005\"\u000e\b\u0000\u0010#*\b\u0012\u0004\u0012\u0002H#0m*\u00020\u00022\u0012\u0010n\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#0\u0004H\u0086\b¢\u0006\u0002\u0010?\u001a-\u0010v\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u001a\u0010p\u001a\u0016\u0012\u0006\b\u0000\u0012\u00020\u00050qj\n\u0012\u0006\b\u0000\u0012\u00020\u0005`r¢\u0006\u0002\u0010s\u001a\n\u0010w\u001a\u00020\u0001*\u00020\u0002\u001a!\u0010w\u001a\u00020\u0001*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a0\u0010x\u001a\u0002Hy\"\b\b\u0000\u0010y*\u00020\u0002*\u0002Hy2\u0012\u0010T\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020S0\u0004H\u0087\b¢\u0006\u0002\u0010z\u001a-\u0010{\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0010*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a-\u0010{\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020 0\u0010*\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\r\u0010|\u001a\u00020\u0005*\u00020\u0002H\u0087\b\u001a\u0014\u0010|\u001a\u00020\u0005*\u00020\u00022\u0006\u0010|\u001a\u00020}H\u0007\u001a6\u0010~\u001a\u00020\u0005*\u00020\u00022'\u0010J\u001a#\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000502H\u0086\b\u001aK\u0010\u007f\u001a\u00020\u0005*\u00020\u00022<\u0010J\u001a8\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050NH\u0086\b\u001a7\u0010\u0080\u0001\u001a\u00020\u0005*\u00020\u00022'\u0010J\u001a#\u0012\u0004\u0012\u00020\u0005\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u000502H\u0086\b\u001aL\u0010\u0081\u0001\u001a\u00020\u0005*\u00020\u00022<\u0010J\u001a8\u0012\u0013\u0012\u00110\"¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u00050NH\u0086\b\u001a\u000b\u0010\u0082\u0001\u001a\u00020\u0002*\u00020\u0002\u001a\u000e\u0010\u0082\u0001\u001a\u00020 *\u00020 H\u0087\b\u001a\u000b\u0010\u0083\u0001\u001a\u00020\u0005*\u00020\u0002\u001a\"\u0010\u0083\u0001\u001a\u00020\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\u0012\u0010\u0084\u0001\u001a\u0004\u0018\u00010\u0005*\u00020\u0002¢\u0006\u0002\u0010C\u001a)\u0010\u0084\u0001\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b¢\u0006\u0002\u0010?\u001a\u001a\u0010\u0085\u0001\u001a\u00020\u0002*\u00020\u00022\r\u0010\u0086\u0001\u001a\b\u0012\u0004\u0012\u00020\"0\b\u001a\u0015\u0010\u0085\u0001\u001a\u00020\u0002*\u00020\u00022\b\u0010\u0086\u0001\u001a\u00030\u0087\u0001\u001a\u001d\u0010\u0085\u0001\u001a\u00020 *\u00020 2\r\u0010\u0086\u0001\u001a\b\u0012\u0004\u0012\u00020\"0\bH\u0087\b\u001a\u0015\u0010\u0085\u0001\u001a\u00020 *\u00020 2\b\u0010\u0086\u0001\u001a\u00030\u0087\u0001\u001a\"\u0010\u0088\u0001\u001a\u00020\"*\u00020\u00022\u0012\u0010n\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\"0\u0004H\u0086\b\u001a$\u0010\u0089\u0001\u001a\u00030\u008a\u0001*\u00020\u00022\u0013\u0010n\u001a\u000f\u0012\u0004\u0012\u00020\u0005\u0012\u0005\u0012\u00030\u008a\u00010\u0004H\u0086\b\u001a\u0013\u0010\u008b\u0001\u001a\u00020\u0002*\u00020\u00022\u0006\u0010'\u001a\u00020\"\u001a\u0013\u0010\u008b\u0001\u001a\u00020 *\u00020 2\u0006\u0010'\u001a\u00020\"\u001a\u0013\u0010\u008c\u0001\u001a\u00020\u0002*\u00020\u00022\u0006\u0010'\u001a\u00020\"\u001a\u0013\u0010\u008c\u0001\u001a\u00020 *\u00020 2\u0006\u0010'\u001a\u00020\"\u001a\"\u0010\u008d\u0001\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\"\u0010\u008d\u0001\u001a\u00020 *\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\"\u0010\u008e\u0001\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\"\u0010\u008e\u0001\u001a\u00020 *\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a+\u0010\u008f\u0001\u001a\u0002H6\"\u0010\b\u0000\u00106*\n\u0012\u0006\b\u0000\u0012\u00020\u00050F*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H6¢\u0006\u0003\u0010\u0090\u0001\u001a\u001d\u0010\u0091\u0001\u001a\u0014\u0012\u0004\u0012\u00020\u00050\u0092\u0001j\t\u0012\u0004\u0012\u00020\u0005`\u0093\u0001*\u00020\u0002\u001a\u0011\u0010\u0094\u0001\u001a\b\u0012\u0004\u0012\u00020\u00050\u001f*\u00020\u0002\u001a\u0011\u0010\u0095\u0001\u001a\b\u0012\u0004\u0012\u00020\u00050Z*\u00020\u0002\u001a\u0012\u0010\u0096\u0001\u001a\t\u0012\u0004\u0012\u00020\u00050\u0097\u0001*\u00020\u0002\u001a1\u0010\u0098\u0001\u001a\b\u0012\u0004\u0012\u00020 0\u001f*\u00020\u00022\u0006\u0010!\u001a\u00020\"2\t\b\u0002\u0010\u0099\u0001\u001a\u00020\"2\t\b\u0002\u0010\u009a\u0001\u001a\u00020\u0001H\u0007\u001aK\u0010\u0098\u0001\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010!\u001a\u00020\"2\t\b\u0002\u0010\u0099\u0001\u001a\u00020\"2\t\b\u0002\u0010\u009a\u0001\u001a\u00020\u00012\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H#0\u0004H\u0007\u001a1\u0010\u009b\u0001\u001a\b\u0012\u0004\u0012\u00020 0\n*\u00020\u00022\u0006\u0010!\u001a\u00020\"2\t\b\u0002\u0010\u0099\u0001\u001a\u00020\"2\t\b\u0002\u0010\u009a\u0001\u001a\u00020\u0001H\u0007\u001aK\u0010\u009b\u0001\u001a\b\u0012\u0004\u0012\u0002H#0\n\"\u0004\b\u0000\u0010#*\u00020\u00022\u0006\u0010!\u001a\u00020\"2\t\b\u0002\u0010\u0099\u0001\u001a\u00020\"2\t\b\u0002\u0010\u009a\u0001\u001a\u00020\u00012\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H#0\u0004H\u0007\u001a\u0018\u0010\u009c\u0001\u001a\u000f\u0012\u000b\u0012\t\u0012\u0004\u0012\u00020\u00050\u009d\u00010\b*\u00020\u0002\u001a)\u0010\u009e\u0001\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00100\u001f*\u00020\u00022\u0007\u0010\u009f\u0001\u001a\u00020\u0002H\u0086\u0004\u001a]\u0010\u009e\u0001\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u001f\"\u0004\b\u0000\u0010\u000e*\u00020\u00022\u0007\u0010\u009f\u0001\u001a\u00020\u000228\u0010\u000f\u001a4\u0012\u0014\u0012\u00120\u0005¢\u0006\r\b3\u0012\t\b4\u0012\u0005\b\b( \u0001\u0012\u0014\u0012\u00120\u0005¢\u0006\r\b3\u0012\t\b4\u0012\u0005\b\b(¡\u0001\u0012\u0004\u0012\u0002H\u000e02H\u0086\b\u001a\u001f\u0010¢\u0001\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00100\u001f*\u00020\u0002H\u0007\u001aT\u0010¢\u0001\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\u0000\u0010#*\u00020\u000228\u0010\u000f\u001a4\u0012\u0014\u0012\u00120\u0005¢\u0006\r\b3\u0012\t\b4\u0012\u0005\b\b( \u0001\u0012\u0014\u0012\u00120\u0005¢\u0006\r\b3\u0012\t\b4\u0012\u0005\b\b(¡\u0001\u0012\u0004\u0012\u0002H#02H\u0087\b¨\u0006£\u0001"},
   d2 = {"all", "", "", "predicate", "Lkotlin/Function1;", "", "any", "asIterable", "", "asSequence", "Lkotlin/sequences/Sequence;", "associate", "", "K", "V", "transform", "Lkotlin/Pair;", "associateBy", "keySelector", "valueTransform", "associateByTo", "M", "", "destination", "(Ljava/lang/CharSequence;Ljava/util/Map;Lkotlin/jvm/functions/Function1;)Ljava/util/Map;", "(Ljava/lang/CharSequence;Ljava/util/Map;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Ljava/util/Map;", "associateTo", "associateWith", "valueSelector", "associateWithTo", "chunked", "", "", "size", "", "R", "chunkedSequence", "count", "drop", "n", "dropLast", "dropLastWhile", "dropWhile", "elementAtOrElse", "index", "defaultValue", "elementAtOrNull", "(Ljava/lang/CharSequence;I)Ljava/lang/Character;", "filter", "filterIndexed", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "filterIndexedTo", "C", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "(Ljava/lang/CharSequence;Ljava/lang/Appendable;Lkotlin/jvm/functions/Function2;)Ljava/lang/Appendable;", "filterNot", "filterNotTo", "(Ljava/lang/CharSequence;Ljava/lang/Appendable;Lkotlin/jvm/functions/Function1;)Ljava/lang/Appendable;", "filterTo", "find", "(Ljava/lang/CharSequence;Lkotlin/jvm/functions/Function1;)Ljava/lang/Character;", "findLast", "first", "firstOrNull", "(Ljava/lang/CharSequence;)Ljava/lang/Character;", "flatMap", "flatMapTo", "", "(Ljava/lang/CharSequence;Ljava/util/Collection;Lkotlin/jvm/functions/Function1;)Ljava/util/Collection;", "fold", "initial", "operation", "acc", "(Ljava/lang/CharSequence;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "foldIndexed", "Lkotlin/Function3;", "(Ljava/lang/CharSequence;Ljava/lang/Object;Lkotlin/jvm/functions/Function3;)Ljava/lang/Object;", "foldRight", "foldRightIndexed", "forEach", "", "action", "forEachIndexed", "getOrElse", "getOrNull", "groupBy", "groupByTo", "", "groupingBy", "Lkotlin/collections/Grouping;", "indexOfFirst", "indexOfLast", "last", "lastOrNull", "map", "mapIndexed", "mapIndexedNotNull", "", "mapIndexedNotNullTo", "(Ljava/lang/CharSequence;Ljava/util/Collection;Lkotlin/jvm/functions/Function2;)Ljava/util/Collection;", "mapIndexedTo", "mapNotNull", "mapNotNullTo", "mapTo", "max", "maxBy", "", "selector", "maxWith", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Ljava/lang/CharSequence;Ljava/util/Comparator;)Ljava/lang/Character;", "min", "minBy", "minWith", "none", "onEach", "S", "(Ljava/lang/CharSequence;Lkotlin/jvm/functions/Function1;)Ljava/lang/CharSequence;", "partition", "random", "Lkotlin/random/Random;", "reduce", "reduceIndexed", "reduceRight", "reduceRightIndexed", "reversed", "single", "singleOrNull", "slice", "indices", "Lkotlin/ranges/IntRange;", "sumBy", "sumByDouble", "", "take", "takeLast", "takeLastWhile", "takeWhile", "toCollection", "(Ljava/lang/CharSequence;Ljava/util/Collection;)Ljava/util/Collection;", "toHashSet", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "toList", "toMutableList", "toSet", "", "windowed", "step", "partialWindows", "windowedSequence", "withIndex", "Lkotlin/collections/IndexedValue;", "zip", "other", "a", "b", "zipWithNext", "kotlin-stdlib"},
   xs = "kotlin/text/StringsKt"
)
class StringsKt___StringsKt extends StringsKt___StringsJvmKt {
   @InlineOnly
   private static final char elementAtOrElse(@NotNull CharSequence var0, int var1, Function1 var2) {
      byte var3 = 0;
      return var1 >= 0 && var1 <= StringsKt.getLastIndex(var0) ? var0.charAt(var1) : (Character)var2.invoke(var1);
   }

   @InlineOnly
   private static final Character elementAtOrNull(@NotNull CharSequence var0, int var1) {
      byte var2 = 0;
      return StringsKt.getOrNull(var0, var1);
   }

   @InlineOnly
   private static final Character find(@NotNull CharSequence var0, Function1 var1) {
      byte var2 = 0;
      boolean var4 = false;
      CharSequence var5 = var0;
      int var6 = 0;

      Character var10000;
      while(true) {
         if (var6 >= var5.length()) {
            var10000 = null;
            break;
         }

         char var7 = var5.charAt(var6);
         if ((Boolean)var1.invoke(var7)) {
            var10000 = var7;
            break;
         }

         ++var6;
      }

      return var10000;
   }

   @InlineOnly
   private static final Character findLast(@NotNull CharSequence var0, Function1 var1) {
      byte var2 = 0;
      CharSequence var3 = var0;
      boolean var4 = false;
      int var5 = var0.length();
      --var5;
      boolean var6 = false;

      Character var10000;
      while(true) {
         if (var5 < 0) {
            var10000 = null;
            break;
         }

         char var7 = var3.charAt(var5);
         if ((Boolean)var1.invoke(var7)) {
            var10000 = var7;
            break;
         }

         --var5;
      }

      return var10000;
   }

   public static final char first(@NotNull CharSequence var0) {
      boolean var2 = false;
      if (var0.length() == 0) {
         throw (Throwable)(new NoSuchElementException("Char sequence is empty."));
      } else {
         return var0.charAt(0);
      }
   }

   public static final char first(@NotNull CharSequence var0, @NotNull Function1 var1) {
      byte var2 = 0;
      CharSequence var5 = var0;

      for(int var4 = 0; var4 < var5.length(); ++var4) {
         char var3 = var5.charAt(var4);
         if ((Boolean)var1.invoke(var3)) {
            return var3;
         }
      }

      throw (Throwable)(new NoSuchElementException("Char sequence contains no character matching the predicate."));
   }

   @Nullable
   public static final Character firstOrNull(@NotNull CharSequence var0) {
      boolean var2 = false;
      return var0.length() == 0 ? null : var0.charAt(0);
   }

   @Nullable
   public static final Character firstOrNull(@NotNull CharSequence var0, @NotNull Function1 var1) {
      byte var2 = 0;
      CharSequence var5 = var0;

      for(int var4 = 0; var4 < var5.length(); ++var4) {
         char var3 = var5.charAt(var4);
         if ((Boolean)var1.invoke(var3)) {
            return var3;
         }
      }

      return null;
   }

   @InlineOnly
   private static final char getOrElse(@NotNull CharSequence var0, int var1, Function1 var2) {
      byte var3 = 0;
      return var1 >= 0 && var1 <= StringsKt.getLastIndex(var0) ? var0.charAt(var1) : (Character)var2.invoke(var1);
   }

   @Nullable
   public static final Character getOrNull(@NotNull CharSequence var0, int var1) {
      return var1 >= 0 && var1 <= StringsKt.getLastIndex(var0) ? var0.charAt(var1) : null;
   }

   public static final int indexOfFirst(@NotNull CharSequence var0, @NotNull Function1 var1) {
      byte var2 = 0;
      int var3 = 0;

      for(int var4 = var0.length(); var3 < var4; ++var3) {
         if ((Boolean)var1.invoke(var0.charAt(var3))) {
            return var3;
         }
      }

      return -1;
   }

   public static final int indexOfLast(@NotNull CharSequence var0, @NotNull Function1 var1) {
      byte var2 = 0;
      int var3 = var0.length();
      --var3;

      for(boolean var4 = false; var3 >= 0; --var3) {
         if ((Boolean)var1.invoke(var0.charAt(var3))) {
            return var3;
         }
      }

      return -1;
   }

   public static final char last(@NotNull CharSequence var0) {
      boolean var2 = false;
      if (var0.length() == 0) {
         throw (Throwable)(new NoSuchElementException("Char sequence is empty."));
      } else {
         return var0.charAt(StringsKt.getLastIndex(var0));
      }
   }

   public static final char last(@NotNull CharSequence var0, @NotNull Function1 var1) {
      byte var2 = 0;
      int var3 = var0.length();
      --var3;

      for(boolean var4 = false; var3 >= 0; --var3) {
         char var5 = var0.charAt(var3);
         if ((Boolean)var1.invoke(var5)) {
            return var5;
         }
      }

      throw (Throwable)(new NoSuchElementException("Char sequence contains no character matching the predicate."));
   }

   @Nullable
   public static final Character lastOrNull(@NotNull CharSequence var0) {
      boolean var2 = false;
      return var0.length() == 0 ? null : var0.charAt(var0.length() - 1);
   }

   @Nullable
   public static final Character lastOrNull(@NotNull CharSequence var0, @NotNull Function1 var1) {
      byte var2 = 0;
      int var3 = var0.length();
      --var3;

      for(boolean var4 = false; var3 >= 0; --var3) {
         char var5 = var0.charAt(var3);
         if ((Boolean)var1.invoke(var5)) {
            return var5;
         }
      }

      return null;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final char random(@NotNull CharSequence var0) {
      byte var1 = 0;
      return StringsKt.random(var0, (Random)Random.Default);
   }

   @SinceKotlin(
      version = "1.3"
   )
   public static final char random(@NotNull CharSequence var0, @NotNull Random var1) {
      boolean var3 = false;
      if (var0.length() == 0) {
         throw (Throwable)(new NoSuchElementException("Char sequence is empty."));
      } else {
         return var0.charAt(var1.nextInt(var0.length()));
      }
   }

   public static final char single(@NotNull CharSequence var0) {
      switch(var0.length()) {
      case 0:
         throw (Throwable)(new NoSuchElementException("Char sequence is empty."));
      case 1:
         return var0.charAt(0);
      default:
         throw (Throwable)(new IllegalArgumentException("Char sequence has more than one element."));
      }
   }

   public static final char single(@NotNull CharSequence var0, @NotNull Function1 var1) {
      byte var2 = 0;
      Character var3 = (Character)null;
      boolean var4 = false;
      CharSequence var7 = var0;

      for(int var6 = 0; var6 < var7.length(); ++var6) {
         char var5 = var7.charAt(var6);
         if ((Boolean)var1.invoke(var5)) {
            if (var4) {
               throw (Throwable)(new IllegalArgumentException("Char sequence contains more than one matching element."));
            }

            var3 = var5;
            var4 = true;
         }
      }

      if (!var4) {
         throw (Throwable)(new NoSuchElementException("Char sequence contains no character matching the predicate."));
      } else if (var3 == null) {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.Char");
      } else {
         return var3;
      }
   }

   @Nullable
   public static final Character singleOrNull(@NotNull CharSequence var0) {
      return var0.length() == 1 ? var0.charAt(0) : null;
   }

   @Nullable
   public static final Character singleOrNull(@NotNull CharSequence var0, @NotNull Function1 var1) {
      byte var2 = 0;
      Character var3 = (Character)null;
      boolean var4 = false;
      CharSequence var7 = var0;

      for(int var6 = 0; var6 < var7.length(); ++var6) {
         char var5 = var7.charAt(var6);
         if ((Boolean)var1.invoke(var5)) {
            if (var4) {
               return null;
            }

            var3 = var5;
            var4 = true;
         }
      }

      if (!var4) {
         return null;
      } else {
         return var3;
      }
   }

   @NotNull
   public static final CharSequence drop(@NotNull CharSequence var0, int var1) {
      boolean var2 = var1 >= 0;
      boolean var3 = false;
      boolean var4 = false;
      if (!var2) {
         boolean var5 = false;
         String var6 = "Requested character count " + var1 + " is less than zero.";
         throw (Throwable)(new IllegalArgumentException(var6.toString()));
      } else {
         return var0.subSequence(RangesKt.coerceAtMost(var1, var0.length()), var0.length());
      }
   }

   @NotNull
   public static final String drop(@NotNull String var0, int var1) {
      boolean var2 = var1 >= 0;
      boolean var3 = false;
      boolean var4 = false;
      if (!var2) {
         boolean var5 = false;
         String var7 = "Requested character count " + var1 + " is less than zero.";
         throw (Throwable)(new IllegalArgumentException(var7.toString()));
      } else {
         int var6 = RangesKt.coerceAtMost(var1, var0.length());
         var4 = false;
         return var0.substring(var6);
      }
   }

   @NotNull
   public static final CharSequence dropLast(@NotNull CharSequence var0, int var1) {
      boolean var2 = var1 >= 0;
      boolean var3 = false;
      boolean var4 = false;
      if (!var2) {
         boolean var5 = false;
         String var6 = "Requested character count " + var1 + " is less than zero.";
         throw (Throwable)(new IllegalArgumentException(var6.toString()));
      } else {
         return StringsKt.take(var0, RangesKt.coerceAtLeast(var0.length() - var1, 0));
      }
   }

   @NotNull
   public static final String dropLast(@NotNull String var0, int var1) {
      boolean var2 = var1 >= 0;
      boolean var3 = false;
      boolean var4 = false;
      if (!var2) {
         boolean var5 = false;
         String var6 = "Requested character count " + var1 + " is less than zero.";
         throw (Throwable)(new IllegalArgumentException(var6.toString()));
      } else {
         return StringsKt.take(var0, RangesKt.coerceAtLeast(var0.length() - var1, 0));
      }
   }

   @NotNull
   public static final CharSequence dropLastWhile(@NotNull CharSequence var0, @NotNull Function1 var1) {
      byte var2 = 0;
      int var3 = StringsKt.getLastIndex(var0);

      for(boolean var4 = false; var3 >= 0; --var3) {
         if (!(Boolean)var1.invoke(var0.charAt(var3))) {
            return var0.subSequence(0, var3 + 1);
         }
      }

      return (CharSequence)"";
   }

   @NotNull
   public static final String dropLastWhile(@NotNull String var0, @NotNull Function1 var1) {
      byte var2 = 0;
      int var3 = StringsKt.getLastIndex((CharSequence)var0);

      for(boolean var4 = false; var3 >= 0; --var3) {
         if (!(Boolean)var1.invoke(var0.charAt(var3))) {
            byte var6 = 0;
            int var7 = var3 + 1;
            boolean var8 = false;
            return var0.substring(var6, var7);
         }
      }

      return "";
   }

   @NotNull
   public static final CharSequence dropWhile(@NotNull CharSequence var0, @NotNull Function1 var1) {
      byte var2 = 0;
      int var3 = 0;

      for(int var4 = var0.length(); var3 < var4; ++var3) {
         if (!(Boolean)var1.invoke(var0.charAt(var3))) {
            return var0.subSequence(var3, var0.length());
         }
      }

      return (CharSequence)"";
   }

   @NotNull
   public static final String dropWhile(@NotNull String var0, @NotNull Function1 var1) {
      byte var2 = 0;
      int var3 = 0;

      for(int var4 = ((CharSequence)var0).length(); var3 < var4; ++var3) {
         if (!(Boolean)var1.invoke(var0.charAt(var3))) {
            boolean var6 = false;
            return var0.substring(var3);
         }
      }

      return "";
   }

   @NotNull
   public static final CharSequence filter(@NotNull CharSequence var0, @NotNull Function1 var1) {
      byte var2 = 0;
      CharSequence var3 = var0;
      Appendable var4 = (Appendable)(new StringBuilder());
      boolean var5 = false;
      int var6 = 0;

      for(int var7 = var0.length(); var6 < var7; ++var6) {
         char var8 = var3.charAt(var6);
         if ((Boolean)var1.invoke(var8)) {
            var4.append(var8);
         }
      }

      return (CharSequence)var4;
   }

   @NotNull
   public static final String filter(@NotNull String var0, @NotNull Function1 var1) {
      byte var2 = 0;
      CharSequence var3 = (CharSequence)var0;
      Appendable var4 = (Appendable)(new StringBuilder());
      boolean var5 = false;
      int var6 = 0;

      for(int var7 = var3.length(); var6 < var7; ++var6) {
         char var8 = var3.charAt(var6);
         if ((Boolean)var1.invoke(var8)) {
            var4.append(var8);
         }
      }

      return ((StringBuilder)var4).toString();
   }

   @NotNull
   public static final CharSequence filterIndexed(@NotNull CharSequence var0, @NotNull Function2 var1) {
      byte var2 = 0;
      Appendable var4 = (Appendable)(new StringBuilder());
      boolean var5 = false;
      boolean var7 = false;
      int var8 = 0;
      CharSequence var9 = var0;

      for(int var10 = 0; var10 < var9.length(); ++var10) {
         char var11 = var9.charAt(var10);
         int var13 = var8++;
         boolean var14 = false;
         if ((Boolean)var1.invoke(var13, var11)) {
            var4.append(var11);
         }
      }

      return (CharSequence)var4;
   }

   @NotNull
   public static final String filterIndexed(@NotNull String var0, @NotNull Function2 var1) {
      byte var2 = 0;
      CharSequence var3 = (CharSequence)var0;
      Appendable var4 = (Appendable)(new StringBuilder());
      boolean var5 = false;
      boolean var7 = false;
      int var8 = 0;
      CharSequence var9 = var3;

      for(int var10 = 0; var10 < var9.length(); ++var10) {
         char var11 = var9.charAt(var10);
         int var13 = var8++;
         boolean var14 = false;
         if ((Boolean)var1.invoke(var13, var11)) {
            var4.append(var11);
         }
      }

      return ((StringBuilder)var4).toString();
   }

   @NotNull
   public static final Appendable filterIndexedTo(@NotNull CharSequence var0, @NotNull Appendable var1, @NotNull Function2 var2) {
      byte var3 = 0;
      boolean var5 = false;
      int var6 = 0;
      CharSequence var7 = var0;

      for(int var8 = 0; var8 < var7.length(); ++var8) {
         char var9 = var7.charAt(var8);
         int var11 = var6++;
         boolean var12 = false;
         if ((Boolean)var2.invoke(var11, var9)) {
            var1.append(var9);
         }
      }

      return var1;
   }

   @NotNull
   public static final CharSequence filterNot(@NotNull CharSequence var0, @NotNull Function1 var1) {
      byte var2 = 0;
      Appendable var4 = (Appendable)(new StringBuilder());
      boolean var5 = false;
      CharSequence var6 = var0;

      for(int var7 = 0; var7 < var6.length(); ++var7) {
         char var8 = var6.charAt(var7);
         if (!(Boolean)var1.invoke(var8)) {
            var4.append(var8);
         }
      }

      return (CharSequence)var4;
   }

   @NotNull
   public static final String filterNot(@NotNull String var0, @NotNull Function1 var1) {
      byte var2 = 0;
      CharSequence var3 = (CharSequence)var0;
      Appendable var4 = (Appendable)(new StringBuilder());
      boolean var5 = false;
      CharSequence var6 = var3;

      for(int var7 = 0; var7 < var6.length(); ++var7) {
         char var8 = var6.charAt(var7);
         if (!(Boolean)var1.invoke(var8)) {
            var4.append(var8);
         }
      }

      return ((StringBuilder)var4).toString();
   }

   @NotNull
   public static final Appendable filterNotTo(@NotNull CharSequence var0, @NotNull Appendable var1, @NotNull Function1 var2) {
      byte var3 = 0;
      CharSequence var6 = var0;

      for(int var5 = 0; var5 < var6.length(); ++var5) {
         char var4 = var6.charAt(var5);
         if (!(Boolean)var2.invoke(var4)) {
            var1.append(var4);
         }
      }

      return var1;
   }

   @NotNull
   public static final Appendable filterTo(@NotNull CharSequence var0, @NotNull Appendable var1, @NotNull Function1 var2) {
      byte var3 = 0;
      int var4 = 0;

      for(int var5 = var0.length(); var4 < var5; ++var4) {
         char var6 = var0.charAt(var4);
         if ((Boolean)var2.invoke(var6)) {
            var1.append(var6);
         }
      }

      return var1;
   }

   @NotNull
   public static final CharSequence slice(@NotNull CharSequence var0, @NotNull IntRange var1) {
      return var1.isEmpty() ? (CharSequence)"" : StringsKt.subSequence(var0, var1);
   }

   @NotNull
   public static final String slice(@NotNull String var0, @NotNull IntRange var1) {
      return var1.isEmpty() ? "" : StringsKt.substring(var0, var1);
   }

   @NotNull
   public static final CharSequence slice(@NotNull CharSequence var0, @NotNull Iterable var1) {
      int var2 = CollectionsKt.collectionSizeOrDefault(var1, 10);
      if (var2 == 0) {
         return (CharSequence)"";
      } else {
         StringBuilder var3 = new StringBuilder(var2);
         Iterator var5 = var1.iterator();

         while(var5.hasNext()) {
            int var4 = ((Number)var5.next()).intValue();
            var3.append(var0.charAt(var4));
         }

         return (CharSequence)var3;
      }
   }

   @InlineOnly
   private static final String slice(@NotNull String var0, Iterable var1) {
      return StringsKt.slice((CharSequence)var0, var1).toString();
   }

   @NotNull
   public static final CharSequence take(@NotNull CharSequence var0, int var1) {
      boolean var2 = var1 >= 0;
      boolean var3 = false;
      boolean var4 = false;
      if (!var2) {
         boolean var5 = false;
         String var6 = "Requested character count " + var1 + " is less than zero.";
         throw (Throwable)(new IllegalArgumentException(var6.toString()));
      } else {
         return var0.subSequence(0, RangesKt.coerceAtMost(var1, var0.length()));
      }
   }

   @NotNull
   public static final String take(@NotNull String var0, int var1) {
      boolean var2 = var1 >= 0;
      boolean var3 = false;
      boolean var4 = false;
      boolean var5;
      if (!var2) {
         var5 = false;
         String var8 = "Requested character count " + var1 + " is less than zero.";
         throw (Throwable)(new IllegalArgumentException(var8.toString()));
      } else {
         byte var6 = 0;
         int var7 = RangesKt.coerceAtMost(var1, var0.length());
         var5 = false;
         return var0.substring(var6, var7);
      }
   }

   @NotNull
   public static final CharSequence takeLast(@NotNull CharSequence var0, int var1) {
      boolean var2 = var1 >= 0;
      boolean var3 = false;
      boolean var4 = false;
      if (!var2) {
         boolean var5 = false;
         String var7 = "Requested character count " + var1 + " is less than zero.";
         throw (Throwable)(new IllegalArgumentException(var7.toString()));
      } else {
         int var6 = var0.length();
         return var0.subSequence(var6 - RangesKt.coerceAtMost(var1, var6), var6);
      }
   }

   @NotNull
   public static final String takeLast(@NotNull String var0, int var1) {
      boolean var2 = var1 >= 0;
      boolean var3 = false;
      boolean var4 = false;
      boolean var5;
      if (!var2) {
         var5 = false;
         String var8 = "Requested character count " + var1 + " is less than zero.";
         throw (Throwable)(new IllegalArgumentException(var8.toString()));
      } else {
         int var6 = var0.length();
         int var7 = var6 - RangesKt.coerceAtMost(var1, var6);
         var5 = false;
         return var0.substring(var7);
      }
   }

   @NotNull
   public static final CharSequence takeLastWhile(@NotNull CharSequence var0, @NotNull Function1 var1) {
      byte var2 = 0;
      int var3 = StringsKt.getLastIndex(var0);

      for(boolean var4 = false; var3 >= 0; --var3) {
         if (!(Boolean)var1.invoke(var0.charAt(var3))) {
            return var0.subSequence(var3 + 1, var0.length());
         }
      }

      return var0.subSequence(0, var0.length());
   }

   @NotNull
   public static final String takeLastWhile(@NotNull String var0, @NotNull Function1 var1) {
      byte var2 = 0;
      int var3 = StringsKt.getLastIndex((CharSequence)var0);

      for(boolean var4 = false; var3 >= 0; --var3) {
         if (!(Boolean)var1.invoke(var0.charAt(var3))) {
            int var6 = var3 + 1;
            boolean var7 = false;
            return var0.substring(var6);
         }
      }

      return var0;
   }

   @NotNull
   public static final CharSequence takeWhile(@NotNull CharSequence var0, @NotNull Function1 var1) {
      byte var2 = 0;
      int var3 = 0;

      for(int var4 = var0.length(); var3 < var4; ++var3) {
         if (!(Boolean)var1.invoke(var0.charAt(var3))) {
            return var0.subSequence(0, var3);
         }
      }

      return var0.subSequence(0, var0.length());
   }

   @NotNull
   public static final String takeWhile(@NotNull String var0, @NotNull Function1 var1) {
      byte var2 = 0;
      int var3 = 0;

      for(int var4 = var0.length(); var3 < var4; ++var3) {
         if (!(Boolean)var1.invoke(var0.charAt(var3))) {
            byte var6 = 0;
            boolean var7 = false;
            return var0.substring(var6, var3);
         }
      }

      return var0;
   }

   @NotNull
   public static final CharSequence reversed(@NotNull CharSequence var0) {
      return (CharSequence)(new StringBuilder(var0)).reverse();
   }

   @InlineOnly
   private static final String reversed(@NotNull String var0) {
      return StringsKt.reversed((CharSequence)var0).toString();
   }

   @NotNull
   public static final Map associate(@NotNull CharSequence var0, @NotNull Function1 var1) {
      byte var2 = 0;
      int var3 = RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length()), 16);
      Map var5 = (Map)(new LinkedHashMap(var3));
      boolean var6 = false;
      CharSequence var7 = var0;

      for(int var8 = 0; var8 < var7.length(); ++var8) {
         char var9 = var7.charAt(var8);
         Pair var11 = (Pair)var1.invoke(var9);
         boolean var12 = false;
         var5.put(var11.getFirst(), var11.getSecond());
      }

      return var5;
   }

   @NotNull
   public static final Map associateBy(@NotNull CharSequence var0, @NotNull Function1 var1) {
      byte var2 = 0;
      int var3 = RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length()), 16);
      Map var5 = (Map)(new LinkedHashMap(var3));
      boolean var6 = false;
      CharSequence var7 = var0;

      for(int var8 = 0; var8 < var7.length(); ++var8) {
         char var9 = var7.charAt(var8);
         var5.put(var1.invoke(var9), var9);
      }

      return var5;
   }

   @NotNull
   public static final Map associateBy(@NotNull CharSequence var0, @NotNull Function1 var1, @NotNull Function1 var2) {
      byte var3 = 0;
      int var4 = RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length()), 16);
      Map var6 = (Map)(new LinkedHashMap(var4));
      boolean var7 = false;
      CharSequence var8 = var0;

      for(int var9 = 0; var9 < var8.length(); ++var9) {
         char var10 = var8.charAt(var9);
         var6.put(var1.invoke(var10), var2.invoke(var10));
      }

      return var6;
   }

   @NotNull
   public static final Map associateByTo(@NotNull CharSequence var0, @NotNull Map var1, @NotNull Function1 var2) {
      byte var3 = 0;
      CharSequence var6 = var0;

      for(int var5 = 0; var5 < var6.length(); ++var5) {
         char var4 = var6.charAt(var5);
         var1.put(var2.invoke(var4), var4);
      }

      return var1;
   }

   @NotNull
   public static final Map associateByTo(@NotNull CharSequence var0, @NotNull Map var1, @NotNull Function1 var2, @NotNull Function1 var3) {
      byte var4 = 0;
      CharSequence var7 = var0;

      for(int var6 = 0; var6 < var7.length(); ++var6) {
         char var5 = var7.charAt(var6);
         var1.put(var2.invoke(var5), var3.invoke(var5));
      }

      return var1;
   }

   @NotNull
   public static final Map associateTo(@NotNull CharSequence var0, @NotNull Map var1, @NotNull Function1 var2) {
      byte var3 = 0;
      CharSequence var6 = var0;

      for(int var5 = 0; var5 < var6.length(); ++var5) {
         char var4 = var6.charAt(var5);
         Pair var8 = (Pair)var2.invoke(var4);
         boolean var9 = false;
         var1.put(var8.getFirst(), var8.getSecond());
      }

      return var1;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final Map associateWith(@NotNull CharSequence var0, @NotNull Function1 var1) {
      byte var2 = 0;
      LinkedHashMap var3 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length()), 16));
      boolean var5 = false;
      CharSequence var6 = var0;

      for(int var7 = 0; var7 < var6.length(); ++var7) {
         char var8 = var6.charAt(var7);
         ((Map)var3).put(var8, var1.invoke(var8));
      }

      return (Map)var3;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final Map associateWithTo(@NotNull CharSequence var0, @NotNull Map var1, @NotNull Function1 var2) {
      byte var3 = 0;
      CharSequence var6 = var0;

      for(int var5 = 0; var5 < var6.length(); ++var5) {
         char var4 = var6.charAt(var5);
         var1.put(var4, var2.invoke(var4));
      }

      return var1;
   }

   @NotNull
   public static final Collection toCollection(@NotNull CharSequence var0, @NotNull Collection var1) {
      CharSequence var4 = var0;

      for(int var3 = 0; var3 < var4.length(); ++var3) {
         char var2 = var4.charAt(var3);
         var1.add(var2);
      }

      return var1;
   }

   @NotNull
   public static final HashSet toHashSet(@NotNull CharSequence var0) {
      return (HashSet)StringsKt.toCollection(var0, (Collection)(new HashSet(MapsKt.mapCapacity(var0.length()))));
   }

   @NotNull
   public static final List toList(@NotNull CharSequence var0) {
      List var10000;
      switch(var0.length()) {
      case 0:
         var10000 = CollectionsKt.emptyList();
         break;
      case 1:
         var10000 = CollectionsKt.listOf(var0.charAt(0));
         break;
      default:
         var10000 = StringsKt.toMutableList(var0);
      }

      return var10000;
   }

   @NotNull
   public static final List toMutableList(@NotNull CharSequence var0) {
      return (List)StringsKt.toCollection(var0, (Collection)(new ArrayList(var0.length())));
   }

   @NotNull
   public static final Set toSet(@NotNull CharSequence var0) {
      Set var10000;
      switch(var0.length()) {
      case 0:
         var10000 = SetsKt.emptySet();
         break;
      case 1:
         var10000 = SetsKt.setOf(var0.charAt(0));
         break;
      default:
         var10000 = (Set)StringsKt.toCollection(var0, (Collection)(new LinkedHashSet(MapsKt.mapCapacity(var0.length()))));
      }

      return var10000;
   }

   @NotNull
   public static final List flatMap(@NotNull CharSequence var0, @NotNull Function1 var1) {
      byte var2 = 0;
      Collection var4 = (Collection)(new ArrayList());
      boolean var5 = false;
      CharSequence var6 = var0;

      for(int var7 = 0; var7 < var6.length(); ++var7) {
         char var8 = var6.charAt(var7);
         Iterable var9 = (Iterable)var1.invoke(var8);
         CollectionsKt.addAll(var4, var9);
      }

      return (List)var4;
   }

   @NotNull
   public static final Collection flatMapTo(@NotNull CharSequence var0, @NotNull Collection var1, @NotNull Function1 var2) {
      byte var3 = 0;
      CharSequence var6 = var0;

      for(int var5 = 0; var5 < var6.length(); ++var5) {
         char var4 = var6.charAt(var5);
         Iterable var7 = (Iterable)var2.invoke(var4);
         CollectionsKt.addAll(var1, var7);
      }

      return var1;
   }

   @NotNull
   public static final Map groupBy(@NotNull CharSequence var0, @NotNull Function1 var1) {
      byte var2 = 0;
      Map var4 = (Map)(new LinkedHashMap());
      boolean var5 = false;
      CharSequence var6 = var0;

      for(int var7 = 0; var7 < var6.length(); ++var7) {
         char var8 = var6.charAt(var7);
         Object var9 = var1.invoke(var8);
         boolean var11 = false;
         Object var12 = var4.get(var9);
         Object var10000;
         if (var12 == null) {
            boolean var13 = false;
            ArrayList var15 = new ArrayList();
            var4.put(var9, var15);
            var10000 = var15;
         } else {
            var10000 = var12;
         }

         List var14 = (List)var10000;
         var14.add(var8);
      }

      return var4;
   }

   @NotNull
   public static final Map groupBy(@NotNull CharSequence var0, @NotNull Function1 var1, @NotNull Function1 var2) {
      byte var3 = 0;
      Map var5 = (Map)(new LinkedHashMap());
      boolean var6 = false;
      CharSequence var7 = var0;

      for(int var8 = 0; var8 < var7.length(); ++var8) {
         char var9 = var7.charAt(var8);
         Object var10 = var1.invoke(var9);
         boolean var12 = false;
         Object var13 = var5.get(var10);
         Object var10000;
         if (var13 == null) {
            boolean var14 = false;
            ArrayList var16 = new ArrayList();
            var5.put(var10, var16);
            var10000 = var16;
         } else {
            var10000 = var13;
         }

         List var15 = (List)var10000;
         var15.add(var2.invoke(var9));
      }

      return var5;
   }

   @NotNull
   public static final Map groupByTo(@NotNull CharSequence var0, @NotNull Map var1, @NotNull Function1 var2) {
      byte var3 = 0;
      CharSequence var6 = var0;

      for(int var5 = 0; var5 < var6.length(); ++var5) {
         char var4 = var6.charAt(var5);
         Object var7 = var2.invoke(var4);
         boolean var10 = false;
         Object var11 = var1.get(var7);
         Object var10000;
         if (var11 == null) {
            boolean var12 = false;
            ArrayList var13 = new ArrayList();
            var1.put(var7, var13);
            var10000 = var13;
         } else {
            var10000 = var11;
         }

         List var8 = (List)var10000;
         var8.add(var4);
      }

      return var1;
   }

   @NotNull
   public static final Map groupByTo(@NotNull CharSequence var0, @NotNull Map var1, @NotNull Function1 var2, @NotNull Function1 var3) {
      byte var4 = 0;
      CharSequence var7 = var0;

      for(int var6 = 0; var6 < var7.length(); ++var6) {
         char var5 = var7.charAt(var6);
         Object var8 = var2.invoke(var5);
         boolean var11 = false;
         Object var12 = var1.get(var8);
         Object var10000;
         if (var12 == null) {
            boolean var13 = false;
            ArrayList var14 = new ArrayList();
            var1.put(var8, var14);
            var10000 = var14;
         } else {
            var10000 = var12;
         }

         List var9 = (List)var10000;
         var9.add(var3.invoke(var5));
      }

      return var1;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Grouping groupingBy(@NotNull CharSequence var0, @NotNull Function1 var1) {
      byte var2 = 0;
      return (Grouping)(new StringsKt___StringsKt$groupingBy$1(var0, var1));
   }

   @NotNull
   public static final List map(@NotNull CharSequence var0, @NotNull Function1 var1) {
      byte var2 = 0;
      Collection var4 = (Collection)(new ArrayList(var0.length()));
      boolean var5 = false;
      CharSequence var6 = var0;

      for(int var7 = 0; var7 < var6.length(); ++var7) {
         char var8 = var6.charAt(var7);
         var4.add(var1.invoke(var8));
      }

      return (List)var4;
   }

   @NotNull
   public static final List mapIndexed(@NotNull CharSequence var0, @NotNull Function2 var1) {
      byte var2 = 0;
      Collection var4 = (Collection)(new ArrayList(var0.length()));
      boolean var5 = false;
      int var6 = 0;
      CharSequence var7 = var0;

      for(int var8 = 0; var8 < var7.length(); ++var8) {
         char var9 = var7.charAt(var8);
         Integer var10002 = var6;
         ++var6;
         var4.add(var1.invoke(var10002, var9));
      }

      return (List)var4;
   }

   @NotNull
   public static final List mapIndexedNotNull(@NotNull CharSequence var0, @NotNull Function2 var1) {
      byte var2 = 0;
      Collection var4 = (Collection)(new ArrayList());
      boolean var5 = false;
      boolean var7 = false;
      int var8 = 0;
      CharSequence var9 = var0;

      for(int var10 = 0; var10 < var9.length(); ++var10) {
         char var11 = var9.charAt(var10);
         int var13 = var8++;
         boolean var14 = false;
         Object var20 = var1.invoke(var13, var11);
         if (var20 != null) {
            Object var15 = var20;
            boolean var16 = false;
            boolean var17 = false;
            boolean var19 = false;
            var4.add(var15);
         }
      }

      return (List)var4;
   }

   @NotNull
   public static final Collection mapIndexedNotNullTo(@NotNull CharSequence var0, @NotNull Collection var1, @NotNull Function2 var2) {
      byte var3 = 0;
      boolean var5 = false;
      int var6 = 0;
      CharSequence var7 = var0;

      for(int var8 = 0; var8 < var7.length(); ++var8) {
         char var9 = var7.charAt(var8);
         int var11 = var6++;
         boolean var12 = false;
         Object var18 = var2.invoke(var11, var9);
         if (var18 != null) {
            Object var13 = var18;
            boolean var14 = false;
            boolean var15 = false;
            boolean var17 = false;
            var1.add(var13);
         }
      }

      return var1;
   }

   @NotNull
   public static final Collection mapIndexedTo(@NotNull CharSequence var0, @NotNull Collection var1, @NotNull Function2 var2) {
      byte var3 = 0;
      int var4 = 0;
      CharSequence var7 = var0;

      for(int var6 = 0; var6 < var7.length(); ++var6) {
         char var5 = var7.charAt(var6);
         Integer var10002 = var4;
         ++var4;
         var1.add(var2.invoke(var10002, var5));
      }

      return var1;
   }

   @NotNull
   public static final List mapNotNull(@NotNull CharSequence var0, @NotNull Function1 var1) {
      byte var2 = 0;
      Collection var4 = (Collection)(new ArrayList());
      boolean var5 = false;
      boolean var7 = false;
      CharSequence var8 = var0;

      for(int var9 = 0; var9 < var8.length(); ++var9) {
         char var10 = var8.charAt(var9);
         boolean var12 = false;
         Object var10000 = var1.invoke(var10);
         if (var10000 != null) {
            Object var13 = var10000;
            boolean var14 = false;
            boolean var15 = false;
            boolean var17 = false;
            var4.add(var13);
         }
      }

      return (List)var4;
   }

   @NotNull
   public static final Collection mapNotNullTo(@NotNull CharSequence var0, @NotNull Collection var1, @NotNull Function1 var2) {
      byte var3 = 0;
      boolean var5 = false;
      CharSequence var6 = var0;

      for(int var7 = 0; var7 < var6.length(); ++var7) {
         char var8 = var6.charAt(var7);
         boolean var10 = false;
         Object var10000 = var2.invoke(var8);
         if (var10000 != null) {
            Object var11 = var10000;
            boolean var12 = false;
            boolean var13 = false;
            boolean var15 = false;
            var1.add(var11);
         }
      }

      return var1;
   }

   @NotNull
   public static final Collection mapTo(@NotNull CharSequence var0, @NotNull Collection var1, @NotNull Function1 var2) {
      byte var3 = 0;
      CharSequence var6 = var0;

      for(int var5 = 0; var5 < var6.length(); ++var5) {
         char var4 = var6.charAt(var5);
         var1.add(var2.invoke(var4));
      }

      return var1;
   }

   @NotNull
   public static final Iterable withIndex(@NotNull CharSequence var0) {
      return (Iterable)(new IndexingIterable((Function0)(new StringsKt___StringsKt$withIndex$1(var0))));
   }

   public static final boolean all(@NotNull CharSequence var0, @NotNull Function1 var1) {
      byte var2 = 0;
      CharSequence var5 = var0;

      for(int var4 = 0; var4 < var5.length(); ++var4) {
         char var3 = var5.charAt(var4);
         if (!(Boolean)var1.invoke(var3)) {
            return false;
         }
      }

      return true;
   }

   public static final boolean any(@NotNull CharSequence var0) {
      boolean var2 = false;
      return var0.length() != 0;
   }

   public static final boolean any(@NotNull CharSequence var0, @NotNull Function1 var1) {
      byte var2 = 0;
      CharSequence var5 = var0;

      for(int var4 = 0; var4 < var5.length(); ++var4) {
         char var3 = var5.charAt(var4);
         if ((Boolean)var1.invoke(var3)) {
            return true;
         }
      }

      return false;
   }

   @InlineOnly
   private static final int count(@NotNull CharSequence var0) {
      byte var1 = 0;
      return var0.length();
   }

   public static final int count(@NotNull CharSequence var0, @NotNull Function1 var1) {
      byte var2 = 0;
      int var3 = 0;
      CharSequence var6 = var0;

      for(int var5 = 0; var5 < var6.length(); ++var5) {
         char var4 = var6.charAt(var5);
         if ((Boolean)var1.invoke(var4)) {
            ++var3;
         }
      }

      return var3;
   }

   public static final Object fold(@NotNull CharSequence var0, Object var1, @NotNull Function2 var2) {
      byte var3 = 0;
      Object var4 = var1;
      CharSequence var7 = var0;

      for(int var6 = 0; var6 < var7.length(); ++var6) {
         char var5 = var7.charAt(var6);
         var4 = var2.invoke(var4, var5);
      }

      return var4;
   }

   public static final Object foldIndexed(@NotNull CharSequence var0, Object var1, @NotNull Function3 var2) {
      byte var3 = 0;
      int var4 = 0;
      Object var5 = var1;
      CharSequence var8 = var0;

      for(int var7 = 0; var7 < var8.length(); ++var7) {
         char var6 = var8.charAt(var7);
         Integer var10001 = var4;
         ++var4;
         var5 = var2.invoke(var10001, var5, var6);
      }

      return var5;
   }

   public static final Object foldRight(@NotNull CharSequence var0, Object var1, @NotNull Function2 var2) {
      byte var3 = 0;
      int var4 = StringsKt.getLastIndex(var0);

      Object var5;
      for(var5 = var1; var4 >= 0; var5 = var2.invoke(var0.charAt(var4--), var5)) {
      }

      return var5;
   }

   public static final Object foldRightIndexed(@NotNull CharSequence var0, Object var1, @NotNull Function3 var2) {
      byte var3 = 0;
      int var4 = StringsKt.getLastIndex(var0);

      Object var5;
      for(var5 = var1; var4 >= 0; --var4) {
         var5 = var2.invoke(var4, var0.charAt(var4), var5);
      }

      return var5;
   }

   public static final void forEach(@NotNull CharSequence var0, @NotNull Function1 var1) {
      byte var2 = 0;
      CharSequence var5 = var0;

      for(int var4 = 0; var4 < var5.length(); ++var4) {
         char var3 = var5.charAt(var4);
         var1.invoke(var3);
      }

   }

   public static final void forEachIndexed(@NotNull CharSequence var0, @NotNull Function2 var1) {
      byte var2 = 0;
      int var3 = 0;
      CharSequence var6 = var0;

      for(int var5 = 0; var5 < var6.length(); ++var5) {
         char var4 = var6.charAt(var5);
         Integer var10001 = var3;
         ++var3;
         var1.invoke(var10001, var4);
      }

   }

   @Nullable
   public static final Character max(@NotNull CharSequence var0) {
      boolean var2 = false;
      if (var0.length() == 0) {
         return null;
      } else {
         char var1 = var0.charAt(0);
         int var5 = 1;
         int var3 = StringsKt.getLastIndex(var0);
         if (var5 <= var3) {
            while(true) {
               char var4 = var0.charAt(var5);
               if (var1 < var4) {
                  var1 = var4;
               }

               if (var5 == var3) {
                  break;
               }

               ++var5;
            }
         }

         return var1;
      }
   }

   @Nullable
   public static final Character maxBy(@NotNull CharSequence var0, @NotNull Function1 var1) {
      byte var2 = 0;
      boolean var4 = false;
      if (var0.length() == 0) {
         return null;
      } else {
         char var3 = var0.charAt(0);
         int var10 = StringsKt.getLastIndex(var0);
         if (var10 == 0) {
            return var3;
         } else {
            Comparable var5 = (Comparable)var1.invoke(var3);
            int var6 = 1;
            int var7 = var10;
            if (var6 <= var10) {
               while(true) {
                  char var8 = var0.charAt(var6);
                  Comparable var9 = (Comparable)var1.invoke(var8);
                  if (var5.compareTo(var9) < 0) {
                     var3 = var8;
                     var5 = var9;
                  }

                  if (var6 == var7) {
                     break;
                  }

                  ++var6;
               }
            }

            return var3;
         }
      }
   }

   @Nullable
   public static final Character maxWith(@NotNull CharSequence var0, @NotNull Comparator var1) {
      boolean var3 = false;
      if (var0.length() == 0) {
         return null;
      } else {
         char var2 = var0.charAt(0);
         int var6 = 1;
         int var4 = StringsKt.getLastIndex(var0);
         if (var6 <= var4) {
            while(true) {
               char var5 = var0.charAt(var6);
               if (var1.compare(var2, var5) < 0) {
                  var2 = var5;
               }

               if (var6 == var4) {
                  break;
               }

               ++var6;
            }
         }

         return var2;
      }
   }

   @Nullable
   public static final Character min(@NotNull CharSequence var0) {
      boolean var2 = false;
      if (var0.length() == 0) {
         return null;
      } else {
         char var1 = var0.charAt(0);
         int var5 = 1;
         int var3 = StringsKt.getLastIndex(var0);
         if (var5 <= var3) {
            while(true) {
               char var4 = var0.charAt(var5);
               if (var1 > var4) {
                  var1 = var4;
               }

               if (var5 == var3) {
                  break;
               }

               ++var5;
            }
         }

         return var1;
      }
   }

   @Nullable
   public static final Character minBy(@NotNull CharSequence var0, @NotNull Function1 var1) {
      byte var2 = 0;
      boolean var4 = false;
      if (var0.length() == 0) {
         return null;
      } else {
         char var3 = var0.charAt(0);
         int var10 = StringsKt.getLastIndex(var0);
         if (var10 == 0) {
            return var3;
         } else {
            Comparable var5 = (Comparable)var1.invoke(var3);
            int var6 = 1;
            int var7 = var10;
            if (var6 <= var10) {
               while(true) {
                  char var8 = var0.charAt(var6);
                  Comparable var9 = (Comparable)var1.invoke(var8);
                  if (var5.compareTo(var9) > 0) {
                     var3 = var8;
                     var5 = var9;
                  }

                  if (var6 == var7) {
                     break;
                  }

                  ++var6;
               }
            }

            return var3;
         }
      }
   }

   @Nullable
   public static final Character minWith(@NotNull CharSequence var0, @NotNull Comparator var1) {
      boolean var3 = false;
      if (var0.length() == 0) {
         return null;
      } else {
         char var2 = var0.charAt(0);
         int var6 = 1;
         int var4 = StringsKt.getLastIndex(var0);
         if (var6 <= var4) {
            while(true) {
               char var5 = var0.charAt(var6);
               if (var1.compare(var2, var5) > 0) {
                  var2 = var5;
               }

               if (var6 == var4) {
                  break;
               }

               ++var6;
            }
         }

         return var2;
      }
   }

   public static final boolean none(@NotNull CharSequence var0) {
      boolean var2 = false;
      return var0.length() == 0;
   }

   public static final boolean none(@NotNull CharSequence var0, @NotNull Function1 var1) {
      byte var2 = 0;
      CharSequence var5 = var0;

      for(int var4 = 0; var4 < var5.length(); ++var4) {
         char var3 = var5.charAt(var4);
         if ((Boolean)var1.invoke(var3)) {
            return false;
         }
      }

      return true;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final CharSequence onEach(@NotNull CharSequence var0, @NotNull Function1 var1) {
      byte var2 = 0;
      boolean var4 = false;
      boolean var5 = false;
      boolean var7 = false;
      CharSequence var8 = var0;

      for(int var9 = 0; var9 < var8.length(); ++var9) {
         char var10 = var8.charAt(var9);
         var1.invoke(var10);
      }

      return var0;
   }

   public static final char reduce(@NotNull CharSequence var0, @NotNull Function2 var1) {
      byte var2 = 0;
      boolean var4 = false;
      if (var0.length() == 0) {
         throw (Throwable)(new UnsupportedOperationException("Empty char sequence can't be reduced."));
      } else {
         char var3 = var0.charAt(0);
         int var6 = 1;
         int var5 = StringsKt.getLastIndex(var0);
         if (var6 <= var5) {
            while(true) {
               var3 = (Character)var1.invoke(var3, var0.charAt(var6));
               if (var6 == var5) {
                  break;
               }

               ++var6;
            }
         }

         return var3;
      }
   }

   public static final char reduceIndexed(@NotNull CharSequence var0, @NotNull Function3 var1) {
      byte var2 = 0;
      boolean var4 = false;
      if (var0.length() == 0) {
         throw (Throwable)(new UnsupportedOperationException("Empty char sequence can't be reduced."));
      } else {
         char var3 = var0.charAt(0);
         int var6 = 1;
         int var5 = StringsKt.getLastIndex(var0);
         if (var6 <= var5) {
            while(true) {
               var3 = (Character)var1.invoke(var6, var3, var0.charAt(var6));
               if (var6 == var5) {
                  break;
               }

               ++var6;
            }
         }

         return var3;
      }
   }

   public static final char reduceRight(@NotNull CharSequence var0, @NotNull Function2 var1) {
      byte var2 = 0;
      int var3 = StringsKt.getLastIndex(var0);
      if (var3 < 0) {
         throw (Throwable)(new UnsupportedOperationException("Empty char sequence can't be reduced."));
      } else {
         char var4;
         for(var4 = var0.charAt(var3--); var3 >= 0; var4 = (Character)var1.invoke(var0.charAt(var3--), var4)) {
         }

         return var4;
      }
   }

   public static final char reduceRightIndexed(@NotNull CharSequence var0, @NotNull Function3 var1) {
      byte var2 = 0;
      int var3 = StringsKt.getLastIndex(var0);
      if (var3 < 0) {
         throw (Throwable)(new UnsupportedOperationException("Empty char sequence can't be reduced."));
      } else {
         char var4;
         for(var4 = var0.charAt(var3--); var3 >= 0; --var3) {
            var4 = (Character)var1.invoke(var3, var0.charAt(var3), var4);
         }

         return var4;
      }
   }

   public static final int sumBy(@NotNull CharSequence var0, @NotNull Function1 var1) {
      byte var2 = 0;
      int var3 = 0;
      CharSequence var6 = var0;

      for(int var5 = 0; var5 < var6.length(); ++var5) {
         char var4 = var6.charAt(var5);
         var3 += ((Number)var1.invoke(var4)).intValue();
      }

      return var3;
   }

   public static final double sumByDouble(@NotNull CharSequence var0, @NotNull Function1 var1) {
      byte var2 = 0;
      double var3 = 0.0D;
      CharSequence var7 = var0;

      for(int var6 = 0; var6 < var7.length(); ++var6) {
         char var5 = var7.charAt(var6);
         var3 += ((Number)var1.invoke(var5)).doubleValue();
      }

      return var3;
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final List chunked(@NotNull CharSequence var0, int var1) {
      return StringsKt.windowed(var0, var1, var1, true);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final List chunked(@NotNull CharSequence var0, int var1, @NotNull Function1 var2) {
      return StringsKt.windowed(var0, var1, var1, true, var2);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final Sequence chunkedSequence(@NotNull CharSequence var0, int var1) {
      return StringsKt.chunkedSequence(var0, var1, (Function1)StringsKt___StringsKt$chunkedSequence$1.INSTANCE);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final Sequence chunkedSequence(@NotNull CharSequence var0, int var1, @NotNull Function1 var2) {
      return StringsKt.windowedSequence(var0, var1, var1, true, var2);
   }

   @NotNull
   public static final Pair partition(@NotNull CharSequence var0, @NotNull Function1 var1) {
      byte var2 = 0;
      StringBuilder var3 = new StringBuilder();
      StringBuilder var4 = new StringBuilder();
      CharSequence var7 = var0;

      for(int var6 = 0; var6 < var7.length(); ++var6) {
         char var5 = var7.charAt(var6);
         if ((Boolean)var1.invoke(var5)) {
            var3.append(var5);
         } else {
            var4.append(var5);
         }
      }

      return new Pair(var3, var4);
   }

   @NotNull
   public static final Pair partition(@NotNull String var0, @NotNull Function1 var1) {
      byte var2 = 0;
      StringBuilder var3 = new StringBuilder();
      StringBuilder var4 = new StringBuilder();
      String var7 = var0;
      int var8 = var0.length();

      for(int var6 = 0; var6 < var8; ++var6) {
         char var5 = var7.charAt(var6);
         if ((Boolean)var1.invoke(var5)) {
            var3.append(var5);
         } else {
            var4.append(var5);
         }
      }

      return new Pair(var3.toString(), var4.toString());
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final List windowed(@NotNull CharSequence var0, int var1, int var2, boolean var3) {
      return StringsKt.windowed(var0, var1, var2, var3, (Function1)StringsKt___StringsKt$windowed$1.INSTANCE);
   }

   public static List windowed$default(CharSequence var0, int var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 1;
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.windowed(var0, var1, var2, var3);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final List windowed(@NotNull CharSequence var0, int var1, int var2, boolean var3, @NotNull Function1 var4) {
      SlidingWindowKt.checkWindowSizeStep(var1, var2);
      int var5 = var0.length();
      ArrayList var6 = new ArrayList((var5 + var2 - 1) / var2);

      for(int var7 = 0; var7 < var5; var7 += var2) {
         int var8 = var7 + var1;
         int var10000;
         if (var8 > var5) {
            if (!var3) {
               break;
            }

            var10000 = var5;
         } else {
            var10000 = var8;
         }

         int var9 = var10000;
         var6.add(var4.invoke(var0.subSequence(var7, var9)));
      }

      return (List)var6;
   }

   public static List windowed$default(CharSequence var0, int var1, int var2, boolean var3, Function1 var4, int var5, Object var6) {
      if ((var5 & 2) != 0) {
         var2 = 1;
      }

      if ((var5 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.windowed(var0, var1, var2, var3, var4);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final Sequence windowedSequence(@NotNull CharSequence var0, int var1, int var2, boolean var3) {
      return StringsKt.windowedSequence(var0, var1, var2, var3, (Function1)StringsKt___StringsKt$windowedSequence$1.INSTANCE);
   }

   public static Sequence windowedSequence$default(CharSequence var0, int var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 1;
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.windowedSequence(var0, var1, var2, var3);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final Sequence windowedSequence(@NotNull CharSequence var0, int var1, int var2, boolean var3, @NotNull Function1 var4) {
      SlidingWindowKt.checkWindowSizeStep(var1, var2);
      IntProgression var5 = RangesKt.step((IntProgression)(var3 ? StringsKt.getIndices(var0) : RangesKt.until(0, var0.length() - var1 + 1)), var2);
      return SequencesKt.map(CollectionsKt.asSequence((Iterable)var5), (Function1)(new StringsKt___StringsKt$windowedSequence$2(var0, var4, var1)));
   }

   public static Sequence windowedSequence$default(CharSequence var0, int var1, int var2, boolean var3, Function1 var4, int var5, Object var6) {
      if ((var5 & 2) != 0) {
         var2 = 1;
      }

      if ((var5 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.windowedSequence(var0, var1, var2, var3, var4);
   }

   @NotNull
   public static final List zip(@NotNull CharSequence var0, @NotNull CharSequence var1) {
      CharSequence var2 = var0;
      boolean var3 = false;
      int var4 = var0.length();
      int var5 = var1.length();
      boolean var6 = false;
      int var7 = Math.min(var4, var5);
      ArrayList var13 = new ArrayList(var7);
      var5 = 0;

      for(int var14 = var7; var5 < var14; ++var5) {
         char var10001 = var2.charAt(var5);
         char var8 = var1.charAt(var5);
         char var9 = var10001;
         boolean var10 = false;
         Pair var12 = TuplesKt.to(var9, var8);
         var13.add(var12);
      }

      return (List)var13;
   }

   @NotNull
   public static final List zip(@NotNull CharSequence var0, @NotNull CharSequence var1, @NotNull Function2 var2) {
      byte var3 = 0;
      int var5 = var0.length();
      int var6 = var1.length();
      boolean var7 = false;
      int var4 = Math.min(var5, var6);
      ArrayList var8 = new ArrayList(var4);
      var6 = 0;

      for(int var9 = var4; var6 < var9; ++var6) {
         var8.add(var2.invoke(var0.charAt(var6), var1.charAt(var6)));
      }

      return (List)var8;
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final List zipWithNext(@NotNull CharSequence var0) {
      CharSequence var1 = var0;
      boolean var2 = false;
      int var3 = var0.length() - 1;
      List var10000;
      if (var3 < 1) {
         var10000 = CollectionsKt.emptyList();
      } else {
         ArrayList var4 = new ArrayList(var3);
         int var5 = 0;

         for(int var6 = var3; var5 < var6; ++var5) {
            char var10001 = var1.charAt(var5);
            char var7 = var1.charAt(var5 + 1);
            char var8 = var10001;
            boolean var9 = false;
            Pair var11 = TuplesKt.to(var8, var7);
            var4.add(var11);
         }

         var10000 = (List)var4;
      }

      return var10000;
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final List zipWithNext(@NotNull CharSequence var0, @NotNull Function2 var1) {
      byte var2 = 0;
      int var3 = var0.length() - 1;
      if (var3 < 1) {
         return CollectionsKt.emptyList();
      } else {
         ArrayList var4 = new ArrayList(var3);
         int var5 = 0;

         for(int var6 = var3; var5 < var6; ++var5) {
            var4.add(var1.invoke(var0.charAt(var5), var0.charAt(var5 + 1)));
         }

         return (List)var4;
      }
   }

   @NotNull
   public static final Iterable asIterable(@NotNull CharSequence var0) {
      if (var0 instanceof String) {
         boolean var2 = false;
         if (var0.length() == 0) {
            return (Iterable)CollectionsKt.emptyList();
         }
      }

      boolean var1 = false;
      return (Iterable)(new StringsKt___StringsKt$asIterable$$inlined$Iterable$1(var0));
   }

   @NotNull
   public static final Sequence asSequence(@NotNull CharSequence var0) {
      if (var0 instanceof String) {
         boolean var2 = false;
         if (var0.length() == 0) {
            return SequencesKt.emptySequence();
         }
      }

      boolean var1 = false;
      return (Sequence)(new StringsKt___StringsKt$asSequence$$inlined$Sequence$1(var0));
   }

   public StringsKt___StringsKt() {
      super();
   }
}
