package com.fasterxml.jackson.core.io;

public final class NumberOutput
{
    private static int MILLION;
    private static int BILLION;
    private static long BILLION_L;
    private static long MIN_INT_AS_LONG;
    private static long MAX_INT_AS_LONG;
    static final String SMALLEST_INT;
    static final String SMALLEST_LONG;
    private static final int[] TRIPLET_TO_CHARS;
    private static final String[] sSmallIntStrs;
    private static final String[] sSmallIntStrs2;
    
    public NumberOutput() {
        super();
    }
    
    public static int outputInt(int n, final char[] array, int n2) {
        if (n < 0) {
            if (n == Integer.MIN_VALUE) {
                return _outputSmallestI(array, n2);
            }
            array[n2++] = '-';
            n = -n;
        }
        if (n < NumberOutput.MILLION) {
            if (n >= 1000) {
                final int n3 = n / 1000;
                n -= n3 * 1000;
                n2 = _leading3(n3, array, n2);
                n2 = _full3(n, array, n2);
                return n2;
            }
            if (n < 10) {
                array[n2] = (char)(48 + n);
                return n2 + 1;
            }
            return _leading3(n, array, n2);
        }
        else {
            if (n >= NumberOutput.BILLION) {
                n -= NumberOutput.BILLION;
                if (n >= NumberOutput.BILLION) {
                    n -= NumberOutput.BILLION;
                    array[n2++] = '2';
                }
                else {
                    array[n2++] = '1';
                }
                return _outputFullBillion(n, array, n2);
            }
            final int n4 = n / 1000;
            final int n5 = n - n4 * 1000;
            n = n4;
            final int n6 = n4 / 1000;
            final int n7 = n - n6 * 1000;
            n2 = _leading3(n6, array, n2);
            n2 = _full3(n7, array, n2);
            return _full3(n5, array, n2);
        }
    }
    
    public static int outputInt(int n, final byte[] array, int n2) {
        if (n < 0) {
            if (n == Integer.MIN_VALUE) {
                return _outputSmallestI(array, n2);
            }
            array[n2++] = 45;
            n = -n;
        }
        if (n < NumberOutput.MILLION) {
            if (n < 1000) {
                if (n < 10) {
                    array[n2++] = (byte)(48 + n);
                }
                else {
                    n2 = _leading3(n, array, n2);
                }
            }
            else {
                final int n3 = n / 1000;
                n -= n3 * 1000;
                n2 = _leading3(n3, array, n2);
                n2 = _full3(n, array, n2);
            }
            return n2;
        }
        if (n >= NumberOutput.BILLION) {
            n -= NumberOutput.BILLION;
            if (n >= NumberOutput.BILLION) {
                n -= NumberOutput.BILLION;
                array[n2++] = 50;
            }
            else {
                array[n2++] = 49;
            }
            return _outputFullBillion(n, array, n2);
        }
        final int n4 = n / 1000;
        final int n5 = n - n4 * 1000;
        n = n4;
        final int n6 = n4 / 1000;
        final int n7 = n - n6 * 1000;
        n2 = _leading3(n6, array, n2);
        n2 = _full3(n7, array, n2);
        return _full3(n5, array, n2);
    }
    
    public static int outputLong(long n, final char[] array, int n2) {
        if (n < 0L) {
            if (n > NumberOutput.MIN_INT_AS_LONG) {
                return outputInt((int)n, array, n2);
            }
            if (n == Long.MIN_VALUE) {
                return _outputSmallestL(array, n2);
            }
            array[n2++] = '-';
            n = -n;
        }
        else if (n <= NumberOutput.MAX_INT_AS_LONG) {
            return outputInt((int)n, array, n2);
        }
        final long n3 = n / NumberOutput.BILLION_L;
        n -= n3 * NumberOutput.BILLION_L;
        if (n3 < NumberOutput.BILLION_L) {
            n2 = _outputUptoBillion((int)n3, array, n2);
        }
        else {
            final long n4 = n3 / NumberOutput.BILLION_L;
            final long n5 = n3 - n4 * NumberOutput.BILLION_L;
            n2 = _leading3((int)n4, array, n2);
            n2 = _outputFullBillion((int)n5, array, n2);
        }
        return _outputFullBillion((int)n, array, n2);
    }
    
    public static int outputLong(long n, final byte[] array, int n2) {
        if (n < 0L) {
            if (n > NumberOutput.MIN_INT_AS_LONG) {
                return outputInt((int)n, array, n2);
            }
            if (n == Long.MIN_VALUE) {
                return _outputSmallestL(array, n2);
            }
            array[n2++] = 45;
            n = -n;
        }
        else if (n <= NumberOutput.MAX_INT_AS_LONG) {
            return outputInt((int)n, array, n2);
        }
        final long n3 = n / NumberOutput.BILLION_L;
        n -= n3 * NumberOutput.BILLION_L;
        if (n3 < NumberOutput.BILLION_L) {
            n2 = _outputUptoBillion((int)n3, array, n2);
        }
        else {
            final long n4 = n3 / NumberOutput.BILLION_L;
            final long n5 = n3 - n4 * NumberOutput.BILLION_L;
            n2 = _leading3((int)n4, array, n2);
            n2 = _outputFullBillion((int)n5, array, n2);
        }
        return _outputFullBillion((int)n, array, n2);
    }
    
    public static String toString(final int n) {
        if (n < NumberOutput.sSmallIntStrs.length) {
            if (n >= 0) {
                return NumberOutput.sSmallIntStrs[n];
            }
            final int n2 = -n - 1;
            if (n2 < NumberOutput.sSmallIntStrs2.length) {
                return NumberOutput.sSmallIntStrs2[n2];
            }
        }
        return Integer.toString(n);
    }
    
    public static String toString(final long n) {
        if (n <= 0L && n >= -2147483648L) {
            return toString((int)n);
        }
        return Long.toString(n);
    }
    
    public static String toString(final double n) {
        return Double.toString(n);
    }
    
    public static String toString(final float n) {
        return Float.toString(n);
    }
    
    private static int _outputUptoBillion(final int n, final char[] array, int leading3) {
        if (n >= NumberOutput.MILLION) {
            final int n2 = n / 1000;
            final int n3 = n - n2 * 1000;
            final int n4 = n2 / 1000;
            final int n5 = n2 - n4 * 1000;
            leading3 = _leading3(n4, array, leading3);
            final int n6 = NumberOutput.TRIPLET_TO_CHARS[n5];
            array[leading3++] = (char)(n6 >> 16);
            array[leading3++] = (char)(n6 >> 8 & 0x7F);
            array[leading3++] = (char)(n6 & 0x7F);
            final int n7 = NumberOutput.TRIPLET_TO_CHARS[n3];
            array[leading3++] = (char)(n7 >> 16);
            array[leading3++] = (char)(n7 >> 8 & 0x7F);
            array[leading3++] = (char)(n7 & 0x7F);
            return leading3;
        }
        if (n < 1000) {
            return _leading3(n, array, leading3);
        }
        final int n8 = n / 1000;
        return _outputUptoMillion(array, leading3, n8, n - n8 * 1000);
    }
    
    private static int _outputFullBillion(final int n, final char[] array, int n2) {
        final int n3 = n / 1000;
        final int n4 = n - n3 * 1000;
        final int n5 = n3 / 1000;
        final int n6 = NumberOutput.TRIPLET_TO_CHARS[n5];
        array[n2++] = (char)(n6 >> 16);
        array[n2++] = (char)(n6 >> 8 & 0x7F);
        array[n2++] = (char)(n6 & 0x7F);
        final int n7 = NumberOutput.TRIPLET_TO_CHARS[n3 - n5 * 1000];
        array[n2++] = (char)(n7 >> 16);
        array[n2++] = (char)(n7 >> 8 & 0x7F);
        array[n2++] = (char)(n7 & 0x7F);
        final int n8 = NumberOutput.TRIPLET_TO_CHARS[n4];
        array[n2++] = (char)(n8 >> 16);
        array[n2++] = (char)(n8 >> 8 & 0x7F);
        array[n2++] = (char)(n8 & 0x7F);
        return n2;
    }
    
    private static int _outputUptoBillion(final int n, final byte[] array, int leading3) {
        if (n >= NumberOutput.MILLION) {
            final int n2 = n / 1000;
            final int n3 = n - n2 * 1000;
            final int n4 = n2 / 1000;
            final int n5 = n2 - n4 * 1000;
            leading3 = _leading3(n4, array, leading3);
            final int n6 = NumberOutput.TRIPLET_TO_CHARS[n5];
            array[leading3++] = (byte)(n6 >> 16);
            array[leading3++] = (byte)(n6 >> 8);
            array[leading3++] = (byte)n6;
            final int n7 = NumberOutput.TRIPLET_TO_CHARS[n3];
            array[leading3++] = (byte)(n7 >> 16);
            array[leading3++] = (byte)(n7 >> 8);
            array[leading3++] = (byte)n7;
            return leading3;
        }
        if (n < 1000) {
            return _leading3(n, array, leading3);
        }
        final int n8 = n / 1000;
        return _outputUptoMillion(array, leading3, n8, n - n8 * 1000);
    }
    
    private static int _outputFullBillion(final int n, final byte[] array, int n2) {
        final int n3 = n / 1000;
        final int n4 = n - n3 * 1000;
        final int n5 = n3 / 1000;
        final int n6 = n3 - n5 * 1000;
        final int n7 = NumberOutput.TRIPLET_TO_CHARS[n5];
        array[n2++] = (byte)(n7 >> 16);
        array[n2++] = (byte)(n7 >> 8);
        array[n2++] = (byte)n7;
        final int n8 = NumberOutput.TRIPLET_TO_CHARS[n6];
        array[n2++] = (byte)(n8 >> 16);
        array[n2++] = (byte)(n8 >> 8);
        array[n2++] = (byte)n8;
        final int n9 = NumberOutput.TRIPLET_TO_CHARS[n4];
        array[n2++] = (byte)(n9 >> 16);
        array[n2++] = (byte)(n9 >> 8);
        array[n2++] = (byte)n9;
        return n2;
    }
    
    private static int _outputUptoMillion(final char[] array, int n, final int n2, final int n3) {
        final int n4 = NumberOutput.TRIPLET_TO_CHARS[n2];
        if (n2 > 9) {
            if (n2 > 99) {
                array[n++] = (char)(n4 >> 16);
            }
            array[n++] = (char)(n4 >> 8 & 0x7F);
        }
        array[n++] = (char)(n4 & 0x7F);
        final int n5 = NumberOutput.TRIPLET_TO_CHARS[n3];
        array[n++] = (char)(n5 >> 16);
        array[n++] = (char)(n5 >> 8 & 0x7F);
        array[n++] = (char)(n5 & 0x7F);
        return n;
    }
    
    private static int _outputUptoMillion(final byte[] array, int n, final int n2, final int n3) {
        final int n4 = NumberOutput.TRIPLET_TO_CHARS[n2];
        if (n2 > 9) {
            if (n2 > 99) {
                array[n++] = (byte)(n4 >> 16);
            }
            array[n++] = (byte)(n4 >> 8);
        }
        array[n++] = (byte)n4;
        final int n5 = NumberOutput.TRIPLET_TO_CHARS[n3];
        array[n++] = (byte)(n5 >> 16);
        array[n++] = (byte)(n5 >> 8);
        array[n++] = (byte)n5;
        return n;
    }
    
    private static int _leading3(final int n, final char[] array, int n2) {
        final int n3 = NumberOutput.TRIPLET_TO_CHARS[n];
        if (n > 9) {
            if (n > 99) {
                array[n2++] = (char)(n3 >> 16);
            }
            array[n2++] = (char)(n3 >> 8 & 0x7F);
        }
        array[n2++] = (char)(n3 & 0x7F);
        return n2;
    }
    
    private static int _leading3(final int n, final byte[] array, int n2) {
        final int n3 = NumberOutput.TRIPLET_TO_CHARS[n];
        if (n > 9) {
            if (n > 99) {
                array[n2++] = (byte)(n3 >> 16);
            }
            array[n2++] = (byte)(n3 >> 8);
        }
        array[n2++] = (byte)n3;
        return n2;
    }
    
    private static int _full3(final int n, final char[] array, int n2) {
        final int n3 = NumberOutput.TRIPLET_TO_CHARS[n];
        array[n2++] = (char)(n3 >> 16);
        array[n2++] = (char)(n3 >> 8 & 0x7F);
        array[n2++] = (char)(n3 & 0x7F);
        return n2;
    }
    
    private static int _full3(final int n, final byte[] array, int n2) {
        final int n3 = NumberOutput.TRIPLET_TO_CHARS[n];
        array[n2++] = (byte)(n3 >> 16);
        array[n2++] = (byte)(n3 >> 8);
        array[n2++] = (byte)n3;
        return n2;
    }
    
    private static int _outputSmallestL(final char[] array, final int n) {
        final int length = NumberOutput.SMALLEST_LONG.length();
        NumberOutput.SMALLEST_LONG.getChars(0, length, array, n);
        return n + length;
    }
    
    private static int _outputSmallestL(final byte[] array, int n) {
        for (int length = NumberOutput.SMALLEST_LONG.length(), i = 0; i < length; ++i) {
            array[n++] = (byte)NumberOutput.SMALLEST_LONG.charAt(i);
        }
        return n;
    }
    
    private static int _outputSmallestI(final char[] array, final int n) {
        final int length = NumberOutput.SMALLEST_INT.length();
        NumberOutput.SMALLEST_INT.getChars(0, length, array, n);
        return n + length;
    }
    
    private static int _outputSmallestI(final byte[] array, int n) {
        for (int length = NumberOutput.SMALLEST_INT.length(), i = 0; i < length; ++i) {
            array[n++] = (byte)NumberOutput.SMALLEST_INT.charAt(i);
        }
        return n;
    }
    
    static {
        NumberOutput.MILLION = 1000000;
        NumberOutput.BILLION = 1000000000;
        NumberOutput.BILLION_L = 1000000000L;
        NumberOutput.MIN_INT_AS_LONG = -2147483648L;
        NumberOutput.MAX_INT_AS_LONG = 0L;
        SMALLEST_INT = String.valueOf(Integer.MIN_VALUE);
        SMALLEST_LONG = String.valueOf(Long.MIN_VALUE);
        TRIPLET_TO_CHARS = new int[1000];
        int n = 0;
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {
                for (int k = 0; k < 10; ++k) {
                    NumberOutput.TRIPLET_TO_CHARS[n++] = (i + 48 << 16 | j + 48 << 8 | k + 48);
                }
            }
        }
        sSmallIntStrs = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
        sSmallIntStrs2 = new String[] { "-1", "-2", "-3", "-4", "-5", "-6", "-7", "-8", "-9", "-10" };
    }
}
