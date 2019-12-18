package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.core.io.*;
import java.util.regex.*;
import java.text.*;
import java.util.*;

public class StdDateFormat extends DateFormat
{
    protected static final String PATTERN_PLAIN_STR = "\\d\\d\\d\\d[-]\\d\\d[-]\\d\\d";
    protected static final Pattern PATTERN_PLAIN;
    protected static final Pattern PATTERN_ISO8601;
    public static final String DATE_FORMAT_STR_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    protected static final String DATE_FORMAT_STR_PLAIN = "yyyy-MM-dd";
    protected static final String DATE_FORMAT_STR_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";
    protected static final String[] ALL_FORMATS;
    protected static final TimeZone DEFAULT_TIMEZONE;
    protected static final Locale DEFAULT_LOCALE;
    protected static final DateFormat DATE_FORMAT_RFC1123;
    protected static final DateFormat DATE_FORMAT_ISO8601;
    public static final StdDateFormat instance;
    protected static final Calendar CALENDAR;
    protected transient TimeZone _timezone;
    protected final Locale _locale;
    protected Boolean _lenient;
    private transient Calendar _calendar;
    private transient DateFormat _formatRFC1123;
    private boolean _tzSerializedWithColon;
    
    public StdDateFormat() {
        super();
        this._tzSerializedWithColon = false;
        this._locale = StdDateFormat.DEFAULT_LOCALE;
    }
    
    @Deprecated
    public StdDateFormat(final TimeZone timezone, final Locale locale) {
        super();
        this._tzSerializedWithColon = false;
        this._timezone = timezone;
        this._locale = locale;
    }
    
    protected StdDateFormat(final TimeZone timeZone, final Locale locale, final Boolean b) {
        this(timeZone, locale, b, false);
    }
    
    protected StdDateFormat(final TimeZone timezone, final Locale locale, final Boolean lenient, final boolean tzSerializedWithColon) {
        super();
        this._tzSerializedWithColon = false;
        this._timezone = timezone;
        this._locale = locale;
        this._lenient = lenient;
        this._tzSerializedWithColon = tzSerializedWithColon;
    }
    
    public static TimeZone getDefaultTimeZone() {
        return StdDateFormat.DEFAULT_TIMEZONE;
    }
    
    public StdDateFormat withTimeZone(TimeZone default_TIMEZONE) {
        if (default_TIMEZONE == null) {
            default_TIMEZONE = StdDateFormat.DEFAULT_TIMEZONE;
        }
        if (default_TIMEZONE == this._timezone || default_TIMEZONE.equals(this._timezone)) {
            return this;
        }
        return new StdDateFormat(default_TIMEZONE, this._locale, this._lenient, this._tzSerializedWithColon);
    }
    
    public StdDateFormat withLocale(final Locale locale) {
        if (locale.equals(this._locale)) {
            return this;
        }
        return new StdDateFormat(this._timezone, locale, this._lenient, this._tzSerializedWithColon);
    }
    
    public StdDateFormat withLenient(final Boolean b) {
        if (_equals(b, this._lenient)) {
            return this;
        }
        return new StdDateFormat(this._timezone, this._locale, b, this._tzSerializedWithColon);
    }
    
    public StdDateFormat withColonInTimeZone(final boolean b) {
        if (this._tzSerializedWithColon == b) {
            return this;
        }
        return new StdDateFormat(this._timezone, this._locale, this._lenient, b);
    }
    
    @Override
    public StdDateFormat clone() {
        return new StdDateFormat(this._timezone, this._locale, this._lenient, this._tzSerializedWithColon);
    }
    
    @Deprecated
    public static DateFormat getISO8601Format(final TimeZone timeZone, final Locale locale) {
        return _cloneFormat(StdDateFormat.DATE_FORMAT_ISO8601, "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timeZone, locale, null);
    }
    
    @Deprecated
    public static DateFormat getRFC1123Format(final TimeZone timeZone, final Locale locale) {
        return _cloneFormat(StdDateFormat.DATE_FORMAT_RFC1123, "EEE, dd MMM yyyy HH:mm:ss zzz", timeZone, locale, null);
    }
    
    @Override
    public TimeZone getTimeZone() {
        return this._timezone;
    }
    
    @Override
    public void setTimeZone(final TimeZone timezone) {
        if (!timezone.equals(this._timezone)) {
            this._clearFormats();
            this._timezone = timezone;
        }
    }
    
    @Override
    public void setLenient(final boolean b) {
        final Boolean value = b;
        if (!_equals(value, this._lenient)) {
            this._lenient = value;
            this._clearFormats();
        }
    }
    
    @Override
    public boolean isLenient() {
        return this._lenient == null || this._lenient;
    }
    
    public boolean isColonIncludedInTimeZone() {
        return this._tzSerializedWithColon;
    }
    
    @Override
    public Date parse(String trim) throws ParseException {
        trim = trim.trim();
        final ParsePosition parsePosition = new ParsePosition(0);
        final Date parseDate = this._parseDate(trim, parsePosition);
        if (parseDate != null) {
            return parseDate;
        }
        final StringBuilder sb = new StringBuilder();
        for (final String s : StdDateFormat.ALL_FORMATS) {
            if (sb.length() > 0) {
                sb.append("\", \"");
            }
            else {
                sb.append('\"');
            }
            sb.append(s);
        }
        sb.append('\"');
        throw new ParseException(String.format("Cannot parse date \"%s\": not compatible with any of standard forms (%s)", trim, sb.toString()), parsePosition.getErrorIndex());
    }
    
    @Override
    public Date parse(final String s, final ParsePosition parsePosition) {
        try {
            return this._parseDate(s, parsePosition);
        }
        catch (ParseException ex) {
            return null;
        }
    }
    
    protected Date _parseDate(final String s, final ParsePosition parsePosition) throws ParseException {
        if (this.looksLikeISO8601(s)) {
            return this.parseAsISO8601(s, parsePosition);
        }
        int length = s.length();
        while (--length >= 0) {
            final char char1 = s.charAt(length);
            if (char1 < '0' || char1 > '9') {
                if (length > 0) {
                    break;
                }
                if (char1 != '-') {
                    break;
                }
                continue;
            }
        }
        if (length < 0 && (s.charAt(0) == '-' || NumberInput.inLongRange(s, false))) {
            return this._parseDateFromLong(s, parsePosition);
        }
        return this.parseAsRFC1123(s, parsePosition);
    }
    
    @Override
    public StringBuffer format(final Date date, final StringBuffer sb, final FieldPosition fieldPosition) {
        TimeZone timeZone = this._timezone;
        if (timeZone == null) {
            timeZone = StdDateFormat.DEFAULT_TIMEZONE;
        }
        this._format(timeZone, this._locale, date, sb);
        return sb;
    }
    
    protected void _format(final TimeZone timeZone, final Locale locale, final Date time, final StringBuffer sb) {
        final Calendar getCalendar = this._getCalendar(timeZone);
        getCalendar.setTime(time);
        pad4(sb, getCalendar.get(1));
        sb.append('-');
        pad2(sb, getCalendar.get(2) + 1);
        sb.append('-');
        pad2(sb, getCalendar.get(5));
        sb.append('T');
        pad2(sb, getCalendar.get(11));
        sb.append(':');
        pad2(sb, getCalendar.get(12));
        sb.append(':');
        pad2(sb, getCalendar.get(13));
        sb.append('.');
        pad3(sb, getCalendar.get(14));
        final int offset = timeZone.getOffset(getCalendar.getTimeInMillis());
        if (offset != 0) {
            final int abs = Math.abs(offset / 60000 / 60);
            final int abs2 = Math.abs(offset / 60000 % 60);
            sb.append((char)((offset < 0) ? 45 : 43));
            pad2(sb, abs);
            if (this._tzSerializedWithColon) {
                sb.append(':');
            }
            pad2(sb, abs2);
        }
        else if (this._tzSerializedWithColon) {
            sb.append("+00:00");
        }
        else {
            sb.append("+0000");
        }
    }
    
    private static void pad2(final StringBuffer sb, int n) {
        final int n2 = n / 10;
        if (n2 == 0) {
            sb.append('0');
        }
        else {
            sb.append((char)(48 + n2));
            n -= 10 * n2;
        }
        sb.append((char)(48 + n));
    }
    
    private static void pad3(final StringBuffer sb, int n) {
        final int n2 = n / 100;
        if (n2 == 0) {
            sb.append('0');
        }
        else {
            sb.append((char)(48 + n2));
            n -= n2 * 100;
        }
        pad2(sb, n);
    }
    
    private static void pad4(final StringBuffer sb, int n) {
        final int n2 = n / 100;
        if (n2 == 0) {
            sb.append('0').append('0');
        }
        else {
            pad2(sb, n2);
            n -= 100 * n2;
        }
        pad2(sb, n);
    }
    
    @Override
    public String toString() {
        return String.format("DateFormat %s: (timezone: %s, locale: %s, lenient: %s)", this.getClass().getName(), this._timezone, this._locale, this._lenient);
    }
    
    public String toPattern() {
        final StringBuilder sb = new StringBuilder(100);
        sb.append("[one of: '").append("yyyy-MM-dd'T'HH:mm:ss.SSSZ").append("', '").append("EEE, dd MMM yyyy HH:mm:ss zzz").append("' (");
        sb.append(Boolean.FALSE.equals(this._lenient) ? "strict" : "lenient").append(")]");
        return sb.toString();
    }
    
    @Override
    public boolean equals(final Object o) {
        return o == this;
    }
    
    @Override
    public int hashCode() {
        return System.identityHashCode(this);
    }
    
    protected boolean looksLikeISO8601(final String s) {
        return s.length() >= 7 && Character.isDigit(s.charAt(0)) && Character.isDigit(s.charAt(3)) && s.charAt(4) == '-' && Character.isDigit(s.charAt(5));
    }
    
    private Date _parseDateFromLong(final String s, final ParsePosition parsePosition) throws ParseException {
        long long1;
        try {
            long1 = NumberInput.parseLong(s);
        }
        catch (NumberFormatException ex) {
            throw new ParseException(String.format("Timestamp value %s out of 64-bit value range", s), parsePosition.getErrorIndex());
        }
        return new Date(long1);
    }
    
    protected Date parseAsISO8601(final String s, final ParsePosition parsePosition) throws ParseException {
        try {
            return this._parseAsISO8601(s, parsePosition);
        }
        catch (IllegalArgumentException ex) {
            throw new ParseException(String.format("Cannot parse date \"%s\", problem: %s", s, ex.getMessage()), parsePosition.getErrorIndex());
        }
    }
    
    protected Date _parseAsISO8601(final String s, final ParsePosition parsePosition) throws IllegalArgumentException, ParseException {
        final int length = s.length();
        TimeZone timeZone = StdDateFormat.DEFAULT_TIMEZONE;
        if (this._timezone != null && 'Z' != s.charAt(length - 1)) {
            timeZone = this._timezone;
        }
        final Calendar getCalendar = this._getCalendar(timeZone);
        getCalendar.clear();
        String s2;
        if (length <= 10) {
            if (StdDateFormat.PATTERN_PLAIN.matcher(s).matches()) {
                getCalendar.set(_parse4D(s, 0), _parse2D(s, 5) - 1, _parse2D(s, 8), 0, 0, 0);
                getCalendar.set(14, 0);
                return getCalendar.getTime();
            }
            s2 = "yyyy-MM-dd";
        }
        else {
            final Matcher matcher = StdDateFormat.PATTERN_ISO8601.matcher(s);
            if (matcher.matches()) {
                final int start = matcher.start(2);
                final int end = matcher.end(2);
                final int n = end - start;
                if (n > 1) {
                    int n2 = _parse2D(s, start + 1) * 3600;
                    if (n >= 5) {
                        n2 += _parse2D(s, end - 2) * 60;
                    }
                    int n3;
                    if (s.charAt(start) == '-') {
                        n3 = n2 * -1000;
                    }
                    else {
                        n3 = n2 * 1000;
                    }
                    getCalendar.set(15, n3);
                    getCalendar.set(16, 0);
                }
                final int parse4D = _parse4D(s, 0);
                final int n4 = _parse2D(s, 5) - 1;
                final int parse2D = _parse2D(s, 8);
                final int parse2D2 = _parse2D(s, 11);
                final int parse2D3 = _parse2D(s, 14);
                int parse2D4;
                if (length > 16 && s.charAt(16) == ':') {
                    parse2D4 = _parse2D(s, 17);
                }
                else {
                    parse2D4 = 0;
                }
                getCalendar.set(parse4D, n4, parse2D, parse2D2, parse2D3, parse2D4);
                final int n5 = matcher.start(1) + 1;
                final int end2 = matcher.end(1);
                if (n5 >= end2) {
                    getCalendar.set(14, 0);
                }
                else {
                    int n6 = 0;
                    final int n7 = end2 - n5;
                    switch (n7) {
                        default: {
                            if (n7 > 9) {
                                throw new ParseException(String.format("Cannot parse date \"%s\": invalid fractional seconds '%s'; can use at most 9 digits", s, matcher.group(1).substring(1)), n5);
                            }
                        }
                        case 3: {
                            n6 += s.charAt(n5 + 2) - '0';
                        }
                        case 2: {
                            n6 += 10 * (s.charAt(n5 + 1) - '0');
                        }
                        case 1: {
                            n6 += 100 * (s.charAt(n5) - '0');
                        }
                        case 0: {
                            getCalendar.set(14, n6);
                            break;
                        }
                    }
                }
                return getCalendar.getTime();
            }
            s2 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
        }
        throw new ParseException(String.format("Cannot parse date \"%s\": while it seems to fit format '%s', parsing fails (leniency? %s)", s, s2, this._lenient), 0);
    }
    
    private static int _parse4D(final String s, final int n) {
        return 1000 * (s.charAt(n) - '0') + 100 * (s.charAt(n + 1) - '0') + 10 * (s.charAt(n + 2) - '0') + (s.charAt(n + 3) - '0');
    }
    
    private static int _parse2D(final String s, final int n) {
        return 10 * (s.charAt(n) - '0') + (s.charAt(n + 1) - '0');
    }
    
    protected Date parseAsRFC1123(final String s, final ParsePosition parsePosition) {
        if (this._formatRFC1123 == null) {
            this._formatRFC1123 = _cloneFormat(StdDateFormat.DATE_FORMAT_RFC1123, "EEE, dd MMM yyyy HH:mm:ss zzz", this._timezone, this._locale, this._lenient);
        }
        return this._formatRFC1123.parse(s, parsePosition);
    }
    
    private static final DateFormat _cloneFormat(DateFormat dateFormat, final String s, final TimeZone timeZone, final Locale locale, final Boolean b) {
        if (!locale.equals(StdDateFormat.DEFAULT_LOCALE)) {
            dateFormat = new SimpleDateFormat(s, locale);
            dateFormat.setTimeZone((timeZone == null) ? StdDateFormat.DEFAULT_TIMEZONE : timeZone);
        }
        else {
            dateFormat = (DateFormat)dateFormat.clone();
            if (timeZone != null) {
                dateFormat.setTimeZone(timeZone);
            }
        }
        if (b != null) {
            dateFormat.setLenient(b);
        }
        return dateFormat;
    }
    
    protected void _clearFormats() {
        this._formatRFC1123 = null;
    }
    
    protected Calendar _getCalendar(final TimeZone timeZone) {
        Calendar calendar = this._calendar;
        if (calendar == null) {
            calendar = (this._calendar = (Calendar)StdDateFormat.CALENDAR.clone());
        }
        if (!calendar.getTimeZone().equals(timeZone)) {
            calendar.setTimeZone(timeZone);
        }
        calendar.setLenient(this.isLenient());
        return calendar;
    }
    
    protected static <T> boolean _equals(final T t, final T t2) {
        return t == t2 || (t != null && t.equals(t2));
    }
    
    @Override
    public Object clone() {
        return this.clone();
    }
    
    static {
        PATTERN_PLAIN = Pattern.compile("\\d\\d\\d\\d[-]\\d\\d[-]\\d\\d");
        Pattern compile;
        try {
            compile = Pattern.compile("\\d\\d\\d\\d[-]\\d\\d[-]\\d\\d[T]\\d\\d[:]\\d\\d(?:[:]\\d\\d)?(\\.\\d+)?(Z|[+-]\\d\\d(?:[:]?\\d\\d)?)?");
        }
        catch (Throwable t) {
            throw new RuntimeException(t);
        }
        PATTERN_ISO8601 = compile;
        ALL_FORMATS = new String[] { "yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSS", "EEE, dd MMM yyyy HH:mm:ss zzz", "yyyy-MM-dd" };
        DEFAULT_TIMEZONE = TimeZone.getTimeZone("UTC");
        DEFAULT_LOCALE = Locale.US;
        (DATE_FORMAT_RFC1123 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", StdDateFormat.DEFAULT_LOCALE)).setTimeZone(StdDateFormat.DEFAULT_TIMEZONE);
        (DATE_FORMAT_ISO8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", StdDateFormat.DEFAULT_LOCALE)).setTimeZone(StdDateFormat.DEFAULT_TIMEZONE);
        instance = new StdDateFormat();
        CALENDAR = new GregorianCalendar(StdDateFormat.DEFAULT_TIMEZONE, StdDateFormat.DEFAULT_LOCALE);
    }
}
