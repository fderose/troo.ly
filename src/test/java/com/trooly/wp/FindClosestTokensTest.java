package com.trooly.wp;

import com.trooly.wp.tokenize.DumbTokenizer;
import com.trooly.wp.tokenize.ITokenizer;
import com.trooly.wp.tokenize.Token;
import com.trooly.wp.tokenize.TokenPair;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Arrays;
import java.util.HashSet;

public class FindClosestTokensTest extends TestCase {
  ITokenizer tokenizer = new DumbTokenizer();

  /**
   * Create the test case
   *
   * @param testName name of the test case
   */
  public FindClosestTokensTest(String testName) {
    super(testName);
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite() {
    return new TestSuite(FindClosestTokensTest.class);
  }

  public void testTextMustNotBeNull() {
    String errMsg = "text must not be null";
    try {
      new FindClosestTokens(null, new HashSet<>(Arrays.asList(new String[]{"a"})), tokenizer).execute();
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
      new FindClosestTokens("a", new HashSet<>(Arrays.asList(new String[]{"a"})), null).execute();
      fail("Test should have failed with error: " + errMsg);
    } catch (Exception e) {
      assertTrue(e.getMessage().equals(errMsg));
    }
  }


  public void testThereMustBeAtLeastTwoWords() {
    String errMsg = "There must be at least 2 words";
    try {
      new FindClosestTokens("a", new HashSet<>(Arrays.asList(new String[]{"a"})), tokenizer).execute();
      fail("Test should have failed with error: " + errMsg);
    } catch (Exception e) {
      assertTrue(e.getMessage().equals(errMsg));
    }
  }

  public void testTextMustContainAtLeast2Tokens() {
    String errMsg = "The text does not contain at least two of the words in the set: a, b";
    try {
      new FindClosestTokens("a", new HashSet<>(Arrays.asList(new String[]{"a", "b"})), tokenizer).execute();
      fail("Test should have failed with error: " + errMsg);
    } catch (Exception e) {
      assertTrue(e.getMessage().equals(errMsg));
    }
  }

  public void testCanFindClosestTokens() {

    TokenPair expected = null;
    TokenPair actual = null;
    try {
      expected = new TokenPair(new Token("a", 0), new Token("b", 2));
      actual = new FindClosestTokens("a b", new HashSet<>(Arrays.asList(new String[]{"a", "b"})), tokenizer).execute();
      assertNotNull(actual);
      assertEquals(actual, expected);

      actual = new FindClosestTokens("a b", new HashSet<>(Arrays.asList(new String[]{"a", "b", "c"})), tokenizer).execute();
      assertNotNull(actual);
      assertEquals(actual, expected);
    } catch (Exception e) {
      StringBuilder.buildString(expected, actual);
    }

    expected = actual = null;
    try {
      expected = new TokenPair(new Token("b", 0), new Token("a", 2));
      actual = new FindClosestTokens("b a", new HashSet<>(Arrays.asList(new String[]{"a", "b"})), tokenizer).execute();
      assertNotNull(actual);
      assertEquals(actual, expected);

      actual = new FindClosestTokens("b a", new HashSet<>(Arrays.asList(new String[]{"a", "b", "c"})), tokenizer).execute();
      assertNotNull(actual);
      assertEquals(actual, expected);
    } catch (Exception e) {
      StringBuilder.buildString(expected, actual);
    }

    expected = actual = null;
    try {
      expected = new TokenPair(new Token("a", 0), new Token("b", 2));
      actual = new FindClosestTokens("a b c", new HashSet<>(Arrays.asList(new String[]{"a", "b"})), tokenizer).execute();
      assertNotNull(actual);
      assertEquals(actual, expected);

      actual = new FindClosestTokens("a b c", new HashSet<>(Arrays.asList(new String[]{"a", "b", "c"})), tokenizer).execute();
      assertNotNull(actual);
      assertEquals(actual, expected);
    } catch (Exception e) {
      StringBuilder.buildString(expected, actual);
    }

    expected = actual = null;
    try {
      expected = new TokenPair(new Token("b", 2), new Token("a", 4));
      actual = new FindClosestTokens("c b a", new HashSet<>(Arrays.asList(new String[]{"a", "b"})), tokenizer).execute();
      assertNotNull(actual);
      assertEquals(actual, expected);

      expected = new TokenPair(new Token("c", 0), new Token("b", 2));
      actual = new FindClosestTokens("c b a", new HashSet<>(Arrays.asList(new String[]{"a", "b", "c"})), tokenizer).execute();
      assertNotNull(actual);
      assertEquals(actual, expected);
    } catch (Exception e) {
      StringBuilder.buildString(expected, actual);
    }

    expected = actual = null;
    try {
      expected = new TokenPair(new Token("a", 2), new Token("b", 4));
      actual = new FindClosestTokens("c a b", new HashSet<>(Arrays.asList(new String[]{"a", "b"})), tokenizer).execute();
      assertNotNull(actual);
      assertEquals(actual, expected);

      expected = new TokenPair(new Token("c", 0), new Token("a", 2));
      actual = new FindClosestTokens("c a b", new HashSet<>(Arrays.asList(new String[]{"a", "b", "c"})), tokenizer).execute();
      assertNotNull(actual);
      assertEquals(actual, expected);
    } catch (Exception e) {
      StringBuilder.buildString(expected, actual);
    }

    expected = actual = null;
    try {
      expected = new TokenPair(new Token("b", 0), new Token("a", 2));
      actual = new FindClosestTokens("b a c", new HashSet<>(Arrays.asList(new String[]{"a", "b"})), tokenizer).execute();
      assertNotNull(actual);
      assertEquals(actual, expected);

      actual = new FindClosestTokens("b a c", new HashSet<>(Arrays.asList(new String[]{"a", "b", "c"})), tokenizer).execute();
      assertNotNull(actual);
      assertEquals(actual, expected);
    } catch (Exception e) {
      StringBuilder.buildString(expected, actual);
    }

    expected = actual = null;
    try {
      expected = new TokenPair(new Token("a", 0), new Token("b", 4));
      actual = new FindClosestTokens("a c b", new HashSet<>(Arrays.asList(new String[]{"a", "b"})), tokenizer).execute();
      assertNotNull(actual);
      assertEquals(actual, expected);

      expected = new TokenPair(new Token("a", 0), new Token("c", 2));
      actual = new FindClosestTokens("a c b", new HashSet<>(Arrays.asList(new String[]{"a", "b", "c"})), tokenizer).execute();
      assertNotNull(actual);
      assertEquals(actual, expected);
    } catch (Exception e) {
      StringBuilder.buildString(expected, actual);
    }

    expected = actual = null;
    try {
      expected = new TokenPair(new Token("a", 8), new Token("b", 10));
      actual = new FindClosestTokens("a a a a a b b b b b b b", new HashSet<>(Arrays.asList(new String[]{"a", "b"})), tokenizer).execute();
      assertNotNull(actual);
      assertEquals(actual, expected);

      actual = new FindClosestTokens("a a a a a b b b b b b b", new HashSet<>(Arrays.asList(new String[]{"a", "b", "c"})), tokenizer).execute();
      assertNotNull(actual);
      assertEquals(actual, expected);
    } catch (Exception e) {
      StringBuilder.buildString(expected, actual);
    }

    expected = actual = null;
    try {
      expected = new TokenPair(new Token("a", 0), new Token("b", 20));
      actual = new FindClosestTokens("a c d e f g h i j k b", new HashSet<>(Arrays.asList(new String[]{"a", "b"})), tokenizer).execute();
      assertNotNull(actual);
      assertEquals(actual, expected);

      expected = new TokenPair(new Token("a", 0), new Token("c", 2));
      actual = new FindClosestTokens("a c d e f g h i j k b", new HashSet<>(Arrays.asList(new String[]{"a", "b", "c"})), tokenizer).execute();
      assertNotNull(actual);
      assertEquals(actual, expected);
    } catch (Exception e) {
      StringBuilder.buildString(expected, actual);
    }

    expected = actual = null;
    try {
      expected = new TokenPair(new Token("a", 8), new Token("b", 12));
      actual = new FindClosestTokens("a a a a a c b b b b b b b", new HashSet<>(Arrays.asList(new String[]{"a", "b"})), tokenizer).execute();
      assertNotNull(actual);
      assertEquals(actual, expected);

      expected = new TokenPair(new Token("a", 8), new Token("c", 10));
      actual = new FindClosestTokens("a a a a a c b b b b b b b", new HashSet<>(Arrays.asList(new String[]{"a", "b", "c"})), tokenizer).execute();
      assertNotNull(actual);
      assertEquals(actual, expected);
    } catch (Exception e) {
      StringBuilder.buildString(expected, actual);
    }

    expected = actual = null;
    try {
      expected = new TokenPair(new Token("e", 28), new Token("f", 30));
      actual = new FindClosestTokens("a x x x x b x x x c x x d x e f x g x x h x x x i x x x x j",
        new HashSet<>(Arrays.asList(new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j"})), tokenizer).execute();
      assertNotNull(actual);
      assertEquals(actual, expected);

      expected = new TokenPair(new Token("c", 18), new Token("d", 24));
      actual = new FindClosestTokens("a x x x x b x x x c x x d x x e x x f x x g x x h x x x i x x x x j",
        new HashSet<>(Arrays.asList(new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j"})), tokenizer).execute();
      assertNotNull(actual);
      assertEquals(actual, expected);
    } catch (Exception e) {
      StringBuilder.buildString(expected, actual);
    }

  }
}
