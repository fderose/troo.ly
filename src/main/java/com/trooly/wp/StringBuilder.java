package com.trooly.wp;

import com.trooly.wp.tokenize.Token;
import com.trooly.wp.tokenize.TokenPair;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StringBuilder {

  private StringBuilder() {
  }

  public static String buildString(TokenPair tokenPair, String text, Set<String> words) {
    Token token1 = tokenPair.token1;
    Token token2 = tokenPair.token2;
    java.lang.StringBuilder sb = new java.lang.StringBuilder();
    sb.append("Text to search: " + text + "\n");
    sb.append("Words to match: " + String.join(", ", words) + "\n");
    sb.append(String.format("Position of first word %s is %d\n", token1.token, token1.start));
    sb.append(String.format("Position of last word %s is %d\n", token2.token, token2.start));
    sb.append(String.format("Distance in characters between them is %d\n", token2.start - token1.end));
    sb.append(String.format("The passage is \"%s\"\n", text.substring(token1.start, token2.end)));
    return sb.toString();
  }

  public static String buildString(Passage passage, String text, Set<String> words) {
    List<Token> tokens = passage.getTokens();
    StringBuffer sb = new StringBuffer();
    sb.append("Text to search: " + text + "\n");
    sb.append("Words to match: " + String.join(", ", words) + "\n");
    List<String> strings = new ArrayList<>();
    for (Token token : tokens) {
      strings.add(String.format("Position of word %s is %d", token.token, token.start));
    }
    sb.append(String.join("\n", strings));
    sb.append("\n");
    Token startToken = tokens.get(0);
    Token endToken = tokens.get(tokens.size() - 1);
    sb.append(String.format("Length of passage in characters is %d\n", endToken.end - startToken.start));
    sb.append(String.format("The passage is \"%s\"\n", text.substring(startToken.start, endToken.end)));
    return sb.toString();
  }

  public static String buildString(Passage passage, String text) {
    List<Token> tokens = passage.getTokens();
    StringBuffer sb = new StringBuffer();
    Token startToken = tokens.get(0);
    Token endToken = tokens.get(tokens.size() - 1);
    sb.append(String.format("Length of passage in characters is %d\n", endToken.end - startToken.start));
    sb.append(String.format("|%s|\n", text.substring(startToken.start, endToken.end)));
    return sb.toString();
  }

  public static String buildString(Passage expected, Passage actual) {
    return "Expected was:\n" + expected + "\n\n" + "Actual was: \n" + actual;
  }

  public static String buildString(TokenPair expected, TokenPair actual) {
    return "Expected was:\n" + expected + "\n\n" + "Actual was: \n" + actual;
  }
}
