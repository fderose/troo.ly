package com.trooly.wp.tokenize;

import java.util.Objects;

public class TokenPair implements Comparable<TokenPair> {
  public static final TokenPair TOKEN_PAIR_WITH_MAX_DIST_BETWEEN_TOKENS = new TokenPair(new Token("a", 0), new Token("b", Integer.MAX_VALUE - 1));

  public final Token token1;
  public final Token token2;

  public TokenPair(Token token1, Token token2) {
    this.token1 = token1;
    this.token2 = token2;
  }

  public boolean areTokensEqual() {
    return token1.token.equals(token2.token);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || !(obj instanceof TokenPair)) {
      return false;
    }
    /**
     * Can't use Token.equals(), since it is defined only on Token.token because of a different
     * interpretation (non-position-sensitive) of "equality" between tokens.
     */
    TokenPair tokenPair = (TokenPair) obj;
    return token1.token.equals(tokenPair.token1.token) && token2.token.equals(tokenPair.token2.token)
      && token1.start == tokenPair.token1.start && token2.start == tokenPair.token2.start;
  }

  @Override
  public int hashCode() {
    return Objects.hash(token1, token2);
  }

  @Override
  public int compareTo(TokenPair tokenPair) {
    if (tokenPair == null) {
      throw new IllegalArgumentException("tokenPair cannot be null");
    }
    return (token2.start - token1.end) - (tokenPair.token2.start - tokenPair.token1.end);
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("\n");
    sb.append(String.format("\nPosition of first word %s is %d\n", token1.token, token1.start));
    sb.append(String.format("Position of second word %s is %d\n", token2.token, token2.start));
    sb.append(String.format("Distance between words is %d\n", token2.start - token1.end));
    return sb.toString();
  }
}
