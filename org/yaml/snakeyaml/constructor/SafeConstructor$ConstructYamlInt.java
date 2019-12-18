package org.yaml.snakeyaml.constructor;

import org.yaml.snakeyaml.nodes.*;

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
                return SafeConstructor.access$100(this.this$0, sign, String.valueOf(val), 10);
            }
            return SafeConstructor.access$100(this.this$0, sign, value, 10);
        }
        return SafeConstructor.access$100(this.this$0, sign, value, base);
    }
}
