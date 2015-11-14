package com.trooly.wp.materialize;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Implementation of a class that uses Tika to materialize text from a url.
 */
public class TikaMaterializeTextFromUrl implements IMaterializeText {
  private final URL url;
  private final StringBuffer sb = new StringBuffer();

  public TikaMaterializeTextFromUrl(URL url) {
    if (url == null) {
      throw new IllegalArgumentException("url must not be null");
    }
    this.url = url;
  }

  public String getText() throws IOException {
    try {
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestProperty("User-Agent", "Mozilla/5.0");
      return new Tika().parseToString(con.getInputStream());
    } catch (TikaException e) {
      throw new IOException(e);
    }
  }
}  
