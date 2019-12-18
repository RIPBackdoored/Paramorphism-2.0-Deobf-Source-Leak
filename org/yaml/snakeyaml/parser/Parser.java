package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.events.Event$ID;

public interface Parser {
   boolean checkEvent(Event$ID var1);

   Event peekEvent();

   Event getEvent();
}
