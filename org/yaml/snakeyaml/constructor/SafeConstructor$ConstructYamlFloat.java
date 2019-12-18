package org.yaml.snakeyaml.constructor;

import org.yaml.snakeyaml.nodes.*;

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
