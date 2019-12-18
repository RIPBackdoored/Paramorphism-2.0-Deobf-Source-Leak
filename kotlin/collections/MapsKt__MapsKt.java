package kotlin.collections;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000~\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010%\n\u0000\n\u0002\u0010&\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010(\n\u0002\u0010)\n\u0002\u0010'\n\u0002\b\n\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0016\u001a\u001e\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005\u001a1\u0010\u0006\u001a\u001e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0007j\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u0005`\b\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005H\u0087\b\u001a_\u0010\u0006\u001a\u001e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0007j\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u0005`\b\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u00052*\u0010\t\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0\n\"\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b¢\u0006\u0002\u0010\f\u001a1\u0010\r\u001a\u001e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000ej\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u0005`\u000f\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005H\u0087\b\u001a_\u0010\r\u001a\u001e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000ej\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u0005`\u000f\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u00052*\u0010\t\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0\n\"\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b¢\u0006\u0002\u0010\u0010\u001a\u0010\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u0001H\u0001\u001a!\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005H\u0087\b\u001aO\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u00052*\u0010\t\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0\n\"\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b¢\u0006\u0002\u0010\u0014\u001a!\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0016\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005H\u0087\b\u001aO\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0016\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u00052*\u0010\t\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0\n\"\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b¢\u0006\u0002\u0010\u0014\u001a*\u0010\u0017\u001a\u0002H\u0004\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0018H\u0087\n¢\u0006\u0002\u0010\u0019\u001a*\u0010\u001a\u001a\u0002H\u0005\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0018H\u0087\n¢\u0006\u0002\u0010\u0019\u001a9\u0010\u001b\u001a\u00020\u001c\"\t\b\u0000\u0010\u0004¢\u0006\u0002\b\u001d\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0006\u0010\u001e\u001a\u0002H\u0004H\u0087\n¢\u0006\u0002\u0010\u001f\u001a1\u0010 \u001a\u00020\u001c\"\t\b\u0000\u0010\u0004¢\u0006\u0002\b\u001d*\u000e\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0002\b\u00030\u00032\u0006\u0010\u001e\u001a\u0002H\u0004H\u0087\b¢\u0006\u0002\u0010\u001f\u001a7\u0010!\u001a\u00020\u001c\"\u0004\b\u0000\u0010\u0004\"\t\b\u0001\u0010\u0005¢\u0006\u0002\b\u001d*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0006\u0010\"\u001a\u0002H\u0005H\u0087\b¢\u0006\u0002\u0010\u001f\u001aS\u0010#\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u001e\u0010$\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0018\u0012\u0004\u0012\u00020\u001c0%H\u0086\b\u001aG\u0010&\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0012\u0010$\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u00020\u001c0%H\u0086\b\u001aS\u0010'\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u001e\u0010$\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0018\u0012\u0004\u0012\u00020\u001c0%H\u0086\b\u001an\u0010(\u001a\u0002H)\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005\"\u0018\b\u0002\u0010)*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0004\u0012\u0006\b\u0000\u0012\u0002H\u00050\u0016*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0006\u0010*\u001a\u0002H)2\u001e\u0010$\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0018\u0012\u0004\u0012\u00020\u001c0%H\u0086\b¢\u0006\u0002\u0010+\u001an\u0010,\u001a\u0002H)\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005\"\u0018\b\u0002\u0010)*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0004\u0012\u0006\b\u0000\u0012\u0002H\u00050\u0016*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0006\u0010*\u001a\u0002H)2\u001e\u0010$\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0018\u0012\u0004\u0012\u00020\u001c0%H\u0086\b¢\u0006\u0002\u0010+\u001aG\u0010-\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0012\u0010$\u001a\u000e\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u00020\u001c0%H\u0086\b\u001a;\u0010.\u001a\u0004\u0018\u0001H\u0005\"\t\b\u0000\u0010\u0004¢\u0006\u0002\b\u001d\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0006\u0010\u001e\u001a\u0002H\u0004H\u0087\n¢\u0006\u0002\u0010/\u001a@\u00100\u001a\u0002H\u0005\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0006\u0010\u001e\u001a\u0002H\u00042\f\u00101\u001a\b\u0012\u0004\u0012\u0002H\u000502H\u0087\b¢\u0006\u0002\u00103\u001a@\u00104\u001a\u0002H\u0005\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0006\u0010\u001e\u001a\u0002H\u00042\f\u00101\u001a\b\u0012\u0004\u0012\u0002H\u000502H\u0080\b¢\u0006\u0002\u00103\u001a@\u00105\u001a\u0002H\u0005\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00162\u0006\u0010\u001e\u001a\u0002H\u00042\f\u00101\u001a\b\u0012\u0004\u0012\u0002H\u000502H\u0086\b¢\u0006\u0002\u00103\u001a1\u00106\u001a\u0002H\u0005\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0006\u0010\u001e\u001a\u0002H\u0004H\u0007¢\u0006\u0002\u0010/\u001a<\u00107\u001a\u0002H8\"\u0014\b\u0000\u0010)*\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0003*\u0002H8\"\u0004\b\u0001\u00108*\u0002H)2\f\u00101\u001a\b\u0012\u0004\u0012\u0002H802H\u0087\b¢\u0006\u0002\u00109\u001a'\u0010:\u001a\u00020\u001c\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003H\u0087\b\u001a:\u0010;\u001a\u00020\u001c\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0012\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u0005\u0018\u00010\u0003H\u0087\b\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000\u001a9\u0010<\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00180=\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003H\u0087\n\u001a<\u0010<\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050?0>\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0016H\u0087\n¢\u0006\u0002\b@\u001aY\u0010A\u001a\u000e\u0012\u0004\u0012\u0002H8\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005\"\u0004\b\u0002\u00108*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u001e\u0010B\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0018\u0012\u0004\u0012\u0002H80%H\u0086\b\u001at\u0010C\u001a\u0002H)\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005\"\u0004\b\u0002\u00108\"\u0018\b\u0003\u0010)*\u0012\u0012\u0006\b\u0000\u0012\u0002H8\u0012\u0006\b\u0000\u0012\u0002H\u00050\u0016*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0006\u0010*\u001a\u0002H)2\u001e\u0010B\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0018\u0012\u0004\u0012\u0002H80%H\u0086\b¢\u0006\u0002\u0010+\u001aY\u0010D\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H80\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005\"\u0004\b\u0002\u00108*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u001e\u0010B\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0018\u0012\u0004\u0012\u0002H80%H\u0086\b\u001at\u0010E\u001a\u0002H)\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005\"\u0004\b\u0002\u00108\"\u0018\b\u0003\u0010)*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0004\u0012\u0006\b\u0000\u0012\u0002H80\u0016*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0006\u0010*\u001a\u0002H)2\u001e\u0010B\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0018\u0012\u0004\u0012\u0002H80%H\u0086\b¢\u0006\u0002\u0010+\u001a@\u0010F\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0006\u0010\u001e\u001a\u0002H\u0004H\u0087\u0002¢\u0006\u0002\u0010G\u001aH\u0010F\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u000e\u0010H\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00040\nH\u0087\u0002¢\u0006\u0002\u0010I\u001aA\u0010F\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\f\u0010H\u001a\b\u0012\u0004\u0012\u0002H\u00040JH\u0087\u0002\u001aA\u0010F\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\f\u0010H\u001a\b\u0012\u0004\u0012\u0002H\u00040KH\u0087\u0002\u001a2\u0010L\u001a\u00020M\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00162\u0006\u0010\u001e\u001a\u0002H\u0004H\u0087\n¢\u0006\u0002\u0010N\u001a:\u0010L\u001a\u00020M\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00162\u000e\u0010H\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00040\nH\u0087\n¢\u0006\u0002\u0010O\u001a3\u0010L\u001a\u00020M\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00162\f\u0010H\u001a\b\u0012\u0004\u0012\u0002H\u00040JH\u0087\n\u001a3\u0010L\u001a\u00020M\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00162\f\u0010H\u001a\b\u0012\u0004\u0012\u0002H\u00040KH\u0087\n\u001a0\u0010P\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003H\u0000\u001a3\u0010Q\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u0005\u0018\u00010\u0003H\u0087\b\u001aT\u0010R\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u001a\u0010\t\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0\nH\u0086\u0002¢\u0006\u0002\u0010S\u001aG\u0010R\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0012\u0010T\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000bH\u0086\u0002\u001aM\u0010R\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0018\u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0JH\u0086\u0002\u001aI\u0010R\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0014\u0010U\u001a\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003H\u0086\u0002\u001aM\u0010R\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0018\u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0KH\u0086\u0002\u001aJ\u0010V\u001a\u00020M\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0004\u0012\u0006\b\u0000\u0012\u0002H\u00050\u00162\u001a\u0010\t\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0\nH\u0087\n¢\u0006\u0002\u0010W\u001a=\u0010V\u001a\u00020M\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0004\u0012\u0006\b\u0000\u0012\u0002H\u00050\u00162\u0012\u0010T\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000bH\u0087\n\u001aC\u0010V\u001a\u00020M\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0004\u0012\u0006\b\u0000\u0012\u0002H\u00050\u00162\u0018\u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0JH\u0087\n\u001a=\u0010V\u001a\u00020M\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0004\u0012\u0006\b\u0000\u0012\u0002H\u00050\u00162\u0012\u0010U\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003H\u0087\n\u001aC\u0010V\u001a\u00020M\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0004\u0012\u0006\b\u0000\u0012\u0002H\u00050\u00162\u0018\u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0KH\u0087\n\u001aG\u0010X\u001a\u00020M\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0004\u0012\u0006\b\u0000\u0012\u0002H\u00050\u00162\u001a\u0010\t\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0\n¢\u0006\u0002\u0010W\u001a@\u0010X\u001a\u00020M\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0004\u0012\u0006\b\u0000\u0012\u0002H\u00050\u00162\u0018\u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0J\u001a@\u0010X\u001a\u00020M\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0004\u0012\u0006\b\u0000\u0012\u0002H\u00050\u00162\u0018\u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0K\u001a;\u0010Y\u001a\u0004\u0018\u0001H\u0005\"\t\b\u0000\u0010\u0004¢\u0006\u0002\b\u001d\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00162\u0006\u0010\u001e\u001a\u0002H\u0004H\u0087\b¢\u0006\u0002\u0010/\u001a:\u0010Z\u001a\u00020M\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00162\u0006\u0010\u001e\u001a\u0002H\u00042\u0006\u0010\"\u001a\u0002H\u0005H\u0087\n¢\u0006\u0002\u0010[\u001a;\u0010\\\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0\n¢\u0006\u0002\u0010\u0014\u001aQ\u0010\\\u001a\u0002H)\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005\"\u0018\b\u0002\u0010)*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0004\u0012\u0006\b\u0000\u0012\u0002H\u00050\u0016*\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0\n2\u0006\u0010*\u001a\u0002H)¢\u0006\u0002\u0010]\u001a4\u0010\\\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0J\u001aO\u0010\\\u001a\u0002H)\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005\"\u0018\b\u0002\u0010)*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0004\u0012\u0006\b\u0000\u0012\u0002H\u00050\u0016*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0J2\u0006\u0010*\u001a\u0002H)¢\u0006\u0002\u0010^\u001a2\u0010\\\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003H\u0007\u001aM\u0010\\\u001a\u0002H)\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005\"\u0018\b\u0002\u0010)*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0004\u0012\u0006\b\u0000\u0012\u0002H\u00050\u0016*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0006\u0010*\u001a\u0002H)H\u0007¢\u0006\u0002\u0010_\u001a4\u0010\\\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0K\u001aO\u0010\\\u001a\u0002H)\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005\"\u0018\b\u0002\u0010)*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0004\u0012\u0006\b\u0000\u0012\u0002H\u00050\u0016*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0K2\u0006\u0010*\u001a\u0002H)¢\u0006\u0002\u0010`\u001a2\u0010a\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0016\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003H\u0007\u001a1\u0010b\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0018H\u0087\b\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000¨\u0006c"},
   d2 = {"INT_MAX_POWER_OF_TWO", "", "emptyMap", "", "K", "V", "hashMapOf", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "pairs", "", "Lkotlin/Pair;", "([Lkotlin/Pair;)Ljava/util/HashMap;", "linkedMapOf", "Ljava/util/LinkedHashMap;", "Lkotlin/collections/LinkedHashMap;", "([Lkotlin/Pair;)Ljava/util/LinkedHashMap;", "mapCapacity", "expectedSize", "mapOf", "([Lkotlin/Pair;)Ljava/util/Map;", "mutableMapOf", "", "component1", "", "(Ljava/util/Map$Entry;)Ljava/lang/Object;", "component2", "contains", "", "Lkotlin/internal/OnlyInputTypes;", "key", "(Ljava/util/Map;Ljava/lang/Object;)Z", "containsKey", "containsValue", "value", "filter", "predicate", "Lkotlin/Function1;", "filterKeys", "filterNot", "filterNotTo", "M", "destination", "(Ljava/util/Map;Ljava/util/Map;Lkotlin/jvm/functions/Function1;)Ljava/util/Map;", "filterTo", "filterValues", "get", "(Ljava/util/Map;Ljava/lang/Object;)Ljava/lang/Object;", "getOrElse", "defaultValue", "Lkotlin/Function0;", "(Ljava/util/Map;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "getOrElseNullable", "getOrPut", "getValue", "ifEmpty", "R", "(Ljava/util/Map;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "isNotEmpty", "isNullOrEmpty", "iterator", "", "", "", "mutableIterator", "mapKeys", "transform", "mapKeysTo", "mapValues", "mapValuesTo", "minus", "(Ljava/util/Map;Ljava/lang/Object;)Ljava/util/Map;", "keys", "(Ljava/util/Map;[Ljava/lang/Object;)Ljava/util/Map;", "", "Lkotlin/sequences/Sequence;", "minusAssign", "", "(Ljava/util/Map;Ljava/lang/Object;)V", "(Ljava/util/Map;[Ljava/lang/Object;)V", "optimizeReadOnlyMap", "orEmpty", "plus", "(Ljava/util/Map;[Lkotlin/Pair;)Ljava/util/Map;", "pair", "map", "plusAssign", "(Ljava/util/Map;[Lkotlin/Pair;)V", "putAll", "remove", "set", "(Ljava/util/Map;Ljava/lang/Object;Ljava/lang/Object;)V", "toMap", "([Lkotlin/Pair;Ljava/util/Map;)Ljava/util/Map;", "(Ljava/lang/Iterable;Ljava/util/Map;)Ljava/util/Map;", "(Ljava/util/Map;Ljava/util/Map;)Ljava/util/Map;", "(Lkotlin/sequences/Sequence;Ljava/util/Map;)Ljava/util/Map;", "toMutableMap", "toPair", "kotlin-stdlib"},
   xs = "kotlin/collections/MapsKt"
)
class MapsKt__MapsKt extends MapsKt__MapsJVMKt {
   private static final int INT_MAX_POWER_OF_TWO = 1073741824;

   @NotNull
   public static final Map emptyMap() {
      EmptyMap var10000 = EmptyMap.INSTANCE;
      if (var10000 == null) {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.Map<K, V>");
      } else {
         return (Map)var10000;
      }
   }

   @NotNull
   public static final Map mapOf(@NotNull Pair... var0) {
      return var0.length > 0 ? MapsKt.toMap(var0, (Map)(new LinkedHashMap(MapsKt.mapCapacity(var0.length)))) : MapsKt.emptyMap();
   }

   @InlineOnly
   private static final Map mapOf() {
      byte var0 = 0;
      return MapsKt.emptyMap();
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final Map mutableMapOf() {
      byte var0 = 0;
      return (Map)(new LinkedHashMap());
   }

   @NotNull
   public static final Map mutableMapOf(@NotNull Pair... var0) {
      LinkedHashMap var1 = new LinkedHashMap(MapsKt.mapCapacity(var0.length));
      boolean var2 = false;
      boolean var3 = false;
      boolean var5 = false;
      MapsKt.putAll((Map)var1, var0);
      return (Map)var1;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final HashMap hashMapOf() {
      byte var0 = 0;
      return new HashMap();
   }

   @NotNull
   public static final HashMap hashMapOf(@NotNull Pair... var0) {
      HashMap var1 = new HashMap(MapsKt.mapCapacity(var0.length));
      boolean var2 = false;
      boolean var3 = false;
      boolean var5 = false;
      MapsKt.putAll((Map)var1, var0);
      return var1;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final LinkedHashMap linkedMapOf() {
      byte var0 = 0;
      return new LinkedHashMap();
   }

   @NotNull
   public static final LinkedHashMap linkedMapOf(@NotNull Pair... var0) {
      return (LinkedHashMap)MapsKt.toMap(var0, (Map)(new LinkedHashMap(MapsKt.mapCapacity(var0.length))));
   }

   @PublishedApi
   public static final int mapCapacity(int var0) {
      if (var0 < 3) {
         return var0 + 1;
      } else {
         return var0 < 1073741824 ? var0 + var0 / 3 : 0;
      }
   }

   @InlineOnly
   private static final boolean isNotEmpty(@NotNull Map var0) {
      byte var1 = 0;
      return !var0.isEmpty();
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final boolean isNullOrEmpty(@Nullable Map var0) {
      byte var1 = 0;
      boolean var2 = false;
      return var0 == null || var0.isEmpty();
   }

   @InlineOnly
   private static final Map orEmpty(@Nullable Map var0) {
      byte var1 = 0;
      Map var10000 = var0;
      if (var0 == null) {
         var10000 = MapsKt.emptyMap();
      }

      return var10000;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final Object ifEmpty(Map var0, Function0 var1) {
      byte var2 = 0;
      return var0.isEmpty() ? var1.invoke() : var0;
   }

   @InlineOnly
   private static final boolean contains(@NotNull Map var0, Object var1) {
      byte var2 = 0;
      boolean var4 = false;
      return var0.containsKey(var1);
   }

   @InlineOnly
   private static final Object get(@NotNull Map var0, Object var1) {
      byte var2 = 0;
      return var0.get(var1);
   }

   @InlineOnly
   private static final void set(@NotNull Map var0, Object var1, Object var2) {
      byte var3 = 0;
      var0.put(var1, var2);
   }

   @InlineOnly
   private static final boolean containsKey(@NotNull Map var0, Object var1) {
      return var0.containsKey(var1);
   }

   @InlineOnly
   private static final boolean containsValue(@NotNull Map var0, Object var1) {
      byte var2 = 0;
      return var0.containsValue(var1);
   }

   @InlineOnly
   private static final Object remove(@NotNull Map var0, Object var1) {
      return TypeIntrinsics.asMutableMap(var0).remove(var1);
   }

   @InlineOnly
   private static final Object component1(@NotNull Entry var0) {
      byte var1 = 0;
      return var0.getKey();
   }

   @InlineOnly
   private static final Object component2(@NotNull Entry var0) {
      byte var1 = 0;
      return var0.getValue();
   }

   @InlineOnly
   private static final Pair toPair(@NotNull Entry var0) {
      byte var1 = 0;
      return new Pair(var0.getKey(), var0.getValue());
   }

   @InlineOnly
   private static final Object getOrElse(@NotNull Map var0, Object var1, Function0 var2) {
      byte var3 = 0;
      Object var10000 = var0.get(var1);
      if (var10000 == null) {
         var10000 = var2.invoke();
      }

      return var10000;
   }

   public static final Object getOrElseNullable(@NotNull Map var0, Object var1, @NotNull Function0 var2) {
      byte var3 = 0;
      Object var4 = var0.get(var1);
      return var4 == null && !var0.containsKey(var1) ? var2.invoke() : var4;
   }

   @SinceKotlin(
      version = "1.1"
   )
   public static final Object getValue(@NotNull Map var0, Object var1) {
      return MapsKt.getOrImplicitDefaultNullable(var0, var1);
   }

   public static final Object getOrPut(@NotNull Map var0, Object var1, @NotNull Function0 var2) {
      byte var3 = 0;
      Object var4 = var0.get(var1);
      Object var10000;
      if (var4 == null) {
         Object var5 = var2.invoke();
         var0.put(var1, var5);
         var10000 = var5;
      } else {
         var10000 = var4;
      }

      return var10000;
   }

   @InlineOnly
   private static final Iterator iterator(@NotNull Map var0) {
      byte var1 = 0;
      return var0.entrySet().iterator();
   }

   @JvmName(
      name = "mutableIterator"
   )
   @InlineOnly
   private static final Iterator mutableIterator(@NotNull Map var0) {
      byte var1 = 0;
      return var0.entrySet().iterator();
   }

   @NotNull
   public static final Map mapValuesTo(@NotNull Map var0, @NotNull Map var1, @NotNull Function1 var2) {
      byte var3 = 0;
      Iterable var4 = (Iterable)var0.entrySet();
      boolean var5 = false;
      Iterator var6 = var4.iterator();

      while(var6.hasNext()) {
         Object var7 = var6.next();
         Entry var8 = (Entry)var7;
         boolean var9 = false;
         Object var11 = var8.getKey();
         var1.put(var11, var2.invoke(var7));
      }

      return var1;
   }

   @NotNull
   public static final Map mapKeysTo(@NotNull Map var0, @NotNull Map var1, @NotNull Function1 var2) {
      byte var3 = 0;
      Iterable var4 = (Iterable)var0.entrySet();
      boolean var5 = false;
      Iterator var6 = var4.iterator();

      while(var6.hasNext()) {
         Object var7 = var6.next();
         Object var10001 = var2.invoke(var7);
         Entry var8 = (Entry)var7;
         Object var11 = var10001;
         boolean var9 = false;
         Object var12 = var8.getValue();
         var1.put(var11, var12);
      }

      return var1;
   }

   public static final void putAll(@NotNull Map var0, @NotNull Pair[] var1) {
      Pair[] var4 = var1;
      int var5 = var1.length;

      for(int var3 = 0; var3 < var5; ++var3) {
         Pair var2 = var4[var3];
         Object var6 = var2.component1();
         Object var7 = var2.component2();
         var0.put(var6, var7);
      }

   }

   public static final void putAll(@NotNull Map var0, @NotNull Iterable var1) {
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         Pair var2 = (Pair)var3.next();
         Object var4 = var2.component1();
         Object var5 = var2.component2();
         var0.put(var4, var5);
      }

   }

   public static final void putAll(@NotNull Map var0, @NotNull Sequence var1) {
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         Pair var2 = (Pair)var3.next();
         Object var4 = var2.component1();
         Object var5 = var2.component2();
         var0.put(var4, var5);
      }

   }

   @NotNull
   public static final Map mapValues(@NotNull Map var0, @NotNull Function1 var1) {
      byte var2 = 0;
      Map var4 = (Map)(new LinkedHashMap(MapsKt.mapCapacity(var0.size())));
      boolean var5 = false;
      Iterable var6 = (Iterable)var0.entrySet();
      boolean var7 = false;
      Iterator var8 = var6.iterator();

      while(var8.hasNext()) {
         Object var9 = var8.next();
         Entry var10 = (Entry)var9;
         boolean var12 = false;
         Object var13 = var10.getKey();
         var4.put(var13, var1.invoke(var9));
      }

      return var4;
   }

   @NotNull
   public static final Map mapKeys(@NotNull Map var0, @NotNull Function1 var1) {
      byte var2 = 0;
      Map var4 = (Map)(new LinkedHashMap(MapsKt.mapCapacity(var0.size())));
      boolean var5 = false;
      Iterable var6 = (Iterable)var0.entrySet();
      boolean var7 = false;
      Iterator var8 = var6.iterator();

      while(var8.hasNext()) {
         Object var9 = var8.next();
         Object var10001 = var1.invoke(var9);
         Entry var10 = (Entry)var9;
         Object var11 = var10001;
         boolean var13 = false;
         Object var14 = var10.getValue();
         var4.put(var11, var14);
      }

      return var4;
   }

   @NotNull
   public static final Map filterKeys(@NotNull Map var0, @NotNull Function1 var1) {
      byte var2 = 0;
      LinkedHashMap var3 = new LinkedHashMap();
      boolean var7 = false;
      Iterator var5 = var0.entrySet().iterator();

      while(var5.hasNext()) {
         Entry var4 = (Entry)var5.next();
         if ((Boolean)var1.invoke(var4.getKey())) {
            var3.put(var4.getKey(), var4.getValue());
         }
      }

      return (Map)var3;
   }

   @NotNull
   public static final Map filterValues(@NotNull Map var0, @NotNull Function1 var1) {
      byte var2 = 0;
      LinkedHashMap var3 = new LinkedHashMap();
      boolean var7 = false;
      Iterator var5 = var0.entrySet().iterator();

      while(var5.hasNext()) {
         Entry var4 = (Entry)var5.next();
         if ((Boolean)var1.invoke(var4.getValue())) {
            var3.put(var4.getKey(), var4.getValue());
         }
      }

      return (Map)var3;
   }

   @NotNull
   public static final Map filterTo(@NotNull Map var0, @NotNull Map var1, @NotNull Function1 var2) {
      byte var3 = 0;
      boolean var7 = false;
      Iterator var5 = var0.entrySet().iterator();

      while(var5.hasNext()) {
         Entry var4 = (Entry)var5.next();
         if ((Boolean)var2.invoke(var4)) {
            var1.put(var4.getKey(), var4.getValue());
         }
      }

      return var1;
   }

   @NotNull
   public static final Map filter(@NotNull Map var0, @NotNull Function1 var1) {
      byte var2 = 0;
      Map var4 = (Map)(new LinkedHashMap());
      boolean var5 = false;
      boolean var7 = false;
      Iterator var8 = var0.entrySet().iterator();

      while(var8.hasNext()) {
         Entry var9 = (Entry)var8.next();
         if ((Boolean)var1.invoke(var9)) {
            var4.put(var9.getKey(), var9.getValue());
         }
      }

      return var4;
   }

   @NotNull
   public static final Map filterNotTo(@NotNull Map var0, @NotNull Map var1, @NotNull Function1 var2) {
      byte var3 = 0;
      boolean var7 = false;
      Iterator var5 = var0.entrySet().iterator();

      while(var5.hasNext()) {
         Entry var4 = (Entry)var5.next();
         if (!(Boolean)var2.invoke(var4)) {
            var1.put(var4.getKey(), var4.getValue());
         }
      }

      return var1;
   }

   @NotNull
   public static final Map filterNot(@NotNull Map var0, @NotNull Function1 var1) {
      byte var2 = 0;
      Map var4 = (Map)(new LinkedHashMap());
      boolean var5 = false;
      boolean var7 = false;
      Iterator var8 = var0.entrySet().iterator();

      while(var8.hasNext()) {
         Entry var9 = (Entry)var8.next();
         if (!(Boolean)var1.invoke(var9)) {
            var4.put(var9.getKey(), var9.getValue());
         }
      }

      return var4;
   }

   @NotNull
   public static final Map toMap(@NotNull Iterable var0) {
      if (var0 instanceof Collection) {
         Map var10000;
         switch(((Collection)var0).size()) {
         case 0:
            var10000 = MapsKt.emptyMap();
            break;
         case 1:
            var10000 = MapsKt.mapOf(var0 instanceof List ? (Pair)((List)var0).get(0) : (Pair)var0.iterator().next());
            break;
         default:
            var10000 = MapsKt.toMap(var0, (Map)(new LinkedHashMap(MapsKt.mapCapacity(((Collection)var0).size()))));
         }

         return var10000;
      } else {
         return MapsKt.optimizeReadOnlyMap(MapsKt.toMap(var0, (Map)(new LinkedHashMap())));
      }
   }

   @NotNull
   public static final Map toMap(@NotNull Iterable var0, @NotNull Map var1) {
      boolean var3 = false;
      boolean var4 = false;
      boolean var6 = false;
      MapsKt.putAll(var1, var0);
      return var1;
   }

   @NotNull
   public static final Map toMap(@NotNull Pair[] var0) {
      Map var10000;
      switch(var0.length) {
      case 0:
         var10000 = MapsKt.emptyMap();
         break;
      case 1:
         var10000 = MapsKt.mapOf(var0[0]);
         break;
      default:
         var10000 = MapsKt.toMap(var0, (Map)(new LinkedHashMap(MapsKt.mapCapacity(var0.length))));
      }

      return var10000;
   }

   @NotNull
   public static final Map toMap(@NotNull Pair[] var0, @NotNull Map var1) {
      boolean var3 = false;
      boolean var4 = false;
      boolean var6 = false;
      MapsKt.putAll(var1, var0);
      return var1;
   }

   @NotNull
   public static final Map toMap(@NotNull Sequence var0) {
      return MapsKt.optimizeReadOnlyMap(MapsKt.toMap(var0, (Map)(new LinkedHashMap())));
   }

   @NotNull
   public static final Map toMap(@NotNull Sequence var0, @NotNull Map var1) {
      boolean var3 = false;
      boolean var4 = false;
      boolean var6 = false;
      MapsKt.putAll(var1, var0);
      return var1;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Map toMap(@NotNull Map var0) {
      Map var10000;
      switch(var0.size()) {
      case 0:
         var10000 = MapsKt.emptyMap();
         break;
      case 1:
         var10000 = MapsKt.toSingletonMap(var0);
         break;
      default:
         var10000 = MapsKt.toMutableMap(var0);
      }

      return var10000;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Map toMutableMap(@NotNull Map var0) {
      return (Map)(new LinkedHashMap(var0));
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Map toMap(@NotNull Map var0, @NotNull Map var1) {
      boolean var3 = false;
      boolean var4 = false;
      boolean var6 = false;
      var1.putAll(var0);
      return var1;
   }

   @NotNull
   public static final Map plus(@NotNull Map var0, @NotNull Pair var1) {
      Map var10000;
      if (var0.isEmpty()) {
         var10000 = MapsKt.mapOf(var1);
      } else {
         LinkedHashMap var2 = new LinkedHashMap(var0);
         boolean var3 = false;
         boolean var4 = false;
         boolean var6 = false;
         var2.put(var1.getFirst(), var1.getSecond());
         var10000 = (Map)var2;
      }

      return var10000;
   }

   @NotNull
   public static final Map plus(@NotNull Map var0, @NotNull Iterable var1) {
      Map var10000;
      if (var0.isEmpty()) {
         var10000 = MapsKt.toMap(var1);
      } else {
         LinkedHashMap var2 = new LinkedHashMap(var0);
         boolean var3 = false;
         boolean var4 = false;
         boolean var6 = false;
         MapsKt.putAll((Map)var2, var1);
         var10000 = (Map)var2;
      }

      return var10000;
   }

   @NotNull
   public static final Map plus(@NotNull Map var0, @NotNull Pair[] var1) {
      Map var10000;
      if (var0.isEmpty()) {
         var10000 = MapsKt.toMap(var1);
      } else {
         LinkedHashMap var2 = new LinkedHashMap(var0);
         boolean var3 = false;
         boolean var4 = false;
         boolean var6 = false;
         MapsKt.putAll((Map)var2, var1);
         var10000 = (Map)var2;
      }

      return var10000;
   }

   @NotNull
   public static final Map plus(@NotNull Map var0, @NotNull Sequence var1) {
      LinkedHashMap var2 = new LinkedHashMap(var0);
      boolean var3 = false;
      boolean var4 = false;
      boolean var6 = false;
      MapsKt.putAll((Map)var2, var1);
      return MapsKt.optimizeReadOnlyMap((Map)var2);
   }

   @NotNull
   public static final Map plus(@NotNull Map var0, @NotNull Map var1) {
      LinkedHashMap var2 = new LinkedHashMap(var0);
      boolean var3 = false;
      boolean var4 = false;
      boolean var6 = false;
      var2.putAll(var1);
      return (Map)var2;
   }

   @InlineOnly
   private static final void plusAssign(@NotNull Map var0, Pair var1) {
      byte var2 = 0;
      var0.put(var1.getFirst(), var1.getSecond());
   }

   @InlineOnly
   private static final void plusAssign(@NotNull Map var0, Iterable var1) {
      byte var2 = 0;
      MapsKt.putAll(var0, var1);
   }

   @InlineOnly
   private static final void plusAssign(@NotNull Map var0, Pair[] var1) {
      byte var2 = 0;
      MapsKt.putAll(var0, var1);
   }

   @InlineOnly
   private static final void plusAssign(@NotNull Map var0, Sequence var1) {
      byte var2 = 0;
      MapsKt.putAll(var0, var1);
   }

   @InlineOnly
   private static final void plusAssign(@NotNull Map var0, Map var1) {
      byte var2 = 0;
      var0.putAll(var1);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Map minus(@NotNull Map var0, Object var1) {
      Map var2 = MapsKt.toMutableMap(var0);
      boolean var3 = false;
      boolean var4 = false;
      boolean var6 = false;
      boolean var9 = false;
      var2.remove(var1);
      return MapsKt.optimizeReadOnlyMap(var2);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Map minus(@NotNull Map var0, @NotNull Iterable var1) {
      Map var2 = MapsKt.toMutableMap(var0);
      boolean var3 = false;
      boolean var4 = false;
      boolean var6 = false;
      boolean var9 = false;
      CollectionsKt.removeAll((Collection)var2.keySet(), var1);
      return MapsKt.optimizeReadOnlyMap(var2);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Map minus(@NotNull Map var0, @NotNull Object[] var1) {
      Map var2 = MapsKt.toMutableMap(var0);
      boolean var3 = false;
      boolean var4 = false;
      boolean var6 = false;
      boolean var9 = false;
      CollectionsKt.removeAll((Collection)var2.keySet(), var1);
      return MapsKt.optimizeReadOnlyMap(var2);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Map minus(@NotNull Map var0, @NotNull Sequence var1) {
      Map var2 = MapsKt.toMutableMap(var0);
      boolean var3 = false;
      boolean var4 = false;
      boolean var6 = false;
      boolean var9 = false;
      CollectionsKt.removeAll((Collection)var2.keySet(), var1);
      return MapsKt.optimizeReadOnlyMap(var2);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final void minusAssign(@NotNull Map var0, Object var1) {
      byte var2 = 0;
      var0.remove(var1);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final void minusAssign(@NotNull Map var0, Iterable var1) {
      byte var2 = 0;
      CollectionsKt.removeAll((Collection)var0.keySet(), var1);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final void minusAssign(@NotNull Map var0, Object[] var1) {
      byte var2 = 0;
      CollectionsKt.removeAll((Collection)var0.keySet(), var1);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final void minusAssign(@NotNull Map var0, Sequence var1) {
      byte var2 = 0;
      CollectionsKt.removeAll((Collection)var0.keySet(), var1);
   }

   @NotNull
   public static final Map optimizeReadOnlyMap(@NotNull Map var0) {
      Map var10000;
      switch(var0.size()) {
      case 0:
         var10000 = MapsKt.emptyMap();
         break;
      case 1:
         boolean var2 = false;
         var10000 = MapsKt.toSingletonMap(var0);
         break;
      default:
         var10000 = var0;
      }

      return var10000;
   }

   public MapsKt__MapsKt() {
      super();
   }
}
