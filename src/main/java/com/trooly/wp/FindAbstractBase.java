package com.trooly.wp;

import com.trooly.wp.tokenize.ITokenizer;
import com.trooly.wp.tokenize.Token;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

abstract class FindAbstractBase {
  protected final String text;
  protected final Set<String> words;
  protected final ITokenizer tokenizer;
  protected final List<Token> allMatchedTokens;
  protected final Set<String> matchedTokens = new HashSet<>();

  protected FindAbstractBase(String text, Set<String> words, ITokenizer tokenizer) throws Exception {
    if (text == null) {
      throw new IllegalArgumentException("text must not be null");
    }
    this.text = text;

    if (words == null || words.size() < 1) {
      throw new IllegalArgumentException("words must not be null or empty");
    }
    this.words = new HashSet<>();
    for (String word : words) {
      this.words.add(word.toLowerCase());
    }

    if (tokenizer == null) {
      throw new IllegalArgumentException("tokenizer must not be null");
    }
    this.tokenizer = tokenizer;

    allMatchedTokens = tokenizer.tokenize(text, words, matchedTokens);
  }
}
