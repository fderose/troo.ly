package com.trooly.wp;

import com.trooly.wp.tokenize.Token;

import java.util.*;

public class Passage implements Comparable<Passage> {

  private final List<Token> tokens;

  public Passage(Set<Token> tokenSet) {
    tokens = new ArrayList<>();
    tokens.addAll(tokenSet);
    Collections.sort(tokens);
  }

  public Passage(List<Token> tokens) {
    this.tokens = tokens;
    Collections.sort(this.tokens);
  }

  public List<Token> getTokens() {
    return tokens;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || !(obj instanceof Passage)) {
      return false;
    }
    return tokens.equals(((Passage)obj).tokens);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tokens);
  }

  @Override
  public int compareTo(Passage passage) {
    return (tokens.get(tokens.size() - 1).start - tokens.get(0).end) - (passage.tokens.get(passage.tokens.size() - 1).start - passage.tokens.get(0).end);
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("\n");
    List<String> strings = new ArrayList<>();
    for (Token token : tokens) {
      strings.add(String.format("Position of word %s is %d", token.token, token.start));
    }
    sb.append(String.join("\n", strings));
    Token startToken = tokens.get(0);
    Token endToken = tokens.get(tokens.size() - 1);
    sb.append(String.format("\nPosition of first word is %d\n", startToken.start));
    sb.append(String.format("Position of last word is %d\n", endToken.start));
    sb.append(String.format("Length of passage in characters is %d\n", endToken.end - startToken.start));
    return sb.toString();
  }
}
