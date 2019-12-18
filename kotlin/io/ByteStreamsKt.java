package kotlin.io;

import kotlin.jvm.*;
import org.jetbrains.annotations.*;
import kotlin.collections.*;
import java.nio.charset.*;
import kotlin.internal.*;
import kotlin.text.*;
import java.io.*;
import kotlin.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 2, d1 = { "\u0000Z\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0017\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0017\u0010\u0000\u001a\u00020\u0005*\u00020\u00062\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0017\u0010\u0007\u001a\u00020\b*\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\nH\u0087\b\u001a\u0017\u0010\u000b\u001a\u00020\f*\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\nH\u0087\b\u001a\u0017\u0010\r\u001a\u00020\u000e*\u00020\u000f2\b\b\u0002\u0010\t\u001a\u00020\nH\u0087\b\u001a\u001c\u0010\u0010\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00062\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u001a\r\u0010\u0013\u001a\u00020\u000e*\u00020\u0014H\u0087\b\u001a\u001d\u0010\u0013\u001a\u00020\u000e*\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u0004H\u0087\b\u001a\r\u0010\u0017\u001a\u00020\u0018*\u00020\u0001H\u0086\u0002\u001a\f\u0010\u0019\u001a\u00020\u0014*\u00020\u0002H\u0007\u001a\u0016\u0010\u0019\u001a\u00020\u0014*\u00020\u00022\b\b\u0002\u0010\u001a\u001a\u00020\u0004H\u0007\u001a\u0017\u0010\u001b\u001a\u00020\u001c*\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\nH\u0087\b\u001a\u0017\u0010\u001d\u001a\u00020\u001e*\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\nH\u0087\b¨\u0006\u001f" }, d2 = { "buffered", "Ljava/io/BufferedInputStream;", "Ljava/io/InputStream;", "bufferSize", "", "Ljava/io/BufferedOutputStream;", "Ljava/io/OutputStream;", "bufferedReader", "Ljava/io/BufferedReader;", "charset", "Ljava/nio/charset/Charset;", "bufferedWriter", "Ljava/io/BufferedWriter;", "byteInputStream", "Ljava/io/ByteArrayInputStream;", "", "copyTo", "", "out", "inputStream", "", "offset", "length", "iterator", "Lkotlin/collections/ByteIterator;", "readBytes", "estimatedSize", "reader", "Ljava/io/InputStreamReader;", "writer", "Ljava/io/OutputStreamWriter;", "kotlin-stdlib" })
@JvmName(name = "ByteStreamsKt")
public final class ByteStreamsKt
{
    @NotNull
    public static final ByteIterator iterator(@NotNull final BufferedInputStream bufferedInputStream) {
        return (ByteIterator)new ByteStreamsKt$iterator.ByteStreamsKt$iterator$1(bufferedInputStream);
    }
    
    @InlineOnly
    private static final ByteArrayInputStream byteInputStream(@NotNull final String s, final Charset charset) {
        if (s == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        return new ByteArrayInputStream(s.getBytes(charset));
    }
    
    static ByteArrayInputStream byteInputStream$default(final String s, Charset utf_8, int n, final Object o) {
        if ((n & 0x1) != 0x0) {
            utf_8 = Charsets.UTF_8;
        }
        n = 0;
        if (s == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        return new ByteArrayInputStream(s.getBytes(utf_8));
    }
    
    @InlineOnly
    private static final ByteArrayInputStream inputStream(@NotNull final byte[] array) {
        return new ByteArrayInputStream(array);
    }
    
    @InlineOnly
    private static final ByteArrayInputStream inputStream(@NotNull final byte[] array, final int n, final int n2) {
        return new ByteArrayInputStream(array, n, n2);
    }
    
    @InlineOnly
    private static final BufferedInputStream buffered(@NotNull final InputStream inputStream, final int n) {
        return (BufferedInputStream)((inputStream instanceof BufferedInputStream) ? inputStream : new BufferedInputStream(inputStream, n));
    }
    
    static BufferedInputStream buffered$default(final InputStream inputStream, int n, int n2, final Object o) {
        if ((n2 & 0x1) != 0x0) {
            n = 8192;
        }
        n2 = 0;
        return (BufferedInputStream)((inputStream instanceof BufferedInputStream) ? inputStream : new BufferedInputStream(inputStream, n));
    }
    
    @InlineOnly
    private static final InputStreamReader reader(@NotNull final InputStream inputStream, final Charset charset) {
        return new InputStreamReader(inputStream, charset);
    }
    
    static InputStreamReader reader$default(final InputStream inputStream, Charset utf_8, int n, final Object o) {
        if ((n & 0x1) != 0x0) {
            utf_8 = Charsets.UTF_8;
        }
        n = 0;
        return new InputStreamReader(inputStream, utf_8);
    }
    
    @InlineOnly
    private static final BufferedReader bufferedReader(@NotNull final InputStream inputStream, final Charset charset) {
        final InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charset);
        final int n = 8192;
        return (inputStreamReader instanceof BufferedReader) ? inputStreamReader : new BufferedReader(inputStreamReader, n);
    }
    
    static BufferedReader bufferedReader$default(final InputStream inputStream, Charset utf_8, int n, final Object o) {
        if ((n & 0x1) != 0x0) {
            utf_8 = Charsets.UTF_8;
        }
        n = 0;
        final InputStreamReader inputStreamReader = new InputStreamReader(inputStream, utf_8);
        final int n2 = 8192;
        return (inputStreamReader instanceof BufferedReader) ? inputStreamReader : new BufferedReader(inputStreamReader, n2);
    }
    
    @InlineOnly
    private static final BufferedOutputStream buffered(@NotNull final OutputStream outputStream, final int n) {
        return (BufferedOutputStream)((outputStream instanceof BufferedOutputStream) ? outputStream : new BufferedOutputStream(outputStream, n));
    }
    
    static BufferedOutputStream buffered$default(final OutputStream outputStream, int n, int n2, final Object o) {
        if ((n2 & 0x1) != 0x0) {
            n = 8192;
        }
        n2 = 0;
        return (BufferedOutputStream)((outputStream instanceof BufferedOutputStream) ? outputStream : new BufferedOutputStream(outputStream, n));
    }
    
    @InlineOnly
    private static final OutputStreamWriter writer(@NotNull final OutputStream outputStream, final Charset charset) {
        return new OutputStreamWriter(outputStream, charset);
    }
    
    static OutputStreamWriter writer$default(final OutputStream outputStream, Charset utf_8, int n, final Object o) {
        if ((n & 0x1) != 0x0) {
            utf_8 = Charsets.UTF_8;
        }
        n = 0;
        return new OutputStreamWriter(outputStream, utf_8);
    }
    
    @InlineOnly
    private static final BufferedWriter bufferedWriter(@NotNull final OutputStream outputStream, final Charset charset) {
        final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, charset);
        final int n = 8192;
        return (outputStreamWriter instanceof BufferedWriter) ? outputStreamWriter : new BufferedWriter(outputStreamWriter, n);
    }
    
    static BufferedWriter bufferedWriter$default(final OutputStream outputStream, Charset utf_8, int n, final Object o) {
        if ((n & 0x1) != 0x0) {
            utf_8 = Charsets.UTF_8;
        }
        n = 0;
        final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, utf_8);
        final int n2 = 8192;
        return (outputStreamWriter instanceof BufferedWriter) ? outputStreamWriter : new BufferedWriter(outputStreamWriter, n2);
    }
    
    public static final long copyTo(@NotNull final InputStream inputStream, @NotNull final OutputStream outputStream, final int n) {
        long n2 = 0L;
        final byte[] array = new byte[n];
        for (int i = inputStream.read(array); i >= 0; i = inputStream.read(array)) {
            outputStream.write(array, 0, i);
            n2 += i;
        }
        return n2;
    }
    
    public static long copyTo$default(final InputStream inputStream, final OutputStream outputStream, int n, final int n2, final Object o) {
        if ((n2 & 0x2) != 0x0) {
            n = 8192;
        }
        return copyTo(inputStream, outputStream, n);
    }
    
    @Deprecated(message = "Use readBytes() overload without estimatedSize parameter", replaceWith = @ReplaceWith(imports = {}, expression = "readBytes()"))
    @NotNull
    @java.lang.Deprecated
    public static final byte[] readBytes(@NotNull final InputStream inputStream, final int n) {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(Math.max(n, inputStream.available()));
        copyTo$default(inputStream, byteArrayOutputStream, 0, 2, null);
        return byteArrayOutputStream.toByteArray();
    }
    
    @java.lang.Deprecated
    public static byte[] readBytes$default(final InputStream inputStream, int n, final int n2, final Object o) {
        if ((n2 & 0x1) != 0x0) {
            n = 8192;
        }
        return readBytes(inputStream, n);
    }
    
    @SinceKotlin(version = "1.3")
    @NotNull
    public static final byte[] readBytes(@NotNull final InputStream inputStream) {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(Math.max(8192, inputStream.available()));
        copyTo$default(inputStream, byteArrayOutputStream, 0, 2, null);
        return byteArrayOutputStream.toByteArray();
    }
}
