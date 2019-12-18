package kotlin.text;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.AbstractCollection;
import kotlin.collections.CollectionsKt;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.functions.Function1;
import kotlin.ranges.IntRange;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000-\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010(\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u00012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0002J\u0013\u0010\b\u001a\u0004\u0018\u00010\u00032\u0006\u0010\t\u001a\u00020\u0005H\u0096\u0002J\u0013\u0010\b\u001a\u0004\u0018\u00010\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0096\u0002J\b\u0010\f\u001a\u00020\rH\u0016J\u0011\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u000fH\u0096\u0002R\u0014\u0010\u0004\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0010"},
   d2 = {"kotlin/text/MatcherMatchResult$groups$1", "Lkotlin/text/MatchNamedGroupCollection;", "Lkotlin/collections/AbstractCollection;", "Lkotlin/text/MatchGroup;", "size", "", "getSize", "()I", "get", "index", "name", "", "isEmpty", "", "iterator", "", "kotlin-stdlib"}
)
public final class MatcherMatchResult$groups$1 extends AbstractCollection implements MatchNamedGroupCollection {
   final MatcherMatchResult this$0;

   public int getSize() {
      return MatcherMatchResult.access$getMatchResult$p(this.this$0).groupCount() + 1;
   }

   public boolean isEmpty() {
      return false;
   }

   @NotNull
   public Iterator iterator() {
      return SequencesKt.map(CollectionsKt.asSequence((Iterable)CollectionsKt.getIndices(this)), (Function1)(new MatcherMatchResult$groups$1$iterator$1(this))).iterator();
   }

   @Nullable
   public MatchGroup get(int var1) {
      IntRange var2 = RegexKt.access$range(MatcherMatchResult.access$getMatchResult$p(this.this$0), var1);
      return var2.getStart() >= 0 ? new MatchGroup(MatcherMatchResult.access$getMatchResult$p(this.this$0).group(var1), var2) : null;
   }

   @Nullable
   public MatchGroup get(@NotNull String var1) {
      return PlatformImplementationsKt.IMPLEMENTATIONS.getMatchResultNamedGroup(MatcherMatchResult.access$getMatchResult$p(this.this$0), var1);
   }

   MatcherMatchResult$groups$1(MatcherMatchResult var1) {
      super();
      this.this$0 = var1;
   }

   public boolean contains(MatchGroup var1) {
      return super.contains(var1);
   }

   public final boolean contains(Object var1) {
      return (var1 != null ? var1 instanceof MatchGroup : true) ? this.contains((MatchGroup)var1) : false;
   }
}
