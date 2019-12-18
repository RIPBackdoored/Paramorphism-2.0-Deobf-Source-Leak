package kotlin.collections;

import org.jetbrains.annotations.*;
import kotlin.internal.*;
import kotlin.jvm.functions.*;
import java.util.*;
import kotlin.collections.unsigned.*;
import kotlin.jvm.internal.*;
import kotlin.*;
import kotlin.jvm.*;
import kotlin.ranges.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 5, xi = 1, d1 = { "\u0000H\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a1\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u000e\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\u0001¢\u0006\u0004\b\u0005\u0010\u0006\u001a!\u0010\u0007\u001a\u00020\b\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\u0001¢\u0006\u0004\b\t\u0010\n\u001a?\u0010\u000b\u001a\u00020\f\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\n\u0010\r\u001a\u00060\u000ej\u0002`\u000f2\u0010\u0010\u0010\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0011H\u0002¢\u0006\u0004\b\u0012\u0010\u0013\u001a+\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0015\"\u0004\b\u0000\u0010\u0002*\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00030\u0003¢\u0006\u0002\u0010\u0016\u001a8\u0010\u0017\u001a\u0002H\u0018\"\u0010\b\u0000\u0010\u0019*\u0006\u0012\u0002\b\u00030\u0003*\u0002H\u0018\"\u0004\b\u0001\u0010\u0018*\u0002H\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00180\u001bH\u0087\b¢\u0006\u0002\u0010\u001c\u001a)\u0010\u001d\u001a\u00020\u0001*\b\u0012\u0002\b\u0003\u0018\u00010\u0003H\u0087\b\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000¢\u0006\u0002\u0010\u001e\u001aG\u0010\u001f\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0015\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00180\u00150 \"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0018*\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00180 0\u0003¢\u0006\u0002\u0010!¨\u0006\"" }, d2 = { "contentDeepEqualsImpl", "", "T", "", "other", "contentDeepEquals", "([Ljava/lang/Object;[Ljava/lang/Object;)Z", "contentDeepToStringImpl", "", "contentDeepToString", "([Ljava/lang/Object;)Ljava/lang/String;", "contentDeepToStringInternal", "", "result", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "processed", "", "contentDeepToStringInternal$ArraysKt__ArraysKt", "([Ljava/lang/Object;Ljava/lang/StringBuilder;Ljava/util/List;)V", "flatten", "", "([[Ljava/lang/Object;)Ljava/util/List;", "ifEmpty", "R", "C", "defaultValue", "Lkotlin/Function0;", "([Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "isNullOrEmpty", "([Ljava/lang/Object;)Z", "unzip", "Lkotlin/Pair;", "([Lkotlin/Pair;)Lkotlin/Pair;", "kotlin-stdlib" }, xs = "kotlin/collections/ArraysKt")
class ArraysKt__ArraysKt extends ArraysKt__ArraysJVMKt
{
    @NotNull
    public static final <T> List<T> flatten(@NotNull final T[][] array) {
        final T[][] array2 = array;
        int n = 0;
        final T[][] array3 = array2;
        for (int length = array3.length, i = 0; i < length; ++i) {
            n += array3[i].length;
        }
        final ArrayList list = new ArrayList<Object>(n);
        for (int length2 = array.length, j = 0; j < length2; ++j) {
            CollectionsKt__MutableCollectionsKt.addAll((Collection<? super T>)list, array[j]);
        }
        return (List<T>)list;
    }
    
    @NotNull
    public static final <T, R> Pair<List<T>, List<R>> unzip(@NotNull final Pair<? extends T, ? extends R>[] array) {
        final ArrayList<T> list = new ArrayList<T>(array.length);
        final ArrayList<R> list2 = new ArrayList<R>(array.length);
        for (final Pair<? extends T, ? extends R> pair : array) {
            list.add((T)pair.getFirst());
            list2.add((R)pair.getSecond());
        }
        return (Pair<List<T>, List<R>>)TuplesKt.to(list, list2);
    }
    
    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final boolean isNullOrEmpty(@Nullable final Object[] array) {
        return array == null || array.length == 0;
    }
    
    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final <C extends Object[], R> R ifEmpty(final C p0, final Function0<? extends R> p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     2: istore_2       
        //     3: aload_0        
        //     4: astore_3       
        //     5: iconst_0       
        //     6: istore          4
        //     8: aload_3        
        //     9: arraylength    
        //    10: ifne            17
        //    13: iconst_1       
        //    14: goto            18
        //    17: iconst_0       
        //    18: ifeq            30
        //    21: aload_1        
        //    22: invokeinterface kotlin/jvm/functions/Function0.invoke:()Ljava/lang/Object;
        //    27: goto            31
        //    30: aload_0        
        //    31: areturn        
        //    Signature:
        //  <C:[Ljava/lang/Object;:TR;R:Ljava/lang/Object;>(TC;Lkotlin/jvm/functions/Function0<+TR;>;)TR;
        //    StackMapTable: 00 04 FE 00 11 01 07 00 36 01 40 01 0B 40 07 00 6B
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @SinceKotlin(version = "1.3")
    @PublishedApi
    @JvmName(name = "contentDeepEquals")
    public static final <T> boolean contentDeepEquals(@NotNull final T[] array, @NotNull final T[] array2) {
        if (array == array2) {
            return true;
        }
        if (array.length != array2.length) {
            return false;
        }
        for (int i = 0; i < array.length; ++i) {
            final T t = array[i];
            final T t2 = array2[i];
            if (t != t2) {
                if (t == null || t2 == null) {
                    return false;
                }
                if (t instanceof Object[] && t2 instanceof Object[]) {
                    if (!contentDeepEquals((T[])(Object)t, (Object[])(T[])(Object)t2)) {
                        return false;
                    }
                }
                else if (t instanceof byte[] && t2 instanceof byte[]) {
                    if (!Arrays.equals((byte[])(Object)t, (byte[])(Object)t2)) {
                        return false;
                    }
                }
                else if (t instanceof short[] && t2 instanceof short[]) {
                    if (!Arrays.equals((short[])(Object)t, (short[])(Object)t2)) {
                        return false;
                    }
                }
                else if (t instanceof int[] && t2 instanceof int[]) {
                    if (!Arrays.equals((int[])(Object)t, (int[])(Object)t2)) {
                        return false;
                    }
                }
                else if (t instanceof long[] && t2 instanceof long[]) {
                    if (!Arrays.equals((long[])(Object)t, (long[])(Object)t2)) {
                        return false;
                    }
                }
                else if (t instanceof float[] && t2 instanceof float[]) {
                    if (!Arrays.equals((float[])(Object)t, (float[])(Object)t2)) {
                        return false;
                    }
                }
                else if (t instanceof double[] && t2 instanceof double[]) {
                    if (!Arrays.equals((double[])(Object)t, (double[])(Object)t2)) {
                        return false;
                    }
                }
                else if (t instanceof char[] && t2 instanceof char[]) {
                    if (!Arrays.equals((char[])(Object)t, (char[])(Object)t2)) {
                        return false;
                    }
                }
                else if (t instanceof boolean[] && t2 instanceof boolean[]) {
                    if (!Arrays.equals((boolean[])(Object)t, (boolean[])(Object)t2)) {
                        return false;
                    }
                }
                else if (t instanceof UByteArray && t2 instanceof UByteArray) {
                    if (!UArraysKt___UArraysKt.contentEquals-kdPth3s(((UByteArray)t).unbox-impl(), ((UByteArray)t2).unbox-impl())) {
                        return false;
                    }
                }
                else if (t instanceof UShortArray && t2 instanceof UShortArray) {
                    if (!UArraysKt___UArraysKt.contentEquals-mazbYpA(((UShortArray)t).unbox-impl(), ((UShortArray)t2).unbox-impl())) {
                        return false;
                    }
                }
                else if (t instanceof UIntArray && t2 instanceof UIntArray) {
                    if (!UArraysKt___UArraysKt.contentEquals-ctEhBpI(((UIntArray)t).unbox-impl(), ((UIntArray)t2).unbox-impl())) {
                        return false;
                    }
                }
                else if (t instanceof ULongArray && t2 instanceof ULongArray) {
                    if (!UArraysKt___UArraysKt.contentEquals-us8wMrg(((ULongArray)t).unbox-impl(), ((ULongArray)t2).unbox-impl())) {
                        return false;
                    }
                }
                else if (Intrinsics.areEqual(t, t2) ^ true) {
                    return false;
                }
            }
        }
        return true;
    }
    
    @SinceKotlin(version = "1.3")
    @PublishedApi
    @JvmName(name = "contentDeepToString")
    @NotNull
    public static final <T> String contentDeepToString(@NotNull final T[] array) {
        final StringBuilder sb = new StringBuilder(RangesKt___RangesKt.coerceAtMost(array.length, 429496729) * 5 + 2);
        contentDeepToStringInternal$ArraysKt__ArraysKt(array, sb, new ArrayList<Object[]>());
        return sb.toString();
    }
    
    private static final <T> void contentDeepToStringInternal$ArraysKt__ArraysKt(@NotNull final T[] array, final StringBuilder sb, final List<Object[]> list) {
        if (list.contains(array)) {
            sb.append("[...]");
            return;
        }
        list.add(array);
        sb.append('[');
        for (int i = 0; i < array.length; ++i) {
            if (i != 0) {
                sb.append(", ");
            }
            final T t2;
            final T t = t2 = array[i];
            if (t2 == null) {
                sb.append("null");
            }
            else if (t2 instanceof Object[]) {
                contentDeepToStringInternal$ArraysKt__ArraysKt((Object[])(Object)t, sb, list);
            }
            else if (t2 instanceof byte[]) {
                sb.append(Arrays.toString((byte[])(Object)t));
            }
            else if (t2 instanceof short[]) {
                sb.append(Arrays.toString((short[])(Object)t));
            }
            else if (t2 instanceof int[]) {
                sb.append(Arrays.toString((int[])(Object)t));
            }
            else if (t2 instanceof long[]) {
                sb.append(Arrays.toString((long[])(Object)t));
            }
            else if (t2 instanceof float[]) {
                sb.append(Arrays.toString((float[])(Object)t));
            }
            else if (t2 instanceof double[]) {
                sb.append(Arrays.toString((double[])(Object)t));
            }
            else if (t2 instanceof char[]) {
                sb.append(Arrays.toString((char[])(Object)t));
            }
            else if (t2 instanceof boolean[]) {
                sb.append(Arrays.toString((boolean[])(Object)t));
            }
            else if (t2 instanceof UByteArray) {
                sb.append(UArraysKt___UArraysKt.contentToString-GBYM_sE(((UByteArray)t).unbox-impl()));
            }
            else if (t2 instanceof UShortArray) {
                sb.append(UArraysKt___UArraysKt.contentToString-rL5Bavg(((UShortArray)t).unbox-impl()));
            }
            else if (t2 instanceof UIntArray) {
                sb.append(UArraysKt___UArraysKt.contentToString--ajY-9A(((UIntArray)t).unbox-impl()));
            }
            else if (t2 instanceof ULongArray) {
                sb.append(UArraysKt___UArraysKt.contentToString-QwZRm1k(((ULongArray)t).unbox-impl()));
            }
            else {
                sb.append(t.toString());
            }
        }
        sb.append(']');
        list.remove(CollectionsKt__CollectionsKt.getLastIndex((List<?>)list));
    }
    
    public ArraysKt__ArraysKt() {
        super();
    }
}
