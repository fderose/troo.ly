package com.trooly.wp.tokenize;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Implementation of a very basic tokenizer that splits tokens on non-alphabetic characters.
 */
public class DumbTokenizer implements ITokenizer {
  
  private void addToken(String text, int tokenStart, int tokenEnd, Set<String> tokensToMatch, List<Token> tokens, Set<String> tokensMatched) {
    String s = text.substring(tokenStart, tokenEnd).toLowerCase();
    if (tokensToMatch.contains(s)) {
      Token token = new Token(s, tokenStart);
      tokens.add(token);
      tokensMatched.add(token.token);
    }
  }

  @Override
  public List<Token> tokenize(String text, Set<String> tokensToMatch, Set<String> tokensMatched) {
    List<Token> tokens = new ArrayList<>();
    int tokenStart = -1;
    int pos = 0;
    for (; pos < text.length(); pos++) {
      if (Character.isAlphabetic(text.charAt(pos))) { // not delimiter == valid token
        if (tokenStart < 0) { //token just started
          tokenStart = pos;
        }
      } else {
        if (tokenStart >= 0) { //token just ended
          addToken(text, tokenStart, pos, tokensToMatch, tokens, tokensMatched);
        }
        tokenStart = -1;
      }
    }
    if (tokenStart >= 0) {
      addToken(text, tokenStart, pos, tokensToMatch, tokens, tokensMatched);
    }
    return tokens;
  }
}
