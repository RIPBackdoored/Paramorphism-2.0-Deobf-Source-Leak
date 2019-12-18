package org.yaml.snakeyaml.constructor;

import org.yaml.snakeyaml.error.*;
import org.yaml.snakeyaml.*;
import org.yaml.snakeyaml.nodes.*;
import java.util.*;
import org.yaml.snakeyaml.introspector.*;

protected class ConstructMapping implements Construct
{
    final /* synthetic */ Constructor this$0;
    
    protected ConstructMapping(final Constructor this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public Object construct(final Node node) {
        final MappingNode mnode = (MappingNode)node;
        if (Properties.class.isAssignableFrom(node.getType())) {
            final Properties properties = new Properties();
            if (!node.isTwoStepsConstruction()) {
                this.this$0.constructMapping2ndStep(mnode, properties);
                return properties;
            }
            throw new YAMLException("Properties must not be recursive.");
        }
        else {
            if (SortedMap.class.isAssignableFrom(node.getType())) {
                final SortedMap<Object, Object> map = new TreeMap<Object, Object>();
                if (!node.isTwoStepsConstruction()) {
                    this.this$0.constructMapping2ndStep(mnode, map);
                }
                return map;
            }
            if (Map.class.isAssignableFrom(node.getType())) {
                if (node.isTwoStepsConstruction()) {
                    return this.this$0.createDefaultMap();
                }
                return this.this$0.constructMapping(mnode);
            }
            else {
                if (SortedSet.class.isAssignableFrom(node.getType())) {
                    final SortedSet<Object> set = new TreeSet<Object>();
                    this.this$0.constructSet2ndStep(mnode, set);
                    return set;
                }
                if (Collection.class.isAssignableFrom(node.getType())) {
                    if (node.isTwoStepsConstruction()) {
                        return this.this$0.createDefaultSet();
                    }
                    return this.this$0.constructSet(mnode);
                }
                else {
                    if (node.isTwoStepsConstruction()) {
                        return this.createEmptyJavaBean(mnode);
                    }
                    return this.constructJavaBean2ndStep(mnode, this.createEmptyJavaBean(mnode));
                }
            }
        }
    }
    
    @Override
    public void construct2ndStep(final Node node, final Object object) {
        if (Map.class.isAssignableFrom(node.getType())) {
            this.this$0.constructMapping2ndStep((MappingNode)node, (Map<Object, Object>)object);
        }
        else if (Set.class.isAssignableFrom(node.getType())) {
            this.this$0.constructSet2ndStep((MappingNode)node, (Set<Object>)object);
        }
        else {
            this.constructJavaBean2ndStep((MappingNode)node, object);
        }
    }
    
    protected Object createEmptyJavaBean(final MappingNode node) {
        try {
            final java.lang.reflect.Constructor<?> c = node.getType().getDeclaredConstructor((Class<?>[])new Class[0]);
            c.setAccessible(true);
            return c.newInstance(new Object[0]);
        }
        catch (Exception e) {
            throw new YAMLException(e);
        }
    }
    
    protected Object constructJavaBean2ndStep(final MappingNode node, final Object object) {
        this.this$0.flattenMapping(node);
        final Class<?> beanType = node.getType();
        final List<NodeTuple> nodeValue = node.getValue();
        for (final NodeTuple tuple : nodeValue) {
            if (!(tuple.getKeyNode() instanceof ScalarNode)) {
                throw new YAMLException("Keys must be scalars but found: " + tuple.getKeyNode());
            }
            final ScalarNode keyNode = (ScalarNode)tuple.getKeyNode();
            final Node valueNode = tuple.getValueNode();
            keyNode.setType(String.class);
            final String key = (String)this.this$0.constructObject(keyNode);
            try {
                final Property property = this.getProperty(beanType, key);
                valueNode.setType(property.getType());
                final TypeDescription memberDescription = this.this$0.typeDefinitions.get(beanType);
                boolean typeDetected = false;
                if (memberDescription != null) {
                    switch (valueNode.getNodeId()) {
                        case sequence: {
                            final SequenceNode snode = (SequenceNode)valueNode;
                            final Class<?> memberType = memberDescription.getListPropertyType(key);
                            if (memberType != null) {
                                snode.setListType(memberType);
                                typeDetected = true;
                                break;
                            }
                            if (property.getType().isArray()) {
                                snode.setListType(property.getType().getComponentType());
                                typeDetected = true;
                                break;
                            }
                            break;
                        }
                        case mapping: {
                            final MappingNode mnode = (MappingNode)valueNode;
                            final Class<?> keyType = memberDescription.getMapKeyType(key);
                            if (keyType != null) {
                                mnode.setTypes(keyType, memberDescription.getMapValueType(key));
                                typeDetected = true;
                                break;
                            }
                            break;
                        }
                    }
                }
                if (!typeDetected && valueNode.getNodeId() != NodeId.scalar) {
                    final Class<?>[] arguments = property.getActualTypeArguments();
                    if (arguments != null && arguments.length > 0) {
                        if (valueNode.getNodeId() == NodeId.sequence) {
                            final Class<?> t = arguments[0];
                            final SequenceNode snode2 = (SequenceNode)valueNode;
                            snode2.setListType(t);
                        }
                        else if (valueNode.getTag().equals(Tag.SET)) {
                            final Class<?> t = arguments[0];
                            final MappingNode mnode = (MappingNode)valueNode;
                            mnode.setOnlyKeyType(t);
                            mnode.setUseClassConstructor(true);
                        }
                        else if (property.getType().isAssignableFrom(Map.class)) {
                            final Class<?> ketType = arguments[0];
                            final Class<?> valueType = arguments[1];
                            final MappingNode mnode2 = (MappingNode)valueNode;
                            mnode2.setTypes(ketType, valueType);
                            mnode2.setUseClassConstructor(true);
                        }
                    }
                }
                Object value = this.this$0.constructObject(valueNode);
                if ((property.getType() == Float.TYPE || property.getType() == Float.class) && value instanceof Double) {
                    value = ((Double)value).floatValue();
                }
                if (property.getType() == String.class && Tag.BINARY.equals(valueNode.getTag()) && value instanceof byte[]) {
                    value = new String((byte[])value);
                }
                property.set(object, value);
            }
            catch (Exception e) {
                throw new ConstructorException("Cannot create property=" + key + " for JavaBean=" + object, node.getStartMark(), e.getMessage(), valueNode.getStartMark(), e);
            }
        }
        return object;
    }
    
    protected Property getProperty(final Class<?> type, final String name) {
        return this.this$0.getPropertyUtils().getProperty(type, name);
    }
}
