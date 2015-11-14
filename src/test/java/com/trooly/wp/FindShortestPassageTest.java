package com.trooly.wp;

import com.trooly.wp.tokenize.DumbTokenizer;
import com.trooly.wp.tokenize.ITokenizer;
import com.trooly.wp.tokenize.Token;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.commons.lang.RandomStringUtils;

import java.util.*;

public class FindShortestPassageTest extends TestCase {
  ITokenizer tokenizer = new DumbTokenizer();

  /**
   * Create the test case
   *
   * @param testName name of the test case
   */
  public FindShortestPassageTest(String testName) {
    super(testName);
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite() {
    return new TestSuite(FindShortestPassageTest.class);
  }

  public void testTextMustNotBeNull() {
    String errMsg = "text must not be null";
    try {
      new FindShortestPassage(null, 1, new HashSet<>(Arrays.asList(new String[]{"a"})), tokenizer).execute();
      fail("Test should have failed with error: " + errMsg);
    } catch (Exception e) {
      assertTrue(e.getMessage().equals(errMsg));
    }
  }

  public void testKMustBeInCorrectRange() {
    String errMsg = "it must be true that: 2 <= k <= number of words to match";
    try {
      new FindShortestPassage("a", 0, new HashSet<>(Arrays.asList(new String[]{"a"})), tokenizer).execute();
      fail("Test should have failed with error: " + errMsg);
    } catch (Exception e) {
      assertTrue(e.getMessage().equals(errMsg));
    }

    try {
      new FindShortestPassage("a", 1, new HashSet<>(Arrays.asList(new String[]{"a"})), tokenizer).execute();
      fail("Test should have failed with error: " + errMsg);
    } catch (Exception e) {
      assertTrue(e.getMessage().equals(errMsg));
    }

    try {
      new FindShortestPassage("a", 2, new HashSet<>(Arrays.asList(new String[]{"a"})), tokenizer).execute();
      fail("Test should have failed with error: " + errMsg);
    } catch (Exception e) {
      assertTrue(e.getMessage().equals(errMsg));
    }

    try {
      new FindShortestPassage("a", 3, new HashSet<>(Arrays.asList(new String[]{"a", "b"})), tokenizer).execute();
      fail("Test should have failed with error: " + errMsg);
    } catch (Exception e) {
      assertTrue(e.getMessage().equals(errMsg));
    }
  }

  public void testWordsMustNotBeNullOrEmpty() {
    String errMsg = "words must not be null or empty";
    try {
      new FindClosestTokens("a", null, tokenizer).execute();
      fail("Test should have failed with error: " + errMsg);
    } catch (Exception e) {
      assertTrue(e.getMessage().equals(errMsg));
    }
    try {
      new FindClosestTokens("", new HashSet<>(Arrays.asList(new String[0])), tokenizer).execute();
      fail("Test should have failed with error: " + errMsg);
    } catch (Exception e) {
      assertTrue(e.getMessage().equals(errMsg));
    }
  }

  public void testTokenizerMustNotBeNull() {
    String errMsg = "tokenizer must not be null";
    try {
      new FindShortestPassage("a", 1, new HashSet<>(Arrays.asList(new String[]{"a"})), null).execute();
      fail("Test should have failed with error: " + errMsg);
    } catch (Exception e) {
      assertTrue(e.getMessage().equals(errMsg));
    }
  }

  public void testTextMustContainAtLeastKTokens() {
    String errMsg = "The text does not contain k words from the set: a, b";
    try {
      new FindShortestPassage("a", 2, new HashSet<>(Arrays.asList(new String[]{"a", "b"})), tokenizer).execute();
      fail("Test should have failed with error: " + errMsg);
    } catch (Exception e) {
      assertTrue(e.getMessage().equals(errMsg));
    }
  }

  public void testFindsShortestPassage() {

    Passage expected = null;
    Passage actual = null;
    try {
      String text = "a b";
      String[] words = new String[]{"a", "b"};
      int k = words.length;
      List<Token> tokens = new ArrayList<>();
      tokens.add(new Token("a", 0));
      tokens.add(new Token("b", 2));
      expected = new Passage(tokens);
      actual = new FindShortestPassage(text, k, new HashSet<>(Arrays.asList(words)), tokenizer).execute();
      assertNotNull(actual);
      assertEquals(actual, expected);
    } catch (Exception e) {
      StringBuilder.buildString(expected, actual);
    }

    expected = null;
    actual = null;
    try {
      String text = "b a";
      String[] words = new String[]{"a", "b"};
      int k = words.length;
      List<Token> tokens = new ArrayList<>();
      tokens.add(new Token("b", 0));
      tokens.add(new Token("a", 2));
      expected = new Passage(tokens);
      actual = new FindShortestPassage(text, k, new HashSet<>(Arrays.asList(words)), tokenizer).execute();
      assertNotNull(actual);
      assertEquals(actual, expected);
    } catch (Exception e) {
      StringBuilder.buildString(expected, actual);
    }

    expected = null;
    actual = null;
    try {
      String text = "a c b";
      String[] words = new String[]{"a", "b"};
      int k = words.length;
      List<Token> tokens = new ArrayList<>();
      tokens.add(new Token("a", 0));
      tokens.add(new Token("b", 4));
      expected = new Passage(tokens);
      actual = new FindShortestPassage(text, k, new HashSet<>(Arrays.asList(words)), tokenizer).execute();
      assertNotNull(actual);
      assertEquals(actual, expected);
    } catch (Exception e) {
      StringBuilder.buildString(expected, actual);
    }

    expected = null;
    actual = null;
    try {
      String text = "b c a";
      String[] words = new String[]{"a", "b"};
      int k = words.length;
      List<Token> tokens = new ArrayList<>();
      tokens.add(new Token("b", 0));
      tokens.add(new Token("a", 4));
      expected = new Passage(tokens);
      actual = new FindShortestPassage(text, k, new HashSet<>(Arrays.asList(words)), tokenizer).execute();
      assertNotNull(actual);
      assertEquals(actual, expected);
    } catch (Exception e) {
      StringBuilder.buildString(expected, actual);
    }

    expected = null;
    actual = null;
    try {
      String text = "a c c b";
      String[] words = new String[]{"a", "b"};
      int k = words.length;
      List<Token> tokens = new ArrayList<>();
      tokens.add(new Token("a", 0));
      tokens.add(new Token("b", 6));
      expected = new Passage(tokens);
      actual = new FindShortestPassage(text, k, new HashSet<>(Arrays.asList(words)), tokenizer).execute();
      assertNotNull(actual);
      assertEquals(actual, expected);
    } catch (Exception e) {
      StringBuilder.buildString(expected, actual);
    }

    expected = null;
    actual = null;
    try {
      String text = "c a b";
      String[] words = new String[]{"a", "b"};
      int k = words.length;
      List<Token> tokens = new ArrayList<>();
      tokens.add(new Token("a", 2));
      tokens.add(new Token("b", 4));
      expected = new Passage(tokens);
      actual = new FindShortestPassage(text, k, new HashSet<>(Arrays.asList(words)), tokenizer).execute();
      assertNotNull(actual);
      assertEquals(actual, expected);
    } catch (Exception e) {
      StringBuilder.buildString(expected, actual);
    }

    expected = null;
    actual = null;
    try {
      String text = "a c c c b c c c c a c b";
      String[] words = new String[]{"a", "b"};
      int k = words.length;
      List<Token> tokens = new ArrayList<>();
      tokens.add(new Token("a", 18));
      tokens.add(new Token("b", 22));
      expected = new Passage(tokens);
      actual = new FindShortestPassage(text, k, new HashSet<>(Arrays.asList(words)), tokenizer).execute();
      assertNotNull(actual);
      assertEquals(actual, expected);
    } catch (Exception e) {
      StringBuilder.buildString(expected, actual);
    }

    expected = null;
    actual = null;
    try {
      String text = "a b c";
      String[] words = new String[]{"a", "b", "c"};
      int k = words.length;
      List<Token> tokens = new ArrayList<>();
      tokens.add(new Token("a", 0));
      tokens.add(new Token("b", 2));
      tokens.add(new Token("c", 4));
      expected = new Passage(tokens);
      actual = new FindShortestPassage(text, k, new HashSet<>(Arrays.asList(words)), tokenizer).execute();
      assertNotNull(actual);
      assertEquals(actual, expected);
    } catch (Exception e) {
      StringBuilder.buildString(expected, actual);
    }

    expected = null;
    actual = null;
    try {
      String text = "c a b";
      String[] words = new String[]{"a", "b", "c"};
      int k = words.length;
      List<Token> tokens = new ArrayList<>();
      tokens.add(new Token("c", 0));
      tokens.add(new Token("a", 2));
      tokens.add(new Token("b", 4));
      expected = new Passage(tokens);
      actual = new FindShortestPassage(text, k, new HashSet<>(Arrays.asList(words)), tokenizer).execute();
      assertNotNull(actual);
      assertEquals(actual, expected);
    } catch (Exception e) {
      StringBuilder.buildString(expected, actual);
    }

    expected = null;
    actual = null;
    try {
      String text = " y c q a b x";
      String[] words = new String[]{"a", "b", "c"};
      int k = words.length;
      List<Token> tokens = new ArrayList<>();
      tokens.add(new Token("c", 3));
      tokens.add(new Token("a", 7));
      tokens.add(new Token("b", 9));
      expected = new Passage(tokens);
      actual = new FindShortestPassage(text, k, new HashSet<>(Arrays.asList(words)), tokenizer).execute();
      assertNotNull(actual);
      assertEquals(actual, expected);
    } catch (Exception e) {
      StringBuilder.buildString(expected, actual);
    }

    expected = null;
    actual = null;
    try {
      String text = " y c c q a b x";
      String[] words = new String[]{"a", "b", "c"};
      int k = words.length;
      List<Token> tokens = new ArrayList<>();
      tokens.add(new Token("c", 5));
      tokens.add(new Token("a", 9));
      tokens.add(new Token("b", 11));
      expected = new Passage(tokens);
      actual = new FindShortestPassage(text, k, new HashSet<>(Arrays.asList(words)), tokenizer).execute();
      assertNotNull(actual);
      assertEquals(actual, expected);
    } catch (Exception e) {
      StringBuilder.buildString(expected, actual);
    }

    expected = null;
    actual = null;
    try {
      String text = " y c q a b x";
      String[] words = new String[]{"y", "q", "x"};
      int k = words.length - 2;
      List<Token> tokens = new ArrayList<>();
      tokens.add(new Token("y", 1));
      expected = new Passage(tokens);
      actual = new FindShortestPassage(text, k, new HashSet<>(Arrays.asList(words)), tokenizer).execute();
      assertNotNull(actual);
      assertEquals(actual, expected);
    } catch (Exception e) {
      StringBuilder.buildString(expected, actual);
    }

    expected = null;
    actual = null;
    try {
      String text = "a b b a c";
      String[] words = new String[]{"a", "b", "c"};
      int k = words.length;
      List<Token> tokens = new ArrayList<>();
      tokens.add(new Token("b", 4));
      tokens.add(new Token("a", 6));
      tokens.add(new Token("c", 8));
      expected = new Passage(tokens);
      actual = new FindShortestPassage(text, k, new HashSet<>(Arrays.asList(words)), tokenizer).execute();
      assertNotNull(actual);
      assertEquals(actual, expected);
    } catch (Exception e) {
      StringBuilder.buildString(expected, actual);
    }

    expected = null;
    actual = null;
    try {
      String text = "a b d c b c a c d b a";
      String[] words = new String[]{"a", "b", "c"};
      int k = words.length;
      List<Token> tokens = new ArrayList<>();
      tokens.add(new Token("b", 8));
      tokens.add(new Token("c", 10));
      tokens.add(new Token("a", 12));
      expected = new Passage(tokens);
      actual = new FindShortestPassage(text, k, new HashSet<>(Arrays.asList(words)), tokenizer).execute();
      assertNotNull(actual);
      assertEquals(actual, expected);
    } catch (Exception e) {
      StringBuilder.buildString(expected, actual);
    }

    expected = null;
    actual = null;
    try {
      String text = "C d d d A d d d B d d A d d C d B A d d d C";
      String[] words = new String[]{"a", "b", "c"};
      int k = words.length;
      List<Token> tokens = new ArrayList<>();
      tokens.add(new Token("c", 28));
      tokens.add(new Token("b", 32));
      tokens.add(new Token("a", 34));
      expected = new Passage(tokens);
      actual = new FindShortestPassage(text, k, new HashSet<>(Arrays.asList(words)), tokenizer).execute();
      assertNotNull(actual);
      assertEquals(actual, expected);
    } catch (Exception e) {
      StringBuilder.buildString(expected, actual);
    }

    expected = null;
    actual = null;
    try {
      String text = "x a x b x x c c x a b a c";
      String[] words = new String[]{"a", "b", "c"};
      int k = words.length;
      List<Token> tokens = new ArrayList<>();
      tokens.add(new Token("b", 20));
      tokens.add(new Token("a", 22));
      tokens.add(new Token("c", 24));
      expected = new Passage(tokens);
      actual = new FindShortestPassage(text, k, new HashSet<>(Arrays.asList(words)), tokenizer).execute();
      assertNotNull(actual);
      assertEquals(actual, expected);
    } catch (Exception e) {
      StringBuilder.buildString(expected, actual);
    }

    expected = null;
    actual = null;
    try {
      Random random = new Random();
      int k = 3;
      for (int i = 0; i < 1000; i++) {
        String[] words = new String[]{
                RandomStringUtils.randomAlphabetic(40),
                RandomStringUtils.randomAlphabetic(40),
                RandomStringUtils.randomAlphabetic(40)};
        List<Token> tokens = new ArrayList<>();
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (int j = 0; j < 3; j++) {
          String s = RandomStringUtils.random(Math.abs(random.nextInt(20)));
          sb.append(s);
          sb.append(" ");
          int wordStart = sb.length();
          sb.append(words[j]);
          tokens.add(new Token(words[j], wordStart));
          sb.append(" ");
        }
        sb.append(RandomStringUtils.random(Math.abs(random.nextInt(20))));
        expected = new Passage(tokens);
        actual = new FindShortestPassage(sb.toString(), k, new HashSet<>(Arrays.asList(words)), tokenizer).execute();
        assertNotNull(actual);
        assertEquals(actual, expected);
      }
    } catch (Exception e) {
      StringBuilder.buildString(expected, actual);
    }
  }
}