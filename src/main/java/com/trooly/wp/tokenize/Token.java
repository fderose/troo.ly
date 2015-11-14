package com.trooly.wp.tokenize;

import java.util.Objects;

public class Token implements Comparable<Token> {

  /**
   * Exposing the instance variables directly for simpplicity, although not a best practice (at least they are final).
   */
  public final String token;
  public final int start;
  public final int end;

  public Token(String token, int start) {
    if (token == null || token.trim().equals("")) {
      throw new IllegalArgumentException("token must not be null or empty");
    }
    this.token = token.toLowerCase();

    if (start < 0) {
      throw new IllegalArgumentException("start must be >= 0");
    }
    this.start = start;

    this.end = start + token.length();
  }

  /**
   * Although the Token class contains position information in the {@code start} and {@code end} fields, these
   * position fields are not taken into consideration when calculating equality or the hash code. This is because
   * for current purposes we want equality between tokens to depend only on the {@code token} field.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || !(obj instanceof Token)) {
      return false;
    }
    return token.equals(((Token) obj).token);
  }

  @Override
  public int hashCode() {
    return Objects.hash(token);
  }

  @Override
  public int compareTo(Token token) {
    return start - token.start;
  }
}
