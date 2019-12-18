package kotlin.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ReplaceWith;
import kotlin.SinceKotlin;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CharIterator;
import kotlin.collections.CollectionsKt;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
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
   d1 = {"\u0000|\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\r\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\f\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0019\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\b\n\u0002\u0010\u0011\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u001b\u001a\u001c\u0010\t\u001a\u00020\n*\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u00022\b\b\u0002\u0010\f\u001a\u00020\r\u001a\u001c\u0010\u000e\u001a\u00020\n*\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u00022\b\b\u0002\u0010\f\u001a\u00020\r\u001a\u001f\u0010\u000f\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\f\u001a\u00020\rH\u0086\u0002\u001a\u001f\u0010\u000f\u001a\u00020\r*\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u00022\b\b\u0002\u0010\f\u001a\u00020\rH\u0086\u0002\u001a\u0015\u0010\u000f\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u0013H\u0087\n\u001a\u001c\u0010\u0014\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\f\u001a\u00020\r\u001a\u001c\u0010\u0014\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00022\b\b\u0002\u0010\f\u001a\u00020\r\u001a:\u0010\u0016\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\n\u0018\u00010\u0017*\u00020\u00022\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\n0\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001aE\u0010\u0016\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\n\u0018\u00010\u0017*\u00020\u00022\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\n0\u00192\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\rH\u0002¢\u0006\u0002\b\u001c\u001a:\u0010\u001d\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\n\u0018\u00010\u0017*\u00020\u00022\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\n0\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a\u0012\u0010\u001e\u001a\u00020\r*\u00020\u00022\u0006\u0010\u001f\u001a\u00020\u0006\u001a4\u0010 \u001a\u0002H!\"\f\b\u0000\u0010\"*\u00020\u0002*\u0002H!\"\u0004\b\u0001\u0010!*\u0002H\"2\f\u0010#\u001a\b\u0012\u0004\u0012\u0002H!0$H\u0087\b¢\u0006\u0002\u0010%\u001a4\u0010&\u001a\u0002H!\"\f\b\u0000\u0010\"*\u00020\u0002*\u0002H!\"\u0004\b\u0001\u0010!*\u0002H\"2\f\u0010#\u001a\b\u0012\u0004\u0012\u0002H!0$H\u0087\b¢\u0006\u0002\u0010%\u001a&\u0010'\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a;\u0010'\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010(\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u001b\u001a\u00020\rH\u0002¢\u0006\u0002\b)\u001a&\u0010'\u001a\u00020\u0006*\u00020\u00022\u0006\u0010*\u001a\u00020\n2\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a&\u0010+\u001a\u00020\u0006*\u00020\u00022\u0006\u0010,\u001a\u00020-2\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a,\u0010+\u001a\u00020\u0006*\u00020\u00022\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\n0\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a\r\u0010.\u001a\u00020\r*\u00020\u0002H\u0087\b\u001a\r\u0010/\u001a\u00020\r*\u00020\u0002H\u0087\b\u001a\r\u00100\u001a\u00020\r*\u00020\u0002H\u0087\b\u001a \u00101\u001a\u00020\r*\u0004\u0018\u00010\u0002H\u0087\b\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000\u001a \u00102\u001a\u00020\r*\u0004\u0018\u00010\u0002H\u0087\b\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000\u001a\r\u00103\u001a\u000204*\u00020\u0002H\u0086\u0002\u001a&\u00105\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a&\u00105\u001a\u00020\u0006*\u00020\u00022\u0006\u0010*\u001a\u00020\n2\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a&\u00106\u001a\u00020\u0006*\u00020\u00022\u0006\u0010,\u001a\u00020-2\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a,\u00106\u001a\u00020\u0006*\u00020\u00022\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\n0\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a\u0010\u00107\u001a\b\u0012\u0004\u0012\u00020\n08*\u00020\u0002\u001a\u0010\u00109\u001a\b\u0012\u0004\u0012\u00020\n0:*\u00020\u0002\u001a\u0015\u0010;\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u0013H\u0087\f\u001a\u000f\u0010<\u001a\u00020\n*\u0004\u0018\u00010\nH\u0087\b\u001a\u001c\u0010=\u001a\u00020\u0002*\u00020\u00022\u0006\u0010>\u001a\u00020\u00062\b\b\u0002\u0010?\u001a\u00020\u0011\u001a\u001c\u0010=\u001a\u00020\n*\u00020\n2\u0006\u0010>\u001a\u00020\u00062\b\b\u0002\u0010?\u001a\u00020\u0011\u001a\u001c\u0010@\u001a\u00020\u0002*\u00020\u00022\u0006\u0010>\u001a\u00020\u00062\b\b\u0002\u0010?\u001a\u00020\u0011\u001a\u001c\u0010@\u001a\u00020\n*\u00020\n2\u0006\u0010>\u001a\u00020\u00062\b\b\u0002\u0010?\u001a\u00020\u0011\u001aG\u0010A\u001a\b\u0012\u0004\u0012\u00020\u000108*\u00020\u00022\u000e\u0010B\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0C2\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010D\u001a\u00020\u0006H\u0002¢\u0006\u0004\bE\u0010F\u001a=\u0010A\u001a\b\u0012\u0004\u0012\u00020\u000108*\u00020\u00022\u0006\u0010B\u001a\u00020-2\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010D\u001a\u00020\u0006H\u0002¢\u0006\u0002\bE\u001a4\u0010G\u001a\u00020\r*\u00020\u00022\u0006\u0010H\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00022\u0006\u0010I\u001a\u00020\u00062\u0006\u0010>\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\rH\u0000\u001a\u0012\u0010J\u001a\u00020\u0002*\u00020\u00022\u0006\u0010K\u001a\u00020\u0002\u001a\u0012\u0010J\u001a\u00020\n*\u00020\n2\u0006\u0010K\u001a\u00020\u0002\u001a\u001a\u0010L\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010(\u001a\u00020\u0006\u001a\u0012\u0010L\u001a\u00020\u0002*\u00020\u00022\u0006\u0010M\u001a\u00020\u0001\u001a\u001d\u0010L\u001a\u00020\n*\u00020\n2\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010(\u001a\u00020\u0006H\u0087\b\u001a\u0015\u0010L\u001a\u00020\n*\u00020\n2\u0006\u0010M\u001a\u00020\u0001H\u0087\b\u001a\u0012\u0010N\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0002\u001a\u0012\u0010N\u001a\u00020\n*\u00020\n2\u0006\u0010\u0015\u001a\u00020\u0002\u001a\u0012\u0010O\u001a\u00020\u0002*\u00020\u00022\u0006\u0010P\u001a\u00020\u0002\u001a\u001a\u0010O\u001a\u00020\u0002*\u00020\u00022\u0006\u0010K\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0002\u001a\u0012\u0010O\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\u0002\u001a\u001a\u0010O\u001a\u00020\n*\u00020\n2\u0006\u0010K\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0002\u001a+\u0010Q\u001a\u00020\n*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\u0014\b\b\u0010R\u001a\u000e\u0012\u0004\u0012\u00020T\u0012\u0004\u0012\u00020\u00020SH\u0087\b\u001a\u001d\u0010Q\u001a\u00020\n*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010U\u001a\u00020\nH\u0087\b\u001a$\u0010V\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\u00112\u0006\u0010U\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a$\u0010V\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\n2\u0006\u0010U\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a$\u0010X\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\u00112\u0006\u0010U\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a$\u0010X\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\n2\u0006\u0010U\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a$\u0010Y\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\u00112\u0006\u0010U\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a$\u0010Y\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\n2\u0006\u0010U\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a$\u0010Z\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\u00112\u0006\u0010U\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a$\u0010Z\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\n2\u0006\u0010U\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a\u001d\u0010[\u001a\u00020\n*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010U\u001a\u00020\nH\u0087\b\u001a\"\u0010\\\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010(\u001a\u00020\u00062\u0006\u0010U\u001a\u00020\u0002\u001a\u001a\u0010\\\u001a\u00020\u0002*\u00020\u00022\u0006\u0010M\u001a\u00020\u00012\u0006\u0010U\u001a\u00020\u0002\u001a%\u0010\\\u001a\u00020\n*\u00020\n2\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010(\u001a\u00020\u00062\u0006\u0010U\u001a\u00020\u0002H\u0087\b\u001a\u001d\u0010\\\u001a\u00020\n*\u00020\n2\u0006\u0010M\u001a\u00020\u00012\u0006\u0010U\u001a\u00020\u0002H\u0087\b\u001a=\u0010]\u001a\b\u0012\u0004\u0012\u00020\n0:*\u00020\u00022\u0012\u0010B\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0C\"\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010D\u001a\u00020\u0006¢\u0006\u0002\u0010^\u001a0\u0010]\u001a\b\u0012\u0004\u0012\u00020\n0:*\u00020\u00022\n\u0010B\u001a\u00020-\"\u00020\u00112\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010D\u001a\u00020\u0006\u001a/\u0010]\u001a\b\u0012\u0004\u0012\u00020\n0:*\u00020\u00022\u0006\u0010P\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010D\u001a\u00020\u0006H\u0002¢\u0006\u0002\b_\u001a%\u0010]\u001a\b\u0012\u0004\u0012\u00020\n0:*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010D\u001a\u00020\u0006H\u0087\b\u001a=\u0010`\u001a\b\u0012\u0004\u0012\u00020\n08*\u00020\u00022\u0012\u0010B\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0C\"\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010D\u001a\u00020\u0006¢\u0006\u0002\u0010a\u001a0\u0010`\u001a\b\u0012\u0004\u0012\u00020\n08*\u00020\u00022\n\u0010B\u001a\u00020-\"\u00020\u00112\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010D\u001a\u00020\u0006\u001a\u001c\u0010b\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\f\u001a\u00020\r\u001a\u001c\u0010b\u001a\u00020\r*\u00020\u00022\u0006\u0010K\u001a\u00020\u00022\b\b\u0002\u0010\f\u001a\u00020\r\u001a$\u0010b\u001a\u00020\r*\u00020\u00022\u0006\u0010K\u001a\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a\u0012\u0010c\u001a\u00020\u0002*\u00020\u00022\u0006\u0010M\u001a\u00020\u0001\u001a\u001d\u0010c\u001a\u00020\u0002*\u00020\n2\u0006\u0010d\u001a\u00020\u00062\u0006\u0010e\u001a\u00020\u0006H\u0087\b\u001a\u001f\u0010f\u001a\u00020\n*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010(\u001a\u00020\u0006H\u0087\b\u001a\u0012\u0010f\u001a\u00020\n*\u00020\u00022\u0006\u0010M\u001a\u00020\u0001\u001a\u0012\u0010f\u001a\u00020\n*\u00020\n2\u0006\u0010M\u001a\u00020\u0001\u001a\u001c\u0010g\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\u00112\b\b\u0002\u0010W\u001a\u00020\n\u001a\u001c\u0010g\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a\u001c\u0010h\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\u00112\b\b\u0002\u0010W\u001a\u00020\n\u001a\u001c\u0010h\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a\u001c\u0010i\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\u00112\b\b\u0002\u0010W\u001a\u00020\n\u001a\u001c\u0010i\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a\u001c\u0010j\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\u00112\b\b\u0002\u0010W\u001a\u00020\n\u001a\u001c\u0010j\u001a\u00020\n*\u00020\n2\u0006\u0010P\u001a\u00020\n2\b\b\u0002\u0010W\u001a\u00020\n\u001a\n\u0010k\u001a\u00020\u0002*\u00020\u0002\u001a!\u0010k\u001a\u00020\u0002*\u00020\u00022\u0012\u0010l\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\r0SH\u0086\b\u001a\u0016\u0010k\u001a\u00020\u0002*\u00020\u00022\n\u0010,\u001a\u00020-\"\u00020\u0011\u001a\r\u0010k\u001a\u00020\n*\u00020\nH\u0087\b\u001a!\u0010k\u001a\u00020\n*\u00020\n2\u0012\u0010l\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\r0SH\u0086\b\u001a\u0016\u0010k\u001a\u00020\n*\u00020\n2\n\u0010,\u001a\u00020-\"\u00020\u0011\u001a\n\u0010m\u001a\u00020\u0002*\u00020\u0002\u001a!\u0010m\u001a\u00020\u0002*\u00020\u00022\u0012\u0010l\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\r0SH\u0086\b\u001a\u0016\u0010m\u001a\u00020\u0002*\u00020\u00022\n\u0010,\u001a\u00020-\"\u00020\u0011\u001a\r\u0010m\u001a\u00020\n*\u00020\nH\u0087\b\u001a!\u0010m\u001a\u00020\n*\u00020\n2\u0012\u0010l\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\r0SH\u0086\b\u001a\u0016\u0010m\u001a\u00020\n*\u00020\n2\n\u0010,\u001a\u00020-\"\u00020\u0011\u001a\n\u0010n\u001a\u00020\u0002*\u00020\u0002\u001a!\u0010n\u001a\u00020\u0002*\u00020\u00022\u0012\u0010l\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\r0SH\u0086\b\u001a\u0016\u0010n\u001a\u00020\u0002*\u00020\u00022\n\u0010,\u001a\u00020-\"\u00020\u0011\u001a\r\u0010n\u001a\u00020\n*\u00020\nH\u0087\b\u001a!\u0010n\u001a\u00020\n*\u00020\n2\u0012\u0010l\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\r0SH\u0086\b\u001a\u0016\u0010n\u001a\u00020\n*\u00020\n2\n\u0010,\u001a\u00020-\"\u00020\u0011\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006o"},
   d2 = {"indices", "Lkotlin/ranges/IntRange;", "", "getIndices", "(Ljava/lang/CharSequence;)Lkotlin/ranges/IntRange;", "lastIndex", "", "getLastIndex", "(Ljava/lang/CharSequence;)I", "commonPrefixWith", "", "other", "ignoreCase", "", "commonSuffixWith", "contains", "char", "", "regex", "Lkotlin/text/Regex;", "endsWith", "suffix", "findAnyOf", "Lkotlin/Pair;", "strings", "", "startIndex", "last", "findAnyOf$StringsKt__StringsKt", "findLastAnyOf", "hasSurrogatePairAt", "index", "ifBlank", "R", "C", "defaultValue", "Lkotlin/Function0;", "(Ljava/lang/CharSequence;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "ifEmpty", "indexOf", "endIndex", "indexOf$StringsKt__StringsKt", "string", "indexOfAny", "chars", "", "isEmpty", "isNotBlank", "isNotEmpty", "isNullOrBlank", "isNullOrEmpty", "iterator", "Lkotlin/collections/CharIterator;", "lastIndexOf", "lastIndexOfAny", "lineSequence", "Lkotlin/sequences/Sequence;", "lines", "", "matches", "orEmpty", "padEnd", "length", "padChar", "padStart", "rangesDelimitedBy", "delimiters", "", "limit", "rangesDelimitedBy$StringsKt__StringsKt", "(Ljava/lang/CharSequence;[Ljava/lang/String;IZI)Lkotlin/sequences/Sequence;", "regionMatchesImpl", "thisOffset", "otherOffset", "removePrefix", "prefix", "removeRange", "range", "removeSuffix", "removeSurrounding", "delimiter", "replace", "transform", "Lkotlin/Function1;", "Lkotlin/text/MatchResult;", "replacement", "replaceAfter", "missingDelimiterValue", "replaceAfterLast", "replaceBefore", "replaceBeforeLast", "replaceFirst", "replaceRange", "split", "(Ljava/lang/CharSequence;[Ljava/lang/String;ZI)Ljava/util/List;", "split$StringsKt__StringsKt", "splitToSequence", "(Ljava/lang/CharSequence;[Ljava/lang/String;ZI)Lkotlin/sequences/Sequence;", "startsWith", "subSequence", "start", "end", "substring", "substringAfter", "substringAfterLast", "substringBefore", "substringBeforeLast", "trim", "predicate", "trimEnd", "trimStart", "kotlin-stdlib"},
   xs = "kotlin/text/StringsKt"
)
class StringsKt__StringsKt extends StringsKt__StringsJVMKt {
   @NotNull
   public static final CharSequence trim(@NotNull CharSequence var0, @NotNull Function1 var1) {
      byte var2 = 0;
      int var3 = 0;
      int var4 = var0.length() - 1;
      boolean var5 = false;

      while(var3 <= var4) {
         int var6 = !var5 ? var3 : var4;
         boolean var7 = (Boolean)var1.invoke(var0.charAt(var6));
         if (!var5) {
            if (!var7) {
               var5 = true;
            } else {
               ++var3;
            }
         } else {
            if (!var7) {
               break;
            }

            --var4;
         }
      }

      return var0.subSequence(var3, var4 + 1);
   }

   @NotNull
   public static final String trim(@NotNull String var0, @NotNull Function1 var1) {
      byte var2 = 0;
      CharSequence var3 = (CharSequence)var0;
      boolean var4 = false;
      int var5 = 0;
      int var6 = var3.length() - 1;
      boolean var7 = false;

      while(var5 <= var6) {
         int var8 = !var7 ? var5 : var6;
         boolean var9 = (Boolean)var1.invoke(var3.charAt(var8));
         if (!var7) {
            if (!var9) {
               var7 = true;
            } else {
               ++var5;
            }
         } else {
            if (!var9) {
               break;
            }

            --var6;
         }
      }

      return var3.subSequence(var5, var6 + 1).toString();
   }

   @NotNull
   public static final CharSequence trimStart(@NotNull CharSequence var0, @NotNull Function1 var1) {
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
   public static final String trimStart(@NotNull String var0, @NotNull Function1 var1) {
      byte var2 = 0;
      CharSequence var3 = (CharSequence)var0;
      boolean var4 = false;
      int var5 = 0;
      int var6 = var3.length();

      CharSequence var10000;
      while(true) {
         if (var5 >= var6) {
            var10000 = (CharSequence)"";
            break;
         }

         if (!(Boolean)var1.invoke(var3.charAt(var5))) {
            var10000 = var3.subSequence(var5, var3.length());
            break;
         }

         ++var5;
      }

      return var10000.toString();
   }

   @NotNull
   public static final CharSequence trimEnd(@NotNull CharSequence var0, @NotNull Function1 var1) {
      byte var2 = 0;
      int var3 = var0.length();
      --var3;

      for(boolean var4 = false; var3 >= 0; --var3) {
         if (!(Boolean)var1.invoke(var0.charAt(var3))) {
            return var0.subSequence(0, var3 + 1);
         }
      }

      return (CharSequence)"";
   }

   @NotNull
   public static final String trimEnd(@NotNull String var0, @NotNull Function1 var1) {
      byte var2 = 0;
      CharSequence var3 = (CharSequence)var0;
      boolean var4 = false;
      int var5 = var3.length();
      --var5;
      boolean var6 = false;

      CharSequence var10000;
      while(true) {
         if (var5 < 0) {
            var10000 = (CharSequence)"";
            break;
         }

         if (!(Boolean)var1.invoke(var3.charAt(var5))) {
            var10000 = var3.subSequence(0, var5 + 1);
            break;
         }

         --var5;
      }

      return var10000.toString();
   }

   @NotNull
   public static final CharSequence trim(@NotNull CharSequence var0, @NotNull char... var1) {
      CharSequence var2 = var0;
      boolean var3 = false;
      int var4 = 0;
      int var5 = var0.length() - 1;
      boolean var6 = false;

      while(var4 <= var5) {
         int var7 = !var6 ? var4 : var5;
         char var8 = var2.charAt(var7);
         boolean var9 = false;
         boolean var10 = ArraysKt.contains(var1, var8);
         if (!var6) {
            if (!var10) {
               var6 = true;
            } else {
               ++var4;
            }
         } else {
            if (!var10) {
               break;
            }

            --var5;
         }
      }

      return var2.subSequence(var4, var5 + 1);
   }

   @NotNull
   public static final String trim(@NotNull String var0, @NotNull char... var1) {
      boolean var3 = false;
      CharSequence var4 = (CharSequence)var0;
      boolean var5 = false;
      int var6 = 0;
      int var7 = var4.length() - 1;
      boolean var8 = false;

      while(var6 <= var7) {
         int var9 = !var8 ? var6 : var7;
         char var10 = var4.charAt(var9);
         boolean var11 = false;
         boolean var12 = ArraysKt.contains(var1, var10);
         if (!var8) {
            if (!var12) {
               var8 = true;
            } else {
               ++var6;
            }
         } else {
            if (!var12) {
               break;
            }

            --var7;
         }
      }

      return var4.subSequence(var6, var7 + 1).toString();
   }

   @NotNull
   public static final CharSequence trimStart(@NotNull CharSequence var0, @NotNull char... var1) {
      CharSequence var2 = var0;
      boolean var3 = false;
      int var4 = 0;
      int var5 = var0.length();

      CharSequence var10000;
      while(true) {
         if (var4 >= var5) {
            var10000 = (CharSequence)"";
            break;
         }

         char var6 = var2.charAt(var4);
         boolean var7 = false;
         if (!ArraysKt.contains(var1, var6)) {
            var10000 = var2.subSequence(var4, var2.length());
            break;
         }

         ++var4;
      }

      return var10000;
   }

   @NotNull
   public static final String trimStart(@NotNull String var0, @NotNull char... var1) {
      boolean var3 = false;
      CharSequence var4 = (CharSequence)var0;
      boolean var5 = false;
      int var6 = 0;
      int var7 = var4.length();

      CharSequence var10000;
      while(true) {
         if (var6 >= var7) {
            var10000 = (CharSequence)"";
            break;
         }

         char var8 = var4.charAt(var6);
         boolean var9 = false;
         if (!ArraysKt.contains(var1, var8)) {
            var10000 = var4.subSequence(var6, var4.length());
            break;
         }

         ++var6;
      }

      return var10000.toString();
   }

   @NotNull
   public static final CharSequence trimEnd(@NotNull CharSequence var0, @NotNull char... var1) {
      CharSequence var2 = var0;
      boolean var3 = false;
      int var4 = var0.length();
      --var4;
      boolean var5 = false;

      CharSequence var10000;
      while(true) {
         if (var4 < 0) {
            var10000 = (CharSequence)"";
            break;
         }

         char var6 = var2.charAt(var4);
         boolean var7 = false;
         if (!ArraysKt.contains(var1, var6)) {
            var10000 = var2.subSequence(0, var4 + 1);
            break;
         }

         --var4;
      }

      return var10000;
   }

   @NotNull
   public static final String trimEnd(@NotNull String var0, @NotNull char... var1) {
      boolean var3 = false;
      CharSequence var4 = (CharSequence)var0;
      boolean var5 = false;
      int var6 = var4.length();
      --var6;
      boolean var7 = false;

      CharSequence var10000;
      while(true) {
         if (var6 < 0) {
            var10000 = (CharSequence)"";
            break;
         }

         char var8 = var4.charAt(var6);
         boolean var9 = false;
         if (!ArraysKt.contains(var1, var8)) {
            var10000 = var4.subSequence(0, var6 + 1);
            break;
         }

         --var6;
      }

      return var10000.toString();
   }

   @NotNull
   public static final CharSequence trim(@NotNull CharSequence var0) {
      CharSequence var1 = var0;
      boolean var2 = false;
      int var3 = 0;
      int var4 = var0.length() - 1;
      boolean var5 = false;

      while(var3 <= var4) {
         int var6 = !var5 ? var3 : var4;
         char var7 = var1.charAt(var6);
         boolean var8 = false;
         boolean var9 = CharsKt.isWhitespace(var7);
         if (!var5) {
            if (!var9) {
               var5 = true;
            } else {
               ++var3;
            }
         } else {
            if (!var9) {
               break;
            }

            --var4;
         }
      }

      return var1.subSequence(var3, var4 + 1);
   }

   @InlineOnly
   private static final String trim(@NotNull String var0) {
      return StringsKt.trim((CharSequence)var0).toString();
   }

   @NotNull
   public static final CharSequence trimStart(@NotNull CharSequence var0) {
      CharSequence var1 = var0;
      boolean var2 = false;
      int var3 = 0;
      int var4 = var0.length();

      CharSequence var10000;
      while(true) {
         if (var3 >= var4) {
            var10000 = (CharSequence)"";
            break;
         }

         char var5 = var1.charAt(var3);
         boolean var6 = false;
         if (!CharsKt.isWhitespace(var5)) {
            var10000 = var1.subSequence(var3, var1.length());
            break;
         }

         ++var3;
      }

      return var10000;
   }

   @InlineOnly
   private static final String trimStart(@NotNull String var0) {
      return StringsKt.trimStart((CharSequence)var0).toString();
   }

   @NotNull
   public static final CharSequence trimEnd(@NotNull CharSequence var0) {
      CharSequence var1 = var0;
      boolean var2 = false;
      int var3 = var0.length();
      --var3;
      boolean var4 = false;

      CharSequence var10000;
      while(true) {
         if (var3 < 0) {
            var10000 = (CharSequence)"";
            break;
         }

         char var5 = var1.charAt(var3);
         boolean var6 = false;
         if (!CharsKt.isWhitespace(var5)) {
            var10000 = var1.subSequence(0, var3 + 1);
            break;
         }

         --var3;
      }

      return var10000;
   }

   @InlineOnly
   private static final String trimEnd(@NotNull String var0) {
      return StringsKt.trimEnd((CharSequence)var0).toString();
   }

   @NotNull
   public static final CharSequence padStart(@NotNull CharSequence var0, int var1, char var2) {
      if (var1 < 0) {
         throw (Throwable)(new IllegalArgumentException("Desired length " + var1 + " is less than zero."));
      } else if (var1 <= var0.length()) {
         return var0.subSequence(0, var0.length());
      } else {
         StringBuilder var3 = new StringBuilder(var1);
         int var4 = 1;
         int var5 = var1 - var0.length();
         if (var4 <= var5) {
            while(true) {
               var3.append(var2);
               if (var4 == var5) {
                  break;
               }

               ++var4;
            }
         }

         var3.append(var0);
         return (CharSequence)var3;
      }
   }

   public static CharSequence padStart$default(CharSequence var0, int var1, char var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = ' ';
      }

      return StringsKt.padStart(var0, var1, var2);
   }

   @NotNull
   public static final String padStart(@NotNull String var0, int var1, char var2) {
      return StringsKt.padStart((CharSequence)var0, var1, var2).toString();
   }

   public static String padStart$default(String var0, int var1, char var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = ' ';
      }

      return StringsKt.padStart(var0, var1, var2);
   }

   @NotNull
   public static final CharSequence padEnd(@NotNull CharSequence var0, int var1, char var2) {
      if (var1 < 0) {
         throw (Throwable)(new IllegalArgumentException("Desired length " + var1 + " is less than zero."));
      } else if (var1 <= var0.length()) {
         return var0.subSequence(0, var0.length());
      } else {
         StringBuilder var3 = new StringBuilder(var1);
         var3.append(var0);
         int var4 = 1;
         int var5 = var1 - var0.length();
         if (var4 <= var5) {
            while(true) {
               var3.append(var2);
               if (var4 == var5) {
                  break;
               }

               ++var4;
            }
         }

         return (CharSequence)var3;
      }
   }

   public static CharSequence padEnd$default(CharSequence var0, int var1, char var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = ' ';
      }

      return StringsKt.padEnd(var0, var1, var2);
   }

   @NotNull
   public static final String padEnd(@NotNull String var0, int var1, char var2) {
      return StringsKt.padEnd((CharSequence)var0, var1, var2).toString();
   }

   public static String padEnd$default(String var0, int var1, char var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = ' ';
      }

      return StringsKt.padEnd(var0, var1, var2);
   }

   @InlineOnly
   private static final boolean isNullOrEmpty(@Nullable CharSequence var0) {
      byte var1 = 0;
      boolean var2 = false;
      return var0 == null || var0.length() == 0;
   }

   @InlineOnly
   private static final boolean isEmpty(@NotNull CharSequence var0) {
      byte var1 = 0;
      return var0.length() == 0;
   }

   @InlineOnly
   private static final boolean isNotEmpty(@NotNull CharSequence var0) {
      byte var1 = 0;
      return var0.length() > 0;
   }

   @InlineOnly
   private static final boolean isNotBlank(@NotNull CharSequence var0) {
      byte var1 = 0;
      return !StringsKt.isBlank(var0);
   }

   @InlineOnly
   private static final boolean isNullOrBlank(@Nullable CharSequence var0) {
      byte var1 = 0;
      boolean var2 = false;
      return var0 == null || StringsKt.isBlank(var0);
   }

   @NotNull
   public static final CharIterator iterator(@NotNull CharSequence var0) {
      return (CharIterator)(new StringsKt__StringsKt$iterator$1(var0));
   }

   @InlineOnly
   private static final String orEmpty(@Nullable String var0) {
      byte var1 = 0;
      String var10000 = var0;
      if (var0 == null) {
         var10000 = "";
      }

      return var10000;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final Object ifEmpty(CharSequence var0, Function0 var1) {
      byte var2 = 0;
      boolean var4 = false;
      return var0.length() == 0 ? var1.invoke() : var0;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final Object ifBlank(CharSequence var0, Function0 var1) {
      byte var2 = 0;
      return StringsKt.isBlank(var0) ? var1.invoke() : var0;
   }

   @NotNull
   public static final IntRange getIndices(@NotNull CharSequence var0) {
      byte var1 = 0;
      return new IntRange(var1, var0.length() - 1);
   }

   public static final int getLastIndex(@NotNull CharSequence var0) {
      return var0.length() - 1;
   }

   public static final boolean hasSurrogatePairAt(@NotNull CharSequence var0, int var1) {
      int var10000 = var0.length() - 2;
      boolean var4;
      if (0 <= var1) {
         if (var10000 >= var1) {
            char var2 = var0.charAt(var1);
            boolean var3 = false;
            if (Character.isHighSurrogate(var2)) {
               var2 = var0.charAt(var1 + 1);
               var3 = false;
               if (Character.isLowSurrogate(var2)) {
                  var4 = true;
                  return var4;
               }
            }
         }
      }

      var4 = false;
      return var4;
   }

   @NotNull
   public static final String substring(@NotNull String var0, @NotNull IntRange var1) {
      int var3 = var1.getStart();
      int var4 = var1.getEndInclusive() + 1;
      boolean var5 = false;
      return var0.substring(var3, var4);
   }

   @NotNull
   public static final CharSequence subSequence(@NotNull CharSequence var0, @NotNull IntRange var1) {
      return var0.subSequence(var1.getStart(), var1.getEndInclusive() + 1);
   }

   /** @deprecated */
   @Deprecated(
      message = "Use parameters named startIndex and endIndex.",
      replaceWith = @ReplaceWith(
   imports = {},
   expression = "subSequence(startIndex = start, endIndex = end)"
)
   )
   @InlineOnly
   private static final CharSequence subSequence(@NotNull String var0, int var1, int var2) {
      byte var3 = 0;
      return var0.subSequence(var1, var2);
   }

   @InlineOnly
   private static final String substring(@NotNull CharSequence var0, int var1, int var2) {
      byte var3 = 0;
      return var0.subSequence(var1, var2).toString();
   }

   static String substring$default(CharSequence var0, int var1, int var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = var0.length();
      }

      boolean var5 = false;
      return var0.subSequence(var1, var2).toString();
   }

   @NotNull
   public static final String substring(@NotNull CharSequence var0, @NotNull IntRange var1) {
      return var0.subSequence(var1.getStart(), var1.getEndInclusive() + 1).toString();
   }

   @NotNull
   public static final String substringBefore(@NotNull String var0, char var1, @NotNull String var2) {
      int var3 = StringsKt.indexOf$default((CharSequence)var0, var1, 0, false, 6, (Object)null);
      String var10000;
      if (var3 == -1) {
         var10000 = var2;
      } else {
         byte var5 = 0;
         boolean var6 = false;
         var10000 = var0.substring(var5, var3);
      }

      return var10000;
   }

   public static String substringBefore$default(String var0, char var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = var0;
      }

      return StringsKt.substringBefore(var0, var1, var2);
   }

   @NotNull
   public static final String substringBefore(@NotNull String var0, @NotNull String var1, @NotNull String var2) {
      int var3 = StringsKt.indexOf$default((CharSequence)var0, var1, 0, false, 6, (Object)null);
      String var10000;
      if (var3 == -1) {
         var10000 = var2;
      } else {
         byte var5 = 0;
         boolean var6 = false;
         var10000 = var0.substring(var5, var3);
      }

      return var10000;
   }

   public static String substringBefore$default(String var0, String var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = var0;
      }

      return StringsKt.substringBefore(var0, var1, var2);
   }

   @NotNull
   public static final String substringAfter(@NotNull String var0, char var1, @NotNull String var2) {
      int var3 = StringsKt.indexOf$default((CharSequence)var0, var1, 0, false, 6, (Object)null);
      String var10000;
      if (var3 == -1) {
         var10000 = var2;
      } else {
         int var5 = var3 + 1;
         int var6 = var0.length();
         boolean var7 = false;
         var10000 = var0.substring(var5, var6);
      }

      return var10000;
   }

   public static String substringAfter$default(String var0, char var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = var0;
      }

      return StringsKt.substringAfter(var0, var1, var2);
   }

   @NotNull
   public static final String substringAfter(@NotNull String var0, @NotNull String var1, @NotNull String var2) {
      int var3 = StringsKt.indexOf$default((CharSequence)var0, var1, 0, false, 6, (Object)null);
      String var10000;
      if (var3 == -1) {
         var10000 = var2;
      } else {
         int var5 = var3 + var1.length();
         int var6 = var0.length();
         boolean var7 = false;
         var10000 = var0.substring(var5, var6);
      }

      return var10000;
   }

   public static String substringAfter$default(String var0, String var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = var0;
      }

      return StringsKt.substringAfter(var0, var1, var2);
   }

   @NotNull
   public static final String substringBeforeLast(@NotNull String var0, char var1, @NotNull String var2) {
      int var3 = StringsKt.lastIndexOf$default((CharSequence)var0, var1, 0, false, 6, (Object)null);
      String var10000;
      if (var3 == -1) {
         var10000 = var2;
      } else {
         byte var5 = 0;
         boolean var6 = false;
         var10000 = var0.substring(var5, var3);
      }

      return var10000;
   }

   public static String substringBeforeLast$default(String var0, char var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = var0;
      }

      return StringsKt.substringBeforeLast(var0, var1, var2);
   }

   @NotNull
   public static final String substringBeforeLast(@NotNull String var0, @NotNull String var1, @NotNull String var2) {
      int var3 = StringsKt.lastIndexOf$default((CharSequence)var0, var1, 0, false, 6, (Object)null);
      String var10000;
      if (var3 == -1) {
         var10000 = var2;
      } else {
         byte var5 = 0;
         boolean var6 = false;
         var10000 = var0.substring(var5, var3);
      }

      return var10000;
   }

   public static String substringBeforeLast$default(String var0, String var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = var0;
      }

      return StringsKt.substringBeforeLast(var0, var1, var2);
   }

   @NotNull
   public static final String substringAfterLast(@NotNull String var0, char var1, @NotNull String var2) {
      int var3 = StringsKt.lastIndexOf$default((CharSequence)var0, var1, 0, false, 6, (Object)null);
      String var10000;
      if (var3 == -1) {
         var10000 = var2;
      } else {
         int var5 = var3 + 1;
         int var6 = var0.length();
         boolean var7 = false;
         var10000 = var0.substring(var5, var6);
      }

      return var10000;
   }

   public static String substringAfterLast$default(String var0, char var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = var0;
      }

      return StringsKt.substringAfterLast(var0, var1, var2);
   }

   @NotNull
   public static final String substringAfterLast(@NotNull String var0, @NotNull String var1, @NotNull String var2) {
      int var3 = StringsKt.lastIndexOf$default((CharSequence)var0, var1, 0, false, 6, (Object)null);
      String var10000;
      if (var3 == -1) {
         var10000 = var2;
      } else {
         int var5 = var3 + var1.length();
         int var6 = var0.length();
         boolean var7 = false;
         var10000 = var0.substring(var5, var6);
      }

      return var10000;
   }

   public static String substringAfterLast$default(String var0, String var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = var0;
      }

      return StringsKt.substringAfterLast(var0, var1, var2);
   }

   @NotNull
   public static final CharSequence replaceRange(@NotNull CharSequence var0, int var1, int var2, @NotNull CharSequence var3) {
      if (var2 < var1) {
         throw (Throwable)(new IndexOutOfBoundsException("End index (" + var2 + ") is less than start index (" + var1 + ")."));
      } else {
         StringBuilder var4 = new StringBuilder();
         var4.append(var0, 0, var1);
         var4.append(var3);
         var4.append(var0, var2, var0.length());
         return (CharSequence)var4;
      }
   }

   @InlineOnly
   private static final String replaceRange(@NotNull String var0, int var1, int var2, CharSequence var3) {
      return StringsKt.replaceRange((CharSequence)var0, var1, var2, var3).toString();
   }

   @NotNull
   public static final CharSequence replaceRange(@NotNull CharSequence var0, @NotNull IntRange var1, @NotNull CharSequence var2) {
      return StringsKt.replaceRange(var0, var1.getStart(), var1.getEndInclusive() + 1, var2);
   }

   @InlineOnly
   private static final String replaceRange(@NotNull String var0, IntRange var1, CharSequence var2) {
      return StringsKt.replaceRange((CharSequence)var0, var1, var2).toString();
   }

   @NotNull
   public static final CharSequence removeRange(@NotNull CharSequence var0, int var1, int var2) {
      if (var2 < var1) {
         throw (Throwable)(new IndexOutOfBoundsException("End index (" + var2 + ") is less than start index (" + var1 + ")."));
      } else if (var2 == var1) {
         return var0.subSequence(0, var0.length());
      } else {
         StringBuilder var3 = new StringBuilder(var0.length() - (var2 - var1));
         var3.append(var0, 0, var1);
         var3.append(var0, var2, var0.length());
         return (CharSequence)var3;
      }
   }

   @InlineOnly
   private static final String removeRange(@NotNull String var0, int var1, int var2) {
      return StringsKt.removeRange((CharSequence)var0, var1, var2).toString();
   }

   @NotNull
   public static final CharSequence removeRange(@NotNull CharSequence var0, @NotNull IntRange var1) {
      return StringsKt.removeRange(var0, var1.getStart(), var1.getEndInclusive() + 1);
   }

   @InlineOnly
   private static final String removeRange(@NotNull String var0, IntRange var1) {
      return StringsKt.removeRange((CharSequence)var0, var1).toString();
   }

   @NotNull
   public static final CharSequence removePrefix(@NotNull CharSequence var0, @NotNull CharSequence var1) {
      return StringsKt.startsWith$default(var0, var1, false, 2, (Object)null) ? var0.subSequence(var1.length(), var0.length()) : var0.subSequence(0, var0.length());
   }

   @NotNull
   public static final String removePrefix(@NotNull String var0, @NotNull CharSequence var1) {
      if (StringsKt.startsWith$default((CharSequence)var0, var1, false, 2, (Object)null)) {
         int var3 = var1.length();
         boolean var4 = false;
         return var0.substring(var3);
      } else {
         return var0;
      }
   }

   @NotNull
   public static final CharSequence removeSuffix(@NotNull CharSequence var0, @NotNull CharSequence var1) {
      return StringsKt.endsWith$default(var0, var1, false, 2, (Object)null) ? var0.subSequence(0, var0.length() - var1.length()) : var0.subSequence(0, var0.length());
   }

   @NotNull
   public static final String removeSuffix(@NotNull String var0, @NotNull CharSequence var1) {
      if (StringsKt.endsWith$default((CharSequence)var0, var1, false, 2, (Object)null)) {
         byte var3 = 0;
         int var4 = var0.length() - var1.length();
         boolean var5 = false;
         return var0.substring(var3, var4);
      } else {
         return var0;
      }
   }

   @NotNull
   public static final CharSequence removeSurrounding(@NotNull CharSequence var0, @NotNull CharSequence var1, @NotNull CharSequence var2) {
      return var0.length() >= var1.length() + var2.length() && StringsKt.startsWith$default(var0, var1, false, 2, (Object)null) && StringsKt.endsWith$default(var0, var2, false, 2, (Object)null) ? var0.subSequence(var1.length(), var0.length() - var2.length()) : var0.subSequence(0, var0.length());
   }

   @NotNull
   public static final String removeSurrounding(@NotNull String var0, @NotNull CharSequence var1, @NotNull CharSequence var2) {
      if (var0.length() >= var1.length() + var2.length() && StringsKt.startsWith$default((CharSequence)var0, var1, false, 2, (Object)null) && StringsKt.endsWith$default((CharSequence)var0, var2, false, 2, (Object)null)) {
         int var4 = var1.length();
         int var5 = var0.length() - var2.length();
         boolean var6 = false;
         return var0.substring(var4, var5);
      } else {
         return var0;
      }
   }

   @NotNull
   public static final CharSequence removeSurrounding(@NotNull CharSequence var0, @NotNull CharSequence var1) {
      return StringsKt.removeSurrounding(var0, var1, var1);
   }

   @NotNull
   public static final String removeSurrounding(@NotNull String var0, @NotNull CharSequence var1) {
      return StringsKt.removeSurrounding(var0, var1, var1);
   }

   @NotNull
   public static final String replaceBefore(@NotNull String var0, char var1, @NotNull String var2, @NotNull String var3) {
      int var4 = StringsKt.indexOf$default((CharSequence)var0, var1, 0, false, 6, (Object)null);
      String var10000;
      if (var4 == -1) {
         var10000 = var3;
      } else {
         byte var6 = 0;
         boolean var7 = false;
         var10000 = StringsKt.replaceRange((CharSequence)var0, var6, var4, (CharSequence)var2).toString();
      }

      return var10000;
   }

   public static String replaceBefore$default(String var0, char var1, String var2, String var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = var0;
      }

      return StringsKt.replaceBefore(var0, var1, var2, var3);
   }

   @NotNull
   public static final String replaceBefore(@NotNull String var0, @NotNull String var1, @NotNull String var2, @NotNull String var3) {
      int var4 = StringsKt.indexOf$default((CharSequence)var0, var1, 0, false, 6, (Object)null);
      String var10000;
      if (var4 == -1) {
         var10000 = var3;
      } else {
         byte var6 = 0;
         boolean var7 = false;
         var10000 = StringsKt.replaceRange((CharSequence)var0, var6, var4, (CharSequence)var2).toString();
      }

      return var10000;
   }

   public static String replaceBefore$default(String var0, String var1, String var2, String var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = var0;
      }

      return StringsKt.replaceBefore(var0, var1, var2, var3);
   }

   @NotNull
   public static final String replaceAfter(@NotNull String var0, char var1, @NotNull String var2, @NotNull String var3) {
      int var4 = StringsKt.indexOf$default((CharSequence)var0, var1, 0, false, 6, (Object)null);
      String var10000;
      if (var4 == -1) {
         var10000 = var3;
      } else {
         int var6 = var4 + 1;
         int var7 = var0.length();
         boolean var8 = false;
         var10000 = StringsKt.replaceRange((CharSequence)var0, var6, var7, (CharSequence)var2).toString();
      }

      return var10000;
   }

   public static String replaceAfter$default(String var0, char var1, String var2, String var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = var0;
      }

      return StringsKt.replaceAfter(var0, var1, var2, var3);
   }

   @NotNull
   public static final String replaceAfter(@NotNull String var0, @NotNull String var1, @NotNull String var2, @NotNull String var3) {
      int var4 = StringsKt.indexOf$default((CharSequence)var0, var1, 0, false, 6, (Object)null);
      String var10000;
      if (var4 == -1) {
         var10000 = var3;
      } else {
         int var6 = var4 + var1.length();
         int var7 = var0.length();
         boolean var8 = false;
         var10000 = StringsKt.replaceRange((CharSequence)var0, var6, var7, (CharSequence)var2).toString();
      }

      return var10000;
   }

   public static String replaceAfter$default(String var0, String var1, String var2, String var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = var0;
      }

      return StringsKt.replaceAfter(var0, var1, var2, var3);
   }

   @NotNull
   public static final String replaceAfterLast(@NotNull String var0, @NotNull String var1, @NotNull String var2, @NotNull String var3) {
      int var4 = StringsKt.lastIndexOf$default((CharSequence)var0, var1, 0, false, 6, (Object)null);
      String var10000;
      if (var4 == -1) {
         var10000 = var3;
      } else {
         int var6 = var4 + var1.length();
         int var7 = var0.length();
         boolean var8 = false;
         var10000 = StringsKt.replaceRange((CharSequence)var0, var6, var7, (CharSequence)var2).toString();
      }

      return var10000;
   }

   public static String replaceAfterLast$default(String var0, String var1, String var2, String var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = var0;
      }

      return StringsKt.replaceAfterLast(var0, var1, var2, var3);
   }

   @NotNull
   public static final String replaceAfterLast(@NotNull String var0, char var1, @NotNull String var2, @NotNull String var3) {
      int var4 = StringsKt.lastIndexOf$default((CharSequence)var0, var1, 0, false, 6, (Object)null);
      String var10000;
      if (var4 == -1) {
         var10000 = var3;
      } else {
         int var6 = var4 + 1;
         int var7 = var0.length();
         boolean var8 = false;
         var10000 = StringsKt.replaceRange((CharSequence)var0, var6, var7, (CharSequence)var2).toString();
      }

      return var10000;
   }

   public static String replaceAfterLast$default(String var0, char var1, String var2, String var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = var0;
      }

      return StringsKt.replaceAfterLast(var0, var1, var2, var3);
   }

   @NotNull
   public static final String replaceBeforeLast(@NotNull String var0, char var1, @NotNull String var2, @NotNull String var3) {
      int var4 = StringsKt.lastIndexOf$default((CharSequence)var0, var1, 0, false, 6, (Object)null);
      String var10000;
      if (var4 == -1) {
         var10000 = var3;
      } else {
         byte var6 = 0;
         boolean var7 = false;
         var10000 = StringsKt.replaceRange((CharSequence)var0, var6, var4, (CharSequence)var2).toString();
      }

      return var10000;
   }

   public static String replaceBeforeLast$default(String var0, char var1, String var2, String var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = var0;
      }

      return StringsKt.replaceBeforeLast(var0, var1, var2, var3);
   }

   @NotNull
   public static final String replaceBeforeLast(@NotNull String var0, @NotNull String var1, @NotNull String var2, @NotNull String var3) {
      int var4 = StringsKt.lastIndexOf$default((CharSequence)var0, var1, 0, false, 6, (Object)null);
      String var10000;
      if (var4 == -1) {
         var10000 = var3;
      } else {
         byte var6 = 0;
         boolean var7 = false;
         var10000 = StringsKt.replaceRange((CharSequence)var0, var6, var4, (CharSequence)var2).toString();
      }

      return var10000;
   }

   public static String replaceBeforeLast$default(String var0, String var1, String var2, String var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = var0;
      }

      return StringsKt.replaceBeforeLast(var0, var1, var2, var3);
   }

   @InlineOnly
   private static final String replace(@NotNull CharSequence var0, Regex var1, String var2) {
      byte var3 = 0;
      return var1.replace(var0, var2);
   }

   @InlineOnly
   private static final String replace(@NotNull CharSequence var0, Regex var1, Function1 var2) {
      byte var3 = 0;
      return var1.replace(var0, var2);
   }

   @InlineOnly
   private static final String replaceFirst(@NotNull CharSequence var0, Regex var1, String var2) {
      byte var3 = 0;
      return var1.replaceFirst(var0, var2);
   }

   @InlineOnly
   private static final boolean matches(@NotNull CharSequence var0, Regex var1) {
      byte var2 = 0;
      return var1.matches(var0);
   }

   public static final boolean regionMatchesImpl(@NotNull CharSequence var0, int var1, @NotNull CharSequence var2, int var3, int var4, boolean var5) {
      if (var3 >= 0 && var1 >= 0 && var1 <= var0.length() - var4 && var3 <= var2.length() - var4) {
         int var6 = 0;

         for(int var7 = var4; var6 < var7; ++var6) {
            if (!CharsKt.equals(var0.charAt(var1 + var6), var2.charAt(var3 + var6), var5)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public static final boolean startsWith(@NotNull CharSequence var0, char var1, boolean var2) {
      return var0.length() > 0 && CharsKt.equals(var0.charAt(0), var1, var2);
   }

   public static boolean startsWith$default(CharSequence var0, char var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.startsWith(var0, var1, var2);
   }

   public static final boolean endsWith(@NotNull CharSequence var0, char var1, boolean var2) {
      return var0.length() > 0 && CharsKt.equals(var0.charAt(StringsKt.getLastIndex(var0)), var1, var2);
   }

   public static boolean endsWith$default(CharSequence var0, char var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.endsWith(var0, var1, var2);
   }

   public static final boolean startsWith(@NotNull CharSequence var0, @NotNull CharSequence var1, boolean var2) {
      return !var2 && var0 instanceof String && var1 instanceof String ? StringsKt.startsWith$default((String)var0, (String)var1, false, 2, (Object)null) : StringsKt.regionMatchesImpl(var0, 0, var1, 0, var1.length(), var2);
   }

   public static boolean startsWith$default(CharSequence var0, CharSequence var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.startsWith(var0, var1, var2);
   }

   public static final boolean startsWith(@NotNull CharSequence var0, @NotNull CharSequence var1, int var2, boolean var3) {
      return !var3 && var0 instanceof String && var1 instanceof String ? StringsKt.startsWith$default((String)var0, (String)var1, var2, false, 4, (Object)null) : StringsKt.regionMatchesImpl(var0, var2, var1, 0, var1.length(), var3);
   }

   public static boolean startsWith$default(CharSequence var0, CharSequence var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.startsWith(var0, var1, var2, var3);
   }

   public static final boolean endsWith(@NotNull CharSequence var0, @NotNull CharSequence var1, boolean var2) {
      return !var2 && var0 instanceof String && var1 instanceof String ? StringsKt.endsWith$default((String)var0, (String)var1, false, 2, (Object)null) : StringsKt.regionMatchesImpl(var0, var0.length() - var1.length(), var1, 0, var1.length(), var2);
   }

   public static boolean endsWith$default(CharSequence var0, CharSequence var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.endsWith(var0, var1, var2);
   }

   @NotNull
   public static final String commonPrefixWith(@NotNull CharSequence var0, @NotNull CharSequence var1, boolean var2) {
      int var4 = var0.length();
      int var5 = var1.length();
      boolean var6 = false;
      int var3 = Math.min(var4, var5);

      for(var4 = 0; var4 < var3 && CharsKt.equals(var0.charAt(var4), var1.charAt(var4), var2); ++var4) {
      }

      if (StringsKt.hasSurrogatePairAt(var0, var4 - 1) || StringsKt.hasSurrogatePairAt(var1, var4 - 1)) {
         --var4;
      }

      return var0.subSequence(0, var4).toString();
   }

   public static String commonPrefixWith$default(CharSequence var0, CharSequence var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.commonPrefixWith(var0, var1, var2);
   }

   @NotNull
   public static final String commonSuffixWith(@NotNull CharSequence var0, @NotNull CharSequence var1, boolean var2) {
      int var3 = var0.length();
      int var4 = var1.length();
      boolean var6 = false;
      int var5 = Math.min(var3, var4);

      int var7;
      for(var7 = 0; var7 < var5 && CharsKt.equals(var0.charAt(var3 - var7 - 1), var1.charAt(var4 - var7 - 1), var2); ++var7) {
      }

      if (StringsKt.hasSurrogatePairAt(var0, var3 - var7 - 1) || StringsKt.hasSurrogatePairAt(var1, var4 - var7 - 1)) {
         --var7;
      }

      return var0.subSequence(var3 - var7, var3).toString();
   }

   public static String commonSuffixWith$default(CharSequence var0, CharSequence var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.commonSuffixWith(var0, var1, var2);
   }

   public static final int indexOfAny(@NotNull CharSequence var0, @NotNull char[] var1, int var2, boolean var3) {
      if (!var3 && var1.length == 1 && var0 instanceof String) {
         char var15 = ArraysKt.single(var1);
         String var16 = (String)var0;
         boolean var17 = false;
         return var16.indexOf(var15, var2);
      } else {
         int var4 = RangesKt.coerceAtLeast(var2, 0);
         int var5 = StringsKt.getLastIndex(var0);
         if (var4 <= var5) {
            while(true) {
               char var6 = var0.charAt(var4);
               boolean var8 = false;
               char[] var9 = var1;
               int var10 = var1.length;
               int var11 = 0;

               boolean var10000;
               while(true) {
                  if (var11 >= var10) {
                     var10000 = false;
                     break;
                  }

                  char var12 = var9[var11];
                  boolean var14 = false;
                  if (CharsKt.equals(var12, var6, var3)) {
                     var10000 = true;
                     break;
                  }

                  ++var11;
               }

               if (var10000) {
                  return var4;
               }

               if (var4 == var5) {
                  break;
               }

               ++var4;
            }
         }

         return -1;
      }
   }

   public static int indexOfAny$default(CharSequence var0, char[] var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.indexOfAny(var0, var1, var2, var3);
   }

   public static final int lastIndexOfAny(@NotNull CharSequence var0, @NotNull char[] var1, int var2, boolean var3) {
      if (!var3 && var1.length == 1 && var0 instanceof String) {
         char var15 = ArraysKt.single(var1);
         String var16 = (String)var0;
         boolean var17 = false;
         return var16.lastIndexOf(var15, var2);
      } else {
         int var4 = RangesKt.coerceAtMost(var2, StringsKt.getLastIndex(var0));

         for(boolean var5 = false; var4 >= 0; --var4) {
            char var6 = var0.charAt(var4);
            boolean var8 = false;
            char[] var9 = var1;
            int var10 = var1.length;
            int var11 = 0;

            boolean var10000;
            while(true) {
               if (var11 >= var10) {
                  var10000 = false;
                  break;
               }

               char var12 = var9[var11];
               boolean var14 = false;
               if (CharsKt.equals(var12, var6, var3)) {
                  var10000 = true;
                  break;
               }

               ++var11;
            }

            if (var10000) {
               return var4;
            }
         }

         return -1;
      }
   }

   public static int lastIndexOfAny$default(CharSequence var0, char[] var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = StringsKt.getLastIndex(var0);
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.lastIndexOfAny(var0, var1, var2, var3);
   }

   private static final int indexOf$StringsKt__StringsKt(@NotNull CharSequence var0, CharSequence var1, int var2, int var3, boolean var4, boolean var5) {
      IntProgression var10000;
      int var7;
      if (!var5) {
         var7 = RangesKt.coerceAtLeast(var2, 0);
         var10000 = (IntProgression)(new IntRange(var7, RangesKt.coerceAtMost(var3, var0.length())));
      } else {
         var10000 = RangesKt.downTo(RangesKt.coerceAtMost(var2, StringsKt.getLastIndex(var0)), RangesKt.coerceAtLeast(var3, 0));
      }

      IntProgression var6 = var10000;
      int var8;
      int var9;
      if (var0 instanceof String && var1 instanceof String) {
         var7 = var6.getFirst();
         var8 = var6.getLast();
         var9 = var6.getStep();
         if (var9 >= 0) {
            if (var7 > var8) {
               return -1;
            }
         } else if (var7 < var8) {
            return -1;
         }

         while(true) {
            if (StringsKt.regionMatches((String)var1, 0, (String)var0, var7, var1.length(), var4)) {
               return var7;
            }

            if (var7 == var8) {
               break;
            }

            var7 += var9;
         }
      } else {
         var7 = var6.getFirst();
         var8 = var6.getLast();
         var9 = var6.getStep();
         if (var9 >= 0) {
            if (var7 > var8) {
               return -1;
            }
         } else if (var7 < var8) {
            return -1;
         }

         while(true) {
            if (StringsKt.regionMatchesImpl(var1, 0, var0, var7, var1.length(), var4)) {
               return var7;
            }

            if (var7 == var8) {
               break;
            }

            var7 += var9;
         }
      }

      return -1;
   }

   static int indexOf$StringsKt__StringsKt$default(CharSequence var0, CharSequence var1, int var2, int var3, boolean var4, boolean var5, int var6, Object var7) {
      if ((var6 & 16) != 0) {
         var5 = false;
      }

      return indexOf$StringsKt__StringsKt(var0, var1, var2, var3, var4, var5);
   }

   private static final Pair findAnyOf$StringsKt__StringsKt(@NotNull CharSequence var0, Collection var1, int var2, boolean var3, boolean var4) {
      int var6;
      if (!var3 && var1.size() == 1) {
         String var16 = (String)CollectionsKt.single((Iterable)var1);
         var6 = !var4 ? StringsKt.indexOf$default(var0, var16, var2, false, 4, (Object)null) : StringsKt.lastIndexOf$default(var0, var16, var2, false, 4, (Object)null);
         return var6 < 0 ? null : TuplesKt.to(var6, var16);
      } else {
         IntProgression var10000;
         if (!var4) {
            var6 = RangesKt.coerceAtLeast(var2, 0);
            var10000 = (IntProgression)(new IntRange(var6, var0.length()));
         } else {
            var10000 = RangesKt.downTo(RangesKt.coerceAtMost(var2, StringsKt.getLastIndex(var0)), 0);
         }

         IntProgression var5 = var10000;
         int var7;
         int var8;
         String var9;
         Iterable var10;
         boolean var11;
         Iterator var12;
         Object var13;
         String var14;
         boolean var15;
         Object var17;
         if (var0 instanceof String) {
            var6 = var5.getFirst();
            var7 = var5.getLast();
            var8 = var5.getStep();
            if (var8 >= 0) {
               if (var6 > var7) {
                  return null;
               }
            } else if (var6 < var7) {
               return null;
            }

            while(true) {
               var10 = (Iterable)var1;
               var11 = false;
               var12 = var10.iterator();

               while(true) {
                  if (!var12.hasNext()) {
                     var17 = null;
                     break;
                  }

                  var13 = var12.next();
                  var14 = (String)var13;
                  var15 = false;
                  if (StringsKt.regionMatches(var14, 0, (String)var0, var6, var14.length(), var3)) {
                     var17 = var13;
                     break;
                  }
               }

               var9 = (String)var17;
               if (var9 != null) {
                  return TuplesKt.to(var6, var9);
               }

               if (var6 == var7) {
                  break;
               }

               var6 += var8;
            }
         } else {
            var6 = var5.getFirst();
            var7 = var5.getLast();
            var8 = var5.getStep();
            if (var8 >= 0) {
               if (var6 > var7) {
                  return null;
               }
            } else if (var6 < var7) {
               return null;
            }

            while(true) {
               var10 = (Iterable)var1;
               var11 = false;
               var12 = var10.iterator();

               while(true) {
                  if (!var12.hasNext()) {
                     var17 = null;
                     break;
                  }

                  var13 = var12.next();
                  var14 = (String)var13;
                  var15 = false;
                  if (StringsKt.regionMatchesImpl((CharSequence)var14, 0, var0, var6, var14.length(), var3)) {
                     var17 = var13;
                     break;
                  }
               }

               var9 = (String)var17;
               if (var9 != null) {
                  return TuplesKt.to(var6, var9);
               }

               if (var6 == var7) {
                  break;
               }

               var6 += var8;
            }
         }

         return null;
      }
   }

   @Nullable
   public static final Pair findAnyOf(@NotNull CharSequence var0, @NotNull Collection var1, int var2, boolean var3) {
      return findAnyOf$StringsKt__StringsKt(var0, var1, var2, var3, false);
   }

   public static Pair findAnyOf$default(CharSequence var0, Collection var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.findAnyOf(var0, var1, var2, var3);
   }

   @Nullable
   public static final Pair findLastAnyOf(@NotNull CharSequence var0, @NotNull Collection var1, int var2, boolean var3) {
      return findAnyOf$StringsKt__StringsKt(var0, var1, var2, var3, true);
   }

   public static Pair findLastAnyOf$default(CharSequence var0, Collection var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = StringsKt.getLastIndex(var0);
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.findLastAnyOf(var0, var1, var2, var3);
   }

   public static final int indexOfAny(@NotNull CharSequence var0, @NotNull Collection var1, int var2, boolean var3) {
      Pair var10000 = findAnyOf$StringsKt__StringsKt(var0, var1, var2, var3, false);
      int var5;
      if (var10000 != null) {
         Integer var4 = (Integer)var10000.getFirst();
         if (var4 != null) {
            var5 = var4;
            return var5;
         }
      }

      var5 = -1;
      return var5;
   }

   public static int indexOfAny$default(CharSequence var0, Collection var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.indexOfAny(var0, var1, var2, var3);
   }

   public static final int lastIndexOfAny(@NotNull CharSequence var0, @NotNull Collection var1, int var2, boolean var3) {
      Pair var10000 = findAnyOf$StringsKt__StringsKt(var0, var1, var2, var3, true);
      int var5;
      if (var10000 != null) {
         Integer var4 = (Integer)var10000.getFirst();
         if (var4 != null) {
            var5 = var4;
            return var5;
         }
      }

      var5 = -1;
      return var5;
   }

   public static int lastIndexOfAny$default(CharSequence var0, Collection var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = StringsKt.getLastIndex(var0);
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.lastIndexOfAny(var0, var1, var2, var3);
   }

   public static final int indexOf(@NotNull CharSequence var0, char var1, int var2, boolean var3) {
      int var10000;
      if (!var3 && var0 instanceof String) {
         String var4 = (String)var0;
         boolean var5 = false;
         var10000 = var4.indexOf(var1, var2);
      } else {
         var10000 = StringsKt.indexOfAny(var0, new char[]{var1}, var2, var3);
      }

      return var10000;
   }

   public static int indexOf$default(CharSequence var0, char var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.indexOf(var0, var1, var2, var3);
   }

   public static final int indexOf(@NotNull CharSequence var0, @NotNull String var1, int var2, boolean var3) {
      int var10000;
      if (!var3 && var0 instanceof String) {
         String var4 = (String)var0;
         boolean var5 = false;
         var10000 = var4.indexOf(var1, var2);
      } else {
         var10000 = indexOf$StringsKt__StringsKt$default(var0, (CharSequence)var1, var2, var0.length(), var3, false, 16, (Object)null);
      }

      return var10000;
   }

   public static int indexOf$default(CharSequence var0, String var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.indexOf(var0, var1, var2, var3);
   }

   public static final int lastIndexOf(@NotNull CharSequence var0, char var1, int var2, boolean var3) {
      int var10000;
      if (!var3 && var0 instanceof String) {
         String var4 = (String)var0;
         boolean var5 = false;
         var10000 = var4.lastIndexOf(var1, var2);
      } else {
         var10000 = StringsKt.lastIndexOfAny(var0, new char[]{var1}, var2, var3);
      }

      return var10000;
   }

   public static int lastIndexOf$default(CharSequence var0, char var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = StringsKt.getLastIndex(var0);
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.lastIndexOf(var0, var1, var2, var3);
   }

   public static final int lastIndexOf(@NotNull CharSequence var0, @NotNull String var1, int var2, boolean var3) {
      int var10000;
      if (!var3 && var0 instanceof String) {
         String var4 = (String)var0;
         boolean var5 = false;
         var10000 = var4.lastIndexOf(var1, var2);
      } else {
         var10000 = indexOf$StringsKt__StringsKt(var0, (CharSequence)var1, var2, 0, var3, true);
      }

      return var10000;
   }

   public static int lastIndexOf$default(CharSequence var0, String var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = StringsKt.getLastIndex(var0);
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.lastIndexOf(var0, var1, var2, var3);
   }

   public static final boolean contains(@NotNull CharSequence var0, @NotNull CharSequence var1, boolean var2) {
      return var1 instanceof String ? StringsKt.indexOf$default(var0, (String)var1, 0, var2, 2, (Object)null) >= 0 : indexOf$StringsKt__StringsKt$default(var0, var1, 0, var0.length(), var2, false, 16, (Object)null) >= 0;
   }

   public static boolean contains$default(CharSequence var0, CharSequence var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.contains(var0, var1, var2);
   }

   public static final boolean contains(@NotNull CharSequence var0, char var1, boolean var2) {
      return StringsKt.indexOf$default(var0, var1, 0, var2, 2, (Object)null) >= 0;
   }

   public static boolean contains$default(CharSequence var0, char var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.contains(var0, var1, var2);
   }

   @InlineOnly
   private static final boolean contains(@NotNull CharSequence var0, Regex var1) {
      byte var2 = 0;
      return var1.containsMatchIn(var0);
   }

   private static final Sequence rangesDelimitedBy$StringsKt__StringsKt(@NotNull CharSequence var0, char[] var1, int var2, boolean var3, int var4) {
      boolean var5 = var4 >= 0;
      boolean var6 = false;
      boolean var7 = false;
      if (!var5) {
         boolean var8 = false;
         String var9 = "Limit must be non-negative, but was " + var4 + '.';
         throw (Throwable)(new IllegalArgumentException(var9.toString()));
      } else {
         return (Sequence)(new DelimitedRangesSequence(var0, var2, var4, (Function2)(new StringsKt__StringsKt$rangesDelimitedBy$2(var1, var3))));
      }
   }

   static Sequence rangesDelimitedBy$StringsKt__StringsKt$default(CharSequence var0, char[] var1, int var2, boolean var3, int var4, int var5, Object var6) {
      if ((var5 & 2) != 0) {
         var2 = 0;
      }

      if ((var5 & 4) != 0) {
         var3 = false;
      }

      if ((var5 & 8) != 0) {
         var4 = 0;
      }

      return rangesDelimitedBy$StringsKt__StringsKt(var0, var1, var2, var3, var4);
   }

   private static final Sequence rangesDelimitedBy$StringsKt__StringsKt(@NotNull CharSequence var0, String[] var1, int var2, boolean var3, int var4) {
      boolean var5 = var4 >= 0;
      boolean var6 = false;
      boolean var7 = false;
      if (!var5) {
         boolean var8 = false;
         String var10 = "Limit must be non-negative, but was " + var4 + '.';
         throw (Throwable)(new IllegalArgumentException(var10.toString()));
      } else {
         List var9 = ArraysKt.asList(var1);
         return (Sequence)(new DelimitedRangesSequence(var0, var2, var4, (Function2)(new StringsKt__StringsKt$rangesDelimitedBy$4(var9, var3))));
      }
   }

   static Sequence rangesDelimitedBy$StringsKt__StringsKt$default(CharSequence var0, String[] var1, int var2, boolean var3, int var4, int var5, Object var6) {
      if ((var5 & 2) != 0) {
         var2 = 0;
      }

      if ((var5 & 4) != 0) {
         var3 = false;
      }

      if ((var5 & 8) != 0) {
         var4 = 0;
      }

      return rangesDelimitedBy$StringsKt__StringsKt(var0, var1, var2, var3, var4);
   }

   @NotNull
   public static final Sequence splitToSequence(@NotNull CharSequence var0, @NotNull String[] var1, boolean var2, int var3) {
      return SequencesKt.map(rangesDelimitedBy$StringsKt__StringsKt$default(var0, (String[])var1, 0, var2, var3, 2, (Object)null), (Function1)(new StringsKt__StringsKt$splitToSequence$1(var0)));
   }

   public static Sequence splitToSequence$default(CharSequence var0, String[] var1, boolean var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = false;
      }

      if ((var4 & 4) != 0) {
         var3 = 0;
      }

      return StringsKt.splitToSequence(var0, var1, var2, var3);
   }

   @NotNull
   public static final List split(@NotNull CharSequence var0, @NotNull String[] var1, boolean var2, int var3) {
      if (var1.length == 1) {
         String var4 = var1[0];
         CharSequence var5 = (CharSequence)var4;
         boolean var6 = false;
         if (var5.length() != 0) {
            return split$StringsKt__StringsKt(var0, var4, var2, var3);
         }
      }

      Iterable var15 = SequencesKt.asIterable(rangesDelimitedBy$StringsKt__StringsKt$default(var0, (String[])var1, 0, var2, var3, 2, (Object)null));
      boolean var16 = false;
      Collection var7 = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(var15, 10)));
      boolean var8 = false;
      Iterator var9 = var15.iterator();

      while(var9.hasNext()) {
         Object var10 = var9.next();
         IntRange var11 = (IntRange)var10;
         boolean var12 = false;
         String var14 = StringsKt.substring(var0, var11);
         var7.add(var14);
      }

      return (List)var7;
   }

   public static List split$default(CharSequence var0, String[] var1, boolean var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = false;
      }

      if ((var4 & 4) != 0) {
         var3 = 0;
      }

      return StringsKt.split(var0, var1, var2, var3);
   }

   @NotNull
   public static final Sequence splitToSequence(@NotNull CharSequence var0, @NotNull char[] var1, boolean var2, int var3) {
      return SequencesKt.map(rangesDelimitedBy$StringsKt__StringsKt$default(var0, (char[])var1, 0, var2, var3, 2, (Object)null), (Function1)(new StringsKt__StringsKt$splitToSequence$2(var0)));
   }

   public static Sequence splitToSequence$default(CharSequence var0, char[] var1, boolean var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = false;
      }

      if ((var4 & 4) != 0) {
         var3 = 0;
      }

      return StringsKt.splitToSequence(var0, var1, var2, var3);
   }

   @NotNull
   public static final List split(@NotNull CharSequence var0, @NotNull char[] var1, boolean var2, int var3) {
      if (var1.length == 1) {
         return split$StringsKt__StringsKt(var0, String.valueOf(var1[0]), var2, var3);
      } else {
         Iterable var4 = SequencesKt.asIterable(rangesDelimitedBy$StringsKt__StringsKt$default(var0, (char[])var1, 0, var2, var3, 2, (Object)null));
         boolean var5 = false;
         Collection var7 = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(var4, 10)));
         boolean var8 = false;
         Iterator var9 = var4.iterator();

         while(var9.hasNext()) {
            Object var10 = var9.next();
            IntRange var11 = (IntRange)var10;
            boolean var12 = false;
            String var14 = StringsKt.substring(var0, var11);
            var7.add(var14);
         }

         return (List)var7;
      }
   }

   public static List split$default(CharSequence var0, char[] var1, boolean var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = false;
      }

      if ((var4 & 4) != 0) {
         var3 = 0;
      }

      return StringsKt.split(var0, var1, var2, var3);
   }

   private static final List split$StringsKt__StringsKt(@NotNull CharSequence var0, String var1, boolean var2, int var3) {
      boolean var4 = var3 >= 0;
      boolean var5 = false;
      boolean var6 = false;
      if (!var4) {
         boolean var16 = false;
         String var15 = "Limit must be non-negative, but was " + var3 + '.';
         throw (Throwable)(new IllegalArgumentException(var15.toString()));
      } else {
         int var13 = 0;
         int var14 = StringsKt.indexOf(var0, var1, var13, var2);
         if (var14 != -1 && var3 != 1) {
            var6 = var3 > 0;
            ArrayList var7 = new ArrayList(var6 ? RangesKt.coerceAtMost(var3, 10) : 10);

            String var12;
            do {
               boolean var9 = false;
               var12 = var0.subSequence(var13, var14).toString();
               var7.add(var12);
               var13 = var14 + var1.length();
               if (var6 && var7.size() == var3 - 1) {
                  break;
               }

               var14 = StringsKt.indexOf(var0, var1, var13, var2);
            } while(var14 != -1);

            int var17 = var0.length();
            boolean var10 = false;
            var12 = var0.subSequence(var13, var17).toString();
            var7.add(var12);
            return (List)var7;
         } else {
            return CollectionsKt.listOf(var0.toString());
         }
      }
   }

   @InlineOnly
   private static final List split(@NotNull CharSequence var0, Regex var1, int var2) {
      byte var3 = 0;
      return var1.split(var0, var2);
   }

   static List split$default(CharSequence var0, Regex var1, int var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = 0;
      }

      boolean var5 = false;
      return var1.split(var0, var2);
   }

   @NotNull
   public static final Sequence lineSequence(@NotNull CharSequence var0) {
      return StringsKt.splitToSequence$default(var0, new String[]{"\r\n", "\n", "\r"}, false, 0, 6, (Object)null);
   }

   @NotNull
   public static final List lines(@NotNull CharSequence var0) {
      return SequencesKt.toList(StringsKt.lineSequence(var0));
   }

   public static final Pair access$findAnyOf(CharSequence var0, Collection var1, int var2, boolean var3, boolean var4) {
      return findAnyOf$StringsKt__StringsKt(var0, var1, var2, var3, var4);
   }

   public StringsKt__StringsKt() {
      super();
   }
}
