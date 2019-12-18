package kotlin.io;

import kotlin.*;
import java.io.*;

@Metadata(mv = { 1, 1, 15 }, bv = { 1, 0, 3 }, k = 2, d1 = { "\u0000\u0010\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a$\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00032\b\u0010\u0005\u001a\u0004\u0018\u00010\u0001H\u0002¨\u0006\u0006" }, d2 = { "constructMessage", "", "file", "Ljava/io/File;", "other", "reason", "kotlin-stdlib" })
public final class ExceptionsKt
{
    private static final String constructMessage(final File file, final File file2, final String s) {
        final StringBuilder sb = new StringBuilder(file.toString());
        if (file2 != null) {
            sb.append(" -> " + file2);
        }
        if (s != null) {
            sb.append(": " + s);
        }
        return sb.toString();
    }
    
    public static final String access$constructMessage(final File file, final File file2, final String s) {
        return constructMessage(file, file2, s);
    }
}
