package com.trooly.wp;

import com.trooly.wp.materialize.IMaterializeText;
import com.trooly.wp.materialize.MaterializeTextFromFile;
import com.trooly.wp.materialize.TikaMaterializeTextFromUrl;
import com.trooly.wp.tokenize.DumbTokenizer;
import com.trooly.wp.tokenize.ITokenizer;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main {

    private static IMaterializeText getTextMaterializer(String source) throws IOException {
        URL url = null;
        try {
            url = new URL(source);
        } catch (Exception e) {}
        if (url != null) {
            return new TikaMaterializeTextFromUrl(new URL(source));
        } else {
            return new MaterializeTextFromFile(source);
        }
    }

    private static Set<String> getWordsToMatch(String[] args, int firstTokenArg) throws Exception {
        // The words to match are in arguments 1 thru args.length - 1
        Set<String> words = new HashSet<>();
        for (int k = firstTokenArg; k < args.length; k++) {
            String word = args[k].toLowerCase();
            if (words.contains(word)) {
                throw new Exception("Words must be distinct");
            }
            words.add(word);
        }
        return words;
    }

    private static ITokenizer getTokenizer() {
        return new DumbTokenizer();
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1 || !(args[0].equals(FindShortestPassage.SELECTOR) || args[0].equals(FindClosestTokens.SELECTOR))) {
            System.out.println(String.format("%s\n%s", FindShortestPassage.getUsage(), FindClosestTokens.getUsage()));
            System.exit(-1);
        }

        String selectorFlag = args[0];
        args = Arrays.copyOfRange(args, 1, args.length);

        if (selectorFlag.equals(FindShortestPassage.SELECTOR)) {

            FindShortestPassage.checkUsage(args);
            String text = getTextMaterializer(args[0]).getText();
            int k = Integer.parseInt(args[1]);
            Set<String> words = getWordsToMatch(args, 2);
            ITokenizer tokenizer = getTokenizer();
            System.out.println(StringBuilder.buildString(new FindShortestPassage(text, k, words, tokenizer).execute(), text, words));

        } else /* if s.equals(FindClosestTokens.SELECTOR) */ {

            FindClosestTokens.checkUsage(args);
            String text = getTextMaterializer(args[0]).getText();
            Set<String> words = getWordsToMatch(args, 1);
            ITokenizer tokenizer = getTokenizer();
            System.out.println(StringBuilder.buildString(new FindClosestTokens(text, words, tokenizer).execute(), text, words));

        }
    }
}