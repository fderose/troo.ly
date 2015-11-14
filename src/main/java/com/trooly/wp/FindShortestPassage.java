package com.trooly.wp;

import com.trooly.wp.tokenize.ITokenizer;
import com.trooly.wp.tokenize.Token;

import java.util.*;

public class FindShortestPassage extends FindAbstractBase {

  public static final String SELECTOR = "-sp";

  public static void checkUsage(String[] args) {
    if (args.length < 4) { // We need a source for the text, k, and at least two distinct words to match.
      System.err.println(getUsage());
      System.exit(-1);
    }
  }

  public static String getUsage() {
    return "To find shortest passage: WordProximity -sp source k word word ...";
  }

  private final int k;

  public FindShortestPassage(String text, int k, Set<String> words, ITokenizer tokenizer) throws Exception {
    super(text, words, tokenizer);

    if (k < 2 || k > words.size()) {
      throw new Exception("it must be true that: 2 <= k <= number of words to match");
    }
    this.k = k;
  }

  public Passage execute() throws Exception {

    if (matchedTokens.size() < k) {
      throw new Exception("The text does not contain k words from the set: " + String.join(", ", words));
    }

    /**
     * Build list of candidate passages
     *
     * The complexity of this algorithm is O(allMatchedTokens.size() * incompletePassages.size()), that is, polynomial.
     * This is mitigated by the fact that once a complete passage is found (a passage with all k tokens),
     * it is removed from incompletePassages, which keeps the size of incompletePassages small.
     *
     * An example of a pathological case would be a string like the following:
     *
     *   (word1 )+ word2
     *
     * That is, a long sequence of the first word to be searched for, followed by a single occurrence of the second word.
     * In this case, the algorithm would continue to add new passages to the incompletePassages list without ever
     * being able to complete any passage until the very last token is encountered, when it would complete all passages.
     *
     * This is the solution I came up with. Then, I googled the problem on the web and found the following links:
     *
     * http://stackoverflow.com/questions/2734313/google-search-results-how-to-find-the-minimum-window-that-contains-all-the-sear
     * http://rcrezende.blogspot.com/2010/08/smallest-relevant-text-snippet-for.html
     *
     * We can discuss the relative merits of the different solutions if we meet.
     */
    List<Set<Token>> incompletePassages = new ArrayList<>();
    List<Passage> passages = new ArrayList<>();
    List<Set<Token>> removeList = new ArrayList<>(); // To keep incompletePassages small while avoiding a
    // ConcurrentModificationException while iterating thru incompletePassages
    for (Token matchedToken : allMatchedTokens) {
      incompletePassages.add(new HashSet<>());
      removeList.clear();
      for (Set<Token> tokenSet : incompletePassages) {
        // Make sure each passage has one each of the desired k tokens
        if (!tokenSet.contains(matchedToken)) {
          tokenSet.add(matchedToken);
        }
        // When a passage has accumulated k tokens, save it as a candidate passage.
        if (tokenSet.size() == k) {
          passages.add(new Passage(tokenSet));
          removeList.add(tokenSet);
        }
      }
      incompletePassages.removeAll(removeList);
    }

    // Sort passages by length (distance between the end of the first token and the start of the last token)
    Collections.sort(passages);

    // Return the passage with the shortest length.
    return passages.get(0);
  }
}