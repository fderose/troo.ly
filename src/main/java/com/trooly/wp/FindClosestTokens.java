package com.trooly.wp;

import com.trooly.wp.tokenize.ITokenizer;
import com.trooly.wp.tokenize.TokenPair;

import java.util.HashSet;
import java.util.Set;

public class FindClosestTokens extends FindAbstractBase {

  public static final String SELECTOR = "-ct";

  public static void checkUsage(String[] args) {
    if (args.length < 3) { // We need a source for the text and at least two distinct words to match.
      System.err.println(getUsage());
      System.exit(-1);
    }
  }

  public static String getUsage() {
    return "To find closest tokens:   run.sh -ct source word word ...";
  }

  public FindClosestTokens(String text, Set<String> words, ITokenizer tokenizer) throws Exception {
    super(text, words, tokenizer);

    if (words.size() < 2) {
      throw new Exception("There must be at least 2 words");
    }
  }

  public TokenPair execute() throws Exception {

    if (matchedTokens.size() < 2) {
      throw new Exception("The text does not contain at least two of the words in the set: " + String.join(", ", words));
    }

    TokenPair pairWithLeastDistanceBetweenTokens = TokenPair.TOKEN_PAIR_WITH_MAX_DIST_BETWEEN_TOKENS;
    for (int j = 1; j < allMatchedTokens.size(); j++) {
      TokenPair currentTokenInfoPair = new TokenPair(allMatchedTokens.get(j - 1), allMatchedTokens.get(j));
      if (!currentTokenInfoPair.areTokensEqual() && (currentTokenInfoPair.compareTo(pairWithLeastDistanceBetweenTokens) < 0)) {
        pairWithLeastDistanceBetweenTokens = currentTokenInfoPair;
      }
    }
    return pairWithLeastDistanceBetweenTokens;
  }
}
