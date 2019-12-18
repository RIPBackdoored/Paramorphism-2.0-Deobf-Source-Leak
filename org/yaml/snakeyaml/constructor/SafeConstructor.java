package org.yaml.snakeyaml.constructor;

import java.math.*;
import org.yaml.snakeyaml.nodes.*;
import org.yaml.snakeyaml.external.biz.base64Coder.*;
import java.text.*;
import java.util.regex.*;
import java.util.*;
import org.yaml.snakeyaml.error.*;

public class SafeConstructor extends BaseConstructor
{
    public static final ConstructUndefined undefinedConstructor;
    private static final Map<String, Boolean> BOOL_VALUES;
    private static final Pattern TIMESTAMP_REGEXP;
    private static final Pattern YMD_REGEXP;
    
    public SafeConstructor() {
        super();
        this.yamlConstructors.put(Tag.NULL, new ConstructYamlNull());
        this.yamlConstructors.put(Tag.BOOL, new ConstructYamlBool());
        this.yamlConstructors.put(Tag.INT, new ConstructYamlInt());
        this.yamlConstructors.put(Tag.FLOAT, new ConstructYamlFloat());
        this.yamlConstructors.put(Tag.BINARY, new ConstructYamlBinary());
        this.yamlConstructors.put(Tag.TIMESTAMP, new ConstructYamlTimestamp());
        this.yamlConstructors.put(Tag.OMAP, new ConstructYamlOmap());
        this.yamlConstructors.put(Tag.PAIRS, new ConstructYamlPairs());
        this.yamlConstructors.put(Tag.SET, new ConstructYamlSet());
        this.yamlConstructors.put(Tag.STR, new ConstructYamlStr());
        this.yamlConstructors.put(Tag.SEQ, new ConstructYamlSeq());
        this.yamlConstructors.put(Tag.MAP, new ConstructYamlMap());
        this.yamlConstructors.put(null, SafeConstructor.undefinedConstructor);
        this.yamlClassConstructors.put(NodeId.scalar, SafeConstructor.undefinedConstructor);
        this.yamlClassConstructors.put(NodeId.sequence, SafeConstructor.undefinedConstructor);
        this.yamlClassConstructors.put(NodeId.mapping, SafeConstructor.undefinedConstructor);
    }
    
    protected void flattenMapping(final MappingNode node) {
        this.processDuplicateKeys(node);
        if (node.isMerged()) {
            node.setValue(this.mergeNode(node, true, new HashMap<Object, Integer>(), new ArrayList<NodeTuple>()));
        }
    }
    
    protected void processDuplicateKeys(final MappingNode node) {
        final List<NodeTuple> nodeValue = node.getValue();
        final Map<Object, Integer> keys = new HashMap<Object, Integer>(nodeValue.size());
        final Deque<Integer> toRemove = new ArrayDeque<Integer>();
        int i = 0;
        for (final NodeTuple tuple : nodeValue) {
            final Node keyNode = tuple.getKeyNode();
            if (!keyNode.getTag().equals(Tag.MERGE)) {
                final Object key = this.constructObject(keyNode);
                if (key != null) {
                    try {
                        key.hashCode();
                    }
                    catch (Exception e) {
                        throw new ConstructorException("while constructing a mapping", node.getStartMark(), "found unacceptable key " + key, tuple.getKeyNode().getStartMark(), e);
                    }
                }
                final Integer prevIndex = keys.put(key, i);
                if (prevIndex != null) {
                    if (!this.isAllowDuplicateKeys()) {
                        throw new IllegalStateException("duplicate key: " + key);
                    }
                    toRemove.add(prevIndex);
                }
            }
            ++i;
        }
        final Iterator<Integer> indicies2remove = toRemove.descendingIterator();
        while (indicies2remove.hasNext()) {
            nodeValue.remove((int)indicies2remove.next());
        }
    }
    
    private List<NodeTuple> mergeNode(final MappingNode node, final boolean isPreffered, final Map<Object, Integer> key2index, final List<NodeTuple> values) {
        final Iterator<NodeTuple> iter = node.getValue().iterator();
        while (iter.hasNext()) {
            final NodeTuple nodeTuple = iter.next();
            final Node keyNode = nodeTuple.getKeyNode();
            final Node valueNode = nodeTuple.getValueNode();
            if (keyNode.getTag().equals(Tag.MERGE)) {
                iter.remove();
                switch (valueNode.getNodeId()) {
                    case mapping: {
                        final MappingNode mn = (MappingNode)valueNode;
                        this.mergeNode(mn, false, key2index, values);
                        continue;
                    }
                    case sequence: {
                        final SequenceNode sn = (SequenceNode)valueNode;
                        final List<Node> vals = sn.getValue();
                        for (final Node subnode : vals) {
                            if (!(subnode instanceof MappingNode)) {
                                throw new ConstructorException("while constructing a mapping", node.getStartMark(), "expected a mapping for merging, but found " + subnode.getNodeId(), subnode.getStartMark());
                            }
                            final MappingNode mnode = (MappingNode)subnode;
                            this.mergeNode(mnode, false, key2index, values);
                        }
                        continue;
                    }
                    default: {
                        throw new ConstructorException("while constructing a mapping", node.getStartMark(), "expected a mapping or list of mappings for merging, but found " + valueNode.getNodeId(), valueNode.getStartMark());
                    }
                }
            }
            else {
                final Object key = this.constructObject(keyNode);
                if (!key2index.containsKey(key)) {
                    values.add(nodeTuple);
                    key2index.put(key, values.size() - 1);
                }
                else {
                    if (!isPreffered) {
                        continue;
                    }
                    values.set(key2index.get(key), nodeTuple);
                }
            }
        }
        return values;
    }
    
    @Override
    protected void constructMapping2ndStep(final MappingNode node, final Map<Object, Object> mapping) {
        this.flattenMapping(node);
        super.constructMapping2ndStep(node, mapping);
    }
    
    @Override
    protected void constructSet2ndStep(final MappingNode node, final Set<Object> set) {
        this.flattenMapping(node);
        super.constructSet2ndStep(node, set);
    }
    
    private Number createNumber(final int sign, String number, final int radix) {
        if (sign < 0) {
            number = "-" + number;
        }
        Number result;
        try {
            result = Integer.valueOf(number, radix);
        }
        catch (NumberFormatException e) {
            try {
                result = Long.valueOf(number, radix);
            }
            catch (NumberFormatException e2) {
                result = new BigInteger(number, radix);
            }
        }
        return result;
    }
    
    static /* synthetic */ Map access$000() {
        return SafeConstructor.BOOL_VALUES;
    }
    
    static /* synthetic */ Number access$100(final SafeConstructor x0, final int x1, final String x2, final int x3) {
        return x0.createNumber(x1, x2, x3);
    }
    
    static /* synthetic */ Pattern access$200() {
        return SafeConstructor.YMD_REGEXP;
    }
    
    static /* synthetic */ Pattern access$300() {
        return SafeConstructor.TIMESTAMP_REGEXP;
    }
    
    static {
        undefinedConstructor = new ConstructUndefined();
        (BOOL_VALUES = new HashMap<String, Boolean>()).put("yes", Boolean.TRUE);
        SafeConstructor.BOOL_VALUES.put("no", Boolean.FALSE);
        SafeConstructor.BOOL_VALUES.put("true", Boolean.TRUE);
        SafeConstructor.BOOL_VALUES.put("false", Boolean.FALSE);
        SafeConstructor.BOOL_VALUES.put("on", Boolean.TRUE);
        SafeConstructor.BOOL_VALUES.put("off", Boolean.FALSE);
        TIMESTAMP_REGEXP = Pattern.compile("^([0-9][0-9][0-9][0-9])-([0-9][0-9]?)-([0-9][0-9]?)(?:(?:[Tt]|[ \t]+)([0-9][0-9]?):([0-9][0-9]):([0-9][0-9])(?:\\.([0-9]*))?(?:[ \t]*(?:Z|([-+][0-9][0-9]?)(?::([0-9][0-9])?)?))?)?$");
        YMD_REGEXP = Pattern.compile("^([0-9][0-9][0-9][0-9])-([0-9][0-9]?)-([0-9][0-9]?)$");
    }
    
    public class ConstructYamlNull extends AbstractConstruct
    {
        final /* synthetic */ SafeConstructor this$0;
        
        public ConstructYamlNull(final SafeConstructor this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Object construct(final Node node) {
            this.this$0.constructScalar((ScalarNode)node);
            return null;
        }
    }
    
    public class ConstructYamlBool extends AbstractConstruct
    {
        final /* synthetic */ SafeConstructor this$0;
        
        public ConstructYamlBool(final SafeConstructor this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Object construct(final Node node) {
            final String val = (String)this.this$0.constructScalar((ScalarNode)node);
            return SafeConstructor.BOOL_VALUES.get(val.toLowerCase());
        }
    }
    
    public class ConstructYamlInt extends AbstractConstruct
    {
        final /* synthetic */ SafeConstructor this$0;
        
        public ConstructYamlInt(final SafeConstructor this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Object construct(final Node node) {
            String value = this.this$0.constructScalar((ScalarNode)node).toString().replaceAll("_", "");
            int sign = 1;
            final char first = value.charAt(0);
            if (first == '-') {
                sign = -1;
                value = value.substring(1);
            }
            else if (first == '+') {
                value = value.substring(1);
            }
            int base = 10;
            if ("0".equals(value)) {
                return 0;
            }
            if (value.startsWith("0b")) {
                value = value.substring(2);
                base = 2;
            }
            else if (value.startsWith("0x")) {
                value = value.substring(2);
                base = 16;
            }
            else if (value.startsWith("0")) {
                value = value.substring(1);
                base = 8;
            }
            else {
                if (value.indexOf(58) != -1) {
                    final String[] digits = value.split(":");
                    int bes = 1;
                    int val = 0;
                    for (int i = 0, j = digits.length; i < j; ++i) {
                        val += (int)(Long.parseLong(digits[j - i - 1]) * bes);
                        bes *= 60;
                    }
                    return SafeConstructor.this.createNumber(sign, String.valueOf(val), 10);
                }
                return SafeConstructor.this.createNumber(sign, value, 10);
            }
            return SafeConstructor.this.createNumber(sign, value, base);
        }
    }
    
    public class ConstructYamlFloat extends AbstractConstruct
    {
        final /* synthetic */ SafeConstructor this$0;
        
        public ConstructYamlFloat(final SafeConstructor this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Object construct(final Node node) {
            String value = this.this$0.constructScalar((ScalarNode)node).toString().replaceAll("_", "");
            int sign = 1;
            final char first = value.charAt(0);
            if (first == '-') {
                sign = -1;
                value = value.substring(1);
            }
            else if (first == '+') {
                value = value.substring(1);
            }
            final String valLower = value.toLowerCase();
            if (".inf".equals(valLower)) {
                return new Double((sign == -1) ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY);
            }
            if (".nan".equals(valLower)) {
                return new Double(Double.NaN);
            }
            if (value.indexOf(58) != -1) {
                final String[] digits = value.split(":");
                int bes = 1;
                double val = 0.0;
                for (int i = 0, j = digits.length; i < j; ++i) {
                    val += Double.parseDouble(digits[j - i - 1]) * bes;
                    bes *= 60;
                }
                return new Double(sign * val);
            }
            final Double d = Double.valueOf(value);
            return new Double(d * sign);
        }
    }
    
    public class ConstructYamlBinary extends AbstractConstruct
    {
        final /* synthetic */ SafeConstructor this$0;
        
        public ConstructYamlBinary(final SafeConstructor this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Object construct(final Node node) {
            final String noWhiteSpaces = this.this$0.constructScalar((ScalarNode)node).toString().replaceAll("\\s", "");
            final byte[] decoded = Base64Coder.decode(noWhiteSpaces.toCharArray());
            return decoded;
        }
    }
    
    public class ConstructYamlNumber extends AbstractConstruct
    {
        private final NumberFormat nf;
        final /* synthetic */ SafeConstructor this$0;
        
        public ConstructYamlNumber(final SafeConstructor this$0) {
            this.this$0 = this$0;
            super();
            this.nf = NumberFormat.getInstance();
        }
        
        @Override
        public Object construct(final Node node) {
            final ScalarNode scalar = (ScalarNode)node;
            try {
                return this.nf.parse(scalar.getValue());
            }
            catch (ParseException e) {
                final String lowerCaseValue = scalar.getValue().toLowerCase();
                if (lowerCaseValue.contains("inf") || lowerCaseValue.contains("nan")) {
                    return this.this$0.yamlConstructors.get(Tag.FLOAT).construct(node);
                }
                throw new IllegalArgumentException("Unable to parse as Number: " + scalar.getValue());
            }
        }
    }
    
    public static class ConstructYamlTimestamp extends AbstractConstruct
    {
        private Calendar calendar;
        
        public ConstructYamlTimestamp() {
            super();
        }
        
        public Calendar getCalendar() {
            return this.calendar;
        }
        
        @Override
        public Object construct(final Node node) {
            final ScalarNode scalar = (ScalarNode)node;
            final String nodeValue = scalar.getValue();
            Matcher match = SafeConstructor.YMD_REGEXP.matcher(nodeValue);
            if (match.matches()) {
                final String year_s = match.group(1);
                final String month_s = match.group(2);
                final String day_s = match.group(3);
                (this.calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))).clear();
                this.calendar.set(1, Integer.parseInt(year_s));
                this.calendar.set(2, Integer.parseInt(month_s) - 1);
                this.calendar.set(5, Integer.parseInt(day_s));
                return this.calendar.getTime();
            }
            match = SafeConstructor.TIMESTAMP_REGEXP.matcher(nodeValue);
            if (!match.matches()) {
                throw new YAMLException("Unexpected timestamp: " + nodeValue);
            }
            final String year_s = match.group(1);
            final String month_s = match.group(2);
            final String day_s = match.group(3);
            final String hour_s = match.group(4);
            final String min_s = match.group(5);
            String seconds = match.group(6);
            final String millis = match.group(7);
            if (millis != null) {
                seconds = seconds + "." + millis;
            }
            final double fractions = Double.parseDouble(seconds);
            final int sec_s = (int)Math.round(Math.floor(fractions));
            final int usec = (int)Math.round((fractions - sec_s) * 1000.0);
            final String timezoneh_s = match.group(8);
            final String timezonem_s = match.group(9);
            TimeZone timeZone;
            if (timezoneh_s != null) {
                final String time = (timezonem_s != null) ? (":" + timezonem_s) : "00";
                timeZone = TimeZone.getTimeZone("GMT" + timezoneh_s + time);
            }
            else {
                timeZone = TimeZone.getTimeZone("UTC");
            }
            (this.calendar = Calendar.getInstance(timeZone)).set(1, Integer.parseInt(year_s));
            this.calendar.set(2, Integer.parseInt(month_s) - 1);
            this.calendar.set(5, Integer.parseInt(day_s));
            this.calendar.set(11, Integer.parseInt(hour_s));
            this.calendar.set(12, Integer.parseInt(min_s));
            this.calendar.set(13, sec_s);
            this.calendar.set(14, usec);
            return this.calendar.getTime();
        }
    }
    
    public class ConstructYamlOmap extends AbstractConstruct
    {
        final /* synthetic */ SafeConstructor this$0;
        
        public ConstructYamlOmap(final SafeConstructor this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Object construct(final Node node) {
            final Map<Object, Object> omap = new LinkedHashMap<Object, Object>();
            if (!(node instanceof SequenceNode)) {
                throw new ConstructorException("while constructing an ordered map", node.getStartMark(), "expected a sequence, but found " + node.getNodeId(), node.getStartMark());
            }
            final SequenceNode snode = (SequenceNode)node;
            for (final Node subnode : snode.getValue()) {
                if (!(subnode instanceof MappingNode)) {
                    throw new ConstructorException("while constructing an ordered map", node.getStartMark(), "expected a mapping of length 1, but found " + subnode.getNodeId(), subnode.getStartMark());
                }
                final MappingNode mnode = (MappingNode)subnode;
                if (mnode.getValue().size() != 1) {
                    throw new ConstructorException("while constructing an ordered map", node.getStartMark(), "expected a single mapping item, but found " + mnode.getValue().size() + " items", mnode.getStartMark());
                }
                final Node keyNode = mnode.getValue().get(0).getKeyNode();
                final Node valueNode = mnode.getValue().get(0).getValueNode();
                final Object key = this.this$0.constructObject(keyNode);
                final Object value = this.this$0.constructObject(valueNode);
                omap.put(key, value);
            }
            return omap;
        }
    }
    
    public class ConstructYamlPairs extends AbstractConstruct
    {
        final /* synthetic */ SafeConstructor this$0;
        
        public ConstructYamlPairs(final SafeConstructor this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Object construct(final Node node) {
            if (!(node instanceof SequenceNode)) {
                throw new ConstructorException("while constructing pairs", node.getStartMark(), "expected a sequence, but found " + node.getNodeId(), node.getStartMark());
            }
            final SequenceNode snode = (SequenceNode)node;
            final List<Object[]> pairs = new ArrayList<Object[]>(snode.getValue().size());
            for (final Node subnode : snode.getValue()) {
                if (!(subnode instanceof MappingNode)) {
                    throw new ConstructorException("while constructingpairs", node.getStartMark(), "expected a mapping of length 1, but found " + subnode.getNodeId(), subnode.getStartMark());
                }
                final MappingNode mnode = (MappingNode)subnode;
                if (mnode.getValue().size() != 1) {
                    throw new ConstructorException("while constructing pairs", node.getStartMark(), "expected a single mapping item, but found " + mnode.getValue().size() + " items", mnode.getStartMark());
                }
                final Node keyNode = mnode.getValue().get(0).getKeyNode();
                final Node valueNode = mnode.getValue().get(0).getValueNode();
                final Object key = this.this$0.constructObject(keyNode);
                final Object value = this.this$0.constructObject(valueNode);
                pairs.add(new Object[] { key, value });
            }
            return pairs;
        }
    }
    
    public class ConstructYamlSet implements Construct
    {
        final /* synthetic */ SafeConstructor this$0;
        
        public ConstructYamlSet(final SafeConstructor this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Object construct(final Node node) {
            if (node.isTwoStepsConstruction()) {
                return this.this$0.createDefaultSet();
            }
            return this.this$0.constructSet((MappingNode)node);
        }
        
        @Override
        public void construct2ndStep(final Node node, final Object object) {
            if (node.isTwoStepsConstruction()) {
                this.this$0.constructSet2ndStep((MappingNode)node, (Set<Object>)object);
                return;
            }
            throw new YAMLException("Unexpected recursive set structure. Node: " + node);
        }
    }
    
    public class ConstructYamlStr extends AbstractConstruct
    {
        final /* synthetic */ SafeConstructor this$0;
        
        public ConstructYamlStr(final SafeConstructor this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Object construct(final Node node) {
            return this.this$0.constructScalar((ScalarNode)node);
        }
    }
    
    public class ConstructYamlSeq implements Construct
    {
        final /* synthetic */ SafeConstructor this$0;
        
        public ConstructYamlSeq(final SafeConstructor this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Object construct(final Node node) {
            final SequenceNode seqNode = (SequenceNode)node;
            if (node.isTwoStepsConstruction()) {
                return this.this$0.createDefaultList(seqNode.getValue().size());
            }
            return this.this$0.constructSequence(seqNode);
        }
        
        @Override
        public void construct2ndStep(final Node node, final Object data) {
            if (node.isTwoStepsConstruction()) {
                this.this$0.constructSequenceStep2((SequenceNode)node, (Collection<Object>)data);
                return;
            }
            throw new YAMLException("Unexpected recursive sequence structure. Node: " + node);
        }
    }
    
    public class ConstructYamlMap implements Construct
    {
        final /* synthetic */ SafeConstructor this$0;
        
        public ConstructYamlMap(final SafeConstructor this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Object construct(final Node node) {
            if (node.isTwoStepsConstruction()) {
                return this.this$0.createDefaultMap();
            }
            return this.this$0.constructMapping((MappingNode)node);
        }
        
        @Override
        public void construct2ndStep(final Node node, final Object object) {
            if (node.isTwoStepsConstruction()) {
                this.this$0.constructMapping2ndStep((MappingNode)node, (Map<Object, Object>)object);
                return;
            }
            throw new YAMLException("Unexpected recursive mapping structure. Node: " + node);
        }
    }
    
    public static final class ConstructUndefined extends AbstractConstruct
    {
        public ConstructUndefined() {
            super();
        }
        
        @Override
        public Object construct(final Node node) {
            throw new ConstructorException(null, null, "could not determine a constructor for the tag " + node.getTag(), node.getStartMark());
        }
    }
}
