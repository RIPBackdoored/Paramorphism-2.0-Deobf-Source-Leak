package org.yaml.snakeyaml.representer;

import org.yaml.snakeyaml.*;
import org.yaml.snakeyaml.introspector.*;
import org.yaml.snakeyaml.error.*;
import java.util.*;
import org.yaml.snakeyaml.nodes.*;

public abstract class BaseRepresenter
{
    protected final Map<Class<?>, Represent> representers;
    protected Represent nullRepresenter;
    protected final Map<Class<?>, Represent> multiRepresenters;
    protected Character defaultScalarStyle;
    protected DumperOptions.FlowStyle defaultFlowStyle;
    protected final Map<Object, Node> representedObjects;
    protected Object objectToRepresent;
    private PropertyUtils propertyUtils;
    private boolean explicitPropertyUtils;
    
    public BaseRepresenter() {
        super();
        this.representers = new HashMap<Class<?>, Represent>();
        this.multiRepresenters = new LinkedHashMap<Class<?>, Represent>();
        this.defaultFlowStyle = DumperOptions.FlowStyle.AUTO;
        this.representedObjects = new IdentityHashMap<Object, Node>() {
            final /* synthetic */ BaseRepresenter this$0;
            
            BaseRepresenter$1() {
                this.this$0 = this$0;
                super();
            }
            
            @Override
            public Node put(final Object key, final Node value) {
                return super.put(key, new AnchorNode(value));
            }
            
            @Override
            public /* bridge */ Object put(final Object key, final Object o) {
                return this.put(key, (Node)o);
            }
        };
        this.explicitPropertyUtils = false;
    }
    
    public Node represent(final Object data) {
        final Node node = this.representData(data);
        this.representedObjects.clear();
        this.objectToRepresent = null;
        return node;
    }
    
    protected final Node representData(final Object data) {
        this.objectToRepresent = data;
        if (this.representedObjects.containsKey(this.objectToRepresent)) {
            final Node node = this.representedObjects.get(this.objectToRepresent);
            return node;
        }
        if (data == null) {
            final Node node = this.nullRepresenter.representData(null);
            return node;
        }
        final Class<?> clazz = data.getClass();
        Node node;
        if (this.representers.containsKey(clazz)) {
            final Represent representer = this.representers.get(clazz);
            node = representer.representData(data);
        }
        else {
            for (final Class<?> repr : this.multiRepresenters.keySet()) {
                if (repr != null && repr.isInstance(data)) {
                    final Represent representer2 = this.multiRepresenters.get(repr);
                    node = representer2.representData(data);
                    return node;
                }
            }
            if (this.multiRepresenters.containsKey(null)) {
                final Represent representer = this.multiRepresenters.get(null);
                node = representer.representData(data);
            }
            else {
                final Represent representer = this.representers.get(null);
                node = representer.representData(data);
            }
        }
        return node;
    }
    
    protected Node representScalar(final Tag tag, final String value, Character style) {
        if (style == null) {
            style = this.defaultScalarStyle;
        }
        final Node node = new ScalarNode(tag, value, null, null, style);
        return node;
    }
    
    protected Node representScalar(final Tag tag, final String value) {
        return this.representScalar(tag, value, null);
    }
    
    protected Node representSequence(final Tag tag, final Iterable<?> sequence, final Boolean flowStyle) {
        int size = 10;
        if (sequence instanceof List) {
            size = ((List)sequence).size();
        }
        final List<Node> value = new ArrayList<Node>(size);
        final SequenceNode node = new SequenceNode(tag, value, flowStyle);
        this.representedObjects.put(this.objectToRepresent, node);
        boolean bestStyle = true;
        for (final Object item : sequence) {
            final Node nodeItem = this.representData(item);
            if (!(nodeItem instanceof ScalarNode) || ((ScalarNode)nodeItem).getStyle() != null) {
                bestStyle = false;
            }
            value.add(nodeItem);
        }
        if (flowStyle == null) {
            if (this.defaultFlowStyle != DumperOptions.FlowStyle.AUTO) {
                node.setFlowStyle(this.defaultFlowStyle.getStyleBoolean());
            }
            else {
                node.setFlowStyle(bestStyle);
            }
        }
        return node;
    }
    
    protected Node representMapping(final Tag tag, final Map<?, ?> mapping, final Boolean flowStyle) {
        final List<NodeTuple> value = new ArrayList<NodeTuple>(mapping.size());
        final MappingNode node = new MappingNode(tag, value, flowStyle);
        this.representedObjects.put(this.objectToRepresent, node);
        boolean bestStyle = true;
        for (final Map.Entry<?, ?> entry : mapping.entrySet()) {
            final Node nodeKey = this.representData(entry.getKey());
            final Node nodeValue = this.representData(entry.getValue());
            if (!(nodeKey instanceof ScalarNode) || ((ScalarNode)nodeKey).getStyle() != null) {
                bestStyle = false;
            }
            if (!(nodeValue instanceof ScalarNode) || ((ScalarNode)nodeValue).getStyle() != null) {
                bestStyle = false;
            }
            value.add(new NodeTuple(nodeKey, nodeValue));
        }
        if (flowStyle == null) {
            if (this.defaultFlowStyle != DumperOptions.FlowStyle.AUTO) {
                node.setFlowStyle(this.defaultFlowStyle.getStyleBoolean());
            }
            else {
                node.setFlowStyle(bestStyle);
            }
        }
        return node;
    }
    
    public void setDefaultScalarStyle(final DumperOptions.ScalarStyle defaultStyle) {
        this.defaultScalarStyle = defaultStyle.getChar();
    }
    
    public void setDefaultFlowStyle(final DumperOptions.FlowStyle defaultFlowStyle) {
        this.defaultFlowStyle = defaultFlowStyle;
    }
    
    public void setPropertyUtils(final PropertyUtils propertyUtils) {
        this.propertyUtils = propertyUtils;
        this.explicitPropertyUtils = true;
    }
    
    public final PropertyUtils getPropertyUtils() {
        if (this.propertyUtils == null) {
            this.propertyUtils = new PropertyUtils();
        }
        return this.propertyUtils;
    }
    
    public final boolean isExplicitPropertyUtils() {
        return this.explicitPropertyUtils;
    }
}
