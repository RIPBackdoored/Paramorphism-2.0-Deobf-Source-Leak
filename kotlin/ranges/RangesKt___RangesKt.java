package kotlin.ranges;

import java.util.NoSuchElementException;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.random.Random;
import kotlin.random.RandomKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000n\n\u0002\b\u0002\n\u0002\u0010\u000f\n\u0002\b\u0002\n\u0002\u0010\u0005\n\u0002\u0010\u0006\n\u0002\u0010\u0007\n\u0002\u0010\b\n\u0002\u0010\t\n\u0002\u0010\n\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\f\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0016\u001a'\u0010\u0000\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\u0006\u0010\u0003\u001a\u0002H\u0001¢\u0006\u0002\u0010\u0004\u001a\u0012\u0010\u0000\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0005\u001a\u0012\u0010\u0000\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u0006\u001a\u0012\u0010\u0000\u001a\u00020\u0007*\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0007\u001a\u0012\u0010\u0000\u001a\u00020\b*\u00020\b2\u0006\u0010\u0003\u001a\u00020\b\u001a\u0012\u0010\u0000\u001a\u00020\t*\u00020\t2\u0006\u0010\u0003\u001a\u00020\t\u001a\u0012\u0010\u0000\u001a\u00020\n*\u00020\n2\u0006\u0010\u0003\u001a\u00020\n\u001a'\u0010\u000b\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\u0006\u0010\f\u001a\u0002H\u0001¢\u0006\u0002\u0010\u0004\u001a\u0012\u0010\u000b\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\f\u001a\u00020\u0005\u001a\u0012\u0010\u000b\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\f\u001a\u00020\u0006\u001a\u0012\u0010\u000b\u001a\u00020\u0007*\u00020\u00072\u0006\u0010\f\u001a\u00020\u0007\u001a\u0012\u0010\u000b\u001a\u00020\b*\u00020\b2\u0006\u0010\f\u001a\u00020\b\u001a\u0012\u0010\u000b\u001a\u00020\t*\u00020\t2\u0006\u0010\f\u001a\u00020\t\u001a\u0012\u0010\u000b\u001a\u00020\n*\u00020\n2\u0006\u0010\f\u001a\u00020\n\u001a3\u0010\r\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\b\u0010\u0003\u001a\u0004\u0018\u0001H\u00012\b\u0010\f\u001a\u0004\u0018\u0001H\u0001¢\u0006\u0002\u0010\u000e\u001a/\u0010\r\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0010H\u0007¢\u0006\u0002\u0010\u0011\u001a-\u0010\r\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0012¢\u0006\u0002\u0010\u0013\u001a\u001a\u0010\r\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u0005\u001a\u001a\u0010\r\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0006\u001a\u001a\u0010\r\u001a\u00020\u0007*\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u0007\u001a\u001a\u0010\r\u001a\u00020\b*\u00020\b2\u0006\u0010\u0003\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\b\u001a\u0018\u0010\r\u001a\u00020\b*\u00020\b2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\b0\u0012\u001a\u001a\u0010\r\u001a\u00020\t*\u00020\t2\u0006\u0010\u0003\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\t\u001a\u0018\u0010\r\u001a\u00020\t*\u00020\t2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\t0\u0012\u001a\u001a\u0010\r\u001a\u00020\n*\u00020\n2\u0006\u0010\u0003\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n\u001a\u001c\u0010\u0014\u001a\u00020\u0015*\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0087\n¢\u0006\u0002\u0010\u0019\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002¢\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002¢\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002¢\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002¢\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002¢\u0006\u0002\b \u001a\u001c\u0010\u0014\u001a\u00020\u0015*\u00020!2\b\u0010\u0017\u001a\u0004\u0018\u00010\bH\u0087\n¢\u0006\u0002\u0010\"\u001a\u001c\u0010\u0014\u001a\u00020\u0015*\u00020#2\b\u0010\u0017\u001a\u0004\u0018\u00010\tH\u0087\n¢\u0006\u0002\u0010$\u001a\u0015\u0010%\u001a\u00020&*\u00020\u00052\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\u00052\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\u00052\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\u00052\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020)*\u00020\u00182\u0006\u0010'\u001a\u00020\u0018H\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\b2\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\b2\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\b2\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\b2\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\t2\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\t2\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\t2\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\t2\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\n2\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\n2\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\n2\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\n2\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\r\u0010*\u001a\u00020\u0018*\u00020\u0016H\u0087\b\u001a\u0014\u0010*\u001a\u00020\u0018*\u00020\u00162\u0006\u0010*\u001a\u00020+H\u0007\u001a\r\u0010*\u001a\u00020\b*\u00020!H\u0087\b\u001a\u0014\u0010*\u001a\u00020\b*\u00020!2\u0006\u0010*\u001a\u00020+H\u0007\u001a\r\u0010*\u001a\u00020\t*\u00020#H\u0087\b\u001a\u0014\u0010*\u001a\u00020\t*\u00020#2\u0006\u0010*\u001a\u00020+H\u0007\u001a\n\u0010,\u001a\u00020)*\u00020)\u001a\n\u0010,\u001a\u00020&*\u00020&\u001a\n\u0010,\u001a\u00020(*\u00020(\u001a\u0015\u0010-\u001a\u00020)*\u00020)2\u0006\u0010-\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010-\u001a\u00020&*\u00020&2\u0006\u0010-\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010-\u001a\u00020(*\u00020(2\u0006\u0010-\u001a\u00020\tH\u0086\u0004\u001a\u0013\u0010.\u001a\u0004\u0018\u00010\u0005*\u00020\u0006H\u0000¢\u0006\u0002\u0010/\u001a\u0013\u0010.\u001a\u0004\u0018\u00010\u0005*\u00020\u0007H\u0000¢\u0006\u0002\u00100\u001a\u0013\u0010.\u001a\u0004\u0018\u00010\u0005*\u00020\bH\u0000¢\u0006\u0002\u00101\u001a\u0013\u0010.\u001a\u0004\u0018\u00010\u0005*\u00020\tH\u0000¢\u0006\u0002\u00102\u001a\u0013\u0010.\u001a\u0004\u0018\u00010\u0005*\u00020\nH\u0000¢\u0006\u0002\u00103\u001a\u0013\u00104\u001a\u0004\u0018\u00010\b*\u00020\u0006H\u0000¢\u0006\u0002\u00105\u001a\u0013\u00104\u001a\u0004\u0018\u00010\b*\u00020\u0007H\u0000¢\u0006\u0002\u00106\u001a\u0013\u00104\u001a\u0004\u0018\u00010\b*\u00020\tH\u0000¢\u0006\u0002\u00107\u001a\u0013\u00108\u001a\u0004\u0018\u00010\t*\u00020\u0006H\u0000¢\u0006\u0002\u00109\u001a\u0013\u00108\u001a\u0004\u0018\u00010\t*\u00020\u0007H\u0000¢\u0006\u0002\u0010:\u001a\u0013\u0010;\u001a\u0004\u0018\u00010\n*\u00020\u0006H\u0000¢\u0006\u0002\u0010<\u001a\u0013\u0010;\u001a\u0004\u0018\u00010\n*\u00020\u0007H\u0000¢\u0006\u0002\u0010=\u001a\u0013\u0010;\u001a\u0004\u0018\u00010\n*\u00020\bH\u0000¢\u0006\u0002\u0010>\u001a\u0013\u0010;\u001a\u0004\u0018\u00010\n*\u00020\tH\u0000¢\u0006\u0002\u0010?\u001a\u0015\u0010@\u001a\u00020!*\u00020\u00052\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010@\u001a\u00020!*\u00020\u00052\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020#*\u00020\u00052\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020!*\u00020\u00052\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020\u0016*\u00020\u00182\u0006\u0010'\u001a\u00020\u0018H\u0086\u0004\u001a\u0015\u0010@\u001a\u00020!*\u00020\b2\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010@\u001a\u00020!*\u00020\b2\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020#*\u00020\b2\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020!*\u00020\b2\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020#*\u00020\t2\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010@\u001a\u00020#*\u00020\t2\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020#*\u00020\t2\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020#*\u00020\t2\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020!*\u00020\n2\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010@\u001a\u00020!*\u00020\n2\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020#*\u00020\n2\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020!*\u00020\n2\u0006\u0010'\u001a\u00020\nH\u0086\u0004¨\u0006A"},
   d2 = {"coerceAtLeast", "T", "", "minimumValue", "(Ljava/lang/Comparable;Ljava/lang/Comparable;)Ljava/lang/Comparable;", "", "", "", "", "", "", "coerceAtMost", "maximumValue", "coerceIn", "(Ljava/lang/Comparable;Ljava/lang/Comparable;Ljava/lang/Comparable;)Ljava/lang/Comparable;", "range", "Lkotlin/ranges/ClosedFloatingPointRange;", "(Ljava/lang/Comparable;Lkotlin/ranges/ClosedFloatingPointRange;)Ljava/lang/Comparable;", "Lkotlin/ranges/ClosedRange;", "(Ljava/lang/Comparable;Lkotlin/ranges/ClosedRange;)Ljava/lang/Comparable;", "contains", "", "Lkotlin/ranges/CharRange;", "element", "", "(Lkotlin/ranges/CharRange;Ljava/lang/Character;)Z", "value", "byteRangeContains", "doubleRangeContains", "floatRangeContains", "intRangeContains", "longRangeContains", "shortRangeContains", "Lkotlin/ranges/IntRange;", "(Lkotlin/ranges/IntRange;Ljava/lang/Integer;)Z", "Lkotlin/ranges/LongRange;", "(Lkotlin/ranges/LongRange;Ljava/lang/Long;)Z", "downTo", "Lkotlin/ranges/IntProgression;", "to", "Lkotlin/ranges/LongProgression;", "Lkotlin/ranges/CharProgression;", "random", "Lkotlin/random/Random;", "reversed", "step", "toByteExactOrNull", "(D)Ljava/lang/Byte;", "(F)Ljava/lang/Byte;", "(I)Ljava/lang/Byte;", "(J)Ljava/lang/Byte;", "(S)Ljava/lang/Byte;", "toIntExactOrNull", "(D)Ljava/lang/Integer;", "(F)Ljava/lang/Integer;", "(J)Ljava/lang/Integer;", "toLongExactOrNull", "(D)Ljava/lang/Long;", "(F)Ljava/lang/Long;", "toShortExactOrNull", "(D)Ljava/lang/Short;", "(F)Ljava/lang/Short;", "(I)Ljava/lang/Short;", "(J)Ljava/lang/Short;", "until", "kotlin-stdlib"},
   xs = "kotlin/ranges/RangesKt"
)
class RangesKt___RangesKt extends RangesKt__RangesKt {
   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final int random(@NotNull IntRange var0) {
      byte var1 = 0;
      return RangesKt.random(var0, (Random)Random.Default);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final long random(@NotNull LongRange var0) {
      byte var1 = 0;
      return RangesKt.random(var0, (Random)Random.Default);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final char random(@NotNull CharRange var0) {
      byte var1 = 0;
      return RangesKt.random(var0, (Random)Random.Default);
   }

   @SinceKotlin(
      version = "1.3"
   )
   public static final int random(@NotNull IntRange var0, @NotNull Random var1) {
      try {
         return RandomKt.nextInt(var1, var0);
      } catch (IllegalArgumentException var3) {
         throw (Throwable)(new NoSuchElementException(var3.getMessage()));
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   public static final long random(@NotNull LongRange var0, @NotNull Random var1) {
      try {
         return RandomKt.nextLong(var1, var0);
      } catch (IllegalArgumentException var3) {
         throw (Throwable)(new NoSuchElementException(var3.getMessage()));
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   public static final char random(@NotNull CharRange var0, @NotNull Random var1) {
      try {
         return (char)var1.nextInt(var0.getFirst(), var0.getLast() + 1);
      } catch (IllegalArgumentException var3) {
         throw (Throwable)(new NoSuchElementException(var3.getMessage()));
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final boolean contains(@NotNull IntRange var0, Integer var1) {
      byte var2 = 0;
      return var1 != null && var0.contains(var1);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final boolean contains(@NotNull LongRange var0, Long var1) {
      byte var2 = 0;
      return var1 != null && var0.contains(var1);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final boolean contains(@NotNull CharRange var0, Character var1) {
      byte var2 = 0;
      return var1 != null && var0.contains(var1);
   }

   @JvmName(
      name = "intRangeContains"
   )
   public static final boolean intRangeContains(@NotNull ClosedRange var0, byte var1) {
      return var0.contains((Comparable)Integer.valueOf(var1));
   }

   @JvmName(
      name = "longRangeContains"
   )
   public static final boolean longRangeContains(@NotNull ClosedRange var0, byte var1) {
      return var0.contains((Comparable)(long)var1);
   }

   @JvmName(
      name = "shortRangeContains"
   )
   public static final boolean shortRangeContains(@NotNull ClosedRange var0, byte var1) {
      return var0.contains((Comparable)(short)var1);
   }

   /** @deprecated */
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @JvmName(
      name = "doubleRangeContains"
   )
   public static final boolean doubleRangeContains(@NotNull ClosedRange var0, byte var1) {
      return var0.contains((Comparable)(double)var1);
   }

   /** @deprecated */
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @JvmName(
      name = "floatRangeContains"
   )
   public static final boolean floatRangeContains(@NotNull ClosedRange var0, byte var1) {
      return var0.contains((Comparable)(float)var1);
   }

   /** @deprecated */
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @JvmName(
      name = "intRangeContains"
   )
   public static final boolean intRangeContains(@NotNull ClosedRange var0, double var1) {
      Integer var3 = RangesKt.toIntExactOrNull(var1);
      boolean var4 = false;
      boolean var5 = false;
      boolean var7 = false;
      return var3 != null ? var0.contains((Comparable)var3) : false;
   }

   /** @deprecated */
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @JvmName(
      name = "longRangeContains"
   )
   public static final boolean longRangeContains(@NotNull ClosedRange var0, double var1) {
      Long var3 = RangesKt.toLongExactOrNull(var1);
      boolean var4 = false;
      boolean var5 = false;
      boolean var7 = false;
      return var3 != null ? var0.contains((Comparable)var3) : false;
   }

   /** @deprecated */
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @JvmName(
      name = "byteRangeContains"
   )
   public static final boolean byteRangeContains(@NotNull ClosedRange var0, double var1) {
      Byte var3 = RangesKt.toByteExactOrNull(var1);
      boolean var4 = false;
      boolean var5 = false;
      boolean var7 = false;
      return var3 != null ? var0.contains((Comparable)var3) : false;
   }

   /** @deprecated */
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @JvmName(
      name = "shortRangeContains"
   )
   public static final boolean shortRangeContains(@NotNull ClosedRange var0, double var1) {
      Short var3 = RangesKt.toShortExactOrNull(var1);
      boolean var4 = false;
      boolean var5 = false;
      boolean var7 = false;
      return var3 != null ? var0.contains((Comparable)var3) : false;
   }

   @JvmName(
      name = "floatRangeContains"
   )
   public static final boolean floatRangeContains(@NotNull ClosedRange var0, double var1) {
      return var0.contains((Comparable)(float)var1);
   }

   /** @deprecated */
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @JvmName(
      name = "intRangeContains"
   )
   public static final boolean intRangeContains(@NotNull ClosedRange var0, float var1) {
      Integer var2 = RangesKt.toIntExactOrNull(var1);
      boolean var3 = false;
      boolean var4 = false;
      boolean var6 = false;
      return var2 != null ? var0.contains((Comparable)var2) : false;
   }

   /** @deprecated */
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @JvmName(
      name = "longRangeContains"
   )
   public static final boolean longRangeContains(@NotNull ClosedRange var0, float var1) {
      Long var2 = RangesKt.toLongExactOrNull(var1);
      boolean var3 = false;
      boolean var4 = false;
      boolean var6 = false;
      return var2 != null ? var0.contains((Comparable)var2) : false;
   }

   /** @deprecated */
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @JvmName(
      name = "byteRangeContains"
   )
   public static final boolean byteRangeContains(@NotNull ClosedRange var0, float var1) {
      Byte var2 = RangesKt.toByteExactOrNull(var1);
      boolean var3 = false;
      boolean var4 = false;
      boolean var6 = false;
      return var2 != null ? var0.contains((Comparable)var2) : false;
   }

   /** @deprecated */
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @JvmName(
      name = "shortRangeContains"
   )
   public static final boolean shortRangeContains(@NotNull ClosedRange var0, float var1) {
      Short var2 = RangesKt.toShortExactOrNull(var1);
      boolean var3 = false;
      boolean var4 = false;
      boolean var6 = false;
      return var2 != null ? var0.contains((Comparable)var2) : false;
   }

   @JvmName(
      name = "doubleRangeContains"
   )
   public static final boolean doubleRangeContains(@NotNull ClosedRange var0, float var1) {
      return var0.contains((Comparable)(double)var1);
   }

   @JvmName(
      name = "longRangeContains"
   )
   public static final boolean longRangeContains(@NotNull ClosedRange var0, int var1) {
      return var0.contains((Comparable)(long)var1);
   }

   @JvmName(
      name = "byteRangeContains"
   )
   public static final boolean byteRangeContains(@NotNull ClosedRange var0, int var1) {
      Byte var2 = RangesKt.toByteExactOrNull(var1);
      boolean var3 = false;
      boolean var4 = false;
      boolean var6 = false;
      return var2 != null ? var0.contains((Comparable)var2) : false;
   }

   @JvmName(
      name = "shortRangeContains"
   )
   public static final boolean shortRangeContains(@NotNull ClosedRange var0, int var1) {
      Short var2 = RangesKt.toShortExactOrNull(var1);
      boolean var3 = false;
      boolean var4 = false;
      boolean var6 = false;
      return var2 != null ? var0.contains((Comparable)var2) : false;
   }

   /** @deprecated */
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @JvmName(
      name = "doubleRangeContains"
   )
   public static final boolean doubleRangeContains(@NotNull ClosedRange var0, int var1) {
      return var0.contains((Comparable)(double)var1);
   }

   /** @deprecated */
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @JvmName(
      name = "floatRangeContains"
   )
   public static final boolean floatRangeContains(@NotNull ClosedRange var0, int var1) {
      return var0.contains((Comparable)(float)var1);
   }

   @JvmName(
      name = "intRangeContains"
   )
   public static final boolean intRangeContains(@NotNull ClosedRange var0, long var1) {
      Integer var3 = RangesKt.toIntExactOrNull(var1);
      boolean var4 = false;
      boolean var5 = false;
      boolean var7 = false;
      return var3 != null ? var0.contains((Comparable)var3) : false;
   }

   @JvmName(
      name = "byteRangeContains"
   )
   public static final boolean byteRangeContains(@NotNull ClosedRange var0, long var1) {
      Byte var3 = RangesKt.toByteExactOrNull(var1);
      boolean var4 = false;
      boolean var5 = false;
      boolean var7 = false;
      return var3 != null ? var0.contains((Comparable)var3) : false;
   }

   @JvmName(
      name = "shortRangeContains"
   )
   public static final boolean shortRangeContains(@NotNull ClosedRange var0, long var1) {
      Short var3 = RangesKt.toShortExactOrNull(var1);
      boolean var4 = false;
      boolean var5 = false;
      boolean var7 = false;
      return var3 != null ? var0.contains((Comparable)var3) : false;
   }

   /** @deprecated */
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @JvmName(
      name = "doubleRangeContains"
   )
   public static final boolean doubleRangeContains(@NotNull ClosedRange var0, long var1) {
      return var0.contains((Comparable)(double)var1);
   }

   /** @deprecated */
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @JvmName(
      name = "floatRangeContains"
   )
   public static final boolean floatRangeContains(@NotNull ClosedRange var0, long var1) {
      return var0.contains((Comparable)(float)var1);
   }

   @JvmName(
      name = "intRangeContains"
   )
   public static final boolean intRangeContains(@NotNull ClosedRange var0, short var1) {
      return var0.contains((Comparable)Integer.valueOf(var1));
   }

   @JvmName(
      name = "longRangeContains"
   )
   public static final boolean longRangeContains(@NotNull ClosedRange var0, short var1) {
      return var0.contains((Comparable)(long)var1);
   }

   @JvmName(
      name = "byteRangeContains"
   )
   public static final boolean byteRangeContains(@NotNull ClosedRange var0, short var1) {
      Byte var2 = RangesKt.toByteExactOrNull(var1);
      boolean var3 = false;
      boolean var4 = false;
      boolean var6 = false;
      return var2 != null ? var0.contains((Comparable)var2) : false;
   }

   /** @deprecated */
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @JvmName(
      name = "doubleRangeContains"
   )
   public static final boolean doubleRangeContains(@NotNull ClosedRange var0, short var1) {
      return var0.contains((Comparable)(double)var1);
   }

   /** @deprecated */
   @Deprecated(
      message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed."
   )
   @JvmName(
      name = "floatRangeContains"
   )
   public static final boolean floatRangeContains(@NotNull ClosedRange var0, short var1) {
      return var0.contains((Comparable)(float)var1);
   }

   @NotNull
   public static final IntProgression downTo(int var0, byte var1) {
      return IntProgression.Companion.fromClosedRange(var0, var1, -1);
   }

   @NotNull
   public static final LongProgression downTo(long var0, byte var2) {
      return LongProgression.Companion.fromClosedRange(var0, (long)var2, -1L);
   }

   @NotNull
   public static final IntProgression downTo(byte var0, byte var1) {
      return IntProgression.Companion.fromClosedRange(var0, var1, -1);
   }

   @NotNull
   public static final IntProgression downTo(short var0, byte var1) {
      return IntProgression.Companion.fromClosedRange(var0, var1, -1);
   }

   @NotNull
   public static final CharProgression downTo(char var0, char var1) {
      return CharProgression.Companion.fromClosedRange(var0, var1, -1);
   }

   @NotNull
   public static final IntProgression downTo(int var0, int var1) {
      return IntProgression.Companion.fromClosedRange(var0, var1, -1);
   }

   @NotNull
   public static final LongProgression downTo(long var0, int var2) {
      return LongProgression.Companion.fromClosedRange(var0, (long)var2, -1L);
   }

   @NotNull
   public static final IntProgression downTo(byte var0, int var1) {
      return IntProgression.Companion.fromClosedRange(var0, var1, -1);
   }

   @NotNull
   public static final IntProgression downTo(short var0, int var1) {
      return IntProgression.Companion.fromClosedRange(var0, var1, -1);
   }

   @NotNull
   public static final LongProgression downTo(int var0, long var1) {
      return LongProgression.Companion.fromClosedRange((long)var0, var1, -1L);
   }

   @NotNull
   public static final LongProgression downTo(long var0, long var2) {
      return LongProgression.Companion.fromClosedRange(var0, var2, -1L);
   }

   @NotNull
   public static final LongProgression downTo(byte var0, long var1) {
      return LongProgression.Companion.fromClosedRange((long)var0, var1, -1L);
   }

   @NotNull
   public static final LongProgression downTo(short var0, long var1) {
      return LongProgression.Companion.fromClosedRange((long)var0, var1, -1L);
   }

   @NotNull
   public static final IntProgression downTo(int var0, short var1) {
      return IntProgression.Companion.fromClosedRange(var0, var1, -1);
   }

   @NotNull
   public static final LongProgression downTo(long var0, short var2) {
      return LongProgression.Companion.fromClosedRange(var0, (long)var2, -1L);
   }

   @NotNull
   public static final IntProgression downTo(byte var0, short var1) {
      return IntProgression.Companion.fromClosedRange(var0, var1, -1);
   }

   @NotNull
   public static final IntProgression downTo(short var0, short var1) {
      return IntProgression.Companion.fromClosedRange(var0, var1, -1);
   }

   @NotNull
   public static final IntProgression reversed(@NotNull IntProgression var0) {
      return IntProgression.Companion.fromClosedRange(var0.getLast(), var0.getFirst(), -var0.getStep());
   }

   @NotNull
   public static final LongProgression reversed(@NotNull LongProgression var0) {
      return LongProgression.Companion.fromClosedRange(var0.getLast(), var0.getFirst(), -var0.getStep());
   }

   @NotNull
   public static final CharProgression reversed(@NotNull CharProgression var0) {
      return CharProgression.Companion.fromClosedRange(var0.getLast(), var0.getFirst(), -var0.getStep());
   }

   @NotNull
   public static final IntProgression step(@NotNull IntProgression var0, int var1) {
      RangesKt.checkStepIsPositive(var1 > 0, (Number)var1);
      return IntProgression.Companion.fromClosedRange(var0.getFirst(), var0.getLast(), var0.getStep() > 0 ? var1 : -var1);
   }

   @NotNull
   public static final LongProgression step(@NotNull LongProgression var0, long var1) {
      RangesKt.checkStepIsPositive(var1 > 0L, (Number)var1);
      return LongProgression.Companion.fromClosedRange(var0.getFirst(), var0.getLast(), var0.getStep() > 0L ? var1 : -var1);
   }

   @NotNull
   public static final CharProgression step(@NotNull CharProgression var0, int var1) {
      RangesKt.checkStepIsPositive(var1 > 0, (Number)var1);
      return CharProgression.Companion.fromClosedRange(var0.getFirst(), var0.getLast(), var0.getStep() > 0 ? var1 : -var1);
   }

   @Nullable
   public static final Byte toByteExactOrNull(int var0) {
      Byte var10000;
      if (-128 <= var0) {
         if (127 >= var0) {
            var10000 = (byte)var0;
            return var10000;
         }
      }

      var10000 = null;
      return var10000;
   }

   @Nullable
   public static final Byte toByteExactOrNull(long var0) {
      long var10000 = (long)-128;
      long var10002 = (long)127;
      Byte var4;
      if (var10000 <= var0) {
         if (var10002 >= var0) {
            var4 = (byte)((int)var0);
            return var4;
         }
      }

      var4 = null;
      return var4;
   }

   @Nullable
   public static final Byte toByteExactOrNull(short var0) {
      short var10000 = (short)-128;
      short var10002 = (short)127;
      Byte var2;
      if (var10000 <= var0) {
         if (var10002 >= var0) {
            var2 = (byte)var0;
            return var2;
         }
      }

      var2 = null;
      return var2;
   }

   @Nullable
   public static final Byte toByteExactOrNull(double var0) {
      double var4 = (double)-128;
      double var6 = (double)127;
      return var0 >= var4 && var0 <= var6 ? (byte)((int)var0) : null;
   }

   @Nullable
   public static final Byte toByteExactOrNull(float var0) {
      float var2 = (float)-128;
      float var3 = (float)127;
      return var0 >= var2 && var0 <= var3 ? (byte)((int)var0) : null;
   }

   @Nullable
   public static final Integer toIntExactOrNull(long var0) {
      long var10000 = (long)Integer.MIN_VALUE;
      long var10002 = (long)0;
      Integer var4;
      if (var10000 <= var0) {
         if (var10002 >= var0) {
            var4 = (int)var0;
            return var4;
         }
      }

      var4 = null;
      return var4;
   }

   @Nullable
   public static final Integer toIntExactOrNull(double var0) {
      double var4 = (double)Integer.MIN_VALUE;
      double var6 = (double)0;
      return var0 >= var4 && var0 <= var6 ? (int)var0 : null;
   }

   @Nullable
   public static final Integer toIntExactOrNull(float var0) {
      float var2 = (float)Integer.MIN_VALUE;
      float var3 = (float)0;
      return var0 >= var2 && var0 <= var3 ? (int)var0 : null;
   }

   @Nullable
   public static final Long toLongExactOrNull(double var0) {
      double var4 = (double)Long.MIN_VALUE;
      double var6 = (double)4294967295L;
      return var0 >= var4 && var0 <= var6 ? (long)var0 : null;
   }

   @Nullable
   public static final Long toLongExactOrNull(float var0) {
      float var2 = (float)Long.MIN_VALUE;
      float var3 = (float)4294967295L;
      return var0 >= var2 && var0 <= var3 ? (long)var0 : null;
   }

   @Nullable
   public static final Short toShortExactOrNull(int var0) {
      Short var10000;
      if (-32768 <= var0) {
         if (32767 >= var0) {
            var10000 = (short)var0;
            return var10000;
         }
      }

      var10000 = null;
      return var10000;
   }

   @Nullable
   public static final Short toShortExactOrNull(long var0) {
      long var10000 = (long)-32768;
      long var10002 = (long)32767;
      Short var4;
      if (var10000 <= var0) {
         if (var10002 >= var0) {
            var4 = (short)((int)var0);
            return var4;
         }
      }

      var4 = null;
      return var4;
   }

   @Nullable
   public static final Short toShortExactOrNull(double var0) {
      double var4 = (double)-32768;
      double var6 = (double)32767;
      return var0 >= var4 && var0 <= var6 ? (short)((int)var0) : null;
   }

   @Nullable
   public static final Short toShortExactOrNull(float var0) {
      float var2 = (float)-32768;
      float var3 = (float)32767;
      return var0 >= var2 && var0 <= var3 ? (short)((int)var0) : null;
   }

   @NotNull
   public static final IntRange until(int var0, byte var1) {
      return new IntRange(var0, var1 - 1);
   }

   @NotNull
   public static final LongRange until(long var0, byte var2) {
      return new LongRange(var0, (long)var2 - 1L);
   }

   @NotNull
   public static final IntRange until(byte var0, byte var1) {
      return new IntRange(var0, var1 - 1);
   }

   @NotNull
   public static final IntRange until(short var0, byte var1) {
      return new IntRange(var0, var1 - 1);
   }

   @NotNull
   public static final CharRange until(char var0, char var1) {
      return var1 <= 0 ? CharRange.Companion.getEMPTY() : new CharRange(var0, (char)(var1 - 1));
   }

   @NotNull
   public static final IntRange until(int var0, int var1) {
      return var1 <= Integer.MIN_VALUE ? IntRange.Companion.getEMPTY() : new IntRange(var0, var1 - 1);
   }

   @NotNull
   public static final LongRange until(long var0, int var2) {
      return new LongRange(var0, (long)var2 - 1L);
   }

   @NotNull
   public static final IntRange until(byte var0, int var1) {
      return var1 <= Integer.MIN_VALUE ? IntRange.Companion.getEMPTY() : new IntRange(var0, var1 - 1);
   }

   @NotNull
   public static final IntRange until(short var0, int var1) {
      return var1 <= Integer.MIN_VALUE ? IntRange.Companion.getEMPTY() : new IntRange(var0, var1 - 1);
   }

   @NotNull
   public static final LongRange until(int var0, long var1) {
      if (var1 <= Long.MIN_VALUE) {
         return LongRange.Companion.getEMPTY();
      } else {
         long var3 = (long)var0;
         return new LongRange(var3, var1 - 1L);
      }
   }

   @NotNull
   public static final LongRange until(long var0, long var2) {
      return var2 <= Long.MIN_VALUE ? LongRange.Companion.getEMPTY() : new LongRange(var0, var2 - 1L);
   }

   @NotNull
   public static final LongRange until(byte var0, long var1) {
      if (var1 <= Long.MIN_VALUE) {
         return LongRange.Companion.getEMPTY();
      } else {
         long var3 = (long)var0;
         return new LongRange(var3, var1 - 1L);
      }
   }

   @NotNull
   public static final LongRange until(short var0, long var1) {
      if (var1 <= Long.MIN_VALUE) {
         return LongRange.Companion.getEMPTY();
      } else {
         long var3 = (long)var0;
         return new LongRange(var3, var1 - 1L);
      }
   }

   @NotNull
   public static final IntRange until(int var0, short var1) {
      return new IntRange(var0, var1 - 1);
   }

   @NotNull
   public static final LongRange until(long var0, short var2) {
      return new LongRange(var0, (long)var2 - 1L);
   }

   @NotNull
   public static final IntRange until(byte var0, short var1) {
      return new IntRange(var0, var1 - 1);
   }

   @NotNull
   public static final IntRange until(short var0, short var1) {
      return new IntRange(var0, var1 - 1);
   }

   @NotNull
   public static final Comparable coerceAtLeast(@NotNull Comparable var0, @NotNull Comparable var1) {
      return var0.compareTo(var1) < 0 ? var1 : var0;
   }

   public static final byte coerceAtLeast(byte var0, byte var1) {
      return var0 < var1 ? var1 : var0;
   }

   public static final short coerceAtLeast(short var0, short var1) {
      return var0 < var1 ? var1 : var0;
   }

   public static final int coerceAtLeast(int var0, int var1) {
      return var0 < var1 ? var1 : var0;
   }

   public static final long coerceAtLeast(long var0, long var2) {
      return var0 < var2 ? var2 : var0;
   }

   public static final float coerceAtLeast(float var0, float var1) {
      return var0 < var1 ? var1 : var0;
   }

   public static final double coerceAtLeast(double var0, double var2) {
      return var0 < var2 ? var2 : var0;
   }

   @NotNull
   public static final Comparable coerceAtMost(@NotNull Comparable var0, @NotNull Comparable var1) {
      return var0.compareTo(var1) > 0 ? var1 : var0;
   }

   public static final byte coerceAtMost(byte var0, byte var1) {
      return var0 > var1 ? var1 : var0;
   }

   public static final short coerceAtMost(short var0, short var1) {
      return var0 > var1 ? var1 : var0;
   }

   public static final int coerceAtMost(int var0, int var1) {
      return var0 > var1 ? var1 : var0;
   }

   public static final long coerceAtMost(long var0, long var2) {
      return var0 > var2 ? var2 : var0;
   }

   public static final float coerceAtMost(float var0, float var1) {
      return var0 > var1 ? var1 : var0;
   }

   public static final double coerceAtMost(double var0, double var2) {
      return var0 > var2 ? var2 : var0;
   }

   @NotNull
   public static final Comparable coerceIn(@NotNull Comparable var0, @Nullable Comparable var1, @Nullable Comparable var2) {
      if (var1 != null && var2 != null) {
         if (var1.compareTo(var2) > 0) {
            throw (Throwable)(new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + var2 + " is less than minimum " + var1 + '.'));
         }

         if (var0.compareTo(var1) < 0) {
            return var1;
         }

         if (var0.compareTo(var2) > 0) {
            return var2;
         }
      } else {
         if (var1 != null && var0.compareTo(var1) < 0) {
            return var1;
         }

         if (var2 != null && var0.compareTo(var2) > 0) {
            return var2;
         }
      }

      return var0;
   }

   public static final byte coerceIn(byte var0, byte var1, byte var2) {
      if (var1 > var2) {
         throw (Throwable)(new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + var2 + " is less than minimum " + var1 + '.'));
      } else if (var0 < var1) {
         return var1;
      } else {
         return var0 > var2 ? var2 : var0;
      }
   }

   public static final short coerceIn(short var0, short var1, short var2) {
      if (var1 > var2) {
         throw (Throwable)(new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + var2 + " is less than minimum " + var1 + '.'));
      } else if (var0 < var1) {
         return var1;
      } else {
         return var0 > var2 ? var2 : var0;
      }
   }

   public static final int coerceIn(int var0, int var1, int var2) {
      if (var1 > var2) {
         throw (Throwable)(new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + var2 + " is less than minimum " + var1 + '.'));
      } else if (var0 < var1) {
         return var1;
      } else {
         return var0 > var2 ? var2 : var0;
      }
   }

   public static final long coerceIn(long var0, long var2, long var4) {
      if (var2 > var4) {
         throw (Throwable)(new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + var4 + " is less than minimum " + var2 + '.'));
      } else if (var0 < var2) {
         return var2;
      } else {
         return var0 > var4 ? var4 : var0;
      }
   }

   public static final float coerceIn(float var0, float var1, float var2) {
      if (var1 > var2) {
         throw (Throwable)(new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + var2 + " is less than minimum " + var1 + '.'));
      } else if (var0 < var1) {
         return var1;
      } else {
         return var0 > var2 ? var2 : var0;
      }
   }

   public static final double coerceIn(double var0, double var2, double var4) {
      if (var2 > var4) {
         throw (Throwable)(new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + var4 + " is less than minimum " + var2 + '.'));
      } else if (var0 < var2) {
         return var2;
      } else {
         return var0 > var4 ? var4 : var0;
      }
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Comparable coerceIn(@NotNull Comparable var0, @NotNull ClosedFloatingPointRange var1) {
      if (var1.isEmpty()) {
         throw (Throwable)(new IllegalArgumentException("Cannot coerce value to an empty range: " + var1 + '.'));
      } else {
         return var1.lessThanOrEquals(var0, var1.getStart()) && !var1.lessThanOrEquals(var1.getStart(), var0) ? var1.getStart() : (var1.lessThanOrEquals(var1.getEndInclusive(), var0) && !var1.lessThanOrEquals(var0, var1.getEndInclusive()) ? var1.getEndInclusive() : var0);
      }
   }

   @NotNull
   public static final Comparable coerceIn(@NotNull Comparable var0, @NotNull ClosedRange var1) {
      if (var1 instanceof ClosedFloatingPointRange) {
         return RangesKt.coerceIn(var0, (ClosedFloatingPointRange)var1);
      } else if (var1.isEmpty()) {
         throw (Throwable)(new IllegalArgumentException("Cannot coerce value to an empty range: " + var1 + '.'));
      } else {
         return var0.compareTo(var1.getStart()) < 0 ? var1.getStart() : (var0.compareTo(var1.getEndInclusive()) > 0 ? var1.getEndInclusive() : var0);
      }
   }

   public static final int coerceIn(int var0, @NotNull ClosedRange var1) {
      if (var1 instanceof ClosedFloatingPointRange) {
         return ((Number)RangesKt.coerceIn((Comparable)var0, (ClosedFloatingPointRange)var1)).intValue();
      } else if (var1.isEmpty()) {
         throw (Throwable)(new IllegalArgumentException("Cannot coerce value to an empty range: " + var1 + '.'));
      } else {
         return var0 < ((Number)var1.getStart()).intValue() ? ((Number)var1.getStart()).intValue() : (var0 > ((Number)var1.getEndInclusive()).intValue() ? ((Number)var1.getEndInclusive()).intValue() : var0);
      }
   }

   public static final long coerceIn(long var0, @NotNull ClosedRange var2) {
      if (var2 instanceof ClosedFloatingPointRange) {
         return ((Number)RangesKt.coerceIn((Comparable)var0, (ClosedFloatingPointRange)var2)).longValue();
      } else if (var2.isEmpty()) {
         throw (Throwable)(new IllegalArgumentException("Cannot coerce value to an empty range: " + var2 + '.'));
      } else {
         return var0 < ((Number)var2.getStart()).longValue() ? ((Number)var2.getStart()).longValue() : (var0 > ((Number)var2.getEndInclusive()).longValue() ? ((Number)var2.getEndInclusive()).longValue() : var0);
      }
   }

   public RangesKt___RangesKt() {
      super();
   }
}
