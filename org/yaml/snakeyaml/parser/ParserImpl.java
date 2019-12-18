package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.util.*;
import org.yaml.snakeyaml.reader.*;
import org.yaml.snakeyaml.scanner.*;
import org.yaml.snakeyaml.*;
import java.util.*;
import org.yaml.snakeyaml.error.*;
import org.yaml.snakeyaml.tokens.*;
import org.yaml.snakeyaml.events.*;

public class ParserImpl implements Parser
{
    private static final Map<String, String> DEFAULT_TAGS;
    protected final Scanner scanner;
    private Event currentEvent;
    private final ArrayStack<Production> states;
    private final ArrayStack<Mark> marks;
    private Production state;
    private VersionTagsTuple directives;
    
    public ParserImpl(final StreamReader reader) {
        this(new ScannerImpl(reader));
    }
    
    public ParserImpl(final Scanner scanner) {
        super();
        this.scanner = scanner;
        this.currentEvent = null;
        this.directives = new VersionTagsTuple(null, new HashMap<String, String>(ParserImpl.DEFAULT_TAGS));
        this.states = new ArrayStack<Production>(100);
        this.marks = new ArrayStack<Mark>(10);
        this.state = new ParseStreamStart(this);
    }
    
    @Override
    public boolean checkEvent(final Event.ID choice) {
        this.peekEvent();
        return this.currentEvent != null && this.currentEvent.is(choice);
    }
    
    @Override
    public Event peekEvent() {
        if (this.currentEvent == null && this.state != null) {
            this.currentEvent = this.state.produce();
        }
        return this.currentEvent;
    }
    
    @Override
    public Event getEvent() {
        this.peekEvent();
        final Event value = this.currentEvent;
        this.currentEvent = null;
        return value;
    }
    
    private VersionTagsTuple processDirectives() {
        DumperOptions.Version yamlVersion = null;
        final HashMap<String, String> tagHandles = new HashMap<String, String>();
        while (this.scanner.checkToken(Token.ID.Directive)) {
            final DirectiveToken token = (DirectiveToken)this.scanner.getToken();
            if (token.getName().equals("YAML")) {
                if (yamlVersion != null) {
                    throw new ParserException(null, null, "found duplicate YAML directive", token.getStartMark());
                }
                final List<Integer> value = token.getValue();
                final Integer major = value.get(0);
                if (major != 1) {
                    throw new ParserException(null, null, "found incompatible YAML document (version 1.* is required)", token.getStartMark());
                }
                final Integer minor = value.get(1);
                switch (minor) {
                    case 0: {
                        yamlVersion = DumperOptions.Version.V1_0;
                        continue;
                    }
                    default: {
                        yamlVersion = DumperOptions.Version.V1_1;
                        continue;
                    }
                }
            }
            else {
                if (!token.getName().equals("TAG")) {
                    continue;
                }
                final List<String> value2 = token.getValue();
                final String handle = value2.get(0);
                final String prefix = value2.get(1);
                if (tagHandles.containsKey(handle)) {
                    throw new ParserException(null, null, "duplicate tag handle " + handle, token.getStartMark());
                }
                tagHandles.put(handle, prefix);
            }
        }
        if (yamlVersion != null || !tagHandles.isEmpty()) {
            for (final String key : ParserImpl.DEFAULT_TAGS.keySet()) {
                if (!tagHandles.containsKey(key)) {
                    tagHandles.put(key, ParserImpl.DEFAULT_TAGS.get(key));
                }
            }
            this.directives = new VersionTagsTuple(yamlVersion, tagHandles);
        }
        return this.directives;
    }
    
    private Event parseFlowNode() {
        return this.parseNode(false, false);
    }
    
    private Event parseBlockNodeOrIndentlessSequence() {
        return this.parseNode(true, true);
    }
    
    private Event parseNode(final boolean block, final boolean indentlessSequence) {
        Mark startMark = null;
        Mark endMark = null;
        Mark tagMark = null;
        Event event;
        if (this.scanner.checkToken(Token.ID.Alias)) {
            final AliasToken token = (AliasToken)this.scanner.getToken();
            event = new AliasEvent(token.getValue(), token.getStartMark(), token.getEndMark());
            this.state = this.states.pop();
        }
        else {
            String anchor = null;
            TagTuple tagTokenTag = null;
            if (this.scanner.checkToken(Token.ID.Anchor)) {
                final AnchorToken token2 = (AnchorToken)this.scanner.getToken();
                startMark = token2.getStartMark();
                endMark = token2.getEndMark();
                anchor = token2.getValue();
                if (this.scanner.checkToken(Token.ID.Tag)) {
                    final TagToken tagToken = (TagToken)this.scanner.getToken();
                    tagMark = tagToken.getStartMark();
                    endMark = tagToken.getEndMark();
                    tagTokenTag = tagToken.getValue();
                }
            }
            else if (this.scanner.checkToken(Token.ID.Tag)) {
                final TagToken tagToken2 = (TagToken)this.scanner.getToken();
                startMark = (tagMark = tagToken2.getStartMark());
                endMark = tagToken2.getEndMark();
                tagTokenTag = tagToken2.getValue();
                if (this.scanner.checkToken(Token.ID.Anchor)) {
                    final AnchorToken token3 = (AnchorToken)this.scanner.getToken();
                    endMark = token3.getEndMark();
                    anchor = token3.getValue();
                }
            }
            String tag = null;
            if (tagTokenTag != null) {
                final String handle = tagTokenTag.getHandle();
                final String suffix = tagTokenTag.getSuffix();
                if (handle != null) {
                    if (!this.directives.getTags().containsKey(handle)) {
                        throw new ParserException("while parsing a node", startMark, "found undefined tag handle " + handle, tagMark);
                    }
                    tag = this.directives.getTags().get(handle) + suffix;
                }
                else {
                    tag = suffix;
                }
            }
            if (startMark == null) {
                startMark = (endMark = this.scanner.peekToken().getStartMark());
            }
            event = null;
            final boolean implicit = tag == null || tag.equals("!");
            if (indentlessSequence && this.scanner.checkToken(Token.ID.BlockEntry)) {
                endMark = this.scanner.peekToken().getEndMark();
                event = new SequenceStartEvent(anchor, tag, implicit, startMark, endMark, Boolean.FALSE);
                this.state = new ParseIndentlessSequenceEntry(this);
            }
            else if (this.scanner.checkToken(Token.ID.Scalar)) {
                final ScalarToken token4 = (ScalarToken)this.scanner.getToken();
                endMark = token4.getEndMark();
                ImplicitTuple implicitValues;
                if ((token4.getPlain() && tag == null) || "!".equals(tag)) {
                    implicitValues = new ImplicitTuple(true, false);
                }
                else if (tag == null) {
                    implicitValues = new ImplicitTuple(false, true);
                }
                else {
                    implicitValues = new ImplicitTuple(false, false);
                }
                event = new ScalarEvent(anchor, tag, implicitValues, token4.getValue(), startMark, endMark, token4.getStyle());
                this.state = this.states.pop();
            }
            else if (this.scanner.checkToken(Token.ID.FlowSequenceStart)) {
                endMark = this.scanner.peekToken().getEndMark();
                event = new SequenceStartEvent(anchor, tag, implicit, startMark, endMark, Boolean.TRUE);
                this.state = new ParseFlowSequenceFirstEntry(this);
            }
            else if (this.scanner.checkToken(Token.ID.FlowMappingStart)) {
                endMark = this.scanner.peekToken().getEndMark();
                event = new MappingStartEvent(anchor, tag, implicit, startMark, endMark, Boolean.TRUE);
                this.state = new ParseFlowMappingFirstKey(this);
            }
            else if (block && this.scanner.checkToken(Token.ID.BlockSequenceStart)) {
                endMark = this.scanner.peekToken().getStartMark();
                event = new SequenceStartEvent(anchor, tag, implicit, startMark, endMark, Boolean.FALSE);
                this.state = new ParseBlockSequenceFirstEntry(this);
            }
            else if (block && this.scanner.checkToken(Token.ID.BlockMappingStart)) {
                endMark = this.scanner.peekToken().getStartMark();
                event = new MappingStartEvent(anchor, tag, implicit, startMark, endMark, Boolean.FALSE);
                this.state = new ParseBlockMappingFirstKey(this);
            }
            else {
                if (anchor == null && tag == null) {
                    String node;
                    if (block) {
                        node = "block";
                    }
                    else {
                        node = "flow";
                    }
                    final Token token5 = this.scanner.peekToken();
                    throw new ParserException("while parsing a " + node + " node", startMark, "expected the node content, but found " + token5.getTokenId(), token5.getStartMark());
                }
                event = new ScalarEvent(anchor, tag, new ImplicitTuple(implicit, false), "", startMark, endMark, '\0');
                this.state = this.states.pop();
            }
        }
        return event;
    }
    
    private Event processEmptyScalar(final Mark mark) {
        return new ScalarEvent(null, null, new ImplicitTuple(true, false), "", mark, mark, '\0');
    }
    
    static /* synthetic */ Production access$102(final ParserImpl x0, final Production x1) {
        return x0.state = x1;
    }
    
    static /* synthetic */ VersionTagsTuple access$302(final ParserImpl x0, final VersionTagsTuple x1) {
        return x0.directives = x1;
    }
    
    static /* synthetic */ Map access$400() {
        return ParserImpl.DEFAULT_TAGS;
    }
    
    static /* synthetic */ ArrayStack access$600(final ParserImpl x0) {
        return x0.states;
    }
    
    static /* synthetic */ VersionTagsTuple access$900(final ParserImpl x0) {
        return x0.processDirectives();
    }
    
    static /* synthetic */ ArrayStack access$1100(final ParserImpl x0) {
        return x0.marks;
    }
    
    static /* synthetic */ Event access$1200(final ParserImpl x0, final Mark x1) {
        return x0.processEmptyScalar(x1);
    }
    
    static /* synthetic */ Event access$1300(final ParserImpl x0, final boolean x1, final boolean x2) {
        return x0.parseNode(x1, x2);
    }
    
    static /* synthetic */ Event access$2200(final ParserImpl x0) {
        return x0.parseBlockNodeOrIndentlessSequence();
    }
    
    static /* synthetic */ Event access$2400(final ParserImpl x0) {
        return x0.parseFlowNode();
    }
    
    static {
        (DEFAULT_TAGS = new HashMap<String, String>()).put("!", "!");
        ParserImpl.DEFAULT_TAGS.put("!!", "tag:yaml.org,2002:");
    }
    
    private class ParseStreamStart implements Production
    {
        final /* synthetic */ ParserImpl this$0;
        
        private ParseStreamStart(final ParserImpl this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Event produce() {
            final StreamStartToken token = (StreamStartToken)this.this$0.scanner.getToken();
            final Event event = new StreamStartEvent(token.getStartMark(), token.getEndMark());
            this.this$0.state = this.this$0.new ParseImplicitDocumentStart();
            return event;
        }
        
        ParseStreamStart(final ParserImpl x0, final ParserImpl$1 x1) {
            this(x0);
        }
    }
    
    private class ParseImplicitDocumentStart implements Production
    {
        final /* synthetic */ ParserImpl this$0;
        
        private ParseImplicitDocumentStart(final ParserImpl this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Event produce() {
            if (!this.this$0.scanner.checkToken(Token.ID.Directive, Token.ID.DocumentStart, Token.ID.StreamEnd)) {
                this.this$0.directives = new VersionTagsTuple(null, ParserImpl.DEFAULT_TAGS);
                final Token token = this.this$0.scanner.peekToken();
                final Mark endMark;
                final Mark startMark = endMark = token.getStartMark();
                final Event event = new DocumentStartEvent(startMark, endMark, false, null, null);
                this.this$0.states.push(this.this$0.new ParseDocumentEnd());
                this.this$0.state = this.this$0.new ParseBlockNode();
                return event;
            }
            final Production p = this.this$0.new ParseDocumentStart();
            return p.produce();
        }
        
        ParseImplicitDocumentStart(final ParserImpl x0, final ParserImpl$1 x1) {
            this(x0);
        }
    }
    
    private class ParseDocumentStart implements Production
    {
        final /* synthetic */ ParserImpl this$0;
        
        private ParseDocumentStart(final ParserImpl this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Event produce() {
            while (this.this$0.scanner.checkToken(Token.ID.DocumentEnd)) {
                this.this$0.scanner.getToken();
            }
            Event event;
            if (!this.this$0.scanner.checkToken(Token.ID.StreamEnd)) {
                Token token = this.this$0.scanner.peekToken();
                final Mark startMark = token.getStartMark();
                final VersionTagsTuple tuple = ParserImpl.this.processDirectives();
                if (!this.this$0.scanner.checkToken(Token.ID.DocumentStart)) {
                    throw new ParserException(null, null, "expected '<document start>', but found " + this.this$0.scanner.peekToken().getTokenId(), this.this$0.scanner.peekToken().getStartMark());
                }
                token = this.this$0.scanner.getToken();
                final Mark endMark = token.getEndMark();
                event = new DocumentStartEvent(startMark, endMark, true, tuple.getVersion(), tuple.getTags());
                this.this$0.states.push(this.this$0.new ParseDocumentEnd());
                this.this$0.state = this.this$0.new ParseDocumentContent();
            }
            else {
                final StreamEndToken token2 = (StreamEndToken)this.this$0.scanner.getToken();
                event = new StreamEndEvent(token2.getStartMark(), token2.getEndMark());
                if (!this.this$0.states.isEmpty()) {
                    throw new YAMLException("Unexpected end of stream. States left: " + this.this$0.states);
                }
                if (!this.this$0.marks.isEmpty()) {
                    throw new YAMLException("Unexpected end of stream. Marks left: " + this.this$0.marks);
                }
                this.this$0.state = null;
            }
            return event;
        }
        
        ParseDocumentStart(final ParserImpl x0, final ParserImpl$1 x1) {
            this(x0);
        }
    }
    
    private class ParseDocumentEnd implements Production
    {
        final /* synthetic */ ParserImpl this$0;
        
        private ParseDocumentEnd(final ParserImpl this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Event produce() {
            Token token = this.this$0.scanner.peekToken();
            Mark endMark;
            final Mark startMark = endMark = token.getStartMark();
            boolean explicit = false;
            if (this.this$0.scanner.checkToken(Token.ID.DocumentEnd)) {
                token = this.this$0.scanner.getToken();
                endMark = token.getEndMark();
                explicit = true;
            }
            final Event event = new DocumentEndEvent(startMark, endMark, explicit);
            this.this$0.state = this.this$0.new ParseDocumentStart();
            return event;
        }
        
        ParseDocumentEnd(final ParserImpl x0, final ParserImpl$1 x1) {
            this(x0);
        }
    }
    
    private class ParseDocumentContent implements Production
    {
        final /* synthetic */ ParserImpl this$0;
        
        private ParseDocumentContent(final ParserImpl this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Event produce() {
            if (this.this$0.scanner.checkToken(Token.ID.Directive, Token.ID.DocumentStart, Token.ID.DocumentEnd, Token.ID.StreamEnd)) {
                final Event event = ParserImpl.this.processEmptyScalar(this.this$0.scanner.peekToken().getStartMark());
                this.this$0.state = this.this$0.states.pop();
                return event;
            }
            final Production p = this.this$0.new ParseBlockNode();
            return p.produce();
        }
        
        ParseDocumentContent(final ParserImpl x0, final ParserImpl$1 x1) {
            this(x0);
        }
    }
    
    private class ParseBlockNode implements Production
    {
        final /* synthetic */ ParserImpl this$0;
        
        private ParseBlockNode(final ParserImpl this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Event produce() {
            return ParserImpl.this.parseNode(true, false);
        }
        
        ParseBlockNode(final ParserImpl x0, final ParserImpl$1 x1) {
            this(x0);
        }
    }
    
    private class ParseBlockSequenceFirstEntry implements Production
    {
        final /* synthetic */ ParserImpl this$0;
        
        private ParseBlockSequenceFirstEntry(final ParserImpl this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Event produce() {
            final Token token = this.this$0.scanner.getToken();
            this.this$0.marks.push(token.getStartMark());
            return this.this$0.new ParseBlockSequenceEntry().produce();
        }
        
        ParseBlockSequenceFirstEntry(final ParserImpl x0, final ParserImpl$1 x1) {
            this(x0);
        }
    }
    
    private class ParseBlockSequenceEntry implements Production
    {
        final /* synthetic */ ParserImpl this$0;
        
        private ParseBlockSequenceEntry(final ParserImpl this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Event produce() {
            if (this.this$0.scanner.checkToken(Token.ID.BlockEntry)) {
                final BlockEntryToken token = (BlockEntryToken)this.this$0.scanner.getToken();
                if (!this.this$0.scanner.checkToken(Token.ID.BlockEntry, Token.ID.BlockEnd)) {
                    this.this$0.states.push(this.this$0.new ParseBlockSequenceEntry());
                    return this.this$0.new ParseBlockNode().produce();
                }
                this.this$0.state = this.this$0.new ParseBlockSequenceEntry();
                return ParserImpl.this.processEmptyScalar(token.getEndMark());
            }
            else {
                if (!this.this$0.scanner.checkToken(Token.ID.BlockEnd)) {
                    final Token token2 = this.this$0.scanner.peekToken();
                    throw new ParserException("while parsing a block collection", this.this$0.marks.pop(), "expected <block end>, but found " + token2.getTokenId(), token2.getStartMark());
                }
                final Token token2 = this.this$0.scanner.getToken();
                final Event event = new SequenceEndEvent(token2.getStartMark(), token2.getEndMark());
                this.this$0.state = this.this$0.states.pop();
                this.this$0.marks.pop();
                return event;
            }
        }
        
        ParseBlockSequenceEntry(final ParserImpl x0, final ParserImpl$1 x1) {
            this(x0);
        }
    }
    
    private class ParseIndentlessSequenceEntry implements Production
    {
        final /* synthetic */ ParserImpl this$0;
        
        private ParseIndentlessSequenceEntry(final ParserImpl this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Event produce() {
            if (!this.this$0.scanner.checkToken(Token.ID.BlockEntry)) {
                final Token token = this.this$0.scanner.peekToken();
                final Event event = new SequenceEndEvent(token.getStartMark(), token.getEndMark());
                this.this$0.state = this.this$0.states.pop();
                return event;
            }
            final Token token = this.this$0.scanner.getToken();
            if (!this.this$0.scanner.checkToken(Token.ID.BlockEntry, Token.ID.Key, Token.ID.Value, Token.ID.BlockEnd)) {
                this.this$0.states.push(this.this$0.new ParseIndentlessSequenceEntry());
                return this.this$0.new ParseBlockNode().produce();
            }
            this.this$0.state = this.this$0.new ParseIndentlessSequenceEntry();
            return ParserImpl.this.processEmptyScalar(token.getEndMark());
        }
        
        ParseIndentlessSequenceEntry(final ParserImpl x0, final ParserImpl$1 x1) {
            this(x0);
        }
    }
    
    private class ParseBlockMappingFirstKey implements Production
    {
        final /* synthetic */ ParserImpl this$0;
        
        private ParseBlockMappingFirstKey(final ParserImpl this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Event produce() {
            final Token token = this.this$0.scanner.getToken();
            this.this$0.marks.push(token.getStartMark());
            return this.this$0.new ParseBlockMappingKey().produce();
        }
        
        ParseBlockMappingFirstKey(final ParserImpl x0, final ParserImpl$1 x1) {
            this(x0);
        }
    }
    
    private class ParseBlockMappingKey implements Production
    {
        final /* synthetic */ ParserImpl this$0;
        
        private ParseBlockMappingKey(final ParserImpl this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Event produce() {
            if (this.this$0.scanner.checkToken(Token.ID.Key)) {
                final Token token = this.this$0.scanner.getToken();
                if (!this.this$0.scanner.checkToken(Token.ID.Key, Token.ID.Value, Token.ID.BlockEnd)) {
                    this.this$0.states.push(this.this$0.new ParseBlockMappingValue());
                    return ParserImpl.this.parseBlockNodeOrIndentlessSequence();
                }
                this.this$0.state = this.this$0.new ParseBlockMappingValue();
                return ParserImpl.this.processEmptyScalar(token.getEndMark());
            }
            else {
                if (!this.this$0.scanner.checkToken(Token.ID.BlockEnd)) {
                    final Token token = this.this$0.scanner.peekToken();
                    throw new ParserException("while parsing a block mapping", this.this$0.marks.pop(), "expected <block end>, but found " + token.getTokenId(), token.getStartMark());
                }
                final Token token = this.this$0.scanner.getToken();
                final Event event = new MappingEndEvent(token.getStartMark(), token.getEndMark());
                this.this$0.state = this.this$0.states.pop();
                this.this$0.marks.pop();
                return event;
            }
        }
        
        ParseBlockMappingKey(final ParserImpl x0, final ParserImpl$1 x1) {
            this(x0);
        }
    }
    
    private class ParseBlockMappingValue implements Production
    {
        final /* synthetic */ ParserImpl this$0;
        
        private ParseBlockMappingValue(final ParserImpl this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Event produce() {
            if (!this.this$0.scanner.checkToken(Token.ID.Value)) {
                this.this$0.state = this.this$0.new ParseBlockMappingKey();
                final Token token = this.this$0.scanner.peekToken();
                return ParserImpl.this.processEmptyScalar(token.getStartMark());
            }
            final Token token = this.this$0.scanner.getToken();
            if (!this.this$0.scanner.checkToken(Token.ID.Key, Token.ID.Value, Token.ID.BlockEnd)) {
                this.this$0.states.push(this.this$0.new ParseBlockMappingKey());
                return ParserImpl.this.parseBlockNodeOrIndentlessSequence();
            }
            this.this$0.state = this.this$0.new ParseBlockMappingKey();
            return ParserImpl.this.processEmptyScalar(token.getEndMark());
        }
        
        ParseBlockMappingValue(final ParserImpl x0, final ParserImpl$1 x1) {
            this(x0);
        }
    }
    
    private class ParseFlowSequenceFirstEntry implements Production
    {
        final /* synthetic */ ParserImpl this$0;
        
        private ParseFlowSequenceFirstEntry(final ParserImpl this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Event produce() {
            final Token token = this.this$0.scanner.getToken();
            this.this$0.marks.push(token.getStartMark());
            return this.this$0.new ParseFlowSequenceEntry(true).produce();
        }
        
        ParseFlowSequenceFirstEntry(final ParserImpl x0, final ParserImpl$1 x1) {
            this(x0);
        }
    }
    
    private class ParseFlowSequenceEntry implements Production
    {
        private boolean first;
        final /* synthetic */ ParserImpl this$0;
        
        public ParseFlowSequenceEntry(final ParserImpl this$0, final boolean first) {
            this.this$0 = this$0;
            super();
            this.first = false;
            this.first = first;
        }
        
        @Override
        public Event produce() {
            if (!this.this$0.scanner.checkToken(Token.ID.FlowSequenceEnd)) {
                if (!this.first) {
                    if (!this.this$0.scanner.checkToken(Token.ID.FlowEntry)) {
                        final Token token = this.this$0.scanner.peekToken();
                        throw new ParserException("while parsing a flow sequence", this.this$0.marks.pop(), "expected ',' or ']', but got " + token.getTokenId(), token.getStartMark());
                    }
                    this.this$0.scanner.getToken();
                }
                if (this.this$0.scanner.checkToken(Token.ID.Key)) {
                    final Token token = this.this$0.scanner.peekToken();
                    final Event event = new MappingStartEvent(null, null, true, token.getStartMark(), token.getEndMark(), Boolean.TRUE);
                    this.this$0.state = this.this$0.new ParseFlowSequenceEntryMappingKey();
                    return event;
                }
                if (!this.this$0.scanner.checkToken(Token.ID.FlowSequenceEnd)) {
                    this.this$0.states.push(this.this$0.new ParseFlowSequenceEntry(false));
                    return ParserImpl.this.parseFlowNode();
                }
            }
            final Token token = this.this$0.scanner.getToken();
            final Event event = new SequenceEndEvent(token.getStartMark(), token.getEndMark());
            this.this$0.state = this.this$0.states.pop();
            this.this$0.marks.pop();
            return event;
        }
    }
    
    private class ParseFlowSequenceEntryMappingKey implements Production
    {
        final /* synthetic */ ParserImpl this$0;
        
        private ParseFlowSequenceEntryMappingKey(final ParserImpl this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Event produce() {
            final Token token = this.this$0.scanner.getToken();
            if (!this.this$0.scanner.checkToken(Token.ID.Value, Token.ID.FlowEntry, Token.ID.FlowSequenceEnd)) {
                this.this$0.states.push(this.this$0.new ParseFlowSequenceEntryMappingValue());
                return ParserImpl.this.parseFlowNode();
            }
            this.this$0.state = this.this$0.new ParseFlowSequenceEntryMappingValue();
            return ParserImpl.this.processEmptyScalar(token.getEndMark());
        }
        
        ParseFlowSequenceEntryMappingKey(final ParserImpl x0, final ParserImpl$1 x1) {
            this(x0);
        }
    }
    
    private class ParseFlowSequenceEntryMappingValue implements Production
    {
        final /* synthetic */ ParserImpl this$0;
        
        private ParseFlowSequenceEntryMappingValue(final ParserImpl this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Event produce() {
            if (!this.this$0.scanner.checkToken(Token.ID.Value)) {
                this.this$0.state = this.this$0.new ParseFlowSequenceEntryMappingEnd();
                final Token token = this.this$0.scanner.peekToken();
                return ParserImpl.this.processEmptyScalar(token.getStartMark());
            }
            final Token token = this.this$0.scanner.getToken();
            if (!this.this$0.scanner.checkToken(Token.ID.FlowEntry, Token.ID.FlowSequenceEnd)) {
                this.this$0.states.push(this.this$0.new ParseFlowSequenceEntryMappingEnd());
                return ParserImpl.this.parseFlowNode();
            }
            this.this$0.state = this.this$0.new ParseFlowSequenceEntryMappingEnd();
            return ParserImpl.this.processEmptyScalar(token.getEndMark());
        }
        
        ParseFlowSequenceEntryMappingValue(final ParserImpl x0, final ParserImpl$1 x1) {
            this(x0);
        }
    }
    
    private class ParseFlowSequenceEntryMappingEnd implements Production
    {
        final /* synthetic */ ParserImpl this$0;
        
        private ParseFlowSequenceEntryMappingEnd(final ParserImpl this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Event produce() {
            this.this$0.state = this.this$0.new ParseFlowSequenceEntry(false);
            final Token token = this.this$0.scanner.peekToken();
            return new MappingEndEvent(token.getStartMark(), token.getEndMark());
        }
        
        ParseFlowSequenceEntryMappingEnd(final ParserImpl x0, final ParserImpl$1 x1) {
            this(x0);
        }
    }
    
    private class ParseFlowMappingFirstKey implements Production
    {
        final /* synthetic */ ParserImpl this$0;
        
        private ParseFlowMappingFirstKey(final ParserImpl this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Event produce() {
            final Token token = this.this$0.scanner.getToken();
            this.this$0.marks.push(token.getStartMark());
            return this.this$0.new ParseFlowMappingKey(true).produce();
        }
        
        ParseFlowMappingFirstKey(final ParserImpl x0, final ParserImpl$1 x1) {
            this(x0);
        }
    }
    
    private class ParseFlowMappingKey implements Production
    {
        private boolean first;
        final /* synthetic */ ParserImpl this$0;
        
        public ParseFlowMappingKey(final ParserImpl this$0, final boolean first) {
            this.this$0 = this$0;
            super();
            this.first = false;
            this.first = first;
        }
        
        @Override
        public Event produce() {
            if (!this.this$0.scanner.checkToken(Token.ID.FlowMappingEnd)) {
                if (!this.first) {
                    if (!this.this$0.scanner.checkToken(Token.ID.FlowEntry)) {
                        final Token token = this.this$0.scanner.peekToken();
                        throw new ParserException("while parsing a flow mapping", this.this$0.marks.pop(), "expected ',' or '}', but got " + token.getTokenId(), token.getStartMark());
                    }
                    this.this$0.scanner.getToken();
                }
                if (this.this$0.scanner.checkToken(Token.ID.Key)) {
                    final Token token = this.this$0.scanner.getToken();
                    if (!this.this$0.scanner.checkToken(Token.ID.Value, Token.ID.FlowEntry, Token.ID.FlowMappingEnd)) {
                        this.this$0.states.push(this.this$0.new ParseFlowMappingValue());
                        return ParserImpl.this.parseFlowNode();
                    }
                    this.this$0.state = this.this$0.new ParseFlowMappingValue();
                    return ParserImpl.this.processEmptyScalar(token.getEndMark());
                }
                else if (!this.this$0.scanner.checkToken(Token.ID.FlowMappingEnd)) {
                    this.this$0.states.push(this.this$0.new ParseFlowMappingEmptyValue());
                    return ParserImpl.this.parseFlowNode();
                }
            }
            final Token token = this.this$0.scanner.getToken();
            final Event event = new MappingEndEvent(token.getStartMark(), token.getEndMark());
            this.this$0.state = this.this$0.states.pop();
            this.this$0.marks.pop();
            return event;
        }
    }
    
    private class ParseFlowMappingValue implements Production
    {
        final /* synthetic */ ParserImpl this$0;
        
        private ParseFlowMappingValue(final ParserImpl this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Event produce() {
            if (!this.this$0.scanner.checkToken(Token.ID.Value)) {
                this.this$0.state = this.this$0.new ParseFlowMappingKey(false);
                final Token token = this.this$0.scanner.peekToken();
                return ParserImpl.this.processEmptyScalar(token.getStartMark());
            }
            final Token token = this.this$0.scanner.getToken();
            if (!this.this$0.scanner.checkToken(Token.ID.FlowEntry, Token.ID.FlowMappingEnd)) {
                this.this$0.states.push(this.this$0.new ParseFlowMappingKey(false));
                return ParserImpl.this.parseFlowNode();
            }
            this.this$0.state = this.this$0.new ParseFlowMappingKey(false);
            return ParserImpl.this.processEmptyScalar(token.getEndMark());
        }
        
        ParseFlowMappingValue(final ParserImpl x0, final ParserImpl$1 x1) {
            this(x0);
        }
    }
    
    private class ParseFlowMappingEmptyValue implements Production
    {
        final /* synthetic */ ParserImpl this$0;
        
        private ParseFlowMappingEmptyValue(final ParserImpl this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public Event produce() {
            this.this$0.state = this.this$0.new ParseFlowMappingKey(false);
            return ParserImpl.this.processEmptyScalar(this.this$0.scanner.peekToken().getStartMark());
        }
        
        ParseFlowMappingEmptyValue(final ParserImpl x0, final ParserImpl$1 x1) {
            this(x0);
        }
    }
}
