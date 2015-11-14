package com.trooly.wp.materialize;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Implementation of a very basic class that materializes text when provided a url.
 */
public class DumbMaterializeTextFromUrl extends HTMLEditorKit.ParserCallback implements IMaterializeText {
  private final URL url;
  private final StringBuffer sb = new StringBuffer();

  public DumbMaterializeTextFromUrl(URL url) {
    if (url == null) {
      throw new IllegalArgumentException("url must not be null");
    }
    this.url = url;
  }

  private void parse(Reader in) throws IOException {
    ParserDelegator delegator = new ParserDelegator();
    delegator.parse(in, this, Boolean.TRUE);
  }

  @Override
  public void handleText(char[] text, int pos) {
    sb.append(text);
  }

  public String getText() throws IOException {
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("GET");
    con.setRequestProperty("User-Agent", "Mozilla/5.0");
    BufferedReader in = null;
    try {
      in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      parse(in);
      return sb.toString();
    } finally {
      if (in != null) {
        in.close();
      }
    }

  }
}  
