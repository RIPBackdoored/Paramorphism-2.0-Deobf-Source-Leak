package org.yaml.snakeyaml.representer;

import java.util.regex.*;
import org.yaml.snakeyaml.nodes.*;
import org.yaml.snakeyaml.reader.*;
import org.yaml.snakeyaml.error.*;
import org.yaml.snakeyaml.external.biz.base64Coder.*;
import java.io.*;
import java.math.*;
import java.util.*;

class SafeRepresenter extends BaseRepresenter
{
    protected Map<Class<?>, Tag> classTags;
    protected TimeZone timeZone;
    public static Pattern MULTILINE_PATTERN;
    
    public SafeRepresenter() {
        super();
        this.timeZone = null;
        this.nullRepresenter = new RepresentNull();
        this.representers.put(String.class, new RepresentString());
        this.representers.put(Boolean.class, new RepresentBoolean());
        this.representers.put(Character.class, new RepresentString());
        this.representers.put(UUID.class, new RepresentUuid());
        this.representers.put(byte[].class, new RepresentByteArray());
        final Represent primitiveArray = new RepresentPrimitiveArray();
        this.representers.put(short[].class, primitiveArray);
        this.representers.put(int[].class, primitiveArray);
        this.representers.put(long[].class, primitiveArray);
        this.representers.put(float[].class, primitiveArray);
        this.representers.put(double[].class, primitiveArray);
        this.representers.put(char[].class, primitiveArray);
        this.representers.put(boolean[].class, primitiveArray);
        this.multiRepresenters.put(Number.class, new RepresentNumber());
        this.multiRepresenters.put(List.class, new RepresentList());
        this.multiRepresenters.put(Map.class, new RepresentMap());
        this.multiRepresenters.put(Set.class, new RepresentSet());
        this.multiRepresenters.put(Iterator.class, new RepresentIterator());
        this.multiRepresenters.put(new Object[0].getClass(), new RepresentArray());
        this.multiRepresenters.put(Date.class, new RepresentDate());
        this.multiRepresenters.put(Enum.class, new RepresentEnum());
        this.multiRepresenters.put(Calendar.class, new RepresentDate());
        this.classTags = new HashMap<Class<?>, Tag>();
    }
    
    protected Tag getTag(final Class<?> clazz, final Tag defaultTag) {
        if (this.classTags.containsKey(clazz)) {
            return this.classTags.get(clazz);
        }
        return defaultTag;
    }
    
    public TimeZone getTimeZone() {
        return this.timeZone;
    }
    
    public void setTimeZone(final TimeZone timeZone) {
        this.timeZone = timeZone;
    }
    
    static {
        SafeRepresenter.MULTILINE_PATTERN = Pattern.compile("\n|\u0085|\u2028|\u2029");
    }
    
    protected class RepresentNull implements Represent
    {
        final /* synthetic */ SafeRepresenter this$0;
        
        protected RepresentNull(final SafeRepresenter this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Node representData(final Object data) {
            return this.this$0.representScalar(Tag.NULL, "null");
        }
    }
    
    protected class RepresentString implements Represent
    {
        final /* synthetic */ SafeRepresenter this$0;
        
        protected RepresentString(final SafeRepresenter this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Node representData(final Object data) {
            Tag tag = Tag.STR;
            Character style = null;
            String value = data.toString();
            if (!StreamReader.isPrintable(value)) {
                tag = Tag.BINARY;
                char[] binary;
                try {
                    final byte[] bytes = value.getBytes("UTF-8");
                    final String checkValue = new String(bytes, "UTF-8");
                    if (!checkValue.equals(value)) {
                        throw new YAMLException("invalid string value has occurred");
                    }
                    binary = Base64Coder.encode(bytes);
                }
                catch (UnsupportedEncodingException e) {
                    throw new YAMLException(e);
                }
                value = String.valueOf(binary);
                style = '|';
            }
            if (this.this$0.defaultScalarStyle == null && SafeRepresenter.MULTILINE_PATTERN.matcher(value).find()) {
                style = '|';
            }
            return this.this$0.representScalar(tag, value, style);
        }
    }
    
    protected class RepresentBoolean implements Represent
    {
        final /* synthetic */ SafeRepresenter this$0;
        
        protected RepresentBoolean(final SafeRepresenter this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Node representData(final Object data) {
            String value;
            if (Boolean.TRUE.equals(data)) {
                value = "true";
            }
            else {
                value = "false";
            }
            return this.this$0.representScalar(Tag.BOOL, value);
        }
    }
    
    protected class RepresentNumber implements Represent
    {
        final /* synthetic */ SafeRepresenter this$0;
        
        protected RepresentNumber(final SafeRepresenter this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Node representData(final Object data) {
            Tag tag;
            String value;
            if (data instanceof Byte || data instanceof Short || data instanceof Integer || data instanceof Long || data instanceof BigInteger) {
                tag = Tag.INT;
                value = data.toString();
            }
            else {
                final Number number = (Number)data;
                tag = Tag.FLOAT;
                if (number.equals(Double.NaN)) {
                    value = ".NaN";
                }
                else if (number.equals(Double.POSITIVE_INFINITY)) {
                    value = ".inf";
                }
                else if (number.equals(Double.NEGATIVE_INFINITY)) {
                    value = "-.inf";
                }
                else {
                    value = number.toString();
                }
            }
            return this.this$0.representScalar(this.this$0.getTag(data.getClass(), tag), value);
        }
    }
    
    protected class RepresentList implements Represent
    {
        final /* synthetic */ SafeRepresenter this$0;
        
        protected RepresentList(final SafeRepresenter this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Node representData(final Object data) {
            return this.this$0.representSequence(this.this$0.getTag(data.getClass(), Tag.SEQ), (Iterable<?>)data, null);
        }
    }
    
    protected class RepresentIterator implements Represent
    {
        final /* synthetic */ SafeRepresenter this$0;
        
        protected RepresentIterator(final SafeRepresenter this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Node representData(final Object data) {
            final Iterator<Object> iter = (Iterator<Object>)data;
            return this.this$0.representSequence(this.this$0.getTag(data.getClass(), Tag.SEQ), new IteratorWrapper(iter), null);
        }
    }
    
    private static class IteratorWrapper implements Iterable<Object>
    {
        private Iterator<Object> iter;
        
        public IteratorWrapper(final Iterator<Object> iter) {
            super();
            this.iter = iter;
        }
        
        @Override
        public Iterator<Object> iterator() {
            return this.iter;
        }
    }
    
    protected class RepresentArray implements Represent
    {
        final /* synthetic */ SafeRepresenter this$0;
        
        protected RepresentArray(final SafeRepresenter this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Node representData(final Object data) {
            final Object[] array = (Object[])data;
            final List<Object> list = Arrays.asList(array);
            return this.this$0.representSequence(Tag.SEQ, list, null);
        }
    }
    
    protected class RepresentPrimitiveArray implements Represent
    {
        final /* synthetic */ SafeRepresenter this$0;
        
        protected RepresentPrimitiveArray(final SafeRepresenter this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Node representData(final Object data) {
            final Class<?> type = data.getClass().getComponentType();
            if (Byte.TYPE == type) {
                return this.this$0.representSequence(Tag.SEQ, this.asByteList(data), null);
            }
            if (Short.TYPE == type) {
                return this.this$0.representSequence(Tag.SEQ, this.asShortList(data), null);
            }
            if (Integer.TYPE == type) {
                return this.this$0.representSequence(Tag.SEQ, this.asIntList(data), null);
            }
            if (Long.TYPE == type) {
                return this.this$0.representSequence(Tag.SEQ, this.asLongList(data), null);
            }
            if (Float.TYPE == type) {
                return this.this$0.representSequence(Tag.SEQ, this.asFloatList(data), null);
            }
            if (Double.TYPE == type) {
                return this.this$0.representSequence(Tag.SEQ, this.asDoubleList(data), null);
            }
            if (Character.TYPE == type) {
                return this.this$0.representSequence(Tag.SEQ, this.asCharList(data), null);
            }
            if (Boolean.TYPE == type) {
                return this.this$0.representSequence(Tag.SEQ, this.asBooleanList(data), null);
            }
            throw new YAMLException("Unexpected primitive '" + type.getCanonicalName() + "'");
        }
        
        private List<Byte> asByteList(final Object in) {
            final byte[] array = (byte[])in;
            final List<Byte> list = new ArrayList<Byte>(array.length);
            for (int i = 0; i < array.length; ++i) {
                list.add(array[i]);
            }
            return list;
        }
        
        private List<Short> asShortList(final Object in) {
            final short[] array = (short[])in;
            final List<Short> list = new ArrayList<Short>(array.length);
            for (int i = 0; i < array.length; ++i) {
                list.add(array[i]);
            }
            return list;
        }
        
        private List<Integer> asIntList(final Object in) {
            final int[] array = (int[])in;
            final List<Integer> list = new ArrayList<Integer>(array.length);
            for (int i = 0; i < array.length; ++i) {
                list.add(array[i]);
            }
            return list;
        }
        
        private List<Long> asLongList(final Object in) {
            final long[] array = (long[])in;
            final List<Long> list = new ArrayList<Long>(array.length);
            for (int i = 0; i < array.length; ++i) {
                list.add(array[i]);
            }
            return list;
        }
        
        private List<Float> asFloatList(final Object in) {
            final float[] array = (float[])in;
            final List<Float> list = new ArrayList<Float>(array.length);
            for (int i = 0; i < array.length; ++i) {
                list.add(array[i]);
            }
            return list;
        }
        
        private List<Double> asDoubleList(final Object in) {
            final double[] array = (double[])in;
            final List<Double> list = new ArrayList<Double>(array.length);
            for (int i = 0; i < array.length; ++i) {
                list.add(array[i]);
            }
            return list;
        }
        
        private List<Character> asCharList(final Object in) {
            final char[] array = (char[])in;
            final List<Character> list = new ArrayList<Character>(array.length);
            for (int i = 0; i < array.length; ++i) {
                list.add(array[i]);
            }
            return list;
        }
        
        private List<Boolean> asBooleanList(final Object in) {
            final boolean[] array = (boolean[])in;
            final List<Boolean> list = new ArrayList<Boolean>(array.length);
            for (int i = 0; i < array.length; ++i) {
                list.add(array[i]);
            }
            return list;
        }
    }
    
    protected class RepresentMap implements Represent
    {
        final /* synthetic */ SafeRepresenter this$0;
        
        protected RepresentMap(final SafeRepresenter this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Node representData(final Object data) {
            return this.this$0.representMapping(this.this$0.getTag(data.getClass(), Tag.MAP), (Map<?, ?>)data, null);
        }
    }
    
    protected class RepresentSet implements Represent
    {
        final /* synthetic */ SafeRepresenter this$0;
        
        protected RepresentSet(final SafeRepresenter this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Node representData(final Object data) {
            final Map<Object, Object> value = new LinkedHashMap<Object, Object>();
            final Set<Object> set = (Set<Object>)data;
            for (final Object key : set) {
                value.put(key, null);
            }
            return this.this$0.representMapping(this.this$0.getTag(data.getClass(), Tag.SET), value, null);
        }
    }
    
    protected class RepresentDate implements Represent
    {
        final /* synthetic */ SafeRepresenter this$0;
        
        protected RepresentDate(final SafeRepresenter this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Node representData(final Object data) {
            Calendar calendar;
            if (data instanceof Calendar) {
                calendar = (Calendar)data;
            }
            else {
                calendar = Calendar.getInstance((this.this$0.getTimeZone() == null) ? TimeZone.getTimeZone("UTC") : this.this$0.timeZone);
                calendar.setTime((Date)data);
            }
            final int years = calendar.get(1);
            final int months = calendar.get(2) + 1;
            final int days = calendar.get(5);
            final int hour24 = calendar.get(11);
            final int minutes = calendar.get(12);
            final int seconds = calendar.get(13);
            final int millis = calendar.get(14);
            final StringBuilder buffer = new StringBuilder(String.valueOf(years));
            while (buffer.length() < 4) {
                buffer.insert(0, "0");
            }
            buffer.append("-");
            if (months < 10) {
                buffer.append("0");
            }
            buffer.append(String.valueOf(months));
            buffer.append("-");
            if (days < 10) {
                buffer.append("0");
            }
            buffer.append(String.valueOf(days));
            buffer.append("T");
            if (hour24 < 10) {
                buffer.append("0");
            }
            buffer.append(String.valueOf(hour24));
            buffer.append(":");
            if (minutes < 10) {
                buffer.append("0");
            }
            buffer.append(String.valueOf(minutes));
            buffer.append(":");
            if (seconds < 10) {
                buffer.append("0");
            }
            buffer.append(String.valueOf(seconds));
            if (millis > 0) {
                if (millis < 10) {
                    buffer.append(".00");
                }
                else if (millis < 100) {
                    buffer.append(".0");
                }
                else {
                    buffer.append(".");
                }
                buffer.append(String.valueOf(millis));
            }
            if (TimeZone.getTimeZone("UTC").equals(calendar.getTimeZone())) {
                buffer.append("Z");
            }
            else {
                final int gmtOffset = calendar.getTimeZone().getOffset(calendar.get(0), calendar.get(1), calendar.get(2), calendar.get(5), calendar.get(7), calendar.get(14));
                final int minutesOffset = gmtOffset / 60000;
                final int hoursOffset = minutesOffset / 60;
                final int partOfHour = minutesOffset % 60;
                buffer.append(((hoursOffset > 0) ? "+" : "") + hoursOffset + ":" + ((partOfHour < 10) ? ("0" + partOfHour) : Integer.valueOf(partOfHour)));
            }
            return this.this$0.representScalar(this.this$0.getTag(data.getClass(), Tag.TIMESTAMP), buffer.toString(), null);
        }
    }
    
    protected class RepresentEnum implements Represent
    {
        final /* synthetic */ SafeRepresenter this$0;
        
        protected RepresentEnum(final SafeRepresenter this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Node representData(final Object data) {
            final Tag tag = new Tag(data.getClass());
            return this.this$0.representScalar(this.this$0.getTag(data.getClass(), tag), ((Enum)data).name());
        }
    }
    
    protected class RepresentByteArray implements Represent
    {
        final /* synthetic */ SafeRepresenter this$0;
        
        protected RepresentByteArray(final SafeRepresenter this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Node representData(final Object data) {
            final char[] binary = Base64Coder.encode((byte[])data);
            return this.this$0.representScalar(Tag.BINARY, String.valueOf(binary), '|');
        }
    }
    
    protected class RepresentUuid implements Represent
    {
        final /* synthetic */ SafeRepresenter this$0;
        
        protected RepresentUuid(final SafeRepresenter this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Node representData(final Object data) {
            return this.this$0.representScalar(this.this$0.getTag(data.getClass(), new Tag(UUID.class)), data.toString());
        }
    }
}
