package com.trooly.wp.tokenize;

import java.util.List;
import java.util.Set;

/**
 * Interface defined so that a more sophisticated tokenizer can be injected later if desired.
 */
public interface ITokenizer {
  List<Token> tokenize(String text, Set<String> tokensToMatch, Set<String> tokensMatched);
}
