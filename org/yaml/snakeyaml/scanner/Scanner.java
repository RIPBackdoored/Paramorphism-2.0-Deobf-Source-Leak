package org.yaml.snakeyaml.scanner;

import org.yaml.snakeyaml.tokens.Token;
import org.yaml.snakeyaml.tokens.Token$ID;

public interface Scanner {
   boolean checkToken(Token$ID... var1);

   Token peekToken();

   Token getToken();
}
